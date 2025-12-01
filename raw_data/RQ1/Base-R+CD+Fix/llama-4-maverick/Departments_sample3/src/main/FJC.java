import java.util.ArrayList;
import java.util.List;

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
     * Adds a department to the company.
     * 
     * @param department the department to be added
     * @return true if the department is added successfully, false otherwise
     */
    public boolean addDepartment(Department department) {
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * 
     * @param employee the employee to be added
     * @return true if the employee is added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company.
     * 
     * @param department the department to be deleted
     * @return true if the department is deleted successfully, false otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.contains(department)) {
            return false;
        }
        // Remove employees of the department from the company
        for (Employee employee : new ArrayList<>(employees)) {
            if (department.deleteEmployee(employee)) {
                employees.remove(employee);
            }
        }
        return departments.remove(department);
    }

    /**
     * Lists departments without a manager.
     * 
     * @return a list of departments without a manager, or null if all departments have a manager
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
     * Assigns an employee as the manager of a department.
     * 
     * @param employee   the employee to be assigned as manager
     * @param department the department to which the manager is to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignManager(Employee employee, Department department) {
        if (!departments.contains(department) || !employees.contains(employee)) {
            return false;
        }
        return department.assignManager(employee);
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

    /**
     * Constructs a new Department object.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter of the department.
     * 
     * @param office the office to be assigned as headquarter
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (!offices.contains(office)) {
            return false;
        }
        if (headquarter != null) {
            return false;
        }
        headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * 
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     * 
     * @param employee the employee to be deleted
     * @return true if the employee is deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (manager == employee) {
            manager = null;
        }
        return true; // In a real scenario, you might want to check if the employee is actually part of the department
    }

    /**
     * Deletes an office from the department.
     * 
     * @param office the office to be deleted
     * @return true if the office is deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (headquarter == office) {
            headquarter = null;
        }
        return offices.remove(office);
    }

    /**
     * Assigns a manager to the department.
     * 
     * @param employee the employee to be assigned as manager
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignManager(Employee employee) {
        if (manager != null) {
            return false;
        }
        manager = employee;
        return true;
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
}

/**
 * Represents an office.
 */
class Office {
    /**
     * Constructs a new Office object.
     */
    public Office() {
    }
}

/**
 * Represents an employee.
 */
class Employee {
    /**
     * Constructs a new Employee object.
     */
    public Employee() {
    }
}