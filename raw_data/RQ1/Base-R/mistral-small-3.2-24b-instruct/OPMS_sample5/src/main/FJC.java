import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments;

    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Calculates the total budget of all projects in the company.
     * @return The sum of the budget amounts of all projects in all departments.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0.0;
        for (Department department : departments) {
            totalBudget += department.calculateTotalBudget();
        }
        return totalBudget;
    }

    /**
     * Counts the total number of employees working on production projects across all departments.
     * @return The total number of employees working on production projects.
     */
    public int countEmployeesOnProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            count += department.countEmployeesOnProductionProjects();
        }
        return count;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }
}

class Department {
    private String id;
    private String email;
    private List<Project> projects;

    public Department() {
        this.projects = new ArrayList<>();
    }

    /**
     * Calculates the total budget of all projects in the department.
     * @return The sum of the budget amounts of all projects in the department.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0.0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return totalBudget;
    }

    /**
     * Calculates the average budget amount of all projects in the department.
     * @return The average budget amount of all projects in the department.
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        return calculateTotalBudget() / projects.size();
    }

    /**
     * Counts the total number of employees working on production projects in the department.
     * @return The total number of employees working on production projects.
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
     * @return A list of funding group types of all community projects.
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

    public void addProject(Project project) {
        this.projects.add(project);
    }
}

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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}

class ProductionProject extends Project {
    private String siteCode;

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

class ResearchProject extends Project {
}

class EducationProject extends Project {
    private FundingGroup fundingGroup;

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

class CommunityProject extends Project {
    private FundingGroup fundingGroup;

    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

class FundingGroup {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Employee {
    private String name;
    private String email;
    private String employeeId;
    private String employeeNumber;

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