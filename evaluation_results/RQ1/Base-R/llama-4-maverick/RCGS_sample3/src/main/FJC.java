import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
     * Constructor for Car with parameters.
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
    private LocalDate dueDate;
    private LocalDate backDate;

    /**
     * Default constructor for Customer.
     */
    public Customer() {}

    /**
     * Constructor for Customer with parameters.
     * @param name The name of the customer.
     * @param surname The surname of the customer.
     * @param address The address of the customer.
     * @param rentedCarPlate The plate number of the rented car.
     * @param dueDate The due date for returning the car.
     */
    public Customer(String name, String surname, String address, String rentedCarPlate, LocalDate dueDate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.rentedCarPlate = rentedCarPlate;
        this.dueDate = dueDate;
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
     * Gets the due date for returning the car.
     * @return The due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the car.
     * @param dueDate The new due date.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the actual return date of the car.
     * @return The return date.
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * Sets the actual return date of the car.
     * @param backDate The new return date.
     */
    public void setBackDate(LocalDate backDate) {
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
     * Adds a car to the store's inventory.
     * @param car The car to add.
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Adds a customer to the store's records.
     * @param customer The customer to add.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Gets the list of available cars sorted by daily price in ascending order.
     * @return A list of available cars.
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals.
     * @return The total revenue.
     */
    public double calculateTotalRevenue() {
        return customers.stream()
                .mapToDouble(customer -> {
                    Car rentedCar = cars.stream()
                            .filter(car -> car.getPlate().equals(customer.getRentedCarPlate()))
                            .findFirst()
                            .orElse(null);
                    if (rentedCar != null) {
                        // Assuming the rental duration is from the rental date (not given) to the due date or back date
                        // For simplicity, let's assume the rental starts from the due date minus 7 days (one week rental)
                        LocalDate rentalDate = customer.getDueDate().minusDays(7);
                        LocalDate endDate = customer.getBackDate() != null ? customer.getBackDate() : LocalDate.now();
                        long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, endDate);
                        return rentedCar.getDailyPrice() * rentalDays;
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
        LocalDate today = LocalDate.now();
        return customers.stream()
                .filter(customer -> customer.getBackDate() == null && today.isAfter(customer.getDueDate()))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average daily price of cars in the store.
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
    public Map<String, Long> countCarsRentedPerCustomer() {
        Map<String, Long> rentalCounts = new HashMap<>();
        for (Customer customer : customers) {
            String customerName = customer.getName() + " " + customer.getSurname();
            rentalCounts.put(customerName, rentalCounts.getOrDefault(customerName, 0L) + 1);
        }
        return rentalCounts;
    }
}

 class Main {
    public static void main(String[] args) {
        // Example usage
        CarRentalStore store = new CarRentalStore();

        Car car1 = new Car("ABC123", "Model1", 50.0);
        Car car2 = new Car("DEF456", "Model2", 70.0);
        store.addCar(car1);
        store.addCar(car2);

        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.now().plusDays(7));
        store.addCustomer(customer1);

        System.out.println("Available Cars:");
        store.getAvailableCars().forEach(car -> System.out.println(car.getPlate() + " " + car.getModel() + " " + car.getDailyPrice()));

        System.out.println("Total Revenue: " + store.calculateTotalRevenue());

        System.out.println("Overdue Customers:");
        store.getOverdueCustomers().forEach(customer -> System.out.println(customer.getName() + " " + customer.getSurname()));

        System.out.println("Average Daily Price: " + store.calculateAverageDailyPrice());

        System.out.println("Cars Rented Per Customer:");
        store.countCarsRentedPerCustomer().forEach((customer, count) -> System.out.println(customer + ": " + count));
    }
}