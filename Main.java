package lanchonete;
import java.util.Scanner;

class Pedido {
    int id;
    String nomeCliente;
    String item;
    int quantidade;
    double valor;
    String tipo; // "Urgente", "Prioritário", "Normal"
    Pedido prox;

    public Pedido(int id, String nomeCliente, String item, int quantidade, double valor, String tipo) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.item = item;
        this.quantidade = quantidade;
        this.valor = valor;
        this.tipo = tipo;
        this.prox = null;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | Cliente:%s | Item:%s | Qtd:%d | Valor: R$%.2f | Tipo:%s]",
                id, nomeCliente, item, quantidade, valor, tipo);
    }
}

class ListaPedidos {
    private Pedido cabeca;
    private Pedido cauda;
    private int tamanho;

    public ListaPedidos() {
        this.cabeca = null;
        this.cauda = null;
        this.tamanho = 0;
    }

    public boolean estaVazia() {
        return cabeca == null;
    }

    public int tamanho() {
        return tamanho;
    }

    // -------- Inserções --------
    private boolean existeId(int id) {
        Pedido atual = cabeca;
        while (atual != null) {
            if (atual.id == id)
                return true;
            atual = atual.prox;
        }
        return false;
    }

    public void inserirUrgente(Pedido novo) {
        if (existeId(novo.id)) {
            System.out.println("❌ Pedido duplicado! ID já existe.");
            return;
        }

        novo.valor *= 1.2; // acréscimo de 20%
        novo.prox = cabeca;
        cabeca = novo;
        if (cauda == null)
            cauda = novo;
        tamanho++;
        System.out.println("✅ Pedido URGENTE inserido no início!");
    }

    public void inserirNormal(Pedido novo) {
        if (existeId(novo.id)) {
            System.out.println("❌ Pedido duplicado! ID já existe.");
            return;
        }

        if (estaVazia()) {
            cabeca = cauda = novo;
        } else {
            cauda.prox = novo;
            cauda = novo;
        }
        tamanho++;
        System.out.println("✅ Pedido NORMAL inserido no fim!");
    }

    public void inserirPrioritario(Pedido novo) {
        if (existeId(novo.id)) {
            System.out.println("❌ Pedido duplicado! ID já existe.");
            return;
        }

        novo.valor *= 1.1; // acréscimo de 10%
        int posicaoMedia = tamanho / 2;

        if (estaVazia()) {
            cabeca = cauda = novo;
        } else if (posicaoMedia == 0) {
            inserirUrgente(novo);
            return;
        } else {
            Pedido atual = cabeca;
            for (int i = 0; i < posicaoMedia - 1; i++) {
                atual = atual.prox;
            }
            novo.prox = atual.prox;
            atual.prox = novo;
            if (novo.prox == null)
                cauda = novo;
            tamanho++;
        }
        System.out.println("✅ Pedido PRIORITÁRIO inserido na posição média!");
    }

    // -------- Remoções --------
    public void removerInicio() {
        if (estaVazia()) {
            System.out.println("⚠️ Lista vazia!");
            return;
        }
        System.out.println("🗑️ Pedido atendido/removido: " + cabeca);
        cabeca = cabeca.prox;
        tamanho--;
        if (tamanho == 0)
            cauda = null;
    }

    public void removerFim() {
        if (estaVazia()) {
            System.out.println("⚠️ Lista vazia!");
            return;
        }
        if (cabeca == cauda) {
            System.out.println("🗑️ Pedido cancelado: " + cabeca);
            cabeca = cauda = null;
        } else {
            Pedido atual = cabeca;
            while (atual.prox != cauda)
                atual = atual.prox;
            System.out.println("🗑️ Pedido cancelado: " + cauda);
            atual.prox = null;
            cauda = atual;
        }
        tamanho--;
    }

    public void removerPosicao(int k) {
        if (k < 0 || k >= tamanho) {
            System.out.println("⚠️ Posição inválida!");
            return;
        }
        if (k == 0) {
            removerInicio();
            return;
        }

        Pedido atual = cabeca;
        for (int i = 0; i < k - 1; i++) {
            atual = atual.prox;
        }
        Pedido removido = atual.prox;
        atual.prox = removido.prox;
        if (removido == cauda)
            cauda = atual;
        tamanho--;
        System.out.println("🗑️ Pedido removido na posição " + k + ": " + removido);
    }

    // -------- Buscas --------
    public Pedido buscarPorId(int id) {
        Pedido atual = cabeca;
        while (atual != null) {
            if (atual.id == id)
                return atual;
            atual = atual.prox;
        }
        return null;
    }

    public void mostrarProximos(int n) {
        Pedido atual = cabeca;
        int cont = 0;
        while (atual != null && cont < n) {
            System.out.println(atual);
            atual = atual.prox;
            cont++;
        }
    }

    // -------- Impressão --------
    public void imprimirLista() {
        if (estaVazia()) {
            System.out.println("⚠️ Nenhum pedido na fila!");
            return;
        }
        System.out.println("\n📋 Lista de pedidos (ordem de atendimento):");
        Pedido atual = cabeca;
        while (atual != null) {
            System.out.println(atual);
            atual = atual.prox;
        }
        System.out.println("-------------------------------------------");
    }
}

// -------- Programa principal com menu interativo --------
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListaPedidos lista = new ListaPedidos();
        int opcao;

        do {
            System.out.println("\n==== SISTEMA DE GESTÃO DE PEDIDOS ====");
            System.out.println("1 - Adicionar novo pedido");
            System.out.println("2 - Remover pedido do início (atendido)");
            System.out.println("3 - Remover pedido do fim (cancelado)");
            System.out.println("4 - Remover pedido por posição");
            System.out.println("5 - Buscar pedido por ID");
            System.out.println("6 - Mostrar próximos N pedidos");
            System.out.println("7 - Exibir todos os pedidos");
            System.out.println("8 - Mostrar tamanho da lista");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("ID do pedido: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome do cliente: ");
                    String nome = sc.nextLine();
                    System.out.print("Item: ");
                    String item = sc.nextLine();
                    System.out.print("Quantidade: ");
                    int qtd = sc.nextInt();
                    System.out.print("Valor: R$");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Tipo (Urgente / Prioritário / Normal): ");
                    String tipo = sc.nextLine();

                    Pedido novo = new Pedido(id, nome, item, qtd, valor, tipo);

                    if (tipo.equalsIgnoreCase("Urgente"))
                        lista.inserirUrgente(novo);
                    else if (tipo.equalsIgnoreCase("Prioritário"))
                        lista.inserirPrioritario(novo);
                    else
                        lista.inserirNormal(novo);
                }

                case 2 -> lista.removerInicio();
                case 3 -> lista.removerFim();

                case 4 -> {
                    System.out.print("Informe a posição a remover: ");
                    int pos = sc.nextInt();
                    lista.removerPosicao(pos);
                }

                case 5 -> {
                    System.out.print("Informe o ID do pedido: ");
                    int idBusca = sc.nextInt();
                    Pedido achado = lista.buscarPorId(idBusca);
                    if (achado != null)
                        System.out.println("🔎 Encontrado: " + achado);
                    else
                        System.out.println("⚠️ Pedido não encontrado!");
                }

                case 6 -> {
                    System.out.print("Quantos pedidos deseja visualizar? ");
                    int n = sc.nextInt();
                    lista.mostrarProximos(n);
                }

                case 7 -> lista.imprimirLista();

                case 8 -> System.out.println("📦 Total de pedidos na lista: " + lista.tamanho());

                case 0 -> System.out.println("👋 Encerrando o sistema...");
                default -> System.out.println("⚠️ Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}
