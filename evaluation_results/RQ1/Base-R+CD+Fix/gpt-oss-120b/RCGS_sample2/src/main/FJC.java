import java.util.*;
import java.util.stream.*;
import java.text.SimpleDateFormat;

/**
 * Represents a car that can be rented from the gallery.
 */
 class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /** Default constructor */
    public Car() { }

    /** Getter for plate */
    public String getPlate() {
        return plate;
    }

    /** Setter for plate */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /** Getter for model */
    public String getModel() {
        return model;
    }

    /** Setter for model */
    public void setModel(String model) {
        this.model = model;
    }

    /** Getter for dailyPrice */
    public double getDailyPrice() {
        return dailyPrice;
    }

    /** Setter for dailyPrice */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    /** Equality based on unique licence plate */
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
    private String rentedCarPlate;   // the plate of the car currently rented (if any)

    /** Default constructor */
    public Customer() { }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for surname */
    public String getSurname() {
        return surname;
    }

    /** Setter for surname */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** Getter for address */
    public String getAddress() {
        return address;
    }

    /** Setter for address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for rentedCarPlate */
    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    /** Setter for rentedCarPlate */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }

    /** Equality based on name, surname and address (assumed unique for this domain) */
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
    private Date backDate;          // null if the car has not been returned yet
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /** Default constructor */
    public Rental() { }

    /** Getter for rentalDate */
    public Date getRentalDate() {
        return rentalDate;
    }

    /** Setter for rentalDate */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /** Getter for dueDate */
    public Date getDueDate() {
        return dueDate;
    }

    /** Setter for dueDate */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /** Getter for backDate */
    public Date getBackDate() {
        return backDate;
    }

    /** Setter for backDate */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /** Getter for totalPrice */
    public double getTotalPrice() {
        return totalPrice;
    }

    /** Setter for totalPrice */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** Getter for leasingTerms */
    public String getLeasingTerms() {
        return leasingTerms;
    }

    /** Setter for leasingTerms */
    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
    }

    /** Getter for car */
    public Car getCar() {
        return car;
    }

    /** Setter for car */
    public void setCar(Car car) {
        this.car = car;
    }

    /** Getter for customer */
    public Customer getCustomer() {
        return customer;
    }

    /** Setter for customer */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return "Rental{" +
                "rentalDate=" + (rentalDate != null ? fmt.format(rentalDate) : null) +
                ", dueDate=" + (dueDate != null ? fmt.format(dueDate) : null) +
                ", backDate=" + (backDate != null ? fmt.format(backDate) : null) +
                ", totalPrice=" + totalPrice +
                ", leasingTerms='" + leasingTerms + '\'' +
                ", car=" + car +
                ", customer=" + customer +
                '}';
    }
}

/**
 * Represents an overdue notice sent to a customer.
 */
 class OverdueNotice {
    private Customer customer;

    /** Default constructor */
    public OverdueNotice() { }

    /** Getter for customer */
    public Customer getCustomer() {
        return customer;
    }

    /** Setter for customer */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends a notice to the given customer. In a real system this would trigger
     * an email or SMS; here we simply print a message.
     *
     * @param customer the customer to be notified
     */
    public void sendNoticeTo(Customer customer) {
        System.out.println("Overdue notice sent to: " + customer.getName()
                + " " + customer.getSurname() + " (Address: " + customer.getAddress() + ")");
    }
}

/**
 * Central class that stores cars, rentals and handles business operations.
 */
 class Store {
    private List<Car> cars = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();
    private List<OverdueNotice> notices = new ArrayList<>();

    /** Default constructor */
    public Store() { }

    /** Getter for cars */
    public List<Car> getCars() {
        return cars;
    }

    /** Setter for cars */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /** Getter for rentals */
    public List<Rental> getRentals() {
        return rentals;
    }

    /** Setter for rentals */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /** Getter for notices */
    public List<OverdueNotice> getNotices() {
        return notices;
    }

    /** Setter for notices */
    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }

    /**
     * Identifies all cars that are currently not rented.
     * <p>
     * A car is considered unavailable if there exists a rental for that car
     * whose {@code backDate} is {@code null} (i.e., the car has not been returned yet).
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         Returns an empty list if no cars are available.
     */
    public List<Car> identifyAvailableCars() {
        // Build a set of plates of cars that are currently rented
        Set<String> rentedPlates = rentals.stream()
                .filter(r -> r.getBackDate() == null && r.getCar() != null)
                .map(r -> r.getCar().getPlate())
                .collect(Collectors.toSet());

        // Filter cars that are not in the rented set and sort them
        return cars.stream()
                .filter(car -> !rentedPlates.contains(car.getPlate()))
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total revenue generated by all rentals.
     *
     * @return the sum of {@code totalPrice} of each rental. Returns 0.0 if no rentals exist.
     */
    public double calculateTotalRevenue() {
        return rentals.stream()
                .mapToDouble(Rental::getTotalPrice)
                .sum();
    }

    /**
     * Finds customers with overdue rentals.
     * <p>
     * A rental is overdue when {@code backDate} is {@code null} and the current
     * date is after the rental's {@code dueDate}.
     *
     * @param currentDate the date against which overdue status is evaluated.
     * @return a list of customers that have at least one overdue rental.
     *         Returns an empty list if none are overdue.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        return rentals.stream()
                .filter(r -> r.getBackDate() == null && r.getDueDate() != null && r.getDueDate().before(currentDate))
                .map(Rental::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the arithmetic mean of the daily prices. Returns 0.0 if there are no cars.
     */
    public double determineAverageDailyPrice() {
        return cars.isEmpty() ? 0.0 :
                cars.stream()
                        .mapToDouble(Car::getDailyPrice)
                        .average()
                        .orElse(0.0);
    }

    /**
     * Counts how many cars each customer has rented.
     *
     * @return a map where the key is a {@link Customer} and the value is the number of rentals
     *         associated with that customer. Returns an empty map if no rentals exist.
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        return rentals.stream()
                .collect(Collectors.toMap(
                        Rental::getCustomer,
                        r -> 1,
                        Integer::sum
                ));
    }

    /**
     * Helper method to add a new car to the store.
     *
     * @param car the car to be added
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    /**
     * Helper method to register a new rental.
     *
     * @param rental the rental to be recorded
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentals.add(rental);
            // Update the customer's rented car plate (for quick lookup)
            if (rental.getCustomer() != null && rental.getCar() != null) {
                rental.getCustomer().setRentedCarPlate(rental.getCar().getPlate());
            }
        }
    }

    /**
     * Helper method to record a returned car.
     *
     * @param rental   the rental being closed
     * @param backDate the actual return date
     */
    public void returnCar(Rental rental, Date backDate) {
        if (rental != null) {
            rental.setBackDate(backDate);
            // Clear the rentedCarPlate field from the customer
            if (rental.getCustomer() != null) {
                rental.getCustomer().setRentedCarPlate(null);
            }
        }
    }

    /**
     * Generates overdue notices for all customers with overdue rentals
     * and stores them in the internal notice list.
     *
     * @param currentDate the date used to evaluate overdue status
     */
    public void generateOverdueNotices(Date currentDate) {
        List<Customer> overdueCustomers = findCustomersWithOverdueRentals(currentDate);
        for (Customer c : overdueCustomers) {
            OverdueNotice notice = new OverdueNotice();
            notice.setCustomer(c);
            notice.sendNoticeTo(c);
            notices.add(notice);
        }
    }
}