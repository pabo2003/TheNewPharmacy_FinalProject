package lk.ijse.gdse.model;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class CartTm {

    private String I_ID;
    private String description;
    private int qty;
    private double unitPrice;
    private double Amount;
    private JFXButton btnRemove;
}
