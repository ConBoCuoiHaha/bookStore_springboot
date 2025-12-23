package com.bookstorevn.models.ViewModels;

import com.bookstorevn.models.Category;
import com.bookstorevn.models.Subcategory;
import java.util.List;

public class SubcategoryVM {
    private Subcategory subcategory;
    private List<Category> categoryList;

    public SubcategoryVM() {}

    public Subcategory getSubcategory() { return subcategory; }
    public void setSubcategory(Subcategory subcategory) { this.subcategory = subcategory; }

    public List<Category> getCategoryList() { return categoryList; }
    public void setCategoryList(List<Category> categoryList) { this.categoryList = categoryList; }
}
