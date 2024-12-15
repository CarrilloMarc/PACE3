/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.CascadeType;
import org.hibernate.query.Query;

/**
 *
 * @author marc
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="Jugadors")

public class Jugador implements Serializable {
    
    static final long serialVersionUID = 137L;

    public Jugador(String nom, String nacionalitat, int punts) {
        this.nom = nom;
        this.nacionalitat = nacionalitat;
        this.punts = punts;
    }    
    
    public Jugador (String nom){
        this.nom =  nom;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idJugador;
    
    @Column
    private String nom;
    
    @Column
    private String nacionalitat;
    
    @Column
    private int punts;
    
    // Tractem les columnes de companys, relació 1-1 amb ell mateix    
    @OneToOne
    @JoinColumn(name = "company")
    private Jugador company;
    
    @OneToOne(mappedBy= "company")
    private Jugador companyDe;
    
    @ManyToOne
    @JoinColumn(name= "idEquip", foreignKey = @ForeignKey(name = "FK_JUGADOR_EQUIP" ))
    @ToString.Exclude
    private Equip elEquip;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }
        Jugador jug = (Jugador) obj; 
        return Objects.equals(nom, jug.nom); 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
