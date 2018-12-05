package G.Ex_FileStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class G 
{
	/**
	 * Write 2 Integer into a file as a byte
	 * @param file Input File
	 * @param a 
	 * @param b
	 * @throws IOException
	 */
	public static void writeTwoBytes(File file,int a, int b) throws IOException {
		try(FileOutputStream st=new FileOutputStream(file)) {
		  st.write(a);
		  st.write(b);
		} catch (IOException e1) {
			throw new IOException("Erreur de lecture du fichier dans writeTwoBytes");
		}
	}
	
	/**
	 * 
	 * @return a String which represents the first 2 byte of the @param file
	 */
	public static String printBytesFromFIle(File file) throws IOException {
		String s="";
		try(FileInputStream st=new FileInputStream(file)) {
			  s+=st.read();
			  s+=" "+st.read();
			} catch (IOException e1) {
				throw new IOException("Erreur de lecture du fichier dans writeTwoBytes");
			}
		return s;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromFile(File file) throws IOException {
		String s="";
		byte[] b=new byte[2];
		try(FileInputStream st=new FileInputStream(file)) {
			  st.read(b);
			  s=new String(b);
			} catch (IOException e1) {
				throw new IOException("Erreur de lecture du fichier dans writeTwoBytes");
			}
		return s;
	}
	public static String getStringFromFile(File file,String endoding) throws IOException {
		String s="";
		byte[] b=new byte[2];
		try(FileInputStream st=new FileInputStream(file)) {
			  st.read(b);
			  s=new String(b,endoding);
			} catch (IOException e1) {
				throw new IOException("Erreur de lecture du fichier dans writeTwoBytes");
			}
		return s;
	}
}
