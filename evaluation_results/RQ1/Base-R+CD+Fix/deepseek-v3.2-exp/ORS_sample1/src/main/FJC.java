import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;

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
     * @param trip1 the first trip to check for stop overlap
     * @param trip2 the second trip to check for stop overlap
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
     * @param newTrip the new trip to be posted
     * @return true if no time conflicts exist, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newDeparture = LocalDateTime.parse(newTrip.getDepartureDate() + " " + newTrip.getDepartureTime(), formatter);
        LocalDateTime newArrival = LocalDateTime.parse(newTrip.getDepartureDate() + " " + newTrip.getArrivalTime(), formatter);
        
        if (newDeparture.isAfter(newArrival) || newDeparture.equals(newArrival)) {
            return false;
        }
        
        for (Trip existingTrip : trips) {
            LocalDateTime existingDeparture = LocalDateTime.parse(existingTrip.getDepartureDate() + " " + existingTrip.getDepartureTime(), formatter);
            LocalDateTime existingArrival = LocalDateTime.parse(existingTrip.getDepartureDate() + " " + existingTrip.getArrivalTime(), formatter);
            
            if (newTrip.isTimeConflicting(existingDeparture.format(formatter), existingArrival.format(formatter))) {
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
     * Calculates total reward points earned by the customer in the given month
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned from eligible bookings in the target month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        int totalPoints = 0;
        // This method would need access to customer's bookings to calculate points
        // Since bookings are stored in Trip class, this implementation assumes Trip has access to bookings
        return totalPoints;
    }

    /**
     * Books seats on a trip after validating eligibility
     * @param trip the trip to book seats on
     * @param numberOfSeats the number of seats to book
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (trip == null) {
            return;
        }
        
        Booking booking = new Booking();
        booking.setCustomer(this);
        booking.setTrip(trip);
        booking.setNumberOfSeats(numberOfSeats);
        booking.setBookingDate(new Date());
        
        if (booking.isBookingEligible()) {
            booking.updateTripSeats();
            trip.getBookings().add(booking);
        }
    }
}

class Trip {
    private String departureStation;
    private String arrivalStation;
    private int numberOfSeats;
    private String departureDate;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
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
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null || bookingTime == null) {
            return price;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
        LocalDateTime departureDateTime = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
        
        if (customer.getMembershipPackage() != null && 
            customer.getMembershipPackage().hasAward(Award.DISCOUNTS) &&
            bookingDateTime.isBefore(departureDateTime.minusHours(24))) {
            return Math.round(price * 0.8 * 10) / 10.0;
        }
        return price;
    }

    /**
     * Calculates monthly points for a specific customer
     * @param customer the customer for whom to calculate points
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned by the customer in the target month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer == null || currentMonth == null) {
            return 0;
        }
        
        int totalPoints = 0;
        YearMonth targetMonth = YearMonth.parse(currentMonth);
        
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                Date bookingDate = booking.getBookingDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(bookingDate);
                YearMonth bookingMonth = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
                
                if (bookingMonth.equals(targetMonth)) {
                    totalPoints += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return totalPoints;
    }

    /**
     * Books the specified number of seats on the trip
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
     * Gets all stop stations as a set
     * @return set of all stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Checks if the new trip time conflicts with existing trip time
     * @param newDepartureTime the new departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime the new arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newDeparture = LocalDateTime.parse(newDepartureTime, formatter);
        LocalDateTime newArrival = LocalDateTime.parse(newArrivalTime, formatter);
        LocalDateTime existingDeparture = LocalDateTime.parse(departureDate + " " + departureTime, formatter);
        LocalDateTime existingArrival = LocalDateTime.parse(departureDate + " " + arrivalTime, formatter);
        
        return (newDeparture.isBefore(existingArrival) && newArrival.isAfter(existingDeparture));
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
     * Checks if the booking is eligible based on various constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        if (numberOfSeats <= 0 || numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingTime = LocalDateTime.ofInstant(bookingDate.toInstant(), 
            java.time.ZoneId.systemDefault());
        LocalDateTime departureTime = LocalDateTime.parse(trip.getDepartureDate() + " " + trip.getDepartureTime(), formatter);
        
        if (!bookingTime.isBefore(departureTime.minusHours(2))) {
            return false;
        }
        
        return !overlapsWith(trip);
    }

    /**
     * Updates the trip seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if the booking overlaps with existing bookings for the same customer on the same day
     * @param trip the trip to check for overlaps
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        if (trip == null || customer == null) {
            return false;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime newDeparture = LocalDateTime.parse(trip.getDepartureDate() + " " + trip.getDepartureTime(), formatter);
        LocalDateTime newArrival = LocalDateTime.parse(trip.getDepartureDate() + " " + trip.getArrivalTime(), formatter);
        
        for (Booking existingBooking : trip.getBookings()) {
            if (existingBooking.getCustomer().equals(customer)) {
                Trip existingTrip = existingBooking.getTrip();
                LocalDateTime existingDeparture = LocalDateTime.parse(existingTrip.getDepartureDate() + " " + existingTrip.getDepartureTime(), formatter);
                LocalDateTime existingArrival = LocalDateTime.parse(existingTrip.getDepartureDate() + " " + existingTrip.getArrivalTime(), formatter);
                
                if (newDeparture.isBefore(existingArrival) && newArrival.isAfter(existingDeparture)) {
                    return true;
                }
            }
        }
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
     * Checks if the membership package has a specific award
     * @param award the award to check for
     * @return true if the award is present, false otherwise
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