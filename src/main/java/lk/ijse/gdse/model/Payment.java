package lk.ijse.gdse.model;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Payment {
    private String payId;
    private String method;
    private double amount;
    private Date date;
}

