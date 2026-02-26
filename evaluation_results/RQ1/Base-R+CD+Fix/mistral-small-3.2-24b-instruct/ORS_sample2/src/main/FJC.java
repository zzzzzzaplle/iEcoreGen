import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
     * @param trip1 the first trip to compare
     * @param trip2 the second trip to compare
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

    public Customer() {
    }

    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    /**
     * Calculates total reward points earned by a customer in the given current month.
     *
     * @param currentMonth the target month to calculate points
     * @return totalPoints the total reward points earned
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            for (Trip trip : getBookedTrips()) {
                if (trip.getDepartureDate().toString().contains(currentMonth)) {
                    for (Booking booking : trip.getBookings()) {
                        if (booking.getCustomer().equals(this)) {
                            totalPoints += booking.getNumberOfSeats() * 5;
                        }
                    }
                }
            }
        }
        return totalPoints;
    }

    private List<Trip> getBookedTrips() {
        List<Trip> bookedTrips = new ArrayList<>();
        for (Trip trip : getAllTrips()) {
            for (Booking booking : trip.getBookings()) {
                if (booking.getCustomer().equals(this)) {
                    bookedTrips.add(trip);
                    break;
                }
            }
        }
        return bookedTrips;
    }

    private List<Trip> getAllTrips() {
        List<Trip> allTrips = new ArrayList<>();
        for (Driver driver : getAllDrivers()) {
            allTrips.addAll(driver.getTrips());
        }
        return allTrips;
    }

    private List<Driver> getAllDrivers() {
        // Implementation depends on how drivers are stored/accessed
        return new ArrayList<>();
    }

    /**
     * Attempts to book seats on a trip.
     *
     * @param trip the trip to book seats on
     * @param numberOfSeats the number of seats to book
     * @throws IllegalArgumentException if booking cannot be made
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (!trip.bookSeats(numberOfSeats)) {
            throw new IllegalArgumentException("Booking could not be made. Please check eligibility and availability.");
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
     * Calculates the final price for a booking after applying reward discounts.
     *
     * @param customer the customer making the booking
     * @param bookingTime the time the booking is made
     * @return the final price after discounts, or original price if no discount applies
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            try {
                Date bookingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(bookingTime);
                Date departureDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(getDepartureDate().toString() + " " + getDepartureTime());
                long timeDiff = departureDateTime.getTime() - bookingDate.getTime();
                long hoursDiff = timeDiff / (60 * 60 * 1000);

                if (hoursDiff >= 24) {
                    return price * 0.8;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    /**
     * Calculates total reward points earned by a customer in the given current month.
     *
     * @param customer the customer to calculate points for
     * @param currentMonth the target month to calculate points
     * @return totalPoints the total reward points earned
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.POINTS)) {
            for (Booking booking : bookings) {
                if (booking.getCustomer().equals(customer) && booking.getBookingDate().toString().contains(currentMonth)) {
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
        if (num <= 0 || numberOfSeats < num) {
            return false;
        }

        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(new Booking().getCustomer()) && booking.overlapsWith(this)) {
                return false;
            }
        }

        Booking newBooking = new Booking();
        newBooking.setNumberOfSeats(num);
        newBooking.setCustomer(new Customer());
        newBooking.setTrip(this);
        newBooking.setBookingDate(new Date());
        bookings.add(newBooking);

        numberOfSeats -= num;
        return true;
    }

    /**
     * Confirms a booking by updating the number of seats.
     *
     * @param seats the number of seats to confirm
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        for (Booking booking : bookings) {
            if (booking.getNumberOfSeats() == seats) {
                booking.updateTripSeats();
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all stop stations for the trip.
     *
     * @return a set of stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if there is a time conflict between the new trip time and existing trips.
     *
     * @param newDepartureTime the departure time of the new trip
     * @param newArrivalTime the arrival time of the new trip
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        try {
            Date newDeparture = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(newDepartureTime);
            Date newArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(newArrivalTime);
            Date tripDeparture = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(getDepartureDate().toString() + " " + getDepartureTime());
            Date tripArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(getDepartureDate().toString() + " " + getArrivalTime());

            return (newDeparture.before(tripArrival) && newArrival.after(tripDeparture));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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

        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.getCustomer().equals(customer) && existingBooking.overlapsWith(trip)) {
                return false;
            }
        }

        try {
            Date bookingTime = new Date();
            Date departureTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(trip.getDepartureDate().toString() + " " + trip.getDepartureTime());
            long timeDiff = departureTime.getTime() - bookingTime.getTime();
            long hoursDiff = timeDiff / (60 * 60 * 1000);

            if (hoursDiff < 2) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Updates the number of seats in the trip after booking.
     */
    public void updateTripSeats() {
        if (isBookingEligible()) {
            trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
        }
    }

    /**
     * Checks if the booking overlaps with another trip.
     *
     * @param trip the trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        try {
            Date bookingDeparture = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(this.trip.getDepartureDate().toString() + " " + this.trip.getDepartureTime());
            Date bookingArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(this.trip.getDepartureDate().toString() + " " + this.trip.getArrivalTime());
            Date tripDeparture = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(trip.getDepartureDate().toString() + " " + trip.getDepartureTime());
            Date tripArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(trip.getDepartureDate().toString() + " " + trip.getArrivalTime());

            return (bookingDeparture.before(tripArrival) && bookingArrival.after(tripDeparture));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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