package com.company;

import java.util.*;

public class CuttingStock {

    private ArrayList<Piece> pieces = new ArrayList<>();
    @SuppressWarnings("unused")
    private int max, total, counter = 0, waste = 0;
    private int comb[];
    private int tempComb[];
    private int limit[];
    private int pieceCut[];
    private int pieceQuantity[];
    private Object[] waste_array;
    private List<Map<Integer, Integer>> mapList = new ArrayList<>();
    private List<Integer> store = new ArrayList<>();
    private int count = 0;


    public CuttingStock(ArrayList<Piece> pieces, int max) {

        for (Piece piece : pieces) {
            if (piece.length > max) {
                throw new InvalidLengthException(piece.length);
            }
        }
        this.total = pieces.size();
        this.pieces = pieces;
        this.max = max;
        this.start();

    }

    private void start() {
        store = new ArrayList<>();
        waste = 0;
        counter = 0;

        //Sort elements Bigger to Smaller
        this.sort();
        pieceCut = new int[pieces.size()];
        pieceQuantity = new int[pieces.size()];
        for (int i = 0; i < pieces.size(); i++) {
            pieceCut[i] = pieces.get(i).getLength();
            pieceQuantity[i] = pieces.get(i).getQuantity();
        }

        System.out.println("**************** START CALCULATION  ******************");
        this.calculate(store);
        waste_array = store.toArray();
    }


    private void sort() {
        Collections.sort(pieces);
    }

    private void calculate(List<Integer> store) {
        initLimit();
        boolean start = true, chaloo = true;
        int best = 0, sum = 0;
        comb = new int[total];
        while (start) {
            this.combinations();
            sum = 0;
            for (int i = 0; i < total; i++) {
                sum += pieceCut[i] * comb[i];
                if (sum > max) {
                    sum = 0;
                    break;
                }
            }
            if (sum > 0) {
                if (sum == max) {
                    this.showCombination(0, store);
                    resetCombinations();
                    updateLimit();
                    best = 0;
                    sum = 0;
                } else if (sum > best) {
                    best = sum;
                    tempComb = new int[total];
                    for (int i = 0; i < total; i++) {
                        tempComb[i] = comb[i];
                    }
                    sum = 0;
                }
            }
            for (int i = 0; i < total; i++) {
                if (comb[i] != limit[i]) {
                    chaloo = true;
                    break;
                }
                chaloo = false;
            }

            if (!chaloo) {
                this.showCombination(best, store);
                updateLimit();
                resetCombinations();
                best = 0;
            }
            for (int i = 0; i < total; i++) {
                if (pieceQuantity[i] == 0 && i != total - 1) {
                    continue;
                } else if (i == total - 1 && pieceQuantity[i] == 0) {
                    start = false;
                }
                break;
            }


        }
    }

    public void calculateWaste() {
        if (waste_array.length > 0) {
            System.out.println("Consider reusing the following remains");
            for (int i = waste_array.length - 1; i >= 0; i--) {
                System.out.println((this.counter + i - waste_array.length + 1) + " " + waste_array[i]);
            }
        }
        System.out.println("# of pieces required: " + this.counter);
        System.out.println("Waste: " + this.waste);
    }

    private void showCombination(int type, List<Integer> store) {
        counter++;
        boolean flag = false;

        if (type == 0) {
            Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < total; i++) {
                if (comb[i] != 0) {
                    tempMap.put(pieceCut[i], comb[i]);
                    pieceQuantity[i] = pieceQuantity[i] - comb[i];
                    if (pieceQuantity[i] - comb[i] < 0) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                mapList.add(tempMap);
                return;
            }
            showCombination(0, store);
        } else {
            Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < total; i++) {
                if (tempComb[i] != 0) {
                    tempMap.put(pieceCut[i], tempComb[i]);
                }
            }
            mapList.add(tempMap);
            waste += max - type;
            store.add(max - type);
            for (int i = 0; i < total; i++) {
                pieceQuantity[i] = pieceQuantity[i] - tempComb[i];
            }

            for (int i = 0; i < total; i++) {
                if ((pieceQuantity[i] - comb[i]) < 0)
                    return;
            }
            showCombination(0, store);
        }
    }

    private void initLimit() {
        int div;
        limit = new int[total];
        for (int i = 0; i < total; i++) {
            div = max / pieceCut[i];
            if (pieceQuantity[i] > div) {
                limit[i] = div;
            } else {
                limit[i] = pieceQuantity[i];
            }

        }
    }

    private void updateLimit() {
        for (int i = 0; i < total; i++) {
            if (pieceQuantity[i] < limit[i]) {
                limit[i] = pieceQuantity[i];
            }
        }
    }

    private void combinations() {
        for (int i = total - 1; ; ) {
            if (comb[i] != limit[i]) {
                comb[i]++;
                break;
            } else if (i == 0 && comb[0] != limit[0]) {
                i = total - 1;

            } else {
                comb[i] = 0;
                i--;
            }
        }
    }

    public boolean hasMoreCombinations() {
        return count < counter;
    }

    public synchronized Map<Integer, Integer> nextCombination() {
        Map<Integer, Integer> map = mapList.get(count);
        counter = mapList.size();
        int counters = count + 1;
        if (mapList.size() > counters) {
            count++;
        } else {
            counter = mapList.size() - 1;
        }
        return map;
    }

    private void resetCombinations() {
        for (int i = 0; i < total; i++) {
            comb[i] = 0;
        }
    }
}
