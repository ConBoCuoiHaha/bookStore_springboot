package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.Category;
import com.bookstorevn.models.Subcategory;
import com.bookstorevn.models.ViewModels.SubcategoryVM;
import com.bookstorevn.repositories.CategoryRepository;
import com.bookstorevn.repositories.SubcategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/subcategory")
public class SubcategoryController {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(Model model) {
        List<Subcategory> subcategoryList = subcategoryRepository.findAll();
        model.addAttribute("subcategoryList", subcategoryList);
        return "admin/subcategory/index";
    }

    @GetMapping("/upsert")
    public String upsert(@RequestParam(value = "id", required = false) Integer id, Model model) {
        SubcategoryVM subcategoryVM = new SubcategoryVM();
        subcategoryVM.setCategoryList(categoryRepository.findAll());
        
        if (id == null || id == 0) {
            Subcategory sub = new Subcategory();
            sub.setCategory(new Category()); // Initialize category object
            subcategoryVM.setSubcategory(sub);
            model.addAttribute("subcategoryVM", subcategoryVM);
            return "admin/subcategory/upsert";
        } else {
            Subcategory subcategory = subcategoryRepository.findById(id).orElse(null);
            if (subcategory == null) return "error/404";
            subcategoryVM.setSubcategory(subcategory);
            model.addAttribute("subcategoryVM", subcategoryVM);
            return "admin/subcategory/upsert";
        }
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("subcategoryVM") SubcategoryVM subcategoryVM, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes, 
                         Model model) {
        if (result.hasErrors()) {
            subcategoryVM.setCategoryList(categoryRepository.findAll());
            return "admin/subcategory/upsert";
        }

        boolean isNew = subcategoryVM.getSubcategory().getId() == null || subcategoryVM.getSubcategory().getId() == 0;
        subcategoryRepository.save(subcategoryVM.getSubcategory());
        
        if (isNew) {
            redirectAttributes.addFlashAttribute("success", "Đã tạo danh mục con thành công");
        } else {
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật danh mục con thành công");
        }
        
        return "redirect:/admin/subcategory";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        if (id == null || id == 0) return "error/404";
        Subcategory subcategory = subcategoryRepository.findById(id).orElse(null);
        if (subcategory == null) return "error/404";
        model.addAttribute("subcategory", subcategory);
        return "admin/subcategory/delete";
    }

    @PostMapping("/delete")
    public String deletePOST(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElse(null);
        if (subcategory == null) return "error/404";
        subcategoryRepository.delete(subcategory);
        redirectAttributes.addFlashAttribute("success", "Đã xóa danh mục con thành công");
        return "redirect:/admin/subcategory";
    }
}
