import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program in the Software Engineering Department
 * Contains a collection of courses offered in the program
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
     * @return List of Course objects
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program
     * @param courses List of Course objects to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
     * by summing up the capacities of all classrooms associated with each course
     * @return Total capacity of all classrooms as integer
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
     * @param courseCode The unique code identifying the course
     * @return Average grade as double, or 0.0 if no students are enrolled or course not found
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
     * Counts the number of courses taught on a specific day in the academic program
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
 * Represents a course offered in the academic program
 * Contains course details, schedule, classroom, and student enrollments
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
     * @param amount The number of additional spots to add to the quota
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount
     * @param amount The number of spots to remove from the quota
     * @throws IllegalArgumentException if the amount exceeds current quota
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > this.quota) {
            throw new IllegalArgumentException("Cannot decrease quota by more than current quota");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly course hours to a new value
     * @param newHours The new number of weekly hours for the course
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days to new specified days
     * @param newDays Array of strings representing the new course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }

    /**
     * Calculates the average grade for students enrolled in this course
     * @return Average grade as double, or 0.0 if no students are enrolled
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
     * Gets the number of students currently enrolled in the course
     * @return Number of enrolled students
     */
    public int getEnrolledStudentCount() {
        return enrollments.size();
    }

    /**
     * Gets the remaining available spots in the course
     * @return Remaining quota (quota minus enrolled students)
     */
    public int getRemainingQuota() {
        return quota - enrollments.size();
    }
}

/**
 * Represents a physical classroom with location and capacity information
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
 * Represents an instructor who teaches courses and mentors students
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
     * @return Total number of enrolled students across all instructor's courses
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudentCount();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota across all courses taught by this instructor
     * @return Total remaining quota across all instructor's courses
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
 * Represents a student who can enroll in courses and request documents
 */
 class Student {
    private List<Enrollment> enrollments;

    /**
     * Default constructor initializes an empty list of enrollments
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
     * Enrolls the student in a course by creating a new enrollment record
     * @param course The course to enroll in
     * @throws IllegalArgumentException if the course is null or quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getRemainingQuota() <= 0) {
            throw new IllegalArgumentException("Course quota is full");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // Default grade
        
        enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course by removing the enrollment record
     * @param course The course to drop from
     * @throws IllegalArgumentException if the student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        Enrollment toRemove = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                toRemove = enrollment;
                break;
            }
        }
        
        if (toRemove != null) {
            enrollments.remove(toRemove);
            course.getEnrollments().remove(toRemove);
        } else {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
    }
}

/**
 * Represents the enrollment relationship between a student and a course
 * Contains grade information for the student in the course
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