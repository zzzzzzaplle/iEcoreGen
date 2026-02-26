import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
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
     * @param department the department to assign headquarters to
     * @param office the office to set as headquarters
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
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
     * @param department the department to assign manager to
     * @param employee the employee to set as manager
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
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
     * @param department the department to delete
     * @return true if successful, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove employees associated with this department
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDepartment() != null && emp.getDepartment().equals(department)) {
                employeesToRemove.add(emp);
            }
        }
        employees.removeAll(employeesToRemove);
        
        // Remove the department
        departments.remove(department);
        return true;
    }

    /**
     * Verifies whether a department has a manager assigned.
     * @param department the department to check
     * @return true if the department has a manager assigned, false otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.getManager() != null;
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
     * Default constructor that initializes an empty list of offices.
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
     * Gets the headquarters office of the department.
     * @return the headquarters office
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
     * @return the department manager
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

    /**
     * Adds an office to the department.
     * @param office the office to add
     * @return true if the office was added successfully, false if the office is null or already exists
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        offices.add(office);
        return true;
    }

    /**
     * Removes an office from the department.
     * If the office is the headquarters, the headquarters is set to null.
     * @param office the office to remove
     * @return true if the office was removed successfully, false if the office is null or doesn't exist
     */
    public boolean removeOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        offices.remove(office);
        if (office.equals(headquarters)) {
            headquarters = null;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents an office location.
 */
 class Office {
    private String location;
    private Department department;

    /**
     * Default constructor.
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
     * Gets the department that this office belongs to.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department that this office belongs to.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(location, office.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}

/**
 * Represents an employee in the company.
 */
 class Employee {
    private String name;
    private Department department;

    /**
     * Default constructor.
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
     * Gets the department the employee belongs to.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department the employee belongs to.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}