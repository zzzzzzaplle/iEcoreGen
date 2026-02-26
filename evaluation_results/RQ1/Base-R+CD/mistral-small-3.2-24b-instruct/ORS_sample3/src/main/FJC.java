import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        trips = new ArrayList<>();
    }

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

    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        for (Trip trip : new ArrayList<>()) {
            totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
        }
        return totalPoints;
    }

    public boolean bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            return true;
        }
        return false;
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
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
        bookings = new ArrayList<>();
        stops = new ArrayList<>();
    }

    /**
     * Calculates the discounted price for a booking if the conditions are met.
     *
     * @param customer The customer making the booking
     * @param bookingTime The time of the booking in yyyy-MM-dd HH:mm format
     * @return The final price after applying discount, or the original price if conditions are not met
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDate + " " + departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (customer.getMembershipPackage() != null &&
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS) &&
            bookingDateTime.isBefore(departureDateTime.minusHours(24))) {
            return price * 0.8;
        }
        return price;
    }

    /**
     * Calculates the monthly reward points for a customer.
     *
     * @param customer The customer
     * @param currentMonth The target month in yyyy-MM format
     * @return The total reward points earned by the customer in the given month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                String bookingDateStr = booking.getBookingDate().toString();
                if (bookingDateStr.startsWith(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Books seats on the trip if there are available seats.
     *
     * @param num The number of seats to book
     * @return true if the seats were booked successfully, false otherwise
     */
    public boolean bookSeats(int num) {
        if (numberOfSeats >= num) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms the booking of seats.
     *
     * @param seats The number of seats to confirm
     * @return true if the booking was confirmed successfully, false otherwise
     */
    public boolean confirmBooking(int seats) {
        if (numberOfSeats + seats <= 0) {
            return false;
        }
        numberOfSeats += seats;
        return true;
    }

    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if the new trip time conflicts with any existing trip time.
     *
     * @param newDepartureTime The departure time of the new trip in HH:mm format
     * @param newArrivalTime The arrival time of the new trip in HH:mm format
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        for (Booking booking : bookings) {
            String existingDepartureTime = departureTime;
            String existingArrivalTime = arrivalTime;

            if (existingDepartureTime.compareTo(newArrivalTime) < 0 &&
                existingArrivalTime.compareTo(newDepartureTime) > 0) {
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
     * Checks if the booking is eligible.
     *
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || trip.getNumberOfSeats() < numberOfSeats) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime departureTime = LocalDateTime.parse(trip.getDepartureDate() + " " + trip.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (currentTime.isAfter(departureTime.minusHours(2))) {
            return false;
        }
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.overlapsWith(trip)) {
                return false;
            }
        }
        return true;
    }

    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    public boolean overlapsWith(Trip trip) {
        return false; // Implementation depends on specific requirements
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

    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a == award) {
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