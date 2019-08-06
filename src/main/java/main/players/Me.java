package main.players;

import main.plan.Plan;

public class Me extends Player {
    private boolean isOnLine;
    private Plan currentPlan;


    public Me(String key) {
        super(key);
        currentPlan = null;
        isOnLine = false;
    }

    public boolean isHavePlan() {
        return currentPlan != null;
    }

    public boolean isFinishPlan() {
        return (isHavePlan() && currentPlan.isFinish());
    }

    public Plan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }
}
