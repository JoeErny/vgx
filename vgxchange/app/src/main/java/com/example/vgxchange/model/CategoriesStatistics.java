package com.example.vgxchange.model;

public class CategoriesStatistics {
    public int totalCategory;
    public String nameCategory;

    public CategoriesStatistics(int totalCategory, String nameCategory) {
        this.totalCategory = totalCategory;
        this.nameCategory = nameCategory;
    }

    public CategoriesStatistics() {
    }

    public String getNameCategory() {
        return nameCategory;
    }
    public int getTotalCategory() {
        return totalCategory;
    }
    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }
    public CategoriesStatistics(String nameCategory, int totalCategory) { }
}
