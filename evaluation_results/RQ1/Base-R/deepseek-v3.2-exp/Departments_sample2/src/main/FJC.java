import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company that consists of departments, offices, and employees.
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

    /**
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     * @param departments the list of departments to set
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
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Assigns an office as headquarters for a department.
     * The office must belong to the department.
     * 
     * @param department the department to assign headquarters for
     * @param office the office to set as headquarters
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
     * @param department the department to assign manager for
     * @param employee the employee to set as manager
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
     * Deletes a department: Removes the department and its offices (including headquarters),
     * and then removes employees (including managers) from the company.
     * 
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all offices from the department
        List<Office> officesToRemove = new ArrayList<>(department.getOffices());
        for (Office office : officesToRemove) {
            department.removeOffice(office);
        }
        
        // Remove manager if exists
        if (department.getManager() != null) {
            Employee manager = department.getManager();
            employees.remove(manager);
            department.setManager(null);
        }
        
        // Remove department from company
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
     * If all departments have already been assigned a manager, return null.
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
     * Constructs a new Department with empty lists of offices and no headquarters or manager.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the name of the department.
     * @return the department name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     * @param name the department name to set
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
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Adds an office to the department.
     * @param office the office to add
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
        }
    }

    /**
     * Removes an office from the department.
     * @param office the office to remove
     */
    public void removeOffice(Office office) {
        if (office != null) {
            offices.remove(office);
            // If the removed office was headquarters, clear headquarters
            if (office.equals(headquarters)) {
                headquarters = null;
            }
        }
    }

    /**
     * Gets the headquarters office of the department.
     * @return the headquarters office, or null if not set
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarters the headquarters office to set
     */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return the department manager, or null if not set
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the department manager to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * Represents an office where departments are located.
 */
 class Office {
    private String location;
    private String address;

    /**
     * Constructs a new Office with no location or address specified.
     */
    public Office() {
    }

    /**
     * Gets the location of the office.
     * @return the office location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the office.
     * @param location the office location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the address of the office.
     * @return the office address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the office.
     * @param address the office address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

/**
 * Represents an employee who can be assigned as a department manager.
 */
 class Employee {
    private String name;
    private String employeeId;
    private String position;

    /**
     * Constructs a new Employee with no name, ID, or position specified.
     */
    public Employee() {
    }

    /**
     * Gets the name of the employee.
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee ID.
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID.
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the position of the employee.
     * @return the employee position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the employee.
     * @param position the employee position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
}