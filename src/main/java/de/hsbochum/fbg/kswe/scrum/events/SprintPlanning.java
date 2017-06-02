
package de.hsbochum.fbg.kswe.scrum.events;

import de.hsbochum.fbg.kswe.scrum.artifacts.NoBacklogItemsAvailableException;
import de.hsbochum.fbg.kswe.scrum.artifacts.BacklogItem;
import de.hsbochum.fbg.kswe.scrum.artifacts.ProductBacklog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author <a href="mailto:m.rieke@52north.org">Matthes Rieke</a>
 */
public class SprintPlanning implements Event {

    private static final Logger LOG = LogManager.getLogger(SprintPlanning.class);

    private final List<BacklogItem> items = new ArrayList<>();
    private final int itemCount;

    public SprintPlanning(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public void init(Event previous, ProductBacklog productBacklog) throws InitializationException {
        for (int j = 0; j < itemCount; j++) {
            try {
                addBacklogItem(productBacklog.getAndRemoveHeadItem());
            } catch (NoBacklogItemsAvailableException ex) {
                LOG.info("Done! Your Backlog is empty! ;-)");
            }
        }
    }

    public void addBacklogItem(BacklogItem item) {
        this.items.add(item);
    }

    public List<BacklogItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public Class<? extends Event> followingEventType() {
        return Sprint.class;
    }

}
