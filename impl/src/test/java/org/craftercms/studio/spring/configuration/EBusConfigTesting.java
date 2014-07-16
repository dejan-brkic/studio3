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

package org.craftercms.studio.spring.configuration;

import org.craftercms.studio.impl.deployment.PreviewDeployer;
import org.craftrercms.commons.ebus.annotations.EnableEBus;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

/**
 * EBus configuration for tests.
 *
 * @author Dejan Brkic
 */
@Configuration
@EnableEBus
public class EBusConfigTesting {

    public final static String TEST_PREVIEW_CONTENT_ROOT = "tests/preview-content";

    @Bean
    public Reactor repositoryReactorMock(Environment env) {
        Reactor reactor = Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();
        Reactor spy = Mockito.spy(reactor);
        return spy;
    }

    @Bean
    public Reactor repositoryReactor(Environment env) {
        Reactor reactor = Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();
        return reactor;
    }

    @Bean
    public PreviewDeployer previewDeployerSUT() {
        PreviewDeployer previewDeployer = new PreviewDeployer();
        previewDeployer.setEnabled(true);
        previewDeployer.setPreviewStoreRootPath(TEST_PREVIEW_CONTENT_ROOT);
        PreviewDeployer spy = Mockito.spy(previewDeployer);
        return spy;
    }
}
