package bg.solutions.hitachi.space.mission;

public interface SpaceMissionAPI {
    /**
     * Method that finds the day for the space shuttle to launch.
     *
     * @return int for the perfect day.
     */
    int findPerfectDayForSpaceShuttleLaunch();

    /**
     * This method generates report based on the given csv data and exports it to report package.
     * The report contains six columns and seven rows (six of them the same parameters in the
     * given csv file and one header row) as follows:
     * C1: Stats/Parameter.
     * C2: Average value.
     * C3: Max value.
     * C4: Min value.
     * C5: Median value.
     * C6: The most appropriate launch day value by parameter.
     *
     * @return String message for export status.
     */
    String generateWeatherReport();
}