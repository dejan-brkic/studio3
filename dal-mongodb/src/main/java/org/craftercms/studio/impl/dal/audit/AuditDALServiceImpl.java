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

package org.craftercms.studio.impl.dal.audit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.craftercms.studio.api.dal.audit.AuditDALService;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.dal.AbstractDALService;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Audit DAL Service MongoDB implementation.
 *
 * @author Dejan Brkic
 */
public class AuditDALServiceImpl extends AbstractDALService implements AuditDALService {

    private Logger logger = LoggerFactory.getLogger(AuditDALServiceImpl.class);


    @Override
    public List<Activity> getActivities(final String ticket, final String site, final List<String> filters) {
        DBCollection collection = dalClient.getDBCollection(collectionName);
        DBCursor cursor = collection.find(new BasicDBObject("siteName", site));
        List<Activity> activityList2 = new ArrayList<>();
        while (cursor.hasNext()) {
            activityList2.add(toActivity(cursor.next()));
        }
        return activityList2;
                            /*
        MongoCollection activities = dalClient.getCollection(collectionName);
        Iterable<Activity> result = activities.find("{'siteName':#}", site).as(Activity.class);
        List<Activity> activityList = new ArrayList<>();
        for (final Activity aResult : result) {
            activityList.add(aResult);
        }
        return activityList;    */
    }

    private Activity toActivity(final DBObject obj) {
        BasicDBObject o = (BasicDBObject)obj;
        Activity act = new Activity();
        act.setId(o.getString("_id"));
        act.setSiteId(o.getString("siteId"));
        act.setSiteName(o.getString("siteName"));
        act.setType(o.getString("type"));
        act.setTarget(o.getString("target"));
        act.setCreator(o.getString("creator"));
        act.setDate(o.getDate("date"));

        //act.setTargetProperties();
        return act;
    }

    @Override
    public Activity logActivity(final String ticket, final String site, final Activity activity) {
        MongoCollection activities = dalClient.getCollection(collectionName);
        activities.insert(activity);
        return activity;
    }
}
