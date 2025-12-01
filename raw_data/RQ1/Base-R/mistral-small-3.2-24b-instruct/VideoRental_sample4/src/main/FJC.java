import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String id;
    private String name;
    private List<VideoTapeRental> rentals;

    public Customer() {
        this.rentals = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VideoTapeRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<VideoTapeRental> rentals) {
        this.rentals = rentals;
    }

    public List<VideoTape> getUnreturnedTapes() {
        List<VideoTape> unreturnedTapes = new ArrayList<>();
        for (VideoTapeRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                unreturnedTapes.add(rental.getVideoTape());
            }
        }
        return unreturnedTapes;
    }

    public boolean hasPastDueAmount() {
        for (VideoTapeRental rental : rentals) {
            if (rental.getReturnDate() == null) {
                LocalDate dueDate = rental.getDueDate();
                LocalDate currentDate = LocalDate.now();
                if (currentDate.isAfter(dueDate)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class VideoTape {
    private String id;
    private String title;
    private String director;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public VideoTape() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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
}

class VideoTapeRental {
    private VideoTape videoTape;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public VideoTapeRental() {
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

    public double calculateOverdueFee() {
        if (returnDate == null) {
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isBefore(dueDate)) {
                return 0.0;
            } else {
                long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentDate);
                return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
            }
        } else {
            if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
                return 0.0;
            } else {
                long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
                return Math.round(overdueDays * 0.5 * 100.0) / 100.0;
            }
        }
    }

    public double calculateTotalPrice() {
        if (returnDate == null) {
            LocalDate currentDate = LocalDate.now();
            long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, currentDate);
            return rentalDays * 1.0 + calculateOverdueFee();
        } else {
            long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(rentalDate, returnDate);
            return rentalDays * 1.0 + calculateOverdueFee();
        }
    }
}

class VideoInventory {
    private List<VideoTape> videoTapes;

    public VideoInventory() {
        this.videoTapes = new ArrayList<>();
    }

    public List<VideoTape> getVideoTapes() {
        return videoTapes;
    }

    public void setVideoTapes(List<VideoTape> videoTapes) {
        this.videoTapes = videoTapes;
    }

    public boolean isTapeAvailable(String tapeId, LocalDate currentDate) {
        for (VideoTape tape : videoTapes) {
            if (tape.getId().equals(tapeId)) {
                for (VideoTapeRental rental : tape.getRentals()) {
                    if (rental.getReturnDate() == null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class VideoRentalSystem {
    private List<Customer> customers;
    private VideoInventory videoInventory;

    public VideoRentalSystem() {
        this.customers = new ArrayList<>();
        this.videoInventory = new VideoInventory();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public VideoInventory getVideoInventory() {
        return videoInventory;
    }

    public void setVideoInventory(VideoInventory videoInventory) {
        this.videoInventory = videoInventory;
    }

    public boolean addVideoTapeRental(String customerId, String tapeId, LocalDate rentalDate, int rentalDays) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            return false;
        }

        if (customer.getRentals().size() >= 20) {
            return false;
        }

        if (customer.hasPastDueAmount()) {
            return false;
        }

        VideoTape tape = findTapeById(tapeId);
        if (tape == null) {
            return false;
        }

        if (!videoInventory.isTapeAvailable(tapeId, LocalDate.now())) {
            return false;
        }

        VideoTapeRental rental = new VideoTapeRental();
        rental.setVideoTape(tape);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(rentalDate.plusDays(rentalDays));
        customer.getRentals().add(rental);
        return true;
    }

    public double calculateTotalBill(String customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            return 0.0;
        }

        double total = 0.0;
        for (VideoTapeRental rental : customer.getRentals()) {
            total += rental.calculateTotalPrice();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public void returnTape(String tapeId, LocalDate returnDate) {
        for (Customer customer : customers) {
            for (VideoTapeRental rental : customer.getRentals()) {
                if (rental.getVideoTape().getId().equals(tapeId)) {
                    rental.setReturnDate(returnDate);
                    return;
                }
            }
        }
    }

    private Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    private VideoTape findTapeById(String tapeId) {
        for (VideoTape tape : videoInventory.getVideoTapes()) {
            if (tape.getId().equals(tapeId)) {
                return tape;
            }
        }
        return null;
    }
}