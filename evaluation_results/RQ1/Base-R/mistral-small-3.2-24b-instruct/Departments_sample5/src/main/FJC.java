import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Company {
    private List<Department> departments = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     *
     * @return A list of departments without a manager, or null if all departments have a manager.
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

class Department {
    private String name;
    private Set<Office> offices = new HashSet<>();
    private Office headquarters;
    private Employee manager;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
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
     * Assigns an office as headquarters for the department. The office must belong to the department.
     *
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
     * Assigns an employee as manager of the department. The employee must belong to the company.
     *
     * @param employee The employee to be assigned as manager.
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
     * Verifies whether the department has a manager assigned.
     *
     * @return true if a manager is assigned, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes the department, its offices (including headquarters), and its employees (including managers).
     *
     * @param company The company from which the department and related entities will be removed.
     * @return true if successful, false if the department doesn't exist.
     */
    public boolean deleteDepartment(Company company) {
        if (company.getDepartments().contains(this)) {
            for (Office office : offices) {
                company.getEmployees().removeAll(office.getEmployees());
                company.getOffices().remove(office);
            }
            company.getDepartments().remove(this);
            return true;
        }
        return false;
    }
}

class Office {
    private String location;
    private Set<Employee> employees = new HashSet<>();

    public Office() {
    }

    public Office(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}

class Employee {
    private String name;
    private String position;

    public Employee() {
    }

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}