/**
 * Author:  Mohamed Kaid
 * Created: 1.16.2022
 * About: it will make an index using a Binary Search Tree and order the contents of a dataBase 
 * in a node consisting of a pair (key,address), and it will allow the user to do multiple functions with it 
 * 
 **/

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	private static BST<Pair<Integer>> tree = new BST<>();
	private static Hashing<Pair<Integer>> Htable = new Hashing<>();
	private static boolean randChecker = false;
	private static boolean dataChecker = false;
	private static String outFile=null;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int studentNum=0;
		//variables 
		int choice=0;
		System.out.println("Programmer:        Mohamed Kaid\r\n"
				+ "Course:            COSC 311,  W '22\r\n"
				+ "Project:           5\r\n"
				+ "Due date:          4-19-22");

		System.out.println("Welcome!!");

		do {
			//display the menu
			displayMenu();
			while(true) {
				try {
					System.out.print("Choice: ");
					choice = input.nextInt();
					break;
				}catch(InputMismatchException e) {
					System.out.println("Invalid Entry please chose between 1-9\n");
					input.nextLine();
				}
			}


			if(choice == 1) {
				outFile = CreateRandomFile();
				studentNum =studentCounter(outFile);
				randChecker=true;
			}
			if(randChecker == true) {
				if(choice == 2) {
					readRandomFile(studentNum);
				}else if(choice == 3) {
					Htable = buildIndex();
					dataChecker = true;
				}
				if(dataChecker==true) {
					if(choice == 4) {
						displayIndex(Htable);
					}else if(choice == 5) {
						getRecord(Htable,outFile,studentNum);
						System.out.println();
					}else if(choice == 6) {
						modifyRecord(Htable,outFile,studentNum);
					}else if(choice == 7) {
						addRecord(Htable,outFile,studentNum);
					}else if(choice == 8){
						deleteRecord(Htable,outFile,studentNum);
					}
				}else if(dataChecker==false && randChecker == true) {
					System.out.println("Build the data base before you continue\n");
				}
			}else{
				System.out.println("Create Random Acssess file first\n");
			}
			if(choice<0 || choice>9){
				System.out.println("Invalid Entry please chose between 1-9\n");
			}
		}while(choice!=9);
		if(Htable==null) {
			System.out.println("Table is Empty!");
			System.out.println("Thank you!! Have a nice day");
		}else {
			Htable.finalPrint();
			System.out.println("Thank you!! Have a nice day");
		}
	}
	///------------------------------------------------------------------//////
	/** 
	 * Displays a Menu for the user
	 * @param  none
	 * @return none 
	 */
	public static void displayMenu() {
		System.out.println("1- Create a random-access file \r\n"
				+ "2- Display a random-access file \r\n"
				+ "3- Build the index \r\n"
				+ "4- Display the index \r\n"
				+ "5- Retrieve a record  \r\n"
				+ "6- Modify a record  \r\n"
				+ "7- Add a new record \r\n"
				+ "8- Delete a record \r\n"
				+ "9- Exit ");
	}

	///------------------------------------------------------------------//////
	/** 
	 * Creates a Random Access file using a text file chosen by the user
	 * @param  none
	 * @return none 
	 */
	public static String CreateRandomFile() {
		//Student Object to store the info from the txt file
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		System.out.println("Creating new Random File");
		System.out.print("Enter an Input File: ");
		String inFile = input.nextLine();
		System.out.print("Enter an output file: ");
		String outFile = input.nextLine();

		File inputFile = new File(inFile);

		try(
				//make a new Random Access File 
				RandomAccessFile rand = new RandomAccessFile(outFile, "rw");
				//Scanner to read the txt file
				Scanner scan = new Scanner(inputFile);
				){

			rand.setLength(0);
			//while statement that reads form the txt and write to the randomFile
			while(scan.hasNext()) {
				student.readFromTextFile(scan);
				student.writeToFile(rand);
			}

			//to notify that the file is created
			System.out.println("Data sent to File\n");

		} catch (FileNotFoundException e) {
			System.out.println("File not Found\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return outFile;

	}
	///------------------------------------------------------------------//////

	///------------------------------------------------------------------//////
	/** 
	 * Creates a Random Access file using a text file chosen by the user
	 * @param  integer student counter
	 * @return true  
	 */
	public static boolean readRandomFile(int studentNum) {
		//student object and scanner
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		String outFile = null,choice=null;
		int counter = 1;
		//ask for the random access file name to open it
		System.out.print("Enter Random Access File Name: ");
		outFile = input.nextLine();
		try(
				//open random access file
				RandomAccessFile rand = new RandomAccessFile(outFile, "rw");
				){
			try {
				System.out.println("Display Records");

				for(int x=0;x<5;x++) {	
					//reads the access file
					student.readFromFile(rand);
					//checks if the current record is not DELETED
					//if it is then it will jump back to label check
					if(student.getFirst().trim().equals("DELETED")) {
						student.readFromFile(rand);
						counter++;
					}
					if(student.getFirst().trim().equals(null) && student.getLast().trim().equals(null)) {
						System.out.println("DataBase was Empty!");
					}
					if(!student.getFirst().trim().equals("DELETED")) {
						System.out.println(student.toString());
						counter++;
					}
				}System.out.println();
				System.out.println("What would you like to do next: "
						+ "\nM: to return to main menu"
						+ "\nN: for next screen"
						+ "\nA: to display all");
				System.out.print("Choice: ");
				choice = input.nextLine();
				if(choice.equalsIgnoreCase("m")) {
					System.out.println();
					return true;	
				}else if(choice.equalsIgnoreCase("n")) {
					for(int x=0;x<5;x++) {	
						//reads the access file
						student.readFromFile(rand);
						//checks if the current record is not DELETED
						//if it is then it will jump back to label check
						if(student.getFirst().trim().equals("DELETED")) {
							student.readFromFile(rand);
							counter++;
						}
						if(!student.getFirst().trim().equals("DELETED")) {
							System.out.println(student.toString());
							counter++;
						}
					}
				}else if(choice.equalsIgnoreCase("a")) {
					while (true) {
						//reads the access file
						student.readFromFile(rand);
						//checks if the current record is not DELETED
						//if it is then it will jump back to label check
						if(student.getFirst().trim().equals("DELETED")) {
							student.readFromFile(rand);
							counter++;
						}
						if(!student.getFirst().trim().equals("DELETED")) {
							System.out.println(student.toString());
							counter++;
						}
					}
				}else
					System.out.println("Invalid Entry pleas chose (N,M,A)\n");
			}catch(EOFException e) {
				System.out.println("File done\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		return true;
	}

	///------------------------------------------------------------------//////
	///------------------------------------------------------------------//////
	/** 
	 * Creates a Hash Table using the Random Access File chosen by the user
	 * @param  none
	 * @return Binary Search Tree
	 */
	public static Hashing<Pair<Integer>> buildIndex() {
		Scanner input = new Scanner(System.in);
		String dataBase;
		int position = 0;

		Student student = new Student();
		Hashing<Pair<Integer>> temp = new Hashing<>();
		try {
			System.out.print("Enter a database name: ");
			dataBase = input.nextLine();
			try(
					RandomAccessFile rand = new RandomAccessFile(dataBase, "rw"); )
			{
				while(true) {
					student.readFromFile(rand);
					Pair pair =  new Pair<Integer>(student.getID(),position);
					temp.put(pair);
					position++;
				}
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			}catch(EOFException e) {
				System.out.println("\nDONE");
			}
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	///------------------------------------------------------------------//////

	/** 
	 * Displays the Binary Search tree using the Breadth First Traversal
	 * @param  BST<Pair<Integer>> 
	 * @param  boolean checker
	 * @return none
	 */
	public static void displayIndex(Hashing<Pair<Integer>> tempTable) {
		Scanner input = new Scanner(System.in);
		String start=null,end=null;
		int s=0,e=0;
		System.out.println("Select the start and the end for printing the table(0-36): ");
		System.out.print("Start: ");
		start = input.nextLine();
		if(start=="") {
			s = 0;
		}else
			s=Integer.parseInt(start);
		System.out.print("End: ");
		end = input.nextLine();
		if(end=="") {
			e=36;
		}else
			e=Integer.parseInt(end);
		tempTable.print(s,e);
		System.out.println();
	}

	///------------------------------------------------------------------//////
	/** 
	 * Searches the Binary Tree to find the record chosen by the user and displays it
	 * @param  BST<Pair<Integer>> 
	 * @param  outFile name of Random Access File
	 * @param  total number of Records
	 * @return true
	 */
	public static boolean getRecord(Hashing<Pair<Integer>> tempTable,String outFile, int counter) {
		//student object and scanner for input
		Pair currentPair,temp = null;
		int i = 0;
		int indexNum = 0;
		Student student = new Student();
		Scanner input = new Scanner(System.in);

		System.out.print("Enter student ID: ");
		int key =input.nextInt();
		temp = new Pair(key,i);
		currentPair = new Pair();
		currentPair = tempTable.get(temp);

		if(currentPair.getFirst()==null && currentPair.getSecond()==null) {
			System.out.println("Record does not exsist!\n");
		}else
			indexNum=(int) currentPair.getSecond();
		if(indexNum>=1 && indexNum<=counter) {
			try (
					RandomAccessFile rand = new RandomAccessFile(outFile,"r");
					){
				//moving the pointer in the file
				rand.seek((indexNum)*92);
				//reading from the file
				student.readFromFile(rand);
				//checking if the record is deleted or not
				if(!student.getFirst().trim().equals("DELETED")) {
					System.out.println(student.toString()+"\n");
				}else
					System.out.println("Record was Deleted\n");
			}catch(EOFException e) {
				System.out.println("Record found\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	///------------------------------------------------------------------//////
	/** 
	 * Searches the Binary Tree to find the record chosen by the user and then modify it
	 * @param  BST<Pair<Integer>> 
	 * @param  outFile name of Random Access File
	 * @param  total number of Records
	 * @return false when modified
	 */
	public static boolean modifyRecord(Hashing<Pair<Integer>> tempTable, String outFile, int counter) {
		//student object and scanner
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		Pair currentPair=null,temp=null;
		int indexNum=0,choice=0,i=0;
		Double gpa=0.0;
		String fname =null, lname=null;

		System.out.print("Enter student ID: ");
		int key =input.nextInt();
		temp = new Pair<Integer>(key,i);
		currentPair = new Pair<Integer>();
		currentPair = tempTable.get(temp);

		if(currentPair==null) {
			System.out.println("Record does not exsist!\n");
		}else
			indexNum=(int) currentPair.getSecond();

		//validation
		if(indexNum>=1 && indexNum<=counter) {
			try (
					RandomAccessFile rand = new RandomAccessFile(outFile,"rw");
					){
				try {
					//moves pointer to the specific location
					rand.seek((indexNum)*92);
					//reads the record from file
					student.readFromFile(rand);
					//checks if it is not a deleted record
					if(!student.getFirst().trim().equals("DELETED")) { 
						System.out.println(student.toString());
						//ask for the new info for the student to over write the old one

						while(true){
							System.out.println("1- Change the first name:\r\n"
									+ "2- Change the last name:\r\n"
									+ "3- Change GPA:\r\n"
									+ "4- Done:");
							System.out.print("Choice: ");
							choice = input.nextInt();
							input.nextLine();
							if(choice == 1) {
								System.out.println("Enter Student First Name: ");
								fname = input.nextLine();
								student.setFirst(fname);
							}else if(choice == 2) {
								System.out.println("Enter Student Last Name: ");
								lname = input.nextLine();
								student.setLast(lname);
							}else if(choice == 3) {
								System.out.println("Enter Student GPA: ");
								gpa = input.nextDouble();
								student.setGPA(gpa);
							}else if(choice == 4) {
								student.setData(student.getFirst(),student.getLast(),student.getID(),student.getGPA());
								rand.seek((indexNum)*92);
								student.writeToFile(rand);
								System.out.println("File Modified\n");
								return false;
							}
						}
					}else
						System.out.println("Record Was Deleted\n");
				}catch(EOFException e) {
					System.out.println("Record Found\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	///------------------------------------------------------------------//////
	/** 
	 * Creates a new Record and then adds it to the Binary Search Tree
	 * @param  BST<Pair<Integer>> 
	 * @param  outFile name of Random Access File
	 * @param  total number of Records
	 * @return true when done
	 */
	public static boolean addRecord(Hashing<Pair<Integer>> tempTable,String outFile, int counter) {
		//student object and scanner
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		try (
				RandomAccessFile rand = new RandomAccessFile(outFile,"rw");
				){
			System.out.println("Adding a new Record");
			//asking for the students info firstName, lastName, ID, and GPA then storing them in variables
			System.out.print("Enter New Student's First Name: ");
			String fname = input.nextLine();
			System.out.print("Enter New Student's Last Name: ");
			String lname = input.nextLine();
			System.out.print("Enter Student ID: ");
			int id = input.nextInt();
			System.out.print("Enter Student GPA: ");
			Double gpa = input.nextDouble();

			//Setting the new info into the student object
			student.setData(fname,lname,id,gpa);

			//moving to the end of the file
			rand.seek((counter)*92);

			//writing the new record  
			student.writeToFile(rand);

			Pair pair = new Pair<Integer>(id,counter);
			tempTable.put(pair);

			System.out.println("Student has been added\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	///------------------------------------------------------------------//////
	/** 
	 * Finds the record chosen by the user and lazy delete it, and remove it form the binary search tree
	 * @param  BST<Pair<Integer>> 
	 * @param  outFile name of Random Access File
	 * @param  total number of Records
	 * @return true when done deleting
	 */
	public static boolean deleteRecord(Hashing<Pair<Integer>> tempTable,String outFile, int counter) {
		//student object, scanner, and variables
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		Pair currentPair,temp;
		int indexNum=0,i=0;
		String fName =null, tempName = "DELETED";



		System.out.print("Enter student ID: ");
		Integer key =input.nextInt();
		temp = new Pair<Integer>(key,i);

		currentPair = new Pair<Integer>();

		currentPair = tempTable.get(temp);
		if(currentPair==null) {
			System.out.println("Record does not exsist!\n");
			return true;
		}else
			indexNum=(int) currentPair.getSecond();

		try (
				RandomAccessFile rand = new RandomAccessFile(outFile,"rw");
				){
			//validation
			if(indexNum>=0 && indexNum<=counter) {
				//move to the record chosen read it and get the first name
				rand.seek((indexNum)*92);
				student.readFromFile(rand);
				fName = student.getFirst();
				tempTable.remove(temp);

				//changing the name to Deleted and then writing it back to the random access file
				System.out.println(student.toString());
				student.setFirst(tempName);
				rand.seek((indexNum)*92);
				student.writeToFile(rand);
				System.out.println("Record Deleted\n");

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}


	///------------------------------------------------------------------//////
	/** 
	 * loops through out the Random Access File and counts the number of records
	 * @param  outFile name of Random Access File
	 * @return Total number of records
	 */
	public static int studentCounter(String outFile) {
		Student student = new Student();
		int counter=0;
		try( 
				//open the random access file
				RandomAccessFile rand = new RandomAccessFile(outFile, "r");
				){
			try {
				while(true) {
					//count how many records exist in the file
					student.readFromFile(rand);
					counter++;
				}
			}catch(EOFException e) {
				System.out.println();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return counter;

	}	

}