package com.dainguyen.E_commercePC.service;

import com.dainguyen.E_commercePC.dto.request.SanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ApiResponse;
import com.dainguyen.E_commercePC.dto.response.SanPhamResponse;
import com.dainguyen.E_commercePC.entity.product.ChiTietSanPham;
import com.dainguyen.E_commercePC.entity.product.DanhMuc;
import com.dainguyen.E_commercePC.entity.product.SanPham;
import com.dainguyen.E_commercePC.exception.AppException;
import com.dainguyen.E_commercePC.exception.ErrorCode;
import com.dainguyen.E_commercePC.mapper.SanPhamMapper;
import com.dainguyen.E_commercePC.repository.ChiTietSanPhamRepository;
import com.dainguyen.E_commercePC.repository.DanhMucRepository;
import com.dainguyen.E_commercePC.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    final SanPhamRepository sanPhamRepository;
    final SanPhamMapper sanPhamMapper;
    final DanhMucRepository danhMucRepository;

    public SanPhamResponse createSanPham(SanPhamRequest sanPhamRequest) {
        var sanPham = sanPhamMapper.toSanPham(sanPhamRequest);

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamRequest.getDanhMucId())
                        . orElseThrow(() -> new RuntimeException("Can not find category"));

        sanPham.setDanhMucId(danhMuc);

        LocalDateTime dateTime = LocalDateTime.now();
        sanPham.setCreatedAt(dateTime);

        sanPhamRepository.save(sanPham);
        return sanPhamMapper.toSanPhamResponse(sanPham);

    }

    public List<SanPhamResponse> getAllSanPham() {
        return sanPhamRepository.findAll().stream().map(sanPhamMapper::toSanPhamResponse).toList();
    }


    public void deleteProduct(Integer productId) {
        if (!sanPhamRepository.existsById(productId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED); // Sử dụng ErrorCode giả định
        }

        sanPhamRepository.deleteById(productId);
    }
}
