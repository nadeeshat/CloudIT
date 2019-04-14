package com.cloudit;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

class QrGenerator {
	
	/* Generate QR Code as a png image and write to the specified file path 
	public void generateQrImage(String text, int width, int height, String filePath) throws WriterException, IOException
	{
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
	} */
	
	/* Generate QR Code as a png image and return as a byte array */
	public byte[] getQrCodeBytes(String text, int width, int height) throws WriterException, IOException
	{
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}
	
	/* Generate QR Code as a png image and save the image in a specified directory with the file name as User Id 
	public void getQrCodeImage(byte[] data, String id) throws IOException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
	    BufferedImage bImage2 = ImageIO.read(bis);
	    
	    ImageIO.write(bImage2, "png", new File("C:\\Users\\tngar\\eclipse-workspace\\cloudit\\cloudit\\WebContent\\views\\qrcodes\\"+id+".png"));
	    System.out.println("image created");
		
	} */
	
	/* Generate QR Code as a png image and save the image in a specified directory with the file name as User Id */
	public BufferedImage getQrCodeImage(byte[] data) throws IOException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
	    BufferedImage bImage2 = ImageIO.read(bis);
	  
	    System.out.println("image passed to servlet");
		return bImage2;
		
	}
}
