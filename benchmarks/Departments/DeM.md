// ==version1==
```
class Company {
  - departments : List<Department> 
  - employees : List<Employee> 

  + addDepartment(department: Department): boolean
  + addEmployee( employee : Employee) : boolean
  + deleteDepartment(department: Department): boolean
  + listDepartmentsWithoutManager(): List<Department>|null
  + assignManager(employee: Employee, department: Department): boolean
}

class Department {
  - offices : List<Office> 
  - headquarter : Office 
  - manager : Employee 
  
  + assignOfficeAsHeadquarter(office: Office): boolean
  + hasManager(): boolean
  + deleteEmployee(employee : Employee): boolean
  + deleteOffice(office : Office): boolean

}
class Office
class Employee

Company *-- "*" Department
Department *-- "1..*" Office
Department --> "1" Office : headquarter
Department --> "1" Employee : manager
Company *-- "*" Employee : employees
```
// ==end==
