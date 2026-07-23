package com.heapvortex.dto.request;

/**
 * Sent by the frontend when the user picks a JVM process to attach to.
 * For a locally-running target, host/port/username/password can be left null
 * and processId alone is used with the JVM Attach API.
 */
public class ConnectJvmRequestDTO {

    private String processId;
    private String host;
    private Integer port;
    private String username;
    private String password;

    public ConnectJvmRequestDTO() {}

    public String getProcessId() { return processId; }
    public void setProcessId(String processId) { this.processId = processId; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
