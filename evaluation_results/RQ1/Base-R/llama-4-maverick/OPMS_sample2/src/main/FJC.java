import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company with departments.
 */
class Company {
    private List<Department> departments;

    /**
     * Constructs a new Company object.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the departments in the company.
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total budget of all projects in the company.
     * @return the total budget amount
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Department department : departments) {
            totalBudget += department.calculateTotalBudget();
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments.
     * @return the number of employees working on production projects
     */
    public int countEmployeesOnProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            count += department.countEmployeesOnProductionProjects();
        }
        return count;
    }
}

/**
 * Represents a department with an ID and email.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    /**
     * Constructs a new Department object.
     */
    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the ID of the department.
     * @return the department ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the department.
     * @param id the department ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the email of the department.
     * @return the department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the department.
     * @param email the department email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the projects in the department.
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the projects in the department.
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Gets the employees in the department.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees in the department.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Calculates the total budget of all projects in the department.
     * @return the total budget amount
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return totalBudget;
    }

    /**
     * Calculates the average budget of all projects in the department.
     * @return the average budget amount
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0;
        }
        return calculateTotalBudget() / projects.size();
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

    /**
     * Counts the number of employees working on production projects in the department.
     * @return the number of employees working on production projects
     */
    public int countEmployeesOnProductionProjects() {
        int count = 0;
        for (Employee employee : employees) {
            for (Project project : employee.getProjects()) {
                if (project instanceof ProductionProject) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}

/**
 * Represents an employee with a name, email, employee ID, and employee number.
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;
    private List<Project> projects;

    /**
     * Constructs a new Employee object.
     */
    public Employee() {
        this.projects = new ArrayList<>();
    }

    /**
     * Gets the name of the employee.
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the employee.
     * @return the employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the employee.
     * @param email the employee email to set
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

    /**
     * Gets the projects the employee is working on.
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the projects the employee is working on.
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}

/**
 * Represents a project with a title, description, budget amount, and deadline.
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;

    /**
     * Constructs a new Project object.
     */
    public Project() {}

    /**
     * Gets the title of the project.
     * @return the project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     * @param title the project title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the project.
     * @return the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     * @param description the project description to set
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
     * @return the project deadline
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the project.
     * @param deadline the project deadline to set
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}

/**
 * Represents a production project with a site code.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Constructs a new ProductionProject object.
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
     * Constructs a new EducationProject object.
     */
    public EducationProject() {}

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
     * Constructs a new CommunityProject object.
     */
    public CommunityProject() {}

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
     * Constructs a new ResearchProject object.
     */
    public ResearchProject() {}
}

/**
 * Represents a funding group with a type.
 */
class FundingGroup {
    private String type;

    /**
     * Constructs a new FundingGroup object.
     */
    public FundingGroup() {}

    /**
     * Gets the type of the funding group.
     * @return the funding group type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the funding group.
     * @param type the funding group type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}