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
    private boolean isMember;
    private String membershipType;

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
     * Checks if the user is a member.
     * @return true if the user is a member, false otherwise
     */
    public boolean isMember() {
        return isMember;
    }

    /**
     * Sets the user's membership status.
     * @param member true if the user is a member, false otherwise
     */
    public void setMember(boolean member) {
        isMember = member;
    }

    /**
     * Gets the user's membership type.
     * @return the user's membership type
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * Sets the user's membership type.
     * @param membershipType the user's membership type
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}

/**
 * Represents a stop in a trip.
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
    private List<Trip> trips;
    private List<User> users;
    private List<Booking> bookings;

    /**
     * Default constructor for ORS.
     */
    public ORS() {
        this.trips = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Gets the trips.
     * @return the trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Sets the trips.
     * @param trips the trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Gets the users.
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the users.
     * @param users the users
     */
    public void setUsers(List<User> users) {
        this.users = users;
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

    /**
     * Validates booking eligibility for a customer.
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
                if (isOverlapping(booking.getBookingTime(), departureTime)) {
                    return false;
                }
            }
        }

        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        return true;
    }

    /**
     * Checks if two times are overlapping on the same day.
     * @param time1 the first time
     * @param time2 the second time
     * @return true if the times are overlapping, false otherwise
     */
    private boolean isOverlapping(LocalDateTime time1, LocalDateTime time2) {
        return time1.toLocalDate().equals(time2.toLocalDate());
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
     * @return the discounted price if eligible, the original price otherwise
     */
    public double calculateDiscountedTripPrice(String customerId, String tripId) {
        User customer = findUserById(customerId);
        Trip trip = findTripById(tripId);
        if (customer == null || trip == null) {
            return trip.getPrice();
        }

        if (!customer.isMember()) {
            return trip.getPrice();
        }

        LocalDateTime bookingTime = LocalDateTime.now();
        LocalDateTime departureTime = trip.getDepartureTime();
        if (bookingTime.isBefore(departureTime.minusHours(24))) {
            return trip.getPrice() * 0.8;
        }

        return trip.getPrice();
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
     * Checks if two indirect trips have any common stops.
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if the trips have common stops, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (!trip1.getDriverId().equals(trip2.getDriverId())) {
            return false;
        }

        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
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
        User customer = findUserById(customerId);
        if (customer == null || !customer.isMember() || !customer.getMembershipType().equals("points")) {
            return 0;
        }

        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(customerId)) {
                LocalDateTime bookingTime = booking.getBookingTime();
                if (bookingTime.getYear() == year && bookingTime.getMonthValue() == month) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }

        return totalPoints;
    }

    /**
     * Validates trip posting feasibility for a driver.
     * @param driverId the driver's ID
     * @param newTrip the new trip
     * @return true if the trip can be posted, false otherwise
     */
    public boolean validateTripPostingFeasibility(String driverId, Trip newTrip) {
        User driver = findUserById(driverId);
        if (driver == null) {
            return false;
        }

        if (newTrip.getDepartureTime().isAfter(newTrip.getArrivalTime())) {
            return false;
        }

        for (Trip trip : trips) {
            if (trip.getDriverId().equals(driverId)) {
                if (isTimeConflicting(trip.getDepartureTime(), trip.getArrivalTime(), newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if two time periods are conflicting.
     * @param start1 the start time of the first period
     * @param end1 the end time of the first period
     * @param start2 the start time of the second period
     * @param end2 the end time of the second period
     * @return true if the periods are conflicting, false otherwise
     */
    private boolean isTimeConflicting(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return !(end1.isBefore(start2) || end2.isBefore(start1));
    }
}