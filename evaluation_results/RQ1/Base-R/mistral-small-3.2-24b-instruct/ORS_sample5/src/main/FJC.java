import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class User {
    private String id;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {
        this.id = "";
        this.email = "";
        this.phoneNumber = "";
        this.membership = null;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    private String type;
    private List<String> rewards;

    public Membership() {
        this.type = "";
        this.rewards = new ArrayList<>();
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }
}

class Trip {
    private String id;
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
        this.id = "";
        this.driverId = "";
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureDateTime = null;
        this.arrivalDateTime = null;
        this.price = 0.0;
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
     * @param customerId The ID of the customer trying to book seats.
     * @param numberOfSeats The number of seats the customer wants to book.
     * @return true if the customer can book seats, false otherwise.
     */
    public boolean validateBookingEligibility(String customerId, int numberOfSeats) {
        if (numberOfSeats <= 0 || numberOfSeats > this.numberOfSeats) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(this.departureDateTime.minusHours(2)) || currentTime.equals(this.departureDateTime.minusHours(2))) {
            return false;
        }

        for (Booking booking : this.bookings) {
            if (booking.getCustomerId().equals(customerId)) {
                if (booking.getBookingDateTime().toLocalDate().isEqual(currentTime.toLocalDate())) {
                    if (currentTime.isAfter(booking.getBookingDateTime()) || currentTime.equals(booking.getBookingDateTime())) {
                        return false;
                    }
                }
            }
        }

        this.numberOfSeats -= numberOfSeats;
        return true;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param otherTrip The other trip to compare with.
     * @return true if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlap(Trip otherTrip) {
        if (this.stops.isEmpty() || otherTrip.getStops().isEmpty()) {
            return false;
        }

        for (Stop stop1 : this.stops) {
            for (Stop stop2 : otherTrip.getStops()) {
                if (stop1.getStation().equals(stop2.getStation())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Validates if a new trip can be posted by checking for time conflicts with existing trips.
     *
     * @param existingTrips The list of existing trips to check against.
     * @return true if no time conflict, false otherwise.
     */
    public boolean validateTripPostingFeasibility(List<Trip> existingTrips) {
        for (Trip existingTrip : existingTrips) {
            if (this.driverId.equals(existingTrip.getDriverId())) {
                if (this.departureDateTime.isBefore(existingTrip.getArrivalDateTime()) &&
                    this.arrivalDateTime.isAfter(existingTrip.getDepartureDateTime())) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Stop {
    private String station;

    public Stop() {
        this.station = "";
    }

    // Getters and Setters
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}

class Booking {
    private String id;
    private String customerId;
    private String tripId;
    private int numberOfSeats;
    private LocalDateTime bookingDateTime;
    private double price;

    public Booking() {
        this.id = "";
        this.customerId = "";
        this.tripId = "";
        this.numberOfSeats = 0;
        this.bookingDateTime = null;
        this.price = 0.0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Calculates the discounted trip price if conditions are met.
     *
     * @param trip The trip being booked.
     * @return The final price after applying discount if conditions are met, otherwise the original price.
     */
    public double calculateDiscountedTripPrice(Trip trip) {
        User customer = new User(); // Assuming customer details are set elsewhere
        if (customer.getMembership() != null && this.bookingDateTime.isBefore(trip.getDepartureDateTime().minusHours(24))) {
            this.price *= 0.8;
        }
        return Math.round(this.price * 10) / 10.0;
    }
}

class RewardService {
    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param customer The customer whose reward points are to be calculated.
     * @param currentMonth The current month to calculate the points for.
     * @return The total reward points earned by the customer.
     */
    public int computeMonthlyRewardPoints(User customer, int currentMonth) {
        int totalPoints = 0;
        if (customer.getMembership() != null && customer.getMembership().getRewards().contains("points")) {
            for (Booking booking : customer.getBookings()) {
                if (booking.getBookingDateTime().getMonthValue() == currentMonth) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }
}