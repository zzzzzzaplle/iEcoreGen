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
     *
     * @param department The department to be added.
     */
    public void addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects in the company.
     *
     * @return The sum of the budget amounts of all projects in the company.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0.0;
        for (Department department : departments) {
            List<Project> projects = department.getProjects();
            for (Project project : projects) {
                totalBudget += project.getBudget();
            }
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments in the company.
     *
     * @return The number of employees working on production projects.
     */
    public int countEmployeesInProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            List<Project> projects = department.getProjects();
            for (Project project : projects) {
                if (project instanceof ProductionProject) {
                    count += project.getWorkingEmployees().size();
                }
            }
        }
        return count;
    }

    // Getter and setter for departments
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
     *
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        if (project != null) {
            projects.add(project);
        }
    }

    /**
     * Calculates the average budget amount of all projects in the department.
     *
     * @return The average budget amount of all projects in the department.
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
     * Retrieves the funding group type of all community projects within the department.
     *
     * @return A list of funding group types of all community projects in the department.
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

    // Getter and setter for ID
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for employees
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Getter and setter for projects
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
    }

    // Getter and setter for type
    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for ID
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    // Getter and setter for number
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

    // Getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and setter for budget
    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    // Getter and setter for deadline
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    // Getter and setter for workingEmployees
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
    }

    // Getter and setter for siteCode
    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

class ResearchProject extends Project {
    public ResearchProject() {
    }
}

class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public EducationProject() {
    }

    // Getter and setter for fundingGroup
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
    }

    // Getter and setter for fundingGroup
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
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for type
    public FundingGroupType getType() {
        return type;
    }

    public void setType(FundingGroupType type) {
        this.type = type;
    }
}