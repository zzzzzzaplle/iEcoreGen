import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class Project {
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees = new ArrayList<>();

    public Project(String title, String description, double budget, Date deadline) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.deadline = deadline;
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

    public void addWorkingEmployee(Employee employee) {
        this.workingEmployees.add(employee);
    }
}

class ProductionProject extends Project {
    private String siteCode;

    public ProductionProject(String title, String description, double budget, Date deadline, String siteCode) {
        super(title, description, budget, deadline);
        this.siteCode = siteCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

class ResearchProject extends Project {
    public ResearchProject(String title, String description, double budget, Date deadline) {
        super(title, description, budget, deadline);
    }
}

class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public EducationProject(String title, String description, double budget, Date deadline, FundingGroup fundingGroup) {
        super(title, description, budget, deadline);
        this.fundingGroup = fundingGroup;
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    public CommunityProject(String title, String description, double budget, Date deadline, FundingGroup fundingGroup) {
        super(title, description, budget, deadline);
        this.fundingGroup = fundingGroup;
    }

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

enum FundingGroupType {
    PRIVATE, GOVERNMENT, MIXED
}

class FundingGroup {
    private String name;
    private FundingGroupType type;

    public FundingGroup(String name, FundingGroupType type) {
        this.name = name;
        this.type = type;
    }

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

enum EmployeeType {
    TEMPORARY, PERMANENT
}

class Employee {
    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    public Employee(EmployeeType type, String name, String email, String ID, String number) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.number = number;
    }

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

class Department {
    private String ID;
    private String email;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public Department(String ID, String email) {
        this.ID = ID;
        this.email = email;
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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Calculates the average budget amount of all projects in the department.
     *
     * @return The average budget amount.
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Project project : projects) {
            sum += project.getBudget();
        }
        return sum / projects.size();
    }

    /**
     * Retrieves the funding group type of all community projects within the department.
     *
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
}

class Company {
    private List<Department> departments = new ArrayList<>();

    public Company() {
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Calculates the sum of the budget amounts of all projects in all departments of the company.
     *
     * @return The total budget amount.
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
     * Counts the total number of employees working on production projects across all departments in the company.
     *
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
}