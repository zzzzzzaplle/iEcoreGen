### CR 1: "Total classroom capacity in an academic program"

```
Computational requirement: "Total classroom capacity in an academic program": Calculate the total capacity of all classrooms used in an Academic Program.

+ Test Case 1: "Single Classroom in Academic Program" 
    Input: "An academic program with a single course using a classroom with a capacity of 30"
    Expected Output: "The total classroom capacity in the academic program is 30"
---
+ Test Case 2: "Multiple Classrooms with Different Capacities" 
    Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
    Expected Output: "The total classroom capacity in the academic program is 100"
---
+ Test Case 3: "Two Identical Classrooms" 
    Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
    Expected Output: "The total classroom capacity in the academic program is 100"
---
+ Test Case 4: "Empty Academic Program" 
    Input: "An academic program with no courses (and thus no classrooms used)"
    Expected Output: "The total classroom capacity in the academic program is 0"
---
+ Test Case 5: "Large Number of Classrooms" 
    Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
    Expected Output: "The total classroom capacity in the academic program is 130"
```


***


### CR 2: "Number of students in an instructor's courses"


```
Computational requirement: "Number of students in an instructor's courses": Determine the total number of students enrolled in all courses of a specific Instructor.


+ Test Case 1: "Single course, single student" 
    Input: "An Instructor with one course. That course has one enrolled student."
    Expected Output: "The total number of students in the instructor's courses should be 1."
---
+ Test Case 2: "Single course, multiple students" 
    Input: "An Instructor with one course. That course has 25 enrolled students."
    Expected Output: "The total number of students in the instructor's courses should be 25."
---
+ Test Case 3: "Multiple courses, single student per course" 
    Input: "An Instructor with 3 courses. Each course has one enrolled student."
    Expected Output: "The total number of students in the instructor's courses should be 3."
---
+ Test Case 4: "Multiple courses, varying students per course" 
    Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
    Expected Output: "The total number of students in the instructor's courses should be 50."
---
+ Test Case 5: "No courses" 
    Input: "An Instructor with no courses assigned."
    Expected Output: "The total number of students in the instructor's courses should be 0."
```


***


### CR 3: "Average student grade in a course of an academic program"

```
Computational requirement: "Average student grade in a course of an academic program": Calculate the average grade of students enrolled in a specific Course within an Academic Program.


+ Test Case 1: "Average grade calculation for students in a specific course" 
    Input: "An AcademicProgram with a specific Course having a list of enrolled Students with different grades."
    Expected Output: "The correct average grade of all the students in that specific Course within the Academic Program."
---
+ Test Case 2: "Average grade calculation for a single - student course" 
    Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
    Expected Output: "The grade of the single enrolled student, as it represents the average grade."
---
+ Test Case 3: "Average grade calculation for a course with no students" 
    Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
    Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
---
+ Test Case 4: "Average grade calculation with all same grades" 
    Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
    Expected Output: "The common grade value as the average grade."
---
+ Test Case 5: "Average grade calculation with a mix of valid and zero grades" 
    Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
    Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
```


***


### CR 4: "Courses on a specific day in an academic program"

```
Computational requirement:  "Courses on a specific day in an academic program": Find the total number of courses in an Academic Program that are taught on a given day. Return the number of courses on the specified day.

+ Test Case 1: "Single Course on Given Day" 
    Input: "Academic Program with one course that occurs on the given day, and the given specific day."
    Expected Output: "The total number of courses returned should be 1 as there is only one course on that day."
---
+ Test Case 2: "No Courses on Given Day" 
    Input: "Academic Program with multiple courses, none of which occur on the given day, and the given specific day."
    Expected Output: "The total number of courses returned should be 0 since no courses are taught on that day."
---
+ Test Case 3: "Multiple Courses on Given Day" 
    Input: "Academic Program with five courses, all of which occur on the given day, and the given specific day."
    Expected Output: "The total number of courses returned should be 5 as all five courses are taught on that day."
---
+ Test Case 4: "Mixed Courses on Given Day" 
    Input: "Academic Program with eight courses, out of which three occur on the given day, and the given specific day."
    Expected Output: "The total number of courses returned should be 3 as only three courses are taught on that day."
---
+ Test Case 5: "Empty Academic Program" 
    Input: "An empty Academic Program and a given specific day."
    Expected Output: "The total number of courses returned should be 0 as there are no courses in the academic program."
```


***


### CR 5: "Remaining quota in an instructor's courses"


```
Computational requirement: "Remaining quota in an instructor's courses": Calculate the remaining quota for all courses of a specific Instructor. 

+ Test Case 1: "SingleCourseWithSingleQuotaIncrease" 
    Input: "An Instructor object with a single course having an initial quota of 20. The instructor increases the quota of this course by 5."
    Expected Output: "The remaining quota of the instructor's single course should be 25."
---
+ Test Case 2: "SingleCourseWithSingleQuotaDecrease" 
    Input: "An Instructor object with a single course having an initial quota of 30. The instructor decreases the quota of this course by 8."
    Expected Output: "The remaining quota of the instructor's single course should be 22."
---
+ Test Case 3: "MultipleCoursesWithNoQuotaChanges" 
    Input: "An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively. There are no quota changes made."
    Expected Output: "The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75."
---
+ Test Case 4: "MultipleCoursesWithQuotaIncreaseOnOneCourse" 
    Input: "An Instructor object with two courses having initial quotas of 20 and 25. The instructor increases the quota of the first course by 10."
    Expected Output: "The remaining quotas of the instructor's courses should be 30 + 25 = 55."
---
+ Test Case 5: "MultipleCoursesWithQuotaIncreaseAndDecrease" 
    Input: "An Instructor object with three courses having initial quotas of 18, 22, and 28. The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4."
    Expected Output: "The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70."
```

***
