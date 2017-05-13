package Aulas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class LaughAnalyzer {
  public static void main(String[] args) {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String word = in.readLine();
      while (!word.equals("FIM")) {
        if (isPalindrome(word.replaceAll("[^aeiou]", ""))) {
          System.out.println("S");
        }
        else {
          System.out.println("N");
        }
        word = in.readLine();
      }
    }
    catch (IOException e) {}
  }

  public static boolean isPalindrome(String word) {
    char[] c = word.toCharArray();
    int leftIdx = 0;
    int rightIdx = c.length - 1;
    while (rightIdx > leftIdx) {
      if (c[leftIdx] != c[rightIdx]) {
        return false;
      }
      ++leftIdx;
      --rightIdx;
    }
    return true;
  }
}