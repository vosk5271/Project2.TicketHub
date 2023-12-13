package com.example.tickethub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tickethub.MainActivity;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.R;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<BookingModel> bookingModels=new ArrayList<>();
    private Context mContext;

    public BookingAdapter(Context mContext, List<BookingModel> bookingModels) {
        this.bookingModels = bookingModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_booking,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {
        String seat="";
        viewHolder.name.setText(bookingModels.get(i).getName());
        viewHolder.movie.setText("Movie : "+bookingModels.get(i).getMovieName());
        viewHolder.time.setText("ShowTime : "+bookingModels.get(i).getShow());
        viewHolder.status.setText("Booking Status : "+bookingModels.get(i).getBookingStatus());
        List<String> Allseat=bookingModels.get(i).getSeatModels();
        for (int j=0; j<Allseat.size();j++){
            if(!seat.equals("")) {
                seat = seat + ", " + Allseat.get(j);
            }else{
                seat = seat + Allseat.get(j);
            }
        }
        viewHolder.seat.setText("Seat No. : "+seat);
        viewHolder.amount.setText("Billing Amount: $"+bookingModels.get(i).getAmount());
        Glide.with(mContext)
                .asBitmap()
                .load(bookingModels.get(i).getPoster())
                .into(viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,movie,time,status,seat,amount;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            img=itemView.findViewById(R.id.img);
            movie=itemView.findViewById(R.id.movie);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            seat=itemView.findViewById(R.id.seat);
            amount=itemView.findViewById(R.id.amount);
        }
    }

}

