package org.craftercms.studio.impl.analytics;

import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsService;
import org.craftercms.studio.commons.dto.AnalyticsReport;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.exception.ErrorCode;

/**
 * {@link AnalyticsService} default implementation.
 *
 * @author Dejan Brkic
 */
public class AnalyticsServiceImpl implements AnalyticsService {
    public AnalyticsServiceImpl() {
    }


    //@Override
    public AnalyticsReport report(final Context context, final String site,
                                  final String report, final Map<String, Object> params) throws StudioException
    {
        throw ErrorManager.createError(ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public AnalyticsReport generateReport(final Context context, final String site, final String reportId, final
    Map<String, Object> params) throws StudioException {
        throw ErrorManager.createError(ErrorCode.NOT_IMPLEMENTED);
    }
}

