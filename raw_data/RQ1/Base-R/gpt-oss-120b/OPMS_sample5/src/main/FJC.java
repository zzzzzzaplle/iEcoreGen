import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    /** List of departments belonging to the company (2‑8 allowed). */
    private List<Department> departments = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
    }

    /** @return the list of departments */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments the list of departments to set */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @throws IllegalStateException if adding the department would exceed the maximum of 8 departments
     */
    public void addDepartment(Department department) {
        if (departments.size() >= 8) {
            throw new IllegalStateException("A company cannot have more than 8 departments.");
        }
        departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the whole company.
     *
     * @return sum of the budget amounts of every project across all departments
     */
    public double getTotalBudget() {
        double total = 0.0;
        for (Department d : departments) {
            for (Project p : d.getProjects()) {
                total += p.getBudgetAmount();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees that work on production projects
     * across all departments of the company.
     *
     * @return total employee count on production projects
     */
    public int getNumberOfEmployeesOnProductionProjects() {
        int count = 0;
        for (Department d : departments) {
            for (Project p : d.getProjects()) {
                if (p instanceof ProductionProject) {
                    count += p.getAssignedEmployees().size();
                }
            }
        }
        return count;
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    /** Department identifier. */
    private String id;

    /** Department contact e‑mail. */
    private String email;

    /** Employees hired by the department. */
    private List<Employee> employees = new ArrayList<>();

    /** Projects the department is responsible for. */
    private List<Project> projects = new ArrayList<>();

    /** No‑argument constructor. */
    public Department() {
    }

    /** @return department id */
    public String getId() {
        return id;
    }

    /** @param id the department id to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return department e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email the department e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return list of employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return list of projects */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects the list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return average budget amount, or 0.0 if the department has no projects
     */
    public double getAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Project p : projects) {
            sum += p.getBudgetAmount();
        }
        return sum / projects.size();
    }

    /**
     * Retrieves the funding group types of all community projects within this department.
     *
     * @return an immutable list of funding group type names (e.g., "PRIVATE", "GOVERNMENT")
     */
    public List<String> getCommunityProjectFundingGroupTypes() {
        List<String> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                FundingGroup fg = ((CommunityProject) p).getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType().name());
                }
            }
        }
        return Collections.unmodifiableList(types);
    }
}

/**
 * Abstract base class for all employees.
 */
abstract class Employee {

    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /** No‑argument constructor. */
    public Employee() {
    }

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email the e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return employee identifier */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param employeeId the identifier to set */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** @return employee number */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /** @param employeeNumber the employee number to set */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Represents a temporary employee.
 */
class TemporaryEmployee extends Employee {

    /** No‑argument constructor. */
    public TemporaryEmployee() {
        super();
    }
}

/**
 * Represents a permanent employee.
 */
class PermanentEmployee extends Employee {

    /** No‑argument constructor. */
    public PermanentEmployee() {
        super();
    }
}

/**
 * Abstract base class for all projects.
 */
abstract class Project {

    private String title;
    private String description;
    private double budgetAmount;
    private LocalDate deadline;
    private List<Employee> assignedEmployees = new ArrayList<>();

    /** No‑argument constructor. */
    public Project() {
    }

    /** @return project title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description */
    public String getDescription() {
        return description;
    }

    /** @param description the description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return budget amount */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /** @param budgetAmount the budget amount to set */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /** @return project deadline */
    public LocalDate getDeadline() {
        return deadline;
    }

    /** @param deadline the deadline to set */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /** @return list of employees assigned to this project */
    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    /** @param assignedEmployees the list of assigned employees */
    public void setAssignedEmployees(List<Employee> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    /**
     * Assigns an employee to the project.
     *
     * @param employee the employee to assign
     */
    public void addEmployee(Employee employee) {
        assignedEmployees.add(employee);
    }
}

/**
 * Production projects have a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑argument constructor. */
    public ProductionProject() {
        super();
    }

    /** @return site code */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode the site code to set */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research projects have no additional fields.
 */
class ResearchProject extends Project {

    /** No‑argument constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education projects are linked to a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public EducationProject() {
        super();
    }

    /** @return funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are linked to a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public CommunityProject() {
        super();
    }

    /** @return funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Abstract base class for funding groups.
 */
abstract class FundingGroup {

    private FundingGroupType type;

    /** No‑argument constructor. */
    public FundingGroup() {
    }

    /** @return the type of funding group */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type the type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/**
 * Private funding group implementation.
 */
class PrivateGroup extends FundingGroup {

    /** No‑argument constructor. */
    public PrivateGroup() {
        super();
        setType(FundingGroupType.PRIVATE);
    }
}

/**
 * Government funding group implementation.
 */
class GovernmentGroup extends FundingGroup {

    /** No‑argument constructor. */
    public GovernmentGroup() {
        super();
        setType(FundingGroupType.GOVERNMENT);
    }
}

/**
 * Mixed funding group implementation.
 */
class MixedGroup extends FundingGroup {

    /** No‑argument constructor. */
    public MixedGroup() {
        super();
        setType(FundingGroupType.MIXED);
    }
}

/**
 * Enumeration of possible funding group categories.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}