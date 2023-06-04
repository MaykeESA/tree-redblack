package br.com.unit.eda;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        int node;

        Scanner scanner = new Scanner(System.in);

        ArvoreRubroNegra myTree = new ArvoreRubroNegra();

        while (true) {
            System.out.println("======= MENU =======");
            System.out.println("1 - Adicionar Nó");
            System.out.println("2 - Remover Nó");
            System.out.println("3 - Consultar Nó");
            System.out.println("4 - Sair");

            System.out.println("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:

                    System.out.println("Digite o número do nó que deseja adicionar na arvore: ");
                    node = scanner.nextInt();
                    scanner.nextLine();
                    myTree.inserir(node);
                    myTree.printRedBlackTree(myTree);
                    break;

                case 2:

                    System.out.println("Digite o número do nó que deseja remover da arvore: ");
                    node = scanner.nextInt();
                    scanner.nextLine();
                    myTree.removerNo(node);
                    myTree.printRedBlackTree(myTree);
                    break;

                case 3:

                    System.out.println("Digite o número do nó que deseja consultar na arvore: ");
                    node = scanner.nextInt();
                    scanner.nextLine();
                    myTree.getDetalhesNo(node);
                    myTree.printRedBlackTree(myTree);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Opção errada, selecione novamente!");

            }

            if (option == 4) {

                break;
            }
        }

    }




}