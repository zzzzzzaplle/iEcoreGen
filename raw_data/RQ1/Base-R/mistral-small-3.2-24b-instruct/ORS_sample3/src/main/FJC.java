import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class User {
    private String userId;
    private String email;
    private String phoneNumber;
    private Membership membership;

    public User() {
    }

    public User(String userId, String email, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

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

class Membership {
    private String membershipId;
    private String rewardType;
    private double discountRate;

    public Membership() {
    }

    public Membership(String membershipId, String rewardType, double discountRate) {
        this.membershipId = membershipId;
        this.rewardType = rewardType;
        this.discountRate = discountRate;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}

class Trip {
    private String tripId;
    private User driver;
    private String departureStation;
    private String arrivalStation;
    private int availableSeats;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private double price;
    private List<Stop> stops;

    public Trip() {
        this.stops = new ArrayList<>();
    }

    public Trip(String tripId, User driver, String departureStation, String arrivalStation, int availableSeats,
                LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, double price) {
        this();
        this.tripId = tripId;
        this.driver = driver;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.availableSeats = availableSeats;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.price = price;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
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

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
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

    public void addStop(Stop stop) {
        this.stops.add(stop);
    }

    public boolean isIndirectTrip() {
        return !stops.isEmpty();
    }
}

class Stop {
    private String stopId;
    private String stationName;

    public Stop() {
    }

    public Stop(String stopId, String stationName) {
        this.stopId = stopId;
        this.stationName = stationName;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}

class Booking {
    private String bookingId;
    private User customer;
    private Trip trip;
    private int seats;
    private LocalDateTime bookingDateTime;

    public Booking() {
    }

    public Booking(String bookingId, User customer, Trip trip, int seats, LocalDateTime bookingDateTime) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.trip = trip;
        this.seats = seats;
        this.bookingDateTime = bookingDateTime;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    /**
     * Validates if a customer can book seats on a trip.
     *
     * @param customer The customer attempting to book seats.
     * @param trip The trip the customer wants to book.
     * @param seats The number of seats the customer wants to book.
     * @param bookingDateTime The date and time of the booking.
     * @return true if the customer can book seats on the trip, false otherwise.
     */
    public boolean validateBookingEligibility(User customer, Trip trip, int seats, LocalDateTime bookingDateTime) {
        if (trip == null || trip.getAvailableSeats() < seats) {
            return false;
        }

        if (bookingDateTime.isAfter(trip.getDepartureDateTime().minusHours(2)) &&
            !bookingDateTime.isEqual(trip.getDepartureDateTime().minusHours(2))) {
            return false;
        }

        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer) &&
                booking.getTrip().equals(trip) &&
                booking.getBookingDateTime().toLocalDate().equals(bookingDateTime.toLocalDate())) {
                return false;
            }
        }

        trip.setAvailableSeats(trip.getAvailableSeats() - seats);
        return true;
    }

    /**
     * Calculates the discounted trip price for a booking.
     *
     * @param booking The booking for which to calculate the discounted price.
     * @return The discounted trip price if conditions are met, otherwise the original price.
     */
    public double calculateDiscountedTripPrice(Booking booking) {
        if (booking.getCustomer().getMembership() != null &&
            booking.getBookingDateTime().isBefore(booking.getTrip().getDepartureDateTime().minusHours(24))) {
            double discount = booking.getTrip().getPrice() * booking.getCustomer().getMembership().getDiscountRate();
            return Math.round((booking.getTrip().getPrice() - discount) * 10) / 10.0;
        }
        return booking.getTrip().getPrice();
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 The first trip.
     * @param trip2 The second trip.
     * @return true if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlapForIndirectTrips(Trip trip1, Trip trip2) {
        if (!trip1.isIndirectTrip() || !trip2.isIndirectTrip() || !trip1.getDriver().equals(trip2.getDriver())) {
            return false;
        }

        for (Stop stop1 : trip1.getStops()) {
            for (Stop stop2 : trip2.getStops()) {
                if (stop1.getStationName().equals(stop2.getStationName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param customer The customer for which to compute the reward points.
     * @param currentMonth The current month to compute the reward points for.
     * @return The total reward points earned by the customer.
     */
    public int computeMonthlyRewardPoints(User customer, int currentMonth) {
        if (customer.getMembership() == null || !customer.getMembership().getRewardType().equals("points")) {
            return 0;
        }

        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer) &&
                booking.getBookingDateTime().getMonthValue() == currentMonth) {
                totalPoints += booking.getSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Validates if a driver can post a new trip without time conflicts.
     *
     * @param driver The driver attempting to post a new trip.
     * @param newTrip The new trip to be posted.
     * @return true if the driver can post the new trip without time conflicts, false otherwise.
     */
    public boolean validateTripPostingFeasibility(User driver, Trip newTrip) {
        if (driver == null || newTrip.getDepartureDateTime().isAfter(newTrip.getArrivalDateTime())) {
            return false;
        }

        for (Trip trip : trips) {
            if (trip.getDriver().equals(driver) &&
                (newTrip.getDepartureDateTime().isBefore(trip.getArrivalDateTime()) &&
                 newTrip.getArrivalDateTime().isAfter(trip.getDepartureDateTime()))) {
                return false;
            }
        }
        return true;
    }
}