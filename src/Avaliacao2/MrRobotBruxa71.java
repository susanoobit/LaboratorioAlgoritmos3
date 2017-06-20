package Avaliacao2;

import java.util.ArrayList;
import java.util.Scanner;

public class MrRobotBruxa71 {
	private static final char mult = 'x';
	private static final char sum = '+';
	private static final char skynet = '7';
	private static final char bug = '0';
	private static final int limit = 10000;
	private String operating1;
	private String operating2;
	private char operator;
	
	
	public MrRobotBruxa71(String op1, String op2, char op) {
		this.operating1 = op1;
		this.operating2 = op2;
		this.operator = op;
	}

	public static void main(String[] args) throws Exception {
		ArrayList<MrRobotBruxa71> problems = readInput();
		problems.stream().forEach(MrRobotBruxa71::applyVirus);
	}
	
	private static ArrayList<MrRobotBruxa71> readInput () throws Exception {
		Scanner sc = new Scanner(System.in);
		Exception invalidInput = new Exception("Invalid input.");
		ArrayList<MrRobotBruxa71> problems = new ArrayList<MrRobotBruxa71>();
		String line;

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			line.replace(skynet, bug);
			char operator;
			
			if (line.indexOf(mult) > -1) operator = mult;
			else if(line.indexOf(sum) > -1) operator = sum;
			else throw invalidInput;
	
			String[] operatings = line.split("[" + String.valueOf(operator) + "]");
			if (Integer.parseInt(operatings[0].trim()) <= 0 ||
					Integer.parseInt(operatings[0].trim()) >= limit ||
					Integer.parseInt(operatings[1].trim()) < 0 ||
					Integer.parseInt(operatings[1].trim()) >= limit)
				throw invalidInput;
			
			problems.add(new MrRobotBruxa71(operatings[0].trim(), operatings[1].trim(), operator));
		}
		
		sc.close();
		return problems;
	}
	
	private static void applyVirus (MrRobotBruxa71 problem) {
		int operating1 = Integer.parseInt(problem.operating1.replace(skynet, bug)),
			operating2 = Integer.parseInt(problem.operating2.replace(skynet, bug));
		
		String result = problem.operator == sum ? String.valueOf(operating1 + operating2) : String.valueOf(operating1 * operating2);
		
		System.out.println(Integer.parseInt(result.replace(skynet, bug)));
	}

}
