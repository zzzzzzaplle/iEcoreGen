// ==version1==
```
class Customer {
    - id : String
    - rentals : List<VideoRental>

    + addVedioTapeRental(tape: Tape,currentDate :Date) : boolean
    + getUnreturnedTapes() : List<Tape>
    + calculateTotalPastDueAmount(currentDate: Date) : double
}

class Tape {
    - id : String
    - videoInformation : String

    + isAvailable(currentDate: Date) : boolean
}

class VideoRental {
    - dueDate : Date
    - returnDate : Date
    - ownedPastDueAmount : double
    - tape : Tape

    + calculateOwedPastDueAmount(currentDate: Date) : double
    + calculateBaseRentalFee(currentDate: Date) : double
}

class RentalTransaction {
    - rentalDate : Date
    - totalPrice : double
    - rentals : List<VideoRental>
    - customer : Customer

    + calculateTotalPrice(rentalDate: Date, currentDate: Date) : double
}

Customer *-- "0..20" VideoRental : rentals
VideoRental --> "1" Tape : tape
RentalTransaction -- "*" VideoRental : rentals
RentalTransaction --> "1" Customer : customer
```
// ==end==
