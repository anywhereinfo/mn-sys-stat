package mn.hello.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class SystemStat {
    @JsonProperty
    private int maxCPU;
    @JsonProperty
    private String maxRam;
    @JsonProperty
    private String freeRam;

    @JsonProperty
    private String hostName;

    @JsonProperty
    private String ip;

    @JsonCreator
    public SystemStat() {
        maxCPU = Runtime.getRuntime().availableProcessors();
        maxRam = String.valueOf(Runtime.getRuntime().maxMemory()/(1024*1024));
        freeRam = String.valueOf((Runtime.getRuntime().freeMemory()/(1024*1024)));
        try {
            hostName = Inet4Address.getLocalHost().getHostName();
            ip = Inet4Address.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostName = "localhost";
        }

    }

    public final int getMaxCPU() {
        return maxCPU;
    }

    public final String getMaxRam() {
        return maxRam + "Mb";
    }

    public final String getFreeRam() {
        return freeRam + "Mb";
    }
}
