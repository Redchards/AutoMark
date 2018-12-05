import EX5.CC2016.*;

import java.util.ArrayList;
import org.junit.Test;
import com.auto.mark.annotation.MarkedTest;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class TestCC2016 {
  
  @MarkedTest (value = 1.0)
  public static void TestNote(){
	  try{
		  Note note=new Note(22,30);
		  Assert.fail();
	  }catch(IllegalArgumentException e) {
	  }
  }
  @MarkedTest (value = 1.0)
  public static void TestNote2() {
	  try{
		  Note note=new Note(10,2.0);
		  Assert.assertEquals("10.0(coeff 2.0)",note.toString());
	  }catch(IllegalArgumentException e) {
		  Assert.fail();
	  }
  }
  
  @MarkedTest (value = 1.0)
  public static void TestgetMoyennePonderee() {
	  ArrayList<Note> list=new ArrayList<Note>();
	  list.add(new Note(20.0,1.0));
	  list.add(new Note(0.0,1.0));
	  Assert.assertEquals(new Note(10.0,2.0).toString(), UtilNote.getMoyennePonderee(list).toString());
  }
  
  @MarkedTest (value = 1.0)
  public static void TestlistNote() {
	  ListNote list=new ListNote();
	  list.add("test", 20, 5);
	  list.add("test", 0, 5);
	  try {
		Assert.assertEquals(new Note(10.0,10.0).toString(), list.getMoyenne("test").toString());
	} catch (Exception ex) {
		Assert.fail();
	}
	  
  }
  @MarkedTest (value = 1.0)
  public static void TestlistNote2() {
	  ListNote list=new ListNote();
		try {
			list.getMoyenne("test");
			Assert.fail();
		} catch (UnknownStudentException e){
		}
  }
  
  
	
	
	
  
}
