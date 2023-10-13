import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Sistema {

    private static List<BCP> processTable;
    private static Escalonador escalonador;

    private static CPU cpu;

    private static Log logFile;

    public static void main(String[] args) throws IOException {


        File[] processosOrdenados = Reader.readProcess("processos");
        setProcessTable(Reader.createProcess(processosOrdenados));
        Sistema.setEscalonador(new Escalonador(new LinkedList<>(getProcessTable())));
        Sistema.setCpu(new CPU());

        String localArquivo = "log/log0" + Reader.readQuantum("processos") + ".txt";
        Sistema.logFile = new Log(localArquivo);
        Sistema.logFile.writeloadProcess(Sistema.getEscalonador().getProntos());

        while (!Sistema.getEscalonador().getProntos().isEmpty()) {
            BCP processo = chooseProcess(Sistema.getEscalonador().getProntos().poll());
            if (processo != null) {
                Sistema.cpu.loadProcess(processo, Sistema.getEscalonador(), Sistema.logFile);

                for (BCP bcp : getProcessTable()) {
                    if (bcp.getState().equals("Bloqueado") && bcp.getWaitingTime() == 0) {
                        Sistema.escalonador.removeBloqueado(bcp);
                        Sistema.escalonador.addProntos(bcp);
                    }
                }
            }
        }
        Sistema.logFile.getWriter().close();
    }

    private static BCP chooseProcess(BCP processo) {
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
