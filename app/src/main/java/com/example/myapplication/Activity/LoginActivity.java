package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText Textlogin,Textpassword;
    TextView signupRedriectText;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupRedriectText = findViewById(R.id.signupRedirectText);
        Textlogin = findViewById(R.id.login_name);
        Textpassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);

        signupRedriectText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            String login = String.valueOf(Textlogin.getText());
            String passwd = String.valueOf(Textpassword.getText());
            loginUser(login,passwd);
        });



    }

    public void loginUser(String login, String password){
        ApiService apiService = RetrofitClient.getApiService();
        Client client = new Client(login,password);

        apiService.login(client).enqueue(new Callback<>() {
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        String responseString = response.body().string();
                        // Obsługa odpowiedzi tekstowej
                        Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid login or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}