import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains several departments.
 */
 class Company {

    /** List of departments belonging to this company (2‑8 departments). */
    private List<Department> departments = new ArrayList<>();

    /** No‑arg constructor required for test instantiation. */
    public Company() {
        // empty constructor
    }

    /** @return mutable list of departments (tests may add/remove). */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments list to set. */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total budget of all projects in the whole company.
     *
     * @return sum of the budget amounts of every project in every department.
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
     * Counts the total number of distinct employees that are working on production projects
     * across all departments of this company.
     *
     * @return number of employees assigned to at least one production project.
     */
    public int getNumberOfEmployeesOnProductionProjects() {
        // Use a set to avoid double‑counting employees that appear in multiple projects.
        java.util.Set<Employee> employeeSet = new java.util.HashSet<>();
        for (Department d : departments) {
            for (Project p : d.getProjects()) {
                if (p instanceof ProductionProject) {
                    employeeSet.addAll(p.getAssignedEmployees());
                }
            }
        }
        return employeeSet.size();
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    private String departmentId;
    private String email;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    /** No‑arg constructor required for test instantiation. */
    public Department() {
        // empty constructor
    }

    /** @return department identifier. */
    public String getDepartmentId() {
        return departmentId;
    }

    /** @param departmentId identifier to set. */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /** @return department e‑mail address. */
    public String getEmail() {
        return email;
    }

    /** @param email e‑mail address to set. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of employees. */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees list to set. */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return mutable list of projects. */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects list to set. */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Calculates the average budget of all projects belonging to this department.
     *
     * @return average budget amount, or 0.0 if the department has no projects.
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
     * Retrieves the funding group type of every community project in this department.
     *
     * @return a list containing the funding group type (as a {@code String}) for each community project.
     *         The list is empty if there are no community projects.
     */
    public List<String> getFundingGroupTypesOfCommunityProjects() {
        List<String> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                FundingGroup fg = ((CommunityProject) p).getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType().name());
                } else {
                    types.add("UNKNOWN");
                }
            }
        }
        return types;
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

    /** No‑arg constructor required for test instantiation. */
    public Employee() {
        // empty constructor
    }

    /** @return employee's full name. */
    public String getName() {
        return name;
    }

    /** @param name name to set. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee's e‑mail address. */
    public String getEmail() {
        return email;
    }

    /** @param email e‑mail address to set. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return unique employee identifier. */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param employeeId identifier to set. */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** @return employee number (e.g., payroll number). */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /** @param employeeNumber number to set. */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/** Temporary employee implementation (no extra fields for now). */
class TemporaryEmployee extends Employee {

    /** No‑arg constructor required for test instantiation. */
    public TemporaryEmployee() {
        super();
    }
}

/** Permanent employee implementation (no extra fields for now). */
class PermanentEmployee extends Employee {

    /** No‑arg constructor required for test instantiation. */
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

    /** No‑arg constructor required for test instantiation. */
    public Project() {
        // empty constructor
    }

    /** @return project title. */
    public String getTitle() {
        return title;
    }

    /** @param title title to set. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description. */
    public String getDescription() {
        return description;
    }

    /** @param description description to set. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return total budget allocated to the project. */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /** @param budgetAmount budget to set. */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /** @return deadline date of the project. */
    public LocalDate getDeadline() {
        return deadline;
    }

    /** @param deadline deadline to set. */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees assigned to this project. */
    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    /** @param assignedEmployees list to set. */
    public void setAssignedEmployees(List<Employee> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }
}

/** Production projects are identified by a site code. */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑arg constructor required for test instantiation. */
    public ProductionProject() {
        super();
    }

    /** @return site code of the production project. */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode site code to set. */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/** Research projects have no extra attributes beyond the base Project. */
class ResearchProject extends Project {

    /** No‑arg constructor required for test instantiation. */
    public ResearchProject() {
        super();
    }
}

/**
 * Projects that are linked to a funding group (education & community).
 */
abstract class FundedProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑arg constructor required for test instantiation. */
    public FundedProject() {
        super();
    }

    /** @return funding group associated with the project. */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set. */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/** Education projects are funded. */
class EducationProject extends FundedProject {

    /** No‑arg constructor required for test instantiation. */
    public EducationProject() {
        super();
    }
}

/** Community projects are funded. */
class CommunityProject extends FundedProject {

    /** No‑arg constructor required for test instantiation. */
    public CommunityProject() {
        super();
    }
}

/**
 * Enum describing the possible types of funding groups.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Abstract base class for a funding group.
 */
abstract class FundingGroup {

    private FundingGroupType type;

    /** No‑arg constructor required for test instantiation. */
    public FundingGroup() {
        // empty constructor
    }

    /** @return the type of this funding group. */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type type to set. */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/** Private funding group implementation. */
class PrivateGroup extends FundingGroup {

    /** No‑arg constructor required for test instantiation. */
    public PrivateGroup() {
        super();
        setType(FundingGroupType.PRIVATE);
    }
}

/** Government funding group implementation. */
class GovernmentGroup extends FundingGroup {

    /** No‑arg constructor required for test instantiation. */
    public GovernmentGroup() {
        super();
        setType(FundingGroupType.GOVERNMENT);
    }
}

/** Mixed funding group implementation. */
class MixedGroup extends FundingGroup {

    /** No‑arg constructor required for test instantiation. */
    public MixedGroup() {
        super();
        setType(FundingGroupType.MIXED);
    }
}