package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.Sign_topImage)
    ImageView toolbarImage;

    @BindView(R.id.Sign_backImage)
    ImageButton backButton;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    private int signType = 0;

    private CallbackManager callbackManager;
    TwitterAuthClient twitterAuthClient;

    @BindView(R.id.Sign_email)
    EditText email;

    @BindView(R.id.Sign_pwd)
    EditText pwd;

    @BindView(R.id.Sign_checkPwd)
    EditText checkPwd;

    @BindView(R.id.Sign_next)
    Button next;


    private String t_email;
    private String t_pwd;
    private String t_pwdCheck;

    private boolean b_email = false;
    private boolean b_pwd = false;
    private  boolean b_pwdCheck = false;

    private String r_email =  "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    private String r_pwd = "^[a-z0-9_-]{6,}$";   ///6자 이상

    private Pattern email_Pattern;
    private Pattern pwd_Pattern;

    private Matcher email_Match;
    private Matcher pwd_Match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        Glide.with(this).load(R.drawable.toolbar).into(toolbarImage);
        Glide.with(this).load(R.drawable.meta).into(backButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        email_Pattern = Pattern.compile(r_email);
        pwd_Pattern = Pattern.compile(r_pwd);


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
                final GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

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
                TwitterSession session = result.data;
                //result.

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
        Log.v("LoginSelect", "ClickedGoogle");
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
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                    Log.v("GoogleResult", "GoogleResult");
                }
                break;
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
        } else {
            // Signed out, show unauthenticated UI.
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


    @OnClick(R.id.Sign_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.Sign_email)
    public void setEmail()
    {
        email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("이메일", "email");
                t_email = email.getText().toString().toLowerCase(Locale.getDefault());
                if(t_email.length() == 0)
                    b_email = false;
                else {
                    b_email = true;
                }
                email_Match = email_Pattern.matcher(t_email);

                if (b_email && b_pwd && b_pwdCheck)
                    next.setEnabled(true);
                Log.v("email", String.valueOf(b_email));
                Log.v("pwd", String.valueOf(b_pwd));
                Log.v("pwdCheck", String.valueOf(b_pwdCheck));

                if(!email_Match.find())
                    email.setBackgroundColor(Color.RED);//나중에 DRawble로 지정.
                else
                    email.setBackgroundColor(Color.TRANSPARENT);
                //email.set

            }
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.Sign_pwd)
    public void setPwd()
    {
        Log.v("비번", "pwd");
        pwd.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("비번", "email");

                t_pwd = pwd.getText().toString().toLowerCase(Locale.getDefault());
                if(t_pwd.length() == 0)
                    b_pwd = false;
                else {
                    b_pwd = true;
                }
                if (b_email && b_pwd && b_pwdCheck)
                    next.setEnabled(true);
                Log.v("email", String.valueOf(b_email));
                Log.v("pwd", String.valueOf(b_pwd));
                Log.v("pwdCheck", String.valueOf(b_pwdCheck));

                pwd_Match = pwd_Pattern.matcher(t_pwd);

                if(!pwd_Match.find())
                    pwd.setBackgroundColor(Color.RED);//나중에 DRawble로 지정.
                else
                    pwd.setBackgroundColor(Color.TRANSPARENT);
            }
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.Sign_checkPwd)
    public void setCheckPwd()
    {
        Log.v("비번체크", "pwd");

        checkPwd.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("비번체크", "email");

                t_pwdCheck = checkPwd.getText().toString().toLowerCase(Locale.getDefault());
                if(t_pwdCheck.length() == 0)
                    b_pwdCheck = false;
                else {
                    b_pwdCheck = true;
                }
                if (b_email && b_pwd && b_pwdCheck)
                    next.setEnabled(true);
                Log.v("email", String.valueOf(b_email));
                Log.v("pwd", String.valueOf(b_pwd));
                Log.v("pwdCheck", String.valueOf(b_pwdCheck));
            }
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.Sign_next)
    public void ClickNext()
    {
        //1. pwd와 t_pwdCheck가 같은지 확인.
        Log.v("Sign", t_pwd + " " +t_pwdCheck);
        if(t_pwd.equals(t_pwdCheck))
        {
            Intent intent = new Intent(getApplicationContext(), SetProfileActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(SignActivity.this, "비밀번호와 확인이 일치한지 확인해주세요", Toast.LENGTH_SHORT).show();
        }

    }



    public boolean checkFill()
    {
        if(t_email != "" && t_pwd != "" && t_pwdCheck != "")
        {//셋 다 공백이 아닐 때.
            next.setEnabled(true);
            return true;
        }
        else
        {//하나라도 공백 있음.
            return false;
        }
    }
}
