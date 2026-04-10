package jo.seongju.emp.employee;

public record SystemInfoResponse(
        String applicationName,
        String instanceId,
        String podName,
        String podNamespace,
        String nodeName,
        String podIp,
        String hostName
) {
}
