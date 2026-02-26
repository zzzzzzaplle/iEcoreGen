import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an academic program that groups multiple courses.
 */
 class AcademicProgram {

    /** List of courses belonging to this academic program. */
    private List<Course> courses;

    /** Unparameterized constructor – initializes the courses list. */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /** @return the list of courses */
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
     * @return the sum of the capacities of each classroom associated with the program's courses
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
     * @return the average grade of all enrollments in the course, or 0.0 if none exist
     */
    public double calculateAverageGrade(String courseCode) {
        for (Course c : courses) {
            if (c.getCode() != null && c.getCode().equals(courseCode)) {
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
        return 0.0;
    }

    /**
     * Counts the number of courses that are taught on a given day.
     *
     * @param day the day to look for (e.g., "Monday")
     * @return the number of courses scheduled on the specified day
     */
    public int countCoursesOnSpecialDay(String day) {
        int count = 0;
        if (day == null) {
            return 0;
        }
        for (Course c : courses) {
            List<String> days = c.getCourseDays();
            if (days != null) {
                for (String d : days) {
                    if (day.equalsIgnoreCase(d)) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}

/**
 * Represents a classroom where a course is held.
 */
class Classroom {

    private String id;
    private int capacity;
    private String floor;
    private String block;

    /** Unparameterized constructor. */
    public Classroom() {
    }

    /** @return classroom identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the capacity of the classroom */
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

    /** @return the block where the classroom is located */
    public String getBlock() {
        return block;
    }

    /** @param block the block to set */
    public void setBlock(String block) {
        this.block = block;
    }
}

/**
 * Represents a course within an academic program.
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
    private Instructor instructor; // optional back‑reference

    /** Unparameterized constructor – initializes collections. */
    public Course() {
        this.courseDays = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    /** @return the course name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the course code */
    public String getCode() {
        return code;
    }

    /** @param code the code to set */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return start time */
    public Date getStartTime() {
        return startTime;
    }

    /** @param startTime the start time to set */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /** @return end time */
    public Date getEndTime() {
        return endTime;
    }

    /** @param endTime the end time to set */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /** @return list of days when the course takes place */
    public List<String> getCourseDays() {
        return courseDays;
    }

    /** @param courseDays the days to set */
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

    /** @param weeklyHours the weekly hours to set */
    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    /** @return the classroom assigned to the course */
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

    /** @param enrollments the enrollment list to set */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /** @return the instructor responsible for the course */
    public Instructor getInstructor() {
        return instructor;
    }

    /** @param instructor the instructor to set */
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /**
     * Increases the enrollment quota by a given amount.
     *
     * @param amount the number of seats to add to the quota (must be non‑negative)
     */
    public void increaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota += amount;
        }
    }

    /**
     * Decreases the enrollment quota by a given amount.
     *
     * @param amount the number of seats to remove from the quota (must be non‑negative)
     */
    public void decreaseQuotaBy(int amount) {
        if (amount > 0) {
            this.quota = Math.max(0, this.quota - amount);
        }
    }

    /**
     * Changes the weekly teaching hours for the course.
     *
     * @param newHours the new number of weekly hours (must be non‑negative)
     */
    public void changeWeeklyHours(int newHours) {
        if (newHours >= 0) {
            this.weeklyHours = newHours;
        }
    }

    /**
     * Replaces the current course days with a new set of days.
     *
     * @param newDays an array containing the new days (e.g., {"Monday","Wednesday"})
     */
    public void changeCourseDays(String[] newDays) {
        if (newDays != null) {
            this.courseDays = new ArrayList<>(Arrays.asList(newDays));
        }
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

    /** Unparameterized constructor – initializes the courses list. */
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

    /** @return instructor's title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return instructor's specialty */
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
     * @return the cumulative count of enrolled students
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
     * Calculates the remaining enrollment quota for all courses taught by this instructor.
     *
     * @return the sum of (quota - current enrollment) for each course
     */
    public int calculateRemainingQuota() {
        int remaining = 0;
        for (Course c : courses) {
            int enrolled = (c.getEnrollments() != null) ? c.getEnrollments().size() : 0;
            int diff = c.getQuota() - enrolled;
            remaining += Math.max(0, diff);
        }
        return remaining;
    }
}

/**
 * Represents a student who can enroll in and drop courses.
 */
class Student {

    private String name;
    private String surname;
    private List<Enrollment> enrollments;

    /** Unparameterized constructor – initializes the enrollment list. */
    public Student() {
        this.enrollments = new ArrayList<>();
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

    /** @return list of enrollments belonging to the student */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /** @param enrollments the enrollment list to set */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Enrolls the student in a given course if quota permits.
     *
     * @param course the course to enroll in
     * @throws IllegalArgumentException if the course is null or quota is full
     */
    public void enrollInCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        int currentEnrollment = (course.getEnrollments() != null) ? course.getEnrollments().size() : 0;
        if (currentEnrollment >= course.getQuota()) {
            throw new IllegalArgumentException("Course quota is full.");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(this);
        enrollment.setCourse(course);
        enrollment.setGrade(0.0); // default grade

        // Add to both sides of the relationship
        this.enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
    }

    /**
     * Drops the student from a given course.
     *
     * @param course the course to drop
     * @throws IllegalArgumentException if the course is null or the student is not enrolled
     */
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        Enrollment toRemove = null;
        for (Enrollment e : this.enrollments) {
            if (e.getCourse() == course) {
                toRemove = e;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalArgumentException("Student is not enrolled in the specified course.");
        }
        this.enrollments.remove(toRemove);
        if (course.getEnrollments() != null) {
            course.getEnrollments().remove(toRemove);
        }
    }
}

/**
 * Represents the enrollment of a student in a specific course, together with the achieved grade.
 */
class Enrollment {

    private double grade;
    private Student student;
    private Course course;

    /** Unparameterized constructor. */
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