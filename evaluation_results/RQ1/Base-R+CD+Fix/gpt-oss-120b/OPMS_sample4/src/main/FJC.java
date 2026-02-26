import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    /** Departments belonging to this company (2..8). */
    private List<Department> departments;

    /** Default constructor initializing the department list. */
    public Company() {
        this.departments = new ArrayList<>();
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
     */
    public void addDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("department cannot be null");
        }
        this.departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the company.
     *
     * @return the sum of the budget amounts of all projects across all departments
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
     * Counts the total number of employees working on production projects across all departments.
     *
     * @return the total number of employees assigned to production projects
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

    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /** Default constructor initializing internal collections. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /** @return department ID */
    public String getID() {
        return ID;
    }

    /** @param ID the department ID to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return department email */
    public String getEmail() {
        return email;
    }

    /** @param email the department email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return list of employees belonging to the department */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the employee list to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return list of projects belonging to the department */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("project cannot be null");
        }
        this.projects.add(project);
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return the average budget amount, or 0.0 if there are no projects
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
     * @return a list of funding group types for each community project (empty list if none)
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
            throw new IllegalArgumentException("employee cannot be null");
        }
        this.employees.add(employee);
    }
}

/**
 * Types of employees.
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

    /** @return employee type */
    public EmployeeType getType() {
        return type;
    }

    /** @param type the employee type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @param name the employee name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee email */
    public String getEmail() {
        return email;
    }

    /** @param email the employee email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return employee ID */
    public String getID() {
        return ID;
    }

    /** @param ID the employee ID to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return employee number */
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

    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /** Default constructor initializing employee list. */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /** @return project title */
    public String getTitle() {
        return title;
    }

    /** @param title the project title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description */
    public String getDescription() {
        return description;
    }

    /** @param description the project description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return project budget */
    public double getBudget() {
        return budget;
    }

    /** @param budget the project budget to set */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /** @return project deadline */
    public Date getDeadline() {
        return deadline;
    }

    /** @param deadline the project deadline to set */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /** @return list of employees working on the project */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Assigns an employee to work on this project.
     *
     * @param employee the employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("employee cannot be null");
        }
        this.workingEmployees.add(employee);
    }
}

/**
 * Production project with a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** Default constructor. */
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
 * Research project (no additional attributes).
 */
class ResearchProject extends Project {

    /** Default constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education project associated with a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** Default constructor. */
    public EducationProject() {
        super();
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
 * Community project associated with a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** Default constructor. */
    public CommunityProject() {
        super();
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

    /** @return name of the funding group */
    public String getName() {
        return name;
    }

    /** @param name the funding group name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return type of the funding group */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type the funding group type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}