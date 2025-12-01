import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Unparameterized constructor that initializes empty lists for departments and employees.
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
     * Assigns an office as headquarters for a department. The office must belong to the department.
     * @param department the department for which to assign the headquarters
     * @param office the office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        if (department == null || office == null) {
            return false;
        }
        
        if (department.hasHeadquarters()) {
            return false;
        }
        
        if (!department.getOffices().contains(office)) {
            return false;
        }
        
        department.setHeadquarters(office);
        return true;
    }

    /**
     * Assigns an employee as manager of a specified department. The employee and the department must belong to the company.
     * @param department the department to assign the manager to
     * @param employee the employee to be assigned as manager
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        
        if (department.hasManager()) {
            return false;
        }
        
        if (!employees.contains(employee)) {
            return false;
        }
        
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department: Removes the department and its offices (including headquarters), and then removes employees (including managers) from the company.
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all employees in this department from the company
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDepartment() == department) {
                employeesToRemove.add(emp);
            }
        }
        employees.removeAll(employeesToRemove);
        
        // Remove the department
        departments.remove(department);
        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.hasManager();
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * @return a list of departments without managers, or null if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManagers() {
        List<Department> departmentsWithoutManagers = new ArrayList<>();
        
        for (Department dept : departments) {
            if (!dept.hasManager()) {
                departmentsWithoutManagers.add(dept);
            }
        }
        
        return departmentsWithoutManagers.isEmpty() ? null : departmentsWithoutManagers;
    }
}

/**
 * Represents a department within a company.
 */
 class Department {
    private List<Office> offices;
    private Office headquarters;
    private Employee manager;

    /**
     * Unparameterized constructor that initializes an empty list of offices.
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
     * @return the headquarters office, or null if not set
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarters the office to set as headquarters
     */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return the manager employee, or null if not set
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the employee to set as manager
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Checks if the department has a headquarters assigned.
     * @return true if the department has a headquarters, false otherwise
     */
    public boolean hasHeadquarters() {
        return headquarters != null;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }
}

/**
 * Represents an office location.
 */
 class Office {
    // Basic office class with minimal properties for this domain
    private String location;

    /**
     * Unparameterized constructor.
     */
    public Office() {
    }

    /**
     * Gets the location of the office.
     * @return the office location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the office.
     * @param location the office location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
}

/**
 * Represents an employee in the company.
 */
 class Employee {
    private String name;
    private Department department;

    /**
     * Unparameterized constructor.
     */
    public Employee() {
    }

    /**
     * Gets the name of the employee.
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the department the employee belongs to.
     * @return the employee's department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department the employee belongs to.
     * @param department the department to assign the employee to
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
}