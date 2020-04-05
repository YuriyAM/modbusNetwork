package modbus_server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.event.EventHandler;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerGUI extends Application {
    // Main stage position coordinates
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(final Stage primaryStage) throws Exception {

        // Load UI settings via FXML file
        String guiFile = "server_ui.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(guiFile));

        // Window dragging setup
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
                primaryStage.setOpacity(0.9);
            }
        });
        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setOpacity(1);
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });// End of window dragging setup

        // Main Scene declaration and setup
        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);

        // Main stage initial setup
        primaryStage.setTitle("MODBUS SERVER");
        primaryStage.setScene(mainScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}