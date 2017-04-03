package com.deokjilmate.www.deokjilmate.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
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

//여기는 로그인 방법 결정하는 곳(구글, 페북, 트위터, 커스텀)
public class LoginSelectActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private int loginType = 0;

    private CallbackManager callbackManager;
    TwitterAuthClient twitterAuthClient;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @BindView(R.id.LoginSelect_topImage)
    ImageView toobarImage;

    @BindView(R.id.LoginSelect_backImage)
    ImageButton backButton;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login_select);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        mfirebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                  //  Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };

        //toolbar = (Toolbar)findViewById(R.id.)
        Glide.with(this).load(R.drawable.toolbar).into(toobarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
       // LoginManager.getInstance().logOut();
    }

    @OnClick(R.id.LoginSelect_findPwd)
    public void FindPwdEvent()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }

    @OnClick(R.id.LoginSelect_facebook)
    public void LoginFacebook()//페이스북 로그인 버튼
    {
        loginType = 1;


        LoginManager.getInstance().logInWithReadPermissions(LoginSelectActivity.this,
                Arrays.asList("public_profile", "email"));
//        LoginManager.getInstance().logInWithPublishPermissions(LoginSelectActivity.this,
//                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
//                final GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
//                {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.v("로그인", "로그인");
//                        //loginResult.getAccessToken();
//
//                    }
//                });

               //TODO : 여기서 가입 정보 없으면 회원가입 페이지로 넘어가게끔. 유도
                //TODO : if 정보 있음이면 다음 페이지


                //TODO : if 정보 없음이면 밑에 다이얼로그


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


//                graphRequest.setParameters(parameters);
//                graphRequest.executeAsync();
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
        twitterAuthClient.authorize(LoginSelectActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
//                TwitterSession session = result.data;
//                String twitter_token = session.getAuthToken().token;


                handleTwitterSession(result.data);

            }
            @Override
            public void failure(TwitterException exception) {
                //로긴 취소 했을 때도 들어옴.
            }
        });

    }
    @OnClick(R.id.LoginSelect_google)
    public void LoginGoogle()//구글 로그인 버튼
    {
        loginType = 3;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v("LoginSelect", "ClickedGoogle");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (loginType)
        {
            case 1://페북 로그인
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case 2://트위터 로그인
                twitterAuthClient.onActivityResult(requestCode, resultCode, data);
                break;
            case 3://구글 로그인
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                }
                break;
        }
    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        mfirebaseAuth.signOut();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]

                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginSelectActivity.this);
                        dialog.setTitle("회원 정보가 없습니다.");
                        dialog.setMessage("회원 가입 하시겠습니까?");

                        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // YES 선택시 처리할 내용
                                Log.v("로그인", "로그인");
                                acct.getIdToken().toString();

                                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
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
                        Log.v("회원 정보", acct.getIdToken().toString());
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





                        //TODO : if 정보 없음이면 밑에 다이얼로그


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

                                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
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
                        Log.v("회원 정보", session.getAuthToken().token.toString());
                    }
                });
    }
}
