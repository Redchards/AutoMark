package TestD.up5;

import java.util.ArrayList;


public class TestNumber {
	/**
	 * @return the biggest Number of an arrayList
	 * 
	 */
	public static Number getMaximum(ArrayList<Number> list) {
		Number n=list.get(0);
		for(int i= 1; i<list.size()-1;i++) {
			if(n.doubleValue()<list.get(i).doubleValue()) { //convert an Number into doubl
				n=list.get(i);
			}
		}
		
		return n;
	}
	
	public static void main(String[] args) {
		ArrayList<Number> list = new ArrayList<Number>();
		list.add(new Integer(6));
		list.add(new Double(8.2));
		list.add(new Short((short)10));
		list.add(new Integer(-16));
		list.add(new Float(4));
		System.out.println(getMaximum(list));
		System.out.println(getMaximum(list).getClass().getName());
		
	}
}
