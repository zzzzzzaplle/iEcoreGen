// ==version1==
```
class Company {
    - departments : List<Department>

    + addDepartment(department: Department) : void
    + calculateTotalBudget() : double
    + countEmployeesInProductionProjects() : int
}

class Department {
    - ID : String
    - email : String
    - employees : List<Employee>

    + getProjects() : List<Project>
    + addProject(project: Project) : void
    + calculateAverageBudget() : double
    + getFundingGroupTypeCommunityProjects() : List<FundingGroupType>
}

enum EmployeeType {
    TEMPORARY
    PERMANENT
}


class Employee {
    - type : EmployeeType
    - name : String
    - email : String
    - ID : String
    - number : String

}

abstract class Project {
    - title : String
    - description : String
    - budget : double
    - deadline : Date
    - workingEmployees : List<Employee>

}

class ProductionProject extends Project {
    - siteCode : String
}

class ResearchProject extends Project {}

class EducationProject extends Project {}

class CommunityProject extends Project {}

enum FundingGroupType {
    PRIVATE
    GOVERNMENT
    MIXED
}

class FundingGroup {
    - name : String
    - type : FundingGroupType

}

Company *-- "2..8" Department : departments
Department *-- "*" Employee : employees
Department *-- "*" Project : projects
Project --> "*" Employee : workingEmployees

EducationProject "*" -- "1" FundingGroup : fundingGroup
CommunityProject "*" -- "1" FundingGroup : fundingGroup

```
// ==end==
