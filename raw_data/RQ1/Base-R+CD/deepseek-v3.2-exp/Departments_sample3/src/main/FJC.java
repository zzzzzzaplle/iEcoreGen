import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Adds a department to the company
     * @param department the department to add
     * @return true if the department was added successfully, false otherwise
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company
     * @param employee the employee to add
     * @return true if the employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company including its offices and employees
     * @param department the department to delete
     * @return true if the department was deleted successfully, false if the department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove all offices from the department
        List<Office> officesToRemove = new ArrayList<>(department.getOffices());
        for (Office office : officesToRemove) {
            department.deleteOffice(office);
        }
        
        // Remove all employees associated with the department
        List<Employee> employeesToRemove = employees.stream()
                .filter(emp -> department.equals(emp.getDepartment()))
                .collect(Collectors.toList());
        
        for (Employee employee : employeesToRemove) {
            employees.remove(employee);
        }
        
        // Remove the department itself
        return departments.remove(department);
    }

    /**
     * Lists departments that have not been assigned a manager
     * @return list of departments without managers, or null if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> departmentsWithoutManager = departments.stream()
                .filter(department -> !department.hasManager())
                .collect(Collectors.toList());
        
        return departmentsWithoutManager.isEmpty() ? null : departmentsWithoutManager;
    }

    /**
     * Assigns an employee as manager of a specified department
     * @param employee the employee to assign as manager
     * @param department the department to assign the manager to
     * @return true if successful, false if department already has a manager or employee doesn't exist in company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || 
            !employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        
        if (department.hasManager()) {
            return false;
        }
        
        department.setManager(employee);
        return true;
    }
}

 class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    public Department() {
        this.offices = new ArrayList<>();
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
     * Assigns an office as headquarters for the department
     * @param office the office to assign as headquarters
     * @return true if successful, false if office doesn't exist in department or department already has headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        if (headquarter != null) {
            return false;
        }
        
        this.headquarter = office;
        return true;
    }

    /**
     * Verifies whether the department has a manager assigned
     * @return true if manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department
     * @param employee the employee to delete
     * @return true if employee was deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        // If the employee is the manager, remove the manager assignment
        if (employee.equals(manager)) {
            manager = null;
        }
        
        return true;
    }

    /**
     * Deletes an office from the department
     * @param office the office to delete
     * @return true if office was deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        // If the office is the headquarters, remove headquarters assignment
        if (office.equals(headquarter)) {
            headquarter = null;
        }
        
        return offices.remove(office);
    }
}

 class Office {
    public Office() {
    }
}

 class Employee {
    private Department department;

    public Employee() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}