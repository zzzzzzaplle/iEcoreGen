import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;

    /**
     * Default constructor for User.
     */
    public User() {
        this.name = "";
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who can submit papers.
 */
class Author extends User {
    private List<Paper> submittedPapers;

    /**
     * Default constructor for Author.
     */
    public Author() {
        super();
        this.submittedPapers = new ArrayList<>();
    }

    /**
     * Gets the list of papers submitted by this author.
     *
     * @return list of submitted papers
     */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /**
     * Sets the list of papers submitted by this author.
     *
     * @param submittedPapers list of papers to set
     */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Adds a paper to the list of submitted papers.
     *
     * @param paper the paper to add
     */
    public void addPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return the total number of submitted papers
     */
    public int countSubmittedPapers() {
        return this.submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * If no papers submitted, returns 0.0.
     *
     * @return acceptance rate as a decimal between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        if (this.submittedPapers.isEmpty()) {
            return 0.0;
        }

        int acceptedCount = 0;
        for (Paper paper : this.submittedPapers) {
            if (paper.getFinalDecision() == Paper.Decision.ACCEPT) {
                acceptedCount++;
            }
        }

        return (double) acceptedCount / this.submittedPapers.size();
    }
}

/**
 * Represents a reviewer who can review papers.
 */
class Reviewer extends User {
    private List<Review> assignedReviews;

    /**
     * Default constructor for Reviewer.
     */
    public Reviewer() {
        super();
        this.assignedReviews = new ArrayList<>();
    }

    /**
     * Gets the list of reviews assigned to this reviewer.
     *
     * @return list of assigned reviews
     */
    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    /**
     * Sets the list of reviews assigned to this reviewer.
     *
     * @param assignedReviews list of reviews to set
     */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    /**
     * Adds a review to the list of assigned reviews.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.assignedReviews.add(review);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * Returns 0 if all reviews are submitted.
     *
     * @return number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (Review review : this.assignedReviews) {
            if (!review.isSubmitted()) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Converts this reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     * Returns 0.0 if no reviews submitted.
     *
     * @return average score between 0.0 and 1.0
     */
    public double calculateAverageReviewScore() {
        if (this.assignedReviews.isEmpty()) {
            return 0.0;
        }

        int totalScore = 0;
        int submittedCount = 0;

        for (Review review : this.assignedReviews) {
            if (review.isSubmitted()) {
                submittedCount++;
                if (review.getGrade() == Review.Grade.ACCEPT) {
                    totalScore += 1;
                }
                // Reject grade contributes 0, so we don't need to add anything
            }
        }

        if (submittedCount == 0) {
            return 0.0;
        }

        return (double) totalScore / submittedCount;
    }
}

/**
 * Represents a co-chair who can make final decisions on papers.
 */
class CoChair extends User {
    /**
     * Default constructor for CoChair.
     */
    public CoChair() {
        super();
    }
}

/**
 * Represents a paper submitted to the system.
 */
class Paper {
 enum Type {
        RESEARCH, EXPERIENCE_REPORT
    }

 enum Decision {
        ACCEPT, REJECT, PENDING
    }

    private String title;
    private Type type;
    private List<Review> reviews;
    private Decision finalDecision;
    private Author author;

    /**
     * Default constructor for Paper.
     */
    public Paper() {
        this.title = "";
        this.type = Type.RESEARCH;
        this.reviews = new ArrayList<>();
        this.finalDecision = Decision.PENDING;
        this.author = new Author();
    }

    /**
     * Gets the title of the paper.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the type of the paper.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     *
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets the list of reviews for this paper.
     *
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this paper.
     *
     * @param reviews list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to this paper.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Gets the final decision for this paper.
     *
     * @return the final decision
     */
    public Decision getFinalDecision() {
        return finalDecision;
    }

    /**
     * Sets the final decision for this paper.
     *
     * @param finalDecision the final decision to set
     */
    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Gets the author of this paper.
     *
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of this paper.
     *
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     * Returns false if there are no reviews or mixed grades.
     *
     * @return true if all reviews have the same grade (all Accept or all Reject), false otherwise
     */
    public boolean areAllReviewsConsistent() {
        if (this.reviews.isEmpty()) {
            return false;
        }

        Review.Grade firstGrade = null;
        for (Review review : this.reviews) {
            if (!review.isSubmitted()) {
                return false; // All reviews must be submitted
            }
            
            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } else if (review.getGrade() != firstGrade) {
                return false; // Mixed grades found
            }
        }
        
        return firstGrade != null;
    }
}

/**
 * Represents a review of a paper by a reviewer.
 */
class Review {
 enum Grade {
        ACCEPT, REJECT
    }

    private String feedback;
    private Grade grade;
    private boolean submitted;
    private Reviewer reviewer;
    private Paper paper;

    /**
     * Default constructor for Review.
     */
    public Review() {
        this.feedback = "";
        this.grade = Grade.REJECT;
        this.submitted = false;
        this.reviewer = new Reviewer();
        this.paper = new Paper();
    }

    /**
     * Gets the feedback for this review.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback for this review.
     *
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade for this review.
     *
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for this review.
     *
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * Checks if this review has been submitted.
     *
     * @return true if submitted, false otherwise
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Sets the submitted status of this review.
     *
     * @param submitted the submitted status to set
     */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Gets the reviewer for this review.
     *
     * @return the reviewer
     */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /**
     * Sets the reviewer for this review.
     *
     * @param reviewer the reviewer to set
     */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Gets the paper for this review.
     *
     * @return the paper
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * Sets the paper for this review.
     *
     * @param paper the paper to set
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}