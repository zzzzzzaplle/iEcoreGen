import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Car {
    private String plate;
    private String model;
    private double dailyPrice;

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
}

class Rental {
    private Customer customer;
    private Car car;
    private LocalDate rentDate;
    private LocalDate dueDate;
    private LocalDate backDate;

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
}

class Customer {
    private String name;
    private String surname;
    private String address;

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

class CarStore {
    private List<Car> cars;
    private List<Rental> rentals;

    public CarStore() {
        this.cars = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public List<Car> getAvailableCars() {
        return cars.stream()
                .filter(car -> rentals.stream().noneMatch(rental -> rental.getCar().equals(car) && rental.getBackDate() == null))
                .sorted(Comparator.comparingDouble(Car::getDailyPrice))
                .collect(Collectors.toList());
    }

    public double calculateTotalRevenue() {
        return rentals.stream()
                .filter(rental -> rental.getBackDate() != null)
                .mapToDouble(rental -> {
                    long daysRented = java.time.temporal.ChronoUnit.DAYS.between(rental.getRentDate(), rental.getBackDate());
                    return daysRented * rental.getCar().getDailyPrice();
                })
                .sum();
    }

    public List<Customer> listOverdueCustomers() {
        LocalDate currentDate = LocalDate.now();
        return rentals.stream()
                .filter(rental -> rental.getBackDate() == null && rental.getDueDate().isBefore(currentDate))
                .map(Rental::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }

    public double calculateAverageDailyPrice() {
        if (cars.isEmpty()) {
            return 0.0;
        }
        double sum = cars.stream().mapToDouble(Car::getDailyPrice).sum();
        return sum / cars.size();
    }

    public Map<Customer, Long> countCarsRentedPerCustomer() {
        return rentals.stream()
                .collect(Collectors.groupingBy(Rental::getCustomer, Collectors.counting()));
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public void returnCar(Rental rental) {
        rental.setBackDate(LocalDate.now());
    }
}