/**
 * 
 */
package com.mvl.luxsoft;

/**
 * @author Mohit
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class VowelAverage {

	private static Set<Character> vowels = new HashSet<>();
	static {
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
	}

	private static Map<Integer, Map<Set<Character>, List<Integer>>> vowelContainer = new HashMap<>();

	public static void main(String[] args) {
		try (Scanner in = new Scanner(new File(VowelAverage.class.getClassLoader().getResource("INPUT.TXT").getFile()));
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("OUTPUT.TXT")))) {
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (!line.isEmpty()) {
					String[] words = line.split(" ");
					for (String word : words) {
						String lowerCaseWord = word.toLowerCase();
						int vowelCount = 0;
						Set<Character> currentVowels = new HashSet<>();
						for (int i = 0; i < lowerCaseWord.length(); i++) {
							Character c = word.charAt(i);
							if (vowels.contains(c)) {
								vowelCount++;
								currentVowels.add(c);
							}
						}
						if (vowelContainer.containsKey(word.length())) {
							Map<Set<Character>, List<Integer>> m = vowelContainer.get(word.length());
							if (m.containsKey(currentVowels)) {
								m.get(currentVowels).add(word.length() / vowelCount);
							} else {
								List<Integer> l = new ArrayList<>();
								l.add(word.length() / vowelCount);
								m.put(currentVowels, l);
							}
						} else {
							List<Integer> l = new ArrayList<>();
							l.add(word.length() / vowelCount);
							Map<Set<Character>, List<Integer>> m = new HashMap<>();
							m.put(currentVowels, l);
							vowelContainer.put(word.length(), m);
						}
					}
				}
			}

			printOutput(vowelContainer, bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printOutput(Map<Integer, Map<Set<Character>, List<Integer>>> vowelContainer, BufferedWriter bw)
			throws IOException {
		if (null != vowelContainer && !vowelContainer.isEmpty()) {
			for (Map.Entry<Integer, Map<Set<Character>, List<Integer>>> e : vowelContainer.entrySet()) {
				Integer wl = e.getKey();
				Map<Set<Character>, List<Integer>> vc = e.getValue();
				for (Map.Entry<Set<Character>, List<Integer>> entry : vc.entrySet()) {
					Set<Character> vs = entry.getKey();
					List<Integer> avg = entry.getValue();
					bw.write("( " + vs + ", " + wl + " ) -> " + avg.stream().mapToInt(i -> i).average());
					bw.newLine();
//                    System.out.println("( " + vs + ", " + wl + " ) -> " + avg.stream().mapToInt(i -> i).average());
				}
			}
		}
	}
}