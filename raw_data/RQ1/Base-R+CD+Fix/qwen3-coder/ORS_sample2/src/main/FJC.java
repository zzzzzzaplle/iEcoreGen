import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Abstract class representing a user in the system.
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    public User() {
        // Default constructor
    }

    // Getters and setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

/**
 * Class representing a driver, who can post trips.
 */
class Driver extends User {
    private List<Trip> trips;

    public Driver() {
        this.trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Check if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 First trip to compare
     * @param trip2 Second trip to compare
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stopStations1 = trip1.getStopStations();
        Set<String> stopStations2 = trip2.getStopStations();

        for (String stop : stopStations1) {
            if (stopStations2.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a driver can post a new trip without time conflicts.
     * 
     * @param newTrip The new trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Check if departure time is before arrival time
        if (!newTrip.isValidTimePeriod()) {
            return false;
        }

        // Check for time conflicts with existing trips
        for (Trip existingTrip : trips) {
            if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }

        return true;
    }
}

/**
 * Class representing a customer, who can book trips and have membership packages.
 */
class Customer extends User {
    private MembershipPackage membershipPackage;

    public Customer() {
        // Default constructor
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    /**
     * Calculate total reward points earned by a customer in the given current month.
     * 
     * @param currentMonth The target month in format "yyyy-MM"
     * @return Total reward points earned in the month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        // Check if customer has membership with points reward
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }

        int totalPoints = 0;
        
        // This method would need access to all bookings by this customer
        // Since we don't have a direct reference to bookings, this is a placeholder
        // In a real implementation, you would need to access bookings through another mechanism
        
        return totalPoints;
    }

    /**
     * Book seats on a trip for this customer.
     * 
     * @param trip The trip to book seats on
     * @param numberOfSeats Number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookTrip(Trip trip, int numberOfSeats) {
        return trip.bookSeats(numberOfSeats);
    }
}

/**
 * Class representing a trip that can be posted by drivers and booked by customers.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate;
    private String departureTime; // Format: yyyy-MM-dd HH:mm
    private String arrivalTime;   // Format: yyyy-MM-dd HH:mm
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // Getters and setters
    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    /**
     * Calculate the discounted price for a booking if eligible.
     * 
     * @param customer The customer making the booking
     * @param bookingTime The time of booking in format "yyyy-MM-dd HH:mm"
     * @return The final price after applying discount if conditions are met, otherwise original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        // Check if customer has membership with discounts award
        if (customer.getMembershipPackage() == null || 
            !customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            return price;
        }

        // Check if booking is made at least 24 hours before departure
        if (!isBooking24HoursBefore(bookingTime)) {
            return price;
        }

        // Apply 20% discount
        return Math.round(price * 0.8 * 10) / 10.0;
    }

    /**
     * Book seats on this trip.
     * 
     * @param num Number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0 || num > numberOfSeats) {
            return false;
        }
        
        numberOfSeats -= num;
        return true;
    }

    /**
     * Confirm if a booking can be made for the specified number of seats.
     * 
     * @param seats Number of seats to book
     * @return true if booking is eligible, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return seats > 0 && seats <= numberOfSeats;
    }

    /**
     * Get all stop stations for this trip.
     * 
     * @return Set of stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Check if there's a time conflict between this trip and a given time period.
     * 
     * @param newDepartureTime New departure time in format "yyyy-MM-dd HH:mm"
     * @param newArrivalTime New arrival time in format "yyyy-MM-dd HH:mm"
     * @return true if there's a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        // Convert string times to Date objects for comparison
        try {
            Date thisDeparture = parseDateTime(departureTime);
            Date thisArrival = parseDateTime(arrivalTime);
            Date newDeparture = parseDateTime(newDepartureTime);
            Date newArrival = parseDateTime(newArrivalTime);

            // Check for time intersection (excluding adjacent boundaries)
            return !(newArrival.before(thisDeparture) || newDeparture.after(thisArrival));
        } catch (Exception e) {
            return false; // If parsing fails, assume no conflict
        }
    }

    /**
     * Check if the departure time is after arrival time.
     * 
     * @return true if departure is before arrival, false otherwise
     */
    public boolean isValidTimePeriod() {
        try {
            Date departure = parseDateTime(departureTime);
            Date arrival = parseDateTime(arrivalTime);
            return departure.before(arrival);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if booking is made at least 24 hours before departure.
     * 
     * @param bookingTime The booking time in format "yyyy-MM-dd HH:mm"
     * @return true if booking is made at least 24 hours before departure, false otherwise
     */
    private boolean isBooking24HoursBefore(String bookingTime) {
        try {
            Date bookingDate = parseDateTime(bookingTime);
            Date departureDate = parseDateTime(departureTime);
            
            // Calculate difference in hours
            long diffInMillies = departureDate.getTime() - bookingDate.getTime();
            long diffInHours = diffInMillies / (60 * 60 * 1000);
            
            return diffInHours >= 24;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parse date time string to Date object.
     * 
     * @param dateTimeString Date time string in format "yyyy-MM-dd HH:mm"
     * @return Date object
     * @throws Exception if parsing fails
     */
    private Date parseDateTime(String dateTimeString) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.parse(dateTimeString);
    }
}

/**
 * Class representing a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
        // Default constructor
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Class representing a booking made by a customer for a trip.
 */
class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    public Booking() {
        // Default constructor
    }

    // Getters and setters
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Check if this booking is eligible based on various criteria.
     * 
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        // Check if trip exists and has available seats
        if (trip == null || !trip.confirmBooking(numberOfSeats)) {
            return false;
        }

        // Check if booking is made at least 2 hours before departure
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        
        try {
            Date twoHoursBeforeDeparture = parseDateTime(trip.getDepartureTime());
            if (calendar.getTime().after(twoHoursBeforeDeparture)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        // Check for overlapping time periods with existing bookings
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking != this && existingBooking.overlapsWith(trip)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Update the number of seats in the associated trip.
     */
    public void updateTripSeats() {
        if (trip != null) {
            int currentSeats = trip.getNumberOfSeats();
            trip.setNumberOfSeats(currentSeats - numberOfSeats);
        }
    }

    /**
     * Check if this booking overlaps with another trip in time.
     * 
     * @param trip The trip to check overlap with
     * @return true if there's an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // This is a placeholder implementation
        // In a real system, you would compare the time periods of trips
        return false;
    }

    /**
     * Parse date time string to Date object.
     * 
     * @param dateTimeString Date time string in format "yyyy-MM-dd HH:mm"
     * @return Date object
     * @throws Exception if parsing fails
     */
    private Date parseDateTime(String dateTimeString) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.parse(dateTimeString);
    }
}

/**
 * Class representing a membership package that includes rewards.
 */
class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {
        this.awards = new Award[0];
    }

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Check if this membership package has a specific award.
     * 
     * @param award The award to check for
     * @return true if the award exists, false otherwise
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a == award) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Enum representing different types of awards in a membership package.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}