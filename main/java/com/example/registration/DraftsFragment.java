package com.example.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DraftsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView emailList = (RecyclerView) inflater.inflate(R.layout.fragment_inbox, container, false);
        ClientDBHelper dbHelper = new ClientDBHelper(getActivity());
        List<EmailDetails> emails = dbHelper.getEmails("drafts");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        emailList.setLayoutManager(layoutManager);
        emailList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        RecyclerAdapter adapter = new RecyclerAdapter(emails, getActivity());
        emailList.setAdapter(adapter);
        return emailList;
        //return inflater.inflate(R.layout.fragment_inbox, container, false);
    }
}
