import java.util.ArrayList;
import java.util.List;

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
     * @param department the department to be added
     * @return true if department was added successfully, false otherwise
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
     * @param employee the employee to be added
     * @return true if employee was added successfully, false otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
            return true;
        }
        return false;
    }

    /**
     * Deletes a department from the company along with its offices and employees
     * @param department the department to be deleted
     * @return true if department was deleted successfully, false if department doesn't exist
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        
        // Remove offices from department
        List<Office> officesToRemove = new ArrayList<>(department.getOffices());
        for (Office office : officesToRemove) {
            department.deleteOffice(office);
        }
        
        // Remove employees from company that belong to this department
        List<Employee> employeesToRemove = new ArrayList<>();
        for (Employee employee : employees) {
            // Assuming Employee has a reference to its department
            if (employee.getDepartment() != null && employee.getDepartment().equals(department)) {
                employeesToRemove.add(employee);
            }
        }
        employees.removeAll(employeesToRemove);
        
        // Remove the department
        departments.remove(department);
        return true;
    }

    /**
     * Lists departments that have not been assigned a manager
     * @return list of departments without managers, or null if all departments have managers
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
     * @param employee the employee to be assigned as manager
     * @param department the department to assign the manager to
     * @return true if assignment was successful, false if department already has a manager or employee doesn't exist
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || 
            !employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        
        if (department.hasManager()) {
            return false;
        }
        
        // Set the manager for the department
        department.setManager(employee);
        // Set the department for the employee
        employee.setDepartment(department);
        return true;
    }
}

class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
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
     * @param office the office to be assigned as headquarters
     * @return true if assignment was successful, false if office doesn't exist in department or department already has headquarters
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        if (headquarter != null) {
            return false;
        }
        
        headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned
     * @return true if manager is assigned, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department
     * @param employee the employee to be deleted
     * @return true if employee was deleted successfully, false otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee != null && manager != null && manager.equals(employee)) {
            manager = null;
        }
        // Employee list is maintained by Company, so we only handle manager reference
        return true;
    }

    /**
     * Deletes an office from the department
     * @param office the office to be deleted
     * @return true if office was deleted successfully, false otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office)) {
            return false;
        }
        
        // If the office being deleted is the headquarter, clear the headquarter reference
        if (headquarter != null && headquarter.equals(office)) {
            headquarter = null;
        }
        
        offices.remove(office);
        return true;
    }
}

class Office {
    public Office() {
    }
}

class Employee {
    private Department department;

    public Employee() {
        this.department = null;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}