import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a car in the rental system
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
     * Gets the license plate of the car
     * @return the plate number
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the license plate of the car
     * @param plate the plate number to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the model of the car
     * @return the car model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car
     * @param model the car model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily rental price of the car
     * @return the daily price
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily rental price of the car
     * @param dailyPrice the daily price to set
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}

/**
 * Represents a customer in the rental system
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
     * Gets the plate number of the car currently rented by the customer
     * @return the rented car plate number
     */
    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    /**
     * Sets the plate number of the car currently rented by the customer
     * @param rentedCarPlate the rented car plate number to set
     */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }
}

/**
 * Represents a rental transaction in the system
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
     * Gets the due date for returning the car
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the car
     * @param dueDate the due date to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the actual return date of the car
     * @return the back date, or null if not yet returned
     */
    public Date getBackDate() {
        return backDate;
    }

    /**
     * Sets the actual return date of the car
     * @param backDate the back date to set
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /**
     * Gets the total price of the rental
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the rental
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the leasing terms of the rental
     * @return the leasing terms
     */
    public String getLeasingTerms() {
        return leasingTerms;
    }

    /**
     * Sets the leasing terms of the rental
     * @param leasingTerms the leasing terms to set
     */
    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
    }

    /**
     * Gets the car associated with this rental
     * @return the rented car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car associated with this rental
     * @param car the rented car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Gets the customer associated with this rental
     * @return the customer who rented the car
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this rental
     * @param customer the customer who rented the car to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Checks if the rental is currently active (car not returned)
     * @return true if the car has not been returned, false otherwise
     */
    public boolean isActive() {
        return backDate == null;
    }

    /**
     * Checks if the rental is overdue based on the current date
     * @param currentDate the current date to check against
     * @return true if the rental is overdue, false otherwise
     */
    public boolean isOverdue(Date currentDate) {
        return isActive() && currentDate.after(dueDate);
    }
}

/**
 * Represents an overdue notice sent to customers
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
     * Sends an overdue notice to the customer
     * @param customer the customer to send the notice to
     */
    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Implementation for sending notice would go here
        // This could involve email, SMS, or physical mail
    }
}

/**
 * Main store class that manages cars, rentals, and overdue notices
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /**
     * Default constructor
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
     * Gets the list of rentals in the store
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals in the store
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the list of overdue notices in the store
     * @return the list of overdue notices
     */
    public List<OverdueNotice> getNotices() {
        return notices;
    }

    /**
     * Sets the list of overdue notices in the store
     * @param notices the list of overdue notices to set
     */
    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }

    /**
     * Identifies available cars in the store that are not currently rented.
     * Cars are sorted by daily price in ascending order.
     * @return a list of available cars sorted by price, or empty list if none available
     */
    public List<Car> identifyAvailableCars() {
        Set<Car> rentedCars = rentals.stream()
                .filter(Rental::isActive)
                .map(Rental::getCar)
                .collect(Collectors.toSet());
        
        return cars.stream()
                .filter(car -> !rentedCars.contains(car))
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all completed rentals in the store.
     * @return the total revenue amount
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .filter(rental -> rental.getBackDate() != null)
                .mapToDouble(Rental::getTotalPrice)
                .sum();
    }

    /**
     * Finds customers with overdue rentals based on the current date.
     * A rental is overdue if the back date is null and current date is past the due date.
     * @param currentDate the current date to check against rental due dates
     * @return a list of customers with overdue rentals, or empty list if none exist
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        return rentals.stream()
                .filter(rental -> rental.isOverdue(currentDate))
                .map(Rental::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of all cars in the store.
     * @return the average daily price, or 0.0 if no cars exist in the store
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
     * Counts the number of cars rented per customer across all rentals.
     * @return a map where keys are customers and values are their rental counts,
     *         or empty map if no customers have rented cars
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
     * Adds a new car to the store's inventory
     * @param car the car to add
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Adds a new rental to the store's records
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    /**
     * Adds a new overdue notice to the store's records
     * @param notice the overdue notice to add
     */
    public void addOverdueNotice(OverdueNotice notice) {
        notices.add(notice);
    }
}