import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all users of the system.
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
 * Represents a driver in the rideshare system.
 */
class Driver extends User {
    private List<Trip> trips = new ArrayList<>();

    public Driver() {
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

/**
 * Represents a customer in the rideshare system.
 */
class Customer extends User {
    private Membership membership;
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {
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
}

/**
 * Enum describing the possible reward types.
 */
enum RewardType {
    CASHBACK,
    DISCOUNT,
    POINTS
}

/**
 * Represents a membership package that a customer can hold.
 */
class Membership {
    private Customer customer;
    private RewardType rewardType;
    private boolean active;

    public Membership() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String station;

    public Stop() {
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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
    private int seats; // remaining seats that can be booked
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private List<Stop> stops = new ArrayList<>(); // empty list for direct trips
    private List<Booking> bookings = new ArrayList<>();

    public Trip() {
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
}

/**
 * Represents a booking made by a customer for a trip.
 */
class Booking {
    private String id;
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingDateTime;

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
 * Service class that implements the functional requirements of the Online Rideshare System.
 */
class ORSService {

    /**
     * Validates whether a customer can book a given number of seats on a specific trip.
     * <p>
     * Eligibility criteria:
     * <ul>
     *   <li>The trip must exist and have enough available seats.</li>
     *   <li>The booking must be made at least two hours BEFORE the departure time
     *       (strictly more than 2 hours, not exactly 2).</li>
     *   <li>The customer must not have any other booking on the same day that
     *       overlaps in time with the new trip.</li>
     * </ul>
     * If the booking is eligible, the method updates the number of available seats
     * on the trip and registers the new booking for both the trip and the customer.
     *
     * @param customer      the customer attempting to book
     * @param trip          the trip to be booked
     * @param seatsRequested number of seats the customer wants to book
     * @param bookingTime   the date‑time when the booking is performed
     * @return {@code true} if the booking is allowed and the trip's seat count
     *         has been updated; {@code false} otherwise
     */
    public static boolean validateBookingEligibility(Customer customer, Trip trip,
                                                      int seatsRequested,
                                                      LocalDateTime bookingTime) {
        if (customer == null || trip == null) {
            return false;
        }

        // Check seat availability
        if (trip.getSeats() < seatsRequested) {
            return false;
        }

        // Booking must be made more than 2 hours before departure
        Duration diff = Duration.between(bookingTime, trip.getDepartureDateTime());
        if (diff.toHours() <= 2) {
            return false;
        }

        // Check overlapping bookings on the same day for the same customer
        LocalDate bookingDate = bookingTime.toLocalDate();
        for (Booking existing : customer.getBookings()) {
            LocalDate existingDate = existing.getBookingDateTime().toLocalDate();
            if (!existingDate.equals(bookingDate)) {
                continue;
            }
            LocalDateTime existStart = existing.getTrip().getDepartureDateTime();
            LocalDateTime existEnd = existing.getTrip().getArrivalDateTime();
            LocalDateTime newStart = trip.getDepartureDateTime();
            LocalDateTime newEnd = trip.getArrivalDateTime();

            // Overlap detection (strict, adjacent times are allowed)
            boolean overlap = newStart.isBefore(existEnd) && existStart.isBefore(newEnd);
            if (overlap) {
                return false;
            }
        }

        // All checks passed – update seats and record booking
        trip.setSeats(trip.getSeats() - seatsRequested);

        Booking newBooking = new Booking();
        newBooking.setId("BKG-" + System.nanoTime());
        newBooking.setCustomer(customer);
        newBooking.setTrip(trip);
        newBooking.setSeatsBooked(seatsRequested);
        newBooking.setBookingDateTime(bookingTime);

        // Register booking
        trip.getBookings().add(newBooking);
        customer.getBookings().add(newBooking);

        return true;
    }

    /**
     * Calculates the final price for a booking after applying a discount reward,
     * if the customer is eligible.
     * <p>
     * Discount rules:
     * <ul>
     *   <li>The customer must have an active membership with a DISCOUNT reward.</li>
     *   <li>The booking must be made at least 24 hours before the trip's departure.</li>
     *   <li>If eligible, a flat 20% discount is applied.</li>
     * </ul>
     * The returned price is rounded to one decimal place.
     *
     * @param booking the booking for which the price is to be calculated
     * @return the final price after discount (rounded to one decimal) if applicable,
     *         otherwise the original trip price
     */
    public static double calculateDiscountedTripPrice(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        double originalPrice = booking.getTrip().getPrice();

        Membership membership = booking.getCustomer().getMembership();
        if (membership == null || !membership.isActive()
                || membership.getRewardType() != RewardType.DISCOUNT) {
            return originalPrice;
        }

        // Check the 24‑hour rule
        Duration diff = Duration.between(booking.getBookingDateTime(),
                                         booking.getTrip().getDepartureDateTime());
        if (diff.toHours() < 24) {
            return originalPrice;
        }

        double discounted = originalPrice * 0.80; // 20% off
        // Round to one decimal place
        return Math.round(discounted * 10.0) / 10.0;
    }

    /**
     * Determines whether two indirect trips (i.e., trips that contain at least one stop)
     * share any common stop stations.
     *
     * @param tripA the first trip
     * @param tripB the second trip
     * @return {@code true} if at least one stop station is present in both trips;
     *         {@code false} otherwise (including the case where either trip has no stops)
     */
    public static boolean checkStopOverlapForIndirectTrips(Trip tripA, Trip tripB) {
        if (tripA == null || tripB == null) {
            return false;
        }
        if (tripA.getStops().isEmpty() || tripB.getStops().isEmpty()) {
            return false; // direct trips are not considered indirect
        }

        for (Stop stopA : tripA.getStops()) {
            for (Stop stopB : tripB.getStops()) {
                if (Objects.equals(stopA.getStation(), stopB.getStation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes the total reward points earned by a customer for bookings made
     * within a specific month, assuming the customer holds a POINTS reward.
     *
     * @param customer the customer whose points are to be calculated
     * @param month    the target month (year and month) for which points are summed
     * @return the total points earned (5 points per seat per booking) for the month;
     *         returns 0 if the customer does not have an active POINTS reward
     */
    public static int computeMonthlyRewardPoints(Customer customer, YearMonth month) {
        if (customer == null || month == null) {
            return 0;
        }

        Membership membership = customer.getMembership();
        if (membership == null || !membership.isActive()
                || membership.getRewardType() != RewardType.POINTS) {
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
     * Validates whether a driver can post a new trip without causing time conflicts
     * with any of the driver’s existing trips.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>The driver must be non‑null.</li>
     *   <li>The new trip's departure time must be strictly before its arrival time.</li>
     *   <li>Time periods that intersect (including identical periods) are considered conflicts.
     *       Adjacent periods where one ends exactly when another starts are NOT conflicts.</li>
     * </ul>
     *
     * @param driver        the driver attempting to post the trip
     * @param newTrip       the new trip to be posted
     * @param existingTrips all trips already posted by the driver
     * @return {@code true} if the new trip can be posted without conflicts; {@code false} otherwise
     */
    public static boolean validateTripPostingFeasibility(Driver driver,
                                                          Trip newTrip,
                                                          List<Trip> existingTrips) {
        if (driver == null || newTrip == null) {
            return false;
        }

        // Validate temporal order of the new trip
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }

        LocalDateTime newStart = newTrip.getDepartureDateTime();
        LocalDateTime newEnd = newTrip.getArrivalDateTime();

        for (Trip existing : existingTrips) {
            // Only compare trips of the same driver
            if (!Objects.equals(existing.getDriver(), driver)) {
                continue;
            }
            LocalDateTime existStart = existing.getDepartureDateTime();
            LocalDateTime existEnd = existing.getArrivalDateTime();

            // Detect overlap (strict). Adjacent boundaries are allowed.
            boolean overlap = newStart.isBefore(existEnd) && existStart.isBefore(newEnd);
            boolean adjacent = newEnd.equals(existStart) || newStart.equals(existEnd);
            if (overlap && !adjacent) {
                return false; // conflict found
            }
        }

        return true; // no conflicts
    }
}