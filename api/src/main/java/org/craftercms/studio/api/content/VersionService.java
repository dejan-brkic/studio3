
package org.craftercms.studio.api.content;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DiffResult;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.Version;
import org.craftercms.studio.commons.exception.StudioException;


public interface VersionService {

    /**
     * Get version history for item
     *
     * @param context context
     * @param itemId  itemId
     * @return tree of version history
     */
    Tree<Version> history(Context context, String itemId) throws StudioException; // tree or list?


// TODO   NEED to tag, tag can be against a whole bunch of things at once (even trees?)

    /**
     * Revert version for item.
     *
     * @param context       context
     * @param itemId        itemId
     * @param revertVersion revertVersion
     */
    void revert(Context context, String itemId, String revertVersion) throws StudioException;

    /**
     * Difference between two versions.
     *
     * @param context  context
     * @param itemId   item id
     * @param version1 version1
     * @param version2 version2
     * @return differences
     */
    DiffResult diff(Context context, String itemId, String version1, String version2) throws StudioException;
}
