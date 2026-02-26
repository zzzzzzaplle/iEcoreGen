import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a car stored in the gallery.
 */
 class Car {

    private String plate;
    private String model;
    private double dailyPrice;

    /** No‑argument constructor required by the task. */
    public Car() {
    }

    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return Objects.equals(plate, car.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }
}

/**
 * Represents a customer who rents cars.
 */
 class Customer {

    private String name;
    private String surname;
    private String address;

    /** No‑argument constructor required by the task. */
    public Customer() {
    }

    public Customer(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

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

    /**
     * Two customers are considered equal if they share the same name, surname and address.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
               Objects.equals(surname, customer.surname) &&
               Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, address);
    }
}

/**
 * Represents a rental transaction.
 */
 class Rental {

    private Customer customer;
    private Car car;
    private LocalDate rentDate;   // date when the car was taken
    private LocalDate dueDate;    // expected return date
    private LocalDate backDate;   // actual return date; null if not yet returned
    private double totalPrice;    // price calculated at the moment of return

    /** No‑argument constructor required by the task. */
    public Rental() {
    }

    public Rental(Customer customer, Car car, LocalDate rentDate, LocalDate dueDate) {
        this.customer = customer;
        this.car = car;
        this.rentDate = rentDate;
        this.dueDate = dueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

/**
 * Manages the gallery of cars and the rentals performed by the store assistant.
 */
 class Store {

    private List<Car> cars;
    private List<Rental> rentals;

    /** No‑argument constructor required by the task. */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

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

    /**
     * Adds a car to the gallery.
     *
     * @param car the car to be added
     */
    public void addCar(Car car) {
        this.cars.add(car);
    }

    /**
     * Registers a new rental. The method creates a {@link Rental} object,
     * adds it to the internal list and leaves the {@code backDate} and {@code totalPrice}
     * as {@code null} and {@code 0.0} respectively until the car is returned.
     *
     * @param customer the customer who rents the car
     * @param car the car being rented
     * @param rentDate the date the rental starts
     * @param dueDate the expected return date
     */
    public void rentCar(Customer customer, Car car, LocalDate rentDate, LocalDate dueDate) {
        Rental rental = new Rental(customer, car, rentDate, dueDate);
        this.rentals.add(rental);
    }

    /**
     * Returns a car from a rental, setting the back date and calculating the total price.
     *
     * @param rental the rental to be closed
     * @param backDate the actual return date
     */
    public void returnCar(Rental rental, LocalDate backDate) {
        rental.setBackDate(backDate);
        long days = java.time.temporal.ChronoUnit.DAYS.between(rental.getRentDate(), backDate);
        // At least one day is charged
        days = Math.max(days, 1);
        double price = days * rental.getCar().getDailyPrice();
        rental.setTotalPrice(price);
    }

    /**
     * Identifies all available cars (i.e., cars that are not currently rented).
     * The result is sorted by daily price in ascending order.
     *
     * @return a list of available cars sorted by price; empty list if none are available
     */
    public List<Car> getAvailableCars() {
        List<Car> available = new ArrayList<>();
        for (Car car : cars) {
            boolean rented = false;
            for (Rental rental : rentals) {
                if (rental.getCar().equals(car) && rental.getBackDate() == null) {
                    rented = true;
                    break;
                }
            }
            if (!rented) {
                available.add(car);
            }
        }
        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all completed rentals.
     *
     * @return the sum of total prices of all rentals that have been returned
     */
    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Rental rental : rentals) {
            if (rental.getBackDate() != null) {
                sum += rental.getTotalPrice();
            }
        }
        return sum;
    }

    /**
     * Retrieves a list of customers with overdue rentals. A rental is overdue when
     * it has no back date and the current date is later than its due date.
     *
     * @return a list of customers with overdue rentals; empty list if none
     */
    public List<Customer> getOverdueCustomers() {
        List<Customer> overdue = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getDueDate().isBefore(today)) {
                if (!overdue.contains(rental.getCustomer())) {
                    overdue.add(rental.getCustomer());
                }
            }
        }
        return overdue;
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the average price, or {@code 0.0} if there are no cars
     */
    public double getAverageDailyPrice() {
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
     * Counts how many rentals each customer has performed.
     *
     * @return a map where the key is a {@link Customer} and the value is the number of rentals;
     *         an empty map if no rentals exist
     */
    public Map<Customer, Integer> getRentalCountPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental rental : rentals) {
            Customer cust = rental.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }
}