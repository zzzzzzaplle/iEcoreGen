import java.util.*;

/**
 * Represents a company with departments and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructor to initialize the company.
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
     * @param employee the employee to be assigned as manager
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (!this.employees.contains(employee) || !this.departments.contains(department)) {
            return false;
        }
        return department.assignManager(employee);
    }

    /**
     * Deletes a department and its associated offices and employees.
     * @param department the department to be deleted
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (!this.departments.contains(department)) {
            return false;
        }
        this.departments.remove(department);
        for (Office office : department.getOffices()) {
            department.removeOffice(office);
        }
        for (Employee employee : new ArrayList<>(this.employees)) {
            if (department.getManager() == employee || department.getEmployees().contains(employee)) {
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
        List<Department> departmentsWithoutManager = new ArrayList<>();
        for (Department department : this.departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Employee manager;
    private List<Employee> employees;

    /**
     * Constructor to initialize the department.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
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
     * Gets the list of employees in the department.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the department.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Assigns an office as headquarters for the department.
     * @param office the office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignHeadquarter(Office office) {
        if (!this.offices.contains(office) || office.isHeadquarter()) {
            return false;
        }
        for (Office o : this.offices) {
            o.setHeadquarter(false);
        }
        office.setHeadquarter(true);
        return true;
    }

    /**
     * Assigns an employee as manager of the department.
     * @param employee the employee to be assigned as manager
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

    /**
     * Removes an office from the department.
     * @param office the office to be removed
     */
    public void removeOffice(Office office) {
        this.offices.remove(office);
    }
}

/**
 * Represents an office within a department.
 */
class Office {
    private boolean isHeadquarter;

    /**
     * Constructor to initialize the office.
     */
    public Office() {
        this.isHeadquarter = false;
    }

    /**
     * Checks if the office is a headquarter.
     * @return true if it's a headquarter, false otherwise
     */
    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    /**
     * Sets whether the office is a headquarter.
     * @param headquarter true to set as headquarter, false otherwise
     */
    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }
}

/**
 * Represents an employee within a company.
 */
class Employee {
    // Add properties and methods as needed for Employee
}