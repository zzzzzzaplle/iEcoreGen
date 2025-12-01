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
    private Membership membership;

    /**
     * Unparameterized constructor for User.
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
     * Gets the user's membership.
     * @return the user's membership
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the user's membership.
     * @param membership the user's membership
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

/**
 * Represents a membership in the Online Rideshare System.
 */
class Membership {
    private boolean hasMembership;
    private String rewardType;

    /**
     * Unparameterized constructor for Membership.
     */
    public Membership() {}

    /**
     * Gets whether the user has a membership.
     * @return whether the user has a membership
     */
    public boolean isHasMembership() {
        return hasMembership;
    }

    /**
     * Sets whether the user has a membership.
     * @param hasMembership whether the user has a membership
     */
    public void setHasMembership(boolean hasMembership) {
        this.hasMembership = hasMembership;
    }

    /**
     * Gets the reward type of the membership.
     * @return the reward type
     */
    public String getRewardType() {
        return rewardType;
    }

    /**
     * Sets the reward type of the membership.
     * @param rewardType the reward type
     */
    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
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
    private List<Booking> bookings;

    /**
     * Unparameterized constructor for Trip.
     */
    public Trip() {
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
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
     * Gets the stops.
     * @return the stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * Sets the stops.
     * @param stops the stops
     */
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    /**
     * Gets the bookings.
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the bookings.
     * @param bookings the bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

/**
 * Represents a stop in a trip.
 */
class Stop {
    private String stopStation;

    /**
     * Unparameterized constructor for Stop.
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
 * Represents a booking in the Online Rideshare System.
 */
class Booking {
    private String customerId;
    private String tripId;
    private int numberOfSeats;
    private LocalDateTime bookingTime;

    /**
     * Unparameterized constructor for Booking.
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
 class ORS {
    private List<User> users;
    private List<Trip> trips;

    /**
     * Unparameterized constructor for ORS.
     */
    public ORS() {
        this.users = new ArrayList<>();
        this.trips = new ArrayList<>();
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
        if (trip.getDepartureTime().isBefore(LocalDateTime.now().plusHours(2))) {
            return false;
        }
        for (Booking booking : trip.getBookings()) {
            if (booking.getCustomerId().equals(customerId) && isSameDay(booking.getBookingTime(), trip.getDepartureTime())) {
                if (isOverlapping(booking.getBookingTime(), trip.getDepartureTime())) {
                    return false;
                }
            }
        }
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        return true;
    }

    /**
     * Checks if two dates are on the same day.
     * @param date1 the first date
     * @param date2 the second date
     * @return true if the dates are on the same day, false otherwise
     */
    private boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().equals(date2.toLocalDate());
    }

    /**
     * Checks if two time periods overlap.
     * @param start1 the start time of the first period
     * @param start2 the start time of the second period
     * @return true if the periods overlap, false otherwise
     */
    private boolean isOverlapping(LocalDateTime start1, LocalDateTime start2) {
        return Math.abs(start1.getHour() - start2.getHour()) < 2;
    }

    /**
     * Calculates the discounted trip price for a booking.
     * @param customerId the customer's ID
     * @param tripId the trip's ID
     * @param bookingTime the booking time
     * @return the discounted price
     */
    public double calculateDiscountedTripPrice(String customerId, String tripId, LocalDateTime bookingTime) {
        User customer = findUserById(customerId);
        Trip trip = findTripById(tripId);
        if (customer.getMembership().isHasMembership() && bookingTime.isBefore(trip.getDepartureTime().minusHours(24))) {
            return trip.getPrice() * 0.8;
        }
        return trip.getPrice();
    }

    /**
     * Checks if two indirect trips share any common stops.
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if the trips share a common stop, false otherwise
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
     * Computes the total reward points earned by a customer in a given month.
     * @param customerId the customer's ID
     * @param year the year
     * @param month the month
     * @return the total reward points
     */
    public int computeMonthlyRewardPoints(String customerId, int year, int month) {
        int totalPoints = 0;
        for (Trip trip : trips) {
            for (Booking booking : trip.getBookings()) {
                if (booking.getCustomerId().equals(customerId) && booking.getBookingTime().getYear() == year && booking.getBookingTime().getMonthValue() == month) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Validates whether a trip can be posted.
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
     * @return true if the trips conflict, false otherwise
     */
    private boolean isTimeConflicting(Trip trip1, Trip trip2) {
        return !(trip1.getDepartureTime().isAfter(trip2.getArrivalTime()) || trip1.getArrivalTime().isBefore(trip2.getDepartureTime()));
    }

    /**
     * Finds a user by ID.
     * @param id the user's ID
     * @return the user, or null if not found
     */
    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a trip by ID.
     * @param id the trip's ID
     * @return the trip, or null if not found
     */
    private Trip findTripById(String id) {
        for (Trip trip : trips) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ORS ors = new ORS();
        // Example usage:
        User user = new User();
        user.setId("1");
        ors.users.add(user);

        Trip trip = new Trip();
        trip.setId("1");
        trip.setDepartureTime(LocalDateTime.parse("2024-03-16 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        trip.setNumberOfSeats(10);
        ors.trips.add(trip);

        System.out.println(ors.validateBookingEligibility("1", "1", 2));
    }
}