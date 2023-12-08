package com.segroup2.progettosegroup2.Controllers;

import com.segroup2.progettosegroup2.Managers.ListenerInterface;
import javafx.scene.control.TableView;

public class TableViewListener implements ListenerInterface {

    private TableView<?> table;

    public TableViewListener(TableView<?> table) {
        this.table = table;
    }

    @Override
    public void update() {
        table.refresh();
    }
}
