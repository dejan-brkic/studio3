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

package org.craftercms.studio.internal.content.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.commons.dto.*;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.commons.filter.Filter;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.repo.content.PathService;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.Reactor;
import reactor.event.Event;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContentManagerImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private ContentManagerImpl contentManagerSUT;

    @Autowired
    private ContentService contentServiceMock;

    @Autowired
    private PathService pathServiceMock;

    @Spy
    @Autowired
    private Reactor repositoryReactorMock;

    @Override
    protected void resetMocks() {
        reset(contentServiceMock, pathServiceMock, repositoryReactorMock);
    }

    /**
     * Use case #1.
     * Test create method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {

        // Setup mocks
        when(contentServiceMock.create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(createItemMock());

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String path = RandomStringUtils.random(100);
        Item item = createItemMock();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));

        ItemId createdItemId = contentManagerSUT.create(context, site, path, item, content);

        verify(contentServiceMock, times(1)).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(repositoryReactorMock, times(1)).notify(Mockito.anyString(), Mockito.any(Event.class));
    }

    /**
     * Use case #2.
     * Test create method using empty site value.
     *
     * @throws Exception
     */
    @Test()
    public void testCreateEmptySite() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = StringUtils.EMPTY;
        String path = RandomStringUtils.random(100);
        Item item = createItemMock();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));

        try {
            ItemId createdItemId = contentManagerSUT.create(context, site, path, item, content);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                    Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #3.
     * Test create method using empty path value.
     *
     * @throws Exception
     */
    @Test()
    public void testCreateEmptyPath() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String path = StringUtils.EMPTY;
        Item item = createItemMock();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));

        try {
            ItemId createdItemId = contentManagerSUT.create(context, site, path, item, content);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                    Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #4.
     * Test create method using null item.
     *
     * @throws Exception
     */
    @Test()
    public void testCreateNullItem() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String path = RandomStringUtils.random(100);
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));

        try {
            ItemId createdItemId = contentManagerSUT.create(context, site, path, null, content);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).create(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                    Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #5.
     * Test read method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {

        // Setup mocks
        when(contentServiceMock.read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).
                thenReturn(createItemMock());


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentId = UUID.randomUUID().toString();

        Item item = contentManagerSUT.read(context, site, contentId);

        verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    /**
     * Use case #6.
     * Test read method using empty content id.
     *
     * @throws Exception
     */
    @Test()
    public void testReadContentIdEmpty() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).
                when(contentServiceMock).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentId = StringUtils.EMPTY;

        try {
            Item item = contentManagerSUT.read(context, site, contentId);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
            return;
        }
        fail();
    }

    /**
     * Use case #7.
     * Test write method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testWrite() throws Exception {

        // Setup mocks
        when(contentServiceMock.read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).
                thenReturn(createItemMock());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).update(Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = RandomStringUtils.random(10);
        ItemId objItemId = new ItemId(itemId);
        LockHandle lockHandle = new LockHandle();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));



        contentManagerSUT.write(context, site, objItemId, lockHandle, content);

        verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        verify(contentServiceMock, times(1)).update(Mockito.anyString(), Mockito.any(Item.class),
                Mockito.any(InputStream.class));
        verify(repositoryReactorMock, times(1)).notify(Mockito.anyString(), Mockito.any(Event.class));
    }

    /**
     * Use case #8.
     * Test write method using blank item id.
     *
     * @throws Exception
     */
    @Test()
    public void testWriteBlankItemId() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).update(Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = StringUtils.EMPTY;
        ItemId objItemId = new ItemId(itemId);
        LockHandle lockHandle = new LockHandle();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));


        try {
            contentManagerSUT.write(context, site, objItemId, lockHandle, content);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
            verify(contentServiceMock, times(0)).update(Mockito.anyString(), Mockito.any(Item.class),
                    Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #9.
     * Test write method using item that is folder.
     *
     * @throws Exception
     */
    @Test
    public void testWriteItemIsFolder() throws Exception {

        // Setup mocks
        doThrow(StudioException.class).when(contentServiceMock)
                .read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).update(Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = RandomStringUtils.random(10);
        ItemId objItemId = new ItemId(itemId);
        LockHandle lockHandle = new LockHandle();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));


        try {
            contentManagerSUT.write(context, site, objItemId, lockHandle, content);
        } catch (StudioException e) {
            verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
            verify(contentServiceMock, times(0)).update(Mockito.anyString(), Mockito.any(Item.class),
                    Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #10.
     * Test write method using item that is broken.
     *
     * @throws Exception
     */
    @Test
    public void testWriteBrokenItem() throws Exception {

        // Setup mocks
        doThrow(StudioException.class).when(contentServiceMock)
                .read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).update(Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = RandomStringUtils.random(10);
        ItemId objItemId = new ItemId(itemId);
        LockHandle lockHandle = new LockHandle();
        InputStream content = IOUtils.toInputStream(RandomStringUtils.random(1000));


        try {
            contentManagerSUT.write(context, site, objItemId, lockHandle, content);
        } catch (StudioException e) {
            verify(contentServiceMock, times(1)).read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
            verify(contentServiceMock, times(0)).update(Mockito.anyString(), Mockito.any(Item.class),
                    Mockito.any(InputStream.class));
            verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
            return;
        }
        fail();
    }

    /**
     * Use case #11.
     * Test delete method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {

        // Setup mocks
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).delete(Mockito.anyString(), Mockito.anyString());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        List<Item> itemsToDelete = createItemListMock();

        contentManagerSUT.delete(context, itemsToDelete);

        verify(contentServiceMock, times(itemsToDelete.size())).delete(Mockito.anyString(), Mockito.anyString());
        verify(repositoryReactorMock, times(1)).notify(Mockito.anyString(), Mockito.any(Event.class));
    }

    /**
     * Use case #12.
     * Test delete method using empty list of items.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteEmptyList() throws Exception {

        // Setup mocks
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentServiceMock).delete(Mockito.anyString(), Mockito.anyString());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(repositoryReactorMock).notify(Mockito.anyString(), Mockito.any(Event.class));


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        List<Item> itemsToDelete = new ArrayList<Item>();

        contentManagerSUT.delete(context, itemsToDelete);

        verify(contentServiceMock, times(0)).delete(Mockito.anyString(), Mockito.anyString());
        verify(repositoryReactorMock, times(0)).notify(Mockito.anyString(), Mockito.any(Event.class));
    }

    /**
     * Use case #13.
     * Test list method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {

        // Setup mocks
        when(contentServiceMock.getChildren(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyInt(), Mockito.anyListOf(Filter.class))).thenReturn(createItemTreeMock());


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();

        List<Item> result = contentManagerSUT.list(context, site, itemId);

        assertNotNull(result);
        verify(contentServiceMock, times(1)).getChildren(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyInt(), Mockito.anyListOf(Filter.class));
    }

    /**
     * Use case #14.
     * Test list method using blank site.
     *
     * @throws Exception
     */
    @Test
    public void testListBlankSite() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).getChildren(Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyListOf(Filter.class));


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = StringUtils.EMPTY;
        String itemId = UUID.randomUUID().toString();

        try {
            contentManagerSUT.list(context, site, itemId);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).getChildren(Mockito.anyString(), Mockito.anyString(),
                    Mockito.anyString(), Mockito.anyInt(), Mockito.anyListOf(Filter.class));
            return;
        }
        fail();
    }

    /**
     * Use case #15.
     * Test list method using blank site.
     *
     * @throws Exception
     */
    @Test
    public void testListBlankItemId() throws Exception {

        // Setup mocks
        doThrow(IllegalArgumentException.class).when(contentServiceMock).getChildren(Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyListOf(Filter.class));


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = StringUtils.EMPTY;

        try {
            contentManagerSUT.list(context, site, itemId);
        } catch (IllegalArgumentException e) {
            verify(contentServiceMock, times(1)).getChildren(Mockito.anyString(), Mockito.anyString(),
                    Mockito.anyString(), Mockito.anyInt(), Mockito.anyListOf(Filter.class));
            return;
        }
        fail();
    }

    /**
     * Use case #16.
     * Test list method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testListItemNotFound() throws Exception {

        // Setup mocks
        when(contentServiceMock.getChildren(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyInt(), Mockito.anyListOf(Filter.class))).thenReturn(null);


        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();

        List<Item> result = contentManagerSUT.list(context, site, itemId);

        assertNull(result);
        verify(contentServiceMock, times(1)).getChildren(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyInt(), Mockito.anyListOf(Filter.class));
    }

    /**
     * Use case #17.
     * Test createFolder method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateFolder() throws Exception {

        // Setup mocks
        when(contentServiceMock.createFolder(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(createItemMock());

        // setup params
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String path = RandomStringUtils.random(100);
        String folderName = RandomStringUtils.random(15);

        Item createdItem = contentManagerSUT.createFolder(context, site, path, folderName);

        verify(contentServiceMock, times(1)).createFolder(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

}