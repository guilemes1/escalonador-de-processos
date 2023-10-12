public class BCP {
    private String programCounter;
    private String state;
    private int x;
    private int y;
    private String[] instructions;
    private String programName;
    private int quantum;

    private int waitingTime;

    private int index;

    public String getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(String programCounter) {
        this.programCounter = programCounter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void decrementWaitingTime() {
        this.waitingTime--;
    }

    public int getIndex() {
        return index;
    }

    public void incrementIndex() {
        this.index++;
    }

    @Override
    public String toString() {
        return this.programName;
    }
}
