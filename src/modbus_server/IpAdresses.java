package modbus_server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpAdresses {

    // Get IPv4 addresses of every NIC
    public static List<String> GetLocalIP() throws Exception {
        // IPv4 adresses list
        List<String> ipList = new ArrayList<>();

        // Gets all network inerfaces
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        
        for (; n.hasMoreElements();) {
            
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();

            // Going through list of IP addresses and choosing IPv4 addresses only
            for (; a.hasMoreElements();) {
                String ip = a.nextElement().getHostAddress();
                // IPv4 address has to match regex "0.0.0.0"
                if (ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}")) {
                    ipList.add(ip);
                }
            }
        }
        // Return list of IPv4 adresses
        return ipList;
    }

}