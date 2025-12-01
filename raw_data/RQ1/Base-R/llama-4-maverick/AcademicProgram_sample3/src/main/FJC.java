import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Classroom with its properties and behaviors.
 */
class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Unparameterized constructor for Classroom.
     */
    public Classroom() {}

    /**
     * Gets the classroom id.
     * @return the classroom id
     */
    public String getClassroomId() {
        return classroomId;
    }

    /**
     * Sets the classroom id.
     * @param classroomId the classroom id to set
     */
    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * Gets the capacity.
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor.
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor.
     * @param floor the floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the block.
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block.
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an Instructor with their properties and behaviors.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Unparameterized constructor for Instructor.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty.
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty.
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the courses.
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the instructor's courses.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Drops a course from the instructor's courses.
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
     * @return the total number of students
     */
    public int numberOfStudentsInCourses() {
        int total = 0;
        for (Course course : courses) {
            total += course.getStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     * @return the remaining quota
     */
    public int remainingQuotaInCourses() {
        int total = 0;
        for (Course course : courses) {
            total += course.getQuotaLimit() - course.getStudents().size();
        }
        return total;
    }
}

/**
 * Represents a Course with its properties and behaviors.
 */
class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private int quotaLimit;
    private List<Student> students;
    private double averageGrade;

    /**
     * Unparameterized constructor for Course.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time.
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days.
     * @return the days
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Sets the days.
     * @param days the days to set
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Gets the classroom.
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom.
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the instructor.
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor.
     * @param instructor the instructor to set
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Gets the quota limit.
     * @return the quota limit
     */
    public int getQuotaLimit() {
        return quotaLimit;
    }

    /**
     * Sets the quota limit.
     * @param quotaLimit the quota limit to set
     */
    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    /**
     * Gets the students.
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students.
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Enrolls a student in the course.
     * @param student the student to enroll
     */
    public void enrollStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Drops a student from the course.
     * @param student the student to drop
     */
    public void dropStudent(Student student) {
        this.students.remove(student);
    }

    /**
     * Calculates the average grade of the students in the course.
     * @return the average grade
     */
    public double averageStudentGrade() {
        double sum = 0;
        for (Student student : students) {
            sum += student.getGrade(this);
        }
        return sum / students.size();
    }
}

/**
 * Represents a Student with their properties and behaviors.
 */
class Student {
    private String name;
    private String surname;
    private List<Course> courses;
    private java.util.Map<Course, Double> grades;

    /**
     * Unparameterized constructor for Student.
     */
    public Student() {
        this.courses = new ArrayList<>();
        this.grades = new java.util.HashMap<>();
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the courses.
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Enrolls in a course.
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        this.courses.add(course);
        course.enrollStudent(this);
    }

    /**
     * Drops a course.
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        this.courses.remove(course);
        course.dropStudent(this);
    }

    /**
     * Gets the grade for a course.
     * @param course the course to get the grade for
     * @return the grade
     */
    public double getGrade(Course course) {
        return grades.getOrDefault(course, 0.0);
    }

    /**
     * Sets the grade for a course.
     * @param course the course to set the grade for
     * @param grade the grade to set
     */
    public void setGrade(Course course, double grade) {
        this.grades.put(course, grade);
    }
}

/**
 * Represents an Academic Program with its properties and behaviors.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Unparameterized constructor for AcademicProgram.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the courses.
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses.
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the academic program.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * @return the total capacity
     */
    public int totalClassroomCapacity() {
        int total = 0;
        for (Course course : courses) {
            total += course.getClassroom().getCapacity();
        }
        return total;
    }

    /**
     * Finds the total number of courses in the academic program that are taught on a given day.
     * @param day the day to find courses for
     * @return the number of courses on the specified day
     */
    public int coursesOnDay(String day) {
        int total = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                total++;
            }
        }
        return total;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course within the academic program.
     * @param course the course to calculate the average grade for
     * @return the average grade
     */
    public double averageGradeInCourse(Course course) {
        return course.averageStudentGrade();
    }
}