import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;
    private String id;

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
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the employee.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
}

/**
 * Represents an office where departments are located.
 */
class Office {
    private String officeId;
    private String address;
    private boolean isHeadquarters;

    /**
     * Default constructor for Office.
     */
    public Office() {
    }

    /**
     * Gets the office ID.
     *
     * @return the office ID
     */
    public String getOfficeId() {
        return officeId;
    }

    /**
     * Sets the office ID.
     *
     * @param officeId the office ID to set
     */
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    /**
     * Gets the address of the office.
     *
     * @return the address of the office
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the office.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
    private String departmentId;
    private String name;
    private List<Office> offices;
    private Employee manager;

    /**
     * Default constructor for Department.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the department ID.
     *
     * @return the department ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department ID.
     *
     * @param departmentId the department ID to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
     * Checks if this department has a headquarters assigned.
     *
     * @return true if a headquarters is assigned, false otherwise
     */
    public boolean hasHeadquarters() {
        for (Office office : offices) {
            if (office.isHeadquarters()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the headquarters office of this department.
     *
     * @return the headquarters office, or null if none exists
     */
    public Office getHeadquarters() {
        for (Office office : offices) {
            if (office.isHeadquarters()) {
                return office;
            }
        }
        return null;
    }

    /**
     * Assigns an office as the headquarters for this department.
     * The office must belong to this department.
     *
     * @param office the office to be assigned as headquarters
     * @return true if successful, false if the office doesn't exist in the department
     *         or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Office office) {
        // Check if department already has a headquarters
        if (hasHeadquarters()) {
            return false;
        }

        // Check if the office belongs to this department
        if (!offices.contains(office)) {
            return false;
        }

        // Set the office as headquarters
        office.setHeadquarters(true);
        return true;
    }

    /**
     * Checks if this department has a manager assigned.
     *
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }
}

/**
 * Represents a company consisting of departments, offices, and employees.
 */
class Company {
    private String companyId;
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
     * Gets the company ID.
     *
     * @return the company ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Sets the company ID.
     *
     * @param companyId the company ID to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
        // Check if department belongs to this company
        if (!departments.contains(department)) {
            return false;
        }

        return department.assignHeadquarters(office);
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
        // Check if department belongs to this company
        if (!departments.contains(department)) {
            return false;
        }

        // Check if employee belongs to this company
        if (!employees.contains(employee)) {
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
     * Deletes a department: Remove the department and its offices (including headquarters),
     * and then remove employees (including managers) from the company.
     *
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        // Check if department exists in the company
        if (!departments.contains(department)) {
            return false;
        }

        // Remove the department from the company
        departments.remove(department);

        // Note: In a real implementation, you might also want to remove
        // the employees from the company's employee list if they only
        // belonged to this department. However, the requirement doesn't
        // specify this, so we're leaving employees in the company's list.

        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     *
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        // Check if department belongs to this company
        if (!departments.contains(department)) {
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

        for (Department department : departments) {
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