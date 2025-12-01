import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
 * Represents a customer who rents cars.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String plateOfRentedCar;

    /**
     * Default constructor for Customer.
     */
    public Customer() {
        this.name = "";
        this.surname = "";
        this.address = "";
        this.plateOfRentedCar = "";
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

    public String getPlateOfRentedCar() {
        return plateOfRentedCar;
    }

    public void setPlateOfRentedCar(String plateOfRentedCar) {
        this.plateOfRentedCar = plateOfRentedCar;
    }
}

/**
 * Represents a car rental record.
 */
class Rental {
    private String customerName;
    private String customerSurname;
    private String carPlate;
    private LocalDate dueDate;
    private LocalDate backDate;
    private int leasingTermDays;

    /**
     * Default constructor for Rental.
     */
    public Rental() {
        this.customerName = "";
        this.customerSurname = "";
        this.carPlate = "";
        this.dueDate = LocalDate.now();
        this.backDate = null;
        this.leasingTermDays = 0;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
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

    public int getLeasingTermDays() {
        return leasingTermDays;
    }

    public void setLeasingTermDays(int leasingTermDays) {
        this.leasingTermDays = leasingTermDays;
    }
}

/**
 * Manages the car rental store operations.
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
     * Identifies available cars in the store. A car is available if it is not currently rented.
     * Returns a list of available cars, sorted by daily price in ascending order.
     * If no available cars exist, returns an empty list.
     *
     * @return A list of available cars sorted by daily price
     */
    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }
        
        // Sort by daily price in ascending order
        availableCars.sort(Comparator.comparingDouble(Car::getDailyPrice));
        
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * Revenue is calculated as the sum of (daily price * leasing term days) for each rental.
     *
     * @return The total revenue generated by all rentals
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        
        for (Rental rental : rentals) {
            // Find the car associated with this rental
            Car rentedCar = null;
            for (Car car : cars) {
                if (car.getPlate().equals(rental.getCarPlate())) {
                    rentedCar = car;
                    break;
                }
            }
            
            if (rentedCar != null) {
                totalRevenue += rentedCar.getDailyPrice() * rental.getLeasingTermDays();
            }
        }
        
        return totalRevenue;
    }

    /**
     * Lists customers with overdue rentals. For each rental, if the back date is null
     * and the current date is past the due date, mark it as overdue.
     * Returns an empty list if no overdue customers exist.
     *
     * @return A list of customers with overdue rentals
     */
    public List<Customer> getOverdueCustomers() {
        List<Customer> overdueCustomers = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        
        for (Rental rental : rentals) {
            // Check if rental is overdue (back date is null and current date is past due date)
            if (rental.getBackDate() == null && 
                currentDate.isAfter(rental.getDueDate())) {
                
                // Find the customer associated with this rental
                for (Customer customer : customers) {
                    if (customer.getName().equals(rental.getCustomerName()) && 
                        customer.getSurname().equals(rental.getCustomerSurname())) {
                        overdueCustomers.add(customer);
                        break;
                    }
                }
            }
        }
        
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store.
     * Calculated as the sum of daily prices of all cars divided by the total number of cars.
     * If the store has no cars, returns 0.0.
     *
     * @return The average daily price of all cars in the store
     */
    public double getAverageDailyPrice() {
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
     * Returns a map of customers and their respective rental counts,
     * or an empty map if no customers have rented cars.
     *
     * @return A map of customers and their rental counts
     */
    public Map<String, Integer> getRentalsPerCustomer() {
        Map<String, Integer> rentalsPerCustomer = new HashMap<>();
        
        for (Rental rental : rentals) {
            String customerKey = rental.getCustomerName() + " " + rental.getCustomerSurname();
            rentalsPerCustomer.put(customerKey, rentalsPerCustomer.getOrDefault(customerKey, 0) + 1);
        }
        
        return rentalsPerCustomer;
    }

    /**
     * Adds a car to the store.
     *
     * @param car The car to add
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Adds a customer to the store.
     *
     * @param customer The customer to add
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Adds a rental record to the store.
     *
     * @param rental The rental record to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
        
        // Mark the car as rented
        for (Car car : cars) {
            if (car.getPlate().equals(rental.getCarPlate())) {
                car.setRented(true);
                break;
            }
        }
    }

    /**
     * Marks a rental as returned by setting the back date.
     *
     * @param carPlate The plate of the car being returned
     * @param backDate The date the car was returned
     */
    public void returnCar(String carPlate, LocalDate backDate) {
        for (Rental rental : rentals) {
            if (rental.getCarPlate().equals(carPlate) && rental.getBackDate() == null) {
                rental.setBackDate(backDate);
                break;
            }
        }
        
        // Mark the car as available
        for (Car car : cars) {
            if (car.getPlate().equals(carPlate)) {
                car.setRented(false);
                break;
            }
        }
    }
}