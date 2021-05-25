package com.example.vgxchange.fragments.adding_product_announce;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vgxchange.model.Game;

import java.util.List;

public class GameSpinnerAdapter extends ArrayAdapter<Game> {
    private Context context;
    private List<Game> values;

    public GameSpinnerAdapter(Context context, int textViewResourceId, List<Game> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Game getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title = (TextView) super.getView(position, convertView, parent);
        title.setTextColor(Color.BLACK);
        title.setText(values.get(position).getName());
        return title;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView title = (TextView) super.getDropDownView(position, convertView, parent);
        title.setTextColor(Color.BLACK);
        title.setText(values.get(position).getName());
        return title;
    }

}
