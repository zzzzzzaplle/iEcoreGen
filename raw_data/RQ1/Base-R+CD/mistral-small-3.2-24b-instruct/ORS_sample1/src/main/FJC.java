import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Calendar;

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
     * Check if two indirect trips by the same driver share any common stops.
     * @param trip1 The first trip to compare
     * @param trip2 The second trip to compare
     * @return true if any stop station is shared, false otherwise
     */
    public boolean checkStopOverlap(Trip trip1, Trip trip2) {
        Set<String> trip1Stops = trip1.getStopStations();
        Set<String> trip2Stops = trip2.getStopStations();
        trip1Stops.retainAll(trip2Stops);
        return !trip1Stops.isEmpty();
    }

    /**
     * Check if a new trip can be posted by the driver.
     * @param newTrip The new trip to be posted
     * @return true if the new trip can be posted, false otherwise
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
     * Calculate total reward points earned by the customer in the given current month.
     * @param currentMonth The target month to calculate points for (format: "yyyy-MM")
     * @return totalPoints The total reward points earned
     */
    public int computeMonthlyRewardPoints(String currentMonth) {
        if (membershipPackage == null || !membershipPackage.hasAward(Award.POINTS)) {
            return 0;
        }
        int totalPoints = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String[] monthParts = currentMonth.split("-");
            int year = Integer.parseInt(monthParts[0]);
            int month = Integer.parseInt(monthParts[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, 1);
            Date firstDayOfMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date lastDayOfMonth = calendar.getTime();
            for (Trip trip : getTrips()) {
                for (Booking booking : trip.getBookings()) {
                    if (booking.getCustomer().equals(this)) {
                        Date bookingDate = booking.getBookingDate();
                        if (bookingDate.after(firstDayOfMonth) && bookingDate.before(lastDayOfMonth)) {
                            totalPoints += booking.getNumberOfSeats() * 5;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPoints;
    }

    /**
     * Book seats on a trip if eligible.
     * @param trip The trip to book seats on
     * @param numberOfSeats The number of seats to book
     * @throws IllegalArgumentException If the booking is not eligible
     */
    public void bookTrip(Trip trip, int numberOfSeats) {
        if (!trip.bookSeats(numberOfSeats)) {
            throw new IllegalArgumentException("Booking is not eligible");
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
     * Calculate the discounted price for a booking if conditions are met.
     * @param customer The customer making the booking
     * @param bookingTime The time of the booking
     * @return The discounted price if conditions are met, otherwise the original price
     */
    public double calculateDiscountedPrice(Customer customer, String bookingTime) {
        if (customer.getMembershipPackage() != null && customer.getMembershipPackage().hasAward(Award.DISCOUNTS)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date bookingDate = sdf.parse(bookingTime);
                Date departureDateTime = sdf.parse(departureDate + " " + departureTime);
                long diff = departureDateTime.getTime() - bookingDate.getTime();
                if (diff >= 24 * 60 * 60 * 1000) {
                    return Math.round(price * 0.8 * 10) / 10.0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    /**
     * Calculate the total reward points earned by a customer in the given current month.
     * @param customer The customer to calculate points for
     * @param currentMonth The target month to calculate points for (format: "yyyy-MM")
     * @return totalPoints The total reward points earned
     */
    public int calculateMonthlyPoints(Customer customer, String currentMonth) {
        return customer.computeMonthlyRewardPoints(currentMonth);
    }

    /**
     * Book seats on the trip if eligible.
     * @param num The number of seats to book
     * @return true if the booking is successful, false otherwise
     */
    public boolean bookSeats(int num) {
        if (numberOfSeats >= num) {
            for (Booking booking : bookings) {
                if (booking.getCustomer().equals(customer) && booking.overlapsWith(this)) {
                    return false;
                }
            }
            numberOfSeats -= num;
            return true;
        }
        return false;
    }

    /**
     * Confirm a booking and update the number of seats.
     * @param seats The number of seats to confirm
     * @return true if the booking is confirmed, false otherwise
     */
    public boolean confirmBooking(int seats) {
        for (Booking booking : bookings) {
            if (booking.getNumberOfSeats() == seats && booking.isBookingEligible()) {
                booking.updateTripSeats();
                return true;
            }
        }
        return false;
    }

    /**
     * Get all stop stations for the trip.
     * @return A set of stop stations
     */
    public Set<String> getStopStations() {
        Set<String> stopStations = new HashSet<>();
        for (Stop stop : stops) {
            stopStations.add(stop.getStopStation());
        }
        return stopStations;
    }

    /**
     * Check if the new trip time conflicts with any existing trip time.
     * @param newDepartureTime The new trip's departure time
     * @param newArrivalTime The new trip's arrival time
     * @return true if there is a time conflict, false otherwise
     */
    public boolean isTimeConflicting(String newDepartureTime, String newArrivalTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date newDeparture = sdf.parse(newDepartureTime);
            Date newArrival = sdf.parse(newArrivalTime);
            for (Booking booking : bookings) {
                Date bookingDeparture = sdf.parse(booking.getTrip().getDepartureTime());
                Date bookingArrival = sdf.parse(booking.getTrip().getArrivalTime());
                if (newDeparture.before(bookingArrival) && newArrival.after(bookingDeparture)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
     * Check if the booking is eligible.
     * @return true if the booking is eligible, false otherwise
     */
    public boolean isBookingEligible() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingTime = new Date();
            Date departureTime = sdf.parse(trip.getDepartureDate() + " " + trip.getDepartureTime());
            long diff = departureTime.getTime() - bookingTime.getTime();
            if (diff < 2 * 60 * 60 * 1000) {
                return false;
            }
            for (Booking existingBooking : trip.getBookings()) {
                if (existingBooking.getCustomer().equals(customer) && existingBooking.overlapsWith(trip)) {
                    return false;
                }
            }
            return trip.getNumberOfSeats() >= numberOfSeats;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the number of seats for the trip.
     */
    public void updateTripSeats() {
        trip.setNumberOfSeats(trip.getNumberOfSeats() - numberOfSeats);
    }

    /**
     * Check if the booking overlaps with another trip on the same day.
     * @param trip The trip to check for overlap
     * @return true if there is an overlap, false otherwise
     */
    public boolean overlapsWith(Trip trip) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date bookingDeparture = sdf.parse(this.trip.getDepartureDate() + " " + this.trip.getDepartureTime());
            Date bookingArrival = sdf.parse(this.trip.getDepartureDate() + " " + this.trip.getArrivalTime());
            Date tripDeparture = sdf.parse(trip.getDepartureDate() + " " + trip.getDepartureTime());
            Date tripArrival = sdf.parse(trip.getDepartureDate() + " " + trip.getArrivalTime());
            if (bookingDeparture.before(tripArrival) && bookingArrival.after(tripDeparture)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * Check if the membership package has a specific award.
     * @param award The award to check for
     * @return true if the award is present, false otherwise
     */
    public boolean hasAward(Award award) {
        for (Award a : awards) {
            if (a.equals(award)) {
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