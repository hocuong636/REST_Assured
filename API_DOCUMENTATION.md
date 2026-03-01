# 📖 Tài Liệu API - Mock API Products (json-server)

## Base URL
```
http://localhost:3000
```

## Resource: Products

### 1. GET /products — Lấy danh sách tất cả sản phẩm

| Thuộc tính       | Giá trị                          |
|-------------------|----------------------------------|
| **URL**           | `GET http://localhost:3000/products` |
| **Ý nghĩa**      | Trả về toàn bộ danh sách sản phẩm trong database |
| **Request Body**  | Không có                          |
| **Status Code**   | `200 OK`                          |
| **Response Body** | Mảng JSON chứa các object sản phẩm |

**Ví dụ Response:**
```json
[
  { "id": 1, "name": "Laptop Dell XPS 15", "price": 32000000, "category": "Electronics" },
  { "id": 2, "name": "Tai nghe Sony WH-1000XM5", "price": 8500000, "category": "Accessories" },
  { "id": 3, "name": "Chuột Logitech MX Master 3S", "price": 2200000, "category": "Accessories" }
]
```

---

### 2. GET /products/:id — Lấy thông tin 1 sản phẩm theo ID

| Thuộc tính       | Giá trị                              |
|-------------------|--------------------------------------|
| **URL**           | `GET http://localhost:3000/products/1` |
| **Ý nghĩa**      | Trả về thông tin chi tiết của sản phẩm có `id` tương ứng |
| **Request Body**  | Không có                              |
| **Status Code**   | `200 OK` (nếu tìm thấy) / `404 Not Found` (nếu không tồn tại) |
| **Response Body** | Object JSON của sản phẩm              |

**Ví dụ Response (id=1):**
```json
{ "id": 1, "name": "Laptop Dell XPS 15", "price": 32000000, "category": "Electronics" }
```

---

### 3. POST /products — Tạo mới một sản phẩm

| Thuộc tính       | Giá trị                              |
|-------------------|--------------------------------------|
| **URL**           | `POST http://localhost:3000/products` |
| **Ý nghĩa**      | Thêm một sản phẩm mới vào database. `id` sẽ được tự động sinh |
| **Content-Type**  | `application/json`                    |
| **Status Code**   | `201 Created`                         |
| **Response Body** | Object JSON của sản phẩm vừa tạo (kèm `id` tự sinh) |

**Ví dụ Request Body:**
```json
{ "name": "Bàn phím cơ Keychron K8", "price": 2500000, "category": "Accessories" }
```

**Ví dụ Response:**
```json
{ "id": 4, "name": "Bàn phím cơ Keychron K8", "price": 2500000, "category": "Accessories" }
```

---

### 4. PUT /products/:id — Cập nhật toàn bộ thông tin sản phẩm

| Thuộc tính       | Giá trị                              |
|-------------------|--------------------------------------|
| **URL**           | `PUT http://localhost:3000/products/1` |
| **Ý nghĩa**      | Thay thế toàn bộ dữ liệu của sản phẩm có `id` tương ứng |
| **Content-Type**  | `application/json`                    |
| **Status Code**   | `200 OK`                              |
| **Response Body** | Object JSON của sản phẩm sau khi cập nhật |

**Ví dụ Request Body:**
```json
{ "name": "Laptop Dell XPS 15 (2024)", "price": 35000000, "category": "Electronics" }
```

**Ví dụ Response:**
```json
{ "id": 1, "name": "Laptop Dell XPS 15 (2024)", "price": 35000000, "category": "Electronics" }
```

---

### 5. DELETE /products/:id — Xóa một sản phẩm

| Thuộc tính       | Giá trị                              |
|-------------------|--------------------------------------|
| **URL**           | `DELETE http://localhost:3000/products/2` |
| **Ý nghĩa**      | Xóa sản phẩm có `id` tương ứng khỏi database |
| **Request Body**  | Không có                              |
| **Status Code**   | `200 OK`                              |
| **Response Body** | Object rỗng `{}`                      |

> ⚠️ Sau khi xóa, nếu GET lại `/products/2` sẽ nhận được `404 Not Found`.

---

## Cách khởi chạy Mock API

```bash
# Cài đặt json-server (nếu chưa có)
npm install -g json-server

# Chạy server với file db.json
npx json-server --watch db.json
```

Server sẽ chạy tại: `http://localhost:3000`

