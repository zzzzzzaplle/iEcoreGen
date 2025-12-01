import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    /** No‑argument constructor required by the specification. */
    public Car() {
    }

    /** Full constructor for convenience (not required by the specification). */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
    }

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

    /** No‑argument constructor required by the specification. */
    public Customer() {
    }

    /** Full constructor for convenience (not required by the specification). */
    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

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

    /** Two customers are considered equal when they share the same name, surname and address. */
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
 * Represents a rental transaction between a customer and a car.
 */
 class Rental {

    private Car car;
    private Customer customer;
    private LocalDate rentDate;   // date when the car was taken
    private LocalDate dueDate;    // expected return date
    private LocalDate backDate;   // actual return date (null if not yet returned)

    /** No‑argument constructor required by the specification. */
    public Rental() {
    }

    /** Full constructor for convenience (not required by the specification). */
    public Rental(Car car, Customer customer, LocalDate rentDate, LocalDate dueDate) {
        this.car = car;
        this.customer = customer;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
        this.backDate = null;
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

    /**
     * Calculates the revenue generated by this rental.
     * <p>
     * If the car has been returned (backDate != null) the number of days is the
     * inclusive period between rentDate and backDate.
     * Otherwise the revenue is calculated using the dueDate as the end date.
     *
     * @return revenue amount (daily price multiplied by number of days)
     */
    public double calculateRevenue() {
        LocalDate end = (backDate != null) ? backDate : dueDate;
        long days = ChronoUnit.DAYS.between(rentDate, end) + 1; // inclusive
        if (days < 0) {
            days = 0;
        }
        return days * car.getDailyPrice();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        return "Rental{" +
                "car=" + car +
                ", customer=" + customer +
                ", rentDate=" + (rentDate != null ? rentDate.format(fmt) : "null") +
                ", dueDate=" + (dueDate != null ? dueDate.format(fmt) : "null") +
                ", backDate=" + (backDate != null ? backDate.format(fmt) : "null") +
                '}';
    }
}

/**
 * Core class used by the store assistant to manage cars, customers and rentals.
 */
 class CarRentalStore {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /** No‑argument constructor required by the specification. */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    // -------------------------------------------------------------------------
    // Accessors / Mutators for the internal collections (needed for tests)
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
    // Functional requirement implementations
    // -------------------------------------------------------------------------

    /**
     * Returns a list of cars that are currently not rented.
     * The result is sorted by the daily price in ascending order.
     * If no cars are available, an empty list is returned.
     *
     * @return list of available cars sorted by price
     */
    public List<Car> getAvailableCarsSortedByPrice() {
        List<Car> available = new ArrayList<>();

        for (Car car : cars) {
            boolean rented = rentals.stream()
                    .anyMatch(r -> r.getCar().equals(car) && r.getBackDate() == null);
            if (!rented) {
                available.add(car);
            }
        }

        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all rentals stored in the system.
     *
     * @return total revenue as a double value
     */
    public double calculateTotalRevenue() {
        double total = 0.0;
        for (Rental rental : rentals) {
            total += rental.calculateRevenue();
        }
        return total;
    }

    /**
     * Returns a list of customers that have overdue rentals.
     * A rental is overdue when its backDate is null and the current date
     * is later than the due date.
     *
     * @return list of customers with overdue rentals; empty list if none
     */
    public List<Customer> getCustomersWithOverdueRentals() {
        List<Customer> overdueCustomers = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && today.isAfter(rental.getDueDate())) {
                Customer cust = rental.getCustomer();
                if (!overdueCustomers.contains(cust)) {
                    overdueCustomers.add(cust);
                }
            }
        }
        return overdueCustomers;
    }

    /**
     * Calculates the average daily price of all cars stored in the gallery.
     * If the store contains no cars, the method returns {@code 0.0}.
     *
     * @return average daily price, or 0.0 when there are no cars
     */
    public double getAverageDailyCarPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Car car : cars) {
            sum += car.getDailyPrice();
        }
        return sum / cars.size();
    }

    /**
     * Returns a map where each key is a {@link Customer} and the value is the
     * number of cars that the customer has rented (including ongoing rentals).
     *
     * @return map of customers to rental counts; empty map if there are no rentals
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();

        for (Rental rental : rentals) {
            Customer cust = rental.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }

    // -------------------------------------------------------------------------
    // Helper methods used by the store assistant (not part of the functional list)
    // -------------------------------------------------------------------------

    /**
     * Registers a new car in the store.
     *
     * @param car car to be added; must not be {@code null}
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    /**
     * Registers a new customer in the store.
     *
     * @param customer customer to be added; must not be {@code null}
     */
    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
        }
    }

    /**
     * Records a new rental transaction.
     *
     * @param rental rental to be added; must not be {@code null}
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentals.add(rental);
        }
    }

    /**
     * Marks a rental as returned by setting its back date.
     *
     * @param rental   the rental that is being returned; must be present in the store
     * @param backDate the actual date the car was returned; must not be {@code null}
     * @throws IllegalArgumentException if the rental is not found in the store
     */
    public void returnRental(Rental rental, LocalDate backDate) {
        if (!rentals.contains(rental)) {
            throw new IllegalArgumentException("Rental not found in the store.");
        }
        rental.setBackDate(backDate);
    }
}