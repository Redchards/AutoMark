import com.auto.mark.annotation.MarkedTest;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import TestD.up5.*;

public class APPTest 
{
  @MarkedTest(value = 1)
   public static void getMaximumTest( ) {
	   ArrayList<Number> list = new ArrayList<Number>();
	    list.add(new Integer(6));
		list.add(new Double(8.2));
		list.add(new Short((short)10));
		list.add(new Integer(-16));
		list.add(new Float(4));
	   assertEquals((short)10,(short)TestNumber.getMaximum(list).intValue());
   }
  @MarkedTest(value = 1) 
   public static void triSelecTest( ) {
		String [] tab1 = {"Gentoo","Archlinux","BSD","Fedora","Debian"};
		String [] tab2 = {"Archlinux","BSD","Debian","Fedora","Gentoo"};
	   assertEquals(tab2,Tri.triSelec(tab1));
   }
  @MarkedTest(value = 1)
   public static void triSelecTest2( ) {
	   String [] tab1 = {"LLG","LLO","LLA","LLQ","LLB"};
	   String [] tab2 = {"LLA","LLB","LLG","LLO","LLQ"};
	   assertEquals(tab2,Tri.triSelec(tab1));
   }
  @MarkedTest(value = 0.5)
   public static void indiceDuPlusPetitTest( ) {
	   String [] tab1 = {"Gentoo","Archlinux","Debian","Fedora","BSD"};
	   assertEquals(1,Tri.indiceDuPlusPetit(tab1, 0, 4));
   }
  @MarkedTest(value = 0.5)
   public static void echangeTest( ) {
	   String [] tab1 = {"Gentoo","Archlinux","Debian","Fedora","BSD"};
	   String [] tab2=Tri.echange(tab1, 1, 2);
	   assertEquals("Debian",tab2[1]);
	   assertEquals("Archlinux",tab2[2]);
   }
   

}
