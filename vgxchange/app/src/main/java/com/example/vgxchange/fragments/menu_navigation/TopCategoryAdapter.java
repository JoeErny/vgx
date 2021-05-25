package com.example.vgxchange.fragments.menu_navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vgxchange.R;
import com.example.vgxchange.model.CategoriesStatistics;
import com.example.vgxchange.model.GamesStatistics;
import java.util.ArrayList;
import java.util.List;

public class TopCategoryAdapter extends RecyclerView.Adapter<TopCategoryAdapter.TopCategoryViewHolder> {

    List<CategoriesStatistics> categories = new ArrayList<>();
    Context context;


    public TopCategoryAdapter(Context context, List<CategoriesStatistics> categories) {

        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public TopCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_top_category, parent, false);
        return new TopCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCategoryViewHolder holder, int position) {
        CategoriesStatistics category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() { return categories.size(); }

    public static class TopCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView category;

        public TopCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.top_category_name_txt);
        }


        public void bind(CategoriesStatistics categoriesStatistics) {
            category.setText(categoriesStatistics.getNameCategory());
        }
    }
}

