package com.example.tickethub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.tickethub.Adapter.BookingAdapter;
import com.example.tickethub.Adapter.UserAdapter;
import com.example.tickethub.Database.BookingDatabase;
import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.UserModel;
import com.example.tickethub.databinding.ActivityBookingListBinding;
import com.example.tickethub.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class BookingList extends AppCompatActivity {
    ActivityBookingListBinding binding;
    BookingAdapter bookingAdapter;
    List<BookingModel> bookingLists= new ArrayList<>();
    Constant constant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

constant= new Constant(this);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.rvBookinglist.setHasFixedSize(true);
        binding.rvBookinglist.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent().getStringExtra("type").equals("User")){
            fetchUserBookingList();
        }else{
            fetchBookingList();
        }

    }

    private void fetchUserBookingList() {
        {
            class GetSavedTasks extends AsyncTask<Void, Void, List<BookingModel>> {
                @Override
                protected List<BookingModel> doInBackground(Void... voids) {
                    BookingDatabase bookingDatabase=BookingDatabase.getInstance(BookingList.this);
                    bookingLists = bookingDatabase.bookingDao().getUserBookingList(constant.getUser_id());
                    return bookingLists;
                }

                @Override
                protected void onPostExecute(List<BookingModel> val) {
                    super.onPostExecute(val);
                    if(bookingLists.size()!=0){

                        bookingAdapter= new BookingAdapter(BookingList.this,bookingLists);
                        binding.rvBookinglist.setAdapter(bookingAdapter);
                        binding.noTxt.setVisibility(View.GONE);
                        binding.rvBookinglist.setVisibility(View.VISIBLE);
                    } else {
                        //   Toast.makeText(MovieList.this, "No Movie Found", Toast.LENGTH_SHORT).show();
                        binding.noTxt.setVisibility(View.VISIBLE);
                        binding.rvBookinglist.setVisibility(View.GONE);
                    }
                }
            }

            GetSavedTasks savedTasks = new GetSavedTasks();
            savedTasks.execute();

        }
    }
    private void fetchBookingList() {
        {
            class GetSavedTasks extends AsyncTask<Void, Void, List<BookingModel>> {
                @Override
                protected List<BookingModel> doInBackground(Void... voids) {
                    BookingDatabase bookingDatabase=BookingDatabase.getInstance(BookingList.this);
                    bookingLists = bookingDatabase.bookingDao().getBookingList();
                    return bookingLists;
                }

                @Override
                protected void onPostExecute(List<BookingModel> val) {
                    super.onPostExecute(val);
                    if(bookingLists.size()!=0){

                        bookingAdapter= new BookingAdapter(BookingList.this,bookingLists);
                        binding.rvBookinglist.setAdapter(bookingAdapter);
                        binding.noTxt.setVisibility(View.GONE);
                        binding.rvBookinglist.setVisibility(View.VISIBLE);
                    } else {
                        //   Toast.makeText(MovieList.this, "No Movie Found", Toast.LENGTH_SHORT).show();
                        binding.noTxt.setVisibility(View.VISIBLE);
                        binding.rvBookinglist.setVisibility(View.GONE);
                    }
                }
            }

            GetSavedTasks savedTasks = new GetSavedTasks();
            savedTasks.execute();

        }
    }

}