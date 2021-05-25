package com.example.vgxchange.fragments.menu_navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vgxchange.R;
import com.example.vgxchange.model.GamesStatistics;
import java.util.ArrayList;
import java.util.List;

public class TopGameAdapter extends RecyclerView.Adapter<TopGameAdapter.TopGameViewHolder> {

    List<GamesStatistics> games = new ArrayList<>();
    Context context;


    public TopGameAdapter(Context context, List<GamesStatistics> games) {

        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public TopGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_top_games, parent, false);
        return new TopGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopGameViewHolder holder, int position) {
        GamesStatistics game = games.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount() { return games.size(); }

    public static class TopGameViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public TopGameViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.top_game_name_txt);
        }


        public void bind(GamesStatistics gamesStatistics) {
            name.setText(gamesStatistics.getNameGame());
        }
    }
}

