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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.event.EventConstants;
import org.craftercms.studio.impl.event.RepositoryEventBulkOpMessage;
import org.craftercms.studio.impl.event.RepositoryEventMessage;
import org.craftercms.studio.impl.exception.StudioImplErrorCode;
import org.craftercms.studio.internal.content.ContentManager;
import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.repo.content.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.Reactor;
import reactor.event.Event;

/**
 * Repository Manager Implementation.
 *
 * @author Dejan Brkic
 */

public class ContentManagerImpl implements ContentManager {

    private ContentService contentService;
    private PathService pathService;

    private Reactor repositoryReactor;

    @Override
    public ItemId create(final Context context, final String site, final String path, final Item item,
                         final InputStream content) throws StudioException {
        Item newItem = contentService.create(context.getTicket(), site, path, item, content);
        RepositoryEventMessage message = new RepositoryEventMessage();
        message.setItemId(newItem.getId().getItemId());
        message.setSite(site);
        message.setPath(newItem.getPath());
        repositoryReactor.notify(EventConstants.REPOSITORY_CREATE_EVENT, Event.wrap(message));
        return newItem.getId();
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        return contentService.read(context.getTicket(), site, itemId);
    }

    @Override
    public void write(final Context context, final String site, final ItemId itemId, final LockHandle lockHandle,
                      final InputStream content) throws StudioException {

        Item item = contentService.read(context.getTicket(), site, itemId.getItemId());
        contentService.update(context.getTicket(), item, content);
        RepositoryEventMessage message = new RepositoryEventMessage();
        message.setItemId(item.getId().getItemId());
        message.setSite(site);
        message.setPath(item.getPath());
        repositoryReactor.notify(EventConstants.REPOSITORY_UPDATE_EVENT, Event.wrap(message));
    }

    @Override
    public void delete(final Context context, final List<Item> itemsToDelete) throws StudioException {
        if (itemsToDelete == null || itemsToDelete.size() < 1) {
            return;
        }
        List<String> deletedPaths = new ArrayList<String>();
        for (Item item : itemsToDelete) {
            contentService.delete(context.getTicket(), item.getId().getItemId());
            deletedPaths.add(item.getPath());
        }
        RepositoryEventBulkOpMessage message = new RepositoryEventBulkOpMessage();
        message.setAffectedPaths(deletedPaths);
        repositoryReactor.notify(EventConstants.REPOSITORY_DELETE_EVENT, Event.wrap(message));
    }

    @Override
    public void move(final Context context, final Item item, final String destinationPath) throws StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void move(final Context context, final List<Item> itemsToMove, final String destinationPath) throws
        StudioException {
        throw ErrorManager.createError(StudioImplErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Item> list(final Context context, final String site, final String itemId) throws StudioException {

        // Get only direct children (depth=1) and no filters
        Tree<Item> resultTree = contentService.getChildren(context.getTicket(), site, itemId, 1, null);
        if (resultTree != null) {
            TreeNode<Item> parent = resultTree.getRootNode();
            List<Item> toRet = new ArrayList<Item>();
            Set<TreeNode<Item>> children = parent.getChildren();
            if (children != null && children.size() > 0) {
                for (TreeNode<Item> child : children) {
                    toRet.add(child.getValue());
                }
            }
            return toRet;
        } else {
            return null;
        }
    }

    @Override
    public Item createFolder(final Context context, final String site, final String path,
                             final String folderName) throws StudioException {
        String ticket = context.getTicket();
        return contentService.createFolder(ticket, site, path, folderName);
    }

    // Getters and setters

    public ContentService getContentService() {
        return contentService;
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }

    public void setPathService(final PathService pathService) {
        this.pathService = pathService;
    }

    public void setRepositoryReactor(Reactor repositoryReactor) {
        this.repositoryReactor = repositoryReactor;
    }
}
