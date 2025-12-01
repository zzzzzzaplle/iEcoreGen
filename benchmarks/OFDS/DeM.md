// ==version1==
```
class Company {
    - name : String
    - employees : List<Employee>
    - vehicles : List<Vehicle>
    
    + getFullTimeEmployeeCount() : int
    + getPartTimeDriverVehicleRegistrationNumbers() : List<String>
    + getRentedVehicleCount() : int
    + getCurrentDriversNames() : List<String>
}

enum EmployeeType {
    PART_TIME
    FULL_TIME
}

class Employee {
    - name : String
    - type : EmployeeType
    - drivenVehicle : Vehicle
}

Company *-- "*" Employee : employees


abstract class Vehicle {
    - registrationNumber : String
    - currentDriver : Employee
}

Company *-- "*" Vehicle : vehicles

class OwnedVehicle extends Vehicle {
    
}

class RentedVehicle extends Vehicle {
  
}

Vehicle --> "0..1" Employee : currentDriver
Employee --> "0..1" Vehicle : drivenVehicle
```
// ==end==
