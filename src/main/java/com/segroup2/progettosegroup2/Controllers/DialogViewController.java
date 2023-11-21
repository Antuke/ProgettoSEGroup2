package com.segroup2.progettosegroup2.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class DialogViewController {



    @FXML
    void btnClose(MouseEvent event) {
        Platform.exit();
    }

}

