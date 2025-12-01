import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private boolean hasRewards;
    private double discountPercentage;
    private boolean isPointsReward;

    /**
     * Unparameterized constructor for Membership.
     */
    public Membership() {}

    /**
     * Gets whether the membership has rewards.
     * @return whether the membership has rewards
     */
    public boolean isHasRewards() {
        return hasRewards;
    }

    /**
     * Sets whether the membership has rewards.
     * @param hasRewards whether the membership has rewards
     */
    public void setHasRewards(boolean hasRewards) {
        this.hasRewards = hasRewards;
    }

    /**
     * Gets the discount percentage of the membership.
     * @return the discount percentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets the discount percentage of the membership.
     * @param discountPercentage the discount percentage
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    /**
     * Gets whether the membership is a points reward.
     * @return whether the membership is a points reward
     */
    public boolean isPointsReward() {
        return isPointsReward;
    }

    /**
     * Sets whether the membership is a points reward.
     * @param pointsReward whether the membership is a points reward
     */
    public void setPointsReward(boolean pointsReward) {
        isPointsReward = pointsReward;
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
     * Adds a stop to the trip.
     * @param stop the stop to add
     */
    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    /**
     * Gets the bookings.
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the trip.
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        this.numberOfSeats -= booking.getNumberOfSeats();
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

    /**
     * Validates whether a customer can book seats on a trip.
     * @param trip the trip to book
     * @param customerId the customer's ID
     * @param numberOfSeats the number of seats to book
     * @param bookingTime the booking time
     * @return true if the customer can book seats, false otherwise
     */
    public boolean validateBookingEligibility(Trip trip, String customerId, int numberOfSeats, LocalDateTime bookingTime) {
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        for (Booking booking : trip.getBookings()) {
            if (booking.getCustomerId().equals(customerId) && isOverlapping(booking.getBookingTime(), bookingTime)) {
                return false;
            }
        }
        long hoursBetween = ChronoUnit.HOURS.between(bookingTime, trip.getDepartureTime());
        return hoursBetween > 2;
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
     * Calculates the discounted trip price for a booking.
     * @param trip the trip
     * @param customer the customer
     * @param bookingTime the booking time
     * @return the discounted price
     */
    public double calculateDiscountedTripPrice(Trip trip, User customer, LocalDateTime bookingTime) {
        if (customer.getMembership() != null && customer.getMembership().isHasRewards() && ChronoUnit.HOURS.between(bookingTime, trip.getDepartureTime()) >= 24) {
            return trip.getPrice() * (1 - customer.getMembership().getDiscountPercentage() / 100);
        }
        return trip.getPrice();
    }

    /**
     * Checks if two indirect trips share any common stops.
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if the trips share any common stops, false otherwise
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
     * @param customer the customer
     * @param year the year
     * @param month the month
     * @return the total reward points
     */
    public int computeMonthlyRewardPoints(User customer, int year, int month) {
        int totalPoints = 0;
        for (Trip trip : getTripsForCustomer(customer.getId())) {
            for (Booking booking : trip.getBookings()) {
                if (booking.getCustomerId().equals(customer.getId()) && booking.getBookingTime().getYear() == year && booking.getBookingTime().getMonthValue() == month) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Gets the trips for a customer.
     * @param customerId the customer's ID
     * @return the trips for the customer
     */
    private List<Trip> getTripsForCustomer(String customerId) {
        // This method is not implemented as it's not part of the given requirements
        // It's assumed that this method will return a list of trips for the given customer ID
        return new ArrayList<>();
    }

    /**
     * Validates whether a trip can be posted by a driver.
     * @param driverId the driver's ID
     * @param newTrip the new trip
     * @param existingTrips the existing trips
     * @return true if the trip can be posted, false otherwise
     */
    public boolean validateTripPostingFeasibility(String driverId, Trip newTrip, List<Trip> existingTrips) {
        if (!isValidDriver(driverId)) {
            return false;
        }
        if (newTrip.getDepartureTime().isAfter(newTrip.getArrivalTime())) {
            return false;
        }
        for (Trip trip : existingTrips) {
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
        return !(trip1.getArrivalTime().isBefore(trip2.getDepartureTime()) || trip1.getDepartureTime().isAfter(trip2.getArrivalTime()));
    }

    /**
     * Checks if a driver is valid.
     * @param driverId the driver's ID
     * @return true if the driver is valid, false otherwise
     */
    private boolean isValidDriver(String driverId) {
        // This method is not implemented as it's not part of the given requirements
        // It's assumed that this method will return true if the driver ID is valid, false otherwise
        return true;
    }

    public static void main(String[] args) {
        OnlineRideshareSystem ors = new OnlineRideshareSystem();
        // Example usage:
        User customer = new User();
        customer.setId("C1");
        Membership membership = new Membership();
        membership.setHasRewards(true);
        membership.setDiscountPercentage(20);
        customer.setMembership(membership);

        Trip trip = new Trip();
        trip.setId("T1");
        trip.setDriverId("D1");
        trip.setDepartureStation("A");
        trip.setArrivalStation("B");
        trip.setNumberOfSeats(10);
        trip.setDepartureTime(LocalDateTime.parse("2024-03-16 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        trip.setArrivalTime(LocalDateTime.parse("2024-03-16 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        trip.setPrice(100);

        Booking booking = new Booking();
        booking.setCustomerId("C1");
        booking.setNumberOfSeats(2);
        booking.setBookingTime(LocalDateTime.parse("2024-03-15 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        trip.addBooking(booking);

        System.out.println(ors.validateBookingEligibility(trip, "C1", 2, LocalDateTime.parse("2024-03-15 08:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        System.out.println(ors.calculateDiscountedTripPrice(trip, customer, LocalDateTime.parse("2024-03-15 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
}