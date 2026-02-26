import java.util.*;

/**
 * Represents an academic program containing courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Constructs a new AcademicProgram with an empty list of courses.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in this academic program.
     *
     * @return the list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in this academic program.
     *
     * @param courses the list of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return the sum of capacities of all classrooms used in the program
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
     * Calculates the average grade for students enrolled in a specific course.
     *
     * @param courseCode the code of the course to calculate average grade for
     * @return the average grade of students in the specified course
     * @throws IllegalArgumentException if the course is not found or has no enrollments
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
            throw new IllegalArgumentException("No enrollments found for course " + courseCode);
        }

        double sumGrades = 0;
        for (Enrollment enrollment : enrollments) {
            sumGrades += enrollment.getGrade();
        }

        return sumGrades / enrollments.size();
    }

    /**
     * Counts the number of courses that are taught on a specific day.
     *
     * @param day the day to check for courses
     * @return the number of courses taught on the specified day
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
 * Represents a course in an academic program.
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
     * Constructs a new Course with default values and empty lists.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of this course.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this course.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of this course.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of this course.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of this course.
     *
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of this course.
     *
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of this course.
     *
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of this course.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days this course is taught.
     *
     * @return the courseDays
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days this course is taught.
     *
     * @param courseDays the courseDays to set
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota (maximum number of students) for this course.
     *
     * @return the quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota (maximum number of students) for this course.
     *
     * @param quota the quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours for this course.
     *
     * @return the weeklyHours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours for this course.
     *
     * @param weeklyHours the weeklyHours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where this course is taught.
     *
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where this course is taught.
     *
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments in this course.
     *
     * @return the enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments in this course.
     *
     * @param enrollments the enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the quota of this course by the specified amount.
     *
     * @param amount the amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the quota of this course by the specified amount.
     *
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0 && this.quota - amount >= 0) {
            this.quota -= amount;
        }
    }

    /**
     * Changes the weekly hours for this course.
     *
     * @param newHours the new weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Changes the days when this course is taught.
     *
     * @param newDays the new array of course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String day : newDays) {
                this.courseDays.add(day);
            }
        }
    }
}

/**
 * Represents a classroom where courses are taught.
 */
class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    /**
     * Constructs a new Classroom with default values.
     */
    public Classroom() {
    }

    /**
     * Gets the ID of this classroom.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of this classroom.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of this classroom.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of this classroom.
     *
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where this classroom is located.
     *
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where this classroom is located.
     *
     * @param floor the floor to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where this classroom is located.
     *
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where this classroom is located.
     *
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor who teaches courses.
 */
class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Constructs a new Instructor with default values and an empty list of courses.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name of this instructor.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this instructor.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of this instructor.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of this instructor.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of this instructor.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this instructor.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of this instructor.
     *
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of this instructor.
     *
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by this instructor.
     *
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by this instructor.
     *
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     *
     * @return the total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota (available spots) across all courses taught by this instructor.
     *
     * @return the total remaining quota
     */
    public int calculateRemainingQuota() {
        int remainingQuota = 0;
        for (Course course : courses) {
            int enrolledStudents = course.getEnrollments().size();
            remainingQuota += (course.getQuota() - enrolledStudents);
        }
        return remainingQuota;
    }
}

/**
 * Represents a student who can enroll in courses.
 */
class Student {
    /**
     * Constructs a new Student.
     */
    public Student() {
    }

    /**
     * Enrolls this student in the specified course.
     *
     * @param course the course to enroll in
     * @throws IllegalArgumentException if the course is at capacity
     */
    public void enrollInCourse(Course course) {
        if (course.getEnrollments().size() >= course.getQuota()) {
            throw new IllegalArgumentException("Course is at full capacity");
        }

        // Check if student is already enrolled
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() == this) {
                return; // Already enrolled
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops this student from the specified course.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        Enrollment toRemove = null;
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() == this) {
                toRemove = enrollment;
                break;
            }
        }
        if (toRemove != null) {
            course.getEnrollments().remove(toRemove);
        }
    }
}

/**
 * Represents an enrollment of a student in a course with a grade.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Constructs a new Enrollment with default values.
     */
    public Enrollment() {
    }

    /**
     * Gets the grade for this enrollment.
     *
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this enrollment.
     *
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student for this enrollment.
     *
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student for this enrollment.
     *
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course for this enrollment.
     *
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course for this enrollment.
     *
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}