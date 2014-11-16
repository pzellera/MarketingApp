package com.coffehauselabs.nodelogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag1 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View myLikes = inflater.inflate(R.layout.frag1, container, false);
        ((TextView)myLikes.findViewById(R.id.fragTab)).setText("My likes");
        return myLikes;
    }
}
