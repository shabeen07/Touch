package com.devcods.touch.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devcods.touch.R;
import com.devcods.touch.adapter.ChatsListAdapter;
import com.devcods.touch.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate( inflater,container,false );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        ArrayList<String> nameList=new ArrayList<>(  );
        nameList.add( "Lion" );
        nameList.add( "Lion" );
        nameList.add( "King" );
        nameList.add( "Lion" );
        nameList.add( "Cat" );
        nameList.add( "Tiger" );
        nameList.add( "Lion" );
        nameList.add( "Monkey" );
        nameList.add( "Lion" );
        nameList.add( "Lion" );
        ChatsListAdapter chatsListAdapter=new ChatsListAdapter( nameList );
        binding.recyclerView.setLayoutManager( new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter( chatsListAdapter );
        chatsListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}