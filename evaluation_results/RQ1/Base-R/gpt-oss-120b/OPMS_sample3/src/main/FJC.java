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

    /** No‑argument constructor required by the task. */
    public Company() {
    }

    /** @return the mutable list of departments */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments the list of departments to set */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total budget of all projects in the whole company.
     *
     * @return sum of the budget amounts of every project in every department.
     */
    public double getTotalBudgetOfAllProjects() {
        double total = 0.0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                total += proj.getBudgetAmount();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees that work on production projects
     * across all departments of the company. The same employee appearing on
     * several production projects is counted multiple times (once per
     * assignment), matching the typical interpretation of “number of
     * employees working on production projects”.
     *
     * @return total employee‑project assignments for production projects.
     */
    public int getNumberOfEmployeesOnProductionProjects() {
        int count = 0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                if (proj instanceof ProductionProject) {
                    count += proj.getEmployees().size();
                }
            }
        }
        return count;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String id;
    private String email;
    private List<Project> projects = new ArrayList<>();

    /** No‑argument constructor required by the task. */
    public Department() {
    }

    /** @return the department identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the contact e‑mail of the department */
    public String getEmail() {
        return email;
    }

    /** @param email the e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of projects belonging to this department */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects the list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return average budget amount, or {@code 0.0} if the department has no projects.
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
     * Retrieves the funding group types of all community projects inside this department.
     *
     * @return an immutable list containing the {@link FundingGroupType} of each community project.
     */
    public List<FundingGroupType> getFundingGroupTypesOfCommunityProjects() {
        List<FundingGroupType> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                FundingGroup fg = ((CommunityProject) p).getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType());
                }
            }
        }
        return Collections.unmodifiableList(types);
    }
}

/**
 * Abstract base class for employees.
 */
abstract class Employee {

    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /** No‑argument constructor required by the task. */
    public Employee() {
    }

    /** @return employee's full name */
    public String getName() {
        return name;
    }

    /** @param name employee name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee's e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email employee e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return internal employee identifier */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param employeeId identifier to set */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** @return employee number (e.g., payroll number) */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /** @param employeeNumber employee number to set */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/** Temporary employee implementation. */
class TemporaryEmployee extends Employee {

    /** No‑argument constructor required by the task. */
    public TemporaryEmployee() {
        super();
    }
}

/** Permanent employee implementation. */
class PermanentEmployee extends Employee {

    /** No‑argument constructor required by the task. */
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
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor required by the task. */
    public Project() {
    }

    /** @return project title */
    public String getTitle() {
        return title;
    }

    /** @param title title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description */
    public String getDescription() {
        return description;
    }

    /** @param description description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return budget amount */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /** @param budgetAmount budget amount to set */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /** @return deadline date */
    public LocalDate getDeadline() {
        return deadline;
    }

    /** @param deadline deadline to set */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees assigned to this project */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/** Production project – characterized by a site code. */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑argument constructor required by the task. */
    public ProductionProject() {
        super();
    }

    /** @return site code where production occurs */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode site code to set */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/** Research project – no extra attributes beyond the base class. */
class ResearchProject extends Project {

    /** No‑argument constructor required by the task. */
    public ResearchProject() {
        super();
    }
}

/** Education project – associated with a funding group. */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor required by the task. */
    public EducationProject() {
        super();
    }

    /** @return funding group linked to this education project */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/** Community project – associated with a funding group. */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor required by the task. */
    public CommunityProject() {
        super();
    }

    /** @return funding group linked to this community project */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set */
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
 * Abstract base class for funding groups.
 */
abstract class FundingGroup {

    /** No‑argument constructor required by the task. */
    public FundingGroup() {
    }

    /**
     * @return the type of the funding group.
     */
    public abstract FundingGroupType getType();
}

/** Private funding group implementation. */
class PrivateGroup extends FundingGroup {

    /** No‑argument constructor required by the task. */
    public PrivateGroup() {
        super();
    }

    @Override
    public FundingGroupType getType() {
        return FundingGroupType.PRIVATE;
    }
}

/** Government funding group implementation. */
class GovernmentGroup extends FundingGroup {

    /** No‑argument constructor required by the task. */
    public GovernmentGroup() {
        super();
    }

    @Override
    public FundingGroupType getType() {
        return FundingGroupType.GOVERNMENT;
    }
}

/** Mixed funding group implementation. */
class MixedGroup extends FundingGroup {

    /** No‑argument constructor required by the task. */
    public MixedGroup() {
        super();
    }

    @Override
    public FundingGroupType getType() {
        return FundingGroupType.MIXED;
    }
}