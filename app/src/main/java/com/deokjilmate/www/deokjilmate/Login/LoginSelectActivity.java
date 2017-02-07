package com.deokjilmate.www.deokjilmate.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//여기는 로그인 방법 결정하는 곳(구글, 페북, 트위터, 커스텀)
public class LoginSelectActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager callbackManager;
    TwitterAuthClient twitterAuthClient;

    @BindView(R.id.LoginSelect_topImage)
    ImageView toobarImage;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    //toolbar = (Toolbar)findViewById(R.id.)
        Glide.with(this).load(R.drawable.meta).into(toobarImage);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }

    @OnClick(R.id.LoginSelect_findPwd)
    public void FindPwdEvent()
    {
        startActivity(new Intent(getApplicationContext(), FindPwdActivity.class));
    }

    @OnClick(R.id.LoginSelect_facebook)
    public void LoginFacebook()//페이스북 로그인 버튼
    {
        LoginManager.getInstance().logInWithReadPermissions(LoginSelectActivity.this,
                Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

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
    @OnClick(R.id.LoginSelect_twitter)
    public void LoginTwitter()//트위터 로그인 버튼
    {
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(LoginSelectActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
    @OnClick(R.id.LoginSelect_google)
    public void LoginGoogle()//구글 로그인 버튼
    {
        signIn();
        Log.v("LoginSelect", "ClickedGoogle");
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.v("GoogleResult", "GoogleResult");
            //TODO : 여기서부터는 페이지 넘어가는거 구현

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
}
