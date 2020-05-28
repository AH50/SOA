package pl.edu.agh.soa.pro1.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@XmlType(propOrder = {"mark"})

public class Mark{
    private int mark;

    public Mark(int ECTS) {
        this.mark = mark;
    }
}
