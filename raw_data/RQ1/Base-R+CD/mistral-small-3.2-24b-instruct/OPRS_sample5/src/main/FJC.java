import java.util.ArrayList;
import java.util.List;

abstract class UserRole {
    // Empty abstract class for UserRole
}

class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Submits a paper for the author.
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return The number of papers submitted.
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return The acceptance rate as a double between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / papers.size();
    }

    /**
     * Gets the list of papers submitted by the author.
     * @return The list of papers.
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the list of papers for the author.
     * @param papers The list of papers.
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the list of review assignments for the reviewer.
     * @return The list of review assignments.
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Sets the list of review assignments for the reviewer.
     * @param assignments The list of review assignments.
     */
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return The number of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int unsubmitted = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmitted++;
            }
        }
        return unsubmitted;
    }

    /**
     * Calculates the acceptance proportion for the reviewer.
     * @return The acceptance proportion as a double between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / assignments.size();
    }
}

class CoChair extends UserRole {
    /**
     * Makes the final decision for a paper.
     * @param paper The paper to make a decision on.
     * @param decision The decision to make.
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
        paper.setDecision(decision);
    }
}

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
    }

    /**
     * Calculates the acceptance rate for the paper.
     * @return True if all reviews are exclusively Accept or exclusively Reject, false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        Grade firstGrade = reviews.get(0).getGrade();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the title of the paper.
     * @return The title of the paper.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     * @param title The title of the paper.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     * @return The type of the paper.
     */
    public PaperType getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * @param type The type of the paper.
     */
    public void setType(PaperType type) {
        this.type = type;
    }

    /**
     * Gets the decision of the paper.
     * @return The decision of the paper.
     */
    public Grade getDecision() {
        return decision;
    }

    /**
     * Sets the decision of the paper.
     * @param decision The decision of the paper.
     */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /**
     * Gets the list of reviews for the paper.
     * @return The list of reviews.
     */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for the paper.
     * @param reviews The list of reviews.
     */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }
}

class ReviewAssignment {
    private String feedback;
    private Grade grade;

    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    /**
     * Gets the feedback for the review assignment.
     * @return The feedback.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for the review assignment.
     * @param feedback The feedback.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for the review assignment.
     * @return The grade.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review assignment.
     * @param grade The grade.
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}

class User {
    private String name;
    private List<UserRole> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    /**
     * Adds a role to the user.
     * @param role The role to add.
     */
    public void addRole(UserRole role) {
        roles.add(role);
    }

    /**
     * Gets the name of the user.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name The name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the roles of the user.
     * @return The roles of the user.
     */
    public List<UserRole> getRoles() {
        return roles;
    }

    /**
     * Sets the roles of the user.
     * @param roles The roles of the user.
     */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}