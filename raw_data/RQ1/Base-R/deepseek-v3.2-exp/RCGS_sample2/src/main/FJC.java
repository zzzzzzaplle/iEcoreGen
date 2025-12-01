import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a car in the car rental system
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean isRented;

    /**
     * Default constructor
     */
    public Car() {
    }

    /**
     * Gets the car's license plate
     * @return the license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the car's license plate
     * @param plate the license plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the car's model
     * @return the car model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the car's model
     * @param model the car model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily rental price
     * @return the daily price
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily rental price
     * @param dailyPrice the daily price to set
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /**
     * Checks if the car is currently rented
     * @return true if the car is rented, false otherwise
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rented status of the car
     * @param rented the rented status to set
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a customer in the car rental system
 */
class Customer {
    private String name;
    private String surname;
    private String address;

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Gets the customer's first name
     * @return the first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's first name
     * @param name the first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer's last name
     * @return the last name
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the customer's last name
     * @param surname the last name to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer's address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns a string representation of the customer
     * @return customer's full name
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }

    /**
     * Checks if this customer is equal to another object
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return name.equals(customer.name) && 
               surname.equals(customer.surname) && 
               address.equals(customer.address);
    }

    /**
     * Returns the hash code for this customer
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (name + surname + address).hashCode();
    }
}

/**
 * Represents a rental record in the car rental system
 */
class Rental {
    private Customer customer;
    private Car car;
    private LocalDate rentDate;
    private LocalDate dueDate;
    private LocalDate backDate;
    private double totalCost;

    /**
     * Default constructor
     */
    public Rental() {
    }

    /**
     * Gets the customer associated with this rental
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer for this rental
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the car associated with this rental
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car for this rental
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Gets the rent date
     * @return the rent date
     */
    public LocalDate getRentDate() {
        return rentDate;
    }

    /**
     * Sets the rent date
     * @param rentDate the rent date to set
     */
    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    /**
     * Gets the due date for returning the car
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the car
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the actual return date of the car
     * @return the back date, or null if not returned yet
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * Sets the actual return date of the car
     * @param backDate the back date to set
     */
    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }

    /**
     * Gets the total cost of this rental
     * @return the total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost of this rental
     * @param totalCost the total cost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Checks if this rental is overdue based on current date
     * @return true if overdue, false otherwise
     */
    public boolean isOverdue() {
        return backDate == null && LocalDate.now().isAfter(dueDate);
    }

    /**
     * Calculates the number of overdue days
     * @return number of overdue days, 0 if not overdue
     */
    public long getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
}

/**
 * Main car rental system that manages cars, customers, and rentals
 */
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /**
     * Default constructor
     */
    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the list of cars
     * @return list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars
     * @param cars the list of cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of customers
     * @return list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers
     * @param customers the list of customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the list of rentals
     * @return list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Identifies available cars in the store. A car is available if it is not currently rented. 
     * Returns a list of available cars, sorted by daily price in ascending order. 
     * If no available cars exist, returns an empty list.
     * @return list of available cars sorted by daily price, or empty list if none available
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return total revenue from all rentals
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .mapToDouble(Rental::getTotalCost)
                .sum();
    }

    /**
     * Lists customers with overdue rentals. For each rental, if the back date is null 
     * and the current date is past the due date, marks it as overdue. 
     * Returns an empty list if no overdue customers exist.
     * @return list of customers with overdue rentals, or empty list if none
     */
    public List<Customer> getOverdueCustomers() {
        return rentals.stream()
                .filter(Rental::isOverdue)
                .map(Rental::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of cars in the store: 
     * sums the daily prices of all cars and divides by the total number of cars. 
     * If the store has no cars, returns 0.0.
     * @return average daily price of cars, or 0.0 if no cars exist
     */
    public double calculateAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        return cars.stream()
                .mapToDouble(Car::getDailyPrice)
                .average()
                .orElse(0.0);
    }

    /**
     * Counts the number of cars rented per customer. 
     * Returns a map of customers and their respective rental counts, 
     * or an empty map if no customers have rented cars.
     * @return map of customers to their rental counts, or empty map if no rentals
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        
        return rentalCounts;
    }

    /**
     * Adds a new car to the system
     * @param car the car to add
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Adds a new customer to the system
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Adds a new rental to the system and marks the car as rented
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.getCar().setRented(true);
    }

    /**
     * Marks a rental as returned by setting the back date and marking the car as available
     * @param rental the rental to mark as returned
     * @param backDate the date the car was returned
     */
    public void returnCar(Rental rental, LocalDate backDate) {
        rental.setBackDate(backDate);
        rental.getCar().setRented(false);
    }

    /**
     * Gets all currently rented cars
     * @return list of currently rented cars
     */
    public List<Car> getRentedCars() {
        return cars.stream()
                .filter(Car::isRented)
                .collect(Collectors.toList());
    }

    /**
     * Gets rental history for a specific customer
     * @param customer the customer to get history for
     * @return list of rentals for the customer
     */
    public List<Rental> getCustomerRentalHistory(Customer customer) {
        return rentals.stream()
                .filter(rental -> rental.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }
}