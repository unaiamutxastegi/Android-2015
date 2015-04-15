package com.unaiamutxastegi.earthquakes.listerners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cursomovil on 13/04/15.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment fragment;
    private Activity activity;
    private Class<T> fragmentClass;
    private int fragmentContainer;

    public TabListener(Activity activity, int fragmentContainer, Class<T> fragmentClass) {
        this.activity = activity;
        this.fragmentContainer = fragmentContainer;
        this.fragmentClass = fragmentClass;
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment == null) {
            String fragmentName = fragmentClass.getName();
            fragment = Fragment.instantiate(activity, fragmentName);
            ft.replace(fragmentContainer, fragment, fragmentName);
        } else {
            ft.attach(fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.attach(fragment);
        }
    }
}
