import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a car in the rental store.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean rented;

    /**
     * Default constructor for Car.
     */
    public Car() {
        this.plate = "";
        this.model = "";
        this.dailyPrice = 0.0;
        this.rented = false;
    }

    // Getters and Setters
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

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}

/**
 * Represents a customer in the rental store.
 */
class Customer {
    private String name;
    private String surname;
    private String address;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.name = "";
        this.surname = "";
        this.address = "";
    }

    // Getters and Setters
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
}

/**
 * Represents a rental record in the rental store.
 */
class Rental {
    private Customer customer;
    private Car car;
    private LocalDate rentDate;
    private LocalDate dueDate;
    private LocalDate backDate;

    /**
     * Default constructor for Rental.
     */
    public Rental() {
        this.customer = new Customer();
        this.car = new Car();
        this.rentDate = LocalDate.now();
        this.dueDate = LocalDate.now();
        this.backDate = null;
    }

    // Getters and Setters
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
     * Checks if the rental is overdue.
     * A rental is overdue if the back date is null and the current date is past the due date.
     *
     * @return true if the rental is overdue, false otherwise
     */
    public boolean isOverdue() {
        return backDate == null && LocalDate.now().isAfter(dueDate);
    }
}

/**
 * Represents the rental store system.
 */
class RentalStore {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /**
     * Default constructor for RentalStore.
     */
    public RentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    // Getters and Setters
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
     * Identifies available cars in the store.
     * A car is available if it is not currently rented.
     * Returns a list of available cars, sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     *
     * @return a list of available cars sorted by daily price
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * Revenue is calculated as the sum of (dailyPrice * rental days) for all completed rentals.
     *
     * @return the total revenue generated
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .filter(rental -> rental.getBackDate() != null)
                .mapToDouble(rental -> {
                    long days = java.time.temporal.ChronoUnit.DAYS.between(
                            rental.getRentDate(), rental.getBackDate());
                    return rental.getCar().getDailyPrice() * days;
                })
                .sum();
    }

    /**
     * Lists customers with overdue rentals.
     * For each rental, if the back date is null and the current date is past the due date,
     * mark it as overdue.
     * Returns an empty list if no overdue customers exist.
     *
     * @return a list of customers with overdue rentals
     */
    public List<Customer> getOverdueCustomers() {
        Set<Customer> overdueCustomers = new HashSet<>();
        for (Rental rental : rentals) {
            if (rental.isOverdue()) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return new ArrayList<>(overdueCustomers);
    }

    /**
     * Determines the average daily price of cars in the store.
     * Calculated as the sum of daily prices of all cars divided by the total number of cars.
     * If the store has no cars, returns 0.0.
     *
     * @return the average daily price of cars
     */
    public double getAverageCarPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double total = cars.stream()
                .mapToDouble(Car::getDailyPrice)
                .sum();
        return total / cars.size();
    }

    /**
     * Counts the number of cars rented per customer.
     * Returns a map of customers and their respective rental counts.
     * Returns an empty map if no customers have rented cars.
     *
     * @return a map of customers and their rental counts
     */
    public Map<Customer, Integer> getRentalsPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        return rentalCounts;
    }

    /**
     * Rents a car to a customer.
     * Updates the car's rented status and adds a new rental record.
     *
     * @param customer the customer renting the car
     * @param car the car being rented
     * @param rentDate the date the car is rented
     * @param dueDate the date the car is due back
     */
    public void rentCar(Customer customer, Car car, LocalDate rentDate, LocalDate dueDate) {
        if (!car.isRented()) {
            car.setRented(true);
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setCar(car);
            rental.setRentDate(rentDate);
            rental.setDueDate(dueDate);
            rentals.add(rental);
        }
    }

    /**
     * Returns a rented car.
     * Updates the car's rented status and sets the back date for the rental.
     *
     * @param car the car being returned
     * @param backDate the date the car is returned
     */
    public void returnCar(Car car, LocalDate backDate) {
        car.setRented(false);
        for (Rental rental : rentals) {
            if (rental.getCar().equals(car) && rental.getBackDate() == null) {
                rental.setBackDate(backDate);
                break;
            }
        }
    }
}