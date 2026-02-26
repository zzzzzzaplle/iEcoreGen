import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;

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
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     * @param trip1 The first trip to check.
     * @param trip2 The second trip to check.
     * @return True if any stop station is shared, false otherwise.
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stopStations1 = trip1.getStopStations();
        Set<String> stopStations2 = trip2.getStopStations();
        for (String stopStation : stopStations1) {
            if (stopStations2.contains(stopStation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a new trip can be posted by the driver without time conflicts.
     * @param newTrip The new trip to check.
     * @return True if no time conflict, false otherwise.
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
    private List<Trip> trips;

    public Customer() {
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Computes the total reward points earned by the customer in the given current month.
     * @param currentMonth The current month to calculate points for.
     * @return Total reward points earned in the current month.
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        for (Trip trip : this.getTrips()) {
            totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
        }
        return totalPoints;
    }

    /**
     * Books seats on a trip if the customer is eligible.
     * @param trip The trip to book seats on.
     * @param numberOfSeats The number of seats to book.
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
     * Calculates the discounted price for a booking if conditions are met.
     * @param customer The customer making the booking.
     * @param bookingTime The time the booking is made.
     * @return The final price after applying discounts, or the original price if no discount applies.
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            long bookingTimeMillis = convertToMillis(bookingTime);
            long departureTimeMillis = convertToMillis(departureTime);
            long diff = departureTimeMillis - bookingTimeMillis;
            if (diff >= 24 * 60 * 60 * 1000) {
                return price * 0.8;
            }
        }
        return price;
    }

    /**
     * Calculates the total reward points earned by the customer in the given current month.
     * @param customer The customer to calculate points for.
     * @param currentMonth The current month to calculate points for.
     * @return Total reward points earned in the current month.
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
     * Attempts to book a specified number of seats on the trip.
     * @param num The number of seats to book.
     * @return True if the booking was successful, false otherwise.
     */
    public boolean bookSeats(int num) {
        if (num <= 0 || num > numberOfSeats) {
            return false;
        }
        numberOfSeats -= num;
        return true;
    }

    /**
     * Confirms a booking by updating the trip's booked seats.
     * @param seats The number of seats to confirm.
     * @return True if the booking was confirmed, false otherwise.
     */
    public boolean confirmBooking(int seats) {
        if (seats <= 0 || seats > numberOfSeats) {
            return false;
        }
        numberOfSeats -= seats;
        return true;
    }

    /**
     * Retrieves a set of stop stations for the trip.
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
     * Checks if the new trip time conflicts with any existing trip time.
     * @param newDepartureTime The new trip's departure time.
     * @param newArrivalTime The new trip's arrival time.
     * @return True if there is a time conflict, false otherwise.
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        long newDeparture = convertToMillis(newDepartureTime);
        long newArrival = convertToMillis(newArrivalTime);
        for (Booking booking : bookings) {
            long existingDeparture = convertToMillis(booking.getTrip().getDepartureTime());
            long existingArrival = convertToMillis(booking.getTrip().getArrivalTime());
            if (newDeparture < existingArrival && newArrival > existingDeparture) {
                return true;
            }
        }
        return false;
    }

    private long convertToMillis(String time) {
        // Implement time string to milliseconds conversion logic
        return 0;
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
     * @return True if the booking is eligible, false otherwise.
     */
    public boolean isBookingEligible() {
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        long bookingTimeMillis = bookingDate.getTime();
        long departureTimeMillis = convertToMillis(trip.getDepartureTime());
        long diff = departureTimeMillis - bookingTimeMillis;
        if (diff < 2 * 60 * 60 * 1000) {
            return false;
        }
        for (Trip existingTrip : customer.getTrips()) {
            if (overlapsWith(existingTrip)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the number of seats on the trip after a successful booking.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks if the booking overlaps with any existing trip.
     * @param trip The trip to check for overlaps.
     * @return True if there is an overlap, false otherwise.
     */
    public boolean overlapsWith(Trip trip) {
        long existingDeparture = convertToMillis(this.trip.getDepartureTime());
        long existingArrival = convertToMillis(this.trip.getArrivalTime());
        long newDeparture = convertToMillis(trip.getDepartureTime());
        long newArrival = convertToMillis(trip.getArrivalTime());
        return newDeparture < existingArrival && newArrival > existingDeparture;
    }

    private long convertToMillis(String time) {
        // Implement time string to milliseconds conversion logic
        return 0;
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
     * @param award The award to check for.
     * @return True if the award is present, false otherwise.
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