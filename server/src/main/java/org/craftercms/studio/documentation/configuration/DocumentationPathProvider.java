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

package org.craftercms.studio.documentation.configuration;

import javax.servlet.ServletContext;

import com.mangofactory.swagger.core.SwaggerPathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Dejan Brkic
 */
public class DocumentationPathProvider implements SwaggerPathProvider {

    private String hostUrl;

    private SwaggerPathProvider defaultSwaggerPathProvider;
    @Autowired
    private ServletContext servletContext;


    @Override
    public String getApiResourcePrefix() {
        return defaultSwaggerPathProvider.getApiResourcePrefix();
    }

    public String getAppBasePath() {
        return UriComponentsBuilder
            .fromHttpUrl(hostUrl)
            .path(servletContext.getContextPath())
            .build()
            .toString();
    }

    @Override
    public String sanitizeRequestMappingPattern(final String requestMappingPattern) {
        String result = requestMappingPattern;
        //remove regex portion '/{businessId:\\w+}'
        result = result.replaceAll("\\{(.*?):.*?\\}", "{$1}");
        return result.isEmpty() ? "/" : result;
    }

    public void setDefaultSwaggerPathProvider(SwaggerPathProvider defaultSwaggerPathProvider) {
        this.defaultSwaggerPathProvider = defaultSwaggerPathProvider;
    }

    public void setHostUrl(final String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
