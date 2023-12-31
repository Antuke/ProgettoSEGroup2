package com.segroup2.progettosegroup2.Triggers;

import java.io.File;

public class TriggerFileSize implements TriggerInterface {
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

    @Override
    public boolean add(TriggerInterface t) {
        return false;
    }

    @Override
    public boolean remove(TriggerInterface t) {
        return false;
    }

    public String toString(){
        return "Check file size: " + this.file.toString() + " Greater or equals to " + size + "bytes";
    }


}

