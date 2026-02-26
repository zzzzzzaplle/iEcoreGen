import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that owns departments, employees and manages
 * the relationships between them.
 */
 class Company {

    /** All departments belonging to this company. */
    private List<Department> departments = new ArrayList<>();

    /** All employees belonging to this company. */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Company() {
    }

    /** @return an unmodifiable view of the departments list. */
    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    /** @param departments the complete list of departments for this company. */
    public void setDepartments(List<Department> departments) {
        this.departments = (departments != null) ? departments : new ArrayList<>();
    }

    /** @return an unmodifiable view of the employees list. */
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    /** @param employees the complete list of employees for this company. */
    public void setEmployees(List<Employee> employees) {
        this.employees = (employees != null) ? employees : new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarters for a department.
     *
     * @param department the department that should receive a headquarters
     * @param office     the office that will become the headquarters
     * @return {@code true} if the assignment succeeded; {@code false} if the
     *         office does not belong to the department or the department already
     *         has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        if (department == null || office == null) {
            return false;
        }
        // office must belong to the department
        if (!department.getOffices().contains(office)) {
            return false;
        }
        // department must not already have a headquarters
        if (department.getHeadquarters() != null) {
            return false;
        }
        department.setHeadquarters(office);
        return true;
    }

    /**
     * Assigns an employee as the manager of a department.
     *
     * @param department the department that will receive a manager
     * @param employee   the employee to be appointed as manager
     * @return {@code true} if the assignment succeeded; {@code false} if the
     *         department already has a manager or the employee does not belong
     *         to this company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        // employee must belong to the company
        if (!employees.contains(employee)) {
            return false;
        }
        // department must not already have a manager
        if (department.getManager() != null) {
            return false;
        }
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department from the company. All offices belonging to the
     * department (including its headquarters) are removed, and every employee
     * that belongs to the department (including its manager) is removed from the
     * company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed; {@code false}
     *         otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null) {
            return false;
        }
        if (!departments.contains(department)) {
            return false;
        }

        // Remove employees that belong to the department
        employees.removeIf(emp -> Objects.equals(emp.getDepartment(), department));

        // Remove the department (its offices are held inside the department object)
        departments.remove(department);
        return true;
    }

    /**
     * Checks whether a given department has a manager assigned.
     *
     * @param department the department to check
     * @return {@code true} if the department has a manager; {@code false} otherwise
     */
    public boolean verifyDepartmentHasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.getManager() != null;
    }

    /**
     * Retrieves a list of all departments that do not have a manager assigned.
     *
     * @return a mutable list containing the departments without managers, or
     *         {@code null} if every department already has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            if (d.getManager() == null) {
                result.add(d);
            }
        }
        return result.isEmpty() ? null : result;
    }

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
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
        }
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    private String name;
    private List<Office> offices = new ArrayList<>();
    private Office headquarters;               // may be null
    private Employee manager;                  // may be null

    /** No‑argument constructor required by the specification. */
    public Department() {
    }

    /** @return the department's name. */
    public String getName() {
        return name;
    }

    /** @param name the department's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return an unmodifiable view of the offices belonging to this department. */
    public List<Office> getOffices() {
        return Collections.unmodifiableList(offices);
    }

    /** @param offices the list of offices for this department. */
    public void setOffices(List<Office> offices) {
        this.offices = (offices != null) ? offices : new ArrayList<>();
    }

    /** @return the headquarters office, or {@code null} if none is set. */
    public Office getHeadquarters() {
        return headquarters;
    }

    /** @param headquarters the office to set as headquarters. */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /** @return the manager employee, or {@code null} if none is set. */
    public Employee getManager() {
        return manager;
    }

    /** @param manager the employee to set as manager. */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
            office.setDepartment(this);
        }
    }
}

/**
 * Represents an office location.
 */
class Office {

    private String location;
    private Department department;   // back‑reference to its department

    /** No‑argument constructor required by the specification. */
    public Office() {
    }

    /** @return the location description of the office. */
    public String getLocation() {
        return location;
    }

    /** @param location the location description of the office. */
    public void setLocation(String location) {
        this.location = location;
    }

    /** @return the department that owns this office. */
    public Department getDepartment() {
        return department;
    }

    /** @param department the department that owns this office. */
    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {

    private String name;
    private Department department;   // department the employee works in (may be null)

    /** No‑argument constructor required by the specification. */
    public Employee() {
    }

    /** @return the employee's name. */
    public String getName() {
        return name;
    }

    /** @param name the employee's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the department this employee belongs to. */
    public Department getDepartment() {
        return department;
    }

    /** @param department the department this employee belongs to. */
    public void setDepartment(Department department) {
        this.department = department;
    }
}