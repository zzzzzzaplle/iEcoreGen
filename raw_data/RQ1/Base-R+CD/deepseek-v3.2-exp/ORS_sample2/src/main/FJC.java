import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;

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
     * Determines if two indirect trips by the same driver share any common stops
     * @param trip1 the first trip to check for common stops
     * @param trip2 the second trip to check for common stops
     * @return true if any stop station is shared betwen the two trips, false otherwise
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
     * Validates if a driver can post a new trip by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflicts exist and the driver is valid, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (this.getID() == null || this.getID().isEmpty()) {
            return false;
        }
        
        if (newTrip == null) {
            return false;
        }
        
        // Check if departure time is before arrival time
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departure = LocalDateTime.parse(newTrip.getDepartureDate() + " " + newTrip.getDepartureTime(), formatter);
            LocalDateTime arrival = LocalDateTime.parse(newTrip.getDepartureDate() + " " + newTrip.getArrivalTime(), formatter);
            
            if (!departure.isBefore(arrival)) {
                return false;
            }
        } catch (DateTimeParseException e) {
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
     * Calculates total reward points earned by a customer in the given current month
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned (sum of seats * 5 for eligible bookings in the target month)
     * @throws IllegalArgumentException if currentMonth is not in valid format
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        try {
            YearMonth targetMonth = YearMonth.parse(currentMonth);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid month format. Expected: yyyy-MM");
        }
        
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getBookingDate() != null) {
                String bookingMonth = booking.getBookingDate().toString().substring(0, 7);
                if (bookingMonth.equals(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }
    
    /**
     * Books a trip for the customer with the specified number of seats
     * @param trip the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
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
    private String departureDate;
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
    
    public List<Stop> getStops() {
        return stops;
    }
    
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
    
    /**
     * Computes the final price for a booking after applying reward discounts
     * @param customer the customer making the booking
     * @param bookingTime the booking time in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount (20% off) if conditions are met, otherwise original price
     * @throws DateTimeParseException if bookingTime or departure time format is invalid
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null || bookingTime == null) {
            return price;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
            LocalDateTime departureDateTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
            
            // Check if booking is made at least 24 hours before departure
            if (bookingDateTime.isBefore(departureDateTime.minusHours(24))) {
                if (customer.getMembershipPackage() != null && 
                    customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
                    double discountedPrice = price * 0.8;
                    return Math.round(discountedPrice * 10.0) / 10.0; // Keep 1 decimal place
                }
            }
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date/time format", e.getParsedString(), e.getErrorIndex());
        }
        
        return price;
    }
    
    /**
     * Books seats on the trip if eligible
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
     * Confirms if booking is possible for the given number of seats
     * @param seats the number of seats to book
     * @return true if seats are available, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return seats > 0 && seats <= numberOfSeats;
    }
    
    /**
     * Gets all stop stations including departure and arrival stations
     * @return set of all stop stations
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
     * Checks if a new trip time conflicts with this trip's time period
     * @param newDepartureTime the new departure time in "HH:mm" format
     * @param newArrivalTime the new arrival time in "HH:mm" format
     * @return true if time periods intersect (excluding adjacent boundaries), false otherwise
     * @throws DateTimeParseException if time format is invalid
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            
            LocalDateTime currentDeparture = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
            LocalDateTime currentArrival = LocalDateTime.parse(departureDate + " " + arrivalTime, formatter);
            LocalDateTime newDeparture = LocalDateTime.parse(departureDate + " " + newDepartureTime, formatter);
            LocalDateTime newArrival = LocalDateTime.parse(departureDate + " " + newArrivalTime, formatter);
            
            // Check for time intersection (excluding adjacent boundaries)
            return (newDeparture.isBefore(currentArrival) && newArrival.isAfter(currentDeparture));
            
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid time format", e.getParsedString(), e.getErrorIndex());
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
    private Date bookingDate;
    
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
    
    public Date getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    /**
     * Checks if a booking is eligible based on system constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check if trip has available seats
        if (numberOfSeats <= 0 || numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        // Check if booking is made at least 2 hours before departure
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departureDateTime = LocalDateTime.parse(trip.getDepartureDate() + " " + trip.getDepartureTime(), formatter);
            LocalDateTime bookingDateTime = LocalDateTime.now(); // Using current time for simulation
            
            if (!bookingDateTime.isBefore(departureDateTime.minusHours(2))) {
                return false;
            }
        } catch (DateTimeParseException e) {
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
     * Updates the trip seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null) {
            trip.bookSeats(numberOfSeats);
        }
    }
    
    /**
     * Checks if this booking overlaps in time with another trip
     * @param otherTrip the trip to check for time overlap
     * @return true if time periods overlap on the same day, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (trip == null || otherTrip == null || !trip.getDepartureDate().equals(otherTrip.getDepartureDate())) {
            return false;
        }
        
        return trip.isTimeConflicting(otherTrip.getDepartureTime(), otherTrip.getArrivalTime());
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
    
    /**
     * Checks if the membership package contains a specific award type
     * @param award the award type to check for
     * @return true if the award is included in the package, false otherwise
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

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}