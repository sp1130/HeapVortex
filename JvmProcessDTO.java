package com.heapvortex.dto.response;

/** One discoverable JVM process the user could attach the profiler to. */
public class JvmProcessDTO {

    private String processId;
    private String applicationName;
    private String displayName;
    private String jmxUrl;
    private boolean connected;

    public JvmProcessDTO() {}

    public JvmProcessDTO(String processId, String applicationName, String displayName,
                          String jmxUrl, boolean connected) {
        this.processId = processId;
        this.applicationName = applicationName;
        this.displayName = displayName;
        this.jmxUrl = jmxUrl;
        this.connected = connected;
    }

    public String getProcessId() { return processId; }
    public void setProcessId(String processId) { this.processId = processId; }

    public String getApplicationName() { return applicationName; }
    public void setApplicationName(String applicationName) { this.applicationName = applicationName; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getJmxUrl() { return jmxUrl; }
    public void setJmxUrl(String jmxUrl) { this.jmxUrl = jmxUrl; }

    public boolean isConnected() { return connected; }
    public void setConnected(boolean connected) { this.connected = connected; }
}
