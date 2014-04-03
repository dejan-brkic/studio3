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

package org.craftercms.studio.exceptions;

/**
 * @author Dejan Brkic
 */
public class ErrorCode extends org.craftercms.studio.commons.exception.ErrorCode {

    public static final String MODULE_ID = "server";

    public static final ErrorCode FILE_UPLOAD_IO_ERROR = new ErrorCode("SERVER-001");
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode("SERVER-002");
    public static final ErrorCode VALIDATION_ERROR = new ErrorCode("SERVER-003");

    private ErrorCode(String code) {
        super(MODULE_ID, code);
    }
}
