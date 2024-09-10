package com.example.myapplication.Activity;

import static com.example.myapplication.entity.DisposalType.BIG;
import static com.example.myapplication.entity.DisposalType.EXTRA;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ApiService;
import com.example.myapplication.Interface.RetrofitClient;
import com.example.myapplication.R;
import com.example.myapplication.adapter.DisposalAdapter;
import com.example.myapplication.entity.Disposal;
import com.example.myapplication.entity.DisposalType;
import com.example.myapplication.entity.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorDetailActivity extends AppCompatActivity {
    private TextView textViewSensorDetailId;
    private TextView textViewSensorDetailValue;
    private TextView textViewClientLogin;
    private Button buttonBack,buttonLogout,buttonExtraDisposal,buttonBigDisposal,buttonMyDisposals;
    SessionManager sessionManager;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sensor_detail_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Inicjalizacja widoków
        textViewSensorDetailId = findViewById(R.id.textViewSensorDetailId);
        textViewSensorDetailValue = findViewById(R.id.textViewSensorDetailValue);
        buttonBack = findViewById(R.id.buttonBack);
        buttonLogout = findViewById(R.id.buttonLogout2);
        buttonExtraDisposal = findViewById(R.id.buttonExtraDisposal);
        buttonBigDisposal = findViewById(R.id.buttonBigDisposal);
        buttonMyDisposals = findViewById(R.id.buttonMyDisposals);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Pobranie danych z Intent
        long sensorId = getIntent().getLongExtra("sensorId",0);
        int sensorValue = getIntent().getIntExtra("sensorValue",0);
        String clientLogin = getIntent().getStringExtra("clientLogin");

        // Wyświetlenie danych
        textViewSensorDetailId.setText("Sensor ID: " + sensorId);
        textViewSensorDetailValue.setText("Fullfilment: " + sensorValue + "%");

        buttonBack.setOnClickListener(v -> finish());

        // Obsługa przycisku wylogowania
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wylogowanie użytkownika
                sessionManager.logout();

                // Przejście do ekranu logowania
                Intent intent = new Intent(SensorDetailActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Usunięcie historii aktywności
                startActivity(intent);
                finish();  // Zamknięcie bieżącej aktywności
            }
        });

        // Obsługa przycisków dodawania Disposal
        buttonExtraDisposal.setOnClickListener(v -> showDisposalConfirmationDialog(sensorId,EXTRA));
        buttonBigDisposal.setOnClickListener(view -> showDisposalConfirmationDialog(sensorId,BIG));
        buttonMyDisposals.setOnClickListener(view -> showDisposalsDialog(sensorId));
    }


    // Metoda do wyświetlenia dialogu potwierdzenia
    private void showDisposalConfirmationDialog(Long sensorId, DisposalType disposalType) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Disposal")
                .setMessage("Do you want to create a disposal for Sensor ID: " + sensorId + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tworzenie obiektu Disposal i wysyłanie do backendu
                        createAndSendDisposal(sensorId,disposalType);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Zamknięcie dialogu
                        dialog.dismiss();
                    }
                })
                .show();
    }


    // Metoda do tworzenia Disposal i wysyłania do Spring Boot
    private void createAndSendDisposal(long sensorId, DisposalType disposalType) {
        Disposal disposal = new Disposal(sensorId, disposalType);  // Przykład disposal_type
        apiService.saveExtraDisposal(disposal,sensorId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                   // Toast.makeText(SensorDetailActivity.this, "Disposal saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SensorDetailActivity.this, "Failed to save disposal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SensorDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showDisposalsDialog(Long sensorId) {
        // Inicjalizacja dialogu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_disposal_list, null);
        builder.setView(dialogView);

        // Pobranie referencji do widoków w dialogu
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewDisposals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button buttonClose = dialogView.findViewById(R.id.buttonClose);

        // Stworzenie dialogu
        AlertDialog dialog = builder.create();

        // Obsługa kliknięcia przycisku zamknięcia
        buttonClose.setOnClickListener(v -> {
            dialog.dismiss();  // Zamknięcie dialogu
        });

        // Pobranie listy Disposal z backendu
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getDisposalsBySensorId(sensorId).enqueue(new Callback<List<Disposal>>() {
            @Override
            public void onResponse(Call<List<Disposal>> call, Response<List<Disposal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Disposal> disposals = response.body();
                    // Ustawienie adaptera z danymi
                    DisposalAdapter adapter = new DisposalAdapter(disposals);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Disposal>> call, Throwable t) {
                // Obsługa błędu
            }
        });

        // Wyświetlenie dialogu
        dialog.show();
    }

}




