import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a car in the car rental system
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    
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
     * Gets the car model
     * @return the car model
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Sets the car model
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
}

/**
 * Represents a customer in the car rental system
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate;
    
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
     * Gets the customer's surname
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Sets the customer's surname
     * @param surname the surname to set
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
     * Gets the license plate of the currently rented car
     * @return the rented car's license plate
     */
    public String getRentedCarPlate() {
        return rentedCarPlate;
    }
    
    /**
     * Sets the license plate of the currently rented car
     * @param rentedCarPlate the rented car's license plate to set
     */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }
}

/**
 * Represents a rental transaction in the car rental system
 */
class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate;
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;
    
    /**
     * Default constructor
     */
    public Rental() {
    }
    
    /**
     * Gets the rental start date
     * @return the rental date
     */
    public Date getRentalDate() {
        return rentalDate;
    }
    
    /**
     * Sets the rental start date
     * @param rentalDate the rental date to set
     */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    /**
     * Gets the rental due date
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }
    
    /**
     * Sets the rental due date
     * @param dueDate the due date to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    /**
     * Gets the car return date
     * @return the back date
     */
    public Date getBackDate() {
        return backDate;
    }
    
    /**
     * Sets the car return date
     * @param backDate the back date to set
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }
    
    /**
     * Gets the total rental price
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    
    /**
     * Sets the total rental price
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * Gets the leasing terms
     * @return the leasing terms
     */
    public String getLeasingTerms() {
        return leasingTerms;
    }
    
    /**
     * Sets the leasing terms
     * @param leasingTerms the leasing terms to set
     */
    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
    }
    
    /**
     * Gets the rented car
     * @return the car object
     */
    public Car getCar() {
        return car;
    }
    
    /**
     * Sets the rented car
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }
    
    /**
     * Gets the customer who rented the car
     * @return the customer object
     */
    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Sets the customer who rented the car
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Represents an overdue notice sent to customers with late returns
 */
class OverdueNotice {
    private Customer customer;
    
    /**
     * Default constructor
     */
    public OverdueNotice() {
    }
    
    /**
     * Gets the customer associated with this notice
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }
    
    /**
     * Sets the customer associated with this notice
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    /**
     * Sends an overdue notice to the specified customer
     * @param customer the customer to send the notice to
     */
    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Implementation for sending notice would go here
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }
}

/**
 * Main store class managing cars, rentals, and overdue notices
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;
    
    /**
     * Default constructor initializing empty lists
     */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }
    
    /**
     * Gets the list of cars in the store
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }
    
    /**
     * Sets the list of cars in the store
     * @param cars the list of cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    
    /**
     * Gets the list of rental transactions
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }
    
    /**
     * Sets the list of rental transactions
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
    
    /**
     * Gets the list of overdue notices
     * @return the list of notices
     */
    public List<OverdueNotice> getNotices() {
        return notices;
    }
    
    /**
     * Sets the list of overdue notices
     * @param notices the list of notices to set
     */
    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }
    
    /**
     * Identifies available cars in the store. A car is available if it is not currently rented.
     * Returns a list of available cars sorted by daily price in ascending order.
     * @return a list of available cars sorted by price, or empty list if no available cars exist
     */
    public List<Car> identifyAvailableCars() {
        // Get all cars that are not currently rented
        Set<String> rentedCarPlates = rentals.stream()
            .filter(rental -> rental.getBackDate() == null)
            .map(rental -> rental.getCar().getPlate())
            .collect(Collectors.toSet());
            
        List<Car> availableCars = cars.stream()
            .filter(car -> !rentedCarPlates.contains(car.getPlate()))
            .sorted(Comparator.comparingDouble(Car::getDailyPrice))
            .collect(Collectors.toList());
            
        return availableCars;
    }
    
    /**
     * Calculates the total revenue generated by all rentals in the store
     * @return the total revenue from all completed rentals
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
            .filter(rental -> rental.getBackDate() != null) // Only completed rentals
            .mapToDouble(Rental::getTotalPrice)
            .sum();
    }
    
    /**
     * Lists customers with overdue rentals. A rental is overdue if the back date is null 
     * and the current date is past the due date.
     * @param currentDate the current date to check against due dates
     * @return a list of customers with overdue rentals, or empty list if none exist
     * @throws IllegalArgumentException if currentDate is null
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date cannot be null");
        }
        
        return rentals.stream()
            .filter(rental -> rental.getBackDate() == null && 
                             currentDate.after(rental.getDueDate()))
            .map(Rental::getCustomer)
            .distinct()
            .collect(Collectors.toList());
    }
    
    /**
     * Determines the average daily price of cars in the store
     * @return the average daily price, or 0.0 if the store has no cars
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        
        double totalPrice = cars.stream()
            .mapToDouble(Car::getDailyPrice)
            .sum();
            
        return totalPrice / cars.size();
    }
    
    /**
     * Counts the number of cars rented per customer
     * @return a map of customers and their respective rental counts, or empty map if no customers have rented cars
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        return rentals.stream()
            .collect(Collectors.groupingBy(
                Rental::getCustomer,
                Collectors.collectingAndThen(
                    Collectors.counting(),
                    Long::intValue
                )
            ));
    }
    
    /**
     * Adds a new car to the store
     * @param car the car to add
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }
    
    /**
     * Adds a new rental transaction
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentals.add(rental);
        }
    }
    
    /**
     * Adds a new overdue notice
     * @param notice the notice to add
     */
    public void addNotice(OverdueNotice notice) {
        if (notice != null) {
            notices.add(notice);
        }
    }
}