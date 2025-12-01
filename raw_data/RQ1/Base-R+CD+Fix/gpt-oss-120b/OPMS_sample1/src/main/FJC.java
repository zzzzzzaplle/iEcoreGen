import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a company that contains a collection of departments.
 */
 class Company {

    private List<Department> departments;

    /** Unparameterized constructor initializing the departments list. */
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
     * Returns the list of departments belonging to the company.
     *
     * @return list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments belonging to the company.
     *
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total budget of all projects across all departments in the company.
     *
     * @return the sum of the budget amounts of all projects
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
}

/**
 * Represents a department within a company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /** Unparameterized constructor initializing collections. */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Returns the department identifier.
     *
     * @return department ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the department identifier.
     *
     * @param ID the department ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the department email address.
     *
     * @return department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the department email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the list of employees belonging to the department.
     *
     * @return list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees belonging to the department.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null) {
            this.employees.add(employee);
        }
    }

    /**
     * Returns the list of projects managed by the department.
     *
     * @return list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects managed by the department.
     *
     * @param projects the list of projects to set
     */
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
            this.projects.add(project);
        }
    }

    /**
     * Calculates the average budget of all projects within this department.
     *
     * @return the average budget amount, or 0.0 if there are no projects
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
     * Retrieves the funding group types of all community projects in this department.
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
}

/**
 * Enumeration of possible employee types.
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

    /** Unparameterized constructor. */
    public Employee() {
    }

    /**
     * Returns the employee type.
     *
     * @return employee type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the employee type.
     *
     * @param type the employee type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Returns the employee's name.
     *
     * @return employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the employee's email address.
     *
     * @return employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the employee identifier.
     *
     * @return employee ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the employee identifier.
     *
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the employee number.
     *
     * @return employee number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the employee number.
     *
     * @param number the number to set
     */
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

    /** Unparameterized constructor initializing the employee list. */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /**
     * Returns the project title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the project title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the project description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the project description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the project budget.
     *
     * @return budget amount
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the project budget.
     *
     * @param budget the budget to set
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Returns the project deadline.
     *
     * @return deadline date
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the project deadline.
     *
     * @param deadline the deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the list of employees working on this project.
     *
     * @return list of working employees
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Sets the list of employees working on this project.
     *
     * @param workingEmployees the list to set
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Adds an employee to the project's working employee list.
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
 * Production project, characterized by a site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** Unparameterized constructor. */
    public ProductionProject() {
        super();
    }

    /**
     * Returns the site code associated with the production project.
     *
     * @return site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code for the production project.
     *
     * @param siteCode the site code to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research project.
 */
class ResearchProject extends Project {

    /** Unparameterized constructor. */
    public ResearchProject() {
        super();
    }
}

/**
 * Education project, linked to a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** Unparameterized constructor. */
    public EducationProject() {
        super();
    }

    /**
     * Returns the funding group associated with the education project.
     *
     * @return funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group for the education project.
     *
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community project, linked to a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** Unparameterized constructor. */
    public CommunityProject() {
        super();
    }

    /**
     * Returns the funding group associated with the community project.
     *
     * @return funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group for the community project.
     *
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enumeration of funding group types.
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

    /** Unparameterized constructor. */
    public FundingGroup() {
    }

    /**
     * Returns the name of the funding group.
     *
     * @return group name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the funding group.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the type of the funding group.
     *
     * @return funding group type
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the type of the funding group.
     *
     * @param type the funding group type to set
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}