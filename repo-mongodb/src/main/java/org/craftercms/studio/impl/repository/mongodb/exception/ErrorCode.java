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

package org.craftercms.studio.impl.repository.mongodb.exception;

/**
 * @author Dejan Brkic
 */
public class ErrorCode extends org.craftercms.studio.commons.exception.ErrorCode {

    public static final String MODULE_ID = "repo-mongodb";

    public static final ErrorCode ERROR_ON_SAVE = new ErrorCode("REPO_MONGODB-001");
    public static final ErrorCode ERROR_ON_FIND = new ErrorCode("REPO_MONGODB-002");
    public static final ErrorCode ERROR_ON_FIND_ONE = new ErrorCode("REPO_MONGODB-003");
    public static final ErrorCode ERROR_ON_AGGREGATION_QUERY = new ErrorCode("REPO_MONGODB-004");
    public static final ErrorCode LAST_ERROR_MESSAGE = new ErrorCode("REPO_MONGODB-005");
    public static final ErrorCode ERROR_QUERY_NOT_FOUND = new ErrorCode("REPO_MONGODB-006");
    public static final ErrorCode ERROR_FOLDER_NOT_CREATED = new ErrorCode("REPO_MONGODB-007");
    public static final ErrorCode ERROR_BROKEN_NODE = new ErrorCode("REPO_MONGODB-008");
    public static final ErrorCode ERROR_FILE_EXPECTED_FOUND_FOLDER = new ErrorCode("REPO_MONGODB-009");
    public static final ErrorCode ERROR_GRIDFS_SAVE_FAILED = new ErrorCode("REPO_MONGODB-010");
    public static final ErrorCode ERROR_GRIDFS_READ_FAILED = new ErrorCode("REPO_MONGODB-011");

    private ErrorCode(String code) {
        super(MODULE_ID, code);
    }
}
