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
     * @return true if the customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmounts() {
        for (Rental rental : rentals) {
            if (rental.getOverdueFee() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the total amount due for the customer.
     * @return the total amount due
     */
    public double getTotalAmountDue() {
        double total = 0;
        for (Rental rental : rentals) {
            total += rental.getTotalPrice();
        }
        return total;
    }

    /**
     * Adds a rental to the customer's rentals list.
     * @param rental the rental to add
     * @return true if the rental was added, false otherwise
     */
    public boolean addRental(Rental rental) {
        if (rentals.size() >= 20) {
            return false;
        }
        rentals.add(rental);
        return true;
    }
}

class Rental {
    private VideoTape videoTape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double baseRentalFee;
    private double overdueFee;

    public Rental() {
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

    public double getBaseRentalFee() {
        return baseRentalFee;
    }

    public void setBaseRentalFee(double baseRentalFee) {
        this.baseRentalFee = baseRentalFee;
    }

    public double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }

    /**
     * Calculates the overdue amount for the rental.
     * @param currentDate the current date
     */
    public void calculateOverdueAmount(LocalDate currentDate) {
        if (returnDate == null) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
            overdueFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        } else if (returnDate.isAfter(dueDate)) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            overdueFee = Math.round(overdueDays * 0.5 * 100.0) / 100.0;
        } else {
            overdueFee = 0;
        }
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price
     */
    public double getTotalPrice() {
        return Math.round((baseRentalFee + overdueFee) * 100.0) / 100.0;
    }
}

class VideoTape {
    private String barCodeId;
    private String title;
    private boolean isAvailable;

    public VideoTape() {
    }

    public String getBarCodeId() {
        return barCodeId;
    }

    public void setBarCodeId(String barCodeId) {
        this.barCodeId = barCodeId;
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
     * Checks if the video tape is available for rental on a given date.
     * @param currentDate the current date
     * @return true if the video tape is available, false otherwise
     */
    public boolean isAvailableForRental(LocalDate currentDate) {
        if (isAvailable) {
            return true;
        }
        for (Rental rental : getActiveRentals(currentDate)) {
            if (rental.getReturnDate() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the active rentals for the video tape.
     * @param currentDate the current date
     * @return a list of active rentals
     */
    private List<Rental> getActiveRentals(LocalDate currentDate) {
        List<Rental> activeRentals = new ArrayList<>();
        // In a real implementation, this would query the database or some data store
        // for rentals of this video tape that are active (i.e., have a null return date)
        return activeRentals;
    }
}

class RentalSystem {
    private List<Customer> customers;
    private List<VideoTape> videoTapes;

    public RentalSystem() {
        this.customers = new ArrayList<>();
        this.videoTapes = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    /**
     * Adds a video tape rental for a customer.
     * @param customerAccountNumber the customer's account number
     * @param videoTapeBarCodeId the video tape's bar code ID
     * @param rentalDate the rental date
     * @param dueDate the due date
     * @return true if the rental was added, false otherwise
     */
    public boolean addVideoTapeRental(String customerAccountNumber, String videoTapeBarCodeId, LocalDate rentalDate, LocalDate dueDate) {
        Customer customer = findCustomerByAccountNumber(customerAccountNumber);
        if (customer == null || customer.hasPastDueAmounts() || customer.getRentals().size() >= 20) {
            return false;
        }

        VideoTape videoTape = findVideoTapeByBarCodeId(videoTapeBarCodeId);
        if (videoTape == null || !videoTape.isAvailableForRental(rentalDate)) {
            return false;
        }

        Rental rental = new Rental();
        rental.setVideoTape(videoTape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setBaseRentalFee(java.time.temporal.ChronoUnit.DAYS.between(rentalDate, dueDate) * 1.0);
        rental.calculateOverdueAmount(rentalDate);

        if (customer.addRental(rental)) {
            videoTape.setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Returns a video tape.
     * @param videoTapeBarCodeId the video tape's bar code ID
     * @param returnDate the return date
     */
    public void returnVideoTape(String videoTapeBarCodeId, LocalDate returnDate) {
        VideoTape videoTape = findVideoTapeByBarCodeId(videoTapeBarCodeId);
        if (videoTape != null) {
            for (Customer customer : customers) {
                for (Rental rental : customer.getRentals()) {
                    if (rental.getVideoTape().getBarCodeId().equals(videoTapeBarCodeId) && rental.getReturnDate() == null) {
                        rental.setReturnDate(returnDate);
                        rental.calculateOverdueAmount(returnDate);
                        videoTape.setAvailable(true);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Gets the unreturned tapes for a customer.
     * @param customerAccountNumber the customer's account number
     * @return a list of unreturned tapes
     */
    public List<VideoTape> getUnreturnedTapes(String customerAccountNumber) {
        List<VideoTape> unreturnedTapes = new ArrayList<>();
        Customer customer = findCustomerByAccountNumber(customerAccountNumber);
        if (customer != null) {
            for (Rental rental : customer.getRentals()) {
                if (rental.getReturnDate() == null) {
                    unreturnedTapes.add(rental.getVideoTape());
                }
            }
        }
        return unreturnedTapes;
    }

    /**
     * Finds a customer by account number.
     * @param accountNumber the account number
     * @return the customer, or null if not found
     */
    private Customer findCustomerByAccountNumber(String accountNumber) {
        for (Customer customer : customers) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Finds a video tape by bar code ID.
     * @param barCodeId the bar code ID
     * @return the video tape, or null if not found
     */
    private VideoTape findVideoTapeByBarCodeId(String barCodeId) {
        for (VideoTape videoTape : videoTapes) {
            if (videoTape.getBarCodeId().equals(barCodeId)) {
                return videoTape;
            }
        }
        return null;
    }
}