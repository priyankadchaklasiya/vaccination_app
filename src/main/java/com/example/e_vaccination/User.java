package com.example.e_vaccination;
public class User {
    private String Dosename;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String birthDate;
    private String appointmentDate;
    private String btn_dose;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String Dosename, String name, String email, String phone, String address, String birthDate, String appointmentDate, String btn_dose) {
        this.Dosename = Dosename;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.appointmentDate = appointmentDate;
        this.btn_dose = btn_dose;
    }


    public String getDosename() {
        return Dosename;
    }

    public void setDosename(String dosename) {
        Dosename = dosename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {this.appointmentDate = appointmentDate;}
    public String getbtn_dose() {
        return btn_dose;
    }

    public void setbtn_dose(String btn_dose) {
        this.btn_dose = btn_dose;
    }
}
