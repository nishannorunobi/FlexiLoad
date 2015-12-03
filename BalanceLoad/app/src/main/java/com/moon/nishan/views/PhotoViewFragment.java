package com.moon.nishan.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.moon.nishan.balanceload.R;
import com.moon.nishan.interfaces.EditViewChangeListener;
import com.moon.nishan.ocr.TessOcr;

import java.io.InputStream;

/**
 * Created by nishan on 11/17/15.
 */
public class PhotoViewFragment extends Fragment {
    public static final String TAKEN_PHOTO = "TAKEN_PHOTO";
    private View fragment;
    private ImageView ivCard;
    private EditViewChangeListener editViewChangeListener;
    public PhotoViewFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.photo_view_fragment, container, false);
        ivCard = (ImageView) fragment.findViewById(R.id.iv_card);
        Bundle bundle = this.getArguments();
        Bitmap bitmap = getImage(bundle);
        ivCard.setImageBitmap(bitmap);
        parseNumber(bitmap);
        return fragment;
    }

    private void parseNumber(Bitmap bitmap) {
        TessOcr tessOcr = new TessOcr(getActivity());
        String number = tessOcr.getOCRResult(bitmap);
        if (number != null){
            editViewChangeListener.setNumber(number);
        }
    }

/*    private Bitmap getImage(){
        Bitmap bitmap = null;
        try {
            InputStream stream = getActivity().openFileInput("picture.jpg");
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }*/

    private Bitmap getImage(Bundle bundle){
        byte[] data = data = bundle.getByteArray(TAKEN_PHOTO);
        Bitmap bitmap = null;
        if (data != null) {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            bitmap = (Bitmap) bundle.get("data");
        }
        return bitmap;
    }

    public void setEditViewChangeListener(EditViewChangeListener editViewChangeListener) {
        this.editViewChangeListener = editViewChangeListener;
    }
}
