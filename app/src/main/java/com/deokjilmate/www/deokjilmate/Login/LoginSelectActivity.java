package com.deokjilmate.www.deokjilmate.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.CustomDialog;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.UserAllSingerData;
import com.deokjilmate.www.deokjilmate.UserAllSingerResponse;
import com.deokjilmate.www.deokjilmate.UserDataSumm;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.home.MainResult;
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
import com.tsengvn.typekit.TypekitContextWrapper;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.ArrayList;
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
    private final String LOG = "LOG::Login";
    private CustomDialog customDialog;
    private Context context;

    private ArrayList<UserAllSingerData> userAllSingerDatas;
    private ArrayList<UserDataSumm> userDataSumms;
    private int most;


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
    private ProgressDialog progressDialog;


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
        setContentView(R.layout.login_login_select);
        ButterKnife.bind(this);
        networkService = ApplicationController.getInstance().getNetworkService();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //TODO : sns로 로그인 할 때는 sns register를 따르면 됨.

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
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Log.v("구글", mGoogleApiClient.toString());

        mfirebaseAuth = FirebaseAuth.getInstance();
        context = this;
        progressDialog = new ProgressDialog(this);


    }

    @OnClick(R.id.LoginSelect_toFindPwd)
    public void toFindPwd()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }

    @OnClick(R.id.LoginSelect_toSign)
    public void toSign()
    {
        startActivity(new Intent(getApplicationContext(), SignActivity.class));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        makeDialog("잠시만 기다려주세요");
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mfirebaseAuth.getCurrentUser();
                            Toast.makeText(LoginSelectActivity.this, "Authentication suceess.",
                                    Toast.LENGTH_SHORT).show();

                            Log.v(LOG, user.getUid());

                            Call<LoginUidCheck> loginUidCheck = networkService.loginResult(user.getUid());
                            loginUidCheck.enqueue(new Callback<LoginUidCheck>() {
                                @Override
                                public void onResponse(Call<LoginUidCheck> call, Response<LoginUidCheck> response) {
                                    if(response.body().result){
                                        successGetToken(response.body().data);
                                    }else{
                                        failGetToken();
                                    }
                                }
                                @Override
                                public void onFailure(Call<LoginUidCheck> call, Throwable t) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
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


                handleFacebookAccessToken(loginResult.getAccessToken());


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

        makeDialog("잠시만 기다려주세요");

        Log.v(LOG, acct.getIdToken().toString());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginSelectActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        FirebaseUser user = mfirebaseAuth.getCurrentUser();

                        Log.v(LOG, user.getUid());

                        Call<LoginUidCheck> loginUidCheck = networkService.loginResult(user.getUid());
                        loginUidCheck.enqueue(new Callback<LoginUidCheck>() {
                            @Override
                            public void onResponse(Call<LoginUidCheck> call, Response<LoginUidCheck> response) {
                                if(response.body().result){
                                    successGetToken(response.body().data);
                                }else{
                                    failGetToken();
                                }
                            }
                            @Override
                            public void onFailure(Call<LoginUidCheck> call, Throwable t) {

                            }
                        });


                    }
                });
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





    @OnClick(R.id.LoginSelect_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
        startActivity(intent);
    }


    private void handleTwitterSession(final TwitterSession session) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        makeDialog("잠시만 기다려주세요");

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

                        //TODO : 가입의 경우 굳이 다이얼로그가 있을 필요는 없음
                        FirebaseUser user = mfirebaseAuth.getCurrentUser();

                        Log.v(LOG, user.getUid());

                        Call<LoginUidCheck> loginUidCheck = networkService.loginResult(user.getUid());
                        loginUidCheck.enqueue(new Callback<LoginUidCheck>() {
                            @Override
                            public void onResponse(Call<LoginUidCheck> call, Response<LoginUidCheck> response) {
                                if(response.body().result){
                                    successGetToken(response.body().data);
                                }else{
                                    failGetToken();
                                }
                            }
                            @Override
                            public void onFailure(Call<LoginUidCheck> call, Throwable t) {

                            }
                        });


                        //이걸 보내면 됨.
                        //credential.getProvider()
                    }
                });
    }

    @OnClick(R.id.LoginSelect_customLogin)
    public void clickCustom(){
        makeDialog("잠시만 기다려주세요");

        Call<LoginResponseResult> setSingerRankingCall = networkService.login(new LoginPost(email.getText().toString(), pwd.getText().toString()));
        setSingerRankingCall.enqueue(new Callback<LoginResponseResult>() {
            @Override
            public void onResponse(Call<LoginResponseResult> call, Response<LoginResponseResult> response) {
                //if(response.body().) {
                if(response.body().result){
                    successGetToken(response.body().data.firebaseToken);
                } else{
                    failGetToken();
                }
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


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public void setHomeData(String firebaseToken, int singer_id){
        Call<MainResult> requestMainResult = networkService.requestMain(firebaseToken,singer_id);

        requestMainResult.enqueue(new Callback<MainResult>() {
            @Override
            public void onResponse(Call<MainResult> call, Response<MainResult> response) {
                if(response.body().result){
                    ApplicationController.getInstance().setMainResult(response.body());
                    SharedPrefrernceController.setUserNickname(LoginSelectActivity.this, response.body().nevi_data.member_name);
                    SharedPrefrernceController.setLoginType(LoginSelectActivity.this, "l");
                    ApplicationController.getInstance().setLoginState("l");

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MainResult> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "네트워크 상태를 확인하세요", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public void successGetToken(final String firebaseToken){
        userAllSingerDatas = new ArrayList<UserAllSingerData>();
        userDataSumms = new ArrayList<UserDataSumm>();

        final Call<UserAllSingerResponse> userAllSingerResponse = networkService.userAllSinger(firebaseToken);
        userAllSingerResponse.enqueue(new Callback<UserAllSingerResponse>() {
            @Override
            public void onResponse(Call<UserAllSingerResponse> call, Response<UserAllSingerResponse> response) {
                if(response.body().result){
                    userAllSingerDatas = response.body().data;
                    int count = userAllSingerDatas.size();
                    ApplicationController.getInstance().setTotalSingerCount(count);
                    ApplicationController.getInstance().setUserAllSingerDatas(userAllSingerDatas);

                    for(int i = 0; i<userAllSingerDatas.size(); i++)
                    {
                        if(i==0 && userAllSingerDatas.get(0)!=null)
                        {

                            ApplicationController.getInstance().setMost(userAllSingerDatas.get(i).getSinger_id());
                            most = userAllSingerDatas.get(i).getSinger_id();
                            SharedPrefrernceController.setMost(LoginSelectActivity.this, most);
                            SharedPrefrernceController.setSelected(LoginSelectActivity.this, most);

                        }
                        userDataSumms.add(new UserDataSumm(userAllSingerDatas.get(i).getSinger_id(), userAllSingerDatas.get(i).getSinger_name(),
                                userAllSingerDatas.get(i).getSinger_img()));
                    }

                    if(userDataSumms.size() == userAllSingerDatas.size()){
                        SharedPrefrernceController.setFirebaseToken(LoginSelectActivity.this, firebaseToken);
                        ApplicationController.getInstance().setUserDataSumms(userDataSumms);
                        setHomeData(firebaseToken, most);
                    }
                } else{
                    Toast.makeText(LoginSelectActivity.this, "정보 불러오는 데에 실패하였습니다", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserAllSingerResponse> call, Throwable t) {

            }
        });


    }

    public void failGetToken(){
        progressDialog.dismiss();
        String content = "존재하지 않은 계정입니다. \n가입부터 하시겠습니까?";
        customDialog = new CustomDialog(context, content, leftListener, rightListener,"확인", "취소");
        customDialog.show();
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            Intent intent = new Intent(getApplicationContext(), SignActivity.class);
            startActivity(intent);
            finish();
            customDialog.dismiss();
            //setSingerActivity.SetComplete(ApplicationController.getInstance().getNumberSingerSet().get(temp_name));

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            customDialog.dismiss();
        }
    };

    public void makeDialog(String message) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }



}
