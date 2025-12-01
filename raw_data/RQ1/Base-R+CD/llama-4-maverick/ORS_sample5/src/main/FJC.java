import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

enum Award {
    CASHBACK,
    DISCOUNTS,
    POINTS
}

class MembershipPackage {
    private Award[] awards;

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

    /**
     * Gets the awards associated with this membership package.
     * 
     * @return the array of awards
     */
    public Award[] getAwards() {
        return awards;
    }

    /**
     * Sets the awards for this membership package.
     * 
     * @param awards the array of awards to set
     */
    public void setAwards(Award[] awards) {
        this.awards = awards;
    }

    public MembershipPackage() {}
}

abstract class User {
    protected String ID;
    protected String email;
    protected String phoneNumber;

    /**
     * Gets the ID of the user.
     * 
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * 
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {}
}

class Driver extends User {
    private List<Trip> trips;

    /**
     * Checks if two indirect trips by the same driver share any common stops.
     * 
     * @param trip1 the first trip to check
     * @param trip2 the second trip to check
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        stops1.retainAll(stops2);
        return !stops1.isEmpty();
    }

    /**
     * Validates if a driver can post a new trip without time conflicts with existing trips.
     * 
     * @param newTrip the new trip to be posted
     * @return true if there are no time conflicts, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        String newDepartureTime = newTrip.getDepartureTime();
        String newArrivalTime = newTrip.getArrivalTime();
        for (Trip trip : trips) {
            if (trip.isTimeConflicting(newDepartureTime, newArrivalTime)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the trips associated with this driver.
     * 
     * @return the list of trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Sets the trips for this driver.
     * 
     * @param trips the list of trips to set
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Driver() {
        this.trips = new ArrayList<>();
    }
}

class Customer extends User {
    private MembershipPackage membershipPackage;

    /**
     * Computes the total reward points earned by this customer in the given month.
     * 
     * @param currentMonth the month to compute reward points for (in "yyyy-MM" format)
     * @return the total reward points
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        int totalPoints = 0;
        if (membershipPackage != null && membershipPackage.hasAward(Award.POINTS)) {
            // Assuming bookings are stored somewhere accessible
            // For simplicity, we'll directly access Trip's method
            // In a real scenario, you'd likely need to iterate over the customer's bookings
            // and then call the appropriate method on Trip
            // This implementation assumes a direct access for demonstration
            for (Trip trip : getBookedTrips()) {
                totalPoints += trip.calculateMonthlyPoints(this, currentMonth);
            }
        }
        return totalPoints;
    }

    // Helper method to get booked trips for a customer
    private List<Trip> getBookedTrips() {
        List<Trip> bookedTrips = new ArrayList<>();
        // Logic to fetch trips booked by this customer
        // This could involve iterating over a list of bookings or querying a database
        return bookedTrips;
    }

    /**
     * Books a trip for this customer.
     * 
     * @param trip         the trip to book
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (trip.bookSeats(numberOfSeats)) {
            Booking booking = new Booking();
            booking.setNumberOfSeats(numberOfSeats);
            booking.setCustomer(this);
            booking.setTrip(trip);
            // Assuming booking date is set to current date
            booking.setBookingDate(new Date());
            trip.getBookings().add(booking);
        }
    }

    /**
     * Gets the membership package associated with this customer.
     * 
     * @return the membership package
     */
    public MembershipPackage getMembershipPackage() {
        return membershipPackage;
    }

    /**
     * Sets the membership package for this customer.
     * 
     * @param membershipPackage the membership package to set
     */
    public void setMembershipPackage(MembershipPackage membershipPackage) {
        this.membershipPackage = membershipPackage;
    }

    public Customer() {}
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

    /**
     * Calculates the discounted price for a booking made by a customer.
     * 
     * @param customer   the customer making the booking
     * @param bookingTime the time of booking in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount if applicable, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = sdf.parse(bookingTime);
            Date departureDateTime = sdf.parse(getDepartureDate() + " " + getDepartureTime());
            long diff = departureDateTime.getTime() - bookingDateTime.getTime();
            if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && diff >= 24 * 60 * 60 * 1000) {
                return Math.round(price * 0.8 * 10.0) / 10.0; // 20% off, rounded to 1 decimal place
            }
        } catch (ParseException e) {
            // Handle exception
        }
        return price;
    }

    /**
     * Calculates the monthly reward points for a customer for this trip.
     * 
     * @param customer    the customer
     * @param currentMonth the current month in "yyyy-MM" format
     * @return the total reward points
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        int totalPoints = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.POINTS)) {
            for (Booking booking : bookings) {
                if (sdf.format(booking.getBookingDate()).equals(currentMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Books seats on this trip.
     * 
     * @param num the number of seats to book
     * @return true if booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date departureDateTime = sdf.parse(getDepartureDate() + " " + getDepartureTime());
            Date currentDate = new Date();
            long diff = departureDateTime.getTime() - currentDate.getTime();
            if (num <= numberOfSeats && diff > 2 * 60 * 60 * 1000) {
                numberOfSeats -= num;
                return true;
            }
        } catch (ParseException e) {
            // Handle exception
        }
        return false;
    }

    /**
     * Confirms a booking on this trip.
     * 
     * @param seats the number of seats booked
     * @return true if confirmation is successful, false otherwise
     */
    public boolean confirmBooking(int seats) {
        // Logic to confirm booking
        return true; // Placeholder
    }

    /**
     * Gets the set of stop stations for this trip.
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
     * Checks if there's a time conflict between this trip and another trip with given departure and arrival times.
     * 
     * @param newDepartureTime the departure time of the new trip
     * @param newArrivalTime   the arrival time of the new trip
     * @return true if there's a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date thisDeparture = sdf.parse(getDepartureDate() + " " + getDepartureTime());
            Date thisArrival = sdf.parse(getDepartureDate() + " " + getArrivalTime());
            Date newDeparture = sdf.parse(getDepartureDate() + " " + newDepartureTime);
            Date newArrival = sdf.parse(getDepartureDate() + " " + newArrivalTime);
            return (thisDeparture.before(newArrival) && thisArrival.after(newDeparture));
        } catch (ParseException e) {
            // Handle exception
        }
        return false;
    }

    // Getters and setters
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

    public Trip() {
        this.bookings = new ArrayList<>();
        this.stops = new ArrayList<>();
    }
}

class Stop {
    private String stopStation;

    /**
     * Gets the stop station.
     * 
     * @return the stop station
     */
    public String getStopStation() {
        return stopStation;
    }

    /**
     * Sets the stop station.
     * 
     * @param stopStation the stop station to set
     */
    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }

    public Stop() {}
}

class Booking {
    private int numberOfSeats;
    private Customer customer;
    private Trip trip;
    private Date bookingDate;

    /**
     * Checks if this booking is eligible.
     * 
     * @return true if eligible, false otherwise
     */
    public boolean isBookingEligible() {
        // Logic to check eligibility
        return true; // Placeholder
    }

    /**
     * Updates the trip seats based on this booking.
     */
    public void updateTripSeats() {
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
    }

    /**
     * Checks if this booking overlaps with another trip.
     * 
     * @param trip the trip to check against
     * @return true if there's an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        // Logic to check overlap
        return false; // Placeholder
    }

    // Getters and setters
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

    public Booking() {}
}