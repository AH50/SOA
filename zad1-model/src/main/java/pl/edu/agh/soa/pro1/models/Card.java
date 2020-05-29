package pl.edu.agh.soa.pro1.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@XmlType(propOrder = {"number","value"})

public class Card{
    private int number;
    private double value;

    public Card(int number,double value) {
        this.number = number;
        this.value = value;
    }
}
