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
        // default constructor
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
 * Driver class – can post trips and check stop overlap.
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
     * Determines whether two indirect trips (each having at least one stop)
     * share any common stop stations.
     *
     * @param trip1 first trip to compare
     * @param trip2 second trip to compare
     * @return true if at least one stop station is common, false otherwise
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
     * Validates whether the driver can post a new trip without time conflicts.
     *
     * @param newTrip the trip the driver wants to post
     * @return true if the trip can be posted, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        // departure must be before arrival
        if (!newTrip.getDepartureDateTime().isBefore(newTrip.getArrivalDateTime())) {
            return false;
        }
        // check for time conflicts with existing trips
        for (Trip existing : trips) {
            if (existing.isTimeConflicting(
                    newTrip.getDepartureDateTime().format(Trip.FORMATTER),
                    newTrip.getArrivalDateTime().format(Trip.FORMATTER))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a trip to the driver's list after validation.
     *
     * @param newTrip the trip to add
     * @return true if added successfully, false otherwise
     */
    public boolean addTrip(Trip newTrip) {
        if (canPostTrip(newTrip)) {
            trips.add(newTrip);
            return true;
        }
        return false;
    }
}

/**
 * Customer class – can hold a membership package and book trips.
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
     * Computes total reward points earned by the customer in the specified month.
     *
     * @param currentMonth a string in the format "yyyy-MM" representing the target month
     * @return total points accumulated in that month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null ||
                !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            String bookingMonth = b.getBookingDate().format(monthFormatter);
            if (bookingMonth.equals(currentMonth)) {
                totalPoints += b.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book a number of seats on a given trip.
     *
     * @param trip            the trip to book
     * @param numberOfSeats   how many seats the customer wants
     * @throws IllegalArgumentException if booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking(this, trip, numberOfSeats);
        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }
        // update trip seats and register booking
        booking.updateTripSeats();
        trip.getBookings().add(booking);
        this.bookings.add(booking);
    }
}

/**
 * Represents a trip offered by a driver.
 */
class Trip {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDate departureDate;               // only date part (optional)
    private String departureTime;                  // "yyyy-MM-dd HH:mm"
    private String arrivalTime;                    // "yyyy-MM-dd HH:mm"
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
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

    // ---------- Helper methods ----------
    /**
     * Parses the stored departureTime string into a LocalDateTime.
     *
     * @return LocalDateTime representation of departure
     */
    public LocalDateTime getDepartureDateTime() {
        return LocalDateTime.parse(departureTime, FORMATTER);
    }

    /**
     * Parses the stored arrivalTime string into a LocalDateTime.
     *
     * @return LocalDateTime representation of arrival
     */
    public LocalDateTime getArrivalDateTime() {
        return LocalDateTime.parse(arrivalTime, FORMATTER);
    }

    /**
     * Reduces the available seats by the specified number if possible.
     *
     * @param num number of seats to book
     * @return true if seats were successfully booked, false otherwise
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
     * @return true if seats confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Returns a set containing all stop station names for this trip.
     *
     * @return set of stop station strings
     */
    public Set<String> getStopStations() {
        Set<String> set = new HashSet<>();
        for (Stop s : stops) {
            set.add(s.getStopStation());
        }
        return set;
    }

    /**
     * Checks whether the supplied time interval conflicts with this trip's interval.
     *
     * @param newDepartureTime departure time of the new interval (format "yyyy-MM-dd HH:mm")
     * @param newArrivalTime   arrival time of the new interval (same format)
     * @return true if intervals intersect (including identical), false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        LocalDateTime newDep = LocalDateTime.parse(newDepartureTime, FORMATTER);
        LocalDateTime newArr = LocalDateTime.parse(newArrivalTime, FORMATTER);
        LocalDateTime thisDep = getDepartureDateTime();
        LocalDateTime thisArr = getArrivalDateTime();

        // identical periods are a conflict
        if (newDep.isEqual(thisDep) && newArr.isEqual(thisArr)) {
            return true;
        }

        // overlapping condition (excluding just-touching boundaries)
        boolean overlaps = !(newArr.isBefore(thisDep) || newDep.isAfter(thisArr));
        // Adjacent boundaries (newArr == thisDep or newDep == thisArr) are NOT a conflict
        if (newArr.isEqual(thisDep) || newDep.isEqual(thisArr)) {
            overlaps = false;
        }
        return overlaps;
    }

    /**
     * Calculates the discounted price for a booking made by a customer.
     *
     * @param customer    the customer making the booking
     * @param bookingTime string representation of booking time ("yyyy-MM-dd HH:mm")
     * @return discounted price (rounded to one decimal) if eligible, otherwise original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        LocalDateTime bTime = LocalDateTime.parse(bookingTime, FORMATTER);
        LocalDateTime departure = getDepartureDateTime();

        // eligibility: membership with DISCOUNTS award and booking at least 24h before departure
        boolean hasDiscountAward = customer.getMembershipPackage() != null &&
                customer.getMembershipPackage().hasAward(Award.DISCOUNTS);
        boolean enoughAdvance = Duration.between(bTime, departure).toHours() >= 24;

        if (hasDiscountAward && enoughAdvance) {
            double discounted = price * 0.8; // 20% off
            return Math.round(discounted * 10.0) / 10.0;
        }
        return price;
    }

    /**
     * Computes reward points earned by a specific customer for bookings on this trip
     * that fall within the given month.
     *
     * @param customer     the customer whose points are calculated
     * @param currentMonth month in format "yyyy-MM"
     * @return total points from this trip for the month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer.getMembershipPackage() == null ||
                !customer.getMembershipPackage().hasAward(Award.POINTS)) {
            return 0;
        }
        int points = 0;
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            if (!b.getCustomer().equals(customer)) {
                continue;
            }
            String bookingMonth = b.getBookingDate().format(monthFormatter);
            if (bookingMonth.equals(currentMonth)) {
                points += b.getNumberOfSeats() * 5;
            }
        }
        return points;
    }
}

/**
 * Represents a stop within an indirect trip.
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
    private LocalDateTime bookingDate; // moment when booking is created

    public Booking() {
        // default constructor
    }

    public Booking(Customer customer, Trip trip, int numberOfSeats) {
        this.customer = customer;
        this.trip = trip;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = LocalDateTime.now();
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

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Determines if this booking is eligible according to business rules.
     *
     * @return true if eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null) {
            return false;
        }
        // 1. Seats availability
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        // 2. Booking must be at least 2 hours before departure (strictly > 2h)
        LocalDateTime departure = trip.getDepartureDateTime();
        Duration diff = Duration.between(bookingDate, departure);
        if (diff.toHours() <= 2) {
            return false;
        }
        // 3. No overlapping bookings for same customer on same day
        for (Booking existing : customer.getBookings()) {
            if (existing == this) {
                continue; // skip self
            }
            if (existing.overlapsWith(this.trip)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the associated trip's seat count after a successful booking.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks whether this booking's trip overlaps in time with another trip on the same day.
     *
     * @param otherTrip the other trip to compare with
     * @return true if the two trips overlap, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (otherTrip == null) {
            return false;
        }
        LocalDateTime thisDep = trip.getDepartureDateTime();
        LocalDateTime thisArr = trip.getArrivalDateTime();

        LocalDateTime otherDep = otherTrip.getDepartureDateTime();
        LocalDateTime otherArr = otherTrip.getArrivalDateTime();

        // Overlap only matters if dates are the same (same calendar day)
        if (!thisDep.toLocalDate().equals(otherDep.toLocalDate())) {
            return false;
        }

        // Overlap condition (excluding just-touching)
        boolean overlaps = !(thisArr.isBefore(otherDep) || thisDep.isAfter(otherArr));
        if (thisArr.isEqual(otherDep) || thisDep.isEqual(otherArr)) {
            overlaps = false;
        }
        return overlaps;
    }
}

/**
 * Membership package that may contain several awards.
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
     * Checks whether this package contains the specified award.
     *
     * @param award the award to look for
     * @return true if present, false otherwise
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