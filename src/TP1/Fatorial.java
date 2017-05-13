package TP1;
import java.math.BigInteger;
import java.util.Scanner;

public class Fatorial {
	
	public static BigInteger fat(BigInteger n) {
		if (n.compareTo(BigInteger.ONE) == 1)
			return n.multiply(fat(n.subtract(BigInteger.ONE)));
		else
			return BigInteger.ONE;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Digite um numero inteiro positivo para calculo do fatorial: ");
		System.out.println("O valor do fatorial eh: " + fat(BigInteger.valueOf(in.nextLong())).toString());
		in.close();
	}

}
