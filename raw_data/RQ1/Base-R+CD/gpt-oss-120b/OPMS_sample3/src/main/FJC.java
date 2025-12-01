import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    /** The departments belonging to the company (2..8). */
    private List<Department> departments;

    /** No‑argument constructor initializing the departments list. */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /** @return the list of departments (modifiable). */
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
     * @param department the department to add; must not be {@code null}
     */
    public void addDepartment(Department department) {
        Objects.requireNonNull(department, "department cannot be null");
        if (departments.size() >= 8) {
            throw new IllegalStateException("A company cannot have more than 8 departments.");
        }
        departments.add(department);
    }

    /**
     * Calculates the total budget of all projects across all departments in the company.
     *
     * @return the sum of the budget amounts of every project; {@code 0.0} if there are no projects
     */
    public double calculateTotalBudget() {
        double total = 0.0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                total += proj.getBudget();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees that work on production projects in the whole company.
     *
     * @return the number of employee references assigned to production projects
     */
    public int countEmployeesInProductionProjects() {
        int count = 0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                if (proj instanceof ProductionProject) {
                    count += proj.getWorkingEmployees().size();
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

    /** Unique identifier of the department. */
    private String ID;

    /** Contact e‑mail of the department. */
    private String email;

    /** Employees that belong to the department. */
    private List<Employee> employees;

    /** Projects that the department is handling. */
    private List<Project> projects;

    /** No‑argument constructor initializing collections. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /** @return the department ID */
    public String getID() {
        return ID;
    }

    /** @param ID the department ID to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return the department e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email the department e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return mutable list of projects */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add; must not be {@code null}
     */
    public void addProject(Project project) {
        Objects.requireNonNull(project, "project cannot be null");
        projects.add(project);
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        employees.add(employee);
    }

    /**
     * Calculates the average budget of all projects belonging to this department.
     *
     * @return average budget, or {@code 0.0} if the department has no projects
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Project p : projects) {
            sum += p.getBudget();
        }
        return sum / projects.size();
    }

    /**
     * Retrieves the funding group types of all community projects within this department.
     *
     * @return a list containing the {@link FundingGroupType} of each community project's funding group.
     *         The list may be empty if there are no community projects.
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                CommunityProject cp = (CommunityProject) p;
                FundingGroup fg = cp.getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType());
                }
            }
        }
        return types;
    }
}

/**
 * Enumeration of employee types.
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee working for a department.
 */
class Employee {

    /** The type of the employee (temporary or permanent). */
    private EmployeeType type;

    /** Employee's full name. */
    private String name;

    /** Employee's e‑mail address. */
    private String email;

    /** Unique identifier for the employee. */
    private String ID;

    /** Employee number (could be internal HR number). */
    private String number;

    /** No‑argument constructor. */
    public Employee() {
        // default constructor
    }

    /** @return the employee type */
    public EmployeeType getType() {
        return type;
    }

    /** @param type the employee type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** @return the employee name */
    public String getName() {
        return name;
    }

    /** @param name the employee name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the employee e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email the employee e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the employee ID */
    public String getID() {
        return ID;
    }

    /** @param ID the employee ID to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return the employee number */
    public String getNumber() {
        return number;
    }

    /** @param number the employee number to set */
    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Abstract base class for all project types.
 */
abstract class Project {

    /** Title of the project. */
    private String title;

    /** Detailed description of the project. */
    private String description;

    /** Budget allocated for the project. */
    private double budget;

    /** Deadline for the project. */
    private Date deadline;

    /** Employees currently working on the project. */
    private List<Employee> workingEmployees;

    /** No‑argument constructor initializing the employee list. */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /** @return the project title */
    public String getTitle() {
        return title;
    }

    /** @param title the project title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the project description */
    public String getDescription() {
        return description;
    }

    /** @param description the project description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the project budget */
    public double getBudget() {
        return budget;
    }

    /** @param budget the project budget to set */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /** @return the project deadline */
    public Date getDeadline() {
        return deadline;
    }

    /** @param deadline the project deadline to set */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees working on the project */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /** @param workingEmployees list of employees to set */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Adds an employee to the list of working employees.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addWorkingEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        workingEmployees.add(employee);
    }
}

/**
 * Production projects are identified by a site code.
 */
class ProductionProject extends Project {

    /** Site code where production occurs. */
    private String siteCode;

    /** No‑argument constructor. */
    public ProductionProject() {
        super();
    }

    /** @return the site code */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode the site code to set */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/** Research projects have no additional attributes beyond the base {@link Project}. */
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

    /** Funding group that supports the education project. */
    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public EducationProject() {
        super();
    }

    /** @return the associated funding group */
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

    /** Funding group that supports the community project. */
    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public CommunityProject() {
        super();
    }

    /** @return the associated funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enumeration of possible funding group types.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group that can support education and community projects.
 */
class FundingGroup {

    /** Name of the funding group. */
    private String name;

    /** Type of the funding group. */
    private FundingGroupType type;

    /** No‑argument constructor. */
    public FundingGroup() {
        // default constructor
    }

    /** @return the name of the funding group */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the type of the funding group */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type the type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}