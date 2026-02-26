import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Online Rideshare System.
 */
class User {
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean hasMembership;
    private int rewardPoints;

    public User() {
        this.userId = "";
        this.email = "";
        this.phoneNumber = "";
        this.hasMembership = false;
        this.rewardPoints = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean hasMembership() {
        return hasMembership;
    }

    public void setHasMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}

/**
 * Represents a stop in a trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
        this.stopStation = "";
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a trip in the Online Rideshare System.
 */
class Trip {
    private String tripId;
    private String driverId;
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private List<Stop> stops;
    private List<Booking> bookings;

    public Trip() {
        this.tripId = "";
        this.driverId = "";
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureDateTime = LocalDateTime.now();
        this.arrivalDateTime = LocalDateTime.now();
        this.price = 0.0;
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Validates if a customer can book seats on a trip.
     *
     * @param customerId The ID of the customer.
     * @param numberOfSeats The number of seats to book.
     * @return true if the customer can book seats, false otherwise.
     */
    public boolean validateBookingEligibility(String customerId, int numberOfSeats) {
        if (numberOfSeats > this.numberOfSeats) {
            return false;
        }

        for (Booking booking : this.bookings) {
            if (booking.getCustomerId().equals(customerId) &&
                booking.getBookingDateTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()) &&
                !(booking.getBookingDateTime().plusHours(2).isBefore(this.departureDateTime) ||
                  booking.getBookingDateTime().plusHours(2).isEqual(this.departureDateTime))) {
                return false;
            }
        }

        this.numberOfSeats -= numberOfSeats;
        return true;
    }

    /**
     * Computes the final price for a booking after applying reward discounts.
     *
     * @param customerId The ID of the customer.
     * @param bookingDateTime The date and time of the booking.
     * @return The final price after applying discounts, or the original price if no discount applies.
     */
    public double calculateDiscountedTripPrice(String customerId, LocalDateTime bookingDateTime) {
        User customer = getCustomerById(customerId);
        if (customer != null && customer.hasMembership() && bookingDateTime.plusHours(24).isBefore(this.departureDateTime)) {
            return Math.round(this.price * 0.8 * 10) / 10.0;
        }
        return this.price;
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops.
     *
     * @param otherTrip The other trip to compare with.
     * @return true if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlap(Trip otherTrip) {
        if (this.driverId.equals(otherTrip.driverId) && !this.stops.isEmpty() && !otherTrip.stops.isEmpty()) {
            for (Stop stop1 : this.stops) {
                for (Stop stop2 : otherTrip.stops) {
                    if (stop1.getStopStation().equals(stop2.getStopStation())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validates if a trip can be posted by a driver.
     *
     * @param driverId The ID of the driver.
     * @param newTrip The new trip to validate.
     * @return true if the trip can be posted, false otherwise.
     */
    public static boolean validateTripPostingFeasibility(String driverId, Trip newTrip) {
        if (newTrip.getDepartureDateTime().isAfter(newTrip.getArrivalDateTime())) {
            return false;
        }

        List<Trip> tripsByDriver = getTripsByDriver(driverId);
        for (Trip existingTrip : tripsByDriver) {
            if (existingTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime()) &&
                existingTrip.getArrivalDateTime().isAfter(newTrip.getDepartureDateTime())) {
                return false;
            }
        }
        return true;
    }

    private User getCustomerById(String customerId) {
        // Implementation to get customer by ID
        return null;
    }

    private static List<Trip> getTripsByDriver(String driverId) {
        // Implementation to get trips by driver ID
        return new ArrayList<>();
    }
}

/**
 * Represents a booking in the Online Rideshare System.
 */
class Booking {
    private String bookingId;
    private String tripId;
    private String customerId;
    private int numberOfSeats;
    private LocalDateTime bookingDateTime;

    public Booking() {
        this.bookingId = "";
        this.tripId = "";
        this.customerId = "";
        this.numberOfSeats = 0;
        this.bookingDateTime = LocalDateTime.now();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }
}

/**
 * Represents the Online Rideshare System.
 */
class ORS {
    private List<User> users;
    private List<Trip> trips;
    private List<Booking> bookings;

    public ORS() {
        this.users = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param customerId The ID of the customer.
     * @param currentMonth The current month to calculate points for.
     * @return The total reward points earned by the customer.
     */
    public int computeMonthlyRewardPoints(String customerId, int currentMonth) {
        User customer = getUserById(customerId);
        if (customer != null && customer.hasMembership()) {
            int totalPoints = 0;
            for (Booking booking : this.bookings) {
                if (booking.getCustomerId().equals(customerId) &&
                    booking.getBookingDateTime().getMonthValue() == currentMonth) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
            return totalPoints;
        }
        return 0;
    }

    private User getUserById(String userId) {
        // Implementation to get user by ID
        return null;
    }
}