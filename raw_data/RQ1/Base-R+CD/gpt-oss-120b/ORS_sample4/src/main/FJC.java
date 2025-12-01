import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/**
 * Abstract base class for all users of the system.
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    public User() {
        // no‑arg constructor
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
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
    private List<Trip> trips;

    public Driver() {
        this.trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Checks whether two indirect trips (having at least one stop) share any common stop stations.
     *
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if there is at least one common stop station, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        for (String s : stops1) {
            if (stops2.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the driver can post a new trip without time conflicts.
     *
     * @param newTrip the trip the driver wants to post
     * @return true if the new trip does not conflict with any existing trips, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        // departure must be before arrival
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }
        // check time conflict with existing trips
        for (Trip existing : trips) {
            if (existing.isTimeConflicting(newTrip.getDepartureDateTime(), newTrip.getArrivalDateTime())) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a customer who can book trips and may have a membership package.
 */
class Customer extends User {
    private MembershipPackage membershipPackage;
    private List<Booking> bookings; // bookings made by this customer

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Calculates the total reward points earned by this customer in a given month.
     *
     * @param currentMonth the month in format {@code yyyy-MM}
     * @return total points earned in that month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int total = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            LocalDateTime bookingDateTime = b.getBookingDateTime();
            String bookingMonth = bookingDateTime.format(monthFormatter);
            if (bookingMonth.equals(currentMonth)) {
                total += b.getNumberOfSeats() * 5; // 5 points per seat
            }
        }
        return total;
    }

    /**
     * Attempts to book a number of seats on a given trip.
     *
     * @param trip           the trip to book
     * @param numberOfSeats  number of seats the customer wants to reserve
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setCustomer(this);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDateTime(LocalDateTime.now());

        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }
        // update trip seat count
        booking.updateTripSeats();
        // add booking to records
        this.bookings.add(booking);
        trip.getBookings().add(booking);
    }
}

/**
 * Represents a trip (direct or indirect) posted by a driver.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate; // only date part
    private String departureTime; // format yyyy-MM-dd HH:mm
    private String arrivalTime;   // format yyyy-MM-dd HH:mm
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // ----- getters & setters -----
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    /**
     * Returns the departure date‑time as {@link LocalDateTime}.
     *
     * @return LocalDateTime representing departure
     */
    public LocalDateTime getDepartureDateTime() {
        return LocalDateTime.parse(this.departureTime, DATE_TIME_FORMATTER);
    }

    /**
     * Returns the arrival date‑time as {@link LocalDateTime}.
     *
     * @return LocalDateTime representing arrival
     */
    public LocalDateTime getArrivalDateTime() {
        return LocalDateTime.parse(this.arrivalTime, DATE_TIME_FORMATTER);
    }

    /**
     * Calculates the final price for a booking after applying a 20% discount if
     * the customer has a discount award and the booking is made at least 24 hours
     * before departure.
     *
     * @param customer       the customer making the booking
     * @param bookingTimeStr the booking time in format {@code yyyy-MM-dd HH:mm}
     * @return discounted price (rounded to one decimal) or original price if not eligible
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTimeStr) {
        LocalDateTime bookingTime = LocalDateTime.parse(bookingTimeStr, DATE_TIME_FORMATTER);
        LocalDateTime departure = getDepartureDateTime();

        // check eligibility
        boolean hasDiscountAward = customer.getMembershipPackage() != null
                && customer.getMembershipPackage().hasAward(Award.DISCOUNTS);
        Duration diff = Duration.between(bookingTime, departure);
        boolean enoughAdvance = diff.toHours() >= 24;

        if (hasDiscountAward && enoughAdvance) {
            double discounted = price * 0.8; // 20% off
            return Math.round(discounted * 10.0) / 10.0; // keep 1 decimal place
        }
        return price;
    }

    /**
     * Calculates total reward points a customer would obtain for this trip in a given month.
     * (Helper used by {@link Customer#computeMonthlyRewardPoints(String)}.)
     *
     * @param customer    the customer
     * @param currentMonth month in format {@code yyyy-MM}
     * @return total points earned for this trip in the month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer.getMembershipPackage() == null
                || !customer.getMembershipPackage().hasAward(Award.POINTS)) {
            return 0;
        }
        int total = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            if (b.getCustomer().equals(customer)) {
                String bookingMonth = b.getBookingDateTime().format(monthFormatter);
                if (bookingMonth.equals(currentMonth)) {
                    total += b.getNumberOfSeats() * 5;
                }
            }
        }
        return total;
    }

    /**
     * Attempts to reserve a number of seats on this trip.
     *
     * @param num the number of seats to reserve
     * @return true if reservation succeeded, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0 || num > numberOfSeats) {
            return false;
        }
        numberOfSeats -= num;
        return true;
    }

    /**
     * Alias for {@link #bookSeats(int)} used by booking confirmation logic.
     *
     * @param seats number of seats to confirm
     * @return true if seats were successfully confirmed
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Returns a set containing all stop station names of this trip.
     *
     * @return Set of stop station strings (empty if no stops)
     */
    public Set<String> getStopStations() {
        Set<String> set = new HashSet<>();
        for (Stop s : stops) {
            set.add(s.getStopStation());
        }
        return set;
    }

    /**
     * Determines whether a given time interval conflicts with this trip's schedule.
     * Adjacent intervals (end == start) are NOT considered a conflict.
     *
     * @param newDeparture the departure time of the new interval
     * @param newArrival   the arrival time of the new interval
     * @return true if the intervals intersect, false otherwise
     */
    public boolean isTimeConflicting(LocalDateTime newDeparture, LocalDateTime newArrival) {
        LocalDateTime existingDep = getDepartureDateTime();
        LocalDateTime existingArr = getArrivalDateTime();

        // no conflict if newArr <= existingDep or newDep >= existingArr
        if (!newArrival.isAfter(existingDep) || !newDeparture.isBefore(existingArr)) {
            return false;
        }
        return true; // intervals overlap
    }
}

/**
 * Represents a stop (intermediate station) on an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
        // no‑arg constructor
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a booking made by a customer for a particular trip.
 */
class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private LocalDateTime bookingDateTime; // includes date and time

    public Booking() {
        // no‑arg constructor
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    /**
     * Validates whether this booking satisfies all eligibility rules:
     * <ul>
     *   <li>Trip exists and has enough available seats.</li>
     *   <li>No overlapping bookings for the same customer on the same day.</li>
     *   <li>Booking is made more than 2 hours before the trip's departure (strictly > 2h).</li>
     * </ul>
     *
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }

        // 1. enough seats
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // 2. time before departure (> 2 hours)
        LocalDateTime departure = trip.getDepartureDateTime();
        Duration diff = Duration.between(bookingDateTime, departure);
        if (diff.toHours() <= 2) { // not strictly greater than 2 hours
            return false;
        }

        // 3. overlapping bookings for same customer on same day
        for (Booking other : customer.getBookings()) {
            if (other == this) {
                continue;
            }
            // same calendar day?
            if (other.getBookingDateTime().toLocalDate().equals(this.bookingDateTime.toLocalDate())) {
                // check time overlap between trips
                if (this.overlapsWith(other.getTrip())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Reduces the number of available seats on the associated trip according to this booking.
     * Should be called after a successful eligibility check.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Determines whether the time interval of this booking's trip overlaps with another trip.
     *
     * @param otherTrip the other trip to compare against
     * @return true if the trips' time intervals intersect, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (otherTrip == null) {
            return false;
        }
        LocalDateTime thisDep = this.trip.getDepartureDateTime();
        LocalDateTime thisArr = this.trip.getArrivalDateTime();

        LocalDateTime otherDep = otherTrip.getDepartureDateTime();
        LocalDateTime otherArr = otherTrip.getArrivalDateTime();

        // overlap if intervals intersect (excluding adjacency)
        if (!thisArr.isAfter(otherDep) || !thisDep.isBefore(otherArr)) {
            return false;
        }
        return true;
    }
}

/**
 * Represents a membership package that may contain several awards.
 */
class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {
        // no‑arg constructor
    }

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Checks whether this package includes a specific award.
     *
     * @param award the award to look for
     * @return true if the award exists in the package, false otherwise
     */
    public boolean hasAward(Award award) {
        if (awards == null) {
            return false;
        }
        for (Award a : awards) {
            if (a == award) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Enumeration of possible award types a membership package can contain.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}