import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Represents a car in the store with its details.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /**
     * Default constructor for Car.
     */
    public Car() {
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
}

/**
 * Represents a customer who rents cars from the store.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
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

    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }
}

/**
 * Represents a rental transaction between a customer and a car.
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
     * Default constructor for Rental.
     */
    public Rental() {
    }

    // Getters and Setters
    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLeasingTerms() {
        return leasingTerms;
    }

    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
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
}

/**
 * Represents an overdue notice sent to customers with late returns.
 */
class OverdueNotice {
    private Customer customer;

    /**
     * Default constructor for OverdueNotice.
     */
    public OverdueNotice() {
    }

    // Getter and Setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends an overdue notice to the specified customer.
     *
     * @param customer The customer to whom the notice is sent.
     */
    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Implementation for sending notice would go here
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }
}

/**
 * Represents the car rental store which manages cars, rentals, and overdue notices.
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /**
     * Default constructor for Store. Initializes empty lists.
     */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    // Getters and Setters
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<OverdueNotice> getNotices() {
        return notices;
    }

    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }

    /**
     * Identifies available cars in the store that are not currently rented.
     * Returns a list of available cars sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     *
     * @return A list of available cars sorted by daily price.
     */
    public List<Car> identifyAvailableCars() {
        Set<String> rentedCarPlates = new HashSet<>();
        
        // Collect plates of all currently rented cars
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null) {
                rentedCarPlates.add(rental.getCar().getPlate());
            }
        }
        
        // Filter out rented cars from the full car list
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!rentedCarPlates.contains(car.getPlate())) {
                availableCars.add(car);
            }
        }
        
        // Sort by daily price in ascending order
        availableCars.sort(Comparator.comparingDouble(Car::getDailyPrice));
        
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all completed rentals in the store.
     *
     * @return The total revenue from all rentals.
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (Rental rental : rentals) {
            totalRevenue += rental.getTotalPrice();
        }
        return totalRevenue;
    }

    /**
     * Finds customers with overdue rentals based on the current date.
     * A rental is overdue if backDate is null and the current date is past the due date.
     *
     * @param currentDate The current date used to check for overdue rentals.
     * @return A list of customers with overdue rentals.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        List<Customer> overdueCustomers = new ArrayList<>();
        
        for (Rental rental : rentals) {
            // Check if rental is overdue
            if (rental.getBackDate() == null && 
                rental.getDueDate() != null && 
                currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of all cars in the store.
     * If the store has no cars, returns 0.0.
     *
     * @return The average daily price of cars in the store.
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        
        double totalDailyPrice = 0.0;
        for (Car car : cars) {
            totalDailyPrice += car.getDailyPrice();
        }
        
        return totalDailyPrice / cars.size();
    }

    /**
     * Counts the number of cars rented per customer.
     * Returns a map of customers and their respective rental counts.
     * If no customers have rented cars, returns an empty map.
     *
     * @return A map containing customers and their rental counts.
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> rentalCountMap = new HashMap<>();
        
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCountMap.put(customer, rentalCountMap.getOrDefault(customer, 0) + 1);
        }
        
        return rentalCountMap;
    }
}