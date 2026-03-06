package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utils.TestConfig;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Test Class: MockApiProductTest
 *
 * Mục đích: Kiểm thử CRUD API quản lý sản phẩm (Products) chạy trên Mock
 * Server.
 * Mock Server sử dụng json-server với file db.json.
 *
 * Lưu ý quan trọng:
 * - Trước khi chạy test, cần khởi động json-server:
 * npx json-server --watch db.json
 * - Server mặc định chạy tại: http://localhost:3000
 *
 * Phương pháp: REST Assured + JUnit 5, phong cách BDD (given/when/then).
 * Thứ tự chạy: Các test case được đánh số thứ tự (@Order) để đảm bảo
 * dữ liệu nhất quán khi chạy tuần tự.
 *
 * Môn học: Kiểm thử và Đảm bảo Chất lượng Phần mềm.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MockApiProductTest extends TestConfig {

    /**
     * Thiết lập Base URI trỏ đến Mock API trên localhost.
     * Chạy 1 lần duy nhất trước tất cả test cases.
     */
    @BeforeAll
    public static void setup() {
        // Cấu hình Base URI cho json-server chạy local
        RestAssured.baseURI = "http://localhost:3000";
    }

    // ================================================================
    // TEST CASE 1: GET - Lấy danh sách tất cả sản phẩm
    // ================================================================
    @Test
    @Order(1)
    @DisplayName("TC01 - GET /products - Lay danh sach san pham, kiem tra status 200")
    public void testGetAllProducts() {
        // MÔ TẢ: Gửi GET request đến /products để lấy toàn bộ danh sách sản phẩm.
        // KỲ VỌNG:
        // - Status code = 200 (OK)
        // - Response body là mảng JSON không rỗng
        // - Mảng có ít nhất 1 sản phẩm (dữ liệu từ db.json)

        given()
                .log().method() // Log phương thức HTTP
                .log().uri() // Log URL đầy đủ
                .when()
                .get("/products") // Gửi GET đến /products
                .then()
                .log().body() // Log body response
                .statusCode(200) // Assertion 1: Status = 200 OK
                .contentType(ContentType.JSON) // Assertion 2: Response là JSON
                .body("$", not(empty())) // Assertion 3: Mảng không rỗng
                .body("size()", greaterThanOrEqualTo(1)) // Assertion 4: Có ít nhất 1 item
                .body("[0].name", notNullValue()); // Assertion 5: Sản phẩm đầu tiên có trường name
    }

    // ================================================================
    // TEST CASE 2: POST - Tạo mới một sản phẩm
    // ================================================================
    @Test
    @Order(2)
    @DisplayName("TC02 - POST /products - Tao san pham moi, kiem tra status 201 va id tu sinh")
    public void testCreateNewProduct() {
        // MÔ TẢ: Gửi POST request với payload JSON chứa thông tin sản phẩm mới.
        // json-server sẽ tự động sinh trường "id" và lưu vào db.json.
        // KỲ VỌNG:
        // - Status code = 201 (Created)
        // - Response body chứa đúng dữ liệu đã gửi
        // - Trường "id" được tự động sinh (không null)

        // Payload: Dữ liệu sản phẩm mới gửi lên server
        String newProduct = """
                {
                    "name": "Keychron K8 Mechanical Keyboard",
                    "price": 2500000,
                    "category": "Accessories"
                }
                """;

        given()
                .contentType(ContentType.JSON) // Header: Content-Type = application/json
                .body(newProduct) // Đính kèm payload JSON
                .log().all() // Log toàn bộ request (để demo)
                .when()
                .post("/products") // Gửi POST đến /products
                .then()
                .log().all() // Log toàn bộ response (để demo)
                .statusCode(201) // Assertion 1: Status = 201 Created
                .body("id", notNullValue()) // Assertion 2: id tự sinh không null
                .body("name", equalTo("Keychron K8 Mechanical Keyboard")) // Assertion 3: name khớp
                .body("price", equalTo(2500000)) // Assertion 4: price khớp
                .body("category", equalTo("Accessories")); // Assertion 5: category khớp
    }

    // ================================================================
    // TEST CASE 3: PUT - Cập nhật toàn bộ thông tin sản phẩm
    // ================================================================
    @Test
    @Order(3)
    @DisplayName("TC03 - PUT /products/1 - Cap nhat san pham id=1, kiem tra status 200")
    public void testUpdateProduct() {
        // MÔ TẢ: Gửi PUT request đến /products/1 để thay thế toàn bộ dữ liệu
        // của sản phẩm có id = 1 bằng dữ liệu mới.
        // KỲ VỌNG:
        // - Status code = 200 (OK)
        // - Response body chứa dữ liệu mới đã cập nhật
        // - Trường "id" vẫn giữ nguyên = 1

        // Payload: Dữ liệu mới thay thế hoàn toàn cho sản phẩm id=1
        String updatedProduct = """
                {
                    "name": "Laptop Dell XPS 15 (2024 Edition)",
                    "price": 35000000,
                    "category": "Electronics"
                }
                """;

        given()
                .contentType(ContentType.JSON) // Header: gửi dữ liệu dạng JSON
                .body(updatedProduct) // Đính kèm payload cập nhật
                .log().all() // Log request để debug
                .when()
                .put("/products/{id}", 1) // Gửi PUT đến /products/{id}
                .then()
                .log().all() // Log response để debug
                .statusCode(200) // Assertion 1: Status = 200 OK
                .body("id", equalTo("1")) // Assertion 2: id vẫn = "1" (json-server trả id dạng String)
                .body("name", equalTo("Laptop Dell XPS 15 (2024 Edition)")) // Assertion 3: name đã cập nhật
                .body("price", equalTo(35000000)) // Assertion 4: price đã cập nhật
                .body("category", equalTo("Electronics")); // Assertion 5: category đã cập nhật
    }

    // ================================================================
    // TEST CASE 4: DELETE - Xóa một sản phẩm
    // ================================================================
    @Test
    @Order(4)
    @DisplayName("TC04 - DELETE /products/:id - Xoa san pham, sau do verify 404")
    public void testDeleteProduct() {
        // MÔ TẢ: Thực hiện 3 bước:
        // Bước 1: Tạo một sản phẩm mới (POST) để có dữ liệu chắc chắn tồn tại.
        // Bước 2: Gửi DELETE request để xóa sản phẩm vừa tạo.
        // Bước 3: Gửi GET lại để xác nhận sản phẩm đã bị xóa (404).

        // -------- BƯỚC 1: Tạo sản phẩm tạm để xóa --------
        String tempProduct = """
                {
                    "name": "Temp Product For Delete",
                    "price": 100000,
                    "category": "Temp"
                }
                """;

        // POST tạo mới và lấy id tự sinh từ response
        String createdId = given()
                .contentType(ContentType.JSON)
                .body(tempProduct)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract().path("id"); // Lấy id tự sinh từ json-server

        // -------- BƯỚC 2: Xóa sản phẩm vừa tạo --------
        given()
                .log().method()
                .log().uri()
                .when()
                .delete("/products/{id}", createdId) // Gửi DELETE đến /products/{id}
                .then()
                .log().status() // Log status code
                .statusCode(200); // Assertion 1: Xóa thành công, status = 200

        // -------- BƯỚC 3: Verify sản phẩm đã bị xóa --------
        // Gửi GET lại để kiểm tra xem sản phẩm còn tồn tại không
        given()
                .log().method()
                .log().uri()
                .when()
                .get("/products/{id}", createdId) // Gửi GET đến /products/{id}
                .then()
                .log().status() // Log status code
                .statusCode(404); // Assertion 2: Sản phẩm không còn, status = 404
    }

    // ================================================================
    // TEST CASE 5: GET - Lấy sản phẩm không tồn tại (Negative Test)
    // ================================================================
    @Test
    @Order(5)
    @DisplayName("TC05 - GET /products/9999 - Lay san pham khong ton tai, kiem tra status 404")
    public void testGetNonExistentProduct_ShouldReturn404() {
        // MÔ TẢ: Gửi GET request đến /products/9999 - một ID không hề tồn tại
        // trong cơ sở dữ liệu (db.json).
        // MỤC ĐÍCH: Kiểm tra rằng server trả về lỗi 404 khi client yêu cầu
        // tài nguyên không tồn tại, thay vì trả về 200 với dữ liệu rỗng.
        // KỲ VỌNG:
        // - Status code = 404 (Not Found)

        given()
                .log().method() // Log phương thức HTTP
                .log().uri() // Log URL đầy đủ
                .when()
                .get("/products/{id}", 9999) // Gửi GET đến /products/9999
                .then()
                .log().status() // Log status code
                .log().body() // Log body để thấy response rỗng
                .statusCode(404); // Assertion: Status = 404 Not Found
    }

    // ================================================================
    // TEST CASE 6: DELETE - Xóa sản phẩm không tồn tại (Negative Test)
    // ================================================================
    @Test
    @Order(6)
    @DisplayName("TC06 - DELETE /products/9999 - Xoa san pham khong ton tai, kiem tra status 404")
    public void testDeleteNonExistentProduct_ShouldReturn404() {
        // MÔ TẢ: Gửi DELETE request đến /products/9999 - ID không tồn tại.
        // MỤC ĐÍCH: Kiểm tra rằng server phản hồi đúng lỗi khi client cố xóa
        // một tài nguyên không có trong hệ thống.
        // Đây là kiểm thử quan trọng để đảm bảo API không trả về
        // status 200 sai lệch khi thao tác thất bại.
        // KỲ VỌNG:
        // - Status code = 404 (Not Found)

        given()
                .log().method() // Log phương thức HTTP
                .log().uri() // Log URL đầy đủ
                .when()
                .delete("/products/{id}", 9999) // Gửi DELETE đến /products/9999
                .then()
                .log().status() // Log status code
                .log().body() // Log body để thấy response
                .statusCode(404); // Assertion: Status = 404 Not Found
    }

    // ================================================================
    // TEST CASE 7: PERFORMANCE - Kiểm tra thời gian phản hồi
    // ================================================================
    @Test
    @Order(7)
    @DisplayName("TC07 - GET /products - Kiem tra Response Time phai duoi 1 giay")
    public void testGetProductsResponseTime_ShouldBeLessThan1Second() {
        // MÔ TẢ: Gửi GET request đến /products và đo thời gian phản hồi.
        // MỤC ĐÍCH: Mock Server chạy trên localhost nên tốc độ phải rất nhanh.
        // Ta đặt ngưỡng khắt khe hơn (1 giây) so với Public API (2 giây)
        // để thể hiện rằng ngưỡng thời gian phản hồi (SLA) có thể
        // được điều chỉnh tùy theo môi trường và yêu cầu hệ thống.
        // KỲ VỌNG:
        // - Status code = 200 (OK)
        // - Thời gian phản hồi < 1000 milliseconds (1 giây)

        // Gửi request và extract thời gian phản hồi ra biến để hiển thị
        long responseTime = given()
                .log().method() // Log phương thức HTTP
                .log().uri() // Log URL đầy đủ
                .when()
                .get("/products") // Gửi GET đến /products
                .then()
                .log().status() // Log status code
                .statusCode(200) // Assertion 1: Status = 200 OK
                .extract().time(); // Extract thời gian phản hồi (ms)

        // In thời gian phản hồi ra console để trực quan khi thuyết trình
        System.out.println(">>> RESPONSE TIME: " + responseTime + " ms (Nguong cho phep: < 1000 ms)");

        // Assertion 2: Kiểm tra thời gian phản hồi < 1 giây
        org.hamcrest.MatcherAssert.assertThat(
                "Response time phai nho hon 1000ms",
                responseTime,
                lessThan(1000L));
    }
}
