import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Company which contains departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor initializes the lists of departments and employees.
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
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, removing its offices and employees.
     *
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if it doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove all employees of this department from the company
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee emp : employees) {
            if (department.getEmployees().contains(emp)) {
                employeesToRemove.add(emp);
            }
        }
        employees.removeAll(employeesToRemove);

        // Remove the department itself
        return departments.remove(department);
    }

    /**
     * Lists all departments that do not have a manager assigned.
     *
     * @return a list of departments without managers, or null if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department dept : departments) {
            if (!dept.hasManager()) {
                departmentsWithoutManager.add(dept);
            }
        }
        
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee the employee to assign as manager
     * @param department the department to which the manager will be assigned
     * @return true if assignment is successful, false if the department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        // Check if department belongs to this company
        if (!departments.contains(department)) {
            return false;
        }
        
        // Check if employee belongs to this company
        if (!employees.contains(employee)) {
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
 * Represents a Department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    private List<Employee> employees; // Added to track employees in this department

    /**
     * Default constructor initializes the lists of offices and employees.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for this department.
     *
     * @param office the office to assign as headquarter
     * @return true if the office was assigned successfully, false if the office doesn't exist
     *         in the department or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (headquarter != null || office == null || !offices.contains(office)) {
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
     * @return true if the employee was deleted successfully, false if the employee doesn't exist
     */
    public boolean deleteEmployee(Employee employee) {
        return employees.remove(employee);
    }

    /**
     * Deletes an office from this department.
     *
     * @param office the office to delete
     * @return true if the office was deleted successfully, false if the office doesn't exist
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        // If we're deleting the headquarter, reset it
        if (office.equals(headquarter)) {
            headquarter = null;
        }
        
        return offices.remove(office);
    }

    /**
     * Assigns an employee as manager of this department.
     *
     * @param employee the employee to assign as manager
     * @return true if assignment is successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (manager != null || employee == null) {
            return false;
        }
        manager = employee;
        return true;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     * @return true if the office was added successfully, false otherwise
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /**
     * Adds an employee to this department.
     *
     * @param employee the employee to add
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
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
 * Represents an Office location.
 */
class Office {
    /**
     * Default constructor for Office.
     */
    public Office() {
        // Default constructor
    }
}

/**
 * Represents an Employee in the company.
 */
class Employee {
    /**
     * Default constructor for Employee.
     */
    public Employee() {
        // Default constructor
    }
}