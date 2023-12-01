package com.segroup2.progettosegroup2.Actions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class ActionMoveFile implements ActionInterface, Serializable {

    private final File fileToMove;
    private final File destSource;

    public ActionMoveFile(File fileToMove, File destSource) {
        this.fileToMove = fileToMove;
        this.destSource = new File(destSource.toString()+"/"+fileToMove.getName());
    }

    @Override
    public boolean execute() {
        if (!fileToMove.exists()) {
            String message = null;
            if (!fileToMove.exists())
                message = "Il file sorgente '" + fileToMove.getName() + "' non esiste";
            throw new RuntimeException(message);
        }
        try {
            Files.move(fileToMove.toPath(), destSource.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Errore nello spostare il file nella cartella " + destSource.getName());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spostamento del file " + fileToMove.getName() + " nella cartella " + destSource.toString();
    }
}
