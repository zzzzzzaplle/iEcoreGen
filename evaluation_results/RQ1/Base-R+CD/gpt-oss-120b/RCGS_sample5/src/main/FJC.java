package com.gallery.rental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a car in the gallery.
 */
 class Car {

    /** License plate of the car */
    private String plate;
    /** Model name of the car */
    private String model;
    /** Daily rental price */
    private double dailyPrice;

    /** No‑argument constructor required by the task */
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
 * Represents a customer of the rental store.
 */
 class Customer {

    /** First name */
    private String name;
    /** Last name */
    private String surname;
    /** Postal address */
    private String address;
    /** Plate of the car currently rented (null if none) */
    private String rentedCarPlate;

    /** No‑argument constructor required by the task */
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

    /** Date when the rental started */
    private Date rentalDate;
    /** Date when the car must be returned */
    private Date dueDate;
    /** Actual return date (null if not yet returned) */
    private Date backDate;
    /** Total price for the rental (could be computed elsewhere) */
    private double totalPrice;
    /** Textual description of the leasing terms */
    private String leasingTerms;

    /** The car that is rented */
    private Car car;
    /** The customer who rents the car */
    private Customer customer;

    /** No‑argument constructor required by the task */
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

    @Override
    public String toString() {
        return "Rental{" +
                "rentalDate=" + rentalDate +
                ", dueDate=" + dueDate +
                ", backDate=" + backDate +
                ", totalPrice=" + totalPrice +
                ", leasingTerms='" + leasingTerms + '\'' +
                ", car=" + car +
                ", customer=" + customer +
                '}';
    }
}

/**
 * Represents a notice sent to a customer when a rental is overdue.
 */
 class OverdueNotice {

    /** Customer to which the notice refers */
    private Customer customer;

    /** No‑argument constructor required by the task */
    public OverdueNotice() {
    }

    /** Getters and setters */

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sends the overdue notice to the supplied customer.
     * In a real system this could send an email or a physical letter.
     *
     * @param customer the customer that will receive the notice
     */
    public void sendNoticeTo(Customer customer) {
        // Placeholder implementation – simply prints to console
        System.out.println("Overdue notice sent to: " + customer.getName() + " " + customer.getSurname());
    }

    @Override
    public String toString() {
        return "OverdueNotice{" +
                "customer=" + customer +
                '}';
    }
}

/**
 * Central class that stores cars, rentals and overdue notices.
 * Provides business‑logic methods required by the functional specification.
 */
 class Store {

    /** All cars that belong to the store */
    private List<Car> cars = new ArrayList<>();
    /** All rentals that have been performed */
    private List<Rental> rentals = new ArrayList<>();
    /** All overdue notices that have been generated */
    private List<OverdueNotice> notices = new ArrayList<>();

    /** No‑argument constructor required by the task */
    public Store() {
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
     * Identifies all cars that are currently available for rent.
     * A car is considered unavailable when there exists a rental
     * whose {@code backDate} is {@code null} (i.e., the car has not been returned).
     *
     * @return a list of available cars sorted by daily price in ascending order.
     *         If no cars are available, an empty list is returned.
     */
    public List<Car> identifyAvailableCars() {
        // Determine plates of cars currently rented
        List<String> rentedPlates = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getCar() != null) {
                rentedPlates.add(rental.getCar().getPlate());
            }
        }

        // Filter cars that are not in the rented list
        List<Car> available = new ArrayList<>();
        for (Car car : cars) {
            if (!rentedPlates.contains(car.getPlate())) {
                available.add(car);
            }
        }

        // Sort by daily price (ascending)
        available.sort(Comparator.comparingDouble(Car::getDailyPrice));
        return available;
    }

    /**
     * Calculates the total revenue generated by all rentals stored in the system.
     *
     * @return the sum of {@code totalPrice} of every {@link Rental}.
     *         Returns {@code 0.0} if there are no rentals.
     */
    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Rental rental : rentals) {
            sum += rental.getTotalPrice();
        }
        return sum;
    }

    /**
     * Retrieves a list of customers that have overdue rentals.
     * A rental is overdue when {@code backDate} is {@code null} and the supplied
     * {@code currentDate} is after the rental's {@code dueDate}.
     *
     * @param currentDate the date against which overdue status is evaluated
     * @return a list of customers with overdue rentals; the list may be empty
     *         if no overdue rentals exist.
     */
    public List<Customer> findCustomersWithOverdueRentals(Date currentDate) {
        List<Customer> overdueCustomers = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getBackDate() == null && rental.getDueDate() != null
                    && currentDate.after(rental.getDueDate())) {
                overdueCustomers.add(rental.getCustomer());
            }
        }
        return overdueCustomers;
    }

    /**
     * Determines the average daily price of all cars stored in the gallery.
     *
     * @return the arithmetic mean of the {@code dailyPrice} of every car.
     *         Returns {@code 0.0} when the store has no cars.
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
     * @return a map where the key is a {@link Customer} and the value is the number
     *         of rentals associated with that customer. Returns an empty map when
     *         no rentals exist.
     */
    public Map<Customer, Integer> countCarsRentedPerCustomer() {
        Map<Customer, Integer> counts = new HashMap<>();
        for (Rental rental : rentals) {
            Customer cust = rental.getCustomer();
            counts.put(cust, counts.getOrDefault(cust, 0) + 1);
        }
        return counts;
    }

    /**
     * Helper method used by the UI or other services to generate overdue notices
     * for all customers that currently have overdue rentals.
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