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
package org.craftercms.studio.ext.spring;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.craftercms.studio.exceptions.formatter.ExceptionFormatter;
import org.craftercms.studio.exceptions.formatter.FormatterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;


/**
 * Makes sure that All Exceptions are return in a JSON.
 * by using {@link org.craftercms.studio.exceptions.formatter.FormatterRegistry}
 *
 * @author cortiz
 */
public class CrafterCMSExceptionResolver extends AbstractHandlerExceptionResolver {

    protected FormatterRegistry formatterRegistry;
    private Logger log = LoggerFactory.getLogger(CrafterCMSExceptionResolver.class);

    public CrafterCMSExceptionResolver() {
    }

    @Override
    protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response,
                                              final Object handler, final Exception ex) {
        final ExceptionFormatter exceptionFormatter = formatterRegistry.getFormatter(ex.getClass());
        try {
            if (exceptionFormatter != null) {
                log.debug("ERROR:", ex);
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(exceptionFormatter.getHttpResponseCode());
                PrintWriter responseWriter = response.getWriter();
                if (responseWriter != null) {
                    responseWriter.write(exceptionFormatter.getFormattedMessage(ex));
                }
            } else {
                log.error("Unable to generate error message, sending raw exception", ex);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ex.printStackTrace(response.getWriter());
            }
        } catch (IOException e) {
            this.log.error("Unable to generate send error due a IOException ", e);
        }
        return new ModelAndView();
    }

    public void setFormatterRegistry(final FormatterRegistry formatterRegistry) {
        this.formatterRegistry = formatterRegistry;
    }
}
