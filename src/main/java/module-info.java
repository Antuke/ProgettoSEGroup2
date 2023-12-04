module com.segroup2.progettosegroup2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires commons.io;


    opens com.segroup2.progettosegroup2.Rules to javafx.base;
    opens com.segroup2.progettosegroup2 to javafx.fxml;
    opens com.segroup2.progettosegroup2.Controllers to javafx.fxml;
    exports com.segroup2.progettosegroup2;
    exports com.segroup2.progettosegroup2.Actions;
    exports com.segroup2.progettosegroup2.Triggers;
    opens com.segroup2.progettosegroup2.Actions to javafx.fxml;
    exports com.segroup2.progettosegroup2.Controllers;
}