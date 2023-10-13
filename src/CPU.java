public class CPU {
    private int programCounter;

    public void loadProcess(BCP processo, Escalonador escalonador) {

        for (int i = 0; i < processo.getQuantum(); i++) {

            String instrucaoAtual = processo.getProgramCounter();
            if (instrucaoAtual.startsWith("X"))
                processo.setX(Integer.parseInt(instrucaoAtual.substring(2)));
            else if (instrucaoAtual.startsWith("Y"))
                processo.setY(Integer.parseInt(instrucaoAtual.substring(2)));
            else if (instrucaoAtual.equals("E/S")) {
                //Logger.ES((i + 1), nomeProcesso);
                processo.incrementIndex();
                processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
                escalonador.decrementBloqueados();   //decrementa a os processos que estao na fila de bloqueados
                escalonador.es(processo);            //poe o corno na fila de bloqueados
                return;
            } else if (instrucaoAtual.equals("SAIDA")) {
                escalonador.saida(processo);
                //Logger.finalizarProcesso(nomeProcesso, i);
                return;
            }
            processo.incrementIndex();
            processo.setProgramCounter(processo.getInstructions()[processo.getIndex()]);
        }


        escalonador.decrementBloqueados();
        escalonador.addProntos(processo);
    }

}
