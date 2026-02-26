import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Enum representing the possible grades a reviewer can give.
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Enum representing the type of a paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/**
 * Base class for all users of the review system.
 */
class User {
    private String name;

    /** Unparameterized constructor. */
    public User() {
        // empty
    }

    /** Parameterized constructor for convenience. */
    public User(String name) {
        this.name = name;
    }

    /** @return the name of the user */
    public String getName() {
        return name;
    }

    /** @param name the name to set for the user */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who can submit papers.
 */
class Author extends User {
    private List<Paper> submittedPapers;

    /** Unparameterized constructor. */
    public Author() {
        this.submittedPapers = new ArrayList<>();
    }

    /** @return an unmodifiable view of the papers submitted by the author */
    public List<Paper> getSubmittedPapers() {
        return Collections.unmodifiableList(submittedPapers);
    }

    /** @param submittedPapers the list of papers to set (usually not used directly) */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Adds a new paper to the author's collection.
     *
     * @param paper the paper to add
     */
    public void submitPaper(Paper paper) {
        Objects.requireNonNull(paper, "paper cannot be null");
        submittedPapers.add(paper);
        paper.setAuthor(this);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return the number of submitted papers
     */
    public int getPaperCount() {
        return submittedPapers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     * Papers without a final decision are ignored.
     *
     * @return acceptance rate as a value between 0.0 and 1.0.
     *         Returns 0.0 if the author has no papers with a final decision.
     */
    public double getAcceptanceRate() {
        int decided = 0;
        int accepted = 0;
        for (Paper p : submittedPapers) {
            if (p.getFinalDecision() != null) {
                decided++;
                if (p.getFinalDecision() == Grade.ACCEPT) {
                    accepted++;
                }
            }
        }
        if (decided == 0) {
            return 0.0;
        }
        return (double) accepted / decided;
    }
}

/**
 * Represents a reviewer who can be assigned reviews and submit them.
 */
class Reviewer extends User {
    private List<Review> assignedReviews;

    /** Unparameterized constructor. */
    public Reviewer() {
        this.assignedReviews = new ArrayList<>();
    }

    /** @return an unmodifiable view of the reviews assigned to this reviewer */
    public List<Review> getAssignedReviews() {
        return Collections.unmodifiableList(assignedReviews);
    }

    /** @param assignedReviews the list of reviews to set (usually not used directly) */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    /**
     * Assigns a new review to this reviewer.
     *
     * @param review the review to assign
     */
    public void assignReview(Review review) {
        Objects.requireNonNull(review, "review cannot be null");
        assignedReviews.add(review);
    }

    /**
     * Calculates the number of reviews that are still pending submission.
     *
     * @return count of unsubmitted reviews (0 if all have been submitted)
     */
    public int getUnsubmittedReviewCount() {
        int count = 0;
        for (Review r : assignedReviews) {
            if (!r.isSubmitted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the historical average score of this reviewer.
     * Accept = 1, Reject = 0.
     *
     * @return average score between 0.0 and 1.0; returns 0.0 if reviewer has no submitted reviews.
     */
    public double getHistoricalAverageScore() {
        int submitted = 0;
        int sum = 0;
        for (Review r : assignedReviews) {
            if (r.isSubmitted()) {
                submitted++;
                sum += (r.getGrade() == Grade.ACCEPT) ? 1 : 0;
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) sum / submitted;
    }
}

/**
 * Represents the program chair who can make final decisions on papers.
 */
class Chair extends User {
    /** Unparameterized constructor. */
    public Chair() {
        // empty
    }

    /**
     * Determines whether a final decision can be made for a given paper.
     * The decision can be made only if all submitted reviews are either all
     * ACCEPT or all REJECT.
     *
     * @param paper the paper to evaluate
     * @return {@code true} if a consensus (all same grade) exists, {@code false} otherwise
     */
    public boolean canMakeFinalDecision(Paper paper) {
        Objects.requireNonNull(paper, "paper cannot be null");
        return paper.isConsensusReached();
    }

    /**
     * Sets the final decision for a paper. This method should be called only
     * after {@link #canMakeFinalDecision(Paper)} returns {@code true}.
     *
     * @param paper the paper to set the decision for
     * @param decision the final decision (ACCEPT or REJECT)
     */
    public void setFinalDecision(Paper paper, Grade decision) {
        Objects.requireNonNull(paper, "paper cannot be null");
        Objects.requireNonNull(decision, "decision cannot be null");
        paper.setFinalDecision(decision);
    }
}

/**
 * Represents a paper submitted to the conference.
 */
class Paper {
    private String title;
    private PaperType type;
    private Author author;
    private List<Review> reviews;
    private Grade finalDecision; // null if not decided yet

    /** Unparameterized constructor. */
    public Paper() {
        this.reviews = new ArrayList<>();
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

    /** @return the author of the paper */
    public Author getAuthor() {
        return author;
    }

    /** @param author the author to set */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /** @return an unmodifiable view of the reviews for this paper */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** @param reviews the list of reviews to set (usually not used directly) */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /** @return the final decision for this paper (may be {@code null}) */
    public Grade getFinalDecision() {
        return finalDecision;
    }

    /** @param finalDecision the final decision to set */
    public void setFinalDecision(Grade finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Adds a review to the paper. The review is also linked back to the reviewer.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        Objects.requireNonNull(review, "review cannot be null");
        reviews.add(review);
        review.setPaper(this);
    }

    /**
     * Checks whether all submitted reviews for this paper have the same grade.
     * Returns {@code false} if there are no submitted reviews.
     *
     * @return {@code true} if consensus (all Accept or all Reject) is reached,
     *         {@code false} otherwise.
     */
    public boolean isConsensusReached() {
        Grade consensus = null;
        boolean anySubmitted = false;
        for (Review r : reviews) {
            if (r.isSubmitted()) {
                anySubmitted = true;
                if (consensus == null) {
                    consensus = r.getGrade();
                } else if (consensus != r.getGrade()) {
                    return false; // mixed grades
                }
            }
        }
        return anySubmitted; // true only if at least one submitted review and all same
    }
}

/**
 * Represents a review written by a reviewer for a specific paper.
 */
class Review {
    private Reviewer reviewer;
    private Paper paper;
    private String feedback;
    private Grade grade;
    private boolean submitted;

    /** Unparameterized constructor. */
    public Review() {
        this.submitted = false;
    }

    /** @return the reviewer who wrote this review */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /** @param reviewer the reviewer to set */
    public void setReviewer(Reviewer reviewer) {
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

    /** @param submitted set to {@code true} when the review is submitted */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Submits the review with the provided feedback and grade.
     *
     * @param feedback the textual feedback
     * @param grade the grade (ACCEPT or REJECT)
     * @throws IllegalStateException if the review has already been submitted
     */
    public void submit(String feedback, Grade grade) {
        if (this.submitted) {
            throw new IllegalStateException("Review has already been submitted.");
        }
        this.feedback = feedback;
        this.grade = grade;
        this.submitted = true;
    }
}