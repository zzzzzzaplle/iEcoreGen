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
 * Represents a driver, which is a type of user.
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
 * Represents a customer, which is a type of user.
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
 * Represents a trip in the rideshare system.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<String> stops;
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

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    public void addStop(String stop) {
        this.stops.add(stop);
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
}

/**
 * Main system class for the Online Rideshare System.
 */
class OnlineRideshareSystem {

    /**
     * Validates if a customer can book seats on a trip based on several criteria.
     * Criteria include: trip existence, available seats, no time overlap with existing bookings,
     * and booking time being at least 2 hours before departure.
     *
     * @param customer       The customer attempting to book
     * @param trip           The trip to book seats on
     * @param numberOfSeats  The number of seats to book
     * @param bookingTime    The time of the booking attempt
     * @return               True if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int numberOfSeats, LocalDateTime bookingTime) {
        // Check if trip exists and has enough available seats
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // Check if booking time is at least 2 hours before departure
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!bookingTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for overlapping time periods with existing bookings on the same day
        for (Booking existingBooking : customer.getBookings()) {
            Trip existingTrip = existingBooking.getTrip();
            
            // Check if both trips are on the same day
            if (existingTrip.getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                // Check for time overlap
                if (!(existingTrip.getArrivalTime().isBefore(trip.getDepartureTime()) || 
                      existingTrip.getDepartureTime().isAfter(trip.getArrivalTime()))) {
                    return false;
                }
            }
        }

        // If all checks pass, update the number of seats and return true
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        
        // Create and add the booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingTime(bookingTime);
        customer.addBooking(booking);
        
        return true;
    }

    /**
     * Calculates the discounted trip price after applying reward discounts.
     * Discount is 20% off and only applies if customer has membership and booking is made
     * at least 24 hours before departure.
     *
     * @param customer    The customer making the booking
     * @param trip        The trip being booked
     * @param bookingTime The time of the booking
     * @return            The final price (with discount if applicable) rounded to 1 decimal place
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, LocalDateTime bookingTime) {
        // Check if customer has membership with discount reward
        if (customer.getMembership() == null || 
            customer.getMembership().getRewardType() != RewardType.DISCOUNT) {
            return trip.getPrice();
        }

        // Check if booking is made at least 24 hours before departure
        LocalDateTime twentyFourHoursBeforeDeparture = trip.getDepartureTime().minusHours(24);
        if (!bookingTime.isBefore(twentyFourHoursBeforeDeparture)) {
            return trip.getPrice();
        }

        // Apply 20% discount
        double discountedPrice = trip.getPrice() * 0.8;
        
        // Round to 1 decimal place
        return Math.round(discountedPrice * 10.0) / 10.0;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 The first trip to compare
     * @param trip2 The second trip to compare
     * @return      True if the trips share any common stops, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        // Check if both trips belong to the same driver
        if (trip1.getDriver() == null || trip2.getDriver() == null || 
            !trip1.getDriver().getUserID().equals(trip2.getDriver().getUserID())) {
            return false;
        }

        // Check if both trips have at least one stop
        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
            return false;
        }

        // Check for common stops
        for (String stop1 : trip1.getStops()) {
            for (String stop2 : trip2.getStops()) {
                if (stop1.equals(stop2)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Computes the total reward points earned by a customer in the given month.
     * Only applies to customers with membership offering points reward.
     * Points are calculated as 5 points per seat per booking within the target month.
     *
     * @param customer The customer for whom to calculate points
     * @param year     The year of the target month
     * @param month    The target month (1-12)
     * @return         Total reward points earned in the specified month
     */
    public int computeMonthlyRewardPoints(Customer customer, int year, int month) {
        // Check if customer has membership with points reward
        if (customer.getMembership() == null || 
            customer.getMembership().getRewardType() != RewardType.POINTS) {
            return 0;
        }

        int totalPoints = 0;

        // Calculate points for eligible bookings
        for (Booking booking : customer.getBookings()) {
            LocalDateTime bookingTime = booking.getBookingTime();
            
            // Check if booking was made in the target month
            if (bookingTime.getYear() == year && bookingTime.getMonthValue() == month) {
                // Add points: 5 points per seat
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip based on time conflict checks.
     * Conflicts occur when time periods intersect. Identical periods conflict,
     * but adjacent boundaries do not.
     *
     * @param driver The driver attempting to post the trip
     * @param newTrip The new trip to be posted
     * @return        True if no time conflicts exist, false otherwise
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
            // Check if time periods intersect
            // Conflict exists if: NOT (new trip ends before existing starts OR new trip starts after existing ends)
            if (!(newTrip.getArrivalTime().isBefore(existingTrip.getDepartureTime()) || 
                  newTrip.getDepartureTime().isAfter(existingTrip.getArrivalTime()))) {
                return false;
            }
        }

        return true;
    }
}