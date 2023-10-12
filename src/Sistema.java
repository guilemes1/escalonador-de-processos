import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sistema {

    private static List<BCP> processTable;
    private static Escalonador escalonador;

    private static CPU cpu;

    public static void main(String[] args) throws IOException {


        File[] processosOrdenados = Reader.readProcess("processos");
        setProcessTable(Reader.createProcess(processosOrdenados));
        Sistema.setEscalonador(new Escalonador(new LinkedList<>(Reader.createProcess(processosOrdenados))));
        Sistema.setCpu(new CPU());

        System.out.println(Sistema.getEscalonador().getProntos());

        while (!Sistema.getEscalonador().getProntos().isEmpty()) {
            BCP processo = Sistema.getEscalonador().getProntos().poll();  //criar um metodo escolhe proximo para alterar o estado
            Sistema.cpu.loadProcess(processo, Sistema.getEscalonador());

            for (BCP bloqueado : Sistema.escalonador.getBloqueados()) {
                if (bloqueado.getWaitingTime() == 0) {
                    Sistema.escalonador.removeBloqueado(bloqueado);
                    Sistema.escalonador.addProntos(bloqueado);
                }
            }

//            Iterator<BCP> iterator = Sistema.escalonador.getBloqueados().iterator();
//            while (iterator.hasNext()) {
//                BCP bloqueado = iterator.next();
//                if (bloqueado.getWaitingTime() == 0) {
//                    Sistema.escalonador.removeBloqueado(bloqueado);
//                    Sistema.escalonador.addProntos(bloqueado);
//                }
//            }
        }

    }

    public static List<BCP> getProcessTable() {
        return processTable;
    }

    public static void setProcessTable(List<BCP> processTable) {
        Sistema.processTable = processTable;
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
