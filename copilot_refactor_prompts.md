# Hướng dẫn Copilot Cải thiện và Refactor Code (REST Assured Project)

Dự án hiện tại đã hoạt động khá tốt, nhưng cần được dọn dẹp và tối ưu lại theo chuẩn best practices của một dự án Automation Test thực thụ.

Hãy dùng Copilot và yêu cầu nó thực hiện lần lượt các bước sau:

***

### Bước 1: Dọn dẹp cấu trúc thư mục dư thừa
**<PROMPT 1>**
```text
Dự án này chỉ tập trung vào kiểm thử API (API Testing). Thư mục `src/main` không cần thiết và gây rác dự án.
1. Hãy tìm và dọn dẹp, xóa hoàn toàn thư mục `src/main/`.
2. Đảm bảo toàn bộ code kiểm thử chỉ nằm trong `src/test/`.
```

***

### Bước 2: Tối ưu hoá file `TestConfig.java` (Sử dụng tính Kế thừa)
**<PROMPT 2>**
```text
Tôi muốn tối ưu hóa `src/test/java/utils/TestConfig.java` để làm lớp cấu hình cha cho toàn bộ bài test.
Yêu cầu:
1. Sửa lớp `TestConfig` để chỉ chứa phần cấu hình chung cho REST Assured (như `EncoderConfig`, `DecoderConfig` sang UTF-8 và `enableLoggingOfRequestAndResponseIfValidationFails`).
2. XÓA dòng cứng `RestAssured.baseURI = "http://localhost:3000";` trong file này vì `baseURI` sẽ được định nghĩa riêng ở từng lớp con.
```

***

### Bước 3: Áp dụng TestConfig vào `JsonPlaceholderTest.java` và sử dụng Path Parameters
**<PROMPT 3>**
```text
Refactor file `src/test/java/api/JsonPlaceholderTest.java`.
Yêu cầu:
1. Cho class này kế thừa (`extends`) từ `utils.TestConfig`.
2. Trong hàm `@BeforeAll setup()`, xóa phần cấu hình UTF-8 (vì đã được `TestConfig` lo), chỉ giữ lại phần gán `RestAssured.baseURI = "https://jsonplaceholder.typicode.com";`.
3. Trong test case `testGetPostById_ShouldReturnCorrectData` (GET /posts/1), hãy thay đổi cách truyền ID. Thay vì hardcode trực tiếp vào chuỗi URL `get("/posts/1")`, hãy sử dụng Path Parameters của REST Assured: `.get("/posts/{id}", 1)`.
4. Đảm bảo toàn bộ các assertions vẫn được giữ nguyên.
```

***

### Bước 4: Áp dụng TestConfig vào `MockApiProductTest.java` và sử dụng Path Parameters
**<PROMPT 4>**
```text
Refactor file `src/test/java/api/MockApiProductTest.java`.
Yêu cầu:
1. Cho class này kế thừa (`extends`) từ `utils.TestConfig`.
2. Trong hàm `@BeforeAll setup()`, xóa đoạn cấu hình UTF-8, chỉ giữ lại `RestAssured.baseURI = "http://localhost:3000";`.
3. Trong các test case có truyền biến động (`testUpdateProduct` và `testDeleteProduct`), hãy thay đổi cách nối chuỗi (ví dụ: `put("/products/1")` hay `delete("/products/" + createdId)`). 
-> Yêu cầu dùng Path Parameter của REST Assured (ví dụ: `.put("/products/{id}", 1)` hoặc `.delete("/products/{id}", createdId)`). Tương tự cho hàm GET xác minh cuối test delete.
4. Đảm bảo các assertions không thay đổi nhưng code sạch sẽ hơn.
```

***

### Bước 5: Giải quyết đồng bộ kiểu dữ liệu 'id' trong Database Mock
**<PROMPT 5>**
```text
Cấu trúc sinh `id` của json-server v1+ với dữ liệu mặc định đang bị bất đồng bộ.
Hãy chỉnh sửa file `db.json`. Đổi toàn bộ các giá trị `"id"` của các items hiện tại thành kiểu chuỗi (String) hoàn toàn để tránh xung đột kiểu.

Ví dụ, đảm bảo dữ liệu hiển thị như sau:
"id": "1",
"id": "2",
"id": "3"
...
```
