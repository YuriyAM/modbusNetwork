package modbus_server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerRestriction {

    private static int port;

    public static int getPort() {
        try {
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();
            return port;
        } catch (IOException ex) {
            return 0;
        }
    }
}
