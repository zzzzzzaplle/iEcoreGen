import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
     * Gets the license plate of the car
     * @return the license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the license plate of the car
     * @param plate the license plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the model of the car
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car
     * @param model the model to set
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
     * Gets the customer's name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name
     * @param name the name to set
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
     * Overrides equals method to compare customers by name, surname, and address
     * @param obj the object to compare
     * @return true if customers are equal, false otherwise
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
     * Overrides hashCode method for proper functioning in collections
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
    private String startDate;
    private String dueDate;
    private String backDate;
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
     * Gets the start date of the rental (format: yyyy-MM-dd)
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the rental (format: yyyy-MM-dd)
     * @param startDate the start date to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the due date of the rental (format: yyyy-MM-dd)
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the rental (format: yyyy-MM-dd)
     * @param dueDate the due date to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the back date when the car was returned (format: yyyy-MM-dd)
     * @return the back date, or null if not returned yet
     */
    public String getBackDate() {
        return backDate;
    }

    /**
     * Sets the back date when the car was returned (format: yyyy-MM-dd)
     * @param backDate the back date to set
     */
    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    /**
     * Gets the total cost of the rental
     * @return the total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost of the rental
     * @param totalCost the total cost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Checks if the rental is currently active (car not returned yet)
     * @return true if the rental is active, false otherwise
     */
    public boolean isActive() {
        return backDate == null || backDate.isEmpty();
    }

    /**
     * Checks if the rental is overdue based on current date
     * @param currentDate the current date to check against (format: yyyy-MM-dd)
     * @return true if the rental is overdue, false otherwise
     * @throws ParseException if date format is invalid
     */
    public boolean isOverdue(String currentDate) throws ParseException {
        if (!isActive() || dueDate == null) {
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date current = sdf.parse(currentDate);
        Date due = sdf.parse(dueDate);
        
        return current.after(due);
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
     * Gets the list of all cars in the system
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars in the system
     * @param cars the list of cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of all customers in the system
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers in the system
     * @param customers the list of customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Gets the list of all rental records in the system
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rental records in the system
     * @param rentals the list of rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Identifies available cars in the store. A car is available if it is not currently rented.
     * Returns a list of available cars, sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     * @return a sorted list of available cars
     */
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        
        // Sort by daily price in ascending order
        Collections.sort(availableCars, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return Double.compare(car1.getDailyPrice(), car2.getDailyPrice());
            }
        });
        
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return the total revenue from all rentals
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        
        for (Rental rental : rentals) {
            totalRevenue += rental.getTotalCost();
        }
        
        return totalRevenue;
    }

    /**
     * Lists customers with overdue rentals. For each rental, if the back date is null 
     * and the current date is past the due date, marks it as overdue.
     * Returns an empty list if no overdue customers exist.
     * @param currentDate the current date to check against (format: yyyy-MM-dd)
     * @return a list of customers with overdue rentals
     * @throws ParseException if date format is invalid
     */
    public List<Customer> getOverdueCustomers(String currentDate) throws ParseException {
        List<Customer> overdueCustomers = new ArrayList<>();
        
        for (Rental rental : rentals) {
            if (rental.isOverdue(currentDate)) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store: 
     * sums the daily prices of all cars and divides by the total number of cars.
     * If the store has no cars, returns 0.0.
     * @return the average daily price of cars
     */
    public double calculateAverageDailyPrice() {
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
     * @return a map with customers as keys and rental counts as values
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
     * @param backDate the return date (format: yyyy-MM-dd)
     */
    public void returnCar(Rental rental, String backDate) {
        rental.setBackDate(backDate);
        rental.getCar().setRented(false);
    }

    /**
     * Gets all currently active rentals (where car has not been returned)
     * @return a list of active rentals
     */
    public List<Rental> getActiveRentals() {
        List<Rental> activeRentals = new ArrayList<>();
        
        for (Rental rental : rentals) {
            if (rental.isActive()) {
                activeRentals.add(rental);
            }
        }
        
        return activeRentals;
    }
}