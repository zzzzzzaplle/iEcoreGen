import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a car stored in the gallery.
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

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------
    // Object overrides (useful for collections)
    // -------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(plate, car.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }

    @Override
    public String toString() {
        return "Car{" +
                "plate='" + plate + '\'' +
                ", model='" + model + '\'' +
                ", dailyPrice=" + dailyPrice +
                '}';
    }
}

/**
 * Represents a customer who can rent cars.
 */
 class Customer {

    private String name;
    private String surname;
    private String address;

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Parameterized constructor for convenience */
    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------
    // Object overrides (used as key in maps)
    // -------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
               Objects.equals(surname, customer.surname) &&
               Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

/**
 * Represents a rental transaction linking a car and a customer.
 */
 class Rental {

    private Car car;
    private Customer customer;
    private LocalDate rentDate;   // date when the car was taken
    private LocalDate dueDate;    // expected return date
    private LocalDate backDate;   // actual return date (null if not returned yet)
    private double totalPrice;    // price for the whole rental period

    /** Unparameterized constructor */
    public Rental() {
    }

    /** Parameterized constructor for convenience */
    public Rental(Car car, Customer customer, LocalDate rentDate,
                  LocalDate dueDate, LocalDate backDate, double totalPrice) {
        this.car = car;
        this.customer = customer;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
        this.backDate = backDate;
        this.totalPrice = totalPrice;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------
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

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getBackDate() {
        return backDate;
    }

    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /**
     * Checks whether this rental is currently active (i.e., the car has not yet been returned).
     *
     * @return true if backDate is null, false otherwise
     */
    public boolean isActive() {
        return backDate == null;
    }

    /**
     * Checks whether this rental is overdue with respect to the supplied reference date.
     *
     * @param reference the date against which the overdue condition is evaluated
     * @return true if the rental is active and the due date is before the reference date
     */
    public boolean isOverdue(LocalDate reference) {
        return isActive() && dueDate.isBefore(reference);
    }

    @Override
    public String toString() {
        return "Rental{" +
                "car=" + car +
                ", customer=" + customer +
                ", rentDate=" + rentDate +
                ", dueDate=" + dueDate +
                ", backDate=" + backDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

/**
 * Central class that stores cars, customers and rentals.
 * Implements the functional requirements described in the specification.
 */
 class Store {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /** Unparameterized constructor */
    public Store() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters (allow test code to add elements directly if needed)
    // -------------------------------------------------------------------------
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    // -------------------------------------------------------------------------
    // Business methods implementing the functional requirements
    // -------------------------------------------------------------------------

    /**
     * Identifies all cars that are currently not rented.
     * A car is considered unavailable if there exists an active rental (backDate == null) for it.
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         Returns an empty list when no car is available.
     */
    public List<Car> getAvailableCars() {
        Set<Car> rentedCars = new HashSet<>();
        for (Rental r : rentals) {
            if (r.isActive()) {
                rentedCars.add(r.getCar());
            }
        }

        List<Car> available = new ArrayList<>();
        for (Car c : cars) {
            if (!rentedCars.contains(c)) {
                available.add(c);
            }
        }

        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all completed rentals.
     * Only rentals that have a non‑null backDate are considered as revenue‑generating.
     *
     * @return the sum of totalPrice of all completed rentals.
     */
    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Rental r : rentals) {
            if (!r.isActive()) {
                sum += r.getTotalPrice();
            }
        }
        return sum;
    }

    /**
     * Retrieves a list of customers that have overdue rentals.
     * A rental is overdue when it is still active and the due date is before today.
     *
     * @return a list of distinct customers with at least one overdue rental.
     *         Returns an empty list if there are no overdue customers.
     */
    public List<Customer> getOverdueCustomers() {
        LocalDate today = LocalDate.now();
        Set<Customer> overdueSet = new HashSet<>();

        for (Rental r : rentals) {
            if (r.isOverdue(today)) {
                overdueSet.add(r.getCustomer());
            }
        }

        return new ArrayList<>(overdueSet);
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the arithmetic mean of daily prices; 0.0 when the store has no cars.
     */
    public double getAverageDailyPrice() {
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
     * Counts how many rentals each customer has performed.
     *
     * @return a map where the key is a {@link Customer} and the value is the number of rentals
     *         associated with that customer. Returns an empty map when no rentals exist.
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental r : rentals) {
            Customer cust = r.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }

    // -------------------------------------------------------------------------
    // Additional helper methods (not required by the specification but useful)
    // -------------------------------------------------------------------------

    /**
     * Registers a new car in the store.
     *
     * @param car the car to add
     */
    public void addCar(Car car) {
        if (car != null && !cars.contains(car)) {
            cars.add(car);
        }
    }

    /**
     * Registers a new customer in the store.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
        }
    }

    /**
     * Records a new rental transaction.
     *
     * @param rental the rental to record
     * @throws IllegalArgumentException if the car is already rented
     */
    public void addRental(Rental rental) {
        if (rental == null) {
            throw new IllegalArgumentException("Rental cannot be null.");
        }
        // Ensure the car is not already rented
        for (Rental r : rentals) {
            if (r.isActive() && r.getCar().equals(rental.getCar())) {
                throw new IllegalArgumentException(
                        "Car with plate " + rental.getCar().getPlate() + " is already rented.");
            }
        }
        rentals.add(rental);
    }

    /**
     * Marks a rental as returned (sets the back date) and updates the total price.
     *
     * @param carPlate   the plate of the car being returned
     * @param returnDate the date on which the car is returned
     * @throws IllegalArgumentException if no active rental is found for the given plate
     */
    public void returnCar(String carPlate, LocalDate returnDate) {
        for (Rental r : rentals) {
            if (r.isActive() && r.getCar().getPlate().equals(carPlate)) {
                r.setBackDate(returnDate);
                long days = java.time.temporal.ChronoUnit.DAYS.between(r.getRentDate(), returnDate);
                days = Math.max(days, 1); // at least one day charge
                double price = days * r.getCar().getDailyPrice();
                r.setTotalPrice(price);
                return;
            }
        }
        throw new IllegalArgumentException("No active rental found for car plate: " + carPlate);
    }
}