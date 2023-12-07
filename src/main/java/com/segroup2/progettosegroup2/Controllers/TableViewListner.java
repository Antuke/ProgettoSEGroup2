package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Managers.CounterListnerInterface;
import javafx.scene.control.TableView;

import java.io.Serializable;

public class TableViewListner implements CounterListnerInterface, Serializable {

    private TableView table;

    public TableViewListner(TableView table) {
        this.table = table;
    }

    @Override
    public void update() {
        table.refresh();
    }
}
