/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="Equips")

public class Equip implements Serializable{
   
    static final long serialVersionUID = 137L;
    
    public Equip (Jugador jugador1, Jugador jugador2){        
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;        
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idEquip;
    
    /*
    @Column
    private int jugador1_id;
    
    @Column
    private int jugador2_id;
    
    @Column
    private int puntsEquip;
    */
    
    @OneToOne
    @JoinColumn(name = "jugador1_id", foreignKey = @ForeignKey(name = "FK_EQUIP_JUGADOR1"))
    @ToString.Exclude
    private Jugador jugador1;

    @OneToOne
    @JoinColumn(name = "jugador2_id", foreignKey = @ForeignKey(name = "FK_EQUIP_JUGADOR2"))
    @ToString.Exclude
    private Jugador jugador2;
    
    public int puntsTotalsEquip(){
        int puntsJugador1 = (jugador1 != null)? jugador1.getPunts() : 0;
        int puntsJugador2 = (jugador2 != null)? jugador2.getPunts() : 0;
        return puntsJugador1 + puntsJugador2;
    }
    
    /*
    @OneToMany(mappedBy="elEquip", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List < Jugador > jugadors = new ArrayList();
    
    
    public void addJugador ( Jugador jug ){
        this.jugadors.add(jug);
    }    
    */
    
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "Torneig_Equip",
               joinColumns = @JoinColumn(name = "idEquip"),
               inverseJoinColumns = @JoinColumn(name = "idTorneig"))
    private List<Torneig> torneigs = new ArrayList<>();
    
}
    

