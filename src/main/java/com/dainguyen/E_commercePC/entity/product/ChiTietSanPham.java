package dainguyen.E_commercePC.entity.product;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String moTa;
    String thuocTinh;
    String giaTri;
    Integer soLuong;

    @OneToOne
    @JoinColumn(name = "san_pham_id", referencedColumnName = "id")
    SanPham sanPhamId;
}
