import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class User {
    private String userId;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {
        this.userId = "";
        this.email = "";
        this.phoneNumber = "";
        this.membership = null;
    }

    // Getters and Setters
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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

class Membership {
    private String membershipId;
    private String rewardType;
    private double discountRate;

    public Membership() {
        this.membershipId = "";
        this.rewardType = "";
        this.discountRate = 0.0;
    }

    // Getters and Setters
    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}

class Trip {
    private String tripId;
    private User driver;
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
        this.driver = new User();
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureDateTime = LocalDateTime.now();
        this.arrivalDateTime = LocalDateTime.now();
        this.price = 0.0;
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Getters and Setters
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
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
     * @param customer The customer trying to book seats.
     * @param numberOfSeats The number of seats the customer wants to book.
     * @return true if the customer can book seats, false otherwise.
     */
    public boolean validateBookingEligibility(User customer, int numberOfSeats) {
        if (this.numberOfSeats < numberOfSeats) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(this.departureDateTime.minusHours(2))) {
            return false;
        }

        for (Booking booking : this.bookings) {
            if (booking.getCustomer().equals(customer) &&
                booking.getBookingDateTime().toLocalDate().equals(currentTime.toLocalDate()) &&
                (booking.getBookingDateTime().isAfter(currentTime) || booking.getBookingDateTime().isEqual(currentTime))) {
                return false;
            }
        }

        this.numberOfSeats -= numberOfSeats;
        return true;
    }

    /**
     * Calculates the discounted trip price for a booking.
     *
     * @param booking The booking for which to calculate the discounted price.
     * @return The discounted price if conditions are met, otherwise the original price.
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        if (booking.getCustomer().getMembership() != null &&
            booking.getBookingDateTime().isBefore(this.departureDateTime.minusHours(24))) {
            return Math.round(this.price * (1 - booking.getCustomer().getMembership().getDiscountRate()) * 10) / 10.0;
        }
        return this.price;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param otherTrip The other trip to compare with.
     * @return true if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlap(Trip otherTrip) {
        if (this.driver.equals(otherTrip.getDriver()) && !this.stops.isEmpty() && !otherTrip.getStops().isEmpty()) {
            for (Stop stop1 : this.stops) {
                for (Stop stop2 : otherTrip.getStops()) {
                    if (stop1.getStopStation().equals(stop2.getStopStation())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validates if a new trip can be posted without time conflicts.
     *
     * @param newTrip The new trip to validate.
     * @return true if no time conflict, false otherwise.
     */
    public boolean validateTripPostingFeasibility(Trip newTrip) {
        if (this.driver.equals(newTrip.getDriver()) &&
            !this.departureDateTime.isAfter(newTrip.getArrivalDateTime()) &&
            !this.arrivalDateTime.isBefore(newTrip.getDepartureDateTime())) {
            return false;
        }
        return true;
    }
}

class Stop {
    private String stopId;
    private String stopStation;

    public Stop() {
        this.stopId = "";
        this.stopStation = "";
    }

    // Getters and Setters
    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

class Booking {
    private String bookingId;
    private User customer;
    private Trip trip;
    private int numberOfSeats;
    private LocalDateTime bookingDateTime;

    public Booking() {
        this.bookingId = "";
        this.customer = new User();
        this.trip = new Trip();
        this.numberOfSeats = 0;
        this.bookingDateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
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

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }
}

class RewardSystem {
    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param customer The customer for whom to compute the reward points.
     * @param currentMonth The current month to compute the reward points for.
     * @return The total reward points earned by the customer.
     */
    public int computeMonthlyRewardPoints(User customer, int currentMonth) {
        int totalPoints = 0;
        if (customer.getMembership() != null && "points".equals(customer.getMembership().getRewardType())) {
            for (Booking booking : customer.getBookings()) {
                if (booking.getBookingDateTime().getMonthValue() == currentMonth) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }
}