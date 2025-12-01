import java.util.*;

/**
 * Represents an academic program containing multiple courses.
 */
class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor initializes an empty list of courses.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in this academic program.
     *
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in this academic program.
     *
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return Total classroom capacity
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
     * @param courseCode The code of the course to calculate average grade for
     * @return Average grade of students in the specified course
     * @throws IllegalArgumentException if no course with the given code exists
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
     * @param day The day to check for courses
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
     * Default constructor initializes empty collections.
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the name of the course.
     *
     * @return Course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name Course name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the code of the course.
     *
     * @return Course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the course.
     *
     * @param code Course code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the start time of the course.
     *
     * @return Start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the course.
     *
     * @param startTime Start time to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the course.
     *
     * @return End time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the course.
     *
     * @param endTime End time to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the days when the course is held.
     *
     * @return List of course days
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the days when the course is held.
     *
     * @param courseDays List of course days to set
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the quota (maximum number of students) for the course.
     *
     * @return Course quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the quota (maximum number of students) for the course.
     *
     * @param quota Course quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours of the course.
     *
     * @return Weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours of the course.
     *
     * @param weeklyHours Weekly hours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom where the course is held.
     *
     * @return Classroom object
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom where the course is held.
     *
     * @param classroom Classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments in this course.
     *
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments in this course.
     *
     * @param enrollments List of enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by a specified amount.
     *
     * @param amount The amount to increase the quota by
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the course quota by a specified amount.
     *
     * @param amount The amount to decrease the quota by
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0 && this.quota >= amount) {
            this.quota -= amount;
        }
    }

    /**
     * Changes the weekly hours of the course.
     *
     * @param newHours The new weekly hours to set
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Changes the days when the course is held.
     *
     * @param newDays Array of new days to set
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        if (newDays != null) {
            for (String day : newDays) {
                this.courseDays.add(day);
            }
        }
    }

    /**
     * Gets the current number of enrolled students.
     *
     * @return Number of enrolled students
     */
    public int getCurrentEnrollmentCount() {
        return this.enrollments.size();
    }

    /**
     * Gets the remaining quota for this course.
     *
     * @return Remaining quota
     */
    public int getRemainingQuota() {
        return this.quota - this.enrollments.size();
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

    /**
     * Default constructor.
     */
    public Classroom() {
    }

    /**
     * Gets the ID of the classroom.
     *
     * @return Classroom ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the classroom.
     *
     * @param id Classroom ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the capacity of the classroom.
     *
     * @return Classroom capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the classroom.
     *
     * @param capacity Classroom capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where the classroom is located.
     *
     * @return Floor information
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the classroom is located.
     *
     * @param floor Floor information to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where the classroom is located.
     *
     * @return Block information
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where the classroom is located.
     *
     * @param block Block information to set
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
     * Default constructor initializes an empty list of courses.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the first name of the instructor.
     *
     * @return Instructor's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name of the instructor.
     *
     * @param name Instructor's first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the instructor.
     *
     * @return Instructor's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the instructor.
     *
     * @param surname Instructor's surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the title of the instructor.
     *
     * @return Instructor's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the instructor.
     *
     * @param title Instructor's title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the specialty of the instructor.
     *
     * @return Instructor's specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the instructor.
     *
     * @param specialty Instructor's specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by this instructor.
     *
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by this instructor.
     *
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     *
     * @return Total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getCurrentEnrollmentCount();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota across all courses taught by this instructor.
     *
     * @return Total remaining quota
     */
    public int calculateRemainingQuota() {
        int remainingQuota = 0;
        for (Course course : courses) {
            remainingQuota += course.getRemainingQuota();
        }
        return remainingQuota;
    }
}

/**
 * Represents a student enrolled in courses.
 */
class Student {
    private String name;
    private String surname;
    private List<Enrollment> enrollments;

    /**
     * Default constructor initializes an empty list of enrollments.
     */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the first name of the student.
     *
     * @return Student's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name of the student.
     *
     * @param name Student's first name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the student.
     *
     * @return Student's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the student.
     *
     * @param surname Student's surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the list of enrollments for this student.
     *
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments for this student.
     *
     * @param enrollments List of enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course The course to enroll in
     */
    public void enrollInCourse(Course course) {
        // Check if already enrolled
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                return; // Already enrolled
            }
        }

        // Check if course has available quota
        if (course.getCurrentEnrollmentCount() >= course.getQuota()) {
            throw new RuntimeException("Course quota is full");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // Default grade

        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course.
     *
     * @param course The course to drop
     */
    public void dropCourse(Course course) {
        Iterator<Enrollment> iterator = enrollments.iterator();
        while (iterator.hasNext()) {
            Enrollment enrollment = iterator.next();
            if (enrollment.getCourse().equals(course)) {
                iterator.remove();
                course.getEnrollments().remove(enrollment);
                return;
            }
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
     * Default constructor.
     */
    public Enrollment() {
    }

    /**
     * Gets the grade for this enrollment.
     *
     * @return Grade value
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this enrollment.
     *
     * @param grade Grade value to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Gets the student in this enrollment.
     *
     * @return Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student in this enrollment.
     *
     * @param student Student object to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course in this enrollment.
     *
     * @return Course object
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course in this enrollment.
     *
     * @param course Course object to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}