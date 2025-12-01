import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Company {
    private List<Department> departments;
    private List<Employee> employees;

    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department The department to add.
     * @return true if the department was added successfully, false if the department already exists.
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to add.
     * @return true if the employee was added successfully, false if the employee already exists.
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, including its offices and employees.
     * @param department The department to delete.
     * @return true if the department was deleted successfully, false if the department doesn't exist.
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        for (Employee employee : department.getEmployees()) {
            employees.remove(employee);
        }
        for (Office office : department.getOffices()) {
            department.deleteOffice(office);
        }
        return departments.remove(department);
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * @return A list of departments without a manager, or null if all departments have a manager.
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
     * Assigns an employee as the manager of a specified department.
     * @param employee The employee to assign as manager.
     * @param department The department to assign the manager to.
     * @return true if the manager was assigned successfully, false if the department already has a manager or the employee doesn't exist in the company.
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

class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    private List<Employee> employees;

    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for the department.
     * @param office The office to assign as headquarter.
     * @return true if the office was assigned as headquarter successfully, false if the office doesn't exist in the department or if the department already has a headquarter.
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office) || headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     * @param employee The employee to delete.
     * @return true if the employee was deleted successfully, false if the employee doesn't exist in the department.
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null || !employees.contains(employee)) {
            return false;
        }
        return employees.remove(employee);
    }

    /**
     * Deletes an office from the department.
     * @param office The office to delete.
     * @return true if the office was deleted successfully, false if the office doesn't exist in the department.
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

    /**
     * Sets the manager of the department.
     * @param manager The employee to set as manager.
     * @return true if the manager was set successfully, false if the manager is null or already set.
     */
    public boolean setManager(Employee manager) {
        if (manager == null || this.manager != null) {
            return false;
        }
        this.manager = manager;
        return true;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

class Office {
    // Add fields, constructors, and methods as needed
}

class Employee {
    // Add fields, constructors, and methods as needed
}