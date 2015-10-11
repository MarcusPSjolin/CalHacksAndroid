package com.marcussjolin.calhacks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDescriptionActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;

    public static final String LOCKER_SIZE = "size";

    private String mCurrentPhotoPath;
    private boolean mPhotoTaken = false;

    private ItemDescriptionActivity mActivity;

    private StoreStuffActivity.LockerSize SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        setContentView(R.layout.activity_item_description);
        setTitle(getString(R.string.tell_us_about_item));

        SIZE = (StoreStuffActivity.LockerSize) getIntent().getExtras().get(LOCKER_SIZE);

        setCameraButton();
        setGoodToGoButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setCameraButton() {
        ImageButton camera = (ImageButton) findViewById(R.id.camera_button);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void setGoodToGoButton() {
        Button goodToGo = (Button) findViewById(R.id.good_to_go);
        goodToGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = isTitleValid() && mPhotoTaken;
                if (valid) {
                    Intent intent = new Intent(mActivity, ConfirmPostmatesActivity.class);
                    startActivity(intent);
                } else {
                    if (!isTitleValid()) {
                        Toast.makeText(mActivity, R.string.must_enter_valid_title, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mActivity, R.string.must_take_picture, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isTitleValid() {
        EditText title = (EditText) findViewById(R.id.title);
        return !title.getText().toString().equals("");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("TAG", "Exception = " + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        ImageButton cameraButton = (ImageButton) findViewById(R.id.camera_button);
        // Get the dimensions of the View
        int targetW = cameraButton.getWidth();
        int targetH = cameraButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        cameraButton.setImageBitmap(bitmap);
        mPhotoTaken = true;
    }

}
