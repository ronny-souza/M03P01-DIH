package tech.devinhouse.aviation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.devinhouse.aviation.model.enums.ClassificationEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passenger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificationEnum classification;

    @Column(nullable = false)
    private Integer miles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passenger")
    private List<BoardingTicket> tickets = new ArrayList<>();

    public void plusMiles() {
        this.miles += this.getClassification().getBalance();
    }
}
