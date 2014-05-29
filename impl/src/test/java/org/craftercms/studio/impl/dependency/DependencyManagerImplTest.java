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

package org.craftercms.studio.impl.dependency;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Dependency Manager implementation.
 *
 * @author Dejan Brkic
 */
public class DependencyManagerImplTest extends AbstractServiceTest {

    @Autowired
    @InjectMocks
    private DependencyManagerImpl dependencyManagerSUT;

    @Override
    protected void resetMocks() {

    }

    @Test(expected = StudioException.class)
    public void testDependsOn() throws Exception {
        this.dependencyManagerSUT.dependsOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = StudioException.class)
    public void testDependsOnItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.dependsOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = StudioException.class)
    public void testDependsOnInvalidItemId() throws Exception {
        this.dependencyManagerSUT.dependsOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = StudioException.class)
    public void testDependsOnInvalidOperation() throws Exception {
        this.dependencyManagerSUT.dependsOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = StudioException.class)
    public void testDependentOn() throws Exception {
        this.dependencyManagerSUT.dependentOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic
            (10));
    }

    @Test(expected = StudioException.class)
    public void testDependentOnItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.dependentOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic
            (10));
    }

    @Test(expected = StudioException.class)
    public void testDependentOnInvalidItemId() throws Exception {
        this.dependencyManagerSUT.dependentOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic
            (10));
    }

    @Test(expected = StudioException.class)
    public void testDependentOnInvalidOperation() throws Exception {
        this.dependencyManagerSUT.dependentOn(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic
            (10));
    }

    @Test(expected = StudioException.class)
    public void testRefresh() throws Exception {
        this.dependencyManagerSUT.refresh(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testRefreshItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.refresh(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testRefreshInvalidItemId() throws Exception {
        this.dependencyManagerSUT.refresh(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testAdd() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testAddItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testAddInvalidItemId() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testAddInvalidOperation() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testAddEmptyList() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testAddInvalidItemInsideList() throws Exception {
        this.dependencyManagerSUT.add(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemove() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemoveItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemoveInvalidItemId() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemoveInvalidOperation() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemoveInvalidItemInsideList() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testRemoveEmptyList() throws Exception {
        this.dependencyManagerSUT.remove(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdate() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdateItemDoesNotExist() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdateInvalidItemId() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdateInvalidOperation() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdateEmptyList() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }

    @Test(expected = StudioException.class)
    public void testUpdateInvalidItemInsideList() throws Exception {
        this.dependencyManagerSUT.update(null, UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10),
            createItemListMock());
    }
}
