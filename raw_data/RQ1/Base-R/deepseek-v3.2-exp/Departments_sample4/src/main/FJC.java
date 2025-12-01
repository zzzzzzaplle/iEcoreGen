import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor that initializes empty lists for departments and employees.
     */
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
        if (department == null || office == null) {
            return false;
        }
        
        if (department.getHeadquarters() != null) {
            return false;
        }
        
        if (!department.getOffices().contains(office)) {
            return false;
        }
        
        department.setHeadquarters(office);
        return true;
    }

    /**
     * Assigns an employee as manager of a specified department.
     * The employee and the department must belong to the company.
     * 
     * @param department the department to assign manager to
     * @param employee the employee to be set as manager
     * @return true if successful, false if the department already has a manager 
     *         or the employee doesn't exist in the company
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        
        if (department.getManager() != null) {
            return false;
        }
        
        if (!employees.contains(employee)) {
            return false;
        }
        
        if (!departments.contains(department)) {
            return false;
        }
        
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department from the company.
     * Removes the department and its offices (including headquarters), 
     * and then removes employees (including managers) from the company.
     * 
     * @param department the department to be deleted
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all employees that belong to this department
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDepartment() == department) {
                employeesToRemove.add(employee);
            }
        }
        employees.removeAll(employeesToRemove);
        
        // Remove the department
        departments.remove(department);
        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     * 
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.getManager() != null;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * 
     * @return list of departments without managers, or null if all departments have managers
     */
    public List<Department> getDepartmentsWithoutManager() {
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
 * Represents a department in a company.
 */
 class Department {
    private String name;
    private List<Office> offices;
    private Office headquarters;
    private Employee manager;

    /**
     * Default constructor that initializes an empty list of offices.
     */
    public Department() {
        this.offices = new ArrayList<>();
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
}

/**
 * Represents an office where a department is located.
 */
 class Office {
    private String location;
    private Department department;

    /**
     * Default constructor.
     */
    public Office() {
    }

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
 * Represents an employee who can be assigned as a manager of a department.
 */
 class Employee {
    private String name;
    private Department department;

    /**
     * Default constructor.
     */
    public Employee() {
    }

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