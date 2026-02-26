import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Online Rideshare System.
 * Users can be either drivers or customers.
 */
class User {
    private String userId;
    private String email;
    private String phoneNumber;
    private Membership membership;

    /**
     * Default constructor for User.
     */
    public User() {
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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

/**
 * Represents a membership package that includes rewards.
 */
class Membership {
    private String membershipId;
    private Reward reward;

    /**
     * Default constructor for Membership.
     */
    public Membership() {
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}

/**
 * Represents a reward that can be cashback, discount, or points.
 */
class Reward {
    private String rewardId;
    private RewardType type;

    /**
     * Default constructor for Reward.
     */
    public Reward() {
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public RewardType getType() {
        return type;
    }

    public void setType(RewardType type) {
        this.type = type;
    }
}

/**
 * Enumeration of reward types available in the system.
 */
enum RewardType {
    CASHBACK, DISCOUNT, POINTS
}

/**
 * Represents a trip in the Online Rideshare System.
 * Trips can be direct or indirect (with multiple stops).
 */
class Trip {
    private String tripId;
    private User driver;
    private String departureStation;
    private String arrivalStation;
    private int availableSeats;
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
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

    /**
     * Checks if this trip is an indirect trip (has stops).
     * @return true if the trip has one or more stops, false otherwise
     */
    public boolean isIndirectTrip() {
        return stops != null && !stops.isEmpty();
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopId;
    private String stopStation;

    /**
     * Default constructor for Stop.
     */
    public Stop() {
    }

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

/**
 * Represents a booking made by a customer for a trip.
 */
class Booking {
    private String bookingId;
    private User customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingTime;

    /**
     * Default constructor for Booking.
     */
    public Booking() {
    }

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

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}

/**
 * Main service class for the Online Rideshare System.
 * Contains business logic for all functional requirements.
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
     * Validates if a customer can book seats on a trip.
     * 
     * @param customer The customer attempting to make the booking
     * @param trip The trip for which seats are being booked
     * @param seatsRequested The number of seats requested
     * @param bookingTime The time when the booking is being made
     * @return true if the booking is eligible, false otherwise
     * @throws IllegalArgumentException if customer, trip, or bookingTime is null, or seatsRequested is non-positive
     */
    public boolean validateBookingEligibility(User customer, Trip trip, int seatsRequested, LocalDateTime bookingTime) {
        if (customer == null || trip == null || bookingTime == null) {
            throw new IllegalArgumentException("Customer, trip, and booking time cannot be null");
        }
        if (seatsRequested <= 0) {
            throw new IllegalArgumentException("Number of seats requested must be positive");
        }

        // Check if trip exists and has available seats
        if (!trips.contains(trip)) {
            return false;
        }
        
        if (trip.getAvailableSeats() < seatsRequested) {
            return false;
        }

        // Check if booking is at least 2 hours before departure (excluding exactly 2 hours)
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!bookingTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for overlapping time periods on the same day
        LocalDateTime bookingDate = bookingTime.toLocalDate().atStartOfDay();
        LocalDateTime nextDay = bookingDate.plusDays(1);
        
        for (Booking existingBooking : bookings) {
            if (existingBooking.getCustomer().equals(customer)) {
                LocalDateTime existingBookingTime = existingBooking.getBookingTime();
                if (existingBookingTime.isAfter(bookingDate) && existingBookingTime.isBefore(nextDay)) {
                    // Check if there's any overlap (excluding adjacent boundaries)
                    if (existingBookingTime.equals(bookingTime)) {
                        return false;
                    }
                }
            }
        }

        // If all checks pass, update available seats and return true
        trip.setAvailableSeats(trip.getAvailableSeats() - seatsRequested);
        return true;
    }

    /**
     * Computes the final price for a booking after applying reward discounts.
     * 
     * @param booking The booking for which to calculate the discounted price
     * @return The final price after discount if applicable, otherwise the original price
     * @throws IllegalArgumentException if booking is null
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        User customer = booking.getCustomer();
        Trip trip = booking.getTrip();
        LocalDateTime bookingTime = booking.getBookingTime();

        // Check if customer has membership with discount reward
        if (customer.getMembership() == null || 
            customer.getMembership().getReward() == null ||
            customer.getMembership().getReward().getType() != RewardType.DISCOUNT) {
            return Math.round(trip.getPrice() * 10.0) / 10.0; // Keep 1 decimal place
        }

        // Check if booking is made at least 24 hours before departure
        LocalDateTime twentyFourHoursBeforeDeparture = trip.getDepartureTime().minusHours(24);
        if (!bookingTime.isBefore(twentyFourHoursBeforeDeparture)) {
            return Math.round(trip.getPrice() * 10.0) / 10.0; // Keep 1 decimal place
        }

        // Apply 20% discount
        double discountedPrice = trip.getPrice() * 0.8;
        return Math.round(discountedPrice * 10.0) / 10.0; // Keep 1 decimal place
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 The first indirect trip
     * @param trip2 The second indirect trip
     * @return true if any stop station is shared between the two trips, false otherwise
     * @throws IllegalArgumentException if either trip is null or not an indirect trip
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            throw new IllegalArgumentException("Trips cannot be null");
        }
        if (!trip1.isIndirectTrip() || !trip2.isIndirectTrip()) {
            throw new IllegalArgumentException("Both trips must be indirect trips");
        }
        if (!trip1.getDriver().equals(trip2.getDriver())) {
            return false;
        }

        // Check for common stops
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
     * Calculates total reward points earned by a customer in the given current month.
     * 
     * @param customer The customer for whom to calculate points
     * @param targetMonth The target month for calculation (format: yyyy-MM)
     * @return The total points earned by the customer in the target month
     * @throws IllegalArgumentException if customer is null, doesn't have membership with points reward, or targetMonth is invalid
     */
    public int computeMonthlyRewardPoints(User customer, String targetMonth) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getMembership() == null || 
            customer.getMembership().getReward() == null ||
            customer.getMembership().getReward().getType() != RewardType.POINTS) {
            throw new IllegalArgumentException("Customer must have a membership with points reward");
        }

        YearMonth month;
        try {
            month = YearMonth.parse(targetMonth);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid target month format. Expected format: yyyy-MM");
        }

        int totalPoints = 0;

        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                LocalDateTime bookingDate = booking.getBookingTime();
                YearMonth bookingMonth = YearMonth.from(bookingDate);
                
                if (bookingMonth.equals(month)) {
                    totalPoints += booking.getSeatsBooked() * 5;
                }
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip without time conflicts.
     * 
     * @param driver The driver posting the trip
     * @param newTrip The new trip to be posted
     * @return true if no time conflicts exist, false otherwise
     * @throws IllegalArgumentException if driver or newTrip is null, or if trip times are invalid
     */
    public boolean validateTripPostingFeasibility(User driver, Trip newTrip) {
        if (driver == null || newTrip == null) {
            throw new IllegalArgumentException("Driver and trip cannot be null");
        }
        if (newTrip.getDepartureTime() == null || newTrip.getArrivalTime() == null) {
            throw new IllegalArgumentException("Trip departure and arrival times cannot be null");
        }
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }

        // Check for time conflicts with existing trips by the same driver
        for (Trip existingTrip : trips) {
            if (existingTrip.getDriver().equals(driver)) {
                LocalDateTime existingStart = existingTrip.getDepartureTime();
                LocalDateTime existingEnd = existingTrip.getArrivalTime();
                LocalDateTime newStart = newTrip.getDepartureTime();
                LocalDateTime newEnd = newTrip.getArrivalTime();

                // Check if time periods intersect (excluding adjacent boundaries)
                boolean startsDuringExisting = (newStart.isAfter(existingStart) && newStart.isBefore(existingEnd));
                boolean endsDuringExisting = (newEnd.isAfter(existingStart) && newEnd.isBefore(existingEnd));
                boolean existingStartsDuringNew = (existingStart.isAfter(newStart) && existingStart.isBefore(newEnd));
                boolean existingEndsDuringNew = (existingEnd.isAfter(newStart) && existingEnd.isBefore(newEnd));
                boolean exactOverlap = newStart.equals(existingStart) && newEnd.equals(existingEnd);

                if (startsDuringExisting || endsDuringExisting || existingStartsDuringNew || existingEndsDuringNew || exactOverlap) {
                    return false;
                }
            }
        }

        return true;
    }
}