import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;

/**
 * Represents a car in the store's gallery.
 */
class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    /**
     * Unparameterized constructor for Car.
     */
    public Car() {}

    /**
     * Constructs a Car object with the given plate, model, and daily price.
     * 
     * @param plate     the plate number of the car
     * @param model     the model of the car
     * @param dailyPrice the daily price of the car
     */
    public Car(String plate, String model, double dailyPrice) {
        this.plate = plate;
        this.model = model;
        this.dailyPrice = dailyPrice;
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
     * @param plate the new plate number
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
     * @param model the new model
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
     * @param dailyPrice the new daily price
     */
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}

/**
 * Represents a customer who rents a car.
 */
class Customer {
    private String name;
    private String surname;
    private String address;
    private String rentedCarPlate;

    /**
     * Unparameterized constructor for Customer.
     */
    public Customer() {}

    /**
     * Constructs a Customer object with the given name, surname, address, and rented car plate.
     * 
     * @param name           the name of the customer
     * @param surname        the surname of the customer
     * @param address        the address of the customer
     * @param rentedCarPlate the plate number of the rented car
     */
    public Customer(String name, String surname, String address, String rentedCarPlate) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.rentedCarPlate = rentedCarPlate;
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
     * @param name the new name
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
     * @param surname the new surname
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
     * @param address the new address
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
     * @param rentedCarPlate the new rented car plate
     */
    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Customer customer = (Customer) obj;
        return name.equals(customer.name) && surname.equals(customer.surname) && address.equals(customer.address);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }
}

/**
 * Represents a rental transaction.
 */
class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate;
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    /**
     * Unparameterized constructor for Rental.
     */
    public Rental() {}

    /**
     * Constructs a Rental object with the given rental date, due date, back date, total price, leasing terms, car, and customer.
     * 
     * @param rentalDate  the date the car was rented
     * @param dueDate     the date the car is due back
     * @param backDate    the date the car was returned
     * @param totalPrice  the total price of the rental
     * @param leasingTerms the terms of the lease
     * @param car         the car being rented
     * @param customer    the customer renting the car
     */
    public Rental(Date rentalDate, Date dueDate, Date backDate, double totalPrice, String leasingTerms, Car car, Customer customer) {
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.backDate = backDate;
        this.totalPrice = totalPrice;
        this.leasingTerms = leasingTerms;
        this.car = car;
        this.customer = customer;
    }

    /**
     * Gets the rental date.
     * 
     * @return the rental date
     */
    public Date getRentalDate() {
        return rentalDate;
    }

    /**
     * Sets the rental date.
     * 
     * @param rentalDate the new rental date
     */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * Gets the due date.
     * 
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * 
     * @param dueDate the new due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the back date.
     * 
     * @return the back date
     */
    public Date getBackDate() {
        return backDate;
    }

    /**
     * Sets the back date.
     * 
     * @param backDate the new back date
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /**
     * Gets the total price.
     * 
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price.
     * 
     * @param totalPrice the new total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the leasing terms.
     * 
     * @return the leasing terms
     */
    public String getLeasingTerms() {
        return leasingTerms;
    }

    /**
     * Sets the leasing terms.
     * 
     * @param leasingTerms the new leasing terms
     */
    public void setLeasingTerms(String leasingTerms) {
        this.leasingTerms = leasingTerms;
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
     * @param car the new car
     */
    public void setCar(Car car) {
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
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Represents an overdue notice sent to a customer.
 */
class OverdueNotice {
    private Customer customer;

    /**
     * Unparameterized constructor for OverdueNotice.
     */
    public OverdueNotice() {}

    /**
     * Constructs an OverdueNotice object with the given customer.
     * 
     * @param customer the customer to send the notice to
     */
    public OverdueNotice(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends a notice to the customer.
     * 
     * @param customer the customer to send the notice to
     */
    public void sendNoticeTo(Customer customer) {
        System.out.println("Sending overdue notice to " + customer.getName() + " " + customer.getSurname());
    }

    /**
     * Gets the customer associated with this notice.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this notice.
     * 
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

/**
 * Represents a store that manages cars, rentals, and overdue notices.
 */
class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    /**
     * Unparameterized constructor for Store.
     */
    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    /**
     * Constructs a Store object with the given list of cars, rentals, and notices.
     * 
     * @param cars    the list of cars
     * @param rentals the list of rentals
     * @param notices the list of overdue notices
     */
    public Store(List<Car> cars, List<Rental> rentals, List<OverdueNotice> notices) {
        this.cars = cars;
        this.rentals = rentals;
        this.notices = notices;
    }

    /**
     * Identifies available cars in the store, sorted by daily price in ascending order.
     * 
     * @return a list of available cars
     */
    public List<Car> identifyAvailableCars() {
        List<Car> availableCars = new ArrayList<>(cars);
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null) {
                availableCars.remove(rental.getCar());
            }
        }
        availableCars.sort((car1, car2) -> Double.compare(car1.getDailyPrice(), car2.getDailyPrice()));
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * 
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Rental rental : rentals) {
            totalRevenue += rental.getTotalPrice();
        }
        return totalRevenue;
    }

    /**
     * Finds customers with overdue rentals based on the given current date.
     * 
     * @param currentDate the current date
     * @return a list of customers with overdue rentals
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        List<Customer> overdueCustomers = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of cars in the store.
     * 
     * @return the average daily price
     */
    public double determineAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double totalDailyPrice = 0;
        for (Car car : cars) {
            totalDailyPrice += car.getDailyPrice();
        }
        return totalDailyPrice / cars.size();
    }

    /**
     * Counts the number of cars rented per customer.
     * 
     * @return a map of customers and their respective rental counts
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        for (Rental rental : rentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        return rentalCounts;
    }

    /**
     * Gets the list of cars in the store.
     * 
     * @return the list of cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Sets the list of cars in the store.
     * 
     * @param cars the new list of cars
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    /**
     * Gets the list of rentals in the store.
     * 
     * @return the list of rentals
     */
    public List<Rental> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals in the store.
     * 
     * @param rentals the new list of rentals
     */
    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Gets the list of overdue notices in the store.
     * 
     * @return the list of overdue notices
     */
    public List<OverdueNotice> getNotices() {
        return notices;
    }

    /**
     * Sets the list of overdue notices in the store.
     * 
     * @param notices the new list of overdue notices
     */
    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }
}