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
     * @param trip1 the first trip to check
     * @param trip2 the second trip to check
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
}

class Customer extends User {
    private MembershipPackage membershipPackage;
    private List<Booking> bookings;

    public Customer() {
        this.bookings = new ArrayList<>();
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Gets the trips booked by the customer.
     *
     * @return the list of trips booked by the customer
     */
    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Booking booking : bookings) {
            trips.add(booking.getTrip());
        }
        return trips;
    }

    /**
     * Computes the total reward points earned by a customer in the given current month.
     *
     * @param currentMonth the month to calculate points for
     * @return totalPoints the total reward points earned
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            for (Trip trip : new ArrayList<>(getTrips())) {
                totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
            }
        }
        return totalPoints;
    }

    /**
     * Books seats on a trip if eligible.
     *
     * @param trip the trip to book seats on
     * @param numberOfSeats the number of seats to book
     * @throws IllegalArgumentException if the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());

        if (!booking.isBookingEligible()) {
            throw new IllegalArgumentException("Booking is not eligible");
        }

        trip.bookSeats(numberOfSeats);
        trip.getBookings().add(booking);
        bookings.add(booking);
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
     * Calculates the discounted price for a booking if conditions are met.
     *
     * @param customer the customer making the booking
     * @param bookingTime the time of the booking
     * @return the discounted price if conditions are met, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            long bookingTimeMillis = parseTimeToMillis(bookingTime);
            long departureTimeMillis = parseTimeToMillis(departureTime);
            long timeDifference = departureTimeMillis - bookingTimeMillis;

            if (timeDifference >= 86400000) { // 24 hours in milliseconds
                return price * 0.8;
            }
        }
        return price;
    }

    /**
     * Calculates the monthly reward points for a customer.
     *
     * @param customer the customer to calculate points for
     * @param currentMonth the current month
     * @return the total reward points
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
     * Books seats on the trip if available.
     *
     * @param num the number of seats to book
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
     * Confirms a booking by updating the number of seats.
     *
     * @param seats the number of seats to confirm
     * @return true if booking is confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        if (numberOfSeats >= seats) {
            numberOfSeats -= seats;
            return true;
        }
        return false;
    }

    /**
     * Gets the set of stop stations for the trip.
     *
     * @return the set of stop stations
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
     * @param newDepartureTime the new departure time
     * @param newArrivalTime the new arrival time
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        long newDepartureMillis = parseTimeToMillis(newDepartureTime);
        long newArrivalMillis = parseTimeToMillis(newArrivalTime);
        long departureMillis = parseTimeToMillis(departureTime);
        long arrivalMillis = parseTimeToMillis(arrivalTime);

        return !(newArrivalMillis <= departureMillis || newDepartureMillis >= arrivalMillis);
    }

    private long parseTimeToMillis(String time) {
        // Implement time parsing logic here
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
     *
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }

        long bookingTimeMillis = bookingDate.getTime();
        long departureTimeMillis = parseTimeToMillis(trip.getDepartureTime());
        long timeDifference = departureTimeMillis - bookingTimeMillis;

        if (timeDifference <= 7200000) { // 2 hours in milliseconds
            return false;
        }

        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.getCustomer().equals(customer) && existingBooking.overlapsWith(trip)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the number of seats for the trip.
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks if the booking overlaps with another trip.
     *
     * @param trip the trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        long bookingDepartureMillis = parseTimeToMillis(trip.getDepartureTime());
        long bookingArrivalMillis = parseTimeToMillis(trip.getArrivalTime());
        long departureMillis = parseTimeToMillis(this.trip.getDepartureTime());
        long arrivalMillis = parseTimeToMillis(this.trip.getArrivalTime());

        return !(bookingArrivalMillis <= departureMillis || bookingDepartureMillis >= arrivalMillis);
    }

    private long parseTimeToMillis(String time) {
        // Implement time parsing logic here
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
     *
     * @param award the award to check for
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