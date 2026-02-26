import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program that consists of multiple courses.
 */
 class AcademicProgram {

    private List<Course> courses;

    /** Default constructor */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** @return the list of courses in the program */
    public List<Course> getCourses() {
        return courses;
    }

    /** @param courses the list of courses to set */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of each classroom assigned to the program's courses
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
     * Calculates the average grade for the students enrolled in a specific course
     * identified by its code.
     *
     * @param courseCode the unique code of the course
     * @return the average grade, or 0.0 if the course has no enrollments or does not exist
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                List<Enrollment> enrollments = c.getEnrollments();
                if (enrollments == null || enrollments.isEmpty()) {
                    return 0.0;
                }
                double sum = 0.0;
                for (Enrollment e : enrollments) {
                    sum += e.getGrade();
                }
                return sum / enrollments.size();
            }
        }
        return 0.0; // course not found
    }

    /**
     * Counts how many courses in the program are taught on a given day.
     *
     * @param day the day to search for (e.g., "Monday")
     * @return the number of courses that have the specified day in their schedule
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null && days.contains(day)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents a single course within an academic program.
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

    /** Default constructor */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** @return the course name */
    public String getName() {
        return name;
    }

    /** @param name the course name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the course code */
    public String getCode() {
        return code;
    }

    /** @param code the course code to set */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return the start time */
    public Date getStartTime() {
        return startTime;
    }

    /** @param startTime the start time to set */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /** @return the end time */
    public Date getEndTime() {
        return endTime;
    }

    /** @param endTime the end time to set */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /** @return the list of days on which the course meets */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /** @param courseDays the list of days to set */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /** @return the enrollment quota */
    public int getQuota() {
        return quota;
    }

    /** @param quota the quota to set */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /** @return weekly teaching hours */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /** @param weeklyHours weekly hours to set */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /** @return the classroom assigned to this course */
    public Classroom getClassroom() {
        return classroom;
    }

    /** @param classroom the classroom to set */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /** @return list of enrollments for this course */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /** @param enrollments the enrollments list to set */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the enrollment quota by a given amount.
     *
     * @param amount the amount to increase the quota (must be non‑negative)
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase must be non‑negative.");
        }
        this.quota += amount;
    }

    /**
     * Decreases the enrollment quota by a given amount.
     *
     * @param amount the amount to decrease the quota (must be non‑negative and not exceed current quota)
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to decrease must be non‑negative.");
        }
        if (amount > this.quota) {
            throw new IllegalArgumentException("Cannot decrease quota below zero.");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly teaching hours for the course.
     *
     * @param newHours the new number of weekly hours (must be positive)
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Weekly hours must be positive.");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Replaces the list of days on which the course is taught.
     *
     * @param newDays an array containing the new days (e.g., {"Monday","Wednesday"})
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String d : newDays) {
            this.courseDays.add(d);
        }
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

    /** @return the classroom identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the seating capacity */
    public int getCapacity() {
        return capacity;
    }

    /** @param capacity the capacity to set */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** @return the floor where the classroom is located */
    public String getFloor() {
        return floor;
    }

    /** @param floor the floor to set */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /** @return the block where the classroom belongs */
    public String getBlock() {
        return block;
    }

    /** @param block the block to set */
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

    /** @return instructor's first name */
    public String getName() {
        return name;
    }

    /** @param name the first name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return instructor's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return the academic title (e.g., "Prof.") */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the area of specialty */
    public String getSpecialty() {
        return specialty;
    }

    /** @param specialty the specialty to set */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /** @return list of courses taught by the instructor */
    public List<Course> getCourses() {
        return courses;
    }

    /** @param courses the list of courses to set */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled across all courses taught by this instructor.
     *
     * @return the sum of enrolled students for each course
     */
    public int calculateTotalEnrolledStudents() {
        int total = 0;
        for (Course c : courses) {
            if (c.getEnrollments() != null) {
                total += c.getEnrollments().size();
            }
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
            int enrolled = (c.getEnrollments() != null) ? c.getEnrollments().size() : 0;
            remaining += Math.max(0, c.getQuota() - enrolled);
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in or drop courses.
 */
class Student {

    private String name;
    private String surname;
    private String studentId;

    /** Default constructor */
    public Student() {
    }

    /** @return student's first name */
    public String getName() {
        return name;
    }

    /** @param name the first name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return student's surname */
    public String getSurname() {
        return surname;
    }

    /** @param surname the surname to set */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /** @return unique identifier of the student */
    public String getStudentId() {
        return studentId;
    }

    /** @param studentId the identifier to set */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Enrolls the student in a given course, creating a new {@link Enrollment}
     * with an initial grade of 0.0.
     *
     * @param course the course to enroll in
     * @throws IllegalArgumentException if the course's quota has been reached
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        int currentEnrollment = (course.getEnrollments() != null) ? course.getEnrollments().size() : 0;
        if (currentEnrollment >= course.getQuota()) {
            throw new IllegalArgumentException("Course quota exceeded.");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a given course. The corresponding {@link Enrollment}
     * is removed from the course's enrollment list.
     *
     * @param course the course to drop
     * @throws IllegalArgumentException if the student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        if (course == null || course.getEnrollments() == null) {
            throw new IllegalArgumentException("Course or enrollment list is null.");
        }
        Enrollment toRemove = null;
        for (Enrollment e : course.getEnrollments()) {
            if (e.getStudent() == this) {
                toRemove = e;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in the specified course.");
        }
        course.getEnrollments().remove(toRemove);
    }
}

/**
 * Represents the enrollment of a student in a specific course, including the
 * student's grade for that course.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** Default constructor */
    public Enrollment() {
    }

    /** @return the grade achieved by the student */
    public double getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /** @return the student associated with this enrollment */
    public Student getStudent() {
        return student;
    }

    /** @param student the student to set */
    public void setStudent(Student student) {
        this.student = student;
    }

    /** @return the course associated with this enrollment */
    public Course getCourse() {
        return course;
    }

    /** @param course the course to set */
    public void setCourse(Course course) {
        this.course = course;
    }
}