package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.Category;
import com.bookstorevn.models.Product;
import com.bookstorevn.models.Subcategory;
import com.bookstorevn.models.ViewModels.ProductVM;
import com.bookstorevn.repositories.CategoryRepository;
import com.bookstorevn.repositories.ProductRepository;
import com.bookstorevn.repositories.SubcategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Value("${upload.path:src/main/resources/static/images/product}")
    private String uploadPath;

    @GetMapping("")
    public String index(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "admin/product/index";
    }

    @GetMapping("/upsert")
    public String upsert(@RequestParam(value = "id", required = false) Integer id, Model model) {
        ProductVM productVM = new ProductVM();
        productVM.setCategoryList(categoryRepository.findAll());
        
        if (id == null || id == 0) {
            // Create
            Product product = new Product();
            Subcategory subcategory = new Subcategory();
            subcategory.setCategory(new Category()); // Initialize Category in Subcategory
            product.setSubcategory(subcategory); // Initialize Subcategory in Product
            productVM.setProduct(product);
            model.addAttribute("productVM", productVM);
            return "admin/product/upsert";
        } else {
            // Update
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) return "error/404";
            productVM.setProduct(product);
            productVM.setSelectedCategoryId(product.getSubcategory().getCategory().getId());
            productVM.setSubcategoryList(subcategoryRepository.findByCategoryId(productVM.getSelectedCategoryId()));
            model.addAttribute("productVM", productVM);
            return "admin/product/upsert";
        }
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("productVM") ProductVM productVM, 
                         BindingResult result, 
                         @RequestParam("file") MultipartFile file, 
                         RedirectAttributes redirectAttributes, 
                         Model model) throws IOException {
        
        if (result.hasErrors()) {
            productVM.setCategoryList(categoryRepository.findAll());
            if (productVM.getSelectedCategoryId() != null && productVM.getSelectedCategoryId() > 0) {
                productVM.setSubcategoryList(subcategoryRepository.findByCategoryId(productVM.getSelectedCategoryId()));
            }
            return "admin/product/upsert";
        }

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path)) Files.createDirectories(path);

            if (productVM.getProduct().getImageUrl() != null && !productVM.getProduct().getImageUrl().isEmpty()) {
                String imageUrl = productVM.getProduct().getImageUrl();
                if (isLocalImagePath(imageUrl)) {
                    Path oldPath = Paths.get("src/main/resources/static" + imageUrl);
                    if (Files.exists(oldPath) && !Files.isDirectory(oldPath)) {
                        Files.deleteIfExists(oldPath);
                    }
                }
            }

            Files.copy(file.getInputStream(), path.resolve(fileName));
            productVM.getProduct().setImageUrl("/images/product/" + fileName);
        }

        productRepository.save(productVM.getProduct());
        redirectAttributes.addFlashAttribute("success", "Đã lưu sản phẩm thành công");
        return "redirect:/admin/product";
    }

    @GetMapping("/getsubcategories")
    @ResponseBody
    public List<Subcategory> getSubcategories(@RequestParam("categoryId") Integer categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }


    private boolean isLocalImagePath(String imageUrl) {
        return imageUrl.startsWith("/images/");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) throws IOException {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return "error";

        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            String imageUrl = product.getImageUrl();
            if (isLocalImagePath(imageUrl)) {
                Path oldPath = Paths.get("src/main/resources/static" + imageUrl);
                if (Files.exists(oldPath) && !Files.isDirectory(oldPath)) {
                    Files.deleteIfExists(oldPath);
                }
            }
        }

        productRepository.delete(product);
        return "success";
    }
}


