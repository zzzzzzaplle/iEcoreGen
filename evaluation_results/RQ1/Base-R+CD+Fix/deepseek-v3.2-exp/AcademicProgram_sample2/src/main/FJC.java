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
     * Sets the list of courses in the academic program
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program
     * @return Total capacity of all classrooms
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            Classroom classroom = course.getClassroom();
            if (classroom != null) {
                totalCapacity += classroom.getCapacity();
            }
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course
     * @param courseCode The code of the course to calculate average grade for
     * @return Average grade of students in the course, or 0.0 if no enrollments
     * @throws IllegalArgumentException if course with given code is not found
     */
    public double calculateAverageGrade(String courseCode) {
        Course targetCourse = null;
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                targetCourse = course;
                break;
            }
        }
        
        if (targetCourse == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found");
        }
        
        List<Enrollment> enrollments = targetCourse.getEnrollments();
        if (enrollments.isEmpty()) {
            return 0.0;
        }
        
        double totalGrade = 0.0;
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() > 0) {
                totalGrade += enrollment.getGrade();
                count++;
            }
        }
        
        return count > 0 ? totalGrade / count : 0.0;
    }

    /**
     * Counts the number of courses taught on a specific day
     * @param day The day to check for courses (e.g., "Monday", "Tuesday")
     * @return Number of courses taught on the specified day
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getCourseDays().contains(day)) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by the specified amount
     * @param amount The amount to increase the quota by
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increase amount cannot be negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount
     * @param amount The amount to decrease the quota by
     * @throws IllegalArgumentException if amount is negative or would result in negative quota
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Decrease amount cannot be negative");
        }
        if (this.quota - amount < 0) {
            throw new IllegalArgumentException("Quota cannot be negative");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours for the course
     * @param newHours The new weekly hours value
     * @throws IllegalArgumentException if newHours is negative
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative");
        }
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
     * Gets the number of currently enrolled students
     * @return Number of enrolled students
     */
    public int getCurrentEnrollmentCount() {
        return enrollments.size();
    }

    /**
     * Gets the remaining available quota for the course
     * @return Remaining quota (total quota minus current enrollments)
     */
    public int getRemainingQuota() {
        return quota - getCurrentEnrollmentCount();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
     * Default constructor initializes empty list of courses
     */
    public Instructor() {
        this.courses = new ArrayList<>();
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

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor
     * @return Total number of enrolled students across all courses
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getCurrentEnrollmentCount();
        }
        return totalStudents;
    }

    /**
     * Calculates the total remaining quota across all courses taught by this instructor
     * @return Total remaining quota across all courses
     */
    public int calculateRemainingQuota() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }

    /**
     * Adds a course to the instructor's course list
     * @param course The course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the instructor's course list
     * @param course The course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
}

/**
 * Represents a student who can enroll in courses
 */
class Student {
    private List<Enrollment> enrollments;

    /**
     * Default constructor initializes empty list of enrollments
     */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a course
     * @param course The course to enroll in
     * @throws IllegalStateException if course quota is full
     */
    public void enrollInCourse(Course course) {
        if (course.getRemainingQuota() <= 0) {
            throw new IllegalStateException("Course quota is full");
        }
        
        // Check if already enrolled
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                throw new IllegalStateException("Student is already enrolled in this course");
            }
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops a course that the student is enrolled in
     * @param course The course to drop
     * @throws IllegalArgumentException if student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                enrollmentToRemove = enrollment;
                break;
            }
        }
        
        if (enrollmentToRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
        
        enrollments.remove(enrollmentToRemove);
        course.getEnrollments().remove(enrollmentToRemove);
    }
}

/**
 * Represents the enrollment relationship between a student and a course
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Default constructor
     */
    public Enrollment() {
        this.grade = 0.0; // Default grade
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}