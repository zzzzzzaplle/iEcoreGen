import java.util.*;

/**
 * Represents a company that consists of multiple departments.
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
     * Adds a department to the company.
     * 
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (departments.size() < 8) {
            departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects across all departments in the company.
     * 
     * @return the sum of all project budgets
     */
    public double calculateTotalBudget() {
        double totalBudget = 0.0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                totalBudget += project.getBudget();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments.
     * 
     * @return the number of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    count += project.getWorkingEmployees().size();
                }
            }
        }
        return count;
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
     * @param departments the list of departments to set
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
     * Constructs a new Department with empty lists of employees and projects.
     */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Gets all projects in this department.
     * 
     * @return the list of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Adds a project to this department.
     * 
     * @param project the project to add
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Calculates the average budget of all projects in this department.
     * 
     * @return the average budget, or 0 if there are no projects
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }

        double totalBudget = 0.0;
        for (Project project : projects) {
            totalBudget += project.getBudget();
        }
        return totalBudget / projects.size();
    }

    /**
     * Retrieves the funding group types of all community projects in this department.
     * 
     * @return a list of funding group types for community projects
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> fundingGroupTypes = new ArrayList<>();
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

    /**
     * Gets the ID of this department.
     * 
     * @return the department ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of this department.
     * 
     * @param ID the department ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
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
     * Sets the list of projects in this department.
     * 
     * @param projects the list of projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}

/**
 * Enum representing the type of an employee.
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee working in a department.
 */
class Employee {
    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    /**
     * Constructs a new Employee with default values.
     */
    public Employee() {
        // Default constructor
    }

    /**
     * Gets the type of this employee.
     * 
     * @return the employee type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of this employee.
     * 
     * @param type the employee type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
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
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of this employee.
     * 
     * @param ID the employee ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the number of this employee.
     * 
     * @return the employee number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number of this employee.
     * 
     * @param number the employee number to set
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
     * Constructs a new Project with an empty list of working employees.
     */
    public Project() {
        this.workingEmployees = new ArrayList<>();
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
     * Gets the budget of this project.
     * 
     * @return the project budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of this project.
     * 
     * @param budget the project budget to set
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Gets the deadline of this project.
     * 
     * @return the project deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline of this project.
     * 
     * @param deadline the project deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the list of employees working on this project.
     * 
     * @return the list of working employees
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Sets the list of employees working on this project.
     * 
     * @param workingEmployees the list of working employees to set
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
}

/**
 * Represents a production project, which is a type of project with a site code.
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
 * Represents a research project, which is a type of project.
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
 * Represents an education project, which is a type of project with a funding group.
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
 * Represents a community project, which is a type of project with a funding group.
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
 * Enum representing the type of a funding group.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group that supports certain projects.
 */
class FundingGroup {
    private String name;
    private FundingGroupType type;

    /**
     * Constructs a new FundingGroup.
     */
    public FundingGroup() {
        // Default constructor
    }

    /**
     * Gets the name of this funding group.
     * 
     * @return the funding group name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this funding group.
     * 
     * @param name the funding group name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of this funding group.
     * 
     * @return the funding group type
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the type of this funding group.
     * 
     * @param type the funding group type to set
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}