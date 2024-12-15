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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author marc
 */

@Data
@NoArgsConstructor
@Entity
@Table(name="Ranking")

public class Ranking implements Serializable{    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_Ranking;
    
    @Column
    private int numJugadors;     
    
    @OneToMany(mappedBy= "ranking", cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List <Jugador> jugadorsDelRanking = new ArrayList();
    
}
