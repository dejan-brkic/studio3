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

package org.craftercms.studio.impl;

import org.craftercms.studio.repo.RepositoryException;

/**
 * Repository Mock Exception.
 * Used in unit tests to mock repository exceptions thrown from mocked services.
 *
 * @author Dejan Brkic
 */
public class RepositoryMockException extends RepositoryException {
    private static final long serialVersionUID = -2878801837270010957L;

    public RepositoryMockException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RepositoryMockException(final String message) {
        super(message);
    }

    public RepositoryMockException(final String message, final Object... args) {
        super(message, args);
    }

    public RepositoryMockException(final Throwable cause) {
        super(cause);
    }
}
