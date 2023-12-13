package com.example.tickethub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.BookTicket;
import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<ShowModel> showModels=new ArrayList<>();
    private Context mContext;

    public ShowAdapter(Context mContext, List<ShowModel> showModels) {
        this.showModels = showModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_show,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText(showModels.get(i).getShowTime());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof BookTicket) {
                    viewHolder.name.setBackground(ContextCompat.getDrawable(mContext,R.drawable.box5));
                    ((BookTicket)mContext).fetchBookedSeats(showModels.get(i).getId(),showModels.get(i).getShowTime());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return showModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.show);

        }
    }

}

