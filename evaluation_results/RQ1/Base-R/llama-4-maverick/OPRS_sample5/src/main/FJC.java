import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system, which can be an author, reviewer, or co-chair.
 */
class User {
    private String name;

    /**
     * Constructs a new User.
     */
    public User() {}

    /**
     * Gets the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author, extending the User class.
 */
class Author extends User {
    private List<Paper> papers;

    /**
     * Constructs a new Author.
     */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Gets the list of papers submitted by the author.
     * @return the list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the list of papers submitted by the author.
     * @param papers the list of papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Adds a paper to the list of papers submitted by the author.
     * @param paper the paper to add
     */
    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return the total number of papers
     */
    public int countPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getFinalDecision() != null && paper.getFinalDecision().equals("Accept")) {
                acceptedCount++;
            }
        }
        return (double) acceptedCount / papers.size();
    }
}

/**
 * Represents a reviewer, extending the User class.
 */
class Reviewer extends User {
    private List<Paper> assignedPapers;

    /**
     * Constructs a new Reviewer.
     */
    public Reviewer() {
        this.assignedPapers = new ArrayList<>();
    }

    /**
     * Gets the list of papers assigned to the reviewer.
     * @return the list of papers
     */
    public List<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    /**
     * Sets the list of papers assigned to the reviewer.
     * @param assignedPapers the list of papers to set
     */
    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    /**
     * Adds a paper to the list of papers assigned to the reviewer.
     * @param paper the paper to add
     */
    public void addAssignedPaper(Paper paper) {
        this.assignedPapers.add(paper);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (Paper paper : assignedPapers) {
            Review review = paper.getReviewForReviewer(this);
            if (review == null || review.getFeedback() == null) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Calculates the average score of the reviewer's historical grades.
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAverageScore() {
        int totalScore = 0;
        int reviewCount = 0;
        for (Paper paper : assignedPapers) {
            Review review = paper.getReviewForReviewer(this);
            if (review != null && review.getGrade() != null) {
                totalScore += review.getGrade().equals("Accept") ? 1 : 0;
                reviewCount++;
            }
        }
        return reviewCount > 0 ? (double) totalScore / reviewCount : 0.0;
    }
}

/**
 * Represents a paper with its title, type, and reviews.
 */
class Paper {
    private String title;
    private String type;
    private List<Review> reviews;
    private String finalDecision;

    /**
     * Constructs a new Paper.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the title of the paper.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the list of reviews for the paper.
     * @return the list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for the paper.
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to the paper.
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Gets the review for a specific reviewer.
     * @param reviewer the reviewer to get the review for
     * @return the review or null if not found
     */
    public Review getReviewForReviewer(Reviewer reviewer) {
        for (Review review : reviews) {
            if (review.getReviewer().equals(reviewer)) {
                return review;
            }
        }
        return null;
    }

    /**
     * Gets the final decision for the paper.
     * @return the final decision
     */
    public String getFinalDecision() {
        return finalDecision;
    }

    /**
     * Sets the final decision for the paper.
     * @param finalDecision the final decision to set
     */
    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject before the final decision is made.
     * @return true if all reviews are consistent, false otherwise
     */
    public boolean areReviewsConsistent() {
        if (finalDecision != null) {
            return true; // Final decision is already made
        }
        boolean hasAccept = false;
        boolean hasReject = false;
        for (Review review : reviews) {
            if (review.getGrade() != null) {
                if (review.getGrade().equals("Accept")) {
                    hasAccept = true;
                } else if (review.getGrade().equals("Reject")) {
                    hasReject = true;
                }
                if (hasAccept && hasReject) {
                    return false; // Both Accept and Reject are present
                }
            }
        }
        return true; // Either all Accept or all Reject, or no grades
    }
}

/**
 * Represents a review with its feedback and grade.
 */
class Review {
    private String feedback;
    private String grade;
    private Reviewer reviewer;

    /**
     * Constructs a new Review.
     */
    public Review() {}

    /**
     * Gets the feedback of the review.
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback of the review.
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade of the review.
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the review.
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Gets the reviewer who made this review.
     * @return the reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * Sets the reviewer who made this review.
     * @param reviewer the reviewer to set
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }
}