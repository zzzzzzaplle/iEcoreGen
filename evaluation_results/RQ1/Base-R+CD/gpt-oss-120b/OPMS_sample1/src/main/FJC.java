import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    /** The list of departments belonging to the company (2..8). */
    private List<Department> departments;

    /** No‑argument constructor initializing the departments list. */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        if (departments.size() >= 8) {
            throw new IllegalStateException("A company cannot have more than 8 departments");
        }
        departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the company.
     *
     * @return the sum of the budget amounts of every project in every department
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
     * Counts the total number of employees that work on production projects across all departments.
     *
     * @return the number of employees assigned to production projects
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

    // -------------------- getters & setters --------------------

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    public void setDepartments(List<Department> departments) {
        if (departments == null) {
            this.departments = new ArrayList<>();
        } else {
            this.departments = departments;
        }
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /** No‑argument constructor initializing collections. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable list of projects belonging to this department.
     *
     * @return list of projects
     */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    /**
     * Adds a project to this department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        projects.add(project);
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return the average budget, or 0.0 if the department has no projects
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
     * Retrieves the funding group types of all community projects in this department.
     *
     * @return a list of {@link FundingGroupType} representing the funding types of community projects
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

    /**
     * Adds an employee to this department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }

    // -------------------- getters & setters --------------------

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
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

    public void setEmployees(List<Employee> employees) {
        if (employees == null) {
            this.employees = new ArrayList<>();
        } else {
            this.employees = employees;
        }
    }

    public void setProjects(List<Project> projects) {
        if (projects == null) {
            this.projects = new ArrayList<>();
        } else {
            this.projects = projects;
        }
    }
}

/**
 * Enum representing the type of an employee.
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee.
 */
class Employee {

    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    /** No‑argument constructor. */
    public Employee() {
    }

    // -------------------- getters & setters --------------------

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
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

	public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

	public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Abstract base class for all projects.
 */
abstract class Project {

    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /** No‑argument constructor initializing the working employees list. */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /**
     * Adds an employee to the list of employees working on this project.
     *
     * @param employee the employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        workingEmployees.add(employee);
    }

    // -------------------- getters & setters --------------------

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

	public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

	public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

	public List<Employee> getWorkingEmployees() {
        return Collections.unmodifiableList(workingEmployees);
    }

    public void setWorkingEmployees(List<Employee> workingEmployees) {
        if (workingEmployees == null) {
            this.workingEmployees = new ArrayList<>();
        } else {
            this.workingEmployees = workingEmployees;
        }
    }
}

/**
 * Production project with a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑argument constructor. */
    public ProductionProject() {
        super();
    }

    // -------------------- getters & setters --------------------

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research project (no additional fields).
 */
class ResearchProject extends Project {

    /** No‑argument constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education project associated with a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public EducationProject() {
        super();
    }

    // -------------------- getters & setters --------------------

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community project associated with a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public CommunityProject() {
        super();
    }

    // -------------------- getters & setters --------------------

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enum representing the type of a funding group.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group.
 */
class FundingGroup {

    private String name;
    private FundingGroupType type;

    /** No‑argument constructor. */
    public FundingGroup() {
    }

    // -------------------- getters & setters --------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public FundingGroupType getType() {
        return type;
    }

    public void setType(FundingGroupType type) {
        this.type = type;
    }
}