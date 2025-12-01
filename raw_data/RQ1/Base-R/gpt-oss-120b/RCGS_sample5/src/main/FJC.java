import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    // Equality based on the unique plate number
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
 * Represents a customer who rents cars.
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
    // Equality based on name, surname and address (acts as a natural key)
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
 * Represents a rental transaction linking a customer and a car.
 */
 class Rental {

    private Car car;
    private Customer customer;
    private LocalDate leaseStartDate; // the day the car was taken
    private LocalDate dueDate;        // expected return date
    private LocalDate backDate;       // actual return date; null if not yet returned

    /** Unparameterized constructor */
    public Rental() {
    }

    /** Parameterized constructor for convenience */
    public Rental(Car car, Customer customer, LocalDate leaseStartDate,
                  LocalDate dueDate, LocalDate backDate) {
        this.car = car;
        this.customer = customer;
        this.leaseStartDate = leaseStartDate;
        this.dueDate = dueDate;
        this.backDate = backDate;
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

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
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

    /**
     * Calculates the revenue generated by this rental.
     * <p>
     * If the car has already been returned, the number of days is the
     * interval between {@code leaseStartDate} (inclusive) and {@code backDate}
     * (exclusive). If the car has not yet been returned, the interval is
     * calculated up to the current date.
     *
     * @return revenue for this rental (daily price × days rented)
     */
    public double calculateRevenue() {
        LocalDate end = (backDate != null) ? backDate : LocalDate.now();
        long days = java.time.temporal.ChronoUnit.DAYS.between(leaseStartDate, end);
        // At least one day is charged
        days = Math.max(days, 1);
        return days * car.getDailyPrice();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        return "Rental{" +
                "car=" + car +
                ", customer=" + customer +
                ", leaseStartDate=" + (leaseStartDate != null ? leaseStartDate.format(fmt) : "null") +
                ", dueDate=" + (dueDate != null ? dueDate.format(fmt) : "null") +
                ", backDate=" + (backDate != null ? backDate.format(fmt) : "null") +
                '}';
    }
}

/**
 * Central class that stores cars, customers and rentals and provides
 * business‑logic operations required by the functional specifications.
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
    // Collection accessors (useful for tests)
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
    // Helper methods to add entities
    // -------------------------------------------------------------------------

    /**
     * Adds a new car to the store.
     *
     * @param car car to be added; must not be {@code null}
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    /**
     * Adds a new customer to the store.
     *
     * @param customer customer to be added; must not be {@code null}
     */
    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
        }
    }

    /**
     * Registers a new rental.
     *
     * @param rental rental to be added; must not be {@code null}
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentals.add(rental);
        }
    }

    // -------------------------------------------------------------------------
    // Functional Requirement Implementations
    // -------------------------------------------------------------------------

    /**
     * Identifies all cars that are currently not rented.
     * <p>
     * A car is considered unavailable if there exists a {@link Rental} for that
     * car whose {@code backDate} is {@code null} (i.e., the car has not been
     * returned yet). The method returns the available cars sorted by their
     * daily price in ascending order.
     *
     * @return list of available cars sorted by daily price; empty list if none
     */
    public List<Car> getAvailableCars() {
        // Collect plates of cars that are currently rented
        List<String> rentedPlates = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getBackDate() == null) {
                rentedPlates.add(r.getCar().getPlate());
            }
        }

        // Filter cars that are not in the rented set
        List<Car> available = new ArrayList<>();
        for (Car c : cars) {
            if (!rentedPlates.contains(c.getPlate())) {
                available.add(c);
            }
        }

        // Sort by daily price ascending
        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates total revenue generated by all rentals in the store.
     *
     * @return sum of revenues of all rentals; {@code 0.0} if there are no rentals
     */
    public double calculateTotalRevenue() {
        double total = 0.0;
        for (Rental r : rentals) {
            total += r.calculateRevenue();
        }
        return total;
    }

    /**
     * Lists all customers that have overdue rentals.
     * <p>
     * A rental is overdue when its {@code backDate} is {@code null} and the
     * current date is after its {@code dueDate}.
     *
     * @return list of customers with at least one overdue rental; empty list if none
     */
    public List<Customer> getOverdueCustomers() {
        LocalDate today = LocalDate.now();
        List<Customer> overdue = new ArrayList<>();

        for (Rental r : rentals) {
            if (r.getBackDate() == null && today.isAfter(r.getDueDate())) {
                if (!overdue.contains(r.getCustomer())) {
                    overdue.add(r.getCustomer());
                }
            }
        }
        return overdue;
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return average daily price; {@code 0.0} if the store has no cars
     */
    public double getAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Car c : cars)            sum += c.getDailyPrice();
        }
        return sum / cars.size();
    }

    /**
     * Counts how many cars each customer has rented.
     *
     * @return a map where the key is a {@link Customer} and the value is the
     * number of rentals performed by that customer; empty map if no rentals exist
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental r : rentals) {
            Customer cust = r.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }
}