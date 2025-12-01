import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        this.trips = new ArrayList<>();
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
     * @param trip1 The first trip to check
     * @param trip2 The second trip to check
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
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
     * Checks if a driver can post a new trip without time conflicts.
     *
     * @param newTrip The new trip to be posted
     * @return true if no time conflict, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
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
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param currentMonth The target month to calculate points for
     * @return totalPoints The total reward points earned
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            for (Trip trip : getTrips()) {
                totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
            }
        }
        return totalPoints;
    }

    /**
     * Books seats on a trip if eligible.
     *
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
        }
    }

    private List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Booking booking : getBookings()) {
            trips.add(booking.getTrip());
        }
        return trips;
    }

    private List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (Trip trip : getTrips()) {
            bookings.addAll(trip.getBookings());
        }
        return bookings;
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
     * Calculates the discounted trip price if conditions are met.
     *
     * @param customer The customer making the booking
     * @param bookingTime The time of the booking
     * @return The final price after applying discount if eligible, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            // Calculate the time difference between booking time and departure time
            // If the difference is >= 24 hours, apply 20% discount
            // Note: This is a simplified calculation. In a real scenario, you would need to parse the strings into Date objects and calculate the difference properly.
            if (isTimeDifferenceGreaterThan24Hours(bookingTime, departureTime)) {
                return price * 0.8;
            }
        }
        return price;
    }

    private boolean isTimeDifferenceGreaterThan24Hours(String bookingTime, String departureTime) {
        // Implement the logic to compare the time difference
        // This is a placeholder for the actual implementation
        return false;
    }

    /**
     * Calculates the monthly reward points for a customer.
     *
     * @param customer The customer to calculate points for
     * @param currentMonth The target month to calculate points for
     * @return The total reward points earned
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer) && isBookingInMonth(booking.getBookingDate(), currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    private boolean isBookingInMonth(Date bookingDate, String currentMonth) {
        // Implement the logic to check if the booking date is within the target month
        // This is a placeholder for the actual implementation
        return false;
    }

    /**
     * Books seats on the trip if available.
     *
     * @param num The number of seats to book
     * @return true if seats are booked successfully, false otherwise
     */
    public boolean bookSeats(int num) {
        if (numberOfSeats >= num) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms a booking and updates the number of seats.
     *
     * @param seats The number of seats to confirm
     * @return true if booking is confirmed successfully, false otherwise
     */
    public boolean confirmBooking(int seats) {
        if (bookSeats(seats)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the set of stop stations for the trip.
     *
     * @return A set of stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if there is a time conflict with the given time period.
     *
     * @param newDepartureTime The new departure time to check
     * @param newArrivalTime The new arrival time to check
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        // Implement the logic to check for time conflicts
        // This is a placeholder for the actual implementation
        return false;
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
     * Checks if the booking is eligible.
     *
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        if (!isBookingTimeValid()) {
            return false;
        }
        if (overlapsWith(trip)) {
            return false;
        }
        return true;
    }

    private boolean isBookingTimeValid() {
        // Implement the logic to check if the booking time is valid
        // This is a placeholder for the actual implementation
        return true;
    }

    /**
     * Updates the number of seats in the trip after a successful booking.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks if the booking overlaps with any existing bookings by the same customer on the same day.
     *
     * @param trip The trip to check for overlaps
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.getCustomer().equals(customer) && isSameDay(bookingDate, existingBooking.getBookingDate())) {
                // Implement the logic to check for time overlaps
                // This is a placeholder for the actual implementation
                return true;
            }
        }
        return false;
    }

    private boolean isSameDay(Date date1, Date date2) {
        // Implement the logic to check if two dates are on the same day
        // This is a placeholder for the actual implementation
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
     * Checks if the membership package has a specific award.
     *
     * @param award The award to check for
     * @return true if the award is present, false otherwise
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

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}