package com.deokjilmate.www.deokjilmate.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
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
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//여기는 로그인 방법 결정하는 곳(구글, 페북, 트위터, 커스텀)
public class LoginSelectActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    private int loginType = 0;

    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private NetworkService networkService;


    @BindView(R.id.LoginSelect_backImage)
    ImageButton backButton;

    @BindView(R.id.LoginSelect_facebook)
    Button facebookButton;

    @BindView(R.id.LoginSelect_twitter)
    Button twitterButton;

    @BindView(R.id.LoginSelect_google)
    Button googleButton;

    @BindView(R.id.LoginSelect_email)
    EditText email;

    @BindView(R.id.LoginSelect_pwd)
    EditText pwd;

    @BindView(R.id.LoginSelect_customLogin)
    Button customLogin;



    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login_select);
        ButterKnife.bind(this);
        networkService = ApplicationController.getInstance().getNetworkService();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                } else {
                }
            }
        };

       // Glide.with(this).load(R.drawable.toolbar).into(toobarImage);
        Glide.with(this).load(R.drawable.topbar_back).into(backButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Log.v("구글", mGoogleApiClient.toString());

        mfirebaseAuth = FirebaseAuth.getInstance();


    }

    @OnClick(R.id.LoginSelect_findPwd)
    public void FindPwdEvent()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }
    private void handleFacebookAccessToken(AccessToken token) {
       // Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
       // showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mfirebaseAuth.getCurrentUser();
                            Toast.makeText(LoginSelectActivity.this, "Authentication suceess.",
                                    Toast.LENGTH_SHORT).show();


                            //facebookButton.setVisibility(GONE);

                            Call<LoginResponseResult> setSingerRankingCall = networkService.loginSns(new LoginSnsPost(user.getToken(true).toString(), "f"));
                            setSingerRankingCall.enqueue(new Callback<LoginResponseResult>() {
                                @Override
                                public void onResponse(Call<LoginResponseResult> call, Response<LoginResponseResult> response) {
                                    if(response.isSuccessful()) {


                                    }
                                    else
                                    {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
                                        dialog.setTitle("회원 정보가 없습니다.");
                                        dialog.setMessage("회원 가입 하시겠습니까?");

                                        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // YES 선택시 처리할 내용
                                                Log.v("로그인", "로그인");
                                              //  session.getAuthToken().token.toString();

                                                Bundle parameters = new Bundle();
                                                parameters.putString("fields", "id,name,email,gender,birthday");

                                                startActivity(new Intent(getApplicationContext(), SignActivity.class));
                                                finish();
                                            }
                                        });

                                        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // NO 선택시 처리할 내용
                                                mfirebaseAuth.signOut();
                                                dialog.cancel();
                                            }
                                        });
                                        dialog.show();

                                    }

                                }
                                @Override
                                public void onFailure(Call<LoginResponseResult> call, Throwable t) {
                                }
                            });


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }







    @OnClick(R.id.LoginSelect_facebook)
    public void LoginFacebook()//페이스북 로그인 버튼
    {
        loginType = 1;


        LoginManager.getInstance().logInWithReadPermissions(LoginSelectActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {


               //TODO : 여기서 가입 정보 없으면 회원가입 페이지로 넘어가게끔. 유도
                //TODO : if 정보 있음이면 다음 페이지


                //TODO : if 정보 없음이면 밑에 다이얼로그


                handleFacebookAccessToken(loginResult.getAccessToken());
                /*
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
                dialog.setTitle("회원 정보가 없습니다.");
                dialog.setMessage("회원 가입 하시겠습니까?");

                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // YES 선택시 처리할 내용
                        Log.v("로그인", "로그인");
                        loginResult.getAccessToken().getToken().toString();
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");

                        startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                        finish();
                    }
                });

                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // NO 선택시 처리할 내용
                        LoginManager.getInstance().logOut();
                        dialog.cancel();
                    }
                });
                dialog.show();

*/



            }
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @OnClick(R.id.LoginSelect_twitter)
    public void LoginTwitter()//트위터 로그인 버튼
    {
        loginType = 2;
        twitterAuthClient = new TwitterAuthClient();
        Log.v("트윗", "트윗");

        twitterAuthClient.authorize(LoginSelectActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {

                Log.v("트윗", "트윗1");

                handleTwitterSession(result.data);

            }
            @Override
            public void failure(TwitterException exception) {
                Log.v("트윗", "트윗2");

            }
        });

    }
    @OnClick(R.id.LoginSelect_google)
    public void LoginGoogle()//구글 로그인 버튼
    {
        loginType = 3;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        Log.v("구글", signInIntent.toString());
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v("LoginSelect", "ClickedGoogle");
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mfirebaseAuth.getCurrentUser();
                            Toast.makeText(LoginSelectActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                          //  Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }




    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
           // String accc = acct.getIdToken().toString();
            //TODO : 여기서 가입 정보 없으면 회원가입 페이지로. & 레트로핏 적용 여기서
            //TODO : 여기서 가입 정보 없으면 회원가입 페이지로 넘어가게끔. 유도
            //TODO : if 정보 있음이면 다음 페이지



            Log.v("Google", "Google");

            //TODO : if 정보 없음이면 밑에 다이얼로그


            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
            dialog.setTitle("회원 정보가 없습니다.");
            dialog.setMessage("회원 가입 하시겠습니까?");

            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // YES 선택시 처리할 내용
                    Log.v("로그인", "로그인");
                    //acct.getIdToken().toString();

                    Log.v("로그인", acct.getIdToken());


                    startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                    finish();
                }
            });

            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // NO 선택시 처리할 내용
                    //mGoogleApiClient.getContext().getApplicatio

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    dialog.cancel();
                }
            });
            dialog.show();

        } else {
            // Signed out, show unauthenticated UI.
            Log.v("GoogleFalse", "GoogleFalse");
            updateUI(false);
        }
    }

    private void signOut() {
        // Firebase sign out
        mfirebaseAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mfirebaseAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                       // updateUI(null);
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




    @OnClick(R.id.LoginSelect_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
        startActivity(intent);
    }


    private void handleTwitterSession(final TwitterSession session) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        final AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //TODO : 여기서 가입 정보 없으면 회원가입 페이지로. & 레트로핏 적용 여기서
                        //TODO : 여기서 가입 정보 없으면 회원가입 페이지로 넘어가게끔. 유도
                        //TODO : if 정보 있음이면 다음 페이지


                        Log.v("트윗", "트윗3");



                        //TODO : if 정보 없음이면 밑에 다이얼로그


                        Log.v("회원 정보", session.getAuthToken().token.toString());



                        Call<LoginResponseResult> setSingerRankingCall = networkService.loginSns(new LoginSnsPost(session.getAuthToken().toString(), "t"));
                        setSingerRankingCall.enqueue(new Callback<LoginResponseResult>() {
                            @Override
                            public void onResponse(Call<LoginResponseResult> call, Response<LoginResponseResult> response) {
                                if(response.isSuccessful()) {
                                }
                                else
                                {


                                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
                                    dialog.setTitle("회원 정보가 없습니다.");
                                    dialog.setMessage("회원 가입 하시겠습니까?");

                                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // YES 선택시 처리할 내용
                                            Log.v("로그인", "로그인");
                                            session.getAuthToken().token.toString();

                                            Bundle parameters = new Bundle();
                                            parameters.putString("fields", "id,name,email,gender,birthday");

                                            startActivity(new Intent(getApplicationContext(), SignActivity.class));
                                            finish();
                                        }
                                    });

                                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // NO 선택시 처리할 내용
                                            mfirebaseAuth.signOut();
                                            dialog.cancel();
                                        }
                                    });
                                    dialog.show();

                                }

                            }
                            @Override
                            public void onFailure(Call<LoginResponseResult> call, Throwable t) {
                                Log.v("트윗", "트윗4");

                            }
                        });

                    }
                });
    }

    @OnClick(R.id.LoginSelect_customLogin)
    public void clickCustom(){
        Call<LoginResponseResult> setSingerRankingCall = networkService.login(new LoginPost(email.getText().toString(), pwd.getText().toString()));
        setSingerRankingCall.enqueue(new Callback<LoginResponseResult>() {
            @Override
            public void onResponse(Call<LoginResponseResult> call, Response<LoginResponseResult> response) {
                //if(response.body().) {
                Log.v("들들들", "들들들");
                    Log.v("멤버", String.valueOf(response.body().result.member_id));
                    Log.v("멤버", String.valueOf(response.body().result.b_vote_count));
                    Log.v("멤버", String.valueOf(response.body().result.singer_info.album_img));
                    Log.v("멤버", String.valueOf(response.body().result.singer_info.choice_count));
                    Log.v("멤버", String.valueOf(response.body().result.singer_info.singer_name));

                    //s@n.v
                    //hi
               // }
//                else
//                {
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
//                    dialog.setTitle("회원 정보가 없습니다.");
//                    dialog.setMessage("회원 가입 하시겠습니까?");
//
//                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // YES 선택시 처리할 내용
//                            Log.v("로그인", "로그인");
//                            //  session.getAuthToken().token.toString();
//
//                            Bundle parameters = new Bundle();
//                            parameters.putString("fields", "id,name,email,gender,birthday");
//
//                            startActivity(new Intent(getApplicationContext(), SignActivity.class));
//                            finish();
//                        }
//                    });
//
//                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // NO 선택시 처리할 내용
//                            mfirebaseAuth.signOut();
//                            dialog.cancel();
//                        }
//                    });
//                    dialog.show();
//
//                }

            }
            @Override
            public void onFailure(Call<LoginResponseResult> call, Throwable t) {
                Log.v("들들들2", "들들들2");

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (loginType)
        {
            case 1://페북 로그인
                Log.v("들어옴", "페북");
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case 2://트위터 로그인
                Log.v("들어옴", "트위터");

                twitterAuthClient.onActivityResult(requestCode, resultCode, data);
                break;
            case 3://구글 로그인
                Log.v("구글", "들어옴");
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = result.getSignInAccount();
                        Log.v("계정", account.toString());
                        firebaseAuthWithGoogle(account);
                        Log.v("google", "success");
                        Toast.makeText(this, "성공", Toast.LENGTH_LONG);

                    } else {
                        // Google Sign In failed, update UI appropriately
                        // [START_EXCLUDE]
                        //updateUI(null);
                        // [END_EXCLUDE]
                        Toast.makeText(this, "실패", Toast.LENGTH_LONG);
                        Log.v("google", "false");

                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
       // Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mfirebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    public void onLoginButtonClick(View v){
        switch (v.getId()){
            case R.id.LoginSelect_facebook:
                loginType = 1;
                break;
            case R.id.LoginSelect_twitter:
                loginType = 2;
                break;
            case R.id.LoginSelect_google:
                loginType = 3;
                break;
        }
    }
}
