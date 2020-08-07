package com.example.gotothefestival;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.gotothefestival.Model.ThemeData;
import com.example.gotothefestival.Model.User;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgPhoto, btnCapture;
    private Button btnSave, btnExit;
    private EditText edtPola, edtTitle, edtContents;

    private String imageFilepath;
    private Uri phorturi;
    static final int RE = 672;
    public static Bitmap imagesave = null;
    private ArrayList<CameraData> cameraList = new ArrayList<>();
    private String imageFile;
    private ThemeData themeData;

    private SQLiteDatabase sqlDG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        findViewByIdFunc();

        themeData = getIntent().getParcelableExtra("themadata");

        TedPermission.with(getApplicationContext()).setPermissionListener(new PermissionListener() {

            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }

        }).setRationaleMessage("카메라권한 필요합니다.")
                .setDeniedMessage("거부")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


        btnCapture.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    public void toastDispaly(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void findViewByIdFunc() {
        imgPhoto = findViewById(R.id.imgPhoto);
        btnCapture = findViewById(R.id.btnCapture);

        btnSave = findViewById(R.id.btnSave);
        btnExit = findViewById(R.id.btnExit);

        edtPola = findViewById(R.id.edtPola);
        edtTitle = findViewById(R.id.edtTitle);
        edtContents = findViewById(R.id.edtContents);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnCapture) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {

                File file = null;

                try {

                    file = imageFileSave();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    phorturi = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), file);
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, phorturi);
                startActivityForResult(intent, RE);

            } else if (view.getId() == R.id.btnSave) {

                UserDBHelper userDBHelper = UserDBHelper.getInstance(getApplicationContext());


                themeData.setContents(edtContents.getText().toString());
                themeData.setTitle(edtTitle.getText().toString());
                themeData.setContent_pola(edtPola.getText().toString());
                themeData.setPicture(imageFilepath);

                userDBHelper.oninsertCameraTBL(LoginActivity.userData, themeData);

                finish();

            } else if (view.getId() == R.id.btnExit) {

                finish();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RE && resultCode == RESULT_OK) {

            Bitmap bitmap = BitmapFactory.decodeFile(imageFilepath);
            ExifInterface exifInterface = null;

            try {
                exifInterface = new ExifInterface(imageFilepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int exifOrientation;//방향
            int exifDegres; //각도

            if (exifInterface != null) {
                exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegres = exiforToDe(exifOrientation);
            } else {
                exifDegres = 0;
            }
            Bitmap bitmapTeep = rotate(bitmap, exifDegres);

            imgPhoto.setImageBitmap(bitmapTeep);
        }
    }

    private Bitmap rotate(Bitmap bitmap, int exifDegres) {
        Matrix matrix = new Matrix();

        matrix.postRotate(exifDegres);

        Bitmap teepre = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return teepre;
    }

    private int exiforToDe(int exifOrientation) {

        switch (exifOrientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:

                return 90;

            case ExifInterface.ORIENTATION_ROTATE_180:

                return 180;

            case ExifInterface.ORIENTATION_ROTATE_270:

                return 270;

        }

        return 0;

    }

    private File imageFileSave() throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyymmdd_HHmmss");

        String string = date.format(new Date());
        imageFile = "test_" + string + "_";

        // 외부장치의 디렉토리
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFile, ".jpg", directory);
        imageFilepath = image.getAbsolutePath();
        Log.d("path", imageFilepath);
        return image;
    }
}
