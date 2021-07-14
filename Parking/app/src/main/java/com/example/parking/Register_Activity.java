    package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parking.Control.DatabaseDAO;

    public class Register_Activity extends AppCompatActivity {
        TextView nickname,account,password,repas,warning,back;
        Button register;
        String errorNoti1 = "Mật khẩu nhập lại không khớp";
        String errorNoti2 = "Bạn chưa nhập mật khẩu";
        String errorNoti3 = "Bạn chưa nhập nick name";
        String errorNoti4 = "Bạn chưa nhập tài khoản";
        DatabaseDAO database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        database = new DatabaseDAO(this);
        nickname = findViewById(R.id.nickname);
        account = findViewById(R.id.account);
        password = findViewById(R.id.passwords);
        repas = findViewById(R.id.repassword);
        warning = findViewById(R.id.warning);
        register = findViewById(R.id.btnregister);
        back = findViewById(R.id.backLogin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nickname.getText().toString().trim();
                String accounts = account.getText().toString().trim();
                String pass = password.getText().toString();
                String repass = repas.getText().toString().trim();
                if(!pass.equals(repass)){
                    warning.setText(errorNoti1);
                }else if(name == ""){
                    warning.setText(errorNoti3);
                }else if(pass == ""){
                    warning.setText(errorNoti2);
                }else if(accounts == ""){
                    warning.setText(errorNoti4);
                }else {
                    setData(name,accounts,pass);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity.this, MainActivity.class));
            }
        });

    }
    public void setData(String name ,String accounts ,String pass){
        Long result = database.addUser(name,accounts,pass);
        if(result > 0){
            Toast.makeText(this,"Register Success",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Register Fail....",Toast.LENGTH_SHORT).show();
        }
    }
}