import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an employee of the company.
 */
 class Employee {

    private String name;
    private int id;

    /** Default constructor */
    public Employee() {
    }

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Setter for name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for id */
    public int getId() {
        return id;
    }

    /** Setter for id */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + '\'' + '}';
    }
}

/**
 * Represents an office belonging to a department.
 */
 class Office {

    private String location;
    private int number;

    /** Default constructor */
    public Office() {
    }

    /** Getter for location */
    public String getLocation() {
        return location;
    }

    /** Setter for location */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Getter for number */
    public int getNumber() {
        return number;
    }

    /** Setter for number */
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Office{number=" + number + ", location='" + location + '\'' + '}';
    }
}

/**
 * Represents a department inside a company.
 */
 class Department {

    private List<Office> offices = new ArrayList<>();
    private Office headquarter;      // may be null if not assigned
    private Employee manager;        // may be null if not assigned

    /** Default constructor */
    public Department() {
    }

    /** Getter for offices (read‑only list) */
    public List<Office> getOffices() {
        return Collections.unmodifiableList(offices);
    }

    /** Setter for offices (replaces the whole list) */
    public void setOffices(List<Office> offices) {
        this.offices = offices != null ? offices : new ArrayList<>();
    }

    /** Getter for headquarter */
    public Office getHeadquarter() {
        return headquarter;
    }

    /** Setter for headquarter */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /** Getter for manager */
    public Employee getManager() {
        return manager;
    }

    /** Setter for manager */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     * @return true if the office was added, false if the office is null or already present
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /**
     * Assigns the given office as the headquarter of this department.
     * The office must already belong to the department and the department must
     * not already have a headquarter assigned.
     *
     * @param office the office to be set as headquarter
     * @return true if the assignment succeeded, false otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        // office must belong to this department
        if (!offices.contains(office)) {
            return false;
        }
        // headquarter must not already be assigned
        if (this.headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks whether this department has a manager assigned.
     *
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes the given employee from this department.
     * Currently the only employee directly linked to a department is the manager.
     *
     * @param employee the employee to delete
     * @return true if the employee was the manager and was removed, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employee.equals(this.manager)) {
            this.manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes the given office from this department.
     * If the office is the current headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return true if the office existed and was removed, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && office.equals(this.headquarter)) {
            this.headquarter = null;
        }
        return removed;
    }

    @Override
    public String toString() {
        return "Department{headquarter=" + headquarter + ", manager=" + manager + ", offices=" + offices + '}';
    }
}

/**
 * Represents the whole company, containing departments and employees.
 */
 class Company {

    private List<Department> departments = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    /** Default constructor */
    public Company() {
    }

    /** Getter for departments (read‑only) */
    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    /** Setter for departments (replaces the whole list) */
    public void setDepartments(List<Department> departments) {
        this.departments = departments != null ? departments : new ArrayList<>();
    }

    /** Getter for employees (read‑only) */
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    /** Setter for employees (replaces the whole list) */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return true if the department was added, false if null or already present
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
     * @return true if the employee was added, false if null or already present
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company. All offices belonging to the department
     * (including its headquarter) are removed, and the department's manager (if any)
     * is also removed from the company's employee list.
     *
     * @param department the department to delete
     * @return true if the department existed and was removed, false otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null) {
            return false;
        }
        boolean removed = departments.remove(department);
        if (!removed) {
            return false; // department not found
        }

        // Remove manager from company employee list, if present
        Employee mgr = department.getManager();
        if (mgr != null) {
            employees.remove(mgr);
        }

        // No explicit storage for offices at company level, so nothing else to do.
        // The department object will be garbage‑collected together with its offices.

        return true;
    }

    /**
     * Lists all departments that do not have a manager assigned.
     *
     * @return a list of departments without a manager, or null if every department has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> withoutManager = new ArrayList<>();
        for (Department d : departments) {
            if (!d.hasManager()) {
                withoutManager.add(d);
            }
        }
        return withoutManager.isEmpty() ? null : withoutManager;
    }

    /**
     * Assigns the given employee as manager of the specified department.
     * Both the employee and the department must belong to this company.
     *
     * @param employee   the employee to become manager
     * @param department the department to receive the manager
     * @return true if assignment succeeded, false otherwise (e.g., employee already manages another department,
     *         department already has a manager, or either object is not part of the company)
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        // employee must be part of the company
        if (!employees.contains(employee)) {
            return false;
        }
        // department must be part of the company
        if (!departments.contains(department)) {
            return false;
        }
        // department must not already have a manager
        if (department.hasManager()) {
            return false;
        }
        // Assign manager
        department.setManager(employee);
        return true;
    }

    @Override
    public String toString() {
        return "Company{departments=" + departments + ", employees=" + employees + '}';
    }
}