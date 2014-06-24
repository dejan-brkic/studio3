/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
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

package org.craftercms.studio.impl.event;

/**
 * Events constants.
 */

public class EventConstants {

    public static final String REPOSITORY_REACTOR = "@repositoryReactor";

    public static final String REPOSITORY_CREATE_EVENT = "repository.create";
    public static final String REPOSITORY_UPDATE_EVENT = "repository.update";
    public static final String REPOSITORY_DELETE_EVENT = "repository.delete";

    private EventConstants() {}
}
