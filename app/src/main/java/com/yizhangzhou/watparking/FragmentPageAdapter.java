package com.yizhangzhou.watparking;

/**
 * Created by Shiina on 2016/6/14.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class FragmentPageAdapter extends FragmentPagerAdapter {
    Fragment capacity = new CapacityFragment();
    Fragment mapView = new MapViewFragment();
    Fragment prediction = new PredictionFragment();
    Fragment findMyCar = new FindMyCarFragment();
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return new CapacityFragment();
               // return capacity;
            case 1:
               // return mapView;
                return new MapViewFragment();
            case 2:
               // return prediction;
                return new PredictionFragment();
            case 3:
               // return findMyCar;
                return new FindMyCarFragment();
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4;
    }

    public void switchContent(Fragment fragment) {

    }
}