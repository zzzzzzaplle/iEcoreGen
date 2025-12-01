import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that contains multiple departments
 */
class Company {
    private List<Department> departments;

    /**
     * Default constructor
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the list of departments in the company
     * @return List of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company
     * @param departments List of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the company
     * @param department Department to add
     */
    public void addDepartment(Department department) {
        if (departments.size() >= 8) {
            throw new IllegalStateException("Company cannot have more than 8 departments");
        }
        departments.add(department);
    }

    /**
     * Calculates the total budget of all projects across all departments in the company
     * @return Total budget amount as double
     */
    public double calculateTotalBudget() {
        double total = 0.0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                total += project.getBudgetAmount();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees working on production projects across all departments
     * @return Total count of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    count += project.getWorkingEmployees().size();
                }
            }
        }
        return count;
    }
}

/**
 * Represents a department within a company
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    /**
     * Default constructor
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the department ID
     * @return Department ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the department ID
     * @param id Department ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the department email
     * @return Department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the department email
     * @param email Department email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of projects in the department
     * @return List of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects in the department
     * @param projects List of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Gets the list of employees in the department
     * @return List of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the department
     * @param employees List of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds a project to the department
     * @param project Project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Adds an employee to the department
     * @param employee Employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculates the average budget amount of all projects in the department
     * @return Average budget amount as double
     * @throws IllegalStateException if there are no projects in the department
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            throw new IllegalStateException("Cannot calculate average budget for department with no projects");
        }
        
        double total = 0.0;
        for (Project project : projects) {
            total += project.getBudgetAmount();
        }
        return total / projects.size();
    }

    /**
     * Retrieves the funding group types of all community projects in the department
     * @return List of funding group types for community projects
     */
    public List<FundingGroupType> getCommunityProjectFundingGroupTypes() {
        List<FundingGroupType> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                CommunityProject communityProject = (CommunityProject) project;
                fundingGroupTypes.add(communityProject.getFundingGroup().getType());
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Abstract class representing a project
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;
    private List<Employee> workingEmployees;

    /**
     * Default constructor
     */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /**
     * Gets the project title
     * @return Project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the project title
     * @param title Project title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the project description
     * @return Project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the project description
     * @param description Project description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project budget amount
     * @return Budget amount
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Sets the project budget amount
     * @param budgetAmount Budget amount to set
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /**
     * Gets the project deadline
     * @return Project deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the project deadline
     * @param deadline Project deadline to set
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the list of employees working on the project
     * @return List of working employees
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Sets the list of employees working on the project
     * @param workingEmployees List of working employees to set
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Adds an employee to the project
     * @param employee Employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        workingEmployees.add(employee);
    }

    /**
     * Removes an employee from the project
     * @param employee Employee to remove
     */
    public void removeWorkingEmployee(Employee employee) {
        workingEmployees.remove(employee);
    }
}

/**
 * Represents a production project
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Default constructor
     */
    public ProductionProject() {
        super();
    }

    /**
     * Gets the site code for the production project
     * @return Site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code for the production project
     * @param siteCode Site code to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents a research project
 */
class ResearchProject extends Project {
    /**
     * Default constructor
     */
    public ResearchProject() {
        super();
    }
}

/**
 * Abstract class for projects that have funding groups
 */
abstract class FundedProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor
     */
    public FundedProject() {
        super();
    }

    /**
     * Gets the funding group for the project
     * @return Funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group for the project
     * @param fundingGroup Funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents an education project
 */
class EducationProject extends FundedProject {
    /**
     * Default constructor
     */
    public EducationProject() {
        super();
    }
}

/**
 * Represents a community project
 */
class CommunityProject extends FundedProject {
    /**
     * Default constructor
     */
    public CommunityProject() {
        super();
    }
}

/**
 * Represents a funding group
 */
class FundingGroup {
    private FundingGroupType type;

    /**
     * Default constructor
     */
    public FundingGroup() {
    }

    /**
     * Gets the funding group type
     * @return Funding group type
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the funding group type
     * @param type Funding group type to set
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/**
 * Enumeration of funding group types
 */
enum FundingGroupType {
    PRIVATE_GROUP,
    GOVERNMENT_GROUP,
    MIXED_GROUP
}

/**
 * Abstract class representing an employee
 */
abstract class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * Gets the employee name
     * @return Employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name
     * @param name Employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee email
     * @return Employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee email
     * @param email Employee email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the employee ID
     * @return Employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID
     * @param employeeId Employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number
     * @return Employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number
     * @param employeeNumber Employee number to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Represents a temporary employee
 */
class TemporaryEmployee extends Employee {
    /**
     * Default constructor
     */
    public TemporaryEmployee() {
        super();
    }
}

/**
 * Represents a permanent employee
 */
class PermanentEmployee extends Employee {
    /**
     * Default constructor
     */
    public PermanentEmployee() {
        super();
    }
}