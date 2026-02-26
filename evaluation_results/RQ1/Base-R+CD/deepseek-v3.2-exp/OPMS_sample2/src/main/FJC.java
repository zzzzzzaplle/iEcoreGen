import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a company with multiple departments
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
     * Gets the list of departments
     * @return List of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments
     * @param departments List of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the company
     * @param department Department to add
     */
    public void addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects in all departments of the company
     * @return Total budget amount as double
     */
    public double calculateTotalBudget() {
        double total = 0.0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                total += project.getBudget();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees working on production projects across all departments
     * @return Total number of employees working on production projects
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
}

/**
 * Represents a department in a company
 */
class Department {
    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    /**
     * Default constructor
     */
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Gets the department ID
     * @return Department ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the department ID
     * @param ID Department ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the department email
     * @return Department email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the department email
     * @param email Department email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of employees
     * @return List of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees
     * @param employees List of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of projects
     * @return List of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of projects
     * @param projects List of projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the department
     * @param project Project to add
     */
    public void addProject(Project project) {
        if (project != null) {
            projects.add(project);
        }
    }

    /**
     * Calculates the average budget amount of all projects in the department
     * @return Average budget amount as double, returns 0 if no projects exist
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Project project : projects) {
            total += project.getBudget();
        }
        return total / projects.size();
    }

    /**
     * Retrieves the funding group type of all community projects within the department
     * @return List of funding group types for community projects
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
}

/**
 * Enum representing different types of employees
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee in a department
 */
class Employee {
    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * Gets the employee type
     * @return Employee type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the employee type
     * @param type Employee type
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the employee name
     * @return Employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name
     * @param name Employee name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee email
     * @return Employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee email
     * @param email Employee email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the employee ID
     * @return Employee ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the employee ID
     * @param ID Employee ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the employee number
     * @return Employee number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the employee number
     * @param number Employee number
     */
    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Abstract class representing a project
 */
abstract class Project {
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    /**
     * Default constructor
     */
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    /**
     * Gets the project title
     * @return Project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the project title
     * @param title Project title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the project description
     * @return Project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the project description
     * @param description Project description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project budget
     * @return Project budget amount
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the project budget
     * @param budget Project budget amount
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Gets the project deadline
     * @return Project deadline date
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets the project deadline
     * @param deadline Project deadline date
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the list of employees working on the project
     * @return List of working employees
     */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /**
     * Sets the list of employees working on the project
     * @param workingEmployees List of working employees
     */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
}

/**
 * Represents a production project
 */
class ProductionProject extends Project {
    private String siteCode;

    /**
     * Default constructor
     */
    public ProductionProject() {
    }

    /**
     * Gets the site code
     * @return Site code
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the site code
     * @param siteCode Site code
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
    public ResearchProject() {
    }
}

/**
 * Represents an education project
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor
     */
    public EducationProject() {
    }

    /**
     * Gets the funding group
     * @return Funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group
     * @param fundingGroup Funding group
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    /**
     * Default constructor
     */
    public CommunityProject() {
    }

    /**
     * Gets the funding group
     * @return Funding group
     */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /**
     * Sets the funding group
     * @param fundingGroup Funding group
     */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Enum representing different types of funding groups
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group
 */
class FundingGroup {
    private String name;
    private FundingGroupType type;

    /**
     * Default constructor
     */
    public FundingGroup() {
    }

    /**
     * Gets the funding group name
     * @return Funding group name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the funding group name
     * @param name Funding group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the funding group type
     * @return Funding group type
     */
    public FundingGroupType getType() {
        return type;
    }

    /**
     * Sets the funding group type
     * @param type Funding group type
     */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}