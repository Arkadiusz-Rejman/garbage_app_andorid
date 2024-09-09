package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.entity.Disposal;

import java.util.List;

public class DisposalAdapter extends RecyclerView.Adapter<DisposalAdapter.DisposalViewHolder> {

    private List<Disposal> disposalList;

    public DisposalAdapter(List<Disposal> disposalList) {
        this.disposalList = disposalList;
    }

    @NonNull
    @Override
    public DisposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disposal_item, parent, false);
        return new DisposalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisposalViewHolder holder, int position) {
        Disposal disposal = disposalList.get(position);
        // Sprawdzenie, czy disposalType nie jest null
        if (disposal.getDisposalType() != null) {
            holder.textViewDisposalType.setText("Type: " + disposal.getDisposalType());
        } else {
            holder.textViewDisposalType.setText("Type: N/A");  // Domyślna wartość, jeśli null
        }

        // Sprawdzenie, czy disposalDate nie jest null
        if (disposal.getDisposalDate() != null) {
            holder.textViewDisposalDate.setText("Date: " + disposal.getDisposalDate().toString());
        } else {
            holder.textViewDisposalDate.setText("Date: N/A");  // Domyślna wartość, jeśli null
        }

        if (disposal.isDisposalScheduled() == true) {
            holder.textViewDisposalScheduled.setText("Disposal is Scheduled");
        } else {
            holder.textViewDisposalScheduled.setText("Disposal not scheduled");  // Domyślna wartość, jeśli null
        }
    }

    @Override
    public int getItemCount() {
        return disposalList.size();
    }

    public static class DisposalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDisposalType, textViewDisposalDate, textViewDisposalScheduled;

        public DisposalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDisposalType = itemView.findViewById(R.id.textViewDisposalType);
            textViewDisposalDate = itemView.findViewById(R.id.textViewDisposalDate);
            textViewDisposalDate = itemView.findViewById(R.id.textViewDisposalDate);
            textViewDisposalScheduled = itemView.findViewById(R.id.textViewDisposalScheduled);
        }
    }
}
