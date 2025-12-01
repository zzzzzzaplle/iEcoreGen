import java.util.*;

/**
 * Represents an academic program containing courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Constructs an AcademicProgram with an empty list of courses.
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
     * @return the sum of capacities of all classrooms used in courses
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
     * @throws IllegalArgumentException if the course is not found
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

        double sumGrades = 0.0;
        for (Enrollment enrollment : enrollments) {
            sumGrades += enrollment.getGrade();
        }

        return sumGrades / enrollments.size();
    }

    /**
     * Counts the number of courses taught on a specific day.
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
     * Constructs a Course with default values.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of the course.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     *
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     *
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     *
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days when the course is taught.
     *
     * @return the courseDays
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days when the course is taught.
     *
     * @param courseDays the courseDays to set
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota of the course.
     *
     * @return the quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota of the course.
     *
     * @param quota the quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     *
     * @return the weeklyHours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     *
     * @param weeklyHours the weeklyHours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where the course is taught.
     *
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is taught.
     *
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the enrollments in this course.
     *
     * @return the enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the enrollments in this course.
     *
     * @param enrollments the enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by the specified amount.
     *
     * @param amount the amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount.
     *
     * @param amount the amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
        if (this.quota < 0) {
            this.quota = 0;
        }
    }

    /**
     * Changes the weekly hours of the course.
     *
     * @param newHours the new weekly hours
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days when the course is taught.
     *
     * @param newDays the new array of course days
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        Collections.addAll(this.courseDays, newDays);
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
     * Constructs a Classroom with default values.
     */
    public Classroom() {
    }

    /**
     * Gets the ID of the classroom.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the classroom.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of the classroom.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     *
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor of the classroom.
     *
     * @return the floor
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor of the classroom.
     *
     * @param floor the floor to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block of the classroom.
     *
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block of the classroom.
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
     * Constructs an Instructor with default values.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the name of the instructor.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     *
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     *
     * @param specialty the specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the courses taught by this instructor.
     *
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the courses taught by this instructor.
     *
     * @param courses the courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses of this instructor.
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
     * Calculates the remaining quota for all courses of this instructor.
     *
     * @return the total remaining quota across all courses
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
 * Represents a student who enrolls in courses.
 */
class Student {
    private String name;
    private String surname;
    private List<Enrollment> enrollments;

    /**
     * Constructs a Student with default values.
     */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of the student.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the student.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the student.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the enrollments of this student.
     *
     * @return the enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the enrollments of this student.
     *
     * @param enrollments the enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course the course to enroll in
     */
    public void enrollInCourse(Course course) {
        // Check if student is already enrolled
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getCode().equals(course.getCode())) {
                return; // Already enrolled
            }
        }

        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0);

        // Add to student's enrollments
        enrollments.add(enrollment);

        // Add to course's enrollments
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops a course from the student's enrollments.
     *
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getCode().equals(course.getCode())) {
                enrollmentToRemove = enrollment;
                break;
            }
        }

        if (enrollmentToRemove != null) {
            enrollments.remove(enrollmentToRemove);
            course.getEnrollments().remove(enrollmentToRemove);
        }
    }
}

/**
 * Represents an enrollment of a student in a course.
 */
class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    /**
     * Constructs an Enrollment with default values.
     */
    public Enrollment() {
    }

    /**
     * Gets the grade of the enrollment.
     *
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the enrollment.
     *
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student of the enrollment.
     *
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student of the enrollment.
     *
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course of the enrollment.
     *
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course of the enrollment.
     *
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}