import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String accountNumber;
    private String idCardBarcode;
    private List<VideoRental> rentals;

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

    public List<VideoRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<VideoRental> rentals) {
        this.rentals = rentals;
    }

    /**
     * Checks if the customer can rent more videos.
     * @return true if the customer can rent more videos, false otherwise
     */
    public boolean canRentMoreVideos() {
        return rentals.stream().filter(rental -> rental.getReturnDate() == null).count() < 20;
    }

    /**
     * Checks if the customer has any past-due amounts.
     * @return true if the customer has past-due amounts, false otherwise
     */
    public boolean hasPastDueAmounts() {
        return rentals.stream().anyMatch(rental -> rental.calculateOverdueAmount() > 0);
    }

    /**
     * Gets the total amount due for all unreturned tapes.
     * @return the total amount due
     */
    public double getTotalAmountDue() {
        return rentals.stream().filter(rental -> rental.getReturnDate() == null)
                .mapToDouble(VideoRental::getTotalPrice).sum();
    }

    /**
     * Lists unreturned tapes for the customer.
     * @return a list of unreturned tape IDs
     */
    public List<String> listUnreturnedTapes() {
        List<String> unreturnedTapes = new ArrayList<>();
        for (VideoRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape().getBarcodeId());
            }
        }
        return unreturnedTapes;
    }
}

class VideoTape {
    private String barcodeId;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class VideoRental {
    private VideoTape videoTape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double baseRentalFee;
    private double overdueFee;

    public VideoRental() {
        this.rentalDate = LocalDate.now();
        this.dueDate = rentalDate.plusDays(7); // Assuming a 7-day rental period
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
     * Calculates the overdue amount for the video rental.
     * @return the overdue amount rounded to two decimal places
     */
    public double calculateOverdueAmount() {
        if (returnDate != null && returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
            return 0.0;
        } else if (returnDate != null) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            double overdueFee = overdueDays * 0.5;
            return Math.round(overdueFee * 100.0) / 100.0;
        } else {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            double overdueFee = overdueDays * 0.5;
            return Math.round(overdueFee * 100.0) / 100.0;
        }
    }

    /**
     * Checks the availability of the tape for a given date.
     * @param currentDate the date to check availability
     * @return true if the tape is available, false otherwise
     */
    public boolean checkTapeAvailability(LocalDate currentDate) {
        return returnDate != null || currentDate.isAfter(dueDate);
    }

    /**
     * Adds a video tape rental.
     * @param customer the customer renting the tape
     * @param videoTape the tape to be rented
     * @return true if the rental is processed, false otherwise
     */
    public boolean addVideoTapeRental(Customer customer, VideoTape videoTape) {
        if (!customer.canRentMoreVideos() || customer.hasPastDueAmounts() || !videoTape.isAvailable()) {
            return false;
        }
        this.videoTape = videoTape;
        videoTape.setAvailable(false);
        customer.getRentals().add(this);
        calculateBaseRentalFee();
        return true;
    }

    /**
     * Calculates the base rental fee.
     */
    private void calculateBaseRentalFee() {
        long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, dueDate);
        if (returnDate != null) {
            rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, returnDate);
        } else {
            rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, LocalDate.now());
        }
        baseRentalFee = rentalDays * 1.0;
    }

    /**
     * Calculates the total price for the rental.
     * @return the total price rounded to two decimal places
     */
    public double getTotalPrice() {
        calculateBaseRentalFee();
        overdueFee = calculateOverdueAmount();
        double totalPrice = baseRentalFee + overdueFee;
        return Math.round(totalPrice * 100.0) / 100.0;
    }
}