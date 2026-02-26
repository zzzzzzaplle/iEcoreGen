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
     * @param trip1 First trip to check for stop overlap
     * @param trip2 Second trip to check for stop overlap
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
     * Validates if a new trip can be posted by checking for time conflicts with existing trips
     * @param newTrip The new trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        
        for (Trip existingTrip : trips) {
            try {
                if (existingTrip.isTimeConflicting(newTrip.getDepartureTime(), newTrip.getArrivalTime())) {
                    return false;
                }
            } catch (ParseException e) {
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
     * Calculates total reward points earned by the customer in the given current month
     * @param currentMonth The target month in format "yyyy-MM"
     * @return Total points earned in the target month
     * @throws ParseException if the month format is invalid
     */
    public int computeMonthlyRewardPoints(String currentMonth) throws ParseException {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        int totalPoints = 0;
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        Date targetMonth = monthFormat.parse(currentMonth);
        
        for (Booking booking : bookings) {
            Date bookingDate = booking.getBookingDate();
            if (monthFormat.format(bookingDate).equals(currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }
    
    /**
     * Books seats on a trip if eligible
     * @param trip The trip to book
     * @param numberOfSeats The number of seats to book
     * @throws Exception if booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) throws Exception {
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            bookings.add(booking);
            trip.getBookings().add(booking);
        } else {
            throw new Exception("Booking is not eligible");
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
     * @param customer The customer making the booking
     * @param bookingTime The booking time in "yyyy-MM-dd HH:mm" format
     * @return Final price after discount (1 decimal place) or original price if conditions not met
     * @throws ParseException if time format is invalid
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) throws ParseException {
        if (customer.getMembershipPackage() != null && 
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date bookingDateTime = format.parse(bookingTime);
            Date departureDateTime = format.parse(departureTime);
            
            long timeDifference = departureDateTime.getTime() - bookingDateTime.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);
            
            if (hoursDifference >= 24) {
                double discountedPrice = price * 0.8;
                return Math.round(discountedPrice * 10.0) / 10.0;
            }
        }
        return price;
    }
    
    /**
     * Books seats on the trip if available
     * @param num Number of seats to book
     * @return true if seats were successfully booked, false otherwise
     */
    public boolean bookSeats(int num) {
        if (num <= numberOfSeats) {
            numberOfSeats -= num;
            return true;
        }
        return false;
    }
    
    /**
     * Confirms booking by checking seat availability
     * @param seats Number of seats to confirm
     * @return true if seats are available, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return seats <= numberOfSeats;
    }
    
    /**
     * Gets all stop stations as a set
     * @return Set of all stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }
    
    /**
     * Checks if a new trip time conflicts with this trip's time period
     * @param newDepartureTime New departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime New arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if time periods conflict, false otherwise
     * @throws ParseException if time format is invalid
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date newDeparture = format.parse(newDepartureTime);
        Date newArrival = format.parse(newArrivalTime);
        Date currentDeparture = format.parse(departureTime);
        Date currentArrival = format.parse(arrivalTime);
        
        // Check for time overlap (excluding adjacent boundaries)
        return (newDeparture.before(currentArrival) && newArrival.after(currentDeparture));
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
     * Checks if the booking is eligible based on trip availability and customer constraints
     * @return true if booking is eligible, false otherwise
     * @throws ParseException if time format is invalid
     */
    public boolean isBookingEligible() throws ParseException {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check if trip has available seats
        if (!trip.confirmBooking(numberOfSeats)) {
            return false;
        }
        
        // Check booking time constraint (at least 2 hours before departure)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date bookingDateTime = bookingDate;
        Date departureDateTime = format.parse(trip.getDepartureTime());
        
        long timeDifference = departureDateTime.getTime() - bookingDateTime.getTime();
        long hoursDifference = timeDifference / (60 * 60 * 1000);
        
        if (hoursDifference <= 2) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings on the same day
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
     * Checks if this booking overlaps time-wise with another trip
     * @param otherTrip The trip to check for overlap
     * @return true if time periods overlap, false otherwise
     * @throws ParseException if time format is invalid
     */
    public boolean overlapsWith(Trip otherTrip) throws ParseException {
        if (trip == null || otherTrip == null) {
            return false;
        }
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thisDeparture = format.parse(trip.getDepartureTime());
        Date thisArrival = format.parse(trip.getArrivalTime());
        Date otherDeparture = format.parse(otherTrip.getDepartureTime());
        Date otherArrival = format.parse(otherTrip.getArrivalTime());
        
        // Check if bookings are on the same day
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(thisDeparture);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(otherDeparture);
        
        if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) || 
            cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH) || 
            cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        
        // Check for time overlap
        return (thisDeparture.before(otherArrival) && thisArrival.after(otherDeparture));
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
     * Checks if the membership package contains a specific award type
     * @param award The award type to check for
     * @return true if the award is included, false otherwise
     */
    public boolean hasAward(Award award) {
        if (awards == null) return false;
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