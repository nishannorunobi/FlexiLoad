package com.zia.nishan.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zia.nishan.flexyload.R;

/**
 * Created by nishan on 11/17/15.
 */
public class PhotoViewFragment extends Fragment {
    public static final String TAKEN_PHOTO = "TAKEN_PHOTO";
    private View fragment;
    private ImageView ivCard;

    public PhotoViewFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.photo_view_fragment, container, false);
        ivCard = (ImageView) fragment.findViewById(R.id.iv_card);

        Bundle bundle = this.getArguments();
        //byte[] data = bundle.getByteArray(TAKEN_PHOTO);
        Uri uri = bundle.getParcelable(TAKEN_PHOTO);
        //Bitmap image = BitmapFactory.decodeByteArray(data,0,data.length);
        ivCard.setImageURI(uri);
        return fragment;
    }

}
