package Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {
    private String id,subjectName, preRequire;
    private int credit,sem,isPassed, difficultLvl;

    public Subject(String ID, String subjectName, int credit, String preReqSubject, int sem, int isPassed, int diffLvl) {
        this.id = ID;
        this.subjectName = subjectName;
        this.credit = credit;
        this.preRequire = preReqSubject;
        this.sem = sem;
        this.isPassed = isPassed;
        this.difficultLvl = diffLvl;
    }

    public String getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getPreRequire() {
        return preRequire;
    }

    public int getCredit() {
        return credit;
    }

    public int getSem() {
        return sem;
    }

    public int getIsPassed() {
        return isPassed;
    }

    public int getDifficultLvl() {
        return difficultLvl;
    }
}
