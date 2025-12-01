import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * User class representing a user in the Online Rideshare System.
 */
class User {
    private String id;
    private String email;
    private String phoneNumber;
    private boolean isMember;

    /**
     * Unparameterized constructor for User.
     */
    public User() {}

    /**
     * Constructor for User.
     * @param id Unique ID of the user.
     * @param email Email of the user.
     * @param phoneNumber Phone number of the user.
     */
    public User(String id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isMember = false;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }
}

/**
 * Stop class representing a stop in a trip.
 */
class Stop {
    private String stopStation;

    /**
     * Unparameterized constructor for Stop.
     */
    public Stop() {}

    /**
     * Constructor for Stop.
     * @param stopStation Station where the trip stops.
     */
    public Stop(String stopStation) {
        this.stopStation = stopStation;
    }

    // Getters and setters
    public String getStopStation() { return stopStation; }
    public void setStopStation(String stopStation) { this.stopStation = stopStation; }
}

/**
 * Trip class representing a trip in the Online Rideshare System.
 */
class Trip {
    private String id;
    private String driverId;
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;

    /**
     * Unparameterized constructor for Trip.
     */
    public Trip() {
        this.stops = new ArrayList<>();
    }

    /**
     * Constructor for Trip.
     * @param id Unique ID of the trip.
     * @param driverId ID of the driver.
     * @param departureStation Departure station of the trip.
     * @param arrivalStation Arrival station of the trip.
     * @param numberOfSeats Number of seats available in the trip.
     * @param departureTime Departure time of the trip.
     * @param arrivalTime Arrival time of the trip.
     * @param price Price of the trip.
     */
    public Trip(String id, String driverId, String departureStation, String arrivalStation, int numberOfSeats, LocalDateTime departureTime, LocalDateTime arrivalTime, double price) {
        this.id = id;
        this.driverId = driverId;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.numberOfSeats = numberOfSeats;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.stops = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public String getDepartureStation() { return departureStation; }
    public void setDepartureStation(String departureStation) { this.departureStation = departureStation; }
    public String getArrivalStation() { return arrivalStation; }
    public void setArrivalStation(String arrivalStation) { this.arrivalStation = arrivalStation; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public List<Stop> getStops() { return stops; }
    public void setStops(List<Stop> stops) { this.stops = stops; }
}

/**
 * Booking class representing a booking made by a customer.
 */
class Booking {
    private String id;
    private String customerId;
    private String tripId;
    private int numberOfSeats;
    private LocalDateTime bookingTime;

    /**
     * Unparameterized constructor for Booking.
     */
    public Booking() {}

    /**
     * Constructor for Booking.
     * @param id Unique ID of the booking.
     * @param customerId ID of the customer who made the booking.
     * @param tripId ID of the trip being booked.
     * @param numberOfSeats Number of seats being booked.
     * @param bookingTime Time when the booking was made.
     */
    public Booking(String id, String customerId, String tripId, int numberOfSeats, LocalDateTime bookingTime) {
        this.id = id;
        this.customerId = customerId;
        this.tripId = tripId;
        this.numberOfSeats = numberOfSeats;
        this.bookingTime = bookingTime;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
}

/**
 * OnlineRideshareSystem class providing methods for managing trips and bookings.
 */
 class OnlineRideshareSystem {
    private List<Trip> trips;
    private List<Booking> bookings;
    private List<User> users;

    /**
     * Unparameterized constructor for OnlineRideshareSystem.
     */
    public OnlineRideshareSystem() {
        this.trips = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Validate if a customer can book seats on a trip.
     * @param customerId ID of the customer.
     * @param tripId ID of the trip.
     * @param numberOfSeats Number of seats to be booked.
     * @return True if the customer can book seats, false otherwise.
     */
    public boolean validateBookingEligibility(String customerId, String tripId, int numberOfSeats) {
        Trip trip = getTripById(tripId);
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        if (LocalDateTime.now().isAfter(trip.getDepartureTime().minusHours(2))) {
            return false;
        }
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(customerId) && booking.getTripId().equals(tripId)) {
                if (isTimeOverlapping(booking.getBookingTime(), trip.getDepartureTime())) {
                    return false;
                }
            }
        }
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        return true;
    }

    /**
     * Check if two times are overlapping on the same day.
     * @param time1 First time.
     * @param time2 Second time.
     * @return True if the times are overlapping, false otherwise.
     */
    private boolean isTimeOverlapping(LocalDateTime time1, LocalDateTime time2) {
        return time1.toLocalDate().equals(time2.toLocalDate());
    }

    /**
     * Calculate the final price for a booking after applying reward discounts.
     * @param customerId ID of the customer.
     * @param tripId ID of the trip.
     * @param numberOfSeats Number of seats booked.
     * @return Final price after discount, or original price if conditions are not met.
     */
    public double calculateDiscountedTripPrice(String customerId, String tripId, int numberOfSeats) {
        User customer = getUserById(customerId);
        Trip trip = getTripById(tripId);
        if (customer.isMember() && LocalDateTime.now().isBefore(trip.getDepartureTime().minusDays(1))) {
            return trip.getPrice() * numberOfSeats * 0.8;
        }
        return trip.getPrice() * numberOfSeats;
    }

    /**
     * Check if two indirect trips by the same driver share any common stops.
     * @param trip1 First trip.
     * @param trip2 Second trip.
     * @return True if the trips share a common stop, false otherwise.
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (!trip1.getDriverId().equals(trip2.getDriverId())) {
            return false;
        }
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
     * Compute the total reward points earned by a customer in the given month.
     * @param customerId ID of the customer.
     * @param year Year for which to compute reward points.
     * @param month Month for which to compute reward points.
     * @return Total reward points earned by the customer in the given month.
     */
    public int computeMonthlyRewardPoints(String customerId, int year, int month) {
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(customerId) && booking.getBookingTime().getYear() == year && booking.getBookingTime().getMonthValue() == month) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Validate if a trip can be posted by a driver.
     * @param driverId ID of the driver.
     * @param newTrip Trip to be posted.
     * @return True if the trip can be posted, false otherwise.
     */
    public boolean validateTripPostingFeasibility(String driverId, Trip newTrip) {
        for (Trip trip : trips) {
            if (trip.getDriverId().equals(driverId) && isTripTimeConflicting(trip, newTrip)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the time period of two trips is conflicting.
     * @param trip1 First trip.
     * @param trip2 Second trip.
     * @return True if the trips are conflicting, false otherwise.
     */
    private boolean isTripTimeConflicting(Trip trip1, Trip trip2) {
        return !(trip1.getArrivalTime().isBefore(trip2.getDepartureTime()) || trip1.getDepartureTime().isAfter(trip2.getArrivalTime()));
    }

    // Helper methods to get trip, user by ID
    private Trip getTripById(String tripId) {
        for (Trip trip : trips) {
            if (trip.getId().equals(tripId)) {
                return trip;
            }
        }
        return null;
    }

    private User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // Getters and setters
    public List<Trip> getTrips() { return trips; }
    public void setTrips(List<Trip> trips) { this.trips = trips; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        OnlineRideshareSystem ors = new OnlineRideshareSystem();

        // Example usage
        User customer = new User("C1", "customer@example.com", "1234567890");
        ors.getUsers().add(customer);

        Trip trip = new Trip("T1", "D1", "Station A", "Station B", 10, LocalDateTime.parse("2024-09-20 10:00", formatter), LocalDateTime.parse("2024-09-20 12:00", formatter), 100.0);
        ors.getTrips().add(trip);

        System.out.println(ors.validateBookingEligibility("C1", "T1", 2)); // Should print: true
        System.out.println(ors.calculateDiscountedTripPrice("C1", "T1", 2)); // Should print the discounted price

        // More example usage can be added here
    }
}