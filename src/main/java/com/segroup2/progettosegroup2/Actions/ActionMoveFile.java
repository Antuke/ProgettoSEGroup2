package com.segroup2.progettosegroup2.Actions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
/**
 * Permette di spostare un file da una cartella ad un altra
 * */
public class ActionMoveFile implements ActionInterface, Serializable {

    private final File fileToMove;
    private final File destSource;

    public ActionMoveFile(File fileToMove, File destSource) {
        this.fileToMove = fileToMove;
        this.destSource = new File(destSource.toString()+"/"+fileToMove.getName());
    }

    /**
     *
     * @throws RuntimeException quando il file è stato spostato/cancellato e/o quando la cartella non è stata cancellata/spostata
     * */
    @Override
    public boolean execute() {
        if (!fileToMove.exists()) {
            String message = null;
            if (!fileToMove.exists())
                message = "Source file '" + fileToMove.getName() + "' doesn't exist";
            throw new RuntimeException(message);
        }
        try {
            Files.move(fileToMove.toPath(), destSource.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error moving the file to the folder. " + destSource.getName());
        }
        return true;
    }

    @Override
    public boolean add(ActionInterface a) {
        return false;
    }

    @Override
    public boolean remove(ActionInterface a) {
        return false;
    }

    @Override
    public String toString() {
        return "Moving file " + fileToMove.getName() + " to folder " + destSource.toString();
    }
}
