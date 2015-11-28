package com.zia.nishan.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zia.nishan.controller.CameraController;
import com.zia.nishan.flexyload.R;
import com.zia.nishan.interfaces.OnCameraButtonClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by nishan on 11/17/15.
 */
public class CameraFragment  extends Fragment implements View.OnClickListener {
    private final String tag = "CameraFragment";
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private CameraController cameraController;
/*    private Camera camera = null;
    private boolean inPreview = false;
    private boolean cameraConfigured = false*/;

    private OnCameraButtonClickListener onCameraButtonClickListener;

    private View fragment;

    public CameraFragment() {
    }

    public void setOnCameraButtonClickListener(OnCameraButtonClickListener onCameraButtonClickListener){
        this.onCameraButtonClickListener = onCameraButtonClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.camera_fragment, container, false);
        populateUI(fragment);
        return fragment;
    }

    private void populateUI(View fragment) {
        //getWindow().setFormat(PixelFormat.UNKNOWN);


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

 /*   private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                }
                else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private void initPreview(int width, int height) {
        if (camera!=null && previewHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size=getBestPreviewSize(width, height,
                        parameters);

                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera!=null) {
            camera.startPreview();
            inPreview=true;
        }
    }
    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            camera=Camera.open();
            startPreview();
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            if (inPreview) {
                camera.stopPreview();
            }

            camera.release();
            camera=null;
            inPreview=false;
        }
    };*/
 /*public Bitmap getImage(byte[] data)
 {

     Bitmap b = null;
     try {
         b = GeneralUtils.decodeFile(data, this);
     } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     }
     ByteArrayOutputStream bos = new ByteArrayOutputStream();
     b.compress(Bitmap.CompressFormat.JPEG, 80, bos);
     //b = null;
     Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, bos.size());
     Matrix m = new Matrix();
     if (b.getWidth() > b.getHeight())
     {
         m.postRotate(90);
         bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

     }
    return b;
 }
*/
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

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
                cameraController.getCamera().takePicture(null,null,null,pictureCallback);
                //camera.takePicture(null, null, null, pictureCallback);
                break;
            default:
                break;
        }
    }
}
