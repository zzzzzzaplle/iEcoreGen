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
        this.plate = "";
        this.model = "";
        this.dailyPrice = 0.0;
    }

    // Getters and setters
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
 * Represents a customer who can rent cars.
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
        this.name = "";
        this.surname = "";
        this.address = "";
        this.rentedCarPlate = "";
    }

    // Getters and setters
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return Objects.equals(name, customer.name) &&
               Objects.equals(surname, customer.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
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
        this.rentalDate = new Date();
        this.dueDate = new Date();
        this.backDate = null;
        this.totalPrice = 0.0;
        this.leasingTerms = "";
        this.car = new Car();
        this.customer = new Customer();
    }

    // Getters and setters
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
        this.customer = new Customer();
    }

    // Getter and setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends an overdue notice to a specified customer.
     *
     * @param customer The customer to send the notice to
     */
    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Implementation would handle sending the actual notice
    }
}

/**
 * Manages the car rental store operations including cars, rentals, and overdue notices.
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /**
     * Default constructor for Store.
     */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    // Getters and setters
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
     *
     * @return A list of available cars sorted by price, or an empty list if none are available
     */
    public List<Car> identifyAvailableCars() {
        Set<String> rentedPlates = new HashSet<>();
        
        // Collect plates of all rented cars where backDate is null (still rented)
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null) {
                rentedPlates.add(rental.getCar().getPlate());
            }
        }
        
        // Filter out rented cars from the cars list
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!rentedPlates.contains(car.getPlate())) {
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
     * @return The total revenue from all rentals
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
     * A rental is overdue if backDate is null and current date is past the due date.
     *
     * @param currentDate The current date to check against due dates
     * @return A list of customers with overdue rentals, or an empty list if none exist
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        List<Customer> overdueCustomers = new ArrayList<>();
        
        for (Rental rental : rentals) {
            // Check if rental is still active (not returned) and overdue
            if (rental.getBackDate() == null && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of all cars in the store.
     *
     * @return The average daily price of cars, or 0.0 if no cars exist
     */
    public double determineAverageDailyPrice() {
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
     * Counts the number of cars rented by each customer.
     *
     * @return A map of customers to their rental counts, or an empty map if no rentals exist
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