import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program containing multiple courses
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in the academic program
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses for the academic program
     * @param courses New list of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
     * @return Total capacity of all classrooms
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            if (course.getClassroom() != null) {
                totalCapacity += course.getClassroom().getCapacity();
            }
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course
     * @param courseCode The code of the course to calculate average grade for
     * @return Average grade of students in the specified course, or 0.0 if no enrollments exist
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course.calculateAverageGrade();
            }
        }
        return 0.0;
    }

    /**
     * Counts the number of courses taught on a specific day
     * @param day The day to check for courses (e.g., "Monday", "Tuesday")
     * @return Number of courses taught on the specified day
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getCourseDays() != null && course.getCourseDays().contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a course in the academic program
 */
class Course {
    private String name;
    private String code;
    private Date startTime;
    private Date endTime;
    private List<String> courseDays;
    private int quota;
    private int weeklyHours;
    private Classroom classroom;
    private List<Enrollment> enrollments;

    /**
     * Default constructor initializes empty lists for course days and enrollments
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of the course
     * @return Course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course
     * @param name New course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course
     * @return Course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course
     * @param code New course code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course
     * @return Course start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course
     * @param startTime New start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course
     * @return Course end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course
     * @param endTime New end time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the list of days the course is taught
     * @return List of course days
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the list of days the course is taught
     * @param courseDays New list of course days
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the course quota (maximum number of students)
     * @return Course quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the course quota
     * @param quota New course quota
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours for the course
     * @return Weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours for the course
     * @param weeklyHours New weekly hours
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom assigned to the course
     * @return Classroom object
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom for the course
     * @param classroom New classroom
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments for the course
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments for the course
     * @param enrollments New list of enrollments
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by the specified amount
     * @param amount The amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount
     * @param amount The amount to decrease the quota by
     * @throws IllegalArgumentException if the amount would make quota negative
     */
    public void decreaseQuotaBy(int amount) {
        if (this.quota - amount < 0) {
            throw new IllegalArgumentException("Quota cannot be negative");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours for the course
     * @param newHours New weekly hours value
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days to the new set of days
     * @param newDays Array of new course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }

    /**
     * Calculates the average grade for students enrolled in this course
     * @return Average grade of enrolled students, or 0.0 if no enrollments exist
     */
    public double calculateAverageGrade() {
        if (enrollments.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (Enrollment enrollment : enrollments) {
            total += enrollment.getGrade();
        }
        return total / enrollments.size();
    }

    /**
     * Gets the number of currently enrolled students
     * @return Number of enrolled students
     */
    public int getEnrolledStudentCount() {
        return enrollments.size();
    }

    /**
     * Gets the remaining quota (available spots) in the course
     * @return Remaining quota
     */
    public int getRemainingQuota() {
        return quota - enrollments.size();
    }
}

/**
 * Represents a classroom with capacity and location information
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Default constructor
     */
    public Classroom() {
    }

    /**
     * Gets the classroom ID
     * @return Classroom ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the classroom ID
     * @param id New classroom ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the classroom capacity
     * @return Classroom capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the classroom capacity
     * @param capacity New capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where the classroom is located
     * @return Floor information
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the classroom is located
     * @param floor New floor information
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where the classroom is located
     * @return Block information
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where the classroom is located
     * @param block New block information
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor who teaches courses
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the instructor's first name
     * @return Instructor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's first name
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's surname
     * @return Instructor surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's surname
     * @param surname New surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's title
     * @return Instructor title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's title
     * @param title New title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's specialty
     * @return Instructor specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's specialty
     * @param specialty New specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor
     * @param courses New list of courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor
     * @return Total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course course : courses) {
            total += course.getEnrolledStudentCount();
        }
        return total;
    }

    /**
     * Calculates the total remaining quota across all courses taught by this instructor
     * @return Total remaining quota
     */
    public int calculateRemainingQuota() {
        int totalRemaining = 0;
        for (Course course : courses) {
            totalRemaining += course.getRemainingQuota();
        }
        return totalRemaining;
    }
}

/**
 * Represents a student who can enroll in courses
 */
class Student {
    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * Enrolls the student in a course
     * @param course The course to enroll in
     */
    public void enrollInCourse(Course course) {
        // Implementation would typically involve creating an enrollment record
    }

    /**
     * Drops the student from a course
     * @param course The course to drop from
     */
    public void dropCourse(Course course) {
        // Implementation would typically involve removing an enrollment record
    }
}

/**
 * Represents an enrollment record linking a student to a course with a grade
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Default constructor
     */
    public Enrollment() {
    }

    /**
     * Gets the grade for this enrollment
     * @return Grade value
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this enrollment
     * @param grade New grade value
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student associated with this enrollment
     * @return Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student for this enrollment
     * @param student New student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course associated with this enrollment
     * @return Course object
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course for this enrollment
     * @param course New course
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}