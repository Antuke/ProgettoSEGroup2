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

    public LoadService(String saveFile){
        try{
            this.saveFile = MainApplication.class.getResource(saveFile).getPath();
        }catch(Exception e){
            this.saveFile = null;
        }
    }
    @Override
    protected Task<ObservableList<Rule>> createTask() {
        return new Task<ObservableList<Rule>>(){
            @Override
            protected ObservableList<Rule> call() throws Exception {
                if(saveFile == null) {
                    ObservableList<Rule> nullRet = FXCollections.observableArrayList();
                    return nullRet;
                }
                ArrayList<Rule> read =new ArrayList<>();
                ObservableList<Rule> ret = FXCollections.observableArrayList();

                File file = new File(saveFile);
                if(!file.exists()){
                    return FXCollections.observableArrayList();
                }

                if (file.length() == 0) {
                    return FXCollections.observableArrayList();
                }


                try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveFile)))){
                    read = (ArrayList<Rule>)ois.readObject();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                ret.addAll(read);
                return ret;

            }


        };
    }
}
