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

        // Remove all employees from the department
        for (Employee employee : department.getEmployees()) {
            this.employees.remove(employee);
        }

        // Remove the department itself
        return this.departments.remove(department);
    }

    /**
     * Lists departments that have not been assigned a manager.
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

        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }

        return departmentsWithoutManager;
    }

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee the employee to assign as manager
     * @param department the department to which the manager will be assigned
     * @return true if the assignment was successful, false if the department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }

        if (!this.employees.contains(employee) || !this.departments.contains(department)) {
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
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    private List<Employee> employees;

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
     * @return true if the assignment was successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !this.offices.contains(office)) {
            return false;
        }

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
     * Deletes an employee from this department.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false if the employee doesn't exist
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        return this.employees.remove(employee);
    }

    /**
     * Deletes an office from this department.
     *
     * @param office the office to delete
     * @return true if the office was deleted successfully, false if the office doesn't exist
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }

        if (this.headquarter == office) {
            this.headquarter = null;
        }

        return this.offices.remove(office);
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

        if (this.manager != null) {
            return false;
        }

        this.manager = employee;
        return true;
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
    public void setOffices(List<Office> offices