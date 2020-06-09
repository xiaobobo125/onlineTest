package com.bolife.online.entity;

import java.util.Date;
/**
 * @Auther: Mr.BoBo
 * @Date: 2020/6/6 16:21
 * @Description: 题目
 */
public class Question {

    private int id;
    private String title;
    private String content;
    private int questionType;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String parse;
    private int subjectId;
    private int contestId;
    private int score;
    private int difficulty;
    private Date createTime;
    private Date updateTime;
    private int state;

    private String subjectName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", questionType=" + questionType +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", answer='" + answer + '\'' +
                ", parse='" + parse + '\'' +
                ", subjectId=" + subjectId +
                ", contestId=" + contestId +
                ", score=" + score +
                ", difficulty=" + difficulty +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (questionType != question.questionType) return false;
        if (subjectId != question.subjectId) return false;
        if (contestId != question.contestId) return false;
        if (score != question.score) return false;
        if (difficulty != question.difficulty) return false;
        if (state != question.state) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (content != null ? !content.equals(question.content) : question.content != null) return false;
        if (optionA != null ? !optionA.equals(question.optionA) : question.optionA != null) return false;
        if (optionB != null ? !optionB.equals(question.optionB) : question.optionB != null) return false;
        if (optionC != null ? !optionC.equals(question.optionC) : question.optionC != null) return false;
        if (optionD != null ? !optionD.equals(question.optionD) : question.optionD != null) return false;
        if (answer != null ? !answer.equals(question.answer) : question.answer != null) return false;
        if (parse != null ? !parse.equals(question.parse) : question.parse != null) return false;
        if (createTime != null ? !createTime.equals(question.createTime) : question.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(question.updateTime) : question.updateTime != null) return false;
        return subjectName != null ? subjectName.equals(question.subjectName) : question.subjectName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + questionType;
        result = 31 * result + (optionA != null ? optionA.hashCode() : 0);
        result = 31 * result + (optionB != null ? optionB.hashCode() : 0);
        result = 31 * result + (optionC != null ? optionC.hashCode() : 0);
        result = 31 * result + (optionD != null ? optionD.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (parse != null ? parse.hashCode() : 0);
        result = 31 * result + subjectId;
        result = 31 * result + contestId;
        result = 31 * result + score;
        result = 31 * result + difficulty;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (subjectName != null ? subjectName.hashCode() : 0);
        return result;
    }
}
