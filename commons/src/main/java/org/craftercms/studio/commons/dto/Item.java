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

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Content item transport object.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@JsonAutoDetect
@ApiModel(value = "Item class")
public class Item implements Comparable<Item> {
    // Fundamental
    /**
     * Crafter Studio item id
     */
    @ApiModelProperty(value = "Item id")
    private ItemId id;
    /**
     * Object Version Number: Monotonically increasing number on persist
     */
    @ApiModelProperty(value = "Object version")
    private int objectVersionNumber;
    /**
     * List of ancestors in TODO order
     */
    @ApiModelProperty(value = "List of ancestors")
    private List<ItemId> ancestors;

    // Core Metadata
    /**
     * Underlying repository id.
     */
    @ApiModelProperty(value = "Item id (in repository)")
    private String repoId;      // TODO think about this
    @ApiModelProperty(value = "Item label")
    private String label;
    @ApiModelProperty(value = "Item file name")
    private String fileName;
    @ApiModelProperty(value = "Item file path")
    private String path;
    @ApiModelProperty(value = "Created by")
    private String createdBy;
    @ApiModelProperty(value = "Creation date")
    private Date creationDate;
    @ApiModelProperty(value = "Modified by")
    private String modifiedBy;
    @ApiModelProperty(value = "Modified date")
    private Date lastModifiedDate;
    @ApiModelProperty(value = "Item type")
    private String type;        // Blueprint, Component, Page, Static Asset, Rendering Template, ...
    @ApiModelProperty(value = "Item is folder")
    private boolean isFolder;   // TODO think about this
    @ApiModelProperty(value = "Item state")
    private String state;       // TODO ENUM
    @ApiModelProperty(value = "Workflow")
    private String workflow;    // TODO ASK ABOUT THIS ONE

    // Something something properties
    @ApiModelProperty(value = "Item's mime type")
    private String mimeType;
    @ApiModelProperty(value = "Item displayed in navigation menus")
    private boolean placeInNav;
    @ApiModelProperty(value = "Item disabled")
    private boolean disabled;

    @JsonIgnore
    private InputStream inputStream;
    /**
     * User id of the lock owner, null if item is not locked
     */
    @ApiModelProperty(value = "Lock owner")
    private String lockOwner;
    /**
     * The URL to preview this item, null if item is not previewable
     */
    @ApiModelProperty(value = "Preview url for item")
    private String previewUrl;
    ////TODO LOCK TYPE
    // Security Properties
    @ApiModelProperty(value = "Security is inherited")
    private boolean securityInherited;
    @ApiModelProperty(value = "Content type")
    private String contentType;
    @ApiModelProperty(value = "List of rendering templates")
    private List<String> renderingTemplates;
    //private List<ACL> acls;
    @ApiModelProperty(value = "Scheduled date")
    private Date scheduledDate;
    @ApiModelProperty(value = "Workflow packages")
    private List<String> packages;
    // Additional Metadata
    @ApiModelProperty(value = "Custom metadata")
    private Map<String, Object> properties;
    public Item() {
    }

    public Item(Item original) {

    }

    @JsonIgnore
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Compare items.
     *
     * @param item item to compare to
     * @return comparison result
     */
    @Override
    public final int compareTo(final Item item) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // Getters and setters
    @JsonProperty
    public ItemId getId() {
        return this.id;
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    @JsonProperty
    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    @JsonProperty
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty
    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @JsonProperty
    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @JsonProperty
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty
    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @JsonProperty
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonProperty
    public boolean isPlaceInNav() {
        return this.placeInNav;
    }

    public void setPlaceInNav(boolean placeInNav) {
        this.placeInNav = placeInNav;
    }

    @JsonProperty
    public String getLockOwner() {
        return this.lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    @JsonProperty
    public List<String> getRenderingTemplates() {
        return this.renderingTemplates;
    }

    public void setRenderingTemplates(List<String> renderingTemplates) {
        this.renderingTemplates = renderingTemplates;
    }

    @JsonProperty
    public Date getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @JsonProperty
    public List<String> getPackages() {
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @JsonProperty
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public int getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(final int objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public List<ItemId> getAncestors() {
        return ancestors;
    }

    public void setAncestors(final List<ItemId> ancestors) {
        this.ancestors = ancestors;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(final boolean folder) {
        isFolder = folder;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(final String workflow) {
        this.workflow = workflow;
    }

    public boolean isSecurityInherited() {
        return securityInherited;
    }

    public void setSecurityInherited(final boolean securityInherited) {
        this.securityInherited = securityInherited;
    }
}
