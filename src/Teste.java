import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Teste {

    private static List<BCP> processTable;
    private static Escalonador escalonador;

    private static CPU cpu;
    private static Log logFile;

    public static void executaTeste(int quantum) throws IOException {
        File[] processosOrdenados = Reader.readProcess("processos");
        setProcessTable(Reader.createProcess(processosOrdenados, quantum));
        Teste.setEscalonador(new Escalonador(new LinkedList<>(getProcessTable())));
        Teste.setCpu(new CPU());
        int quantidadeDeTrocas = 0;
        int quantidadeDeProcessos = getProcessTable().size();

        String localArquivo = "log/log0" + quantum + ".txt";
        Teste.logFile = new Log(localArquivo);
        Teste.logFile.writeloadProcess(Teste.getEscalonador().getProntos());

        while (!getProcessTable().isEmpty()) {
            BCP processo = chooseProcess(Teste.getEscalonador().getProntos().poll());
            if (processo != null) {
                Teste.cpu.loadProcess(processo, Teste.getEscalonador(), Teste.logFile);

                Iterator<BCP> iterator = getProcessTable().iterator();

                while (iterator.hasNext()) {
                    BCP bcp = iterator.next();
                    if (bcp.getState().equals("Bloqueado") && bcp.getWaitingTime() == 0) {
                        Teste.escalonador.removeBloqueado(bcp);
                        Teste.escalonador.addProntos(bcp);
                    }

                    if (bcp.getState().equals("Finalizado"))
                        iterator.remove();
                }
            } else if (!Teste.getEscalonador().getBloqueados().isEmpty()) {
                Teste.escalonador.forceReady();
                Teste.escalonador.cleanBloqueados();
            }

            quantidadeDeTrocas++;
        }
        int totalInstrucoes = Teste.cpu.getQuantidadeTotalDeInstrucoes();
        int totalQuantum = Teste.cpu.getTotalQuantum();
        Teste.logFile.writeMeanAndQuantum(quantidadeDeTrocas, quantidadeDeProcessos, totalInstrucoes, totalQuantum, quantum);
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
        return Teste.processTable = processTable;
    }

    public static Escalonador getEscalonador() {
        return escalonador;
    }

    public static void setEscalonador(Escalonador escalonador) {
        Teste.escalonador = escalonador;
    }

    public static CPU getCpu() {
        return cpu;
    }

    public static void setCpu(CPU cpu) {
        Teste.cpu = cpu;
    }
}
