package tech.devinhouse.aviation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "tinyint DEFAULT 1")
    private boolean available;

    @Column(columnDefinition = "tinyint DEFAULT 0")
    private boolean emergency;

    public void reserveSeat() {
        this.available = false;
    }
}
