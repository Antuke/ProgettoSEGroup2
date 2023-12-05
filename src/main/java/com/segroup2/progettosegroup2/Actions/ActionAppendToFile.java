package com.segroup2.progettosegroup2.Actions;

import java.io.*;

/**
 * Permette di scrivere in append su un file di testo
 */
public class ActionAppendToFile implements ActionInterface, Serializable {

    private String toAppend;
    private File file;

    public ActionAppendToFile(String toAppend, File file) {
        this.toAppend = toAppend;
        this.file = file;
    }
    /**
     * @return boolean : True quando è riuscita a scrivere su file
     * @Throws RuntimeException : Quando non trova più il file di testo su cui scrivere (è stato spostato/cancellato)
     * */
    @Override
    public boolean execute() throws RuntimeException{
        if(!file.exists()){
            throw new RuntimeException("File non è stato trovato!");
        }
        try (FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(toAppend);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.err.println("Non sono riuscito a scrivere sul file: " + e.getMessage());
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

    public String toString(){
        return "Scrittura di " + toAppend + "in append al file " + file.toString();
    }
}
