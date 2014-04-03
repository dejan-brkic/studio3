/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
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

package org.craftercms.studio.commons.exception;

/**
 * @author Dejan Brkic
 */
public class ErrorCode {



    public static final String MODULE_ID = "commons";

    public static final ErrorCode NOT_IMPLEMENTED = new ErrorCode(MODULE_ID, "COMMON-001");
    public static final ErrorCode INVALID_CONTEXT = new ErrorCode(MODULE_ID, "COMMON-002");
    public static final ErrorCode IO_ERROR = new ErrorCode(MODULE_ID, "COMMON-003");
    public static final ErrorCode INVALID_SITE = new ErrorCode(MODULE_ID, "COMMON-004");
    public static final ErrorCode ITEM_NOT_FOUND = new ErrorCode(MODULE_ID, "COMMON-005");
    public static final ErrorCode INVALID_CONTENT = new ErrorCode(MODULE_ID, "COMMON-006");

    private String moduleId;
    private String code;

    protected ErrorCode() { }

    protected ErrorCode(String moduleId, String code) {
        this.moduleId = moduleId;
        this.code = code;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(final String moduleId) {
        this.moduleId = moduleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
