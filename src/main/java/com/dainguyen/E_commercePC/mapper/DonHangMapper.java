package com.dainguyen.E_commercePC.mapper;

import com.dainguyen.E_commercePC.dto.response.OrderResponse;
import com.dainguyen.E_commercePC.entity.order.DonHang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    OrderResponse toOrderResponse(DonHang donHang);
    // Có thể thêm mapping cho ChiTietDonHang nếu cần
}
