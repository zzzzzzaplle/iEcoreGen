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
     * Default constructor
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
    private RewardType rewardType;

    /**
     * Default constructor
     */
    public Membership() {
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }
}

/**
 * Enum representing different types of rewards available in the system.
 */
enum RewardType {
    CASHBACK, DISCOUNT, POINTS
}

/**
 * Represents a station in the rideshare system.
 */
class Station {
    private String stationId;
    private String stationName;

    /**
     * Default constructor
     */
    public Station() {
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopId;
    private Station stopStation;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    /**
     * Default constructor
     */
    public Stop() {
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public Station getStopStation() {
        return stopStation;
    }

    public void setStopStation(Station stopStation) {
        this.stopStation = stopStation;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}

/**
 * Represents a trip in the rideshare system.
 * Trips can be direct or indirect (with multiple stops).
 */
class Trip {
    private String tripId;
    private User driver;
    private Station departureStation;
    private Station arrivalStation;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;
    private List<Booking> bookings;

    /**
     * Default constructor
     */
    public Trip() {
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
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

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Checks if this trip is an indirect trip (has one or more stops).
     *
     * @return true if the trip has at least one stop, false otherwise
     */
    public boolean isIndirectTrip() {
        return stops != null && !stops.isEmpty();
    }

    /**
     * Gets all stop stations in this trip.
     *
     * @return list of all stop stations
     */
    public List<Station> getAllStopStations() {
        List<Station> stopStations = new ArrayList<>();
        if (stops != null) {
            for (Stop stop : stops) {
                if (stop.getStopStation() != null) {
                    stopStations.add(stop.getStopStation());
                }
            }
        }
        return stopStations;
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
    private double finalPrice;

    /**
     * Default constructor
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

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
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
     * Default constructor
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
     * Eligibility criteria:
     * - Trip must exist and have available seats
     * - Number of available seats must be greater than or equal to number of booking seats
     * - No overlapping time periods between existing bookings and the new booking on the same day
     * - Current booking time must be at least 2 hours earlier than departure time (excluding exactly 2 hours)
     *
     * @param customer the customer attempting to make the booking
     * @param trip the trip to book seats on
     * @param seatsRequested the number of seats requested
     * @param bookingTime the time when the booking is being made
     * @return true if booking is eligible, false otherwise
     * @throws IllegalArgumentException if customer, trip, or bookingTime is null, or seatsRequested is not positive
     */
    public boolean validateBookingEligibility(User customer, Trip trip, int seatsRequested, LocalDateTime bookingTime) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (trip == null) {
            throw new IllegalArgumentException("Trip cannot be null");
        }
        if (bookingTime == null) {
            throw new IllegalArgumentException("Booking time cannot be null");
        }
        if (seatsRequested <= 0) {
            throw new IllegalArgumentException("Seats requested must be positive");
        }

        // Check if trip exists and has available seats
        if (!trips.contains(trip)) {
            return false;
        }

        if (trip.getAvailableSeats() < seatsRequested) {
            return false;
        }

        // Check time constraint: booking must be at least 2 hours before departure
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!bookingTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for overlapping time periods on the same day
        if (hasTimeOverlap(customer, trip, bookingTime)) {
            return false;
        }

        // If all checks pass, update available seats and return true
        trip.setAvailableSeats(trip.getAvailableSeats() - seatsRequested);
        return true;
    }

    /**
     * Checks if there are overlapping time periods between the new booking and existing bookings for the same customer on the same day.
     *
     * @param customer the customer making the booking
     * @param newTrip the new trip being booked
     * @param bookingTime the booking time
     * @return true if there's a time overlap, false otherwise
     */
    private boolean hasTimeOverlap(User customer, Trip newTrip, LocalDateTime bookingTime) {
        LocalDateTime newTripStart = newTrip.getDepartureTime();
        LocalDateTime newTripEnd = newTrip.getArrivalTime();

        for (Booking existingBooking : bookings) {
            if (existingBooking.getCustomer().equals(customer)) {
                Trip existingTrip = existingBooking.getTrip();
                
                // Check if bookings are on the same day
                if (newTripStart.toLocalDate().equals(existingTrip.getDepartureTime().toLocalDate())) {
                    LocalDateTime existingTripStart = existingTrip.getDepartureTime();
                    LocalDateTime existingTripEnd = existingTrip.getArrivalTime();

                    // Check for time overlap (adjacent boundaries are not considered overlap)
                    if (newTripStart.isBefore(existingTripEnd) && newTripEnd.isAfter(existingTripStart)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Computes the final price for a booking after applying reward discounts.
     * Discount of 20% applies only if:
     * - Customer has membership with discount reward type
     * - Booking is made at least 24 hours before departure
     *
     * @param booking the booking to calculate discounted price for
     * @return the final price after discount (rounded to 1 decimal place) or original price if conditions not met
     * @throws IllegalArgumentException if booking is null
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        User customer = booking.getCustomer();
        Trip trip = booking.getTrip();
        LocalDateTime bookingTime = booking.getBookingTime();

        // Check conditions for discount
        boolean hasMembership = customer.getMembership() != null;
        boolean hasDiscountReward = hasMembership && customer.getMembership().getRewardType() == RewardType.DISCOUNT;
        boolean bookedEarlyEnough = bookingTime.isBefore(trip.getDepartureTime().minusHours(24));

        if (hasDiscountReward && bookedEarlyEnough) {
            double discountedPrice = trip.getPrice() * 0.8;
            // Round to 1 decimal place
            return Math.round(discountedPrice * 10.0) / 10.0;
        }

        return trip.getPrice();
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 the first indirect trip
     * @param trip2 the second indirect trip
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

        List<Station> trip1Stops = trip1.getAllStopStations();
        List<Station> trip2Stops = trip2.getAllStopStations();

        for (Station stop1 : trip1Stops) {
            for (Station stop2 : trip2Stops) {
                if (stop1.equals(stop2)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Calculates total reward points earned by a customer in the given current month.
     * Preconditions: Customer must have membership with points reward type.
     * Calculation: 5 points per seat per booking within the target month.
     *
     * @param customer the customer to calculate points for
     * @param targetMonth the target month for calculation
     * @return total points earned in the target month, or 0 if conditions not met
     * @throws IllegalArgumentException if customer or targetMonth is null
     */
    public int computeMonthlyRewardPoints(User customer, YearMonth targetMonth) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (targetMonth == null) {
            throw new IllegalArgumentException("Target month cannot be null");
        }

        // Check preconditions
        if (customer.getMembership() == null || customer.getMembership().getRewardType() != RewardType.POINTS) {
            return 0;
        }

        int totalPoints = 0;

        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                LocalDateTime bookingTime = booking.getBookingTime();
                YearMonth bookingMonth = YearMonth.from(bookingTime);

                if (bookingMonth.equals(targetMonth)) {
                    totalPoints += booking.getSeatsBooked() * 5;
                }
            }
        }

        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip without time conflicts.
     * Preconditions: Driver must exist and be valid; new trip time must be valid (departure < arrival).
     * Checks for time conflicts with existing trips (intersecting time periods).
     * Adjacent boundaries are not considered conflicts.
     *
     * @param driver the driver posting the trip
     * @param newTrip the new trip to be posted
     * @return true if no time conflicts found, false otherwise
     * @throws IllegalArgumentException if driver or newTrip is null, or trip times are invalid
     */
    public boolean validateTripPostingFeasibility(User driver, Trip newTrip) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (newTrip == null) {
            throw new IllegalArgumentException("New trip cannot be null");
        }
        if (newTrip.getDepartureTime() == null || newTrip.getArrivalTime() == null) {
            throw new IllegalArgumentException("Trip times cannot be null");
        }
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }

        LocalDateTime newTripStart = newTrip.getDepartureTime();
        LocalDateTime newTripEnd = newTrip.getArrivalTime();

        for (Trip existingTrip : trips) {
            if (existingTrip.getDriver().equals(driver)) {
                LocalDateTime existingTripStart = existingTrip.getDepartureTime();
                LocalDateTime existingTripEnd = existingTrip.getArrivalTime();

                // Check for time conflict (adjacent boundaries are not considered conflict)
                if (newTripStart.isBefore(existingTripEnd) && newTripEnd.isAfter(existingTripStart)) {
                    return false;
                }
            }
        }

        return true;
    }
}