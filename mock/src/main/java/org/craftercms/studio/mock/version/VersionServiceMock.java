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

package org.craftercms.studio.mock.version;

import org.craftercms.studio.api.content.VersionService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DiffResult;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.Version;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.mock.exception.MockErrorCode;

/**
 * Version Manager mock implementation.
 *
 * @author Dejan Brkic
 */
public class VersionServiceMock implements VersionService {

    @Override
    public Tree<Version> history(final Context context, final String itemId) throws StudioException {
        throw ErrorManager.createError(MockErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void revert(final Context context, final String itemId, final String revertVersion) throws StudioException {
        throw ErrorManager.createError(MockErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public DiffResult diff(final Context context, final String itemId, final String version1,
                           final String version2) throws StudioException {
        throw ErrorManager.createError(MockErrorCode.NOT_IMPLEMENTED);
    }
}
