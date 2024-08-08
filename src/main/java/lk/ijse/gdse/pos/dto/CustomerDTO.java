package lk.ijse.gdse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String mobile;
}