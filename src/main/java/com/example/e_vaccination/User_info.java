package com.example.e_vaccination;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class User_info extends AppCompatActivity {
    private EditText DoseName, Email, editTextName, editTextEmail, editTextPhone, editTextAddress, birthDate, appointmentDate;
    private Button buttonSubmit,btn_dose;
    FirebaseAuth auth;
    FirebaseUser user;
    BottomNavigationView nav;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        auth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.users);
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(),Login_activity.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }
        String caption = getIntent().getStringExtra("caption");
        TextView DoseName = findViewById(R.id.Dosename);
        DoseName.setText(caption); // Set the text of DoseName EditText to the value of caption



// Initialize Firebase
        FirebaseApp.initializeApp(this);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        birthDate = findViewById(R.id.birthDate);
        appointmentDate = findViewById(R.id.appointmentDate);
        btn_dose = findViewById(R.id.btn_dose1);
        buttonSubmit = findViewById(R.id.buttonSubmit);
// Get a reference to your Firebase database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

// Add data to your Firebase database
        buttonSubmit.setOnClickListener(v -> {
            // Get the input values
            String Dosename = DoseName.getText().toString().trim();
            String name = editTextName.getText().toString().trim();
            String email = textView.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String birth = birthDate.getText().toString().trim();
            String appointment = appointmentDate.getText().toString().trim();
            String btnDoseText = btn_dose.getText().toString().trim();

            // Create a User object with the input values
            User user = new User(Dosename, name, email, phone, address, birth, appointment, btnDoseText);
            // Push the User object to the Firebase Database
            database.child("Users").push().setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        // Data successfully added
                        Toast.makeText(User_info.this, "Data added successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Failed to add data
                        Toast.makeText(User_info.this, "Failed to add data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


        // Add code here to initialize UI elements or handle form submission
        appointmentDate.setOnClickListener(v -> {
            // Get today's date
            final Calendar c = Calendar.getInstance();
            int currentYear = c.get(Calendar.YEAR);
            int currentMonth = c.get(Calendar.MONTH);
            int currentDay = c.get(Calendar.DAY_OF_MONTH);

            // Create a DatePickerDialog with a listener to set the selected date
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    User_info.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Set the selected date to the appointmentDate EditText
                        appointmentDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    },
                    currentYear, currentMonth, currentDay);

            // Set the minimum date to today's date to restrict past dates
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            // Show the DatePickerDialog
            datePickerDialog.show();
        });

        btn_dose.setOnClickListener(this::showDoseMenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nav = findViewById(R.id.nav_bar);
        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }


            if (item.getItemId() == R.id.Logout) {
                Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                startActivity(intent);
                finish();
            }

            return false;
        });
    }

    public void showDoseMenu(View v) {
        // Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(User_info.this, v);
        // Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_dose_options, popup.getMenu());

        // registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(item -> {
            Button btnDose1 = findViewById(R.id.btn_dose1);
            // Handle item selection
            if (item.getItemId() == R.id.dose1) {
                // Handle Dose 1 selection
                btnDose1.setText("Dose 1");
                return true;
            } else if (item.getItemId() == R.id.dose2) {
                // Handle Dose 2 selection
                btnDose1.setText("Dose 2");
                return true;
            } else if (item.getItemId() == R.id.dose3) {
                // Handle Dose 3 selection
                btnDose1.setText("Dose 3");
                return true;
            }
            else if (item.getItemId() == R.id.dose4) {
                // Handle Dose 3 selection
                btnDose1.setText("Dose 4");
                return true;
            } else {
                return false;
            }
        });

        // Showing the popup menu
        popup.show();
    }

}
