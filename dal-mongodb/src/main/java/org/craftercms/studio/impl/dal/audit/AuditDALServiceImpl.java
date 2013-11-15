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

package org.craftercms.studio.impl.dal.audit;

import java.util.List;

import org.craftercms.studio.api.dal.audit.AuditDALService;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.dal.AbstractDALService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Audit DAL Service MongoDB implementation.
 *
 * @author Dejan Brkic
 */
public class AuditDALServiceImpl extends AbstractDALService implements AuditDALService {

    private Logger log = LoggerFactory.getLogger(AuditDALServiceImpl.class);

    @Override
    public List<Activity> getActivities(final String ticket, final String site, final List<String> filters) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Activity logActivity(final String ticket, final String site, final Activity activity) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
