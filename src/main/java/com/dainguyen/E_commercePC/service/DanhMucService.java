package com.dainguyen.E_commercePC.service;

import com.dainguyen.E_commercePC.dto.request.DanhMucRequest;
import com.dainguyen.E_commercePC.dto.response.DanhMucResponse;
import com.dainguyen.E_commercePC.mapper.DanhMucMapper;
import com.dainguyen.E_commercePC.repository.DanhMucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanhMucService {
    final DanhMucRepository danhMucRepository;
    final DanhMucMapper danhMucMapper;

    public DanhMucResponse createDanhMuc(DanhMucRequest danhMucRequest) {
        var danhMuc = danhMucMapper.toDanhMuc(danhMucRequest);

        danhMucRepository.save(danhMuc);

        return danhMucMapper.toDanhMucResponse(danhMuc);
    }

    public List<DanhMucResponse> getAllDanhMuc() {
        return danhMucRepository.findAll().stream().map(danhMucMapper::toDanhMucResponse).toList();
    }

    public void deleteDanhMuc(Integer id) {
        danhMucRepository.deleteById(id);
    }
}
