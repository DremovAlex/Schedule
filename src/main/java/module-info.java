module com.oriseus.schedule {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires java.base;
	requires javafx.graphics;
	requires java.desktop;


    opens com.oriseus.schedule to javafx.fxml;
    opens com.oriseus.schedule.controller to javafx.fxml;
    opens com.oriseus.schedule.model to javafx.fxml;
    
    exports com.oriseus.schedule;
    exports com.oriseus.schedule.controller;
    exports com.oriseus.schedule.model;
}
