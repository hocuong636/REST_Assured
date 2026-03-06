# Hướng dẫn Cài đặt và Viết Test với REST Assured (Từ Cơ bản đến Thực hành)

Tài liệu này được thiết kế theo dạng Multi-step (Từng bước) để bạn có thể dễ dàng copy vào Slide thuyết trình, hoặc dùng làm tài liệu phát tay cho người tham dự thực hành theo.

---

## 🛠 Phần 1: Các Công cụ Cần Chuẩn Bị (Prerequisites)
Trước khi bắt đầu, hãy đảm bảo máy tính của bạn đã cài đặt các phần mềm sau:
1. **Java Development Kit (JDK)**: Khuyên dùng JDK 11 hoặc JDK 17.
2. **IDE (Môi trường lập trình)**: IntelliJ IDEA (Community hoặc Ultimate) hoặc Eclipse.
3. **Maven**: Công cụ quản lý thư viện (Thông thường IntelliJ IDEA đã tích hợp sẵn, không cần cài rời).

---

## 🚀 Bước 1: Khởi tạo Project Maven
*Bước này giúp tạo bộ khung dự án Java để chứa mã nguồn kiểm thử.*

1. Mở IDE **IntelliJ IDEA**.
2. Nhấn vào **New Project** (Dự án mới).
3. Thiếp lập các thông số sau:
   - **Name**: `REST_Assured_Demo` (Hoặc bất cứ tên gì bạn muốn).
   - **Language**: Java.
   - **Build system**: Maven.
   - **JDK**: Chọn phiên bản JDK bạn đang có (VD: 17).
4. Nhấn nút **Create** và chờ IntelliJ tải bộ khung dự án.

---

## 📦 Bước 2: Thêm Thư viện (Dependencies) vào `pom.xml`
*Chúng ta cần dặn Maven tải thư viện REST Assured và bộ Test Framework về máy.*

1. Trong cây thư mục bên trái, mở file `pom.xml`.
2. Thêm cặp thẻ `<dependencies> ... </dependencies>` ngay phía trước thẻ đóng `</project>` (nếu chưa có).
3. Copy đoạn XML sau dán vào giữa cặp thẻ `<dependencies>`:

```xml
    <!-- 1. Thư viện chính: REST Assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>

    <!-- 2. Thư viện để chạy và quản lý test: TestNG (hoặc JUnit) -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.9.0</version>
        <scope>test</scope>
    </dependency>

    <!-- 3. Thư viện hỗ trợ đọc/ghi dữ liệu định dạng JSON (Jackson) -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.1</version>
    </dependency>
```

4. **[Cực kỳ quan trọng]** Sau khi copy, IntelliJ sẽ hiện một icon nhỏ chữ **M (Load Maven Changes)** ở góc phải màn hình. Hãy click vào đó để Maven bắt đầu tải thư viện từ internet về máy!

---

## 💻 Bước 3: Viết Kịch bản Kiểm thử Đầu Tiên (First Test Script)
*Chúng ta sẽ dùng một API công cộng (miễn phí) là `jsonplaceholder` để kiểm tra chức năng lấy thông tin Bài viết (GET).*

1. Mở rộng thư mục `src/test/java`.
2. Chuột phải vào thư mục `java` -> **New** -> **Package**. Đặt tên là: `api`.
3. Chuột phải vào package `api` vừa tạo -> **New** -> **Java Class**. Đặt tên là: `FirstApiTest`.
4. Copy đoạn code sau vào class vừa tạo:

```java
package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

// Chèn thư viện static để có thể gọi thẳng câu lệnh given(), when(), then()
import static io.restassured.RestAssured.given;

public class FirstApiTest {

    @Test
    public void testGetPostInformation() {
        // [1] KHỞI TẠO URL BASE
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // [2] GỬI REQUEST VÀ NHẬN RESPONSE VỀ
        System.out.println("Đang gửi yêu cầu GET tới API...");
        Response response = given()
                .header("Content-Type", "application/json")
            .when()
                .get("/posts/1")
            .then()
                .extract().response();

        // [3] IN KẾT QUẢ RA MÀN HÌNH (CONSOLE)
        System.out.println("Mã trạng thái (Status Code): " + response.getStatusCode());
        System.out.println("Nội dung phản hồi (Response Body): \n" + response.getBody().asPrettyString());

        // [4] KIỂM CHỨNG TỰ ĐỘNG (ASSERTIONS)
        // Kiểm tra xem mã trạng thái trả về có đúng là 200 (Thành công) hay không?
        Assert.assertEquals(response.getStatusCode(), 200, "API trả về lỗi, Status Code không phải 200!");
        
        // Kiểm tra xem ID của user trả về có đúng với bài viết số 1 hay không?
        int userId = response.jsonPath().getInt("userId");
        Assert.assertEquals(userId, 1, "ID Người dùng không khớp với dữ liệu gốc!");
    }
}
```

---

## ▶ Bước 4: Chạy Thử và Phân Tích Kết Quả
1. Trong file code `FirstApiTest`, bạn sẽ thấy một **nút Play (dấu tam giác màu xanh)** ở bên cạnh cột đánh số dòng, ngay dòng chữ `@Test`.
2. Bấm vào nút Play đó và chọn **Run 'testGetPostInformation()'**.
3. Chờ công cụ build code (vài giây).
4. Quan sát tab **Run (Console)** bật lên ở dưới màn hình.
5. **Dấu hiệu thành công:**
   - Bạn sẽ nhìn thấy Data dạng JSON được in ra.
   - Text thông báo báo xanh lá cây: `Tests passed: 1`.

---

## 🎯 Góc Tóm Tắt (Dành cho Slide Chốt)

REST Assured được viết dựa trên mô hình **BDD (Behavior-Driven Development)** bằng tiếng Anh rất sát với tư duy con người. Cấu trúc kinh điển mọi người cần nhớ:

- **GIVEN()**: Chỗ để "Chuẩn bị đồ đạc" (Bỏ URL, token đăng nhập, headers, data vào đây).
- **WHEN()**: Chỗ để "Hành động" (Gọi phương thức `GET`, `POST`, `PUT`, `DELETE` tới đường dẫn nhánh).
- **THEN()**: Chỗ để "Nghiệm thu" (Kiểm tra kết quả trả về xem có đúng như lúc Test bằng Postman hay không).
