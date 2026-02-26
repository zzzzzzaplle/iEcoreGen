import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;

abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;
    
    public User() {
    }
    
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
        trips = new ArrayList<>();
    }
    
    public List<Trip> getTrips() {
        return trips;
    }
    
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
    
    /**
     * Check if two indirect trips by the same driver share any common stops
     * @param trip1 the first trip to check
     * @param trip2 the second trip to check
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        
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
     * Validate if a driver can post a new trip by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflict exists, false otherwise
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
    private List<Booking> bookings;
    
    public Customer() {
        bookings = new ArrayList<>();
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
     * Calculate total reward points earned by a customer in the given current month
     * @param currentMonth the target month in format "yyyy-MM"
     * @return total points earned in the target month
     * @throws IllegalArgumentException if currentMonth format is invalid
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        if (currentMonth == null || !currentMonth.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid month format. Expected yyyy-MM");
        }
        
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getBookingDate() != null) {
                String bookingMonth = booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
                if (bookingMonth.equals(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }
    
    /**
     * Book seats on a trip
     * @param trip the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(LocalDateTime.now());
        
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            bookings.add(booking);
            trip.getBookings().add(booking);
        }
    }
}

class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureDate;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private List<Booking> bookings;
    private List<Stop> stops;
    
    public Trip() {
        bookings = new ArrayList<>();
        stops = new ArrayList<>();
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
    
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }
    
    public void setDepartureDate(LocalDateTime departureDate) {
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
     * Compute the final price for a booking after applying reward discounts
     * @param customer the customer making the booking
     * @param bookingTime the booking time in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount (1 decimal place) or original price if conditions not met
     * @throws IllegalArgumentException if bookingTime format is invalid
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null || bookingTime == null) {
            return price;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime;
        LocalDateTime departureDateTime;
        
        try {
            bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
            departureDateTime = LocalDateTime.parse(departureTime, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date/time format. Expected yyyy-MM-dd HH:mm");
        }
        
        long hoursDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toHours();
        
        if (customer.getMembershipPackage() != null && 
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && 
            hoursDifference >= 24) {
            double discountedPrice = price * 0.8;
            return Math.round(discountedPrice * 10.0) / 10.0;
        }
        
        return price;
    }
    
    /**
     * Book seats on this trip
     * @param num the number of seats to book
     * @return true if booking was successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0 || num > numberOfSeats) {
            return false;
        }
        numberOfSeats -= num;
        return true;
    }
    
    /**
     * Confirm booking by checking eligibility and updating seats
     * @param seats the number of seats to book
     * @return true if booking is confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        if (seats <= 0 || seats > numberOfSeats) {
            return false;
        }
        return true;
    }
    
    /**
     * Get all stop stations as a set
     * @return set of all stop stations including departure and arrival
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        stopStations.add(departureStation);
        stopStations.add(arrivalStation);
        
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        
        return stopStations;
    }
    
    /**
     * Check if a new trip time conflicts with this trip's time period
     * @param newDepartureTime the new departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime the new arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if time periods intersect (excluding adjacent boundaries), false otherwise
     * @throws IllegalArgumentException if time format is invalid
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        try {
            LocalDateTime newDeparture = LocalDateTime.parse(newDepartureTime, formatter);
            LocalDateTime newArrival = LocalDateTime.parse(newArrivalTime, formatter);
            LocalDateTime currentDeparture = LocalDateTime.parse(departureTime, formatter);
            LocalDateTime currentArrival = LocalDateTime.parse(arrivalTime, formatter);
            
            // Check if time periods intersect (excluding adjacent boundaries)
            return (newDeparture.isBefore(currentArrival) && newArrival.isAfter(currentDeparture)) ||
                   (currentDeparture.isBefore(newArrival) && currentArrival.isAfter(newDeparture));
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date/time format. Expected yyyy-MM-dd HH:mm");
        }
    }
}

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

class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private LocalDateTime bookingDate;
    
    public Booking() {
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
     * Check if a booking is eligible based on availability and timing constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check if trip has available seats
        if (numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        // Check if booking is at least 2 hours before departure
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departureDateTime = LocalDateTime.parse(trip.getDepartureTime(), formatter);
        long hoursDifference = java.time.Duration.between(bookingDate, departureDateTime).toHours();
        
        if (hoursDifference <= 2) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings on the same day
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.overlapsWith(trip)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Update trip seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null) {
            trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        }
    }
    
    /**
     * Check if this booking's trip time overlaps with another trip
     * @param otherTrip the trip to check against
     * @return true if time periods overlap on the same day, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (trip == null || otherTrip == null) {
            return false;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        try {
            LocalDateTime thisDeparture = LocalDateTime.parse(trip.getDepartureTime(), formatter);
            LocalDateTime thisArrival = LocalDateTime.parse(trip.getArrivalTime(), formatter);
            LocalDateTime otherDeparture = LocalDateTime.parse(otherTrip.getDepartureTime(), formatter);
            LocalDateTime otherArrival = LocalDateTime.parse(otherTrip.getArrivalTime(), formatter);
            
            // Check if trips are on the same day
            if (!thisDeparture.toLocalDate().equals(otherDeparture.toLocalDate())) {
                return false;
            }
            
            // Check for time overlap
            return (thisDeparture.isBefore(otherArrival) && thisArrival.isAfter(otherDeparture)) ||
                   (otherDeparture.isBefore(thisArrival) && otherArrival.isAfter(thisDeparture));
            
        } catch (Exception e) {
            return false;
        }
    }
}

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

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}