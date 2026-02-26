import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an Academic Program containing multiple courses
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor initializing the courses list
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
     * @param courses List of Course objects
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
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
     * @param courseCode The code of the course to calculate average grade for
     * @return Average grade as double, returns 0.0 if no enrollments found
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
        for (Enrollment enrollment : enrollments) {
            totalGrade += enrollment.getGrade();
        }
        
        return totalGrade / enrollments.size();
    }

    /**
     * Counts the number of courses taught on a specific day in the Academic Program
     * @param day The day to search for (e.g., "Monday", "Tuesday")
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
     * Default constructor initializing collections
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the course name
     * @return Course name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name
     * @param name Course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course code
     * @return Course code as String
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code
     * @param code Course code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the course start time
     * @return Start time as Date
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the course start time
     * @param startTime Start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the course end time
     * @return End time as Date
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the course end time
     * @param endTime End time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the list of course days
     * @return List of days as Strings
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the list of course days
     * @param courseDays List of days
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the course quota
     * @return Course quota as integer
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the course quota
     * @param quota Course quota
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours
     * @return Weekly hours as integer
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours
     * @param weeklyHours Weekly hours
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
     * @param classroom Classroom object
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments for the course
     * @return List of Enrollment objects
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments for the course
     * @param enrollments List of Enrollment objects
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by specified amount
     * @param amount The amount to increase quota by
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the course quota by specified amount
     * @param amount The amount to decrease quota by
     * @throws IllegalArgumentException if amount is negative or exceeds current quota
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount > this.quota) {
            throw new IllegalArgumentException("Cannot decrease quota below zero");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours for the course
     * @param newHours New weekly hours value
     * @throws IllegalArgumentException if newHours is negative
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days to new specified days
     * @param newDays Array of new days for the course
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
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
     * @return Classroom ID as String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the classroom ID
     * @param id Classroom ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the classroom capacity
     * @return Capacity as integer
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the classroom capacity
     * @param capacity Classroom capacity
     * @throws IllegalArgumentException if capacity is negative
     */
    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.capacity = capacity;
    }

    /**
     * Gets the floor where classroom is located
     * @return Floor information as String
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where classroom is located
     * @param floor Floor information
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where classroom is located
     * @return Block information as String
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where classroom is located
     * @param block Block information
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor with personal and professional information
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor initializing courses list
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the instructor's first name
     * @return First name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's first name
     * @param name First name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's surname
     * @return Surname as String
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's surname
     * @param surname Surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's title
     * @return Title as String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's title
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's specialty
     * @return Specialty as String
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's specialty
     * @param specialty Specialty
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor
     * @return List of Course objects
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor
     * @param courses List of Course objects
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by the instructor
     * @return Total number of enrolled students as integer
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses taught by the instructor
     * @return Total remaining quota as integer
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        int totalEnrollments = 0;
        
        for (Course course : courses) {
            totalQuota += course.getQuota();
            totalEnrollments += course.getEnrollments().size();
        }
        
        return totalQuota - totalEnrollments;
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
        // Implementation would typically add enrollment to course
    }

    /**
     * Drops the student from a course
     * @param course The course to drop from
     */
    public void dropCourse(Course course) {
        // Implementation would typically remove enrollment from course
    }
}

/**
 * Represents an enrollment relationship between a student and a course
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
     * Gets the grade for the enrollment
     * @return Grade as double
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the enrollment
     * @param grade Grade value
     * @throws IllegalArgumentException if grade is negative
     */
    public void setGrade(double grade) {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative");
        }
        this.grade = grade;
    }

    /**
     * Gets the student associated with the enrollment
     * @return Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student associated with the enrollment
     * @param student Student object
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course associated with the enrollment
     * @return Course object
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course associated with the enrollment
     * @param course Course object
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}