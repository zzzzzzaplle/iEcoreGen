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
    }

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
 * Represents a driver in the Online Rideshare System.
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

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}

/**
 * Represents a customer in the Online Rideshare System.
 */
class Customer extends User {
    private List<Booking> bookings;

    public Customer() {
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
    private RewardType rewardType;

    public Membership() {
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }
}

/**
 * Enum representing types of rewards in a membership.
 */
enum RewardType {
    CASHBACK, DISCOUNT, POINTS
}

/**
 * Represents a trip in the Online Rideshare System.
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
        this.stops = new ArrayList<>();
    }

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

    /**
     * Checks if this trip is an indirect trip (has one or more stops).
     *
     * @return true if the trip has at least one stop, false otherwise
     */
    public boolean isIndirectTrip() {
        return !this.stops.isEmpty();
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
    }

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
    private double finalPrice;

    public Booking() {
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

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}

/**
 * Main system class for the Online Rideshare System.
 */
 class OnlineRideshareSystem {

    /**
     * Validates if a customer can book seats on a trip based on several conditions.
     * Conditions: Trip must exist and have available seats, no seat overlap,
     * no time overlap with existing bookings on the same day, and booking must be
     * at least 2 hours before departure time.
     *
     * @param customer          the customer attempting to book
     * @param trip              the trip to book seats on
     * @param numberOfBookingSeats the number of seats to book
     * @param bookingTime       the time of booking
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int numberOfBookingSeats, LocalDateTime bookingTime) {
        // Check if trip exists and has enough available seats
        if (trip == null || trip.getNumberOfSeats() < numberOfBookingSeats) {
            return false;
        }

        // Check if booking is at least 2 hours before departure (excluding exactly 2 hours)
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!bookingTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for time overlap with existing bookings on the same day
        for (Booking existingBooking : customer.getBookings()) {
            Trip existingTrip = existingBooking.getTrip();
            
            // Only check bookings on the same day as the new booking
            if (existingTrip.getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                // Check if time periods overlap
                if (!(existingTrip.getArrivalTime().isBefore(trip.getDepartureTime()) || 
                      trip.getArrivalTime().isBefore(existingTrip.getDepartureTime()))) {
                    return false;
                }
            }
        }

        // If all checks pass, update the trip's available seats and return true
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfBookingSeats);
        return true;
    }

    /**
     * Calculates the discounted trip price if the customer has membership and
     * the booking is made at least 24 hours before departure.
     *
     * @param customer    the customer making the booking
     * @param trip        the trip being booked
     * @param bookingTime the time of booking
     * @return the final price after applying discount if applicable, otherwise the original price
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, LocalDateTime bookingTime) {
        // Check if customer has membership
        if (customer.getMembership() == null) {
            return trip.getPrice();
        }

        // Check if booking is made at least 24 hours before departure
        LocalDateTime twentyFourHoursBeforeDeparture = trip.getDepartureTime().minusHours(24);
        if (!bookingTime.isBefore(twentyFourHoursBeforeDeparture)) {
            return trip.getPrice();
        }

        // Apply 20% discount
        double discountedPrice = trip.getPrice() * 0.8;
        
        // Format to keep 1 decimal place
        return Math.round(discountedPrice * 10.0) / 10.0;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 the first trip to compare
     * @param trip2 the second trip to compare
     * @return true if the trips share any common stops, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        // Check if both trips belong to the same driver
        if (trip1.getDriver() == null || trip2.getDriver() == null || 
            !trip1.getDriver().getUserID().equals(trip2.getDriver().getUserID())) {
            return false;
        }

        // Check if both trips are indirect (have stops)
        if (!trip1.isIndirectTrip() || !trip2.isIndirectTrip()) {
            return false;
        }

        // Compare stops between the two trips
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
     *
     * @param customer the customer whose points are to be calculated
     * @param year     the year to check bookings
     * @param month    the month to check bookings (1-12)
     * @return the total reward points earned in the specified month
     */
    public int computeMonthlyRewardPoints(Customer customer, int year, int month) {
        // Check if customer has a membership with points reward
        if (customer.getMembership() == null || 
            customer.getMembership().getRewardType() != RewardType.POINTS) {
            return 0;
        }

        int totalPoints = 0;
        
        // Calculate points for eligible bookings
        for (Booking booking : customer.getBookings()) {
            LocalDateTime bookingDate = booking.getBookingTime();
            
            // Check if booking date is within the target month
            if (bookingDate.getYear() == year && bookingDate.getMonthValue() == month) {
                // 5 points per seat per booking
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip based on time conflict checks.
     *
     * @param driver   the driver posting the trip
     * @param newTrip  the new trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        // Check if driver exists
        if (driver == null) {
            return false;
        }

        // Check if new trip time is valid (departure < arrival)
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            return false;
        }

        // Check for time conflicts with existing trips
        for (Trip existingTrip : driver.getTrips()) {
            // Check if time periods intersect
            // If one trip ends when another starts, it's not considered a conflict
            if (!(existingTrip.getArrivalTime().isBefore(newTrip.getDepartureTime()) || 
                  newTrip.getArrivalTime().isBefore(existingTrip.getDepartureTime()))) {
                // Check for completely identical time periods
                if (existingTrip.getDepartureTime().equals(newTrip.getDepartureTime()) && 
                    existingTrip.getArrivalTime().equals(newTrip.getArrivalTime())) {
                    return false;
                }
                
                // Check for intersecting time periods (not just adjacent)
                if (existingTrip.getDepartureTime().isBefore(newTrip.getArrivalTime()) && 
                    newTrip.getDepartureTime().isBefore(existingTrip.getArrivalTime())) {
                    return false;
                }
            }
        }

        return true;
    }
}