package com.efrei.bballcoachcompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efrei.bballcoachcompanion.Modal.RencontreModal;

import java.util.List;

public class RencontreRVAdapter extends RecyclerView.Adapter<RencontreRVAdapter.ViewHolder> {

    private List<RencontreModal> rencontres;
    private Context context;

    public RencontreRVAdapter(List<RencontreModal> rencontres, Context context) {
        this.rencontres = rencontres;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RencontreModal rencontre = rencontres.get(position);
        holder.eq1TextView.setText(rencontre.getEquipe1());
        holder.dateTextView.setText(rencontre.getDate());
        holder.eq2TextView.setText(rencontre.getEquipe2());
        holder.bestScoreTextView.setText(rencontre.getBestScoreur());
        holder.dateTextView.setText(rencontre.getDate());
        holder.ptsTextView.setText(rencontre.getPts_mis());
    }

    @Override
    public int getItemCount() {
        return rencontres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eq1TextView, eq2TextView, scoreTextView, bestScoreTextView, ptsTextView, dateTextView;


        public ViewHolder(View view) {
            super(view);
            eq1TextView = view.findViewById(R.id.eq1TextView);
            eq2TextView = view.findViewById(R.id.eq2TextView);
            scoreTextView = view.findViewById(R.id.scoreTextView);
            bestScoreTextView = view.findViewById(R.id.bestScoreTextView);
            ptsTextView = view.findViewById(R.id.ptsTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
        }
    }
}
