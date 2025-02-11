package com.quizapp.model;

public class Category {
    private int categoryId;
    private String name;

    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // ✅ Getter 方法
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }

    // ✅ Setter 方法（如果需要）
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setName(String name) { this.name = name; }
}
