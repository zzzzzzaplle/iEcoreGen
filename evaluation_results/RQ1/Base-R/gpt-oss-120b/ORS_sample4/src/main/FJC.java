import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Base class for all users of the Online Rideshare System.
 */
class User {
    private String id;
    private String email;
    private String phoneNumber;

    public User() {
    }

    public User(String id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /* Getters and Setters */
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
 * Represents a driver who can post trips.
 */
class Driver extends User {
    private List<Trip> postedTrips;

    public Driver() {
        this.postedTrips = new ArrayList<>();
    }

    public Driver(String id, String email, String phoneNumber) {
        super(id, email, phoneNumber);
        this.postedTrips = new ArrayList<>();
    }

    public List<Trip> getPostedTrips() {
        return postedTrips;
    }

    public void setPostedTrips(List<Trip> postedTrips) {
        this.postedTrips = postedTrips;
    }

    public void addTrip(Trip trip) {
        this.postedTrips.add(trip);
    }
}

/**
 * Represents a customer who can book seats and may have a membership.
 */
class Customer extends User {
    private Membership membership;
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public Customer(String id, String email, String phoneNumber) {
        super(id, email, phoneNumber);
        this.bookings = new ArrayList<>();
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

/**
 * Enum representing the possible reward types a membership can hold.
 */
enum RewardType {
    CASHBACK,
    DISCOUNT,
    POINTS
}

/**
 * Represents a membership package that may contain a reward.
 */
class Membership {
    private boolean active;
    private RewardType rewardType;

    public Membership() {
    }

    public Membership(boolean active, RewardType rewardType) {
        this.active = active;
        this.rewardType = rewardType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }
}

/**
 * Abstract base class for a trip.
 */
abstract class Trip {
    private String tripId;
    private Driver driver;
    private String departureStation;
    private String arrivalStation;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price; // price per seat

    public Trip() {
    }

    public Trip(String tripId, Driver driver, String departureStation, String arrivalStation,
                int totalSeats, LocalDateTime departureDateTime,
                LocalDateTime arrivalDateTime, double price) {
        this.tripId = tripId;
        this.driver = driver;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // initially all seats are available
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
    }

    /* Getters and Setters */
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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
}

/**
 * Represents a direct (non‑stop) trip.
 */
class DirectTrip extends Trip {
    public DirectTrip() {
        super();
    }

    public DirectTrip(String tripId, Driver driver, String departureStation, String arrivalStation,
                      int totalSeats, LocalDateTime departureDateTime,
                      LocalDateTime arrivalDateTime, double price) {
        super(tripId, driver, departureStation, arrivalStation, totalSeats,
                departureDateTime, arrivalDateTime, price);
    }
}

/**
 * Represents an indirect trip that includes one or more stops.
 */
class IndirectTrip extends Trip {
    private List<Stop> stops;

    public IndirectTrip() {
        this.stops = new ArrayList<>();
    }

    public IndirectTrip(String tripId, Driver driver, String departureStation, String arrivalStation,
                        int totalSeats, LocalDateTime departureDateTime,
                        LocalDateTime arrivalDateTime, double price,
                        List<Stop> stops) {
        super(tripId, driver, departureStation, arrivalStation, totalSeats,
                departureDateTime, arrivalDateTime, price);
        this.stops = stops != null ? stops : new ArrayList<>();
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
    }

    public Stop(String stopStation) {
        this.stopStation = stopStation;
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a booking made by a customer for a specific trip.
 */
class Booking {
    private String bookingId;
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingDateTime; // time when the booking was made

    public Booking() {
    }

    public Booking(String bookingId, Customer customer, Trip trip, int seatsBooked,
                   LocalDateTime bookingDateTime) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.trip = trip;
        this.seatsBooked = seatsBooked;
        this.bookingDateTime = bookingDateTime;
    }

    /* Getters and Setters */
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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
 * Service class that implements the functional requirements of the system.
 */
class ORSService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Checks whether a customer can book a given number of seats on a trip.
     * <p>
     * Eligibility criteria:
     * <ul>
     *   <li>The trip must exist and have enough available seats.</li>
     *   <li>The booking must be made at least 2 hours before the trip's departure
     *       (exactly 2 hours is considered valid).</li>
     *   <li>The customer must not have another booking on the same day that
     *       overlaps in time with the new trip.</li>
     * </ul>
     * If the booking is possible, the method updates the trip's available seats.
     *
     * @param customer    the customer attempting the booking
     * @param trip        the trip to be booked
     * @param seatsNeeded number of seats the customer wants to book
     * @param bookingTime the moment when the booking is attempted
     * @return {@code true} if the booking can be performed; {@code false} otherwise
     */
    public static boolean validateBookingEligibility(Customer customer, Trip trip,
                                                      int seatsNeeded,
                                                      LocalDateTime bookingTime) {
        // Trip existence and seat availability
        if (trip == null || trip.getAvailableSeats() < seatsNeeded) {
            return false;
        }

        // Booking must be at least 2 hours before departure (including exactly 2 hours)
        Duration diff = Duration.between(bookingTime, trip.getDepartureDateTime());
        if (diff.toHours() < 2) {
            return false;
        }

        // No overlapping bookings on the same day for the same customer
        LocalDateTime newTripStart = trip.getDepartureDateTime();
        LocalDateTime newTripEnd = trip.getArrivalDateTime();
        for (Booking existing : customer.getBookings()) {
            LocalDateTime existingStart = existing.getTrip().getDepartureDateTime();
            LocalDateTime existingEnd = existing.getTrip().getArrivalDateTime();

            // same day check (date part of departure)
            if (existingStart.toLocalDate().equals(newTripStart.toLocalDate())) {
                boolean overlap = !(existingEnd.isBefore(newTripStart) || existingStart.isAfter(newTripEnd));
                if (overlap) {
                    return false;
                }
            }
        }

        // All checks passed – update available seats and return true
        trip.setAvailableSeats(trip.getAvailableSeats() - seatsNeeded);
        return true;
    }

    /**
     * Calculates the final price for a booking after applying the discount reward.
     * <p>
     * A discount of 20% is applied only when:
     * <ul>
     *   <li>The customer holds an active membership with a DISCOUNT reward.</li>
     *   <li>The booking is made at least 24 hours before the trip's departure.</li>
     * </ul>
     * The result is rounded to one decimal place.
     *
     * @param booking the booking for which the price is to be calculated
     * @return the discounted price if applicable; otherwise the original price
     */
    public static double calculateDiscountedTripPrice(Booking booking) {
        Trip trip = booking.getTrip();
        Customer customer = booking.getCustomer();

        double originalPrice = trip.getPrice() * booking.getSeatsBooked();

        // Preconditions for discount
        if (customer.getMembership() != null && customer.getMembership().isActive()
                && customer.getMembership().getRewardType() == RewardType.DISCOUNT) {

            Duration duration = Duration.between(booking.getBookingDateTime(),
                    trip.getDepartureDateTime());

            if (duration.toHours() >= 24) {
                double discounted = originalPrice * 0.8; // 20% off
                // round to one decimal place
                return Math.round(discounted * 10.0) / 10.0;
            }
        }
        return originalPrice;
    }

    /**
     * Determines whether two indirect trips (each having at least one stop) share any common stop stations.
     *
     * @param t1 the first indirect trip
     * @param t2 the second indirect trip
     * @return {@code true} if at least one stop station is shared; {@code false} otherwise
     */
    public static boolean checkStopOverlapForIndirectTrips(IndirectTrip t1, IndirectTrip t2) {
        if (t1 == null || t2 == null) {
            return false;
        }

        for (Stop s1 : t1.getStops()) {
            for (Stop s2 : t2.getStops()) {
                if (Objects.equals(s1.getStopStation(), s2.getStopStation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes the total reward points earned by a customer for bookings made within a specific month.
     * <p>
     * The calculation is performed only if the customer holds an active membership with a POINTS reward.
     * Each booked seat contributes 5 points.
     *
     * @param customer the customer whose points are to be calculated
     * @param month    the target month (e.g., {@code YearMonth.of(2025, 11)})
     * @return total reward points earned in the given month
     */
    public static int computeMonthlyRewardPoints(Customer customer, YearMonth month) {
        if (customer.getMembership() == null || !customer.getMembership().isActive()
                || customer.getMembership().getRewardType() != RewardType.POINTS) {
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
     * Validates whether a driver can post a new trip without time conflicts with his/her existing trips.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>The driver must be non‑null and valid.</li>
     *   <li>The new trip's departure time must be before its arrival time.</li>
     * </ul>
     * A conflict exists when the time interval of the new trip overlaps (intersects) with any existing
     * trip of the driver. Identical intervals are also conflicts. Adjacent intervals (where the end
     * of one equals the start of the other) are allowed.
     *
     * @param driver       the driver who wants to post the trip
     * @param newTrip      the new trip to be posted
     * @return {@code true} if the trip can be posted (no conflicts); {@code false} otherwise
     */
    public static boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        if (driver == null || newTrip == null) {
            return false;
        }

        // departure must be before arrival
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }

        LocalDateTime newStart = newTrip.getDepartureDateTime();
        LocalDateTime newEnd = newTrip.getArrivalDateTime();

        for (Trip existing : driver.getPostedTrips()) {
            LocalDateTime existStart = existing.getDepartureDateTime();
            LocalDateTime existEnd = existing.getArrivalDateTime();

            // Check for overlap (excluding adjacent boundaries)
            boolean overlap = !(newEnd.isEqual(existStart) || newEnd.isBefore(existStart)
                    || newStart.isEqual(existEnd) || newStart.isAfter(existEnd));

            if (overlap) {
                return false; // conflict found
            }
        }
        return true; // no conflicts
    }
}

/**
 * Utility class to parse date‑time strings used throughout the system.
 */
class DateTimeUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    /**
     * Parses a string in the format {@code yyyy-MM-dd HH:mm} into a {@link LocalDateTime}.
     *
     * @param dateTimeStr the date‑time string
     * @return the parsed {@link LocalDateTime}
     * @throws java.time.format.DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

    /**
     * Formats a {@link LocalDateTime} into the standard {@code yyyy-MM-dd HH:mm} string.
     *
     * @param dateTime the date‑time to format
     * @return formatted string
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}