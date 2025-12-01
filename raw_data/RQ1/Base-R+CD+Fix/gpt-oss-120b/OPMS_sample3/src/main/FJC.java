import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a company that owns several departments.
 */
 class Company {

    /** Departments belonging to the company (2..8). */
    private List<Department> departments = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
    }

    /** @return the mutable list of departments (modifications affect the company). */
    public List<Department> getDepartments() {
        return departments;
    }

    /** @param departments the list of departments to set. */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department != null) {
            departments.add(department);
        }
    }

    /**
     * Calculates the total budget of all projects in the whole company.
     *
     * @return sum of the budget amounts of every project in every department
     */
    public double calculateTotalBudget() {
        double total = 0.0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                total += proj.getBudget();
            }
        }
        return total;
    }

    /**
     * Counts the total number of employees that are assigned to production projects
     * across all departments of the company.
     *
     * @return total number of employee‑project assignments on production projects
     */
    public int countEmployeesInProductionProjects() {
        int count = 0;
        for (Department dept : departments) {
            for (Project proj : dept.getProjects()) {
                if (proj instanceof ProductionProject) {
                    count += proj.getWorkingEmployees().size();
                }
            }
        }
        return count;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String ID;
    private String email;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    /** No‑argument constructor. */
    public Department() {
    }

    /** @return department identifier */
    public String getID() {
        return ID;
    }

    /** @param ID the identifier to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return department e‑mail address */
    public String getEmail() {
        return email;
    }

    /** @param email the e‑mail address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return mutable list of employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** @return mutable list of projects */
    public List<Project> getProjects() {
        return projects;
    }

    /** @param projects list of projects to set */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Adds a project to the department.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (project != null) {
            projects.add(project);
        }
    }

    /**
     * Calculates the average budget of all projects in this department.
     *
     * @return average budget, or 0.0 if the department has no projects
     */
    public double calculateAverageBudget() {
        if (projects.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Project p : projects) {
            sum += p.getBudget();
        }
        return sum / projects.size();
    }

    /**
     * Retrieves the funding group type of every community project in the department.
     *
     * @return list of {@link FundingGroupType} values; empty list if none exist
     */
    public List<FundingGroupType> getFundingGroupTypeCommunityProjects() {
        List<FundingGroupType> types = new ArrayList<>();
        for (Project p : projects) {
            if (p instanceof CommunityProject) {
                CommunityProject cp = (CommunityProject) p;
                FundingGroup fg = cp.getFundingGroup();
                if (fg != null) {
                    types.add(fg.getType());
                }
            }
        }
        return Collections.unmodifiableList(types);
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }
}

/**
 * Types of employees.
 */
enum EmployeeType {
    TEMPORARY,
    PERMANENT
}

/**
 * Represents an employee (temporary or permanent).
 */
class Employee {

    private EmployeeType type;
    private String name;
    private String email;
    private String ID;
    private String number;

    /** No‑argument constructor. */
    public Employee() {
    }

    /** @return employee type */
    public EmployeeType getType() {
        return type;
    }

    /** @param type employee type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @param name employee name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee e‑mail */
    public String getEmail() {
        return email;
    }

    /** @param email employee e‑mail to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return employee identifier */
    public String getID() {
        return ID;
    }

    /** @param ID employee identifier to set */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** @return employee number */
    public String getNumber() {
        return number;
    }

    /** @param number employee number to set */
    public void setNumber(String number) {
        this.number = number;
    }
}

/**
 * Abstract base class for all projects.
 */
abstract class Project {

    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private List<Employee> workingEmployees = new ArrayList<>();

    /** No‑argument constructor. */
    public Project() {
    }

    /** @return project title */
    public String getTitle() {
        return title;
    }

    /** @param title title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return project description */
    public String getDescription() {
        return description;
    }

    /** @param description description to set */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return project budget */
    public double getBudget() {
        return budget;
    }

    /** @param budget budget amount to set */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /** @return project deadline */
    public Date getDeadline() {
        return deadline;
    }

    /** @param deadline deadline to set */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /** @return mutable list of employees working on the project */
    public List<Employee> getWorkingEmployees() {
        return workingEmployees;
    }

    /** @param workingEmployees list of employees to set */
    public void setWorkingEmployees(List<Employee> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    /**
     * Assigns an employee to this project.
     *
     * @param employee employee to add
     */
    public void addWorkingEmployee(Employee employee) {
        if (employee != null) {
            workingEmployees.add(employee);
        }
    }
}

/**
 * Production projects have an additional site code.
 */
class ProductionProject extends Project {

    private String siteCode;

    /** No‑argument constructor. */
    public ProductionProject() {
    }

    /** @return site code */
    public String getSiteCode() {
        return siteCode;
    }

    /** @param siteCode site code to set */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}

/**
 * Research projects have no extra fields.
 */
class ResearchProject extends Project {

    /** No‑argument constructor. */
    public ResearchProject() {
    }
}

/**
 * Education projects are linked to a funding group.
 */
class EducationProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public EducationProject() {
    }

    /** @return associated funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Community projects are linked to a funding group.
 */
class CommunityProject extends Project {

    private FundingGroup fundingGroup;

    /** No‑argument constructor. */
    public CommunityProject() {
    }

    /** @return associated funding group */
    public FundingGroup getFundingGroup() {
        return fundingGroup;
    }

    /** @param fundingGroup funding group to set */
    public void setFundingGroup(FundingGroup fundingGroup) {
        this.fundingGroup = fundingGroup;
    }
}

/**
 * Types of funding groups.
 */
enum FundingGroupType {
    PRIVATE,
    GOVERNMENT,
    MIXED
}

/**
 * Represents a funding group that can sponsor education and community projects.
 */
class FundingGroup {

    private String name;
    private FundingGroupType type;

    /** No‑argument constructor. */
    public FundingGroup() {
    }

    /** @return group name */
    public String getName() {
        return name;
    }

    /** @param name group name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return funding group type */
    public FundingGroupType getType() {
        return type;
    }

    /** @param type funding group type to set */
    public void setType(FundingGroupType type) {
        this.type = type;
    }
}