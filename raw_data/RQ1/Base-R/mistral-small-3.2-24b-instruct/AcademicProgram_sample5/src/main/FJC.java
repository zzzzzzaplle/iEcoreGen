import java.util.ArrayList;
import java.util.List;

class AcademicProgram {
    private List<Course> courses;
    private List<Classroom> classrooms;
    private List<Instructor> instructors;

    /**
     * Default constructor for AcademicProgram.
     * Initializes empty lists for courses, classrooms, and instructors.
     */
    public AcademicProgram() {
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
        this.instructors = new ArrayList<>();
    }

    /**
     * Adds a course to the academic program.
     * @param course The course to be added.
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Adds a classroom to the academic program.
     * @param classroom The classroom to be added.
     */
    public void addClassroom(Classroom classroom) {
        this.classrooms.add(classroom);
    }

    /**
     * Adds an instructor to the academic program.
     * @param instructor The instructor to be added.
     */
    public void addInstructor(Instructor instructor) {
        this.instructors.add(instructor);
    }

    /**
     * Calculates the total capacity of all classrooms used in the academic program.
     * @return The total classroom capacity.
     */
    public int calculateTotalClassroomCapacity() {
        int totalCapacity = 0;
        for (Classroom classroom : classrooms) {
            totalCapacity += classroom.getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Finds the total number of courses in the academic program that are taught on a given day.
     * @param day The day to check for courses.
     * @return The number of courses on the specified day.
     */
    public int getNumberOfCoursesOnDay(String day) {
        int count = 0;
        for (Course course : courses) {
            if (course.getDays().contains(day)) {
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
}

class Course {
    private String name;
    private String code;
    private String startTime;
    private String endTime;
    private List<String> days;
    private Classroom classroom;
    private Instructor instructor;
    private int quota;
    private List<Student> students;

    /**
     * Default constructor for Course.
     * Initializes empty lists for days and students.
     */
    public Course() {
        this.days = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * Adds a day to the course schedule.
     * @param day The day to be added.
     */
    public void addDay(String day) {
        this.days.add(day);
    }

    /**
     * Adds a student to the course.
     * @param student The student to be added.
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Removes a student from the course.
     * @param student The student to be removed.
     */
    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    /**
     * Increases the course quota by the specified amount.
     * @param amount The amount to increase the quota by.
     */
    public void increaseQuota(int amount) {
        this.quota += amount;
    }

    /**
     * Decreases the course quota by the specified amount.
     * @param amount The amount to decrease the quota by.
     */
    public void decreaseQuota(int amount) {
        this.quota -= amount;
    }

    /**
     * Calculates the average grade for the students enrolled in the course.
     * @return The average grade.
     */
    public double calculateAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Student student : students) {
            sum += student.getGrade();
        }
        return sum / students.size();
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

class Classroom {
    private String id;
    private int capacity;
    private int floor;
    private String block;

    /**
     * Default constructor for Classroom.
     */
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

class Instructor {
    private String name;
    private String surname;
    private String title;
    private String specialty;
    private List<Course> courses;

    /**
     * Default constructor for Instructor.
     * Initializes an empty list for courses.
     */
    public Instructor() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the instructor's list.
     * @param course The course to be added.
     */
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    /**
     * Removes a course from the instructor's list.
     * @param course The course to be removed.
     */
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    /**
     * Determines the total number of students enrolled in all courses of the instructor.
     * @return The total number of students.
     */
    public int getTotalNumberOfStudents() {
        int totalStudents = 0;
        for (Course course : courses) {
            totalStudents += course.getStudents().size();
        }
        return totalStudents;
    }

    /**
     * Calculates the remaining quota for all courses of the instructor.
     * @return The remaining quota.
     */
    public int calculateRemainingQuota() {
        int remainingQuota = 0;
        for (Course course : courses) {
            remainingQuota += course.getQuota() - course.getStudents().size();
        }
        return remainingQuota;
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
    private String name;
    private String surname;
    private String studentId;
    private List<Course> enrolledCourses;
    private List<Double> grades;

    /**
     * Default constructor for Student.
     * Initializes empty lists for enrolledCourses and grades.
     */
    public Student() {
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    /**
     * Enrolls the student in a course.
     * @param course The course to enroll in.
     */
    public void enrollInCourse(Course course) {
        this.enrolledCourses.add(course);
    }

    /**
     * Drops the student from a course.
     * @param course The course to drop.
     */
    public void dropCourse(Course course) {
        this.enrolledCourses.remove(course);
    }

    /**
     * Adds a grade for a course.
     * @param grade The grade to add.
     */
    public void addGrade(double grade) {
        this.grades.add(grade);
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

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    public double getGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.get(grades.size() - 1);
    }
}