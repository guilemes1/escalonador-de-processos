import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {

    public static File[] readProcess(String string) throws IOException {
        File folder = new File(string);
        File[] files = folder.listFiles();
        Arrays.sort(files);
        return files;
    }

    public static List<BCP> createProcess(File[] processosOrdenados, int quantum) throws IOException {
        BufferedReader buffRead = null;
        FileReader arquivo = null;
        List<BCP> processTable = new ArrayList<>();

        for (int i = 0; i < processosOrdenados.length - 1; i++) {
            arquivo = new FileReader(processosOrdenados[i]);
            buffRead = new BufferedReader(arquivo);
            String linha = "";
            List<String> linhas = new ArrayList<>();
            while((linha = buffRead.readLine()) != null) {
                linhas.add(linha);
            }
            List<String> subLista = linhas.subList(1, linhas.size());
            String[] instrucoes = subLista.toArray(new String[0]);


            BCP processo = new BCP();
            processo.setProgramName(linhas.get(0));
            processo.setInstructions(instrucoes);
            processo.setProgramCounter(instrucoes[0]);
            processo.setState("Pronto");
            processo.setQuantum(quantum);

            processTable.add(processo);  //Adiciona na tabela de processos
        }

        return processTable;
    }

    public static int readQuantum(String folder) throws NumberFormatException, IOException {
        String nomeArquivo = folder + "/quantum.txt";
        FileReader file = new FileReader(nomeArquivo);
        BufferedReader buff = new BufferedReader(file);
        return Integer.parseInt(buff.readLine());
    }
}
