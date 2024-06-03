package com.example.e_vaccination;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder> {

    private final ArrayList<User> dataList;
    private final Context context;
    private OnItemClickListener acceptListener;
    private OnItemClickListener VaccinListener;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");


    public User_Adapter(Context context, ArrayList<User> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setAcceptListener(OnItemClickListener listener) {
        this.acceptListener = listener;
    }

    public void setVaccinListener(OnItemClickListener listener) {
        this.VaccinListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User data = dataList.get(position);
        holder.getName.setText(data.getName());
        holder.getEmail.setText(data.getEmail());
        holder.getDosename.setText(data.getDosename());
        holder.getBirthDate.setText(data.getBirthDate());
        holder.getAppointmentDate.setText(data.getAppointmentDate());
        holder.getbtn_dose.setText(data.getbtn_dose());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView getName,getEmail,getDosename,getBirthDate,getAppointmentDate, getbtn_dose;
        Button acceptButton, VaccinetedButton;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getName = itemView.findViewById(R.id.User_name);
            getEmail = itemView.findViewById(R.id.name);
            getDosename = itemView.findViewById(R.id.Dose);
            getBirthDate = itemView.findViewById(R.id.DOB);
            getAppointmentDate = itemView.findViewById(R.id.appointment_Date);
            getbtn_dose = itemView.findViewById(R.id.Dose_type);
            acceptButton = itemView.findViewById(R.id.accept);
            VaccinetedButton = itemView.findViewById(R.id.Vaccineted);

            // Set click listener for the accept button
            acceptButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User user = dataList.get(position);
                    if (acceptListener != null) {
                        acceptListener.onItemClick(user);
                        acceptButton.setText("Accepted");
                        acceptButton.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.app_theme));


                    }
                }
            });


            // Set click listener for the reject button
            VaccinetedButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (VaccinListener != null && position != RecyclerView.NO_POSITION) {
                    User user = dataList.get(position);
                    VaccinListener.onItemClick(user);
                    VaccinetedButton.setText("Vaccineted");
                    VaccinetedButton.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.app_theme));


                }
            });


        }
    }
}
