import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Company {
    private List<Department> departments;

    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department The department to add.
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Calculates the total budget of all projects in the company.
     * @return The total budget amount.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Department department : departments) {
            for (Project project : department.getProjects()) {
                totalBudget += project.getBudget();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the number of employees working on production projects in the company.
     * @return The count of employees working on production projects.
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

    // Getters and Setters
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}

class Department {
    private String ID;
    private String email;
    private List<Employee> employees;
    private List<Project> projects;

    public Department() {
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Adds a project to the department.
     * @param project The project to add.
     */
    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Calculates the average budget of all projects in the department.
     * @return The average budget amount.
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0;
        }
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudget();
        }
        return totalBudget / projects.size();
    }

    /**
     * Retrieves the funding group types of all community projects in the department.
     * @return A list of funding group types.
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> fundingGroupTypes = new ArrayList<>();
        for (Project project : projects) {
            if (project instanceof CommunityProject) {
                CommunityProject communityProject = (CommunityProject) project;
                fundingGroupTypes.add(communityProject.getFundingGroup().getType());
            }
        }
        return fundingGroupTypes;
    }

    // Getters and Setters
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
}

enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

class Employee {
    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    public Employee() {
        // Default constructor
    }

    // Getters and Setters
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

abstract class Project {
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees;

    public Project() {
        this.workingEmployees = new ArrayList<>();
    }

    // Getters and Setters
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
}

class ProductionProject extends Project {
    private String siteCode;

    public ProductionProject() {
        // Default constructor
    }

    // Getters and Setters
    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

class ResearchProject extends Project {
    public ResearchProject() {
        // Default constructor
    }
}

class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public EducationProject() {
        // Default constructor
    }

    // Getters and Setters
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    public CommunityProject() {
        // Default constructor
    }

    // Getters and Setters
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

class FundingGroup {
    private String name;
    private FundingGroupType type;

    public FundingGroup() {
        // Default constructor
    }

    // Getters and Setters
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