package com.zia.nishan.views;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.zia.nishan.controller.CameraController;
import com.zia.nishan.flexyload.R;
import com.zia.nishan.interfaces.FragmentNavigator;
import com.zia.nishan.interfaces.OnCameraButtonClickListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by nishan on 11/17/15.
 */
public class CameraFragment  extends Fragment implements View.OnClickListener {
    private final String tag = "CameraFragment";
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private CameraController cameraController;
    private FragmentNavigator fragmentNavigator = null;
/*    private Camera camera = null;
    private boolean inPreview = false;
    private boolean cameraConfigured = false*/;
    //private ImageView imageView;
    //private TextView image_test_tv;
    private OnCameraButtonClickListener onCameraButtonClickListener;

    private View fragment;

    public CameraFragment() {
    }

    public void setOnCameraButtonClickListener(OnCameraButtonClickListener onCameraButtonClickListener){
        this.onCameraButtonClickListener = onCameraButtonClickListener;
    }

    public void setFragmentNavigator(FragmentNavigator fragmentNavigator) {
        this.fragmentNavigator = fragmentNavigator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.camera_fragment, container, false);
        populateUI(fragment);
        return fragment;
    }

    private void populateUI(View fragment) {
        preview=(SurfaceView)fragment.findViewById(R.id.surfaceView_camera);
        previewHolder=preview.getHolder();
        cameraController = new CameraController();
        previewHolder.addCallback(cameraController.getSurfaceCallback());
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preview.setFocusable(true);
        preview.setFocusableInTouchMode(true);
        preview.setClickable(true);
        preview.setOnClickListener(this);
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (onCameraButtonClickListener != null) {
                onCameraButtonClickListener.turnOff();
            }

            Bundle bundle = new Bundle();
            bundle.putByteArray(PhotoViewFragment.TAKEN_PHOTO, data);
            if(fragmentNavigator != null) {
                fragmentNavigator.setFragment(R.layout.photo_view_fragment, bundle);
            }
        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.TITLE, "image");

            Uri uri = getActivity().getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    contentValues);

            Bitmap pictureTaken = BitmapFactory.decodeByteArray(data, 0, data.length);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            pictureTaken.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);

            // Log.e(TAG+
            // "onpictureTaken()","bitmap is: "+pictureTaken.toString());
            // ImageView imageView = new ImageView(getApplicationContext());
            // imageView.setImageBitmap(pictureTaken);
            // setContentView(imageView);

            /*Bundle bundle = new Bundle();
            //intent.putExtras(bundle);
            //bundle.putParcelable(PhotoViewFragment.TAKEN_PHOTO,uri);
            bundle.putByteArray(PhotoViewFragment.TAKEN_PHOTO, outputStream.toByteArray());
            if(fragmentNavigator != null) {
                fragmentNavigator.setFragment(R.layout.photo_view_fragment, bundle);
            }*/

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_on_btn:
                if (onCameraButtonClickListener != null) {
                    onCameraButtonClickListener.turnOn();
                }
                break;
            case R.id.camera_off_btn:
                if (onCameraButtonClickListener != null) {
                    onCameraButtonClickListener.turnOff();
                }
                break;
            case R.id.surfaceView_camera:
                System.out.println("Nishan");
                cameraController.getCamera().takePicture(null,null,null,jpegCallback);
                //camera.takePicture(null, null, null, pictureCallback);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        //cameraController.getSurfaceCallback().surfaceDestroyed(previewHolder);
    }
}
