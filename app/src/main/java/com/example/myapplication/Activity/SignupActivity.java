package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Interface.ApiService;
import com.example.myapplication.R;
import com.example.myapplication.Interface.RetrofitClient;
import com.example.myapplication.entity.Client;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText signupLogin,signupPassword,signupAddress, signupName, signupSurname;
    TextView loginRedirectText;
    Button signupButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupLogin = findViewById(R.id.signup_login);
        signupPassword = findViewById(R.id.signup_password);
        signupAddress = findViewById(R.id.signup_addres);
        signupName = findViewById(R.id.signup_name);
        signupSurname = findViewById(R.id.signup_surname);


        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);




        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClient();
            }
        });


    }
    public void registerClient(){
        String login = signupLogin.getText().toString();
        String password = signupPassword.getText().toString();
        String address = signupAddress.getText().toString();
        String name = signupName.getText().toString();
        String surname = signupSurname.getText().toString();

        Client client = new Client(login,password,address,name,surname);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.registerClient(client);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "User registered succesfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Registartion failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(SignupActivity.this, "Fatal error (no server respone): " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignupActivity", Objects.requireNonNull(t.getMessage()));
            }
        });


    }

}