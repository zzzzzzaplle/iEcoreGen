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
 * Represents a car that can be rented from the gallery.
 */
 class Car {

    private String plate;
    private String model;
    private double dailyPrice;

    /** Unparameterized constructor required for tests. */
    public Car() {
    }

    /** Parameterized constructor for convenience. */
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
 * Holds information about a customer who rents cars.
 */
 class Customer {

    private String name;
    private String surname;
    private String address;

    /** Unparameterized constructor required for tests. */
    public Customer() {
    }

    /** Parameterized constructor for convenience. */
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

    /** Customers are considered equal when all personal data match. */
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
 * Represents a rental transaction between a customer and the store.
 */
 class Rental {

    private Customer customer;
    private Car car;
    private LocalDate rentDate;   // day the car was taken
    private LocalDate dueDate;    // expected return date
    private LocalDate backDate;   // actual return date; may be null if still rented

    /** Unparameterized constructor required for tests. */
    public Rental() {
    }

    /** Parameterized constructor for convenience. */
    public Rental(Customer customer, Car car, LocalDate rentDate, LocalDate dueDate) {
        this.customer = customer;
        this.car = car;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
        this.backDate = null;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
     * Calculates the revenue produced by this rental.
     * If the car has not yet been returned, the revenue is based on the due date.
     *
     * @return total amount earned for this rental
     */
    public double calculateRevenue() {
        LocalDate end = (backDate != null) ? backDate : dueDate;
        long days = java.time.temporal.ChronoUnit.DAYS.between(rentDate, end);
        days = Math.max(days, 1); // at least one day is charged
        return days * car.getDailyPrice();
    }

    @Override
    public String toString() {
        return "Rental{" +
                "customer=" + customer +
                ", car=" + car +
                ", rentDate=" + rentDate +
                ", dueDate=" + dueDate +
                ", backDate=" + backDate +
                '}';
    }
}

/**
 * Central class used by store assistants to manage cars, customers and rentals.
 */
 class CarRentalStore {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /** Unparameterized constructor required for tests. */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

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

    /**
     * Adds a new car to the gallery.
     *
     * @param car the car to be added
     */
    public void addCar(Car car) {
        this.cars.add(car);
    }

    /**
     * Registers a new customer.
     *
     * @param customer the customer to be added
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Records a new rental. The method also marks the car as rented by creating a Rental object.
     *
     * @param rental the rental transaction to be stored
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }

    /**
     * Identifies all cars that are currently not rented.
     * A car is considered available if there is no active rental (i.e., a rental whose
     * backDate is {@code null}) associated with its plate.
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         Returns an empty list if no cars are available.
     */
    public List<Car> getAvailableCars() {
        // Determine plates of cars that are currently rented
        Set<String> rentedPlates = new HashSet<>();
        for (Rental r : rentals) {
            if (r.getBackDate() == null) {
                rentedPlates.add(r.getCar().getPlate());
            }
        }

        // Collect cars whose plates are not in the rented set
        List<Car> available = new ArrayList<>();
        for (Car c : cars) {
            if (!rentedPlates.contains(c.getPlate())) {
                available.add(c);
            }
        }

        // Sort by daily price (ascending)
        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all rentals stored in the system.
     *
     * @return sum of revenues of each rental. If there are no rentals, returns 0.0.
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
     * A rental is overdue when its {@code backDate} is {@code null} and the current date
     * is after the rental's {@code dueDate}.
     *
     * @return a list of distinct customers with overdue rentals.
     *         Returns an empty list if no overdue customers exist.
     */
    public List<Customer> getOverdueCustomers() {
        LocalDate today = LocalDate.now();
        Set<Customer> overdueSet = new HashSet<>();

        for (Rental r : rentals) {
            if (r.getBackDate() == null && r.getDueDate().isBefore(today)) {
                overdueSet.add(r.getCustomer());
            }
        }

        return new ArrayList<>(overdueSet);
    }

    /**
     * Computes the average daily price of all cars stored in the gallery.
     *
     * @return the average price; returns 0.0 if there are no cars.
     */
    public double getAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Car c : cars) {
            sum += c.getDailyPrice();
        }
        return sum / cars.size();
    }

    /**
     * Counts how many cars each customer has rented.
     *
     * @return a map where the key is a {@link Customer} and the value is the number of
     *         rentals that customer has performed. Returns an empty map if no rentals exist.
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental r : rentals) {
            Customer c = r.getCustomer();
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        return countMap;
    }
}