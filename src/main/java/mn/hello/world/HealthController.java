package mn.hello.world;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.http.annotation.Get;
import io.micronaut.web.router.naming.$HyphenatedUriNamingStrategyDefinitionClass;

public class HealthController {

    @Get(uri = "/v1/api/health")
    public HealthStatus getHealth() {
        return new HealthStatus();
    }

    public static class HealthStatus {

        enum Status {OK,DEGRADED };

        @JsonProperty
        private Status health;

        @JsonProperty
        private SystemStat stats;

        @JsonCreator
        public HealthStatus() {
            stats = new SystemStat();
            if (stats.getFreeRam() < 1)
                health = Status.DEGRADED;
        }

    }
}
