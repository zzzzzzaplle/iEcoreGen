import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a car in the store.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /**
     * Default constructor.
     */
    public Car() {
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the dailyPrice
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * @param dailyPrice the dailyPrice to set
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}

/**
 * Represents a customer who rents cars.
 */
class Customer {
    private String name;
    private String surname;
    private String address;

    /**
     * Default constructor.
     */
    public Customer() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}

/**
 * Represents a rental record.
 */
class Rental {
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate backDate;

    /**
     * Default constructor.
     */
    public Rental() {
    }

    /**
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the backDate
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * @param backDate the backDate to set
     */
    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }

    /**
     * Checks if the rental is overdue.
     * @return true if the rental is overdue, false otherwise
     */
    public boolean isOverdue() {
        return backDate == null && dueDate != null && dueDate.isBefore(LocalDate.now());
    }
}

/**
 * Represents the car rental store.
 */
class CarRentalStore {
    private List<Car> cars;
    private List<Rental> rentals;

    /**
     * Default constructor.
     */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * @return the cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * @param cars the cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * @return the rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * @param rentals the rentals to set
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Identifies available cars in the store.
     * @return a list of available cars, sorted by daily price in ascending order
     */
    public List<Car> identifyAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            boolean isAvailable = true;
            for (Rental rental : rentals) {
                if (rental.getCar().getPlate().equals(car.getPlate()) && rental.getBackDate() == null) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableCars.add(car);
            }
        }
        Collections.sort(availableCars, Comparator.comparingDouble(Car::getDailyPrice));
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (Rental rental : rentals) {
            if (rental.getBackDate() != null) {
                long daysRented = java.time.temporal.ChronoUnit.DAYS.between(rental.getStartDate(), rental.getBackDate());
                totalRevenue += daysRented * rental.getCar().getDailyPrice();
            }
        }
        return totalRevenue;
    }

    /**
     * Lists customers with overdue rentals.
     * @return a list of customers with overdue rentals
     */
    public List<Customer> listOverdueCustomers() {
        List<Customer> overdueCustomers = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.isOverdue()) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store.
     * @return the average daily price
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
     * Counts the number of cars rented per customer.
     * @return a map of customers and their respective rental counts
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        return rentalCounts;
    }
}