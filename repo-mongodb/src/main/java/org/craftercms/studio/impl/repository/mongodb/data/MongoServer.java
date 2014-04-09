package org.craftercms.studio.impl.repository.mongodb.data;

/**
 *
 */
public final class MongoServer {
    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }
}
