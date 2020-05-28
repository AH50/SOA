package pl.edu.agh.soa.pro1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class SandwichEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sandwichId;
    private String name;

}