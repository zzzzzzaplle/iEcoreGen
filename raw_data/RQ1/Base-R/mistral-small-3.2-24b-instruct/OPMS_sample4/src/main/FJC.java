import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments;

    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department The department to be added.
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Removes a department from the company.
     * @param department The department to be removed.
     */
    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    /**
     * Gets all departments in the company.
     * @return A list of all departments.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Calculates the total budget of all projects in the company.
     * @return The total budget amount.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Department department : departments) {
            totalBudget += department.calculateTotalBudget();
        }
        return totalBudget;
    }

    /**
     * Counts the number of employees working on production projects in the company.
     * @return The number of employees working on production projects.
     */
    public int countEmployeesOnProductionProjects() {
        int count = 0;
        for (Department department : departments) {
            count += department.countEmployeesOnProductionProjects();
        }
        return count;
    }
}

class Department {
    private String id;
    private String email;
    private List<Project> projects;

    public Department() {
        this.projects = new ArrayList<>();
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

    /**
     * Adds a project to the department.
     * @param project The project to be added.
     */
    public void addProject(Project project) {
        projects.add(project);
    }

    /**
     * Removes a project from the department.
     * @param project The project to be removed.
     */
    public void removeProject(Project project) {
        projects.remove(project);
    }

    /**
     * Calculates the total budget of all projects in the department.
     * @return The total budget amount.
     */
    public double calculateTotalBudget() {
        double totalBudget = 0;
        for (Project project : projects) {
            totalBudget += project.getBudgetAmount();
        }
        return totalBudget;
    }

    /**
     * Calculates the average budget of all projects in the department.
     * @return The average budget amount.
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0;
        }
        return calculateTotalBudget() / projects.size();
    }

    /**
     * Counts the number of employees working on production projects in the department.
     * @return The number of employees working on production projects.
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
     * Retrieves the funding group type of all community projects in the department.
     * @return A list of funding group types.
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

class Project {
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

    /**
     * Adds an employee to the project.
     * @param employee The employee to be added.
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes an employee from the project.
     * @param employee The employee to be removed.
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
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
    // Additional fields and methods specific to ResearchProject can be added here
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

class TemporaryEmployee extends Employee {
    // Additional fields and methods specific to TemporaryEmployee can be added here
}

class PermanentEmployee extends Employee {
    // Additional fields and methods specific to PermanentEmployee can be added here
}