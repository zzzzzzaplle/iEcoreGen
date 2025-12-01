import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a car in the gallery.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /** Unparameterized constructor. */
    public Car() {
    }

    /** Getters and setters */
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

    /** Equality based on the unique license plate. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Car)) return false;
        Car other = (Car) obj;
        return plate != null && plate.equals(other.plate);
    }

    @Override
    public int hashCode() {
        return plate != null ? plate.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Car{" +
                "plate='" + plate + '\'' +
                ", model='" + model + '\'' +
                ", dailyPrice=" + dailyPrice +
                '}';
    }
}

/**
 * Represents a customer of the store.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate; // plate of the car currently rented (if any)

    /** Unparameterized constructor. */
    public Customer() {
    }

    /** Getters and setters */
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

    /** Equality based on the combination of personal data. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return (name != null && name.equals(other.name)) &&
               (surname != null && surname.equals(other.surname)) &&
               (address != null && address.equals(other.address));
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", rentedCarPlate='" + rentedCarPlate + '\'' +
                '}';
    }
}

/**
 * Represents a rental transaction.
 */
class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate; // null if not yet returned
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /** Unparameterized constructor. */
    public Rental() {
    }

    /** Getters and setters */
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
 * Represents an overdue notice that can be sent to a customer.
 */
class OverdueNotice {
    private Customer customer;

    /** Unparameterized constructor. */
    public OverdueNotice() {
    }

    /** Getter and setter */
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends an overdue notice to the supplied customer.
     *
     * @param customer the customer who will receive the notice
     */
    public void sendNoticeTo(Customer customer) {
        // In a real system this would involve email/SMS/etc.
        System.out.println("Overdue notice sent to: " + customer);
    }
}

/**
 * Central store that manages cars, rentals and overdue notices.
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /** Unparameterized constructor initialising internal collections. */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    /** Getters and setters */
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
     * Identify all cars that are currently not rented.
     * <p>
     * A car is considered unavailable if there exists a {@link Rental} for that car
     * whose {@code backDate} is {@code null} (i.e., the car has not been returned yet).
     * The returned list is sorted by the car's daily price in ascending order.
     *
     * @return a list of available cars sorted by daily price; empty list if none are available
     */
    public List<Car> identifyAvailableCars() {
        Set<String> rentedCarPlates = new HashSet<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getCar() != null) {
                rentedCarPlates.add(rental.getCar().getPlate());
            }
        }

        List<Car> available = new ArrayList<>();
        for (Car car : cars) {
            if (!rentedCarPlates.contains(car.getPlate())) {
                available.add(car);
            }
        }

        Collections.sort(available, Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all rentals stored in the system.
     *
     * @return the sum of {@code totalPrice} of every {@link Rental}
     */
    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Rental rental : rentals) {
            sum += rental.getTotalPrice();
        }
        return sum;
    }

    /**
     * Finds all customers that have overdue rentals.
     * <p>
     * A rental is overdue when {@code backDate} is {@code null} and the supplied
     * {@code currentDate} is after the rental's {@code dueDate}.
     *
     * @param currentDate the date against which overdue status is evaluated
     * @return a list of distinct customers with overdue rentals; empty list if none
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        Set<Customer> overdueCustomers = new HashSet<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getDueDate() != null
                    && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return new ArrayList<>(overdueCustomers);
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the average price, or {@code 0.0} if there are no cars
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Car car : cars) {
            total += car.getDailyPrice();
        }
        return total / cars.size();
    }

    /**
     * Counts how many cars each customer has rented.
     *
     * @return a map where the key is a {@link Customer} and the value is the number of rentals made by that customer;
     *         an empty map if no rentals exist
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();
        for (Rental rental : rentals) {
            Customer cust = rental.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }
        return countMap;
    }
}