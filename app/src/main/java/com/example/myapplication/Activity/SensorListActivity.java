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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ApiService;
import com.example.myapplication.Interface.RetrofitClient;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SensorAdapter;
import com.example.myapplication.entity.Sensor;
import com.example.myapplication.entity.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.protobuf.Api;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorListActivity extends AppCompatActivity {
    private FloatingActionButton buttonAddSensor;
    private SessionManager sessionManager;
    private ApiService apiService;
    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;

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

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        recyclerView = findViewById(R.id.recyclerViewSensors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchClientSensors();


        buttonAddSensor = findViewById(R.id.buttonAddSensor);
        buttonAddSensor.setOnClickListener(view -> {
            //tu obsługa guzika + na liscie
            showAddSensorDialog();
        });
    }

    //guzik dodawania na liscie :)
    private void showAddSensorDialog() {
        // Tworzymy nowy dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_sensor);

        dialog.setCanceledOnTouchOutside(false);

        // Znajdujemy widoki w dialogu
        EditText etSensorId = dialog.findViewById(R.id.etSensorId);
        Button btnAddSensor = dialog.findViewById(R.id.btnAddSensor);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Obsługa przycisku Dodaj Sensor
        btnAddSensor.setOnClickListener(v -> {
            String sensorId = etSensorId.getText().toString();
            assignSensorToClient(sensorId);

        });

        btnCancel.setOnClickListener(v -> {
                    dialog.dismiss();
                    fetchClientSensors();
                }
        );

        dialog.show(); // Wyświetlamy dialog
    }



    private void assignSensorToClient(String sensorId){
        String clientLogin = sessionManager.getUserLogin();
        apiService.addSensorToClient(sensorId,clientLogin).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SensorListActivity.this, "Sensor assigned successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SensorListActivity.this, "Failed to assign sensor" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(SensorListActivity.this, "Fatal Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchClientSensorsX() {
        String clientLogin = sessionManager.getUserLogin(); // Pobieramy login użytkownika

        apiService.getClientSensors(clientLogin).enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Sensor> sensors = response.body();
                    // Tutaj możesz np. zaktualizować widok listy sensorów
                    Toast.makeText(SensorListActivity.this, "Found " + sensors.size() + " sensors", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SensorListActivity.this, "No sensors found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Toast.makeText(SensorListActivity.this, "Failed to fetch sensors", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchClientSensors() {
        String clientLogin = sessionManager.getUserLogin(); // Pobieramy login użytkownika

        apiService.getClientSensors(clientLogin).enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Sensor> sensors = response.body();
                    sensorAdapter = new SensorAdapter(sensors);
                    recyclerView.setAdapter(sensorAdapter); // Ustawienie adaptera
                    Toast.makeText(SensorListActivity.this, "Found " + sensors.size() + " sensors", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SensorListActivity.this, "No sensors found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Toast.makeText(SensorListActivity.this, "Failed to fetch sensors", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadSensors() {
        // Załaduj listę sensorów zalogowanego klienta i wyświetl kafelki
        // Implementacja zależna od tego, jak chcesz wyświetlać kafelki (np. RecyclerView, GridLayout itd.)
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
    private void addSensorToClient(String sensorId){
//    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//    Call<Void> call = apiService.addSensorToClient(sensorId);
//
//    call.enqueue(new Callback<Void>() {
//        @Override
//        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//            if (response.isSuccessful()) {
//                Toast.makeText(SensorListActivity.this, "Sensor added successfully", Toast.LENGTH_SHORT).show();
//                //loadSensors();  // Odświeżamy kafelki z sensorami
//            } else {
//                Toast.makeText(SensorListActivity.this, "Failed to add sensor", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//            Toast.makeText(SensorListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.e("SensorListActivity", Objects.requireNonNull(t.getMessage()));
//        }
//    });
}
}

