
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.util.*;
import java.lang.Runtime;
import Record;
import class.WindowHandler ; 
import class.PassWord ; 
import class.Product;
import class.MouseClickedHandler;
import class.UpdateRec;
import class.MenuHandler;
import class.NewRec;


public class HardwareStore extends JFrame
          implements ActionListener {

   private JButton updateButton, /** update record */
                   newButton,    /** add new Record */
                   deleteButton, /** delete Record  */
                   listButton,   /** list all records */
                   done;         /** quit program */

   private PassWord pWord;     /** dialog box for password checking */
  // private ListRecs listRecs;    /** dialog box listing all records */
   private UpdateRec update;     /** dialog box for record update */
   private NewRec    newRec;     /** dialog box for new record */
   private DeleteRec deleteRec;  /** dialog box for delete record */
   private Record data;
   private Product products ;
   private String pData[] []  = new String [ 250 ] [ 7 ];
   private JMenuBar menuBar ;
   private JMenu fileMenu, viewMenu, optionsMenu, toolsMenu,
           helpMenu, aboutMenu ;
   /** File Menu Items */
   private JMenuItem eMI ;
   /** View Menu Items */
   private JMenuItem lmMI, lmtMI, hdMI, dpMI, hamMI, csMI, tabMI, bandMI,
               sandMI, stapMI, wdvMI, sccMI;
   /** Options Menu Items */
   private JMenuItem deleteMI, addMI, updateMI, listAllMI ;
   /** Tools Menu Items */
   private JMenuItem debugON, debugOFF ;
   /** Help Menu Items */
   private JMenuItem   helpHWMI ;
   /** About Menu Items */
   private JMenuItem   aboutHWMI ;
   private MenuHandler menuHandler = new MenuHandler();
   private JTable table;
   private   file;  /** file from which data is read */
   private File aFile ;
   private JButton cancel, refresh;
   private JPanel buttonPanel ;
   protected boolean validPW = false ;
   private boolean myDebug = false ; /** This flag toggles debug */
   HardwareStore hws ;

   private String columnNames[] = {
      "Record ID",
      "Type of tool",
      "Brand Name", 
      "Tool Description", 
      "partNum",
      "Quantity", 
      "Price"
   } ;

   private Container c ;
   private int numEntries = 0 , ZERO;

   /** 
    * Method: HardwareStore() constructor initializes the
    * 1- Menu bar
    * 2- Tables
    * 3- Buttons
    * used to construct the HardwareStore GUI.
    ****/
   public HardwareStore()
   {
      super( "Hardware Store: Lawn Mower " );

      data  = new Record();
      aFile = new File( "lawnmower.dat" ) ;
      c = getContentPane() ;

      setupMenu();

      InitRecord( "lawnmower.dat" ,  products.getLawnMower() , 27 ) ;

      InitRecord( "lawnTractor.dat" , products.getLawnTractor() , 11 ) ;

      InitRecord( "handDrill.dat" ,  products.getHandDrill() , 15 ) ;

      InitRecord( "drillPress.dat" ,   products.getDrillPress() , 10 ) ;

      InitRecord( "circularSaw.dat" ,   products.getCircularSaw() , 12 ) ;

      InitRecord( "hammer.dat" ,   products.getHammer() , 12 ) ;

      InitRecord( "tableSaw.dat" ,  products.getTableSaw() , 15 ) ;

      InitRecord( "bandSaw.dat" ,  products.getBandSaw() , 10 ) ;

      InitRecord( "sanders.dat" ,  products.getSanders() , 15 ) ;

      InitRecord( "stapler.dat" ,  products.getStapler() , 15 ) ;


      setup();

      addWindowListener( new WindowHandler( this ) );
      setSize( 700, 400 );
      setVisible( true );
   }

   private String initFile(String fileName,String fileTitle) {
       file = new File(fileName) ;
       titleFile = fileTitle ;
       return fileName ;
   }

   /**
    * Create and initialize the {@link JMenu}
    */

   public void setupMenu()   {
      /** Create the menubar */
      menuBar = new JMenuBar();

      /** Add the menubar to the frame */
      setJMenuBar(menuBar);

      /** Create the File menu and add it to the menubar  */
      fileMenu = new JMenu("File");

      menuBar.add(fileMenu);
      /** Add the Exit menuitems */
      eMI = new JMenuItem("Exit") ;
      fileMenu.add( eMI );
      eMI.addActionListener( menuHandler );

      /** Create the View menu and add it to the menubar  */
      viewMenu = new JMenu("View");

      /** Add the Lawn Mower menuitems */
      lmMI = new JMenuItem("Lawn Mowers") ;
      viewMenu.add( lmMI );
      lmMI.addActionListener( menuHandler );

      /** Add the Lawn Mower menuitems */
      lmtMI = new JMenuItem("Lawn Mowing Tractors") ;
      viewMenu.add( lmtMI );
      lmtMI.addActionListener( menuHandler );

      /** Add the Hand Drills Tools menuitems */
      hdMI = new JMenuItem("Hand Drills Tools") ;
      viewMenu.add( hdMI );
      hdMI.addActionListener( menuHandler );

      /** Add the Drill Press Power Tools menuitems */
      dpMI = new JMenuItem("Drill Press Power Tools") ;
      viewMenu.add( dpMI );
      dpMI.addActionListener( menuHandler );

      /** Add the Circular Saws  menuitems */
      csMI = new JMenuItem("Circular Saws") ;
      viewMenu.add( csMI );
      csMI.addActionListener( menuHandler );

      /** Add the Hammer menuitems */
      hamMI = new JMenuItem("Hammers") ;
      viewMenu.add( hamMI );
      hamMI.addActionListener( menuHandler );

      /** Add the Table Saws menuitems */
      tabMI = new JMenuItem("Table Saws") ;
      viewMenu.add( tabMI );
      tabMI.addActionListener( menuHandler );

      /** Add the Band Saws menuitems */
      bandMI = new JMenuItem("Band Saws") ;
      viewMenu.add( bandMI );
      bandMI.addActionListener( menuHandler );

      /** Add the Sanders menuitems */
      sandMI = new JMenuItem("Sanders") ;
      viewMenu.add( sandMI );
      sandMI.addActionListener( menuHandler );


      /** Add the Stapler menuitems */
      stapMI = new JMenuItem("Staplers") ;
      viewMenu.add( stapMI );
      stapMI.addActionListener( menuHandler );

      /** Add the Wet-Dry Vacs menuitems */
      wdvMI = new JMenuItem("Wet-Dry Vacs") ;
      viewMenu.add( wdvMI );
      wdvMI.addActionListener( menuHandler );

      /** Add the Storage, Chests & Cabinets menuitems */
      sccMI = new JMenuItem("Storage, Chests & Cabinets") ;
      viewMenu.add( sccMI );
      sccMI.addActionListener( menuHandler );

      menuBar.add(viewMenu);
      /** Create the Options menu and add it to the menubar  */
      optionsMenu = new JMenu("Options") ;

      /** Add the List All menuitems */
      listAllMI = new JMenuItem("List All") ;
      optionsMenu.add( listAllMI );
      listAllMI.addActionListener( menuHandler );
      optionsMenu.addSeparator();

      /** Add the Add menuitems */
      addMI = new JMenuItem("Add") ;
      optionsMenu.add( addMI );
      addMI.addActionListener( menuHandler );

      /** Add the Update menuitems */
      updateMI = new JMenuItem("Update") ;
      optionsMenu.add( updateMI );
      updateMI.addActionListener( menuHandler );
      optionsMenu.addSeparator();

      /** Add the Delete menuitems */
      deleteMI = new JMenuItem("Delete") ;
      optionsMenu.add( deleteMI );
      deleteMI.addActionListener( menuHandler );

      menuBar.add(optionsMenu);

      /** Create the Tools menu and add it to the menubar  */
      toolsMenu = new JMenu("Tools") ;
      menuBar.add(toolsMenu);
      /** Add the Tools menuitems */
      debugON = new JMenuItem("Debug On") ;
      debugOFF = new JMenuItem("Debug Off") ;
      toolsMenu.add( debugON );
      toolsMenu.add( debugOFF );
      debugON.addActionListener( menuHandler );
      debugOFF.addActionListener( menuHandler );

      /** Create the Help menu and add it to the menubar  */
      helpMenu = new JMenu("Help") ;

      /** Add the Help HW Store menuitems */
      helpHWMI = new JMenuItem("Help on HW Store") ;
      helpMenu.add( helpHWMI );
      helpHWMI.addActionListener( menuHandler );

      menuBar.add(helpMenu);

      /** Create the About menu and add it to the menubar  */
      aboutMenu = new JMenu("About") ;

      /** Add the About Store menuitems */
      aboutHWMI = new JMenuItem("About HW Store") ;
      aboutMenu.add( aboutHWMI );
      aboutHWMI.addActionListener( menuHandler );

      menuBar.add(aboutMenu);
   }

  /**
    * Initialize the {@link JMenu}'s appearance
    */

   public void setup()   {
      double loopLimit = 0.0;
      data = new Record();

      try {
         /** Divide  the length of the file by the record size to
          *  determine the number of records in the file
          */

         file = new RandomAccessFile( "lawnmower.dat" , "rw" );

         aFile = new File( "lawnmower.dat" ) ;

         numEntries = toArray( file, pData ) ;

         file.close() ;
      }
       catch ( IOException ex ) {
            //part.setText( "Error reading file" );
      }

     
      table = new JTable( pData, columnNames );
      table.addMouseListener( new MouseClickedHandler( file, table , pData ) ) ;
      table.setEnabled( true );

      c.add( table , BorderLayout.CENTER) ;
      c.add( new JScrollPane ( table ) );
      /** Make the Frame visiable */
      cancel  = new JButton( "Cancel" ) ;
      refresh = new JButton( "Refresh" ) ;
      buttonPanel = new JPanel() ;
      buttonPanel.add( cancel ) ;
      c.add( buttonPanel , BorderLayout.SOUTH) ;

      refresh.addActionListener( this );
      cancel.addActionListener( this );

      /** Create dialog boxes */
      update = new UpdateRec( hws, file, pData , -1);
      deleteRec = new DeleteRec( hws, file, table, pData );
      
      pWord = new PassWord( this ) ;
   }

  /**
    *  Initialize the Record
    * @param fileDat First index of initRecord
    * @param FileRecord Second index of initRecord
    * @param loopCtl Third index of initRecord
    */
   public void InitRecord( String fileDat , String FileRecord[][] ,
                    int loopCtl ) {

      aFile = new File( fileDat ) ;

      sysPrint("initRecord(): 1a - the value of fileData is " + aFile );

      try {
         /** Open the fileDat file in RW mode.
          *  If the file does not exist, create it
          *  and initialize it to 250 empty records.
          */

         sysPrint("initTire(): 1ab - checking to see if " + aFile + " exist." );
         if ( !aFile.exists() ) {

            sysPrint("initTire(): 1b - " + aFile + " does not exist." );

            file = new RandomAccessFile( aFile , "rw" );
            data = new Record() ;

            for ( int index = 0 ; index < loopCtl ; index++ ) {
               data.setRecID( Integer.parseInt( FileRecord[ index ][ 0 ] ) ) ;
               sysPrint("initTire(): 1c - The value of record ID is " + data.getRecID() ) ;
               data.setToolType( FileRecord[ index ][ 1 ] ) ;
               sysPrint("initTire(): 1cb - The length of ToolType is " + data.getToolType().length() ) ;
               data.setBrandName( FileRecord[ index ][ 2 ] ) ;
               data.setToolDesc( FileRecord[ index ][ 3 ] ) ;
               sysPrint("initTire(): 1cc - The length of ToolDesc is " + data.getToolDesc().length() ) ;
               data.setPartNumber( FileRecord[ index ][ 4 ] ) ;
               data.setQuantity( Integer.parseInt( FileRecord[ index ][ 5 ] ) ) ;
               data.setCost( FileRecord[ index ][ 6 ] ) ;

               sysPrint("initTire(): 1d - Calling Record method write() during initialization. " + index );
               file.seek( index * Record.getSize() );
               data.write( file );

            }
         }
         else {
            sysPrint("initTire(): 1e - " + fileDat + " exists." );
            file = new RandomAccessFile( aFile , "rw" );
         }

         file.close();
      }
      catch ( IOException e ) {
            System.err.println( "InitRecord() " + e.toString() +
                   " " + aFile );
            System.exit( 1 );
      }
   }

   /**
    *  method that displays the info items
    * @param str First index of display
    */

   public void display( String str ) {

      String  df = null ,  title = null ;

      switch(productName) {
      case "Lawn Mowers":
          displayFile = initFile("lawnMower.dat","Hardware Store: Lawn Mowers");
          
      case "Lawn Tractor Mowers":
          displayFile = initFile("lawnTractor.dat","Hardware Store: Lawn Tractor Mowers");
          
      case "Hand Drill Tools":
          displayFile = initFile("handDrill.dat","Hardware Store: Hand Drill Tools");
    
      case "Drill Press Power Tools":
          displayFile = initFile("drillPress.dat","Hardware Store: Drill Press Power Tools");
          
      case "Circular Saws":
          displayFile = initFile("circularSaw.dat","Hardware Store: Circular Saws");
          
      case "Hammers":
          displayFile = initFile("hammer.dat","Hardware Store: Hammers");
          
      case "Table Saws":
          displayFile = initFile("tableSaw.dat","Hardware Store: Table Saws");
          
      case "Band Saws":
          displayFile = initFile("bandSaw.dat","Hardware Store: Band Saws");
          
      case "Sanders":
          displayFile = initFile("sanders.dat","Hardware Store: Sanders");
          
      case "Staplers":
          displayFile = initFile("stapler.dat","Hardware Store: Staplers");
      }
**
      try {
         /** Open the .dat file in RW mode.
          *  If the file does not exist, create it
          *  and initialize it to 250 empty records.
          */

         sysPrint("display(): 1a - checking to see if " + df + " exists." );
         if ( !aFile.exists() ) {

            sysPrint("display(): 1b - " + df + " does not exist." );

         }
         else {
            file = new RandomAccessFile( df , "rw" );

            this.setTitle( title );

            Redisplay( file , pData  ) ;
         }

         file.close();
      }
      catch ( IOException e ) {
            System.err.println( e.toString() );
            System.err.println( "Failed in opening " + df );
            System.exit( 1 );
      }

   }

   /**
    * Method Actualize {@link JTable}
    * @param file First index of Redisplay
    * @param a Second index of Redisplay
    */
   public void Redisplay( RandomAccessFile file, String a[][] ) {


      for ( int index = 0 ; index < numEntries + 5; index++ ) {
         a[ index ][ 0 ] = "" ;
         a[ index ][ 1 ] = "" ;
         a[ index ][ 2 ] = "" ;
         a[ index ][ 3 ] = "" ;
         a[ index ][ 4 ] = "" ;
         a[ index ][ 5 ] = "" ;
         a[ index ][ 6 ] = "" ;
      }
      int entries = toArray( file , a );
      sysPrint("Redisplay(): 1  - The number of entries is " + entries);
      setEntries( entries ) ;
      c.remove( table ) ;
      table = new JTable( a , columnNames ) ;
      table.setEnabled( true );
      c.add( table , BorderLayout.CENTER) ;
      c.add( new JScrollPane ( table ) );
      c.validate();
   }

   /**
    * Method that respond to what button is clicked
    * @param e First index of actionPerformed
    */
   public void actionPerformed( ActionEvent e )   {

      if ( e.getSource() == refresh  )  {
         sysPrint( "\nThe Refresh button was pressed. " ) ;
         Container cc = getContentPane() ;

         table = new JTable( pData, columnNames );
            cc.validate();
      }
      else if (e.getSource() == cancel  )
         cleanup();
   }

   /**
    * Close the application
    * @exception IOException e
    */

   public void cleanup() {
      try {
         file.close();
      }
      catch ( IOException e ) {
         System.exit( 1 );
      }

      setVisible( false );
      System.exit( 0 );
   }

   /**
    * Show the Dialog when a record is deleted
    */
   public void displayDeleteDialog() {
      sysPrint ("The Delete Record Dialog was made visible.\n") ;
      deleteRec.setVisible( true );
   }

  /**
    * Show the Dialog when a record is updated
    */

   public void displayUpdateDialog() {
      sysPrint ("The Update Record Dialog was made visible.\n") ;
      JOptionPane.showMessageDialog(null,
                    "Enter the record ID to be updated and press enter.",
                    "Update Record", JOptionPane.INFORMATION_MESSAGE) ;
      update = new UpdateRec( hws, file, pData , -1);
      update.setVisible( true );
   }

   /**
    * Method that set the current number of entries
    * @param ent First index of setEntries
    */ 

   public void setEntries( int ent )   {
      numEntries = ent ;
   }


   /**
    * Get the entries
    * @return The number of entries
    */

   public int getEntries(  )   {
      return numEntries  ;
   }

   /**
    * Get the entries
    * @return The number of entries
    */
   public void sysPrint( String str  )   {
      if ( myDebug ) {
         System.out.println( str );
      }
   }

   /**
    * ordering elements in an Array
    * @param file First index of toArray
    * @param a Second index of toArray
    * @return return length of the array
    */

   public int toArray( RandomAccessFile file, String a[][] ) {

      Record NodeRef = new Record() ,
             PreviousNode  = null ;
      int index = 0 , squareIndex = 0 , fileSize = 0;

      try {
         fileSize = (int) file.length() / Record.getSize() ;
         sysPrint("toArray(): 1 - The size of the file is " + fileSize ) ;
         /** If the file is empty, do nothing.  */
         if (  fileSize > ZERO  ) {

             NodeRef.setFileLen( file.length() ) ;


             while ( index < fileSize )  {
                sysPrint( "toArray(): 2 - NodeRef.getRecID is "
                                      + NodeRef.getRecID() );

                file.seek( 0 ) ;
                file.seek( index *  NodeRef.getSize() ) ;
                NodeRef.setFilePos( index *  NodeRef.getSize() ) ;
                sysPrint( "toArray(): 3 - input data file - Read record " + index );
                NodeRef.ReadRec( file );

                String str2 = a[ index ] [ 0 ]  ;
                sysPrint( "toArray(): 4 - the value of a[ index ] [ 0 ] is " +
                                      a[ 0 ] [ 0 ] );

                if ( NodeRef.getRecID() != -1 ) {
                   a[ squareIndex ] [ 0 ]  =  String.valueOf( NodeRef.getRecID() ) ;
                   a[ squareIndex ] [ 1 ]  =  NodeRef.getToolType().trim()  ;
                   a[ squareIndex ] [ 2 ]  =  NodeRef.getBrandName().trim() ;
                   a[ squareIndex ] [ 3 ]  =  NodeRef.getToolDesc().trim()  ;
                   a[ squareIndex ] [ 4 ]  =  NodeRef.getPartNumber().trim() ;
                   a[ squareIndex ] [ 5 ]  =  String.valueOf( NodeRef.getQuantity() )  ;
                   a[ squareIndex ] [ 6 ]  =  NodeRef.getCost().trim() ;

                   sysPrint( "toArray(): 5 - 0- " + a[ squareIndex ] [ 0 ] +
                                    " 1- " + a[ squareIndex ] [ 1 ] +
                                    " 2- " + a[ squareIndex ] [ 2 ] +
                                    " 3- " + a[ squareIndex ] [ 3 ] +
                                    " 4- " + a[ squareIndex ] [ 4 ] +
                                    " 5- " + a[ squareIndex ] [ 5 ] +
                                    " 6- " + a[ squareIndex ] [ 6 ]  );

                   squareIndex++;
                }
                else {
                   sysPrint( "toArray(): 5a the record ID is " + index ) ;
                }

                index++;

            }  /** End of do-while loop   */
         }  /** End of outer if   */
      }
      catch ( IOException ex ) {
                sysPrint(  "toArray(): 6 - input data file failure. Index is " +  index
                + "\nFilesize is " + fileSize );
      }

      return index ;

   }

   //--- main function 

   /**
    * method main that start the program
    * @param args First index of main
    */
   public static void main( String args[] )
   {
      HardwareStore hwstore = new HardwareStore();
      hwstore.hws = hwstore ;
   }
}