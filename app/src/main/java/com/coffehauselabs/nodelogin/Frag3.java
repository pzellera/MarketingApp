package com.coffehauselabs.nodelogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag3 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View thirdTab = inflater.inflate(R.layout.frag3, container, false);
        ((TextView)thirdTab.findViewById(R.id.fragTab)).setText("Third tab");
        return thirdTab;
    }
}