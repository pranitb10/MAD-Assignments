package edu.northeastern.numad22faPranitBrahmbhatt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerLinkAdapter extends RecyclerView.Adapter<RecyclerLinkAdapter.MyViewHolder> {
    Context context;
    ArrayList<LinkModel> arrLink;

    public RecyclerLinkAdapter(@NonNull Context context, @NonNull ArrayList<LinkModel> arrLink) {
        this.context = context;
        this.arrLink = arrLink;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View linkLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(linkLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LinkModel lmodel = arrLink.get(position);
        holder.lName.setText(lmodel.name);
        holder.links.setText(lmodel.link);
    }

    @Override
    public int getItemCount() {
        return arrLink.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lName, links;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.link_title);
            links = itemView.findViewById(R.id.url);
        }
    }
}
