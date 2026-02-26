import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

class User {
    private String id;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

class Membership {
    private String type;
    private Reward reward;

    public Membership() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}

class Reward {
    private String rewardType;

    public Reward() {
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}

class Trip {
    private String id;
    private User driver;
    private String departureStation;
    private String arrivalStation;
    private int availableSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;
    private List<Booking> bookings;

    public Trip() {
        stops = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Validates if a customer can book seats on this trip
     * 
     * @param customer the customer attempting to make the booking
     * @param requestedSeats the number of seats requested for booking
     * @param bookingTime the time when the booking is being made
     * @return true if the booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(User customer, int requestedSeats, LocalDateTime bookingTime) {
        // Check if trip exists and has available seats
        if (this.id == null || this.availableSeats < requestedSeats) {
            return false;
        }
        
        // Check if booking is at least 2 hours before departure (excluding exactly 2 hours)
        if (!bookingTime.isBefore(departureTime.minusHours(2))) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings on the same day
        for (Booking existingBooking : bookings) {
            if (existingBooking.getCustomer().equals(customer)) {
                LocalDateTime existingDeparture = existingBooking.getTrip().getDepartureTime();
                LocalDateTime existingArrival = existingBooking.getTrip().getArrivalTime();
                
                // Check if same day and time periods overlap
                if (departureTime.toLocalDate().equals(existingDeparture.toLocalDate()) &&
                    departureTime.isBefore(existingArrival) && 
                    arrivalTime.isAfter(existingDeparture)) {
                    return false;
                }
            }
        }
        
        // Update available seats if booking is eligible
        this.availableSeats -= requestedSeats;
        return true;
    }

    /**
     * Calculates discounted trip price based on membership and booking time
     * 
     * @param customer the customer making the booking
     * @param bookingTime the time when the booking is being made
     * @return the final price after discount (1 decimal place) or original price
     */
    public double calculateDiscountedTripPrice(User customer, LocalDateTime bookingTime) {
        double finalPrice = this.price;
        
        // Check if customer has membership and booking is ≥24 hours before departure
        if (customer.getMembership() != null && 
            bookingTime.isBefore(departureTime.minusHours(24))) {
            // Apply 20% discount
            finalPrice = this.price * 0.8;
        }
        
        // Keep 1 decimal place
        return Math.round(finalPrice * 10.0) / 10.0;
    }

    /**
     * Checks if this indirect trip shares any common stops with another trip by the same driver
     * 
     * @param otherTrip the other trip to compare against
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip otherTrip) {
        // Check if both trips are indirect (have ≥1 stop) and by same driver
        if (this.stops.isEmpty() || otherTrip.getStops().isEmpty() || 
            !this.driver.equals(otherTrip.getDriver())) {
            return false;
        }
        
        // Check for common stops
        for (Stop stop1 : this.stops) {
            for (Stop stop2 : otherTrip.getStops()) {
                if (stop1.getStopStation().equals(stop2.getStopStation())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Validates if posting this new trip is feasible for the driver
     * 
     * @param driver the driver attempting to post the trip
     * @param existingTrips list of existing trips for the driver
     * @return true if no time conflict, false otherwise
     */
    public boolean validateTripPostingFeasibility(User driver, List<Trip> existingTrips) {
        // Precondition: Driver must exist and be valid
        if (driver == null || driver.getId() == null) {
            return false;
        }
        
        // Precondition: Departure time must be before arrival time
        if (!departureTime.isBefore(arrivalTime)) {
            return false;
        }
        
        // Check for time conflicts with existing trips
        for (Trip existingTrip : existingTrips) {
            if (existingTrip.getDriver().equals(driver)) {
                LocalDateTime existingDeparture = existingTrip.getDepartureTime();
                LocalDateTime existingArrival = existingTrip.getArrivalTime();
                
                // Check if time periods intersect (adjacent boundaries not considered conflict)
                if ((departureTime.isBefore(existingArrival) && arrivalTime.isAfter(existingDeparture)) ||
                    (existingDeparture.isBefore(arrivalTime) && existingArrival.isAfter(departureTime))) {
                    return false;
                }
            }
        }
        
        return true;
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
    private String id;
    private User customer;
    private Trip trip;
    private int seats;
    private LocalDateTime bookingTime;

    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}

class OnlineRideshareSystem {
    private List<User> users;
    private List<Trip> trips;
    private List<Booking> bookings;

    public OnlineRideshareSystem() {
        users = new ArrayList<>();
        trips = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Calculates total reward points earned by a customer in the given month
     * 
     * @param customer the customer whose points are being calculated
     * @param targetMonth the target month for which to calculate points (format: yyyy-MM)
     * @return total points earned in the target month
     * @throws IllegalArgumentException if customer doesn't have membership with points reward
     */
    public int computeMonthlyRewardPoints(User customer, String targetMonth) {
        // Precondition: Customer must have membership with points reward
        if (customer.getMembership() == null || 
            !"points".equals(customer.getMembership().getReward().getRewardType())) {
            throw new IllegalArgumentException("Customer must have membership with points reward");
        }
        
        int totalPoints = 0;
        YearMonth targetYearMonth = YearMonth.parse(targetMonth);
        
        // Calculate points for eligible bookings
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                LocalDateTime bookingTime = booking.getBookingTime();
                YearMonth bookingYearMonth = YearMonth.from(bookingTime);
                
                // Check if booking is within target month
                if (bookingYearMonth.equals(targetYearMonth)) {
                    totalPoints += booking.getSeats() * 5;
                }
            }
        }
        
        return totalPoints;
    }
}