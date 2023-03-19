# SA4E, Winter 2022

Abgabe zu Übungsblatt 3, Sebastian Irsch, 1337932

## zu Aufgabe 1

Im Rahmen der ersten Aufgabe soll ein Prototyp einer Web-Anwendung entwickelt werden, der die
Zahlenrätsel und Lösungen protokolliert. Die Web-Anwendung soll unter Verwendung des
Spring-Frameworks mit Spring Boot erstellt werden und als Docker-Image ausgeliefert werden.

### Implementierungsbeschreibung

Das Grundgerüst des Maven-Projekts für die Web-Anwendung wurde mit dem Projektgenerator
spring initializr auf https://start.spring.io generiert. Dabei wurde Java 17 und Spring Boot 2.7.9
gewählt. Für die Web-UI wurde das Vaadin-Framework als Dependency hinzugefügt.

Die Anwendung verbindet sich mit einem MQTT-Broker und abonniert dort die benötigten Topics. Dazu
wird der MQTT-Client Paho verwendet. Da der Paho `MqttClient` eine reichhaltige Schnittstelle 
bietet, deren überwiegende Funktionalität von der Anwendung nicht benötigt wird, wurde eine Klasse 
`Client` mit einfacher API als Fassade für den Paho-Client bereitgestellt. Zunächst wurde eine
Apache-Camel-basierte Anbindung in Betracht gezogen. Weil es sich jedoch um eine Webanwendung
handelt und die eingegebenen Zugangsdaten nur für die aktuelle Session verwendet werden sollen,
wurde der Camel-basierte Ansatz als zu aufwändig erachtet und nicht weiter verfolgt.

Damit die Anwendung / das Docker-Image nicht mit hart kodierten Zugangsdaten ausgeliefert werden
muss, wurde eine Eingabemaske für die Eingabe der Zugangsdaten in der Benutzeroberfläche vorgesehen.
Die Zugangsdaten werden in einem Objekt der Klasse `Settings` von der View an den Client
weitergegeben.

Die Benutzeroberfläche ist nach dem Model-View-Presenter-Muster aufgebaut. Die Klasse `PuzzlesView`
stellt die grafische Benutzeroberfläche bereit. Dazu verwendet sie das Vaadin-Framework. Mit dem
Vaadin-Framework können grafische Benutzeroberflächen für Web-Anwendungen in Java programmiert
werden. Die Struktur der Oberfläche wird in Java, analog zur klassischen GUI-Programmierung
hergestellt. Das Framework spiegelt die Komponenten-Struktur in den Browser und rendert dort die UI.
Ereignisse, die durch Benutzerinteraktion ausgelöst werden, werden durch das Framework zum Server
übertragen, sodass dort ein Event-Listener ausgeführt werden kann. Die Benutzeroberfläche stellt
Eingabefelder für die Zugangsdaten zum MQTT-Server bereit. Ferner verfügt sie über zwei Buttons
zum Herstellen und Trennen der Verbindung und über einen Ausgabebereich, in dem die Zahlenrätsel und
Lösungen protokolliert werden.

Die Steuerung des Verhaltens der `PuzzlesView` wird durch den Presenter `PuzzlesPresenter`
übernommen. Dieser reagiert auf die Button-Clicks durch Herstellen oder Trennen der Verbindung zum
MQTT-Server. Bei bestehender Verbindung werden die Zahlenrätsel (Datenstruktur:
`CommonSolvePuzzleRequest`) und Lösungen (`CommonSolvePuzzleResponse`) an die View zur Ausgabe im
Protokollbereich übergeben.

Um die Anwendung als Docker-Image bereitzustellen, kann das Spring-Boot-Maven-Plugin verwendet
werden. Der Befehl
`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=sirsch/puzzles-webapp` erzeugt das
Image auf dem lokalen Rechner. Mit dem Befehl `docker push NAME[:TAG]` wird das Image anschließend
auf Docker Hub hochgeladen, damit es darüber verfügbar ist. Dazu wird ein entsprechendes
Benutzerkonto bei Docker Hub benötigt.

### Verwendung

Weil die Anwendung als Docker-Image verfügbar ist, kann sie einfach mittels Docker run gestartet
werden. Der genaue Befehl dazu lautet:
`docker run -p 8080:8080 sirsch/puzzles-webapp:0.0.1-SNAPSHOT`. Hier ist anzumerken, dass eine
Port-Weiterleitung für Port 8080, auf dem die Anwendung per http erreichbar ist, benötigt wird.

#### Verbindung mit einem MQTT-Server herstellen
Die Web-UI stellt ein Formular bereit, in das die Zugangsdaten des MQTT-Servers eingetragen werden
sollen. Im Anschluss daran kann die Verbindung durch Klick auf den Button 'verbinden' hergestellt
werden.

| Formularfeld    | Bedeutung                                                           |
|-----------------|---------------------------------------------------------------------|
| MQTT-Server-URI | URI des Servers: `tcp://<hostname>:<port>`                          |
| Client-ID       | Die für die Verbindung zu verwendende Client-ID                     |
| Benutzername    | (optional) Der zu verwendende Benutzername                          |
| Passwort        | (optional) Das zu verwendende Passwort                              |
| Topics          | Die zu abonnierenden Topics, normalerweise 'Zahlenraetsel, Loesung' |
| QOS             | Das zu verwendende QOS, normalerweise '2'                           |

Nachdem die Verbindung hergestellt wurde, werden alle ab diesem Zeitpunkt übertragenen
MQTT-Nachrichten für Zahlenrätsel und Lösungen auf der Benutzeroberfläche ausgegeben.

#### Verbindung trennen

Durch Klicken auf den Button 'trennen' kann eine Verbindung zum MQTT-Server getrennt werden.
