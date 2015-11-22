package com.zia.nishan.flexyload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zia.nishan.controller.FlashController;
import com.zia.nishan.util.TessOcr;
import com.zia.nishan.views.ViewNavigator;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FlashController flashController;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private FloatingActionButton lightFab;
    private FloatingActionButton cameraFab;
    private FloatingActionButton actionFab;

    Fragment fragment;
    View userView;

    private ViewNavigator framentNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lightFab = (FloatingActionButton) findViewById(R.id.fab_start);
        lightFab.setOnClickListener(this);
        cameraFab = (FloatingActionButton) findViewById(R.id.fab_center);
        cameraFab.setOnClickListener(this);
        actionFab = (FloatingActionButton) findViewById(R.id.fab_end);
        actionFab.setOnClickListener(this);

        framentNavigator = new ViewNavigator(this);
        framentNavigator.replaceWith(1);
        flashController = new FlashController(this);

        //loadFragment();
    }

    private void loadFragment() {
        fragment = new HomeActivityFragment();
        FragmentManager fm = getSupportFragmentManager();
        if (fragment == null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, fragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_start:
                flashController.makeFlash();
                break;
            case R.id.fab_center:
                initUserInterface();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.fab_end:
                call();
                break;
        }
    }

    private ImageView cardIv;
    private EditText changeEt;
    private void initUserInterface() {
        userView = findViewById(R.id.content_fragment);
        cardIv = (ImageView) userView.findViewById(R.id.iv_card);
        changeEt = (EditText) userView.findViewById(R.id.et_number);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap = null;
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            cardIv.setImageBitmap(imageBitmap);
            changeEt.setText("123");
        }
    }
}
