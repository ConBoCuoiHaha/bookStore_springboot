package com.bookstorevn.models.ViewModels;

import com.bookstorevn.models.Category;
import com.bookstorevn.models.Product;
import com.bookstorevn.models.Subcategory;
import java.util.List;

public class ProductVM {
    private Product product;
    private List<Category> categoryList;
    private Integer selectedCategoryId;
    private List<Subcategory> subcategoryList;

    public ProductVM() {}

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public List<Category> getCategoryList() { return categoryList; }
    public void setCategoryList(List<Category> categoryList) { this.categoryList = categoryList; }

    public Integer getSelectedCategoryId() { return selectedCategoryId; }
    public void setSelectedCategoryId(Integer selectedCategoryId) { this.selectedCategoryId = selectedCategoryId; }

    public List<Subcategory> getSubcategoryList() { return subcategoryList; }
    public void setSubcategoryList(List<Subcategory> subcategoryList) { this.subcategoryList = subcategoryList; }
}
