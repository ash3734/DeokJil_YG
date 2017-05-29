package com.deokjilmate.www.deokjilmate.Login;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetProfileActivity extends AppCompatActivity {

    @BindView(R.id.SetProfile_profileImage)
    ImageView profileImage;

    @BindView(R.id.SetProfile_select)
    ImageButton profileSelect;

    @BindView(R.id.SetProfile_backImage)
    ImageButton backButton;

    @BindView(R.id.nickname)
    EditText tvnickName;



    final int REQ_CODE_SELECT_IMAGE = 100;
    protected  int a = 0;
    private NetworkService networkService;
    private Uri data;
    private String email;
    private String passwd;
    private String member_name;
    private int checkReturn = -1;
    private RequestBody b_email;
    private RequestBody b_passwd;
    private RequestBody b_memberName;
    private MultipartBody.Part profile_img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_profile);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.profile_default).into(profileImage);

        Glide.with(this).load(R.drawable.profilesetting_photo).into(profileSelect);

        Glide.with(this).load(R.drawable.meta).into(backButton);
        networkService = ApplicationController.getInstance().getNetworkService();
        email = getIntent().getExtras().getString("email");
        passwd = getIntent().getExtras().getString("passwd");

    }
    @OnClick(R.id.SetProfile_select)
    public void ProfileSet()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //data = data2.getData();
        //data2 = data2.getData();
        //Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.v("result", "result_well");
                try {
                    //Uri에서 이미지 이름을 얻어온다.

                    // 서버에 보낼 imgUrl

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.SetProfile_profileImage);

                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image_bitmap);
                    bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight()) / 2.0f);
                    bitmapDrawable.setAntiAlias(true);


                    b_email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
                    b_passwd = RequestBody.create(MediaType.parse("multipart/form-data"), passwd);


//
//                    // 배치해놓은 imageview에 동그랗게 set!!!
                    image.setImageDrawable(bitmapDrawable);
                    this.data = data.getData();

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    InputStream in = null; // here, you need to get your context.
                    try {
                        in = getContentResolver().openInputStream(this.data);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),


                    File photo = new File(data.toString()); // 그저 블러온 파일의 이름을 알아내려고 사용.
                    RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                    // MultipartBody.Part is used to send also the actual file name
                    //이미지 이름을 서버로 보낼 때에에는 아무렇게나 보내줘도된다! 서버에서 자동변환된다 (보안의문제)
                    profile_img = MultipartBody.Part.createFormData("profile_img", "jpg", photoBody);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 선택된 이미지 파일명 가져오기
     */


    @OnClick(R.id.SetProfile_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), SignActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.SetProfile_next)
    public void ClickNext()
    {
        member_name = tvnickName.getText().toString();
        if(member_name == null)
            Toast.makeText(this, "닉네임을 입력하세요", Toast.LENGTH_SHORT);
        else {
            checkNickname(member_name);
        }
    }

    public void checkNickname(final String nickname){
        //중복 안 되면 거짓 되면 참을 리턴
        Call<SetProfileResult> setProfileResult = networkService.setProfileResult(nickname);
        setProfileResult.enqueue(new Callback<SetProfileResult>() {
            @Override
            public void onResponse(Call<SetProfileResult> call, Response<SetProfileResult> response) {
                Log.v("들어는 옴", "들어는 옴");
                if (response.body().result) {
                    checkReturn = 0;
                    //이건 중복 되었다는 이야기
                    Log.v("통신", "성공");
                    b_memberName = RequestBody.create(MediaType.parse("multipart/form-data"), nickname);

                }
                else{
                    checkReturn = 1;
                    //이건 중복 안 되었다는 이야기
                    Log.v("통신", "살패");

                    b_memberName = RequestBody.create(MediaType.parse("multipart/form-data"), nickname);

                    Call<RegisterResult> registerResult = networkService.registerResult(new TempBody(email, passwd, nickname));
                    //Log.v("SingPsswd", passwd);
                    registerResult.enqueue(new Callback<RegisterResult>() {
                        @Override
                        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                            if (response.body().result) {
                                Intent intent = new Intent(getApplicationContext(), SetSingerActivity.class);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<RegisterResult> call, Throwable t) {
                            Log.v("통신", "서버 꺼져 있음");
                        }
                    });

                }

            }
            @Override
            public void onFailure(Call<SetProfileResult> call, Throwable t) {
                Log.v("통신", "서버 꺼져 있음2");

            }
        });

    }
}
