import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import.
 */
 class Company {

    private List<Department> departments;
    private List<Employee> employees;

    /** Unparameterized constructor */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /** ----------------------- Getters & Setters ----------------------- */

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
 ----------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
        }
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee            employees.add(employee);
        }
    }

    /**
     * Finds a department by its name.
     *
     * @param name department name
     * @return the Department object or {@code null} if not found
     */
    public Department findDepartmentByName(String name) {
        for (Department d : departments) {
            if (Objects.equals(d.getName(), name)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Finds an employee by its identifier.
     *
     * @param id employee identifier
     * @return the Employee object or {@code null} if not found
     */
    public Employee findEmployeeById(String id) if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }

    /** ----------------------- Functional Requirements ----------------------- */

    /**
     * Assigns an employee as the manager of a specified department.
     * <p>
     * The employee and the department must both belong to this company.
     * The department must not already have a manager.
     *
     * @param employee   the employee to become manager
     * @param department the department to receive the manager
     * @return {@code true} if the manager was successfully assigned,
     *         {@code false} otherwise (department already has a manager,
     *         employee or department does not belong to the company)
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        // Verify both belong to this company
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        // Department must not already have a manager
        if (department.getManager() != null) {
            return false;
        }
        // Assign manager
        department.setManager(employee);
        // Ensure employee is linked to the department
        employee.setDepartment(department);
        return true;
    }

    /**
     * Deletes a department from the company.
     * <p>
     * The department, its offices (including headquarters) and all its employees
     * (including the manager) are removed from the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed,
     *         {@code false} if the department does not exist in the company
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove all employees belonging to the department
        List<Employee> toRemove = new ArrayList<>();
        for (Employee e : employees) {
            if (department.equals(e.get        }
        employees.removeAll(toRemove);

        // Remove the department itself (its offices are held only inside the department)
        departments.remove(department);
        return true;
    }

    /**
     * Lists all departments that have not been assigned a manager.
     *
     * @return an immutable list of departments without a manager,
     *         or {@code null} if every department already has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            }
        }
        return result.isEmpty() ? null : Collections.unmodifiableList(result);
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String name;
    private List<Office> offices;
    private Office headquarters;          // can be null if not assigned
    private Employee manager;             // can be null if not assigned
    private List<Employee> employees;     // employees that belong to this department

    /** Unparameterized constructor */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /** ----------------------- Getters & Setters ----------------------- */

 setName(String name) {
        this.name = name;
    }

	public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public Office getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
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

    /** ----------------------- Helper Methods ---------------- @param office the office to add
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
        }
    }

    /**
     * Adds an employee to this department.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
            employee.setDepartment(this);
        }
    }

    /** ----------------------- Functional Requirements ----------------------- */

    /**
     * Assigns an office as the headquarters for this department.
     * <p * not already have a headquarters.
     *
     * @param office the office to become headquarters
     * @return {@code true} if the assignment succeeded,
     *         {@code false} if the office is not part of the department
     *         or a headquarters is already set
     */
    public boolean assignHeadquarters(Office office) {
        if (office == null) {
            return false;
        }
        // Office must belong to this department
        if (!offices.contains(office)) {
            return false;
        }
        // Department must not already have a headquarters
        if (headquarters != null) {
            return false;
        }
        this.headquarters this department @return {@code true} if a manager is assigned, {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }
}

/**
 * Represents an office location.
 */
class Office {

    private String address;
    private String id; // unique identifier, optional

    /** Unparameterized constructor */
    public Office() {
    }

    /** ----------------------- Getters & Setters ----------------------- */

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

/**
 * Represents an employee.
 */
class Employee {

    private String id;          // unique identifier
    private String name;
    private Department department; // department employee belongs to (may be null)

    /** Unparameterized constructor */
    public Employee() {
    }

    /** ----------------------- Getters & Setters ----------------------- */

    public String getId() {
        return.id = id name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}