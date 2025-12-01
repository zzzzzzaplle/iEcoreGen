// ==version1==
```
abstract class Employee {
    - department : String
    - name : String
    - birthDate : Date
    - socialInsuranceNumber : String
}

class Manager extends Employee {
    - salary : double
    - position : String
    
    + getDirectSubordinateEmployeesCount() : int
}

class SalesPeople extends Employee {
    - salary : double
    - amountOfSales : double
    - commissionPercentage : double
    
    + getTotalCommission() : double
}

abstract class Worker extends Employee {
    - weeklyWorkingHour : integer
    - hourlyRates : double
}

class ShiftWorker extends Worker {
    - holidayPremium: double
    
    + calculateHolidayPremium() : double
}

class OffShiftWorker extends Worker {
}

class Company {
    - employees: List<Employees>
    
    + calculateTotalEmployeeSalary() : double
    + calculateTotalSalesPeopleCommission() : double
    + calculateTotalShiftWorkerHolidayPremiums() : double
}

enum DepartmentType {
    PRODUCTION
    CONTROL
    DELIVERY
}

class Department {
    - type : DepartmentType
    + calculateAverageWorkerWorkingHours() : double
}

Manager --> "*" Employee : subordinates
Company *-- "*" Department : departments
Company *-- "*" Employee : employees
Department --> "1" Manager : manager
Department --> "*" Employee : employees

```
// ==end==
