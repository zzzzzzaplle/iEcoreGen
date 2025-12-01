import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a customer with personal information and rental history.
 */
 class Customer {
    private String name;
    private String surname;
    private String address;
    private List<Rental> rentalHistory;

    /**
     * Constructor for Customer class.
     */
    public Customer() {
        this.rentalHistory = new ArrayList<>();
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

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(List<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    /**
     * Adds a rental record to the customer's rental history.
     * @param rental The rental record to add
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentalHistory.add(rental);
        }
    }

    /**
     * Gets the list of currently active rentals for this customer.
     * @return List of active Rental objects
     */
    public List<Rental> getActiveRentals() {
        return rentalHistory.stream()
                .filter(rental -> rental.getReturnDate() == null)
                .collect(Collectors.toList());
    }
}

/**
 * Represents a car available for rental in the gallery.
 */
 class Car {
    private String plate;
    private String model;
    private double dailyPrice;
    private boolean isAvailable;
    private List<Rental> rentalHistory;

    /**
     * Constructor for Car class.
     */
    public Car() {
        this.isAvailable = true;
        this.rentalHistory = new ArrayList<>();
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(List<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    /**
     * Adds a rental record to the car's rental history.
     * @param rental The rental record to add
     */
    public void addRental(Rental rental) {
        if (rental != null) {
            rentalHistory.add(rental);
        }
    }

    /**
     * Marks the car as rented by setting availability to false.
     */
    public void markAsRented() {
        this.isAvailable = false;
    }

    /**
     * Marks the car as available by setting availability to true.
     */
    public void markAsReturned() {
        this.isAvailable = true;
    }
}

/**
 * Represents a rental transaction between a customer and a car.
 */
 class Rental {
    private Customer customer;
    private Car car;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double totalCost;

    /**
     * Constructor for Rental class.
     */
    public Rental() {
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

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Checks if the rental is currently active (car not yet returned).
     * @return true if the car has not been returned, false otherwise
     */
    public boolean isActive() {
        return returnDate == null;
    }

    /**
     * Checks if the rental is overdue (past due date and not returned).
     * @return true if the rental is overdue, false otherwise
     */
    public boolean isOverdue() {
        return isActive() && LocalDate.now().isAfter(dueDate);
    }

    /**
     * Calculates the number of days the rental is overdue.
     * @return Number of days overdue, or 0 if not overdue
     */
    public int getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return (int) (LocalDate.now().toEpochDay() - dueDate.toEpochDay());
    }
}

/**
 * Manages the car rental system including cars, customers, and rentals.
 */
 class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    /**
     * Constructor for CarRentalSystem class.
     */
    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Adds a car to the system.
     * @param car The car to add
     */
    public void addCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    /**
     * Adds a customer to the system.
     * @param customer The customer to add
     */
    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
        }
    }

    /**
     * Adds a rental to the system and updates car availability.
     * @param rental The rental to add
     */
    public void addRental(Rental rental) {
        if (rental != null && rental.getCar() != null && rental.getCustomer() != null) {
            rentals.add(rental);
            rental.getCar().markAsRented();
            rental.getCustomer().addRental(rental);
            rental.getCar().addRental(rental);
        }
    }

    /**
     * Gets all available cars sorted by daily price in ascending order.
     * @return List of available cars sorted by price
     */
    public List<Car> getAvailableCarsSortedByPrice() {
        return cars.stream()
                .filter(Car::isAvailable)
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    /**
     * Gets all currently rented cars.
     * @return List of rented cars
     */
    public List<Car> getRentedCars() {
        return cars.stream()
                .filter(car -> !car.isAvailable())
                .collect(Collectors.toList());
    }

    /**
     * Gets all currently active rentals.
     * @return List of active Rental objects
     */
    public List<Rental> getActiveRentals() {
        return rentals.stream()
                .filter(Rental::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Searches for available cars by model name (case-insensitive).
     * @param model The model name to search for
     * @return List of available cars matching the model
     */
    public List<Car> searchAvailableCarsByModel(String model) {
        return cars.stream()
                .filter(car -> car.isAvailable() && 
                        car.getModel().toLowerCase().contains(model.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Processes a car return by updating the rental record and car availability.
     * @param rental The rental to complete
     * @param returnDate The date when the car is returned
     */
    public void returnCar(Rental rental, LocalDate returnDate) {
        if (rental != null && returnDate != null && rental.isActive()) {
            rental.setReturnDate(returnDate);
            rental.getCar().markAsReturned();
            
            // Calculate final cost based on actual rental period
            long daysRented = returnDate.toEpochDay() - rental.getRentalDate().toEpochDay();
            rental.setTotalCost(daysRented * rental.getCar().getDailyPrice());
        }
    }

    /**
     * Generates a rental report showing all rentals with details.
     * @return List of all rental records
     */
    public List<Rental> generateRentalReport() {
        return new ArrayList<>(rentals);
    }

    /**
     * Generates overdue notices for rentals that are past their due date.
     * @return List of overdue Rental objects
     */
    public List<Rental> generateOverdueNotices() {
        return rentals.stream()
                .filter(Rental::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Finds a customer by name and surname.
     * @param name The customer's first name
     * @param surname The customer's last name
     * @return The customer if found, null otherwise
     */
    public Customer findCustomer(String name, String surname) {
        return customers.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name) && 
                        c.getSurname().equalsIgnoreCase(surname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a car by its license plate.
     * @param plate The license plate to search for
     * @return The car if found, null otherwise
     */
    public Car findCarByPlate(String plate) {
        return cars.stream()
                .filter(c -> c.getPlate().equalsIgnoreCase(plate))
                .findFirst()
                .orElse(null);
    }
}