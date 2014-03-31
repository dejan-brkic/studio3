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

package org.craftercms.studio.impl.repository.mongodb;

/**
 * @author Dejan Brkic
 */
public class ModuleConstants {

    public static final String MODULE_ID = "commons";

    public enum ErrorCode {

        NOT_IMPLEMENTED("REPO_MONGODB-001"),
        ERROR_ON_SAVE("REPO_MONGODB-002"),
        ERROR_ON_FIND("REPO_MONGODB-003"),
        ERROR_ON_FIND_ONE("REPO_MONGODB-004"),
        ERROR_ON_AGGREGATION_QUERY("REPO_MONGODB-005"),
        LAST_ERROR_MESSAGE("REPO_MONGODB-006"),
        ERROR_QUERY_NOT_FOUND("REPO_MONGODB-007"),
        ERROR_FOLDER_NOT_CREATED("REPO_MONGODB-008"),
        ERROR_BROKEN_NODE("REPO_MONGODB-009"),
        ERROR_FILE_EXPECTED_FOUND_FOLDER("REPO_MONGODB-010"),
        ERROR_GRIDFS_SAVE_FAILED("REPO_MONGODB-011"),
        ERROR_GRIDFS_READ_FAILED("REPO_MONGODB-012"),
        ;

        private final String code;

        ErrorCode(String code) {
            this.code = code;
        }


        @Override
        public String toString() {
            return this.code;
        }
    }
}
