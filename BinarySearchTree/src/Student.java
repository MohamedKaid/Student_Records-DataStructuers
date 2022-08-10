/**
 * Description: A class that represents a student 
 *
 * @author (COSC 311, W'22)
 * @version (1-13-22)
 */


import java.io.*;
import java.util.*;

public class Student {
	/** A student has a first name (fName), a last name (lName),
	 * an id (ID), and a gpa (GPA).
	 */
	private final int LENGTH = 20;
	private String fName;
	private String lName;
	private int ID;
	private double GPA;
	private final int RECSIZE = 92;
	
	
	/**
	 * Set the student info
	 * @param first the first name
	 * @param last the last name
	 * @param id the ID
	 * @param gpa the GPA
	 */
	public void setData (String first, String last, int id, double gpa){
		fName = pad (first,LENGTH); lName = pad (last, LENGTH); ID = id; GPA = gpa;
	}

	/**
	 * set the student first name
	 * @param first the first name
	 * @return none
	 */
	public void setFirst(String first){
		fName = pad (first, LENGTH);
	}
	
	/**
	 * set the student last name
	 * @param last the last name
	 * @return none
	 */
	public void setLast(String last){
		lName = pad (last, LENGTH);
	}
	
	/**
	 * set the student ID 
	 * @param id the ID
	 * @return none
	 */
	public void setID(int id){
		ID = id;
	}
	
	/**
	 * set the student GPA 
	 * @param gpa the GPA
	 * @return none
	 */
	public void setGPA(double gpa){
		GPA = gpa;
	}
	
	/**
	 * get the student first name
	 * @return a string representing the first name
	 */
	public String getFirst(){
		return fName;
	}
	
	/**
	 * get the student' last name
	 * @return a string representing the last name
	 */
	public String getLast(){
		return lName;
	}
	
	/**
	 * get the student ID
	 * @return an int representing the ID
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * get the student GPA
	 * @return a string representing the GPA
	 */
	public double getGPA(){
		return GPA;
	}
	
	/**
	 * get the student record size
	 * @return an int representing the record size
	 */
	public int size(){
		return RECSIZE;
	}
	
	public int length(){
		return LENGTH;
	}
	
	/**
	 * Determine if two students have the same ID
	 * @return true if same ID, otherwise return false
	 */
	public boolean equals(Object other){
		if (other == null)
			return false;
		else if (getClass() != other.getClass())
			return false;
		else
			return ID == ((Student)other).ID;
	}

	/**
	 * Write a student object (or record) to the random access file
	 * @param out the random access file
	 * @throws IOException
	 */
	public void writeToFile(RandomAccessFile out) throws IOException {
		writeString(out, fName,LENGTH );
		writeString(out, lName, LENGTH);
		out.writeInt(ID);
		out.writeDouble(GPA);
	}
	
	/**
	 * Read a student object (or record) from the random access file
	 * @param in the random access file
	 * @throws IOException
	 */
	public void readFromFile(RandomAccessFile in)throws IOException {
		fName = readString(in, LENGTH); 
		lName = readString(in, LENGTH); 
		ID = in.readInt();
		GPA = in.readDouble();
	}
	
	/**
	 * Read size characters from the random access file
	 * @param in the random access file
	 * @param size the number of characters to read
	 * @return a string representing the characters read
	 * @throws IOException
	 */
	private String readString(RandomAccessFile in, int size)throws IOException{
		char [] temp = new char [size];
		for (int i =0; i<temp.length; i++)
			temp[i] = in.readChar();
		return new String(temp);
	}
	
	/**
	 * Write size characters to the random access file
	 * @param out the random access file
	 * @param str the characters to write
	 * @param size number of character s to write
	 * @throws IOException
	 */
	private void writeString(RandomAccessFile out, String str, int size)throws IOException{
		char [] temp = new char [size];
		temp = str.toCharArray();
		for (int i=0; i<temp.length; i++)
			out.writeChar(temp[i]);
	}
	
	/**
	 * Read a student info from a text file and pad the first and last names with a blank(s) if needed
	 * @param in the text file
	 * @throws IOException
	 */
	public void readFromTextFile(Scanner in) throws IOException {
		fName = in.next();
		lName = in.next();
		ID = in.nextInt();
		GPA = in.nextDouble();
		fName = pad (fName, LENGTH);
		lName = pad (lName, LENGTH);
	}
	
	/**
	 * Padding a string with trailing blank(s) 
	 * @param s the string
	 * @param size the length of the resulting string
	 * @return a string of length size
	 */
	public static String pad (String s, int size){
		if (s.length() > size) s = s.substring(0,size);
		else {
			for (int i = s.length(); i <size; i++)
				s += ' ';
		}
		return s;
	}
	
	/**
	 * Create and return a string that represents a student 
	 * @return a string representing a student
	 */
	public String toString(){
		return String.format("%-20s",fName) + String.format("%-20s",lName) +  
				   String.format("%-10s",ID) + String.format("%-10s",GPA);
	}	
}

