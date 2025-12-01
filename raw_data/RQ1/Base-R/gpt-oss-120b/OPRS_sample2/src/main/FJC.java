import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Enumerates the two possible types of a paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/**
 * Enumerates the possible grades a reviewer can give.
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Enumerates the possible final decisions a chair can make for a paper.
 */
enum Decision {
    ACCEPTED,
    REJECTED,
    PENDING
}

/**
 * Base class for all users of the review system.
 */
class User {
    private String name;

    /** Unparameterized constructor required by the specification. */
    public User() {
    }

    /** Parameterized constructor for convenience. */
    public User(String name) {
        this.name = name;
    }

    /** @return the name of the user. */
    public String getName() {
        return name;
    }

    /** @param name the name to set for the user. */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who can submit many papers.
 */
class Author extends User {
    private List<Paper> submittedPapers = new ArrayList<>();

    /** Unparameterized constructor required by the specification. */
    public Author() {
        super();
    }

    /** @return the list of papers submitted by this author. */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /** @param submittedPapers the list of papers to set for this author. */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Adds a paper to the author's submission list.
     *
     * @param paper the paper to add
     */
    public void addPaper(Paper paper) {
        if (paper != null) {
            submittedPapers.add(paper);
        }
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return the number of submitted papers (always >= 0)
     */
    public int countSubmittedPapers() {
        return submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of this author's papers.
     * Acceptance rate = (# of accepted papers) / (total submitted papers).
     *
     * @return a value between 0.0 and 1.0; returns 0.0 if the author has no papers.
     */
    public double acceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        long accepted = submittedPapers.stream()
                .filter(p -> p.getFinalDecision() == Decision.ACCEPTED)
                .count();
        return (double) accepted / submittedPapers.size();
    }
}

/**
 * Represents a reviewer who is assigned papers to review.
 */
class Reviewer extends User {
    private List<Review> assignedReviews = new ArrayList<>();

    /** Unparameterized constructor required by the specification. */
    public Reviewer() {
        super();
    }

    /** @return the list of reviews assigned to this reviewer. */
    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    /** @param assignedReviews the list of reviews to set for this reviewer. */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    /**
     * Assigns a new review to the reviewer.
     *
     * @param review the review to assign
     */
    public void assignReview(Review review) {
        if (review != null) {
            assignedReviews.add(review);
        }
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * An unsubmitted review is one where {@code Review.isSubmitted()} is false.
     *
     * @return the count of unsubmitted reviews (0 if all are submitted)
     */
    public int countUnsubmittedReviews() {
        int count = 0;
        for (Review r : assignedReviews) {
            if (!r.isSubmitted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Converts the reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0. The average is computed over all submitted reviews.
     *
     * @return a value between 0.0 and 1.0; returns 0.0 if no reviews have been submitted.
     */
    public double historicalAverageScore() {
        int total = 0;
        int submittedCount = 0;
        for (Review r : assignedReviews) {
            if (r.isSubmitted()) {
                submittedCount++;
                total += (r.getGrade() == Grade.ACCEPT) ? 1 : 0;
            }
        }
        if (submittedCount == 0) {
            return 0.0;
        }
        return (double) total / submittedCount;
    }
}

/**
 * Represents the program chair (the final decision maker).
 */
class Chair extends User {
    /** Unparameterized constructor required by the specification. */
    public Chair() {
        super();
    }

    /**
     * Makes a final decision for a paper if all its reviews are uniform
     * (either all Accept or all Reject). The decision is stored in the paper.
     *
     * @param paper the paper for which to make a decision
     * @throws IllegalStateException if the paper's reviews are not uniform
     */
    public void makeFinalDecision(Paper paper) {
        Objects.requireNonNull(paper, "Paper cannot be null");
        if (!paper.areReviewsUniform()) {
            throw new IllegalStateException("Cannot decide: reviews are not uniform.");
        }
        // All grades are the same, decide accordingly
        Grade uniformGrade = paper.getReviews().get(0).getGrade();
        paper.setFinalDecision(uniformGrade == Grade.ACCEPT ? Decision.ACCEPTED : Decision.REJECTED);
    }
}

/**
 * Represents a paper submitted to the conference.
 */
class Paper {
    private String title;
    private PaperType type;
    private List<Review> reviews = new ArrayList<>();
    private Decision finalDecision = Decision.PENDING;

    /** Unparameterized constructor required by the specification. */
    public Paper() {
    }

    /** @param title the title of the paper */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /** @return the title of the paper */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the type of the paper */
    public PaperType getType() {
        return type;
    }

    /** @param type the type to set */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** @return an unmodifiable view of the reviews for this paper */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** @param reviews the list of reviews to set (used rarely) */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /** @return the final decision made by the chair */
    public Decision getFinalDecision() {
        return finalDecision;
    }

    /** @param finalDecision the final decision to set */
    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Adds a review to this paper. The review is also linked back to the paper.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
            review.setPaper(this);
        }
    }

    /**
     * Checks if all submitted reviews for this paper are either exclusively
     * Accept or exclusively Reject.
     *
     * @return {@code true} if all submitted reviews share the same grade,
     *         {@code false} otherwise (including the case of no submitted reviews)
     */
    public boolean areReviewsUniform() {
        Grade uniformGrade = null;
        for (Review r : reviews) {
            if (!r.isSubmitted()) {
                // If any review is not yet submitted, uniformity cannot be guaranteed
                return false;
            }
            if (uniformGrade == null) {
                uniformGrade = r.getGrade();
            } else if (r.getGrade() != uniformGrade) {
                return false;
            }
        }
        // If there are no reviews at all, we treat it as non‑uniform
        return uniformGrade != null;
    }
}

/**
 * Represents a single review written by a reviewer for a paper.
 */
class Review {
    private Paper paper;
    private Reviewer reviewer;
    private String feedback;
    private Grade grade;
    private boolean submitted = false;

    /** Unparameterized constructor required by the specification. */
    public Review() {
    }

    /** @param reviewer the reviewer who will write this review */
    public Review(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /** @return the paper being reviewed */
    public Paper getPaper() {
        return paper;
    }

    /** @param paper the paper to set */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /** @return the reviewer who authored this review */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /** @param reviewer the reviewer to set */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /** @return the textual feedback */
    public String getFeedback() {
        return feedback;
    }

    /** @param feedback the feedback to set */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** @return the grade given by the reviewer */
    public Grade getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /** @return {@code true} if the review has been submitted */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Submits the review. The method records feedback and grade and marks the
     * review as submitted.
     *
     * @param feedback textual feedback for the paper
     * @param grade    the grade (Accept or Reject)
     * @throws IllegalStateException if the review was already submitted
     * @throws NullPointerException  if feedback or grade is {@code null}
     */
    public void submit(String feedback, Grade grade) {
        if (this.submitted) {
            throw new IllegalStateException("Review has already been submitted.");
        }
        Objects.requireNonNull(feedback, "Feedback cannot be null");
        Objects.requireNonNull(grade, "Grade cannot be null");
        this.feedback = feedback;
        this.grade = grade;
        this.submitted = true;
    }
}