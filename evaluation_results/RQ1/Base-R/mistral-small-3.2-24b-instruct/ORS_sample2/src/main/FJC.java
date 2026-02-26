import java.time.LocalDateTime;
import java.time.LocalDate;
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

    public User(String userId, String email, String phoneNumber, Membership membership) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.membership = membership;
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
    private int rewardValue;

    public Membership() {
        this.membershipId = "";
        this.rewardType = "";
        this.rewardValue = 0;
    }

    public Membership(String membershipId, String rewardType, int rewardValue) {
        this.membershipId = membershipId;
        this.rewardType = rewardType;
        this.rewardValue = rewardValue;
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

    public int getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(int rewardValue) {
        this.rewardValue = rewardValue;
    }
}

class Trip {
    private String tripId;
    private User driver;
    private String departureStation;
    private String arrivalStation;
    private int seats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private List<Stop> stops;
    private List<Booking> bookings;

    public Trip() {
        this.tripId = "";
        this.driver = null;
        this.departureStation = "";
        this.arrivalStation = "";
        this.seats = 0;
        this.departureDateTime = null;
        this.arrivalDateTime = null;
        this.price = 0.0;
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public Trip(String tripId, User driver, String departureStation, String arrivalStation, int seats,
                LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, double price) {
        this.tripId = tripId;
        this.driver = driver;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.seats = seats;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
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
     * @param customer The customer attempting to book seats.
     * @param seats The number of seats the customer wants to book.
     * @return true if the customer can book seats, false otherwise.
     */
    public boolean validateBookingEligibility(User customer, int seats) {
        if (this.seats < seats) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(departureDateTime.minusHours(2))) {
            return false;
        }

        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer) &&
                (booking.getBookingDateTime().toLocalDate().equals(currentTime.toLocalDate()) ||
                 booking.getBookingDateTime().toLocalDate().equals(departureDateTime.toLocalDate()))) {
                return false;
            }
        }

        this.seats -= seats;
        return true;
    }

    /**
     * Computes the final price for a booking after applying reward discounts.
     *
     * @param booking The booking to compute the price for.
     * @return The final price after applying discounts.
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        if (booking.getCustomer().getMembership() != null &&
            booking.getBookingDateTime().isBefore(departureDateTime.minusHours(24))) {
            return Math.round(price * 0.8 * 10) / 10.0;
        }
        return price;
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops.
     *
     * @param otherTrip The other trip to check for common stops.
     * @return true if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlap(Trip otherTrip) {
        if (this.stops.size() < 1 || otherTrip.getStops().size() < 1) {
            return false;
        }

        for (Stop stop1 : this.stops) {
            for (Stop stop2 : otherTrip.getStops()) {
                if (stop1.getStopStation().equals(stop2.getStopStation())) {
                    return true;
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
        if (this.departureDateTime.isAfter(this.arrivalDateTime)) {
            return false;
        }

        for (Trip existingTrip : this.driver.getTrips()) {
            if (existingTrip.getTripId().equals(newTrip.getTripId())) {
                continue;
            }

            if (!(newTrip.getDepartureDateTime().isAfter(existingTrip.getArrivalDateTime()) ||
                  newTrip.getArrivalDateTime().isBefore(existingTrip.getDepartureDateTime()))) {
                return false;
            }
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

    public Stop(String stopId, String stopStation) {
        this.stopId = stopId;
        this.stopStation = stopStation;
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
    private int seats;
    private LocalDateTime bookingDateTime;

    public Booking() {
        this.bookingId = "";
        this.customer = null;
        this.trip = null;
        this.seats = 0;
        this.bookingDateTime = null;
    }

    public Booking(String bookingId, User customer, Trip trip, int seats, LocalDateTime bookingDateTime) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.trip = trip;
        this.seats = seats;
        this.bookingDateTime = bookingDateTime;
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param currentMonth The current month to calculate points for.
     * @return The total reward points earned.
     */
    public int computeMonthlyRewardPoints(LocalDate currentMonth) {
        if (customer.getMembership() == null ||
            !customer.getMembership().getRewardType().equals("points")) {
            return 0;
        }

        int totalPoints = 0;
        for (Booking booking : customer.getBookings()) {
            if (booking.getBookingDateTime().toLocalDate().getMonth().equals(currentMonth.getMonth()) &&
                booking.getBookingDateTime().toLocalDate().getYear() == currentMonth.getYear()) {
                totalPoints += booking.getSeats() * 5;
            }
        }
        return totalPoints;
    }
}