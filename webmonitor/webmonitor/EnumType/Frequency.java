package webmonitor.EnumType;

/**
 * Defines the frequency constants for website monitoring.
 * Used to specify how often a website should be checked for updates.
 *
 * This enum is used by the Subscription class to define monitoring schedules.
 */
// This enum represents the frequency of checks for a website.
public enum Frequency {
    MINUTELY,
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}
