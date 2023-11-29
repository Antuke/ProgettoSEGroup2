package com.segroup2.progettosegroup2.Actions;

import java.io.*;


public class ActionAppendToFIle implements ActionInterface, Serializable {

    private String toAppend;
    private File file;

    public ActionAppendToFIle(String toAppend,File file) {
        this.toAppend = toAppend;
        this.file = file;
    }

    @Override
    public boolean execute() {
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(toAppend);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.err.println("Non sono riuscito a scrivere sul file: " + e.getMessage());
        }

        return true;
    }

    public String toString(){
        return "Scrittura di " + toAppend + "in append al file " + file.toString();
    }
}
