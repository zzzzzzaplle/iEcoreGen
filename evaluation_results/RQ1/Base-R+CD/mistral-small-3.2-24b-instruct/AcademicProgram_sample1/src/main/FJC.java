import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AcademicProgram {
    private List<Course> courses;

    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program.
     *
     * @return The total classroom capacity.
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course within the Academic Program.
     *
     * @param courseCode The code of the course.
     * @return The average grade for the course.
     * @throws IllegalArgumentException if the course is not found.
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                if (course.getEnrollments().isEmpty()) {
                    return 0.0;
                }
                double totalGrade = 0.0;
                for (Enrollment enrollment : course.getEnrollments()) {
                    totalGrade += enrollment.getGrade();
                }
                return totalGrade / course.getEnrollments().size();
            }
        }
        throw new IllegalArgumentException("Course not found: " + courseCode);
    }

    /**
     * Finds the total number of courses in the Academic Program that are taught on a given day.
     *
     * @param day The day to count courses for.
     * @return The number of courses on the specified day.
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

    // Getters and Setters
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

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

    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Increases the quota of the course by the specified amount.
     *
     * @param amount The amount to increase the quota by.
     */
    public void increaseQuotaBy(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the quota of the course by the specified amount.
     *
     * @param amount The amount to decrease the quota by.
     */
    public void decreaseQuotaBy(int amount) {
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours of the course to the specified new hours.
     *
     * @param newHours The new weekly hours.
     */
    public void changeWeeklyHours(int newHours) {
        this.weeklyHours = newHours;
    }

    /**
     * Changes the days of the course to the specified new days.
     *
     * @param newDays The new days of the course.
     */
    public void changeCourseDays(String[] newDays) {
        this.courseDays.clear();
        for (String day : newDays) {
            this.courseDays.add(day);
        }
    }

    // Getters and Setters
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
}

class Classroom {
    private String id;
    private int capacity;
    private String floor;
    private String block;

    public Classroom() {
    }

    // Getters and Setters
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

class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
     *
     * @return The total number of enrolled students.
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrollments().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     *
     * @return The total remaining quota.
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota();
        }
        return totalQuota;
    }

    // Getters and Setters
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
}

class Student {
    public Student() {
    }

    /**
     * Enrolls the student in the specified course.
     *
     * @param course The course to enroll in.
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from the specified course.
     *
     * @param course The course to drop.
     */
    public void dropCourse(Course course) {
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent().equals(this)) {
                enrollmentToRemove = enrollment;
                break;
            }
        }
        if (enrollmentToRemove != null) {
            course.getEnrollments().remove(enrollmentToRemove);
        }
    }
}

class Enrollment {
    private double grade;
    private Student student;
    private Course course;

    public Enrollment() {
    }

    // Getters and Setters
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