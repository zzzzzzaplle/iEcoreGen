import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an instructor in the academic program
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
     * Adds a course to the instructor's course list
     * @param course the course to be added
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.addInstructor(this);
        }
    }

    /**
     * Removes a course from the instructor's course list
     * @param course the course to be removed
     */
    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.removeInstructor(this);
        }
    }

    /**
     * Increases the course quota by the specified amount
     * @param course the course whose quota will be increased
     * @param amount the amount to increase the quota by
     */
    public void increaseCourseQuota(Course course, int amount) {
        if (courses.contains(course)) {
            course.increaseQuota(amount);
        }
    }

    /**
     * Decreases the course quota by the specified amount
     * @param course the course whose quota will be decreased
     * @param amount the amount to decrease the quota by
     */
    public void decreaseCourseQuota(Course course, int amount) {
        if (courses.contains(course)) {
            course.decreaseQuota(amount);
        }
    }

    /**
     * Changes the weekly course hours for a course
     * @param course the course to modify
     * @param newStartTime the new start time
     * @param newEndTime the new end time
     */
    public void changeCourseHours(Course course, String newStartTime, String newEndTime) {
        if (courses.contains(course)) {
            course.setStartTime(newStartTime);
            course.setEndTime(newEndTime);
        }
    }

    /**
     * Changes the course days for a course
     * @param course the course to modify
     * @param newDays the new course days
     */
    public void changeCourseDays(Course course, List<String> newDays) {
        if (courses.contains(course)) {
            course.setDays(newDays);
        }
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

/**
 * Represents a classroom in the academic program
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

    // Getters and Setters
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
 * Represents a student in the academic program
 */
class Student {
    private String name;
    private String surname;
    private String studentId;
    private Map<Course, Double> courseGrades;

    /**
     * Default constructor
     */
    public Student() {
        this.courseGrades = new HashMap<>();
    }

    /**
     * Enrolls the student in a course
     * @param course the course to enroll in
     * @return true if enrollment was successful, false otherwise
     */
    public boolean enrollCourse(Course course) {
        if (course.addStudent(this)) {
            courseGrades.put(course, null); // Initialize with no grade
            return true;
        }
        return false;
    }

    /**
     * Drops the student from a course
     * @param course the course to drop
     * @return true if drop was successful, false otherwise
     */
    public boolean dropCourse(Course course) {
        if (course.removeStudent(this)) {
            courseGrades.remove(course);
            return true;
        }
        return false;
    }

    /**
     * Sets the grade for a course
     * @param course the course to set grade for
     * @param grade the grade to set
     */
    public void setCourseGrade(Course course, double grade) {
        if (courseGrades.containsKey(course)) {
            courseGrades.put(course, grade);
        }
    }

    /**
     * Gets the transcript of the student
     * @return a map of courses and their grades
     */
    public Map<Course, Double> getTranscript() {
        return new HashMap<>(courseGrades);
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Map<Course, Double> getCourseGrades() {
        return courseGrades;
    }

    public void setCourseGrades(Map<Course, Double> courseGrades) {
        this.courseGrades = courseGrades;
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
    private List<String> days;
    private Classroom classroom;
    private List<Instructor> instructors;
    private List<Student> students;
    private int quota;

    /**
     * Default constructor
     */
    public Course() {
        this.days = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Adds an instructor to the course
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
        }
    }

    /**
     * Removes an instructor from the course
     * @param instructor the instructor to remove
     */
    public void removeInstructor(Instructor instructor) {
        instructors.remove(instructor);
    }

    /**
     * Adds a student to the course if quota allows
     * @param student the student to add
     * @return true if student was added successfully, false otherwise
     */
    public boolean addStudent(Student student) {
        if (students.size() < quota && !students.contains(student)) {
            students.add(student);
            return true;
        }
        return false;
    }

    /**
     * Removes a student from the course
     * @param student the student to remove
     * @return true if student was removed successfully, false otherwise
     */
    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    /**
     * Increases the course quota
     * @param amount the amount to increase quota by
     */
    public void increaseQuota(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota
     * @param amount the amount to decrease quota by
     */
    public void decreaseQuota(int amount) {
        this.quota = Math.max(0, this.quota - amount);
    }

    /**
     * Gets the remaining quota for the course
     * @return the remaining quota
     */
    public int getRemainingQuota() {
        return quota - students.size();
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
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
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

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}

/**
 * Represents an academic program in the Software Engineering Department
 */
class AcademicProgram {
    private String programName;
    private List<Course> courses;

    /**
     * Default constructor
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the academic program
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    /**
     * Removes a course from the academic program
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    /**
     * Calculates the total capacity of all classrooms used in the Academic Program
     * @return the total capacity of all classrooms
     */
    public int calculateTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Course course : courses) {
            if (course.getClassroom() != null) {
                totalCapacity += course.getClassroom().getCapacity();
            }
        }
        return totalCapacity;
    }

    /**
     * Determines the total number of students enrolled in all courses of a specific Instructor
     * @param instructor the instructor whose courses will be checked
     * @return the total number of students in the instructor's courses
     */
    public int getNumberOfStudentsInInstructorCourses(Instructor instructor) {
        int totalStudents = 0;
        for (Course course : instructor.getCourses()) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the average grade for the students enrolled in a specific course within the Academic Program
     * @param course the course to calculate average grade for
     * @return the average grade of students in the course, or 0.0 if no students have grades
     */
    public double calculateAverageStudentGradeInCourse(Course course) {
        if (!courses.contains(course)) {
            return 0.0;
        }

        double totalGrade = 0.0;
        int count = 0;
        
        for (Student student : course.getStudents()) {
            Double grade = student.getCourseGrades().get(course);
            if (grade != null) {
                totalGrade += grade;
                count++;
            }
        }
        
        return count > 0 ? totalGrade / count : 0.0;
    }

    /**
     * Finds the total number of courses in the Academic Program that are taught on a given day
     * @param day the day to check (e.g., "Monday", "Tuesday", etc.)
     * @return the number of courses on the specified day
     */
    public int getCoursesOnSpecificDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the remaining quota for all courses of a specific Instructor
     * @param instructor the instructor whose courses will be checked
     * @return the total remaining quota across all of the instructor's courses
     */
    public int calculateRemainingQuotaInInstructorCourses(Instructor instructor) {
        int totalRemainingQuota = 0;
        for (Course course : instructor.getCourses()) {
            totalRemainingQuota += course.getRemainingQuota();
        }
        return totalRemainingQuota;
    }

    // Getters and Setters
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}