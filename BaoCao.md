# Bao Cao Do An Mon J2EE Spring Boot

## 1. Thong tin chung
- Ten de tai: Website ban sach BookStoreVN
- Mon hoc: Lap trinh J2EE Spring Boot
- Sinh vien: [Dien ten]
- MSSV: [Dien MSSV]
- Lop: [Dien lop]
- Giang vien: [Dien ten giang vien]
- Thoi gian thuc hien: [Dien thoi gian]

## 2. Mo ta tong quan
BookStoreVN la mot website ban sach don gian, cho phep nguoi dung xem san pham, them vao gio hang, dat hang va theo doi don hang. Khu vuc quan tri (admin) cho phep quan ly danh muc, danh muc con, san pham, don hang, van chuyen va nguoi dung.

## 3. Muc tieu do an
- Ap dung mo hinh MVC trong Spring Boot.
- Su dung Spring Data JPA de thao tac CSDL.
- Quan ly dang nhap, phan quyen voi Spring Security.
- Thuc hien quy trinh dat hang co ban.
- Xay dung giao dien web than thien voi Thymeleaf va Bootstrap.

## 4. Cong nghe su dung
- Ngon ngu: Java
- Framework: Spring Boot (MVC, Security, Data JPA)
- View engine: Thymeleaf
- CSDL: MySQL/MariaDB (Wampserver)
- Frontend: HTML, CSS, JavaScript, Bootstrap
- Cong cu: Maven, Git

## 5. Kien truc he thong
Kien truc theo mo hinh MVC:
- Controller: xu ly request, dieu huong view, goi repository.
- Model: entity JPA dai dien bang du lieu.
- View: Thymeleaf templates cho giao dien.

Luu y: Du an chua tach service layer, repository duoc goi truc tiep trong controller (phu hop do an mon hoc).

## 6. Thiet ke co so du lieu
### 6.1 Danh sach bang chinh
- AspNetUsers (ApplicationUser)
  - id, name, email, phoneNumber, userName, password, role, lockoutEnd, address fields
- Categories (Category)
  - id, name, displayOrder
- Subcategories (Subcategory)
  - id, name, categoryId
- Products (Product)
  - id, title, description, isbn, author, listPrice, price, imageUrl, stockCount, publisherName, publishYear, weight, dimensions, subcategoryId
- ShoppingCarts (ShoppingCart)
  - id, productId, applicationUserId, count
- OrderHeaders (OrderHeader)
  - id, applicationUserId, orderDate, orderTotal, orderStatus, paymentStatus, shipping info, customer info
- OrderDetails (OrderDetail)
  - id, orderHeaderId, productId, count, price
- ProductReviews (ProductReview)
  - id, productId, applicationUserId, rating, comment, reviewDate

### 6.2 Quan he
- Category 1 - n Subcategory
- Subcategory 1 - n Product
- ApplicationUser 1 - n ShoppingCart
- ApplicationUser 1 - n OrderHeader
- OrderHeader 1 - n OrderDetail
- Product 1 - n OrderDetail
- Product 1 - n ProductReview

## 7. Chuc nang he thong
### 7.1 Nguoi dung (Customer)
- Xem danh sach san pham, tim chi tiet san pham.
- Them san pham vao gio hang, cap nhat so luong.
- Dat hang (nhap thong tin giao hang, thanh toan demo).
- Xem lich su don hang cua tai khoan.
- Dang ky, dang nhap, dang xuat.

### 7.2 Quan tri (Admin)
- Quan ly danh muc (Category): them, sua, xoa.
- Quan ly danh muc con (Subcategory): them, sua, xoa.
- Quan ly san pham (Product): them, sua, xoa, upload anh.
- Quan ly don hang, cap nhat van chuyen.
- Quan ly nguoi dung, khoa/mo khoa tai khoan.
- Dashboard thong ke co ban.

## 8. Luong du lieu chinh
### 8.1 Dang nhap va phan quyen
- Nguoi dung dang nhap qua /login.
- Spring Security kiem tra thong tin tai khoan.
- Tai khoan bi khoa (lockoutEnd con hieu luc) se khong duoc dang nhap.
- Vai tro ADMIN duoc phep truy cap /admin/**.

### 8.2 Dat hang
- Them san pham vao gio hang.
- Chon dat hang, nhap thong tin giao hang.
- He thong tao OrderHeader va OrderDetail, cap nhat tong tien.

### 8.3 Quan ly san pham
- Admin tao moi san pham, chon danh muc cha va danh muc con.
- Upload anh luu vao /images/product.
- Neu anh cu la URL ngoai, he thong se khong xoa file local de tranh loi.


## 10. Giao dien (UI)
Trang chinh:
- Trang chu: danh sach san pham, gia, tac gia.
- Chi tiet san pham.
- Gio hang va thanh toan.

Trang admin:
- Dashboard
- Category / Subcategory
- Product
- Order / Shipping
- User

## 11. Xu ly loi (Error Handling)
Da bo sung trang loi:
- /error (mac dinh)
- /error/404
- /error/500
Giam trang "Whitelabel Error Page" mac dinh cua Spring.

## 12. Bao mat
- Spring Security bao ve khu vuc /admin/**.
- Tai khoan bi khoa khong the dang nhap.
- Luu y: password hien dang la plain text (demo). De bao mat tot hon can dung BCrypt va bat CSRF.

## 13. Huong dan cai dat va chay
### 13.1 Yeu cau
- JDK 17+ (hoac JDK phu hop voi Spring Boot)
- Maven
- MySQL/MariaDB (Wampserver)

### 13.2 Cau hinh CSDL
File: `src/main/resources/application.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/BookStoreVN?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```

### 13.3 Chay ung dung
```
mvn spring-boot:run
```
Mo trinh duyet:
- http://localhost:8080/
- http://localhost:8080/admin (can tai khoan admin)

Tai khoan mac dinh neu chua co:
- admin@admin / 1234


## 15. Han che
- Mat khau chua ma hoa (chi de demo).
- Chua co phan quyen chi tiet theo tung chuc nang.
- Chua co thanh toan thuc (chi demo).
- Chua co test tu dong (unit/integration).

## 16. Huong phat trien
- Ma hoa mat khau bang BCrypt, bat CSRF.
- Them chuc nang tim kiem, loc san pham, phan trang.
- Mo rong quan ly kho, khuyen mai, voucher.
- Bo sung test tu dong va CI/CD.
- Toi uu giao dien, ho tro responsive day du.

## 17. Ket luan
Do an BookStoreVN da hoan thanh cac chuc nang co ban cua mot website ban sach, ap dung dung cac kien thuc J2EE/Spring Boot trong mon hoc. He thong co the mo rong de phuc vu muc dich thuc te trong tuong lai.
