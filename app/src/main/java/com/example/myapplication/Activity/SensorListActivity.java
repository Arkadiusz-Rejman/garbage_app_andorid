package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Interface.ApiService;
import com.example.myapplication.Interface.RetrofitClient;
import com.example.myapplication.R;
import com.example.myapplication.entity.Sensor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.protobuf.Api;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorListActivity extends AppCompatActivity {
    private FloatingActionButton buttonAddSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_sensor_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAddSensor = findViewById(R.id.buttonAddSensor);
        buttonAddSensor.setOnClickListener(view -> {
            showAddSensorDialog2();
        });
    }

    private void addSensorToClient(String sensorId){
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.addSensorToClient(sensorId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SensorListActivity.this, "Sensor added successfully", Toast.LENGTH_SHORT).show();
                    //loadSensors();  // Odświeżamy kafelki z sensorami
                } else {
                    Toast.makeText(SensorListActivity.this, "Failed to add sensor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(SensorListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SensorListActivity", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void loadSensors() {
        // Załaduj listę sensorów zalogowanego klienta i wyświetl kafelki
        // Implementacja zależna od tego, jak chcesz wyświetlać kafelki (np. RecyclerView, GridLayout itd.)
    }
    private void showAddSensorDialog2() {
        // Tworzymy nowy dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_sensor);

        dialog.setCanceledOnTouchOutside(false);

        // Znajdujemy widoki w dialogu
        EditText etSensorId = dialog.findViewById(R.id.etSensorId);
        Button btnAddSensor = dialog.findViewById(R.id.btnAddSensor);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Obsługa przycisku Dodaj Sensor
        btnAddSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sensorId = etSensorId.getText().toString();
                sendSensorIdToServer(sensorId);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show(); // Wyświetlamy dialog
    }

    private void sendSensorIdToServer(String sensorId) {
        ApiService service = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Sensor> call = service.getSensorById(sensorId);

        call.enqueue(new Callback<Sensor>() {
            @Override
            public void onResponse(Call<Sensor> call, Response<Sensor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Sensor istnieje, otrzymujemy dane sensora
                    Sensor sensor = response.body();
                    Toast.makeText(SensorListActivity.this, "Sensor found: " + sensor.getId(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SensorListActivity.this, "Sensor not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sensor> call, Throwable t) {
                Toast.makeText(SensorListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
    }

