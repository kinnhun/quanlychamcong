-- 1. Bảng người dùng
CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) CHECK (role IN ('admin', 'manager', 'employee')),
    employment_type VARCHAR(20) CHECK (employment_type IN ('fulltime', 'parttime')),
    status VARCHAR(20) DEFAULT 'active',
    created_at DATETIME DEFAULT GETDATE()
);

-- 2. Danh sách chi nhánh/địa điểm làm việc
CREATE TABLE locations (
    location_id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100),
    address NVARCHAR(255),
    is_active BIT DEFAULT 1
);

-- 3. Gán chi nhánh cho nhân viên (có thể nhiều hơn 1 chi nhánh)
CREATE TABLE user_locations (
    user_id INT,
    location_id INT,
    is_default BIT DEFAULT 0,
    PRIMARY KEY (user_id, location_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (location_id) REFERENCES locations(location_id)
);

-- 4. Bảng chấm công
CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT NOT NULL,
    date DATE NOT NULL,
    checkin_time TIME,
    checkout_time TIME,
    location_id INT,
    checkin_image_url NVARCHAR(255),
    checkout_image_url NVARCHAR(255),
    is_locked BIT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (location_id) REFERENCES locations(location_id)
);

-- 5. Lịch sử khóa dữ liệu chấm công
CREATE TABLE attendance_locks (
    lock_id INT PRIMARY KEY IDENTITY(1,1),
    month INT NOT NULL,
    year INT NOT NULL,
    locked_by INT,
    locked_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (locked_by) REFERENCES users(user_id)
);

-- 6. Khiếu nại kết quả chấm công
CREATE TABLE attendance_disputes (
    dispute_id INT PRIMARY KEY IDENTITY(1,1),
    attendance_id INT NOT NULL,
    user_id INT NOT NULL,
    reason NVARCHAR(500),
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'approved', 'rejected')),
    manager_comment NVARCHAR(500),
    created_at DATETIME DEFAULT GETDATE(),
    resolved_at DATETIME,
    FOREIGN KEY (attendance_id) REFERENCES attendance(attendance_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 7. Yêu cầu nghỉ phép
CREATE TABLE leave_requests (
    request_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    start_date DATE,
    end_date DATE,
    leave_type VARCHAR(30) CHECK (leave_type IN ('annual', 'sick', 'unpaid', 'personal')),
    status VARCHAR(20) DEFAULT 'pending' CHECK (status IN ('pending', 'approved', 'rejected')),
    days_count INT,
    reason NVARCHAR(500),
    created_at DATETIME DEFAULT GETDATE(),
    approved_by INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

-- 8. Cấu hình nghỉ phép hệ thống
CREATE TABLE leave_config (
    config_id INT PRIMARY KEY IDENTITY(1,1),
    year INT,
    leave_type VARCHAR(50),
    default_days INT,
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- 9. Theo dõi số ngày phép cá nhân hóa của nhân viên
CREATE TABLE leave_balance (
    user_id INT,
    year INT,
    leave_type VARCHAR(50),
    total_days INT,
    used_days INT DEFAULT 0,
    PRIMARY KEY (user_id, year, leave_type),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 10. Nhật ký hoạt động
CREATE TABLE audit_logs (
    log_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT,
    action NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE password_reset_tokens (
    token VARCHAR(255) PRIMARY KEY,
    user_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

