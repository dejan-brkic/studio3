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

package org.craftercms.studio.impl.deployment;

import org.apache.commons.io.FileUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.craftercms.studio.impl.event.EventConstants;
import org.craftercms.studio.impl.event.RepositoryEventBulkOpMessage;
import org.craftercms.studio.impl.event.RepositoryEventMessage;
import org.craftercms.studio.internal.content.ContentManager;
import org.craftercms.studio.spring.configuration.EBusConfigTesting;
import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PreviewDeployerTest extends AbstractServiceTest{


    @InjectMocks
    @Autowired
    private PreviewDeployer previewDeployerSUT;

    @Autowired
    private ContentManager contentManagerMock;


    @Autowired
    private Reactor repositoryReactor;

    @AfterClass
    public static void cleanUp() {
        File file = new File(EBusConfigTesting.TEST_PREVIEW_CONTENT_ROOT);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void resetMocks() {
        reset(contentManagerMock, previewDeployerSUT);
    }

    /**
     * Use case 1:
     * Valid execution of content create event.
     *
     * @throws Exception
     */
    @Test
    public void testOnContentCreate() throws Exception {
        // Set up mocks
        Item mockItem = createItemMock();
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockItem);

        //PreviewDeployer spySUT = Mockito.spy(previewDeployerSUT);
        RepositoryEventMessage message = createEventMessage(mockItem);
        EventCallback verifyReady = new EventCallback();
        repositoryReactor.notify(EventConstants.REPOSITORY_CREATE_EVENT, Event.wrap(message), verifyReady);

        while (!verifyReady.isCompleted()) {
            Thread.sleep(1);
        }

        verify(previewDeployerSUT, times(1)).onContentCreate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(0)).onContentUpdate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(0)).onContentDelete(Mockito.any(Event.class));

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        verify(previewDeployerSUT, times(1)).writeFile(Mockito.anyString(), Mockito.any(InputStream.class));
        StringBuilder sbSavePath = new StringBuilder(previewDeployerSUT.getPreviewStoreRootPath());
        sbSavePath.append(File.separator);
        sbSavePath.append(mockItem.getPath());
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);
        File file = new File(savePath);
        assertTrue(file.exists());
    }

    /**
     * Use case 2:
     * Valid execution of content update event.
     *
     * @throws Exception
     */
    @Test
    public void testOnContentUpdate() throws Exception {
        // Set up mocks
        Item mockItem = createItemMock();
        when(contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockItem);

        //PreviewDeployer spySUT = Mockito.spy(previewDeployerSUT);
        RepositoryEventMessage message = createEventMessage(mockItem);
        EventCallback verifyReady = new EventCallback();
        repositoryReactor.notify(EventConstants.REPOSITORY_UPDATE_EVENT, Event.wrap(message), verifyReady);

        while (!verifyReady.isCompleted()) {
            Thread.sleep(1);
        }

        verify(previewDeployerSUT, times(0)).onContentCreate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(1)).onContentUpdate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(0)).onContentDelete(Mockito.any(Event.class));

        verify(contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
        verify(previewDeployerSUT, times(1)).writeFile(Mockito.anyString(), Mockito.any(InputStream.class));
        StringBuilder sbSavePath = new StringBuilder(previewDeployerSUT.getPreviewStoreRootPath());
        sbSavePath.append(File.separator);
        sbSavePath.append(mockItem.getPath());
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);
        File file = new File(savePath);
        assertTrue(file.exists());
    }

    /**
     * Use case 3:
     * Valid execution of content delete event.
     *
     * @throws Exception
     */
    @Test
    public void testOnContentDelete() throws Exception {
        // Set up mocks
        Item mockItem = createItemMock();

        StringBuilder sbSavePath = new StringBuilder(previewDeployerSUT.getPreviewStoreRootPath());
        sbSavePath.append(File.separator);
        sbSavePath.append(mockItem.getPath());
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);
        File file = new File(savePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        assertTrue(file.exists());

        //PreviewDeployer spySUT = Mockito.spy(previewDeployerSUT);
        RepositoryEventBulkOpMessage message = createDeleteEventMessage(mockItem);
        DeleteEventCallback verifyReady = new DeleteEventCallback();
        repositoryReactor.notify(EventConstants.REPOSITORY_DELETE_EVENT, Event.wrap(message), verifyReady);

        while (!verifyReady.isCompleted()) {
            Thread.sleep(1);
        }

        verify(previewDeployerSUT, times(0)).onContentCreate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(0)).onContentUpdate(Mockito.any(Event.class));
        verify(previewDeployerSUT, times(1)).onContentDelete(Mockito.any(Event.class));

        assertTrue(!file.exists());
    }

    @Override
    protected Item createItemMock() {
        Item toRet = super.createItemMock();
        toRet.setPath("/test-content/" + UUID.randomUUID().toString() + "/sample.xml");
        return toRet;
    }

    private RepositoryEventMessage createEventMessage(Item item) {
        RepositoryEventMessage message = new RepositoryEventMessage();
        message.setPath(item.getPath());
        message.setSite("test");
        message.setItemId(item.getId().getItemId());
        return message;
    }

    private RepositoryEventBulkOpMessage createDeleteEventMessage(Item item) {
        RepositoryEventBulkOpMessage message = new RepositoryEventBulkOpMessage();
        message.setAffectedPaths(Arrays.asList(item.getPath()));
        return message;
    }

    private class EventCallback implements Consumer<Event<RepositoryEventMessage>> {
        private boolean completed = false;

        @Override
        public void accept(Event<RepositoryEventMessage> event) {
            completed = true;
        }

        public boolean isCompleted() {
            return completed;
        }
    }

    private class DeleteEventCallback implements Consumer<Event<RepositoryEventBulkOpMessage>> {
        private boolean completed = false;

        @Override
        public void accept(Event<RepositoryEventBulkOpMessage> event) {
            completed = true;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}