import java.util.ArrayList;
import java.util.List;

/**
 * Represents an office location.
 */
class Office {
    private String name;
    
    public Office() {
        this.name = "";
    }
    
    public Office(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {
    private String name;
    
    public Employee() {
        this.name = "";
    }
    
    public Employee(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a department within the company.
 */
class Department {
    private String name;
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;
    
    public Department() {
        this.name = "";
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }
    
    public Department(String name) {
        this.name = name;
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
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
    
    /**
     * Checks if this department has a manager assigned.
     * 
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return this.manager != null;
    }
}

/**
 * Represents a company consisting of departments, offices, and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;
    
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
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
    
    /**
     * Assigns an office as headquarters for a department.
     * The office must belong to the department.
     * 
     * @param department the department to assign headquarters to
     * @param office the office to be set as headquarters
     * @return true if successful, false if the office doesn't exist in the department 
     *         or if the department already has a headquarters
     */
    public boolean assignHeadquarters(Department department, Office office) {
        // Check if department already has a headquarters
        if (department.getHeadquarter() != null) {
            return false;
        }
        
        // Check if office belongs to the department
        if (!department.getOffices().contains(office)) {
            return false;
        }
        
        // Assign the office as headquarters
        department.setHeadquarter(office);
        return true;
    }
    
    /**
     * Assigns an employee as manager of a specified department.
     * The employee and the department must belong to the company.
     * 
     * @param department the department to assign a manager to
     * @param employee the employee to be assigned as manager
     * @return true if successful, false if the department already has a manager 
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        // Check if department already has a manager
        if (department.getManager() != null) {
            return false;
        }
        
        // Check if employee belongs to the company
        if (!this.employees.contains(employee)) {
            return false;
        }
        
        // Check if department belongs to the company
        if (!this.departments.contains(department)) {
            return false;
        }
        
        // Assign the employee as manager
        department.setManager(employee);
        return true;
    }
    
    /**
     * Deletes a department: removes the department and its offices (including headquarters),
     * and removes employees (including managers) from the company.
     * 
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        // Check if department exists in the company
        if (!this.departments.contains(department)) {
            return false;
        }
        
        // Remove the department and its employees from the company
        this.departments.remove(department);
        
        // If the manager of this department is not manager of any other department,
        // remove them from the company's employees list
        Employee manager = department.getManager();
        if (manager != null) {
            boolean isManagerElsewhere = false;
            for (Department dept : this.departments) {
                if (manager.equals(dept.getManager())) {
                    isManagerElsewhere = true;
                    break;
                }
            }
            if (!isManagerElsewhere) {
                this.employees.remove(manager);
            }
        }
        
        return true;
    }
    
    /**
     * Verifies whether a department has a manager assigned.
     * 
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        return department.hasManager();
    }
    
    /**
     * Lists the departments of the company that have not been assigned a manager.
     * If all departments have already been assigned a manager, return null.
     * 
     * @return list of departments without managers, or null if all departments have managers
     */
    public List<Department> getDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department department : this.departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        
        // If all departments have managers, return null
        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }
        
        return departmentsWithoutManager;
    }
}