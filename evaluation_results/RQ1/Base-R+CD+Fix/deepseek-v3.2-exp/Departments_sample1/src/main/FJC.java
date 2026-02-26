import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor initializing empty lists for departments and employees.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department The department to add
     * @return true if department was added successfully, false if department is null or already exists
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to add
     * @return true if employee was added successfully, false if employee is null or already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company including its offices and employees.
     * @param department The department to delete
     * @return true if department was deleted successfully, false if department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove department's offices
        List<Office> offices = department.getOffices();
        for (Office office : new ArrayList<>(offices)) {
            department.deleteOffice(office);
        }
        
        // Remove department's manager if exists
        if (department.hasManager()) {
            Employee manager = department.getManager();
            department.deleteEmployee(manager);
            employees.remove(manager);
        }
        
        return departments.remove(department);
    }

    /**
     * Lists departments that have not been assigned a manager.
     * @return List of departments without managers, or null if all departments have managers
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
     * Assigns an employee as manager of a specified department.
     * @param employee The employee to assign as manager
     * @param department The department to assign the manager to
     * @return true if assignment successful, false if department already has manager or employee doesn't exist
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || 
            !employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        
        if (department.hasManager()) {
            return false;
        }
        
        department.setManager(employee);
        return true;
    }

    // Getters and setters
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
     * Default constructor initializing empty list for offices.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as headquarters for this department.
     * @param office The office to assign as headquarters
     * @return true if assignment successful, false if office doesn't exist in department or department already has headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office)) {
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
     * @return true if manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     * @param employee The employee to delete
     * @return true if employee was deleted successfully, false if employee doesn't exist in department
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null || !Objects.equals(manager, employee)) {
            return false;
        }
        
        manager = null;
        return true;
    }

    /**
     * Deletes an office from the department.
     * @param office The office to delete
     * @return true if office was deleted successfully, false if office doesn't exist in department
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        // If the office being deleted is the headquarters, clear headquarters reference
        if (Objects.equals(headquarter, office)) {
            headquarter = null;
        }
        
        return offices.remove(office);
    }

    // Getters and setters
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
 * Represents an office where departments are located.
 */
class Office {
    // Basic office class - can be extended with additional properties as needed
    public Office() {
        // Default constructor
    }
}

/**
 * Represents an employee in the company.
 */
class Employee {
    // Basic employee class - can be extended with additional properties as needed
    public Employee() {
        // Default constructor
    }
}