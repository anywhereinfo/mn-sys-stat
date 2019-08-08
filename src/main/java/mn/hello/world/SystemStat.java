package mn.hello.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.net.Inet4Address.*;

public class SystemStat {
    @JsonProperty
    private int maxCPU;

    @JsonProperty
    private String maxRam;

    @JsonIgnore
    private double freeRAM;

    @JsonProperty
    private String freeDisplayRAM;

    @JsonIgnore
    private double freeAllocatableRam;

    @JsonProperty
    private String totalAllocatedRAM;


    @JsonProperty
    private String usedAllocatedRAM;

    @JsonProperty
    private String freeAllocatedRAM;

    @JsonProperty
    private String hostName;

    @JsonProperty
    private List<String> ip;

    @JsonProperty
    private String routableIP;

    @JsonCreator
    public SystemStat() {
        NumberFormat nf = new DecimalFormat("###,##0.0");
        maxCPU = Runtime.getRuntime().availableProcessors();
        maxRam = String.valueOf( nf.format((double)Runtime.getRuntime().maxMemory()/(double)(1024*1024))) + "Mb";
        freeAllocatableRam = (double) Runtime.getRuntime().freeMemory()/(double)(1024*1024);
        freeAllocatedRAM = nf.format(freeAllocatableRam) + "Mb";
        totalAllocatedRAM = nf.format((double)Runtime.getRuntime().totalMemory()/ (double)(1024*1024)) + "Mb";
        usedAllocatedRAM = nf.format(((double)Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/ (double) (1024 * 1024)) + "Mb";
        freeRAM = (double)(Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))/ (double)(1024*1024);
        freeDisplayRAM = nf.format(freeRAM)    + "Mb";
        try {
            hostName = getLocalHost().getHostName();
            ip = getIP();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostName = "localhost";
        }
        routableIP = getRoutableIP();
    }

    public final double getFreeRam() {
        return freeRAM;
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
