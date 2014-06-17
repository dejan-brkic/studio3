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

import org.craftercms.studio.commons.exception.ErrorCode;

/**
 * Studio Server error codes.
 *
 * @author Dejan Brkic
 */
public final class StudioServerErrorCode extends ErrorCode {

    /**
     * Module ID.
     */
    public static final String MODULE_ID = "server";

    /**
     * File upload IO error.
     */
    public static final ErrorCode FILE_UPLOAD_IO_ERROR = new StudioServerErrorCode("SERVER-001");

    /**
     * System Error.
     */
    public static final ErrorCode SYSTEM_ERROR = new StudioServerErrorCode("SERVER-002");

    /**
     * Validation error.
     */
    public static final ErrorCode VALIDATION_ERROR = new StudioServerErrorCode("SERVER-003");

    private StudioServerErrorCode(final String code) {
        super(MODULE_ID, code);
    }
}
