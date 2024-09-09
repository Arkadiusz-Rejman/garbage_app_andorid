package com.example.myapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.SensorDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.Sensor;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private List<Sensor> sensorList;
    private String clientLogin;

    public SensorAdapter(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    public SensorAdapter(List<Sensor> sensorList, String clientLogin) {
        this.sensorList = sensorList;
        this.clientLogin = clientLogin; // Przekaż login klienta do adaptera
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_item, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor sensor = sensorList.get(position);
        holder.textViewSensorId.setText("Sensor ID: " + sensor.getId());
        holder.textViewSensorValue.setText("Value: " + sensor.getSensorValue()+"%");
        // Ustawienie onClickListener na kafelek
        holder.itemView.setOnClickListener(v -> {
            // Przekazanie danych do nowego Activity
            Intent intent = new Intent(holder.itemView.getContext(), SensorDetailActivity.class);
            intent.putExtra("sensorId", sensor.getId());
            intent.putExtra("sensorValue", sensor.getSensorValue());
            intent.putExtra("clientLogin", clientLogin);
            // Przekazanie loginu
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    // ViewHolder
    public static class SensorViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSensorId;
        public TextView textViewSensorValue;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorId = itemView.findViewById(R.id.textViewSensorId);
            textViewSensorValue = itemView.findViewById(R.id.textViewSensorValue);
        }
    }
}