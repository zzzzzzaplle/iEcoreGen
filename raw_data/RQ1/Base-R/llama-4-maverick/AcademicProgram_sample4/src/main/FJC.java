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
     * Gets the classroom ID.
     * @return the classroom ID
     */
    public String getClassroomId() {
        return classroomId;
    }

    /**
     * Sets the classroom ID.
     * @param classroomId the classroom ID to set
     */
    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * Gets the capacity of the classroom.
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor of the classroom.
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor of the classroom.
     * @param floor the floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the block of the classroom.
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block of the classroom.
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
     * Gets the name of the instructor.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor.
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor.
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Adds a course to the instructor's course list.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the instructor's course list.
     * @param course the course to remove
     */
    public void dropCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
     * @return the total number of students
     */
    public int numberOfStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     * @return the remaining quota
     */
    public int remainingQuotaInCourses() {
        int totalQuota = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota();
        }
        int totalStudents = numberOfStudentsInCourses();
        return totalQuota - totalStudents;
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
    private List<Student> students;
    private int quota;
    private double averageGrade;

    /**
     * Unparameterized constructor for Course.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Gets the name of the course.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days of the course.
     * @return the days
     */
    public List<String> getDays() {
        return days;
    }

    /**
     * Sets the days of the course.
     * @param days the days to set
     */
    public void setDays(List<String> days) {
        this.days = days;
    }

    /**
     * Gets the classroom of the course.
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom of the course.
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the instructor of the course.
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Sets the instructor of the course.
     * @param instructor the instructor to set
     */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Gets the list of students enrolled in the course.
     * @return the list of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students enrolled in the course.
     * @param students the list of students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Gets the quota of the course.
     * @return the quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota of the course.
     * @param quota the quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the average grade of the course.
     * @return the average grade
     */
    public double getAverageGrade() {
        return averageGrade;
    }

    /**
     * Sets the average grade of the course.
     * @param averageGrade the average grade to set
     */
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
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
    public double calculateAverageGrade() {
        double totalGrade = 0;
        for (Student student : students) {
            totalGrade += student.getGrade();
        }
        return students.isEmpty() ? 0 : totalGrade / students.size();
    }
}

/**
 * Represents a Student with their properties and behaviors.
 */
class Student {
    private String name;
    private String surname;
    private double grade;

    /**
     * Unparameterized constructor for Student.
     */
    public Student() {}

    /**
     * Gets the name of the student.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the student.
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the student.
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the grade of the student.
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the student.
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
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
     * Gets the list of courses in the academic program.
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program.
     * @param courses the list of courses to set
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
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Finds the total number of courses in the academic program that are taught on a given day.
     * @param day the day to check
     * @return the number of courses on the specified day
     */
    public int coursesOnSpecificDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course within the academic program.
     * @param courseCode the code of the course
     * @return the average grade
     */
    public double averageGradeInCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course.calculateAverageGrade();
            }
        }
        return 0;
    }
}