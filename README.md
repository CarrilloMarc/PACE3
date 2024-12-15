Gestió de Torneigs, Equips i Jugadors
Descripció del Projecte
Aquest projecte és una aplicació de gestió de torneigs, equips i jugadors utilitzant Java i el framework Hibernate per a la persistència de dades. Permet gestionar entitats com ara jugadors, equips i tornejos, així com establir relacions entre aquestes entitats i realitzar operacions CRUD.

Funcionalitats
1. Gestió de Jugadors
Afegir nou jugador: Introdueix el nom, nacionalitat i punts inicials d'un jugador i l'emmagatzema a la base de dades.
Editar jugador: Permet modificar el nom, nacionalitat o punts d'un jugador existent.
Mostrar el ranking: Visualitza una llista de jugadors ordenats de més punts a menys.
Eliminar jugador: Elimina un jugador si no té relacions actives amb equips o tornejos.
2. Gestió d'Equips
Crear nou equip: Forma un equip amb dos jugadors lliures (sense equip) i els vincula a l'equip.
Eliminar equip: Permet eliminar un equip després de desvincular els jugadors associats.
Consultar punts totals d'un equip: Mostra la suma dels punts dels dos jugadors d'un equip.
3. Gestió de Tornejos
Afegir nou torneig: Introdueix la informació d'un torneig, incloent-hi el país i la categoria (MAJOR, P1, P2), i crea automàticament els punts associats per a cada posició.
Eliminar torneig: Elimina un torneig després de desvincular-lo dels equips i puntuacions associades.
Afegir resultats de torneig: Permet assignar puntuacions a jugadors d'un equip segons la posició en el torneig.
Estructura del Projecte
Classes Principals
Jugador:

Representa un jugador amb nom, nacionalitat i punts.
Relacions: Equip (1-N), Jugador (relació bidireccional 1-1 com a company d'equip).
Equip:

Representa un equip format per dos jugadors.
Relacions: Torneig (N-M).
Torneig:

Representa un torneig amb una categoria (MAJOR, P1, P2) i un país.
Genera punts automàticament segons la categoria.
Punts_Ranking:

Defineix la puntuació per categoria dins d'un torneig.
Ranking:

Gestió opcional de jugadors en un ranking.
Relacions
Jugador ↔ Equip:
Cada jugador pot pertànyer a un equip.
Cada equip té dos jugadors.
Equip ↔ Torneig:
Un equip pot participar en diversos tornejos.
Un torneig pot tenir diversos equips inscrits.
Torneig ↔ Punts_Ranking:
Cada torneig té una llista de puntuacions associades segons les posicions.
Configuració del Projecte
Requisits
Java 8 o superior
Maven
Hibernate
MySQL
Configuració de la Base de Dades
Crear una base de dades MySQL:
sql
Copy code
CREATE DATABASE gestio_torneigs;
Configurar hibernate.cfg.xml amb les credencials de la base de dades.
Execució
Clonar aquest repositori:
bash
Copy code
git clone https://github.com/usuari/gestio-torneigs.git
Executar el projecte des de l'entorn IDE o línia de comandes.
Exemples de Funcionalitat
Afegir un nou jugador
plaintext
Copy code
Introdueix el nom del jugador: Joan
Introdueix la nacionalitat del jugador: Espanya
Introdueix els punts inicials: 1200
Jugador afegit correctament!
Mostrar el ranking
plaintext
Copy code
========== RANKING DE JUGADORS ==========
1. Nom: Joan, Nacionalitat: Espanya, Punts: 1200
2. Nom: Maria, Nacionalitat: França, Punts: 1100
3. Nom: Marc, Nacionalitat: Alemanya, Punts: 1000
Crear un nou torneig
plaintext
Copy code
En quin país es jugarà el torneig?: Espanya
Quina categoria de torneig és?:
1.- MAJOR
2.- P1
3.- P2
>> 1
Torneig creat correctament: Torneig{id=1, pais='Espanya', categoria='MAJOR'}
Contribucions
Les contribucions són benvingudes! Si tens suggeriments o trobes errors, crea un issue o envia un pull request.

Autor
Marc
