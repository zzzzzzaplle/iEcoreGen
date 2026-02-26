import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an academic program containing multiple courses
 */
 class AcademicProgram {
    private List<Course> courses;

    /**
     * Default constructor
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the list of courses in the academic program
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses in the academic program
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
     * @return Total capacity of all classrooms
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
     * @return Average grade of students in the specified course
     * @throws IllegalArgumentException if course code is null or empty, or if course is not found
     */
    public double calculateAverageGrade(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        
        Course targetCourse = null;
        for (Course course : courses) {
            if (courseCode.equals(course.getCode())) {
                targetCourse = course;
                break;
            }
        }
        
        if (targetCourse == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found");
        }
        
        return targetCourse.calculateAverageGrade();
    }

    /**
     * Counts the number of courses taught on a specific day in the academic program
     * @param day The day to count courses for (e.g., "Monday", "Tuesday")
     * @return Number of courses taught on the specified day
     * @throws IllegalArgumentException if day is null or empty
     */
    public int countCoursesOnSpecialDay(String day) {
        if (day == null || day.trim().isEmpty()) {
            throw new IllegalArgumentException("Day cannot be null or empty");
        }
        
        int count = 0;
        for (Course course : courses) {
            if (course.getCourseDays() != null && course.getCourseDays().contains(day)) {
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
     * Default constructor
     */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the course name
     * @return Course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name
     * @param name Course name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course code
     * @return Course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code
     * @param code Course code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the course start time
     * @return Course start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the course start time
     * @param startTime Course start time to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the course end time
     * @return Course end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the course end time
     * @param endTime Course end time to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the list of course days
     * @return List of course days
     */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /**
     * Sets the list of course days
     * @param courseDays List of course days to set
     */
    public void setCourseDays(List<String> courseDays) {
        this.courseDays = courseDays;
    }

    /**
     * Gets the course quota
     * @return Course quota
     */
    public int getQuota() {
        return quota;
    }

    /**
     * Sets the course quota
     * @param quota Course quota to set
     */
    public void setQuota(int quota) {
        this.quota = quota;
    }

    /**
     * Gets the weekly hours
     * @return Weekly hours
     */
    public int getWeeklyHours() {
        return weeklyHours;
    }

    /**
     * Sets the weekly hours
     * @param weeklyHours Weekly hours to set
     */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /**
     * Gets the classroom
     * @return Classroom object
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Sets the classroom
     * @param classroom Classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Gets the list of enrollments
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of enrollments
     * @param enrollments List of enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Increases the course quota by the specified amount
     * @param amount The amount to increase the quota by
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount
     * @param amount The amount to decrease the quota by
     * @throws IllegalArgumentException if amount is negative or if new quota would be less than current enrollments
     */
    public void decreaseQuotaBy(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (this.quota - amount < enrollments.size()) {
            throw new IllegalArgumentException("New quota cannot be less than current number of enrollments");
        }
        this.quota -= amount;
    }

    /**
     * Changes the weekly hours for the course
     * @param newHours The new weekly hours value
     * @throws IllegalArgumentException if newHours is negative
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours < 0) {
            throw new IllegalArgumentException("Weekly hours cannot be negative");
        }
        this.weeklyHours = newHours;
    }

    /**
     * Changes the course days
     * @param newDays Array of new course days
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
     * Calculates the average grade for students enrolled in this course
     * @return Average grade of enrolled students
     * @throws IllegalStateException if there are no enrollments
     */
    public double calculateAverageGrade() {
        if (enrollments.isEmpty()) {
            throw new IllegalStateException("Cannot calculate average grade: no enrollments");
        }
        
        double totalGrade = 0;
        for (Enrollment enrollment : enrollments) {
            totalGrade += enrollment.getGrade();
        }
        return totalGrade / enrollments.size();
    }

    /**
     * Gets the number of students currently enrolled in the course
     * @return Number of enrolled students
     */
    public int getEnrolledStudentCount() {
        return enrollments.size();
    }

    /**
     * Gets the remaining quota for the course
     * @return Remaining quota
     */
    public int getRemainingQuota() {
        return quota - enrollments.size();
    }
}

/**
 * Represents a classroom where courses are taught
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
     * @return Classroom ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the classroom ID
     * @param id Classroom ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the classroom capacity
     * @return Classroom capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the classroom capacity
     * @param capacity Classroom capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the floor where classroom is located
     * @return Floor information
     */
    public String getFloor() {
        return floor;
    }

    /**
     * Sets the floor where classroom is located
     * @param floor Floor information to set
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the block where classroom is located
     * @return Block information
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the block where classroom is located
     * @param block Block information to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents an instructor who teaches courses
 */
 class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Gets the instructor's name
     * @return Instructor name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instructor's name
     * @param name Instructor name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the instructor's surname
     * @return Instructor surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the instructor's surname
     * @param surname Instructor surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the instructor's title
     * @return Instructor title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the instructor's title
     * @param title Instructor title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the instructor's specialty
     * @return Instructor specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the instructor's specialty
     * @param specialty Instructor specialty to set
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the list of courses taught by the instructor
     * @return List of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses taught by the instructor
     * @param courses List of courses to set
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Calculates the total number of students enrolled in all courses of this instructor
     * @return Total number of enrolled students
     */
    public int calculateTotalEnrolledStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudentCount();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this instructor
     * @return Total remaining quota across all courses
     */
    public int calculateRemainingQuota() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }
}

/**
 * Represents a student who can enroll in courses
 */
 class Student {
    private List<Enrollment> enrollments;

    /**
     * Default constructor
     */
    public Student() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Gets the list of student's enrollments
     * @return List of enrollments
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Sets the list of student's enrollments
     * @param enrollments List of enrollments to set
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a course
     * @param course The course to enroll in
     * @throws IllegalArgumentException if course is null or if course has no remaining quota
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getRemainingQuota() <= 0) {
            throw new IllegalArgumentException("Course has no remaining quota");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // Default grade
        
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops a course that the student is enrolled in
     * @param course The course to drop
     * @throws IllegalArgumentException if course is null or if student is not enrolled in the course
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        
        Enrollment enrollmentToRemove = null;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().equals(course)) {
                enrollmentToRemove = enrollment;
                break;
            }
        }
        
        if (enrollmentToRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
        
        enrollments.remove(enrollmentToRemove);
        course.getEnrollments().remove(enrollmentToRemove);
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
     * Gets the grade for this enrollment
     * @return Grade value
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this enrollment
     * @param grade Grade value to set
     * @throws IllegalArgumentException if grade is negative
     */
    public void setGrade(double grade) {
        if (grade < 0) {
            throw new IllegalArgumentException("Grade cannot be negative");
        }
        this.grade = grade;
    }

    /**
     * Gets the student associated with this enrollment
     * @return Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student associated with this enrollment
     * @param student Student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the course associated with this enrollment
     * @return Course object
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the course associated with this enrollment
     * @param course Course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}