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
    public StoreService(ObservableList<Rule> toSave , String whereToSave) {
        this.toSave = toSave;
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
            protected ObservableList<Rule> call() throws Exception {
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
