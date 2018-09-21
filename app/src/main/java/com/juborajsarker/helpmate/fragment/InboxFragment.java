package com.juborajsarker.helpmate.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juborajsarker.helpmate.R;


public class InboxFragment extends Fragment {

    View view;
    RecyclerView chatRV;


    public InboxFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inbox, container, false);

        init();



        return view;
    }

    private void init() {

        chatRV = (RecyclerView) view.findViewById(R.id.chat_RV);
    }

}
