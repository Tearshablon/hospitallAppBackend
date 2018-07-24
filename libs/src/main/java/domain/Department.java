package domain;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Department {
    private Long id;
    private DepartmentName name;
    private String description;
    private List<Doctor> doctorList;

}