import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Abstract base class for all users of the Online Rideshare System.
 */
abstract class User {
    /** Unique identifier of the user. */
    private String id;
    /** Email address of the user. */
    private String email;
    /** Phone number of the user. */
    private String phoneNumber;

    /** No‑argument constructor. */
    public User() {
    }

    // -------------------- Getters & Setters --------------------
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
    /** Trips posted by this driver. */
    private List<Trip> trips = new ArrayList<>();

    /** No‑argument constructor. */
    public Driver() {
    }

    // -------------------- Getters & Setters --------------------
    public List<Trip> getTrips() {
        return trips;
    }
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Checks whether two indirect trips posted by the same driver share any common stop stations.
     *
     * @param trip1 the first trip (must have at least one stop)
     * @param trip2 the second trip (must have at least one stop)
     * @return {@code true} if any stop station is shared, {@code false} otherwise
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
     * Determines whether the driver can post a new trip without time conflicts.
     *
     * @param newTrip the trip the driver wishes to post
     * @return {@code true} if the trip can be posted, {@code false} otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        // Validate time order
        if (!newTrip.isDepartureBeforeArrival()) {
            return false;
        }
        // Check for time conflicts with existing trips
        for (Trip existing : trips) {
            if (existing.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a new trip to the driver's list after validation.
     *
     * @param newTrip the trip to add
     * @return {@code true} if the trip was added, {@code false} otherwise
     */
    public boolean postTrip(Trip newTrip) {
        if (canPostTrip(newTrip)) {
            trips.add(newTrip);
            return true;
        }
        return false;
    }
}

/**
 * Represents a customer who can book trips and may have a membership package.
 */
class Customer extends User {
    /** Membership package (may be {@code null}). */
    private MembershipPackage membershipPackage;
    /** All bookings made by this customer. */
    private List<Booking> bookings = new ArrayList<>();

    /** No‑argument constructor. */
    public Customer() {
    }

    // -------------------- Getters & Setters --------------------
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
     * Computes total reward points earned by the customer in the specified month.
     *
     * @param currentMonth the month in format {@code yyyy-MM}
     * @return total points earned in that month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        YearMonth targetMonth;
        try {
            targetMonth = YearMonth.parse(currentMonth);
        } catch (Exception e) {
            return 0;
        }
        int totalPoints = 0;
        for (Booking b : bookings) {
            LocalDate bookingDate = b.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            YearMonth bookingMonth = YearMonth.from(bookingDate);
            if (bookingMonth.equals(targetMonth)) {
                totalPoints += b.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book a number of seats on a given trip.
     *
     * @param trip            the trip to book
     * @param numberOfSeats   number of seats requested
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip cannot be null.");
        }
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date()); // current time

        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }
        // Update trip seats and add booking references
        booking.updateTripSeats();
        trip.getBookings().add(booking);
        this.bookings.add(booking);
    }
}

/**
 * Represents a ride trip, which may be direct or indirect (with stops).
 */
class Trip {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate;          // only date part
    private String departureTime;        // full datetime string "yyyy-MM-dd HH:mm"
    private String arrivalTime;          // full datetime string "yyyy-MM-dd HH:mm"
    private double price;
    private List<Booking> bookings = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();

    /** No‑argument constructor. */
    public Trip() {
    }

    // -------------------- Getters & Setters --------------------
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
     * Checks if the departure datetime is before the arrival datetime.
     *
     * @return {@code true} if departure < arrival, {@code false} otherwise
     */
    public boolean isDepartureBeforeArrival() {
        LocalDateTime dep = LocalDateTime.parse(departureTime, FORMATTER);
        LocalDateTime arr = LocalDateTime.parse(arrivalTime, FORMATTER);
        return dep.isBefore(arr);
    }

    /**
     * Calculates the final price for a booking after applying a 20% discount
     * when the customer has a discount award and the booking is made at least
     * 24 hours before departure.
     *
     * @param customer   the customer making the booking
     * @param bookingTimeStr booking time as string {@code "yyyy-MM-dd HH:mm"}
     * @return discounted price rounded to one decimal place, or original price if conditions not met
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTimeStr) {
        if (customer == null || bookingTimeStr == null) {
            return price;
        }
        MembershipPackage mp = customer.getMembershipPackage();
        if (mp == null || !mp.hasAward(Award.DISCOUNTS)) {
            return price;
        }
        LocalDateTime bookingTime;
        try {
            bookingTime = LocalDateTime.parse(bookingTimeStr, FORMATTER);
        } catch (Exception e) {
            return price;
        }
        LocalDateTime departure = LocalDateTime.parse(departureTime, FORMATTER);
        long hoursBetween = ChronoUnit.HOURS.between(bookingTime, departure);
        if (hoursBetween >= 24) {
            double discounted = price * 0.8;
            BigDecimal bd = new BigDecimal(discounted).setScale(1, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        return price;
    }

    /**
     * Returns the set of stop station names for this trip.
     *
     * @return a {@link Set} containing all stop station names
     */
    public Set<String> getStopStations() {
        Set<String> result = new HashSet<>();
        for (Stop s : stops) {
            result.add(s.getStopStation());
        }
        return result;
    }

    /**
     * Checks whether a new trip time interval conflicts with this trip's interval.
     * Adjacent intervals (end == start) are NOT considered a conflict.
     *
     * @param newDepTimeStr new departure time string {@code "yyyy-MM-dd HH:mm"}
     * @param newArrTimeStr new arrival time string {@code "yyyy-MM-dd HH:mm"}
     * @return {@code true} if there is a conflict, {@code false} otherwise
     */
    public boolean isTimeConflicting(String newDepTimeStr, String newArrTimeStr) {
        if (newDepTimeStr == null || newArrTimeStr == null) {
            return false;
        }
        LocalDateTime thisDep = LocalDateTime.parse(departureTime, FORMATTER);
        LocalDateTime thisArr = LocalDateTime.parse(arrivalTime, FORMATTER);
        LocalDateTime newDep = LocalDateTime.parse(newDepTimeStr, FORMATTER);
        LocalDateTime newArr = LocalDateTime.parse(newArrTimeStr, FORMATTER);

        // Overlap exists when intervals intersect but are not just touching
        boolean overlap = thisDep.isBefore(newArr) && newDep.isBefore(thisArr);
        boolean adjacent = thisArr.equals(newDep) || newArr.equals(thisDep);
        return overlap && !adjacent;
    }

    /**
     * Attempts to reserve a number of seats on this trip.
     *
     * @param num number of seats to reserve
     * @return {@code true} if reservation succeeded, {@code false} otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0) {
            return false;
        }
        if (numberOfSeats >= num) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms a booking by reducing the available seats.
     *
     * @param seats number of seats to confirm
     * @return {@code true} if the seats were successfully confirmed, {@code false} otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Calculates reward points for a customer based on bookings of this trip
     * that fall within a specific month.
     *
     * @param customer     the customer
     * @param currentMonth month in format {@code yyyy-MM}
     * @return total points earned from this trip for the month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer == null || currentMonth == null) {
            return 0;
        }
        YearMonth targetMonth;
        try {
            targetMonth = YearMonth.parse(currentMonth);
        } catch (Exception e) {
            return 0;
        }
        int points = 0;
        for (Booking b : bookings) {
            if (!b.getCustomer().equals(customer)) {
                continue;
            }
            LocalDate bookingDate = b.getBookingDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            YearMonth ym = YearMonth.from(bookingDate);
            if (ym.equals(targetMonth)) {
                points += b.getNumberOfSeats() * 5;
            }
        }
        return points;
    }
}

/**
 * Represents a stop (intermediate station) in an indirect trip.
 */
class Stop {
    private String stopStation;

    /** No‑argument constructor. */
    public Stop() {
    }

    // -------------------- Getters & Setters --------------------
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
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate; // moment when booking was created

    /** No‑argument constructor. */
    public Booking() {
    }

    // -------------------- Getters & Setters --------------------
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
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Determines if this booking is eligible according to the system rules.
     *
     * @return {@code true} if the booking can be made, {@code false} otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null || bookingDate == null) {
            return false;
        }
        // 1. Seats availability
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        // 2. No overlapping bookings for the same customer on the same day
        LocalDate bookingLocalDate = bookingDate.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        for (Booking other : customer.getBookings()) {
            if (other == this) {
                continue;
            }
            LocalDate otherDate = other.getBookingDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            if (!bookingLocalDate.equals(otherDate)) {
                continue;
            }
            if (this.overlapsWith(other.getTrip())) {
                return false;
            }
        }
        // 3. Booking must be made more than 2 hours before departure (not exactly 2)
        LocalDateTime bookingDateTime = LocalDateTime.ofInstant(bookingDate.toInstant(),
                ZoneId.systemDefault());
        LocalDateTime departureDateTime = LocalDateTime.parse(trip.getDepartureTime(),
                Trip.FORMATTER);
        long minutesDiff = ChronoUnit.MINUTES.between(bookingDateTime, departureDateTime);
        if (minutesDiff <= 120) { // <= 2 hours not allowed
            return false;
        }
        return true;
    }

    /**
     * Updates the trip's available seats after a successful booking.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks whether this booking's trip overlaps in time with another trip.
     *
     * @param otherTrip the other trip to compare with
     * @return {@code true} if the two trips overlap on the same day, {@code false} otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (otherTrip == null) {
            return false;
        }
        LocalDateTime thisDep = LocalDateTime.parse(trip.getDepartureTime(), Trip.FORMATTER);
        LocalDateTime thisArr = LocalDateTime.parse(trip.getArrivalTime(), Trip.FORMATTER);
        LocalDateTime otherDep = LocalDateTime.parse(otherTrip.getDepartureTime(), Trip.FORMATTER);
        LocalDateTime otherArr = LocalDateTime.parse(otherTrip.getArrivalTime(), Trip.FORMATTER);

        // Same day check (based on departure date)
        if (!thisDep.toLocalDate().equals(otherDep.toLocalDate())) {
            return false;
        }

        // Overlap condition (excluding adjacent)
        boolean overlap = thisDep.isBefore(otherArr) && otherDep.isBefore(thisArr);
        boolean adjacent = thisArr.equals(otherDep) || otherArr.equals(thisDep);
        return overlap && !adjacent;
    }
}

/**
 * Represents a membership package that can contain various awards.
 */
class MembershipPackage {
    private Award[] awards;

    /** No‑argument constructor. */
    public MembershipPackage() {
    }

    // -------------------- Getters & Setters --------------------
    public Award[] getAwards() {
        return awards;
    }
    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Checks whether the package contains a specific award.
     *
     * @param award the award to look for
     * @return {@code true} if the award is present, {@code false} otherwise
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
 * Enumeration of possible awards that a membership package can provide.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}