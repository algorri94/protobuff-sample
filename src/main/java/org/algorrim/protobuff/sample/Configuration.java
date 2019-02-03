package org.algorrim.protobuff.sample;

public class Configuration {

    private String folder;
    private int rolling_timeout_ms;
    private int port;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getRolling_timeout_ms() {
        return rolling_timeout_ms;
    }

    public void setRolling_timeout_ms(int rolling_timeout_ms) {
        this.rolling_timeout_ms = rolling_timeout_ms;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
