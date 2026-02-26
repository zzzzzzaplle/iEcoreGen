import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the rideshare system.
 */
class User {
    private String id;
    private String email;
    private String phoneNumber;

    public User() {
    }

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
}

/**
 * Represents a driver – a special kind of user.
 */
class Driver extends User {
    public Driver() {
        super();
    }
}

/**
 * Types of rewards a membership can provide.
 */
enum RewardType {
    CASHBACK, DISCOUNT, POINTS
}

/**
 * Membership information for a customer.
 */
class Membership {
    private RewardType rewardType;
    private boolean active; // true if the membership is valid

    public Membership() {
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

/**
 * Represents a customer – a user that can book trips.
 */
class Customer extends User {
    private Membership membership;

    public Customer() {
        super();
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

/**
 * Simple POJO for a stop (used in indirect trips).
 */
class Stop {
    private String stationName;

    public Stop() {
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}

/**
 * Represents a trip posted by a driver.
 */
class Trip {
    private String id;
    private Driver driver;
    private String departureStation;
    private String arrivalStation;
    private int availableSeats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double pricePerSeat; // price for a single seat
    private List<Stop> stops; // empty list for direct trips

    public Trip() {
        stops = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
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

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}

/**
 * Represents a booking made by a customer.
 */
class Booking {
    private String id;
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingDateTime; // when the booking was made

    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }
}

/**
 * Core service class that stores data and implements the functional requirements.
 */
class RideShareSystem {

    private List<User> users;
    private List<Trip> trips;
    private List<Booking> bookings;

    public RideShareSystem() {
        users = new ArrayList<>();
        trips = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    /* ---------------------------------------------------------------------- */
    /* -------------------------- Data Management --------------------------- */
    /* ---------------------------------------------------------------------- */

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
     * Adds a user to the system.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Adds a trip to the system.
     *
     * @param trip the trip to add
     */
    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    /**
     * Adds a booking to the system.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /* ---------------------------------------------------------------------- */
    /* ---------------------- Functional Requirement 1 ---------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Validates whether a customer can book a given number of seats on a trip.
     * <p>
     * Eligibility checks:
     * <ul>
     *   <li>The trip must exist and have enough available seats.</li>
     *   <li>The booking must be made at least 2 hours before the trip departure
     *       (strictly earlier, not exactly 2 hours).</li>
     *   <li>The customer must not have another booking on the same day whose
     *       time interval overlaps with the new trip.</li>
     * </ul>
     * If the booking is eligible, the method reduces the trip's available seats
     * by the requested amount and returns {@code true}.
     *
     * @param customer       the customer attempting the booking
     * @param trip           the trip to be booked
     * @param seatsRequested number of seats the customer wants to reserve
     * @param bookingTime    the moment when the booking is attempted
     * @return {@code true} if the booking is allowed and the trip's seat count
     *         is updated; {@code false} otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip,
                                               int seatsRequested,
                                               LocalDateTime bookingTime) {
        // Trip existence and seat availability
        if (trip == null || seatsRequested <= 0) {
            return false;
        }
        if (trip.getAvailableSeats() < seatsRequested) {
            return false;
        }

        // Booking must be made more than 2 hours before departure
        LocalDateTime latestAllowed = trip.getDepartureDateTime().minusHours(2);
        if (!bookingTime.isBefore(latestAllowed)) { // includes exactly 2h case
            return false;
        }

        // Overlap check with customer's existing bookings on the same day
        for (Booking b : bookings) {
            if (!b.getCustomer().getId().equals(customer.getId())) {
                continue;
            }
            Trip existingTrip = b.getTrip();
            // Same calendar day (departure date) as the new trip?
            if (existingTrip.getDepartureDateTime().toLocalDate()
                    .equals(trip.getDepartureDateTime().toLocalDate())) {
                if (intervalsOverlap(
                        existingTrip.getDepartureDateTime(),
                        existingTrip.getArrivalDateTime(),
                        trip.getDepartureDateTime(),
                        trip.getArrivalDateTime())) {
                    return false; // overlapping time period on the same day
                }
            }
        }

        // All checks passed – update available seats and accept booking
        trip.setAvailableSeats(trip.getAvailableSeats() - seatsRequested);
        return true;
    }

    /**
     * Helper method to determine whether two time intervals intersect.
     *
     * @param start1 start of first interval (inclusive)
     * @param end1   end of first interval (exclusive)
     * @param start2 start of second interval (inclusive)
     * @param end2   end of second interval (exclusive)
     * @return {@code true} if the intervals overlap, {@code false} otherwise
     */
    private boolean intervalsOverlap(LocalDateTime start1, LocalDateTime end1,
                                     LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    /* ---------------------------------------------------------------------- */
    /* ---------------------- Functional Requirement 2 ---------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Calculates the final price for a booking after applying a 20% discount
     * when the customer holds a membership that provides a discount reward and
     * the booking is made at least 24 hours before the trip's departure.
     *
     * @param booking the booking for which the price is to be calculated
     * @return the discounted price rounded to one decimal place if the
     *         conditions are satisfied; otherwise the original total price
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        Trip trip = booking.getTrip();
        Customer customer = booking.getCustomer();
        double originalTotal = trip.getPricePerSeat() * booking.getSeatsBooked();

        // Preconditions for discount
        if (customer.getMembership() != null
                && customer.getMembership().isActive()
                && customer.getMembership().getRewardType() == RewardType.DISCOUNT) {

            LocalDateTime bookingTime = booking.getBookingDateTime();
            LocalDateTime departure = trip.getDepartureDateTime();

            if (!bookingTime.isBefore(departure.minusHours(24))) {
                // booking is less than 24h before departure – no discount
                return originalTotal;
            }

            double discounted = originalTotal * 0.80; // 20% off
            // round to one decimal place
            return Math.round(discounted * 10.0) / 10.0;
        }

        return originalTotal;
    }

    /* ---------------------------------------------------------------------- */
    /* ---------------------- Functional Requirement 3 ---------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Checks whether two indirect trips (i.e., trips that contain at least one
     * stop) share any common stop stations.
     *
     * @param trip1 first trip to compare
     * @param trip2 second trip to compare
     * @return {@code true} if there is at least one stop station appearing in
     *         both trips; {@code false} otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        if (trip1.getStops() == null || trip2.getStops() == null) {
            return false;
        }
        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
            return false; // at least one stop required for an indirect trip
        }

        for (Stop s1 : trip1.getStops()) {
            for (Stop s2 : trip2.getStops()) {
                if (Objects.equals(s1.getStationName(), s2.getStationName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /* ---------------------------------------------------------------------- */
    /* ---------------------- Functional Requirement 4 ---------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Computes the total reward points earned by a customer in a specific month.
     * The calculation only applies when the customer possesses an active
     * membership with a POINTS reward type. For each eligible booking,
     * 5 points are awarded per seat booked.
     *
     * @param customer the customer whose points are to be calculated
     * @param month    the target month (e.g., 2023-05)
     * @return total reward points earned in the given month
     */
    public int computeMonthlyRewardPoints(Customer customer, YearMonth month) {
        if (customer == null
                || customer.getMembership() == null
                || !customer.getMembership().isActive()
                || customer.getMembership().getRewardType() != RewardType.POINTS) {
            return 0;
        }

        int totalPoints = 0;
        for (Booking b : bookings) {
            if (!b.getCustomer().getId().equals(customer.getId())) {
                continue;
            }
            YearMonth bookingMonth = YearMonth.from(b.getBookingDateTime());
            if (bookingMonth.equals(month)) {
                totalPoints += b.getSeatsBooked() * 5;
            }
        }
        return totalPoints;
    }

    /* ---------------------------------------------------------------------- */
    /* ---------------------- Functional Requirement 5 ---------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Validates whether a driver can post a new trip without causing a time
     * conflict with his/her existing trips.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>The driver must exist and be registered in the system.</li>
     *   <li>The new trip's departure time must be strictly before its arrival
     *       time.</li>
     *   <li>The new trip must not intersect (overlap) with any existing trip of
     *       the same driver. Identical periods are considered a conflict,
     *       while adjacent periods (end == start) are allowed.</li>
     * </ul>
     *
     * @param driver   the driver who wants to post the trip
     * @param newTrip  the trip to be posted
     * @return {@code true} if the trip can be posted (no conflicts); {@code false}
     *         otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        // Driver existence check
        if (driver == null || !users.contains(driver)) {
            return false;
        }

        // Time validity check
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }

        // Conflict detection with existing trips of the same driver
        for (Trip existing : trips) {
            if (!existing.getDriver().getId().equals(driver.getId())) {
                continue;
            }

            // Two intervals intersect if startA < endB && startB < endA
            // Adjacent boundaries (end == start) are NOT a conflict.
            LocalDateTime startA = existing.getDepartureDateTime();
            LocalDateTime endA = existing.getArrivalDateTime();
            LocalDateTime startB = newTrip.getDepartureDateTime();
            LocalDateTime endB = newTrip.getArrivalDateTime();

            boolean overlap = startA.isBefore(endB) && startB.isBefore(endA);
            if (overlap) {
                return false; // conflict found
            }
        }

        // No conflicts detected
        return true;
    }

    /* ---------------------------------------------------------------------- */
    /* ----------------------------- Utilities ------------------------------ */
    /* ---------------------------------------------------------------------- */

    /**
     * Parses a date‑time string with pattern "yyyy-MM-dd HH:mm".
     *
     * @param dateTimeStr the string to parse
     * @return the corresponding {@link LocalDateTime}
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}