package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.tickethub.Adapter.SeatAdapter;
import com.example.tickethub.Adapter.ShowAdapter;

import com.example.tickethub.Database.BookingDatabase;
import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.ShowDatabase;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.SeatModel;
import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.databinding.ActivityBookTicketBinding;

import java.util.ArrayList;
import java.util.List;

public class BookTicket extends AppCompatActivity {

    public ActivityBookTicketBinding binding;
    List<ShowModel> showModels= new ArrayList<>();
    List<SeatModel> seatModels= new ArrayList<>();
    List<SeatModel> bookedSeatModels= new ArrayList<SeatModel>();
    public List<String> ticketBookModels= new ArrayList<String>();
    public List<String> seatNumbers= new ArrayList<String>();

    ShowAdapter showAdapter;
    SeatAdapter seatAdapter;

    String movieId="";
    String theatreId="";
    String showId="";
    String url="";
    String movieName="";
    String show="";
Constant constant;
int total=0;
    int available_seats=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        constant=new Constant(this);
        movieId= getIntent().getStringExtra("movieId");
        theatreId= getIntent().getStringExtra("theatreId");
        movieName= getIntent().getStringExtra("movieName");
        url= getIntent().getStringExtra("url");
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "User");
                startActivity(new Intent(BookTicket.this, MainActivity.class).putExtras(bundle));
                finish();
            }
        });
        binding.rvSeat1.setHasFixedSize(true);
        binding.rvShow.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(BookTicket.this,LinearLayoutManager.HORIZONTAL,false);

        binding.rvShow.setLayoutManager(layoutManager);





        GridLayoutManager gridLayoutManager=new GridLayoutManager(BookTicket.this,5);



        binding.rvSeat1.setLayoutManager(gridLayoutManager);


        binding.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ticketBookModels.size()==0){
                    Toast.makeText(BookTicket.this, "Select Seat", Toast.LENGTH_SHORT).show();

                }else {
                    total=(100*ticketBookModels.size());
                    bookTicket();

                }
            }
        });

        fetchShows();

    }

    private void bookTicket() {
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                BookingModel model= new BookingModel(constant.getName(),constant.getUser_id(),show,showId,movieId,movieName,theatreId,
                        "Confirmed",String.valueOf(total),url,ticketBookModels);
                BookingDatabase bookingDatabase= BookingDatabase.getInstance(BookTicket.this);
                long val = bookingDatabase.bookingDao().insertBooking(model);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){

                    Toast.makeText(BookTicket.this, "Error!!", Toast.LENGTH_SHORT).show();

                }else {
                    available_seats=available_seats-ticketBookModels.size();
                    updateSeat();

                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();
    }

    private void updateSeat() {
        class saveTaskInBackend extends AsyncTask<Void, Void, Long> {
            @SuppressLint("WrongThread")
            @Override
            protected Long doInBackground(Void... voids) {
                ShowDatabase showDatabase= ShowDatabase.getInstance(BookTicket.this);
                long val = showDatabase.showDao().updateSeat(showId,movieId,theatreId,available_seats);

                return val;
            }

            @Override
            protected void onPostExecute(Long val) {
                super.onPostExecute(val);
                if (val==0){
                    Toast.makeText(BookTicket.this, "Ticket not Booked!!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(BookTicket.this, "Ticket Booked", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "User");
                    startActivity(new Intent(BookTicket.this, MainActivity.class).putExtras(bundle));
                    finish();

                }


            }
        }
        saveTaskInBackend st = new saveTaskInBackend();
        st.execute();
    }

    private void fetchShows() {
        class GetSavedTasks extends AsyncTask<Void, Void, List<ShowModel>> {
            @Override
            protected List<ShowModel> doInBackground(Void... voids) {
                ShowDatabase showDatabase=ShowDatabase.getInstance(BookTicket.this);
                showModels = showDatabase.showDao().getMovieShowList(movieId,theatreId);
                return showModels;
            }

            @Override
            protected void onPostExecute(List<ShowModel> val) {
                super.onPostExecute(val);
                if (val.size()!=0){
                    showAdapter=new ShowAdapter(BookTicket.this,showModels);

                    binding.rvShow.setAdapter(showAdapter);

                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }

    public void fetchBookedSeats(int show_id,String show) {
        showId= String.valueOf(show_id);
        show= show;
        class GetSavedTasks extends AsyncTask<Void, Void, List<BookingModel>> {
            @Override
            protected List<BookingModel> doInBackground(Void... voids) {
                BookingDatabase bookingDatabase=BookingDatabase.getInstance(BookTicket.this);

                    List<BookingModel> bookingModels=new ArrayList<>();

                    bookingModels = bookingDatabase.bookingDao().getBookingSeatList(movieId, String.valueOf(show_id), theatreId);

                return bookingModels;
            }

            @Override
            protected void onPostExecute(List<BookingModel> val) {
                super.onPostExecute(val);
                if (val.size()!=0){
                    for (int i=0; i<val.size();i++){


                        List<String> seatModel= new ArrayList<>();
                        seatModel=val.get(i).getSeatModels();
                        for (int j=0;j<seatModel.size();j++){
                            seatNumbers.add(String.valueOf(seatModel.get(j)));
                        }
//                        seatModel=val.get(i).getSeatModels().subList(0,val.get(i).getSeatModels().size()-1);
//                        bookedSeatModels.add(new SeatModel(seatModel));
                    }
                }
                fetchAllSeats(show_id);
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    public void fetchAllSeats(int show_id) {
        class GetSavedTasks extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                ShowDatabase showDatabase=ShowDatabase.getInstance(BookTicket.this);
                available_seats = showDatabase.showDao().getavailableSeat(show_id);
                return available_seats;
            }

            @Override
            protected void onPostExecute(Integer val) {
                super.onPostExecute(val);
                if (val!=0){
                    for(int i=1;i<=40;i++){
                        if(seatNumbers.size()!=0){
                            if (seatNumbers.contains(String.valueOf(i))) {
                                seatModels.add(new SeatModel(i, true));
                            }else {
                                seatModels.add(new SeatModel(i, false));
                            }
                        }else {
                            seatModels.add(new SeatModel(i,false ));
                        }
                    }
                    seatAdapter=new SeatAdapter(BookTicket.this,seatModels);


                    binding.rvSeat1.setAdapter(seatAdapter);
                    binding.noTxt.setVisibility(View.GONE);
                    binding.rvSeat1.setVisibility(View.VISIBLE);


                }
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
}