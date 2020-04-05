package modbus_client;

import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.animation.*;

import com.jfoenix.controls.*;

import de.re.easymodbus.modbusclient.ModbusClient;

public class ClientControl {

    @FXML private Label firstBtnLabel;
    @FXML private Label secondBtnLabel;
    @FXML private Rectangle firstBtn;
    @FXML private Rectangle secondBtn;

    @FXML private JFXToggleButton firstToggle;
    @FXML private JFXToggleButton secondToggle;
    @FXML private JFXButton connectionBtn;
    @FXML private JFXButton exitBtn;

    @FXML private JFXTextField ipField;
    @FXML private JFXTextField portField;
    @FXML private Label headLabel;

    // Modbus client declaration
    private ModbusClient client = new ModbusClient();
    // Declare Timeline to continuously update server info
    Timeline timeline = new Timeline();

    @FXML   // GUI additional initial setup
    public void initialize() {

        /* TODO */

    } // End of GUI additional initial setup
 

    // Displaying toggle button selection
    private void setToggle(Rectangle rect, Label label) {
        String greenColor = "#009688";
        rect.setFill(Color.web(greenColor));
        label.setText("ON");
    }

    // Displaying toggle button deselection
    private void unsetToggle(Rectangle rect, Label label) {
        String redColor = "#f44336";
        rect.setFill(Color.web(redColor));
        label.setText("OFF");
    }
    
    // First toggle event handler
    public void firstToggleAction(ActionEvent event) {
        if (firstToggle.isSelected()) {
            setToggle(firstBtn, firstBtnLabel);
            ClientLogic.setCoil(client, 0);
        } else {
            unsetToggle(firstBtn, firstBtnLabel);
            ClientLogic.unsetCoil(client, 0);
        }
    }

    // Second toggle event handler
    public void secondToggleAction(ActionEvent event) {
        if (secondToggle.isSelected()) {
            setToggle(secondBtn, secondBtnLabel);
            ClientLogic.setCoil(client, 1);
        } else {
            unsetToggle(secondBtn, secondBtnLabel);
            ClientLogic.unsetCoil(client, 1);
        }
    }

    // Change UI after clicking "CONNECT"
    private void connectClientGUI() {
        String redColor = "#f44336";
        headLabel.setText("CLIENT IS CONNECTED");
        connectionBtn.setStyle("-fx-background-color: " + redColor);
        connectionBtn.setText("DISCONNECT");

        firstToggle.setDisable(false);
        secondToggle.setDisable(false);
        ipField.setEditable(false);
        portField.setEditable(false);
    }

    // Change UI after clicking "DISCONNECT"
    private void disconnectClientGUI() {
        String greenColor = "#009688";
        headLabel.setText("MODBUS CLIENT");
        connectionBtn.setStyle("-fx-background-color: " + greenColor);
        connectionBtn.setText("CONNECT");

        firstToggle.setDisable(true);
        secondToggle.setDisable(true);
        ipField.setEditable(true);
        portField.setEditable(true);
    }

    // Clicking button to connect or disconnect from server
    public void setConnection(ActionEvent event) {

        if (connectionBtn.getText().equals("CONNECT")) {
            try {
                ClientLogic.clientSetup(client, ipField.getText(), portField.getText());
                ClientLogic.connectClient(client);
                connectClientGUI();

                // Update server info every 5 seconds
                updateGUI();

            } catch (Exception e) {
                headLabel.setText(e.getMessage());
            }
        } else if (connectionBtn.getText().equals("DISCONNECT")) {
            try {
                ClientLogic.disconnectClient(client);
                disconnectClientGUI();
                timeline.stop();
            } catch (Exception e) {
                headLabel.setText(e.getMessage());
            }
        }
    }

    // Exit button event handler
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    // Update server information every 5 seconds and display changes
    private void updateGUI(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            try {
                boolean toggleState[] = client.ReadCoils(0, 2);
                System.out.println(toggleState[0] + " " + toggleState[1]);
                firstToggle.setSelected(toggleState[0]);
                secondToggle.setSelected(toggleState[1]);
                firstToggleAction(new ActionEvent());
                secondToggleAction(new ActionEvent());
    
            } catch (Exception e) {
                headLabel.setText("UNRESOLVED ERROR");
                System.out.println(e);
                setConnection(new ActionEvent());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    };
}