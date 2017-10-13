package com.deokjilmate.www.deokjilmate.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.Login.SetProfileResult;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.deokjilmate.www.deokjilmate.application.ApplicationController;
import com.deokjilmate.www.deokjilmate.home.HomeActivity;
import com.deokjilmate.www.deokjilmate.network.NetworkService;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.EditProfile_save)
    ImageView editP_save;

    @BindView(R.id.EditProfile_backImage)
    ImageView editP_back;

    @BindView(R.id.editPorfile_nickname)
    EditText editP_nickname;

    @BindView(R.id.editProfile_profileImage)
    ImageView editP_profileImage;

    @BindView(R.id.editProfile_select)
    ImageView editP_select;

    final int REQ_CODE_SELECT_IMAGE = 100;
    private NetworkService networkService;
    private Uri data;
    private String nickname;

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
        setContentView(R.layout.edit_profile);
        ButterKnife.bind(this);
        networkService = ApplicationController.getInstance().getNetworkService();
        Glide.with(this).load(R.drawable.profile_default).into(editP_profileImage);

        init();

    }

    public void init(){
        Uri data;
        data = Uri.parse(SharedPrefrernceController.getUserImage(this));
        if(!data.toString().equals("")) {
            Glide.with(editP_profileImage.getContext())
                    .load(data)
                    .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                    .into(editP_profileImage);
        }

        //String nickname;
        nickname = SharedPrefrernceController.getUserNickname(this);
        editP_nickname.setText(nickname);
    }


    @OnClick(R.id.editProfile_select)
    public void ProfileSet()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
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


                    Glide.with(editP_profileImage.getContext())
                            .load(data.getData())
                            .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(editP_profileImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.EditProfile_save)
    public void editSave(){
        SharedPrefrernceController.setUserImage(EditProfileActivity.this, data.toString());
        if (!editP_nickname.getText().toString().equals(nickname)) {
            Call<SetProfileResult> setProfileResult = networkService.setProfileResult(editP_nickname.getText().toString());
            setProfileResult.enqueue(new Callback<SetProfileResult>() {
                @Override
                public void onResponse(Call<SetProfileResult> call, Response<SetProfileResult> response) {
                    if (!response.body().result) {
                        //이건 중복 되었다는 이야기
                        Toast.makeText(EditProfileActivity.this, "존재하는 닉네임입니다.", Toast.LENGTH_LONG).show();

                    } else {
                        SharedPrefrernceController.setUserNickname(EditProfileActivity.this, editP_nickname.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<SetProfileResult> call, Throwable t) {

                }
            });
        }

        Toast.makeText(EditProfileActivity.this, "변경 되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.EditProfile_backImage)
    public void editBack(){
        //TODO : 뒤로 가는 것.
//        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
