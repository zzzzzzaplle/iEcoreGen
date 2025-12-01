// ==version1==
```
abstract class User {
    - ID : String
    - email : String
    - phoneNumber : String

}

class Driver extends User {
    - trips : List<Trip>
    + checkStopOverlap(trip1, trip2): boolean
    + canPostTrip(newTrip): boolean
}

class Customer extends User {
    - membershipPackage: MembershipPackage

    + computeMonthlyRewardPoints(currentMonth: String): int
    + bookTrip(Trip trip, int numberOfSeats) : void
    
}

class Trip {
    - departureStation : String
    - arrivalStation : String
    - numberOfSeats : integer
    - departureDate : Date
    - departureTime : String
    - arrivalTime : String
    - price : double
    - bookings: List<Booking>
    - stops : List<Stop>

    + calculateDiscountedPrice(customer: Customer, bookingTime: String): double
    + calculateMonthlyPoints(customer: Customer, currentMonth: String): int
    + bookSeats(int num) : boolean
    + confirmBooking(int seats) : boolean
    
    + getStopStations() : Set<String>
    + isTimeConflicting(String newDepartureTime, String newArrivalTime) : boolean
   
    
}

Driver *-- "*" Trip : trips

class Stop {
    - stopStation : String
    + getStopStation() : String
    + setStopStation(stopStation : String) : void
}

Trip *-- "*" Stop : stops

class Booking {
    - numberOfSeats : integer
    - customer: Customer
    - trip : Trip 
    - bookingDate : Date
    
    + isBookingEligible(): boolean
    + updateTripSeats() : void
    + overlapsWith(Trip trip) : boolean
    + getNumberOfSeats() : integer
    + setNumberOfSeats(numberOfSeats : integer) : void
}

Trip *-- "*" Booking : bookings

Booking --> "1" Customer : customer

class MembershipPackage {
    - awards : Award[]
    + hasAward( award : Award) : boolean
    + getAwards() : Award[]
    + setAwards(awards : Award[]) : void
    
}

enum Award {
    CASHBACK
    DISCOUNTS
    POINTS
}

Customer *-- "0..1" MembershipPackage : membership
```
// ==end==
