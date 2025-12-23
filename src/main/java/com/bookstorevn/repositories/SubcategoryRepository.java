package com.bookstorevn.repositories;

import com.bookstorevn.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {
    List<Subcategory> findByCategoryId(Integer categoryId);
    Optional<Subcategory> findByNameIgnoreCaseAndCategoryId(String name, Integer categoryId);
}
