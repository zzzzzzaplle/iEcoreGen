import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;

abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;

    public User() {}

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
     * Determines if two indirect trips by the same driver share any common stops
     * @param trip1 the first trip to check for stop overlap
     * @param trip2 the second trip to check for stop overlap
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        
        Set<String> trip1Stops = trip1.getStopStations();
        Set<String> trip2Stops = trip2.getStopStations();
        
        for (String stop : trip1Stops) {
            if (trip2Stops.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if a new trip can be posted by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflicts exist, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        
        for (Trip existingTrip : trips) {
            if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                return false;
            }
        }
        return true;
    }
}

class Customer extends User {
    private MembershipPackage membershipPackage;

    public Customer() {}

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    /**
     * Calculates total reward points earned by the customer in the given month
     * @param currentMonth the target month in format "yyyy-MM"
     * @return total points earned in the specified month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        // This method would need access to all bookings made by this customer
        // Implementation would require additional data structures to track all bookings
        return totalPoints;
    }

    /**
     * Books a trip for the customer with specified number of seats
     * @param trip the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        // Implementation would involve creating a booking and validating eligibility
    }
}

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
     * Computes the final price for a booking after applying membership discounts
     * @param customer the customer making the booking
     * @param bookingTime the time when booking is made (yyyy-MM-dd HH:mm format)
     * @return the final price after discount (1 decimal place) or original price if conditions not met
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
        LocalDateTime departureDateTime = LocalDateTime.parse(departureTime, formatter);
        
        long hoursDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toHours();
        
        if (customer.getMembershipPackage() != null && 
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && 
            hoursDifference >= 24) {
            return Math.round(price * 0.8 * 10) / 10.0;
        }
        return price;
    }

    /**
     * Books specified number of seats on the trip
     * @param num the number of seats to book
     * @return true if booking was successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= numberOfSeats && num > 0) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms a booking by checking eligibility and updating seats
     * @param seats the number of seats to confirm
     * @return true if booking confirmation was successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        // This would involve more comprehensive validation
        return bookSeats(seats);
    }

    /**
     * Gets all stop stations as a set for overlap checking
     * @return set of all stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if a new trip time conflicts with this trip's time period
     * @param newDepartureTime the new trip's departure time (yyyy-MM-dd HH:mm)
     * @param newArrivalTime the new trip's arrival time (yyyy-MM-dd HH:mm)
     * @return true if time periods conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newDeparture = LocalDateTime.parse(newDepartureTime, formatter);
        LocalDateTime newArrival = LocalDateTime.parse(newArrivalTime, formatter);
        LocalDateTime currentDeparture = LocalDateTime.parse(departureTime, formatter);
        LocalDateTime currentArrival = LocalDateTime.parse(arrivalTime, formatter);
        
        // Check for time overlap (excluding adjacent boundaries)
        return (newDeparture.isBefore(currentArrival) && newArrival.isAfter(currentDeparture));
    }
}

class Stop {
    private String stopStation;

    public Stop() {}

    public String getStopStation() {
        return stopStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }
}

class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    public Booking() {}

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
     * Checks if the booking is eligible based on various constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check available seats
        if (numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        // Check booking time constraint (2 hours before departure)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(
            bookingDate.toString().substring(0, 16), formatter);
        LocalDateTime departureDateTime = LocalDateTime.parse(trip.getDepartureTime(), formatter);
        
        long minutesDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toMinutes();
        if (minutesDifference <= 120) {
            return false;
        }
        
        // Check for overlapping time periods with customer's existing bookings
        // This would require access to all customer bookings
        
        return true;
    }

    /**
     * Updates the trip's available seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null && isBookingEligible()) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if this booking overlaps in time with another trip
     * @param trip the trip to check overlap with
     * @return true if time periods overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Implementation would compare time periods
        return false;
    }
}

class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {}

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    /**
     * Checks if the membership package includes a specific award type
     * @param award the award type to check for
     * @return true if the award is included, false otherwise
     */
    public boolean hasAward(Award award) {
        if (awards == null) return false;
        for (Award a : awards) {
            if (a == award) {
                return true;
            }
        }
        return false;
    }
}

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}