package com.segroup2.progettosegroup2.Managers;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.Rules.Rule;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StoreService extends Service<ObservableList<Rule>> {

    private ObservableList<Rule> toSave;
    private String saveFile;

    /**
     *
     * @param toSave lista osservabile da salvare su file
     * @param whereToSave path nelle risorse dove salvare il file
     */
    public StoreService(ObservableList<Rule> toSave , String whereToSave) {
        this.toSave = toSave;
        // se il file non viene trovato viene comunque salvata la stringa
        try{
            this.saveFile = MainApplication.class.getResource(whereToSave).getPath();
        }catch(Exception e){
            this.saveFile = whereToSave;
        }
    }

    @Override
    protected Task<ObservableList<Rule>> createTask() {
        return new Task<ObservableList<Rule>>(){
            @Override
            protected ObservableList<Rule> call() {
                /*ObservableList non è serializzabile, quindi viene prima convertito*/
                ArrayList<Rule> pList = new ArrayList<>(toSave);

                try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveFile)))){
                    oos.writeObject(pList);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                return null;

            }

        };
    }
}
