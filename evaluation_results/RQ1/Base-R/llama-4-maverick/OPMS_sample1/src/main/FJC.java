import java.util.ArrayList;
import java.util.List;

/**
 * Enum for project type.
 */
enum ProjectType {
    PRODUCTION,
    RESEARCH,
    EDUCATION,
    COMMUNITY
}

/**
 * Enum for funding group type.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Class representing a Funding Group.
 */
class FundingGroup {
    private FundingGroupType type;

    /**
     * Unparameterized constructor for FundingGroup.
     */
    public FundingGroup() {}

    /**
     * Gets the type of funding group.
     * @return FundingGroupType
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the type of funding group.
     * @param type FundingGroupType
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/**
 * Abstract class representing an Employee.
 */
abstract class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the employee.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the employee.
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the employee ID.
     * @return String
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID.
     * @param employeeId String
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number.
     * @return String
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number.
     * @param employeeNumber String
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Class representing a Temporary Employee.
 */
class TemporaryEmployee extends Employee {
    /**
     * Unparameterized constructor for TemporaryEmployee.
     */
    public TemporaryEmployee() {}
}

/**
 * Class representing a Permanent Employee.
 */
class PermanentEmployee extends Employee {
    /**
     * Unparameterized constructor for PermanentEmployee.
     */
    public PermanentEmployee() {}
}

/**
 * Abstract class representing a Project.
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;
    private List<Employee> employees;

    /**
     * Unparameterized constructor for Project.
     */
    public Project() {
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the title of the project.
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the project.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the budget amount of the project.
     * @return double
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Sets the budget amount of the project.
     * @param budgetAmount double
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /**
     * Gets the deadline of the project.
     * @return String
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the project.
     * @param deadline String
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the employees working on the project.
     * @return List<Employee>
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Adds an employee to the project.
     * @param employee Employee
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}

/**
 * Class representing a Production Project.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Unparameterized constructor for ProductionProject.
     */
    public ProductionProject() {}

    /**
     * Gets the site code of the production project.
     * @return String
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code of the production project.
     * @param siteCode String
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Class representing an Education Project.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Unparameterized constructor for EducationProject.
     */
    public EducationProject() {
        this.fundingGroup = new FundingGroup();
    }

    /**
     * Gets the funding group of the education project.
     * @return FundingGroup
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the education project.
     * @param fundingGroup FundingGroup
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Class representing a Community Project.
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Unparameterized constructor for CommunityProject.
     */
    public CommunityProject() {
        this.fundingGroup = new FundingGroup();
    }

    /**
     * Gets the funding group of the community project.
     * @return FundingGroup
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the community project.
     * @param fundingGroup FundingGroup
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Class representing a Research Project.
 */
class ResearchProject extends Project {
    /**
     * Unparameterized constructor for ResearchProject.
     */
    public ResearchProject() {}
}

/**
 * Class representing a Department.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    /**
     * Unparameterized constructor for Department.
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the ID of the department.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the department.
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the department.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the department.
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the projects in the department.
     * @return List<Project>
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Adds a project to the department.
     * @param project Project
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Gets the employees in the department.
     * @return List<Employee>
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Adds an employee to the department.
     * @param employee Employee
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the average budget amount of all projects in the department.
     * @return double
     */
    public double calculateAverageBudget() {
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return projects.isEmpty() ? 0 : totalBudget / projects.size();
    }

    /**
     * Retrieves the funding group type of all community projects within the department.
     * @return List<FundingGroupType>
     */
    public List<FundingGroupType> getFundingGroupTypesOfCommunityProjects() {
        List<FundingGroupType> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                fundingGroupTypes.add(((CommunityProject) project).getFundingGroup().getType());
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Class representing a Company.
 */
class Company {
    private List<Department> departments;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the departments in the company.
     * @return List<Department>
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Adds a department to the company.
     * @param department Department
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Calculates the total budget amount of all projects in the company.
     * @return double
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                totalBudget += project.getBudgetAmount();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments.
     * @return int
     */
    public int countEmployeesOnProductionProjects() {
        int totalEmployees = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    totalEmployees += project.getEmployees().size();
                }
            }
        }
        return totalEmployees;
    }
}