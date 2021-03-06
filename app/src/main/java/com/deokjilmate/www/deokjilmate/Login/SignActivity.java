package com.deokjilmate.www.deokjilmate.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.ResourcesUtil;
import com.deokjilmate.www.deokjilmate.Setting.Terms.TermsActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.Sign_backImage)
    ImageButton backButton;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    private int signType = 0;

    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @BindView(R.id.Sign_email)
    EditText email;

    @BindView(R.id.Sign_pwd)
    EditText pwd;

    @BindView(R.id.Sign_checkPwd)
    EditText checkPwd;

    @BindView(R.id.Sign_next)
    Button next;

    @BindView(R.id.Sign_agree)
    CheckBox agree;

    @BindView(R.id.Sign_provision)
    TextView provision;

    @BindView(R.id.Sign_privacy)
    TextView privacy;

    private String t_email;
    private String t_pwd;
    private String t_pwdCheck;

    private boolean b_email = false;
    private boolean b_pwd = false;
    private boolean b_pwdCheck = false;

    private String r_email =  "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private String r_pwd = "^[a-z0-9_-]{6,}$";   ///6자 이상

    private Pattern email_Pattern;
    private Pattern pwd_Pattern;

    private Matcher email_Match;
    private Matcher pwd_Match;
    private final String LOG = "LOG::Sign";
    private ProgressDialog progressDialog;
   // private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            //Drawable background = this.getResources().getDrawable(R.drawable.gradation);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
            //window.setNavigationBarColor(this.getResources().getColor(R.color.tw__transparent));
            //window.setBackgroundDrawable(background);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());


        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        callbackManager = CallbackManager.Factory.create();

        Glide.with(this).load(R.drawable.topbar_back).into(backButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                   // Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        email_Pattern = Pattern.compile(r_email);
        pwd_Pattern = Pattern.compile(r_pwd);

        mfirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


    }


    @OnClick(R.id.Sign_facebook)
    public void LoginFacebook()//페이스북 로그인 버튼
    {
        signType = 1;
        LoginManager.getInstance().logInWithReadPermissions(SignActivity.this,
                Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    @OnClick(R.id.Sign_twitter)
    public void LoginTwitter()//트위터 로그인 버튼
    {
        signType = 2;
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(SignActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {



                handleTwitterSession(result.data);

            }
            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
    @OnClick(R.id.Sign_google)
    public void LoginGoogle()//구글 로그인 버튼
    {
        signType = 3;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v(LOG, "ClickedGoogle");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (signType)
        {
            case 1://페북 로그인
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case 2://트위터 로그인
                twitterAuthClient.onActivityResult(requestCode, resultCode, data);
                break;
            case 3://구글 로그인
                //startActivityForResult();
                if(requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        Log.v(LOG, "googleSuccess");
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = result.getSignInAccount();
                        Log.v(LOG, account.toString());
                        firebaseAuthWithGoogle(account);
                    } else {
                        Log.v(LOG, "googleFalse");
                    }
                }
                break;
        }
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    private void updateUI(boolean signedIn) {
//        if (signedIn) {
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
//        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @OnClick(R.id.Sign_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.Sign_next)
    public void ClickNext()
    {
        if(email.getText().toString().isEmpty() || pwd.getText().toString().isEmpty() || checkPwd.getText().toString().isEmpty()){
            Toast.makeText(this, "모든 입력란을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pwd.getText().toString().equals(checkPwd.getText().toString())){
            Toast.makeText(this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isPasswordValid(pwd.getText().toString())){
            Toast.makeText(this, "비밀번호 패턴을 확인해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!ResourcesUtil.checkEmail(email.getText().toString())){
            Toast.makeText(this, "이메일을 확인해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!agree.isChecked()){
            Toast.makeText(this, "이용약관에 동의해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        createAcctount(email.getText().toString(), pwd.getText().toString());

    }
    public void createAcctount(final String email, final String pwd){
        makeDialog("잠시만 기다려주세요.");
        mfirebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG, "createUserWithEmail:success");
                            singWithEmailPassword(email, pwd);
                        } else {
                            // If sign in fails, display a message to the user.
                            singWithEmailPassword(email, pwd);
                            Log.w(LOG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignActivity.this, "존재하는 계정입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void singWithEmailPassword(final String email, final String pwd){
        mfirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(LOG, "signInWithEmail:success");

                    //makeDialog("처리중입니다.");
                    FirebaseUser user = mfirebaseAuth.getCurrentUser();

                    Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);

                    intent.putExtra("uid", user.getUid());
                    Log.v(LOG, user.getUid());
                    intent.putExtra("member_email", email);
                    intent.putExtra("notSns", true);
                    intent.putExtra("member_passwd", pwd);
                    intent.putExtra("type", 1);
                    progressDialog.dismiss();
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(LOG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(SignActivity.this, "존재하는 계정입니다.",
                            Toast.LENGTH_SHORT).show();
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful()) {
                }
                // [END_EXCLUDE]
            }
        });

    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        makeDialog("잠시만 기다려주세요.");
        Log.v(LOG, acct.getIdToken().toString());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Toast.makeText(SignActivity.this, "존재하는 계정입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                       // makeDialog("처리중입니다.");

                        FirebaseUser user = mfirebaseAuth.getCurrentUser();

                        Log.v(LOG, user.getUid());
                        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                        intent.putExtra("uid", user.getUid());
                        intent.putExtra("notSns", false);
                        intent.putExtra("type", 4);
                        progressDialog.dismiss();
                        startActivity(intent);
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        makeDialog("잠시만 기다려주세요.");

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                           // makeDialog("처리중입니다.");

                            FirebaseUser user = mfirebaseAuth.getCurrentUser();
//                            Toast.makeText(SignActivity.this, "Authentication suceess.",
//                                    Toast.LENGTH_SHORT).show();

                            Log.v(LOG, user.getUid());
                            Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                            intent.putExtra("uid", user.getUid());
                            intent.putExtra("notSns", false);
                            intent.putExtra("type", 2);
                            progressDialog.dismiss();
                            startActivity(intent);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignActivity.this, "존재하는 계정입니다.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }

                });
    }


    private void handleTwitterSession(final TwitterSession session) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        makeDialog("잠시만 기다려주세요.");
        final AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignActivity.this, "존재하는 계정입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //makeDialog("처리중입니다.");

                        //TODO : 가입의 경우 굳이 다이얼로그가 있을 필요는 없음
                        FirebaseUser user = mfirebaseAuth.getCurrentUser();

                        Log.v(LOG, user.getUid());
                        Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
                        intent.putExtra("uid", user.getUid());
                        intent.putExtra("notSns", false);
                        intent.putExtra("type", 3);
                        progressDialog.dismiss();
                        startActivity(intent);
                        //이걸 보내면 됨.
                        //credential.getProvider()
                    }
                });
    }

    public boolean isPasswordValid(final String raw) {
        String Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^&()'/?><,.|*+=-]*)(?=.*[0-9]+).{6,16}$";
        Pattern pattern = Pattern.compile(Passwrod_PATTERN);
        Matcher matcher = pattern.matcher(raw);
        return matcher.matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mfirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void makeDialog(String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @OnClick(R.id.Sign_provision)
    public void clickProvision(){
        Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.Sign_privacy)
    public void clickPrivacy(){
        Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
        startActivity(intent);
    }
}