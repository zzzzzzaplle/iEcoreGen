import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AcademicProgram {
    private List<Course> courses = new ArrayList<>();

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program.
     * @return The sum of classroom capacities.
     */
    public int sumClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            totalCapacity += course.getClassroom().getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates the average grade for students enrolled in a specific course.
     * @param courseCode The code of the course.
     * @return The average grade for the specified course.
     * @throws IllegalArgumentException If the course is not found or has no enrollments.
     */
    public double calculateAverageGrade(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }
        List<Enrollment> enrollments = course.getEnrollments();
        if (enrollments.isEmpty()) {
            throw new IllegalArgumentException("No enrollments for the course");
        }
        double sum = 0;
        for (Enrollment enrollment : enrollments) {
            sum += enrollment.getGrade();
        }
        return sum / enrollments.size();
    }

    /**
     * Counts the number of courses taught on a specific day.
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

    /**
     * Finds a course by its code.
     * @param courseCode The code of the course to find.
     * @return The Course object if found, null otherwise.
     */
    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
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
    private List<String> courseDays = new ArrayList<>();
    private int quota;
    private int weeklyHours;
    private Classroom classroom;
    private List<Enrollment> enrollments = new ArrayList<>();

    /**
     * Increases the course quota by the specified amount.
     * @param amount The amount to increase the quota by.
     */
    public void increaseQuotaBy(int amount) {
        quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount.
     * @param amount The amount to decrease the quota by.
     * @throws IllegalArgumentException If the amount is negative or exceeds the quota.
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount > quota) {
            throw new IllegalArgumentException("Amount exceeds quota");
        }
        quota -= amount;
    }

    /**
     * Changes the weekly hours of the course.
     * @param newHours The new weekly hours.
     * @throws IllegalArgumentException If the new hours are not positive.
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours <= 0) {
            throw new IllegalArgumentException("Hours must be positive");
        }
        weeklyHours = newHours;
    }

    /**
     * Changes the days of the course.
     * @param newDays The new days for the course.
     * @throws IllegalArgumentException If the new days list is empty.
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays == null || newDays.length == 0) {
            throw new IllegalArgumentException("Days list cannot be empty");
        }
        courseDays.clear();
        for (String day : newDays) {
            courseDays.add(day);
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
    private List<Course> courses = new ArrayList<>();

    /**
     * Calculates the total number of students enrolled in all courses of the instructor.
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
     * @return The remaining quota.
     */
    public int calculateRemainingQuota() {
        int totalQuota = 0;
        for (Course course : courses) {
            totalQuota += course.getQuota();
        }
        int totalEnrolled = calculateTotalEnrolledStudents();
        return totalQuota - totalEnrolled;
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
    /**
     * Enrolls the student in a course.
     * @param course The course to enroll in.
     */
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a course.
     * @param course The course to drop from.
     * @throws IllegalArgumentException If the student is not enrolled in the course.
     */
    public void dropCourse(Course course) {
        List<Enrollment> enrollments = course.getEnrollments();
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getStudent().equals(this)) {
                enrollments.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Student is not enrolled in the course");
    }
}

class Enrollment {
    private double grade;
    private Student student;
    private Course course;

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