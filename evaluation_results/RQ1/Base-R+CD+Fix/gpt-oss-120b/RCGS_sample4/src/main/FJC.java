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

    /** Unparameterized constructor */
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

    /** Equality based on unique plate */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return plate != null ? plate.equals(car.plate) : car.plate == null;
    }

    @Override
    public int hashCode() {
        return plate != null ? plate.hashCode() : 0;
    }
}

/**
 * Represents a customer.
 */
 class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate; // the plate of the car currently rented (if any)

    /** Unparameterized constructor */
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

    /** Equality based on name, surname and address (assumed unique for this context) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (surname != null ? !surname.equals(customer.surname) : customer.surname != null) return false;
        return address != null ? address.equals(customer.address) : customer.address == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

/**
 * Represents a rental transaction.
 */
 class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate;          // null if the car has not yet been returned
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /** Unparameterized constructor */
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

    /** Unparameterized constructor */
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
     * Sends a notice to the given customer.
     *
     * @param customer the customer that will receive the notice
     */
    public void sendNoticeTo(Customer customer) {
        // In a real system this could send an email or a printed letter.
        // For this implementation we simply output a message.
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }
}

/**
 * Central class that manages cars, rentals and notices.
 */
 class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /** Unparameterized constructor */
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
     * Identifies all cars that are currently not rented.
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         Returns an empty list if no cars are available.
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
     * @return the sum of totalPrice of every rental. If there are no rentals, returns 0.0.
     */
    public double calculateTotalRevenue() {
        double revenue = 0.0;
        for (Rental rental : rentals) {
            revenue += rental.getTotalPrice();
        }
        return revenue;
    }

    /**
     * Finds all customers that have overdue rentals.
     * A rental is overdue when its backDate is null and the current date is later than its dueDate.
     *
     * @param currentDate the date against which overdue status is evaluated
     * @return a list of customers with at least one overdue rental.
     *         Returns an empty list if there are no overdue customers.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        Set<Customer> overdueCustomers = new HashSet<>();

        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }

        return new ArrayList<>(overdueCustomers);
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the average daily price, or 0.0 if there are no cars.
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
     * @return a map where the key is a Customer and the value is the number of rentals
     *         associated with that customer. Returns an empty map if no rentals exist.
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> countMap = new HashMap<>();

        for (Rental rental : rentals) {
            Customer cust = rental.getCustomer();
            countMap.put(cust, countMap.getOrDefault(cust, 0) + 1);
        }

        return countMap;
    }

    /**
     * Helper method to add a new car to the store.
     *
     * @param car the car to add
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    /**
     * Helper method to register a new rental.
     *
     * @param rental the rental to add
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentals.add(rental);
            // Update the customer's rentedCarPlate for quick reference
            if (rental.getCustomer() != null && rental.getCar() != null) {
                rental.getCustomer().setRentedCarPlate(rental.getCar().getPlate());
            }
        }
    }

    /**
     * Helper method to add an overdue notice.
     *
     * @param notice the notice to add
     */
    public void addNotice(OverdueNotice notice) {
        if (notice != null) {
            notices.add(notice);
        }
    }
}