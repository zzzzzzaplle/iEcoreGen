import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Online Rideshare System.
 */
class User {
    private String userID;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {}

    // Getters and setters
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }
}

/**
 * Represents a driver in the Online Rideshare System.
 */
class Driver extends User {
    private List<Trip> trips;

    public Driver() {
        trips = new ArrayList<>();
    }

    public List<Trip> getTrips() { return trips; }
    public void setTrips(List<Trip> trips) { this.trips = trips; }
    
    public void addTrip(Trip trip) { this.trips.add(trip); }
}

/**
 * Represents a customer in the Online Rideshare System.
 */
class Customer extends User {
    private List<Booking> bookings;

    public Customer() {
        bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
    
    public void addBooking(Booking booking) { this.bookings.add(booking); }
}

/**
 * Represents a membership package with rewards.
 */
class Membership {
    private String type; // "cashback", "discount", "points"
    private boolean active;

    public Membership() {}

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

/**
 * Represents a trip in the Online Rideshare System.
 */
class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<String> stops; // For indirect trips

    public Trip() {
        stops = new ArrayList<>();
    }

    // Getters and setters
    public String getDepartureStation() { return departureStation; }
    public void setDepartureStation(String departureStation) { this.departureStation = departureStation; }
    
    public String getArrivalStation() { return arrivalStation; }
    public void setArrivalStation(String arrivalStation) { this.arrivalStation = arrivalStation; }
    
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
    
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public List<String> getStops() { return stops; }
    public void setStops(List<String> stops) { this.stops = stops; }
    
    public void addStop(String stop) { this.stops.add(stop); }
}

/**
 * Represents a booking made by a customer for a trip.
 */
class Booking {
    private Customer customer;
    private Trip trip;
    private int numberOfSeats;
    private LocalDateTime bookingTime;
    private double finalPrice;

    public Booking() {}

    // Getters and setters
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Trip getTrip() { return trip; }
    public void setTrip(Trip trip) { this.trip = trip; }
    
    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
    
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    
    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }
}

/**
 * Main system class implementing the functional requirements.
 */
 class OnlineRideshareSystem {
    
    /**
     * Validates if a customer can book seats on a trip based on several criteria.
     * Criteria: Trip must exist and have available seats, no seat overlap, no time conflict,
     * and booking must be at least 2 hours before departure.
     *
     * @param customer The customer attempting to book
     * @param trip The trip to book seats on
     * @param numberOfSeats The number of seats requested
     * @param bookingTime The time of booking
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int numberOfSeats, LocalDateTime bookingTime) {
        // Check if trip exists and has enough available seats
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        // Check if booking is at least 2 hours before departure (excluding exactly 2 hours)
        LocalDateTime twoHoursBeforeDeparture = trip.getDepartureTime().minusHours(2);
        if (!bookingTime.isBefore(twoHoursBeforeDeparture)) {
            return false;
        }

        // Check for overlapping time periods between existing bookings and new booking on the same day
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.getTrip().getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                // Check if time periods overlap
                LocalDateTime existingStart = existingBooking.getTrip().getDepartureTime();
                LocalDateTime existingEnd = existingBooking.getTrip().getArrivalTime();
                LocalDateTime newStart = trip.getDepartureTime();
                LocalDateTime newEnd = trip.getArrivalTime();
                
                // Check for overlap: max(start1, start2) < min(end1, end2)
                if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                    return false;
                }
            }
        }

        // If all checks pass, update the number of seats and return true
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        return true;
    }
    
    /**
     * Calculates the discounted trip price based on membership and booking timing.
     * Applies a 20% discount if customer has membership and booking is made at least 24 hours before departure.
     *
     * @param customer The customer making the booking
     * @param trip The trip being booked
     * @param bookingTime The time of booking
     * @return The final price after applying discount if applicable, otherwise the original price
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, LocalDateTime bookingTime) {
        // Check if customer has membership and booking is made at least 24 hours before departure
        if (customer.getMembership() != null && 
            customer.getMembership().isActive() &&
            bookingTime.isBefore(trip.getDepartureTime().minusHours(24))) {
            
            // Apply 20% discount
            double discountedPrice = trip.getPrice() * 0.8;
            return Math.round(discountedPrice * 10) / 10.0; // Keep 1 decimal place
        }
        
        // Return original price if conditions are not met
        return trip.getPrice();
    }
    
    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 The first trip to compare
     * @param trip2 The second trip to compare
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        // Check if both trips have stops
        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
            return false;
        }
        
        // Check for common stops
        for (String stop1 : trip1.getStops()) {
            for (String stop2 : trip2.getStops()) {
                if (stop1.equals(stop2)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Computes the total reward points earned by a customer in the given current month.
     * Only calculates bookings whose booking dates are within the target month.
     * Rewards 5 points per seat per booking.
     *
     * @param customer The customer to calculate points for
     * @param targetMonth The month to calculate points for (1-12)
     * @param targetYear The year to calculate points for
     * @return Total reward points earned in the specified month
     */
    public int computeMonthlyRewardPoints(Customer customer, int targetMonth, int targetYear) {
        // Check if customer has a membership with points reward
        if (customer.getMembership() == null || 
            !customer.getMembership().isActive() || 
            !"points".equals(customer.getMembership().getType())) {
            return 0;
        }
        
        int totalPoints = 0;
        
        // Calculate points for eligible bookings
        for (Booking booking : customer.getBookings()) {
            LocalDateTime bookingDate = booking.getBookingTime();
            
            // Check if booking date is within the target month and year
            if (bookingDate.getMonthValue() == targetMonth && bookingDate.getYear() == targetYear) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        
        return totalPoints;
    }
    
    /**
     * Validates if a driver can post a new trip based on time conflicts with existing trips.
     * Time conflict occurs when time periods intersect (completely identical periods are conflicts).
     * Adjacent boundaries are not considered conflicts.
     *
     * @param driver The driver posting the trip
     * @param newTrip The new trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        // Check if driver exists and is valid
        if (driver == null) {
            return false;
        }
        
        // Check if new trip time is valid (departure < arrival)
        if (!newTrip.getDepartureTime().isBefore(newTrip.getArrivalTime())) {
            return false;
        }
        
        // Check for time conflicts with existing trips
        for (Trip existingTrip : driver.getTrips()) {
            LocalDateTime existingStart = existingTrip.getDepartureTime();
            LocalDateTime existingEnd = existingTrip.getArrivalTime();
            LocalDateTime newStart = newTrip.getDepartureTime();
            LocalDateTime newEnd = newTrip.getArrivalTime();
            
            // Check for intersection: max(start1, start2) < min(end1, end2)
            // This also covers completely identical time periods
            if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                return false;
            }
        }
        
        return true;
    }
}