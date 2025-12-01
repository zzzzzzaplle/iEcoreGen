import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a company with multiple departments.
 */
 class Company {
    private List<Department> departments;

    /**
     * Default constructor.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Removes a department from the company.
     *
     * @param department the department to remove
     */
    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    /**
     * Gets all departments in the company.
     *
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the departments for the company.
     *
     * @param departments the list of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total budget of all projects in the company.
     *
     * @return the total budget
     */
    public double calculateTotalBudget() {
        return departments.stream()
                .flatMap(department -> department.getProjects().stream())
                .mapToDouble(Project::getBudget)
                .sum();
    }

    /**
     * Counts the total number of employees working on production projects in the company.
     *
     * @return the count of employees
     */
    public int countEmployeesOnProductionProjects() {
        return departments.stream()
                .flatMap(department -> department.getProjects().stream())
                .filter(project -> project instanceof ProductionProject)
                .flatMap(project -> ((ProductionProject) project).getEmployees().stream())
                .collect(Collectors.toList()).size();
    }
}

/**
 * Represents a department within a company.
 */
 class Department {
    private String id;
    private String email;
    private List<Project> projects;

    /**
     * Default constructor.
     */
    public Department() {
        this.projects = new ArrayList<>();
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Removes a project from the department.
     *
     * @param project the project to remove
     */
    public void removeProject(Project project) {
        projects.remove(project);
    }

    /**
     * Gets all projects in the department.
     *
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the projects for the department.
     *
     * @param projects the list of projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Gets the ID of the department.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the department.
     *
     * @param id the ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the department.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the department.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Calculates the average budget of all projects in the department.
     *
     * @return the average budget
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        return projects.stream()
                .mapToDouble(Project::getBudget)
                .average()
                .orElse(0.0);
    }

    /**
     * Retrieves the funding group type of all community projects within the department.
     *
     * @return the list of funding group types
     */
    public List<String> getFundingGroupTypesOfCommunityProjects() {
        return projects.stream()
                .filter(project -> project instanceof CommunityProject)
                .map(project -> ((CommunityProject) project).getFundingGroup().getType())
                .collect(Collectors.toList());
    }
}

/**
 * Represents an employee.
 */
 class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /**
     * Gets the name of the employee.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the employee.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the employee.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the ID of the employee.
     *
     * @return the ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the ID of the employee.
     *
     * @param employeeId the ID
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number.
     *
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number.
     *
     * @param employeeNumber the employee number
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Represents a project.
 */
public abstract class Project {
    private String title;
    private String description;
    private double budget;
    private String deadline;

    /**
     * Default constructor.
     */
    public Project() {
    }

    /**
     * Gets the title of the project.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the project.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the budget of the project.
     *
     * @return the budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the project.
     *
     * @param budget the budget
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Gets the deadline of the project.
     *
     * @return the deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the project.
     *
     * @param deadline the deadline
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}

/**
 * Represents a production project.
 */
 class ProductionProject extends Project {
    private String siteCode;
    private List<Employee> employees;

    /**
     * Default constructor.
     */
    public ProductionProject() {
        this.employees = new ArrayList<>();
    }

    /**
     * Adds an employee to the project.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes an employee from the project.
     *
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    /**
     * Gets all employees working on the project.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees for the project.
     *
     * @param employees the list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the site code of the project.
     *
     * @return the site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code of the project.
     *
     * @param siteCode the site code
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents an education project.
 */
 class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor.
     */
    public EducationProject() {
    }

    /**
     * Gets the funding group of the project.
     *
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the project.
     *
     * @param fundingGroup the funding group
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project.
 */
 class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor.
     */
    public CommunityProject() {
    }

    /**
     * Gets the funding group of the project.
     *
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the project.
     *
     * @param fundingGroup the funding group
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a funding group.
 */
 class FundingGroup {
    private String type;

    /**
     * Default constructor.
     */
    public FundingGroup() {
    }

    /**
     * Gets the type of the funding group.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the funding group.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
}