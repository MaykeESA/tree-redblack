package br.com.unit.eda;

public class ArvoreRubroNegra<T extends Comparable<T>> {
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;

    private class No {
        T dado;
        No esquerda;
        No direita;
        No pai;
        boolean cor;

        public No(T dado) {
            this.dado = dado;
            this.cor = VERMELHO;
        }
    }

    private No raiz;

    public void inserir(T dado) {
        No novoNo = new No(dado);
        if (raiz == null) {
            raiz = novoNo;
        } else {
            if (getDetalhesNoEncontrado(dado)) {
                System.out.println("Esse nó já existe");
                return;
            }

            No atual = raiz;
            No pai = null;
            while (true) {
                pai = atual;
                if (dado.compareTo(atual.dado) < 0) {
                    atual = atual.esquerda;
                    if (atual == null) {
                        pai.esquerda = novoNo;
                        novoNo.pai = pai;
                        break;
                    }
                } else {
                    atual = atual.direita;
                    if (atual == null) {
                        pai.direita = novoNo;
                        novoNo.pai = pai;
                        break;
                    }
                }
            }
            balancearArvore(novoNo);
        }
        raiz.cor = PRETO;
    }

    private void balancearArvore(No novoNo) {
        while (novoNo.pai != null && novoNo.pai.cor == VERMELHO) {
            if (novoNo.pai == novoNo.pai.pai.esquerda) {
                No tio = novoNo.pai.pai.direita;
                if (tio != null && tio.cor == VERMELHO) {
                    novoNo.pai.cor = PRETO;
                    tio.cor = PRETO;
                    novoNo.pai.pai.cor = VERMELHO;
                    novoNo = novoNo.pai.pai;
                } else {
                    if (novoNo == novoNo.pai.direita) {
                        novoNo = novoNo.pai;
                        rotacionarEsquerda(novoNo);
                    }
                    novoNo.pai.cor = PRETO;
                    novoNo.pai.pai.cor = VERMELHO;
                    rotacionarDireita(novoNo.pai.pai);
                }
            } else {
                No tio = novoNo.pai.pai.esquerda;
                if (tio != null && tio.cor == VERMELHO) {
                    novoNo.pai.cor = PRETO;
                    tio.cor = PRETO;
                    novoNo.pai.pai.cor = VERMELHO;
                    novoNo = novoNo.pai.pai;
                } else {
                    if (novoNo == novoNo.pai.esquerda) {
                        novoNo = novoNo.pai;
                        rotacionarDireita(novoNo);
                    }
                    novoNo.pai.cor = PRETO;
                    novoNo.pai.pai.cor = VERMELHO;
                    rotacionarEsquerda(novoNo.pai.pai);
                }
            }
        }
    }

    private void rotacionarEsquerda(No no) {
        No filhoDireito = no.direita;
        no.direita = filhoDireito.esquerda;
        if (filhoDireito.esquerda != null) {
            filhoDireito.esquerda.pai = no;
        }
        filhoDireito.pai = no.pai;
        if (no.pai == null) {
            raiz = filhoDireito;
        } else if (no == no.pai.esquerda) {
            no.pai.esquerda = filhoDireito;
        } else {
            no.pai.direita = filhoDireito;
        }
        filhoDireito.esquerda = no;
        no.pai = filhoDireito;
    }

    private void rotacionarDireita(No no) {
        No filhoEsquerdo = no.esquerda;
        no.esquerda = filhoEsquerdo.direita;
        if (filhoEsquerdo.direita != null) {
            filhoEsquerdo.direita.pai = no;
        }
        filhoEsquerdo.pai = no.pai;
        if (no.pai == null) {
            raiz = filhoEsquerdo;
        } else if (no == no.pai.direita) {
            no.pai.direita = filhoEsquerdo;
        } else {
            no.pai.esquerda = filhoEsquerdo;
        }
        filhoEsquerdo.direita = no;
        no.pai = filhoEsquerdo;
    }

    public void getDetalhesNo(T dado) {
        No no = encontrarNo(dado);
        if (no != null) {
            No pai = (no.pai != null) ? no.pai : null;
            No filhoEsquerdo = (no.esquerda != null) ? no.esquerda : null;
            No filhoDireito = (no.direita != null) ? no.direita : null;

            System.out.println("Nó: " + no.dado);
            System.out.println("Pai: " + ((pai != null) ? pai.dado : "null"));
            System.out.println("Filho Esquerdo: " + ((filhoEsquerdo != null) ? filhoEsquerdo.dado : "null"));
            System.out.println("Filho Direito: " + ((filhoDireito != null) ? filhoDireito.dado : "null"));
        } else {
            System.out.println("Nó não encontrado.");
        }
    }

    public boolean getDetalhesNoEncontrado(T dado) {
        No no = encontrarNo(dado);
        if (no != null) {
            return true;
        } else {
            return false;
        }
    }

    private No encontrarNo(T dado) {
        return encontrarNo(raiz, dado);
    }

    private No encontrarNo(No atual, T dado) {
        if (atual == null || dado.equals(atual.dado)) {
            return atual;
        }

        if (dado.compareTo(atual.dado) < 0) {
            return encontrarNo(atual.esquerda, dado);
        } else {
            return encontrarNo(atual.direita, dado);
        }
    }

    public void removerNo(T dado) {
        No no = encontrarNo(dado);
        if (no != null) {
            removerNo(no);
        } else {
            System.out.println("Nó não encontrado.");
        }
    }

    private void removerNo(No no) {
        if (no.esquerda != null && no.direita != null) {
            No sucessor = getSucessor(no);
            no.dado = sucessor.dado;
            no = sucessor;
        }

        No filho = (no.direita != null) ? no.direita : no.esquerda;

        if (filho != null) {
            filho.pai = no.pai;
            if (no.pai == null) {
                raiz = filho;
            } else if (no == no.pai.esquerda) {
                no.pai.esquerda = filho;
            } else {
                no.pai.direita = filho;
            }
            if (no.cor == PRETO) {
                balancearArvoreRemocao(filho);
            }
        } else if (no.pai == null) {
            raiz = null;
        } else {
            if (no.cor == PRETO) {
                balancearArvoreRemocao(no);
            }
            if (no.pai != null) {
                if (no == no.pai.esquerda) {
                    no.pai.esquerda = null;
                } else if (no == no.pai.direita) {
                    no.pai.direita = null;
                }
                no.pai = null;
            }
        }
    }

    private void balancearArvoreRemocao(No no) {
        while (no != raiz && no.cor == PRETO) {
            if (no == no.pai.esquerda) {
                No irmao = no.pai.direita;
                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    no.pai.cor = VERMELHO;
                    rotacionarEsquerda(no.pai);
                    irmao = no.pai.direita;
                }

                if (irmao.esquerda.cor == PRETO && irmao.direita.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    no = no.pai;
                } else {
                    if (irmao.direita.cor == PRETO) {
                        irmao.esquerda.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacionarDireita(irmao);
                        irmao = no.pai.direita;
                    }

                    irmao.cor = no.pai.cor;
                    no.pai.cor = PRETO;
                    irmao.direita.cor = PRETO;
                    rotacionarEsquerda(no.pai);
                    no = raiz;
                }
            } else {
                No irmao = no.pai.esquerda;
                if (irmao.cor == VERMELHO) {
                    irmao.cor = PRETO;
                    no.pai.cor = VERMELHO;
                    rotacionarDireita(no.pai);
                    irmao = no.pai.esquerda;
                }

                if (irmao.direita.cor == PRETO && irmao.esquerda.cor == PRETO) {
                    irmao.cor = VERMELHO;
                    no = no.pai;
                } else {
                    if (irmao.esquerda.cor == PRETO) {
                        irmao.direita.cor = PRETO;
                        irmao.cor = VERMELHO;
                        rotacionarEsquerda(irmao);
                        irmao = no.pai.esquerda;
                    }

                    irmao.cor = no.pai.cor;
                    no.pai.cor = PRETO;
                    irmao.esquerda.cor = PRETO;
                    rotacionarDireita(no.pai);
                    no = raiz;
                }
            }
        }
        no.cor = PRETO;
    }

    private No getSucessor(No no) {
        if (no == null) {
            return null;
        }

        No atual = no.direita;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }



    public void imprimirEstruturaArvore() {
        imprimirEstruturaArvore(raiz, 0);
    }

    private void imprimirEstruturaArvore(No no, int prefixo) {
        if (no == null) {
            return;
        }

        StringBuilder prefixoBuilder = new StringBuilder();
        for (int i = 0; i < prefixo; i++) {
            prefixoBuilder.append(" ");
        }
        String prefixoString = prefixoBuilder.toString();

        System.out.println(prefixoString + "└─ " + no.dado + " " + (no.cor ? "(V)" : "(P)"));

        if (no.esquerda != null || no.direita != null) {
            int prefixoFilho = prefixo + 4;

            if (no.direita != null) {
                System.out.print(prefixoString + "├─D: ");
                imprimirEstruturaArvore(no.direita, prefixoFilho);
            }

            if (no.esquerda != null) {
                System.out.print(prefixoString + (no.direita != null ? "└─E: " : "├─E: "));
                imprimirEstruturaArvore(no.esquerda, prefixoFilho);
            }
        }
    }


    public  void printRedBlackTree(ArvoreRubroNegra tree) {
        printRedBlackTree(tree.raiz, "", true);
    }

    public void printRedBlackTree(No node, String indent, boolean last) {
        if (node == null) {
            System.out.println(indent + (last ? "└─ " : "├─ ") + "vazio");
            return;
        }

        String color = node.cor ? "Vermelho" : "Preto";
        System.out.println(indent + (last ? "└─ " : "├─ ") + node.dado + "[" + color + "]");

        String childIndent = indent + (last ? "    " : "│   ");
        printRedBlackTree(node.esquerda, childIndent, false);
        printRedBlackTree(node.direita, childIndent, true);
    }

}