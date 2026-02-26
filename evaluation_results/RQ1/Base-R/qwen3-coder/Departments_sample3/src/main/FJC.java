import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;
    private int id;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
    }

    /**
     * Gets the name of the employee.
     *
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the employee.
     *
     * @return the ID of the employee
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the employee.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
}

/**
 * Represents an office where departments are located.
 */
class Office {
    private String location;
    private int officeId;
    private boolean isHeadquarters;

    /**
     * Default constructor for Office.
     */
    public Office() {
        this.isHeadquarters = false;
    }

    /**
     * Gets the location of the office.
     *
     * @return the location of the office
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the office.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the ID of the office.
     *
     * @return the office ID
     */
    public int getOfficeId() {
        return officeId;
    }

    /**
     * Sets the ID of the office.
     *
     * @param officeId the office ID to set
     */
    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    /**
     * Checks if this office is the headquarters.
     *
     * @return true if this office is the headquarters, false otherwise
     */
    public boolean isHeadquarters() {
        return isHeadquarters;
    }

    /**
     * Sets whether this office is the headquarters.
     *
     * @param isHeadquarters true if this office is the headquarters, false otherwise
     */
    public void setHeadquarters(boolean isHeadquarters) {
        this.isHeadquarters = isHeadquarters;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private String name;
    private int departmentId;
    private Employee manager;
    private List<Office> offices;

    /**
     * Default constructor for Department.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the name of the department.
     *
     * @return the name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the department.
     *
     * @return the department ID
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the ID of the department.
     *
     * @param departmentId the department ID to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the manager of the department.
     *
     * @return the manager employee
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     *
     * @param manager the manager to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Gets the list of offices for this department.
     *
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices for this department.
     *
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     */
    public void addOffice(Office office) {
        this.offices.add(office);
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
     * Checks if this department has a headquarters assigned.
     *
     * @return true if a headquarters is assigned, false otherwise
     */
    public boolean hasHeadquarters() {
        return this.offices.stream().anyMatch(Office::isHeadquarters);
    }

    /**
     * Gets the headquarters office for this department.
     *
     * @return the headquarters office, or null if none exists
     */
    public Office getHeadquarters() {
        return this.offices.stream()
                .filter(Office::isHeadquarters)
                .findFirst()
                .orElse(null);
    }
}

/**
 * Represents a company with departments, employees, and offices.
 */
class Company {
    private String name;
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     *
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
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
        // Check if department exists in company
        if (!this.departments.contains(department)) {
            return false;
        }

        // Check if office exists in department
        if (!department.getOffices().contains(office)) {
            return false;
        }

        // Check if department already has a headquarters
        if (department.hasHeadquarters()) {
            return false;
        }

        // Assign the office as headquarters
        office.setHeadquarters(true);
        return true;
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
        // Check if department exists in company
        if (!this.departments.contains(department)) {
            return false;
        }

        // Check if employee exists in company
        if (!this.employees.contains(employee)) {
            return false;
        }

        // Check if department already has a manager
        if (department.hasManager()) {
            return false;
        }

        // Assign the employee as manager
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department: removes the department and its offices (including headquarters),
     * and then removes employees (including managers) from the company.
     *
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        // Check if department exists in company
        if (!this.departments.contains(department)) {
            return false;
        }

        // Remove the department from the company
        this.departments.remove(department);

        // Remove all employees of this department from the company
        Employee manager = department.getManager();
        if (manager != null && this.employees.contains(manager)) {
            this.employees.remove(manager);
        }

        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     *
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        // Check if department exists in company
        if (!this.departments.contains(department)) {
            return false;
        }

        return department.hasManager();
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * If all departments have already been assigned a manager, return null.
     *
     * @return a list of departments without managers, or null if all departments have managers
     */
    public List<Department> getDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();

        for (Department department : this.departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }

        // If all departments have managers, return null
        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }

        return departmentsWithoutManager;
    }
}