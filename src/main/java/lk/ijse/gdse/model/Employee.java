package lk.ijse.gdse.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Employee {
    private String employeeId;
    private String name;
    private String NICNo;
    private String address;
    private String tel;
    private double salary;
}

