import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Store {
    private List<Car> cars;
    private List<Rental> rentals;
    private List<OverdueNotice> notices;

    public Store() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
        this.notices = new ArrayList<>();
    }

    public List<Car> identifyAvailableCars() {
        return this.cars.stream()
                .filter(car -> !this.rentals.stream().anyMatch(rental -> rental.getCar().getPlate().equals(car.getPlate())))
                .sorted((car1, car2) -> Double.compare(car1.getDailyPrice(), car2.getDailyPrice()))
                .collect(Collectors.toList());
    }

    public double calculateTotalRevenue() {
        return this.rentals.stream()
                .mapToDouble(Rental::getTotalPrice)
                .sum();
    }

    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        return this.rentals.stream()
                .filter(rental -> rental.getBackDate() == null && rental.getDueDate().before(currentDate))
                .map(Rental::getCustomer)
                .collect(Collectors.toList());
    }

    public double determineAverageDailyPrice() {
        if (this.cars.isEmpty()) {
            return 0.0;
        }
        return this.cars.stream()
                .mapToDouble(Car::getDailyPrice)
                .average()
                .orElse(0.0);
    }

    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> customerRentalCount = new HashMap<>();
        for (Rental rental : this.rentals) {
            customerRentalCount.put(rental.getCustomer(), customerRentalCount.getOrDefault(rental.getCustomer(), 0) + 1);
        }
        return customerRentalCount;
    }

    // Getters and Setters
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
}

class Car {
    private String plate;
    private String model;
    private double dailyPrice;

    public Car() {
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
    private String rentedCarPlate;

    public Customer() {
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

    public String getRentedCarPlate() {
        return rentedCarPlate;
    }

    public void setRentedCarPlate(String rentedCarPlate) {
        this.rentedCarPlate = rentedCarPlate;
    }
}

class Rental {
    private Date rentalDate;
    private Date dueDate;
    private Date backDate;
    private double totalPrice;
    private String leasingTerms;
    private Car car;
    private Customer customer;

    public Rental() {
    }

    // Getters and Setters
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

class OverdueNotice {
    private Customer customer;

    public OverdueNotice() {
    }

    public void sendNoticeTo(Customer customer) {
        this.customer = customer;
        // Logic to send overdue notice to the customer
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}