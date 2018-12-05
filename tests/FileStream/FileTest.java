import G.Ex_FileStream.*;

import static org.junit.Assert.assertEquals;
import com.auto.mark.annotation.MarkedTest;
import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class FileTest {
   
	@MarkedTest (value = 1.0)
   public static void testWriteTwoBytes() {
	  File f =new File("test");
	  if(f.exists()) {
		  f.delete();
	  }
	  try {
		f.createNewFile();
		G.writeTwoBytes(f, 195, 169);
		String s=G.getStringFromFile(f).replaceAll("\r","").replaceAll("\n","");
		if(!((s.compareTo("é")==0) || (s.compareTo("Ã©")==0))) {
			Assert.fail();
		}
	} catch (IOException e) {
		Assert.fail();
	}
	  
   }
   
   @MarkedTest (value = 1.0)
   public static void getStringFromFileTest() {
	   File f =new File("test");
		  if(f.exists()) {
			  f.delete();
		  }
		  try {
			f.createNewFile();
			G.writeTwoBytes(f, 195, 169);
			assertEquals("é",G.getStringFromFile(f,"UTF-8"));
		} catch (IOException e) {
			Assert.fail();
		}
   }
   
   @MarkedTest (value = 1.0)
   public static void getStringFromFileTest2() {
	   File f =new File("test");
		  if(f.exists()) {
			  f.delete();
		  }
		  try {
			f.createNewFile();
			G.writeTwoBytes(f, 195, 169);
			assertEquals("Ã©",G.getStringFromFile(f,"ISO-8859-1"));
		} catch (IOException e) {
			Assert.fail();
		}
   }
}
