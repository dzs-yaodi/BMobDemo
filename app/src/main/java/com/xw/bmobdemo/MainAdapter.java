package com.xw.bmobdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private List<UserData> userData = new ArrayList<>();

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_main_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int i) {
            holder.userId.setText(userData.get(i).getObjectId());
            holder.username.setText(userData.get(i).getUserName());
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void addList(List<UserData> userDataList) {
        this.userData.clear();
        userData.addAll(userDataList);
        notifyDataSetChanged();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        TextView userId,username;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            username = itemView.findViewById(R.id.username);
        }
    }
}
