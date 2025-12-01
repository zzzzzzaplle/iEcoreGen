import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    /** The list of departments belonging to this company (2..8). */
    private List<Department> departments;

    /** No‑argument constructor. */
    public Company() {
        this.departments = new ArrayList<>();
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
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("department cannot be null");
        }
        if (departments.size() >= 8) {
            throw new IllegalStateException("A company can have at most 8 departments");
        }
        departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the whole company.
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
     * Counts the total number of employees that work on production projects
     * across all departments of the company.
     *
     * @return the number of employees working on production projects
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
 * Represents a department within a company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /** No‑argument constructor. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /** @return the department identifier */
    public String getID() {
        return ID;
    }

    /** @param ID the department identifier to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return the department email */
    public String getEmail() {
        return email;
    }

    /** @param email the department email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return mutable list of projects */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects the list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to this department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }
        projects.add(project);
    }

    /**
     * Adds an employee to this department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("employee cannot be null");
        }
        employees.add(employee);
    }

    /**
     * Calculates the average budget of all projects belonging to this department.
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
     * Retrieves the funding group type of all community projects within this department.
     *
     * @return a list containing the {@link FundingGroupType} of each community project
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
        return Collections.unmodifiableList(types);
    }
}

/**
 * Enum describing the type of an employee.
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee that can be assigned to projects.
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

    /** @return the employee email */
    public String getEmail() {
        return email;
    }

    /** @param email the employee email to set */
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
 * Abstract base class for all kinds of projects.
 */
abstract class Project {

    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /** No‑argument constructor. */
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

    /** @return the budget amount */
    public double getBudget() {
        return budget;
    }

    /** @param budget the budget amount to set */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /** @return the deadline */
    public Date getDeadline() {
        return deadline;
    }

    /** @param deadline the deadline to set */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees working on this project */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /** @param workingEmployees the list of working employees to set */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Adds an employee to the list of employees working on this project.
     *
     * @param employee the employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("employee cannot be null");
        }
        workingEmployees.add(employee);
    }
}

/**
 * Production projects are characterized by a site code.
 */
class ProductionProject extends Project {

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

/**
 * Research projects have no additional attributes beyond the base {@link Project}.
 */
class ResearchProject extends Project {

    /** No‑argument constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education projects are linked to a single funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public EducationProject() {
        super();
    }

    /** @return the associated funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to associate */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are linked to a single funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public CommunityProject() {
        super();
    }

    /** @return the associated funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to associate */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enum describing the type of a funding group.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group that can be linked to education or community projects.
 */
class FundingGroup {

    private String name;
    private FundingGroupType type;

    /** No‑argument constructor. */
    public FundingGroup() {
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

    /** @param type the funding group type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}