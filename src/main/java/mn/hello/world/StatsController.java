package mn.hello.world;


import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/v1/api/stats")
public class StatsController
{

    @Get(produces = MediaType.APPLICATION_JSON)
    public SystemStat getSystemStat() {
        return new SystemStat();
    }
}
