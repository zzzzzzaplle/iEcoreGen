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
    private List<Booking> bookings;

    public User() {
        this.bookings = new ArrayList<>();
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

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
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
}

class Customer extends User {
    public Customer() {
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
    private Driver driver;
    private String departureStation;
    private String arrivalStation;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;
    private List<Booking> bookings;

    public Trip() {
        this.stops = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
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

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
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
    private Customer customer;
    private Trip trip;
    private int seatsBooked;
    private LocalDateTime bookingTime;

    public Booking() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
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
        this.users = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.bookings = new ArrayList<>();
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
     * Validates if a customer can book seats on a trip.
     * Eligibility criteria: 
     * - Trip must exist and have available seats
     * - Number of available seats must be greater than or equal to requested seats
     * - No overlapping time periods between existing bookings and the new booking on the same day
     * - Booking must be made at least 2 hours before departure time
     * 
     * @param customer The customer attempting to make the booking
     * @param trip The trip to book seats on
     * @param seatsRequested Number of seats requested for booking
     * @param bookingTime The time when the booking is being made
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int seatsRequested, LocalDateTime bookingTime) {
        if (trip == null || trip.getAvailableSeats() < seatsRequested) {
            return false;
        }
        
        if (bookingTime.isAfter(trip.getDepartureTime().minusHours(2))) {
            return false;
        }
        
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.getTrip().getDepartureTime().toLocalDate().equals(trip.getDepartureTime().toLocalDate())) {
                LocalDateTime existingStart = existingBooking.getTrip().getDepartureTime();
                LocalDateTime existingEnd = existingBooking.getTrip().getArrivalTime();
                LocalDateTime newStart = trip.getDepartureTime();
                LocalDateTime newEnd = trip.getArrivalTime();
                
                if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                    return false;
                }
            }
        }
        
        trip.setAvailableSeats(trip.getAvailableSeats() - seatsRequested);
        return true;
    }

    /**
     * Calculates the discounted price for a trip booking.
     * Applies 20% discount if customer has membership and booking is made ≥24 hours before departure.
     * 
     * @param customer The customer making the booking
     * @param trip The trip being booked
     * @param bookingTime The time when the booking is made
     * @return The final price after discount (1 decimal place) or original price if conditions not met
     */
    public double calculateDiscountedTripPrice(Customer customer, Trip trip, LocalDateTime bookingTime) {
        if (customer.getMembership() != null && 
            bookingTime.isBefore(trip.getDepartureTime().minusHours(24))) {
            double discountedPrice = trip.getPrice() * 0.8;
            return Math.round(discountedPrice * 10.0) / 10.0;
        }
        return trip.getPrice();
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 The first indirect trip
     * @param trip2 The second indirect trip
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (trip1.getDriver() != trip2.getDriver()) {
            return false;
        }
        
        if (trip1.getStops().isEmpty() || trip2.getStops().isEmpty()) {
            return false;
        }
        
        for (Stop stop1 : trip1.getStops()) {
            for (Stop stop2 : trip2.getStops()) {
                if (stop1.getStopStation().equals(stop2.getStopStation())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Calculates total reward points earned by a customer in the given month.
     * Only counts bookings within the target month for customers with points-based membership.
     * 
     * @param customer The customer whose points are being calculated
     * @param targetMonth The target month for point calculation (format: yyyy-MM)
     * @return Total points earned (5 points per seat per eligible booking)
     */
    public int computeMonthlyRewardPoints(Customer customer, String targetMonth) {
        if (customer.getMembership() == null || 
            !"points".equals(customer.getMembership().getReward().getRewardType())) {
            return 0;
        }
        
        YearMonth target = YearMonth.parse(targetMonth);
        int totalPoints = 0;
        
        for (Booking booking : customer.getBookings()) {
            YearMonth bookingMonth = YearMonth.from(booking.getBookingTime());
            if (bookingMonth.equals(target)) {
                totalPoints += booking.getSeatsBooked() * 5;
            }
        }
        
        return totalPoints;
    }

    /**
     * Validates if a new trip can be posted by a driver without time conflicts.
     * Checks for valid driver, valid time sequence, and time conflicts with existing trips.
     * 
     * @param driver The driver posting the trip
     * @param newTrip The new trip to be posted
     * @return true if trip posting is feasible (no time conflicts), false otherwise
     */
    public boolean validateTripPostingFeasibility(Driver driver, Trip newTrip) {
        if (driver == null || newTrip.getDepartureTime().isAfter(newTrip.getArrivalTime())) {
            return false;
        }
        
        for (Trip existingTrip : driver.getTrips()) {
            LocalDateTime existingStart = existingTrip.getDepartureTime();
            LocalDateTime existingEnd = existingTrip.getArrivalTime();
            LocalDateTime newStart = newTrip.getDepartureTime();
            LocalDateTime newEnd = newTrip.getArrivalTime();
            
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                return false;
            }
        }
        
        return true;
    }
}