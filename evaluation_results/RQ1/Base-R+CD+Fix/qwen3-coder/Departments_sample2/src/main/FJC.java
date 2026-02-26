import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a new Company with empty departments and employees lists.
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

        // Remove the department's employees from the company
        if (department.getManager() != null) {
            employees.remove(department.getManager());
        }

        // Remove all employees in the department's offices
        for (Office office : department.getOffices()) {
            employees.removeAll(office.getEmployees());
        }

        // Remove the department itself
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
        if (!departments.contains(department) || !employees.contains(employee)) {
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

    /**
     * Constructs a new Department with empty offices list.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /**
     * Assigns an office as headquarters for this department.
     *
     * @param office the office to assign as headquarters
     * @return true if the assignment was successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (headquarter != null || !offices.contains(office)) {
            return false;
        }
        
        headquarter = office;
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
     * Deletes an employee from this department.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        // Remove employee from all offices in this department
        for (Office office : offices) {
            office.getEmployees().remove(employee);
        }
        
        // If the employee is the manager, remove the manager assignment
        if (manager == employee) {
            manager = null;
        }
        
        return true;
    }

    /**
     * Deletes an office from this department.
     *
     * @param office the office to delete
     * @return true if the office was deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (!offices.contains(office)) {
            return false;
        }
        
        // If the office is the headquarter, remove the headquarter assignment
        if (headquarter == office) {
            headquarter = null;
        }
        
        return offices.remove(office);
    }

    /**
     * Assigns an employee as manager of this department.
     *
     * @param employee the employee to assign as manager
     * @return true if the assignment was successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (manager != null) {
            return false;
        }
        
        manager = employee;
        return true;
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
}

/**
 * Represents an office that can be associated with a department.
 */
class Office {
    private List<Employee> employees;

    /**
     * Constructs a new Office with an empty employees list.
     */
    public Office() {
        this.employees = new ArrayList<>();
    }

    // Getters and setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents an employee of a company.
 */
class Employee {
    /**
     * Constructs a new Employee.
     */
    public Employee() {
        // Default constructor
    }
}