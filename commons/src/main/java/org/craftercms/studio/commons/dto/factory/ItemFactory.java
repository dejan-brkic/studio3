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

package org.craftercms.studio.commons.dto.factory;

import org.craftercms.studio.commons.dto.AssetItem;
import org.craftercms.studio.commons.dto.ComponentItem;
import org.craftercms.studio.commons.dto.DefaultItem;
import org.craftercms.studio.commons.dto.FolderItem;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemTypes;
import org.craftercms.studio.commons.dto.PageItem;
import org.craftercms.studio.commons.dto.ScriptItem;
import org.craftercms.studio.commons.dto.TemplateItem;
import org.craftercms.studio.commons.exception.ErrorCode;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Item factory.
 *
 * @author Dejan Brkic
 */
public class ItemFactory {

    public static Item createEmptyItem(String itemType) {
        Item item = null;
        switch (itemType) {
            case ItemTypes.ASSET:
                item = new AssetItem();
                break;

            case ItemTypes.RENDERING_TEMPLATE:
                item = new TemplateItem();
                break;

            case ItemTypes.COMPONENT:
                item = new ComponentItem();
                break;

            case ItemTypes.PAGE:
                item = new PageItem();
                break;

            case ItemTypes.SCRIPT:
                item = new ScriptItem();
                break;

            case ItemTypes.FOLDER:
                item = new FolderItem();
                break;

            default:
                item = new DefaultItem();
        }
        return item;
    }

    public static Item createItem(String itemType, String creator, String filename, String label) {
        Item item = createEmptyItem(itemType);
        item.setCreatedBy(creator);
        item.setFileName(filename);
        item.setLabel(label);
        return item;
    }
}
