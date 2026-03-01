# 🧪 REST Assured - Demo Kiểm Thử API Tự Động

> **Môn học:** Kiểm thử và Đảm bảo Chất lượng Phần mềm  
> **Mục tiêu:** Xây dựng dự án tự động hoá kiểm thử API sử dụng Java, Maven, REST Assured

---

## 📋 Mục Lục

- [Giới thiệu](#-giới-thiệu)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Hướng dẫn cài đặt](#-hướng-dẫn-cài-đặt)
- [Hướng dẫn chạy test](#-hướng-dẫn-chạy-test)
- [Mô tả Test Cases](#-mô-tả-test-cases)
- [API Documentation](#-api-documentation)

---

## 📖 Giới Thiệu

Dự án demo kiểm thử API tự động sử dụng **REST Assured** — một thư viện Java phổ biến cho phép viết test case theo phong cách **BDD (Behavior Driven Development)** với cú pháp `given() / when() / then()`.

Dự án kiểm thử **2 loại API**:

| Loại API | Mô tả | URL |
|----------|--------|-----|
| **Public API** | JSONPlaceholder — REST API miễn phí để test | `https://jsonplaceholder.typicode.com` |
| **Mock API** | json-server — Mock server chạy local | `http://localhost:3000` |

---

## 🛠 Công Nghệ Sử Dụng

| Công nghệ | Phiên bản | Mục đích |
|------------|-----------|----------|
| **Java** | 17 | Ngôn ngữ lập trình chính |
| **Maven** | 3.x | Quản lý dependency & build |
| **REST Assured** | 5.4.0 | Thư viện kiểm thử API (BDD style) |
| **JUnit 5 (Jupiter)** | 5.10.2 | Framework chạy & quản lý test cases |
| **Jackson** | 2.17.0 | Xử lý JSON serialization/deserialization |
| **Maven Surefire Plugin** | 3.2.5 | Plugin chạy unit test, hỗ trợ JUnit 5 & UTF-8 |
| **json-server** | latest | Mock API server (Node.js) |

---

## 📁 Cấu Trúc Dự Án

```
REST_Assured/
├── pom.xml                          # Cấu hình Maven & dependencies
├── db.json                          # Dữ liệu mock cho json-server (3 sản phẩm mẫu)
├── API_DOCUMENTATION.md             # Tài liệu API chi tiết
├── README.md                        # File hướng dẫn (file này)
│
└── src/
    └── test/
        └── java/
            ├── utils/
            │   └── TestConfig.java              # ⚙️ Lớp cấu hình chung (UTF-8, logging)
            └── api/
                ├── JsonPlaceholderTest.java      # 🌐 Test Public API (3 test cases) — extends TestConfig
                └── MockApiProductTest.java       # 🏠 Test Mock API (4 test cases)  — extends TestConfig
```

> **Lưu ý:** Dự án chỉ chứa mã kiểm thử (`src/test/`), không có mã nguồn ứng dụng (`src/main/`) vì đây là dự án kiểm thử API thuần túy.

### Kiến trúc kế thừa (Inheritance)

```
TestConfig (Lớp cha — cấu hình chung UTF-8, logging)
  ├── JsonPlaceholderTest (extends TestConfig — set baseURI = jsonplaceholder)
  └── MockApiProductTest (extends TestConfig — set baseURI = localhost:3000)
```

---

## 💻 Yêu Cầu Hệ Thống

- **Java JDK 17** trở lên
- **Apache Maven 3.x**
- **Node.js** (để chạy json-server)

Kiểm tra phiên bản đã cài:

```bash
java -version
mvn -version
node -v
```

---

## 🚀 Hướng Dẫn Cài Đặt

### 1. Clone dự án

```bash
git clone https://github.com/hocuong636/REST_Assured.git
cd REST_Assured
```

### 2. Cài đặt dependencies Maven

```bash
mvn clean install -DskipTests
```

### 3. Cài đặt json-server (nếu chưa có)

```bash
npm install -g json-server
```

---

## ▶️ Hướng Dẫn Chạy Test

### Bước 1: Khởi động Mock API Server

Mở **Terminal 1** và chạy:

```bash
npx json-server --watch db.json
```

> Server sẽ chạy tại: `http://localhost:3000`

### Bước 2: Chạy Test

Mở **Terminal 2** và chạy:

```bash
# Chạy toàn bộ test (7 test cases)
mvn clean test

# Chạy riêng test Public API (JSONPlaceholder)
mvn test "-Dtest=api.JsonPlaceholderTest"

# Chạy riêng test Mock API (localhost)
mvn test "-Dtest=api.MockApiProductTest"
```

### Kết quả mong đợi

```
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 📝 Mô Tả Test Cases

### 1. JsonPlaceholderTest — Public API (3 test cases)

| # | Test Case | Method | Endpoint | Kiểm tra |
|---|-----------|--------|----------|----------|
| TC01 | `testGetPostsList_ShouldReturn200` | GET | `/posts` | Status 200, Content-Type JSON, danh sách không rỗng |
| TC02 | `testGetPostById_ShouldReturnCorrectData` | GET | `/posts/1` | Status 200, kiểm tra `userId`, `id`, `title`, `body` |
| TC03 | `testCreateNewPost_ShouldReturn201` | POST | `/posts` | Status 201, response chứa dữ liệu gửi lên + `id` tự sinh |

### 2. MockApiProductTest — Mock API (4 test cases)

| # | Test Case | Method | Endpoint | Kiểm tra |
|---|-----------|--------|----------|----------|
| TC01 | `testGetAllProducts` | GET | `/products` | Status 200, response là mảng JSON không rỗng |
| TC02 | `testCreateNewProduct` | POST | `/products` | Status 201, `id` tự sinh, dữ liệu khớp payload |
| TC03 | `testUpdateProduct` | PUT | `/products/1` | Status 200, dữ liệu được cập nhật đúng |
| TC04 | `testDeleteProduct` | DELETE | `/products/:id` | Status 200 khi xóa, GET lại trả về 404 |

---

## 📚 API Documentation

Xem chi tiết tài liệu API Mock Server tại file: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

### Tóm tắt Endpoints

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/products` | Lấy danh sách tất cả sản phẩm |
| GET | `/products/:id` | Lấy thông tin 1 sản phẩm theo ID |
| POST | `/products` | Tạo mới một sản phẩm |
| PUT | `/products/:id` | Cập nhật toàn bộ thông tin sản phẩm |
| DELETE | `/products/:id` | Xóa một sản phẩm |

---

## 🔑 Điểm Nổi Bật Khi Demo

1. **BDD Style** — Code test viết theo chuẩn `given() / when() / then()`, dễ đọc như văn bản tự nhiên.
2. **Kế thừa TestConfig** — Cấu hình chung (UTF-8, logging) tập trung ở lớp cha `TestConfig`, các test class kế thừa để tái sử dụng, tránh lặp code.
3. **Path Parameters** — Sử dụng `.get("/posts/{id}", 1)` thay vì nối chuỗi, giúp code sạch và chuẩn REST Assured.
4. **Log chi tiết** — Sử dụng `.log().all()` để hiển thị đầy đủ Request Headers, Body gửi đi và Response Headers, Body nhận về ngay trên console.
5. **CRUD đầy đủ** — Cover đủ 4 HTTP methods: GET, POST, PUT, DELETE.
6. **2 loại API** — Test cả Public API (JSONPlaceholder) lẫn Mock API (json-server local).
7. **Assertions đa dạng** — Kiểm tra status code, content-type, JSON body fields, null check.

---

## 👥 Tác Giả

- Dự án môn học: **Kiểm thử và Đảm bảo Chất lượng Phần mềm**

