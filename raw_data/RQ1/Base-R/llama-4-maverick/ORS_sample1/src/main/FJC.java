import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Online Rideshare System.
 */
class User {
    private String id;
    private String email;
    private String phoneNumber;
    private boolean isDriver;
    private boolean hasMembership;

    /**
     * Default constructor for User.
     */
    public User() {}

    /**
     * Gets the user's ID.
     * @return the user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     * @param id the user's ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's email.
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's phone number.
     * @return the user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * @param phoneNumber the user's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Checks if the user is a driver.
     * @return true if the user is a driver, false otherwise
     */
    public boolean isDriver() {
        return isDriver;
    }

    /**
     * Sets whether the user is a driver.
     * @param driver true if the user is a driver, false otherwise
     */
    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    /**
     * Checks if the user has a membership.
     * @return true if the user has a membership, false otherwise
     */
    public boolean hasMembership() {
        return hasMembership;
    }

    /**
     * Sets whether the user has a membership.
     * @param hasMembership true if the user has a membership, false otherwise
     */
    public void setHasMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }
}

/**
 * Represents a trip stop in the Online Rideshare System.
 */
class Stop {
    private String stopStation;

    /**
     * Default constructor for Stop.
     */
    public Stop() {}

    /**
     * Gets the stop station.
     * @return the stop station
     */
    public String getStopStation() {
        return stopStation;
    }

    /**
     * Sets the stop station.
     * @param stopStation the stop station
     */
    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a trip in the Online Rideshare System.
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
     * Default constructor for Trip.
     */
    public Trip() {
        this.stops = new ArrayList<>();
    }

    /**
     * Gets the trip's ID.
     * @return the trip's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the trip's ID.
     * @param id the trip's ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the driver's ID.
     * @return the driver's ID
     */
    public String getDriverId() {
        return driverId;
    }

    /**
     * Sets the driver's ID.
     * @param driverId the driver's ID
     */
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    /**
     * Gets the departure station.
     * @return the departure station
     */
    public String getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station.
     * @param departureStation the departure station
     */
    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Gets the arrival station.
     * @return the arrival station
     */
    public String getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station.
     * @param arrivalStation the arrival station
     */
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Gets the number of seats.
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats.
     * @param numberOfSeats the number of seats
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the departure time.
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     * @param departureTime the departure time
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time.
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time.
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the list of stops.
     * @return the list of stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * Sets the list of stops.
     * @param stops the list of stops
     */
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    /**
     * Adds a stop to the trip.
     * @param stop the stop to add
     */
    public void addStop(Stop stop) {
        this.stops.add(stop);
    }
}

/**
 * Represents a booking in the Online Rideshare System.
 */
class Booking {
    private String customerId;
    private String tripId;
    private int numberOfSeats;
    private LocalDateTime bookingTime;

    /**
     * Default constructor for Booking.
     */
    public Booking() {}

    /**
     * Gets the customer's ID.
     * @return the customer's ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer's ID.
     * @param customerId the customer's ID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the trip's ID.
     * @return the trip's ID
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * Sets the trip's ID.
     * @param tripId the trip's ID
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * Gets the number of seats booked.
     * @return the number of seats booked
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats booked.
     * @param numberOfSeats the number of seats booked
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the booking time.
     * @return the booking time
     */
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    /**
     * Sets the booking time.
     * @param bookingTime the booking time
     */
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}

/**
 * Provides functionality for the Online Rideshare System.
 */
class OnlineRideshareSystem {
    private List<User> users;
    private List<Trip> trips;
    private List<Booking> bookings;

    /**
     * Default constructor for OnlineRideshareSystem.
     */
    public OnlineRideshareSystem() {
        this.users = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Gets the list of users.
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users.
     * @param users the list of users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of trips.
     * @return the list of trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Sets the list of trips.
     * @param trips the list of trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Gets the list of bookings.
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings.
     * @param bookings the list of bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Validates whether a customer can book seats on a trip.
     * @param customerId the customer's ID
     * @param tripId the trip's ID
     * @param numberOfSeats the number of seats to book
     * @return true if the customer can book seats, false otherwise
     */
    public boolean validateBookingEligibility(String customerId, String tripId, int numberOfSeats) {
        Trip trip = findTripById(tripId);
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        LocalDateTime departureTime = trip.getDepartureTime();
        LocalDateTime bookingTime = LocalDateTime.now();
        if (bookingTime.isAfter(departureTime.minusHours(2))) {
            return false;
        }

        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(customerId) && booking.getTripId().equals(tripId)) {
                if (isOverlapping(booking.getBookingTime().toLocalDate(), bookingTime.toLocalDate(), departureTime.toLocalDate())) {
                    return false;
                }
            }
        }

        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        return true;
    }

    /**
     * Checks if two dates are overlapping.
     * @param date1 the first date
     * @param date2 the second date
     * @param departureDate the departure date
     * @return true if the dates are overlapping, false otherwise
     */
    private boolean isOverlapping(LocalDate date1, LocalDate date2, LocalDate departureDate) {
        return date1.equals(departureDate) && date2.equals(departureDate);
    }

    /**
     * Finds a trip by its ID.
     * @param tripId the trip's ID
     * @return the trip if found, null otherwise
     */
    private Trip findTripById(String tripId) {
        for (Trip trip : trips) {
            if (trip.getId().equals(tripId)) {
                return trip;
            }
        }
        return null;
    }

    /**
     * Calculates the discounted trip price for a booking.
     * @param customerId the customer's ID
     * @param tripId the trip's ID
     * @param bookingTime the booking time
     * @return the discounted price if applicable, otherwise the original price
     */
    public double calculateDiscountedTripPrice(String customerId, String tripId, LocalDateTime bookingTime) {
        User customer = findUserById(customerId);
        Trip trip = findTripById(tripId);

        if (customer.hasMembership() && bookingTime.isBefore(trip.getDepartureTime().minusHours(24))) {
            return trip.getPrice() * 0.8;
        } else {
            return trip.getPrice();
        }
    }

    /**
     * Finds a user by their ID.
     * @param userId the user's ID
     * @return the user if found, null otherwise
     */
    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Checks if two indirect trips share any common stops.
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if the trips share common stops, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (trip1.getDriverId().equals(trip2.getDriverId()) && trip1.getStops().size() > 0 && trip2.getStops().size() > 0) {
            for (Stop stop1 : trip1.getStops()) {
                for (Stop stop2 : trip2.getStops()) {
                    if (stop1.getStopStation().equals(stop2.getStopStation())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Computes the total reward points earned by a customer in a given month.
     * @param customerId the customer's ID
     * @param year the year
     * @param month the month
     * @return the total reward points
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
     * Validates whether a trip can be posted by a driver.
     * @param driverId the driver's ID
     * @param newTrip the new trip
     * @return true if the trip can be posted, false otherwise
     */
    public boolean validateTripPostingFeasibility(String driverId, Trip newTrip) {
        for (Trip trip : trips) {
            if (trip.getDriverId().equals(driverId) && isTimeConflicting(trip, newTrip)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two trips have conflicting times.
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if the trips have conflicting times, false otherwise
     */
    private boolean isTimeConflicting(Trip trip1, Trip trip2) {
        return (trip1.getDepartureTime().isBefore(trip2.getArrivalTime()) && trip1.getArrivalTime().isAfter(trip2.getDepartureTime()));
    }
}