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
        // Remove the department and its offices
        departments.remove(department);
        // Remove employees from the company
        for (Employee employee : new ArrayList<>(employees)) {
            if (employee.getDepartment() == department) {
                employees.remove(employee);
            }
        }
        return true;
    }

    /**
     * Lists the departments that have not been assigned a manager.
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
     * @param department the department to be managed
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignManager(Employee employee, Department department) {
        if (!employees.contains(employee) || !departments.contains(department)) {
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
 * Represents a department with offices and a manager.
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
        if (!offices.contains(office) || headquarter != null) {
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
        return true; // Note: This method does not actually remove the employee from the company's employee list
    }

    /**
     * Deletes an office from the department.
     * 
     * @param office the office to be deleted
     * @return true if the office is deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (offices.remove(office)) {
            if (headquarter == office) {
                headquarter = null;
            }
            return true;
        }
        return false;
    }

    /**
     * Assigns an employee as the manager of the department.
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
    private Department department;

    /**
     * Constructs a new Employee object.
     */
    public Employee() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}