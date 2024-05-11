package lk.ijse.gdse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Item {
    private String itemId;
    private String description;
    private double unitPrice;
    private int QtyOnHand;
    private String stockId;


}
