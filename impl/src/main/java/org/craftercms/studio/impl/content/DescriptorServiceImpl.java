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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.DescriptorService;
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
 * Descriptor Service implementation.
 *
 * @author Dejan Brkic
 */
public class DescriptorServiceImpl implements DescriptorService {

    private ContentManager contentManager;

    private SecurityService securityService;

    private String repoRootPath;
    private PathService pathService;

    /**
     * Create a new descriptor.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id as defined in the {@link org.craftercms.studio.api.content
     *                      .ContentTypeService}
     * @param parentId      the id of the parent item (can be a folder or a descriptor)
     * @param fileName      file name of the descriptor
     * @param content       the InputStream containing the XML that is compliant with the model defined in Studio
     *                      (typically done using Studio's Form Engine)
     * @param properties    key-value-pair properties, can be null
     * @return item representing descriptor
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String contentTypeId, final String parentId,
                       final String fileName, final InputStream content, final Map<String,
        String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = createDescriptorItem(fileName);
            ItemId itemId = contentManager.create(context, site, parentId, item, content);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Create a new descriptor.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id as defined in the {@link org.craftercms.studio.api.content
     *                      .ContentTypeService}
     * @param parentId      the id of the parent item (can be a folder or a descriptor)
     * @param fileName      file name of the descriptor
     * @param content       the XML that is compliant with the model defined in Studio (typically done using
     *                      Studio's Form Engine)
     * @param properties    key-value-pair properties, can be null
     * @return item representing descriptor
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String contentTypeId, final String parentId,
                       final String fileName, final String content, final Map<String, String> properties

    ) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = createDescriptorItem(fileName);
            InputStream contentStream = IOUtils.toInputStream(content);
            ItemId itemId = contentManager.create(context, site, parentId, item, contentStream);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Create a copy of existing descriptor.
     *
     * @param context  the caller's context
     * @param site     the site to use
     * @param itemId   the source item to duplicate
     * @param parentId the id of the parent item (can be a folder or a descriptor)
     * @param fileName file name of the descriptor
     * @return item representing descriptor
     * @throws StudioException
     */
    @Override
    public Item duplicate(final Context context, final String site, final ItemId itemId, final String parentId,
                          final String fileName) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item original = contentManager.read(context, site, itemId.getItemId());
            InputStream content = original.getInputStream();
            ItemId copyItemId = contentManager.create(context, site, parentId, original, content);
            Item copy = contentManager.read(context, site, copyItemId.getItemId());
            return copy;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Move/rename descriptor item.
     *
     * @param context  the caller's context
     * @param site     the site to use
     * @param itemId   the source item to move
     * @param parentId the id of the parent item (can be a folder or a descriptor)
     * @param fileName file name of the descriptor
     * @return item representing descriptor
     * @throws StudioException
     */
    @Override
    public Item move(final Context context, final String site, final ItemId itemId, final String parentId,
                     final String fileName) throws StudioException {
        if (context != null && securityService.validate(context)) {
            Item item = contentManager.read(context, site, itemId.getItemId());
            contentManager.move(context, item, parentId);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public Item read(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            return contentManager.read(context, site, itemId.getItemId());
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
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            contentManager.write(context, site, itemId, lockHandle, content);
            Item item = contentManager.read(context, site, itemId.getItemId());
            return item;
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
            Item item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            List<Item> items = new ArrayList<>();
            Item item = contentManager.read(context, site, itemId.getItemId());
            items.add(item);
            contentManager.delete(context, items);
        } else {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }
    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Item> list(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (!(context != null && securityService.validate(context))) {
            throw ErrorManager.createError(StudioImplErrorCode.INVALID_CONTEXT);
        }

        if (itemId != null) {
            return contentManager.list(context, site, itemId.getItemId());
        }

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

    private Item createDescriptorItem(final String fileName) {
        Item item = new Item();
        item.setCreatedBy(RandomStringUtils.random(10));
        item.setFileName(fileName);
        item.setLabel(fileName);
        return item;
    }

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
