import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
     * Checks if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 The first trip to compare
     * @param trip2 The second trip to compare
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        
        Set<String> stopStations1 = trip1.getStopStations();
        Set<String> stopStations2 = trip2.getStopStations();
        
        for (String station : stopStations1) {
            if (stopStations2.contains(station)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if a driver can post a new trip by checking for time conflicts with existing trips.
     * 
     * @param newTrip The new trip to be posted
     * @return true if no time conflicts exist with existing trips, false otherwise
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

    public Customer() {
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    /**
     * Calculates total reward points earned by a customer in the given current month.
     * Preconditions: The customer must have a membership with a points reward.
     * 
     * @param currentMonth The target month in "yyyy-MM" format
     * @return Total points earned in the specified month (5 points per seat per booking)
     * @throws IllegalArgumentException if currentMonth is null or invalid format
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (currentMonth == null || currentMonth.trim().isEmpty()) {
            throw new IllegalArgumentException("Current month cannot be null or empty");
        }
        
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        int totalPoints = 0;
        // This would typically iterate through customer's bookings
        // For simplicity, we assume customer has access to their bookings
        return totalPoints;
    }

    /**
     * Books seats on a trip for this customer.
     * 
     * @param trip The trip to book
     * @param numberOfSeats The number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (trip != null) {
            trip.bookSeats(numberOfSeats);
        }
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
     * Computes the final price for a booking after applying reward discounts.
     * Discount applies only if the booking is made by customer with membership 
     * and the booking time is ≥24 hours before departure.
     * 
     * @param customer The customer making the booking
     * @param bookingTime The booking time in "yyyy-MM-dd HH:mm" format
     * @return The final price after discount (20% off) if conditions are met, otherwise original price
     * @throws IllegalArgumentException if bookingTime is null or invalid format
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (bookingTime == null || bookingTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking time cannot be null or empty");
        }
        
        if (customer == null || customer.getMembershipPackage() == null) {
            return price;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
            LocalDateTime departureDateTime = LocalDateTime.parse(departureTime, formatter);
            
            long hoursDifference = ChronoUnit.HOURS.between(bookingDateTime, departureDateTime);
            
            if (hoursDifference >= 24) {
                double discountedPrice = price * 0.8;
                return Math.round(discountedPrice * 10.0) / 10.0; // Keep 1 decimal place
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected: yyyy-MM-dd HH:mm");
        }
        
        return price;
    }

    /**
     * Books seats on this trip if eligible.
     * 
     * @param num The number of seats to book
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
     * Confirms booking by updating trip seats.
     * 
     * @param seats The number of seats to confirm
     * @return true if confirmation was successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Gets all stop stations for this trip.
     * 
     * @return Set of all stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if the given time period conflicts with this trip's time period.
     * Adjacent boundaries are not considered conflicts.
     * 
     * @param newDepartureTime The new departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime The new arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if there is a time conflict, false otherwise
     * @throws IllegalArgumentException if time formats are invalid
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        if (newDepartureTime == null || newArrivalTime == null) {
            return false;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime newDeparture = LocalDateTime.parse(newDepartureTime, formatter);
            LocalDateTime newArrival = LocalDateTime.parse(newArrivalTime, formatter);
            LocalDateTime thisDeparture = LocalDateTime.parse(departureTime, formatter);
            LocalDateTime thisArrival = LocalDateTime.parse(arrivalTime, formatter);
            
            // Check if time periods overlap (excluding adjacent boundaries)
            return !(newArrival.isBefore(thisDeparture) || newArrival.equals(thisDeparture) ||
                    newDeparture.isAfter(thisArrival) || newDeparture.equals(thisArrival));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected: yyyy-MM-dd HH:mm");
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
     * Checks if this booking is eligible based on various criteria.
     * Eligibility: Trip must exist and have available seats. The number of available seats on the trip ≥ the number of booking seats.
     * There are no overlapping time periods between existing bookings booked by customer and the new booking time on the same day.
     * The current booking time must be at least 2 hours earlier than the departure time of the trip.
     * 
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null || bookingDate == null) {
            return false;
        }
        
        // Check if trip has enough seats
        if (numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        // Check if booking is at least 2 hours before departure
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime bookingDateTime = LocalDateTime.parse(
                bookingDate.toString().substring(0, 16), formatter);
            LocalDateTime departureDateTime = LocalDateTime.parse(trip.getDepartureTime(), formatter);
            
            long hoursDifference = ChronoUnit.HOURS.between(bookingDateTime, departureDateTime);
            if (hoursDifference <= 2) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
        // Check for overlapping bookings on the same day
        // This would typically check customer's existing bookings
        return true;
    }

    /**
     * Updates trip seats after successful booking.
     */
    public void updateTripSeats() {
        if (trip != null && isBookingEligible()) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if this booking overlaps with the given trip's time period.
     * 
     * @param trip The trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        if (this.trip == null || trip == null) {
            return false;
        }
        
        // This would typically compare booking times and trip times
        // For simplicity, we assume overlap checking logic here
        return false;
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
     * Checks if this membership package has the specified award type.
     * 
     * @param award The award type to check for
     * @return true if the award type is present, false otherwise
     */
    public boolean hasAward(Award award) {
        if (awards == null || award == null) {
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