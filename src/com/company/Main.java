package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        int combination = 0;
        int rodLength = 0;
        Map<Integer,Integer> map;


        Scanner scanner = new Scanner(System.in);
        ArrayList<Piece> pieces = new ArrayList<>();

        System.out.println("Read the file?: Yes/No");
        String answer = scanner.next();
        answer = answer.trim().toLowerCase();
        if(answer.contains("y")) {

            System.out.println("Set the file path: ");
            String path = scanner.next();
            pieces = readCsv(path);

            for (Piece piece: pieces) {
                System.out.println(piece.length + " - " + piece.quantity);
            }
            System.out.println("\n******** END SETUP ARRAY ********");
            System.out.print("Set the Rod Length: ");
            rodLength = scanner.nextInt();
        } else  {

            System.out.print("Set the Rod Length: ");
            rodLength = scanner.nextInt();

            System.out.print("Set cut Types:  ");
            int cuts = scanner.nextInt();

            for (int cut = 0; cut < cuts; cut++) {
                System.out.print("Cut Length: ");
                int length = scanner.nextInt();
                System.out.print("Cut quantity: ");
                int quantity = scanner.nextInt();

                pieces.add(new Piece(length,quantity));
            }
        }


        CuttingStock  cuttingStock = new CuttingStock(pieces,rodLength);
        while (cuttingStock.hasMoreCombinations()) {
            System.out.println("\n Combination # "+ (++combination));
            map = cuttingStock.nextCombination();
            for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key+" * "+value);
            }
        }
        System.out.println("##########################################");
        cuttingStock.calculateWaste();

    }

    public static ArrayList<Piece> readCsv(String csvFile) {
        ArrayList<Piece> pieces = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";

        System.out.println("\n******** SETUP ARRAY ********");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] piece = line.split(cvsSplitBy);

                pieces.add(new Piece(Integer.parseInt(piece[0]),Integer.parseInt(piece[1])));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pieces;
    }
}


