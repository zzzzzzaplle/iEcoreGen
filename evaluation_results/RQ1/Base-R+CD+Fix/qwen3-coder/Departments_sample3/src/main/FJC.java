import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor initializes empty lists for departments and employees.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return true if the department was added successfully, false otherwise
     */
    public boolean addDepartment(Department department) {
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, removing its offices and employees.
     *
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.contains(department)) {
            return false;
        }

        // Remove all employees from the department (including manager)
        department.getEmployees().clear();
        
        // Remove all offices from the department (including headquarter)
        department.getOffices().clear();
        department.setHeadquarter(null);
        
        // Remove the department from the company
        return departments.remove(department);
    }

    /**
     * Lists departments that have not been assigned a manager.
     *
     * @return a list of departments without managers, or null if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department department : departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if the assignment was successful, false if the department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        // Check if the employee belongs to the company
        if (!employees.contains(employee)) {
            return false;
        }
        
        // Check if the department belongs to the company
        if (!departments.contains(department)) {
            return false;
        }
        
        return department.assignManager(employee);
    }

    // Getters and setters
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    private List<Employee> employees;

    /**
     * Default constructor initializes empty lists for offices and employees.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for this department.
     *
     * @param office the office to assign as headquarter
     * @return true if the assignment was successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarter
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        // Check if the office belongs to this department
        if (!offices.contains(office)) {
            return false;
        }
        
        // Check if the department already has a headquarter
        if (headquarter != null) {
            return false;
        }
        
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if this department has a manager assigned.
     *
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Assigns an employee as manager of this department.
     *
     * @param employee the employee to assign as manager
     * @return true if the assignment was successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        // Check if the department already has a manager
        if (manager != null) {
            return false;
        }
        
        this.manager = employee;
        return true;
    }

    /**
     * Deletes an employee from this department.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false if the employee doesn't exist
     */
    public boolean deleteEmployee(Employee employee) {
        boolean removed = employees.remove(employee);
        if (removed && manager == employee) {
            manager = null;
        }
        return removed;
    }

    /**
     * Deletes an office from this department.
     *
     * @param office the office to delete
     * @return true if the office was deleted successfully, false if the office doesn't exist
     */
    public boolean deleteOffice(Office office) {
        boolean removed = offices.remove(office);
        if (removed && headquarter == office) {
            headquarter = null;
        }
        return removed;
    }

    // Getters and setters
    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public Office getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents an office that can be associated with a department.
 */
class Office {
    /**
     * Default constructor.
     */
    public Office() {
    }
}

/**
 * Represents an employee who can be assigned to a department or as a manager.
 */
class Employee {
    /**
     * Default constructor.
     */
    public Employee() {
    }
}