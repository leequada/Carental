package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parking.Control.DatabaseDAO;
import com.example.parking.Model.User;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    TextView register;
    Button btnLogin;
    public static User u;
    DatabaseDAO database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btnLogin);
        database = new DatabaseDAO(this);


        database.queryData("CREATE TABLE IF NOT EXISTS " +
                "Users(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name varchar(200) , account varchar(200) , passwords varchar(200)) ");
        database.queryData("CREATE TABLE IF NOT EXISTS " +
                "Car(Id INTEGER PRIMARY KEY AUTOINCREMENT, Bienxe varchar(200) , Time varchar(200) , dateIn date) ");
        //database.queryData("INSERT INTO Car VALUES(null,'29Y3-12245','13:30','28-5-2021')");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = username.getText().toString();
                String pass = password.getText().toString();
                User user = database.Checklogin(acc,pass);
                if(user != null){
                    u = user;
                    startActivity(new Intent(MainActivity.this,Home_Activity.class));
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register_Activity.class));
            }
        });
    }
}