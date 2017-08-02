package com.deokjilmate.www.deokjilmate.Login;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.network.NetworkService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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

    private String member_email;
    private String member_passwd;
    private String uid;
    private boolean notSns;
    private String member_name;
    private int type;

    private int checkReturn = -1;
    private RequestBody b_email;
    private RequestBody b_passwd;
    private RequestBody b_memberName;
    private MultipartBody.Part profile_img;
    private RequestManager requestManager;
    private final String LOG = "LOG::SetProfile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_profile);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.profile_default).into(profileImage);

        Glide.with(this).load(R.drawable.profilesetting_photo).into(profileSelect);

        Glide.with(this).load(R.drawable.topbar_back).into(backButton);

        requestManager = Glide.with(this);

        networkService = ApplicationController.getInstance().getNetworkService();
        //email = getIntent().getExtras().getString("email");
        //passwd = getIntent().getExtras().getString("passwd");

        type = getIntent().getExtras().getInt("type");
        switch (type){
            case 1:
                //notSns
                uid = getIntent().getExtras().getString("uid");
                member_email = getIntent().getExtras().getString("member_email");
                member_passwd = getIntent().getExtras().getString("member_passwd");
                notSns = getIntent().getExtras().getBoolean("notSns");
                //true
                break;
            case 2:
                //Sns
                uid = getIntent().getExtras().getString("uid");
                notSns = getIntent().getExtras().getBoolean("notSns");
                //false
                break;
            default:
                break;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.SetProfile_profileImage));

    }



    private void recycleView(View view) {

        if(view != null) {

            Drawable bg = view.getBackground();

            if(bg != null) {

                bg.setCallback(null);

                ((BitmapDrawable)bg).getBitmap().recycle();

                view.setBackgroundDrawable(null);

            }

        }

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


                    Glide.with(profileImage.getContext())
                            .load(data.getData())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(profileImage);



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
                Log.v(LOG, "들어는 옴");
                if (response.body().result) {
                    checkReturn = 0;
                    //이건 중복 되었다는 이야기
                    Toast.makeText(getApplicationContext(),"존재하는 닉네임입니다", Toast.LENGTH_LONG);
                    Log.v(LOG, "중복");

                }
                else{
                    checkReturn = 1;
                    //이건 중복 안 되었다는 이야기
                    Toast.makeText(getApplicationContext(),"사용 가능한 닉네임입니다", Toast.LENGTH_LONG);
                    //SetSinger로 넘어감
                    Intent intent = new Intent(getApplicationContext(), SetSingerActivity.class);

                    switch (type){
                        case 1:
                            intent.putExtra("uid", uid);
                            intent.putExtra("member_email", member_email);
                            intent.putExtra("notSns", true);
                            intent.putExtra("member_passwd", member_passwd);
                            intent.putExtra("member_name", member_name);
                            intent.putExtra("type", 1);
                            break;
                        case 2:
                            intent.putExtra("uid", uid);
                            intent.putExtra("notSns", false);
                            intent.putExtra("member_name", member_name);
                            intent.putExtra("type", 2);
                            break;
                        default:
                            break;
                    }


                    startActivity(intent);

                    Log.v(LOG, "중복아님");

                }

            }
            @Override
            public void onFailure(Call<SetProfileResult> call, Throwable t) {
                Log.v(LOG, "서버 꺼져 있음2");

            }
        });

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = 8;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}
