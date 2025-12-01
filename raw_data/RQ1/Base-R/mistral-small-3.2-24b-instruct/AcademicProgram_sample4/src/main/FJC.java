import java.util.ArrayList;
import java.util.List;

class AcademicProgram {
    private List<Course> courses;

    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the academic program.
     * @param course The course to be added.
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the academic program.
     * @param course The course to be removed.
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * @return The total classroom capacity.
     */
    public int calculateTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Finds the total number of courses in the academic program that are taught on a given day.
     * @param day The day to check for courses.
     * @return The number of courses on the specified day.
     */
    public int getNumberOfCoursesOnDay(String day) {
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
     * @param courseCode The code of the course to calculate the average grade for.
     * @return The average grade for the specified course.
     * @throws IllegalArgumentException If the course is not found in the academic program.
     */
    public double calculateAverageGrade(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found in the academic program.");
        }
        return course.calculateAverageGrade();
    }

    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

class Course {
    private String courseName;
    private String courseCode;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private List<Student> students;
    private int quotaLimit;

    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
        this.quotaLimit = 0;
    }

    /**
     * Adds a student to the course.
     * @param student The student to be added.
     */
    public void addStudent(Student student) {
        if (students.size() < quotaLimit) {
            students.add(student);
        }
    }

    /**
     * Removes a student from the course.
     * @param student The student to be removed.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Increases the course quota limit.
     * @param amount The amount to increase the quota by.
     */
    public void increaseQuotaLimit(int amount) {
        this.quotaLimit += amount;
    }

    /**
     * Decreases the course quota limit.
     * @param amount The amount to decrease the quota by.
     */
    public void decreaseQuotaLimit(int amount) {
        if (quotaLimit - amount >= 0) {
            this.quotaLimit -= amount;
        }
    }

    /**
     * Calculates the average grade for the students enrolled in the course.
     * @return The average grade for the course.
     */
    public double calculateAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Student student : students) {
            total += student.getGrade();
        }
        return total / students.size();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }
}

class Classroom {
    private String classroomId;
    private int capacity;
    private String floor;
    private String block;

    public Classroom() {
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}

class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the instructor's list.
     * @param course The course to be added.
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the instructor's list.
     * @param course The course to be removed.
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Determines the total number of students enrolled in all courses of the instructor.
     * @return The total number of students.
     */
    public int getTotalNumberOfStudents() {
        int total = 0;
        for (Course course : courses) {
            total += course.getStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     * @return The remaining quota.
     */
    public int getRemainingQuota() {
        int remaining = 0;
        for (Course course : courses) {
            remaining += course.getQuotaLimit() - course.getStudents().size();
        }
        return remaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

class Student {
    private String name;
    private String surname;
    private String studentId;
    private double grade;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}