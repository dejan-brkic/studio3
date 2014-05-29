/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.internal.content;

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Repository Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 */
public interface ContentManager {
    /**
     * create content.
     *
     * @param context context
     * @param path    path to write to
     * @param item    item meta-data
     * @param content content
     */
    ItemId create(Context context, String site, String path, Item item, InputStream content) throws StudioException;

    /**
     * Read content for given item id.
     *
     * @param context context
     * @param itemId  item id
     * @return content item
     */
    Item read(Context context, String site, String itemId) throws StudioException;
    //
    //    /**
    //     * Read content for given item id and version.
    //     *
    //     * @param context context
    //     * @param itemId  item id
    //     * @param version content version
    //     * @return content
    //     */
    //    InputStream read(Context context, ItemId itemId, String version) throws StudioException;
    //
    //    // TODO Add read meta-data, write meta-data
    //    // TODO UPDATE TO USE ItemId
    //
    //    // TODO add version management support here?
    //

    /**
     * Write content.
     *
     * @param context    security context
     * @param site       site identifier
     * @param itemId     content identifier
     * @param lockHandle lock handle
     * @param content    content to be written
     * @throws StudioException
     */
    void write(Context context, String site, ItemId itemId, LockHandle lockHandle,
               InputStream content) throws StudioException;

    /**
     * Delete content items.
     *
     * @param context       context
     * @param itemsToDelete items to delete
     */
    void delete(Context context, List<Item> itemsToDelete) throws StudioException;
    //
    //    /**
    //     * Copy items to destination path.
    //     *
    //     * @param context         context
    //     * @param itemsToCopy     items to copy
    //     * @param destinationPath destination path
    //     * @param includeChildren include children
    //     */
    //    void copy(Context context, List<Item> itemsToCopy, String destinationPath, boolean includeChildren);
    //

    /**
     * Move item to destination path.
     *
     * @param context         context
     * @param item            item to move
     * @param destinationPath destination path
     */
    void move(Context context, Item item, String destinationPath) throws StudioException;

    /**
     * Move items to destination path.
     *
     * @param context         context
     * @param itemsToMove     items to move
     * @param destinationPath destination path
     */
    void move(Context context, List<Item> itemsToMove, String destinationPath) throws StudioException;

    /**
     * Get children.
     *
     * @param context context
     * @param itemId  item id
     * @return children
     */
    List<Item> list(Context context, String site, String itemId) throws StudioException;

    /**
     * Create new folder.
     *
     * @param context    caller's context
     * @param site       site identifier
     * @param path       path where to create folder
     * @param folderName folder name
     * @return content item representing folder
     */
    Item createFolder(Context context, String site, String path, String folderName) throws StudioException;
    //
    //    /**
    //     * Get sub-tree with given item as root.
    //     *
    //     * @param context    context
    //     * @param itemId     item id
    //     * @param depth      depth
    //     * @param filters    filters
    //     * @param extractors extractors
    //     * @return tree of items
    //     */
    //    Tree<Item> tree(Context context, String itemId, int depth, List<ItemFilter> filters,
    //                    List<ItemExtractor> extractors);
    //
    //    /**
    //     * Get sites.
    //     *
    //     * @param context context
    //     * @return list of sites
    //     */
    //    List<Site> getSiteList(Context context);
}
