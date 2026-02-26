import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company consisting of departments and employees.
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
        
        // Remove the department's employees from the company
        for (Employee emp : department.getEmployees()) {
            employees.remove(emp);
        }
        
        // Remove the department's offices
        department.removeAllOffices();
        
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
     * @return true if assignment was successful, false if department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        
        // Check if employee belongs to the company
        if (!employees.contains(employee)) {
            return false;
        }
        
        // Delegate to department's assignManager method
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
    private List<Employee> employees; // Added to track employees in this department

    /**
     * Constructs a new Department with empty offices and employees lists.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarters for this department.
     *
     * @param office the office to assign as headquarters
     * @return true if assignment was successful, false if office doesn't exist in department
     *         or department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        if (headquarter != null) {
            return false; // Department already has a headquarters
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
     * Assigns an employee as manager of this department.
     *
     * @param employee the employee to assign as manager
     * @return true if assignment was successful, false if department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (employee == null || manager != null) {
            return false;
        }
        
        manager = employee;
        return true;
    }

    /**
     * Removes an employee from this department.
     *
     * @param employee the employee to remove
     * @return true if the employee was removed successfully, false if not found
     */
    public boolean deleteEmployee(Employee employee) {
        return employees.remove(employee);
    }

    /**
     * Removes an office from this department.
     *
     * @param office the office to remove
     * @return true if the office was removed successfully, false if not found
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
     * Removes all offices from this department.
     */
    public void removeAllOffices() {
        offices.clear();
        headquarter = null;
    }

    // Additional helper methods
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

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
 * Represents an office location.
 */
class Office {
    // Office implementation can be extended as needed
    
    /**
     * Constructs a new Office.
     */
    public Office() {
        // Default constructor
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {
    // Employee implementation can be extended as needed
    
    /**
     * Constructs a new Employee.
     */
    public Employee() {
        // Default constructor
    }
}