package domain;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
    private String photo;

    public Doctor(Long id, String firstName, String lastName, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }
}
