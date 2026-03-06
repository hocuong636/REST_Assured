package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utils.TestConfig;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Test Class: JsonPlaceholderTest
 *
 * Mục đích: Kiểm thử các endpoint của Public API JSONPlaceholder
 * (https://jsonplaceholder.typicode.com/posts)
 *
 * Phương pháp: Sử dụng REST Assured theo phong cách BDD (given/when/then)
 * kết hợp JUnit 5 để quản lý và chạy test cases.
 *
 * Môn học: Kiểm thử và Đảm bảo Chất lượng Phần mềm.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonPlaceholderTest extends TestConfig {

    /**
     * Thiết lập cấu hình trước khi chạy tất cả test.
     * Trỏ Base URI đến JSONPlaceholder - một Public REST API miễn phí
     * chuyên dùng để luyện tập và demo kiểm thử API.
     */
    @BeforeAll
    public static void setup() {
        // Đặt Base URI cho tất cả request trong class này
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // ================================================================
    // TEST CASE 1: GET - Lấy danh sách tất cả posts
    // ================================================================
    @Test
    @Order(1)
    @DisplayName("TC01 - GET /posts - Lay danh sach posts, kiem tra status 200 va Content-Type")
    public void testGetPostsList_ShouldReturn200() {
        // MÔ TẢ: Gửi request GET đến /posts để lấy toàn bộ danh sách bài viết.
        // KỲ VỌNG:
        // - Status code trả về là 200 (OK)
        // - Header Content-Type phải chứa "application/json" (API trả về JSON)
        // - Body response phải là một mảng không rỗng (có ít nhất 1 bài viết)

        given()
                .log().method() // Log phương thức HTTP (GET)
                .log().uri() // Log đường dẫn URL đầy đủ
                .when()
                .get("/posts") // Gửi request GET đến endpoint /posts
                .then()
                .log().status() // Log status code trả về
                .statusCode(200) // Assertion 1: Kiểm tra status code = 200
                .contentType(ContentType.JSON) // Assertion 2: Kiểm tra Content-Type = application/json
                .body("$", not(empty())) // Assertion 3: Body trả về không được rỗng
                .body("size()", greaterThan(0)); // Assertion 4: Mảng có ít nhất 1 phần tử
    }

    // ================================================================
    // TEST CASE 2: GET - Lấy chi tiết 1 post theo ID
    // ================================================================
    @Test
    @Order(2)
    @DisplayName("TC02 - GET /posts/1 - Lay post id=1, kiem tra userId va id trong body")
    public void testGetPostById_ShouldReturnCorrectData() {
        // MÔ TẢ: Gửi request GET đến /posts/1 để lấy bài viết có id = 1.
        // KỲ VỌNG:
        // - Status code = 200
        // - Trường "id" trong response body phải bằng 1
        // - Trường "userId" phải bằng 1 (theo data gốc của JSONPlaceholder)
        // - Trường "title" và "body" phải tồn tại (không null)

        given()
                .log().method()
                .log().uri()
                .when()
                .get("/posts/{id}", 1) // Gửi GET request lấy post có id = 1
                .then()
                .log().body() // Log toàn bộ body response ra console
                .statusCode(200) // Assertion 1: Status code = 200
                .body("id", equalTo(1)) // Assertion 2: Trường id phải = 1
                .body("userId", equalTo(1)) // Assertion 3: Trường userId phải = 1
                .body("title", notNullValue()) // Assertion 4: Trường title không được null
                .body("body", notNullValue()); // Assertion 5: Trường body không được null
    }

    // ================================================================
    // TEST CASE 3: POST - Tạo mới một post
    // ================================================================
    @Test
    @Order(3)
    @DisplayName("TC03 - POST /posts - Tao post moi, kiem tra status 201 va du lieu tra ve")
    public void testCreateNewPost_ShouldReturn201() {
        // MÔ TẢ: Gửi request POST đến /posts với payload JSON chứa title, body, userId.
        // JSONPlaceholder sẽ "giả lập" việc tạo mới (không lưu thật vào DB).
        // KỲ VỌNG:
        // - Status code = 201 (Created)
        // - Response body chứa đúng dữ liệu đã gửi lên
        // - Response body chứa trường "id" được tự động sinh

        // Chuẩn bị payload JSON gửi lên server
        String requestBody = """
                {
                    "title": "Test Post from REST Assured",
                    "body": "This is a test post created by REST Assured for API Testing demo",
                    "userId": 99
                }
                """;

        given()
                .contentType(ContentType.JSON) // Khai báo Content-Type = JSON
                .body(requestBody) // Đính kèm payload JSON vào request body
                .log().all() // Log toàn bộ request (headers + body)
                .when()
                .post("/posts") // Gửi POST request đến /posts
                .then()
                .log().all() // Log toàn bộ response để debug
                .statusCode(201) // Assertion 1: Status code = 201 Created
                .body("title", equalTo("Test Post from REST Assured")) // Assertion 2: title khớp
                .body("body", equalTo("This is a test post created by REST Assured for API Testing demo")) // Assertion
                                                                                                           // 3: body
                                                                                                           // khớp
                .body("userId", equalTo(99)) // Assertion 4: userId khớp
                .body("id", notNullValue()); // Assertion 5: id được tự động sinh (không null)
    }

    // ================================================================
    // TEST CASE 4: GET - Lấy bài viết không tồn tại (Negative Test)
    // ================================================================
    @Test
    @Order(4)
    @DisplayName("TC04 - GET /posts/9999 - Lay post khong ton tai, kiem tra status 404")
    public void testGetNonExistentPost_ShouldReturn404() {
        // MÔ TẢ: Gửi GET request đến /posts/9999 - một ID không hề tồn tại
        // trong hệ thống JSONPlaceholder (chỉ có id từ 1 đến 100).
        // MỤC ĐÍCH: Kiểm tra rằng Public API trả về đúng mã lỗi 404 khi client
        // yêu cầu một tài nguyên không tồn tại, thay vì trả về 200
        // với dữ liệu trống hoặc null.
        // KỲ VỌNG:
        // - Status code = 404 (Not Found)
        // - Response body là object rỗng {} (đặc trưng của JSONPlaceholder)

        given()
                .log().method() // Log phương thức HTTP (GET)
                .log().uri() // Log URL đầy đủ
                .when()
                .get("/posts/{id}", 9999) // Gửi GET đến /posts/9999
                .then()
                .log().status() // Log status code
                .log().body() // Log body để thấy response rỗng
                .statusCode(404); // Assertion: Status = 404 Not Found
    }

    // ================================================================
    // TEST CASE 5: PERFORMANCE - Kiểm tra thời gian phản hồi
    // ================================================================
    @Test
    @Order(5)
    @DisplayName("TC05 - GET /posts - Kiem tra Response Time phai duoi 2 giay")
    public void testGetPostsResponseTime_ShouldBeLessThan2Seconds() {
        // MÔ TẢ: Gửi GET request đến /posts và đo thời gian phản hồi (Response Time).
        // MỤC ĐÍCH: Một API chất lượng không chỉ trả về đúng dữ liệu mà còn
        // phải phản hồi trong khoảng thời gian chấp nhận được.
        // Đây là tiêu chí quan trọng trong kiểm thử phi chức năng
        // (Non-Functional Testing) thuộc lĩnh vực Đảm bảo chất lượng.
        // KỲ VỌNG:
        // - Status code = 200 (OK)
        // - Thời gian phản hồi < 2000 milliseconds (2 giây)

        // Gửi request và extract thời gian phản hồi ra biến để hiển thị
        long responseTime = given()
                .log().method() // Log phương thức HTTP
                .log().uri() // Log URL đầy đủ
                .when()
                .get("/posts") // Gửi GET đến /posts
                .then()
                .log().status() // Log status code
                .statusCode(200) // Assertion 1: Status = 200 OK
                .extract().time(); // Extract thời gian phản hồi (ms)

        // In thời gian phản hồi ra console để trực quan khi thuyết trình
        System.out.println(">>> RESPONSE TIME: " + responseTime + " ms (Nguong cho phep: < 2000 ms)");

        // Assertion 2: Kiểm tra thời gian phản hồi < 2 giây
        org.hamcrest.MatcherAssert.assertThat(
                "Response time phai nho hon 2000ms",
                responseTime,
                lessThan(2000L));
    }
}
