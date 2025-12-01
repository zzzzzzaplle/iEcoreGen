import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Enum representing different types of awards.
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}

/**
 * Represents a membership package with associated awards.
 */
class MembershipPackage {
    private Award[] awards;

    /**
     * Checks if the membership package has a specific award.
     * 
     * @param award the award to check for
     * @return true if the award is present, false otherwise
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a == award) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the awards associated with this membership package.
     * 
     * @return the array of awards
     */
    public Award[] getAwards() {
        return awards;
    }

    /**
     * Sets the awards for this membership package.
     * 
     * @param awards the array of awards to set
     */
    public void setAwards(Award[] awards) {
        this.awards = awards;
    }
}

/**
 * Abstract base class representing a user in the system.
 */
abstract class User {
    protected String ID;
    protected String email;
    protected String phoneNumber;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Gets the ID of the user.
     * 
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * 
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

/**
 * Represents a driver in the system.
 */
class Driver extends User {
    private List<Trip> trips;

    /**
     * Default constructor.
     */
    public Driver() {
        trips = new ArrayList<>();
    }

    /**
     * Checks if two trips have any common stops.
     * 
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if there are common stops, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        stops1.retainAll(stops2);
        return !stops1.isEmpty();
    }

    /**
     * Checks if a new trip can be posted by the driver without time conflicts.
     * 
     * @param newTrip the new trip to check
     * @return true if the trip can be posted, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        String newDepartureTime = newTrip.getDepartureTime();
        String newArrivalTime = newTrip.getArrivalTime();
        for (Trip trip : trips) {
            if (trip.isTimeConflicting(newDepartureTime, newArrivalTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the list of trips associated with this driver.
     * 
     * @return the list of trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Sets the list of trips for this driver.
     * 
     * @param trips the list of trips to set
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

/**
 * Represents a customer in the system.
 */
class Customer extends User {
    private MembershipPackage membershipPackage;

    /**
     * Default constructor.
     */
    public Customer() {}

    /**
     * Computes the monthly reward points for the customer.
     * 
     * @param currentMonth the current month in "yyyy-MM" format
     * @return the total reward points for the month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Booking booking : getBookings()) {
                String bookingDateStr = sdf.format(booking.getBookingDate());
                if (bookingDateStr.startsWith(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Gets the list of bookings made by this customer.
     * 
     * @return the list of bookings
     */
    private List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        // Assuming there's a way to get bookings for a customer
        return bookings;
    }

    /**
     * Books a trip for the customer.
     * 
     * @param trip         the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setCustomer(this);
        booking.setNumberOfSeats(numberOfSeats);
        // Assuming booking logic is handled elsewhere
    }

    /**
     * Gets the membership package associated with this customer.
     * 
     * @return the membership package
     */
    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    /**
     * Sets the membership package for this customer.
     * 
     * @param membershipPackage the membership package to set
     */
    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }
}

/**
 * Represents a trip in the system.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    /**
     * Default constructor.
     */
    public Trip() {
        bookings = new ArrayList<>();
        stops = new ArrayList<>();
    }

    /**
     * Calculates the discounted price for a booking.
     * 
     * @param customer   the customer making the booking
     * @param bookingTime the time of booking
     * @return the discounted price
     * @throws ParseException if date parsing fails
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = sdf.parse(bookingTime);
        Date departureDateTime = sdf.parse(getDepartureDateStr());
        long diff = departureDateTime.getTime() - bookingDate.getTime();
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)
                && diff >= 24 * 60 * 60 * 1000) {
            return price * 0.8;
        }
        return price;
    }

    /**
     * Gets the departure date and time as a string.
     * 
     * @return the departure date and time
     */
    private String getDepartureDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(departureDate);
    }

    /**
     * Books seats on this trip.
     * 
     * @param num the number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= numberOfSeats) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms a booking on this trip.
     * 
     * @param seats the number of seats booked
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        // Assuming logic to confirm booking
        return true;
    }

    /**
     * Gets the set of stop stations for this trip.
     * 
     * @return the set of stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if there's a time conflict with another trip.
     * 
     * @param newDepartureTime the departure time of the new trip
     * @param newArrivalTime   the arrival time of the new trip
     * @return true if there's a conflict, false otherwise
     * @throws ParseException if date parsing fails
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thisDeparture = sdf.parse(getDepartureTime());
        Date thisArrival = sdf.parse(getArrivalTime());
        Date newDeparture = sdf.parse(newDepartureTime);
        Date newArrival = sdf.parse(newArrivalTime);
        return (thisDeparture.before(newArrival) && thisArrival.after(newDeparture));
    }

    /**
     * Gets the departure station.
     * 
     * @return the departure station
     */
    public String getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station.
     * 
     * @param departureStation the departure station to set
     */
    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Gets the arrival station.
     * 
     * @return the arrival station
     */
    public String getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station.
     * 
     * @param arrivalStation the arrival station to set
     */
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Gets the number of seats available.
     * 
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats.
     * 
     * @param numberOfSeats the number of seats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the departure date.
     * 
     * @return the departure date
     */
    public Date getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date.
     * 
     * @param departureDate the departure date to set
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the departure time.
     * 
     * @return the departure time
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     * 
     * @param departureTime the departure time to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time.
     * 
     * @return the arrival time
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time.
     * 
     * @param arrivalTime the arrival time to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the price of the trip.
     * 
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the trip.
     * 
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the list of bookings for this trip.
     * 
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings for this trip.
     * 
     * @param bookings the list of bookings to set
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Gets the list of stops for this trip.
     * 
     * @return the list of stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * Sets the list of stops for this trip.
     * 
     * @param stops the list of stops to set
     */
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}

/**
 * Represents a stop on a trip.
 */
class Stop {
    private String stopStation;

    /**
     * Default constructor.
     */
    public Stop() {}

    /**
     * Gets the stop station.
     * 
     * @return the stop station
     */
    public String getStopStation() {
        return stopStation;
    }

    /**
     * Sets the stop station.
     * 
     * @param stopStation the stop station to set
     */
    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a booking made by a customer.
 */
class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    /**
     * Default constructor.
     */
    public Booking() {}

    /**
     * Checks if this booking is eligible.
     * 
     * @return true if eligible, false otherwise
     * @throws ParseException if date parsing fails
     */
    public boolean isBookingEligible() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date currentTime = new Date();
        Date departureTime = sdf.parse(trip.getDepartureTime());
        long diff = departureTime.getTime() - currentTime.getTime();
        if (diff > 2 * 60 * 60 * 1000 && trip.getNumberOfSeats() >= numberOfSeats) {
            // Check for overlapping bookings
            for (Booking b : trip.getBookings()) {
                if (b.getCustomer().equals(customer) && isOverlapping(b)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this booking overlaps with another booking.
     * 
     * @param other the other booking
     * @return true if overlapping, false otherwise
     * @throws ParseException if date parsing fails
     */
    private boolean isOverlapping(Booking other) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thisDeparture = sdf.parse(trip.getDepartureTime());
        Date otherDeparture = sdf.parse(other.trip.getDepartureTime());
        // Simplified overlap check, actual logic may vary
        return thisDeparture.equals(otherDeparture);
    }

    /**
     * Updates the trip seats after booking.
     */
    public void updateTripSeats() {
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
    }

    /**
     * Gets the number of seats booked.
     * 
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats booked.
     * 
     * @param numberOfSeats the number of seats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the customer who made this booking.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made this booking.
     * 
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the trip associated with this booking.
     * 
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets the trip associated with this booking.
     * 
     * @param trip the trip to set
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Gets the booking date.
     * 
     * @return the booking date
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date.
     * 
     * @param bookingDate the booking date to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}