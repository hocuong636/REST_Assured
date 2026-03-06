package utils;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.DecoderConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.*;

/**
 * Lớp cấu hình chung (TestConfig) cho toàn bộ dự án kiểm thử API.
 *
 * Mục đích:
 * - Thiết lập các thông số cấu hình mặc định cho REST Assured.
 * - Tự động in banner tiêu đề và kết quả (PASSED/FAILED) cho mỗi test case.
 * - Tất cả các Test Class khác sẽ kế thừa (extends) lớp này
 * để tự động có sẵn cấu hình mà không cần viết lại.
 *
 * Môn học: Kiểm thử và Đảm bảo Chất lượng Phần mềm.
 */
@ExtendWith(TestConfig.TestCaseBannerExtension.class)
public class TestConfig {

    /**
     * Phương thức setup() được đánh dấu @BeforeAll, nghĩa là nó sẽ
     * chạy MỘT LẦN DUY NHẤT trước tất cả các test case trong class.
     *
     * Đây là nơi tập trung cấu hình chung (UTF-8, logging) cho REST Assured.
     * Base URI sẽ được định nghĩa riêng ở từng lớp con kế thừa.
     */
    @BeforeAll
    public static void setup() {

        // ============================================================
        // CẤU HÌNH CHUNG REST ASSURED
        // ============================================================

        // 1. Cấu hình Encoding UTF-8 cho Request & Response.
        // Mặc định REST Assured dùng ISO-8859-1, gây lỗi font tiếng Việt.
        // Cần đặt charset UTF-8 để hiển thị đúng ký tự Unicode.
        RestAssured.config = RestAssured.config()
                .encoderConfig(EncoderConfig.encoderConfig()
                        .defaultContentCharset("UTF-8")
                        .defaultCharsetForContentType("UTF-8", "application/json"))
                .decoderConfig(DecoderConfig.decoderConfig()
                        .defaultContentCharset("UTF-8"));

        // 2. Bật chế độ log chi tiết khi gặp lỗi (validation fail).
        // Giúp xem được Request & Response đầy đủ trong console log
        // để dễ dàng debug khi test case không pass.
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // ================================================================
    // JUnit 5 Extension: Tự động in banner cho mỗi test case
    // ================================================================

    /**
     * Extension tùy chỉnh để hiển thị thông tin test case rõ ràng trên console.
     *
     * - Trước mỗi test: In banner với tên test case (lấy từ @DisplayName).
     * - Sau mỗi test: In kết quả PASSED ✅ hoặc FAILED ❌.
     *
     * Điều này giúp console output dễ đọc hơn khi chạy test,
     * đặc biệt hữu ích khi thuyết trình trước lớp.
     */
    static class TestCaseBannerExtension implements BeforeEachCallback, AfterEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) {
            // Lấy tên hiển thị từ @DisplayName (nếu có), nếu không thì lấy tên method
            String displayName = context.getDisplayName();
            String className = context.getRequiredTestClass().getSimpleName();

            System.out.println();
            System.out.println(
                    "+----------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|  CLASS: " + className);
            System.out.println("|  TEST : " + displayName);
            System.out.println(
                    "+----------------------------------------------------------------------------------------------------------------------------+");
        }

        @Override
        public void afterEach(ExtensionContext context) {
            // Kiểm tra xem test có lỗi không
            String displayName = context.getDisplayName();
            boolean isFailed = context.getExecutionException().isPresent();

            if (isFailed) {
                System.out.println(">>> KET QUA: " + displayName + " => FAILED");
            } else {
                System.out.println(">>> KET QUA: " + displayName + " => PASSED");
            }
            System.out.println(
                    "+----------------------------------------------------------------------------------------------------------------------------+");
        }
    }
}
