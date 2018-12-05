
import javafx.scene.control.TextField;
import junit.framework.*;
import com.auto.mark.annotation.MarkedTest;
import static org.junit.Assert.assertEquals;
import CC4.up5.CC4.EX3.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class CC2017Test {
	
	
	@MarkedTest(value = 1)
	public static void getStringFromListTest() {
		ArrayList<String> list=new ArrayList<String>();
		list.add("ab");
		list.add("cde");
		list.add("efg");
		assertEquals("cde\nefg\n",Util.getStringFromList(list, 1));
	}
	
	@MarkedTest(value = 1)
	public static void getContentFromTest() {
		ArrayList<String> sList=new ArrayList<String>();
		sList.add("MrX");
		sList.add("Bonjour");
		File file=new File("testmessages");
		File[] listFile=file.listFiles();
		try(BufferedReader br=new BufferedReader(new FileReader(listFile[0]))) {
			assertEquals(sList,Util.getContentFrom(br));
		}catch(IOException e) {
			Assert.fail();
		}
	}
	
	
	@MarkedTest(value = 1)
	public static void getDateLisibleTest() {
		assertEquals("23/12/2016",Util.getDateLisible("20161223"));
	}
	
	@MarkedTest(value = 1)
	public static void getDateLisibleTest2() {
		try{
			Util.getDateLisible("2sdqdqs0161ssdq223");
			Assert.fail();
		} catch(IllegalArgumentException e) {
		}
	} 
	
	@MarkedTest(value = 1)
	public static void getSommeTest() {
		assertEquals(10,CalcPane.getSomme("6", "4"));
	}
	
	
	@MarkedTest(value = 1)
	public static void getSommeTest2() {
		try{
			CalcPane.getSomme("6a", "s4");
			Assert.fail();
		} catch(NumberFormatException ex) {
		}
	}

	@MarkedTest(value = 1)
	public static void getMessageTest2() {
		File file=new File("messages");
		file.delete();
		file.mkdir();
		try {
			MessagePane.getMessage(file);
			Assert.fail();
		} catch (IOException e) {
		}
	}
	@MarkedTest(value = 2)
	public static void getMessageTest() {
		File file=new File("testmessages");
		File file2=new File("messages");
		try {
			Path path=Paths.get(("messages/19952004.txt"));
			file2.delete();
			file2.mkdir();
			Files.copy(file.listFiles()[0].toPath(), path);
			Message messTest2=MessagePane.getMessage(file2);
			Files.copy(file.listFiles()[0].toPath(), path);
			assertEquals("04/20/1995",messTest2.getDate());
			assertEquals("MrX",messTest2.getExpediteur());
			assertEquals("Bonjour",messTest2.getTexte());
		} catch (IOException e) {
			Assert.fail(file2.getPath());
		}
	}
	@MarkedTest(value = 0.5)
	public static void getContentFromFileTest() {
		File file=new File("testmessages");
		ArrayList<String> sList=new ArrayList<String>();
		sList.add("MrX");
		sList.add("Bonjour");
		try {
			assertEquals(sList,MessagePane.getContentFromFile(file.listFiles()[0]));
		} catch (IOException e) 
		{
			Assert.fail();
		}
		
	}
}
