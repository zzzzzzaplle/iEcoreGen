// ==version1==
```
class Airline {
  - flights : List<Flight>  

  + addFlight(f:Flight) : void
  + removeFlight(f:Flight) : void 

  + publishFlight(f:Flight, now:Date)              : boolean
  + closeFlight(flightId:String, now:Date)         : boolean
  + searchFlights(origin:String, dest:String, date:Date) : List<Flight>
}

class Flight {
    - id : String
    - openForBooking : boolean
    - departureTime : Date
    - arrivalTime : Date
    - departureAirport : Airport
    - arrialAirport : Airport
    - stopovers : List<Stopover>
    - reservations : List<Reservation>  

    + addStopover(stop:Stopover, now:Date) : boolean
    + removeStopover(stop:Stopover, now:Date) : boolean
    + getConfirmedReservations() : List<Reservation>
}

class Stopover {
  - departureTime : Date
  - arrivalTime : Date
  - airport : Airport

}

Airline *-- "*" Flight : flights
Flight *-- "0..*" Stopover : stopovers
Stopover --> "1" Airport :airport
Flight --> "1" Airport : departureAirport
Flight --> "1" Airport : arrialAirport

class Airport {
  - id : String
  - servesForCities : List<City>
  
  + addCity(c:City): void 
}

class City

Airport --> "1..*" City : servesForCities

class Customer {
  - bookings : List<Booking>

  + addBooking(f:Flight, now:Date listOfPassengerNames:List<String>):boolean
  + confirm(reservationID:String, now:Date):boolean
  + cancel(reservationID:String, now:Date):boolean
}

class Passenger {
  - name: String

}

class Booking {
  - customer      : Customer
  - reservations  : List<Reservation>

  + createReservation(f:Flight, passenger:String, now:Date) : boolean
}

enum ReservationStatus {
    PENDING
    CONFIRMED
    CANCELED
}

class Reservation {
  - id         : String
  - status     : ReservationStatus
  - passenger  : Passenger
  - flight     : Flight

+ setStatus(s:ReservationStatus): void  
}

Booking *-- "1..*" Reservation : reservations
Reservation --> "1" Passenger : passenger
Reservation --> "1" Flight : flight
Booking --> "1" Customer : customer
Customer --> "*"  Booking : bookings
```
// ==end==