package tourGuide.tracker;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tourGuide.model.user.User;
import tourGuide.service.ITourGuideService;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Launch the tracking of all users location2.
 *
 * @author Laura Habdul
 */
public class Tracker extends Thread {

    /**
     * Tracker logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(Tracker.class);

    /**
     * Time interval between each users tracking.
     */
    private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);

    /**
     * Creates an Executor that uses a single worker thread.
     */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * ITourGuideService's implement class reference.
     */
    private final ITourGuideService tourGuideService;

    /**
     * Indicates if the tracker is stopped (true) or running (true).
     */
    private boolean stop = false;

    /**
     * Constructor of class Tracker.
     * Initialize tourGuideService.
     *
     * @param tourGuideService ITourGuideService's implement class reference
     */
    public Tracker(final TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    /**
     * Assures to start the Tracker thread.
     */
    public void startTracking() {
        stop = false;
        executorService.submit(this);
    }

    /**
     * Assures to shut down the Tracker thread.
     */
    public void stopTracking() {
        stop = true;
        executorService.shutdownNow();
    }

    /**
     * Overrides the run method once Tracker class is launched.
     * Retrieves all user by calling TourGuideService's getAllUsers method, starts a StopWatch, then tracks all
     * user location by calling TourGuideService's trackAllUserLocation method.
     * Resets the StopWatch then wait 5 minutes before updating the users' location.
     */
    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();

        while (true) {

            if (Thread.currentThread().isInterrupted() || stop) {
                LOGGER.debug("Tracker stopping");
                break;
            }

            List<User> users = tourGuideService.getAllUsers();
            LOGGER.info("Begin Tracker. Tracking " + users.size() + " users.");
            stopWatch.start();

            tourGuideService.trackAllUserLocation(users);

            stopWatch.stop();
            LOGGER.info("Finished tracking users, tracker Time Elapsed: "
                    + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
            stopWatch.reset();
            try {
                LOGGER.info("Tracker sleeping");
                TimeUnit.SECONDS.sleep(trackingPollingInterval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}