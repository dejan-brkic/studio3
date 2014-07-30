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

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.event.EventConstants;
import org.craftercms.studio.impl.event.RepositoryEventBulkOpMessage;
import org.craftercms.studio.impl.event.RepositoryEventMessage;
import org.craftercms.studio.internal.content.ContentManager;
import org.craftercms.commons.ebus.annotations.EListener;
import org.craftercms.commons.ebus.annotations.EventHandler;
import org.craftercms.commons.ebus.annotations.EventSelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.event.Event;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Preview deployer.
 *
 * @author Dejan Brkic
 */
@EListener
public class PreviewDeployer {

    private Logger log = LoggerFactory.getLogger(PreviewDeployer.class);

    private String previewStoreRootPath;
    private boolean enabled = false;
    private ContentManager contentManager;

    private Context dummyContext = new Context(UUID.randomUUID().toString(), new Tenant());

    @EventHandler(
            event = EventConstants.REPOSITORY_CREATE_EVENT,
            ebus = EventConstants.REPOSITORY_REACTOR,
            type = EventSelectorType.REGEX)
    public void onContentCreate(Event<RepositoryEventMessage> event) throws StudioException {
        if (!enabled) {
            return;
        }

        RepositoryEventMessage message = event.getData();
        Item item = contentManager.read(dummyContext, message.getSite(), message.getItemId());
        String path = message.getPath();
        InputStream content = item.getInputStream();

        try {
            writeFile(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(
            event = EventConstants.REPOSITORY_UPDATE_EVENT,
            ebus = EventConstants.REPOSITORY_REACTOR,
            type = EventSelectorType.REGEX)
    public void onContentUpdate(Event<RepositoryEventMessage> event) throws StudioException {

        if (!enabled) {
            return;
        }

        RepositoryEventMessage message = event.getData();
        Item item = contentManager.read(dummyContext, message.getSite(), message.getItemId());
        String path = message.getPath();
        InputStream content = item.getInputStream();

        try {
            writeFile(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeFile(final String path, final InputStream content) throws IOException {

        BufferedInputStream contentStream = new BufferedInputStream(content);
        StringBuilder sbSavePath = new StringBuilder(previewStoreRootPath);
        sbSavePath.append(File.separator);
        sbSavePath.append(path);
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);

        File file = new File(savePath);
        OutputStream outputStream = null;

        try {
            contentStream.mark(0);
            contentStream.reset();
            // create new file if doesn't exist
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            IOUtils.copy(contentStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("Error: not able to open output stream for file " + path);
            }
            throw e;
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Error: not able to write file " + path);
            }
            throw e;
        } finally {

            IOUtils.closeQuietly(outputStream);
        }

    }

    @EventHandler(
            event = EventConstants.REPOSITORY_DELETE_EVENT,
            ebus = EventConstants.REPOSITORY_REACTOR,
            type = EventSelectorType.REGEX)
    public void onContentDelete(Event<RepositoryEventBulkOpMessage> event) {
        if (!enabled) {
            return;
        }

        RepositoryEventBulkOpMessage message = event.getData();
        List<String> affectedPaths = message.getAffectedPaths();
        for (String path : affectedPaths) {
            StringBuilder sbDeletePath = new StringBuilder(previewStoreRootPath);
            sbDeletePath.append(File.separator);
            sbDeletePath.append(path);
            String deletePath = sbDeletePath.toString();
            deletePath = deletePath.replaceAll(File.separator + "+", File.separator);
            File file = new File(deletePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String getPreviewStoreRootPath() {
        return previewStoreRootPath;
    }

    public void setPreviewStoreRootPath(String previewStoreRootPath) {
        this.previewStoreRootPath = previewStoreRootPath;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
