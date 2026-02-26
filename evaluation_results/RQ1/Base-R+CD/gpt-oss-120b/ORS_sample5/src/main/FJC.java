import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Abstract base class for all users of the ORS system.
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    /** Default constructor */
    public User() {
    }

    /** Getters and Setters */
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
    private List<Trip> trips = new ArrayList<>();

    /** Default constructor */
    public Driver() {
    }

    /** Getters and Setters */
    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Determines whether two indirect trips share any common stop stations.
     *
     * @param trip1 the first trip (must have at least one stop)
     * @param trip2 the second trip (must have at least one stop)
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
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
     * Validates whether the driver can post a new trip without time conflicts.
     *
     * @param newTrip the trip the driver wishes to post
     * @return true if posting is feasible, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Preconditions: driver exists (this object) and newTrip has valid times
        if (newTrip == null) {
            return false;
        }
        // departure must be before arrival
        if (!newTrip.isDepartureBeforeArrival()) {
            return false;
        }

        // Check conflicts with existing trips
        for (Trip existing : trips) {
            if (existing.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false; // conflict found
            }
        }
        return true; // no conflict
    }
}

/**
 * Represents a customer who may hold a membership package and make bookings.
 */
class Customer extends User {
    private MembershipPackage membershipPackage;
    private List<Booking> bookings = new ArrayList<>();

    /** Default constructor */
    public Customer() {
    }

    /** Getters and Setters */
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
     * Calculates total reward points earned by this customer in a given month.
     *
     * @param currentMonth the month in the format "yyyy-MM"
     * @return total reward points earned in the month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            LocalDateTime bookingDate = toLocalDateTime(b.getBookingDate());
            String bookingMonth = bookingDate.format(monthFormatter);
            if (bookingMonth.equals(currentMonth)) {
                totalPoints += b.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book a number of seats on a given trip.
     *
     * @param trip           the trip to be booked
     * @param numberOfSeats  number of seats the customer wants to book
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) throws IllegalArgumentException {
        if (trip == null) {
            throw new IllegalArgumentException("Trip cannot be null.");
        }
        Booking newBooking = new Booking();
        newBooking.setTrip(trip);
        newBooking.setCustomer(this);
        newBooking.setNumberOfSeats(numberOfSeats);
        newBooking.setBookingDate(new Date());

        if (!newBooking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }

        // Update seats on the trip
        newBooking.updateTripSeats();

        // Register booking
        trip.getBookings().add(newBooking);
        this.bookings.add(newBooking);
    }

    /**
     * Helper method to convert java.util.Date to LocalDateTime.
     */
    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}

/**
 * Represents a trip posted by a driver.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate; // only the date part
    private String departureTime; // format "yyyy-MM-dd HH:mm"
    private String arrivalTime;   // format "yyyy-MM-dd HH:mm"
    private double price;
    private List<Booking> bookings = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();

    /** Default constructor */
    public Trip() {
    }

    /** Getters and Setters */
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
     * Calculates the discounted price for a booking made by a given customer.
     *
     * @param customer    the customer making the booking
     * @param bookingTime the time of booking in format "yyyy-MM-dd HH:mm"
     * @return discounted price (rounded to one decimal) if conditions are met,
     *         otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null || customer.getMembershipPackage() == null) {
            return price;
        }
        if (!customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            return price;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, fmt);
        LocalDateTime departureDateTime = LocalDateTime.parse(this.departureTime, fmt);

        long hoursBetween = ChronoUnit.HOURS.between(bookingDateTime, departureDateTime);
        if (hoursBetween >= 24) {
            double discounted = price * 0.80; // 20% off
            return Math.round(discounted * 10.0) / 10.0; // keep one decimal place
        }
        return price;
    }

    /**
     * Checks whether this trip's time interval conflicts with a given time interval.
     *
     * @param newDepartureTime the proposed departure time (format "yyyy-MM-dd HH:mm")
     * @param newArrivalTime   the proposed arrival time (format "yyyy-MM-dd HH:mm")
     * @return true if there is a conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime thisDep = LocalDateTime.parse(this.departureTime, fmt);
        LocalDateTime thisArr = LocalDateTime.parse(this.arrivalTime, fmt);
        LocalDateTime newDep = LocalDateTime.parse(newDepartureTime, fmt);
        LocalDateTime newArr = LocalDateTime.parse(newArrivalTime, fmt);

        // No conflict if one ends exactly when the other starts
        if (thisArr.isEqual(newDep) || newArr.isEqual(thisDep)) {
            return false;
        }

        // Overlap occurs when intervals intersect
        boolean overlap = !(newArr.isBefore(thisDep) || newDep.isAfter(thisArr));
        return overlap;
    }

    /**
     * Returns the set of stop stations associated with this trip.
     *
     * @return a set containing stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stations = new HashSet<>();
        for (Stop s : stops) {
            stations.add(s.getStopStation());
        }
        return stations;
    }

    /**
     * Attempts to reserve a number of seats on this trip.
     *
     * @param num number of seats to reserve
     * @return true if reservation succeeded, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0) {
            return false;
        }
        if (num <= this.numberOfSeats) {
            this.numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Alias for {@link #bookSeats(int)} used by booking confirmation flow.
     *
     * @param seats number of seats to confirm
     * @return true if confirmation succeeded, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Helper method to verify that departure time precedes arrival time.
     *
     * @return true if departure is before arrival, false otherwise
     */
    public boolean isDepartureBeforeArrival() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dep = LocalDateTime.parse(this.departureTime, fmt);
        LocalDateTime arr = LocalDateTime.parse(this.arrivalTime, fmt);
        return dep.isBefore(arr);
    }
}

/**
 * Represents a stop within an indirect trip.
 */
class Stop {
    private String stopStation;

    /** Default constructor */
    public Stop() {
    }

    /** Getters and Setters */
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
    private Date bookingDate;

    /** Default constructor */
    public Booking() {
    }

    /** Getters and Setters */
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
     * Determines if this booking is eligible according to system rules.
     *
     * @return true if the booking can be made, false otherwise
     */
    public boolean isBookingEligible() {
        // Rule 1: Trip must exist and have enough available seats
        if (trip == null) {
            return false;
        }
        if (numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }

        // Rule 2: Booking must be at least 2 hours before departure (strictly > 2 hours)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = LocalDateTime.parse(trip.getDepartureTime(), fmt);
        Duration diff = Duration.between(now, departure);
        if (diff.toHours() <= 2) { // not more than 2 hours
            return false;
        }

        // Rule 3: No overlapping bookings for the same customer on the same day
        if (customer != null) {
            for (Booking existing : customer.getBookings()) {
                if (overlapsWith(existing.getTrip())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Updates the number of available seats on the associated trip.
     */
    public void updateTripSeats() {
        trip.bookSeats(this.numberOfSeats);
    }

    /**
     * Checks whether this booking's trip time overlaps with another trip on the same day.
     *
     * @param otherTrip the other trip to compare against
     * @return true if the trips overlap, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (otherTrip == null) {
            return false;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime thisDep = LocalDateTime.parse(this.trip.getDepartureTime(), fmt);
        LocalDateTime thisArr = LocalDateTime.parse(this.trip.getArrivalTime(), fmt);
        LocalDateTime otherDep = LocalDateTime.parse(otherTrip.getDepartureTime(), fmt);
        LocalDateTime otherArr = LocalDateTime.parse(otherTrip.getArrivalTime(), fmt);

        // Overlap if dates are the same and intervals intersect
        boolean sameDay = thisDep.toLocalDate().equals(otherDep.toLocalDate());
        if (!sameDay) {
            return false;
        }
        // Adjacent intervals are not overlapping
        if (thisArr.isEqual(otherDep) || otherArr.isEqual(thisDep)) {
            return false;
        }
        // General overlap condition
        return !(thisArr.isBefore(otherDep) || thisDep.isAfter(otherArr));
    }
}

/**
 * Represents a membership package that may contain various awards.
 */
class MembershipPackage {
    private Award[] awards;

    /** Default constructor */
    public MembershipPackage() {
    }

    /** Getters and Setters */
    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Checks whether this package includes a specific award.
     *
     * @param award the award to check
     * @return true if the award is present, false otherwise
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
 * Enumeration of possible award types.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}