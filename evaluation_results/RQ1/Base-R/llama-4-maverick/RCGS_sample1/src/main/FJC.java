import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a Car with its details.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean isRented;

    /**
     * Default constructor for Car.
     */
    public Car() {}

    /**
     * Constructor for Car with details.
     * @param plate the plate number of the car
     * @param model the model of the car
     * @param dailyPrice the daily price of the car
     */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isRented = false;
    }

    /**
     * Gets the plate number of the car.
     * @return the plate number
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the plate number of the car.
     * @param plate the new plate number
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the model of the car.
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     * @param model the new model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily price of the car.
     * @return the daily price
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily price of the car.
     * @param dailyPrice the new daily price
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /**
     * Checks if the car is rented.
     * @return true if rented, false otherwise
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rental status of the car.
     * @param rented true if rented, false otherwise
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a Customer with their details and rental information.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private List<Rental> rentals;

    /**
     * Default constructor for Customer.
     */
    public Customer() {}

    /**
     * Constructor for Customer with details.
     * @param name the name of the customer
     * @param surname the surname of the customer
     * @param address the address of the customer
     */
    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the name of the customer.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the customer.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the customer.
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the address of the customer.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of rentals for the customer.
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals for the customer.
     * @param rentals the new list of rentals
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a rental to the customer's list of rentals.
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        this.rentals.add(rental);
    }
}

/**
 * Represents a Rental with its details.
 */
class Rental {
    private Car car;
    private LocalDate dueDate;
    private LocalDate backDate;

    /**
     * Default constructor for Rental.
     */
    public Rental() {}

    /**
     * Constructor for Rental with details.
     * @param car the car being rented
     * @param dueDate the due date for returning the car
     */
    public Rental(Car car, LocalDate dueDate) {
        this.car = car;
        this.dueDate = dueDate;
        this.backDate = null;
    }

    /**
     * Gets the car being rented.
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car being rented.
     * @param car the new car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Gets the due date for returning the car.
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the car.
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the date the car was returned.
     * @return the return date, or null if not returned
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * Sets the date the car was returned.
     * @param backDate the return date
     */
    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }
}

/**
 * Manages the car rental store's data and operations.
 */
class CarRentalStore {
    private List<Car> cars;
    private List<Customer> customers;

    /**
     * Default constructor for CarRentalStore.
     */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    /**
     * Gets the list of cars in the store.
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars in the store.
     * @param cars the new list of cars
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of customers in the store.
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers in the store.
     * @param customers the new list of customers
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Identifies available cars in the store, sorted by daily price in ascending order.
     * @return a list of available cars
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        return customers.stream()
                .flatMap(customer -> customer.getRentals().stream())
                .mapToDouble(rental -> rental.getCar().getDailyPrice() * 
                        (rental.getBackDate() != null ? 
                                java.time.temporal.ChronoUnit.DAYS.between(rental.getDueDate(), rental.getBackDate()) : 
                                java.time.temporal.ChronoUnit.DAYS.between(rental.getDueDate(), LocalDate.now())))
                .sum();
    }

    /**
     * Lists customers with overdue rentals.
     * @return a list of customers with overdue rentals
     */
    public List<Customer> getCustomersWithOverdueRentals() {
        return customers.stream()
                .filter(customer -> customer.getRentals().stream().anyMatch(rental -> 
                        rental.getBackDate() == null && rental.getDueDate().isBefore(LocalDate.now())))
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of cars in the store.
     * @return the average daily price
     */
    public double calculateAverageDailyPrice() {
        return cars.isEmpty() ? 0.0 : cars.stream().mapToDouble(Car::getDailyPrice).average().orElse(0.0);
    }

    /**
     * Counts the number of cars rented per customer.
     * @return a map of customers and their respective rental counts
     */
    public Map<Customer, Long> countRentalsPerCustomer() {
        Map<Customer, Long> rentalCounts = new HashMap<>();
        for (Customer customer : customers) {
            long count = customer.getRentals().stream().filter(rental -> rental.getBackDate() == null).count();
            if (count > 0) {
                rentalCounts.put(customer, count);
            }
        }
        return rentalCounts;
    }
}