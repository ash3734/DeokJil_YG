package com.deokjilmate.www.deokjilmate.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.MyPage.MyPageActivity;
import com.deokjilmate.www.deokjilmate.R;
import com.deokjilmate.www.deokjilmate.SharedPrefrernceController;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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
    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        ButterKnife.bind(this);

        init();

    }

    public void init(){
        Uri data;
        data = Uri.parse(SharedPrefrernceController.getUserImage(this));
        Glide.with(editP_profileImage.getContext())
                .load(data)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(editP_profileImage);

        String nickname;
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
        SharedPrefrernceController.setUserNickname(EditProfileActivity.this, editP_nickname.getText().toString());
    }

    @OnClick(R.id.EditProfile_backImage)
    public void editBack(){
        //TODO : 뒤로 가는 것.
        Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
