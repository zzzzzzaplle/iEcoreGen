import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Enum describing the possible types of a funding group.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/* -------------------------------------------------------------------------- */
/*                                FUNDING GROUP                               */
/* -------------------------------------------------------------------------- */

/**
 * Base class for all funding groups.
 */
abstract class FundingGroup {
    private FundingGroupType type;

    public FundingGroup() {
        // default constructor
    }

    public FundingGroupType getType() {
        return type;
    }

    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/**
 * Funding group owned by a private entity.
 */
class PrivateGroup extends FundingGroup {
    public PrivateGroup() {
        setType(FundingGroupType.PRIVATE);
    }
}

/**
 * Funding group owned by a government entity.
 */
class GovernmentGroup extends FundingGroup {
    public GovernmentGroup() {
        setType(FundingGroupType.GOVERNMENT);
    }
}

/**
 * Funding group that is a mixture of private and government sources.
 */
class MixedGroup extends FundingGroup {
    public MixedGroup() {
        setType(FundingGroupType.MIXED);
    }
}

/* -------------------------------------------------------------------------- */
/*                                   EMPLOYEE                                 */
/* -------------------------------------------------------------------------- */

/**
 * Base class for all employees.
 */
abstract class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    public Employee() {
        // default constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

	public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Temporary employee – no additional attributes for now.
 */
class TemporaryEmployee extends Employee {
    public TemporaryEmployee() {
        // default constructor
    }
}

/**
 * Permanent employee – no additional attributes for now.
 */
class PermanentEmployee extends Employee {
    public PermanentEmployee() {
        // default constructor
    }
}

/* -------------------------------------------------------------------------- */
/*                                   PROJECT                                   */
/* -------------------------------------------------------------------------- */

/**
 * Base class for all projects.
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private LocalDate deadline;
    private List<Employee> assignedEmployees = new ArrayList<>();

    public Project() {
        // default constructor
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

	public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns an unmodifiable view of the employees assigned to this project.
     */
    public List<Employee> getAssignedEmployees() {
        return Collections.unmodifiableList(assignedEmployees);
    }

    /**
     * Assigns an employee to the project.
     *
     * @param employee the employee to assign; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        assignedEmployees.add(employee);
    }

    /**
     * Removes an employee from the project.
     *
     * @param employee the employee to remove; must not be {@code null}
     * @return {@code true} if the employee was removed, {@code false} otherwise
     */
    public boolean removeEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        return assignedEmployees.remove(employee);
    }
}

/**
 * Production projects are identified by a site code.
 */
class ProductionProject extends Project {
    private String siteCode;

    public ProductionProject() {
        // default constructor
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research projects – no extra attributes.
 */
class ResearchProject extends Project {
    public ResearchProject() {
        // default constructor
    }
}

/**
 * Education projects are linked to a funding group.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public EducationProject() {
        // default constructor
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are linked to a funding group.
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    public CommunityProject() {
        // default constructor
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/* -------------------------------------------------------------------------- */
/*                                 DEPARTMENT                                  */
/* -------------------------------------------------------------------------- */

/**
 * Represents a department inside a company.
 */
class Department {
    private String id;
    private String email;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public Department() {
        // default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        employees.add(employee);
    }

    public boolean removeEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        return employees.remove(employee);
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public void addProject(Project project) {
        Objects.requireNonNull(project, "project cannot be null");
        projects.add(project);
    }

    public boolean removeProject(Project project) {
        Objects.requireNonNull(project, "project cannot be null");
        return projects.remove(project);
    }

    /**
     * Calculates the average budget of all projects that belong to this department.
     *
     * @return the average budget; {@code 0.0} if the department has no projects
     */
    public double getAverageProjectBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Project p : projects) {
            total += p.getBudgetAmount();
        }
        return total / projects.size();
    }

    /**
     * Retrieves the funding group type of every community project in this department.
     *
     * @return a list containing the {@link FundingGroupType} of each community project.
     *         The list is empty if there are no community projects.
     */
    public List<FundingGroupType> getCommunityProjectFundingGroupTypes() {
        List<FundingGroupType> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                FundingGroup fg = ((CommunityProject) p).getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType());
                }
            }
        }
        return types;
    }
}

/* -------------------------------------------------------------------------- */
/*                                   COMPANY                                   */
/* -------------------------------------------------------------------------- */

/**
 * Represents a company that contains between two and eight departments.
 */
class Company {
    private List<Department> departments = new ArrayList<>();

    public Company() {
        // default constructor
    }

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add; must not be {@code null}
     * @throws IllegalStateException if adding this department would exceed the maximum
     *                               allowed number of departments (8)
     */
    public void addDepartment(Department department) {
        Objects.requireNonNull(department, "department cannot be null");
        if (departments.size() >= 8) {
            throw new IllegalStateException("A company cannot have more than 8 departments.");
        }
        departments.add(department);
    }

    /**
     * Removes a department from the company.
     *
     * @param department the department to remove; must not be {@code null}
     * @return {@code true} if the department was removed, {@code false} otherwise
     */
    public boolean removeDepartment(Department department) {
        Objects.requireNonNull(department, "department cannot be null");
        return departments.remove(department);
    }

    /**
     * Calculates the total budget of all projects across all departments of the company.
     *
     * @return the sum of the budget amounts of every project in the company.
     */
    public double getTotalBudgetOfAllProjects() {
        double total = 0.0;
        for (Department d : departments) {
            for (Project p : d.getProjects()) {
                total += p.getBudgetAmount();
            }
        }
        return total;
    }

    /**
     * Calculates the average budget of all projects that belong to a specific department.
     *
     * @param department the department whose projects are examined; must not be {@code null}
     * @return the average budget; {@code 0.0} if the department has no projects
     * @throws IllegalArgumentException if {@code department} is not part of this company
     */
    public double getAverageBudgetOfDepartment(Department department) {
        Objects.requireNonNull(department, "department cannot be null");
        if (!departments.contains(department)) {
            throw new IllegalArgumentException("The supplied department does not belong to this company.");
        }
        return department.getAverageProjectBudget();
    }

    /**
     * Counts the total number of employees that are assigned to production projects
     * across the whole company.
     *
     * @return the number of employee‑project assignments for production projects.
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

    /**
     * Retrieves the funding group types of all community projects within a given department.
     *
     * @param department the department to query; must not be {@code null}
     * @return a list of {@link FundingGroupType} values representing each community project's funding group
     * @throws IllegalArgumentException if {@code department} is not part of this company
     */
    public List<FundingGroupType> getFundingGroupTypesOfCommunityProjectsInDepartment(Department department) {
        Objects.requireNonNull(department, "department cannot be null");
        if (!departments.contains(department)) {
            throw new IllegalArgumentException("The supplied department does not belong to this company.");
        }
        return department.getCommunityProjectFundingGroupTypes();
    }
}