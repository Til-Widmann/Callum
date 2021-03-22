package com.imageScript;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;


import static ij.IJ.*;
import static ij.IJ.run;
import static ij.plugin.filter.Analyzer.setOption;

public class ImageJScript {


    private final double  MM_PER_PIXEL = 0.02117148921;

    public ParticleAnalyzerResult calcResult() {
        ImagePlus image = new ImagePlus("temp.tif");
//        image.show(); Show Images while editing is done.
        Calibration cal = image.getCalibration();
        cal.setUnit("mm");
        cal.pixelWidth = MM_PER_PIXEL;
        cal.pixelHeight = MM_PER_PIXEL;
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
                Measurements.AREA,
                resultsTable,
                30,
                2000
        );
        particleAnalyzer.analyze(image);
        image.close();

        float area = 0;
        for (float item : resultsTable.getColumn(0)) {
            area += item;
        }
        return new ParticleAnalyzerResult(area, resultsTable.size());
    }

    class ParticleAnalyzerResult{
        float area;
        int particleAmount;

        public ParticleAnalyzerResult(float area, int particleAmount) {
            this.area = area;
            this.particleAmount = particleAmount;
        }
    }
}
