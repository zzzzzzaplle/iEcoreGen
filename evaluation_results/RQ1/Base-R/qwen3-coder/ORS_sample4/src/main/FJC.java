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

    /**
     * Default constructor for User.
     */
    public User() {
    }

    // Getters and Setters
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

    /**
     * Default constructor for Driver.
     */
    public Driver() {
        this.trips = new ArrayList<>();
    }

    // Getters and Setters
    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Adds a trip to the driver's list of trips.
     *
     * @param trip The trip to add
     */
    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}

/**
 * Represents a customer, which is a type of user.
 */
class Customer extends User {
    private List<Booking> bookings;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    // Getters and Setters
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Adds a booking to the customer's list of bookings.
     *
     * @param booking The booking to add
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

/**
 * Represents a membership package that includes rewards.
 */
class Membership {
    private String type; // "cashback", "discount", or "points"
    private boolean active;

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    /**
     * Default constructor for Trip.
     */
    public Trip() {
        this.stops = new ArrayList<>();
    }

    // Getters and Setters
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

    /**
     * Adds a stop to the trip.
     *
     * @param stop The stop to add
     */
    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    /**
     * Checks if the trip is an indirect trip (has stops).
     *
     * @return true if the trip has at least one stop, false otherwise
     */
    public boolean isIndirect() {
        return !stops.isEmpty();
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    /**
     * Default constructor for Stop.
     */
    public Stop() {
    }

    // Getters and Setters
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

    /**
     * Default constructor for Booking.
     */
    public Booking() {
    }

    // Getters and Setters
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
 * Main system class that implements the functional requirements.
 */
 class OnlineRideshareSystem {

    private List<Trip> allTrips;
    private List<Driver> drivers;
    private List<Customer> customers;

    /**
     * Default constructor for OnlineRideshareSystem.
     */
    public OnlineRideshareSystem() {
        this.allTrips = new ArrayList<>();
        this.drivers = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    // Getters and Setters
    public List<Trip> getAllTrips() {
        return allTrips;
    }

    public void setAllTrips(List<Trip> allTrips) {
        this.allTrips = allTrips;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Validates if a customer can book seats on a trip based on several conditions.
     * Conditions: Trip must exist and have available seats, no time overlap with existing bookings,
     * and booking must be at least 2 hours before departure.
     *
     * @param customer     The customer attempting to book
     * @param trip         The trip to book seats on
     * @param seatsToBook  The number of seats to book
     * @param bookingTime  The time of booking
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int seatsToBook, LocalDateTime bookingTime) {
        // Check if trip exists and has available seats
        if (trip == null || trip.getNumberOfSeats() < seatsToBook) {
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
            
            // Check if both trips are on the same day
            if (existingTrip.getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                // Check for time overlap: [start1, end1] and [start2, end2] overlap if start1 < end2 and start2 < end1
                if (existingTrip.getDepartureTime().isBefore(trip.getArrivalTime()) && 
                    trip.getDepartureTime().isBefore(existingTrip.getArrivalTime())) {
                    return false;
                }
            }
        }

        // If all conditions are met, update the number of seats and return true
        trip.setNumberOfSeats(trip.getNumberOfSeats() - seatsToBook);
        return true;
    }

    /**
     * Calculates the discounted trip price if applicable.
     * Discount applies only if customer has membership and booking is made ≥24 hours before departure.
     *
     * @param customer    The customer making the booking
     * @param trip        The trip being booked
     * @param bookingTime The time of booking
     * @return The final price after applying discount if conditions are met, otherwise the original price
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, LocalDateTime bookingTime) {
        // Check if customer has membership
        if (customer.getMembership() == null || !customer.getMembership().isActive()) {
            return trip.getPrice();
        }

        // Check if booking is made at least 24 hours before departure
        LocalDateTime twentyFourHoursBeforeDeparture = trip.getDepartureTime().minusHours(24);
        if (!bookingTime.isBefore(twentyFourHoursBeforeDeparture)) {
            return trip.getPrice();
        }

        // Apply 20% discount
        double discountedPrice = trip.getPrice() * 0.8;
        return Math.round(discountedPrice * 10) / 10.0; // Keep 1 decimal place
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 The first trip to compare
     * @param trip2 The second trip to compare
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        // Check if both trips belong to the same driver
        if (trip1.getDriver() == null || trip2.getDriver() == null || 
            !trip1.getDriver().getUserID().equals(trip2.getDriver().getUserID())) {
            return false;
        }

        // Check if both trips are indirect (have stops)
        if (!trip1.isIndirect() || !trip2.isIndirect()) {
            return false;
        }

        // Compare stop stations
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
     * Computes total reward points earned by a customer in the given current month.
     * Only calculates bookings within the target month, 5 points per seat per booking.
     *
     * @param customer The customer to calculate points for
     * @param year     The year to calculate points for
     * @param month    The month to calculate points for (1-12)
     * @return Total reward points earned in the specified month
     */
    public int computeMonthlyRewardPoints(Customer customer, int year, int month) {
        // Check if customer has a membership with points reward
        if (customer.getMembership() == null || !customer.getMembership().isActive() || 
            !"points".equals(customer.getMembership().getType())) {
            return 0;
        }

        int totalPoints = 0;

        // Calculate points for eligible bookings
        for (Booking booking : customer.getBookings()) {
            LocalDateTime bookingDate = booking.getBookingTime();
            
            // Check if booking is within the target month
            if (bookingDate.getYear() == year && bookingDate.getMonthValue() == month) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip based on time conflicts.
     * Time conflict exists if the new trip's time period intersects with any existing trip's time period.
     *
     * @param driver The driver posting the trip
     * @param newTrip The new trip to be posted
     * @return true if no time conflict, false otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        // Check if driver exists
        if (driver == null) {
            return false;
        }

        // Check if trip time is valid (departure < arrival)
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            return false;
        }

        // Check for time conflicts with existing trips
        for (Trip existingTrip : driver.getTrips()) {
            // Check for time intersection: [start1, end1] and [start2, end2] intersect if start1 < end2 and start2 < end1
            // This excludes adjacent boundaries (e.g., A ends = B starts)
            if (newTrip.getDepartureTime().isBefore(existingTrip.getArrivalTime()) && 
                existingTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
                return false;
            }
        }

        return true;
    }
}