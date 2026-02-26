import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a car in the car rental system.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean isRented;

    /**
     * Default constructor for Car.
     */
    public Car() {
    }

    /**
     * Gets the license plate of the car.
     * @return the license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the license plate of the car.
     * @param plate the license plate to set
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
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily rental price of the car.
     * @return the daily price
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily rental price of the car.
     * @param dailyPrice the daily price to set
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /**
     * Checks if the car is currently rented.
     * @return true if the car is rented, false otherwise
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rented status of the car.
     * @param rented the rented status to set
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a customer in the car rental system.
 */
class Customer {
    private String name;
    private String surname;
    private String address;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
    }

    /**
     * Gets the customer's first name.
     * @return the first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's first name.
     * @param name the first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Compares this customer to the specified object for equality.
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return java.util.Objects.equals(name, customer.name) &&
                java.util.Objects.equals(surname, customer.surname) &&
                java.util.Objects.equals(address, customer.address);
    }

    /**
     * Returns a hash code value for the customer.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, surname, address);
    }
}

/**
 * Represents a rental record in the car rental system.
 */
class Rental {
    private Customer customer;
    private Car car;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate backDate;
    private double totalPrice;

    /**
     * Default constructor for Rental.
     */
    public Rental() {
    }

    /**
     * Gets the customer associated with this rental.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this rental.
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the car associated with this rental.
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car associated with this rental.
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Gets the start date of the rental.
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the rental.
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the due date for the rental return.
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for the rental return.
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the actual return date of the rental.
     * @return the back date, or null if not yet returned
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * Sets the actual return date of the rental.
     * @param backDate the back date to set
     */
    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }

    /**
     * Gets the total price of the rental.
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the rental.
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

/**
 * Manages the car rental system including cars, customers, and rentals.
 */
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /**
     * Default constructor for CarRentalSystem.
     * Initializes empty lists for cars, customers, and rentals.
     */
    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the list of cars in the system.
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars in the system.
     * @param cars the list of cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of customers in the system.
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers in the system.
     * @param customers the list of customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the list of rentals in the system.
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals in the system.
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Identifies available cars in the store.
     * A car is available if it is not currently rented.
     * Returns a list of available cars, sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     * @return a sorted list of available cars, or an empty list if none are available
     */
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        availableCars.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return the total revenue from all rentals
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (Rental rental : rentals) {
            totalRevenue += rental.getTotalPrice();
        }
        return totalRevenue;
    }

    /**
     * Lists customers with overdue rentals.
     * For each rental, if the back date is null and the current date is past the due date,
     * marks it as overdue. Returns an empty list if no overdue customers exist.
     * @return a list of customers with overdue rentals, or an empty list if none exist
     */
    public List<Customer> getCustomersWithOverdueRentals() {
        List<Customer> overdueCustomers = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && currentDate.isAfter(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store.
     * Sums the daily prices of all cars and divides by the total number of cars.
     * If the store has no cars, returns 0.0.
     * @return the average daily price of cars, or 0.0 if there are no cars
     */
    public double getAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double totalPrice = 0.0;
        for (Car car : cars) {
            totalPrice += car.getDailyPrice();
        }
        return totalPrice / cars.size();
    }

    /**
     * Counts the number of cars rented per customer.
     * Returns a map of customers and their respective rental counts.
     * Returns an empty map if no customers have rented cars.
     * @return a map of customers to their rental counts, or an empty map if no rentals exist
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        return rentalCounts;
    }
}