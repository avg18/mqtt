
## Install ##
* Mosquitto (oder sonstigen Broker) installieren und starten (Port 1883)
* In Projekt *Temperatursensor*:
    * gradle jar
    * java -jar build/libs/temperatursensor-1.0.jar
* In Projekt *Heizung*:
    * gradle jar
    * java -jar build/libs/heizung-1.0.jar
* In Projekt *Client* (Interaktive Konsolenanwendung):
    * gradle jar
    * java -jar build/libs/client-1.0.jar

## Interaktion ##
        * "Enter" Taste: refresht die gemessene Temperatur und Heizungsstatus auf stdout
        * "T" Taste: Erlaubt Eingabe neuer Richttemperatur, Enter zum Abschicken
        * "X" Taste: Beendet Programm

## Szenario ##
*  Raspberry Pi​
    * Leitet Daten weiter ​
* Temperatursensor​
    * Sendet aktuelle Temperaturdaten​
* Heizung​
    * Empfängt Temperaturdaten​
    * Empfängt neue Richtwerte​
    * Sobald Temperatur unter Richtwert, geht Heizung an​
    * Sendet ob Heizung an/aus​
* User (Bewohner) mit Smartphone-Client​
    * Empfängt ob Heizung an/aus​
    * Empfängt Temperaturdaten​
    * Kann neuen Richtwert senden​

## Fehlerszenarien #
1) MQTT Server läuft nicht:  
    Die Heizung empfängt keine neuen Temperaturdaten und wechselt ihren Status nicht mehr.
    Der Client kann keine neue Richttemperatur einstellen.