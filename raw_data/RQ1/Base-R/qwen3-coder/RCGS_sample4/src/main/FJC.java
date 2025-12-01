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

    // Getters and setters
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

    // Getters and setters
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
     * Revenue is calculated as the sum of (daily price * leasing term days) for each rental
     * where the car has been returned (back date is not null).
     *
     * @return The total revenue generated by completed rentals
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        
        for (Rental rental : rentals) {
            if (rental.getBackDate() != null) {
                // Find the car to get its daily price
                for (Car car : cars) {
                    if (car.getPlate().equals(rental.getCarPlate())) {
                        totalRevenue += car.getDailyPrice() * rental.getLeasingTermDays();
                        break;
                    }
                }
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
            // Check if rental is overdue (no back date and current date past due date)
            if (rental.getBackDate() == null && 
                currentDate.isAfter(rental.getDueDate())) {
                
                // Find the customer associated with this overdue rental
                for (Customer customer : customers) {
                    if (customer.getName().equals(rental.getCustomerName()) && 
                        customer.getSurname().equals(rental.getCustomerSurname())) {
                        // Check if customer is already in the list to avoid duplicates
                        boolean alreadyAdded = false;
                        for (Customer overdueCustomer : overdueCustomers) {
                            if (overdueCustomer.getName().equals(customer.getName()) && 
                                overdueCustomer.getSurname().equals(customer.getSurname())) {
                                alreadyAdded = true;
                                break;
                            }
                        }
                        if (!alreadyAdded) {
                            overdueCustomers.add(customer);
                        }
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
     * Returns a map of customers and their respective rental counts.
     * Returns an empty map if no customers have rented cars.
     *
     * @return A map containing customers and their rental counts
     */
    public Map<Customer, Integer> getRentalsPerCustomer() {
        Map<Customer, Integer> rentalsPerCustomer = new HashMap<>();
        
        for (Rental rental : rentals) {
            // Find the customer associated with this rental
            Customer rentalCustomer = null;
            for (Customer customer : customers) {
                if (customer.getName().equals(rental.getCustomerName()) && 
                    customer.getSurname().equals(rental.getCustomerSurname())) {
                    rentalCustomer = customer;
                    break;
                }
            }
            
            if (rentalCustomer != null) {
                // Update the rental count for this customer
                Integer count = rentalsPerCustomer.get(rentalCustomer);
                if (count == null) {
                    rentalsPerCustomer.put(rentalCustomer, 1);
                } else {
                    rentalsPerCustomer.put(rentalCustomer, count + 1);
                }
            }
        }
        
        return rentalsPerCustomer;
    }

    /**
     * Rents a car to a customer.
     * Updates the car's rented status and creates a new rental record.
     *
     * @param car The car to be rented
     * @param customer The customer renting the car
     * @param leasingTermDays The number of days for the rental
     */
    public void rentCar(Car car, Customer customer, int leasingTermDays) {
        // Mark car as rented
        car.setRented(true);
        
        // Update customer's rented car plate
        customer.setPlateOfRentedCar(car.getPlate());
        
        // Create new rental record
        Rental rental = new Rental();
        rental.setCustomerName(customer.getName());
        rental.setCustomerSurname(customer.getSurname());
        rental.setCarPlate(car.getPlate());
        rental.setLeasingTermDays(leasingTermDays);
        rental.setDueDate(LocalDate.now().plusDays(leasingTermDays));
        rental.setBackDate(null);
        
        // Add rental to the list
        rentals.add(rental);
    }

    /**
     * Returns a rented car.
     * Updates the car's rented status and sets the back date for the rental record.
     *
     * @param car The car being returned
     * @param customer The customer returning the car
     */
    public void returnCar(Car car, Customer customer) {
        // Mark car as not rented
        car.setRented(false);
        
        // Clear customer's rented car plate
        customer.setPlateOfRentedCar("");
        
        // Update the rental record with back date
        for (Rental rental : rentals) {
            if (rental.getCarPlate().equals(car.getPlate()) && 
                rental.getCustomerName().equals(customer.getName()) && 
                rental.getCustomerSurname().equals(customer.getSurname()) && 
                rental.getBackDate() == null) {
                rental.setBackDate(LocalDate.now());
                break;
            }
        }
    }
}