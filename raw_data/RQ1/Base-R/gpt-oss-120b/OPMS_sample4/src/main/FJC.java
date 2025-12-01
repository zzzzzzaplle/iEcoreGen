import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains several departments.
 */
 class Company {

    /** Minimum number of departments allowed (inclusive). */
    private static final int MIN_DEPARTMENTS = 2;
    /** Maximum number of departments allowed (inclusive). */
    private static final int MAX_DEPARTMENTS = 8;

    /** List of departments belonging to the company. */
    private List<Department> departments = new ArrayList<>();

    /** No‑arg constructor required by the specification. */
    public Company() {
    }

    /** @return the mutable list of departments (modifications affect the company). */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments the list of departments to set. */
    public void setDepartments(List<Department> departments) {
        if (departments == null) {
            this.departments = new ArrayList<>();
        } else {
            this.departments = departments;
        }
    }

    /**
     * Calculates the total budget of all projects in the whole company.
     *
     * @return sum of the budget amounts of all projects across all departments.
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
     * Counts the total number of employees that work on production projects
     * across all departments of the company.
     *
     * @return number of distinct employee instances assigned to production projects.
     */
    public int getNumberOfEmployeesWorkingOnProductionProjects() {
        int count = 0;
        for (Department d : departments) {
            for (Project p : d.getProjects()) {
                if (p instanceof ProductionProject) {
                    count += ((ProductionProject) p).getEmployees().size();
                }
            }
        }
        return count;
    }

    /**
     * Validates that the company complies with the department count constraint.
     *
     * @throws IllegalStateException if the number of departments is outside the allowed range.
     */
    public void validateDepartmentCount() {
        int size = departments.size();
        if (size < MIN_DEPARTMENTS || size > MAX_DEPARTMENTS) {
            throw new IllegalStateException(
                String.format("Company must have between %d and %d departments, found %d.",
                    MIN_DEPARTMENTS, MAX_DEPARTMENTS, size));
        }
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String departmentId;
    private String email;
    private List<Project> projects = new ArrayList<>();

    /** No‑arg constructor required by the specification. */
    public Department() {
    }

    /** @return the department identifier. */
    public String getDepartmentId() {
        return departmentId;
    }

    /** @param departmentId the identifier to set. */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /** @return the department contact e‑mail. */
    public String getEmail() {
        return email;
    }

    /** @param email the contact e‑mail to set. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of projects belonging to this department. */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects the list of projects to replace the current list. */
    public void setProjects(List<Project> projects) {
        this.projects = projects != null ? projects : new ArrayList<>();
    }

    /**
     * Calculates the average budget amount of all projects in this department.
     *
     * @return the average budget, or 0.0 if the department has no projects.
     */
    public double getAverageBudgetOfProjects() {
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
     * Retrieves the funding group types of all community projects in this department.
     *
     * @return an unmodifiable list of funding group type names (e.g., "PRIVATE", "GOVERNMENT", "MIXED").
     */
    public List<String> getFundingGroupTypesOfCommunityProjects() {
        List<String> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                FundingGroup fg = ((CommunityProject) p).getFundingGroup();
                if (fg != null) {
                    types.add(fg.getGroupType().name());
                }
            }
        }
        return Collections.unmodifiableList(types);
    }
}

/**
 * Base class for all employees.
 */
abstract class Employee {

    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /** No‑arg constructor required by the specification. */
    public Employee() {
    }

    /** @return employee's full name. */
    public String getName() {
        return name;
    }

    /** @param name employee's full name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee's e‑mail address. */
    public String getEmail() {
        return email;
    }

    /** @param email employee's e‑mail address. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return unique identifier of the employee. */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param employeeId unique identifier to set. */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** @return employee number (could be internal HR number). */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /** @param employeeNumber employee number to set. */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Represents a permanent employee.
 */
class PermanentEmployee extends Employee {

    /** No‑arg constructor required by the specification. */
    public PermanentEmployee() {
        super();
    }
}

/**
 * Represents a temporary employee.
 */
class TemporaryEmployee extends Employee {

    /** No‑arg constructor required by the specification. */
    public TemporaryEmployee() {
        super();
    }
}

/**
 * Base class for all projects.
 */
abstract class Project {

    private String title;
    private String description;
    private double budgetAmount;
    private LocalDate deadline;
    private List<Employee> employees = new ArrayList<>();

    /** No‑arg constructor required by the specification. */
    public Project() {
    }

    /** @return project title. */
    public String getTitle() {
        return title;
    }

    /** @param title project title to set. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description. */
    public String getDescription() {
        return description;
    }

    /** @param description project description to set. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return budget amount for the project. */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /** @param budgetAmount budget amount to set. */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /** @return deadline of the project. */
    public LocalDate getDeadline() {
        return deadline;
    }

    /** @param deadline deadline to set. */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees assigned to this project. */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees list of employees to replace the current list. */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /**
     * Adds an employee to the project.
     *
     * @param employee employee to add; must not be {@code null}.
     * @throws NullPointerException if {@code employee} is {@code null}.
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee cannot be null");
        employees.add(employee);
    }
}

/**
 * Production projects have a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑arg constructor required by the specification. */
    public ProductionProject() {
        super();
    }

    /** @return site code associated with the production project. */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode site code to set. */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research projects have no extra fields beyond the base {@link Project}.
 */
class ResearchProject extends Project {

    /** No‑arg constructor required by the specification. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education projects are linked to a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑arg constructor required by the specification. */
    public EducationProject() {
        super();
    }

    /** @return funding group associated with the education project. */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set. */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are linked to a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑arg constructor required by the specification. */
    public CommunityProject() {
        super();
    }

    /** @return funding group associated with the community project. */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set. */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Types of funding groups.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Base class for all funding groups.
 */
abstract class FundingGroup {

    private FundingGroupType groupType;

    /** No‑arg constructor required by the specification. */
    public FundingGroup() {
    }

    /** @return the type of the funding group. */
    public FundingGroupType getGroupType() {
        return groupType;
    }

    /** @param groupType the type to set. */
    public void setGroupType(FundingGroupType groupType) {
        this.groupType = groupType;
    }
}

/**
 * Private funding group.
 */
class PrivateGroup extends FundingGroup {

    /** No‑arg constructor required by the specification. */
    public PrivateGroup() {
        setGroupType(FundingGroupType.PRIVATE);
    }
}

/**
 * Government funding group.
 */
class GovernmentGroup extends FundingGroup {

    /** No‑arg constructor required by the specification. */
    public GovernmentGroup() {
        setGroupType(FundingGroupType.GOVERNMENT);
    }
}

/**
 * Mixed funding group (both private and government sources).
 */
class MixedGroup extends FundingGroup {

    /** No‑arg constructor required by the specification. */
    public MixedGroup() {
        setGroupType(FundingGroupType.MIXED);
    }
}