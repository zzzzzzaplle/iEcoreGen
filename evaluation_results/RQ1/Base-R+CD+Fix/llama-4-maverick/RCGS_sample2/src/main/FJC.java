import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;
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
     * @param rentalDate  the rental date
     * @param dueDate     the due date
     * @param backDate    the back date
     * @param totalPrice  the total price
     * @param leasingTerms the leasing terms
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
     * @param rentalDate the rental date to set
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
     * @param dueDate the due date to set
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
     * @param backDate the back date to set
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
     * @param totalPrice the total price to set
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
     * @param leasingTerms the leasing terms to set
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
     * @param car the car to set
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
     * @param customer the customer to set
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
        // Implementation of sending a notice
    }

    /**
     * Gets the customer associated with this overdue notice.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with this overdue notice.
     * 
     * @param customer the customer to set
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
     * Identifies available cars in the store, sorted by daily price in ascending order.
     * 
     * @return a list of available cars
     */
    public List<Car> identifyAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            boolean isRented = false;
            for (Rental rental : rentals) {
                if (rental.getCar().getPlate().equals(car.getPlate()) && rental.getBackDate() == null) {
                    isRented = true;
                    break;
                }
            }
            if (!isRented) {
                availableCars.add(car);
            }
        }
        availableCars.sort((c1, c2) -> Double.compare(c1.getDailyPrice(), c2.getDailyPrice()));
        return availableCars;
    }

    /**
     * Calculates the total revenue generated by all rentals in the store.
     * 
     * @return the total revenue
     */
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
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
    public List<Customer> findCustomersWithOverdueRentals(String currentDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = dateFormat.parse(currentDate);
        List<Customer> overdueCustomers = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getDueDate().before(current)) {
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
        double sum = 0.0;
        for (Car car : cars) {
            sum += car.getDailyPrice();
        }
        return sum / cars.size();
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
     * @param cars the list of cars to set
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
     * @param rentals the list of rentals to set
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
     * @param notices the list of overdue notices to set
     */
    public void setNotices(List<OverdueNotice> notices) {
        this.notices = notices;
    }
}