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

package org.craftercms.studio.internal.content.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author Dejan Brkic
 */
@Aspect
public class SimplePreviewDeployerAspect {

    private Logger log = LoggerFactory.getLogger(SimplePreviewDeployerAspect.class);

    private String previewStoreRootPath;
    private boolean enabled = false;
    private ContentManager contentManager;



    /*
    ItemId create(Context context, String site, String path, Item item, InputStream content) throws StudioException;
     */

    @Before(value = "execution(* org.craftercms.studio.internal.content.ContentManager.create(..))")
    public void createContent(JoinPoint joinPoint) {
        if (!enabled) {
            return;
        }
        Object[] arguments = joinPoint.getArgs();
        String path = (String)arguments[2];
        Item item = (Item)arguments[3];
        InputStream content = (InputStream)arguments[4];

        try {
            writeFile(path, item.getFileName(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    void write(Context context, String site, ItemId itemId, LockHandle lockHandle,
               InputStream content) throws StudioException;
     */
    @Before(value = "execution(* org.craftercms.studio.internal.content.ContentManager.write(..))")
    public void updateContent(JoinPoint joinPoint) {
        if (!enabled) {
            return;
        }
        Object[] arguments = joinPoint.getArgs();
        Context context = (Context)arguments[0];
        String site = (String)arguments[1];
        ItemId itemId = (ItemId)arguments[2];
        InputStream content = (InputStream)arguments[4];
        Item item = null;
        try {
            item = contentManager.read(context, site, itemId.getItemId());
        } catch (StudioException e) {
            log.error("Unable to process update for preview sync", e);
            return;
        }
        if (item != null) {
            String path = item.getPath();
            path = path.replace(item.getFileName(), "");
            try {
                writeFile(path, item.getFileName(), content);
            } catch (IOException e) {
                log.error("Unable to process update for preview sync", e);
            }
        }
    }

    protected void writeFile(String path, String filename, final InputStream content) throws IOException {

        //BufferedInputStream contentStream = new BufferedInputStream(content);
        StringBuilder sbSavePath = new StringBuilder(previewStoreRootPath);
        sbSavePath.append(File.separator);
        sbSavePath.append(path);
        sbSavePath.append(File.separator);
        sbSavePath.append(filename);
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);

        File file = new File(savePath);
        OutputStream outputStream = null;

        try {
            content.mark(0);
            // create new file if doesn't exist
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            IOUtils.copy(content, outputStream);

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
            content.reset();

            IOUtils.closeQuietly(outputStream);
        }

    }

    /*
    void delete(Context context, List<Item> itemsToDelete) throws StudioException;
     */
    @Before(value = "execution(* org.craftercms.studio.internal.content.ContentManager.delete(..))")
    public void deleteContent(JoinPoint joinPoint) {
        if (!enabled) {
            return;
        }

        Object[] arguments = joinPoint.getArgs();
        List<Item> items = (List<Item>)arguments[1];
        for (Item item : items) {
            StringBuilder sbDeletePath = new StringBuilder(previewStoreRootPath);
            sbDeletePath.append(File.separator);
            sbDeletePath.append(item.getPath());
            String deletePath = sbDeletePath.toString();
            deletePath = deletePath.replaceAll(File.separator + "+", File.separator);
            File file = new File(deletePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Required
    public void setPreviewStoreRootPath(final String previewStoreRootPath) {
        this.previewStoreRootPath = previewStoreRootPath;
    }

    @Required
    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
