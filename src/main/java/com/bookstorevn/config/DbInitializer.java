package com.bookstorevn.config;

import com.bookstorevn.models.*;
import com.bookstorevn.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

@Configuration
public class DbInitializer {

    @Bean
    public CommandLineRunner seedData(
            CategoryRepository categoryRepository,
            SubcategoryRepository subcategoryRepository,
            ProductRepository productRepository,
            ApplicationUserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("---- System checking for Seed Data ----");
            long catCount = categoryRepository.count();
            long productCount = productRepository.count();
            long userCount = userRepository.count();
            System.out.println("Current counts: Categories=" + catCount + ", Products=" + productCount + ", Users=" + userCount);

            // 1. Ensure Categories and Subcategories (append if missing)
            System.out.println("Ensuring Categories and Subcategories...");
            Category c1 = ensureCategory(categoryRepository, "Van H?c", 1);
            Category c2 = ensureCategory(categoryRepository, "Kinh T?", 2);
            Category c3 = ensureCategory(categoryRepository, "K? Nang S?ng", 3);
            Category c4 = ensureCategory(categoryRepository, "Khoa Hoc", 4);
            Category c5 = ensureCategory(categoryRepository, "Lich Su", 5);
            Category c6 = ensureCategory(categoryRepository, "Thieu Nhi", 6);

            // 2. Ensure Subcategories
            Subcategory s1 = ensureSubcategory(subcategoryRepository, "Truy?n Ng?n", c1);
            Subcategory s2 = ensureSubcategory(subcategoryRepository, "Ti?u Thuy?t", c1);
            Subcategory s3 = ensureSubcategory(subcategoryRepository, "Qu?n Tr? - Lanh D?o", c2);
            Subcategory s4 = ensureSubcategory(subcategoryRepository, "Tai Chinh Ca Nhan", c2);
            Subcategory s5 = ensureSubcategory(subcategoryRepository, "Thien Van", c4);
            Subcategory s6 = ensureSubcategory(subcategoryRepository, "Sinh Hoc", c4);
            Subcategory s7 = ensureSubcategory(subcategoryRepository, "Lich Su The Gioi", c5);
            Subcategory s8 = ensureSubcategory(subcategoryRepository, "Co Dai", c5);
            Subcategory s9 = ensureSubcategory(subcategoryRepository, "Truyen Thieu Nhi", c6);

            Subcategory novelSub = s2;
            Subcategory financeSub = s4;
            Subcategory scienceSub = s5;
            Subcategory biologySub = s6;
            Subcategory historySub = s7;
            Subcategory ancientSub = s8;
            Subcategory kidsSub = s9;
            // 3. Ensure Products (append if missing)
            System.out.println("Ensuring Products...");

            String placeholderImage = "https://placehold.co/300x400?text=Book";

            Product p1 = new Product();
            p1.setTitle("M?t Bi?c");
            p1.setAuthor("Nguy?n Nh?t Anh");
            p1.setIsbn("MB-2023-001");
            p1.setListPrice(120000.0);
            p1.setPrice(100000.0);
            p1.setStockCount(100);
            p1.setSubcategory(novelSub);
            p1.setImageUrl("https://salt.tikicdn.com/cache/w1200/ts/product/7c/49/da/d06f5223c2a041cb333501cb6a3a41e9.jpg");
            p1.setDescription("M?t t c ph?m kinh di?n c?a nh… van Nguy?n Nh?t Anh.");

            Product p2 = new Product();
            p2.setTitle("D?c Nhƒn Tƒm");
            p2.setAuthor("Dale Carnegie");
            p2.setIsbn("DNT-2023-002");
            p2.setListPrice(90000.0);
            p2.setPrice(75000.0);
            p2.setStockCount(50);
            p2.setSubcategory(financeSub);
            p2.setImageUrl("https://salt.tikicdn.com/cache/w1200/ts/product/45/3b/03/996323c28a8a4b4ee97c9b0e9d997232.jpg");
            p2.setDescription("Cu?n s ch g?i d?u giu?ng v? ngh? thu?t ?ng x?.");

            Product p3 = new Product();
            p3.setTitle("Cha Gi…u Cha NghŠo");
            p3.setAuthor("Robert Kiyosaki");
            p3.setIsbn("CGCN-2023-003");
            p3.setListPrice(180000.0);
            p3.setPrice(150000.0);
            p3.setStockCount(30);
            p3.setSubcategory(financeSub);
            p3.setImageUrl("https://salt.tikicdn.com/cache/w1200/ts/product/05/85/90/198b1e42a9756184a44b93192a6132b4.jpg");
            p3.setDescription("D?y con l…m gi…u v… tu duy t…i ch¡nh kh c bi?t.");

            Product p4 = new Product();
            p4.setTitle("T“i Th?y Hoa V…ng Trˆn C? Xanh");
            p4.setAuthor("Nguy?n Nh?t Anh");
            p4.setIsbn("TTHV-2023-004");
            p4.setListPrice(125000.0);
            p4.setPrice(110000.0);
            p4.setStockCount(75);
            p4.setSubcategory(novelSub);
            p4.setImageUrl("https://salt.tikicdn.com/cache/w1200/ts/product/91/d5/92/72886f6104c865f128be2f35759efc78.jpg");
            p4.setDescription("M?t cƒu chuy?n c?m d?ng v? tu?i tho v… tnh anh em.");

            Product p5 = new Product();
            p5.setTitle("Tu Duy Kinh Doanh");
            p5.setAuthor("Napoleon Hill");
            p5.setIsbn("TDKD-2025-005");
            p5.setListPrice(150000.0);
            p5.setPrice(125000.0);
            p5.setStockCount(60);
            p5.setSubcategory(financeSub);
            p5.setImageUrl(placeholderImage);
            p5.setDescription("Business mindset and goal setting.");

            Product p6 = new Product();
            p6.setTitle("Quan Ly Tai Chinh Ca Nhan");
            p6.setAuthor("Ramit Sethi");
            p6.setIsbn("QLTC-2025-006");
            p6.setListPrice(160000.0);
            p6.setPrice(135000.0);
            p6.setStockCount(40);
            p6.setSubcategory(financeSub);
            p6.setImageUrl(placeholderImage);
            p6.setDescription("Personal finance for daily life.");

            Product p7 = new Product();
            p7.setTitle("Hanh Trinh Ve Phuong Dong");
            p7.setAuthor("Bui Giang");
            p7.setIsbn("HTVD-2025-007");
            p7.setListPrice(140000.0);
            p7.setPrice(115000.0);
            p7.setStockCount(55);
            p7.setSubcategory(novelSub);
            p7.setImageUrl(placeholderImage);
            p7.setDescription("Classic travel and culture story.");

            Product p8 = new Product();
            p8.setTitle("Doi Ngan Dung Ngu");
            p8.setAuthor("Robin Sharma");
            p8.setIsbn("DNDN-2025-008");
            p8.setListPrice(110000.0);
            p8.setPrice(95000.0);
            p8.setStockCount(70);
            p8.setSubcategory(novelSub);
            p8.setImageUrl(placeholderImage);
            p8.setDescription("Short life, long lessons.");

            Product p9 = new Product();
            p9.setTitle("Vu Tru Khong Gian");
            p9.setAuthor("Carl Sagan");
            p9.setIsbn("VTKG-2025-009");
            p9.setListPrice(180000.0);
            p9.setPrice(150000.0);
            p9.setStockCount(35);
            p9.setSubcategory(scienceSub);
            p9.setImageUrl(placeholderImage);
            p9.setDescription("An introduction to the cosmos.");

            Product p10 = new Product();
            p10.setTitle("Sinh Hoc Co Ban");
            p10.setAuthor("Campbell");
            p10.setIsbn("SHCB-2025-010");
            p10.setListPrice(200000.0);
            p10.setPrice(170000.0);
            p10.setStockCount(25);
            p10.setSubcategory(biologySub);
            p10.setImageUrl(placeholderImage);
            p10.setDescription("Biology basics for beginners.");

            Product p11 = new Product();
            p11.setTitle("Lich Su The Gioi");
            p11.setAuthor("E H Gombrich");
            p11.setIsbn("LSTG-2025-011");
            p11.setListPrice(170000.0);
            p11.setPrice(145000.0);
            p11.setStockCount(30);
            p11.setSubcategory(historySub);
            p11.setImageUrl(placeholderImage);
            p11.setDescription("A brief history of the world.");

            Product p12 = new Product();
            p12.setTitle("Co Dai La Ma");
            p12.setAuthor("Mary Beard");
            p12.setIsbn("CDLM-2025-012");
            p12.setListPrice(175000.0);
            p12.setPrice(150000.0);
            p12.setStockCount(20);
            p12.setSubcategory(ancientSub);
            p12.setImageUrl(placeholderImage);
            p12.setDescription("Ancient Rome made accessible.");

            Product p13 = new Product();
            p13.setTitle("Truyen Thieu Nhi - Khu Vuon Bi Mat");
            p13.setAuthor("Frances H Burnett");
            p13.setIsbn("TTN-2025-013");
            p13.setListPrice(90000.0);
            p13.setPrice(75000.0);
            p13.setStockCount(80);
            p13.setSubcategory(kidsSub);
            p13.setImageUrl(placeholderImage);
            p13.setDescription("A kids story about a secret garden.");

            Product p14 = new Product();
            p14.setTitle("Kham Pha The Gioi");
            p14.setAuthor("DK");
            p14.setIsbn("KPTG-2025-014");
            p14.setListPrice(130000.0);
            p14.setPrice(110000.0);
            p14.setStockCount(65);
            p14.setSubcategory(kidsSub);
            p14.setImageUrl(placeholderImage);
            p14.setDescription("Explore the world for kids.");

            int newProducts = 0;
            if (ensureProduct(productRepository, p1)) { newProducts++; }
            if (ensureProduct(productRepository, p2)) { newProducts++; }
            if (ensureProduct(productRepository, p3)) { newProducts++; }
            if (ensureProduct(productRepository, p4)) { newProducts++; }
            if (ensureProduct(productRepository, p5)) { newProducts++; }
            if (ensureProduct(productRepository, p6)) { newProducts++; }
            if (ensureProduct(productRepository, p7)) { newProducts++; }
            if (ensureProduct(productRepository, p8)) { newProducts++; }
            if (ensureProduct(productRepository, p9)) { newProducts++; }
            if (ensureProduct(productRepository, p10)) { newProducts++; }
            if (ensureProduct(productRepository, p11)) { newProducts++; }
            if (ensureProduct(productRepository, p12)) { newProducts++; }
            if (ensureProduct(productRepository, p13)) { newProducts++; }
            if (ensureProduct(productRepository, p14)) { newProducts++; }

            if (newProducts > 0) {
                System.out.println("Seeded " + newProducts + " product(s).");
            } else {
                System.out.println("Products already present. No new products added.");
            }
            // 4. Seed Users (Admin/Customer)
            ApplicationUser existingAdmin = userRepository.findByEmail("admin@admin").orElse(null);
            if (existingAdmin == null) {
                System.out.println("Admin account missing. Seeding Admin and Customer accounts...");
                ApplicationUser admin = new ApplicationUser();
                admin.setId(UUID.randomUUID().toString());
                admin.setName("Admin Manager");
                admin.setEmail("admin@admin");
                admin.setUserName("admin@admin");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setRole("ADMIN");
                admin.setCity("Hồ Chí Minh");

                ApplicationUser customer = new ApplicationUser();
                customer.setId(UUID.randomUUID().toString());
                customer.setName("Nguyễn Văn Khách");
                customer.setEmail("customer@gmail.com");
                customer.setUserName("customer@gmail.com");
                customer.setPassword(passwordEncoder.encode("1234"));
                customer.setRole("CUSTOMER");
                customer.setCity("Hà Nội");

                userRepository.saveAll(Arrays.asList(admin, customer));
                System.out.println("Seeding of Users completed.");
            } else if (existingAdmin.getRole() == null || !existingAdmin.getRole().equals("ADMIN")) {
                existingAdmin.setRole("ADMIN");
                userRepository.save(existingAdmin);
                System.out.println("Updated Admin role to ADMIN.");
            } else if (existingAdmin.getPassword() != null && !isBcryptHash(existingAdmin.getPassword())) {
                existingAdmin.setPassword(passwordEncoder.encode(existingAdmin.getPassword()));
                userRepository.save(existingAdmin);
                System.out.println("Updated Admin password to BCrypt.");
            }
            System.out.println("---- Seed Data check finished ----");
        };
    }

    private Category ensureCategory(CategoryRepository categoryRepository, String name, int displayOrder) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(name);
                    category.setDisplayOrder(displayOrder);
                    return categoryRepository.save(category);
                });
    }

    private boolean isBcryptHash(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }

    private Subcategory ensureSubcategory(SubcategoryRepository subcategoryRepository, String name, Category category) {
        return subcategoryRepository.findByNameIgnoreCaseAndCategoryId(name, category.getId())
                .orElseGet(() -> {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setName(name);
                    subcategory.setCategory(category);
                    return subcategoryRepository.save(subcategory);
                });
    }

    private boolean ensureProduct(ProductRepository productRepository, Product product) {
        if (productRepository.findByIsbn(product.getIsbn()).isPresent()) {
            return false;
        }
        productRepository.save(product);
        return true;
    }
}




