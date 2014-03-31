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
public class ErrorManager {

    private static ErrorManager instance;
    protected Map<String, ResourceBundle> errorMap;

    private ErrorManager() {
        errorMap = new HashMap<String, ResourceBundle>();
    }

    public Map<String, ResourceBundle> getErrorMap() {
        return errorMap;
    }

    public static void registerError(String moduleId, String messageBundleLocation) {
        if (instance != null) {
            instance = new ErrorManager();
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(messageBundleLocation, Locale.getDefault());
        Map<String, ResourceBundle> errorMap = instance.getErrorMap();
        errorMap.put(moduleId, resourceBundle);
    }

    public static StudioException createError(String moduleId, String code) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(moduleId);
                StudioException error = StudioException.createStudioException(messageBundle, code);
                return error;
            } else {
                return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
            }
        } else {
            return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
        }
    }

    public static StudioException createError(String moduleId, String code, Exception exc) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(moduleId);
                StudioException error = StudioException.createStudioException(messageBundle, code, exc);
                return error;
            } else {
                return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
            }
        } else {
            return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
        }
    }

    public static StudioException createError(String moduleId, String code, String... args) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(moduleId);
                StudioException error = StudioException.createStudioException(messageBundle, code, args);
                return error;
            } else {
                return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
            }
        } else {
            return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
        }
    }

    public static StudioException createError(String moduleId, String code, Exception exc, String... args) {
        if (instance != null) {
            Map<String, ResourceBundle> errorMap = instance.getErrorMap();
            if (errorMap != null) {
                ResourceBundle messageBundle = errorMap.get(moduleId);
                StudioException error = StudioException.createStudioException(messageBundle, code, exc, args);
                return error;
            } else {
                return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
            }
        } else {
            return StudioException.createStudioException(null, "SYSTEM ERROR", "SYSTEM ERROR");
        }
    }
}
