import java.io.*;
import java.util.Queue;

public class Log {

    private String pathFile;

    private File file;

    private PrintWriter writer;

    public Log(String pathFile) throws IOException {
        this.pathFile = pathFile;
        this.file = new File(pathFile);
        this.writer = new PrintWriter(pathFile);
        file.createNewFile();
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void writeloadProcess(Queue<BCP> prontos) throws FileNotFoundException {
        for (BCP processo : prontos) {
            writer.println("Carregando " + processo.getProgramName());
        }
    }

    public void writeExecution(BCP processo) {
        writer.println("Executando " + processo.getProgramName());
    }

    public void writeInterruptionES(BCP processo, int i) {
        writer.println("E/S iniciado em " + processo.getProgramName());
        writeInterruption(processo, i);
    }

    public void writeInterruption(BCP processo, int i) {
        if (i == 0)
            writer.println("Interrompendo " + processo.getProgramName() + " após " + (i+1) + " instrução");
        else
            writer.println("Interrompendo " + processo.getProgramName() + " após " + (i+1) + " instruções");
    }

    public void printFinish(BCP processo, int i) {
        writer.println(processo.getProgramName() + " terminado. X=" + processo.getX() + ". Y=" + processo.getY());
    }

    public void writeMeanAndQuantum(int quantidadeDeTrocas, int quantidadeDeProcessos, int totalInstrucoes, int totalQuantum, int quantum) {
        double meanTrocas = (double) (quantidadeDeTrocas-1) / quantidadeDeProcessos;
        double meanInstructions = (double) totalInstrucoes / (quantidadeDeTrocas-1);

        writer.println("MEDIA DE TROCAS: " + (meanTrocas));
        writer.println("MEDIA DE INSTRUCOES: " + meanInstructions);
        writer.println("QUANTUM: " + quantum);
        writer.close();
    }

    //public teste


}
