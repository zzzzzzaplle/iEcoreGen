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
 * Class representing a driver in the system
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

    /**
     * Check if two indirect trips share any common stops
     * @param trip1 First trip to compare
     * @param trip2 Second trip to compare
     * @return true if any stop station is shared, false otherwise
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
     * @param newTrip The new trip to be posted
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
 * Class representing a customer in the system
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
     * @return Total reward points earned in the given month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        // Check if customer has membership with points reward
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }

        int totalPoints = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date monthStart = dateFormat.parse(currentMonth + "-01");
            Calendar cal = Calendar.getInstance();
            cal.setTime(monthStart);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthEnd = cal.getTime();
            
            // Calculate points for eligible bookings
            for (Trip trip : getAllBookedTrips()) {
                for (Booking booking : trip.getBookings()) {
                    if (booking.getCustomer().equals(this)) {
                        Date bookingDate = booking.getBookingDate();
                        if (bookingDate.compareTo(monthStart) >= 0 && bookingDate.compareTo(monthEnd) <= 0) {
                            totalPoints += booking.getNumberOfSeats() * 5;
                        }
                    }
                }
            }
        } catch (ParseException e) {
            // Handle parse exception
            return 0;
        }
        
        return totalPoints;
    }

    /**
     * Helper method to get all trips booked by this customer
     * @return List of trips booked by this customer
     */
    private List<Trip> getAllBookedTrips() {
        List<Trip> bookedTrips = new ArrayList<>();
        // This would need to be implemented based on how bookings are stored in the system
        return bookedTrips;
    }

    /**
     * Book seats on a trip for this customer
     * @param trip The trip to book seats on
     * @param numberOfSeats The number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            trip.getBookings().add(booking);
        }
    }
}

/**
 * Class representing a trip in the system
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
     * Calculate the discounted trip price based on customer membership and booking time
     * @param customer The customer making the booking
     * @param bookingTime The time of booking (format: "yyyy-MM-dd HH:mm")
     * @return The final price after applying discount if applicable
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        // Check if customer has membership with discount award
        MembershipPackage membership = customer.getMembershipPackage();
        if (membership == null || !membership.hasAward(Award.DISCOUNTS)) {
            return this.price;
        }

        try {
            // Parse booking time and departure time
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = dateTimeFormat.parse(bookingTime);
            Date departureDateTime = dateTimeFormat.parse(this.departureDate.toString() + " " + this.departureTime);

            // Check if booking is made at least 24 hours before departure
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
     * Check if this trip has time conflicts with another time period
     * @param newDepartureTime The departure time to check
     * @param newArrivalTime The arrival time to check
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        // Time conflict exists if intervals overlap
        // Overlap condition: start1 < end2 AND start2 < end1
        // But we exclude adjacent boundaries
        return this.departureTime.compareTo(newArrivalTime) < 0 && 
               newDepartureTime.compareTo(this.arrivalTime) < 0;
    }

    /**
     * Book seats on this trip
     * @param num Number of seats to book
     * @return true if booking successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= this.numberOfSeats) {
            this.numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirm booking of seats
     * @param seats Number of seats to confirm
     * @return true if booking confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }
}

/**
 * Class representing a stop in a trip
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
 * Class representing a booking in the system
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
     * Check if this booking is eligible based on various constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        // Check if trip exists and has available seats
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // Check if booking is made at least 2 hours before departure
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentTime = new Date();
            Date departureDateTime = dateTimeFormat.parse(trip.getDepartureDate().toString() + " " + trip.getDepartureTime());

            // Calculate time difference
            long diffInMillis = departureDateTime.getTime() - currentTime.getTime();
            long diffInHours = diffInMillis / (60 * 60 * 1000);

            if (diffInHours < 2) {
                return false;
            }

            // Check for overlapping bookings on the same day
            Calendar currentCal = Calendar.getInstance();
            currentCal.setTime(currentTime);
            
            for (Booking existingBooking : trip.getBookings()) {
                if (existingBooking.getCustomer().equals(this.customer)) {
                    Calendar existingCal = Calendar.getInstance();
                    existingCal.setTime(existingBooking.getBookingDate());
                    
                    // Check if bookings are on the same day
                    if (currentCal.get(Calendar.YEAR) == existingCal.get(Calendar.YEAR) &&
                        currentCal.get(Calendar.DAY_OF_YEAR) == existingCal.get(Calendar.DAY_OF_YEAR)) {
                        // Check if time periods overlap (this is a simplified check)
                        // In a real implementation, you'd need more detailed time overlap logic
                        return false;
                    }
                }
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Update the number of seats available on the trip
     */
    public void updateTripSeats() {
        int newSeatCount = trip.getNumberOfSeats() - numberOfSeats;
        trip.setNumberOfSeats(newSeatCount);
    }

    /**
     * Check if this booking overlaps with another trip
     * @param trip The trip to check overlap with
     * @return true if there is overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // This would need implementation based on specific overlap logic
        return false;
    }
}

/**
 * Class representing a membership package
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
     * @return true if the award exists, false otherwise
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
 * Enum representing different types of awards
 */
enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}