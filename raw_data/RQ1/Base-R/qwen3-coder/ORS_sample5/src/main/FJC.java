import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Online Rideshare System.
 */
class User {
    private String userID;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {
        this.userID = "";
        this.email = "";
        this.phoneNumber = "";
        this.membership = null;
    }

    // Getters and setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

/**
 * Represents a driver, which is a type of user.
 */
class Driver extends User {
    private List<Trip> trips;

    public Driver() {
        super();
        this.trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}

/**
 * Represents a customer, which is a type of user.
 */
class Customer extends User {
    private List<Booking> bookings;

    public Customer() {
        super();
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

/**
 * Represents a membership package with rewards.
 */
class Membership {
    private String packageType; // "cashback", "discount", or "points"
    private double rewardValue;

    public Membership() {
        this.packageType = "";
        this.rewardValue = 0.0;
    }

    // Getters and setters
    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(double rewardValue) {
        this.rewardValue = rewardValue;
    }
}

/**
 * Represents a trip in the system.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;
    private Driver driver;

    public Trip() {
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureTime = null;
        this.arrivalTime = null;
        this.price = 0.0;
        this.stops = new ArrayList<>();
        this.driver = null;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    public boolean isIndirectTrip() {
        return !stops.isEmpty();
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
        this.stopStation = "";
    }

    // Getters and setters
    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a booking made by a customer for a trip.
 */
class Booking {
    private Customer customer;
    private Trip trip;
    private int numberOfSeats;
    private LocalDateTime bookingTime;
    private double price;

    public Booking() {
        this.customer = null;
        this.trip = null;
        this.numberOfSeats = 0;
        this.bookingTime = null;
        this.price = 0.0;
    }

    // Getters and setters
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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Main system class for the Online Rideshare System.
 */
 class OnlineRideshareSystem {
    
    /**
     * Validates if a customer can book seats on a trip based on several conditions.
     * Conditions: Trip must exist and have available seats, no time overlap with existing bookings,
     * and booking must be at least 2 hours before departure.
     *
     * @param customer The customer attempting to book
     * @param trip The trip to book seats on
     * @param numberOfBookingSeats The number of seats the customer wants to book
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int numberOfBookingSeats) {
        // Check if trip exists and has enough available seats
        if (trip == null || trip.getNumberOfSeats() < numberOfBookingSeats) {
            return false;
        }

        // Check if current time is at least 2 hours before departure
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!currentTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for overlapping time periods with existing bookings on the same day
        for (Booking existingBooking : customer.getBookings()) {
            Trip existingTrip = existingBooking.getTrip();
            
            // Check if both trips are on the same day
            if (existingTrip.getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                // Check for time overlap
                if (!(existingTrip.getArrivalTime().isBefore(trip.getDepartureTime()) || 
                      trip.getArrivalTime().isBefore(existingTrip.getDepartureTime()))) {
                    return false;
                }
            }
        }

        // If all conditions are met, update the number of seats and return true
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfBookingSeats);
        return true;
    }

    /**
     * Calculates the final price for a booking after applying reward discounts.
     * Discount is 20% off and only applies if customer has membership and booking is made
     * at least 24 hours before departure.
     *
     * @param customer The customer making the booking
     * @param trip The trip being booked
     * @param originalPrice The original price of the booking
     * @return The final price after applying discounts (rounded to 1 decimal place), or original price if no discount applies
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, double originalPrice) {
        // Check if customer has membership and booking is made at least 24 hours before departure
        if (customer.getMembership() != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime twentyFourHoursBeforeDeparture = trip.getDepartureTime().minusHours(24);
            
            if (currentTime.isBefore(twentyFourHoursBeforeDeparture)) {
                // Apply 20% discount
                double discountedPrice = originalPrice * 0.8;
                // Round to 1 decimal place
                return Math.round(discountedPrice * 10.0) / 10.0;
            }
        }
        
        // Return original price if no discount applies
        return originalPrice;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 The first indirect trip
     * @param trip2 The second indirect trip
     * @return true if the trips share any common stops, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        // Check if both trips are indirect (have stops) and are by the same driver
        if (!trip1.isIndirectTrip() || !trip2.isIndirectTrip() || 
            !trip1.getDriver().getUserID().equals(trip2.getDriver().getUserID())) {
            return false;
        }

        // Check for common stops
        for (Stop stop1 : trip1.getStops()) {
            for (Stop stop2 : trip2.getStops()) {
                if (stop1.getStopStation().equals(stop2.getStopStation())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Computes the total reward points earned by a customer in the given current month.
     * Only calculates bookings within the target month, with 5 points per seat per booking.
     *
     * @param customer The customer whose points are being calculated
     * @param targetMonth The month to calculate points for (1-12)
     * @param targetYear The year to calculate points for
     * @return The total reward points earned in the specified month
     */
    public int computeMonthlyRewardPoints(Customer customer, int targetMonth, int targetYear) {
        // Check if customer has a membership with points reward
        if (customer.getMembership() == null || 
            !customer.getMembership().getPackageType().equals("points")) {
            return 0;
        }

        int totalPoints = 0;
        
        // Calculate points for eligible bookings
        for (Booking booking : customer.getBookings()) {
            LocalDateTime bookingDate = booking.getBookingTime();
            
            // Check if booking is within the target month and year
            if (bookingDate.getMonthValue() == targetMonth && 
                bookingDate.getYear() == targetYear) {
                // Add 5 points per seat
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip based on time feasibility and conflicts.
     * Checks that the driver exists, trip times are valid, and there are no time conflicts
     * with existing trips.
     *
     * @param driver The driver attempting to post the trip
     * @param newTrip The new trip to be posted
     * @return true if the trip can be posted, false otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        // Check if driver exists
        if (driver == null) {
            return false;
        }

        // Check if trip times are valid (departure < arrival)
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            return false;
        }

        // Check for time conflicts with existing trips
        for (Trip existingTrip : driver.getTrips()) {
            // Check if time periods intersect (completely identical periods are conflicts)
            // Adjacent boundaries are not considered conflicts
            boolean isConflict = !(newTrip.getArrivalTime().isBefore(existingTrip.getDepartureTime()) || 
                                 newTrip.getDepartureTime().isAfter(existingTrip.getArrivalTime()));
            
            if (isConflict) {
                return false;
            }
        }

        return true;
    }
}