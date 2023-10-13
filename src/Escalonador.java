import java.util.*;

public class Escalonador {
    private Queue<BCP> prontos;
    private Queue<BCP> bloqueados;

    public Escalonador(Queue<BCP> prontos) {
        this.prontos = prontos;
        this.bloqueados = new LinkedList<>();
    }

    public Queue<BCP> getProntos() {
        return prontos;
    }

    public Queue<BCP> getBloqueados() {
        return bloqueados;
    }

    public void inicializaProntos(Queue<BCP> prontos) {
        this.prontos = prontos;
    }

    public void addProntos(BCP bcp) {
        bcp.setState("Pronto");
        this.prontos.add(bcp);
    }

    public void inicializaBloqueados(Queue<BCP> bloqueados) {
        this.bloqueados = bloqueados;
    }

    public void removeBloqueado(BCP bcp) {
        this.bloqueados.remove();
    }

    public void es(BCP processo) {
        processo.setState("Bloqueado");
        processo.setWaitingTime(2);
        //Despachante.retirarContexto(processo);
        bloqueados.add(processo);
    }

    public void saida(BCP processo) {
        processo.setState("Finalizado");
        //S.removeProcesso(executando);
    }

    public void decrementBloqueados() {
        if (bloqueados.isEmpty())
            return;

        for (BCP processo : bloqueados) {
            processo.decrementWaitingTime();
        }
    }

    public void forceReady() {
        for (BCP processo : bloqueados) {
            processo.setWaitingTime(0);
            processo.setState("Pronto");
            prontos.add(processo);
//            bloqueados.remove();
        }
    }

    public void cleanBloqueados() {
        Iterator<BCP> iterator = bloqueados.iterator();

        while (iterator.hasNext()) {
            BCP processo = iterator.next();
            iterator.remove();
        }
    }
}
