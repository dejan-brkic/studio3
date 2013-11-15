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

package org.craftercms.studio.impl.audit;

import java.util.List;

import org.craftercms.studio.api.audit.AuditService;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.NotImplementedException;


/**
 * Audit Manager Implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class AuditServiceImpl implements AuditService {


    @Override
    public List<Activity> getActivities(final Context context, final String site, final List<String> filters) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Activity logActivity(final Context context, final String site, final Activity activity) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
