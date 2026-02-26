import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company with departments and employees.
 */
class Company {
    private List<Department> departments;
    private List<Employee> employees;

    /**
     * Constructs a new Company object.
     */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     * 
     * @param department the department to be added
     * @return true if the department is added successfully, false otherwise
     */
    public boolean addDepartment(Department department) {
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     * 
     * @param employee the employee to be added
     * @return true if the employee is added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company.
     * 
     * @param department the department to be deleted
     * @return true if the department is deleted successfully, false otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.remove(department)) {
            return false;
        }
        // Remove employees from the company who are part of the deleted department
        employees.removeIf(employee -> department.getManager() == employee || department.getOffices().stream().anyMatch(office -> office.getEmployees().contains(employee)));
        return true;
    }

    /**
     * Lists departments that do not have a manager assigned.
     * 
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
     * Assigns an employee as the manager of a department.
     * 
     * @param employee   the employee to be assigned as manager
     * @param department the department for which the manager is to be assigned
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignManager(Employee employee, Department department) {
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        return department.assignManager(employee);
    }

    /**
     * Gets the departments of the company.
     * 
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the departments of the company.
     * 
     * @param departments the list of departments to be set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the employees of the company.
     * 
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees of the company.
     * 
     * @param employees the list of employees to be set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    /**
     * Constructs a new Department object.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarters of the department.
     * 
     * @param office the office to be assigned as headquarters
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (!offices.contains(office)) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * 
     * @return true if a manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     * 
     * @param employee the employee to be deleted
     * @return true if the employee is deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (manager == employee) {
            manager = null;
        }
        for (Office office : offices) {
            office.deleteEmployee(employee);
        }
        return true;
    }

    /**
     * Deletes an office from the department.
     * 
     * @param office the office to be deleted
     * @return true if the office is deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (headquarter == office) {
            headquarter = null;
        }
        return offices.remove(office);
    }

    /**
     * Assigns a manager to the department.
     * 
     * @param employee the employee to be assigned as manager
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignManager(Employee employee) {
        if (hasManager()) {
            return false;
        }
        this.manager = employee;
        return true;
    }

    /**
     * Gets the offices of the department.
     * 
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the offices of the department.
     * 
     * @param offices the list of offices to be set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarter office of the department.
     * 
     * @return the headquarter office
     */
    public Office getHeadquarter() {
        return headquarter;
    }

    /**
     * Sets the headquarter office of the department.
     * 
     * @param headquarter the headquarter office to be set
     */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /**
     * Gets the manager of the department.
     * 
     * @return the manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * 
     * @param manager the manager to be set
     */
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * Represents an office within a department.
 */
class Office {
    private List<Employee> employees;

    /**
     * Constructs a new Office object.
     */
    public Office() {
        this.employees = new ArrayList<>();
    }

    /**
     * Deletes an employee from the office.
     * 
     * @param employee the employee to be deleted
     * @return true if the employee is deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        return employees.remove(employee);
    }

    /**
     * Gets the employees of the office.
     * 
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees of the office.
     * 
     * @param employees the list of employees to be set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

/**
 * Represents an employee within a company.
 */
class Employee {
    // Employee class can be extended with additional properties and methods as needed
}