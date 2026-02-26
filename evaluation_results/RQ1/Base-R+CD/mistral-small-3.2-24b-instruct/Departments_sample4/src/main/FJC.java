import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company with departments and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs an empty Company with no departments or employees.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return true if the department was added successfully, false if the department already exists
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
     * @return true if the employee was added successfully, false if the employee already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department and its associated offices and employees.
     *
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if the department does not exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        for (Employee employee : new ArrayList<>(department.getEmployees())) {
            department.deleteEmployee(employee);
            employees.remove(employee);
        }
        for (Office office : new ArrayList<>(department.getOffices())) {
            department.deleteOffice(office);
        }
        return departments.remove(department);
    }

    /**
     * Lists departments that do not have a manager.
     *
     * @return a list of departments without a manager, or null if all departments have a manager
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
     * Assigns an employee as the manager of a department.
     *
     * @param employee  the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if the assignment was successful, false if the department already has a manager or the employee does not exist
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || !employees.contains(employee) || !departments.contains(department) || department.hasManager()) {
            return false;
        }
        return department.setManager(employee);
    }

    // Getters and Setters
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
    private List<Employee> employees;

    /**
     * Constructs an empty Department with no offices, headquarter, manager, or employees.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter of the department.
     *
     * @param office the office to assign as headquarter
     * @return true if the assignment was successful, false if the office does not belong to the department or the department already has a headquarter
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office) || headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager.
     *
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted successfully, false if the employee does not exist in the department
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null || !employees.contains(employee)) {
            return false;
        }
        if (this.manager != null && this.manager.equals(employee)) {
            this.manager = null;
        }
        return employees.remove(employee);
    }

    /**
     * Deletes an office from the department.
     *
     * @param office the office to delete
     * @return true if the office was deleted successfully, false if the office does not exist in the department
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        if (this.headquarter != null && this.headquarter.equals(office)) {
            this.headquarter = null;
        }
        return offices.remove(office);
    }

    /**
     * Sets the manager of the department.
     *
     * @param manager the employee to set as manager
     * @return true if the manager was set successfully, false if the manager is already set
     */
    public boolean setManager(Employee manager) {
        if (this.manager != null) {
            return false;
        }
        this.manager = manager;
        return employees.contains(manager) || employees.add(manager);
    }

    // Getters and Setters
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
 * Represents an office within a department.
 */
 class Office {
    // Add fields, constructors, and methods as needed
}

/**
 * Represents an employee within a company.
 */
 class Employee {
    // Add fields, constructors, and methods as needed
}