package com.dainguyen.E_commercePC.controller;

import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.FileResponse;
import com.dainguyen.E_commercePC.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    // Endpoint này nhận file từ Frontend qua Multipart/form-data
    @PostMapping("/upload")
    public ApiResponse<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        // Cần xác thực quyền ADMIN ở đây nếu chưa được cấu hình trong SecurityConfig

        try {
            String fileUrl = fileService.saveFileToLocal(file);
            return ApiResponse.<FileResponse>builder()
                    .result(new FileResponse(fileUrl))
                    .message("Upload file thành công")
                    .build();
        } catch (Exception e) {
            // Xử lý lỗi upload
            return ApiResponse.<FileResponse>builder()
                    .code(9999)
                    .message("Upload file thất bại: " + e.getMessage())
                    .build();
        }
    }
}
