import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
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
     * @param trip1 the first trip to check for common stops
     * @param trip2 the second trip to check for common stops
     * @return true if any stop station is shared between the two trips, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        if (trip1 == null || trip2 == null) {
            return false;
        }
        
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
     * Validates if a new trip can be posted by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflict exists, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (newTrip == null) {
            return false;
        }
        
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
     * Calculates total reward points earned by the customer in the given month
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned from bookings in the target month
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        int totalPoints = 0;
        for (Booking booking : bookings) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
            String bookingMonth = monthFormat.format(booking.getBookingDate());
            
            if (bookingMonth.equals(currentMonth)) {
                totalPoints += booking.getNumberOfSeats() * 5;
            }
        }
        return totalPoints;
    }

    public void bookTrip(Trip trip, int numberOfSeats) {
        // Implementation would handle booking logic
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
     * Calculates discounted price for a booking after applying reward discounts
     * @param customer the customer making the booking
     * @param bookingTime the time when booking is made in "yyyy-MM-dd HH:mm" format
     * @return final price after discount (1 decimal place) or original price if conditions not met
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer == null || bookingTime == null) {
            return price;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String departureDateStr = dateFormat.format(departureDate);
            LocalDateTime departureDateTime = LocalDateTime.parse(departureDateStr + " " + departureTime, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            long hoursDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toHours();
            
            if (customer.getMembershipPackage() != null && 
                customer.getMembershipPackage().hasAward(Award.DISCOUNTS) && 
                hoursDifference >= 24) {
                double discountedPrice = price * 0.8;
                return Math.round(discountedPrice * 10.0) / 10.0;
            }
        } catch (Exception e) {
            // If parsing fails, return original price
        }
        
        return price;
    }

    /**
     * Calculates monthly points for a specific customer from this trip's bookings
     * @param customer the customer whose points are being calculated
     * @param currentMonth the target month in "yyyy-MM" format
     * @return total points earned by the customer from this trip in the target month
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        if (customer == null || currentMonth == null) {
            return 0;
        }
        
        int points = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
                String bookingMonth = monthFormat.format(booking.getBookingDate());
                
                if (bookingMonth.equals(currentMonth)) {
                    points += booking.getNumberOfSeats() * 5;
                }
            }
        }
        return points;
    }

    /**
     * Attempts to book seats on the trip
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
     * Confirms a booking by validating eligibility and updating seats
     * @param seats the number of seats to confirm
     * @return true if booking is confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        return bookSeats(seats);
    }

    /**
     * Gets all stop stations including departure and arrival stations
     * @return set of all stop stations
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
     * @param newDepartureTime new trip's departure time in "yyyy-MM-dd HH:mm" format
     * @param newArrivalTime new trip's arrival time in "yyyy-MM-dd HH:mm" format
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        if (newDepartureTime == null || newArrivalTime == null) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime newDeparture = LocalDateTime.parse(newDepartureTime, formatter);
            LocalDateTime newArrival = LocalDateTime.parse(newArrivalTime, formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String departureDateStr = dateFormat.format(departureDate);
            LocalDateTime currentDeparture = LocalDateTime.parse(departureDateStr + " " + departureTime, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime currentArrival = LocalDateTime.parse(departureDateStr + " " + arrivalTime, 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            // Check for time conflict (excluding adjacent boundaries)
            return (newDeparture.isBefore(currentArrival) && newArrival.isAfter(currentDeparture));
            
        } catch (Exception e) {
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
     * Checks if the booking is eligible based on various criteria
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        // Check available seats
        if (numberOfSeats > trip.getNumberOfSeats()) {
            return false;
        }
        
        // Check booking time is at least 2 hours before departure
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime bookingDateTime = LocalDateTime.parse(
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(bookingDate), formatter);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String departureDateStr = dateFormat.format(trip.getDepartureDate());
            LocalDateTime departureDateTime = LocalDateTime.parse(
                departureDateStr + " " + trip.getDepartureTime(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            long minutesDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toMinutes();
            if (minutesDifference <= 120) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
        // Check for overlapping time periods with existing bookings on same day
        for (Booking existingBooking : customer.getBookings()) {
            if (existingBooking.overlapsWith(trip)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Updates the trip seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null && isBookingEligible()) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if this booking overlaps in time with another trip
     * @param otherTrip the trip to check for overlap
     * @return true if there is a time overlap, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (trip == null || otherTrip == null) {
            return false;
        }
        
        // Check if bookings are on the same day
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bookingDay = dateFormat.format(bookingDate);
        String otherTripDay = dateFormat.format(otherTrip.getDepartureDate());
        
        if (!bookingDay.equals(otherTripDay)) {
            return false;
        }
        
        return trip.isTimeConflicting(otherTrip.getDepartureTime(), otherTrip.getArrivalTime());
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