package com.moon.nishan.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import com.moon.nishan.interfaces.OnFlashButtonClickListener;

/**
 * Created by nishan on 11/16/15.
 */
public class FlashController {
    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera camera;
    private Camera.Parameters params;
    private Activity context;
    private OnFlashButtonClickListener onFlashButtonClickListener;

    public FlashController(Activity context,OnFlashButtonClickListener onFlashButtonClickListener){
        this.context = context;
        this.onFlashButtonClickListener = onFlashButtonClickListener;
        hasFlash = context.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void makeFlash() {
        if (!hasFlash) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your mobile does not support flashlight")
                    .setTitle("Error");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCancelable(true);
            dialog.show();
            onFlashButtonClickListener.turnOff();
        } else {
            turnOnFlash();
        }
    }

    private void turnOnFlash() {
        if (camera == null) {
            camera = Camera.open();
        }

        if (camera == null){
            return;
        } else {
            params = camera.getParameters();
        }

        if (!isFlashOn) {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }else {
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }
}
