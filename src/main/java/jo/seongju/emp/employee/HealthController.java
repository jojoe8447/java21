package jo.seongju.emp.employee;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final Environment environment;

    public HealthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/info")
    public SystemInfoResponse getInfo() {
        String applicationName = resolve("spring.application.name", "application");
        String hostName = resolve("HOSTNAME", "unknown");
        String podName = resolve("POD_NAME", hostName);
        String podNamespace = resolve("POD_NAMESPACE", "unknown");
        String nodeName = resolve("NODE_NAME", "unknown");
        String podIp = resolve("POD_IP", "unknown");
        String instanceId = applicationName + "@" + podName;

        return new SystemInfoResponse(
                applicationName,
                instanceId,
                podName,
                podNamespace,
                nodeName,
                podIp,
                hostName
        );
    }

    private String resolve(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }
}
