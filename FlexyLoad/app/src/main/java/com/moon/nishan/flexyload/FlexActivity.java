package com.zia.nishan.flexyload;

import android.content.Intent;
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
import android.widget.ImageView;

import com.zia.nishan.controller.FlashController;
import com.zia.nishan.interfaces.FragmentNavigator;
import com.zia.nishan.interfaces.OnCameraButtonClickListener;
import com.zia.nishan.interfaces.OnFlashButtonClickListener;
import com.zia.nishan.views.CameraFragment;
import com.zia.nishan.views.HomeFragment;
import com.zia.nishan.views.PhotoViewFragment;

/**
 * Created by nishan on 11/24/15.
 */
public class FlexActivity extends AppCompatActivity implements View.OnClickListener {
    private FlashController flashController;
    private Button callBtn;
    private ImageView flash_on_btn;
    private ImageView flash_off_btn;
    private ImageView camera_on_btn;
    private ImageView camera_off_btn;

    private HomeFragment homeFragment;
    private CameraFragment cameraFragment;
    private PhotoViewFragment photoViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexy_home);
        /*Initialize fragment*/
        homeFragment = new HomeFragment();
        cameraFragment = new CameraFragment();
        cameraFragment.setOnCameraButtonClickListener(onCameraButtonClickListener);
        cameraFragment.setFragmentNavigator(fragmentNavigator);
        photoViewFragment = new PhotoViewFragment();
        /*Init view*/
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
            switch (tag){
                case R.layout.photo_view_fragment:
                    photoViewFragment.setArguments(bundle);
                    loadFragment(photoViewFragment);
                break;
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
                    loadFragment(cameraFragment);
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
}
