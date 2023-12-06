package com.segroup2.progettosegroup2.Managers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che si occupa della persistenza dell'applicazione
 */
public class PersistanceManager<T> {
    private static final String DEFAULT_FILE_NAME = "save.bin";
    private static final Path DEFAULT_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "IfttGruppo2Save", "saves");
    private final Path directoryPath;
    private final Path filePath;

    /**
     * Scelta della posizione del file per la gestione della persistenza
     * @param fileName Nome del file che si intente salvare. Se nullo o vuoto verrà impostato il nome di default
     * @param directory Cartella dentro la quale salvare il file. Se nulla verrà impostata la cartella di default
     */
    public PersistanceManager(String fileName, Path directory) {
        directoryPath= (directory!=null) ? directory : DEFAULT_DIRECTORY_PATH;
        String nameOfFile= (fileName!=null && !fileName.isEmpty()) ? fileName : DEFAULT_FILE_NAME;
        filePath= directoryPath.resolve(nameOfFile);
    }

    /**
     * Scelta della posizione del file per la gestione della persistenza<br>
     * Richiama il costruttore principale passando il secondo parametro nullo: {@link #PersistanceManager(String, Path)}
     * @param fileName Nome del file che si intente salvare. Se nullo o vuoto verrà impostato il nome di default
     */
    public PersistanceManager(String fileName){
        this(fileName, null);
    }

    /**
     * Scelta della posizione del file per la gestione della persistenza<br>
     * Richiama il costruttore principale passando entrambi i parametri nulli: {@link #PersistanceManager(String, Path)}
     */
    public PersistanceManager(){
        this(null, null);
    }

    /**
     * @return Il path completo della posizione del file da gestire
     */
    public Path getFilePath(){
        return(filePath);
    }

    /**
     * Creazione del file specificato nella cartella specificata se non esistono
     */
    private void createFile(){
        try {
            Files.createDirectories(directoryPath);
            if ( !Files.exists(filePath) )
                Files.createFile(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(List<T> items) {
        this.createFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( filePath.toString() ))) {
            oos.writeObject(new ArrayList<>(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return La collezione letta dal file se il file esiste altrimenti una lista vuota
     */
    public List<T> load() {
        if( filePath.toFile().length()!=0 ) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream( filePath.toString() ))) {
                return (ArrayList<T>) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
