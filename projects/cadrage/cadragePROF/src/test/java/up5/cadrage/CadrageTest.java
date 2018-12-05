package up5.cadrage;

import static org.junit.Assert.*;

import org.junit.Test;

import up5.cadrage.Cadrage;

/**
 * Unit test for Cadrage
 */
public class CadrageTest 
   
{
   @Test
   public  void cadrerAGaucheTest( ) {
	   assertEquals("azertxxxxx",new Cadrage('x').cadrerAGauche("azert",10));
   }
   
   @Test
   public  void cadrerADroiteTest( ) {
	   assertEquals("xxxxxazert",new Cadrage('x').cadrerADroite("azert",10));
   }
   @Test
   public  void cadrerADroiteTronque( ) {
	   assertEquals("aze",new Cadrage('x').cadrerADroite("azert",3));
   }
   @Test
   public  void cadrerAGaucheTronque( ) {
	   assertEquals("aze",new Cadrage('x').cadrerAGauche("azert",3));
   }
   

}
