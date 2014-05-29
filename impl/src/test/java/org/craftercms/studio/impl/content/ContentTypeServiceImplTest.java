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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.ContentType;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.internal.content.ContentManager;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ContentTypeService implementation.
 *
 * @author Dejan Brkic
 */
public class ContentTypeServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private ContentTypeServiceImpl contentTypeServiceSUT;

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
     * Create new content type using valid parameters
     *
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        // Setup mock object
        when(contentManagerMock.create(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Item.class), Mockito.any(InputStream.class))).thenReturn(new ItemId(UUID.randomUUID()
            .toString()));
        when(securityServiceMock.validate(Mockito.any(Context.class))).thenReturn(true);

        Context context = new Context(RandomStringUtils.randomAlphanumeric(10), new Tenant());
        String site = RandomStringUtils.random(10);
        String typeName = RandomStringUtils.random(25);
        String formId = RandomStringUtils.random(25);
        String defaultTemplateId = RandomStringUtils.random(50);
        Map<String, String> templateIds = new HashMap<String, String>();
        byte[] thumbnail = "thumbnail".getBytes();
        List<String> permissionIds = new ArrayList<String>();
        boolean previewable = (new Random()).nextBoolean();
        String lifecycleScripts = RandomStringUtils.random(250);
        Map<String, String> properties = new HashMap<String, String>();

        ContentType contentType = contentTypeServiceSUT.create(context, site, typeName, formId, defaultTemplateId,
            templateIds, thumbnail, permissionIds, previewable, lifecycleScripts, properties);

        // assert and verify
        verify(contentManagerMock, times(1)).create(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Item.class), Mockito.any(InputStream.class));
        verify(securityServiceMock, times(1)).validate(Mockito.any(Context.class));
    }
}
