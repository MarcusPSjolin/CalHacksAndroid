package com.marcussjolin.calhacks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDescriptionActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_UPDATE_ADDRESS = 0;

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
        } else if (requestCode == REQUEST_UPDATE_ADDRESS) {
            if (resultCode == RESULT_OK) {
                setPostForItem();
            } else {

            }
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
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
                    boolean isNewUser = sharedPrefs.getBoolean(MainActivity.NEW_USER, true);

                    Intent intent;
                    if (isNewUser) {
                        intent = new Intent(mActivity, SettingsActivity.class);
                        intent.putExtra(SettingsActivity.FIRST_USE, true);
                        startActivityForResult(intent, REQUEST_UPDATE_ADDRESS);
                    } else {
                        setPostForItem();
                        intent = new Intent(mActivity, ConfirmPostmatesActivity.class);
                        startActivity(intent);
                    }
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

    private void setPostForItem() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        StringBuilder builder = new StringBuilder();
        builder.append(CalHacksApplication.URL);
        builder.append("items/");

        JSONObject object = new JSONObject();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            EditText title = (EditText) findViewById(R.id.title);
            EditText desc = (EditText) findViewById(R.id.description);
            Bitmap image = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Bitmap[] images = new Bitmap[1];
            images[0] = image;

            object.put(Constants.Item.title, title.getText().toString());
            object.put(Constants.Item.description, desc.getText().toString());
            object.put(Constants.Item.state, 1);
            object.put(Constants.User.user_id, CalHacksApplication.USER_ID);
            object.put(Constants.Item.facility, CalHacksApplication.FACILITY);
            object.put(Constants.Item.images, images);
        } catch (JSONException e) {
            Log.e("TAG", "JSONException " + e);
        }

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
                try {
                    sharedPrefs.edit().putString(MainActivity.CURRENT_ITEM, response.getString(Constants.Response.id)).apply();
                    setPostOnResponse();
                } catch (Exception e) {
                    Log.e("TAG", "Exception in onResponse() = " + e);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse error = " + error);
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                builder.toString(), object, responseListener, errorListener);

        queue.add(jsonObjectRequest);
    }

    private void setPostOnResponse() {
        
    }

}
