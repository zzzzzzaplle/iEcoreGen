import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Company that consists of departments and employees.
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
        if (department == null) {
            return false;
        }
        return this.departments.add(department);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        return this.employees.add(employee);
    }

    /**
     * Deletes a department from the company, removing its offices and employees.
     *
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !this.departments.contains(department)) {
            return false;
        }

        // Remove the department and all its offices
        this.departments.remove(department);

        return true;
    }

    /**
     * Lists all departments that have not been assigned a manager.
     *
     * @return a list of departments without managers, or null if all departments have managers
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

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee   the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if the assignment was successful, false if the department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }

        // Check if employee belongs to the company
        if (!this.employees.contains(employee)) {
            return false;
        }

        // Check if department belongs to the company
        if (!this.departments.contains(department)) {
            return false;
        }

        return department.assignManager(employee);
    }

    /**
     * Gets the list of departments in the company.
     *
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     *
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in the company.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     *
     * @param employees the list of employees to set
     */
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

    /**
     * Constructs a new Department with empty offices list.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /**
     * Assigns an office as the headquarter for this department.
     *
     * @param office the office to assign as headquarter
     * @return true if the assignment was successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }

        // Check if office belongs to this department
        if (!this.offices.contains(office)) {
            return false;
        }

        // Check if department already has a headquarter
        if (this.headquarter != null) {
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
        return this.manager != null;
    }

    /**
     * Assigns a manager to this department.
     *
     * @param employee the employee to assign as manager
     * @return true if the assignment was successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (employee == null) {
            return false;
        }

        // Check if department already has a manager
        if (this.manager != null) {
            return false;
        }

        this.manager = employee;
        return true;
    }

    /**
     * Deletes an employee from this department.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }

        // If the employee is the manager, remove the manager assignment
        if (this.manager == employee) {
            this.manager = null;
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
        if (office == null || !this.offices.contains(office)) {
            return false;
        }

        // If the office is the headquarter, remove the headquarter assignment
        if (this.headquarter == office) {
            this.headquarter = null;
        }

        this.offices.remove(office);
        return true;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     * @return true if the office was added successfully, false otherwise
     */
    public boolean addOffice(Office office) {
        if (office == null) {
            return false;
        }
        return this.offices.add(office);
    }

    /**
     * Gets the list of offices in this department.
     *
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in this department.
     *
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarter office of this department.
     *
     * @return the headquarter office
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarter office of this department.
     *
     * @param headquarter the headquarter office to set
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager of this department.
     *
     * @return the manager employee
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of this department.
     *
     * @param manager the manager employee to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * Represents an Office within a department.
 */
class Office {
    /**
     * Constructs a new Office.
     */
    public Office() {
        // Default constructor
    }
}

/**
 * Represents an Employee in a company.
 */
class Employee {
    /**
     * Constructs a new Employee.
     */
    public Employee() {
        // Default constructor
    }
}