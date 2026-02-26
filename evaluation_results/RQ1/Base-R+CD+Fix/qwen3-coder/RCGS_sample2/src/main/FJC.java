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
 * Represents a customer with personal information.
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
 * Represents an overdue notice sent to a customer.
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
     * Sends an overdue notice to a customer.
     *
     * @param customer The customer to send the notice to.
     */
    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Implementation for sending notice would go here
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }
}

/**
 * Represents the car rental store with cars, rentals, and overdue notices.
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
     * Identifies available cars in the store.
     * A car is available if it is not currently rented.
     * Returns a list of available cars, sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     *
     * @return A list of available cars sorted by daily price.
     */
    public List<Car> identifyAvailableCars() {
        List<Car> rentedCars = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null) {
                rentedCars.add(rental.getCar());
            }
        }

        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            boolean isRented = false;
            for (Car rentedCar : rentedCars) {
                if (rentedCar.getPlate().equals(car.getPlate())) {
                    isRented = true;
                    break;
                }
            }
            if (!isRented) {
                availableCars.add(car);
            }
        }

        // Sort by daily price
        availableCars.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     *
     * @return The total revenue from all completed rentals.
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
     * mark it as overdue.
     *
     * @param currentDate The current date to check against due dates.
     * @return A list of customers with overdue rentals.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        List<Customer> overdueCustomers = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store.
     * Sums the daily prices of all cars and divides by the total number of cars.
     * If the store has no cars, returns 0.0.
     *
     * @return The average daily price of all cars.
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }

        double totalPrices = 0.0;
        for (Car car : cars) {
            totalPrices += car.getDailyPrice();
        }
        return totalPrices / cars.size();
    }

    /**
     * Counts the number of cars rented per customer.
     * Returns a map of customers and their respective rental counts.
     * Returns an empty map if no customers have rented cars.
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