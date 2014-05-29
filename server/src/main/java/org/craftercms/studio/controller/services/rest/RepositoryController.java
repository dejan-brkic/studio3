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


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.craftercms.studio.api.content.ContentManager;

/**
 * Repository Controller.
 */
@Controller
@RequestMapping("/api/1/content")
public class RepositoryController {
    //    /**
    //     * Content Manager instance.
    //     */
    //    @Autowired
    //    private ContentManager contentManager;
    //    /**
    //     * TODO: write java doc.
    //     * Get content for given item ID and version.
    //     *
    //     * @param itemId  itemId.
    //     * @param version   version.
    //     * @param request http request.
    //     * @param response http response.
    //     */
    //    @RequestMapping(value = "/read/{site}", method = RequestMethod.GET)
    //    public void getContent(@PathVariable final String site, @RequestParam(required = true) final String itemId,
    //                           @RequestParam(required = false) final String version,
    //                           final HttpServletRequest request,
    //                           final HttpServletResponse response)
    //            throws StudioException {
    //
    //        final InputStream content = this.contentManager.read(new Context(), itemId);
    //        try {
    //            final OutputStream out = response.getOutputStream();
    //            IOUtils.copy(content, out);
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //    }
    //
    //    /**
    //     * TODO: write java doc.
    //     * Update content.
    //     * @param site site.
    //     * @param itemId    itemId.
    //     * @param request http request.
    //     * @param response http response.
    //     */
    //    @RequestMapping(value = "/update/{site}", method = RequestMethod.POST)
    //    public void update(@PathVariable final String site, @RequestParam(required = true) final String itemId,
    //                       @Valid @RequestBody(required = true) final String content,
    // final HttpServletRequest request,
    //                       final HttpServletResponse response) throws IOException {
    //        InputStream contentStream = IOUtils.toInputStream(content);
    //        this.contentManager.update(new Context(), itemId, contentStream);
    //    }
    //
    //    /**
    //     * TODO: javadoc.
    //     * @param site site.
    //     * @param itemId itemId.
    //     * @param request http request
    //     * @param response http response
    //     */
    //    @RequestMapping(value = "/open/{site}", produces = MediaType.APPLICATION_JSON_VALUE,
    // method = RequestMethod.GET)
    //    @ResponseBody
    //    public LockHandle openForEdit(@PathVariable final String site, @RequestParam(required = true) final String
    // itemId,
    //                            final HttpServletRequest request, final HttpServletResponse response) {
    //        LockHandle lockHandle = this.contentManager.open(new Context(), itemId);
    //        return lockHandle;
    //    }
    //
    //    /**
    //     * TODO: javadoc.
    //     * @param site site.
    //     * @param itemId itemId.
    //     * @param lockHandleId lockHandle.
    //     * @param content content.
    //     * @param request request
    //     * @param response response.
    //     */
    //    @RequestMapping(value = "/save/{site}", method = RequestMethod.POST)
    //    public void saveContent(@PathVariable final String site, @RequestParam(required = true) final String itemId,
    //                            @RequestParam(required = true) final String lockHandleId,
    //                            @Valid @RequestBody final String content, final HttpServletRequest request,
    //                            final HttpServletResponse response) {
    //        InputStream contentStream = IOUtils.toInputStream(content);
    //        LockHandle lockHandle = new LockHandle();
    //        lockHandle.setId(lockHandleId);
    //        this.contentManager.save(new Context(), itemId, lockHandle, contentStream);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site.
    //     * @param itemId site.
    //     * @param lockHandleId site.
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/close/{site}", method = RequestMethod.POST)
    //    public void close(@PathVariable final String site, @RequestParam(required = true) final String itemId,
    //                      @RequestParam(required = true) final String lockHandleId,
    //                      final HttpServletRequest request, final HttpServletResponse response) {
    //        LockHandle lockHandle = new LockHandle();
    //        lockHandle.setId(lockHandleId);
    //        this.contentManager.close(new Context(), itemId, lockHandle);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/delete/{site}", method = RequestMethod.POST)
    //    public void deleteContent(@PathVariable final String site, @Valid @RequestBody final String itemsJson,
    //                              final HttpServletRequest request, final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        this.contentManager.delete(new Context(), items);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param destinationPath destinationPath
    //     * @param includeChildren includeChildren
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/copy/{site}", method = RequestMethod.POST)
    //    public void copy(@PathVariable final String site, @Valid @RequestBody final String itemsJson,
    //                     @RequestParam(required = true) final String destinationPath,
    //                     @RequestParam(required = false, defaultValue = "true") final boolean includeChildren,
    //                     final HttpServletRequest request, final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        this.contentManager.copy(new Context(), items, destinationPath, includeChildren);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param destinationPath destinationPath
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/move/{site}", method = RequestMethod.POST)
    //    public void move(@PathVariable final String site, @Valid @RequestBody final String itemsJson,
    //                     @RequestParam(required = true) final String destinationPath,
    //                     final HttpServletRequest request, final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        this.contentManager.move(new Context(), items, destinationPath);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/lock/{site}", method = RequestMethod.POST)
    //    @ResponseBody
    //    public LockHandle lock(@PathVariable final String site, @Valid @RequestBody final String itemsJson,
    //                     final HttpServletRequest request, final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        LockHandle lockHandle = this.contentManager.lock(new Context(), items);
    //        return lockHandle;
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param lockHandle lock handle
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/unlock/{site}", method = RequestMethod.POST)
    //    public void unlock(@PathVariable final String site, @Valid @RequestBody final String itemsJson,
    //                       @RequestParam(required = true) final String lockHandle, final HttpServletRequest request,
    //                           final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        LockHandle lh = new LockHandle();
    //        lh.setId(lockHandle);
    //        this.contentManager.unlock(new Context(), items, lh);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemsJson items
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/get_lock_status/{site}", method = RequestMethod.POST)
    //    @ResponseBody
    //    public List<LockStatus> getLockStatus(@PathVariable final String site, @Valid @RequestBody final String
    // itemsJson,
    //                              final HttpServletRequest request, final HttpServletResponse response) {
    //        ObjectMapper mapper = new ObjectMapper();
    //        List<Item> items = null;
    //        try {
    //            items = mapper.readValue(itemsJson.getBytes(), new TypeReference<List<Item>>() { });
    //        } catch (IOException e) {
    //            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    //        }
    //        return this.contentManager.getLockStatus(new Context(), items);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemId itemId
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/list/{site}", method = RequestMethod.GET)
    //    @ResponseBody
    //    public List<Item> getChildren(@PathVariable final String site, @RequestParam(required = true) final String
    // itemId,
    //                            final HttpServletRequest request, final HttpServletResponse response) {
    //        return this.contentManager.list(new Context(), itemId);
    //    }
    //
    //    /**
    //     * TODO: javadoc
    //     * @param site site
    //     * @param itemId itemId
    //     * @param depth depth
    //     * @param filters filters
    //     * @param extractors extractors
    //     * @param request request
    //     * @param response response
    //     */
    //    @RequestMapping(value = "/tree/{site}", method = RequestMethod.GET)
    //    @ResponseBody
    //    public Tree<Item> getTree(@PathVariable final String site, @RequestParam(required = true) final String itemId,
    //                        @RequestParam(required = false, defaultValue = "-1") final int depth,
    //                        @RequestParam(required = false) final List<String> filters,
    //                        @RequestParam(required = false) final List<String> extractors,
    //                        final HttpServletRequest request, final HttpServletResponse response) {
    //        List<ItemFilter> filterList = new ArrayList<ItemFilter>();
    //        List<ItemExtractor> extractorList = new ArrayList<ItemExtractor>();
    //        Tree<Item> tree = this.contentManager.tree(new Context(), itemId, depth, filterList, extractorList);
    //        return tree;
    //    }
    //
    //    /**
    //     * TODO: javadoc.
    //     * Get site list.
    //     */
    //    @RequestMapping(value = "/site_list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    //    @ResponseBody
    //    public List<Site> getSites() {
    //        return this.contentManager.getSiteList(new Context());
    //    }
    //
    //    @InitBinder
    //    protected void initBinder(WebDataBinder binder) {
    //        binder.setValidator(new Validator() {
    //            @Override
    //            public boolean supports(final Class<?> clazz) {
    //                return String.class.equals(clazz);
    //            }
    //
    //            @Override
    //            public void validate(final Object o, final Errors errors) {
    //                if (o instanceof String) {
    //                    if (StringUtils.isEmpty((String)o)) {
    //                        errors.reject((String)o, "Request body can not be empty");
    //                    }
    //                }
    //            }
    //        });
    //    }
}
