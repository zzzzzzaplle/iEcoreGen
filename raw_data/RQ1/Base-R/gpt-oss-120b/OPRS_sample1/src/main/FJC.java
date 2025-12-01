import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of possible paper types.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/**
 * Enumeration of possible grades a reviewer can give.
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Enumeration of the final decision taken by the chair.
 */
enum Decision {
    ACCEPTED,
    REJECTED,
    PENDING
}

/**
 * Base class for all users of the system.
 */
class User {
    private String name;

    /** No‑argument constructor required by the specification. */
    public User() {
    }

    /** Getter for {@code name}. */
    public String getName() {
        return name;
    }

    /** Setter for {@code name}. */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author who can submit papers.
 */
class Author extends User {
    private List<Paper> submittedPapers = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Author() {
    }

    /** Getter for the list of submitted papers. */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /** Setter for the list of submitted papers. */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Adds a paper to the author's collection.
     *
     * @param paper the paper to add
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            submittedPapers.add(paper);
        }
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
     * Acceptance rate = (number of accepted papers) / (total submitted papers).
     *
     * @return a value between 0.0 and 1.0; returns 0.0 if the author has not submitted any papers
     */
    public double getAcceptanceRate() {
        if (submittedPapers.isEmpty()) {
            return 0.0;
        }
        long accepted = submittedPapers.stream()
                .filter(p -> p.getDecision() == Decision.ACCEPTED)
                .count();
        return (double) accepted / submittedPapers.size();
    }
}

/**
 * Represents a reviewer who can be assigned papers and submit reviews.
 */
class Reviewer extends User {
    private List<Paper> assignedPapers = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Reviewer() {
    }

    /** Getter for assigned papers. */
    public List<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    /** Setter for assigned papers. */
    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    /** Getter for reviews written (or to be written) by the reviewer. */
    public List<Review> getReviews() {
        return reviews;
    }

    /** Setter for reviews. */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Assigns a paper to this reviewer.
     *
     * @param paper the paper to assign
     */
    public void assignPaper(Paper paper) {
        if (paper != null && !assignedPapers.contains(paper)) {
            assignedPapers.add(paper);
            Review review = new Review();
            review.setReviewer(this);
            review.setPaper(paper);
            reviews.add(review);
            paper.addReview(review);
        }
    }

    /**
     * Calculates the number of reviews that have not yet been submitted by this reviewer.
     *
     * @return the count of unsubmitted reviews (0 if all are submitted)
     */
    public int getUnsubmittedReviewCount() {
        int count = 0;
        for (Review r : reviews) {
            if (!r.isSubmitted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the historical average grade of the reviewer.
     * Accept = 1, Reject = 0. The average is between 0.0 and 1.0.
     *
     * @return the average grade; returns 0.0 if the reviewer has no submitted reviews
     */
    public double getHistoricalGradeAverage() {
        int submitted = 0;
        int acceptCount = 0;
        for (Review r : reviews) {
            if (r.isSubmitted()) {
                submitted++;
                if (r.getGrade() == Grade.ACCEPT) {
                    acceptCount++;
                }
            }
        }
        return submitted == 0 ? 0.0 : (double) acceptCount / submitted;
    }
}

/**
 * Represents the program chair who makes final decisions.
 */
class Chair extends User {
    /** No‑argument constructor required by the specification. */
    public Chair() {
    }

    /**
     * Checks whether a paper's reviews are uniform, i.e., all are Accept or all are Reject.
     *
     * @param paper the paper to examine
     * @return {@code true} if all submitted reviews have the same grade and at least one review exists;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code paper} is {@code null}
     */
    public boolean canMakeFinalDecision(Paper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper must not be null");
        }
        return paper.areReviewsUniform();
    }

    /**
     * Sets the final decision for a paper after confirming uniformity of reviews.
     *
     * @param paper    the paper whose decision is to be set
     * @param decision the decision to apply
     * @throws IllegalStateException    if the reviews are not uniform
     * @throws IllegalArgumentException if {@code paper} or {@code decision} is {@code null}
     */
    public void makeFinalDecision(Paper paper, Decision decision) {
        if (paper == null || decision == null) {
            throw new IllegalArgumentException("Paper and decision must not be null");
        }
        if (!paper.areReviewsUniform()) {
            throw new IllegalStateException("Cannot make decision: reviews are not uniform");
        }
        paper.setDecision(decision);
    }
}

/**
 * Represents a paper submitted to the conference.
 */
class Paper {
    private String title;
    private PaperType type;
    private List<Review> reviews = new ArrayList<>();
    private Decision decision = Decision.PENDING;

    /** No‑argument constructor required by the specification. */
    public Paper() {
    }

    /** Getter for {@code title}. */
    public String getTitle() {
        return title;
    }

    /** Setter for {@code title}. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for {@code type}. */
    public PaperType getType() {
        return type;
    }

    /** Setter for {@code type}. */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** Getter for the list of reviews. */
    public List<Review> getReviews() {
        return reviews;
    }

    /** Setter for the list of reviews. */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /** Getter for the final decision. */
    public Decision getDecision() {
        return decision;
    }

    /** Setter for the final decision. */
    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    /**
     * Adds a review object to this paper.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        if (review != null && !reviews.contains(review)) {
            reviews.add(review);
        }
    }

    /**
     * Determines whether all submitted reviews for this paper are exclusively Accept
     * or exclusively Reject.
     *
     * @return {@code true} if all submitted reviews share the same grade and at least three
     *         reviews have been submitted; {@code false} otherwise
     */
    public boolean areReviewsUniform() {
        // At least three reviews must be present according to the domain description.
        List<Review> submitted = new ArrayList<>();
        for (Review r : reviews) {
            if (r.isSubmitted()) {
                submitted.add(r);
            }
        }
        if (submitted.size() < 3) {
            return false;
        }
        Grade firstGrade = submitted.get(0).getGrade();
        for (Review r : submitted) {
            if (r.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review written by a reviewer for a paper.
 */
class Review {
    private Reviewer reviewer;
    private Paper paper;
    private String feedback;
    private Grade grade;
    private boolean submitted = false;

    /** No‑argument constructor required by the specification. */
    public Review() {
    }

    /** Getter for {@code reviewer}. */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /** Setter for {@code reviewer}. */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /** Getter for {@code paper}. */
    public Paper getPaper() {
        return paper;
    }

    /** Setter for {@code paper}. */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /** Getter for {@code feedback}. */
    public String getFeedback() {
        return feedback;
    }

    /** Setter for {@code feedback}. */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** Getter for {@code grade}. */
    public Grade getGrade() {
        return grade;
    }

    /** Setter for {@code grade}. */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /** Getter for {@code submitted}. */
    public boolean isSubmitted() {
        return submitted;
    }

    /** Setter for {@code submitted}. */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Submits the review with the given feedback and grade.
     *
     * @param feedback the textual feedback for the paper (must not be {@code null})
     * @param grade    the grade (ACCEPT or REJECT) (must not be {@code null})
     * @throws IllegalArgumentException if {@code feedback} or {@code grade} is {@code null}
     */
    public void submit(String feedback, Grade grade) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null");
        }
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null");
        }
        this.feedback = feedback;
        this.grade = grade;
        this.submitted = true;
    }
}