import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company comprised of departments.
 */
class Company {
    private List<Department> departments;

    /**
     * Constructs a new Company with an empty list of departments.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the list of departments in this company.
     *
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in this company.
     *
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to this company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (departments.size() < 8) {
            this.departments.add(department);
        }
    }

    /**
     * Calculates the sum of the budget amounts of all projects in all departments of this company.
     *
     * @return the total budget of all projects in the company
     */
    public double getTotalBudgetOfAllProjects() {
        double totalBudget = 0.0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                totalBudget += project.getBudgetAmount();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments in this company.
     *
     * @return the number of employees working on production projects
     */
    public int getNumberOfEmployeesWorkingOnProductionProjects() {
        int employeeCount = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    employeeCount += project.getEmployees().size();
        }
            }
        }
        return employeeCount;
    }
}

/**
 * Represents a department in a company.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    /**
     * Constructs a new Department with empty lists of projects and employees.
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the ID of this department.
     *
     * @return the department ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of this department.
     *
     * @param id the department ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of this department.
     *
     * @return the department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this department.
     *
     * @param email the department email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of projects in this department.
     *
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects in this department.
     *
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to this department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Gets the list of employees in this department.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in this department.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to this department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the average budget amount of all projects in this department.
     *
     * @return the average budget of all projects in the department, or 0 if there are no projects
     */
    public double getAverageBudgetOfAllProjects() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        
        double totalBudget = 0.0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return totalBudget / projects.size();
    }

    /**
     * Retrieves the funding group type of all community projects within this department.
     *
     * @return a list of funding group types for community projects
     */
    public List<String> getFundingGroupTypeOfAllCommunityProjects() {
        List<String> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                FundingGroup fundingGroup = ((CommunityProject) project).getFundingGroup();
                if (fundingGroup != null) {
                    fundingGroupTypes.add(fundingGroup.getType());
                }
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Represents an employee working on projects.
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Constructs a new Employee.
     */
    public Employee() {
    }

    /**
     * Gets the name of this employee.
     *
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this employee.
     *
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of this employee.
     *
     * @return the employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this employee.
     *
     * @param email the employee email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the ID of this employee.
     *
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the ID of this employee.
     *
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number of this employee.
     *
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number of this employee.
     *
     * @param employeeNumber the employee number to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Abstract class representing a project.
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;
    private List<Employee> employees;

    /**
     * Constructs a new Project with an empty list of employees.
     */
    public Project() {
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the title of this project.
     *
     * @return the project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this project.
     *
     * @param title the project title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of this project.
     *
     * @return the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this project.
     *
     * @param description the project description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the budget amount of this project.
     *
     * @return the project budget amount
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Sets the budget amount of this project.
     *
     * @param budgetAmount the project budget amount to set
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /**
     * Gets the deadline of this project.
     *
     * @return the project deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of this project.
     *
     * @param deadline the project deadline to set
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the list of employees working on this project.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees working on this project.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to this project.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}

/**
 * Represents a production project.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Constructs a new ProductionProject.
     */
    public ProductionProject() {
        super();
    }

    /**
     * Gets the site code of this production project.
     *
     * @return the site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code of this production project.
     *
     * @param siteCode the site code to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents a research project.
 */
class ResearchProject extends Project {
    /**
     * Constructs a new ResearchProject.
     */
    public ResearchProject() {
        super();
    }
}

/**
 * Represents an education project.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Constructs a new EducationProject.
     */
    public EducationProject() {
        super();
    }

    /**
     * Gets the funding group of this education project.
     *
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of this education project.
     *
     * @param fundingGroup the funding group to set
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
     * Constructs a new CommunityProject.
     */
    public CommunityProject() {
        super();
    }

    /**
     * Gets the funding group of this community project.
     *
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of this community project.
     *
     * @param fundingGroup the funding group to set
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
     * Constructs a new FundingGroup.
     */
    public FundingGroup() {
    }

    /**
     * Gets the type of this funding group.
     *
     * @return the funding group type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this funding group.
     *
     * @param type the funding group type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}