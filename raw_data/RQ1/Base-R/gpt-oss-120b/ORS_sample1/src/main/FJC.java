import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all users of the Online Rideshare System.
 */
 class User {
    private String id;
    private String email;
    private String phoneNumber;

    public User() {
        // no‑arg constructor
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}

/**
 * Driver is a special kind of user who can post trips.
 */
 class Driver extends User {
    private List<Trip> postedTrips = new ArrayList<>();

    public Driver() {
        // no‑arg constructor
    }

    public List<Trip> getPostedTrips() { return postedTrips; }
    public void setPostedTrips(List<Trip> postedTrips) { this.postedTrips = postedTrips; }

    public void addTrip(Trip trip) {
        this.postedTrips.add(trip);
    }
}

/**
 * Customer is a user that can make bookings and may have a membership.
 */
 class Customer extends User {
    private Membership membership;
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {
        // no‑arg constructor
    }

    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

/**
 * Membership defines the reward package a customer may have.
 */
 class Membership {
 enum RewardType { CASHBACK, DISCOUNT, POINTS }

    private RewardType rewardType;
    private boolean pointsReward;   // true if points are part of the package

    public Membership() {
        // no‑arg constructor
    }

    public RewardType getRewardType() { return rewardType; }
    public void setRewardType(RewardType rewardType) { this.rewardType = rewardType; }

    public boolean isPointsReward() { return pointsReward; }
    public void setPointsReward(boolean pointsReward) { this.pointsReward = pointsReward; }
}

/**
 * Represents a stop in an indirect trip.
 */
 class Stop {
    private String stationName;

    public Stop() {
        // no‑arg constructor
    }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
}

/**
 * Trip can be direct (no stops) or indirect (one or more stops).
 */
 class Trip {
    private String id;
    private Driver driver;
    private String departureStation;
    private String arrivalStation;
    private int totalSeats;
    private int availableSeats;   // mutable value reflecting remaining seats
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private List<Stop> stops = new ArrayList<>();

    public Trip() {
        // no‑arg constructor
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public String getDepartureStation() { return departureStation; }
    public void setDepartureStation(String departureStation) { this.departureStation = departureStation; }

    public String getArrivalStation() { return arrivalStation; }
    public void setArrivalStation(String arrivalStation) { this.arrivalStation = arrivalStation; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { 
        this.totalSeats = totalSeats; 
        // initialise available seats on creation
        if (this.availableSeats == 0) {
            this.availableSeats = totalSeats;
        }
    }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public LocalDateTime getDepartureDateTime() { return departureDateTime; }
    public void setDepartureDateTime(LocalDateTime departureDateTime) { this.departureDateTime = departureDateTime; }

    public LocalDateTime getArrivalDateTime() { return arrivalDateTime; }
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) { this.arrivalDateTime = arrivalDateTime; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<Stop> getStops() { return stops; }
    public void setStops(List<Stop> stops) { this.stops = stops; }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    public boolean isIndirect() {
        return !stops.isEmpty();
    }
}

/**
 * Booking records a customer's reservation of seats on a trip.
 */
 class Booking {
    private String id;
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingDateTime;

    public Booking() {
        // no‑arg constructor
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Trip getTrip() { return trip; }
    public void setTrip(Trip trip) { this.trip = trip; }

    public int getSeatsBooked() { return seatsBooked; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }

    public LocalDateTime getBookingDateTime() { return bookingDateTime; }
    public void setBookingDateTime(LocalDateTime bookingDateTime) { this.bookingDateTime = bookingDateTime; }
}

/**
 * Service class containing static methods that implement the functional requirements.
 */
 class ORSService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private ORSService() {
        // utility class – prevent instantiation
    }

    /**
     * Validate Booking Eligibility.
     * <p>
     * Checks if a customer can book the requested number of seats on a trip.
     * Conditions:
     * <ul>
     *   <li>The trip must exist and have enough available seats.</li>
     *   <li>The customer must not have another booking that overlaps in time on the same day.</li>
     *   <li>The booking must be made at least 2 hours before the trip departure (strictly > 2 hours).</li>
     * </ul>
     * If all conditions are satisfied, the method updates the trip's available seats and returns {@code true};
     * otherwise it returns {@code false}.
     *
     * @param customer   the customer attempting the booking (must not be {@code null})
     * @param trip       the trip to be booked (must not be {@code null})
     * @param seats      number of seats the customer wants to book (must be &gt; 0)
     * @param bookingTime the date‑time when the booking request is made
     * @return {@code true} if the booking is allowed and the trip's seat count is updated; {@code false} otherwise
     */
    public static boolean validateBookingEligibility(Customer customer, Trip trip, int seats, LocalDateTime bookingTime) {
        Objects.requireNonNull(customer, "Customer must not be null");
        Objects.requireNonNull(trip, "Trip must not be null");
        if (seats <= 0) {
            return false;
        }

        // 1) check seat availability
        if (trip.getAvailableSeats() < seats) {
            return false;
        }

        // 2) booking must be > 2 hours before departure
        Duration diff = Duration.between(bookingTime, trip.getDepartureDateTime());
        if (diff.toMinutes() <= 120) { // 2 hours = 120 minutes, must be strictly greater
            return false;
        }

        // 3) overlapping bookings on same day for the customer
        LocalDateTime newStart = trip.getDepartureDateTime();
        LocalDateTime newEnd = trip.getArrivalDateTime();

        for (Booking existing : customer.getBookings()) {
            LocalDateTime existStart = existing.getTrip().getDepartureDateTime();
            LocalDateTime existEnd = existing.getTrip().getArrivalDateTime();

            // same calendar day?
            if (newStart.toLocalDate().equals(existStart.toLocalDate())) {
                boolean overlap = !(newEnd.isBefore(existStart) || newStart.isAfter(existEnd));
                if (overlap) {
                    return false;
                }
            }
        }

        // all checks passed – update seats and record booking
        trip.setAvailableSeats(trip.getAvailableSeats() - seats);
        return true;
    }

    /**
     * Calculate Discounted Trip Price.
     * <p>
     * Applies a flat 20% discount to the trip price if the customer has a membership
     * and the booking is made at least 24 hours before the trip departure.
     * The final price is rounded to one decimal place.
     *
     * @param booking the booking for which the price is to be calculated (must not be {@code null})
     * @return the discounted price (rounded to 1 decimal) if applicable; otherwise the original price
     */
    public static double calculateDiscountedTripPrice(Booking booking) {
        Objects.requireNonNull(booking, "Booking must not be null");
        Customer customer = booking.getCustomer();
        Trip trip = booking.getTrip();

        // no discount if customer has no membership
        if (customer.getMembership() == null) {
            return roundToOneDecimal(trip.getPrice());
        }

        // check 24‑hour rule
        Duration diff = Duration.between(booking.getBookingDateTime(), trip.getDepartureDateTime());
        if (diff.toHours() >= 24) {
            // discount applies (20% off)
            double discounted = trip.getPrice() * 0.8;
            return roundToOneDecimal(discounted);
        }

        // otherwise return original price
        return roundToOneDecimal(trip.getPrice());
    }

    private static double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    /**
     * Check Stop Overlap for Indirect Trips.
     * <p>
     * Determines whether two indirect trips (each having at least one stop) posted by the same driver
     * share any common stop stations.
     *
     * @param tripA first trip (must be indirect)
     * @param tripB second trip (must be indirect)
     * @return {@code true} if there is at least one stop station present in both trips; {@code false} otherwise
     */
    public static boolean checkStopOverlapForIndirectTrips(Trip tripA, Trip tripB) {
        Objects.requireNonNull(tripA, "First trip must not be null");
        Objects.requireNonNull(tripB, "Second trip must not be null");

        if (!tripA.isIndirect() || !tripB.isIndirect()) {
            return false;
        }

        for (Stop stopA : tripA.getStops()) {
            for (Stop stopB : tripB.getStops()) {
                if (stopA.getStationName().equalsIgnoreCase(stopB.getStationName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Compute Monthly Reward Points.
     * <p>
     * Calculates the total reward points earned by a customer for bookings made
     * within the specified month. The calculation only applies if the customer
     * has a membership that includes points rewards.
     * Each seat booked awards 5 points.
     *
     * @param customer the customer whose points are to be calculated (must not be {@code null})
     * @param month    the target month (e.g., 2023‑05) (must not be {@code null})
     * @return total reward points earned in the month; {@code 0} if the customer has no points reward or no bookings in the month
     */
    public static int computeMonthlyRewardPoints(Customer customer, YearMonth month) {
        Objects.requireNonNull(customer, "Customer must not be null");
        Objects.requireNonNull(month, "Month must not be null");

        Membership membership = customer.getMembership();
        if (membership == null || !membership.isPointsReward()) {
            return 0;
        }

        int totalPoints = 0;
        for (Booking booking : customer.getBookings()) {
            YearMonth bookingMonth = YearMonth.from(booking.getBookingDateTime());
            if (bookingMonth.equals(month)) {
                totalPoints += booking.getSeatsBooked() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Validate Trip Posting Feasibility.
     * <p>
     * Checks whether a driver can post a new trip without time conflicts.
     * Preconditions:
     * <ul>
     *   <li>The driver must exist.</li>
     *   <li>The new trip's departure time must be before its arrival time.</li>
     *   <li>The new trip must not intersect (overlap) with any existing trip of the driver.
     *       Identical time periods are considered a conflict, while adjacent boundaries (end == start) are allowed.</li>
     * </ul>
     *
     * @param driver       the driver attempting to post the trip (must not be {@code null})
     * @param newTrip      the new trip to be posted (must not be {@code null})
     * @return {@code true} if the trip can be posted (no conflicts); {@code false} otherwise
     */
    public static boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        Objects.requireNonNull(driver, "Driver must not be null");
        Objects.requireNonNull(newTrip, "New trip must not be null");

        // departure must be before arrival
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }

        // check for time conflicts with existing trips
        for (Trip existing : driver.getPostedTrips()) {
            LocalDateTime existStart = existing.getDepartureDateTime();
            LocalDateTime existEnd = existing.getArrivalDateTime();

            LocalDateTime newStart = newTrip.getDepartureDateTime();
            LocalDateTime newEnd = newTrip.getArrivalDateTime();

            // Conflict if intervals intersect (excluding the case where end == start)
            boolean intersect = !(newEnd.isBefore(existStart) || newStart.isAfter(existEnd));
            if (intersect) {
                // also need to exclude the exact adjacent case where newEnd == existStart or newStart == existEnd
                if (newEnd.equals(existStart) || newStart.equals(existEnd)) {
                    continue; // adjacent – not a conflict
                }
                return false; // real overlap
            }
        }

        return true;
    }

    /**
     * Helper method to parse a date‑time string in the format "yyyy-MM-dd HH:mm".
     *
     * @param dateTimeStr the string representation of the date‑time
     * @return the corresponding {@link LocalDateTime}
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }
}