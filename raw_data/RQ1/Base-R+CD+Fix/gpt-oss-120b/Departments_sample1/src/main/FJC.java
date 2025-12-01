import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** The list of departments belonging to this company. */
    private List<Department> departments = new ArrayList<>();

    /** The list of employees belonging to this company. */
    private List<Employee> employees = new ArrayList<>();

    /** Default (no‑arg) constructor. */
    public Company() {
        // intentionally empty
    }

    /* -------------------------------------------------
       Getters and Setters
       ------------------------------------------------- */

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    public void setDepartments(List<Department> departments) {
        this.departments = new ArrayList<>(departments);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    /* -------------------------------------------------
       Business methods required by the functional spec
       ------------------------------------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was added,
     *         {@code false} if the department is {@code null}
     *         or already present in the company
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
     * @return {@code true} if the employee was added,
     *         {@code false} if the employee is {@code null}
     *         or already present in the company
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company. The department, its offices
     * (including the headquarters) and its manager are removed from the
     * company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove manager (if any) from the global employee list
        Employee manager = department.getManager();
        if (manager != null) {
            employees.remove(manager);
        }

        // The department's offices are held only inside the department,
        // removing the department dereferences them and they become eligible
        // for garbage collection.

        // Finally remove the department itself
        return departments.remove(department);
    }

    /**
     * Returns a list of departments that have not been assigned a manager.
     *
     * @return a mutable {@link List} of departments without a manager,
     *         or {@code null} if every department already has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            if (!d.hasManager()) {
                result.add(d);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Assigns an employee as the manager of a given department.
     *
     * @param employee   the employee to become manager
     * @param department the department to which the manager will be assigned
     * @return {@code true} if the assignment succeeded,
     *         {@code false} if the employee or department does not belong to
     *         the company, or the department already has a manager
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        if (department.getManager() != null) {
            return false; // already has a manager
        }
        department.setManager(employee);
        return true;
    }

    /* -------------------------------------------------
       Additional helper methods (optional)
       ------------------------------------------------- */

    /**
     * Checks whether a department exists in the company.
     *
     * @param department the department to check
     * @return {@code true} if the department is part of the company
     */
    public boolean containsDepartment(Department department) {
        return departments.contains(department);
    }

    /**
     * Checks whether an employee exists in the company.
     *
     * @param employee the employee to check
     * @return {@code true} if the employee is part of the company
     */
    public boolean containsEmployee(Employee employee) {
        return employees.contains(employee);
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices = new ArrayList<>();

    /** The headquarter office of this department (may be {@code null}). */
    private Office headquarter = null;

    /** The manager of this department (may be {@code null}). */
    private Employee manager = null;

    /** Default (no‑arg) constructor. */
    public Department() {
        // intentionally empty
    }

    /* -------------------------------------------------
       Getters and Setters
       ------------------------------------------------- */

    public List<Office> getOffices() {
        return Collections.unmodifiableList(offices);
    }

    public void setOffices(List<Office> offices) {
        this.offices = new ArrayList<>(offices);
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

    /* -------------------------------------------------
       Business methods required by the functional spec
       ------------------------------------------------- */

    /**
     * Assigns an existing office of this department as the headquarter.
     *
     * @param office the office to become headquarter
     * @return {@code true} if the office belongs to the department and the
     *         department does not already have a headquarter;
     *         {@code false} otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (!offices.contains(office) || headquarter != null) {
            return false;
        }
        headquarter = office;
        return true;
    }

    /**
     * Checks whether this department currently has a manager assigned.
     *
     * @return {@code true} if a manager is assigned, {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Removes an employee from the department. The only employee stored at the
     * department level is the manager; if the supplied employee is the manager,
     * the manager reference is cleared.
     *
     * @param employee the employee to remove
     * @return {@code true} if the employee was the manager and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee != null && employee.equals(manager)) {
            manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from the department. If the office being removed is the
     * headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && office.equals(headquarter)) {
            headquarter = null;
        }
        return removed;
    }

    /**
     * Adds a new office to the department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added,
     *         {@code false} if the office is {@code null} or already present
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /* -------------------------------------------------
       Equality based on object identity (default)
       ------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}

/**
 * Represents an office. For simplicity, an office has a unique identifier and a name.
 */
class Office {

    /** Unique identifier of the office. */
    private int id;

    /** Human‑readable name of the office. */
    private String name;

    /** Default (no‑arg) constructor. */
    public Office() {
        // intentionally empty
    }

    /**
     * Creates an office with the given id and name.
     *
     * @param id   the unique identifier
     * @param name the office name
     */
    public Office(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /* -------------------------------------------------
       Getters and Setters
       ------------------------------------------------- */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* -------------------------------------------------
       Equality based on the unique identifier
       ------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;
        return id == office.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an employee. For simplicity, an employee has a unique identifier and a name.
 */
class Employee {

    /** Unique identifier of the employee. */
    private int id;

    /** Human‑readable name of the employee. */
    private String name;

    /** Default (no‑arg) constructor. */
    public Employee() {
        // intentionally empty
    }

    /**
     * Creates an employee with the given id and name.
     *
     * @param id   the unique identifier
     * @param name the employee name
     */
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /* -------------------------------------------------
       Getters and Setters
       ------------------------------------------------- */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* -------------------------------------------------
       Equality based on the unique identifier
       ------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}