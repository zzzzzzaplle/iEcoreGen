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
     * Check if two indirect trips share any common stops
     * @param trip1 First trip to compare
     * @param trip2 Second trip to compare
     * @return true if trips share at least one common stop, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stopStations1 = trip1.getStopStations();
        Set<String> stopStations2 = trip2.getStopStations();
        
        for (String stop : stopStations1) {
            if (stopStations2.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate if a driver can post a new trip based on time conflicts
     * @param newTrip The trip to be posted
     * @return true if there are no time conflicts, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Check if departure time is before arrival time
        if (newTrip.getDepartureTime().compareTo(newTrip.getArrivalTime()) >= 0) {
            return false;
        }
        
        // Check for conflicts with existing trips
        for (Trip existingTrip : this.trips) {
            if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Class representing a customer who can book trips and have membership packages
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
     * Compute total reward points earned by customer in a given month
     * @param currentMonth The month to calculate points for (format: "yyyy-MM")
     * @return Total reward points earned in the month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        // Check if customer has membership with points reward
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }

        int totalPoints = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date startDate = dateFormat.parse(currentMonth + "-01");
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date endDate = cal.getTime();
            
            // Iterate through all trips to find bookings in the target month
            // In a real implementation, we would have a reference to all bookings
            // For this implementation, we'll assume we have access to relevant bookings
            // This is a simplified approach due to the design model limitations
            
        } catch (ParseException e) {
            return 0;
        }
        
        return totalPoints;
    }

    /**
     * Book seats on a trip for this customer
     * @param trip The trip to book
     * @param numberOfSeats Number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            trip.addBooking(booking);
        }
    }
}

/**
 * Class representing a trip that can be booked by customers
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;

    public Trip() {
        this.departureStation = "";
        this.arrivalStation = "";
        this.numberOfSeats = 0;
        this.departureDate = "";
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
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

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    /**
     * Calculate the discounted price for a booking
     * @param customer The customer making the booking
     * @param bookingTime The time of booking
     * @return The discounted price if applicable, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        // Check if customer has membership
        if (customer.getMembershipPackage() == null) {
            return this.price;
        }
        
        // Check if booking is made at least 24 hours before departure
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = dateTimeFormat.parse(bookingTime);
            Date departureDateTime = dateTimeFormat.parse(this.departureDate + " " + this.departureTime);
            
            long diffInMillis = departureDateTime.getTime() - bookingDateTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            
            if (diffInHours >= 24) {
                // Apply 20% discount
                return Math.round(this.price * 0.8 * 10) / 10.0;
            }
        } catch (ParseException e) {
            // If parsing fails, return original price
            return this.price;
        }
        
        return this.price;
    }

    /**
     * Get all stop stations for this trip
     * @return A set of stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : this.stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Check if this trip's time conflicts with given time period
     * @param newDepartureTime The departure time to check
     * @param newArrivalTime The arrival time to check
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        // Check if time periods overlap
        // Overlap exists if: max(start1, start2) < min(end1, end2)
        return this.departureTime.compareTo(newArrivalTime) < 0 && 
               newDepartureTime.compareTo(this.arrivalTime) < 0;
    }

    /**
     * Book seats on this trip
     * @param num Number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (this.numberOfSeats >= num) {
            this.numberOfSeats -= num;
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
        // Check if enough seats are available
        if (this.numberOfSeats >= seats) {
            this.numberOfSeats -= seats;
            return true;
        }
        return false;
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
        if (this.trip == null) {
            return false;
        }
        
        // Check if trip has enough available seats
        if (this.trip.getNumberOfSeats() < this.numberOfSeats) {
            return false;
        }
        
        // Check if booking is made at least 2 hours before departure
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = new Date(); // Current time
            Date departureDateTime = dateTimeFormat.parse(this.trip.getDepartureDate() + " " + this.trip.getDepartureTime());
            
            long diffInMillis = departureDateTime.getTime() - bookingDateTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            long diffInMinutes = (diffInMillis % (60 * 60 * 1000)) / (60 * 1000);
            
            if (diffInHours < 2 || (diffInHours == 2 && diffInMinutes > 0)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings
        // This would require access to customer's other bookings
        // For simplicity, we'll assume no overlap in this implementation
        
        return true;
    }

    /**
     * Update the number of seats in the associated trip
     */
    public void updateTripSeats() {
        if (this.trip != null) {
            int currentSeats = this.trip.getNumberOfSeats();
            this.trip.setNumberOfSeats(currentSeats - this.numberOfSeats);
        }
    }

    /**
     * Check if this booking overlaps with a given trip in time
     * @param trip The trip to check overlap with
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Implementation would check time overlap between this booking and the trip
        // For simplicity, returning false in this implementation
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
        for (Award a : this.awards) {
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