import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company comprised of departments.
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
     * Calculate the sum of the budget amounts of all projects in all departments of a company.
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
     * Count the total number of employees working on production projects across all departments in a company.
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
 * Represents a department within a company.
 */
class Department {
    private String id;
    private String email;
    private List<Project> projects;
    private List<Employee> employees;

    public Department() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Calculate the average budget amount of all projects in this department.
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
     * Retrieve the funding group type of all community projects within this department.
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
 * Represents an employee working in a department on projects.
 */
class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

    public Employee() {
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

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

    public Project() {
        this.employees = new ArrayList<>();
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

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents a production project, characterized by a site code.
 */
class ProductionProject extends Project {
    private String siteCode;

    public ProductionProject() {
        super();
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Represents a research project.
 */
class ResearchProject extends Project {
    public ResearchProject() {
        super();
    }
}

/**
 * Represents an education project associated with a funding group.
 */
class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public EducationProject() {
        super();
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a community project associated with a funding group.
 */
class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    public CommunityProject() {
        super();
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Represents a funding group for education and community projects.
 */
class FundingGroup {
    private String type; // private group, government group, or mixed group

    public FundingGroup() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}