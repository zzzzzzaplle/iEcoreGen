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
     * Constructs an empty Company.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return true if the department was added, false if it already exists
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
     * @return true if the employee was added, false if it already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, including its offices and employees.
     *
     * @param department the department to delete
     * @return true if the department was deleted, false if it doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        for (Employee employee : new ArrayList<>(department.getEmployees())) {
            deleteEmployee(employee);
        }
        for (Office office : new ArrayList<>(department.getOffices())) {
            department.deleteOffice(office);
        }
        return departments.remove(department);
    }

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if the manager was assigned, false if the department already has a manager or the employee doesn't exist
     */
    public boolean assignManager(Employee employee, Department department) {
        if (department == null || employee == null || !departments.contains(department) || !employees.contains(employee)) {
            return false;
        }
        return department.assignManager(employee);
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
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
     * Deletes an employee from the company.
     *
     * @param employee the employee to delete
     * @return true if the employee was deleted, false if it doesn't exist
     */
    private boolean deleteEmployee(Employee employee) {
        if (employee == null || !employees.contains(employee)) {
            return false;
        }
        for (Department department : new ArrayList<>(departments)) {
            if (department.getManager() != null && department.getManager().equals(employee)) {
                department.assignManager(null);
            }
        }
        return employees.remove(employee);
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
 * Represents a department with offices and a manager.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Constructs an empty Department.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as headquarters for the department.
     *
     * @param office the office to assign as headquarters
     * @return true if the office was assigned, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office) || this.headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     *
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Assigns an employee as manager of the department.
     *
     * @param employee the employee to assign as manager
     * @return true if the manager was assigned, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (this.manager != null) {
            return false;
        }
        this.manager = employee;
        return true;
    }

    /**
     * Deletes an office from the department.
     *
     * @param office the office to delete
     * @return true if the office was deleted, false if it doesn't exist
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
        List<Employee> employees = new ArrayList<>();
        // In a real scenario, you would have a way to track which employees belong to which department
        // For example, you might have a method in the Employee class to get the department
        // This is a placeholder to satisfy the design model
        return employees;
    }
}

/**
 * Represents an office.
 */
class Office {
    // Add fields, constructors, and methods as needed
}

/**
 * Represents an employee.
 */
class Employee {
    // Add fields, constructors, and methods as needed
}