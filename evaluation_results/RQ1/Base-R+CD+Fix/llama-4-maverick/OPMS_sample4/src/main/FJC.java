import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a company with multiple departments.
 */
class Company {
    private List<Department> departments;

    /**
     * Default constructor to initialize the company object.
     */
    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * 
     * @param department the department to be added
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the company.
     * 
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
     * 
     * @return the total number of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        int totalEmployees = 0;
        for (Department department : departments) {
            totalEmployees += department.countEmployeesInProductionProjects();
        }
        return totalEmployees;
    }

    /**
     * Gets the list of departments in the company.
     * 
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     * 
     * @param departments the list of departments to be set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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

    /**
     * Default constructor to initialize the department object.
     */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Gets the ID of the department.
     * 
     * @return the department ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the department.
     * 
     * @param ID the department ID to be set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the email of the department.
     * 
     * @return the department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the department.
     * 
     * @param email the department email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of employees in the department.
     * 
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the department.
     * 
     * @param employees the list of employees to be set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of projects in the department.
     * 
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects in the department.
     * 
     * @param projects the list of projects to be set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the department.
     * 
     * @param project the project to be added
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Calculates the total budget of all projects in the department.
     * 
     * @return the total budget amount
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudget();
        }
        return totalBudget;
    }

    /**
     * Calculates the average budget of all projects in the department.
     * 
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
     * 
     * @return the total number of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        int totalEmployees = 0;
        for (Project project : projects) {
            if (project instanceof ProductionProject) {
                totalEmployees += project.getWorkingEmployees().size();
            }
        }
        return totalEmployees;
    }

    /**
     * Retrieves the funding group type of all community projects within the department.
     * 
     * @return the list of funding group types
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                fundingGroupTypes.add(((CommunityProject) project).getFundingGroup().getType());
            }
        }
        return fundingGroupTypes;
    }
}

/**
 * Enum representing the type of employee.
 */
enum EmployeeType {
    TEMPORARY, PERMANENT
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

    /**
     * Default constructor to initialize the employee object.
     */
    public Employee() {}

    /**
     * Gets the type of the employee.
     * 
     * @return the employee type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     * 
     * @param type the employee type to be set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * 
     * @param name the employee name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the employee.
     * 
     * @return the employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the employee.
     * 
     * @param email the employee email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the ID of the employee.
     * 
     * @return the employee ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the employee.
     * 
     * @param ID the employee ID to be set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the number of the employee.
     * 
     * @return the employee number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number of the employee.
     * 
     * @param number the employee number to be set
     */
    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Abstract class representing a project.
 */
abstract class Project {
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /**
     * Default constructor to initialize the project object.
     */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /**
     * Gets the title of the project.
     * 
     * @return the project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the project.
     * 
     * @param title the project title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the project.
     * 
     * @return the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     * 
     * @param description the project description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the budget of the project.
     * 
     * @return the project budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the project.
     * 
     * @param budget the project budget to be set
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Gets the deadline of the project.
     * 
     * @return the project deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of the project.
     * 
     * @param deadline the project deadline to be set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the list of employees working on the project.
     * 
     * @return the list of working employees
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Sets the list of employees working on the project.
     * 
     * @param workingEmployees the list of working employees to be set
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
}

/**
 * Represents a production project.
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Default constructor to initialize the production project object.
     */
    public ProductionProject() {}

    /**
     * Gets the site code of the production project.
     * 
     * @return the site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code of the production project.
     * 
     * @param siteCode the site code to be set
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
     * Default constructor to initialize the research project object.
     */
    public ResearchProject() {}
}

/**
 * Represents an education project.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor to initialize the education project object.
     */
    public EducationProject() {}

    /**
     * Gets the funding group of the education project.
     * 
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the education project.
     * 
     * @param fundingGroup the funding group to be set
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
     * Default constructor to initialize the community project object.
     */
    public CommunityProject() {}

    /**
     * Gets the funding group of the community project.
     * 
     * @return the funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group of the community project.
     * 
     * @param fundingGroup the funding group to be set
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enum representing the type of funding group.
 */
enum FundingGroupType {
    PRIVATE, GOVERNMENT, MIXED
}

/**
 * Represents a funding group.
 */
class FundingGroup {
    private String name;
    private FundingGroupType type;

    /**
     * Default constructor to initialize the funding group object.
     */
    public FundingGroup() {}

    /**
     * Gets the name of the funding group.
     * 
     * @return the funding group name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the funding group.
     * 
     * @param name the funding group name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the funding group.
     * 
     * @return the funding group type
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the type of the funding group.
     * 
     * @param type the funding group type to be set
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}