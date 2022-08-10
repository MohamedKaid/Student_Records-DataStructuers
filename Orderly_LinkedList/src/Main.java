/**
 * Author:  Mohamed Kaid
 * About: it will make an index using an ordered singly Linked list and order the contents of a dataBase 
 * in a node consisting of a pair (key,address), and it will allow the user to do multiple functions with it 
 * 
 **/

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int studentNum=0;
		//variables 
		String outFile = null;
		int choice=0;
		OrderedSinglyLinkedList<Pair<Integer>> list = new OrderedSinglyLinkedList();

		System.out.println("Programmer:        Mohamed Kaid\r\n");

		System.out.println("Welcome!!");
		do {
			//display the menu
			displayMenu();
			System.out.print("Choice: ");
			choice = input.nextInt();



			if(choice == 1) {
				outFile = CreateRandomFile();
				studentNum =studentCounter(outFile);
			}else if(choice == 2) {
				readRandomFile(studentNum);
			}else if(choice == 3) {
				list = buildIndex();
			}else if(choice == 4) {
				displayIndex(list);
			}else if(choice == 5) {
				getRecord(list,outFile,studentNum);
			}else if(choice == 6) {
				modifyRecord(list,outFile,studentNum);
			}else if(choice == 7) {
				addRecord(list,outFile,studentNum);
			}else if(choice == 8){
				deleteRecord(list,outFile,studentNum);
			}else if(choice<0 || choice>9){
				System.out.println("Invalid Entry please chose between 1-9\n");
			}
		}while(choice!=9);
		System.out.println("Thank you!! Have a nice day");
	}

	///------------------------------------------------------------------//////
	//a method to display the menu
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
	//method to create a random -access file
	public static String CreateRandomFile() {
		//Student Object to store the info from the txt file
		Student student = new Student();
		Scanner input = new Scanner(System.in);

		System.out.println("Cerating new Random File");
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
	//Method that displays the contents of random file
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
						System.out.println(counter+".)"+student.toString());
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
							System.out.println(counter+".)"+student.toString());
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
							System.out.println(counter+".)"+student.toString());
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
	public static OrderedSinglyLinkedList buildIndex() {
		Scanner input = new Scanner(System.in);
		String dataBase, index;
		int key,address;
		int position = 0;

		Student student = new Student();
		OrderedSinglyLinkedList<Pair<Integer>> list = new OrderedSinglyLinkedList();
		try {
			System.out.print("Enter a database name: ");
			dataBase = input.nextLine();
			try(
					RandomAccessFile rand = new RandomAccessFile(dataBase, "rw"); )
			{

				while(true) {
					student.readFromFile(rand);
					Pair pair =  new Pair(student.getID(),position);
					list.add(pair);
					position++;
				}
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found");
			}catch(EOFException e) {
				System.out.println("\nDONE");
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	///------------------------------------------------------------------//////

	public static void displayIndex(OrderedSinglyLinkedList<Pair<Integer>> list) {
		Scanner input = new Scanner(System.in);
		int counter = 0;
		int indexNum = 0;
		try {
			System.out.print("Enter the starting ID or -1 for the entire index: ");
			int id = input.nextInt();
			if(list.get(0).equals(null)) {
				throw new IndexOutOfBoundsException();
			}
			if(id == -1) {
				for(Pair x:list) {
					System.out.println(x.toString());
				}}else if(id > 0) {
					for(Pair y: list) {
						counter++;
					}
					for(Pair x: list) {
						if(x.getFirst().equals(id)) {
							System.out.println(x.toString());
							indexNum++;
							for(int j=indexNum;j<counter;j++) {
								System.out.println(list.get(j));
							}
						}else
							indexNum++;
					}

				}else
					System.out.println("Invalid Entry\n");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Build the Index First \n");
		}
	}

	///------------------------------------------------------------------//////
	//Method that gets a specific record in the file
	public static void getRecord(OrderedSinglyLinkedList<Pair<Integer>> list,String outFile, int counter) {
		//student object and scanner for input
		int indexNum =0;
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		System.out.print("Enter student ID: ");
		Object id =input.nextInt();

		try(
				//open random access file
				RandomAccessFile rand = new RandomAccessFile(outFile, "r");
				){
			for(Pair x: list) {
				if(x.getFirst().equals(id)) {
					indexNum = (int) x.getSecond();
				}else
					continue;
				//validation
				if(indexNum>=1 && indexNum<=counter) {
					try {
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
					}
				}
			}} catch (IOException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("Build a dataBase, choice 1 first\n");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Build the Index First \n");
			}
	}

	///------------------------------------------------------------------//////
	//Boolean Method that takes an existing record and modifies it 
	public static boolean modifyRecord(OrderedSinglyLinkedList<Pair<Integer>> list,String outFile, int counter) {
		//student object and scanner
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		int indexNum=0,choice=0,newId=0;
		Double gpa=0.0;
		String fname =null, lname=null;

		//asking user which record to modify
		System.out.print("Enter student ID: ");
		Object id =input.nextInt();

		for(Pair x: list) {
			if(x.getFirst().equals(id)) {
				indexNum = (int) x.getSecond();
			}else
				continue;
		}

		//validation
		if(indexNum>=1 && indexNum<=counter) {
			try(
					//open random access file
					RandomAccessFile rand = new RandomAccessFile(outFile, "rw");
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
		}else if(student.equals(null)) {
			System.out.println("Build the dataBase first, choice 1\n");
			return false;
		}else if(list.equals(null)) {
			System.out.println("Build The Index First, choice 3\n");
			return false;
		}else
			System.out.println();
		return false;
	}

	///------------------------------------------------------------------//////
	//Method to add a new record that random access file
	public static void addRecord(OrderedSinglyLinkedList<Pair<Integer>> list,String outFile, int counter) {
		//student object and scanner
		Student student = new Student();
		Scanner input = new Scanner(System.in);
		try(
				//open Random access file
				RandomAccessFile rand = new RandomAccessFile(outFile, "rw");
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

			Pair pair = new Pair(id,counter);
			list.add(pair);

			System.out.println("Student has been added\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	///------------------------------------------------------------------//////
	//boolean method that deletes a single record
		public static boolean deleteRecord(OrderedSinglyLinkedList<Pair<Integer>> list,String outFile, int counter) {
			//student object, scanner, and variables
			Student student = new Student();
			Scanner input = new Scanner(System.in);
			int recordNum=0,numIndex =0;
			String fName =null, tempName = "DELETED";

			System.out.print("Enter student ID: ");
			Object id =input.nextInt();

			for(Pair x: list) {
				if(x.getFirst().equals(id)) {
					recordNum = (int) x.getSecond();
					numIndex=list.indexOf(x);
				}else
					continue;
			}
			try(
					//open random access file
					RandomAccessFile rand = new RandomAccessFile(outFile, "rw");
					){
				//validation
				if(recordNum>=0 && recordNum<=counter) {
					//move to the record chosen read it and get the first name
					rand.seek((recordNum)*92);
					student.readFromFile(rand);
					fName = student.getFirst();
					list.remove(numIndex);
					//checking if the first name already equals DELETED
					if(fName.trim().equals(tempName)) {
						System.out.println("Record Already Deleted\n");
						return true;
					}else
						//changing the name to Deleted and then writing it back to the random access file
					System.out.println(student.toString());
					student.setFirst(tempName);
					rand.seek((recordNum)*92);
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
	//new Method that counts the number of records
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