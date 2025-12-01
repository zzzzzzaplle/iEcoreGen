import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Office in the company.
 */
class Office {
    // No additional properties or methods needed for this class based on the given requirements
}

/**
 * Represents an Employee in the company.
 */
class Employee {
    // No additional properties or methods needed for this class based on the given requirements
}

/**
 * Represents a Department in the company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /**
     * Assigns an office as the headquarter for this department.
     *
     * @param office The office to be assigned as headquarter.
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarter.
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (this.headquarter != null) {
            return false;
        }
        if (this.offices.contains(office)) {
            this.headquarter = office;
            return true;
        }
        return false;
    }

    /**
     * Checks if this department has a manager assigned.
     *
     * @return true if a manager is assigned, false otherwise.
     */
    public boolean hasManager() {
        return this.manager != null;
    }

    /**
     * Deletes an employee from this department.
     *
     * @param employee The employee to be deleted.
     * @return true if the employee was found and deleted, false otherwise.
     */
    public boolean deleteEmployee(Employee employee) {
        if (this.manager != null && this.manager.equals(employee)) {
            this.manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from this department.
     *
     * @param office The office to be deleted.
     * @return true if the office was found and deleted, false otherwise.
     */
    public boolean deleteOffice(Office office) {
        if (this.headquarter != null && this.headquarter.equals(office)) {
            this.headquarter = null;
        }
        return this.offices.remove(office);
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
}

/**
 * Represents a Company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department The department to be added.
     * @return true if the department was added, false if it already exists.
     */
    public boolean addDepartment(Department department) {
        if (this.departments.contains(department)) {
            return false;
        }
        this.departments.add(department);
        return true;
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee The employee to be added.
     * @return true if the employee was added, false if it already exists.
     */
    public boolean addEmployee(Employee employee) {
        if (this.employees.contains(employee)) {
            return false;
        }
        this.employees.add(employee);
        return true;
    }

    /**
     * Deletes a department from the company, including its offices and employees.
     *
     * @param department The department to be deleted.
     * @return true if the department was found and deleted, false otherwise.
     */
    public boolean deleteDepartment(Department department) {
        if (!this.departments.contains(department)) {
            return false;
        }

        // Delete the department's offices
        for (Office office : department.getOffices()) {
            department.deleteOffice(office);
        }

        // Delete the department's manager if it exists
        if (department.getManager() != null) {
            this.employees.remove(department.getManager());
        }

        this.departments.remove(department);
        return true;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     *
     * @return A list of departments without a manager, or null if all departments have a manager.
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
     * Assigns an employee as the manager of a specified department.
     *
     * @param employee  The employee to be assigned as manager.
     * @param department The department to which the manager will be assigned.
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee, Department department) {
        if (!this.employees.contains(employee) || !this.departments.contains(department)) {
            return false;
        }
        if (department.hasManager()) {
            return false;
        }
        department.setManager(employee);
        return true;
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