package modbus_server;

import java.io.IOException;

import de.re.easymodbus.server.ModbusServer;

public class ServerLogic {

    // Initial server setup
    public static void serverSetup(ModbusServer server, String stringPort) throws Exception {

        int port = 0;

        // If text in portField is an integer
        if (stringPort.matches("\\d+")) {
            port = Integer.parseInt(stringPort);
        } else {
            throw new Exception("INVALID PORT NUMBER");
        }
                
        // If port is in registered or private range
        if (port > 1023 && port < 65536)
            server.setPort(port);
        else
            throw new Exception("INVALID PORT NUMBER");
    }

    // Start listening on specified port
    public static void startServer(ModbusServer server) throws Exception {
        try {
            server.Listen();
        } catch (IOException e) {
            throw new Exception("UNRESOLVED ERROR");
        }
    }

    // Stop listening
    public static void stopServer(ModbusServer server) throws ThreadDeath {
        // StopListening method is not working properly
        server.StopListening();
    }

    // Change specified coil state to "true"
    public static void setCoil(ModbusServer server, int coil) {
        server.coils[coil] = true;
    }

    // Change specified coil state to "false"
    public static void unsetCoil(ModbusServer server, int coil) {
        server.coils[coil] = false;
    }
}