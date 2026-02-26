import java.util.ArrayList;
import java.util.List;

/**
 * Represents an office in the company.
 */
class Office {
    private String name;

    /**
     * Constructs an Office object.
     */
    public Office() {}

    /**
     * Constructs an Office object with a given name.
     * @param name The name of the office.
     */
    public Office(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the office.
     * @return The name of the office.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the office.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;

    /**
     * Constructs an Employee object.
     */
    public Employee() {}

    /**
     * Constructs an Employee object with a given name.
     * @param name The name of the employee.
     */
    public Employee(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the employee.
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a department in the company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Constructs a Department object.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for this department.
     * @param office The office to be assigned as headquarter.
     * @return True if successful, false if the office doesn't exist in the department or if the department already has a headquarters.
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (offices.contains(office) && headquarter == null) {
            headquarter = office;
            return true;
        }
        return false;
    }

    /**
     * Checks if this department has a manager assigned.
     * @return True if a manager is assigned, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from this department.
     * @param employee The employee to be deleted.
     * @return True if the employee is the manager and is successfully removed, false otherwise.
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee.equals(manager)) {
            manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from this department.
     * @param office The office to be deleted.
     * @return True if the office is the headquarter or is successfully removed, false otherwise.
     */
    public boolean deleteOffice(Office office) {
        if (office.equals(headquarter)) {
            headquarter = null;
        }
        return offices.remove(office);
    }

    /**
     * Adds an office to this department.
     * @param office The office to be added.
     */
    public void addOffice(Office office) {
        offices.add(office);
    }

    /**
     * Gets the list of offices in this department.
     * @return The list of offices.
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in this department.
     * @param offices The list of offices to set.
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarter office of this department.
     * @return The headquarter office.
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarter office of this department.
     * @param headquarter The headquarter office to set.
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager of this department.
     * @return The manager.
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of this department.
     * @param manager The manager to set.
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * Represents a company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a Company object.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to this company.
     * @param department The department to be added.
     * @return True if the department is successfully added, false if it already exists.
     */
    public boolean addDepartment(Department department) {
        if (!departments.contains(department)) {
            return departments.add(department);
        }
        return false;
    }

    /**
     * Adds an employee to this company.
     * @param employee The employee to be added.
     * @return True if the employee is successfully added, false if they already exist.
     */
    public boolean addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            return employees.add(employee);
        }
        return false;
    }

    /**
     * Deletes a department from this company.
     * @param department The department to be deleted.
     * @return True if the department is successfully deleted, false if it doesn't exist.
     */
    public boolean deleteDepartment(Department department) {
        if (departments.remove(department)) {
            // Remove offices (including headquarters)
            department.getOffices().clear();
            // Remove employees (including managers) from the company
            if (department.getManager() != null && employees.remove(department.getManager())) {
                department.setManager(null);
            }
            return true;
        }
        return false;
    }

    /**
     * Lists the departments in this company that have not been assigned a manager.
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
     * @param employee The employee to be assigned as manager.
     * @param department The department for which the manager is to be assigned.
     * @return True if the assignment is successful, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employees.contains(employee) && departments.contains(department) && !department.hasManager()) {
            department.setManager(employee);
            return true;
        }
        return false;
    }

    /**
     * Gets the list of departments in this company.
     * @return The list of departments.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in this company.
     * @param departments The list of departments to set.
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in this company.
     * @return The list of employees.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in this company.
     * @param employees The list of employees to set.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}