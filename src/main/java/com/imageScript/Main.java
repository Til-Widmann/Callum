package com.imageScript;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;

import java.util.Arrays;

import static ij.IJ.*;
import static ij.plugin.filter.Analyzer.setOption;


public class Main {

    public static void main(String args []) {
        skript();
    }


    public static void skript() {
        ImagePlus image = openImage("D:\\callum\\test.tif");
        IJ.run(image, "8-bit","");
        IJ.run(image, "Invert", "");
        IJ.run(image,
                "Bandpass Filter...",
                "filter_large=35 filter_small=10 suppress=None tolerance=5 autoscale saturate"
        );
        setAutoThreshold(image, "Default dark");
        IJ.run(image, "Threshold...","");
        setThreshold(image,0, 15);
        setOption("BlackBackground", true);
        run(image, "Convert to Mask", "");
        ResultsTable resultsTable = new ResultsTable();
        ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(
                ParticleAnalyzer.SHOW_NONE,
                Measurements.ALL_STATS,
                resultsTable,
                30,
                2000
        );
        particleAnalyzer.analyze(image);

        System.out.println(Arrays.toString(resultsTable.getColumn(0)));
        float sum = 0;
        for (float item : resultsTable.getColumn(0)) {
            sum += item;
        }
        System.out.println(sum);
    }
}