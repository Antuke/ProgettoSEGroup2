# Simple IFTT by Gruppo2 Unisa

## Descrizione
Questa è un'applicazione JavaFX che permette di creare e gestire regole di automazione basate sul paradigma "If This Then That" (IFTTT). L'utente può definire dei **Trigger** (eventi scatenanti) e associare ad essi delle **Azioni** da eseguire quando il trigger si attiva.

## Funzionalità

### Trigger Disponibili
L'applicazione supporta i seguenti tipi di trigger:
*   **Data e Ora:**
    *   Data specifica
    *   Giorno del mese
    *   Giorno della settimana
    *   Orario specifico
*   **File System:**
    *   Esistenza di un file
    *   Dimensione di un file
*   **Contatori:**
    *   Confronto tra un contatore e un valore
    *   Confronto tra due contatori
*   **Programmi Esterni:**
    *   Verifica dello stato di uscita di un programma

### Azioni Disponibili
Quando un trigger si attiva, è possibile eseguire le seguenti azioni:
*   **File System:**
    *   Aggiungi testo a un file (Append)
    *   Copia file
    *   Elimina file
    *   Sposta file
*   **Audio/Video:**
    *   Riproduzione audio
*   **Interfaccia Utente:**
    *   Mostra una finestra di dialogo (Dialog Box)
*   **Esecuzione:**
    *   Esegui un programma esterno
*   **Contatori:**
    *   Imposta valore contatore
    *   Aggiungi costante a contatore
    *   Somma tra contatori
*   **Sequenze:**
    *   Esecuzione di una sequenza di azioni

## Prerequisiti
*   Java Development Kit (JDK) 19 o superiore
*   Maven

## Installazione e Esecuzione
Per avviare l'applicazione, è consigliato utilizzare il Maven Wrapper incluso nel progetto.

**Su Windows (PowerShell/CMD):**
```powershell
.\mvnw clean javafx:run
```

**Su Linux/macOS:**
```bash
./mvnw clean javafx:run
```

Se si preferisce usare una installazione globale di Maven, assicurarsi che `mvn` sia nel PATH di sistema.

## Eseguibili Pre-compilati
Nella cartella `SCRUM ARTIFACTS AND PROJ FILES` sono disponibili dei file JAR eseguibili generati durante i vari sprint di sviluppo:

*   **Sprint 1:** `SCRUM ARTIFACTS AND PROJ FILES/Prima Sprint/Primo Incremento.jar`
*   **Sprint 2:** `SCRUM ARTIFACTS AND PROJ FILES/Seconda Sprint/Secondo incremento.jar`
*   **Sprint 3:** `SCRUM ARTIFACTS AND PROJ FILES/Terza Sprint/ProgettoSEGroup2.jar`
*   **Ultima release** `Final.jar`
È possibile eseguire questi file direttamente se si dispone di un ambiente Java configurato correttamente (assicurarsi che la versione di Java sia compatibile, ovvero Java 19+).

Esempio di esecuzione da riga di comando:
```bash
java -jar "SCRUM ARTIFACTS AND PROJ FILES/Terza Sprint/ProgettoSEGroup2.jar"
```

## Link Utili
*   **Trello:** [Board di Progetto](https://trello.com/b/PUTic1v3/work-project)
*   **GitHub:** [Repository](https://github.com/Antuke/ProgettoSEGroup2)
