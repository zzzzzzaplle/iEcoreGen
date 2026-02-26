import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic program in the Software Engineering Department
 */
 class AcademicProgram {
    private String name;
    private List<Course> courses;
    private List<Classroom> classrooms;
    private List<Instructor> instructors;
    private List<Student> students;

    /**
     * Default constructor
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Calculates the total capacity of all classrooms used in this Academic Program
     * @return the total capacity of all classrooms
     */
    public int getTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Classroom classroom : classrooms) {
            totalCapacity += classroom.getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Finds the total number of courses in this Academic Program that are taught on a given day
     * @param day the day to check for courses
     * @return the number of courses on the specified day
     */
    public int getCoursesOnDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays() != null && course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

/**
 * Represents a course in the academic program
 */
 class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private String days;
    private Classroom classroom;
    private Instructor instructor;
    private List<Student> enrolledStudents;
    private int quota;
    private int weeklyHours;

    /**
     * Default constructor
     */
    public Course() {
        this.enrolledStudents = new ArrayList<>();
    }

    /**
     * Calculates the average grade for the students enrolled in this course
     * @return the average grade of all enrolled students, or 0.0 if no students are enrolled
     */
    public double getAverageGrade() {
        if (enrolledStudents.isEmpty()) {
            return 0.0;
        }
        
        double totalGrade = 0.0;
        int count = 0;
        
        for (Student student : enrolledStudents) {
            Double grade = student.getCourseGrade(this);
            if (grade != null) {
                totalGrade += grade;
                count++;
            }
        }
        
        return count > 0 ? totalGrade / count : 0.0;
    }

    /**
     * Gets the remaining quota for this course
     * @return the remaining available spots in the course
     */
    public int getRemainingQuota() {
        return quota - enrolledStudents.size();
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
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
}

/**
 * Represents a classroom with capacity and location information
 */
 class Classroom {
    private String classroomId;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Default constructor
     */
    public Classroom() {
    }

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
 * Represents an instructor with personal and professional information
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
     * Determines the total number of students enrolled in all courses of this instructor
     * @return the total number of students across all courses taught by this instructor
     */
    public int getTotalStudentsInCourses() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getEnrolledStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of this instructor
     * @return the total remaining quota across all courses taught by this instructor
     */
    public int getRemainingQuotaInCourses() {
        int totalRemainingQuota = 0;
        for (Course course : courses) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }

    /**
     * Increases the course quota limit
     * @param course the course to modify
     * @param increaseAmount the amount to increase the quota by
     */
    public void increaseCourseQuota(Course course, int increaseAmount) {
        if (courses.contains(course)) {
            course.setQuota(course.getQuota() + increaseAmount);
        }
    }

    /**
     * Decreases the course quota limit
     * @param course the course to modify
     * @param decreaseAmount the amount to decrease the quota by
     */
    public void decreaseCourseQuota(Course course, int decreaseAmount) {
        if (courses.contains(course)) {
            int newQuota = course.getQuota() - decreaseAmount;
            if (newQuota < course.getEnrolledStudents().size()) {
                newQuota = course.getEnrolledStudents().size();
            }
            course.setQuota(newQuota);
        }
    }

    /**
     * Changes the weekly course hours
     * @param course the course to modify
     * @param newWeeklyHours the new weekly hours for the course
     */
    public void changeWeeklyCourseHours(Course course, int newWeeklyHours) {
        if (courses.contains(course)) {
            course.setWeeklyHours(newWeeklyHours);
        }
    }

    /**
     * Changes the course days
     * @param course the course to modify
     * @param newDays the new days for the course
     */
    public void changeCourseDays(Course course, String newDays) {
        if (courses.contains(course)) {
            course.setDays(newDays);
        }
    }

    /**
     * Adds a course to the instructor's list
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.setInstructor(this);
        }
    }

    /**
     * Drops a course from the instructor's list
     * @param course the course to drop
     */
    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.setInstructor(null);
        }
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
}

/**
 * Represents a student in the academic program
 */
 class Student {
    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;
    private List<GradeRecord> gradeRecords;

    /**
     * Default constructor
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.gradeRecords = new ArrayList<>();
    }

    /**
     * Enrolls in a course
     * @param course the course to enroll in
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollCourse(Course course) {
        if (!enrolledCourses.contains(course) && course.getRemainingQuota() > 0) {
            enrolledCourses.add(course);
            course.getEnrolledStudents().add(this);
            return true;
        }
        return false;
    }

    /**
     * Drops a course
     * @param course the course to drop
     * @return true if drop was successful, false otherwise
     */
    public boolean dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.getEnrolledStudents().remove(this);
            removeGradeRecord(course);
            return true;
        }
        return false;
    }

    /**
     * Gets the grade for a specific course
     * @param course the course to get the grade for
     * @return the grade for the course, or null if no grade is recorded
     */
    public Double getCourseGrade(Course course) {
        for (GradeRecord record : gradeRecords) {
            if (record.getCourse().equals(course)) {
                return record.getGrade();
            }
        }
        return null;
    }

    /**
     * Sets or updates the grade for a course
     * @param course the course to set the grade for
     * @param grade the grade to set
     */
    public void setCourseGrade(Course course, double grade) {
        for (GradeRecord record : gradeRecords) {
            if (record.getCourse().equals(course)) {
                record.setGrade(grade);
                return;
            }
        }
        gradeRecords.add(new GradeRecord(course, grade));
    }

    private void removeGradeRecord(Course course) {
        gradeRecords.removeIf(record -> record.getCourse().equals(course));
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<GradeRecord> getGradeRecords() {
        return gradeRecords;
    }

    public void setGradeRecords(List<GradeRecord> gradeRecords) {
        this.gradeRecords = gradeRecords;
    }
}

/**
 * Represents a grade record for a student in a specific course
 */
 class GradeRecord {
    private Course course;
    private double grade;

    /**
     * Default constructor
     */
    public GradeRecord() {
    }

    /**
     * Constructor with course and grade
     * @param course the course
     * @param grade the grade
     */
    public GradeRecord(Course course, double grade) {
        this.course = course;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}