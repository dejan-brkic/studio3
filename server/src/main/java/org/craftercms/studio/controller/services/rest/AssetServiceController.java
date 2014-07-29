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

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.ErrorManager;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.exceptions.StudioServerErrorCode;
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
 * Asset controller.
 * Defines restful services for assets.
 *
 * @author Dejan Brkic
 */

@Controller
@RequestMapping(value = "/api/1/content/asset")
@Api(
        value = "Asset Services",
        description = "Asset RESTful Services",
        position = 1
)
public class AssetServiceController {

    @Autowired
    private AssetService assetService;

    /**
     * Add asset file to repository.
     *
     * @param site       site identifier
     * @param parentId   parent identifier
     * @param fileName   asset file name
     * @param file       asset content file
     * @param mimeType   mime type
     * @param properties additional properties
     * @return item representing given asset in repository
     * @throws StudioException
     */
    @ApiOperation(
            value = "Create new asset",
            notes = "Adds new asset file to repository by uploading file",
            produces = MediaType.APPLICATION_JSON_VALUE, position = 1, response = Item.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 400, message = "Bad request")}
    )
    @RequestMapping(
            value = "/create/{site}",
            params = {"parent_id", "file_name", "mime_type"},
            method = RequestMethod.POST
    )
    @ResponseBody
    public Item create(
            @ApiParam(name = "site", required = true, value = "Site identifier", allowableValues = "Site name")
            @PathVariable final String site,

            @ApiParam(name = "parent_id", required = true, value = "Parent identifier")
            @RequestParam(value = "parent_id") final String parentId,

            @ApiParam(name = "file_name", required = true, value = "File name")
            @RequestParam(value = "file_name") final String fileName,

            @ApiParam(name = "file", required = true, value = "Multipart file")
            @RequestParam(value = "file") final MultipartFile file,

            @ApiParam(name = "mime_type", required = true, value = "Mime type")
            @RequestParam(value = "mime_type") final String mimeType,

            @ApiParam(name = "properties", required = false, value = "Properties key/value map")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        InputStream contentStream = null;
        try {
            contentStream = file.getInputStream();
        } catch (IOException e) {
            throw ErrorManager.createError(StudioServerErrorCode.SYSTEM_ERROR);
        }
        Context context = RestControllerUtils.createMockContext();
        return assetService.create(context, site, parentId, fileName, contentStream, mimeType, properties);
    }


    /**
     * Read asset meta-data for given item id.
     *
     * @param site   site identifier
     * @param itemId asset identifier
     * @return asset meta data
     * @throws StudioException
     */
    @ApiOperation(
            value = "Read asset metadata",
            notes = "Reads asset metadata from repository for given id.",
            position = 2)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 400, message = "Bad request")}
    )
    @RequestMapping(
            value = "/read/{site}",
            params = {"item_id"},
            method = RequestMethod.GET
    )
    @ResponseBody
    public Item read(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") final String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.read(context, site, itemId);
    }

    /**
     * Read textual content for given asset id.
     *
     * @param site   site identifier
     * @param itemId asset item id
     * @return textual content of asset
     * @throws StudioException
     */
    @ApiOperation(
            value = "read textual content of asset",
            position = 3)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @RequestMapping(
            value = "/read_text/{site}",
            params = {"item_id"},
            method = RequestMethod.GET)
    @ResponseBody
    public String getTextContent(
            @ApiParam(name = "site", required = true, value = "String", allowableValues = "existing site identifiers")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") final String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.getTextContent(context, site, itemId);
    }

    /**
     * Read asset content for given id.
     *
     * @param site     site identifier
     * @param itemId   asset identifier
     * @param response content
     * @throws StudioException
     */
    @ApiOperation(value = "read asset content", position = 4)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = InputStream.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @RequestMapping(
            value = "/get_content/{site}",
            params = {"item_id"},
            method = RequestMethod.GET)
    public void getInputStream(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") final String itemId,

            final HttpServletResponse response
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        InputStream content = assetService.getInputStream(context, site, id);
        OutputStream out = null;
        Item item = assetService.read(context, site, itemId);
        response.setContentType(item.getMimeType());
        try {
            out = response.getOutputStream();
            IOUtils.copy(content, out);
        } catch (IOException e) {
            throw ErrorManager.createError(StudioServerErrorCode.IO_ERROR, e);
        } finally {
            IOUtils.closeQuietly(content);
            IOUtils.closeQuietly(out);
        }

    }

    /**
     * Update asset with given id and file.
     *
     * @param site       site identifier
     * @param itemId     asset item id
     * @param file       asset content file
     * @param properties additional properties
     * @return item representing asset
     * @throws StudioException
     */
    @ApiOperation(value = "update asset", position = 5)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 400, message = "Bad Request")})
    @RequestMapping(
            value = "/update/{site}",
            params = {"item_id"},
            method = RequestMethod.POST
    )
    @ResponseBody
    public Item update(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") final String itemId,

            @ApiParam(name = "file", required = true, value = "org.springframework.web.multipartMultipartFile")
            @RequestParam(value = "file") final MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        InputStream content = null;
        try {
            content = file.getInputStream();
        } catch (IOException e) {
            throw ErrorManager.createError(StudioServerErrorCode.SYSTEM_ERROR);
        }
        return assetService.update(context, site, id, content, properties);
    }

    /**
     * Delete asset for given item id.
     *
     * @param site   site identifier
     * @param itemId asset item id
     * @throws StudioException
     */
    @ApiOperation(value = "delete asset", position = 6)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Success, no content"),
            @ApiResponse(code = 400, message = "Bad request")})
    @RequestMapping(
            value = "/delete/{site}",
            params = {"item_id"},
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") final String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        assetService.delete(context, site, id);
    }

    /**
     * Find asset by query.
     *
     * @param site  site identifier
     * @param query query
     * @return list of asset items
     * @throws StudioException
     */
    @ApiOperation(value = "find assets", position = 7)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Item.class),
            @ApiResponse(code = 400, message = "Bad request")})
    @RequestMapping(
            value = "/find/{site}",
            params = {"query"},
            method = RequestMethod.GET)
    @ResponseBody
    public List<Item> findBy(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "query", required = true, value = "String")
            @RequestParam(value = "query") final String query
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.findBy(context, site, query);
    }

    /**
     * List children for given item.
     *
     * @param site   site identifier
     * @param itemId parent asset item id
     * @return list of children items
     * @throws StudioException
     */
    @ApiOperation(value = "list children assets")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Item.class), @ApiResponse(code = 400,
        message = "Bad request")})
    @RequestMapping(
        value = "/list/{site}",
        method = RequestMethod.GET
    )
    @ResponseBody
    public List<Item> list(

        @ApiParam(name = "site", required = true, value = "String") @PathVariable final String site,

        @ApiParam(name = "item_id", required = false, value = "String") @RequestParam(value = "item_id",
            required = false) final String itemId) throws StudioException {
        Context context = RestControllerUtils.createMockContext();
        ItemId assetItemId = null;
        if (StringUtils.isNotEmpty(itemId)) {
            assetItemId = new ItemId(itemId);
        }
        return assetService.list(context, site, assetItemId);
    }
}
