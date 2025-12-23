package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.Category;
import com.bookstorevn.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
//@PreAuthorize("hasRole('ADMIN')") // Will configure roles in SecurityConfig later
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "admin/category/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/category/create";
        }
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("success", "Đã tạo thể loại thành công");
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        if (id == null || id == 0) return "error/404";
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return "error/404";
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/category/edit";
        }
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật thể loại thành công");
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        if (id == null || id == 0) return "error/404";
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return "error/404";
        model.addAttribute("category", category);
        return "admin/category/delete";
    }

    @PostMapping("/delete")
    public String deletePOST(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return "error/404";
        categoryRepository.delete(category);
        redirectAttributes.addFlashAttribute("success", "Đã xóa thể loại thành công");
        return "redirect:/admin/category";
    }
}
