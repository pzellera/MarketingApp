package com.coffehauselabs.nodelogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag2 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View categories = inflater.inflate(R.layout.frag2, container, false);
        ((TextView)categories.findViewById(R.id.fragTab)).setText("Categories");
        return categories;
    }
}