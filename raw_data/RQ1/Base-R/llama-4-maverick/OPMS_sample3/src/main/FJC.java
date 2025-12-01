import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company with departments and projects.
 */
class Company {
    private List<Department> departments;

    /**
     * Default constructor to initialize a company.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
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
     * @return the total number of employees working on production projects
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
 * Represents a department with an ID, email, and projects.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;

    /**
     * Default constructor to initialize a department.
     */
    public Department() {
        this.projects = new ArrayList<>();
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
     * Gets the list of projects in the department.
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects in the department.
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
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
     * Calculates the average budget amount of all projects in the department.
     * @return the average budget amount
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0;
        }
        return calculateTotalBudget() / projects.size();
    }

    /**
     * Counts the total number of employees working on production projects in the department.
     * @return the total number of employees working on production projects
     */
    public int countEmployeesOnProductionProjects() {
        int count = 0;
        for (Project project : projects) {
            if (project instanceof ProductionProject) {
                count += project.getEmployees().size();
            }
        }
        return count;
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
 * Represents a project with a title, description, budget amount, and deadline.
 */
abstract class Project {
    private String title;
    private String description;
    private double budgetAmount;
    private String deadline;
    private List<Employee> employees;

    /**
     * Default constructor to initialize a project.
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
     * Gets the list of employees working on the project.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees working on the project.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents a production project with a site code.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Default constructor to initialize a production project.
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
 * Represents a community project with a funding group.
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor to initialize a community project.
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
 * Represents an education project with a funding group.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor to initialize an education project.
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
 * Represents a research project.
 */
class ResearchProject extends Project {
    /**
     * Default constructor to initialize a research project.
     */
    public ResearchProject() {}
}

/**
 * Represents a funding group with a type.
 */
class FundingGroup {
    private String type;

    /**
     * Default constructor to initialize a funding group.
     */
    public FundingGroup() {}

    /**
     * Gets the type of the funding group.
     * @return the type
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
 * Represents an employee with a name, email, employee ID, and employee number.
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    /**
     * Default constructor to initialize an employee.
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
     * Gets the employee ID of the employee.
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID of the employee.
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee number of the employee.
     * @return the employee number
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the employee number of the employee.
     * @param employeeNumber the employee number to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}