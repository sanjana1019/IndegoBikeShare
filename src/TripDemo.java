import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

public class TripDemo {

	/**
	 * tripDemo constructor 
	 * @param tripRouteCategory
	 * @param tripRouteYear
	 * @param endDestination
	 * @param goLiveYear
	 * @param goLiveStatus
	 * @param givenPassholderType
	 * @param startHour
	 * @param endHour
	 * @param bikesInUseDateTime
	 */
    public TripDemo(String tripRouteCategory, int tripRouteYear, String endDestination, int goLiveYear, String goLiveStatus, String givenPassholderType, int startHour, int endHour,LocalDateTime bikesInUseDateTime) {
    	this.tripRouteCategory = tripRouteCategory;
    	this.tripRouteYear = tripRouteYear;
    	this.endDestination = endDestination;
    	this.goLiveYear = goLiveYear;
    	this.goLiveStatus = goLiveStatus; 
    	this.givenPassholderType = givenPassholderType; 
    	this.startHour = startHour;
    	this.endHour = endHour; 
    	this.bikesInUseDateTime = bikesInUseDateTime;
    }
    
 
    //these instance variables are used for statistics reporting  
    private int numberOfOneWayTrips = 0; 
    private int totalNumOfTrips = 0; 
    private double destinationId; 
    private double destinationIdCount = 0; 
    private double destPercent = 0.0; 
    private int countOfActiveStationsIn2016; 
    private int monthOfMostTrips; 
    private int countOfTripsBtwnStartAndEndHour = 0; 
    private double pctOfTripsBtwnStartAndEndHour = 0.0;
    private int bikeIdOfMostDuration;
    private int countBikesInUse = 0;
    private Trip tripOfLongestDistance; 
    private int countOfTripsThatStartedAt3am = 0; 
    private int countOfWalkupPassholderType = 0;
    private double pctOfWalkupPassholderType = 0.0; 
    private int IdOfLeastPopularStation;
    private String nameOfLeastPopularEndStation; 
    private int countOfTripsOfStationsThatWentLiveAlone = 0; 
    

    //these instance variables hold the arguments at run-time 
    private String tripRouteCategory;
    private int tripRouteYear; 
    private String endDestination; 
    private int goLiveYear;
    private String goLiveStatus; 
    private String givenPassholderType;
    private int startHour;
    private int endHour; 
    private LocalDateTime bikesInUseDateTime; 
    


    /**
     * main method requires the following arguments in the args[] array
     * tripRouteCategory e.g. "One Way"
     * tripRouteYear  e.g. 2017
     * endDestination e.g. "Philadelphia Zoo"
     * goLiveYear e.g. 2016
     * goLiveStatus e.g. "Active"
     * givenPassholderType e.g. Indego30
     * startHour e.g. 0 for midnight
     * endHour e.g. 5
     * bikesInUseDateTime e.g. "9/15/2017 07:00:00"
     * @param args
     */
    public static void main(String[] args) {
    	//validate expected input parameters 
    	if (args.length != 9){
    		throw new IllegalArgumentException(" Expecting 9 paramaters but only receieved " + args.length);
    	}
    	//assign input arguments to variables 
    	String tripRouteCategory = args[0];
    	int tripRouteYear = Integer.parseInt(args[1]);
    	String endDestination = args[2];
    	int goLiveYear = Integer.parseInt(args[3]);
    	String goLiveStatus = args[4];
    	String givenPassholderType = args[5];
    	int startHour = Integer.parseInt(args[6]);
    	int endHour = Integer.parseInt(args[7]);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm:ss");
    	LocalDateTime bikesInUseDateTime = LocalDateTime.parse(args[8], formatter);
    	
    	
    	//create TripDemo object 
        TripDemo demo = new TripDemo(tripRouteCategory, tripRouteYear, endDestination, goLiveYear, goLiveStatus, givenPassholderType, startHour, endHour, bikesInUseDateTime);
       
        Map<Integer, Station> stations = demo.loadStations();
        demo.processTrips(stations);
        
        //print out the answers to the Analysis Questions 
        System.out.println("----------------Analysis Questions ----------------");
        System.out.println("Q1: number of " + tripRouteCategory + " trips in the 3rd quarter of " + tripRouteYear + " is: " + demo.numberOfOneWayTrips);
        System.out.println("Q2: the number of stations with a go live date in " + goLiveYear + " that are still " + goLiveStatus + " is: " + demo.countOfActiveStationsIn2016);
       // System.out.println("total number of trips in this file is: " + demo.totalNumOfTrips);
        System.out.println("Q3: the % of trips ending at the " + endDestination + " is: " + demo.destPercent);
        System.out.println("Q4: The month (7=July, 8=Aug, 9=Sept) when the most " + givenPassholderType + " trips were taken was: " + demo.monthOfMostTrips);
        System.out.println("Q5: ID of the bike with the longest duration is: " + demo.bikeIdOfMostDuration);
       // System.out.println("Q6: count: " + demo.countOfTripsBtwnStartAndEndHour);
        System.out.println("Q6: the % of trips btwn " + startHour + " and " + endHour + " is: " + String.format("%.8f", demo.pctOfTripsBtwnStartAndEndHour));
        System.out.println("Q7: the number of bikes in use at " + demo.bikesInUseDateTime + " is: " + demo.countBikesInUse);
        System.out.println("Q8: trip info for the longest trip by distance: " + demo.tripOfLongestDistance.toString());
        System.out.println("Q9: number of trips that had a station which was the only"
        		+ "\n station to go live on its respective go-live date is: " + demo.countOfTripsOfStationsThatWentLiveAlone);
        System.out.println("Q10 (wild card): the number of trips that started at exactly 03:00am is: " + demo.countOfTripsThatStartedAt3am);
        System.out.println("--------------------Extra Credit-------------------");
        System.out.println("Q1: "); 
        demo.printCloseStations(stations.values());
        System.out.println("uncomment line to get all the pairs."); 
        System.out.println("Q2: the least popular end station is: " + demo.nameOfLeastPopularEndStation);
        System.out.printf("Q3 (wild card): The percent of trips that were of walk-up passholder type is: " + demo.pctOfWalkupPassholderType);
     
        //call method to write out station summaries 
        demo.saveStationSummaries(stations.values());
         
    }

    /**
     * process the stations file
     * @return
     */
    public Map<Integer, Station> loadStations() {
        Scanner scanner = null;

        Map<Integer, Station> stations = new HashMap<>();

        try {
        	String filename = "/Users/sanjana/Desktop/indego-stations-2017-10-20.csv";
        	File inputfile = new File(filename);
            scanner = new Scanner(inputfile);    

            // Stations file is in dos format with both CR and LF
            scanner.useDelimiter(",|\r");

            // Go live date is in the format M/d/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

            // Skip the header row
            scanner.nextLine();

            while(scanner.hasNextLine()) {
            	//get stationID and station name
                int stationId = scanner.nextInt();
                String stationName = scanner.next();
                if(stationName.charAt(0) == '"') {
                    //for cases where there is a comma inside a station name
                    stationName = stationName + scanner.next();
                }

                // Parse each field of the line from the file into the station attributes
                String strDate = scanner.next();
                //get goLiveDate and station status 
                LocalDate goLiveDate = LocalDate.parse(strDate, formatter);
                String stationStatus = scanner.next();
                
                //Q2: How many stations that had a go-live date in 2016 are still active 
                int stationGoLiveYear = goLiveDate.getYear();
                if (stationGoLiveYear == goLiveYear && stationStatus.equalsIgnoreCase(goLiveStatus)){
                	countOfActiveStationsIn2016++;
                }

                //create object called station
                Station station = new Station(stationId, stationName, goLiveDate, stationStatus);
                //hashmap, key = station ID & value = station object
                stations.put(stationId, station);
                
                //Q3:find the percentage of trips that end at Philadelphia Zoo (endDestination)
                if (stationName.equals(this.endDestination)){
                	destinationId = stationId;
                	//System.out.println("destId is:" + destinationId);
                }
                
                scanner.nextLine();
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        return stations;
    }
    
    /**
     * This method is used for Q9
     * to get stations that correspond to each go live date
     * @param stations
     * @return
     */
    public Map<LocalDate, Set<Station>> getStationsByGoLiveDate(Collection<Station> stations){
    	Map<LocalDate, Set<Station>> stationsByGoLiveDate = new HashMap<>();
    	for ( Station station: stations){
    		 LocalDate stationGoLiveDate = station.getGoLiveDate();
    		//if hashmap does contain go-live date (key)
    		 if (stationsByGoLiveDate.containsKey(stationGoLiveDate)){  
    			//then get the set of all stations for this go-live date 
    			 Set<Station> allStationsOnDate = stationsByGoLiveDate.get(stationGoLiveDate); 
    			//add new station object to this set 
    			 allStationsOnDate.add(station);   
    			//put the date(key) and set(value) into the map 
    			 stationsByGoLiveDate.put(stationGoLiveDate, allStationsOnDate); 
    		 }else {							
    			//else if it doesn't have the go-live date yet 
    			//set with all the stations on this go live date 
    			 Set<Station> allStationsOnDate = new HashSet<>(); 
    			//add station to the set of Stations
    			 allStationsOnDate.add(station);	
    			//put go-live date(key) and set of stations (value) into map
    			 stationsByGoLiveDate.put(stationGoLiveDate, allStationsOnDate); 
    		 }
    	}
    	return stationsByGoLiveDate;
    }

    /**
     * process the trips file
     * @param stations
     */
    public void processTrips(Map<Integer, Station> stations) {
        Scanner scanner = null;

        try {
        	String filename2 = "/Users/sanjana/Desktop/indego-trips-2017-q3.csv";
        	File inputfile2 = new File(filename2);
            scanner = new Scanner(inputfile2);

            // Trips file is in unix format, with only line feeds
            scanner.useDelimiter(",|\n");

            // Start and end times for trips are in the format yyyy-MM-dd HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Skip the header row
            scanner.nextLine();
            
            //declare the HashMap, key = month, value = # of trips
            Map<Integer, Integer> tripsByMonth = new HashMap<>(); 
            
            //declare HashMap, key = bike Id, value = duration
            Map<Integer,Integer> bikeIdMostDuration = new HashMap<>();
            
            //declare HashMap for EC Q2, key = ID for end station, value = count 
            Map<Integer,Integer> leastPopularEndStation = new HashMap<>();
            
            //max distance (Q8)
            double maxDistance = 0.0; 
            
            //Q9
            Map<LocalDate, Set<Station>> stationsByGoLivedate = getStationsByGoLiveDate(stations.values());

            while(scanner.hasNextLine()) {
            	
            	//get total number of trips in this file 
            	//can be used for percentage questions 
            	totalNumOfTrips++; 	

                // Parse fields from the trips file
                int tripId = 0;
                try {
                    tripId = scanner.nextInt();
                } catch(NoSuchElementException e) {
                    // At the end of the file
                    break;
                }

                int duration = scanner.nextInt();
                String strStartTime = scanner.next();
                LocalDateTime startTime = LocalDateTime.parse(strStartTime.substring(1, strStartTime.length()-1), formatter);
                                                  
                String strEndTime = scanner.next();
                LocalDateTime endTime = LocalDateTime.parse(strEndTime.substring(1, strEndTime.length()-1), formatter);
                int startStationId = scanner.nextInt();
                
                //Q7: on 9/15/17 at 7:00am, how many bikes were being used? 
                if((startTime.isBefore(bikesInUseDateTime) && endTime.isAfter(bikesInUseDateTime)) || 
                		(startTime.isBefore(bikesInUseDateTime) && endTime.equals(bikesInUseDateTime)) ||
                		(startTime.equals(bikesInUseDateTime) && endTime.isAfter(bikesInUseDateTime))){
                	countBikesInUse++;
                }
                
                //wild card Q10: How many trips started at exactly 3:00am? 
                int startTripTimeHour = startTime.getHour();
                int startTripTimeMinute = startTime.getMinute();
                if((startTripTimeHour == 03) && (startTripTimeMinute == 00)){
                	countOfTripsThatStartedAt3am++;
                }

                // start Lat/lon may not be provided
                String strStartLat = scanner.next();
                double startLat = 0.00;
                if (strStartLat != null && !"".equals(strStartLat) && !"\"\"".equals(strStartLat)) {
                    startLat = Double.parseDouble(strStartLat);
                }
                String strStartLon = scanner.next();
                double startLon = 0.00;
                if (strStartLon != null && !"".equals(strStartLon) && !"\"\"".equals(strStartLon)) {
                    startLon = Double.parseDouble(strStartLon);
                }

                int endStationId = scanner.nextInt();

                // end Lat/lon may not be provided
                String strEndLat = scanner.next();
                double endLat = 0.00;
                if (strEndLat != null && !"".equals(strEndLat) && !"\"\"".equals(strEndLat)) {
                    endLat = Double.parseDouble(strEndLat);
                }
                String strEndLon = scanner.next();
                double endLon = 0.00;
                if (strEndLon != null && !"".equals(strEndLon) && !"\"\"".equals(strEndLon)) {
                    endLon = Double.parseDouble(strEndLon);
                }

                String strBikeId = scanner.next();
                int bikeId = 0;
                try {
                    bikeId = Integer.parseInt(strBikeId);
                } catch(NumberFormatException e) {
                    bikeId = Integer.parseInt(strBikeId.substring(1, strBikeId.length()-1));
                }
                
              // Q5: what's the Id of the bike that's traveled the most in terms of duration? 
                //if the key already exists in the map
                if(bikeIdMostDuration.containsKey(bikeId)){
                	//get value (time) associated with the key (bike ID)
                	int totalDuration = bikeIdMostDuration.get(bikeId);
                	//add it to the total duration 
                	totalDuration = totalDuration + duration;
                	//put it back in the map 
                	bikeIdMostDuration.put(bikeId, totalDuration);
                } else {
                	//if key does not exist yet, then just add the new key + value pair
                	bikeIdMostDuration.put(bikeId, duration);
                }
                
                // EC Q2: Id of the least popular end station
              //if end station Id already exists in the map
                if(leastPopularEndStation.containsKey(endStationId)){  
                	//then get the count, increment it, and put it back in the map
                    Integer countOfEndStationId = leastPopularEndStation.get(endStationId);
                 	countOfEndStationId++;
                 	leastPopularEndStation.put(endStationId, countOfEndStationId); 
                } else {                         
                	//if Id is not already there and you're coming across it for the first time
                	//then insert it with a value of 1
                	leastPopularEndStation.put(endStationId, 1);
                }
                
                //get the rest of the info
                int planDuration = scanner.nextInt();
                String tripRouteCategory = scanner.next();
                String passholderType = scanner.next();
                
                //Q4: Track the number of Indego30 trips per month. Which month has the most? 
                int monthOfTrip = startTime.getMonthValue(); //key 
                if (tripsByMonth.containsKey(monthOfTrip) && passholderType.equalsIgnoreCase(givenPassholderType)){
                	Integer countOfTrips = tripsByMonth.get(monthOfTrip); 
                	countOfTrips++; 
                	tripsByMonth.put(monthOfTrip, countOfTrips);  //update the value with the new count 
                } else {
                	//if seeing key for first time, put it in with value of 1
                	tripsByMonth.put(monthOfTrip, 1);
                }
                
                
                //Extra credit Q3: what percent of trips were a walk-up passholder type?
                //how many trips were walk-ups?
                if(passholderType.contains("Walk-up")){
                	countOfWalkupPassholderType++;
                }

                //create trip object 
                Trip trip = new Trip(
                        tripId,
                        duration,
                        startTime,
                        endTime,
                        startStationId,
                        startLat,
                        startLon,
                        endStationId,
                        endLat,
                        endLon,
                        bikeId,
                        planDuration,
                        tripRouteCategory,
                        passholderType
                );
                
                //Q1:find the number of one-way trips in third quarter of 2017
                if (tripRouteCategory.contains(this.tripRouteCategory) && 
                		startTime.getYear() == this.tripRouteYear){
                	numberOfOneWayTrips++;
                }
                
               //Q3:find the percentage of trips that end at Philadelphia Zoo
                if (endStationId == destinationId){
                	destinationIdCount++; 
                }
                
                
                //Q6: what % of trips happened btwn 12am and 5am 
                int tripStartHour = startTime.getHour();		
                int tripEndHour = endTime.getHour();
                //from 12:00am-4:59am, assuming trip is on same date
                if ((startTime.getDayOfMonth() == endTime.getDayOfMonth()) && (tripStartHour >= startHour) && (tripEndHour < endHour)){    
                	countOfTripsBtwnStartAndEndHour++; 
                }
                //System.out.println("countOfTripsBtwnStartAndEndHour: " + countOfTripsBtwnStartAndEndHour);	
                
                
                //find the distance; if lat/lon not given, then distance = 0
                double distance;
                if (startLat == 0.0 || endLat == 0.0 || startLon == 0.0 || endLon == 0.0){
                	distance = 0.0;
                } else {
                	distance = Math.sqrt(Math.pow((endLon - startLon), 2.0) + 
                	Math.pow((endLat - startLat), 2.0));
                }
                
                
                //Q8: get trip info for longest trip by distance               
                if (distance > maxDistance){
                	maxDistance = distance; 
                	tripOfLongestDistance = trip; 
                }
                
                
                // Assume just the start station
                // should have their trip counter incremented
                // gets the station attributes (values) and puts it in the station object 
                Station startStation = stations.get(startStationId);
                //get info for station summary
                if (startStation != null) {
                    startStation.addTrip();
                    startStation.addTripDuration(duration);
                    startStation.addTripDistance(distance);
                    startStation.setMaxTripDuration(duration);
                    startStation.setMaxTripDistance(distance);
                    startStation.addCountOfTripsStartAtStation();
                    if (tripRouteCategory.contains(this.tripRouteCategory)){
                    	startStation.addOneWayTrips();
                    }
                    //System.out.println(startStation.toString());
                    
                    //EC Q1, Assume station lat/lon is consistent across trips for every station
                    startStation.setStationLat(startLat);
                    startStation.setStationLon(startLon);   
                    
                   //Q9
                   LocalDate goLiveDate = startStation.getGoLiveDate();
                   //stationsByGoLivedate contains go-live date (key), set of stations(value)
                   if (stationsByGoLivedate.containsKey(goLiveDate)){     
                	  //get the set of all stations on this go-live date
                	  Set<Station> stationsLiveOnDate = stationsByGoLivedate.get(goLiveDate);  
                	  //if size of set = 1
                	  if (stationsLiveOnDate != null && stationsLiveOnDate.size() == 1){  
                		  //then increase count 
                		  countOfTripsOfStationsThatWentLiveAlone++;         
                	  }
                   }
                    
                } else {
                    //System.out.printf("Start station id %d not found\n", startStationId);
                }

                //get information about the end station 
                Station endStation = stations.get(endStationId);
                if (endStation != null) {
                    endStation.addTrip();
                    endStation.addCountOfTripsEndAtStation();
                    endStation.setStationLat(endLat);
                    endStation.setStationLon(endLon);
                } 
                //else {
                    //System.out.printf("End station id %d not found\n", endStationId);
               // }        
            }
            
            //Q4: set month with the most trips 
            int maxTrips = 0;
            Set<Map.Entry<Integer, Integer>> entrySet = tripsByMonth.entrySet();
            for ( Map.Entry<Integer, Integer> entry : entrySet){
            	if (entry.getValue() > maxTrips){
            		monthOfMostTrips = entry.getKey();
                	maxTrips = entry.getValue();
            	}
            }
            
            //Q5 - set the Id of the bike that traveled the most duration 
            int maxDuration = 0;
            entrySet = bikeIdMostDuration.entrySet();
            for ( Map.Entry<Integer, Integer> entry : entrySet){
            	if (entry.getValue() > maxDuration){
            		bikeIdOfMostDuration = entry.getKey();
            		maxDuration = entry.getValue();
            	}
            }
            
            //EC Q2- set the least popular end station, gets id
            int minCount = totalNumOfTrips;
            entrySet = leastPopularEndStation.entrySet();
            for ( Map.Entry<Integer, Integer> entry : entrySet){
            	if (entry.getValue() < minCount){
            		IdOfLeastPopularStation = entry.getKey();
            		minCount = entry.getValue();        		
            	}
            }

            //EC Q2- get name of least popular end station 
            Set<Map.Entry<Integer, Station>> eSet = stations.entrySet();
            for (Map.Entry<Integer, Station> entry : eSet){
            	if (entry.getValue().getStationId() == IdOfLeastPopularStation){
            		nameOfLeastPopularEndStation = entry.getValue().getStationName();
            	}
            }
            
            //Q3- get the percent
            destPercent = (destinationIdCount/totalNumOfTrips) * 100.0;
            
            //Q6- get the percent
            pctOfTripsBtwnStartAndEndHour = (double) countOfTripsBtwnStartAndEndHour/totalNumOfTrips * 100.0;
            
            //EC Q3- get the percent
            pctOfWalkupPassholderType = (double) countOfWalkupPassholderType/totalNumOfTrips * 100.0; 
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return;
    }
    
    public void printCloseStations(Collection <Station> allStations){
    	int closeCount = 0;
    	//copy the collection to avoid concurrent modification exception when removing 
    	Collection <Station> otherStations = new HashSet<>(allStations); 
    	for (Station thisStation : allStations){
    		for (Station thatStation : otherStations){
    			if (thisStation.getStationId() != thatStation.getStationId()){
    				 if (thisStation.isCloseTo(thatStation)){
    					 closeCount++; 
    					 //uncomment next line to list all the pairs 
    					 //System.out.println(thisStation.getStationId() + " is close to " + thatStation.getStationId());
    				 }
    			}
    		}
    		//don't compare station again, to avoid duplicate pairs 
    		otherStations.remove(thisStation);     
    	}
    	System.out.println("the count of close stations is: " + closeCount);
    }

    /**
     * write out station summary to a file
     * @param stations
     */
    public void saveStationSummaries(Collection<Station> stations){
    	PrintWriter writer = null;
    	try{
    		writer = new PrintWriter("Stations Summaries.csv");
    		writer.println("station ID, station name, total num of trips, "
    				+ "avg trip duration, avg trip distance, max trip duration, "
    				+ "max trip distance, % of one-way trips, diff start-end stations");
    		//for each station...
    		for (Station station : stations){ 
    			writer.println(String.format("%d, %s, %d, %.2f, %.5f, %d, %.5f, %.5f, %d",
    					station.getStationId(), 
    					station.getStationName(),
    					station.getCountOfTrips(),
    					station.getAverageDuration(),
    					station.getAverageDistance(),
    					station.getMaxTripDuration(),
    					station.getMaxTripDistance(),
    					station.getPctOfOneWayTrips(),
    					station.getStartAndEndDifference()
    					));   
    		}
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	finally {
    		writer.close();
    	}
    }    
}