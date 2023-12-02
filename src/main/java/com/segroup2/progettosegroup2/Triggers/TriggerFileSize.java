package com.segroup2.progettosegroup2.Triggers;

import java.io.File;
import java.io.Serializable;

public class TriggerFileSize implements TriggerInterface, Serializable {
    private final File file;
    private final int size;

    public TriggerFileSize(File file, int size) {
        this.file = file;
        this.size = size;
    }

    @Override
    public boolean check() {
        // Restituisco true se il file esiste e la sua dimensione è maggiore o uguale a quella specificata
        return file.exists() && file.length() >= size;
    }

    public String toString(){
        return "Trigger verifica dimensione file: " + this.file.toString() + "pari a " + size + "bytes";
    }


}

