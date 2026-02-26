import java.util.ArrayList;
import java.util.List;

/**
 * Represents a funding group with a specific type.
 */
class FundingGroup {
    private String type;

    /**
     * Unparameterized constructor for FundingGroup.
     */
    public FundingGroup() {}

    /**
     * Gets the type of the funding group.
     * @return the type of the funding group
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the funding group.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}

/**
 * Represents a project with common attributes.
 */
abstract class Project {
    protected String title;
    protected String description;
    protected double budgetAmount;
    protected String deadline;
    protected List<Employee> employees;

    /**
     * Unparameterized constructor for Project.
     */
    public Project() {
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the title of the project.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the project.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the budget amount of the project.
     * @return the budget amount
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }

    /**
     * Sets the budget amount of the project.
     * @param budgetAmount the budget amount to set
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    /**
     * Gets the deadline of the project.
     * @return the deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the project.
     * @param deadline the deadline to set
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Adds an employee to the project.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Gets the list of employees working on the project.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }
}

/**
 * Represents a production project with a site code.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Unparameterized constructor for ProductionProject.
     */
    public ProductionProject() {}

    /**
     * Gets the site code of the production project.
     * @return the site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code of the production project.
     * @param siteCode the site code to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents an education project with a funding group.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Unparameterized constructor for EducationProject.
     */
    public EducationProject() {
        this.fundingGroup = new FundingGroup();
    }

    /**
     * Gets the funding group of the education project.
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the education project.
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project with a funding group.
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Unparameterized constructor for CommunityProject.
     */
    public CommunityProject() {
        this.fundingGroup = new FundingGroup();
    }

    /**
     * Gets the funding group of the community project.
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the community project.
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a research project.
 */
class ResearchProject extends Project {
    /**
     * Unparameterized constructor for ResearchProject.
     */
    public ResearchProject() {}
}

/**
 * Represents an employee with identifying attributes.
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the employee.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the employee.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the employee ID.
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID.
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number.
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number.
     * @param employeeNumber the employee number to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}

/**
 * Represents a department with an ID, email, and list of projects and employees.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    /**
     * Unparameterized constructor for Department.
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the ID of the department.
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the department.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the department.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the department.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Adds a project to the department.
     * @param project the project to add
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Gets the list of projects in the department.
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Adds an employee to the department.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Gets the list of employees in the department.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Calculates the average budget amount of all projects in the department.
     * @return the average budget amount
     */
    public double calculateAverageBudget() {
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return projects.isEmpty() ? 0 : totalBudget / projects.size();
    }

    /**
     * Retrieves the funding group type of all community projects within the department.
     * @return a list of funding group types
     */
    public List<String> getFundingGroupTypesOfCommunityProjects() {
        List<String> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                fundingGroupTypes.add(((CommunityProject) project).getFundingGroup().getType());
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Represents a company with a list of departments.
 */
class Company {
    private List<Department> departments;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Calculates the total budget amount of all projects in the company.
     * @return the total budget amount
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                totalBudget += project.getBudgetAmount();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments.
     * @return the total number of employees
     */
    public int countEmployeesOnProductionProjects() {
        int totalEmployees = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    totalEmployees += project.getEmployees().size();
                }
            }
        }
        return totalEmployees;
    }
}