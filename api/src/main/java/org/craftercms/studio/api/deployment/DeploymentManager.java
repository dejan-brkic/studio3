/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
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
package org.craftercms.studio.api.deployment;

import java.util.List;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Deployment Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface DeploymentManager {

    /**
     * Get deployment history.
     *
     * @param context context
     * @param site    site
     * @param filters filters
     * @return list of items
     */
    List<Item> history(Context context, String site, List<String> filters) throws StudioException;

    /**
     * Get deployment channels.
     *
     * @param context     context
     * @param site        site
     * @param environment environment
     * @return list of deployment channels
     */
    List<DeploymentChannel> channels(Context context, String site, String environment) throws StudioException;

    /**
     * Create or update deployment channel.
     *
     * @param context context
     * @param site    site
     * @param channel channel
     */
    void updateChannel(Context context, String site, DeploymentChannel channel) throws StudioException;

    /**
     * Remove deployment channel.
     *
     * @param context context
     * @param site    site
     * @param channel channel
     */
    void removeChannel(Context context, String site, DeploymentChannel channel) throws StudioException;

    /**
     * Deploy items.
     *
     * @param context context
     * @param itemIds list of item ids
     */
    void deploy(Context context, String site, List<String> itemIds) throws StudioException;

    /**
     * Get deployment channel status.
     *
     * @param context context
     * @param site    site
     * @param channel channel
     */
    String status(Context context, String site, DeploymentChannel channel) throws StudioException;

    /**
     * Get deployment channel version.
     *
     * @param context context
     * @param site    site
     * @param channel channel
     */
    long version(Context context, String site, DeploymentChannel channel) throws StudioException;

    /**
     * Abort deployment.
     *
     * @param context context
     * @param site    site
     * @param channel channel
     */
    void abort(Context context, String site, DeploymentChannel channel) throws StudioException;
}
