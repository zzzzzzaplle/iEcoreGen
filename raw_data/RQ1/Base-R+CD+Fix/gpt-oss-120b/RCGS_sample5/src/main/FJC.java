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
 * Represents a car stored in the gallery.
 */
 class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /** Unparameterized constructor */
    public Car() {
    }

    /** Getters and Setters */
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

    /** Equality based on unique plate number */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Car)) return false;
        Car other = (Car) obj;
        return plate != null && plate.equals(other.plate);
    }

    @Override
    public int hashCode() {
        return plate == null ? 0 : plate.hashCode();
    }
}

/**
 * Holds information about a customer.
 */
 class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate;   // plate of the car currently rented (if any)

    /** Unparameterized constructor */
    public Customer() {
    }

    /** Getters and Setters */
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

    /** Equality based on personal data (name, surname, address) */
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
        int result = (name == null) ? 0 : name.hashCode();
        result = 31 * result + ((surname == null) ? 0 : surname.hashCode());
        result = 31 * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }
}

/**
 * Represents a rental transaction.
 */
 class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate;          // null if not yet returned
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /** Unparameterized constructor */
    public Rental() {
    }

    /** Getters and Setters */
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

    /** Unparameterized constructor */
    public OverdueNotice() {
    }

    /** Getters and Setters */
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends an overdue notice to the given customer.
     *
     * @param customer the customer to notify
     */
    public void sendNoticeTo(Customer customer) {
        // In a real system this would send an email or a printed notice.
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }
}

/**
 * Central class managing cars, rentals and overdue notices.
 */
 class Store {
    private List<Car> cars = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();
    private List<OverdueNotice> notices = new ArrayList<>();

    /** Unparameterized constructor */
    public Store() {
    }

    /** Getters and Setters */
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
     * The returned list is sorted by daily price in ascending order.
     *
     * @return a list of available cars (may be empty)
     */
    public List<Car> identifyAvailableCars() {
        List<Car> available = new ArrayList<>();

        for (Car car : cars) {
            boolean isRented = false;
            for (Rental rental : rentals) {
                if (rental.getCar() != null && rental.getCar().equals(car) && rental.getBackDate() == null) {
                    isRented = true;
                    break;
                }
            }
            if (!isRented) {
                available.add(car);
            }
        }

        // Sort by daily price ascending
        Collections.sort(available, Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all rentals.
     *
     * @return sum of totalPrice of every rental; 0.0 if there are no rentals
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
     * A rental is overdue when its backDate is null and the current date is after its dueDate.
     *
     * @param currentDate the date against which overdue status is checked
     * @return a list of customers with overdue rentals (unique, may be empty)
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        Set<Customer> overdueCustomers = new HashSet<>();

        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getDueDate() != null && rental.getDueDate().before(currentDate)) {
                overdueCustomers.add(rental.getCustomer());
            }
        }

        return new ArrayList<>(overdueCustomers);
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return average daily price; 0.0 if the store has no cars
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
     * Counts how many cars each customer has rented.
     *
     * @return a map where the key is a Customer and the value is the number of rentals for that customer;
     *         empty map if no rentals exist
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