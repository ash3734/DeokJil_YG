package com.deokjilmate.www.deokjilmate.Login;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deokjilmate.www.deokjilmate.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetProfileActivity extends AppCompatActivity {


    @BindView(R.id.SetProfile_topImage)
    ImageView toobarImage;

    @BindView(R.id.SetProfile_profileImage)
    ImageView profileImage;

    @BindView(R.id.SetProfile_select)
    ImageButton profileSelect;

    @BindView(R.id.SetProfile_backImage)
    ImageButton backButton;

    final int REQ_CODE_SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_profile);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.toolbar).into(toobarImage);

        Glide.with(this).load(R.drawable.profile_default).into(profileImage);

        Glide.with(this).load(R.drawable.profilesetting_photo).into(profileSelect);

        Glide.with(this).load(R.drawable.meta).into(backButton);

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
                    String name_Str = getImageNameToUri(data.getData());

                    // 서버에 보낼 imgUrl

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.SetProfile_profileImage);

//                    RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image_bitmap);
//                    bitmapDrawable.setCornerRadius(Math.max(image_bitmap.getWidth(), image_bitmap.getHeight()) / 2.0f);
//                    bitmapDrawable.setAntiAlias(true);
//
//                    // 배치해놓은 imageview에 동그랗게 set!!!
//                    image.setImageDrawable(bitmapDrawable);

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
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);


        return imgName;
    }

    @OnClick(R.id.SetProfile_backImage)
    public void ClickBack()
    {
        Intent intent = new Intent(getApplicationContext(), SignActivity.class);
        startActivity(intent);
    }

}
