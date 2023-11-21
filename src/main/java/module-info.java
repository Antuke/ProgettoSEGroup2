module com.segroup2.progettosegroup2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.segroup2.progettosegroup2 to javafx.fxml;
    opens com.segroup2.progettosegroup2.Controllers to javafx.fxml;
    exports com.segroup2.progettosegroup2;
    exports com.segroup2.progettosegroup2.Actions;
    exports com.segroup2.progettosegroup2.Triggers;
    opens com.segroup2.progettosegroup2.Actions to javafx.fxml;
}