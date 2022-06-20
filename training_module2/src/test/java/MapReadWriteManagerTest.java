import com.epam.multicurrency.training.task1.MapReadWriteManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

public class MapReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(MapReadWriteManagerTest.class);

    @Test
    public void testManagerInit() {
        MapReadWriteManager manager = new MapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS));

        assertTrue(manager.getStorage().isEmpty());
        try {
            manager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(manager.getStorage().isEmpty());

    }


    @Test
    public void testManagerWithoutWaitFails() {

        try {
            MapReadWriteManager manager = new MapReadWriteManager(10, 0, 0, Duration.of(5, ChronoUnit.SECONDS));
            manager.processReadAndWrite();
            assertTrue(manager.getError().getCause().equals(ConcurrentModificationException.class));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

}
