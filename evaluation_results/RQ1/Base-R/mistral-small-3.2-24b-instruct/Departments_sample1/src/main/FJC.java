import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments;

    public Company() {
        this.departments = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * @param department The department to add.
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Removes a department from the company.
     * @param department The department to remove.
     * @return true if the department was removed, false otherwise.
     */
    public boolean removeDepartment(Department department) {
        return this.departments.remove(department);
    }

    /**
     * Gets all departments in the company.
     * @return List of departments.
     */
    public List<Department> getDepartments() {
        return new ArrayList<>(this.departments);
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * @return List of departments without a manager, or null if all departments have a manager.
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = new ArrayList<>();
        for (Department department : this.departments) {
            if (!department.hasManager()) {
                departmentsWithoutManager.add(department);
            }
        }
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }
}

class Department {
    private List<Office> offices;
    private Office headquarters;
    private Employee manager;
    private Company company;

    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as headquarters for the department.
     * @param office The office to assign as headquarters.
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters.
     */
    public boolean assignHeadquarters(Office office) {
        if (this.offices.contains(office) && this.headquarters == null) {
            this.headquarters = office;
            return true;
        }
        return false;
    }

    /**
     * Assigns an employee as manager of the department.
     * @param employee The employee to assign as manager.
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee) {
        if (this.manager == null && this.company != null && this.company.getEmployees().contains(employee)) {
            this.manager = employee;
            return true;
        }
        return false;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise.
     */
    public boolean hasManager() {
        return this.manager != null;
    }

    /**
     * Adds an office to the department.
     * @param office The office to add.
     */
    public void addOffice(Office office) {
        this.offices.add(office);
    }

    /**
     * Removes an office from the department.
     * @param office The office to remove.
     * @return true if the office was removed, false otherwise.
     */
    public boolean removeOffice(Office office) {
        if (this.headquarters != null && this.headquarters.equals(office)) {
            this.headquarters = null;
        }
        return this.offices.remove(office);
    }

    /**
     * Gets all offices in the department.
     * @return List of offices.
     */
    public List<Office> getOffices() {
        return new ArrayList<>(this.offices);
    }

    /**
     * Gets the headquarters of the department.
     * @return The headquarters office.
     */
    public Office getHeadquarters() {
        return this.headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return The manager employee.
     */
    public Employee getManager() {
        return this.manager;
    }

    /**
     * Sets the company for the department.
     * @param company The company.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the company of the department.
     * @return The company.
     */
    public Company getCompany() {
        return this.company;
    }
}

class Office {
    private String address;

    public Office(String address) {
        this.address = address;
    }

    /**
     * Gets the address of the office.
     * @return The address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the address of the office.
     * @param address The address.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the employee.
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the employee.
     * @param name The name.
     */
    public void setName(String name) {
        this.name = name;
    }
}