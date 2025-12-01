import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a classroom where courses are held.
 */
 class Classroom {

    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /** No‑argument constructor */
    public Classroom() {
    }

    /** Getters and Setters */
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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
 * Represents an instructor that can teach and mentor students.
 */
 class Instructor {

    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> coursesTaught = new ArrayList<>();

    /** No‑argument constructor */
    public Instructor() {
    }

    /** Getters and Setters */
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

    public List<Course> getCoursesTaught() {
        return Collections.unmodifiableList(coursesTaught);
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = new ArrayList<>(coursesTaught);
    }

    /**
     * Adds a course to the instructor's list of courses.
     *
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (!coursesTaught.contains(course)) {
            coursesTaught.add(course);
        }
    }

    /**
     * Removes a course from the instructor's list of courses.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        coursesTaught.remove(course);
    }

    /**
     * Calculates the total number of students enrolled in all courses taught by this instructor.
     *
     * @return total number of enrolled students
     */
    public int getTotalStudentsInCourses() {
        int total = 0;
        for (Course c : coursesTaught) {
            total += c.getEnrolledStudents().size();
        }
        return total;
    }

    /**
     * Calculates the remaining quota (available seats) across all courses taught by this instructor.
     *
     * @return sum of remaining quota for each course
     */
    public int getRemainingQuotaInCourses() {
        int remaining = 0;
        for (Course c : coursesTaught) {
            remaining += c.getRemainingQuota();
        }
        return remaining;
    }
}

/**
 * Represents a student that can enroll in courses, receive grades, etc.
 */
 class Student {

    private String studentId;
    private String name;
    private String surname;
    private Map<Course, Double> grades = new HashMap<>();

    /** No‑argument constructor */
    public Student() {
    }

    /** Getters and Setters */
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public Map<Course, Double> getGrades() {
        return Collections.unmodifiableMap(grades);
    }

    public void setGrades(Map<Course, Double> grades) {
        this.grades = new HashMap<>(grades);
    }

    /**
     * Enrolls the student in a course.
     *
     * @param course the course to enroll in
     */
    public void enroll(Course course) {
        course.addStudent(this);
    }

    /**
     * Drops the student from a course.
     *
     * @param course the course to drop
     */
    public void drop(Course course) {
        course.removeStudent(this);
    }

    /**
     * Assigns a grade to the student for a specific course.
     *
     * @param course the course for which the grade is given
     * @param grade  the numeric grade (e.g., 0.0 – 100.0)
     */
    public void setGrade(Course course, double grade) {
        grades.put(course, grade);
    }

    /**
     * Retrieves the grade of the student for a given course.
     *
     * @param course the course whose grade is requested
     * @return the grade, or {@code null} if no grade has been recorded
     */
    public Double getGrade(Course course) {
        return grades.get(course);
    }
}

/**
 * Represents a course within an academic program.
 */
 class Course {

    private String name;
    private String code;
    private String startTime;               // format "HH:mm"
    private String endTime;                 // format "HH:mm"
    private List<String> days = new ArrayList<>(); // e.g., "Monday", "Wednesday"
    private Classroom classroom;
    private List<Instructor> instructors = new ArrayList<>();
    private int quotaLimit;                 // maximum number of students allowed
    private int weeklyHours;
    private List<Student> enrolledStudents = new ArrayList<>();
    private Map<Student, Double> studentGrades = new HashMap<>();

    /** No‑argument constructor */
    public Course() {
    }

    /** Getters and Setters */
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
        return Collections.unmodifiableList(days);
    }

    public void setDays(List<String> days) {
        this.days = new ArrayList<>(days);
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public List<Instructor> getInstructors() {
        return Collections.unmodifiableList(instructors);
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = new ArrayList<>(instructors);
    }

    public int getQuotaLimit() {
        return quotaLimit;
    }

    public void setQuotaLimit(int quotaLimit) {
        this.quotaLimit = quotaLimit;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public List<Student> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = new ArrayList<>(enrolledStudents);
    }

    public Map<Student, Double> getStudentGrades() {
        return Collections.unmodifiableMap(studentGrades);
    }

    public void setStudentGrades(Map<Student, Double> studentGrades) {
        this.studentGrades = new HashMap<>(studentGrades);
    }

    /**
     * Adds a student to the course if quota allows.
     *
     * @param student the student to add
     * @throws IllegalStateException if the course quota is already full
     */
    public void addStudent(Student student) {
        if (enrolledStudents.size() >= quotaLimit) {
            throw new IllegalStateException("Course quota is full.");
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    /**
     * Removes a student from the course.
     *
     * @param student the student to remove
     */
    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
        studentGrades.remove(student);
    }

    /**
     * Returns the number of remaining seats (quota) in this course.
     *
     * @return remaining quota
     */
    public int getRemainingQuota() {
        return quotaLimit - enrolledStudents.size();
    }

    /**
     * Increases the quota limit by a given amount.
     *
     * @param amount the amount to increase (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     */
    public void increaseQuota(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive.");
        }
        quotaLimit += amount;
    }

    /**
     * Decreases the quota limit by a given amount.
     *
     * @param amount the amount to decrease (must be positive and not lower than current enrollment)
     * @throws IllegalArgumentException if amount is invalid
     */
    public void decreaseQuota(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive.");
        }
        int newQuota = quotaLimit - amount;
        if (newQuota < enrolledStudents.size()) {
            throw new IllegalArgumentException("New quota cannot be less than current enrollment.");
        }
        quotaLimit = newQuota;
    }

    /**
     * Sets the weekly teaching hours for the course.
     *
     * @param hours the new weekly hours (must be non‑negative)
     * @throws IllegalArgumentException if hours is negative
     */
    public void setWeeklyHours(int hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative.");
        }
        this.weeklyHours = hours;
    }

    /**
     * Adds a day on which the course is taught.
     *
     * @param day the day to add (e.g., "Monday")
     */
    public void addDay(String day) {
        if (!days.contains(day)) {
            days.add(day);
        }
    }

    /**
     * Removes a teaching day from the course schedule.
     *
     * @param day the day to remove
     */
    public void removeDay(String day) {
        days.remove(day);
    }

    /**
     * Assigns an instructor to this course.
     *
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
            instructor.addCourse(this);
        }
    }

    /**
     * Removes an instructor from this course.
     *
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        if (instructors.remove(instructor)) {
            instructor.removeCourse(this);
        }
    }

    /**
     * Records a grade for a student in this course.
     *
     * @param student the student whose grade is recorded
     * @param grade   numeric grade (e.g., 0.0 – 100.0)
     * @throws IllegalArgumentException if the student is not enrolled in the course
     */
    public void recordGrade(Student student, double grade) {
        if (!enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }
        studentGrades.put(student, grade);
    }

    /**
     * Calculates the average grade of all students in this course.
     *
     * @return average grade, or {@code 0.0} if no grades are present
     */
    public double calculateAverageGrade() {
        if (studentGrades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double g : studentGrades.values()) {
            sum += g;
        }
        return sum / studentGrades.size();
    }

    /**
     * Checks whether the course meets on a particular day.
     *
     * @param day the day to check (e.g., "Tuesday")
     * @return {@code true} if the course is scheduled on that day, otherwise {@code false}
     */
    public boolean isTaughtOnDay(String day) {
        return days.contains(day);
    }
}

/**
 * Represents an academic program consisting of many courses.
 */
 class AcademicProgram {

    private String programName;
    private List<Course> courses = new ArrayList<>();

    /** No‑argument constructor */
    public AcademicProgram() {
    }

    /** Getters and Setters */
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    /**
     * Adds a course to the academic program.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the academic program.
     *
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in this academic program.
     *
     * @return sum of capacities of distinct classrooms associated with the program's courses
     */
    public int getTotalClassroomCapacity() {
        int total = 0;
        // Use a set to avoid counting the same classroom multiple times
        java.util.Set<Classroom> counted = new java.util.HashSet<>();
        for (Course c : courses) {
            Classroom cr = c.getClassroom();
            if (cr != null && !counted.contains(cr)) {
                total += cr.getCapacity();
                counted.add(cr);
            }
        }
        return total;
    }

    /**
     * Returns the number of courses that are taught on a specific day.
     *
     * @param day the day to search for (e.g., "Monday")
     * @return number of courses scheduled on the given day
     */
    public int getNumberOfCoursesOnDay(String day) {
        int count = 0;
        for (Course c : courses) {
            if (c.isTaughtOnDay(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the average grade for a specific course within this academic program.
     *
     * @param courseCode the unique code identifying the course
     * @return average grade, or {@code 0.0} if the course does not exist or has no grades
     */
    public double getAverageGradeForCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                return c.calculateAverageGrade();
            }
        }
        return 0.0;
    }
}