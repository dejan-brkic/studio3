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

package org.craftercms.studio.controller.services.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.api.configuration.ConfigurationService;
import org.craftercms.studio.commons.dto.Configuration;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.controller.services.rest.dto.ConfigurationWriteRequest;
import org.craftercms.studio.exceptions.StudioServerErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Configuration controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/config")
public class ConfigurationController {

    private ConfigurationService configurationService;

    @RequestMapping(value = "/configuration/{site}", method = RequestMethod.GET)
    @ResponseBody
    public Configuration configuration(@PathVariable final String site, @RequestParam(required = true) final String
        module) throws StudioException {
        throw ErrorManager.createError(StudioServerErrorCode.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/configure/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void configure(@PathVariable final String site, @RequestParam(required = true) final String module,
                          @Valid @RequestBody final Configuration moduleConfiguration) throws StudioException {
        throw ErrorManager.createError(StudioServerErrorCode.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/content/{site}", method = RequestMethod.GET)
    public void content(@PathVariable final String site, @RequestParam(required = true) final String object,
                        final HttpServletRequest request, final HttpServletResponse response) throws StudioException {
        throw ErrorManager.createError(StudioServerErrorCode.NOT_IMPLEMENTED);

    }

    @RequestMapping(value = "/write/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void write(@PathVariable final String site, @RequestParam(required = true) final String object,
                      @Valid @RequestBody(required = true) final ConfigurationWriteRequest writeRequest) throws
        StudioException {
        InputStream contentStream = IOUtils.toInputStream(writeRequest.getContent());
        throw ErrorManager.createError(StudioServerErrorCode.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/list/{config}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> uiConfiguration(@PathVariable(value = "config") final String config) throws
        StudioException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = getClass().getResource("/extension/studio3/" + config + ".json");
        Map map;
        if (url == null) {
            throw ErrorManager.createError(StudioServerErrorCode.IO_ERROR);
        } else {
            try {
                map = mapper.readValue(Files.readAllBytes(Paths.get(url.toURI())), Map.class);
            } catch (URISyntaxException | IOException ex) {
                throw ErrorManager.createError(StudioServerErrorCode.SYSTEM_ERROR, ex);
            }
        }
        return map;
    }

}