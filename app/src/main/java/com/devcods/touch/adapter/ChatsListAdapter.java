package com.devcods.touch.adapter;// Created by devCods on 3/17/2021.

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devcods.touch.databinding.LayoutUserRowBinding;

import java.util.ArrayList;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.MyHolder> {

    ArrayList<String> nameList;

    public ChatsListAdapter(ArrayList<String> nameList) {
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from( parent.getContext() );
        LayoutUserRowBinding layoutUserRowBinding=LayoutUserRowBinding.inflate( layoutInflater,parent,false );
        return new MyHolder(layoutUserRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String name=nameList.get( position );
        holder.layoutUserRowBinding.userName.setText( name );

        if (position==nameList.size()-1){
            holder.layoutUserRowBinding.layoutLine.setVisibility( View.GONE );
        }else {
            holder.layoutUserRowBinding.layoutLine.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        LayoutUserRowBinding layoutUserRowBinding;
        public MyHolder(@NonNull LayoutUserRowBinding layoutUserRowBinding) {
            super( layoutUserRowBinding.getRoot());
            this.layoutUserRowBinding=layoutUserRowBinding;
        }
    }
}
