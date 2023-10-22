import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sistema {

    private static List<BCP> processTable;
    private static Escalonador escalonador;

    private static CPU cpu;

    private static Log logFile;

    public static void main(String[] args) throws IOException {

        int quantum = Reader.readQuantum("processos");
        File[] processosOrdenados = Reader.readProcess("processos");
        setProcessTable(Reader.createProcess(processosOrdenados, quantum));
        Sistema.setEscalonador(new Escalonador(new LinkedList<>(getProcessTable())));
        Sistema.setCpu(new CPU());
        int quantidadeDeTrocas = 0;
        int quantidadeDeProcessos = getProcessTable().size();



        String localArquivo = "log/log0" + Reader.readQuantum("processos") + ".txt";
        Sistema.logFile = new Log(localArquivo);
        Sistema.logFile.writeloadProcess(Sistema.getEscalonador().getProntos());

        while (!getProcessTable().isEmpty()) {
            BCP processo = chooseProcess(Sistema.getEscalonador().getProntos().poll());
            if (processo != null) {
                Sistema.cpu.loadProcess(processo, Sistema.getEscalonador(), Sistema.logFile);

                Iterator<BCP> iterator = getProcessTable().iterator();

                while (iterator.hasNext()) {
                    BCP bcp = iterator.next();
                    if (bcp.getState().equals("Bloqueado") && bcp.getWaitingTime() == 0) {
                        Sistema.escalonador.removeBloqueado(bcp);
                        Sistema.escalonador.addProntos(bcp);
                    }

                    if (bcp.getState().equals("Finalizado"))
                        iterator.remove();
                }
            } else if (!Sistema.getEscalonador().getBloqueados().isEmpty()) {
                Sistema.escalonador.forceReady();
                Sistema.escalonador.cleanBloqueados();
            }

            quantidadeDeTrocas++;
        }
        int totalInstrucoes = Sistema.cpu.getQuantidadeTotalDeInstrucoes();
        int totalQuantum = Sistema.cpu.getTotalQuantum();
        Sistema.logFile.writeMeanAndQuantum(quantidadeDeTrocas, quantidadeDeProcessos, totalInstrucoes, totalQuantum, quantum);

        for (int i = 4; i < 14; i++) {
            Teste.executaTeste(i);
        }
    }

    private static BCP chooseProcess(BCP processo) {
        if (processo == null)
            return null;

        processo.setState("Executando");
        return processo;
    }

    public static List<BCP> getProcessTable() {
        return processTable;
    }

    public static List<BCP> setProcessTable(List<BCP> processTable) {
        return Sistema.processTable = processTable;
    }

    public static Escalonador getEscalonador() {
        return escalonador;
    }

    public static void setEscalonador(Escalonador escalonador) {
        Sistema.escalonador = escalonador;
    }

    public static CPU getCpu() {
        return cpu;
    }

    public static void setCpu(CPU cpu) {
        Sistema.cpu = cpu;
    }
}
