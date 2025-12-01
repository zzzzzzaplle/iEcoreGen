import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Abstract class representing a user in the system
 */
abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    public User() {
        // Default constructor
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
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
            return false;
        }

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
     * @return true if no time conflict exists, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        // Check if departure time is before arrival time
        if (!newTrip.isDepartureBeforeArrival()) {
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
 * Class representing a customer who can book trips and have membership packages
 */
class Customer extends User {
    private MembershipPackage membershipPackage;

    public Customer() {
        // Default constructor
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
     * @return Total reward points earned
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
            // In a real implementation, we would have access to all bookings
            // This is a simplified version assuming we have access to relevant trips
            return totalPoints;
        } catch (ParseException e) {
            return 0;
        }
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

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    /**
     * Calculate the discounted price for a booking
     * @param customer The customer making the booking
     * @param bookingTime The time of booking (format: yyyy-MM-dd HH:mm)
     * @return The final price after applying discount if applicable
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        // Check if customer has membership with discounts
        if (customer.getMembershipPackage() == null || 
            !customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            return this.price;
        }

        // Check if booking is made at least 24 hours before departure
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDate = sdf.parse(bookingTime);
            Date departureDate = sdf.parse(this.departureTime);
            
            long diffInMillis = departureDate.getTime() - bookingDate.getTime();
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
     * Check if this trip's time conflicts with given time period
     * @param newDepartureTime Departure time to check (format: yyyy-MM-dd HH:mm)
     * @param newArrivalTime Arrival time to check (format: yyyy-MM-dd HH:mm)
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date thisDeparture = sdf.parse(this.departureTime);
            Date thisArrival = sdf.parse(this.arrivalTime);
            Date newDeparture = sdf.parse(newDepartureTime);
            Date newArrival = sdf.parse(newArrivalTime);

            // Check if time periods intersect (excluding adjacent boundaries)
            return !(newArrival.compareTo(thisDeparture) <= 0 || newDeparture.compareTo(thisArrival) >= 0);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Check if departure time is before arrival time
     * @return true if departure is before arrival, false otherwise
     */
    public boolean isDepartureBeforeArrival() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dep = sdf.parse(this.departureTime);
            Date arr = sdf.parse(this.arrivalTime);
            return dep.before(arr);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Book seats on this trip
     * @param num Number of seats to book
     * @return true if booking successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0) {
            return false;
        }
        
        // Create a temporary booking to check eligibility
        Booking tempBooking = new Booking();
        tempBooking.setNumberOfSeats(num);
        tempBooking.setTrip(this);
        
        if (tempBooking.isBookingEligible()) {
            tempBooking.updateTripSeats();
            this.bookings.add(tempBooking);
            return true;
        }
        return false;
    }

    /**
     * Confirm booking of seats
     * @param seats Number of seats to confirm
     * @return true if confirmation successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        if (seats <= this.numberOfSeats) {
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
        // Default constructor
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
        if (trip.getNumberOfSeats() < this.numberOfSeats) {
            return false;
        }

        // Check if booking is made at least 2 hours before departure
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentTime = new Date();
            Date departureTime = sdf.parse(trip.getDepartureTime());
            
            long diffInMillis = departureTime.getTime() - currentTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);
            long diffInMinutes = (diffInMillis % (60 * 60 * 1000)) / (60 * 1000);
            
            if (diffInHours < 2 || (diffInHours == 2 && diffInMinutes <= 0)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        // Check for overlapping time periods with existing bookings
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking != this && existingBooking.overlapsWith(trip)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Update the number of seats available in the trip
     */
    public void updateTripSeats() {
        if (trip != null) {
            int currentSeats = trip.getNumberOfSeats();
            trip.setNumberOfSeats(currentSeats - this.numberOfSeats);
        }
    }

    /**
     * Check if this booking overlaps with given trip in time
     * @param trip The trip to check overlap with
     * @return true if there is overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Simplified implementation - in a real system, we would need more detailed time checking
        // For now, we assume all bookings for the same trip on the same day overlap
        return true;
    }
}

/**
 * Class representing a membership package with rewards
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
     * Check if this membership package has a specific award
     * @param award The award to check for
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
}

/**
 * Enum representing different types of awards in membership packages
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}