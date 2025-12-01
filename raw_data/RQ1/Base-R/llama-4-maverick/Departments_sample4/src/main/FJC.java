import java.util.*;

/**
 * Represents a company with departments, offices, and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a new Company object.
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
     * Assigns an employee as manager of a specified department.
     * @param department the department to assign the manager to
     * @param employee the employee to assign as manager
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (!this.employees.contains(employee)) {
            return false;
        }
        return department.assignManager(employee);
    }

    /**
     * Deletes a department and its associated offices and employees.
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (!this.departments.contains(department)) {
            return false;
        }
        this.departments.remove(department);
        for (Office office : department.getOffices()) {
            // Assuming offices don't need to be explicitly removed from somewhere else
        }
        for (Employee employee : new ArrayList<>(this.employees)) {
            if (department.equals(employee.getDepartment())) {
                this.employees.remove(employee);
            }
        }
        return true;
    }

    /**
     * Lists the departments that have not been assigned a manager.
     * @return a list of departments without a manager, or null if all departments have a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department department : departments) {
            if (!department.hasManager()) {
                result.add(department);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Employee manager;
    private Office headquarters;

    /**
     * Constructs a new Department object.
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
     * Gets the manager of the department.
     * @return the manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Gets the headquarters office of the department.
     * @return the headquarters office
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarters the headquarters office to set
     */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /**
     * Assigns an office as the headquarters for the department.
     * @param office the office to assign as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Office office) {
        if (!this.offices.contains(office)) {
            return false;
        }
        if (this.headquarters != null) {
            return false;
        }
        this.headquarters = office;
        return true;
    }

    /**
     * Assigns an employee as manager of the department.
     * @param employee the employee to assign as manager
     * @return true if successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (this.manager != null) {
            return false;
        }
        this.manager = employee;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return this.manager != null;
    }
}

/**
 * Represents an office within a department.
 */
class Office {
    // Add properties and methods as necessary for Office

    /**
     * Constructs a new Office object.
     */
    public Office() {
    }
}

/**
 * Represents an employee within a company.
 */
class Employee {
    private Department department;

    /**
     * Constructs a new Employee object.
     */
    public Employee() {
    }

    /**
     * Gets the department the employee belongs to.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department the employee belongs to.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
}