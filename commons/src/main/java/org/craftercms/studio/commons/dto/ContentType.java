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

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Content Type.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@ApiModel(value = "Content type class")
public class ContentType {

    @ApiModelProperty(value = "Content type id")
    private String id;
    @ApiModelProperty(value = "Content type name")
    private String name;
    @ApiModelProperty(value = "Site identifier")
    private String siteId;
    @ApiModelProperty(value = "Site name")
    private String siteName;
    @ApiModelProperty(value = "Content type path")
    private String path;
    @ApiModelProperty(value = "Content type is previewable")
    private boolean previewable;
    @ApiModelProperty(value = "Thumbnail image")
    private String thumbnail;
    @ApiModelProperty(value = "Content type form")
    private Form form;

    public ContentType() {
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

    public String getSiteId() {
        return this.siteId;
    }

    public void setSiteId(final String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public boolean isPreviewable() {
        return this.previewable;
    }

    public void setPreviewable(final boolean previewable) {
        this.previewable = previewable;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(final Form form) {
        this.form = form;
    }
}
