/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pace;

import com.mycompany.main.Equip;
import com.mycompany.main.Jugador;
import com.mycompany.main.Punts_Ranking;
import com.mycompany.main.Torneig;
import com.mycompany.utilitats.HibernateUtil;
import com.mycompany.utilitats.Leer;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author marc
 */
public class PACE3 {

    public static void main(String[] args) {

        PACE3 app = new PACE3();

        app.start();
        
    }
    
    private void start(){
        
        int opcio;
        do {
         
            System.out.println("Sobre qué vols manipular o vore dades?: ");
            System.out.println("1: Jugadors ");
            System.out.println("2: Equips");
            System.out.println("3: Torneigs");            
            System.out.println("4: Eixir");            
            opcio = Leer.leerEntero("");
            
            switch (opcio){
                case 1:
                    menuJugadors();
                    break;
                case 2:
                    menuEquips();
                    break;
                case 3:
                    menuTorneigs();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opció incorrecta");
                
            }
        } while (opcio != 4);
    }
        
    private void menuJugadors(){

        int opcio;
        do {
            System.out.println("========== OPCIONS ==========");
            System.out.println("1.- Inserir nou jugador");
            System.out.println("2.- Editar jugador");
            System.out.println("3.- Mostar ranking");
            System.out.println("4.- Eliminar jugador");
            System.out.println("5.- Eixir");
            opcio = Leer.leerEntero("");
            
            switch (opcio){
                case 1:
                    afegirJugador();
                    break;
                case 2:
                    editarJugador();
                    break;
                case 3:
                    mostraRanking();
                    break;
                case 4:
                    eliminaJugador();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opció incorrecta");
                    break;
                 
            }
        } while (opcio!= 5);
    
    }
    
    private void editarJugador() {
    try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
        laSesion.getTransaction().begin();

        // Mostrem els jugadors existents
        System.out.println("Llista de jugadors disponibles:");
        Query<Jugador> query = laSesion.createQuery("from Jugador", Jugador.class);
        List<Jugador> jugadors = query.list();

        if (jugadors.isEmpty()) {
            System.out.println("No hi ha jugadors registrats.");
            laSesion.getTransaction().commit();
            return;
        }

        for (Jugador jugador : jugadors) {
            System.out.println("Nom: " + jugador.getNom() + ", Nacionalitat: " + jugador.getNacionalitat() + ", Punts: " + jugador.getPunts());
        }

        // Demanem el nom del jugador a editar
        String nomJugador = Leer.leerTexto("Introdueix el nom del jugador que vols editar: ");
        Query<Jugador> jugadorQuery = laSesion.createQuery("from Jugador where nom = :nom", Jugador.class);
        jugadorQuery.setParameter("nom", nomJugador);
        List<Jugador> resultat = jugadorQuery.list();

        if (resultat.isEmpty()) {
            System.out.println("El jugador amb nom '" + nomJugador + "' no existeix.");
        } else {
            Jugador jugador = resultat.get(0); 
            System.out.println("Jugador trobat: " + jugador);

            // Demanem quina dada vol editar l'usuari
            System.out.println("Quina dada vols editar?");
            System.out.println("1. Nom");
            System.out.println("2. Nacionalitat");
            System.out.println("3. Punts");
            int opcio = Leer.leerEntero("Introdueix una opció: ");

            switch (opcio) {
                case 1:
                    String nouNom = Leer.leerTexto("Introdueix el nou nom: ");
                    jugador.setNom(nouNom);
                    break;
                case 2:
                    String novaNacionalitat = Leer.leerTexto("Introdueix la nova nacionalitat: ");
                    jugador.setNacionalitat(novaNacionalitat);
                    break;
                case 3:
                    int nousPunts = Leer.leerEntero("Introdueix els nous punts: ");
                    jugador.setPunts(nousPunts);
                    break;
                default:
                    System.out.println("Opció incorrecta. No s'ha realitzat cap canvi.");
            }

            // Actualitzem el jugador a la base de dades
            laSesion.update(jugador);
            System.out.println("Jugador actualitzat: " + jugador.getNom());
        }

        laSesion.getTransaction().commit();
    } catch (Exception e) {
        System.out.println("Error durant l'edició del jugador: " + e.getMessage());
        }
    }

    private void afegirJugador(){
        
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();            
            
            // Demanem per teclat les dades per a inserir el jugador en la bdd i comprovem que no existisca            
            String nom = Leer.leerTexto("Nom del jugador: ");
            Query <Jugador> query = laSesion.createQuery("from Jugador where nom = :nom", Jugador.class);
            query.setParameter("nom", nom);
            
            // Creació d'un array dels jugadors per a verificar que no existisca ja en la bdd
            List <Jugador> resultat = query.list();

            if (!resultat.isEmpty()) {
            System.out.println("El jugador ja existeix en la base de dades!");
            } else {
                String nacionalitat = Leer.leerTexto("Nacionalitat del jugador: ");
                int punts = Leer.leerEntero("Punts del jugador: ");
                Jugador jug = new Jugador(nom, nacionalitat, punts);

                // Inserim jugador en la BDD
                laSesion.persist(jug);
                System.out.println("Jugador afegit correctament.");
            }

        // Fem commit i tanquem la connexió
        laSesion.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error en la conexió");
        }
    }
    
    private void mostraRanking() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Consulta per a obtenir jugadors ordenats pels punts en ordre descendent
            Query<Jugador> query = laSesion.createQuery("from Jugador order by punts desc", Jugador.class);
            List<Jugador> jugadorsOrdenats = query.list();

            // Comprovem si hi ha jugadors
            if (jugadorsOrdenats.isEmpty()) {
                System.out.println("No hi ha jugadors registrats.");
            } else {
                System.out.println("========== RANKING DE JUGADORS ==========");
                for (Jugador jugador : jugadorsOrdenats) {
                    System.out.println("Nom: " + jugador.getNom() +
                                       ", Nacionalitat: " + jugador.getNacionalitat() +
                                       ", Punts: " + jugador.getPunts());
                }
            }

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al mostrar el ranking: " + e.getMessage());
        }
    }
    
    private void eliminaJugador() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            String nomJugador = Leer.leerTexto("Introdueix el nom del jugador que vols eliminar: ");
            Long idJugador = buscarIdJugadorPerNom(laSesion, nomJugador);

            if (idJugador == null) {
                System.out.println("El jugador no existeix.");
                return;
            }

            // Obtenim el jugador
            Jugador jugador = laSesion.get(Jugador.class, idJugador);

            // Desvincular relacions amb l'equip
            if (jugador.getElEquip() != null) {
                Equip equip = jugador.getElEquip();

                if (equip.getJugador1() != null && equip.getJugador1().equals(jugador)) {
                    equip.setJugador1(null);
                }
                if (equip.getJugador2() != null && equip.getJugador2().equals(jugador)) {
                    equip.setJugador2(null);
                }

                jugador.setElEquip(null);
                laSesion.update(equip); // Actualitzem l'equip sense aquest jugador
            }

            // Desvincular relacions amb el company
            if (jugador.getCompany() != null) {
                Jugador company = jugador.getCompany();
                company.setCompany(null);
                jugador.setCompany(null);
                laSesion.update(company); // Actualitzem el company
            }

            // Eliminar el jugador
            laSesion.delete(jugador);
            System.out.println("Jugador eliminat correctament.");

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en eliminar el jugador: " + e.getMessage());
        }
    }

    
    private Long buscarIdJugadorPerNom(Session laSesion, String nomJugador) {
        Query<Long> query = laSesion.createQuery("select idJugador from Jugador where nom = :nom", Long.class);
        query.setParameter("nom", nomJugador);
        List<Long> result = query.list();

        if (result.isEmpty()) {
            System.out.println("No s'ha trobat cap jugador amb el nom: " + nomJugador);
            return null;
        }

        return result.get(0); // Retorna el primer resultat assumint que els noms són únics
    }
    
    private void menuEquips(){
        
        int opcio;
        do {
            System.out.println("========== OPCIONS ==========");
            System.out.println("1.- Crear nou equip");
            System.out.println("2.- Eliminar equip existent");
            System.out.println("3.- Vore punts d'un equip");
            System.out.println("4.- Eixir");
            opcio = Leer.leerEntero("");
            
            switch (opcio){
                case 1:
                    creaEquip();
                    break;
                case 2:
                    eliminaEquip();
                    break;
                case 3:
                    getPuntsEquip();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Has de seleccionar una opció correcta!");
                    break;
            }
        } while (opcio != 4);
    }
    
    private void creaEquip() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            System.out.println("Llistat de jugadors sense equip:");
            Query<Jugador> query = laSesion.createQuery("from Jugador where elEquip is null", Jugador.class);
            List<Jugador> jugadorsSenseEquip = query.list();

            for (Jugador jugador : jugadorsSenseEquip) {
                System.out.println("Nom: " + jugador.getNom() + ", Punts: " + jugador.getPunts());
            }

            String nomJugador1 = Leer.leerTexto("Nom del primer jugador: ");
            String nomJugador2 = Leer.leerTexto("Nom del segon jugador: ");

            Long idJugador1 = buscarIdJugadorPerNom(laSesion, nomJugador1);
            Long idJugador2 = buscarIdJugadorPerNom(laSesion, nomJugador2);

            if (idJugador1 == null || idJugador2 == null) {
                System.out.println("Un o ambdós jugadors no existeixen.");
            } else {
                Jugador jugador1 = laSesion.get(Jugador.class, idJugador1);
                Jugador jugador2 = laSesion.get(Jugador.class, idJugador2);

                if (jugador1.getElEquip() != null || jugador2.getElEquip() != null) {
                    System.out.println("Un dels jugadors ja pertany a un equip.");
                } else {
                    // Crea l'equip i associa els jugadors
                    Equip nouEquip = new Equip(jugador1, jugador2);
                    laSesion.persist(nouEquip);

                    jugador1.setElEquip(nouEquip);
                    jugador2.setElEquip(nouEquip);

                    System.out.println("Equip creat correctament!");
                }
            }

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en crear l'equip: " + e.getMessage());
        }
    }


    private void eliminaEquip() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Demanem el nom dels jugadors
            String nomJugador1 = Leer.leerTexto("Introdueix el nom del primer jugador de l'equip: ");
            String nomJugador2 = Leer.leerTexto("Introdueix el nom del segon jugador de l'equip: ");

            // Obtenim els IDs dels jugadors
            Long idJugador1 = buscarIdJugadorPerNom(laSesion, nomJugador1);
            Long idJugador2 = buscarIdJugadorPerNom(laSesion, nomJugador2);

            if (idJugador1 == null || idJugador2 == null) {
                System.out.println("Un o ambdós jugadors no existeixen.");
                return;
            }

            // Comprovem si formen un equip
            Query<Equip> query = laSesion.createQuery(
                "from Equip where jugador1_id = :idJugador1 and jugador2_id = :idJugador2", Equip.class
            );
            query.setParameter("idJugador1", idJugador1);
            query.setParameter("idJugador2", idJugador2);

            List<Equip> equips = query.list();

            if (equips.isEmpty()) {
                System.out.println("Els jugadors no formen un equip.");
            } else {
                Equip equip = equips.get(0);

                // Desvinculem els jugadors i eliminem l'equip
                laSesion.delete(equip);
                System.out.println("Equip eliminat correctament.");
            }

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en eliminar l'equip: " + e.getMessage());
        }
    }

    
    private void getPuntsEquip() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Demanem el nom dels jugadors
            String nomJugador1 = Leer.leerTexto("Introdueix el nom del primer jugador de l'equip: ");
            String nomJugador2 = Leer.leerTexto("Introdueix el nom del segon jugador de l'equip: ");

            // Obtenim els IDs dels jugadors
            Long idJugador1 = buscarIdJugadorPerNom(laSesion, nomJugador1);
            Long idJugador2 = buscarIdJugadorPerNom(laSesion, nomJugador2);

            if (idJugador1 == null || idJugador2 == null) {
                System.out.println("Un o ambdós jugadors no existeixen.");
                return;
            }

            // Comprovem si formen un equip
            Query<Equip> query = laSesion.createQuery(
                "from Equip where jugador1_id = :idJugador1 and jugador2_id = :idJugador2", Equip.class
            );
            query.setParameter("idJugador1", idJugador1);
            query.setParameter("idJugador2", idJugador2);

            List<Equip> equips = query.list();

            if (equips.isEmpty()) {
                System.out.println("Els jugadors no formen un equip.");
            } else {
                Equip equip = equips.get(0);

                // Obtenim els punts totals de l'equip
                int puntsTotals = equip.puntsTotalsEquip();
                System.out.println("Els punts totals de l'equip són: " + puntsTotals);
            }

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en obtenir els punts de l'equip: " + e.getMessage());
        }
    }
    
    private void menuTorneigs(){
        
        int opcio;
        do {
            System.out.println("========== OPCIONS ==========");
            System.out.println("1.- Afegir nou torneig");
            System.out.println("2.- Eliminar torneig");
            System.out.println("3.- Afegir resultats a torneig");
            System.out.println("4.- Eixir");
            
            opcio = Leer.leerEntero("");
            switch (opcio){
                case 1:
                    afegirTorneig();
                    break;
                case 2:
                    eliminaTorneig();
                    break;
                case 3:
                    resultatsTorneig();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Has d'inserir una opció correcta");
            }
            
        } while (opcio != 4);
    }        

    
    private void afegirTorneig() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Demanem les dades del torneig
            String paisTorneig = Leer.leerTexto("En quin país es jugarà el torneig?: ");

            String categoriaText = "";
            OUTER:
            while (true) {
                int categoria = Leer.leerEntero("Quina categoria de torneig és?: \n1.- MAJOR\n2.- P1\n3.- P2");
                switch (categoria) {
                    case 1:
                        categoriaText = "MAJOR";
                        break OUTER;
                    case 2:
                        categoriaText = "P1";
                        break OUTER;
                    case 3:
                        categoriaText = "P2";
                        break OUTER;
                    default:
                        System.out.println("Categoria incorrecta. Introdueix una opció vàlida.");
                        break;
                }
            }

            // Creem l'objecte torneig i el guardem
            Torneig torneig = new Torneig(paisTorneig, categoriaText);
            laSesion.persist(torneig); // Persistim el torneig en la base de dades

            System.out.println("Torneig creat correctament: " + torneig);

            laSesion.getTransaction().commit(); // Confirmem la transacció
        } catch (Exception e) {
            System.out.println("Error en afegir el torneig: " + e.getMessage());
        }
    }

    
    private void eliminaTorneig() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Comprovem si hi ha tornejos disponibles
            Query<Torneig> query = laSesion.createQuery("from Torneig", Torneig.class);
            List<Torneig> tornejos = query.list();

            if (tornejos.isEmpty()) {
                System.out.println("No hi ha tornejos registrats.");
            } else {
                System.out.println("========== LLISTA DE TORNEJOS ==========");
                for (Torneig torneig : tornejos) {
                    System.out.println("ID: " + torneig.getIdTorneig() + ", Categoria: " + torneig.getCategoria() + ", País: " + torneig.getPais());
                }

                int idTorneig = Leer.leerEntero("Introdueix l'ID del torneig que vols eliminar: ");
                Torneig torneig = laSesion.get(Torneig.class, idTorneig);

                if (torneig == null) {
                    System.out.println("El torneig amb ID " + idTorneig + " no existeix.");
                } else {
                    // Desvinculem les relacions
                    torneig.getEquips_inscrits().forEach(equip -> equip.getTorneigs().remove(torneig));
                    torneig.getPuntsRanking().forEach(punts -> punts.setTorneig(null));

                    // Eliminem el torneig
                    laSesion.delete(torneig);
                    System.out.println("Torneig eliminat correctament.");
                }
            }

            laSesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en eliminar el torneig: " + e.getMessage());
        }
    }

    private void resultatsTorneig() {
        try (Session laSesion = HibernateUtil.getSessionFactory().getCurrentSession()) {
            laSesion.getTransaction().begin();

            // Demanem la categoria i el país
            String categoria = Leer.leerTexto("Introdueix la categoria del torneig (MAJOR, P1, P2): ");
            String pais = Leer.leerTexto("Introdueix el país del torneig: ");

            Query<Torneig> query = laSesion.createQuery("from Torneig where categoria = :categoria and pais = :pais", Torneig.class);
            query.setParameter("categoria", categoria);
            query.setParameter("pais", pais);

            List<Torneig> tornejos = query.list();

            if (tornejos.isEmpty()) {
                System.out.println("No hi ha cap torneig amb aquesta categoria i país.");
            } else {
                Torneig torneig = tornejos.get(0);

                String nomJugador1 = Leer.leerTexto("Nom del primer jugador de l'equip: ");
                String nomJugador2 = Leer.leerTexto("Nom del segon jugador de l'equip: ");

                Long idJugador1 = buscarIdJugadorPerNom(laSesion, nomJugador1);
                Long idJugador2 = buscarIdJugadorPerNom(laSesion, nomJugador2);

                if (idJugador1 == null || idJugador2 == null) {
                    System.out.println("Un o ambdós jugadors no existeixen.");
                } else {
                    Jugador jugador1 = laSesion.get(Jugador.class, idJugador1);
                    Jugador jugador2 = laSesion.get(Jugador.class, idJugador2);

                    if (jugador1.getElEquip() == null || jugador2.getElEquip() == null || !jugador1.getElEquip().equals(jugador2.getElEquip())) {
                        System.out.println("Els jugadors no formen un equip.");
                    } else {
                        // Mostrem els premis del torneig
                        torneig.mostrarPremis();

                        String posicio = Leer.leerTexto("Indica la posició (Guanyador, Finalista, Semifinalista, etc.): ");

                        Punts_Ranking punts = torneig.getPuntsRanking().stream()
                                .filter(p -> p.getCategoria().equalsIgnoreCase(posicio))
                                .findFirst()
                                .orElse(null);

                        if (punts == null) {
                            System.out.println("Posició no vàlida per a aquest torneig.");
                        } else {
                            // Assignem els punts als jugadors
                            jugador1.setPunts(jugador1.getPunts() + punts.getPunts());
                            jugador2.setPunts(jugador2.getPunts() + punts.getPunts());

                            laSesion.update(jugador1);
                            laSesion.update(jugador2);

                            System.out.println("S'han assignat " + punts.getPunts() + " punts a cada jugador de l'equip.");
                        }
                    }
                }
            }

            laSesion.getTransaction().commit();
        }
    }
}
