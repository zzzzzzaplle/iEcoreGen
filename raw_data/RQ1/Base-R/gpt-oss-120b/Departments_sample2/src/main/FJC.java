import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    private List<Department> departments = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Company() {
    }

    /* Getters and setters ---------------------------------------------------- */

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

    /* Utility methods -------------------------------------------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was added; {@code false} if it
     *         already exists in the company
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
     * @return {@code true} if the employee was added; {@code false} if it
     *         already exists in the company
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Assigns an office as the headquarters for a department.
     *
     * @param department the department for which to assign headquarters
     * @param office     the office to assign as headquarters
     * @return {@code true} if the headquarters was successfully assigned;
     *         {@code false} if the office is not part of the department or
     *         the department already has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        if (department == null || office == null) {
            return false;
        }
        // Check that the department belongs to the company
        if (!departments.contains(department)) {
            return false;
        }
        // Check that the office belongs to the department
        if (!department.getOffices().contains(office)) {
            return false;
        }
        // Check that the department does not already have a headquarters
        if (department.getHeadquarters() != null) {
            return false;
        }
        department.setHeadquarters(office);
        return true;
    }

    /**
     * Assigns an employee as the manager of a specified department.
     *
     * @param department the department to assign a manager to
     * @param employee   the employee to assign as manager
     * @return {@code true} if the manager was successfully assigned;
     *         {@code false} if the department already has a manager,
     *         or if the department or employee does not belong to the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        // Check that both department and employee belong to the company
        if (!departments.contains(department) || !employees.contains(employee)) {
            return false;
        }
        // Check that the department does not already have a manager
        if (department.getManager() != null) {
            return false;
        }
        // Ensure the employee is part of the department's employee list
        if (!department.getEmployees().contains(employee)) {
            department.addEmployee(employee);
        }
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department from the company.
     * This removes the department, its offices, and all employees belonging
     * to that department from the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department was successfully deleted;
     *         {@code false} if the department does not exist in the company
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove the department from the company's list
        departments.remove(department);

        // Remove all employees of the department from the company
        Iterator<Employee> empIter = employees.iterator();
        while (empIter.hasNext()) {
            Employee e = empIter.next();
            if (department.getEmployees().contains(e)) {
                empIter.remove();
            }
        }

        // The department's offices are automatically discarded as the
        // department object itself is removed.
        return true;
    }

    /**
     * Checks whether a department has a manager assigned.
     *
     * @param department the department to check
     * @return {@code true} if the department has a manager; {@code false}
     *         otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        return department.getManager() != null;
    }

    /**
     * Retrieves a list of departments that have not been assigned a manager.
     *
     * @return a list of departments without a manager; {@code null} if all
     *         departments already have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department dept : departments) {
            if (dept.getManager() == null) {
                result.add(dept);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    private String id;
    private String name;
    private List<Office> offices = new ArrayList<>();
    private Office headquarters; // can be null
    private Employee manager;    // can be null
    private List<Employee> employees = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Department() {
    }

    /* Getters and setters ---------------------------------------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    /* Utility methods -------------------------------------------------------- */

    /**
     * Adds an office to the department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added; {@code false} if it already
     *         exists
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /**
     * Adds an employee to the department.
     *
     * @param employee the employee to add
     * @return {@code true} if the employee was added; {@code false} if it
     *         already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }
}

/**
 * Represents an office location.
 */
class Office {

    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public Office() {
    }

    /* Getters and setters ---------------------------------------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an employee.
 */
class Employee {

    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /* Getters and setters ---------------------------------------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}