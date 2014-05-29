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

package org.craftercms.studio.impl.forms;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.content.FormService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Form;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.exception.StudioImplErrorCode;

/**
 * Forms Manager implementation.
 *
 * @author Dejan Brkic
 */
public class FormServiceImpl implements FormService {

    @Override
    public Form create(final Context context, final String site, final String formName, final String formXml,
                       final Map<String, String> properties) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Form duplicate(final Context context, final String site, final String formId,
                          final String formName) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Form duplicate(final Context context, final String site, final String formId,
                          final String destinationSite, final String formName) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Form read(final Context context, final String site, final String formId) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Form update() throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void delete(final Context context, final String site, final String formId) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Form> findBy(final Context context, final String site, final String query) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Form> list(final Context context, final String site, final List<String> filters) throws
        StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void update(final Context context, final String site, final Form form) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void remove(final Context context, final String site, final String formId) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void copy(final Context context, final String site, final String source,
                     final String destination) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }
}
