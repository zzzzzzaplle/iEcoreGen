import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor to initialize the company.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * 
     * @param department The department to be added.
     * @return true if the department is added successfully, false otherwise.
     */
    public boolean addDepartment(Department department) {
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * 
     * @param employee The employee to be added.
     * @return true if the employee is added successfully, false otherwise.
     */
    public boolean addEmployee(Employee employee) {
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company.
     * 
     * @param department The department to be deleted.
     * @return true if the department is deleted successfully, false if the department doesn't exist.
     */
    public boolean deleteDepartment(Department department) {
        if (departments.contains(department)) {
            // Remove employees of the department from the company
            for (Employee employee : department.getEmployees()) {
                employees.remove(employee);
            }
            // Remove the department from the company
            return departments.remove(department);
        }
        return false;
    }

    /**
     * Lists the departments that have not been assigned a manager.
     * 
     * @return A list of departments without a manager, or null if all departments have a manager.
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        for (Department department : departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }

    /**
     * Assigns an employee as the manager of a specified department.
     * 
     * @param employee   The employee to be assigned as the manager.
     * @param department The department for which the manager is to be assigned.
     * @return true if the assignment is successful, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee, Department department) {
        if (departments.contains(department) && employees.contains(employee)) {
            return department.assignManager(employee);
        }
        return false;
    }

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
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    private List<Employee> employees;

    /**
     * Default constructor to initialize the department.
     */
    public Department() {
        this.offices = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for the department.
     * 
     * @param office The office to be assigned as the headquarter.
     * @return true if the assignment is successful, false if the office doesn't exist in the department or if the department already has a headquarter.
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (offices.contains(office) && headquarter == null) {
            headquarter = office;
            return true;
        }
        return false;
    }

    /**
     * Checks if the department has a manager assigned.
     * 
     * @return true if the department has a manager, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Assigns a manager to the department.
     * 
     * @param employee The employee to be assigned as the manager.
     * @return true if the assignment is successful, false if the department already has a manager.
     */
    public boolean assignManager(Employee employee) {
        if (manager == null) {
            manager = employee;
            return true;
        }
        return false;
    }

    /**
     * Deletes an employee from the department.
     * 
     * @param employee The employee to be deleted.
     * @return true if the employee is deleted successfully, false otherwise.
     */
    public boolean deleteEmployee(Employee employee) {
        if (employees.contains(employee)) {
            if (manager.equals(employee)) {
                manager = null;
            }
            return employees.remove(employee);
        }
        return false;
    }

    /**
     * Deletes an office from the department.
     * 
     * @param office The office to be deleted.
     * @return true if the office is deleted successfully, false otherwise.
     */
    public boolean deleteOffice(Office office) {
        if (offices.contains(office)) {
            if (headquarter != null && headquarter.equals(office)) {
                headquarter = null;
            }
            return offices.remove(office);
        }
        return false;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public boolean addEmployee(Employee employee) {
        return employees.add(employee);
    }

    public boolean addOffice(Office office) {
        return offices.add(office);
    }
}

/**
 * Represents an office.
 */
class Office {
    /**
     * Default constructor to initialize the office.
     */
    public Office() {
    }
}

/**
 * Represents an employee.
 */
class Employee {
    /**
     * Default constructor to initialize the employee.
     */
    public Employee() {
    }
}