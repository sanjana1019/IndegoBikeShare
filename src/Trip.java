import java.time.LocalDateTime;

public class Trip {
    private int tripId;
    private int duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int startStation;
    private double startLat;
    private double startLon;
    private int endStation;
    private double endLat;
    private double endLon;
    private int bikeId;
    private int planDuration;
    private String tripRouteCategory;
    private String passholderType;

    /**
     * constructor
     * @param tripId
     * @param duration
     * @param startTime
     * @param endTime
     * @param startStation
     * @param startLat
     * @param startLon
     * @param endStation
     * @param endLat
     * @param endLon
     * @param bikeId
     * @param planDuration
     * @param tripRouteCategory
     * @param passholderType
     */
    public Trip(int tripId, int duration, LocalDateTime startTime, LocalDateTime endTime, int startStation, double startLat, double startLon, int endStation, double endLat, double endLon, int bikeId, int planDuration, String tripRouteCategory, String passholderType) {
        this.tripId = tripId;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startStation = startStation;
        this.startLat = startLat;
        this.startLon = startLon;
        this.endStation = endStation;
        this.endLat = endLat;
        this.endLon = endLon;
        this.bikeId = bikeId;
        this.planDuration = planDuration;
        this.tripRouteCategory = tripRouteCategory;
        this.passholderType = passholderType;
    }

	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", duration=" + duration + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", startStation=" + startStation + ", startLat=" + startLat + ", startLon=" + startLon
				+ ", endStation=" + endStation + ", endLat=" + endLat + ", endLon=" + endLon + ", bikeId=" + bikeId
				+ ", planDuration=" + planDuration + ", tripRouteCategory=" + tripRouteCategory + ", passholderType="
				+ passholderType + "]";
	}
    
}