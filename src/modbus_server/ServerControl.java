package modbus_server;

import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;

import com.jfoenix.controls.*;

import de.re.easymodbus.server.ModbusServer;
import de.re.easymodbus.server.ICoilsChangedDelegator;

public class ServerControl implements ICoilsChangedDelegator{

    @FXML private Label firstBtnLabel;
    @FXML private Label secondBtnLabel;
    @FXML private Rectangle firstBtn;
    @FXML private Rectangle secondBtn;

    @FXML private JFXToggleButton firstToggle;
    @FXML private JFXToggleButton secondToggle;
    @FXML private JFXButton startServerBtn;
    @FXML private JFXButton stopServerBtn;
    @FXML private JFXButton exitBtn;

    @FXML private JFXComboBox<String> ipComboBox;
    @FXML private JFXTextField portField;
    @FXML private Label headLabel;
    @FXML private Label stopLabel;

    // Label fade out transition declaration
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(3000));
    // Modbus server declaration
    private ModbusServer server = new ModbusServer();
    
    @FXML   // GUI additional initial setup
    public void initialize() {

        // Set handler for coils update event
        server.setNotifyCoilsChanged(this);

        // Automatically generate port number
        int port = ServerRestriction.getPort();
        headLabel.setText(port == 0 ? "NO AVAILABLE PORTS" : "MODBUS SERVER");
        portField.setText(Integer.toString(port));

        // Initial setup and fade transition setup for stopLabel
        stopLabel.setText("");
        // If stop button is pressed, show label and than make it disappear
        fadeOut.setNode(stopLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);

        // Get IPv4 addresses of every NIC and add them to ipComboBox
        try {
            for (String ip : IpAdresses.GetLocalIP())
                ipComboBox.getItems().add(ip);
            ipComboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            headLabel.setText(e.getMessage());
        }

    } // End of GUI additional initial setup

    // Change GUI after clicking "START"
    private void startServerGUI() {
        headLabel.setText("SERVER IS RUNNING");
        firstToggle.setDisable(false);
        secondToggle.setDisable(false);
        portField.setEditable(false);
        startServerBtn.setDisable(true);
        stopServerBtn.setDisable(false);
    }

    // Change GUI after clicking "STOP"
    private void stopServerGUI() {
        headLabel.setText("MODBUS SERVER");
        firstToggle.setDisable(true);
        secondToggle.setDisable(true);
        portField.setEditable(true);
        startServerBtn.setDisable(false);
        stopServerBtn.setDisable(true);
    }

    // Displaying toggle button selection
    private void setToggle(Rectangle rect, Label label) {
        String greenColor = "#009688";
        rect.setFill(Color.web(greenColor));

        // Using Platform.runLater because this method can be called
        // outside of JavaFX thread while handling coils changes
        Platform.runLater(() -> label.setText("ON"));
    }

    // Displaying toggle button deselection
    private void unsetToggle(Rectangle rect, Label label) {
        String redColor = "#f44336";
        rect.setFill(Color.web(redColor));

        // Using Platform.runLater because this method can be called
        // outside of JavaFX thread while handling coils changes
        Platform.runLater(() -> label.setText("OFF"));
    }

    // First toggle event handler
    public void firstToggleAction(ActionEvent event) {
        if (firstToggle.isSelected()) {
            setToggle(firstBtn, firstBtnLabel);
            ServerLogic.setCoil(server, 1);
        } else {
            unsetToggle(firstBtn, firstBtnLabel);
            ServerLogic.unsetCoil(server, 1);
        }
    }

    // Second toggle event handlers
    public void secondToggleAction(ActionEvent event) {
        if (secondToggle.isSelected()) {
            setToggle(secondBtn, secondBtnLabel);
            ServerLogic.setCoil(server, 2);
        } else {
            unsetToggle(secondBtn, secondBtnLabel);
            ServerLogic.unsetCoil(server, 2);
        }
    }

    // Start button event handler
    public void startServer(ActionEvent event) {
        try {
            ServerLogic.serverSetup(server, portField.getText());
            ServerLogic.startServer(server);
            startServerGUI();

        } catch (Exception e) {
            headLabel.setText(e.getMessage());
        }
        
    }

    // Stop button click event handler
    public void stopServer(ActionEvent event) {
        // Server stopping is not working properly
        // try {
        //     ServerLogic.StopServer(server);
        //     StopServerGUI();
        // } catch (ThreadDeath e) {
        //     System.out.println(e);
        // }
        System.out.println(server.coils[0] + " " + server.coils[1]);
        stopLabel.setText("Server stopping is not supported");
        fadeOut.playFromStart();
    }

    // Exit button click event handler
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    // Change of coils event handler
    public void coilsChangedEvent(){
        firstToggle.setSelected(server.coils[1]);
        secondToggle.setSelected(server.coils[2]);
        firstToggleAction(new ActionEvent());
        secondToggleAction(new ActionEvent());
    }
}