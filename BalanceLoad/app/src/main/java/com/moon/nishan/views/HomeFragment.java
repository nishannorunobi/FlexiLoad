package com.moon.nishan.views;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moon.nishan.balanceload.R;
import com.moon.nishan.ocr.TessOcr;
import com.moon.nishan.util.ApplicationUtil;

import java.io.InputStream;

/**
 * Created by nishan on 11/17/15.
 */
public class HomeFragment extends Fragment{

    private View fragment;
    private Activity context;
    private TextView numnberTv;
    private ImageView ivNumner;

    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.home_fragment, container, false);
        numnberTv = (TextView) fragment.findViewById(R.id.test_number_tv);
        ivNumner = (ImageView) fragment.findViewById(R.id.iv_mumber);
        try {
            InputStream inputStream = null;
            inputStream = getResources().openRawResource(R.raw.numher_image);
            ivNumner.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            numnberTv.setText(ApplicationUtil.getBasePackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

}
