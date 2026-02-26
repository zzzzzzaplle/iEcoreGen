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

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     *
     * @param trip1 the first trip
     * @param trip2 the second trip
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> trip1Stops = trip1.getStopStations();
        Set<String> trip2Stops = trip2.getStopStations();
        trip1Stops.retainAll(trip2Stops);
        return !trip1Stops.isEmpty();
    }

    /**
     * Checks if a driver can post a new trip without time conflicts.
     *
     * @param newTrip the new trip to be posted
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

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

class Customer extends User {
    private MembershipPackage membershipPackage;

    public Customer() {
    }

    /**
     * Calculates the total reward points earned by a customer in the given current month.
     *
     * @param currentMonth the target month to calculate points for
     * @return total reward points earned
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        for (Trip trip : getTrips()) {
            totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
        }
        return totalPoints;
    }

    /**
     * Attempts to book seats on a trip for a customer.
     *
     * @param trip the trip to book seats on
     * @param numberOfSeats the number of seats to book
     * @throws IllegalArgumentException if booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (!trip.bookSeats(numberOfSeats)) {
            throw new IllegalArgumentException("Booking is not eligible.");
        }
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    private List<Trip> getTrips() {
        List<Trip> allTrips = new ArrayList<>();
        for (Booking booking : getBookings()) {
            allTrips.add(booking.getTrip());
        }
        return allTrips;
    }

    private List<Booking> getBookings() {
        List<Booking> allBookings = new ArrayList<>();
        for (Trip trip : getTrips()) {
            allBookings.addAll(trip.getBookings());
        }
        return allBookings;
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

    /**
     * Calculates the final price for a booking after applying reward discounts.
     *
     * @param customer the customer making the booking
     * @param bookingTime the time of the booking
     * @return the final price after applying discounts, or original price if no discount applies
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null &&
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            try {
                Date bookingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(bookingTime);
                Date departureDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(departureDate + " " + departureTime);
                long diffInHours = (departureDateTime.getTime() - bookingDate.getTime()) / (60 * 60 * 1000);
                if (diffInHours >= 24) {
                    return price * 0.8;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    /**
     * Calculates the total reward points earned by a customer in the given current month for this trip.
     *
     * @param customer the customer to calculate points for
     * @param currentMonth the target month to calculate points for
     * @return total reward points earned for this trip
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                String bookingDate = booking.getBookingDate().toString();
                if (bookingDate.startsWith(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Attempts to book seats on the trip.
     *
     * @param num the number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (numberOfSeats >= num) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms a booking by validating its eligibility.
     *
     * @param seats the number of seats to book
     * @return true if booking is eligible, false otherwise
     */
    public boolean confirmBooking(int seats) {
        Booking booking = new Booking();
        booking.setNumberOfSeats(seats);
        return booking.isBookingEligible();
    }

    /**
     * Gets all stop stations for this trip.
     *
     * @return a set of stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stations = new HashSet<>();
        for (Stop stop : stops) {
            stations.add(stop.getStopStation());
        }
        return stations;
    }

    /**
     * Checks if the given time period conflicts with any existing trip time period.
     *
     * @param newDepartureTime the new departure time
     * @param newArrivalTime the new arrival time
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        for (Booking booking : bookings) {
            if (booking.overlapsWith(this)) {
                return true;
            }
        }
        return false;
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

    /**
     * Checks if a booking is eligible.
     *
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.overlapsWith(trip) && existingBooking.getCustomer().equals(customer)) {
                return false;
            }
        }
        try {
            Date currentDate = new Date();
            Date departureDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(trip.getDepartureDate() + " " + trip.getDepartureTime());
            long diffInHours = (departureDateTime.getTime() - currentDate.getTime()) / (60 * 60 * 1000);
            if (diffInHours < 2) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Updates the number of seats for the associated trip.
     */
    public void updateTripSeats() {
        if (isBookingEligible()) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if this booking overlaps with the time period of another trip.
     *
     * @param trip the trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Implement time period overlap logic here
        return false;
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
}

class MembershipPackage {
    private Award[] awards;

    public MembershipPackage() {
    }

    /**
     * Checks if the membership package has a specific award.
     *
     * @param award the award to check for
     * @return true if the award is present, false otherwise
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a.equals(award)) {
                return true;
            }
        }
        return false;
    }

    public Award[] getAwards() {
        return awards;
    }

    public void setAwards(Award[] awards) {
        this.awards = awards;
    }
}

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}