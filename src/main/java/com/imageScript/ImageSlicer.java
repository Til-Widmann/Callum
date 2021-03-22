package com.imageScript;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class ImageSlicer {


    Map<String, BufferedImage> sheetList;
    List<Point> points;
    Iterator<String> bufferedImageIterator;

    private final int SQUARE_SIZE = 1417;

    public ImageSlicer(Map<String, BufferedImage> sheetList) {
        this.sheetList = sheetList;
        initCoordinates();
        bufferedImageIterator = this.sheetList.keySet().iterator();
    }

    public boolean hasNext(){
        return bufferedImageIterator.hasNext();
    }


    public Sheet nextSheet(){
        String currentKey = bufferedImageIterator.next();
        BufferedImage currentImage = sheetList.get(currentKey);
        String qrCode = currentKey;//decodeQRCode(currentImage);

        return new Sheet(qrCode, getSquares( currentImage));

    }

    public List<BufferedImage> getSquares(BufferedImage currentSheet){
        List<BufferedImage> squares = new ArrayList<>();
        this.points.forEach((point) -> {
            squares.add(currentSheet.getSubimage(point.x + 236, point.y + 236, SQUARE_SIZE, SQUARE_SIZE));
        });
        return squares;
    }

    private void initCoordinates(){
        this.points = new ArrayList<>();
		points.add(new Point(401,490));
		points.add(new Point(2781,490));
		points.add(new Point(5165,490));
		points.add(new Point(7539,490));
		points.add(new Point(401,2851));
		points.add(new Point(2781,2851));
		points.add(new Point(5165,2851));
		points.add(new Point(7539,2851));
		points.add(new Point(401,5216));
		points.add(new Point(2781,5216));
		points.add(new Point(5165,5216));
		points.add(new Point(7539,5216));
		points.add(new Point(401,7579));
		points.add(new Point(2781,7579));
		points.add(new Point(5165,7579));
		points.add(new Point(7539,7579));
	}

//    private static String decodeQRCode(BufferedImage image) {
//        int qrCodeXPos = 2000;
//        int qrCodeYPos = 10000;
//        int size = 2500;
//        BufferedImage qrCodeCrop = image.getSubimage(qrCodeXPos, qrCodeYPos, size, size);
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Map<DecodeHintType, Boolean> hints = new HashMap<>();
//        hints.put(DecodeHintType.TRY_HARDER, true);
//        display(qrCodeCrop);
//
//        try {
//
//            Result result = new MultiFormatReader().decode(bitmap, hints);
//            return result.getText();
//        } catch (Exception e) {
//            System.out.println("There is no QR code in the image");
//            return "qr code not read";
//        }
//    }
//    public static void display(BufferedImage image){
//        JFrame frame = null;
//        JLabel label = null;
//        if(frame==null){
//            frame=new JFrame();
//            frame.setTitle("stained_image");
//            frame.setSize(400, 400);
//            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            label=new JLabel();
//            label.setIcon(new ImageIcon(image));
//            frame.getContentPane().add(label,BorderLayout.CENTER);
//            frame.setLocationRelativeTo(null);
//            frame.pack();
//            frame.setVisible(true);
//        }else label.setIcon(new ImageIcon(image));
//    }

    public class Sheet {
        String qrCode;
        List<BufferedImage> imageList;

        public Sheet(String qrCode, List<BufferedImage> imageList) {
            this.qrCode = qrCode;
            this.imageList = imageList;
        }
    }


}

