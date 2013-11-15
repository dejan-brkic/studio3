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

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractImplTest;
import org.craftercms.studio.impl.audit.AuditServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Audit Manager implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class AuditManagerImplTest extends AbstractImplTest {

    @InjectMocks
    @Autowired
    private AuditServiceImpl auditManagerSUT;

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = NotImplementedException.class)
    public void testGetActivities() throws Exception {
        this.auditManagerSUT.getActivities(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testGetActivitiesInvalidSite() throws Exception {
        this.auditManagerSUT.getActivities(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testLogActivity() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testLogActivityInvalidSite() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testLogActivityInvalidActivity() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }
}
