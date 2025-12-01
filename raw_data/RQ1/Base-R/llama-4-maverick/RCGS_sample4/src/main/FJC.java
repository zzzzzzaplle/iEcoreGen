import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a Car with plate, model, and daily price.
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
     * Constructor for Car.
     * 
     * @param plate     the plate number of the car
     * @param model     the model of the car
     * @param dailyPrice the daily price of the car
     */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.isRented = false;
    }

    /**
     * Gets the plate number of the car.
     * 
     * @return the plate number
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the plate number of the car.
     * 
     * @param plate the plate number to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Gets the model of the car.
     * 
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     * 
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the daily price of the car.
     * 
     * @return the daily price
     */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /**
     * Sets the daily price of the car.
     * 
     * @param dailyPrice the daily price to set
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /**
     * Checks if the car is rented.
     * 
     * @return true if the car is rented, false otherwise
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rental status of the car.
     * 
     * @param rented true if the car is rented, false otherwise
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a Customer with name, surname, address, and rented car details.
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
     * Constructor for Customer.
     * 
     * @param name           the name of the customer
     * @param surname        the surname of the customer
     * @param address        the address of the customer
     * @param rentedCarPlate the plate number of the rented car
     * @param dueDate        the due date for returning the car
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
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the customer.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the customer.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the address of the customer.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * 
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the plate number of the rented car.
     * 
     * @return the rented car plate
     */
    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    /**
     * Sets the plate number of the rented car.
     * 
     * @param rentedCarPlate the rented car plate to set
     */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }

    /**
     * Gets the due date for returning the car.
     * 
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date for returning the car.
     * 
     * @param dueDate the due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the actual return date of the car.
     * 
     * @return the back date
     */
    public LocalDate getBackDate() {
        return backDate;
    }

    /**
     * Sets the actual return date of the car.
     * 
     * @param backDate the back date to set
     */
    public void setBackDate(LocalDate backDate) {
        this.backDate = backDate;
    }
}

/**
 * Represents a Rental with customer and car details.
 */
class Rental {
    private Customer customer;
    private Car car;

    /**
     * Default constructor for Rental.
     */
    public Rental() {}

    /**
     * Constructor for Rental.
     * 
     * @param customer the customer renting the car
     * @param car      the car being rented
     */
    public Rental(Customer customer, Car car) {
        this.customer = customer;
        this.car = car;
    }

    /**
     * Gets the customer renting the car.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer renting the car.
     * 
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the car being rented.
     * 
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car being rented.
     * 
     * @param car the car to set
     */
    public void setCar(Car car) {
        this.car = car;
    }
}

/**
 * Manages the car rental store's operations.
 */
class CarRentalStore {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /**
     * Default constructor for CarRentalStore.
     */
    public CarRentalStore() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * Adds a car to the store's inventory.
     * 
     * @param car the car to add
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Adds a customer to the store's records.
     * 
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Adds a rental to the store's records.
     * 
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.getCar().setRented(true);
    }

    /**
     * Identifies available cars in the store, sorted by daily price in ascending order.
     * 
     * @return a list of available cars
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> !car.isRented())
                .sorted((c1, c2) -> Double.compare(c1.getDailyPrice(), c2.getDailyPrice()))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * 
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .mapToDouble(rental -> rental.getCar().getDailyPrice() * 
                        (rental.getCustomer().getBackDate() != null ? 
                                java.time.temporal.ChronoUnit.DAYS.between(rental.getCustomer().getDueDate(), rental.getCustomer().getBackDate()) : 0))
                .sum();
    }

    /**
     * Lists customers with overdue rentals.
     * 
     * @return a list of customers with overdue rentals
     */
    public List<Customer> getOverdueCustomers() {
        LocalDate today = LocalDate.now();
        return customers.stream()
                .filter(customer -> customer.getBackDate() == null && customer.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of cars in the store.
     * 
     * @return the average daily price
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
     * 
     * @return a map of customers and their respective rental counts
     */
    public Map<String, Integer> countCarsRentedPerCustomer() {
        Map<String, Integer> rentalCounts = new HashMap<>();
        for (Rental rental : rentals) {
            String customerName = rental.getCustomer().getName() + " " + rental.getCustomer().getSurname();
            rentalCounts.put(customerName, rentalCounts.getOrDefault(customerName, 0) + 1);
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

        Rental rental1 = new Rental(customer1, car1);
        store.addRental(rental1);

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