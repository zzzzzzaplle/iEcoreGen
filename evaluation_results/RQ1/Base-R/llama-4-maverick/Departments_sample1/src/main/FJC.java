import java.util.*;

/**
 * Represents a company with departments and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a new Company object.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     * @param departments the list of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Deletes a department from the company.
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.contains(department)) {
            return false;
        }
        departments.remove(department);
        for (Office office : department.getOffices()) {
            for (Employee employee : employees) {
                if (employee.getOffice() == office) {
                    employee.setOffice(null);
                }
            }
        }
        employees.removeIf(employee -> employee.getDepartment() == department);
        return true;
    }

    /**
     * Lists the departments that have not been assigned a manager.
     * @return a list of departments without a manager, or null if all departments have a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
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
 * Represents a department in the company.
 */
class Department {
    private String name;
    private List<Office> offices;
    private Office headquarters;
    private Employee manager;

    /**
     * Constructs a new Department object.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the name of the department.
     * @return the name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     * @param name the name of the department
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of offices in the department.
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in the department.
     * @param offices the list of offices
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarters office of the department.
     * @return the headquarters office
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarters the headquarters office
     */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return the manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Assigns an office as the headquarters for the department.
     * @param office the office to assign as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Office office) {
        if (!offices.contains(office) || headquarters != null) {
            return false;
        }
        headquarters = office;
        return true;
    }

    /**
     * Assigns an employee as the manager of the department.
     * @param employee the employee to assign as manager
     * @return true if successful, false if the department already has a manager
     */
    public boolean assignManager(Employee employee) {
        if (manager != null) {
            return false;
        }
        manager = employee;
        return true;
    }

    /**
     * Verifies whether the department has a manager assigned.
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }
}

/**
 * Represents an office in the company.
 */
class Office {
    private String location;

    /**
     * Constructs a new Office object.
     */
    public Office() {}

    /**
     * Gets the location of the office.
     * @return the location of the office
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the office.
     * @param location the location of the office
     */
    public void setLocation(String location) {
        this.location = location;
    }
}

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;
    private Department department;
    private Office office;

    /**
     * Constructs a new Employee object.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name of the employee
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the department of the employee.
     * @return the department of the employee
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee.
     * @param department the department of the employee
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Gets the office of the employee.
     * @return the office of the employee
     */
    public Office getOffice() {
        return office;
    }

    /**
     * Sets the office of the employee.
     * @param office the office of the employee
     */
    public void setOffice(Office office) {
        this.office = office;
    }
}