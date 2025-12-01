import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract base class for all users of the Online Rideshare System.
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    /** No‑arg constructor */
    public User() {}

    // Getters and Setters
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

    /** No‑arg constructor */
    public Driver() {
        this.trips = new ArrayList<>();
    }

    // Getters and Setters
    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Determines whether two indirect trips (having at least one stop) share any common stop stations.
     *
     * @param trip1 the first trip (must have at least one stop)
     * @param trip2 the second trip (must have at least one stop)
     * @return true if there is at least one stop station common to both trips, false otherwise
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
     * Checks whether the driver can post a new trip without time conflicts.
     *
     * @param newTrip the trip the driver wants to post
     * @return true if posting is feasible, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        // departure must be before arrival
        if (!newTrip.isDepartureBeforeArrival()) {
            return false;
        }
        // check time conflicts with existing trips
        for (Trip existing : trips) {
            if (existing.isTimeConflicting(newTrip.getDepartureDateTimeString(),
                                          newTrip.getArrivalDateTimeString())) {
                return false; // conflict found
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

    /** No‑arg constructor */
    public Customer() {
        this.bookings = new ArrayList<>();
    }

    // Getters and Setters
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
     * Computes total reward points earned by the customer in the given month.
     *
     * @param currentMonth month in the format {@code yyyy-MM}
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
                totalPoints += b.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book a given number of seats on a trip.
     *
     * @param trip            the trip to be booked
     * @param numberOfSeats   number of seats requested
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setCustomer(this);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(LocalDate.now());

        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking not eligible");
        }
        // update trip seat count
        booking.updateTripSeats();
        // register booking
        trip.getBookings().add(booking);
        this.bookings.add(booking);
    }
}

/**
 * Represents a trip offered by a driver.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDate departureDate;               // only the date part
    private String departureTime;                  // format "HH:mm"
    private String arrivalTime;                    // format "HH:mm"
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    /** No‑arg constructor */
    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    // Getters and Setters
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

    /**
     * Returns a set containing the stop stations of this trip.
     *
     * @return Set of stop station names (empty if no stops)
     */
    public Set<String> getStopStations() {
        Set<String> stations = new HashSet<>();
        for (Stop s : stops) {
            stations.add(s.getStopStation());
        }
        return stations;
    }

    /**
     * Checks whether the given departure/arrival time interval conflicts with this trip.
     *
     * @param newDepartureTime departure time in format {@code yyyy-MM-dd HH:mm}
     * @param newArrivalTime   arrival time in format {@code yyyy-MM-dd HH:mm}
     * @return true if intervals intersect (including identical intervals), false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime existingDep = LocalDateTime.of(departureDate, LocalDateTime.parse(departureDate + " " + departureTime, fmt).toLocalTime());
        LocalDateTime existingArr = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("HH:mm"))
                .atDate(departureDate); // assuming arrival on same day; for simplicity

        LocalDateTime newDep = LocalDateTime.parse(newDepartureTime, fmt);
        LocalDateTime newArr = LocalDateTime.parse(newArrivalTime, fmt);

        // Adjacent boundaries are NOT a conflict: [a,b) intersect [c,d) if a < d && c < b
        return !(existingArr.isEqual(newDep) || newArr.isEqual(existingDep)) &&
               existingDep.isBefore(newArr) && newDep.isBefore(existingArr);
    }

    /**
     * Helper to verify that departure is strictly before arrival.
     *
     * @return true if departure date‑time is before arrival date‑time
     */
    public boolean isDepartureBeforeArrival() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dep = LocalDateTime.parse(getDepartureDateTimeString(), fmt);
        LocalDateTime arr = LocalDateTime.parse(getArrivalDateTimeString(), fmt);
        return dep.isBefore(arr);
    }

    /**
     * Returns the combined departure date‑time string.
     *
     * @return departure date‑time in {@code yyyy-MM-dd HH:mm}
     */
    public String getDepartureDateTimeString() {
        return departureDate.toString() + " " + departureTime;
    }

    /**
     * Returns the combined arrival date‑time string.
     *
     * @return arrival date‑time in {@code yyyy-MM-dd HH:mm}
     */
    public String getArrivalDateTimeString() {
        // For simplicity we assume arrival occurs on the same date as departure;
        // in a real system we would store a full LocalDateTime for arrival.
        return departureDate.toString() + " " + arrivalTime;
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
     * @return true if confirmation succeeded, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Calculates the final price for a booking after applying possible discount.
     *
     * @param customer     the customer making the booking
     * @param bookingTime  booking time in format {@code yyyy-MM-dd HH:mm}
     * @return discounted price (rounded to one decimal place) if conditions are met,
     *         otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null) {
            return price;
        }
        MembershipPackage mp = customer.getMembershipPackage();
        if (mp == null || !mp.hasAward(Award.DISCOUNTS)) {
            return price;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, fmt);
        LocalDateTime departureDateTime = LocalDateTime.parse(getDepartureDateTimeString(), fmt);
        long hoursBetween = Duration.between(bookingDateTime, departureDateTime).toHours();

        // discount applies only if booking is at least 24 hours before departure
        if (hoursBetween >= 24) {
            double discounted = price * 0.8; // 20% off
            return Math.round(discounted * 10.0) / 10.0; // keep 1 decimal place
        }
        return price;
    }

    /**
     * Calculates reward points earned by a customer for this trip in a specific month.
     *
     * @param customer     the customer
     * @param currentMonth month in format {@code yyyy-MM}
     * @return total points earned for this trip (0 if not applicable)
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer == null) {
            return 0;
        }
        MembershipPackage mp = customer.getMembershipPackage();
        if (mp == null || !mp.hasAward(Award.POINTS)) {
            return 0;
        }
        int points = 0;
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Booking b : bookings) {
            if (b.getCustomer().equals(customer)) {
                String bookingMonth = b.getBookingDate().format(monthFmt);
                if (bookingMonth.equals(currentMonth)) {
                    points += b.getNumberOfSeats() * 5;
                }
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

    /** No‑arg constructor */
    public Stop() {}

    // Getter and Setter
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

    /** No‑arg constructor */
    public Booking() {}

    // Getters and Setters
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
     * Checks whether this booking is eligible according to system rules.
     *
     * @return true if the booking can be made, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        // 1. Trip must have enough available seats
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // 2. No overlapping bookings for the same customer on the same day
        for (Booking existing : customer.getBookings()) {
            if (existing == this) {
                continue; // skip self
            }
            // overlapping if dates are same and times intersect
            if (existing.getTrip().getDepartureDate().equals(trip.getDepartureDate())) {
                if (existing.overlapsWith(trip)) {
                    return false;
                }
            }
        }

        // 3. Booking must be made at least 2 hours before departure (strictly > 2 hours)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = LocalDateTime.parse(trip.getDepartureDateTimeString(), fmt);
        long hoursBetween = Duration.between(now, departure).toHours();
        if (hoursBetween <= 2) { // not more than 2 hours
            return false;
        }

        return true;
    }

    /**
     * Updates the associated trip's seat availability after a successful booking.
     */
    public void updateTripSeats() {
        trip.confirmBooking(numberOfSeats);
    }

    /**
     * Determines whether this booking's trip overlaps in time with another trip.
     *
     * @param otherTrip the other trip to compare with
     * @return true if the two trips overlap in time, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime thisDep = LocalDateTime.parse(trip.getDepartureDateTimeString(), fmt);
        LocalDateTime thisArr = LocalDateTime.parse(trip.getArrivalDateTimeString(), fmt);
        LocalDateTime otherDep = LocalDateTime.parse(otherTrip.getDepartureDateTimeString(), fmt);
        LocalDateTime otherArr = LocalDateTime.parse(otherTrip.getArrivalDateTimeString(), fmt);

        // Overlap if intervals intersect (excluding adjacent boundaries)
        return !(thisArr.isEqual(otherDep) || otherArr.isEqual(thisDep)) &&
               thisDep.isBefore(otherArr) && otherDep.isBefore(thisArr);
    }
}

/**
 * Represents a membership package which may contain multiple awards.
 */
class MembershipPackage {
    private Award[] awards;

    /** No‑arg constructor */
    public MembershipPackage() {}

    // Getters and Setters
    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Checks whether the package contains the specified award.
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