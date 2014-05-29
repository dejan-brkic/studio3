package org.craftercms.studio.api.content;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Descriptor Service provides an API for managing general descriptors (XML files) in a CrafterCMS-based site.
 *
 * @author Sumer Jabri
 */
public interface DescriptorService {
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
     * @return the Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item create(Context context, String site, String contentTypeId, String parentId, String fileName,
                InputStream content, Map<String, String> properties) throws StudioException;

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
     * @return the Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item create(Context context, String site, String contentTypeId, String parentId, String fileName, String content,
                Map<String, String> properties) throws StudioException;

    // TODO consider                // additional, detailed, exceptions

    /**
     * Create a duplicate of an existing descriptor.
     *
     * @param context  the caller's context
     * @param site     the site to use
     * @param itemId   the source item to duplicate
     * @param parentId the id of the parent item (can be a folder or a descriptor)
     * @param fileName file name of the descriptor
     * @return the new Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item duplicate(Context context, String site, ItemId itemId, String parentId,
                   String fileName) throws StudioException;

    /**
     * Move a descriptor to a new path.
     *
     * @param context  the caller's context
     * @param site     the site to use
     * @param itemId   the source item to move
     * @param parentId the id of the parent item (can be a folder or a descriptor)
     * @param fileName file name of the descriptor
     * @return the new Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item move(Context context, String site, ItemId itemId, String parentId, String fileName) throws StudioException;

    /**
     * Read a descriptor and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item read(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Read descriptor textual content and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item file content as text
     * @throws StudioException
     */
    String getTextContent(Context context, String site, String itemId) throws StudioException;

    /**
     * Update a descriptor given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the InputStream containing the XML that is compliant with the model defined in Studio
     *                   (typically done using Studio's Form Engine)
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item update(Context context, String site, ItemId itemId, InputStream content, Map<String,
        String> properties) throws StudioException;

    /**
     * Update a descriptor given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the XML that is compliant with the model defined in Studio (typically done using
     *                   Studio's Form Engine)
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    Item update(Context context, String site, ItemId itemId, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Delete a descriptor.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to delete
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    void delete(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Find descriptors matching a query.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return list of items matching the query
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    List<Item> findBy(Context context, String site, String query) throws StudioException;

    /**
     * Lists all children for given item.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  parent item
     * @return list of children
     */
    List<Item> list(Context context, String site, ItemId itemId) throws StudioException;
}
