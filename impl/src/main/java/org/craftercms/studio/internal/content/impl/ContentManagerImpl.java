package org.craftercms.studio.internal.content.impl;

import java.io.InputStream;
import java.util.Date;

import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;

/**
 * Repository Manager Implementation.
 *
 * @author Dejan Brkic
 */
public class ContentManagerImpl implements ContentManager {

    private ContentService contentService;

    private AuditDALService auditDALService;

    @Override
    public ItemId create(final Context context, final String site, final String path, final Item item,
                    final InputStream content) throws StudioException {
        Item newItem = contentService.create(context.getTicket(), site, path, item, content);
        auditDALService.logActivity(context.getTicket(), site, createActivity(site, path, item));
        return newItem.getId();
    }

    private Activity createActivity(final String site, final String path, final Item item) {
        Activity activity = new Activity();
        activity.setCreator("ME");
        activity.setDate(new Date());
        activity.setSiteName(site);
        activity.setTarget(path);
        activity.setType("CREATED");
        return activity;
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        return contentService.read(context.getTicket(), site, itemId);
    }

    // Getters and setters

    public ContentService getContentService() {
        return contentService;
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }

    public AuditDALService getAuditDALService() {
        return auditDALService;
    }

    public void setAuditDALService(final AuditDALService auditDALService) {
        this.auditDALService = auditDALService;
    }
}
