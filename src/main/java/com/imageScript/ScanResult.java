package com.imageScript;

import java.util.ArrayList;
import java.util.List;

public class ScanResult {
    String name;
    List<SheetResult> sheetResultList;

    public ScanResult(String name) {
        this.name = name;
        sheetResultList = new ArrayList<>();
    }

    public void addSheetResult(SheetResult sheetResult) {
        sheetResultList.add(sheetResult);
    }
}

class SheetResult {
    String QrName;
    List<Square> square;

    public SheetResult(String qrName) {
        QrName = qrName;
        square = new ArrayList<>();
    }
    public void addSquare(Square square){
        this.square.add(square);
    }
}
class Square {
    int number;
    float area;
    int particleAmount;

    public Square(int number, float area, int particleAmount) {
        this.number = number;
        this.area = area;
        this.particleAmount = particleAmount;
    }
}