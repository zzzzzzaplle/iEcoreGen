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
    private List<Trip> trips = new ArrayList<>();

    public Driver() {
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Check if two indirect trips by the same driver share any common stops.
     * @param trip1 The first indirect trip.
     * @param trip2 The second indirect trip.
     * @return true if any stop station is shared, false otherwise.
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
     * Check if a driver can post a new trip without time conflicts.
     * @param newTrip The new trip to be posted.
     * @return true if no time conflict, false otherwise.
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
     * Calculate total reward points earned by a customer in the given current month.
     * @param currentMonth The target month to calculate points for.
     * @return Total points earned in the given month.
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            for (Booking booking : getBookings()) {
                if (booking.getBookingDate().toString().contains(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    private List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (Trip trip : getTrips()) {
            bookings.addAll(trip.getBookings());
        }
        return bookings;
    }

    private List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Booking booking : getBookings()) {
            if (booking.getCustomer().equals(this)) {
                trips.add(booking.getTrip());
            }
        }
        return trips;
    }

    /**
     * Book seats on a trip if eligible.
     * @param trip The trip to book seats on.
     * @param numberOfSeats The number of seats to book.
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
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
    private List<Booking> bookings = new ArrayList<>();
    private List<Stop> stops = new ArrayList<>();

    public Trip() {
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
     * Calculate the final price for a booking after applying reward discounts.
     * @param customer The customer making the booking.
     * @param bookingTime The time of the booking.
     * @return The final price after applying discounts, or the original price if no discount is applied.
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            String departureDateTime = departureDate.toString() + " " + departureTime;
            if (isAtLeast24HoursBeforeDeparture(bookingTime, departureDateTime)) {
                return price * 0.8;
            }
        }
        return price;
    }

    private boolean isAtLeast24HoursBeforeDeparture(String bookingTime, String departureDateTime) {
        // Implement time comparison logic
        return false;
    }

    /**
     * Calculate total reward points earned by a customer in the given current month.
     * @param customer The customer to calculate points for.
     * @param currentMonth The target month to calculate points for.
     * @return Total points earned in the given month.
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer) && booking.getBookingDate().toString().contains(currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    /**
     * Book seats on the trip if eligible.
     * @param num The number of seats to book.
     * @return true if seats are booked successfully, false otherwise.
     */
    public boolean bookSeats(int num) {
        if (confirmBooking(num)) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirm if a booking can be made.
     * @param seats The number of seats to book.
     * @return true if booking can be confirmed, false otherwise.
     */
    public boolean confirmBooking(int seats) {
        if (numberOfSeats >= seats) {
            numberOfSeats -= seats;
            return true;
        }
        return false;
    }

    /**
     * Get all stop stations for the trip.
     * @return A set of stop stations.
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Check if the new trip time conflicts with existing trips.
     * @param newDepartureTime The departure time of the new trip.
     * @param newArrivalTime The arrival time of the new trip.
     * @return true if there is a time conflict, false otherwise.
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        for (Booking booking : bookings) {
            if (booking.overlapsWith(this)) {
                return true;
            }
        }
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
     * Check if the booking is eligible.
     * @return true if the booking is eligible, false otherwise.
     */
    public boolean isBookingEligible() {
        if (trip.getNumberOfSeats() >= numberOfSeats) {
            String departureTime = trip.getDepartureDate().toString() + " " + trip.getDepartureTime();
            if (isAtLeast2HoursBeforeDeparture(bookingDate.toString(), departureTime)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAtLeast2HoursBeforeDeparture(String bookingTime, String departureTime) {
        // Implement time comparison logic
        return false;
    }

    /**
     * Update the number of seats for the trip after a booking.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Check if the booking overlaps with an existing trip.
     * @param trip The trip to check for overlap.
     * @return true if there is an overlap, false otherwise.
     */
    public boolean overlapsWith(Trip trip) {
        // Implement overlap check logic
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
     * Check if the membership package has a specific award.
     * @param award The award to check for.
     * @return true if the award is present, false otherwise.
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a.equals(award)) {
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