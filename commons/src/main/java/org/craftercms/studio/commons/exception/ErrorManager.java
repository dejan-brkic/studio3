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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Dejan Brkic
 */
public final class ErrorManager {

    public static final String SYSTEM_ERROR = "SYSTEM ERROR";

    private static ErrorManager instance;
    protected Map<String, ResourceBundle> errorMap;

    private ErrorManager() {
        errorMap = new HashMap<String, ResourceBundle>();
    }

    public static void registerError(final String moduleId, final String messageBundleLocation) {
        if (instance == null) {
            instance = new ErrorManager();
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(messageBundleLocation, Locale.getDefault());
        Map<String, ResourceBundle> errorMap = instance.getErrorMap();
        errorMap.put(moduleId, resourceBundle);
    }

    public static StudioException createError(final ErrorCode errorCode) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(errorCode.getModuleId());
                StudioException error = StudioException.createStudioException(messageBundle, errorCode.getCode());
                return error;
            }
        }
        return StudioException.createStudioException(null, SYSTEM_ERROR, SYSTEM_ERROR);
    }

    public static StudioException createError(final ErrorCode errorCode, final Exception cause) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(errorCode.getModuleId());
                StudioException error = StudioException.createStudioException(messageBundle, errorCode.getCode(),
                    cause);
                return error;
            }
        }
        return StudioException.createStudioException(null, SYSTEM_ERROR, SYSTEM_ERROR);

    }

    public static StudioException createError(final ErrorCode errorCode, final String... args) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(errorCode.getModuleId());
                StudioException error = StudioException.createStudioException(messageBundle, errorCode.getCode(), args);
                return error;
            }
        }
        return StudioException.createStudioException(null, SYSTEM_ERROR, SYSTEM_ERROR);
    }

    public static StudioException createError(final ErrorCode errorCode, final Exception cause, final String... args) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(errorCode.getModuleId());
                StudioException error = StudioException.createStudioException(messageBundle, errorCode.getCode(),
                    cause, args);
                return error;
            }
        }
        return StudioException.createStudioException(null, SYSTEM_ERROR, SYSTEM_ERROR);
    }

    public Map<String, ResourceBundle> getErrorMap() {
        return errorMap;
    }
}
