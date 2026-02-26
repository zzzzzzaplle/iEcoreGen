import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for all users of the system.
 */
abstract class User {
    private String id;
    private String email;
    private String phoneNumber;

    public User() {
        // no‑arg constructor
    }

    public User(String id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
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
    private List<Trip> postedTrips = new ArrayList<>();

    public Driver() {
        // no‑arg constructor
    }

    public Driver(String id, String email, String phoneNumber) {
        super(id, email, phoneNumber);
    }

    public List<Trip> getPostedTrips() {
        return postedTrips;
    }

    public void setPostedTrips(List<Trip> postedTrips) {
        this.postedTrips = postedTrips;
    }

    public void addTrip(Trip trip) {
        if (trip != null) {
            postedTrips.add(trip);
        }
    }

    /**
     * Validates if a new trip can be posted by this driver without time conflicts.
     *
     * @param newTrip the trip the driver wants to post
     * @return {@code true} if the trip can be posted, {@code false} otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        // departure must be before arrival
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }
        // check conflict with existing trips
        for (Trip existing : postedTrips) {
            if (timeIntervalsConflict(existing.getDepartureDateTime(), existing.getArrivalDateTime(),
                    newTrip.getDepartureDateTime(), newTrip.getArrivalDateTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether two time intervals conflict.
     * Identical intervals conflict; adjacent intervals (end == start) do NOT conflict.
     *
     * @param start1 start of first interval
     * @param end1   end of first interval
     * @param start2 start of second interval
     * @param end2   end of second interval
     * @return {@code true} if intervals intersect (including identical), {@code false} otherwise
     */
    private boolean timeIntervalsConflict(LocalDateTime start1, LocalDateTime end1,
                                          LocalDateTime start2, LocalDateTime end2) {
        // intervals intersect if start1 < end2 && start2 < end1
        // this excludes the case where end1 == start2 or end2 == start1 (adjacent)
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}

/**
 * Represents a customer who can book trips and may have a membership.
 */
class Customer extends User {
    private Membership membership;
    private List<Booking> bookings = new ArrayList<>();

    public Customer() {
        // no‑arg constructor
    }

    public Customer(String id, String email, String phoneNumber) {
        super(id, email, phoneNumber);
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
        if (booking != null) {
            bookings.add(booking);
        }
    }

    /**
     * Checks if the customer can book the requested number of seats on a trip at a given booking time.
     * <p>
     * Eligibility criteria:
     * <ul>
     *   <li>Trip exists and has enough available seats.</li>
     *   <li>No overlapping time periods with the customer's existing bookings on the same day.</li>
     *   <li>Booking is made strictly more than 2 hours before the trip's departure.</li>
     * </ul>
     * If eligible, the method updates the trip's available seats and registers the booking.
     *
     * @param trip          the trip to be booked
     * @param seatsRequested number of seats the customer wants to book
     * @param bookingTime   the time at which the booking is attempted
     * @return {@code true} if booking is possible and performed, {@code false} otherwise
     */
    public boolean validateBookingEligibility(Trip trip, int seatsRequested, LocalDateTime bookingTime) {
        if (trip == null || seatsRequested <= 0 || bookingTime == null) {
            return false;
        }

        // 1. Check seat availability
        if (trip.getAvailableSeats() < seatsRequested) {
            return false;
        }

        // 2. Booking must be more than 2 hours before departure
        Duration diff = Duration.between(bookingTime, trip.getDepartureDateTime());
        if (diff.toHours() <= 2) { // excludes exactly 2 hours
            return false;
        }

        // 3. No overlapping bookings on the same day
        LocalDate targetDay = trip.getDepartureDateTime().toLocalDate();
        for (Booking existing : bookings) {
            Trip existingTrip = existing.getTrip();
            if (existingTrip == null) {
                continue;
            }
            if (!existingTrip.getDepartureDateTime().toLocalDate().equals(targetDay)) {
                continue;
            }
            if (timeIntervalsOverlap(existingTrip.getDepartureDateTime(),
                    existingTrip.getArrivalDateTime(),
                    trip.getDepartureDateTime(),
                    trip.getArrivalDateTime())) {
                return false;
            }
        }

        // All checks passed – perform booking
        trip.reduceAvailableSeats(seatsRequested);
        Booking newBooking = new Booking(this, trip, seatsRequested, bookingTime);
        this.addBooking(newBooking);
        trip.addBooking(newBooking);
        return true;
    }

    /**
     * Determines whether two time intervals overlap.
     *
     * @param start1 start of first interval
     * @param end1   end of first interval
     * @param start2 start of second interval
     * @param end2   end of second interval
     * @return {@code true} if intervals intersect (including identical), {@code false} otherwise
     */
    private boolean timeIntervalsOverlap(LocalDateTime start1, LocalDateTime end1,
                                          LocalDateTime start2, LocalDateTime end2) {
        // Overlap exists if start1 < end2 && start2 < end1
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    /**
     * Calculates the total reward points earned by the customer for bookings made within a specific month.
     * <p>
     * Preconditions: The customer must have a membership with a {@link RewardType#POINTS} reward.
     *
     * @param month the target month (e.g., 2025‑11)
     * @return total reward points earned in the month
     */
    public int computeMonthlyRewardPoints(YearMonth month) {
        if (membership == null || membership.getRewardType() != RewardType.POINTS) {
            return 0;
        }
        int totalPoints = 0;
        for (Booking b : bookings) {
            LocalDate bookingDate = b.getBookingDateTime().toLocalDate();
            if (YearMonth.from(bookingDate).equals(month)) {
                totalPoints += b.getSeatsBooked() * 5;
            }
        }
        return totalPoints;
    }
}

/**
 * Represents a membership package that may include a reward.
 */
class Membership {
    private boolean active;
    private RewardType rewardType; // CASHBACK, DISCOUNT, POINTS

    public Membership() {
        // no‑arg constructor
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
 * Types of rewards that a membership can provide.
 */
enum RewardType {
    CASHBACK,
    DISCOUNT,
    POINTS
}

/**
 * Abstract class representing a trip (direct or indirect).
 */
abstract class Trip {
    private String departureStation;
    private String arrivalStation;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private Driver driver;
    private List<Booking> bookings = new ArrayList<>();

    public Trip() {
        // no‑arg constructor
    }

    public Trip(String departureStation, String arrivalStation, int totalSeats,
                LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                double price, Driver driver) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
        this.driver = driver;
    }

    // Getters and setters
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
        // keep availableSeats consistent if totalSeats is changed
        if (this.availableSeats > totalSeats) {
            this.availableSeats = totalSeats;
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = Math.max(0, Math.min(availableSeats, totalSeats));
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking != null) {
            bookings.add(booking);
        }
    }

    /**
     * Reduces the number of available seats on the trip.
     *
     * @param seats number of seats to deduct
     */
    public void reduceAvailableSeats(int seats) {
        if (seats > 0 && seats <= availableSeats) {
            availableSeats -= seats;
        }
    }
}

/**
 * Represents a direct trip (no intermediate stops).
 */
class DirectTrip extends Trip {
    public DirectTrip() {
        // no‑arg constructor
    }

    public DirectTrip(String departureStation, String arrivalStation, int totalSeats,
                      LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                      double price, Driver driver) {
        super(departureStation, arrivalStation, totalSeats, departureDateTime, arrivalDateTime, price, driver);
    }
}

/**
 * Represents an indirect trip that contains one or more stops.
 */
class IndirectTrip extends Trip {
    private List<Stop> stops = new ArrayList<>();

    public IndirectTrip() {
        // no‑arg constructor
    }

    public IndirectTrip(String departureStation, String arrivalStation, int totalSeats,
                        LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                        double price, Driver driver, List<Stop> stops) {
        super(departureStation, arrivalStation, totalSeats, departureDateTime, arrivalDateTime, price, driver);
        if (stops != null) {
            this.stops = stops;
        }
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void addStop(Stop stop) {
        if (stop != null) {
            stops.add(stop);
        }
    }

    /**
     * Checks whether two indirect trips share at least one common stop station.
     *
     * @param t1 first indirect trip
     * @param t2 second indirect trip
     * @return {@code true} if any stop station is shared, {@code false} otherwise
     */
    public static boolean haveCommonStops(IndirectTrip t1, IndirectTrip t2) {
        if (t1 == null || t2 == null) {
            return false;
        }
        for (Stop s1 : t1.getStops()) {
            for (Stop s2 : t2.getStops()) {
                if (Objects.equals(s1.getStation(), s2.getStation())) {
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Represents a stop within an indirect trip.
 */
class Stop {
    private String station;

    public Stop() {
        // no‑arg constructor
    }

    public Stop(String station) {
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}

/**
 * Represents a booking made by a customer for a specific trip.
 */
class Booking {
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingDateTime;

    public Booking() {
        // no‑arg constructor
    }

    public Booking(Customer customer, Trip trip, int seatsBooked, LocalDateTime bookingDateTime) {
        this.customer = customer;
        this.trip = trip;
        this.seatsBooked = seatsBooked;
        this.bookingDateTime = bookingDateTime;
    }

    // Getters and setters
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

    /**
     * Computes the final price for this booking after applying a 20% discount if applicable.
     * <p>
     * Discount conditions:
     * <ul>
     *   <li>The customer must have an active membership with a {@link RewardType#DISCOUNT} reward.</li>
     *   <li>The booking must be made at least 24 hours before the trip's departure.</li>
     * </ul>
     * The final price is rounded to one decimal place.
     *
     * @return the final price to be paid for the booking
     */
    public double calculateFinalPrice() {
        double basePrice = trip.getPrice() * seatsBooked;

        // Check discount eligibility
        Membership mem = customer.getMembership();
        if (mem != null && mem.isActive() && mem.getRewardType() == RewardType.DISCOUNT) {
            Duration diff = Duration.between(bookingDateTime, trip.getDepartureDateTime());
            if (diff.toHours() >= 24) {
                double discounted = basePrice * 0.8; // 20% off
                return Math.round(discounted * 10.0) / 10.0;
            }
        }
        // No discount applies
        return Math.round(basePrice * 10.0) / 10.0;
    }
}