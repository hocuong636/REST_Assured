# Kịch Bản Thuyết Trình: Demo REST Assured

## 1. Phần Mở Đầu: Chào hỏi & Giới thiệu (Slide 1)

**Lời thoại:**
> "Dạ em chào thầy Trịnh Công Nhựt và xin chào tất cả các bạn. Em là **[Tên của bạn]**, hôm nay em rất vui được đại diện cho Nhóm 22, lớp 22DTHB4 trình bày về một công cụ cực kỳ mạnh mẽ trong môn học Kiểm thử và Đảm bảo chất lượng phần mềm, đó chính là **Rest Assured**. Bài báo cáo hôm nay là thành quả tìm hiểu của 5 thành viên trong nhóm, và hy vọng sẽ mang lại những góc nhìn thực tế nhất cho mọi người về công cụ này."

---

## 2. Phần Nội Dung: Rest Assured là gì? & Tại sao lại chọn nó? (Slide 2 & 3)

**Lời thoại:**
> "Đến với nội dung chính, đầu tiên mình cùng tìm hiểu: **Rest Assured là gì?** Nói một cách đơn giản, đây là một thư viện hỗ trợ viết bằng ngôn ngữ Java. Mục đích lớn nhất của nó sinh ra là để giúp chúng ta kiểm thử tự động các REST APIs một cách dễ dàng.
> 
> Có thể các bạn sẽ thắc mắc: *'Tại sao lại dùng Rest Assured mà không dùng code Java thuần?'*.
> Lý do là vì nếu dùng Java thuần (như `HttpURLConnection`), đoạn code của chúng ta sẽ rất dài và phức tạp. Thay vào đó, Rest Assured áp dụng phong cách BDD – tức là Phát triển hướng hành vi. Nhờ vậy, đoạn code của chúng ta viết ra sẽ liền mạch và dễ đọc như một câu tiếng Anh, giúp việc bảo trì sau này nhàn hơn rất nhiều."

---

## 3. Phần Trọng Tâm: Cấu trúc hoạt động Given - When - Then (Slide 4 & 5)

**Lời thoại:**
> "Để thấy rõ sự liền mạch đó, chúng ta cùng nhìn vào cấu trúc hoạt động cốt lõi của Rest Assured. Nó xoay quanh đúng 3 hàm cơ bản: `Given`, `When` và `Then`.
> 
> Đầu tiên là `given()` - Đây là bước chuẩn bị. Ở đây mình sẽ khai báo các thông tin đầu vào như đường dẫn URI, các Header hay Parameter.
> 
> Tiếp theo là `when()` - Đây là lúc mình 'hành động'. Mình sẽ gọi các phương thức như GET, POST và truyền body vào để gửi request đi.
> 
> Cuối cùng là `then()` - Bước kiểm tra kết quả. Mình sẽ dùng hàm này để xác nhận xem Status code trả về có đúng 200 không, hay dữ liệu bên trong có khớp với mong đợi không.

*(Chuyển sang slide Ví dụ - Chỉ tay/Laser pointer vào màn hình)*
> Mọi người có thể nhìn vào ví dụ ngắn trên slide: Bắt đầu bằng `given()` để setup địa chỉ API, tiếp đến `when()` để gọi phương thức GET lấy thông tin user số 1, và `then()` lập tức kiểm tra xem hệ thống có trả về thành công với tên user là 'John Doe' hay không. Rất trực quan và rõ ràng đúng không ạ?"

---

## 4. Phần So Sánh: Rest Assured vs Postman (Slide 6 & 7)

**Lời thoại:**
> "Tới đây, chắc hẳn nhiều bạn sẽ tự hỏi: *'Thế công cụ này khác gì Postman mình hay xài?'*. Nhóm mình đã làm một bảng so sánh nhỏ để mọi người cùng đánh giá.
> 
> Về Postman: Ưu điểm tuyệt vời của nó là giao diện trực quan, cực kỳ dễ dùng, không biết code vẫn test ngon lành. Tuy nhiên, nó lại bộc lộ nhược điểm khi làm việc nhóm vì khó chia sẻ code, hạn chế khi dùng lượng dữ liệu lớn, hay việc xuất báo cáo chi tiết.
> 
> Và đó là lúc Rest Assured tỏa sáng: Mặc dù lúc mới học sẽ hơi cực vì yêu cầu mọi người phải biết về Java, hiểu về BDD và phải đọc kết quả qua màn hình console. Nhưng bù lại, nó cho phép chúng ta tái sử dụng code cực tốt, tích hợp mượt mà vào các hệ thống CI/CD (như Maven/Gradle), và không hề có giới hạn nào khi xây dựng các framework kiểm thử hướng dữ liệu lớn."

---

## 5. Phần Chuyển Giao: Dẫn dắt sang phần Demo (Slide 8)

**Lời thoại:**
> "Tóm lại, nếu chỉ test nhanh hoặc dự án nhỏ, Postman là chân ái. Nhưng nếu làm một dự án thực tế lớn, cần tự động hóa cao và bảo trì lâu dài, Rest Assured chắc chắn là sự lựa chọn tối ưu.
> 
> Vừa rồi là những lý thuyết cốt lõi nhất về Rest Assured. Nhưng 'trăm nghe không bằng một thấy', để chứng minh sự tiện lợi của bộ ba hàm Given - When - Then hoạt động thực tế ra sao, ngay sau đây, nhóm mình xin phép chuyển sang phần Demo.
> 
> Trong phần này, nhóm sẽ chạy thử một kịch bản test API thực tế và bắt lỗi tự động để mọi người cùng xem. Xin mời thầy và các bạn cùng hướng mắt lên màn hình của bạn **[Tên bạn diễn Demo]** để bắt đầu ạ!"

---

## 6. Phần Demo Thực Tế: Chạy các Test Case bằng REST Assured (Slide 9 - 14)

*(Người thuyết trình chuyển pass cho bạn chạy Demo, hoặc tự chạy trực tiếp trên máy trình chiếu.)*

**Lời thoại & Hành động:**
> "Xin chào thầy và các bạn, sau đây nhóm mình sẽ tiến hành demo thực tế công cụ Rest Assured. Nhóm chuẩn bị sẵn 2 phần: test trên một Public API có thật trên mạng, và test trên một Mock API do nhóm tự dựng.

### 6.1. Demo test Public API - JSONPlaceholder

*(Mở IDE hiển thị file **`JsonPlaceholderTest.java`**)*
> Đầu tiên, chúng ta sẽ xem cách Rest Assured tương tác với một API thật trên internet là `jsonplaceholder`. Đây là API mở cho phép test các thao tác cơ bản.
> Trong file này, nhóm đã viết 5 Test Case bao gồm GET danh sách bài viết, GET chi tiết 1 bài, POST tạo bài viết mới, và cả kiểm tra lỗi (Negative Test) khi tìm bài viết không tồn tại.

*(Nhấn nút Run toàn bộ class `JsonPlaceholderTest`)*
> ...Như các bạn thấy ở phía console, các test case chạy rất nhanh. Ở đoạn test tạo mới bài viết (TC03), Console đã in ra chi tiết Body JSON định dạng rất rõ ràng mà Rest Assured gửi đi, cũng như bóc tách được Response trả về với HTTP status là 201 Created. Đặc biệt, nhóm cũng cài đặt một test kiểm tra hiệu năng (TC05): yêu cầu Public API này phải phản hồi dưới 2 giây (2000ms), và test đã Pass thành công. Việc test thẳng lên một server thật chứng tỏ Rest Assured xử lý thao tác HTTP thực tế rất mạnh mẽ.

### 6.2. Demo test Local Mock API - json-server quản lý Sản Phẩm

*(Mở IDE hiển thị file **`MockApiProductTest.java`**)*
> Tiếp theo, để kiểm soát hoàn toàn được dữ liệu (có thể sửa, xóa thật sự), nhóm đã dựng một Mock Server quản lý Sản phẩm cá nhân chạy ở dưới máy local.

*(Mở Terminal chạy lệnh: `npx json-server --watch db.json`)*
> Hệ thống backend giả lập đã chạy trên cổng 3000. 
> Ở code này, nhóm thiết kế tổng cộng 7 Test case theo luồng chuẩn hóa. Nhưng để tiết kiệm thời gian, nhóm xin phép chạy thử tình huống liền mạch nhất: Xóa dữ liệu và Bắt lỗi.

*(Chỉ vào **TC04** - Delete và Nhấn nút Run)*
> ...Ở TC04, sau khi gửi lệnh DELETE thành công, nhóm thiết kế cho code lập tức gửi lệnh GET gọi lại đúng ID vừa xóa. Kết quả trả về ngay lập tức là mã 404 Not Found, test case báo Pass rực rỡ chứng tỏ backend đã xóa thật dữ liệu khỏi DB và Rest Assured assert điều đó vô cùng chuẩn xác.

*(Chỉ vào **TC05, TC06** - Test Negative và Nhấn Run)*
> Một API xịn không chỉ xử lý luồng chuẩn mà còn phải biết báo lỗi chặn thao tác sai. Ở TC05 và 06, nhóm cố tình lấy và xóa một sản phẩm có id = 9999 (không tồn tại).
> ...Mọi người có thể thấy Rest Assured xác thực chính xác status code 404 được trả về. Tránh tình trạng hệ thống bị 'ngáo' và trả về 200 kèm body rỗng. Điều này rất thiết thực trong kiểm thử bảo mật và logic."

---

## 7. Tổng kết & Q&A (Slide 15)

**Lời thoại:**
> "Vậy là qua phần Demo vừa rồi, tất cả các bài test từ luồng chuẩn (CRUD), luồng thao tác sai cho đến test sức chịu tải nhẹ cho cả Public API lẫn Local API đều chạy mượt mà và đúng yêu cầu.
> 
> Qua bài báo cáo này, nhóm 22 hy vọng đã giới thiệu rõ ràng được tính ứng dụng, cú pháp BDD cực kỳ tự nhiên, và sức mạnh bao quát của Rest Assured khi đưa vào tự động hóa kiểm thử API trong thực tế. 
> 
> Bài thuyết trình của nhóm 22 đến đây là kết thúc. Cảm ơn thầy Trịnh Công Nhựt và các bạn đã ưu ái theo dõi từ nãy đến giờ!
> Không biết thầy và mọi người có câu hỏi hay nhận xét gì cho nhóm em không ạ? Xin mời mọi người đặt câu hỏi!"
