package pl.edu.agh.soa.pro1;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private int number;

    @Column
    private double value;

}
