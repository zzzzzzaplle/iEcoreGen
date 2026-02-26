import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that contains departments, offices, and employees.
 */
 class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a new Company with empty lists of departments and employees.
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
     * @param department the department to assign a manager to
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
     * Deletes a department: Remove the department and its offices (including headquarters), 
     * and then remove employees (including managers) from the company.
     *
     * @param department the department to be deleted
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all employees who belong to this department
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDepartment() != null && employee.getDepartment().equals(department)) {
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
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        
        for (Department department : departments) {
            if (department.getManager() == null) {
                departmentsWithoutManager.add(department);
            }
        }
        
        if (departmentsWithoutManager.isEmpty()) {
            return null;
        }
        
        return departmentsWithoutManager;
    }
}

/**
 * Represents a department within a company.
 */
 class Department {
    private String name;
    private List<Office> offices;
    private Office headquarters;
    private Employee manager;

    /**
     * Constructs a new Department with empty lists of offices and no headquarters or manager.
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

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
        }
    }

    /**
     * Removes an office from this department.
     *
     * @param office the office to remove
     * @return true if successful, false if the office doesn't exist in the department
     */
    public boolean removeOffice(Office office) {
        if (office != null && offices.contains(office)) {
            // If this office is the headquarters, clear the headquarters reference
            if (office.equals(headquarters)) {
                headquarters = null;
            }
            offices.remove(office);
            return true;
        }
        return false;
    }
}

/**
 * Represents an office location.
 */
 class Office {
    private String location;
    private String address;

    /**
     * Constructs a new Office with no location or address specified.
     */
    public Office() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents an employee in the company.
 */
 class Employee {
    private String name;
    private String employeeId;
    private Department department;

    /**
     * Constructs a new Employee with no name, ID, or department specified.
     */
    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}