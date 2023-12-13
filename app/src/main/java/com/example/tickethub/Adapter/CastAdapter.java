package com.example.tickethub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.Model.CastModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<CastModel> castModels=new ArrayList<>();
    private Context mContext;

    public CastAdapter(Context mContext, List<CastModel> castModels) {
        this.castModels = castModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cast,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText(castModels.get(i).getName());
        viewHolder.role.setText(castModels.get(i).getRole());


    }

    @Override
    public int getItemCount() {
        return castModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            role=itemView.findViewById(R.id.role);
        }
    }

}

