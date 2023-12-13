package com.example.tickethub.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickethub.AddCast;

import com.example.tickethub.Database.CastDatabase;
import com.example.tickethub.Database.UserDatabase;
import com.example.tickethub.MainActivity;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.UserModel;
import com.example.tickethub.R;
import com.example.tickethub.UserList;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private List<UserModel> userModels=new ArrayList<>();
    private Context mContext;

    public UserAdapter(Context mContext, List<UserModel> userModels) {
        this.userModels = userModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_user,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, int i) {

        viewHolder.name.setText("Name : "+userModels.get(i).getName());
        viewHolder.email.setText("Email Id : "+userModels.get(i).getEmail());
        viewHolder.phone.setText("Phone Number: "+userModels.get(i).getPhone());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the object of AlertDialog Builder class
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to delete this User ?");

                // Set Alert Title
                builder.setTitle("Alert !");
                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {


                    class saveTaskInBackend extends AsyncTask<Void, Void, Integer> {
                        @SuppressLint("WrongThread")
                        @Override
                        protected Integer doInBackground(Void... voids) {
                            UserDatabase userDatabase=UserDatabase.getInstance(mContext);

                            int val = userDatabase.userDao().deleteByUserId(userModels.get(i).getUserId());

                            return val;
                        }

                        @Override
                        protected void onPostExecute(Integer val) {
                            super.onPostExecute(val);
                            if (val==0){
                                Toast.makeText(mContext, "User not Deleted", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(mContext, "User Deleted", Toast.LENGTH_SHORT).show();
                                ((UserList)mContext).fetchUser();
                            }


                        }
                    }
                    saveTaskInBackend st = new saveTaskInBackend();
                    st.execute();
//                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(mContext, "User Deleted", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    });
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,phone,email;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            email=itemView.findViewById(R.id.email);
            delete=itemView.findViewById(R.id.delete);

        }
    }

}

