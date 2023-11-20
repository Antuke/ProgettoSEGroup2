module com.segroup2.progettosegroup2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.segroup2.progettosegroup2 to javafx.fxml;
    opens com.segroup2.progettosegroup2.Controllers to javafx.fxml;
    exports com.segroup2.progettosegroup2;
}