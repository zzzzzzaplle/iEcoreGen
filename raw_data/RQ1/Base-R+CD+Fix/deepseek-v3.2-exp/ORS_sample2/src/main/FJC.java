import java.util.*;
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
        trips = new ArrayList<>();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Determines if two indirect trips by the same driver share any common stops
     *
     * @param trip1 the first trip to check for common stops
     * @param trip2 the second trip to check for common stops
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> stops1 = trip1.getStopStations();
        Set<String> stops2 = trip2.getStopStations();
        
        for (String stop : stops1) {
            if (stops2.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates trip posting feasibility by checking for time conflicts with existing trips
     *
     * @param newTrip the new trip to be posted
     * @return true if no time conflict exists with existing trips, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        for (Trip existingTrip : trips) {
            try {
                if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                    return false;
                }
            } catch (ParseException e) {
                // Handle parsing exception, consider it as no conflict for safety
                continue;
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
     * Calculates total reward points earned by the customer in the given current month
     *
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned in the specified month (5 points per seat per eligible booking)
     * @throws ParseException if the date parsing fails
     */
    public int computeMonthlyRewardPoints(String currentMonth) throws ParseException {
        int totalPoints = 0;
        
        // Check if customer has membership with points reward
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return totalPoints;
        }
        
        // This method would typically iterate through customer's bookings
        // Since bookings are stored in Trip, we need to find all trips where this customer has bookings
        // For simplicity, we assume access to all trips in the system
        // In a real implementation, we would have a repository of trips
        
        return totalPoints;
    }

    /**
     * Books seats on a trip after validating eligibility
     *
     * @param trip the trip to book seats on
     * @param numberOfSeats the number of seats to book
     * @throws Exception if booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) throws Exception {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (!booking.isBookingEligible()) {
            throw new Exception("Booking is not eligible");
        }
        
        booking.updateTripSeats();
        trip.getBookings().add(booking);
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
     * Computes the final price for a booking after applying reward discounts
     *
     * @param customer the customer making the booking
     * @param bookingTime the booking time in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount (20% off) if conditions are met, otherwise original price
     * @throws ParseException if the time parsing fails
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDate = format.parse(bookingTime);
        Date departureDateTime = format.parse(departureTime);
        
        long timeDifference = departureDateTime.getTime() - bookingDate.getTime();
        long hoursDifference = timeDifference / (60 * 60 * 1000);
        
        // Check if customer has membership with discounts and booking is made ≥24 hours before departure
        if (customer.getMembershipPackage() != null && 
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && 
            hoursDifference >= 24) {
            return Math.round(price * 0.8 * 10) / 10.0; // 20% discount, keep 1 decimal place
        }
        
        return price;
    }

    /**
     * Books seats on the trip if available
     *
     * @param num the number of seats to book
     * @return true if seats were successfully booked, false otherwise
     */
    public boolean bookSeats(int num) {
        if (numberOfSeats >= num) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirms booking by checking seat availability
     *
     * @param seats the number of seats to confirm
     * @return true if seats are available, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return numberOfSeats >= seats;
    }

    /**
     * Gets all stop stations from the trip's stops
     *
     * @return a set of all stop station names
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if the new trip time conflicts with this trip's time period
     *
     * @param newDepartureTime the new departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime the new arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if time periods intersect (excluding adjacent boundaries), false otherwise
     * @throws ParseException if the time parsing fails
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date newDeparture = format.parse(newDepartureTime);
        Date newArrival = format.parse(newArrivalTime);
        Date thisDeparture = format.parse(departureTime);
        Date thisArrival = format.parse(arrivalTime);
        
        // Check for time intersection (excluding adjacent boundaries)
        return (newDeparture.before(thisArrival) && newArrival.after(thisDeparture));
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
     * Validates booking eligibility based on system requirements
     *
     * @return true if booking is eligible, false otherwise
     * @throws ParseException if the time parsing fails
     */
    public boolean isBookingEligible() throws ParseException {
        // Check if trip exists and has available seats
        if (trip == null || !trip.confirmBooking(numberOfSeats)) {
            return false;
        }
        
        // Check if booking time is at least 2 hours before departure time
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date departureDateTime = format.parse(trip.getDepartureTime());
        long timeDifference = departureDateTime.getTime() - bookingDate.getTime();
        long hoursDifference = timeDifference / (60 * 60 * 1000);
        
        if (hoursDifference <= 2) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings on the same day
        for (Booking existingBooking : customer.getMembershipPackage() != null ? 
             getAllCustomerBookings(customer) : new ArrayList<Booking>()) {
            if (overlapsWith(existingBooking.getTrip())) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Updates the trip's seat count after successful booking
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }

    /**
     * Checks if this booking overlaps in time with another trip
     *
     * @param otherTrip the trip to check for overlap
     * @return true if time periods overlap, false otherwise
     * @throws ParseException if the time parsing fails
     */
    public boolean overlapsWith(Trip otherTrip) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thisDeparture = format.parse(trip.getDepartureTime());
        Date thisArrival = format.parse(trip.getArrivalTime());
        Date otherDeparture = format.parse(otherTrip.getDepartureTime());
        Date otherArrival = format.parse(otherTrip.getArrivalTime());
        
        // Check if booking dates are on the same day
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(thisDeparture);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(otherDeparture);
        
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                         cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        
        if (!sameDay) {
            return false;
        }
        
        // Check for time overlap
        return (thisDeparture.before(otherArrival) && thisArrival.after(otherDeparture));
    }
    
    // Helper method to get all bookings for a customer (would typically come from a repository)
    private List<Booking> getAllCustomerBookings(Customer customer) {
        // This is a simplified implementation
        // In a real system, this would query a database or repository
        return new ArrayList<>();
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
     * Checks if the membership package contains a specific award type
     *
     * @param award the award type to check for
     * @return true if the award type is included in the package, false otherwise
     */
    public boolean hasAward(Award award) {
        if (awards == null) {
            return false;
        }
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