package com.example.e_vaccination;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Admin_Activity extends AppCompatActivity {
    FloatingActionButton fab;
    ImageButton log;
    private ArrayList<User> dataList;
    private User_Adapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Admin_Activity.this, UploadActivity.class);
            startActivity(intent);
            finish();
        });

        log = findViewById(R.id.log_out);
        log.setOnClickListener(view -> {
            Toast.makeText(Admin_Activity.this, "Admin Logout Successfull.",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Admin_Activity.this, Login_activity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();
        adapter = new User_Adapter(this, dataList);
        recyclerView.setAdapter(adapter);
        User_Adapter.OnItemClickListener acceptListener = user -> {
            // Handle accept action here
            sendEmail(user.getEmail(),user.getName(),user.getAppointmentDate());

             };

        // Define reject listener
        User_Adapter.OnItemClickListener VaccinListener = user -> {
            // Handle reject action here
            ApruvalsendEmail(user.getEmail(),user.getName(),user.getDosename(),user.getbtn_dose());  };

        // Set accept and reject listeners to the adapter
        adapter.setAcceptListener(acceptListener);
        adapter.setVaccinListener(VaccinListener);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Clear previous data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    dataList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void sendEmail(String receiver,String Name, String date) {
        final String username = "hardikpmr@rbi.edu.in"; // Replace with your email address
        final String password = "hp982480"; // Replace with your email password


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("Vaccination Appointment Confirmation");
            message.setText("Dear " + Name + "\n\nYour vaccination appointment is confirmed:\n" +
                    "- Date: " + date + ".\n\n" +
                    "Arrive 15 mins early.\n" +
                    "For changes, contact us at (+91-8780484740).\n\n" +
                    "Thank you,\nE-Vaccination Team");

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(message);
                    runOnUiThread(() -> Toast.makeText(Admin_Activity.this, "Email sent successfully", Toast.LENGTH_SHORT).show());
                } catch (MessagingException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(Admin_Activity.this, "Failed to send email", Toast.LENGTH_SHORT).show());
                }
            });
            thread.start();
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ApruvalsendEmail(String receiver, String Name, String dose, String dose_type) {
        final String username = "hardikpmr@rbi.edu.in"; // Replace with your email address
        final String password = "hp982480"; // Replace with your email password


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("Vaccination As Done");
            String htmlMessage = "<html><body>" +
                    "<p>Dear " + Name + ",</p>" +
                    "<p>We are pleased to inform you that you have successfully completed your vaccination against " + dose + " for dose " + dose_type + " at our vaccination center.</p>" +
                    "<p>For changes, contact us at <a href='tel:+918780484740'>(+91-8780484740)</a>.</p>" +
                    "<p>Thank you,<br>E-Vaccination Team</p>" +
                    "</body></html>";

            message.setText(String.valueOf(Html.fromHtml(htmlMessage, Html.FROM_HTML_MODE_LEGACY)));

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(message);
                    runOnUiThread(() -> Toast.makeText(Admin_Activity.this, "Email sent successfully", Toast.LENGTH_SHORT).show());
                } catch (MessagingException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(Admin_Activity.this, "Failed to send email", Toast.LENGTH_SHORT).show());
                }
            });
            thread.start();
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show();
        }
    }
}