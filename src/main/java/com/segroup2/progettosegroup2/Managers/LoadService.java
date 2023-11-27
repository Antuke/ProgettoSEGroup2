package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class LoadService extends Service<ObservableList<Rule>> {

    private String saveFile;

    /**
     * @param saveFile Path del file di salvataggio
     */
    public LoadService(String saveFile) {

        this.saveFile = MainApplication.class.getResource(saveFile).getPath();

    }


    @Override
    protected Task<ObservableList<Rule>> createTask() {
        return new Task<ObservableList<Rule>>() {
            @Override
            protected ObservableList<Rule> call()  {
                /* se il saveFile è null, quindi il file di salvataggio non c'è / non è stato trovato
                 restituisco una lista vuota */
                if (saveFile == null) {
                    return FXCollections.observableArrayList();
                }
                ArrayList<Rule> read = new ArrayList<>();
                ObservableList<Rule> ret = FXCollections.observableArrayList();

                File file = new File(saveFile);
                /* gestico i casi in cui il file non esiste / è vuoto */
                if (!file.exists()) {
                    return FXCollections.observableArrayList();
                }

                if (file.length() == 0) {
                    return FXCollections.observableArrayList();
                }


                try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveFile)))) {
                    read = (ArrayList<Rule>) ois.readObject();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ret.addAll(read);
                return ret;

            }


        };
    }
}
