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


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.ContentManager;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.validation.LockHandleValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Repository Controller.
 */
@Controller
@RequestMapping("/api/1/content")
public class RepositoryController {
    /**
     * Content Manager instance.
     */
    @Autowired
    private ContentManager contentManager;
    /**
     * TODO: write java doc.
     * Get content for given item ID and version.
     *
     * @param itemId  itemId.
     * @param version   version.
     * @param request http request.
     * @param response http response.
     */
    @RequestMapping(value = "/read/{site}", method = RequestMethod.GET)
    public void getContent(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                           @RequestParam(required = false) final String version,
                           final HttpServletRequest request,
                           final HttpServletResponse response)
            throws StudioException {

        final InputStream content = this.contentManager.read(new Context(), itemId);
        try {
            final OutputStream out = response.getOutputStream();
            IOUtils.copy(content, out);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * TODO: write java doc.
     * Update content.
     * @param site site.
     * @param itemId    itemId.
     * @param request http request.
     * @param response http response.
     */
    @RequestMapping(value = "/update/{site}", method = RequestMethod.POST)
    public void update(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                       @Valid @RequestBody(required = true) final String content, final HttpServletRequest request,
                       final HttpServletResponse response) throws IOException {
        InputStream contentStream = IOUtils.toInputStream(content);
        this.contentManager.update(new Context(), itemId, contentStream);
    }

    /**
     * TODO: javadoc.
     * @param site site.
     * @param itemId itemId.
     * @param request http request
     * @param response http response
     */
    @RequestMapping(value = "/open/{site}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public LockHandle openForEdit(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                            final HttpServletRequest request, final HttpServletResponse response) {
        LockHandle lockHandle = this.contentManager.open(new Context(), itemId);
        return lockHandle;
    }

    /**
     * TODO: javadoc.
     * @param site site.
     * @param itemId itemId.
     * @param lockHandleId lockHandle.
     * @param content content.
     * @param request request
     * @param response response.
     */
    @RequestMapping(value = "/save/{site}", method = RequestMethod.POST)
    public void saveContent(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                            @RequestParam(required = true) final String lockHandleId,
                            @Valid @RequestBody final String content, final HttpServletRequest request,
                            final HttpServletResponse response) {
        InputStream contentStream = IOUtils.toInputStream(content);
        LockHandle lockHandle = new LockHandle();
        lockHandle.setId(lockHandleId);
        this.contentManager.save(new Context(), itemId, lockHandle, contentStream);
    }

    /**
     * TODO: javadoc
     * @param site site.
     * @param itemId site.
     * @param lockHandleId site.
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/close/{site}", method = RequestMethod.POST)
    public void close(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                      @RequestParam(required = true) final String lockHandleId,
                      final HttpServletRequest request, final HttpServletResponse response) {
        LockHandle lockHandle = new LockHandle();
        lockHandle.setId(lockHandleId);
        this.contentManager.close(new Context(), itemId, lockHandle);
    }

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/delete/{site}", method = RequestMethod.POST)
    public void deleteContent(@PathVariable final String site, final List<Item> items, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param destinationPath destinationPath
     * @param includeChildren includeChildren
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/copy/{site}", method = RequestMethod.POST)
    public void copy(@PathVariable final String site, final List<Item> items, final String destinationPath, final boolean includeChildren, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param destinationPath destinationPath
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/move/{site}", method = RequestMethod.POST)
    public void move(@PathVariable final String site, final List<Item> items, final String destinationPath, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/lock/{site}", method = RequestMethod.POST)
    public void lock(@PathVariable final String site, final List<Item> items, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param lockHandle lock handle
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/unlock/{site}", method = RequestMethod.POST)
    public void unlock(@PathVariable final String site, final List<Item> items, final LockHandle lockHandle, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param items items
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/get_lock_status/{site}", method = RequestMethod.GET)
    public void getLockStatus(@PathVariable final String site, final List<Item> items, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param itemId itemId
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/list/{site}/{itemId}", method = RequestMethod.GET)
    public void getChildren(@PathVariable final String site, @PathVariable final String itemId, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc
     * @param site site
     * @param itemId itemId
     * @param depth depth
     * @param filters filters
     * @param extractors extractors
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/tree/{site}/{itemId}", method = RequestMethod.GET)
    public void getTree(@PathVariable final String site, @PathVariable final String itemId, final int depth, final String filters, final String extractors, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc.
     * Get site list.
     */
    @RequestMapping(value = "/site_list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<Site> getSites() {
        return this.contentManager.getSiteList(new Context());
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new Validator() {
            @Override
            public boolean supports(final Class<?> clazz) {
                return String.class.equals(clazz);
            }

            @Override
            public void validate(final Object o, final Errors errors) {
                if (o instanceof String) {
                    if (StringUtils.isEmpty((String)o)) {
                        errors.reject((String)o, "Request body can not be empty");
                    }
                }
            }
        });
    }
}
