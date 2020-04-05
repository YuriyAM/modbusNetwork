package modbus_server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerRestriction {

    private static int port;

    /*
     * Finds free port by creating a new socket with automatically allocated port.
     * Opening and closing sockets is not a good practice so try not to use this
     * method and set static port in range 49152...65535
     */
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
