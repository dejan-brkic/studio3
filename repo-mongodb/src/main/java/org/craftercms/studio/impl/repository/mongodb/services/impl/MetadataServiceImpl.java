/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.util.Map;

import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.repository.mongodb.exception.MongodbRepoErrorCode;
import org.craftercms.studio.repo.content.MetaDataService;

/**
 * Mongo Implementation of MetaDataService.
 */
public class MetadataServiceImpl implements MetaDataService {

    @Override
    public <T> Map<String, T> getProperties(final String ticket, final String itemId,
                                            final Class<T> type) throws StudioException {
        throw ErrorManager.createError(MongodbRepoErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public <T> void setProperties(final String ticket, final String itemId, final Map<String,
        T> properties) throws StudioException {
        throw ErrorManager.createError(MongodbRepoErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public <T> T getProperty(final String ticket, final String itemId, final String property,
                             final Class<T> type) throws StudioException {
        throw ErrorManager.createError(MongodbRepoErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public <T> void setProperty(final String ticket, final String itemId, final String property,
                                final T value) throws StudioException {
        throw ErrorManager.createError(MongodbRepoErrorCode.NOT_IMPLEMENTED);
    }


}
