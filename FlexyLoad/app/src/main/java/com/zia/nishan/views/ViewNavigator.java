package com.zia.nishan.views;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zia.nishan.flexyload.HomeActivity;
import com.zia.nishan.flexyload.R;

/**
 * Created by nishan on 11/17/15.
 */
public class ViewNavigator {
    private HomeActivity context = null;
    private Fragment fragment = null;
    private FragmentTransaction transaction = null;
    public ViewNavigator(HomeActivity context){
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            transaction = context.getSupportFragmentManager().beginTransaction();
        } else {

        }
    }

    public void replaceWith(int id){

        switch (id){
            case R.id.fab_center:

                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        if(transaction == null){
            return;
        } else {
            transaction.replace(R.id.fragment_container,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
