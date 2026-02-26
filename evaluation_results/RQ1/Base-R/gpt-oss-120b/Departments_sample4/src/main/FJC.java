import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments, offices and employees.
 * Provides operations required by the functional specifications.
 */
 class Company {

    /** List of all departments belonging to the company. */
    private List<Department> departments = new ArrayList<>();

    /** List of all employees belonging to the company. */
    private List<Employee> employees = new ArrayList<>();

    /** Unparameterized constructor. */
    public Company() {
    }

    /** -------------------- Getters & Setters -------------------- */

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments != null ? departments : new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /** -------------------- Helper Methods -------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add (must not be {@code null})
     */
    public void addDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
        }
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add (must not be {@code null})
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
        }
    }

    /**
     * Checks whether the supplied department belongs to this company.
     *
     * @param department the department to test
     * @return {@code true} if the department is part of the company
     */
    private boolean containsDepartment(Department department) {
        return departments.contains(department);
    }

    /**
     * Checks whether the supplied employee belongs to this company.
     *
     * @param employee the employee to test
     * @return {@code true} if the employee is part of the company
     */
    private boolean containsEmployee(Employee employee) {
        return employees.contains(employee);
    }

    /** -------------------- Functional Requirements -------------------- */

    /**
     * Assigns an office as the headquarters for a department.
     * The office must already be part of the department and the department must not have a headquarters yet.
     *
     * @param department the department to which the headquarters will be assigned
     * @param office     the office that will become the headquarters
     * @return {@code true} if the assignment succeeded; {@code false} if the office does not belong to the department
     *         or the department already has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        if (department == null || office == null) {
            return false;
        }
        if (!containsDepartment(department)) {
            return false;
        }
        // Office must belong to the department
        if (!department.getOffices().contains(office)) {
            return false;
        }
        // Department must not already have a headquarters
        if (department.getHeadquarters() != null) {
            return false;
        }
        department.setHeadquarters(office);
        return true;
    }

    /**
     * Assigns an employee as manager of a specified department.
     * Both the employee and the department must belong to the company and the department must not already have a manager.
     *
     * @param department the department that will receive the manager
     * @param employee   the employee that will become the manager
     * @return {@code true} if the assignment succeeded; {@code false} if the department already has a manager
     *         or the employee does not belong to the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        if (!containsDepartment(department) || !containsEmployee(employee)) {
            return false;
        }
        // Department must not already have a manager
        if (department.getManager() != null) {
            return false;
        }
        department.setManager(employee);
        // Optionally, link employee to the department they manage
        employee.setDepartment(department);
        return true;
    }

    /**
     * Deletes a department from the company.
     * The department, all its offices (including the headquarters) and all employees that belong to the department
     * (including the manager) are removed.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed; {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !containsDepartment(department)) {
            return false;
        }

        // Remove all offices that belong to the department
        department.getOffices().clear();
        department.setHeadquarters(null);
        department.setManager(null);

        // Remove employees that belong to this department
        List<Employee> toRemove = new ArrayList<>();
        for (Employee e : employees) {
            if (Objects.equals(e.getDepartment(), department)) {
                toRemove.add(e);
            }
        }
        employees.removeAll(toRemove);

        // Finally remove the department itself
        departments.remove(department);
        return true;
    }

    /**
     * Checks whether a department has a manager assigned.
     *
     * @param department the department to examine
     * @return {@code true} if the department has a manager; {@code false} otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.getManager() != null;
    }

    /**
     * Returns a list of all departments that do not have a manager assigned.
     * If every department already has a manager, the method returns {@code null}.
     *
     * @return an unmodifiable list of departments without a manager, or {@code null} if none exist
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            if (d.getManager() == null) {
                result.add(d);
            }
        }
        return result.isEmpty() ? null : Collections.unmodifiableList(result);
    }
}

/**
 * Represents a department inside a company.
 * A department can have many offices, one of which may be designated as the headquarters,
 * and can have a manager that is an employee of the company.
 */
class Department {

    /** Name of the department (optional, used for identification). */
    private String name;

    /** Offices belonging to this department. */
    private List<Office> offices = new ArrayList<>();

    /** The headquarters office of this department (may be {@code null}). */
    private Office headquarters;

    /** The manager of this department (may be {@code null}). */
    private Employee manager;

    /** Unparameterized constructor. */
    public Department() {
    }

    /** -------------------- Getters & Setters -------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices != null ? offices : new ArrayList<>();
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

    /** -------------------- Helper Methods -------------------- */

    /**
     * Adds an office to the department.
     *
     * @param office the office to add (must not be {@code null})
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
            office.setDepartment(this);
        }
    }

    /**
     * Removes an office from the department.
     *
     * @param office the office to remove
     */
    public void removeOffice(Office office) {
        if (offices.remove(office)) {
            office.setDepartment(null);
            if (office.equals(headquarters)) {
                headquarters = null;
            }
        }
    }
}

/**
 * Represents an office location.
 * An office belongs to a single department.
 */
class Office {

    /** Physical address or identifier of the office. */
    private String location;

    /** The department this office belongs to (may be {@code null}). */
    private Department department;

    /** Unparameterized constructor. */
    public Office() {
    }

    /** -------------------- Getters & Setters -------------------- */

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Represents an employee of the company.
 * An employee may belong to a department and can be appointed as a manager.
 */
class Employee {

    /** Name of the employee. */
    private String name;

    /** Department the employee works for (may be {@code null}). */
    private Department department;

    /** Unparameterized constructor. */
    public Employee() {
    }

    /** -------------------- Getters & Setters -------------------- */

    public String getName() {
        return name;
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