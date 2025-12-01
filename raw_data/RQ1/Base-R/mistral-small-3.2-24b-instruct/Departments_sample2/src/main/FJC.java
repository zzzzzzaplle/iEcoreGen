import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Company {
    private List<Department> departments = new ArrayList<>();
    private Set<Employee> employees = new HashSet<>();

    /**
     * Adds a department to the company.
     * @param department The department to add.
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to add.
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes a department from the company.
     * @param department The department to remove.
     * @return true if the department was found and removed, false otherwise.
     */
    public boolean removeDepartment(Department department) {
        if (departments.remove(department)) {
            for (Office office : department.getOffices()) {
                employees.remove(office.getManager());
            }
            employees.remove(department.getManager());
            return true;
        }
        return false;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     * @return A list of departments without a manager, or null if all departments have a manager.
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

    // Getters and Setters
    public List<Department> getDepartments() {
        return departments;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}

class Department {
    private String name;
    private Employee manager;
    private List<Office> offices = new ArrayList<>();

    public Department() {
    }

    /**
     * Assigns an office as headquarters for the department.
     * @param office The office to assign as headquarters.
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters.
     */
    public boolean assignHeadquarters(Office office) {
        if (offices.contains(office) && getHeadquarters() == null) {
            offices.get(0).setIsHeadquarters(true);
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
        if (manager == null) {
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
     * Gets the headquarters office of the department.
     * @return The headquarters office, or null if not set.
     */
    public Office getHeadquarters() {
        for (Office office : offices) {
            if (office.isIsHeadquarters()) {
                return office;
            }
        }
        return null;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }
}

class Office {
    private String location;
    private Employee manager;
    private boolean isHeadquarters;

    public Office() {
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public boolean isIsHeadquarters() {
        return isHeadquarters;
    }

    public void setIsHeadquarters(boolean isHeadquarters) {
        this.isHeadquarters = isHeadquarters;
    }
}

class Employee {
    private String name;
    private String employeeId;

    public Employee() {
    }

    // Getters and Setters
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
}