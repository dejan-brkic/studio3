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

package org.craftercms.studio.controller.services.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.craftercms.studio.api.content.TemplateService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.documentation.configuration.DocumentationServiceOrder;
import org.craftercms.studio.utils.RestControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 * Template Service RESTful controller.
 * Defines services for templates.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping("/api/1/template")
@Api(value = "TemplateService", description = "Template RESTful services", position = DocumentationServiceOrder.TEMPLATE_SERVICE)
public class TemplateServiceController {

    @Autowired
    private TemplateService templateService;

    /**
     * Create a new template.
     *
     * @param site          site identifier
     * @param parentId      parent identifier
     * @param fileName      file name
     * @param file          template file
     * @param properties    template properties
     * @return              template descriptor item
     * @throws StudioException
     */
    @ApiOperation(value = "Create new template", notes = "Adds new template file to repository",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/create/{site}",
        method = RequestMethod.POST,
        params = { "parent_id", "file_name"}
    )
    @ResponseBody
    public Item create(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) final String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name", required = true) final String fileName,

            @ApiParam(name = "file", required = true, value = "String")
            @RequestParam(value = "file", required = true) final MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        InputStream content = null;
        try {
            content = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException(StudioException.ErrorCode.SYSTEM_ERROR, e);
        }
        Item item = templateService.create(context, site, parentId, fileName, content, properties);
        return item;
    }

    /**
     * Create a new template.
     *
     * @param site          site identifier
     * @param parentId      parent identifier
     * @param fileName      file name
     * @param content       template content
     * @param properties    template properties
     * @return              template descriptor
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/create/{site}",
        method = RequestMethod.POST,
        params = { "parent_id", "file_name", "content" }
    )
    @ResponseBody
    public Item create(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) final String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name", required = true) final String fileName,

            @ApiParam(name = "content", required = true, value = "String")
            @RequestParam(value = "content", required = true) final String content,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        Item item = templateService.create(context, site, parentId, fileName, content, properties);
        return item;
    }

    /**
     * Get template descriptor.
     *
     * @param site      site identifier
     * @param itemId    template identifier
     * @return          template descriptor
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/read/{site}",
        method = RequestMethod.GET
    )
    @ResponseBody
    public Item read(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) final String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId templateItemId = new ItemId(itemId);
        Item item = templateService.read(context, site, templateItemId);
        return item;
    }

    /**
     * Update template.
     *
     * @param site          site identifier
     * @param itemId        template identifier
     * @param file          template file
     * @param properties    template properties
     * @return              template descriptor
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/update/{site}",
        method = RequestMethod.POST,
        params = { "item_id" }
    )
    @ResponseBody
    public Item update(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) final String itemId,

            @ApiParam(name = "file", required = true, value = "MultipartFile")
            @RequestParam(value = "file", required = true) final MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId templateItemId = new ItemId(itemId);
        InputStream content = null;
        try {
            content = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException(StudioException.ErrorCode.SYSTEM_ERROR, e);
        }
        Item item = templateService.update(context, site, templateItemId, content, properties);
        return item;
    }

    /**
     * Update template.
     *
     * @param site          site identifier
     * @param itemId        template identifier
     * @param content       template content
     * @param properties    template properties
     * @return              template descriptor
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/update/{site}",
        method = RequestMethod.POST,
        params = { "item_id", "content" }
    )
    @ResponseBody
    public Item update(

        @ApiParam(name = "site", required = true, value = "String")
        @PathVariable final String site,

        @ApiParam(name = "item_id", required = true, value = "String")
        @RequestParam(value = "item_id", required = true) final String itemId,

        @ApiParam(name = "content", required = true, value = "String")
        @RequestParam(value = "content", required = true) final String content,

        @ApiParam(name = "properties", required = true, value = "Map<String, String>")
        @RequestParam(value = "properties", required = true) final Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId templateItemId = new ItemId(itemId);
        Item item = templateService.update(context, site, templateItemId, content, properties);
        return item;
    }

    /**
     * Delete template descriptor.
     *
     * @param site      site identifier
     * @param itemId    template identifier
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/delete/{site}",
        method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true)
            @RequestParam(value = "item_id", required = true) final String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId templateItemId = new ItemId(itemId);
        templateService.delete(context, site, templateItemId);
    }

    /**
     * Find templates by given criteria.
     *
     * @param site      site identifier
     * @param query     search query
     * @return          list of template descriptors
     * @throws StudioException
     */
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Item.class),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(
        value = "/find/{site}",
        method = RequestMethod.GET
    )
    public List<Item> findBy(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "query", required = true, value = "String")
            @RequestParam(value = "query", required = true) final String query
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void setTemplateService(final TemplateService templateService) {
        this.templateService = templateService;
    }
}
