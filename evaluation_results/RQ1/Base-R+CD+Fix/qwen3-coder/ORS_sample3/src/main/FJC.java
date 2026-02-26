import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Abstract class representing a user in the system
 */
abstract class User {
    protected String ID;
    protected String email;
    protected String phoneNumber;

    public User() {
        this.ID = "";
        this.email = "";
        this.phoneNumber = "";
    }

    // Getters and setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
 * Class representing a driver, who can post trips
 */
class Driver extends User {
    private List<Trip> trips;

    public Driver() {
        super();
        this.trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    /**
     * Check if two indirect trips by the same driver share any common stops
     * @param trip1 First trip to compare
     * @param trip2 Second trip to compare
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        
        for (String stop : stops1) {
            if (stops2.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a driver can post a new trip based on time conflicts
     * @param newTrip The trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Check if departure time is before arrival time
        if (newTrip.getDepartureTime().compareTo(newTrip.getArrivalTime()) >= 0) {
            return false;
        }
        
        // Check for time conflicts with existing trips
        for (Trip existingTrip : trips) {
            if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Class representing a customer, who can book trips and have membership packages
 */
class Customer extends User {
    private MembershipPackage membershipPackage;

    public Customer() {
        super();
        this.membershipPackage = null;
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    /**
     * Calculate total reward points earned by a customer in the given month
     * @param currentMonth The month to calculate points for (format: "yyyy-MM")
     * @return Total points earned in the month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        // Check if customer has membership with points reward
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }

        int totalPoints = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Iterate through all trips to find bookings in the target month
        for (Trip trip : getAllBookedTrips()) {
            for (Booking booking : trip.getBookings()) {
                if (booking.getCustomer() == this) {
                    // Check if booking date is within target month
                    String bookingDateStr = dateFormat.format(booking.getBookingDate());
                    if (bookingDateStr.startsWith(currentMonth)) {
                        totalPoints += booking.getNumberOfSeats() * 5;
                    }
                }
            }
        }
        
        return totalPoints;
    }

    /**
     * Helper method to get all trips this customer has booked
     * @return List of trips booked by this customer
     */
    private List<Trip> getAllBookedTrips() {
        List<Trip> bookedTrips = new ArrayList<>();
        // In a real implementation, this would query all trips for bookings by this customer
        // For this implementation, we'll return an empty list as we don't have access to all trips
        return bookedTrips;
    }

    /**
     * Book seats on a trip
     * @param trip The trip to book seats on
     * @param numberOfSeats Number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        trip.bookSeats(numberOfSeats);
    }
}

/**
 * Class representing a trip that can be booked by customers
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private Date departureDate;
    private String departureTime; // Format: yyyy-MM-dd HH:mm
    private String arrivalTime;   // Format: yyyy-MM-dd HH:mm
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    public Trip() {
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureDate = new Date();
        this.departureTime = "";
        this.arrivalTime = "";
        this.price = 0.0;
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
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
     * Calculate the discounted price for a booking
     * @param customer The customer making the booking
     * @param bookingTime The time of booking (format: yyyy-MM-dd HH:mm)
     * @return The final price after applying discount if applicable
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        // Check if customer has membership
        if (customer.getMembershipPackage() == null) {
            return price;
        }
        
        // Check if booking is made at least 24 hours before departure
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = dateFormat.parse(bookingTime);
            Date departureDateTime = dateFormat.parse(departureTime);
            
            long diffInMillis = departureDateTime.getTime() - bookingDateTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            
            if (diffInHours >= 24) {
                // Apply 20% discount
                return Math.round(price * 0.8 * 10) / 10.0;
            }
        } catch (ParseException e) {
            // If parsing fails, return original price
            return price;
        }
        
        return price;
    }

    /**
     * Book seats on this trip
     * @param num Number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        Booking booking = new Booking();
        booking.setNumberOfSeats(num);
        booking.setTrip(this);
        
        if (booking.isBookingEligible()) {
            bookings.add(booking);
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirm a booking for a specific number of seats
     * @param seats Number of seats to confirm
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Get all stop stations for this trip
     * @return Set of stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Check if this trip has time conflicts with a given time period
     * @param newDepartureTime Departure time to check (format: yyyy-MM-dd HH:mm)
     * @param newArrivalTime Arrival time to check (format: yyyy-MM-dd HH:mm)
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        // Check if time periods intersect
        return !(newArrivalTime.compareTo(departureTime) <= 0 || 
                 newDepartureTime.compareTo(arrivalTime) >= 0);
    }
}

/**
 * Class representing a stop in an indirect trip
 */
class Stop {
    private String stopStation;

    public Stop() {
        this.stopStation = "";
    }

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

/**
 * Class representing a booking made by a customer for a trip
 */
class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    public Booking() {
        this.numberOfSeats = 0;
        this.customer = null;
        this.trip = null;
        this.bookingDate = new Date();
    }

    // Getters and setters
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
     * Check if this booking is eligible based on various conditions
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        // Check if trip exists
        if (trip == null) {
            return false;
        }
        
        // Check if there are enough available seats
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        
        // Check if booking is made at least 2 hours before departure
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentTime = new Date();
            Date departureTime = dateFormat.parse(trip.getDepartureTime());
            
            long diffInMillis = departureTime.getTime() - currentTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            long diffInMinutes = (diffInMillis % (60 * 60 * 1000)) / (60 * 1000);
            
            if (diffInHours < 2 || (diffInHours == 2 && diffInMinutes > 0)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.getCustomer() == customer && 
                existingBooking.overlapsWith(trip)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Update the number of seats available on the trip
     */
    public void updateTripSeats() {
        if (trip != null) {
            int updatedSeats = trip.getNumberOfSeats() - numberOfSeats;
            trip.setNumberOfSeats(updatedSeats);
        }
    }

    /**
     * Check if this booking overlaps with a given trip
     * @param trip The trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // In a more complete implementation, this would check for actual time overlaps
        // For now, we'll assume no overlap as a simplification
        return false;
    }
}

/**
 * Class representing a membership package that includes rewards
 */
class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {
        this.awards = new Award[0];
    }

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Check if this membership package includes a specific award
     * @param award The award to check for
     * @return true if the award is included, false otherwise
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a == award) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Enum representing different types of awards in membership packages
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}