package mn.hello.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import javax.annotation.Nullable;

@Controller
public class HealthController {

    @Get(uri = "/api/v1/health")
    public HealthStatus getHealth(@Nullable @QueryValue("memLimit") String memoryLimit) {
        return new HealthStatus(memoryLimit);
    }

    @Get(uri = "/api/v1/liveness")
    public HttpStatus liveness(@Nullable @QueryValue("memLimit") String memoryLimit) {
        HealthStatus status = new HealthStatus(memoryLimit);
        if (status.health == HealthStatus.Status.DEGRADED)
            return HttpStatus.EXPECTATION_FAILED;
        else
            return HttpStatus.OK;
    }

    public static class HealthStatus {

        enum Status {OK,DEGRADED };

        @JsonProperty
        private Status health;

        @JsonProperty
        private SystemStat stats;

        @JsonCreator
        public HealthStatus(String memoryLimit) {
            int memLimit;
            if ((memoryLimit != null) && memoryLimit.length() > 0)
                memLimit = Integer.valueOf(memoryLimit);
            else memLimit = 100;

            stats = new SystemStat();
            if (stats.getFreeRam() < memLimit)
                health = Status.DEGRADED;
            else
                health = Status.OK;
        }

    }
}
