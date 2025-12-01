import java.util.ArrayList;
import java.util.List;

class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Default constructor for Company
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the list of departments
     * @return List of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments
     * @param departments List of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees
     * @return List of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees
     * @param employees List of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds a department to the company
     * @param department The department to add
     * @return true if department was added successfully, false if department already exists
     */
    public boolean addDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
            return true;
        }
        return false;
    }

    /**
     * Adds an employee to the company
     * @param employee The employee to add
     * @return true if employee was added successfully, false if employee already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
            return true;
        }
        return false;
    }

    /**
     * Deletes a department from the company, including its offices and employees
     * @param department The department to delete
     * @return true if department was deleted successfully, false if department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department != null && departments.contains(department)) {
            // Remove all offices from the department
            List<Office> officesToRemove = new ArrayList<>(department.getOffices());
            for (Office office : officesToRemove) {
                department.deleteOffice(office);
            }
            
            // Remove all employees from the department
            List<Employee> employeesToRemove = new ArrayList<>();
            for (Employee employee : employees) {
                if (department.getManager() != null && department.getManager().equals(employee)) {
                    department.setManager(null);
                }
                employeesToRemove.add(employee);
            }
            
            // Remove employees from company
            employees.removeAll(employeesToRemove);
            
            // Remove the department
            departments.remove(department);
            return true;
        }
        return false;
    }

    /**
     * Lists all departments that have not been assigned a manager
     * @return List of departments without managers, or null if all departments have managers
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

    /**
     * Assigns an employee as manager of a specified department
     * @param employee The employee to assign as manager
     * @param department The department to assign the manager to
     * @return true if assignment was successful, false if department already has a manager or employee doesn't exist in company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        
        // Check if employee exists in company and department exists in company
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        
        // Check if department already has a manager
        if (department.hasManager()) {
            return false;
        }
        
        // Assign the manager
        department.setManager(employee);
        return true;
    }
}

class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Default constructor for Department
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the list of offices
     * @return List of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices
     * @param offices List of offices
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarter office
     * @return Headquarter office
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarter office
     * @param headquarter Headquarter office
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager employee
     * @return Manager employee
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager employee
     * @param manager Manager employee
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Assigns an office as headquarters for the department
     * @param office The office to assign as headquarters
     * @return true if assignment was successful, false if office doesn't exist in department or department already has headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        
        // Check if office exists in department
        if (!offices.contains(office)) {
            return false;
        }
        
        // Check if department already has headquarters
        if (headquarter != null) {
            return false;
        }
        
        // Assign as headquarters
        headquarter = office;
        return true;
    }

    /**
     * Verifies whether the department has a manager assigned
     * @return true if department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department
     * @param employee The employee to delete
     * @return true if employee was deleted successfully, false if employee doesn't exist in department
     */
    public boolean deleteEmployee(Employee employee) {
        // This method is kept for consistency with the design model
        // In the current implementation, employees are managed at Company level
        // This could be extended if Department maintains its own employee list
        return true;
    }

    /**
     * Deletes an office from the department
     * @param office The office to delete
     * @return true if office was deleted successfully, false if office doesn't exist in department
     */
    public boolean deleteOffice(Office office) {
        if (office != null && offices.contains(office)) {
            // If this office is the headquarters, remove headquarters assignment
            if (office.equals(headquarter)) {
                headquarter = null;
            }
            offices.remove(office);
            return true;
        }
        return false;
    }
}

class Office {
    // Basic Office class - can be extended with additional properties as needed
    
    /**
     * Default constructor for Office
     */
    public Office() {
    }
}

class Employee {
    // Basic Employee class - can be extended with additional properties as needed
    
    /**
     * Default constructor for Employee
     */
    public Employee() {
    }
}