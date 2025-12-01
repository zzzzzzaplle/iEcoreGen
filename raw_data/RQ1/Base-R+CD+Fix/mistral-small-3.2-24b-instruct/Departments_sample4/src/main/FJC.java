import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Company {
    private List<Department> departments;
    private List<Employee> employees;

    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /**
     * Adds a department to the company.
     *
     * @param department The department to add.
     * @return true if the department was added successfully, false if the department already exists.
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee The employee to add.
     * @return true if the employee was added successfully, false if the employee already exists.
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company, including its offices and employees.
     *
     * @param department The department to delete.
     * @return true if the department was deleted successfully, false if the department doesn't exist.
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        department.getOffices().clear();
        department.setManager(null);
        return departments.remove(department);
    }

    /**
     * Lists the departments of the company that have not been assigned a manager.
     *
     * @return A list of departments without a manager, or null if all departments have a manager.
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department department : departments) {
            if (!department.hasManager()) {
                result.add(department);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Assigns an employee as manager of a specified department.
     *
     * @param employee  The employee to assign as manager.
     * @param department The department to assign the manager to.
     * @return true if the manager was assigned successfully, false if the department already has a manager or the employee doesn't exist in the company.
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null || department.hasManager() || !employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        department.setManager(employee);
        return true;
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
}

class Department {
    private List<Office> offices;
    private Office headquarter;
    private Employee manager;

    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Assigns an office as the headquarters for the department.
     *
     * @param office The office to assign as headquarters.
     * @return true if the office was assigned successfully, false if the office doesn't exist in the department or if the department already has a headquarters.
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null || !offices.contains(office) || this.headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     *
     * @return true if the department has a manager, false otherwise.
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from the department.
     *
     * @param employee The employee to delete.
     * @return true if the employee was deleted successfully, false if the employee doesn't exist in the department.
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null || this.manager != employee) {
            return false;
        }
        this.manager = null;
        return true;
    }

    /**
     * Deletes an office from the department.
     *
     * @param office The office to delete.
     * @return true if the office was deleted successfully, false if the office doesn't exist in the department or if it is the headquarters.
     */
    public boolean deleteOffice(Office office) {
        if (office == null || !offices.contains(office) || Objects.equals(this.headquarter, office)) {
            return false;
        }
        return offices.remove(office);
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
}

class Office {
    // Office class implementation
}

class Employee {
    // Employee class implementation
}