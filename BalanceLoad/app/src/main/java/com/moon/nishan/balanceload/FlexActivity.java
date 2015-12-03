package com.moon.nishan.balanceload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moon.nishan.controller.FlashController;
import com.moon.nishan.interfaces.EditViewChangeListener;
import com.moon.nishan.interfaces.FragmentNavigator;
import com.moon.nishan.interfaces.OnCameraButtonClickListener;
import com.moon.nishan.interfaces.OnFlashButtonClickListener;
import com.moon.nishan.util.ApplicationUtil;
import com.moon.nishan.util.Constants;
import com.moon.nishan.views.CameraFragment;
import com.moon.nishan.views.HomeFragment;
import com.moon.nishan.views.PhotoViewFragment;

import java.nio.ByteBuffer;

/**
 * Created by nishan on 11/24/15.
 */
public class FlexActivity extends AppCompatActivity implements View.OnClickListener {
    private FlashController flashController;
    private EditText etNumber;
    private Button callBtn;
    private ImageView flash_on_btn;
    private ImageView flash_off_btn;
    private ImageView camera_on_btn;
    private ImageView camera_off_btn;

    private HomeFragment homeFragment;
    private CameraFragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexy_home);
        /*Initialize fragment*/
        homeFragment = new HomeFragment();
        cameraFragment = new CameraFragment();
        cameraFragment.setOnCameraButtonClickListener(onCameraButtonClickListener);
        cameraFragment.setFragmentNavigator(fragmentNavigator);
        /*Init view*/
        etNumber = (EditText) findViewById(R.id.et_number);
        callBtn = (Button) findViewById(R.id.btn_call);
        callBtn.setOnClickListener(this);
        flashController = new FlashController(this,onFlashButtonClickListener);
        flash_on_btn=(ImageView)findViewById(R.id.flash_on_btn);
        flash_on_btn.setOnClickListener(this);
        flash_off_btn=(ImageView)findViewById(R.id.flash_off_btn);
        flash_off_btn.setOnClickListener(this);

        camera_on_btn=(ImageView)findViewById(R.id.camera_on_btn);
        camera_on_btn.setOnClickListener(this);
        camera_off_btn=(ImageView)findViewById(R.id.camera_off_btn);
        camera_off_btn.setOnClickListener(this);

        loadFragment(homeFragment);
    }

    private FragmentNavigator fragmentNavigator = new FragmentNavigator() {
        @Override
        public void setFragment(int tag, Bundle bundle) {
            Fragment fragment = null;
            switch (tag){
                case R.layout.photo_view_fragment:
                    PhotoViewFragment fg = new PhotoViewFragment();
                    fg.setEditViewChangeListener(editViewChangeListener);
                    fg.setArguments(bundle);
                    fragment = fg;
                break;
            }
            if (fragment != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fl_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.flash_on_btn:
                if (onFlashButtonClickListener != null){
                    flashController.makeFlash();
                    onFlashButtonClickListener.turnOn();
                }
                break;
            case R.id.flash_off_btn:
                if (onFlashButtonClickListener != null){
                    flashController.makeFlash();
                    onFlashButtonClickListener.turnOff();
                }
                break;
            case R.id.btn_call:
                call();
                break;
            case R.id.camera_on_btn:
                if (onCameraButtonClickListener != null) {
                    openCamera();
                    onCameraButtonClickListener.turnOn();
                }
                break;
            case R.id.camera_off_btn:
                if (onCameraButtonClickListener != null) {
                    Camera camera = Camera.open();
                    camera.release();
                    onCameraButtonClickListener.turnOff();
                }
                break;
            default:
                loadFragment(new HomeFragment());
                break;
        }
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,Constants.REQUEST_CODE_FOR_EXTERNAL_CAMERA_INTENT);
    }

    private void call() {
        String ussdCode = "*" + "123" + Uri.encode("#");
        Uri uri = Uri.parse("tel:"+ussdCode);
        Intent in = new Intent(Intent.ACTION_CALL,uri);
        try{
            startActivity(in);
        }catch (android.content.ActivityNotFoundException ex){
            Log.e(ex.getMessage(), ex.toString());
        }
    }

    private OnCameraButtonClickListener onCameraButtonClickListener = new OnCameraButtonClickListener() {
        @Override
        public void turnOn() {
            camera_on_btn.setVisibility(View.GONE);
            camera_off_btn.setVisibility(View.VISIBLE);
        }

        @Override
        public void turnOff() {
            camera_off_btn.setVisibility(View.GONE);
            camera_on_btn.setVisibility(View.VISIBLE);
        }
    };

    private OnFlashButtonClickListener onFlashButtonClickListener = new OnFlashButtonClickListener() {
        @Override
        public void turnOn() {
            flash_on_btn.setVisibility(View.GONE);
            flash_off_btn.setVisibility(View.VISIBLE);
        }

        @Override
        public void turnOff() {
            flash_off_btn.setVisibility(View.GONE);
            flash_on_btn.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == Constants.REQUEST_CODE_FOR_EXTERNAL_CAMERA_INTENT && resultCode == RESULT_OK) {
            onCameraButtonClickListener.turnOff();
            Bundle bundle = data.getExtras();
            fragmentNavigator.setFragment(R.layout.photo_view_fragment, bundle);
        }
    }

    private EditViewChangeListener editViewChangeListener = new EditViewChangeListener() {
        @Override
        public void setNumber(String number) {
            if (etNumber != null){
                etNumber.setText(number);
            }
        }
    };

}
