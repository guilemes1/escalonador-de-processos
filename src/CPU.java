public class CPU {
    private int quantidadeTotalDeInstrucoes;

    private int totalQuantum;

    public void loadProcess(BCP processo, Escalonador escalonador, Log logFile) {

        logFile.writeExecution(processo);

        for (int i = 0; i < processo.getQuantum(); i++) {

            String instruction = processo.getProgramCounter();
            if (instruction.startsWith("X"))
                processo.setX(Integer.parseInt(instruction.substring(2)));
            else if (instruction.startsWith("Y"))
                processo.setY(Integer.parseInt(instruction.substring(2)));
            else if (instruction.equals("E/S")) {
                incrementQuantidadeTotalDeInstrucoes();
                totalQuantum += processo.getQuantum();
                logFile.writeInterruptionES(processo, i);
                processo.incrementIndex();
                processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
                escalonador.decrementBloqueados();
                escalonador.es(processo);
                return;
            } else if (instruction.equals("SAIDA")) {
                incrementQuantidadeTotalDeInstrucoes();
                totalQuantum += processo.getQuantum();
                escalonador.saida(processo);
                logFile.printFinish(processo, i);
                return;
            }

            incrementQuantidadeTotalDeInstrucoes();
            totalQuantum += processo.getQuantum();
            processo.incrementIndex();
            processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
        }

        logFile.writeInterruption(processo, 2);
//        incrementQuantidadeTotalDeInstrucoes();
        escalonador.decrementBloqueados();
        escalonador.addProntos(processo);
    }

    public int getQuantidadeTotalDeInstrucoes() {
        return quantidadeTotalDeInstrucoes;
    }

    public void incrementQuantidadeTotalDeInstrucoes() {
        this.quantidadeTotalDeInstrucoes++;
    }

    public int getTotalQuantum() {
        return totalQuantum;
    }
}
