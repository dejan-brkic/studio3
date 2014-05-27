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

package org.craftercms.studio.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * Controller for All Non JSON responses.
 */
//TODO cortiz: To the index redirect Better
@Controller
public class HomeController {
    /**
     * Index ftl file name.
     */
    private static final String INDEX_VIEW = "index";

    /**
     * Default HomeController Constructor.
     */
    public HomeController() {
    }

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String index() {
        return INDEX_VIEW;
    }

    @RequestMapping(value = "/studio/*", method = RequestMethod.GET)
    public String indexStudio() {
        return INDEX_VIEW;
    }

    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    public String exception(HttpServletRequest request) {
        System.out.println("ASDDSADSADSADS" + request.getRequestURL());
        return INDEX_VIEW;
    }


}
