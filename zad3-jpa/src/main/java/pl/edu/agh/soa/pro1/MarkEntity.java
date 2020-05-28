package pl.edu.agh.soa.pro1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class MarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private int mark;

}
