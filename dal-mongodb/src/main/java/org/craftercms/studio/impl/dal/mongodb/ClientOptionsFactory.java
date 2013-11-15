/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.dal.mongodb;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.FactoryBean;

/**
 * Client options factory.
 *
 * @author Dejan Brkic
 */
public class ClientOptionsFactory implements FactoryBean<MongoClientOptions> {

    private static final int PRIMARY_READ_PREFERENCE = 1;
    private static final int NEAREST_READ_PREFERENCE = 2;
    private static final int SECONDARY_READ_PREFERENCE = 3;
    private int readPreference;
    private MongoClientOptions clientOptions;
    private boolean alwaysUseMBeans;
    private boolean autoConnectRetry;
    private int connectionsPerHost;
    private boolean cursorFinalizerEnabled;
    private int connectTimeout;
    private long maxAutoConnectRetryTime;
    private int maxWaitTime;
    private boolean socketKeepAlive;
    private String writeConcern;
    private int threadsAllowedToBlockForConnectionMultiplier;

    @Override
    public MongoClientOptions getObject() throws Exception {
        return clientOptions;
    }

    @Override
    public Class<?> getObjectType() {
        return ClientOptionsFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public int getReadPreference() {
        return readPreference;
    }

    public void setReadPreference(final int readPreference) {
        this.readPreference = readPreference;
    }

    public MongoClientOptions getClientOptions() {
        return clientOptions;
    }

    public void setClientOptions(final MongoClientOptions clientOptions) {
        this.clientOptions = clientOptions;
    }

    public boolean isAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(final boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public boolean isAutoConnectRetry() {
        return autoConnectRetry;
    }

    public void setAutoConnectRetry(final boolean autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(final int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public boolean isCursorFinalizerEnabled() {
        return cursorFinalizerEnabled;
    }

    public void setCursorFinalizerEnabled(final boolean cursorFinalizerEnabled) {
        this.cursorFinalizerEnabled = cursorFinalizerEnabled;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getMaxAutoConnectRetryTime() {
        return maxAutoConnectRetryTime;
    }

    public void setMaxAutoConnectRetryTime(final long maxAutoConnectRetryTime) {
        this.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(final int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(final boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public String getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(final String writeConcern) {
        this.writeConcern = writeConcern;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(final int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }
}
