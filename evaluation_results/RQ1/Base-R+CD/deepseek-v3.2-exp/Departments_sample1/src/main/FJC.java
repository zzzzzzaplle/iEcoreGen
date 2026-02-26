import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Represents an employee in the company
 */
class Employee {
    // No specific attributes mentioned in the domain description
    // Additional fields can be added as needed
    
    /**
     * Default constructor
     */
    public Employee() {
    }
}

/**
 * Represents an office location
 */
class Office {
    // No specific attributes mentioned in the domain description
    // Additional fields can be added as needed
    
    /**
     * Default constructor
     */
    public Office() {
    }
}

/**
 * Represents a department within the company
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    
    /**
     * Default constructor
     */
    public Department() {
        this.offices = new ArrayList<>();
    }
    
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
    
    /**
     * Assigns an office as headquarters for this department
     * @param office The office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department 
     *         or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (this.headquarter != null) {
            return false; // Department already has a headquarters
        }
        
        if (!this.offices.contains(office)) {
            return false; // Office doesn't belong to this department
        }
        
        this.headquarter = office;
        return true;
    }
    
    /**
     * Checks if this department has a manager assigned
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return this.manager != null;
    }
    
    /**
     * Removes an employee from this department
     * @param employee The employee to be removed
     * @return true if the employee was successfully removed, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (this.manager != null && this.manager.equals(employee)) {
            this.manager = null;
            return true;
        }
        return false;
    }
    
    /**
     * Removes an office from this department
     * @param office The office to be removed
     * @return true if the office was successfully removed, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (this.headquarter != null && this.headquarter.equals(office)) {
            this.headquarter = null;
        }
        return this.offices.remove(office);
    }
    
    /**
     * Adds an office to this department
     * @param office The office to be added
     * @return true if the office was successfully added, false otherwise
     */
    public boolean addOffice(Office office) {
        if (office != null && !this.offices.contains(office)) {
            this.offices.add(office);
            return true;
        }
        return false;
    }
}

/**
 * Represents a company containing departments and employees
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;
    
    /**
     * Default constructor
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
    
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
    
    /**
     * Adds a department to the company
     * @param department The department to be added
     * @return true if the department was successfully added, false otherwise
     */
    public boolean addDepartment(Department department) {
        if (department != null && !this.departments.contains(department)) {
            this.departments.add(department);
            return true;
        }
        return false;
    }
    
    /**
     * Adds an employee to the company
     * @param employee The employee to be added
     * @return true if the employee was successfully added, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee != null && !this.employees.contains(employee)) {
            this.employees.add(employee);
            return true;
        }
        return false;
    }
    
    /**
     * Deletes a department from the company along with its offices and employees
     * @param department The department to be deleted
     * @return true if the department was successfully deleted, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !this.departments.contains(department)) {
            return false; // Department doesn't exist
        }
        
        // Remove the department and all associated data
        this.departments.remove(department);
        
        // Remove employees who were managers of this department
        Iterator<Employee> employeeIterator = this.employees.iterator();
        while (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            if (department.getManager() != null && department.getManager().equals(employee)) {
                employeeIterator.remove();
            }
        }
        
        return true;
    }
    
    /**
     * Lists departments that have not been assigned a manager
     * @return List of departments without managers, or null if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department department : this.departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        
        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }
        
        return departmentsWithoutManager;
    }
    
    /**
     * Assigns an employee as manager of a specified department
     * @param employee The employee to be assigned as manager
     * @param department The department to assign the manager to
     * @return true if successful, false if the department already has a manager 
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (department == null || !this.departments.contains(department)) {
            return false; // Department doesn't exist in the company
        }
        
        if (employee == null || !this.employees.contains(employee)) {
            return false; // Employee doesn't exist in the company
        }
        
        if (department.hasManager()) {
            return false; // Department already has a manager
        }
        
        department.setManager(employee);
        return true;
    }
    
    /**
     * Verifies whether a department has a manager assigned
     * @param department The department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean verifyDepartmentHasManager(Department department) {
        if (department == null || !this.departments.contains(department)) {
            return false;
        }
        return department.hasManager();
    }
}