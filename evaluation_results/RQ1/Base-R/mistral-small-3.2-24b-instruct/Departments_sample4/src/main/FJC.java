import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    public Company() {
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
     * Adds a department to the company.
     * @param department The department to add.
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Removes a department from the company.
     * @param department The department to remove.
     */
    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to add.
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes an employee from the company.
     * @param employee The employee to remove.
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
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
    private String name;
    private List<Office> offices = new ArrayList<>();
    private Office headquarters;
    private Employee manager;

    public Department() {
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
     * Assigns an office as headquarters for the department.
     * @param office The office to assign as headquarters.
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
     * Adds an office to the department.
     * @param office The office to add.
     */
    public void addOffice(Office office) {
        offices.add(office);
    }

    /**
     * Removes an office from the department.
     * @param office The office to remove.
     */
    public void removeOffice(Office office) {
        offices.remove(office);
    }
}

class Office {
    private String location;

    public Office() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

class Employee {
    private String name;
    private String departmentName;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}