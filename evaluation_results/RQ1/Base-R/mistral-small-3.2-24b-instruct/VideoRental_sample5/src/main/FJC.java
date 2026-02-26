import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String accountNumber;
    private String idCardBarcode;
    private List<Rental> rentals;

    public Customer() {
        this.rentals = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIdCardBarcode() {
        return idCardBarcode;
    }

    public void setIdCardBarcode(String idCardBarcode) {
        this.idCardBarcode = idCardBarcode;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks if the customer has any past-due amounts.
     * @return true if the customer has past-due amounts, false otherwise.
     */
    public boolean hasPastDueAmounts() {
        for (Rental rental : rentals) {
            if (rental.getOverdueAmount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the customer has fewer than 20 rentals.
     * @return true if the customer has fewer than 20 rentals, false otherwise.
     */
    public boolean hasFewerThan20Rentals() {
        return rentals.size() < 20;
    }

    /**
     * Adds a rental to the customer's list of rentals.
     * @param rental the rental to add.
     */
    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    /**
     * Lists unreturned tapes for the customer.
     * @return a list of unreturned tape IDs.
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getBarcodeId());
            }
        }
        return unreturnedTapes;
    }
}

class Rental {
    private VideoTape videoTape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double overdueAmount;

    public Rental() {
        this.overdueAmount = 0.0;
    }

    public VideoTape getVideoTape() {
        return videoTape;
    }

    public void setVideoTape(VideoTape videoTape) {
        this.videoTape = videoTape;
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

    public double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    /**
     * Calculates the past-due amount for the rental.
     * @param currentDate the current date used for calculation if the tape has not been returned.
     */
    public void calculatePastDueAmount(LocalDate currentDate) {
        if (returnDate == null) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
            overdueAmount = overdueDays * 0.5;
        } else if (returnDate.isAfter(dueDate)) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            overdueAmount = overdueDays * 0.5;
        } else {
            overdueAmount = 0.0;
        }
        overdueAmount = Math.round(overdueAmount * 100.0) / 100.0;
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price rounded to two decimal places.
     */
    public double calculateTotalPrice() {
        long rentalDays;
        if (returnDate == null) {
            rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, LocalDate.now());
        } else {
            rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, returnDate);
        }
        double baseRentalFee = rentalDays * 1.0;
        return Math.round((baseRentalFee + overdueAmount) * 100.0) / 100.0;
    }
}

class VideoTape {
    private String barcodeId;
    private String title;
    private boolean isAvailable;

    public VideoTape() {
        this.isAvailable = true;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Checks the availability of the tape for a given date.
     * @param currentDate the current date to check availability.
     * @return true if the tape is available, false otherwise.
     */
    public boolean checkAvailability(LocalDate currentDate) {
        return isAvailable;
    }
}

class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoInventory;

    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.videoInventory = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<VideoTape> getVideoInventory() {
        return videoInventory;
    }

    public void setVideoInventory(List<VideoTape> videoInventory) {
        this.videoInventory = videoInventory;
    }

    /**
     * Adds a video tape rental for a customer.
     * @param customer the customer renting the tape.
     * @param videoTape the video tape being rented.
     * @param rentalDate the rental date.
     * @param dueDate the due date.
     * @return true if the rental is processed, false otherwise.
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape videoTape, LocalDate rentalDate, LocalDate dueDate) {
        if (!customer.hasFewerThan20Rentals() || customer.hasPastDueAmounts() || !videoTape.isAvailable()) {
            return false;
        }
        Rental rental = new Rental();
        rental.setVideoTape(videoTape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        customer.addRental(rental);
        videoTape.setAvailable(false);
        return true;
    }

    /**
     * Calculates the total price for a customer's rental transaction.
     * @param customer the customer whose rental transaction total price is to be calculated.
     * @return the total price rounded to two decimal places.
     */
    public double calculateTotalPriceForTransaction(Customer customer) {
        double totalPrice = 0.0;
        for (Rental rental : customer.getRentals()) {
            totalPrice += rental.calculateTotalPrice();
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}