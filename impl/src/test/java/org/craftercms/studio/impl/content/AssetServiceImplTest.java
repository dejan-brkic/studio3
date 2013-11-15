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

package org.craftercms.studio.impl.content;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractImplTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for AssetServiceImpl.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImplTest extends AbstractImplTest {

    /**
     * Asset Service SUT
     */
    private AssetServiceImpl assetServiceImplSUT;

    /**
     * Content Manager Mock
     */
    private ContentManager contentManagerMock;

    @Before
    public void setUp() throws Exception {
        assetServiceImplSUT = new AssetServiceImpl();
        contentManagerMock = mock(ContentManager.class);
        assetServiceImplSUT.setContentManager(contentManagerMock);
    }

    /**
     * Use case:
     * Valid execution
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(createItemIdMock());

        ItemId newItemId = assetServiceImplSUT.create(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(20), mock(InputStream.class),
            RandomStringUtils.randomAlphabetic(15));

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        Assert.assertNotNull(newItemId);
    }

    /**
     * Use case:
     * Invalid destination path
     */
    @Test(expected = StudioException.class)
    public void testCreateWithInvalidDestinationPath() throws Exception{
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenThrow(new StudioException("Invalid " +
            "destination path.") {
        });

        ItemId newItemId = assetServiceImplSUT.create(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(20), mock(InputStream.class),
            RandomStringUtils.randomAlphabetic(15));

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        Assert.assertNull(newItemId);
    }

    /**
     * Use case:
     * Invalid destination path
     */
    @Test(expected = StudioException.class)
    public void testCreateWithEmptyFileName() throws Exception{
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenThrow(new StudioException("Empty file name" +
            ".") {
        });

        ItemId newItemId = assetServiceImplSUT.create(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(20), mock(InputStream.class),
            RandomStringUtils.randomAlphabetic(15));

        verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        Assert.assertNull(newItemId);
    }

    /**
     * Use case:
     * Duplicate file at destination
     */
    @Test(expected = StudioException.class)
    public void testCreateWithDuplicateFileAtDestination() throws Exception{
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenThrow(new StudioException("Duplicate file " +
            "name.") {
        });

        ItemId newItemId = assetServiceImplSUT.create(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(20), mock(InputStream.class),
            RandomStringUtils.randomAlphabetic(15));

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        Assert.assertNull(newItemId);
    }

    /**
     * Use case:
     * Site not found
     */
    @Test(expected = StudioException.class)
    public void testCreateWithNonExistingSite() throws Exception{
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenThrow(new StudioException("Site not found.") {
        });

        ItemId newItemId = assetServiceImplSUT.create(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(20), mock(InputStream.class),
            RandomStringUtils.randomAlphabetic(15));

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        Assert.assertNull(newItemId);
    }

    /**
     * Use case:
     * valid execution
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(mock(Item.class));

        Item item = contentManagerMock.read(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            UUID.randomUUID().toString());

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        Assert.assertNotNull(item);
    }

    /**
     * Use case:
     * Content not found
     * @throws Exception
     */
    @Test(expected = StudioException.class)
    public void testReadContentNotFound() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
            .thenThrow(new StudioException("Content not found") {
        });

        Item item = contentManagerMock.read(createContextMock(), RandomStringUtils.randomAlphabetic(10),
            UUID.randomUUID().toString());

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        Assert.assertNull(item);
    }
}
