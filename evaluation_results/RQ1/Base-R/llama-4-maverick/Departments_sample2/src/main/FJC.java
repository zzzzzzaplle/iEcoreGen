import java.util.*;

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
     * Gets the list of departments in the company.
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Deletes a department from the company.
     * @param department the department to delete
     * @return true if the department was deleted successfully, false otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (!departments.contains(department)) {
            return false;
        }
        departments.remove(department);
        for (Office office : department.getOffices()) {
            department.removeOffice(office);
        }
        for (Employee employee : employees) {
            if (employee.getDepartment() == department) {
                employee.setDepartment(null);
                if (department.getManager() == employee) {
                    department.setManager(null);
                }
            }
        }
        return true;
    }

    /**
     * Lists the departments that have not been assigned a manager.
     * @return a list of departments without a manager, or null if all departments have a manager
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

    /**
     * Assigns an employee as manager of a specified department.
     * @param department the department to assign the manager to
     * @param employee the employee to assign as manager
     * @return true if the assignment was successful, false otherwise
     */
    public boolean assignManager(Department department, Employee employee) {
        if (!departments.contains(department) || !employees.contains(employee) || department.getManager() != null) {
            return false;
        }
        if (employee.getDepartment() != department) {
            return false;
        }
        department.setManager(employee);
        return true;
    }
}

/**
 * Represents a department within a company.
 */
class Department {
    private List<Office> offices;
    private Office headquarters;
    private Manager manager;

    /**
     * Constructs a new Department object.
     */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /**
     * Gets the list of offices in the department.
     * @return the list of offices
     */
    public List<Office> getOffices() {
        return offices;
    }

    /**
     * Sets the list of offices in the department.
     * @param offices the list of offices to set
     */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /**
     * Gets the headquarters office of the department.
     * @return the headquarters office
     */
    public Office getHeadquarters() {
        return headquarters;
    }

    /**
     * Sets the headquarters office of the department.
     * @param headquarters the headquarters office to set
     */
    public void setHeadquarters(Office headquarters) {
        this.headquarters = headquarters;
    }

    /**
     * Gets the manager of the department.
     * @return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Adds an office to the department.
     * @param office the office to add
     */
    public void addOffice(Office office) {
        offices.add(office);
    }

    /**
     * Removes an office from the department.
     * @param office the office to remove
     */
    public void removeOffice(Office office) {
        offices.remove(office);
        if (headquarters == office) {
            headquarters = null;
        }
    }

    /**
     * Assigns an office as the headquarters for the department.
     * @param office the office to assign as headquarters
     * @return true if the assignment was successful, false otherwise
     */
    public boolean assignHeadquarters(Office office) {
        if (!offices.contains(office) || headquarters != null) {
            return false;
        }
        headquarters = office;
        return true;
    }

    /**
     * Checks if the department has a manager assigned.
     * @return true if the department has a manager, false otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }
}

/**
 * Represents an office within a department.
 */
class Office {
    // Add properties and methods as needed
}

/**
 * Represents an employee within a company.
 */
class Employee {
    private Department department;

    /**
     * Constructs a new Employee object.
     */
    public Employee() {}

    /**
     * Gets the department of the employee.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Represents a manager, which is a type of employee.
 */
class Manager extends Employee {
    // Add properties and methods as needed
}

 class Main {
    public static void main(String[] args) {
        // Example usage:
        Company company = new Company();
        Department department = new Department();
        company.getDepartments().add(department);
        Office office = new Office();
        department.addOffice(office);
        department.assignHeadquarters(office);
        Employee employee = new Manager();
        employee.setDepartment(department);
        company.getEmployees().add(employee);
        company.assignManager(department, (Manager) employee);
        System.out.println(department.hasManager());  // true
        System.out.println(company.listDepartmentsWithoutManager());  // null
        company.deleteDepartment(department);
        System.out.println(company.getDepartments().size());  // 0
    }
}