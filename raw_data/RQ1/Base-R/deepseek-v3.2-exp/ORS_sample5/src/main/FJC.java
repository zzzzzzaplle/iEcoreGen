import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

class User {
    private String userId;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

class Membership {
    private String membershipId;
    private RewardType rewardType;

    public Membership() {}

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }
}

enum RewardType {
    CASHBACK, DISCOUNT, POINTS
}

class Trip {
    private String tripId;
    private Driver driver;
    private String departureStation;
    private String arrivalStation;
    private int availableSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private List<Stop> stops;
    private boolean isDirect;

    public Trip() {
        this.stops = new ArrayList<>();
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }
}

class Stop {
    private String stopId;
    private String station;

    public Stop() {}

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}

class Booking {
    private String bookingId;
    private Customer customer;
    private Trip trip;
    private int seats;
    private LocalDateTime bookingTime;

    public Booking() {}

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

class ORSService {
    private List<Trip> trips;
    private List<User> users;
    private List<Booking> bookings;

    public ORSService() {
        this.trips = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
     * @param trip The trip for which booking is being attempted
     * @param requestedSeats Number of seats requested for booking
     * @param bookingTime The time when the booking is being made
     * @return true if booking is eligible, false otherwise
     */
    public boolean validateBookingEligibility(Customer customer, Trip trip, int requestedSeats, LocalDateTime bookingTime) {
        if (trip == null || trip.getAvailableSeats() < requestedSeats) {
            return false;
        }

        if (bookingTime.isAfter(trip.getDepartureTime().minusHours(2)) || bookingTime.equals(trip.getDepartureTime().minusHours(2))) {
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

        trip.setAvailableSeats(trip.getAvailableSeats() - requestedSeats);
        return true;
    }

    /**
     * Computes the final price for a booking after applying reward discounts.
     * Discount applies only if customer has membership and booking is made ≥24 hours before departure.
     * 
     * @param customer The customer making the booking
     * @param trip The trip being booked
     * @param bookingTime The time when the booking is being made
     * @return The final price after discount (rounded to 1 decimal place) or original price
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
     * Determines if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 First indirect trip
     * @param trip2 Second indirect trip
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (trip1.isDirect() || trip2.isDirect() || 
            !trip1.getDriver().equals(trip2.getDriver())) {
            return false;
        }

        for (Stop stop1 : trip1.getStops()) {
            for (Stop stop2 : trip2.getStops()) {
                if (stop1.getStation().equals(stop2.getStation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates total reward points earned by a customer in the given month.
     * Only calculates bookings within target month for customers with points reward membership.
     * 
     * @param customer The customer for whom points are being calculated
     * @param targetMonth The target month for calculation (format: yyyy-MM)
     * @return Total points earned (sum of seats * 5 for eligible bookings)
     * @throws IllegalArgumentException if targetMonth format is invalid
     */
    public int computeMonthlyRewardPoints(Customer customer, String targetMonth) {
        if (customer.getMembership() == null || 
            customer.getMembership().getRewardType() != RewardType.POINTS) {
            return 0;
        }

        YearMonth month;
        try {
            month = YearMonth.parse(targetMonth);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid target month format. Expected: yyyy-MM");
        }

        int totalPoints = 0;
        for (Booking booking : customer.getBookings()) {
            YearMonth bookingMonth = YearMonth.from(booking.getBookingTime());
            if (bookingMonth.equals(month)) {
                totalPoints += booking.getSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Validates if a trip can be posted by a driver.
     * Preconditions: Driver must exist, departure time must be before arrival time.
     * Checks for time conflicts with existing trips by the same driver.
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