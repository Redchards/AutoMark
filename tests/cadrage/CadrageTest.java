import com.auto.mark.annotation.MarkedTest;
import static org.junit.Assert.assertEquals;

import up5.cadrage.Cadrage;

/**
 * Unit test for Cadrage
 */
public class CadrageTest 
{
   @MarkedTest(value = 3.0)
   public static void cadrerAGaucheTest( ) {
	   assertEquals("azertxxxxx",new Cadrage('x').cadrerAGauche("azert",10));
   }
   
   @MarkedTest(value = 2.0)
   public static void cadrerADroiteTest( ) {
	   assertEquals("xxxxxazert",new Cadrage('x').cadrerADroite("azert",10));
   }

   @MarkedTest(value = 1.5)
   public static void cadrerADroiteTronque( ) {
	   assertEquals("aze",new Cadrage('x').cadrerADroite("azert",3));
   }
   
   @MarkedTest(value = 1.5)
   public static void cadrerAGaucheTronque( ) {
	   assertEquals("aze",new Cadrage('x').cadrerAGauche("azert",3));
   }
   

}
