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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.exception.StudioImplErrorCode;
import org.craftercms.studio.internal.content.ContentManager;
import org.craftercms.studio.repo.content.PathService;

/**
 * Implementation of {@link org.craftercms.studio.api.content.AssetService}.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImpl implements AssetService {

    private ContentManager contentManager;
    private SecurityService securityService;
    private String repoRootPath;
    private PathService pathService;

    // Content manager requires Item object to add new item to repository
    private Item createAssetItem(final String fileName) {
        Item item = new Item();
        item.setCreatedBy(RandomStringUtils.random(10));
        item.setFileName(fileName);
        item.setLabel(fileName);
        return item;
    }

    /**
     * Create new asset item.
     * {@link org.craftercms.studio.api.content.AssetService}
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content InputStream, can be null if creating a 0 byte file
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName,
                       final InputStream content, final String mimeType, final Map<String,
        String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            StringBuilder sb = new StringBuilder(destinationPath);
            sb.append(File.separator);
            sb.append(fileName);
            Item item = createAssetItem(fileName);
            ItemId itemId = contentManager.create(context, site, destinationPath, item, content);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content as a string (textual content)
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return item representing asset
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName,
                       final String content, final String mimeType, final Map<String,
        String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = createAssetItem(fileName);
            InputStream contentStream = IOUtils.toInputStream(content);
            ItemId itemId = contentManager.create(context, site, destinationPath, item, contentStream);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName,
                       final byte[] content, final String mimeType, final Map<String,
        String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = createAssetItem(fileName);
            InputStream contentStream = new ByteArrayInputStream(content);
            ItemId itemId = contentManager.create(context, site, destinationPath, item, contentStream);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            return contentManager.read(context, site, itemId);
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public String getTextContent(final Context context, final String site, final String itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = contentManager.read(context, site, itemId);
            InputStream content = item.getInputStream();
            try {
                return IOUtils.toString(content);
            } catch (IOException e) {
                throw ErrorManager.createError(StudioImplErrorCode.IO_ERROR, e);
            }
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public InputStream getInputStream(final Context context, final String site,
                                      final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = contentManager.read(context, site, itemId.getItemId());
            return item.getInputStream();
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            contentManager.write(context, site, itemId, lockHandle, content);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final String content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            InputStream contentStream = IOUtils.toInputStream(content);
            contentManager.write(context, site, itemId, lockHandle, contentStream);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final byte[] content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            InputStream contentStream = new ByteArrayInputStream(content);
            contentManager.write(context, site, itemId, lockHandle, contentStream);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            List<Item> itemList = new ArrayList<>();
            Item item = contentManager.read(context, site, itemId.getItemId());
            itemList.add(item);
            contentManager.delete(context, itemList);
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public List<Item> list(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (!(context != null && securityService.validate(context))) {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
        if (itemId != null) {
            return contentManager.list(context, site, itemId.getItemId());
        } else {
            String rootItemId = pathService.getItemIdByPath(context.getTicket(), site, repoRootPath);
            if (StringUtils.isEmpty(rootItemId)) {
                int lastIndex = repoRootPath.lastIndexOf("/");
                String rootParent = "/";
                String folderName = repoRootPath;
                if (lastIndex > 0) {
                    rootParent = repoRootPath.substring(0, lastIndex - 1);
                    folderName = repoRootPath.substring(lastIndex + 1);
                } else {
                    if (repoRootPath.startsWith("/")) {
                        folderName = repoRootPath.substring(1);
                    }
                }
                Item rootItem = contentManager.createFolder(context, site, rootParent, folderName);
                rootItemId = rootItem.getId().toString();
            }
            return contentManager.list(context, site, rootItemId);
        }
    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
    }

    // Getters and setters

    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    public void setSecurityService(final SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setRepoRootPath(final String repoRootPath) {
        this.repoRootPath = repoRootPath;
    }

    public void setPathService(final PathService pathService) {
        this.pathService = pathService;
    }
}
