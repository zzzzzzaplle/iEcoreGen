import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a car in the gallery.
 */
 class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /** Unparameterized constructor */
    public Car() {
    }

    /** Parameterized constructor for convenience */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
    }

    // ---------- Getters & Setters ----------
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    // ---------- Equality (used as map key) ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Car)) return false;
        Car other = (Car) obj;
        return (plate != null ? plate.equals(other.plate) : other.plate == null);
    }

    @Override
    public int hashCode() {
        return plate != null ? plate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Car{plate='" + plate + "', model='" + model + "', dailyPrice=" + dailyPrice + '}';
    }
}

/**
 * Represents a customer.
 */
 class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate; // plate of the currently rented car, if any

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Parameterized constructor for convenience */
    public Customer(String name, String surname, String address, String rentedCarPlate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.rentedCarPlate = rentedCarPlate;
    }

    // ---------- Getters & Setters ----------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }

    // ---------- Equality (used as map key) ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return (name != null ? name.equals(other.name) : other.name == null)
                && (surname != null ? surname.equals(other.surname) : other.surname == null)
                && (address != null ? address.equals(other.address) : other.address == null);
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', surname='" + surname + "', address='" + address + "'}";
    }
}

/**
 * Represents a rental transaction.
 */
 class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate; // null if not yet returned
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /** Unparameterized constructor */
    public Rental() {
    }

    /** Parameterized constructor for convenience */
    public Rental(Date rentalDate, Date dueDate, Date backDate, double totalPrice,
                  String leasingTerms, Car car, Customer customer) {
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.backDate = backDate;
        this.totalPrice = totalPrice;
        this.leasingTerms = leasingTerms;
        this.car = car;
        this.customer = customer;
    }

    // ---------- Getters & Setters ----------
    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLeasingTerms() {
        return leasingTerms;
    }

    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Rental{car=" + car + ", customer=" + customer + ", rentalDate=" + rentalDate
                + ", dueDate=" + dueDate + ", backDate=" + backDate + ", totalPrice=" + totalPrice + '}';
    }
}

/**
 * Represents an overdue notice that can be sent to a customer.
 */
 class OverdueNotice {
    private Customer customer;

    /** Unparameterized constructor */
    public OverdueNotice() {
    }

    /** Parameterized constructor for convenience */
    public OverdueNotice(Customer customer) {
        this.customer = customer;
    }

    // ---------- Getters & Setters ----------
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Simulates sending an overdue notice to a customer.
     *
     * @param customer the customer to whom the notice will be sent
     */
    public void sendNoticeTo(Customer customer) {
        // In a real system this could send an email or SMS.
        System.out.println("Overdue notice sent to: " + customer);
    }

    @Override
    public String toString() {
        return "OverdueNotice{customer=" + customer + '}';
    }
}

/**
 * Central class that manages cars, rentals, and overdue notices.
 */
 class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /** Unparameterized constructor */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<OverdueNotice> getNotices() {
        return notices;
    }

    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }

    // ---------- Helper methods to add data ----------
    public void addCar(Car car) {
        this.cars.add(car);
    }

    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    public void addOverdueNotice(OverdueNotice notice) {
        this.notices.add(notice);
    }

    /**
     * Identify all cars that are currently not rented.
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         Returns an empty list if no cars are available.
     */
    public List<Car> identifyAvailableCars() {
        Set<String> rentedPlates = new HashSet<>();
        for (Rental r : rentals) {
            // If backDate is null, the car is still out.
            if (r.getBackDate() == null && r.getCar() != null) {
                rentedPlates.add(r.getCar().getPlate());
            }
        }

        List<Car> available = new ArrayList<>();
        for (Car c : cars) {
            if (!rentedPlates.contains(c.getPlate())) {
                available.add(c);
            }
        }

        // Sort by daily price ascending
        Collections.sort(available, Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculate the total revenue generated by all rentals.
     *
     * @return sum of totalPrice of every rental in the store.
     */
    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Rental r : rentals) {
            sum += r.getTotalPrice();
        }
        return sum;
    }

    /**
     * Find all customers that have overdue rentals.
     * A rental is overdue when its backDate is null and the current date is after its dueDate.
     *
     * @param currentDate the date against which overdue status is evaluated
     * @return a list of distinct customers with overdue rentals.
     *         Returns an empty list if none are overdue.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        Set<Customer> overdueCustomers = new HashSet<>();
        for (Rental r : rentals) {
            if (r.getBackDate() == null && r.getDueDate() != null && r.getDueDate().before(currentDate)) {
                overdueCustomers.add(r.getCustomer());
            }
        }
        return new ArrayList<>(overdueCustomers);
    }

    /**
     * Determine the average daily price of all cars in the store.
     *
     * @return average daily price, or 0.0 if the store has no cars.
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Car c : cars) {
            total += c.getDailyPrice();
        }
        return total / cars.size();
    }

    /**
     * Count how many cars each customer has rented.
     *
     * @return a map where the key is a Customer and the value is the number of rentals made by that customer.
     *         Returns an empty map if no rentals exist.
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental r : rentals) {
            Customer cust = r.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }
}