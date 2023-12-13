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
import com.example.tickethub.Model.SeatModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<SeatModel> seatModels=new ArrayList<>();
    private Context mContext;
    int amount=0;
    public SeatAdapter(Context mContext, List<SeatModel> seatModels) {
        this.seatModels = seatModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SeatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_seat,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText(""+seatModels.get(i).getId());

        if (seatModels.get(i).isBooked()){
            viewHolder.name.setBackground(ContextCompat.getDrawable(mContext, R.drawable.box4));
            viewHolder.name.setEnabled(false);
        }

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!seatModels.get(i).isBooked()) {
                    viewHolder.name.setBackground(ContextCompat.getDrawable(mContext, R.drawable.box4));
                    ((BookTicket)mContext).ticketBookModels.add(String.valueOf(seatModels.get(i).getId()));
                    amount=amount+100;
                    ((BookTicket)mContext).binding.totalAmount.setText("$"+amount);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return seatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.number);

        }
    }

}

