import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a Company that contains a collection of Departments.
 */
 class Company {

    /** List of departments belonging to the company (2..8). */
    private List<Department> departments;

    /** Default constructor initializing the departments list. */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department != null) {
            this.departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects in the company.
     *
     * @return the sum of the budget amounts of every project across all departments
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
     * Counts the total number of employees working on production projects
     * across all departments in the company. Employees may be counted multiple
     * times if they work on more than one production project.
     *
     * @return the number of employee‑project assignments for production projects
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
 * Represents a Department within a Company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /** Default constructor initializing the employee and project lists. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable view of the projects belonging to this department.
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
        if (project != null) {
            this.projects.add(project);
        }
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return the average budget, or 0.0 if there are no projects
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
     * Retrieves the funding group types of all community projects within this department.
     *
     * @return a list containing the {@link FundingGroupType} of each community project
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

    /** @return the department ID */
    public String getID() {
        return ID;
    }

    /** @param ID the department ID to set */
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

    /** @return the list of employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @param projects the list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
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
 * Represents an employee working for a department.
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
 * Abstract base class for all project types.
 */
abstract class Project {

    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /** Default constructor initializing the employee list. */
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

    /** @return the list of employees working on this project */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /** @param workingEmployees the list of employees to set */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Adds an employee to the list of working employees.
     *
     * @param employee the employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        if (employee != null) {
            this.workingEmployees.add(employee);
        }
    }
}

/**
 * Production projects are characterized by a site code.
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
 * Research projects have no additional attributes beyond the base {@link Project}.
 */
class ResearchProject extends Project {

    /** Default constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education projects are associated with a single funding group.
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
 * Community projects are associated with a single funding group.
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

    /** Default constructor. */
    public FundingGroup() {
    }

    /** @return the funding group name */
    public String getName() {
        return name;
    }

    /** @param name the funding group name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the funding group type */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type the funding group type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}