import java.util.ArrayList;
import java.util.List;

 class User {
    private String name;
    private List<Paper> submittedPapers;

    public User() {
        this.name = "";
        this.submittedPapers = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
        this.submittedPapers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    public int countSubmittedPapers() {
        return this.submittedPapers.size();
    }

    public double calculateAcceptanceRate() {
        if (this.submittedPapers.isEmpty()) {
            return 0.0;
        }
        int acceptedPapers = 0;
        for (Paper paper : this.submittedPapers) {
            if (paper.getFinalDecision() != null && paper.getFinalDecision().equals("Accept")) {
                acceptedPapers++;
            }
        }
        return (double) acceptedPapers / this.submittedPapers.size();
    }
}

 class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private String finalDecision;

    public Paper() {
        this.title = "";
        this.isResearchPaper = false;
        this.reviews = new ArrayList<>();
        this.finalDecision = null;
    }

    public Paper(String title, boolean isResearchPaper) {
        this.title = title;
        this.isResearchPaper = isResearchPaper;
        this.reviews = new ArrayList<>();
        this.finalDecision = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isResearchPaper() {
        return isResearchPaper;
    }

    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public boolean areReviewsConsistent() {
        if (this.reviews.isEmpty()) {
            return false;
        }
        String firstDecision = this.reviews.get(0).getGrade();
        for (Review review : this.reviews) {
            if (!review.getGrade().equals(firstDecision)) {
                return false;
            }
        }
        return true;
    }
}

 class Review {
    private String feedback;
    private String grade;

    public Review() {
        this.feedback = "";
        this.grade = "";
    }

    public Review(String feedback, String grade) {
        this.feedback = feedback;
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

 class Reviewer extends User {
    private List<Paper> assignedPapers;

    public Reviewer() {
        super();
        this.assignedPapers = new ArrayList<>();
    }

    public Reviewer(String name) {
        super(name);
        this.assignedPapers = new ArrayList<>();
    }

    public List<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    public void addAssignedPaper(Paper paper) {
        this.assignedPapers.add(paper);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     *
     * @return The number of unsubmitted reviews.
     */
    public int countUnsubmittedReviews() {
        int unsubmittedReviews = 0;
        for (Paper paper : this.assignedPapers) {
            for (Review review : paper.getReviews()) {
                if (review.getGrade() == null || review.getGrade().isEmpty()) {
                    unsubmittedReviews++;
                }
            }
        }
        return unsubmittedReviews;
    }

    /**
     * Calculates the average score of the reviewer's historical grades.
     *
     * @return The average score between 0.0 and 1.0.
     */
    public double calculateAverageScore() {
        int totalReviews = 0;
        int totalScore = 0;
        for (Paper paper : this.assignedPapers) {
            for (Review review : paper.getReviews()) {
                if (review.getGrade() != null && !review.getGrade().isEmpty()) {
                    totalReviews++;
                    if (review.getGrade().equals("Accept")) {
                        totalScore++;
                    }
                }
            }
        }
        if (totalReviews == 0) {
            return 0.0;
        }
        return (double) totalScore / totalReviews;
    }
}

 class Chair extends User {
    public Chair() {
        super();
    }

    public Chair(String name) {
        super(name);
    }

    /**
     * Makes the final decision for a paper based on the reviews.
     *
     * @param paper The paper to make a decision for.
     * @throws IllegalArgumentException If the paper has fewer than 3 reviews.
     */
    public void makeFinalDecision(Paper paper) {
        if (paper.getReviews().size() < 3) {
            throw new IllegalArgumentException("A paper must have at least 3 reviews.");
        }
        if (paper.areReviewsConsistent()) {
            String decision = paper.getReviews().get(0).getGrade();
            paper.setFinalDecision(decision);
        } else {
            paper.setFinalDecision("Reject");
        }
    }
}