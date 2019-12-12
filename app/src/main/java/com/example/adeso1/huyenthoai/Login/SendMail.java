package com.example.adeso1.huyenthoai.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adeso1.huyenthoai.Player.MainActivity;
import com.example.adeso1.huyenthoai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.*;

public class SendMail extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnDangKy,btnKQuayLai;
    EditText edkEmail,edkPassWord;
    ProgressBar simpleProses;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        btnDangKy=findViewById(R.id.kbtnDangky);
        edkEmail=findViewById(R.id.kedEmail);
        edkPassWord=findViewById(R.id.kedPassWord);
        btnKQuayLai=findViewById(R.id.btnKQuayLai);
        simpleProses=findViewById(R.id.simpleProgressBar);
        btnKQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SendMail.this, MainActivity.class));
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleProses.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(edkEmail.getText().toString(),edkPassWord.getText().toString())
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                simpleProses.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful())
                                            {
                                                makeText(SendMail.this,"Đăng ký thành công làm kiểm tra email để xác minh", LENGTH_LONG).show();


                                            }
                                            else {
                                                Toast.makeText(SendMail.this,task.getException().getMessage(), LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                }
                                else {
                                    Toast.makeText(SendMail.this,task.getException().getMessage(), LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });


    }

}
