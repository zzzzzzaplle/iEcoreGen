import java.util.*;

/**
 * Represents a company consisting of departments, offices, and employees.
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
     * Gets the list of departments in this company.
     *
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in this company.
     *
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in this company.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in this company.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Assigns an office as headquarters for a department.
     * The office must belong to the department.
     *
     * @param department the department for which to assign headquarters
     * @param office the office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        if (!departments.contains(department)) {
            return false;
        }
        
        return department.setHeadquarters(office);
    }

    /**
     * Assigns an employee as manager of a specified department.
     * The employee and the department must belong to the company.
     *
     * @param department the department for which to assign a manager
     * @param employee the employee to be assigned as manager
     * @return true if successful, false if the department already has a manager
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (!departments.contains(department) || !employees.contains(employee)) {
            return false;
        }
        
        return department.setManager(employee);
    }

    /**
     * Deletes a department: removes the department and its offices (including headquarters),
     * and then removes employees (including managers) from the company.
     *
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.contains(department)) {
            return false;
        }

        // Remove all employees of this department from the company
        employees.removeAll(department.getEmployees());

        // Remove the department
        departments.remove(department);
        
        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     *
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        if (!departments.contains(department)) {
            return false;
        }
        
        return department.getManager() != null;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * If all departments have already been assigned a manager, return null.
     *
     * @return a list of departments without managers, or null if all departments have managers
     */
    public List<Department> getDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department department : departments) {
            if (department.getManager() == null) {
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
    private List<Employee> employees;
    private Office headquarters;
    private Employee manager;

    /**
     * Constructs a new Department with empty offices and employees lists.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.headquarters = null;
        this.manager = null;
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
     * Gets the list of employees in this department.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in this department.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the headquarters office of this department.
     *
     * @return the headquarters office
     */
    public Office getHeadquarters() {
        return headquarters;
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
     * @param manager the manager to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Sets an office as headquarters for this department.
     * The office must belong to the department.
     *
     * @param office the office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean setHeadquarters(Office office) {
        if (!offices.contains(office) || this.headquarters != null) {
            return false;
        }
        
        this.headquarters = office;
        return true;
    }

    /**
     * Assigns an employee as manager of this department.
     *
     * @param employee the employee to be assigned as manager
     * @return true if successful, false if the department already has a manager
     */
    public boolean setManager(Employee employee) {
        if (this.manager != null) {
            return false;
        }
        
        this.manager = employee;
        return true;
    }
}

/**
 * Represents an office that can be assigned to a department.
 */
class Office {
    private String name;

    /**
     * Constructs a new Office.
     */
    public Office() {
        this.name = "";
    }

    /**
     * Gets the name of this office.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this office.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an employee who can work in a department and potentially be a manager.
 */
class Employee {
    private String name;

    /**
     * Constructs a new Employee.
     */
    public Employee() {
        this.name = "";
    }

    /**
     * Gets the name of this employee.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this employee.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}