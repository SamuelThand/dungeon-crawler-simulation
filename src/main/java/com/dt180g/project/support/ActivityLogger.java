package com.dt180g.project.support;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The singleton class responsible for logging all game activity,
 * provides a public interface for logging actions.
 *
 * @author Samuel Thand
 */
public final class ActivityLogger implements Constants {

    public static final ActivityLogger INSTANCE = new ActivityLogger();
    private Logger logger;
    private final String indentation = " ".repeat(4);

    /**
     * Constructor, sets the logger object.
     */
    private ActivityLogger() {
        setLogger();
    }

    /**
     * Set up the logger object with a console handler
     * and a simple formatter that only logs the log message.
     */
    private void setLogger() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(final LogRecord logRecord) {
                return logRecord.getMessage();
            }
        });

        logger.addHandler(consoleHandler);
    }

    /**
     * Delay execution based on values of USE_SLEEP_DELAY and SLEEP_DELAY.
     */
    private void delayExecution() {
        try {
            Thread.sleep(SLEEP_DELAY);
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Log a message to console.
     *
     * @param message The message to log to console.
     */
    private void performLog(final String message) {
        logger.log(Level.INFO, message + "\n");
        if (USE_SLEEP_DELAY) {
            delayExecution();
        }
    }

    /**
     * Log round info to console.
     *
     * @param message The round info to log to console.
     */
    public void logRoundInfo(final String message) {
        performLog(ANSI_PURPLE + message);
    }

    /**
     * Log turn info to console.
     *
     * @param message The turn info to log to console.
     */
    public void logTurnInfo(final String message) {
        if (message.contains(CHARACTER_TYPE_ENEMY)) {
            performLog(ANSI_MAGENTA + message);
        } else {
            performLog(ANSI_BLUE + message);
        }
    }

    /**
     * Log an attack to console.
     *
     * @param message The attack to log to console.
     */
    public void logAttack(final String message) {
        performLog(ANSI_GREEN + indentation + message);
    }

    /**
     * Log damage info to console.
     *
     * @param message The damage info to log to console.
     */
    public void logDamage(final String message) {
        performLog(ANSI_YELLOW + indentation.repeat(2) + message);
    }

    /**
     * Log a death to console.
     *
     * @param message The death to log to console.
     */
    public void logDeath(final String message) {
        performLog(ANSI_RED + indentation.repeat(2) + message);
    }

    /**
     * Log healing to console.
     *
     * @param message The healing to log to console.
     */
    public void logHealing(final String message) {
        performLog(ANSI_CYAN + indentation.repeat(2) + message);
    }
}
