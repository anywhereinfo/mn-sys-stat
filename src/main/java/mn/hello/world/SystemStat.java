package mn.hello.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.net.Inet4Address.*;

public class SystemStat {
    @JsonProperty
    private int maxCPU;

    @JsonProperty
    private String maxRam;

    private long freeRam;
    @JsonProperty
    private String displayMaxRam;

    @JsonProperty
    private String hostName;

    @JsonProperty
    private List<String> ip;

    @JsonProperty
    private String routableIP;

    @JsonCreator
    public SystemStat() {
        maxCPU = Runtime.getRuntime().availableProcessors();
        maxRam = String.valueOf(Runtime.getRuntime().maxMemory()/(1024*1024));
        freeRam = Runtime.getRuntime().freeMemory()/(1024*1024);
        displayMaxRam = freeRam + "Mb";

        try {
            hostName = getLocalHost().getHostName();
            ip = getIP();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostName = "localhost";
        }
        routableIP = getRoutableIP();
    }

    public final long getFreeRam() {
        return freeRam;
    }

    private List<String> getIP() {
        List<String> ip = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration<InetAddress> ee = n.getInetAddresses();
                while(ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    ip.add(i.getHostAddress());
                }
            }
        } catch(SocketException se) {
            se.printStackTrace();
        }
        return ip;
    }

    private String getRoutableIP() {
        String routableIP = "unknown";
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(getByName("8.8.8.8"), 10002);
            routableIP = socket.getLocalAddress().getHostAddress();
        } catch(SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return routableIP;
    }
}
