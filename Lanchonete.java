package lanchonete;
import java.util.LinkedList;
import java.util.Scanner;

class Pedido {
    int id;
    String cliente;
    String item;
    int qtd;
    double valor;
    String tipo;

    public Pedido(int id, String cliente, String item, int qtd, double valor, String tipo) {
        this.id = id;
        this.cliente = cliente;
        this.item = item;
        this.qtd = qtd;
        this.valor = valor;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | Cliente:%s | Item:%s | Qtd:%d | Valor:R$%.2f | Tipo:%s]",
                id, cliente, item, qtd, valor, tipo);
    }
}

public class Lanchonete {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Pedido> pedidos = new LinkedList<>();
        int opcao;

        do {
            System.out.println("\n==== SISTEMA DE GESTÃO DE PEDIDOS ====");
            System.out.println("1 - Adicionar novo pedido");
            System.out.println("2 - Remover do início (pedido atendido)");
            System.out.println("3 - Remover do fim (pedido cancelado)");
            System.out.println("4 - Remover por posição");
            System.out.println("5 - Buscar por ID");
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

                    boolean duplicado = pedidos.stream().anyMatch(p -> p.id == id);
                    if (duplicado) {
                        System.out.println("❌ Pedido com esse ID já existe!");
                        break;
                    }

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

                    if (tipo.equalsIgnoreCase("Urgente")) {
                        novo.valor *= 1.2;
                        pedidos.addFirst(novo);
                        System.out.println("✅ Pedido URGENTE adicionado no início!");
                    } else if (tipo.equalsIgnoreCase("Prioritário")) {
                        novo.valor *= 1.1;
                        int meio = pedidos.size() / 2;
                        pedidos.add(meio, novo);
                        System.out.println("✅ Pedido PRIORITÁRIO adicionado no meio!");
                    } else {
                        pedidos.addLast(novo);
                        System.out.println("✅ Pedido NORMAL adicionado no fim!");
                    }
                }

                case 2 -> {
                    if (pedidos.isEmpty())
                        System.out.println("⚠️ Lista vazia!");
                    else
                        System.out.println("🗑️ Removido: " + pedidos.removeFirst());
                }

                case 3 -> {
                    if (pedidos.isEmpty())
                        System.out.println("⚠️ Lista vazia!");
                    else
                        System.out.println("🗑️ Removido: " + pedidos.removeLast());
                }

                case 4 -> {
                    System.out.print("Informe a posição a remover: ");
                    int pos = sc.nextInt();
                    if (pos < 0 || pos >= pedidos.size())
                        System.out.println("⚠️ Posição inválida!");
                    else
                        System.out.println("🗑️ Removido: " + pedidos.remove(pos));
                }

                case 5 -> {
                    System.out.print("Informe o ID do pedido: ");
                    int idBusca = sc.nextInt();
                    Pedido achado = pedidos.stream().filter(p -> p.id == idBusca).findFirst().orElse(null);
                    if (achado != null)
                        System.out.println("🔎 Pedido encontrado: " + achado);
                    else
                        System.out.println("⚠️ Pedido não encontrado!");
                }

                case 6 -> {
                    System.out.print("Quantos pedidos deseja visualizar? ");
                    int n = sc.nextInt();
                    for (int i = 0; i < n && i < pedidos.size(); i++) {
                        System.out.println(pedidos.get(i));
                    }
                }

                case 7 -> {
                    if (pedidos.isEmpty())
                        System.out.println("⚠️ Nenhum pedido na lista!");
                    else {
                        System.out.println("\n📋 Lista de pedidos:");
                        pedidos.forEach(System.out::println);
                    }
                }

                case 8 -> System.out.println("📦 Total de pedidos na lista: " + pedidos.size());

                case 0 -> System.out.println("👋 Encerrando o sistema...");
                default -> System.out.println("⚠️ Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
                }
