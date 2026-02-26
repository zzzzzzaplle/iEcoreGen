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
     * Constructs a MembershipPackage object.
     */
    public MembershipPackage() {}

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
     * Constructs a User object.
     */
    public User() {}

    /**
     * Gets the ID of the user.
     * 
     * @return the user's ID
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
     * @return the user's email
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
     * @return the user's phone number
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
 * Represents a driver in the system, extending the User class.
 */
class Driver extends User {
    private List<Trip> trips;

    /**
     * Constructs a Driver object.
     */
    public Driver() {
        this.trips = new ArrayList<>();
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 the first trip to compare
     * @param trip2 the second trip to compare
     * @return true if any stop is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        stops1.retainAll(stops2);
        return !stops1.isEmpty();
    }

    /**
     * Checks if a driver can post a new trip without time conflicts with existing trips.
     * 
     * @param newTrip the new trip to be posted
     * @return true if there are no time conflicts, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date newDepartureTime = sdf.parse(newTrip.getDepartureTime());
            Date newArrivalTime = sdf.parse(newTrip.getArrivalTime());
            for (Trip trip : trips) {
                if (trip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                    return false;
                }
            }
        } catch (ParseException e) {
            // Handle exception
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
 * Represents a customer in the system, extending the User class.
 */
class Customer extends User {
    private MembershipPackage membershipPackage;

    /**
     * Constructs a Customer object.
     */
    public Customer() {}

    /**
     * Computes the total reward points earned by this customer in a given month.
     * 
     * @param currentMonth the month for which to compute reward points (in "yyyy-MM" format)
     * @return the total reward points earned in the given month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Booking booking : Trip.getBookingsForCustomer(this)) {
                String bookingDateStr = sdf.format(booking.getBookingDate());
                if (bookingDateStr.startsWith(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Books a trip for this customer.
     * 
     * @param trip         the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
        }
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
    private String departureTime;
    private String arrivalTime;
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    /**
     * Constructs a Trip object.
     */
    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }

    /**
     * Calculates the discounted price for a booking made by a customer.
     * 
     * @param customer    the customer making the booking
     * @param bookingTime the time of booking (in "yyyy-MM-dd HH:mm" format)
     * @return the discounted price if eligible, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDate = sdf.parse(bookingTime);
            Date departureDate = sdf.parse(departureTime);
            long diff = departureDate.getTime() - bookingDate.getTime();
            if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && diff >= 24 * 60 * 60 * 1000) {
                return price * 0.8; // 20% discount
            }
        } catch (ParseException e) {
            // Handle exception
        }
        return price;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date currentTime = new Date();
            Date departureDate = sdf.parse(departureTime);
            long diff = departureDate.getTime() - currentTime.getTime();
            if (diff > 2 * 60 * 60 * 1000) { // More than 2 hours before departure
                return bookSeats(seats);
            }
        } catch (ParseException e) {
            // Handle exception
        }
        return false;
    }

    /**
     * Gets the stop stations for this trip.
     * 
     * @return a set of stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if a new trip time conflicts with this trip's time.
     * 
     * @param newDepartureTime the departure time of the new trip
     * @param newArrivalTime   the arrival time of the new trip
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date thisDeparture = sdf.parse(this.departureTime);
            Date thisArrival = sdf.parse(this.arrivalTime);
            Date newDeparture = sdf.parse(newDepartureTime);
            Date newArrival = sdf.parse(newArrivalTime);
            return (thisDeparture.before(newArrival) && thisArrival.after(newDeparture));
        } catch (ParseException e) {
            // Handle exception
        }
        return false;
    }

    /**
     * Gets the list of bookings for a specific customer across all trips.
     * 
     * @param customer the customer for whom to retrieve bookings
     * @return a list of bookings made by the customer
     */
    public static List<Booking> getBookingsForCustomer(Customer customer) {
        List<Booking> customerBookings = new ArrayList<>();
        // This method should ideally be implemented in a repository or service class
        // For simplicity, it's assumed that all trips are accessible here
        // In a real application, you would query a database or repository for bookings
        return customerBookings;
    }

    /**
     * Gets the departure station of this trip.
     * 
     * @return the departure station
     */
    public String getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station for this trip.
     * 
     * @param departureStation the departure station to set
     */
    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Gets the arrival station of this trip.
     * 
     * @return the arrival station
     */
    public String getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station for this trip.
     * 
     * @param arrivalStation the arrival station to set
     */
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Gets the number of seats available on this trip.
     * 
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats for this trip.
     * 
     * @param numberOfSeats the number of seats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the departure time of this trip.
     * 
     * @return the departure time (in "yyyy-MM-dd HH:mm" format)
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time for this trip.
     * 
     * @param departureTime the departure time to set (in "yyyy-MM-dd HH:mm" format)
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time of this trip.
     * 
     * @return the arrival time (in "yyyy-MM-dd HH:mm" format)
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time for this trip.
     * 
     * @param arrivalTime the arrival time to set (in "yyyy-MM-dd HH:mm" format)
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the price of this trip.
     * 
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price for this trip.
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
     * Constructs a Stop object.
     */
    public Stop() {}

    /**
     * Gets the stop station name.
     * 
     * @return the stop station name
     */
    public String getStopStation() {
        return stopStation;
    }

    /**
     * Sets the stop station name.
     * 
     * @param stopStation the stop station name to set
     */
    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Represents a booking made by a customer on a trip.
 */
class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    /**
     * Constructs a Booking object.
     */
    public Booking() {}

    /**
     * Checks if this booking is eligible based on various conditions.
     * 
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date departureDate = sdf.parse(trip.getDepartureTime());
            Date currentTime = new Date();
            long diff = departureDate.getTime() - currentTime.getTime();
            if (diff <= 2 * 60 * 60 * 1000) { // Less than or equal to 2 hours before departure
                return false;
            }
            if (trip.getNumberOfSeats() < numberOfSeats) {
                return false;
            }
            // Additional checks for overlapping bookings can be added here
        } catch (ParseException e) {
            // Handle exception
        }
        return true;
    }

    /**
     * Updates the number of seats available on the trip after this booking.
     */
    public void updateTripSeats() {
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
    }

    /**
     * Checks if this booking overlaps with another trip's time.
     * 
     * @param trip the trip to check for overlap
     * @return true if there's an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Implement logic to check for overlap
        return false;
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
     * Sets the number of seats for this booking.
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
     * Sets the customer for this booking.
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
     * Sets the trip for this booking.
     * 
     * @param trip the trip to set
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Gets the date when this booking was made.
     * 
     * @return the booking date
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date for this booking.
     * 
     * @param bookingDate the booking date to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}