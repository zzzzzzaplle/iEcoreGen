import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * @param award the award to check
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
     * Gets the awards associated with the membership package.
     * 
     * @return the awards
     */
    public Award[] getAwards() {
        return awards;
    }

    /**
     * Sets the awards for the membership package.
     * 
     * @param awards the awards to set
     */
    public void setAwards(Award[] awards) {
        this.awards = awards;
    }
}

/**
 * Abstract class representing a user in the system.
 */
abstract class User {
    protected String ID;
    protected String email;
    protected String phoneNumber;

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
     * Default constructor for Driver.
     */
    public Driver() {
        this.trips = new ArrayList<>();
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
     * Checks if a driver can post a new trip without time conflicts.
     * 
     * @param newTrip the new trip to be posted
     * @return true if the trip can be posted, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        for (Trip trip : trips) {
            if (trip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the trips associated with the driver.
     * 
     * @return the trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Sets the trips for the driver.
     * 
     * @param trips the trips to set
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
     * Default constructor for Customer.
     */
    public Customer() {
    }

    /**
     * Computes the monthly reward points for the customer.
     * 
     * @param currentMonth the current month in "yyyy-MM" format
     * @return the total reward points
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Booking booking : getBookings()) {
            String bookingDateStr = sdf.format(booking.getBookingDate());
            if (bookingDateStr.startsWith(currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Gets the bookings associated with the customer.
     * 
     * @return the bookings
     */
    private List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        // Assuming there's a way to get bookings for a customer
        return bookings;
    }

    /**
     * Gets the membership package of the customer.
     * 
     * @return the membershipPackage
     */
    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    /**
     * Sets the membership package for the customer.
     * 
     * @param membershipPackage the membershipPackage to set
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
     * Default constructor for Trip.
     */
    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
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
        Date departureDateTime = sdf.parse(sdf.format(departureDate) + " " + departureTime);
        long diff = departureDateTime.getTime() - bookingDate.getTime();
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)
                && diff >= 24 * 60 * 60 * 1000) {
            return price * 0.8;
        }
        return price;
    }

    /**
     * Books seats on the trip.
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
     * Confirms a booking.
     * 
     * @param seats the number of seats booked
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        // Logic to confirm booking
        return true;
    }

    /**
     * Gets the stop stations associated with the trip.
     * 
     * @return the stop stations
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
        Date thisDeparture = sdf.parse(sdf.format(departureDate) + " " + this.departureTime);
        Date thisArrival = sdf.parse(sdf.format(departureDate) + " " + this.arrivalTime);
        Date newDeparture = sdf.parse(newDepartureTime);
        Date newArrival = sdf.parse(newArrivalTime);
        return (thisDeparture.before(newArrival) && thisArrival.after(newDeparture));
    }

    /**
     * Gets the departure station.
     * 
     * @return the departureStation
     */
    public String getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the departure station.
     * 
     * @param departureStation the departureStation to set
     */
    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    /**
     * Gets the arrival station.
     * 
     * @return the arrivalStation
     */
    public String getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the arrival station.
     * 
     * @param arrivalStation the arrivalStation to set
     */
    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    /**
     * Gets the number of seats.
     * 
     * @return the numberOfSeats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats.
     * 
     * @param numberOfSeats the numberOfSeats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the departure date.
     * 
     * @return the departureDate
     */
    public Date getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date.
     * 
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the departure time.
     * 
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     * 
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time.
     * 
     * @return the arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time.
     * 
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Gets the price.
     * 
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     * 
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the bookings.
     * 
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the bookings.
     * 
     * @param bookings the bookings to set
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Gets the stops.
     * 
     * @return the stops
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * Sets the stops.
     * 
     * @param stops the stops to set
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
     * Gets the stop station.
     * 
     * @return the stopStation
     */
    public String getStopStation() {
        return stopStation;
    }

    /**
     * Sets the stop station.
     * 
     * @param stopStation the stopStation to set
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
     * Checks if the booking is eligible.
     * 
     * @return true if eligible, false otherwise
     * @throws ParseException if date parsing fails
     */
    public boolean isBookingEligible() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date departureDateTime = sdf.parse(sdf.format(trip.getDepartureDate()) + " " + trip.getDepartureTime());
        Date bookingDateTime = sdf.parse(sdf.format(bookingDate) + " 00:00"); // Assuming booking date is considered at midnight
        long diff = departureDateTime.getTime() - bookingDateTime.getTime();
        if (diff < 2 * 60 * 60 * 1000) {
            return false; // Less than 2 hours before departure
        }
        // Additional checks for overlapping bookings and available seats can be added here
        return true;
    }

    /**
     * Updates the trip seats after a booking.
     */
    public void updateTripSeats() {
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
    }

    /**
     * Gets the number of seats booked.
     * 
     * @return the numberOfSeats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats booked.
     * 
     * @param numberOfSeats the numberOfSeats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets the customer who made the booking.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who made the booking.
     * 
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the trip associated with the booking.
     * 
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets the trip associated with the booking.
     * 
     * @param trip the trip to set
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Gets the booking date.
     * 
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the booking date.
     * 
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
}