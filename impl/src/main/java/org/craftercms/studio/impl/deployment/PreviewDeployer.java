package org.craftercms.studio.impl.deployment;

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tenant;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.event.RepositoryEventBulkOpMessage;
import org.craftercms.studio.impl.event.RepositoryEventMessage;
import org.craftercms.studio.internal.content.ContentManager;
import org.craftrercms.commons.ebus.annotations.EListener;
import org.craftrercms.commons.ebus.annotations.EventHandler;
import org.craftrercms.commons.ebus.annotations.EventSelectorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Preview deployer.
 *
 * @author Dejan Brkic
 */
@EListener
public class PreviewDeployer {

    private Logger log = LoggerFactory.getLogger(PreviewDeployer.class);

    private String previewStoreRootPath;
    private boolean enabled = false;
    private ContentManager contentManager;

    private Context dummyContext = new Context(UUID.randomUUID().toString(), new Tenant());

    @EventHandler(event = "repository.create", ebus = "@repositoryReactor", type = EventSelectorType.REGEX)
    public void onContentCreate(Event<RepositoryEventMessage> event) throws StudioException {
        if (!enabled) {
            return;
        }

        RepositoryEventMessage message = event.getData();
        Item item = contentManager.read(dummyContext, message.getSite(), message.getItemId());
        String path = message.getPath();
        InputStream content = item.getInputStream();

        try {
            writeFile(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(event = "repository.update", ebus = "@repositoryReactor", type = EventSelectorType.REGEX)
    public void onContentUpdate(Event<RepositoryEventMessage> event) throws StudioException {

        if (!enabled) {
            return;
        }

        RepositoryEventMessage message = event.getData();
        Item item = contentManager.read(dummyContext, message.getSite(), message.getItemId());
        String path = message.getPath();
        InputStream content = item.getInputStream();

        try {
            writeFile(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeFile(final String path, final InputStream content) throws IOException {

        BufferedInputStream contentStream = new BufferedInputStream(content);
        StringBuilder sbSavePath = new StringBuilder(previewStoreRootPath);
        sbSavePath.append(File.separator);
        sbSavePath.append(path);
        String savePath = sbSavePath.toString();
        savePath = savePath.replaceAll(File.separator + "+", File.separator);

        File file = new File(savePath);
        OutputStream outputStream = null;

        try {
            contentStream.mark(0);
            contentStream.reset();
            // create new file if doesn't exist
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            IOUtils.copy(contentStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("Error: not able to open output stream for file " + path);
            }
            throw e;
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Error: not able to write file " + path);
            }
            throw e;
        } finally {

            IOUtils.closeQuietly(outputStream);
        }

    }

    @EventHandler(event = "repository.delete", ebus = "@repositoryReactor", type = EventSelectorType.REGEX)
    public void onContentDelete(Event<RepositoryEventBulkOpMessage> event) {
        if (!enabled) {
            return;
        }

        RepositoryEventBulkOpMessage message = event.getData();
        List<String> affectedPaths = message.getAffectedPaths();
        for (String path : affectedPaths) {
            StringBuilder sbDeletePath = new StringBuilder(previewStoreRootPath);
            sbDeletePath.append(File.separator);
            sbDeletePath.append(path);
            String deletePath = sbDeletePath.toString();
            deletePath = deletePath.replaceAll(File.separator + "+", File.separator);
            File file = new File(deletePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String getPreviewStoreRootPath() {
        return previewStoreRootPath;
    }

    public void setPreviewStoreRootPath(String previewStoreRootPath) {
        this.previewStoreRootPath = previewStoreRootPath;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
