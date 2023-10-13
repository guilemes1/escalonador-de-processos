public class CPU {
    private int programCounter;

    public void loadProcess(BCP processo, Escalonador escalonador, Log logFile) {

        logFile.writeExecution(processo);

        for (int i = 0; i < processo.getQuantum(); i++) {

            String instrucaoAtual = processo.getProgramCounter();
            if (instrucaoAtual.startsWith("X"))
                processo.setX(Integer.parseInt(instrucaoAtual.substring(2)));
            else if (instrucaoAtual.startsWith("Y"))
                processo.setY(Integer.parseInt(instrucaoAtual.substring(2)));
            else if (instrucaoAtual.equals("E/S")) {
                //Logger.ES((i + 1), nomeProcesso);
                logFile.writeInterruptionES(processo, i);
                processo.incrementIndex();
                processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
                escalonador.decrementBloqueados();   //decrementa a os processos que estao na fila de bloqueados
                escalonador.es(processo);            //poe o corno na fila de bloqueados
                return;
            } else if (instrucaoAtual.equals("SAIDA")) {
                escalonador.saida(processo);
                logFile.printFinish(processo, i);
                return;
            }

            processo.incrementIndex();
            processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
        }

        logFile.writeInterruption(processo, 2);
        escalonador.decrementBloqueados();
        escalonador.addProntos(processo);
    }

}
