package modbus_server;

import java.io.IOException;

import de.re.easymodbus.server.*;

public class conMasterLogic {
    public static void main(String[] args) throws IOException {
        ModbusServer modbusServer = new ModbusServer();
        modbusServer.setPort(1522);// Note that Standard Port for Modbus TCP communication is 502
        modbusServer.coils[1] = true;
        modbusServer.holdingRegisters[1] = 1234;
        try {
            modbusServer.Listen();
            modbusServer.getNumberOfConnectedClients();
        } catch (IOException e) {
        }
    }
}