package com.dainguyen.E_commercePC.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Lấy giá trị thư mục upload (mặc định là "uploads")
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 1. CHUẨN HÓA ĐƯỜNG DẪN VẬT LÝ
        // Lấy đường dẫn tuyệt đối của thư mục uploads
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Đảm bảo thư mục uploads tồn tại
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            System.err.println("Could not create upload directory: " + e.getMessage());
        }

        // Tạo Resource Location URI: file:///path/to/project/uploads/
        // URI này phải kết thúc bằng dấu / để chỉ ra nó là một thư mục
        String resourceLocation = uploadPath.toUri().toString();

        // 2. CẤU HÌNH HANDLER
        // Ánh xạ URL công khai /static/** tới thư mục vật lý
        // Handler: /static/**
        // Location: file:/.../uploads/
        registry.addResourceHandler("/static/**")
                .addResourceLocations(resourceLocation);
    }
}