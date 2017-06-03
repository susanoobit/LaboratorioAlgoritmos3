package Avaliacao1;
import java.util.ArrayList;

public class Instancia {
	int numAtracoes, tempoDisponivel;
	ArrayList<Atracao> atracoes = new ArrayList<Atracao>();
	
	Instancia (int numAtracoes, int tempoDisponivel, ArrayList<Atracao> atracoes) {
		this.numAtracoes = numAtracoes;
		this.tempoDisponivel = tempoDisponivel;
		this.atracoes = atracoes;
		this.atracoes.sort((at1, at2) -> at1.duracao - at2.duracao); // ordenando asc por duracao (peso)
	}
}
