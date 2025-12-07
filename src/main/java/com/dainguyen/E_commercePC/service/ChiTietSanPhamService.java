package com.dainguyen.E_commercePC.service;

import com.dainguyen.E_commercePC.dto.request.ChiTietSanPhamRequest;
import com.dainguyen.E_commercePC.dto.response.ChiTietSanPhamResponse;
import com.dainguyen.E_commercePC.mapper.ChiTietSanPhamMapper;
import com.dainguyen.E_commercePC.repository.ChiTietSanPhamRepository;
import com.dainguyen.E_commercePC.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiTietSanPhamService {
    final ChiTietSanPhamMapper mapper;
    final ChiTietSanPhamRepository chiTietSanPhamRepository;
    final SanPhamRepository sanPhamRepository;

    public ChiTietSanPhamResponse createChiTietSanPham(ChiTietSanPhamRequest chiTietSanPhamRequest) {
        var chiTietSanPham = mapper.toChiTietSanPham(chiTietSanPhamRequest);

        var sanPham = sanPhamRepository.findById(chiTietSanPhamRequest.getSanPhamId())
                .orElseThrow(() -> new RuntimeException("Product not exsist!"));

        chiTietSanPham.setSanPhamId(sanPham);

        chiTietSanPhamRepository.save(chiTietSanPham);
        return mapper.toChiTietSanPhamResponse(chiTietSanPham);
    }

    public List<ChiTietSanPhamResponse> getAllChiTietSanPham() {
        return chiTietSanPhamRepository.findAll().stream().map(mapper::toChiTietSanPhamResponse).toList();
    }
}
