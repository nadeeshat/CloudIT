package com.cloudit;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import com.google.api.client.util.Base64;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

class CreateQrModel {

	String uId;
	String uName;
	String uEmail;
	String uAdd;
	String randomNumber;
	DbUtil util = null;
	QrGenerator qrg = null;
	
	public CreateQrModel(String id)
	{
		this.uId = id;
		util = new DbUtil();
		qrg = new QrGenerator();
	}

	public CreateQrModel(String id, String name, String email, String add) {
		
		this.uId = id;
		this.uName = name;
		this.uEmail = email;
		this.uAdd = add;
		util = new DbUtil();
		qrg = new QrGenerator();
	}
	
	public void createRandom()
	{
		randomNumber = Double.toString(Math.random());
	}
	
	public String readQrFile() throws IOException 
	{
		String fileName = "QR-Code-"+this.uId+".txt";
		File file = new File("C:\\Users\\tngar\\eclipse-workspace\\cloudit\\cloudit\\WebContent\\views",fileName);
		String fileText = null;
		
		if(file.exists())
		{
			BufferedReader in = new BufferedReader(new FileReader(file));
			fileText = in.readLine();
		}
		else
		{
			fileText = "N/A";
		}
		
		return fileText;
	}
	
//	public void retrieveQrFile() throws ClassNotFoundException, SQLException, IOException
//	{
//		util.getConnection();
//		
//		String sql = "SELECT * FROM tbluserdata WHERE id='"+this.uId+"';";
//		
//		ResultSet myRs = util.selectQuery(sql);
//		
//		byte[] imgArray = null;
//		
//		String qrCodeStringRead = null;
//		
//		while(myRs.next())
//		{   qrCodeStringRead = myRs.getString("qrcode"); //Read the QR Code string from the result set and assign in to a String variable		
//			imgArray = qrCodeStringRead.getBytes(); //Convert the String to byte array
//		}
//		
//		byte[] base64Decoded = Base64.decodeBase64(imgArray); //Decode the base64 byte array in to original byte array
//		
//		//System.out.println(qrCodeStringRead);
//		//System.out.println("Reading array byte[] version:"+base64Decoded);
//		
//		qrg.getQrCodeImage(base64Decoded, this.uId); //Pass the original byte array and userId to create and save the QR Code as a png file with userId as the file name
//		
//		util.closeConnection();
//		
//	}
	
	public BufferedImage retrieveQrFile() throws ClassNotFoundException, SQLException, IOException
	{
		util.getConnection();
		
		String sql = "SELECT * FROM tbluserdata WHERE id='"+this.uId+"';";
		
		ResultSet myRs = util.selectQuery(sql);
		
		byte[] imgArray = null;
		
		String qrCodeStringRead = null;
		
		while(myRs.next())
		{   qrCodeStringRead = myRs.getString("qrcode"); //Read the QR Code string from the result set and assign in to a String variable		
			imgArray = qrCodeStringRead.getBytes(); //Convert the String to byte array
		}
		
		byte[] base64Decoded = Base64.decodeBase64(imgArray); //Decode the base64 byte array in to original byte array
		
		//System.out.println(qrCodeStringRead);
		//System.out.println("Reading array byte[] version:"+base64Decoded);
		
		BufferedImage bi = qrg.getQrCodeImage(base64Decoded); //Pass the original byte array
		
		util.closeConnection();
		
		return bi;
	}
	
	public void createQrFile() throws IOException, SQLException, ClassNotFoundException, WriterException
	{			
		String userData = this.uId+" "+this.uName+" "+this.uEmail+" "+this.uAdd; //Create a String with all the details of logged in user
		
		byte[] qrImg = qrg.getQrCodeBytes(userData, 350, 350); //Pass the user data, generate the QR code and get the byte array of the generated QR code
		
		byte[] base64Encoded = Base64.encodeBase64(qrImg); //Encode the byte array of the QR code as base64 byte array
		
		String qrCodeStringWrite = new String(base64Encoded); //Convert the base64 byte array to String
		
		//System.out.println(arrayStringWrite);
		//System.out.println("Writing array byte[] version:"+qrImg);
		
		util.getConnection();
		
		int updatedRows = 0;
		
		/* Update the tbluserdata by saving the qrcode as a string */
		String sql = "UPDATE tbluserdata SET qrcode='"+qrCodeStringWrite+"' WHERE id='"+this.uId+"';";
		updatedRows = util.updateQuery(sql);
		
		if(updatedRows>0)
		{
			System.out.println("Successfully updated the QR Code!");
		}
		
		util.closeConnection();		
	}
	
	public int createQrHash(String productData) throws WriterException, IOException, ClassNotFoundException, SQLException
	{			
		byte[] prodQrImg = qrg.getQrCodeBytes(productData, 350, 350); //Pass the product data, generate the QR code and get the byte array of the generated QR code
		
		byte[] base64Encoded = Base64.encodeBase64(prodQrImg); //Encode the byte array of the QR code as base64 byte array
		
		String prodQrHash = new String(base64Encoded); //Convert the base64 byte array to String
		
		//System.out.println(arrayStringWrite);
		//System.out.println("Writing array byte[] version:"+qrImg);
		
		util.getConnection();
		
		int insertedRows = 0;
		
		/* Update the tbluserdata by saving the qrcode as a string */
		String sql = "INSERT INTO tblproductdata (productqrcode, productdetails, user) VALUES ('"+prodQrHash+"', '"+productData+"', '"+this.uId+"');";
		insertedRows = util.updateQuery(sql);
		
		if(insertedRows>0)
		{
			System.out.println("Successfully inserted the product QR Code under the user profile!");
		}
		
		util.closeConnection();	
		return insertedRows;
	}
	
	public String decodeQrCode(BufferedImage bi)
	{
		LuminanceSource source = new BufferedImageLuminanceSource(bi);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
	}
}
