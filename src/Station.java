import java.time.LocalDate;

public class Station {

    // Non-static member variables. An instance is required
    private int stationId;
    private String stationName;
    private LocalDate goLiveDate;
    private String stationStatus;
    private int countOfTrips;
    private int totalDurations;
    private double totalDistance; 
    private int maxTripDuration;
    private double maxTripDistance;
    private int countOneWayTrips; 
    private int countOfTripsStartAtStation;
    private int countOfTripsEndAtStation;
    private double stationLat;
    private double stationLon;

    /**
     * Station constructor
     * @param stationId
     * @param stationName
     * @param goLiveDate
     * @param stationStatus
     */
    public Station(int stationId, String stationName, LocalDate goLiveDate, String stationStatus) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.goLiveDate = goLiveDate;
        this.stationStatus = stationStatus;
        this.countOfTrips = 0;
        this.totalDurations = 0; 
        this.totalDistance = 0.0; 
        this.maxTripDuration = 0;
        this.maxTripDistance = 0.0;
        this.countOneWayTrips = 0; 
        this.countOfTripsStartAtStation = 0;
        this.countOfTripsEndAtStation = 0;
    }
    
    /**
     * set station lat
     * @param lat
     */
    public void setStationLat(double lat){
    	if(lat != 0.0){					//if lat is not provided
    		stationLat = lat;
    	}
    }
    
    /**
     * set station longitude
     * @param lon
     */
    public void setStationLon(double lon){
    	if(lon != 0.0){					//if lon is not provided
    		stationLon = lon;
    	}
    }
    
    /**
     * get station longitude
     * @return station longitude 
     */
    public double getStationLon(){
    	return stationLon;
    }
    
    /**
     * get station latitude
     * @return station latitude 
     */
    public double getStationLat(){
    	return stationLat;
    }
    
    /**
     * for EC q1- stations close to each other
     * @param that
     * @return true if they're close to each other,else false 
     */
    public boolean isCloseTo(Station that){
    	if ((Math.abs(this.getStationLon() - that.getStationLon()) + Math.abs(this.getStationLat() - that.getStationLat()))/2.0 < 0.02){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
  
    /**
     * get station Id
     * @return station Id
     */
    public int getStationId(){
    	return stationId;
    }
    
    /**
     * get go live date
     * @return go live date
     */
    public LocalDate getGoLiveDate(){
    	return goLiveDate; 
    }
    
    
    /**
     * get station name 
     * @return station name 
     */
    public String getStationName(){
    	return stationName;
    }

 
    /**
     * add trip count
     */
    public void addTrip() {
        this.countOfTrips++;
    }
    
    /**
     * get count of trips
     * @return count of trips
     */
    public int getCountOfTrips(){
    	return countOfTrips;
    }
    
    
    /**
     * add trip durations 
     * @param duration
     */
    public void addTripDuration(int duration){
    	totalDurations+= duration;
    }
    
    
    /**
     * get average trip duration from this station
     * @return average duration 
     */
    public double getAverageDuration(){
    	if (countOfTrips == 0){ //to avoid divide by 0 errors
    		return 0;
    	}
    	double averageDuration = (double) totalDurations/countOfTrips;
    	return averageDuration;
    }
    

    /**
     * add trip distance 
     * @param distance
     */
    public void addTripDistance(double distance){
    	totalDistance+= distance;
    }

    /**
     * get average trip distance 
     * @return average trip distance
     */
    public double getAverageDistance(){
    	//to avoid divide by 0 errors 
    	if (countOfTrips == 0){ 
    		return 0;
    	}
    	double averageDistance = totalDistance/countOfTrips;
    	return averageDistance; 
    }
    
    
    /**
     * get max trip duration 
     * @return max duration 
     */
    public int getMaxTripDuration(){
    	return maxTripDuration;
    }
    

    /**
     * set max trip duration 
     * @param duration
     */
    public void setMaxTripDuration(int duration){
    	if(duration > maxTripDuration){
    		maxTripDuration = duration;
    	}
    }
    

    /**
     * get max trip distance from this station
     * @return max trip distance
     */
    public double getMaxTripDistance(){
    	return maxTripDistance;
    }
    
    /**
     * set max trip distance from this station
     * @param distance
     */
    public void setMaxTripDistance(double distance){
    	if (distance > maxTripDistance){
    		maxTripDistance = distance;
    	}
    }
    
  
    /**
     * add one way trips 
     */
    public void addOneWayTrips(){
    	countOneWayTrips++;
    }
    
    
    /**
     * get percentage of one way trips
     * @return percent of one way trips 
     */
    public double getPctOfOneWayTrips(){
    	if (countOfTrips == 0){
    		return 0;
    	}
    	return (double) countOneWayTrips/countOfTrips * 100.0; 
    }
    
  
    /**
     * add count of trips that start at this station
     */
    public void addCountOfTripsStartAtStation(){
    	countOfTripsStartAtStation++; 
    }
    
    /**
     *  add count of trips that end at this station
     */
    public void addCountOfTripsEndAtStation(){
    	countOfTripsEndAtStation++; 
    }
    
    /**
     * @return difference between the total number of trips that start 
     * at this station and that end at this station
     */
    public int getStartAndEndDifference(){
    	return Math.abs(countOfTripsStartAtStation - countOfTripsEndAtStation);  	
    }
      

}