/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author marc
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="Torneig")

public final class Torneig implements Serializable {

    public Torneig(String pais, String categoria) {
        this.pais = pais;
        this.categoria = categoria;

        // Punts per a la categoria "MAJOR"
        if ("MAJOR".equalsIgnoreCase(categoria)) {
            this.addPuntuacióRanking(new Punts_Ranking("Guanyador", 2000));
            this.addPuntuacióRanking(new Punts_Ranking("Finalista", 1200));
            this.addPuntuacióRanking(new Punts_Ranking("Semifinalista", 720));
            this.addPuntuacióRanking(new Punts_Ranking("Quarts", 360));
            this.addPuntuacióRanking(new Punts_Ranking("Octaus", 180));
        }
        // Punts per a la categoria "P1"
        else if ("P1".equalsIgnoreCase(categoria)) {
            this.addPuntuacióRanking(new Punts_Ranking("Guanyador", 1000));
            this.addPuntuacióRanking(new Punts_Ranking("Finalista", 600));
            this.addPuntuacióRanking(new Punts_Ranking("Semifinalista", 360));
            this.addPuntuacióRanking(new Punts_Ranking("Quarts", 180));
            this.addPuntuacióRanking(new Punts_Ranking("Octaus", 90));
        }
        // Punts per a la categoria "P2"
        else if ("P2".equalsIgnoreCase(categoria)) {
            this.addPuntuacióRanking(new Punts_Ranking("Guanyador", 500));
            this.addPuntuacióRanking(new Punts_Ranking("Finalista", 300));
            this.addPuntuacióRanking(new Punts_Ranking("Semifinalista", 180));
            this.addPuntuacióRanking(new Punts_Ranking("Quarts", 90));
            this.addPuntuacióRanking(new Punts_Ranking("Octaus", 45));
        }
    }

    
    static final long serialVersionUID = 137L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTorneig;
    
    @Column
    private String pais;
    
    @Column
    private String categoria;
    
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,mappedBy= "torneigs")
    @ToString.Exclude
    private List < Equip > equips_inscrits = new ArrayList();
    
    // Relacionem amb Punts_Torneig, perque un torneig dona molts punts diferents
    @OneToMany(mappedBy = "torneig", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Punts_Ranking> puntsRanking = new ArrayList<>();
    
    public void addPuntuacióRanking (Punts_Ranking punts){
        
        puntsRanking.add(punts);
        punts.setTorneig(this);
        
    }
    
    public void mostrarPremis() {
        System.out.println();
        System.out.println("\tPremis per al torneig de categoria: " + categoria);
        System.out.println();
        System.out.println("\t=======================================");
        for (Punts_Ranking punts : puntsRanking) {
            System.out.println("\tCategoria: " + punts.getCategoria() + " - Punts: " + punts.getPunts());
        }
        System.out.println("\t=======================================");
        System.out.println();
    }
}
    
