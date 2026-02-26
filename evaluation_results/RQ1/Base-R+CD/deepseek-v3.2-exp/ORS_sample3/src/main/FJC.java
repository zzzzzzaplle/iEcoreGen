import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
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
        
        Set<String> stopStations1 = trip1.getStopStations();
        Set<String> stopStations2 = trip2.getStopStations();
        
        for (String stop : stopStations1) {
            if (stopStations2.contains(stop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if a new trip can be posted by checking for time conflicts with existing trips
     * @param newTrip the new trip to be posted
     * @return true if no time conflict exists and the driver is valid, false otherwise
     */
    public boolean canPostTrip(Trip newTrip) {
        if (this.getID() == null || newTrip == null) {
            return false;
        }
        
        if (!newTrip.isValidTime()) {
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
     * @throws ParseException if the currentMonth parameter format is invalid
     */
    public int computeMonthlyRewardPoints(String currentMonth) throws ParseException {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        Date targetMonth = monthFormat.parse(currentMonth);
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetMonth);
        
        int totalPoints = 0;
        // This method would need access to all bookings made by this customer
        // Implementation would iterate through customer's bookings and sum points
        return totalPoints;
    }

    /**
     * Books seats on a trip if eligible
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
     * Calculates the discounted price for a booking based on customer membership and booking time
     * @param customer the customer making the booking
     * @param bookingTime the booking time in "yyyy-MM-dd HH:mm" format
     * @return the final price after discount if eligible, otherwise the original price
     * @throws ParseException if the time format is invalid
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) throws ParseException {
        if (customer == null || bookingTime == null) {
            return price;
        }
        
        if (customer.getMembershipPackage() == null) {
            return price;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime bookingDateTime = LocalDateTime.parse(bookingTime, formatter);
        LocalDateTime departureDateTime = LocalDateTime.parse(
            new SimpleDateFormat("yyyy-MM-dd").format(departureDate) + " " + departureTime, 
            formatter
        );
        
        long hoursDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toHours();
        
        if (hoursDifference >= 24) {
            return Math.round(price * 0.8 * 10) / 10.0;
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
     * Checks if the trip has valid time (departure before arrival)
     * @return true if departure time is before arrival time, false otherwise
     */
    public boolean isValidTime() {
        if (departureTime == null || arrivalTime == null) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime depTime = LocalDateTime.parse(
                new SimpleDateFormat("yyyy-MM-dd").format(departureDate) + " " + departureTime, 
                formatter
            );
            LocalDateTime arrTime = LocalDateTime.parse(
                new SimpleDateFormat("yyyy-MM-dd").format(departureDate) + " " + arrivalTime, 
                formatter
            );
            
            return depTime.isBefore(arrTime);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a new time period conflicts with this trip's time period
     * @param newDepartureTime the new departure time in "HH:mm" format
     * @param newArrivalTime the new arrival time in "HH:mm" format
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String baseDate = new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
            
            LocalDateTime currentDep = LocalDateTime.parse(baseDate + " " + departureTime, formatter);
            LocalDateTime currentArr = LocalDateTime.parse(baseDate + " " + arrivalTime, formatter);
            LocalDateTime newDep = LocalDateTime.parse(baseDate + " " + newDepartureTime, formatter);
            LocalDateTime newArr = LocalDateTime.parse(baseDate + " " + newArrivalTime, formatter);
            
            return (newDep.isBefore(currentArr) && newArr.isAfter(currentDep)) ||
                   (currentDep.isBefore(newArr) && currentArr.isAfter(newDep)) ||
                   newDep.equals(currentDep) || newArr.equals(currentArr);
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
     * Checks if the booking is eligible based on seat availability and time constraints
     * @return true if booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        if (trip == null || customer == null) {
            return false;
        }
        
        if (numberOfSeats <= 0 || !trip.confirmBooking(numberOfSeats)) {
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime bookingDateTime = LocalDateTime.parse(
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(bookingDate), 
                formatter
            );
            LocalDateTime departureDateTime = LocalDateTime.parse(
                new SimpleDateFormat("yyyy-MM-dd").format(trip.getDepartureDate()) + " " + trip.getDepartureTime(), 
                formatter
            );
            
            long hoursDifference = java.time.Duration.between(bookingDateTime, departureDateTime).toHours();
            if (hoursDifference <= 2) {
                return false;
            }
            
            for (Booking existingBooking : customer.getBookings()) {
                if (existingBooking.overlapsWith(trip)) {
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the trip's available seats after successful booking
     */
    public void updateTripSeats() {
        if (trip != null) {
            trip.bookSeats(numberOfSeats);
        }
    }

    /**
     * Checks if this booking overlaps in time with another trip on the same day
     * @param otherTrip the trip to check for overlap
     * @return true if there is a time overlap on the same day, false otherwise
     */
    public boolean overlapsWith(Trip otherTrip) {
        if (trip == null || otherTrip == null) {
            return false;
        }
        
        if (!trip.getDepartureDate().equals(otherTrip.getDepartureDate())) {
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
     * Checks if the membership package contains a specific award type
     * @param award the award type to check for
     * @return true if the award is included in the package, false otherwise
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