# Kế Hoạch & Prompt Cho Copilot: Dự án Demo REST Assured

Dự án môn học: Kiểm thử và Đảm bảo Chất lượng Phần mềm.
Mục tiêu: Xây dựng dự án tự động hoá kiểm thử API sử dụng Java, Maven, REST Assured.

## 1. Lựa chọn Công nghệ & Framework
* **Ngôn ngữ:** Java (phiên bản 11 hoặc 17).
* **Quản lý dependency:** Maven.
* **Công cụ test API:** REST Assured.
* **Framework Test:** **JUnit 5 (Jupiter)**. 
  *(Lý do: Đây là nền tảng test tiêu chuẩn, hiện đại nhất của Java hiện nay. Việc thiết lập, khởi chạy và đọc report rất tường minh, cực kỳ phù hợp cho trình bày demo chuyên nghiệp).*
* **Đối tượng Kiểm thử:**
  1. **Public API:** `JSONPlaceholder` (https://jsonplaceholder.typicode.com).
  2. **Mock API:** Sử dụng công cụ **`json-server`** (NodeJS).
  *(Lý do: json-server cho phép bóc tách hoàn toàn phần backend. Bạn chỉ cần 1 file `db.json` đơn giản là đã có một hệ thống API RESTful hoàn chỉnh hỗ trợ đủ GET, POST, PUT, PATCH, DELETE. Nó chạy siêu nhanh tại localhost, rất dễ để show cho hội đồng lúc thuyết trình).*

---

## 2. Hướng dẫn sử dụng file này
Bạn hãy mở project `d:\code\demo\REST_Assured` trên IDE của mình (IntelliJ hoặc VSCode), bật Copilot Chat lên và copy từng khối `<PROMPT>` dưới đây dán vào để Copilot tự động gen code và tạo file cho bạn.

---

### BƯỚC 1: Khởi tạo Project Maven
**<PROMPT 1>**
```text
Tôi đang làm dự án môn học Kiểm thử phần mềm. Hãy giúp tôi tạo nội dung cho file `pom.xml`.
Yêu cầu:
- Java 17.
- Khai báo các dependency mới nhất cho: REST Assured, JUnit 5 (junit-jupiter-api và junit-jupiter-engine), và Jackson (jackson-databind) để xử lý JSON.
- Thêm maven-surefire-plugin vào phần build để chạy test JUnit 5.
Hãy viết mã pom.xml hoàn chỉnh.
```

---

### BƯỚC 2: Setup Mock API với Json-Server và Tài Liệu API
**<PROMPT 2>**
```text
Tôi cần tạo một Mock API bằng `json-server` quản lý danh sách "Sản phẩm" (Products).
1. Hãy sinh cho tôi đoạn code JSON chứa data mẫu để tôi bỏ vào file `db.json` (khoảng 3 sản phẩm, mỗi sản phẩm có id, name, price, category).
2. Viết ngay bên dưới một Tài Liệu API (API Documentation) dưới dạng Markdown mô tả cấu trúc các endpoint mà json-server sẽ gen ra từ db.json này. 
Tài liệu cần bao gồm:
- URL base (mặc định là http://localhost:3000)
- Các endpoint chi tiết cho GET (lấy danh sách, lấy 1), POST (tạo mới), PUT (cập nhật toàn bộ), DELETE (xóa).
- Với mỗi endpoint, ghi rõ: Ý nghĩa, Body gửi lên (nếu có), và Output mong đợi (HTTP Status Code và định dạng trả về).
```

---

### BƯỚC 3: Viết Code Setup & Utilities chung
**<PROMPT 3>**
```text
Hãy tạo một class `TestConfig` trong thư mục `src/test/java/utils/`. 
Yêu cầu:
- Class này chứa các setup chung cấu hình base URI, base Path cho REST Assured bằng cách sử dụng `@BeforeAll` của JUnit 5.
- Viết code thật Clean, có comment tiếng Việt giải thích rõ ràng từng dòng cấu hình nhằm mục đích giáo dục cho môn học Kiểm thử phần mềm.
```

---

### BƯỚC 4: Viết Test Case cho Public API (JSONPlaceholder)
**<PROMPT 4>**
```text
Hãy viết Test Class `JsonPlaceholderTest` trong thư mục `src/test/java/api/` để test public API: https://jsonplaceholder.typicode.com/posts.
Sử dụng JUnit 5 và REST Assured. 
Yêu cầu tối thiểu các test cases sau:
1. GET: Lấy danh sách posts và verify status code 200, verify header trả về "application/json".
2. GET: Lấy post có id = 1 và verify trường `userId` và `id` trong body.
3. POST: Tạo 1 post mới với title, body, userId. Verify status code 201 và response body chứa dữ liệu vừa tạo.
4. Yêu cầu: Đặt tên method test rõ nghĩa (vd: testGetPostsList_ShouldReturn200). 
Code phải thật sự Clean, áp dụng BDD format của REST Assured (given/when/then) và có comment tiếng Việt giải thích ý nghĩa các assertion cho môn Kiểm thử.
```

---

### BƯỚC 5: Viết Test Case cho Mock API (Localhost)
**<PROMPT 5>**
```text
Hãy viết Test Class `MockApiProductTest` trong thư mục `src/test/java/api/` để test Mock API quản lý Products đang chạy ở `http://localhost:3000/products`.
Sử dụng JUnit 5 và REST Assured.
Yêu cầu viết 4 test cases chi tiết cho đủ 4 method:
1. GET: `testGetAllProducts` - Kiểm tra status code là 200 và response có chứa danh sách item.
2. POST: `testCreateNewProduct` - POST một san_pham mới dạng JSON, kiểm tra status 201 created và response trả về chứa `id` tự sinh.
3. PUT: `testUpdateProduct` - Cập nhật thông tin của product có id=1. Kiểm tra status 200 và dữ liệu mới được lưu.
4. DELETE: `testDeleteProduct` - Xóa product có id=2. Kiểm tra status 200 (hoặc 204), sau đó GET lại id=2 và verify nhận được status 404 (Not Found).
Hãy viết theo chuẩn BDD (given/when/then), trình bày chuỗi method chaining gọn gàng, đẹp mắt.
Mỗi test cần có comment giải thích các bước setup dữ liệu gửi đi (Payload) và cách verify kết quả (Assertions).
```

---
### Lời khuyên khi Demo:
1. **Mở sẵn Terminal 1**: Chạy lệnh `npx json-server --watch db.json` để Mock API luôn chạy nền. Để lúc thuyết trình ai cũng có thể thấy Console báo HTTP Logs sinh động.
2. **Mở sẵn Terminal 2**: Chạy lệnh `mvn clean test` hoặc ấn nút "Run All Tests" trên IDE. 
3. Chỉ vào console log của REST Assured (được in ra thông qua phương thức `.log().all()` trong Copilot Prompt) để giảng viên thấy chi tiết Request Header, Body gửi đi và Response Header, Body nhận về. Đoạn này là điểm cộng lớn nhất của việc dùng REST Assured.
