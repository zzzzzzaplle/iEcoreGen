import java.util.*;
import java.util.Date;

/**
 * Represents a type of employee
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents the type of funding group
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group that can support education and community projects
 */
class FundingGroup {
    private String name;
    private FundingGroupType type;
    
    public FundingGroup() {}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public FundingGroupType getType() {
        return type;
    }
    
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}

/**
 * Abstract base class representing a project
 */
abstract class Project {
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;
    
    public Project() {
        this.workingEmployees = new ArrayList<>();
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
    }
    
    public Date getDeadline() {
        return deadline;
    }
    
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }
    
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
    
    /**
     * Adds an employee to work on this project
     * @param employee the employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        this.workingEmployees.add(employee);
    }
}

/**
 * Represents a production project with a site code
 */
class ProductionProject extends Project {
    private String siteCode;
    
    public ProductionProject() {}
    
    public String getSiteCode() {
        return siteCode;
    }
    
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents a research project
 */
class ResearchProject extends Project {
    public ResearchProject() {}
}

/**
 * Represents an education project with associated funding group
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;
    
    public EducationProject() {}
    
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }
    
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project with associated funding group
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;
    
    public CommunityProject() {}
    
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }
    
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents an employee in the company
 */
class Employee {
    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;
    
    public Employee() {}
    
    public EmployeeType getType() {
        return type;
    }
    
    public void setType(EmployeeType type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Represents a department in the company
 */
class Department {
    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;
    
    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public List<Project> getProjects() {
        return projects;
    }
    
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    /**
     * Adds an employee to the department
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
    
    /**
     * Adds a project to the department
     * @param project the project to add
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }
    
    /**
     * Calculates the average budget of all projects in the department
     * @return the average budget amount of all projects in the department
     * @throws ArithmeticException if there are no projects in the department
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            throw new ArithmeticException("Cannot calculate average budget: department has no projects");
        }
        
        double total = 0;
        for (Project project : projects) {
            total += project.getBudget();
        }
        return total / projects.size();
    }
    
    /**
     * Retrieves the funding group type of all community projects in the department
     * @return a list of funding group types for all community projects in the department
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
 * Represents a company with multiple departments
 */
class Company {
    private List<Department> departments;
    
    public Company() {
        this.departments = new ArrayList<>();
    }
    
    public List<Department> getDepartments() {
        return departments;
    }
    
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
     * Calculates the total budget of all projects in all departments of the company
     * @return the sum of budget amounts of all projects across all departments
     */
    public double calculateTotalBudget() {
        double total = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                total += project.getBudget();
            }
        }
        return total;
    }
    
    /**
     * Counts the total number of employees working on production projects across all departments
     * @return the total number of employees working on production projects
     */
    public int countEmployeesInProductionProjects() {
        Set<Employee> uniqueEmployees = new HashSet<>();
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                if (project instanceof ProductionProject) {
                    uniqueEmployees.addAll(project.getWorkingEmployees());
                }
            }
        }
        return uniqueEmployees.size();
    }
}