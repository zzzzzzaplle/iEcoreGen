import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

abstract class User {
    private String ID;
    private String email;
    private String phoneNumber;
    
    public User() {}
    
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
     * @param trip1 the first trip to check for common stops
     * @param trip2 the second trip to check for common stops
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        
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
     * Validates if a driver can post a new trip by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflicts exist and trip timing is valid, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        
        // Check if departure time is before arrival time
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date departure = format.parse(newTrip.getDepartureTime());
            Date arrival = format.parse(newTrip.getArrivalTime());
            
            if (departure.compareTo(arrival) >= 0) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        
        // Check for time conflicts with existing trips
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
        bookings = new ArrayList<>();
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
     * Calculates total reward points earned by a customer in the given current month
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned from eligible bookings in the target month
     * @throws ParseException if the currentMonth parameter has invalid format
     */
    public int computeMonthlyRewardPoints(String currentMonth) throws ParseException {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        int totalPoints = 0;
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat bookingDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Booking booking : bookings) {
            String bookingMonth = monthFormat.format(booking.getBookingDate());
            if (bookingMonth.equals(currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        
        return totalPoints;
    }
    
    /**
     * Attempts to book seats on a trip for the customer
     * @param trip the trip to book
     * @param numberOfSeats the number of seats to book
     * @throws IllegalStateException if booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (!booking.isBookingEligible()) {
            throw new IllegalStateException("Booking is not eligible");
        }
        
        booking.updateTripSeats();
        bookings.add(booking);
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
     * Calculates the discounted price for a booking after applying reward discounts
     * @param customer the customer making the booking
     * @param bookingTime the booking time in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount if conditions are met, otherwise original price
     * @throws ParseException if the bookingTime or departureTime has invalid format
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) throws ParseException {
        if (customer.getMembershipPackage() == null || 
            !customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            return price;
        }
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDateTime = format.parse(bookingTime);
        Date departureDateTime = format.parse(departureTime);
        
        long timeDifference = departureDateTime.getTime() - bookingDateTime.getTime();
        long hoursDifference = timeDifference / (60 * 60 * 1000);
        
        if (hoursDifference >= 24) {
            double discountedPrice = price * 0.8;
            return Math.round(discountedPrice * 10.0) / 10.0; // Keep 1 decimal place
        }
        
        return price;
    }
    
    /**
     * Books seats on the trip if available
     * @param num the number of seats to book
     * @return true if seats were successfully booked, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= 0 || num > numberOfSeats) {
            return false;
        }
        numberOfSeats -= num;
        return true;
    }
    
    /**
     * Confirms booking by checking seat availability
     * @param seats the number of seats to confirm
     * @return true if seats are available, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return seats > 0 && seats <= numberOfSeats;
    }
    
    /**
     * Gets all stop stations including departure and arrival stations
     * @return a set of all stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        stopStations.add(departureStation);
        stopStations.add(arrivalStation);
        
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        
        return stopStations;
    }
    
    /**
     * Checks if a new trip time conflicts with this trip's time period
     * @param newDepartureTime the new departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime the new arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if time periods intersect (excluding adjacent boundaries), false otherwise
     * @throws ParseException if time parameters have invalid format
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Date newDeparture = format.parse(newDepartureTime);
        Date newArrival = format.parse(newArrivalTime);
        Date currentDeparture = format.parse(departureTime);
        Date currentArrival = format.parse(arrivalTime);
        
        // Adjacent boundaries are not considered conflicts
        if (newArrival.equals(currentDeparture) || currentArrival.equals(newDeparture)) {
            return false;
        }
        
        // Check for time period intersection
        return (newDeparture.before(currentArrival) && newArrival.after(currentDeparture)) ||
               (currentDeparture.before(newArrival) && currentArrival.after(newDeparture));
    }
}

class Stop {
    private String stopStation;
    
    public Stop() {}
    
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
    
    public Booking() {}
    
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
     * Validates if a booking is eligible based on trip availability and customer constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check if trip has available seats
        if (numberOfSeats <= 0 || !trip.confirmBooking(numberOfSeats)) {
            return false;
        }
        
        // Check if booking time is at least 2 hours before departure
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = format.parse(format.format(bookingDate));
            Date departureDateTime = format.parse(trip.getDepartureTime());
            
            long timeDifference = departureDateTime.getTime() - bookingDateTime.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);
            
            if (hoursDifference <= 2) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        
        // Check for overlapping time periods on the same day
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.overlapsWith(trip)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Updates the trip's available seats after successful booking
     */
    public void updateTripSeats() {
        trip.bookSeats(numberOfSeats);
    }
    
    /**
     * Checks if this booking overlaps in time with another trip
     * @param otherTrip the trip to check for overlap
     * @return true if time periods overlap on the same day, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (trip == null || otherTrip == null) {
            return false;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date thisDeparture = format.parse(trip.getDepartureTime());
            Date thisArrival = format.parse(trip.getArrivalTime());
            Date otherDeparture = format.parse(otherTrip.getDepartureTime());
            Date otherArrival = format.parse(otherTrip.getArrivalTime());
            
            // Check if same day
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!dateFormat.format(thisDeparture).equals(dateFormat.format(otherDeparture))) {
                return false;
            }
            
            // Check for time overlap (excluding adjacent boundaries)
            return (thisDeparture.before(otherArrival) && thisArrival.after(otherDeparture)) ||
                   (otherDeparture.before(thisArrival) && otherArrival.after(thisDeparture));
            
        } catch (ParseException e) {
            return false;
        }
    }
}

class MembershipPackage {
    private Award[] awards;
    
    public MembershipPackage() {}
    
    public Award[] getAwards() {
        return awards;
    }
    
    public void setAwards(Award[] awards) {
        this.awards = awards;
    }
    
    /**
     * Checks if the membership package includes a specific award type
     * @param award the award type to check for
     * @return true if the award is included, false otherwise
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