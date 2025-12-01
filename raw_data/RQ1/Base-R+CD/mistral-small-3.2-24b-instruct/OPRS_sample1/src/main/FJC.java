import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private List<UserRole> roles = new ArrayList<>();

    /**
     * Constructs a new User with the given name.
     * @param name The name of the user.
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Adds a role to the user.
     * @param role The role to be added.
     */
    public void addRole(UserRole role) {
        roles.add(role);
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}

abstract class UserRole {
    // Common properties and methods for all user roles can be added here
}

class Author extends UserRole {
    private List<Paper> papers = new ArrayList<>();

    /**
     * Submits a paper for the author.
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return The number of submitted papers.
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

        int acceptedPapers = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                acceptedPapers++;
            }
        }

        return (double) acceptedPapers / papers.size();
    }

    // Getter and Setter methods
    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments = new ArrayList<>();

    /**
     * Gets the list of review assignments for the reviewer.
     * @return The list of review assignments.
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
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
     * Calculates the acceptance proportion of the reviewer.
     * @return The acceptance proportion as a double between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int total = assignments.size();
        int accept = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                accept++;
            }
        }

        return (double) accept / total;
    }

    // Getter and Setter methods
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }
}

class CoChair extends UserRole {
    /**
     * Makes the final decision for a paper.
     * @param paper The paper to make a decision for.
     * @param decision The decision to be made.
     * @throws IllegalArgumentException if the decision is not valid.
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            throw new IllegalArgumentException("Decision cannot be UNDECIDED");
        }
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
    private Grade decision = Grade.UNDECIDED;
    private List<ReviewAssignment> reviews = new ArrayList<>();

    /**
     * Constructs a new Paper with the given title and type.
     * @param title The title of the paper.
     * @param type The type of the paper.
     */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return true if all reviews are either exclusively Accept or exclusively Reject, false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }

        boolean isFirstAccept = (reviews.get(0).getGrade() == Grade.ACCEPT);
        for (ReviewAssignment review : reviews) {
            if (isFirstAccept && review.getGrade() == Grade.REJECT) {
                return false;
            }
            if (!isFirstAccept && review.getGrade() == Grade.ACCEPT) {
                return false;
            }
        }
        return true;
    }

    // Getter and Setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public Grade getDecision() {
        return decision;
    }

    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }
}

class ReviewAssignment {
    private String feedback;
    private Grade grade = Grade.UNDECIDED;

    /**
     * Constructs a new ReviewAssignment with the given feedback.
     * @param feedback The feedback for the review.
     */
    public ReviewAssignment(String feedback) {
        this.feedback = feedback;
    }

    // Getter and Setter methods
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}