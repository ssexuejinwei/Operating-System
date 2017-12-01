package replacementpolicy;

/*
 * @author Pu Dongqi
 */
import java.util.*;

class ReplacementPolicy{

	public static void main(String args[]){

	    Scanner scan = new Scanner(System.in);
	    int f_size, page=0, choice, n;							//Declare variables for: frame size, page, choice, and size n
	    String ref_string, pages[];								//String variable for the input string and array of Strings for the pages 
	  	String frame[];											//The array for the frames

		do{
			/* MAIN MENU */
			System.out.println("====================");
			System.out.println("\tMenu");
			System.out.println("====================");
			System.out.println("\t1.FIFO");
			System.out.println("\t2.LRU");
			System.out.println("\t3.LFU");
			System.out.println("\t4.EXIT");

			/* Input Choice */
			do {
				System.out.println("ENTER YOUR CHOICE: ");
			    while (!scan.hasNextInt()) {
			        System.out.println("Your input is invalid. The choices are 1, 2, 3 and 4 only.");
					System.out.println("ENTER YOUR CHOICE: ");
			        scan.next();
			    }
			    choice = scan.nextInt();
			    if(choice!=1 && choice!=2 && choice!=3 && choice!=4)
			    	System.out.println("Your input is invalid. The choices are 1, 2, 3 and 4 only. Enter Again.");
			} while (choice!=1 && choice!=2 && choice!=3 && choice!=4);

			/* EXIT if input choice is 4*/
			if(choice==4){
				System.out.println("*****************************");
				System.out.println("  You chose to EXIT. Bye! :)");
				System.out.println("*****************************");
				break;
			}
			/* Input Number of Pages */
			do {																		//while input is not a positive integer, asks for input
				System.out.println("Enter the number of pages: ");
			    while (!scan.hasNextInt()) {											//checks if input is not an integer
			        System.out.println("Please enter an integer.");	//displays error message
			        scan.next();
			    }
			    n = scan.nextInt();														//gets number of pages input
			    if(n <= 0)																//checks if input is not positive
			    	System.out.println("Please enter a positive integer.");				//displays error message
			} while (n <= 0);
			
			pages = new String[n];														//allocates memory for n number of Strings

			/* Input the Reference String separated by  "\\s+" or space */
			System.out.println("Enter Reference String (must be separated by space): ");
			scan.nextLine();
			do{																			//while length of pages[] array is not equal to n, asks for input
				ref_string = scan.nextLine();											//gets the input string
				pages = ref_string.split("\\s+");										//splits the string into substrings separated by space and store in the pages[] array
				if(pages.length != n){													//checks if the number of pages entered is equal to n
					System.out.println("The number of pages in your input string is not " 
						+ n + ". It is " + pages.length + ". Please enter string again."); //displays error message
				}
			}while(pages.length != n);

			/* Input the Number of Frames */
			do {																		//while input is not a positive integer, asks for input
				System.out.println("Enter Number of Frames: ");
			    while (!scan.hasNextInt()) {											//checks if input is not an integer
			        System.out.println("Please enter an integer.");	//displays error message
			        scan.next();
			    }
			    f_size = scan.nextInt();												//gets frame buffer size input
			    if(f_size <= 0)															//checks if input is not positive
			    	System.out.println("Please enter a positive integer.");				//displays error message
			} while (f_size <= 0);

			frame = new String[f_size];													//string array frame[] of f_size
			for(int i=0;i<f_size;i++){													//initializes frame array with " " which indicates an empty frame array
				frame[i]=" ";
			}

			/* Display the data inputed */
			System.out.println("The size of reference string: " + n);
			System.out.println("The reference string: " + ref_string);
			System.out.println("The Number of Frames: " + f_size + "\n");
			System.out.println("pages array: ");
			for (int i=0; i<pages.length ; i++) {
				System.out.println("index " + "[" + i + "]: " + pages[i]);	
			}
			System.out.println("\n");
			/* Perform FIFO page replacement */
			if(choice==1){
				System.out.println("************************");
				System.out.println("\tFIFO");
				System.out.println("************************");
				FIFO(n, pages, frame);
			}
			/* Perform LRU page replacement */
			if(choice==2){
				System.out.println("************************");
				System.out.println("\tLRU");
				System.out.println("************************");
				LRU(n, pages, frame, f_size);
			}
			/* Perform LFU page replacement */
			if(choice==3){
				System.out.println("************************");
				System.out.println("\tLFU");
				System.out.println("************************");
				LFU(n, pages, frame, f_size);
			}
		}while(choice != 4);
	}

	/* 1. First In First Out (FIFO) */
	public static void FIFO(int n, String pages[], String frame[]){						//arguments accept a size n, an array of the pages and the frame array
		String page;
		boolean flag;															//flag for page fault
		int ctr=0, pg_fault=0;															//frame ctr; page fault counter
		/* while there are pages */
		for(int pg=0 ; pg < n ; pg++){												
			page = pages[pg];
			flag = true;																//initially, flag is true because it has not yet found a page hit
			for(int j=0 ; j < frame.length ; j++){										//checks if page hit
				if(frame[j].equals(page)){												
					flag = false;														//if page hit, no fault occurs
					break;
				}
			}
			if(flag){																	//If there is page fault,
				frame[ctr] = page;														//replace the page in frame[ctr].
				ctr++;
				if(ctr==frame.length) ctr=0;											//set ctr back to 0 if ctr is equal to length of frame
				System.out.print("frame: ");
				/* display the frame buffer array */
				for(int j=0 ; j < frame.length ; j++)
					System.out.print(frame[j]+"   ");
				System.out.print(" --> page fault!");
				System.out.println();
				pg_fault++;																//add 1 to the page faults
			}
			else{
				System.out.print("frame: ");											//If page hit, no replacement
				/* diaplay the frame buffer array */											
				for(int j=0 ; j < frame.length ; j++)
					System.out.print(frame[j]+"   ");
				System.out.print(" --> page hit!");
				System.out.println();
			}
		}
		System.out.println("\nTotal Page Fault/s:" + pg_fault + "\n");							//Display Total Page Fault
	}

	/* Least Recently Used (LRU) */
	public static void LRU(int n, String pages[], String frame[], int f_size){			//arguments accept a size n, an array of the pages, the frame array and frame size
		String page = " ";																//temp page
		boolean flag;																	//flag for page fault
		int k = 0, pg_fault = 0;														//index k (if page fault occurs); page fault counter
		String a[] = new String[f_size];												/* 2 temporary arrays to keep track of LRU page, sorted from most recent to least recent */
		String b[] = new String[f_size];												/* first element of a[] is most recent and the last element is the LRU */
		for(int i=0 ; i<f_size ; i++){													//initialize array elements to " "
			a[i] = " ";
			b[i] = " ";
		}

		for(int pg=0 ; pg < n ; pg++){
			page = pages[pg];
			flag = true;																//initially, flag is true because it has not yet found a page hit
			for(int j=0 ; j < f_size ; j++){											//checks if page hit
				if(frame[j].equals(page)){												
					flag = false; 														//If page hit, no page fault occurs
					break;
				}
			}
	
			for(int j=0 ; j < f_size && flag ; j++){									//While page fault occurs and find the least recently used page,
				if(frame[j].equals(a[f_size-1])){										//If least recently used
					k = j;																//set index to be replaced
					break;
				}
			}
	
			if(flag){																	//If page fault,
				frame[k]=page;															//replace frame[k] with the page.	
				System.out.print("frame: " );
				/* display frame buffer array */
				for(int j=0 ; j < f_size ; j++)
					System.out.print(frame[j] + "  ");
				System.out.println(" --> page fault!" );
				pg_fault++;																//add 1 to page fault counter
			}
			else{																		//If page hit, no replacement
				/* display frame buffer array */
				System.out.print("frame: " );
				for(int j=0 ; j < f_size ; j++)
					System.out.print(frame[j]+"  ");
				System.out.println(" --> page hit!" );
			}

			int p=1;																	//counter
			b[0]=page;																	//first element of b[] is the page (b is most recent)
			/* update MRU-LRU array */
			for(int j=0 ; j < a.length ; j++){											//while j < size of frames
				if(!page.equals(a[j]) && p < f_size){									//the elements in a[] that are not equal to referenced page or is not the most recently used are copied to b[j] from left
					b[p]=a[j];															
					p++;
				}
			}
			for(int j=0 ; j < f_size ; j++){											//set LRU a[] to the updated LRU b[]
				a[j]=b[j];
			}
		}
		System.out.println("\nTotal Page Fault/s: "+ pg_fault + "\n");							//display total page faults
	}

	/* Least Frequently Used (LFU) */
	public static void LFU(int n, String pages[], String frame[], int f_size){			//arguments accept a size n, an array of the pages, the frame array and frame size
		int k=0, pg_fault=0;															//index k for frequency array; page fault counter
		int sml;																		//for the least frequency
		String page;																	//tempp page
		int cnt[] = new int[f_size];													//array to store and keep track of frequencies
		boolean flag=true;																//flag for a page fault
		
		/* Initializes the frequency to 0 */
		for(int i=0 ; i < f_size ; i++){
			cnt[i] = 0;
		}
		/* while there is page */
		for(int pg=0 ; pg < n ; pg++){
			page = pages[pg];															//assign temp page = pages[pg]
			flag=true;																	//initially, flag is true because it has not yet found a page hit

			for(int j=0 ; j < f_size ; j++){											//checks if page hit
				if(page.equals(frame[j])){												//If page hit, no page fault occurs
					flag=false;
					cnt[j]++;															//add 1 to its frequency
					break;																//break
				}
			}

			if(flag){																	//If a page hit occurs,
				sml = cnt[0];
				for(int j=0 ; j < f_size ; j++){										//Look for least number of frequency
					if(cnt[j] < sml){
						sml = cnt[j];
						break;
					}
				}
				for(int j=0 ; j < f_size ; j++){										//Find the page with the least frequency from the left
					if(sml==cnt[j]){													//The left-most page will be the one to be replaced
						frame[j] = page;
						k = j;
						break;
					}
				}
				cnt[k] = 1;																//set the frequency of new page to 1
				System.out.print("frame: ");
				/* display frame buffer array */
				for(int j=0 ; j < f_size ; j++){
					System.out.print(frame[j]+"   ");
					pg_fault++;															//add 1 to page fault counter
				}
				System.out.println(" --> Page fault!");
			}
			else{																		//If page hit, no replacement
				System.out.print("frame: ");
				/* display frame buffer array */
				for(int j=0 ; j < f_size ; j++)
					System.out.print(frame[j]+"   ");
				System.out.print(" --> Page hit!");
				System.out.println();
			}
		}		
		System.out.println("\nTotal Page Fault/s: " + pg_fault + "\n");
	}
}
