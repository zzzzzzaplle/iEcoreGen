import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments and employees.
 * Provides functionality to manage departments, employees, and their relationships.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor that initializes empty lists for departments and employees.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds a department to the company.
     * @param department the department to add
     * @return true if the department was added successfully, false if the department is null or already exists
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * @param employee the employee to add
     * @return true if the employee was added successfully, false if the employee is null or already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, including its offices and employees.
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all employees from the department
        List<Employee> departmentEmployees = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDepartment() != null && emp.getDepartment().equals(department)) {
                departmentEmployees.add(emp);
            }
        }
        employees.removeAll(departmentEmployees);
        
        // Remove the department
        departments.remove(department);
        return true;
    }

    /**
     * Lists departments that have not been assigned a manager.
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
     * @param employee the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || 
            !employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        
        if (department.hasManager()) {
            return false;
        }
        
        employee.setDepartment(department);
        department.setManager(employee);
        return true;
    }
}

/**
 * Represents a department within a company.
 * Contains offices and may have a headquarters office and a manager.
 */
 class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Default constructor that initializes an empty list of offices.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the list of offices in the department.
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in the department.
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarters office of the department.
     * @return the headquarters office
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarter the headquarters office to set
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager of the department.
     * @return the manager employee
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager employee to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Assigns an office as headquarters for the department.
     * @param office the office to assign as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        if (headquarter != null) {
            return false;
        }
        
        headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false if the employee doesn't exist in the department
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null || manager == null || !manager.equals(employee)) {
            return false;
        }
        
        manager = null;
        employee.setDepartment(null);
        return true;
    }

    /**
     * Deletes an office from the department.
     * @param office the office to delete
     * @return true if the office was deleted successfully, false if the office doesn't exist in the department
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        if (headquarter != null && headquarter.equals(office)) {
            headquarter = null;
        }
        
        return offices.remove(office);
    }
}

/**
 * Represents an office location where departments are located.
 */
 class Office {
    // Basic office class with no specific functionality required by the domain
}

/**
 * Represents an employee who can be assigned as a manager of a department.
 */
 class Employee {
    private Department department;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /**
     * Gets the department where the employee works.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department where the employee works.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
}