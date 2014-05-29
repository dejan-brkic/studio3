/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.commons.dto;

import java.util.List;

/**
 * Deployment Channel.
 *
 * @author Carlos Ortiz
 */
public class DeploymentChannel {

    private String id;
    private String name;
    private String target;
    private String host;
    private String port;
    private String publishingUrl;
    private String versionUrl;
    private String statusUrl;
    private List<String> excludePatterns;
    private List<String> includePatterns;
    private boolean publishMetadata;
    private boolean disabled;
    private String type;

    public DeploymentChannel() {
    }

    // Getters and setters

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(final String target) {
        this.target = target;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(final String port) {
        this.port = port;
    }

    public String getPublishingUrl() {
        return this.publishingUrl;
    }

    public void setPublishingUrl(final String publishingUrl) {
        this.publishingUrl = publishingUrl;
    }

    public String getVersionUrl() {
        return this.versionUrl;
    }

    public void setVersionUrl(final String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getStatusUrl() {
        return this.statusUrl;
    }

    public void setStatusUrl(final String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public List<String> getExcludePatterns() {
        return this.excludePatterns;
    }

    public void setExcludePatterns(final List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

    public List<String> getIncludePatterns() {
        return this.includePatterns;
    }

    public void setIncludePatterns(final List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public boolean isPublishMetadata() {
        return this.publishMetadata;
    }

    public void setPublishMetadata(final boolean publishMetadata) {
        this.publishMetadata = publishMetadata;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
