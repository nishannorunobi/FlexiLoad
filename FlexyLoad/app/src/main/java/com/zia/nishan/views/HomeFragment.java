package com.zia.nishan.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zia.nishan.flexyload.R;

/**
 * Created by nishan on 11/17/15.
 */
public class HomeFragment extends Fragment{

    private View fragment;
    private Activity context;

    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.home_fragment, container, false);
        context = getActivity();
        return fragment;
    }

}
