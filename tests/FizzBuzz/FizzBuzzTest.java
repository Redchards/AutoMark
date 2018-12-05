import static org.junit.Assert.assertEquals;
import org.junit.Test;
import junit.framework.TestSuite;
import com.auto.mark.annotation.MarkedTest;
import fizz.buzz.*;

public class FizzBuzzTest {
	@MarkedTest (value = 1)
	public static void FizzBuzzTest() {
		assertEquals("Fizz",FizzBuzz.DoFizzBuzz(3));
		assertEquals("Buzz",FizzBuzz.DoFizzBuzz(5));
		assertEquals("FizzBuzz",FizzBuzz.DoFizzBuzz(15));
	}
	
	/*@MarkedTest (value = 2)
	public static void FizzBuzzTest2() {
		assertEquals("FizzBuzz",FizzBuzz.DoFizzBuzz(1530));
	}*/
	
}