package com.example.waterleakage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeakageAdapter extends RecyclerView.Adapter<LeakageAdapter.ViewHolder>
{
    ItemClicked itemClicked;
    private List<Leakage> leakageList;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public LeakageAdapter(Context context, List<Leakage> list)
    {
        itemClicked = (ItemClicked) context;
        leakageList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatus;
        TextView tvTypeL,tvPostEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvTypeL = itemView.findViewById(R.id.tvTypeL);
            tvPostEmail = itemView.findViewById(R.id.tvPostEmail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked.onItemClicked(leakageList.indexOf((Leakage) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public LeakageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.row_layout,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeakageAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(leakageList.get(position));
        if (leakageList.get(position).getLeakStatus().equals("Pending")) {
            holder.ivStatus.setImageResource(R.mipmap.pending);
        }
        else if(leakageList.get(position).getLeakStatus().equals("In progress"))
        {
            holder.ivStatus.setImageResource(R.mipmap.progress);
        }
        else
        {
            holder.ivStatus.setImageResource(R.mipmap.complete);
        }
        holder.tvTypeL.setText(leakageList.get(position).getLeakType());
        holder.tvPostEmail.setText(leakageList.get(position).getLeakBy());

    }

    @Override
    public int getItemCount() {
        return leakageList.size();
    }
}
