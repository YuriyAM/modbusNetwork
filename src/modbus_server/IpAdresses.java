package modbus_server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpAdresses {

    public static List<String> GetLocalIP() throws Exception {
        List<String> ipList = new ArrayList<>();

        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements();) {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();) {
                String ip = a.nextElement().getHostAddress();
                if (ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}")) {
                    ipList.add(ip);
                }
            }
        }
        return ipList;
    }

}