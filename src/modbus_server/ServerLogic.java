package modbus_server;

import java.io.IOException;

import de.re.easymodbus.server.ModbusServer;

public class ServerLogic {

    public static void serverSetup(ModbusServer server, String stringPort) throws Exception {

        int port = 0;

        if (stringPort.matches("\\d+")) {
            port = Integer.parseInt(stringPort);
        } else {
            throw new Exception("INVALID PORT NUMBER");
        }
        if (port < 1024 || port > 65535) {
            throw new Exception("INVALID PORT NUMBER");
        } else {
            server.setPort(port);
        }
        server.setClientConnectionTimeout(20000);
    }

    public static void startServer(ModbusServer server) throws Exception {
        try {
            server.Listen();
        } catch (IOException e) {
            throw new Exception("UNRESOLVED ERROR");
        }
    }

    public static void stopServer(ModbusServer server) throws ThreadDeath {
        server.StopListening();
        // stopTask(timer);
    }

    public static void setCoil(ModbusServer server, int coil) {
        server.coils[coil] = true;
    }

    public static void unsetCoil(ModbusServer server, int coil) {
        server.coils[coil] = false;
    }
}