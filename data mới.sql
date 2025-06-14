USE [chamcongnhanhsu]
GO
/****** Object:  Table [dbo].[attendance]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attendance](
	[attendance_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[date] [date] NOT NULL,
	[checkin_time] [time](7) NULL,
	[checkout_time] [time](7) NULL,
	[location_id] [int] NULL,
	[checkin_image_url] [nvarchar](255) NULL,
	[checkout_image_url] [nvarchar](255) NULL,
	[is_locked] [bit] NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[attendance_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[attendance_disputes]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attendance_disputes](
	[dispute_id] [int] IDENTITY(1,1) NOT NULL,
	[attendance_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[reason] [nvarchar](500) NULL,
	[status] [varchar](20) NULL,
	[manager_comment] [nvarchar](500) NULL,
	[created_at] [datetime] NULL,
	[resolved_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[dispute_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[attendance_locks]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attendance_locks](
	[lock_id] [int] IDENTITY(1,1) NOT NULL,
	[month] [int] NOT NULL,
	[year] [int] NOT NULL,
	[locked_by] [int] NULL,
	[locked_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[lock_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[audit_logs]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[audit_logs](
	[log_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[action] [nvarchar](255) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[log_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[departments]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[departments](
	[department_id] [int] IDENTITY(1,1) NOT NULL,
	[department_name] [nvarchar](255) NOT NULL,
	[department_code] [nvarchar](50) NULL,
	[description] [nvarchar](max) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[department_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[leave_balance]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[leave_balance](
	[leave_balance_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[year] [int] NULL,
	[leave_type] [varchar](50) NULL,
	[total_days] [int] NULL,
	[used_days] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[leave_balance_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[leave_config]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[leave_config](
	[config_id] [int] IDENTITY(1,1) NOT NULL,
	[year] [int] NULL,
	[default_days] [int] NULL,
	[created_by] [int] NULL,
	[leave_type_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[config_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[leave_requests]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[leave_requests](
	[request_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[start_date] [date] NULL,
	[end_date] [date] NULL,
	[status] [varchar](20) NULL,
	[days_count] [int] NULL,
	[reason] [nvarchar](500) NULL,
	[created_at] [datetime] NULL,
	[approved_by] [int] NULL,
	[approve_comment] [nvarchar](max) NULL,
	[leave_type_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[request_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[leave_types]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[leave_types](
	[leave_type_id] [int] IDENTITY(1,1) NOT NULL,
	[leave_type_name] [nvarchar](100) NOT NULL,
	[status] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[leave_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[leave_type_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[location_departments]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[location_departments](
	[location_id] [int] NOT NULL,
	[department_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[location_id] ASC,
	[department_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[locations]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[locations](
	[location_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NULL,
	[address] [nvarchar](255) NULL,
	[is_active] [bit] NULL,
	[ip_map] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[location_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[password_reset_tokens]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[password_reset_tokens](
	[token] [varchar](255) NOT NULL,
	[user_id] [int] NOT NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[token] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shifts]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shifts](
	[shift_id] [int] IDENTITY(1,1) NOT NULL,
	[shift_name] [nvarchar](100) NOT NULL,
	[start_time] [time](7) NOT NULL,
	[end_time] [time](7) NOT NULL,
	[description] [nvarchar](max) NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[shift_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_locations]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_locations](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[location_id] [int] NOT NULL,
	[assigned_at] [datetime] NULL,
	[department_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 6/14/2025 4:08:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password_hash] [varchar](255) NOT NULL,
	[full_name] [nvarchar](100) NOT NULL,
	[email] [varchar](100) NULL,
	[phone] [varchar](20) NULL,
	[role] [varchar](20) NULL,
	[employment_type] [varchar](20) NULL,
	[status] [varchar](20) NULL,
	[created_at] [datetime] NULL,
	[ban_reason] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[attendance] ADD  DEFAULT ((0)) FOR [is_locked]
GO
ALTER TABLE [dbo].[attendance] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[attendance_disputes] ADD  DEFAULT ('pending') FOR [status]
GO
ALTER TABLE [dbo].[attendance_disputes] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[attendance_locks] ADD  DEFAULT (getdate()) FOR [locked_at]
GO
ALTER TABLE [dbo].[audit_logs] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[departments] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[leave_balance] ADD  DEFAULT ((0)) FOR [used_days]
GO
ALTER TABLE [dbo].[leave_requests] ADD  DEFAULT ('pending') FOR [status]
GO
ALTER TABLE [dbo].[leave_requests] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[leave_types] ADD  DEFAULT ('active') FOR [status]
GO
ALTER TABLE [dbo].[locations] ADD  DEFAULT ((1)) FOR [is_active]
GO
ALTER TABLE [dbo].[password_reset_tokens] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[shifts] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[user_locations] ADD  DEFAULT (getdate()) FOR [assigned_at]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ('active') FOR [status]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[attendance]  WITH CHECK ADD FOREIGN KEY([location_id])
REFERENCES [dbo].[locations] ([location_id])
GO
ALTER TABLE [dbo].[attendance]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[attendance_disputes]  WITH CHECK ADD FOREIGN KEY([attendance_id])
REFERENCES [dbo].[attendance] ([attendance_id])
GO
ALTER TABLE [dbo].[attendance_disputes]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[attendance_locks]  WITH CHECK ADD FOREIGN KEY([locked_by])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[audit_logs]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[leave_balance]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[leave_config]  WITH CHECK ADD FOREIGN KEY([created_by])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[leave_config]  WITH CHECK ADD  CONSTRAINT [FK_leave_config_leave_type] FOREIGN KEY([leave_type_id])
REFERENCES [dbo].[leave_types] ([leave_type_id])
GO
ALTER TABLE [dbo].[leave_config] CHECK CONSTRAINT [FK_leave_config_leave_type]
GO
ALTER TABLE [dbo].[leave_requests]  WITH CHECK ADD FOREIGN KEY([approved_by])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[leave_requests]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[leave_requests]  WITH CHECK ADD  CONSTRAINT [FK_leave_requests_leave_type_id] FOREIGN KEY([leave_type_id])
REFERENCES [dbo].[leave_types] ([leave_type_id])
GO
ALTER TABLE [dbo].[leave_requests] CHECK CONSTRAINT [FK_leave_requests_leave_type_id]
GO
ALTER TABLE [dbo].[location_departments]  WITH CHECK ADD FOREIGN KEY([department_id])
REFERENCES [dbo].[departments] ([department_id])
GO
ALTER TABLE [dbo].[location_departments]  WITH CHECK ADD FOREIGN KEY([location_id])
REFERENCES [dbo].[locations] ([location_id])
GO
ALTER TABLE [dbo].[password_reset_tokens]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[user_locations]  WITH CHECK ADD FOREIGN KEY([location_id])
REFERENCES [dbo].[locations] ([location_id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_locations]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_locations]  WITH CHECK ADD  CONSTRAINT [FK_user_locations_department] FOREIGN KEY([department_id])
REFERENCES [dbo].[departments] ([department_id])
GO
ALTER TABLE [dbo].[user_locations] CHECK CONSTRAINT [FK_user_locations_department]
GO
ALTER TABLE [dbo].[attendance_disputes]  WITH CHECK ADD CHECK  (([status]='rejected' OR [status]='approved' OR [status]='pending'))
GO
ALTER TABLE [dbo].[leave_requests]  WITH CHECK ADD  CONSTRAINT [chk_leave_status] CHECK  (([status]='canceled' OR [status]='rejected' OR [status]='approved' OR [status]='pending'))
GO
ALTER TABLE [dbo].[leave_requests] CHECK CONSTRAINT [chk_leave_status]
GO
ALTER TABLE [dbo].[leave_requests]  WITH CHECK ADD CHECK  (([status]='rejected' OR [status]='approved' OR [status]='pending'))
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD CHECK  (([employment_type]='parttime' OR [employment_type]='fulltime'))
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD CHECK  (([role]='employee' OR [role]='manager' OR [role]='admin'))
GO
