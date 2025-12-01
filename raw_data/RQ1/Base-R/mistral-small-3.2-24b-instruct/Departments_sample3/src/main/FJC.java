import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments = new ArrayList<>();

    /**
     * Adds a department to the company.
     * @param department The department to be added.
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Removes a department from the company.
     * @param department The department to be removed.
     * @return true if the department was found and removed, false otherwise.
     */
    public boolean removeDepartment(Department department) {
        return departments.remove(department);
    }

    /**
     * Gets all departments in the company.
     * @return A list of all departments.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
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
}

class Department {
    private List<Office> offices = new ArrayList<>();
    private Office headquarters;
    private Employee manager;

    /**
     * Adds an office to the department.
     * @param office The office to be added.
     */
    public void addOffice(Office office) {
        offices.add(office);
    }

    /**
     * Removes an office from the department.
     * @param office The office to be removed.
     * @return true if the office was found and removed, false otherwise.
     */
    public boolean removeOffice(Office office) {
        return offices.remove(office);
    }

    /**
     * Assigns an office as headquarters for the department.
     * @param office The office to be assigned as headquarters.
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters.
     */
    public boolean assignHeadquarters(Office office) {
        if (offices.contains(office) && headquarters == null) {
            headquarters = office;
            return true;
        }
        return false;
    }

    /**
     * Assigns an employee as manager of the department.
     * @param employee The employee to be assigned as manager.
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee) {
        if (manager == null && employee != null) {
            manager = employee;
            return true;
        }
        return false;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Gets the headquarters of the department.
     * @return The headquarters office.
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return The manager employee.
     */
    public Employee getManager() {
        return manager;
    }
}

class Office {
    private String address;

    /**
     * Constructor for Office.
     */
    public Office() {
    }

    /**
     * Sets the address of the office.
     * @param address The address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the address of the office.
     * @return The address of the office.
     */
    public String getAddress() {
        return address;
    }
}

class Employee {
    private String name;

    /**
     * Constructor for Employee.
     */
    public Employee() {
    }

    /**
     * Sets the name of the employee.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the employee.
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }
}