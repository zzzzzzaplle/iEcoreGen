import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /**
     * Constructor for Car class.
     */
    public Car() {
    }

    /**
     * Constructor for Car class with parameters.
     * @param plate the license plate of the car
     * @param model the model of the car
     * @param dailyPrice the daily rental price of the car
     */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
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

class Customer {
    private String name;
    private String surname;
    private String address;

    /**
     * Constructor for Customer class.
     */
    public Customer() {
    }

    /**
     * Constructor for Customer class with parameters.
     * @param name the first name of the customer
     * @param surname the last name of the customer
     * @param address the address of the customer
     */
    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return name.equals(customer.name) && surname.equals(customer.surname);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + surname.hashCode();
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate backDate;

    /**
     * Constructor for Rental class.
     */
    public Rental() {
    }

    /**
     * Constructor for Rental class with parameters.
     * @param car the car being rented
     * @param customer the customer renting the car
     * @param startDate the start date of the rental
     * @param dueDate the due date of the rental
     */
    public Rental(Car car, Customer customer, LocalDate startDate, LocalDate dueDate) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    // Getters and Setters
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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
}

class CarGallery {
    private List<Car> cars;
    private List<Rental> rentals;

    /**
     * Constructor for CarGallery class.
     */
    public CarGallery() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    /**
     * Gets the list of cars in the gallery.
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Adds a car to the gallery.
     * @param car the car to add
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Gets the list of rentals in the gallery.
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Adds a rental to the gallery.
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    /**
     * Gets the list of available cars in the gallery, sorted by daily price in ascending order.
     * @return the list of available cars
     */
    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> rentals.stream().noneMatch(rental -> rental.getCar().equals(car) && rental.getBackDate() == null))
                .sorted((car1, car2) -> Double.compare(car1.getDailyPrice(), car2.getDailyPrice()))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .filter(rental -> rental.getBackDate() != null)
                .mapToDouble(rental -> {
                    long daysRented = rental.getBackDate().toEpochDay() - rental.getStartDate().toEpochDay();
                    return daysRented * rental.getCar().getDailyPrice();
                })
                .sum();
    }

    /**
     * Lists customers with overdue rentals.
     * @return the list of customers with overdue rentals
     */
    public List<Customer> listOverdueCustomers() {
        LocalDate currentDate = LocalDate.now();
        return rentals.stream()
                .filter(rental -> rental.getBackDate() == null && rental.getDueDate().isBefore(currentDate))
                .map(Rental::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of cars in the store.
     * @return the average daily price
     */
    public double getAverageDailyPrice() {
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
     * @return a map of customers and their respective rental counts
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> customerRentalCount = new HashMap<>();
        for (Rental rental : rentals) {
            customerRentalCount.put(rental.getCustomer(), customerRentalCount.getOrDefault(rental.getCustomer(), 0) + 1);
        }
        return customerRentalCount;
    }
}