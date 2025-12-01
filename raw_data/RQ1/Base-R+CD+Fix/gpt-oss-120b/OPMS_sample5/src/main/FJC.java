import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains between two and eight departments.
 */
 class Company {

    /** The list of departments belonging to this company. */
    private List<Department> departments = new ArrayList<>();

    /** Default constructor required for reflection / test frameworks. */
    public Company() {
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects across all departments of the company.
     *
     * @return the sum of the budget amounts of every project in the company; 0.0 if there are none
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
     * Counts the total number of employees that are working on production projects in the whole company.
     *
     * @return the total number of employee assignments to production projects
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

    /** @return the list of departments */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments the list of departments to set */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    /** Default constructor. */
    public Department() {
    }

    /** @return the department identifier */
    public String getID() {
        return ID;
    }

    /** @param ID the identifier to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return the department email */
    public String getEmail() {
        return email;
    }

    /** @param email the email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the list of employees belonging to this department */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return the list of projects owned by this department */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects the list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (project != null) {
            projects.add(project);
        }
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return the average budget; 0.0 if the department has no projects
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Project proj : projects) {
            sum += proj.getBudget();
        }
        return sum / projects.size();
    }

    /**
     * Retrieves the funding group type of every community project in this department.
     *
     * @return a list containing the {@link FundingGroupType} of each community project;
     *         the list may be empty if there are no community projects
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> types = new ArrayList<>();
        for (Project proj : projects) {
            if (proj instanceof CommunityProject) {
                CommunityProject cp = (CommunityProject) proj;
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
 * Represents an employee.
 */
class Employee {

    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    /** Default constructor. */
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

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the employee email */
    public String getEmail() {
        return email;
    }

    /** @param email the email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the employee ID */
    public String getID() {
        return ID;
    }

    /** @param ID the ID to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return the employee number */
    public String getNumber() {
        return number;
    }

    /** @param number the number to set */
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
    private List<Employee> workingEmployees = new ArrayList<>();

    /** Default constructor. */
    public Project() {
    }

    /** @return the project title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the project description */
    public String getDescription() {
        return description;
    }

    /** @param description the description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the project budget */
    public double getBudget() {
        return budget;
    }

    /** @param budget the budget to set */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /** @return the project deadline */
    public Date getDeadline() {
        return deadline;
    }

    /** @param deadline the deadline to set */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /** @return the list of employees working on this project */
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
        if (employee != null) {
            workingEmployees.add(employee);
        }
    }
}

/**
 * Production projects have a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** Default constructor. */
    public ProductionProject() {
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

/** Research projects have no additional attributes. */
class ResearchProject extends Project {

    /** Default constructor. */
    public ResearchProject() {
    }
}

/**
 * Education projects are associated with a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** Default constructor. */
    public EducationProject() {
    }

    /** @return the funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are associated with a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** Default constructor. */
    public CommunityProject() {
    }

    /** @return the funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup the funding group to set */
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
 * Represents a funding group.
 */
class FundingGroup {

    private String name;
    private FundingGroupType type;

    /** Default constructor. */
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

    /** @param type the type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}