// ==version1==
```
class Store {
    - cars: List<Car>
    - rentals: List<Rental>
    - notices: List<OverdueNotice>

    + List<Car> identifyAvailableCars() 
    + double calculateTotalRevenue() 
    + List<Customer> findCustomersWithOverdueRentals( currentDate Date) 
    + double determineAverageDailyPrice() 
    + Map<Customer, Integer> countCarsRentedPerCustomer() 
}

class Car {
    - plate : String
    - model : String
    - dailyPrice : double 
}

class Customer {
    - name : String
    - surname : String
    - address: String
    - rentedCarPlate: String
}

class Rental {
    - rentalDate : Date
    - dueDate : Date
    - backDate : Date
    - totalPrice : double
    - leasingTerms : String
    - car: Car
    - customer: Customer
}

class OverdueNotice {
    - customer: Customer
    
    + void sendNoticeTo(Customer customer)
}

Rental --> "1" Car : car
Rental --> "1" Customer : customer
OverdueNotice --> "1" Customer : customer

Store *-- "*" Car : cars
Store *-- "*" Rental : rentals
Store *-- "*" OverdueNotice : notices
```
// ==end==
