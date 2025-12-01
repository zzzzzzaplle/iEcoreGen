import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class for all users of the system.
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    public User() {
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
     * Determines whether two indirect trips (i.e., trips that have at least one stop)
     * posted by the same driver share any common stop stations.
     *
     * @param trip1 the first trip to compare
     * @param trip2 the second trip to compare
     * @return true if the two trips have at least one stop station in common; false otherwise
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
     * Checks if a new trip can be posted by this driver without time conflicts.
     *
     * @param newTrip the trip the driver wishes to post
     * @return true if the new trip does not conflict with any existing trips; false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Preconditions: driver must exist (this object) – always true here.
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            // departure must be before arrival
            return false;
        }

        for (Trip existing : trips) {
            if (existing.isTimeConflicting(newTrip.getDepartureDateTime(), newTrip.getArrivalDateTime())) {
                return false; // conflict found
            }
        }
        return true; // no conflicts
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
     * Computes the total reward points earned by the customer in the specified month.
     *
     * @param currentMonth the month for which points are calculated, formatted as "yyyy-MM"
     * @return total reward points earned in that month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }

        int totalPoints = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            String bookingMonth = b.getBookingDate().format(monthFormatter);
            if (bookingMonth.equals(currentMonth)) {
                totalPoints += b.getNumberOfSeats() * 5; // 5 points per seat
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book the requested number of seats on the given trip.
     * If the booking is eligible, a new Booking object is created,
     * the trip seat count is updated, and the booking is stored.
     *
     * @param trip           the trip to book
     * @param numberOfSeats  the number of seats the customer wants to reserve
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking(this, trip, numberOfSeats, LocalDate.now());
        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }
        booking.updateTripSeats();
        bookings.add(booking);
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
    private LocalDate departureDate; // only the date part
    private LocalDateTime departureDateTime; // full timestamp
    private LocalDateTime arrivalDateTime;   // full timestamp
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTimeStr) {
        this.departureDateTime = LocalDateTime.parse(departureDateTimeStr, DATE_TIME_FORMATTER);
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTimeStr) {
        this.arrivalDateTime = LocalDateTime.parse(arrivalDateTimeStr, DATE_TIME_FORMATTER);
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
     * Calculates the final price for a booking after applying a 20% discount,
     * if the customer has a membership with a discount award and the booking
     * is made at least 24 hours before departure.
     *
     * @param customer    the customer making the booking
     * @param bookingTime the time the booking is made, formatted as "yyyy-MM-dd HH:mm"
     * @return discounted price (rounded to one decimal place) if applicable; otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        double finalPrice = price;
        if (customer.getMembershipPackage() != null
                && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, DATE_TIME_FORMATTER);
            Duration diff = Duration.between(bookingDateTime, departureDateTime);
            if (!diff.isNegative() && diff.toHours() >= 24) {
                finalPrice = Math.round(price * 0.8 * 10.0) / 10.0; // 20% off, one decimal
            }
        }
        return finalPrice;
    }

    /**
     * Attempts to reserve the given number of seats on this trip.
     *
     * @param num number of seats requested
     * @return true if seats were successfully reserved; false otherwise
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
     * Alias for {@link #bookSeats(int)} – kept for compatibility with design model.
     *
     * @param seats number of seats to confirm
     * @return true if seats were confirmed; false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Returns a set containing the station names of all stops for this trip.
     *
     * @return set of stop station names; empty set if no stops
     */
    public Set<String> getStopStations() {
        Set<String> stationSet = new HashSet<>();
        for (Stop s : stops) {
            stationSet.add(s.getStopStation());
        }
        return stationSet;
    }

    /**
     * Checks whether the given time interval conflicts with this trip's interval.
     * Two intervals conflict if they intersect (overlap) but not if they merely touch
     * (e.g., one ends exactly when the other starts).
     *
     * @param newDeparture the start of the new interval
     * @param newArrival   the end of the new interval
     * @return true if there is a conflict; false otherwise
     */
    public boolean isTimeConflicting(LocalDateTime newDeparture, LocalDateTime newArrival) {
        // No conflict if new interval ends before this starts or starts after this ends
        if (newArrival.isBefore(this.departureDateTime) || newArrival.isEqual(this.departureDateTime)) {
            return false;
        }
        if (newDeparture.isAfter(this.arrivalDateTime) || newDeparture.isEqual(this.arrivalDateTime)) {
            return false;
        }
        // Otherwise intervals intersect
        return true;
    }
}

/**
 * Represents a stop in an indirect trip.
 */
class Stop {
    private String stopStation;

    public Stop() {
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
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private LocalDate bookingDate; // date when the booking was made

    public Booking() {
    }

    public Booking(Customer customer, Trip trip, int numberOfSeats, LocalDate bookingDate) {
        this.customer = customer;
        this.trip = trip;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = bookingDate;
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

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Determines whether this booking is eligible according to the business rules:
     * <ul>
     *   <li>Trip must exist and have enough available seats.</li>
     *   <li>The customer must not have another booking that overlaps in time on the same day.</li>
     *   <li>Booking must be made at least 2 hours before the trip's departure (strictly > 2 hours).</li>
     * </ul>
     *
     * @return true if the booking satisfies all eligibility criteria; false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null) {
            return false;
        }

        // Check seat availability
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // Check 2‑hour rule
        LocalDateTime now = LocalDateTime.now();
        Duration untilDeparture = Duration.between(now, trip.getDepartureDateTime());
        if (untilDeparture.toHours() <= 2) { // must be > 2 hours
            return false;
        }

        // Check overlapping bookings for the same customer on the same day
        for (Booking other : customer.getBookings()) {
            if (other == this) {
                continue;
            }
            // Same day?
            if (other.getTrip().getDepartureDate().equals(this.trip.getDepartureDate())) {
                // Overlap?
                if (this.overlapsWith(other.getTrip())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reduces the number of available seats on the associated trip according to this booking.
     * Assumes {@link #isBookingEligible()} has already returned true.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks whether this booking's trip time interval overlaps with another trip's interval.
     *
     * @param otherTrip the other trip to compare against
     * @return true if the intervals intersect (excluding touching boundaries); false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        // Use the same logic as Trip.isTimeConflicting but reversed
        return otherTrip.isTimeConflicting(this.trip.getDepartureDateTime(), this.trip.getArrivalDateTime());
    }
}

/**
 * Represents a membership package that can contain several awards.
 */
class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {
    }

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
     * @return true if the award is present in the package; false otherwise
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
 * Enumeration of possible award types in a membership package.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}