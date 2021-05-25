package com.example.vgxchange.fragments.my_product;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vgxchange.tool.ProductState;

import java.util.List;

public class ProductStateSpinnerAdapter extends ArrayAdapter<ProductState.State> {

    private Context context;
    private List<ProductState.State> values;

    public ProductStateSpinnerAdapter(Context context, int resource, List<ProductState.State> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public ProductState.State getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).name());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
// Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).name());

        return label;
    }


}
