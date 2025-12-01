import java.util.ArrayList;
import java.util.List;

/**
 * Represents a funding group that provides funding for projects
 */
class FundingGroup {
    private String type;
    
    /**
     * Default constructor
     */
    public FundingGroup() {}
    
    /**
     * Gets the type of funding group
     * @return the type of funding group
     */
    public String getType() {
        return type;
    }
    
    /**
     * Sets the type of funding group
     * @param type the type of funding group to set
     */
    public void setType(String type) {
        this.type = type;
    }
}

/**
 * Abstract base class for all types of projects
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;
    private List<Employee> workingEmployees;
    
    /**
     * Default constructor
     */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }
    
    /**
     * Gets the project title
     * @return the project title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the project title
     * @param title the project title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the project description
     * @return the project description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the project description
     * @param description the project description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the budget amount
     * @return the budget amount
     */
    public double getBudgetAmount() {
        return budgetAmount;
    }
    
    /**
     * Sets the budget amount
     * @param budgetAmount the budget amount to set
     */
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    
    /**
     * Gets the project deadline
     * @return the project deadline
     */
    public String getDeadline() {
        return deadline;
    }
    
    /**
     * Sets the project deadline
     * @param deadline the project deadline to set
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    
    /**
     * Gets the list of employees working on this project
     * @return list of employees working on this project
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }
    
    /**
     * Sets the list of employees working on this project
     * @param workingEmployees the list of employees to set
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
    
    /**
     * Adds an employee to the project
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.workingEmployees.add(employee);
    }
    
    /**
     * Removes an employee from the project
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        this.workingEmployees.remove(employee);
    }
}

/**
 * Represents a production project characterized by a site code
 */
class ProductionProject extends Project {
    private String siteCode;
    
    /**
     * Default constructor
     */
    public ProductionProject() {}
    
    /**
     * Gets the site code
     * @return the site code
     */
    public String getSiteCode() {
        return siteCode;
    }
    
    /**
     * Sets the site code
     * @param siteCode the site code to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents a research project
 */
class ResearchProject extends Project {
    /**
     * Default constructor
     */
    public ResearchProject() {}
}

/**
 * Represents an education project associated with a funding group
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;
    
    /**
     * Default constructor
     */
    public EducationProject() {}
    
    /**
     * Gets the funding group
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }
    
    /**
     * Sets the funding group
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project associated with a funding group
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;
    
    /**
     * Default constructor
     */
    public CommunityProject() {}
    
    /**
     * Gets the funding group
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }
    
    /**
     * Sets the funding group
     * @param fundingGroup the funding group to set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents an employee in the company
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;
    private boolean isTemporary;
    
    /**
     * Default constructor
     */
    public Employee() {}
    
    /**
     * Gets the employee name
     * @return the employee name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the employee name
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the employee email
     * @return the employee email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the employee email
     * @param email the employee email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the employee ID
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }
    
    /**
     * Sets the employee ID
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * Gets the employee number
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    /**
     * Sets the employee number
     * @param employeeNumber the employee number to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    /**
     * Checks if the employee is temporary
     * @return true if the employee is temporary, false otherwise
     */
    public boolean isTemporary() {
        return isTemporary;
    }
    
    /**
     * Sets whether the employee is temporary
     * @param temporary true if the employee is temporary, false otherwise
     */
    public void setTemporary(boolean temporary) {
        isTemporary = temporary;
    }
}

/**
 * Represents a department in the company
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;
    
    /**
     * Default constructor
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
    
    /**
     * Gets the department ID
     * @return the department ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the department ID
     * @param id the department ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Gets the department email
     * @return the department email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the department email
     * @param email the department email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the list of projects in the department
     * @return list of projects in the department
     */
    public List<Project> getProjects() {
        return projects;
    }
    
    /**
     * Sets the list of projects in the department
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    /**
     * Gets the list of employees in the department
     * @return list of employees in the department
     */
    public List<Employee> getEmployees() {
        return employees;
    }
    
    /**
     * Sets the list of employees in the department
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    /**
     * Adds a project to the department
     * @param project the project to add
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }
    
    /**
     * Removes a project from the department
     * @param project the project to remove
     */
    public void removeProject(Project project) {
        this.projects.remove(project);
    }
    
    /**
     * Adds an employee to the department
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
    
    /**
     * Removes an employee from the department
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }
    
    /**
     * Calculates the average budget amount of all projects in this department
     * @return the average budget amount of all projects in the department
     * @throws ArithmeticException if there are no projects in the department
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            throw new ArithmeticException("Cannot calculate average budget: department has no projects");
        }
        
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return totalBudget / projects.size();
    }
    
    /**
     * Retrieves the funding group types of all community projects within this department
     * @return list of funding group types for all community projects in the department
     */
    public List<String> getCommunityProjectFundingGroupTypes() {
        List<String> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                CommunityProject communityProject = (CommunityProject) project;
                if (communityProject.getFundingGroup() != null) {
                    fundingGroupTypes.add(communityProject.getFundingGroup().getType());
                }
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Represents a company comprised of departments
 */
class Company {
    private List<Department> departments;
    
    /**
     * Default constructor
     */
    public Company() {
        this.departments = new ArrayList<>();
    }
    
    /**
     * Gets the list of departments in the company
     * @return list of departments in the company
     */
    public List<Department> getDepartments() {
        return departments;
    }
    
    /**
     * Sets the list of departments in the company
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    /**
     * Adds a department to the company
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }
    
    /**
     * Calculates the total budget of all projects across all departments in the company
     * @return the sum of budget amounts of all projects in all departments
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
     * Counts the total number of employees working on production projects across all departments
     * @return the total number of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        int employeeCount = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    employeeCount += project.getWorkingEmployees().size();
                }
            }
        }
        return employeeCount;
    }
}