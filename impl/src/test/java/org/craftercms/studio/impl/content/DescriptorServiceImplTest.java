/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for DescriptorService implementation.
 *
 * @author Dejan Brkic
 */
public class DescriptorServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private DescriptorServiceImpl descriptorServiceSUT;

    @Autowired
    private ContentManager contentManagerMock;

    @Autowired
    private SecurityService securityServiceMock;


    @Override
    protected void resetMocks() {
        reset(contentManagerMock, securityServiceMock);
    }

    /**
     * Use case 1:
     * Test create method using input stream and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStream() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream,
            props);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 2:
     * Test create method using input stream and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamNullContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 3:
     * Test create method using input stream and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 4:
     * Test create method using input stream and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 5:
     * Test create method using input stream and duplicate content type id.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingInputStreamInvalidContentType() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, fileStream, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 6:
     * Test create method using content string and valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentString() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
            props);

        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 7:
     * Test create method using content string and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringNullContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 8:
     * Test create method using content string and null context.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidContext() throws Exception {
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content,
                props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 9:
     * Test create method using content string and invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 10:
     * Test create method using content string and duplicate content type id.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUsingContentStringInvalidContentType() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String contentTypeId = RandomStringUtils.random(20);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        try {
            Item testItem = descriptorServiceSUT.create(context, site, contentTypeId, parentId, fileName, content, props);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 11:
     * Test duplicate method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicate() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(duplicatedId));
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 12:
     * Test duplicate method using null context.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingNullContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 13:
     * Test duplicate method using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 14:
     * Test duplicate method using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).read(Mockito
            .any(Context.class), Mockito.anyString(), Mockito.anyString());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 15:
     * Test duplicate method using invalid duplicated content type id.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidDuplicatedItem() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT)).when(contentManagerMock).create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = null;
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(0)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 16:
     * Test duplicate method using invalid parent id.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidParent() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock).create(Mockito.any
            (Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = null;
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 17:
     * Test duplicate method using invalid filename.
     *
     * @throws Exception
     */
    @Test
    public void testDuplicateUsingInvalidFilename() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock).create(Mockito.any
            (Context.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = null;
        String parentId = RandomStringUtils.random(75);
        String duplicatedId = UUID.randomUUID().toString();
        ItemId duplicatedItemId = new ItemId(duplicatedId);

        try {
            Item testItem = descriptorServiceSUT.duplicate(context, site, duplicatedItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(),
                eq(duplicatedId));
            verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 18:
     * Test move method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testMove() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);

        verify(contentManagerMock, times(2)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
        verify(contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.any(Item.class),
            Mockito.anyString());
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 18:
     * Test move method using null context.
     *
     * @throws Exception
     */
    @Test
    public void testMoveNullContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(0)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 20:
     * Test move method using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testMoveInvalidContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(0)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 21:
     * Test move method using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testMoveInvalidSite() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE))
            .when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 22:
     * Test move method using invalid item id.
     *
     * @throws Exception
     */
    @Test
    public void testMoveInvalidItemId() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND))
            .when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = RandomStringUtils.random(75);
        String itemId = null;
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 23:
     * Test move method using invalid parent id.
     *
     * @throws Exception
     */
    @Test
    public void testMoveInvalidParent() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND))
            .when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = RandomStringUtils.random(20);
        String parentId = null;
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 24:
     * Test move method using invalid file name.
     *
     * @throws Exception
     */
    @Test
    public void testMoveInvalidFilename() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_CONTENT))
            .when(contentManagerMock).move(Mockito.any(Context.class), Mockito.any(Item.class), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String fileName = null;
        String parentId = UUID.randomUUID().toString();
        String itemId = UUID.randomUUID().toString();
        ItemId moveItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.move(context, site, moveItemId, parentId, fileName);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTENT, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.any(Item.class),
                Mockito.anyString());
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 25:
     * Test read method using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testRead() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();
        ItemId readItemId = new ItemId(itemId);

        Item testItem = descriptorServiceSUT.read(context, site, readItemId);

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
        verify(securityServiceMock, times(1)).validate(context);
    }

    /**
     * Use case 26:
     * Test read method using null context.
     *
     * @throws Exception
     */
    @Test
    public void testReadNullContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = null;
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();
        ItemId readItemId = new ItemId(itemId);

        try {
        Item testItem = descriptorServiceSUT.read(context, site, readItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(securityServiceMock, times(0)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 27:
     * Test read method using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testReadInvalidContext() throws Exception {
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(createItemMock());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();
        ItemId readItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.read(context, site, readItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 28:
     * Test read method using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testReadInvalidSite() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE)).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();
        ItemId readItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.read(context, site, readItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 29:
     * Test read method using invalid item id.
     *
     * @throws Exception
     */
    @Test
    public void testReadInvalidItem() throws Exception {
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND)).when(contentManagerMock)
            .read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String itemId = UUID.randomUUID().toString();
        ItemId readItemId = new ItemId(itemId);

        try {
            Item testItem = descriptorServiceSUT.read(context, site, readItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), eq(itemId));
            verify(securityServiceMock, times(1)).validate(context);
            return;
        }
        fail();
    }

    /**
     * Use case 30:
     * Test update method with input stream using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStream() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, fileStream, props);

        // assert and verify
        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 31:
     * Test update method with string content using valid parameters.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentString() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, content, props);

        // assert and verify
        verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 32:
     * Test update method with input stream using null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithNullContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 33:
     * Test update method with string content using null context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithNullContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 34:
     * Test update method with input stream using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 35:
     * Test update method with string content using invalid context.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidContext() throws Exception {
        // Setup mock object
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(contentManagerMock).write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // setup parameters
        Context context = new Context("INVALID", new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 36:
     * Test update method with input stream using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidSite() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 37:
     * Test update method with string content using invalid site.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidSite() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 38:
     * Test update method with input stream using invalid item.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingInputStreamWithInvalidItem() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        InputStream fileStream = IOUtils.toInputStream(RandomStringUtils.random(1000));
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, fileStream, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 39:
     * Test update method with string content using invalid item.
     *
     * @throws Exception
     */
    @Test
    public void testUpdateUsingContentStringWithInvalidItem() throws Exception {
        // Setup mock object
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test")).when(contentManagerMock)
            .write(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(ItemId.class),
                Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // setup parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = UUID.randomUUID().toString();
        ItemId descriptorItemId = new ItemId(descriptorId);
        String content = RandomStringUtils.random(1000);
        Map<String, String> props = new HashMap<>();

        // execute tested method
        try {
            Item testItem = descriptorServiceSUT.update(context, site, descriptorItemId, content, props);
        } catch (StudioException expectedException) {
            // assert and verify
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.any(ItemId.class), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 40:
     * Test delete method.
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = RandomStringUtils.random(10);
        ItemId descriptorItemId = new ItemId(descriptorId);

        // execute delete method
        descriptorServiceSUT.delete(context, site, descriptorItemId);

        verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class),Mockito.anyListOf(Item.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }

    /**
     * Use case 41:
     * Test delete method using null context.
     * @throws Exception
     */
    @Test
    public void testDeleteWithNullContext() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = null;
        String site = RandomStringUtils.random(10);
        String descriptorId = RandomStringUtils.random(10);
        ItemId descriptorItemId = new ItemId(descriptorId);

        // execute delete method
        try {
            descriptorServiceSUT.delete(context, site, descriptorItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(0)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 42:
     * Test delete method using invalid context.
     * @throws Exception
     */
    @Test
    public void testDeleteWithInvalidContext() throws Exception {
        // Set up mock objects
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(false);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = RandomStringUtils.random(10);
        ItemId descriptorItemId = new ItemId(descriptorId);

        // execute delete method
        try {
            descriptorServiceSUT.delete(context, site, descriptorItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_CONTEXT, expectedException.getErrorCode());
            verify(contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 43:
     * Test delete method for invalid site.
     * @throws Exception
     */
    @Test
    public void testDeleteSiteNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.INVALID_SITE, "Unit test.")).when(contentManagerMock)
            .delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = RandomStringUtils.random(10);
        ItemId descriptorItemId = new ItemId(descriptorId);

        // execute delete method
        try {
            descriptorServiceSUT.delete(context, site, descriptorItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.INVALID_SITE, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }

    /**
     * Use case 44:
     * Test delete method for invalid item.
     * @throws Exception
     */
    @Test
    public void testDeleteItemNotExists() throws Exception {
        // Set up mock objects
        doThrow(new StudioException(StudioException.ErrorCode.ITEM_NOT_FOUND, "Unit test.")).when(contentManagerMock)
            .delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        // set up parameters
        Context context = new Context(UUID.randomUUID().toString(), new Tenant());
        String site = RandomStringUtils.random(10);
        String descriptorId = RandomStringUtils.random(10);
        ItemId descriptorItemId = new ItemId(descriptorId);

        // execute delete method
        try {
            descriptorServiceSUT.delete(context, site, descriptorItemId);
        } catch (StudioException expectedException) {
            assertEquals(StudioException.ErrorCode.ITEM_NOT_FOUND, expectedException.getErrorCode());
            verify(contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
            verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
            return;
        }
        fail();
    }
}
