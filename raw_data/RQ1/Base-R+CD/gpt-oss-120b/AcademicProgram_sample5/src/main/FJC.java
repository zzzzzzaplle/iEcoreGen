import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program that contains multiple courses.
 */
 class AcademicProgram {

    /** List of courses belonging to this academic program */
    private List<Course> courses;

    /** Default constructor */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** @return the list of courses */
    public List<Course> getCourses() {
        return courses;
    }

    /** @param courses the list of courses to set */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total classroom capacity of all courses in this academic program.
     *
     * @return the sum of capacities of each classroom used by the program's courses
     */
    public int sumClassroomCapacity() {
        int total = 0;
        for (Course c : courses) {
            Classroom room = c.getClassroom();
            if (room != null) {
                total += room.getCapacity();
            }
        }
        return total;
    }

    /**
     * Calculates the average grade of all students enrolled in the course identified by the given code.
     *
     * @param courseCode the unique code of the course
     * @return the average grade, or 0.0 if the course does not exist or has no graded enrollments
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().equals(courseCode)) {
                List<Enrollment> enrollments = c.getEnrollments();
                if (enrollments.isEmpty()) {
                    return 0.0;
                }
                double sum = 0.0;
                int count = 0;
                for (Enrollment e : enrollments) {
                    // Assume a grade of negative means "not yet graded"
                    if (e.getGrade() >= 0) {
                        sum += e.getGrade();
                        count++;
                    }
                }
                return count == 0 ? 0.0 : sum / count;
            }
        }
        return 0.0;
    }

    /**
     * Counts how many courses in this academic program are taught on a specific day.
     *
     * @param day the day to search for (e.g., "Monday")
     * @return the number of courses that have the given day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        int counter = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null && days.contains(day)) {
                counter++;
            }
        }
        return counter;
    }
}

/**
 * Represents a single course.
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
    private Instructor instructor; // optional link back to the instructor

    /** Default constructor */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    // ---------- Business Methods ----------
    /**
     * Increases the quota of the course by the specified amount.
     *
     * @param amount the number of seats to add (must be non‑negative)
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase must be non‑negative.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the quota of the course by the specified amount.
     *
     * @param amount the number of seats to remove (must be non‑negative and not reduce quota below current enrollment)
     * @throws IllegalArgumentException if amount is negative or would make quota less than current enrollment size
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to decrease must be non‑negative.");
        }
        int newQuota = this.quota - amount;
        if (newQuota < this.enrollments.size()) {
            throw new IllegalArgumentException("Cannot decrease quota below current number of enrolled students.");
        }
        this.quota = newQuota;
    }

    /**
     * Changes the weekly teaching hours for the course.
     *
     * @param newHours the new weekly hour count (must be positive)
     * @throws IllegalArgumentException if newHours is not positive
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive.");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the current set of course days with a new array of days.
     *
     * @param newDays an array containing the new days (e.g., {"Monday","Wednesday"})
     * @throws IllegalArgumentException if newDays is null or empty
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null || newDays.length == 0) {
            throw new IllegalArgumentException("New days must contain at least one entry.");
        }
        this.courseDays = new ArrayList<>();
        Collections.addAll(this.courseDays, newDays);
    }
}

/**
 * Represents a classroom where courses are held.
 */
class Classroom {

    private String id;
    private int capacity;
    private String floor;
    private String block;

    /** Default constructor */
    public Classroom() {
    }

    // ---------- Getters and Setters ----------
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
 * Represents an instructor who can teach multiple courses.
 */
class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /** Default constructor */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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

    // ---------- Business Methods ----------
    /**
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return the sum of enrolled students for each course
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            total += c.getEnrollments().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return the sum of (quota - current enrollment) for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            remaining += (c.getQuota() - c.getEnrollments().size());
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in and drop courses.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;
    private List<Enrollment> enrollments;

    /** Default constructor */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    // ---------- Business Methods ----------
    /**
     * Enrolls the student in the given course if there is available quota.
     *
     * @param course the course to enroll in
     * @throws IllegalStateException if the course quota is full
     */
    public void enrollInCourse(Course course) {
        if (course.getEnrollments().size() >= course.getQuota()) {
            throw new IllegalStateException("Cannot enroll: course quota is full.");
        }
        // Prevent duplicate enrollment
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                throw new IllegalStateException("Student is already enrolled in this course.");
            }
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(-1); // -1 indicates not yet graded
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from the given course if currently enrolled.
     *
     * @param course the course to drop
     * @throws IllegalStateException if the student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        Enrollment toRemove = null;
        for (Enrollment e : enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalStateException("Student is not enrolled in the specified course.");
        }
        enrollments.remove(toRemove);
        course.getEnrollments().remove(toRemove);
    }
}

/**
 * Represents the relationship between a student and a course, including the student's grade.
 */
class Enrollment {

    private double grade; // negative grade indicates not yet assigned
    private Student student;
    private Course course;

    /** Default constructor */
    public Enrollment() {
    }

    // ---------- Getters and Setters ----------
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