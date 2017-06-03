package Avaliacao1;
import java.util.ArrayList;
import java.util.Scanner;

public class SixFlags {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ArrayList<Instancia> listaInstancias = new ArrayList<Instancia>();
		
		while (true) {
			String[] inicial = in.nextLine().trim().split(" ");
			if (Integer.valueOf(inicial[0]) == 0 || Integer.valueOf(inicial[1]) == 0) break;
			
			int numAtracoes = Integer.valueOf(inicial[0]);
			int tempoDisponivel = Integer.valueOf(inicial[1]);
			
			ArrayList<Atracao> atracoes = new ArrayList<Atracao>();
			
			for (int i = 0; i < numAtracoes; i++) {
				String leitura = in.nextLine();
				if (leitura == null) break;
				String[] dadosAtracao = leitura.trim().split(" ");;
				atracoes.add(new Atracao(Integer.valueOf(dadosAtracao[0]), Integer.valueOf(dadosAtracao[1])));
			}
			
			listaInstancias.add(new Instancia(numAtracoes, tempoDisponivel, atracoes));
		}
		
		// usando programacao dinamica com o problema da mochila
		// a mochila eh o limite de tempo
		// o peso dos itens e' o tempo gasto por cada atracao
		// o lucro seria os pontos, porem limitado pelo numero de atracoes e tempo
		for (int i = 0; i < listaInstancias.size(); i++) {
			solucao(listaInstancias.get(i), i + 1);
			if (i < listaInstancias.size() - 1)
				System.out.print("");
		}
		
		/*
		 * A complexidade de se construir uma tabela de programação dinâmica pode ser simplificada para (N x M)(T),
		 * onde N é o número de elementos, M é o limitador (no caso da mochila, o que ela consegue carregar) e T
		 * é a operação relevante.
		 * 
		 * No caso dessa rotina, a operação relevante consiste do custo da ordenação dos elementos pelo seu peso
		 * somado ao custo de percorrer um array bidimensional N x M, somado ao custo, por posicao do array,
		 * de uma comparacao (resumidamente) e operacoes aritmeticas.
		 */
	}
	
	public static void solucao (Instancia instancia, int index) {
		int tempoDisponivel = instancia.tempoDisponivel;
		int numAtracoes = instancia.numAtracoes;
		ArrayList<Atracao> listaAtracoes = instancia.atracoes;
		
		int limitador = tempoDisponivel/numAtracoes;
		int[][] d = new int[listaAtracoes.size()][numAtracoes + 1];
		int i, j, k;
		
		for (i = 0, j = 0; j <= numAtracoes; j++, i = j * limitador)
			d[0][j] = i < listaAtracoes.get(0).duracao ? 0 : (i / listaAtracoes.get(0).duracao) * listaAtracoes.get(0).pontuacao;
		
		/*
		for (i = 1; i < listaAtracoes.size(); i++) {
			for (j = 1; j < numAtracoes; j++) {
				if (j < ((j * limitador) / listaAtracoes.get(i).duracao))
					d[i][j] = d[i - 1][j];
				else
					d[i][j] = Math.max(d[i - 1][j], d[i - 1][j - ((j * limitador) / listaAtracoes.get(i).duracao)] + listaAtracoes.get(i).pontuacao);
			}
		}
		*/
		
		for (i = 1; i < listaAtracoes.size(); i++) {
			for (j = 1 * limitador, k = 1; k <= numAtracoes; k++, j = k * limitador) {
				if (j < listaAtracoes.get(i).duracao)
					d[i][k] = d[i - 1][k];
				else
					d[i][k] = Math.max(d[i - 1][k], d[i - 1][k - (listaAtracoes.get(i).duracao / limitador)] + listaAtracoes.get(i).pontuacao);
			}
		}
		
		System.out.println("Instancia " + index);
		System.out.println(d[listaAtracoes.size() - 1][numAtracoes]);
	}

}
