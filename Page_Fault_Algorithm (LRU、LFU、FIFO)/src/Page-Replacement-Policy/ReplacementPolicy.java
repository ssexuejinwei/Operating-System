package replacementpolicy;

import java.util.*;

class ReplacementPolicy{

	public static void main(String args[]){

	    Scanner scan = new Scanner(System.in);
	    int frameSize, page=0, choice, n;					//Declare variables for: frame size, page, choice, and size n
	    String inputString;                                 //String variable for the input string and array of Strings for the pages 
	    String pages[];						
	  	String frame[];										//The array for the frames

		do{
			/* MAIN MENU */
			System.out.println( "====================" );
			System.out.println( "\tMenu" );
			System.out.println(  "====================") ;
			System.out.println("\t1.FIFO" );
			System.out.println( "\t2.LRU" );
			System.out.println( "\t3.LFU" );
			System.out.println( "\t4.EXIT" );

			/* Input Choice */
			do {
				System.out.println( "Enter your choice: " );
			    while ( !scan.hasNextInt() ) {
			        System.out.println( "Your input is invalid. The choices are 1, 2, 3 and 4 only." );
					System.out.println("Enter your choice: ");
			        scan.next();
			    }
			    choice = scan.nextInt();
			    if( choice!=1 && choice!=2 && choice!=3 && choice!=4 )
			    {
			    	System.out.println("Your input is invalid. The choices are 1, 2, 3 and 4 only. Enter Again.");
			    }
			}while (choice!=1 && choice!=2 && choice!=3 && choice!=4);

			/* EXIT if input choice is 4*/
			if( choice == 4 ){
				System.out.println( "*****************************" );
				System.out.println( "  You chose to EXIT. Bye! :)" );
				System.out.println( "*****************************" );
				break;
			}
			/* Input Number of Pages */
			do {																		//while input is not a positive integer, asks for input
				System.out.println( "Enter the number of pages: " );
			    while ( !scan.hasNextInt() ) {											//checks if input is not an integer
			        System.out.println( "Please enter an integer." );						//displays error message
			        scan.next();
			    }
			    n = scan.nextInt();														//gets number of pages input
			    if( n <= 0 ){
			    	System.out.println( "Please enter a positive integer." );	//checks if input is not positive
			    }																//displays error message
			    	                                                  			
			} while ( n <= 0 );
			
			pages = new String[n];														//allocates memory for n number of Strings

			/* Input the Reference String separated by  "\\s+" or space */
			System.out.println( "Enter Reference String (must be separated by space): " );
			scan.nextLine();
			do{																			//while length of pages[] array is not equal to n, asks for input
				inputString = scan.nextLine();											//gets the input string
				pages = inputString.split( "\\s+" );										//splits the string into substrings separated by space and store in the pages[] array
				if( pages.length != n ){													//checks if the number of pages entered is equal to n
					System.out.println( "The number of pages in your input string is not " + n + ". It is " + pages.length + ". Please enter string again." ); //displays error message
				}
			}while( pages.length != n );

			/* Input the Number of Frames */
			do {																		//while input is not a positive integer, asks for input
				System.out.println( "Enter Number of Frames: " );
			    while ( !scan.hasNextInt() ) {											//checks if input is not an integer
			        System.out.println( "Please enter an integer." );	//displays error message
			        scan.next();
			    }
			    frameSize = scan.nextInt();												//gets frame buffer size input
			    if( frameSize <= 0) {
			    	System.out.println( "Please enter a positive integer." );	//checks if input is not positive
			    															//displays error message
			    }												
							
			}while ( frameSize <= 0 );

			frame = new String[ frameSize ];													//string array frame[] of frameSize
			for( int i = 0; i < frameSize; i++ ){													//initializes frame array with " " which indicates an empty frame array
				frame[i]=" ";
			}

			/* Display the data inputed */
			System.out.println( "The size of input string: " + n );
			System.out.println( "The input string: " + inputString );
			System.out.println( "The Number of Frames: " + frameSize + "\n" );
			System.out.println( "pages array: " );

			for (int i = 0; i < pages.length ; i++) {
				System.out.println("index " + "[" + i + "]: " + pages[i]);	
			}
			System.out.println("\n");

			/* Perform FIFO page replacement */
			if( choice == 1 ){
				System.out.println( "************************" );
				System.out.println( "\tFIFO" );
				System.out.println( "************************" );
				FIFO(n, pages, frame);
			}

			/* Perform LRU page replacement */
			if( choice == 2 ){
				System.out.println( "************************" );
				System.out.println( "\tLRU" );
				System.out.println( "************************" );
				LRU( n, pages, frame, frameSize );
			}

			/* Perform LFU page replacement */
			if( choice == 3 ){
				System.out.println( "************************" );
				System.out.println( "\tLFU" );
				System.out.println( "************************" );
				LFU( n, pages, frame, frameSize );
			}
		}while( choice != 4 );
	}

	/* 1. First In First Out (FIFO) */
	public static void FIFO( int n, String pages[], String frame[] ){						//arguments accept a size n, an array of the pages and the frame array
		String page;
		boolean flag;																	//flag for page fault
		int pageFaultCounter = 0, page_fault = 0;											//frame pageFaultCounter; page fault counter
		/* while there are pages */
		for( int pg=0 ; pg < n ; pg++ ){												
			page = pages[ pg ];
			flag = true;																//initially, flag is true because it has not yet found a page hit
			for( int j=0 ; j < frame.length ; j++ ){										//checks if page hit
				if( frame[j].equals( page ) ){												
					flag = false;														//if page hit, no fault occurs
					break;
				}
			}
			if( flag ){																	//If there is page fault,
				frame[ pageFaultCounter ] = page;										//replace the page in frame[pageFaultCounter].
				pageFaultCounter++;
				if( pageFaultCounter == frame.length ) 
				{
					pageFaultCounter=0;			           //set pageFaultCounter back to 0 if pageFaultCounter is equal to length of frame
				}								
				System.out.print( "frame: " );
				/* display the frame buffer array */
				for( int j=0 ; j < frame.length ; j++ )
				{
					System.out.print( frame[j]+"   " );
				}
				System.out.print( " --> page fault!" );
				System.out.println();
				page_fault++;																//add 1 to the page faults
			}
			else{
				System.out.print( "frame: " );											//If page hit, no replacement
				/* diaplay the frame buffer array */											
				for( int j=0 ; j < frame.length ; j++ ){
					System.out.print(frame[j]+"   " );
				}
				System.out.print( " --> page hit!" );
				System.out.println();
			}
		}
		System.out.println( "\nTotal Page Fault/s:" + page_fault + "\n" );							//Display Total Page Fault
	}

	/* Least Recently Used (LRU) */
	public static void LRU( int n, String pages[], String frame[], int frameSize ){			//arguments accept a size n, an array of the pages, the frame array and frame size
		String page = " ";																//temp page
		boolean flag;																	//flag for page fault
		int k = 0, page_fault = 0;														//index k (if page fault occurs); page fault counter
		String a[] = new String[ frameSize ];												/* 2 temporary arrays to keep track of LRU page, sorted from most recent to least recent */
		String b[] = new String[ frameSize ];												/* first element of a[] is most recent and the last element is the LRU */
		for(int i = 0 ; i < frameSize ; i++ ){													//initialize array elements to " "
			a[ i ] = " ";
			b[ i ] = " ";
		}

		for( int pg = 0 ; pg < n ; pg++ ){
			page = pages[ pg ];
			flag = true;																//initially, flag is true because it has not yet found a page hit
			for( int j=0 ; j < frameSize ; j++ ){											//checks if page hit
				if( frame[ j ].equals( page ) ){												
					flag = false; 														//If page hit, no page fault occurs
					break;
				}
			}
	
			for( int j=0 ; j < frameSize && flag ; j++ ){									//While page fault occurs and find the least recently used page,
				if( frame[ j ].equals(a[ frameSize-1 ] ) ){										//If least recently used
					k = j;																//set index to be replaced
					break;
				}
			}
	
			if( flag ){																	//If page fault,
				frame[ k ] = page;															//replace frame[k] with the page.	
				System.out.print( "frame: " );
				/* display frame buffer array */
				for(int j = 0 ; j < frameSize ; j++)
					System.out.print( frame[j] + "  " );
				System.out.println( " --> page fault!" );
				page_fault++;																//add 1 to page fault counter
			}
			else{																		//If page hit, no replacement
				/* display frame buffer array */
				System.out.print( "frame: " );
				for( int j=0 ; j < frameSize ; j++ )
					System.out.print( frame[ j ]+"  " );
				System.out.println( " --> page hit!" );
			}

			int p = 1;																	//counter
			b[ 0 ] = page;																	//first element of b[] is the page (b is most recent)
			/* update MRU-LRU array */
			for( int j=0 ; j < a.length ; j++ ){											//while j < size of frames
				if( !page.equals( a[ j ] ) && p < frameSize ) {									//the elements in a[] that are not equal to referenced page or is not the most recently used are copied to b[j] from left
					b[ p ] = a[ j ];															
					p++;
				}
			}
			for( int j = 0 ; j < frameSize ; j++ ){											//set LRU a[] to the updated LRU b[]
				a[ j ] = b[ j ];
			}
		}
		System.out.println( "\nTotal Page Fault/s: "+ page_fault + "\n" );							//display total page faults
	}

	/* Least Frequently Used (LFU) */
	public static void LFU( int n, String pages[], String frame[], int frameSize ){			//arguments accept a size n, an array of the pages, the frame array and frame size
		int k = 0, page_fault = 0;															//index k for frequency array; page fault counter
		int leastFrequency;																		//for the least frequency
		String page;																	//tempp page
		int Frequency[] = new int[ frameSize ];													//array to store and keep track of frequencies
		boolean flag = true;																//flag for a page fault
		
		/* Initializes the frequency to 0 */
		for(int i = 0 ; i < frameSize ; i++ ){
			Frequency[ i ] = 0;
		}
		/* while there is page */
		for( int pg = 0 ; pg < n ; pg++ ){
			page = pages[ pg ];															//assign temp page = pages[page]
			flag = true;																	//initially, flag is true because it has not yet found a page hit

			for( int j=0 ; j < frameSize ; j++ ){											//checks if page hit
				if( page.equals( frame[ j ] ) ){												//If page hit, no page fault occurs
					flag = false;
					Frequency[ j ]++;															//add 1 to its frequency
					break;																//break
				}
			}

			if( flag ){																	//If a page hit occurs,
				leastFrequency = Frequency[ 0 ];
				for( int j = 0 ; j < frameSize ; j++ ){										//Look for least number of frequency
					if( Frequency[ j ] < leastFrequency ){
						leastFrequency = Frequency[ j ];
						break;
					}
				}
				for( int j = 0 ; j < frameSize ; j++ ){										//Find the page with the least frequency from the left
					if( leastFrequency == Frequency[ j ] ){													//The left-most page will be the one to be replaced
						frame[ j ] = page;
						k = j;
						break;
					}
				}
				Frequency[ k ] = 1;																//set the frequency of new page to 1
				System.out.print( "frame: " );
				/* display frame buffer array */
				for( int j = 0 ; j < frameSize ; j++ ){
					System.out.print( frame[ j ]+"   " );
					page_fault++;															//add 1 to page fault counter
				}
				System.out.println( " --> Page fault!" );
			}
			else{																		//If page hit, no replacement
				System.out.print( "frame: " );
				/* display frame buffer array */
				for( int j = 0 ; j < frameSize ; j++ )
					System.out.print( frame[ j ]+"   " ); 
				System.out.print( " --> Page hit!" );
				System.out.println();
			}
		}		
		System.out.println( "\nTotal Page Fault/s: " + page_fault + "\n" );
	}
}
