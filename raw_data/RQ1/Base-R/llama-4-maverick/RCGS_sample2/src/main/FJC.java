import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a Car with its details.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean isRented;

    /**
     * Default constructor for Car.
     */
    public Car() {}

    /**
     * Parameterized constructor for Car.
     * @param plate The plate number of the car.
     * @param model The model of the car.
     * @param dailyPrice The daily price of the car.
     */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isRented = false;
    }

    /**
     * Gets the plate number of the car.
     * @return The plate number.
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the plate number of the car.
     * @param plate The new plate number.
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the model of the car.
     * @return The model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     * @param model The new model.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily price of the car.
     * @return The daily price.
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily price of the car.
     * @param dailyPrice The new daily price.
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /**
     * Checks if the car is rented.
     * @return True if rented, false otherwise.
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rental status of the car.
     * @param rented True if rented, false otherwise.
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a Customer with their details and rental information.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate;
    private String leaseStartDate;
    private String leaseDueDate;
    private String backDate;

    /**
     * Default constructor for Customer.
     */
    public Customer() {}

    /**
     * Parameterized constructor for Customer.
     * @param name The name of the customer.
     * @param surname The surname of the customer.
     * @param address The address of the customer.
     * @param rentedCarPlate The plate number of the rented car.
     * @param leaseStartDate The start date of the lease.
     * @param leaseDueDate The due date of the lease.
     */
    public Customer(String name, String surname, String address, String rentedCarPlate, String leaseStartDate, String leaseDueDate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.rentedCarPlate = rentedCarPlate;
        this.leaseStartDate = leaseStartDate;
        this.leaseDueDate = leaseDueDate;
        this.backDate = null;
    }

    /**
     * Gets the name of the customer.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the customer.
     * @return The surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the customer.
     * @param surname The new surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the address of the customer.
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the plate number of the rented car.
     * @return The plate number.
     */
    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    /**
     * Sets the plate number of the rented car.
     * @param rentedCarPlate The new plate number.
     */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }

    /**
     * Gets the start date of the lease.
     * @return The start date.
     */
    public String getLeaseStartDate() {
        return leaseStartDate;
    }

    /**
     * Sets the start date of the lease.
     * @param leaseStartDate The new start date.
     */
    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    /**
     * Gets the due date of the lease.
     * @return The due date.
     */
    public String getLeaseDueDate() {
        return leaseDueDate;
    }

    /**
     * Sets the due date of the lease.
     * @param leaseDueDate The new due date.
     */
    public void setLeaseDueDate(String leaseDueDate) {
        this.leaseDueDate = leaseDueDate;
    }

    /**
     * Gets the back date of the rental.
     * @return The back date.
     */
    public String getBackDate() {
        return backDate;
    }

    /**
     * Sets the back date of the rental.
     * @param backDate The new back date.
     */
    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}

/**
 * Manages the car rental store's operations.
 */
class CarRentalStore {
    private List<Car> cars;
    private List<Customer> customers;

    /**
     * Default constructor for CarRentalStore.
     */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    /**
     * Gets the list of cars in the store.
     * @return The list of cars.
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars in the store.
     * @param cars The new list of cars.
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of customers in the store.
     * @return The list of customers.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets the list of customers in the store.
     * @param customers The new list of customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * Identifies available cars in the store, sorted by daily price in ascending order.
     * @return A list of available cars.
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted((c1, c2) -> Double.compare(c1.getDailyPrice(), c2.getDailyPrice()))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return The total revenue.
     */
    public double calculateTotalRevenue() {
        return customers.stream()
                .filter(customer -> customer.getBackDate() != null)
                .mapToDouble(customer -> {
                    Car car = cars.stream()
                            .filter(c -> c.getPlate().equals(customer.getRentedCarPlate()))
                            .findFirst()
                            .orElse(null);
                    if (car != null) {
                        LocalDate startDate = LocalDate.parse(customer.getLeaseStartDate());
                        LocalDate endDate = LocalDate.parse(customer.getBackDate());
                        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
                        return days * car.getDailyPrice();
                    } else {
                        return 0;
                    }
                })
                .sum();
    }

    /**
     * Lists customers with overdue rentals.
     * @return A list of customers with overdue rentals.
     */
    public List<Customer> getOverdueCustomers() {
        LocalDate currentDate = LocalDate.now();
        return customers.stream()
                .filter(customer -> customer.getBackDate() == null && LocalDate.parse(customer.getLeaseDueDate()).isBefore(currentDate))
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of cars in the store.
     * @return The average daily price.
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
     * @return A map of customers and their respective rental counts.
     */
    public Map<String, Integer> countCarsRentedPerCustomer() {
        Map<String, Integer> rentalCounts = new HashMap<>();
        for (Customer customer : customers) {
            String customerName = customer.getName() + " " + customer.getSurname();
            rentalCounts.put(customerName, rentalCounts.getOrDefault(customerName, 0) + 1);
        }
        return rentalCounts;
    }
}