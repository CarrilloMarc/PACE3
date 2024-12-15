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
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author marc
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="Punts_Ranking")

public class Punts_Ranking implements Serializable {
    
    public Punts_Ranking (String categoria, int punts){
        this.categoria = categoria;
        this.punts = punts;
    }
    
    static final long serialVersionUID = 137L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String categoria;
    
    @Column
    private int punts;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "torneig_id", foreignKey = @ForeignKey(name = "FK_RAN_TOR"))
    private Torneig torneig;
    
    /*
    public void setTorneig(Torneig torneig) {
        
        this.torneig = torneig; 

        if (torneig != null) {
            
            if (!torneig.getPuntsRanking().contains(this)) {
                torneig.getPuntsRanking().add(this);
            }
        } 
    }
    */
            
}
