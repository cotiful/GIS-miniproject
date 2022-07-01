package com.forcewave.project.testcode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReseponDto {

    @Id
    private Integer id;

    @Column(name = "mega_nm")
    private String megaNm;

    @Column(name = "st_astext")
    private String st_astext;

    @Column(name = "emdnmae")
    private String emdname;

    @Column(name = "points")
    private Integer points;

    public char[] getMegaNm() {
        return null;
    }

}
