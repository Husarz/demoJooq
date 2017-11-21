package co.aisystem.hibernate.domain;

import co.aisystem.hibernate.domain.types.Nip;
import co.aisystem.hibernate.domain.types.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private Boolean firstVisit;

    @Embedded
    @Column(unique = true)
    private Nip nip;
}

