import java.util.ArrayList;
import java.util.List;

/**
 * Represents an office in the company.
 */
class Office {
    private String name;

    /**
     * Unparameterized constructor for Office.
     */
    public Office() {}

    /**
     * Parameterized constructor for Office.
     * @param name the name of the office
     */
    public Office(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the office.
     * @return the name of the office
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the office.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Parameterized constructor for Employee.
     * @param name the name of the employee
     */
    public Employee(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the employee.
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a department in the company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Unparameterized constructor for Department.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarter for this department.
     * @param office the office to be assigned as headquarter
     * @return true if successful, false if the office doesn't exist in the department or if the department already has a headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (offices.contains(office) && headquarter == null) {
            headquarter = office;
            return true;
        }
        return false;
    }

    /**
     * Checks if this department has a manager assigned.
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from this department.
     * @param employee the employee to be deleted
     * @return true if the employee is the manager and is successfully removed, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (manager == employee) {
            manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from this department.
     * @param office the office to be deleted
     * @return true if the office is the headquarter and is successfully removed, or if the office is not the headquarter but is removed from the list of offices
     */
    public boolean deleteOffice(Office office) {
        if (headquarter == office) {
            headquarter = null;
        }
        return offices.remove(office);
    }

    /**
     * Gets the list of offices in this department.
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in this department.
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarter of this department.
     * @return the headquarter
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarter of this department.
     * @param headquarter the headquarter to set
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager of this department.
     * @return the manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of this department.
     * @param manager the manager to set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * Represents a company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to this company.
     * @param department the department to be added
     * @return true if the department is successfully added, false if it already exists
     */
    public boolean addDepartment(Department department) {
        if (!departments.contains(department)) {
            return departments.add(department);
        }
        return false;
    }

    /**
     * Adds an employee to this company.
     * @param employee the employee to be added
     * @return true if the employee is successfully added, false if they already exist
     */
    public boolean addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            return employees.add(employee);
        }
        return false;
    }

    /**
     * Deletes a department from this company.
     * @param department the department to be deleted
     * @return true if the department is successfully deleted, false if it doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (departments.contains(department)) {
            // Remove offices (including headquarters)
            for (Office office : new ArrayList<>(department.getOffices())) {
                department.deleteOffice(office);
            }
            // Remove employees (including managers) from the company
            for (Employee employee : new ArrayList<>(employees)) {
                if (department.getManager() == employee) {
                    department.deleteEmployee(employee);
                }
            }
            return departments.remove(department);
        }
        return false;
    }

    /**
     * Lists the departments in this company that have not been assigned a manager.
     * @return a list of departments without a manager, or null if all departments have a manager
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
     * Assigns an employee as the manager of a specified department.
     * @param employee the employee to be assigned as manager
     * @param department the department for which the manager is to be assigned
     * @return true if successful, false if the department already has a manager or the employee doesn't exist in the company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employees.contains(employee) && departments.contains(department) && !department.hasManager()) {
            department.setManager(employee);
            return true;
        }
        return false;
    }

    /**
     * Gets the list of departments in this company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in this company.
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in this company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in this company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}