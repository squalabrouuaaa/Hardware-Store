import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class UpdateRec extends Dialog  implements ActionListener {

    //--- properties 

    private RandomAccessFile file;
    private JTextField recID, toolType, brandName, toolDesc,
            partNum, quantity, price;
    private JLabel recIDLabel,  toolTypeLabel, brandNameLabel,
                toolDescLabel,  partNumLabel, quantityLabel,
                priceLabel;
    private JButton cancel, save;
    private Record data;
    private int theRecID, index, toCont, loopCtrl;
    private String pData [] [] ;
    private HardwareStore hwstore ;
    private boolean found = false ;

    //--- constructors 

    public UpdateRec( HardwareStore hw_store, RandomAccessFile f ,
                String p_Data [] [], int iiPassed)
    {

        super( new Frame(), "Update Record", true );
        setSize( 400, 280 );
        setLayout( new GridLayout( 9, 2 ) );
        file = f;
        pData = p_Data ;
        index = iiPassed ;
        hwstore = hw_store ;

        upDSetup() ;
    }

    //--- methods 
    
    public void upDSetup() {

        //  text fields 
        recID      = new JTextField( 10 );
        toolType   = new JTextField( 10 );
        brandName  = new JTextField( 10 );
        toolDesc   = new JTextField( 10 );
        partNum    = new JTextField( 10 );
        quantity   = new JTextField( 10 );
        price      = new JTextField( 10 );

        //labels 
        recIDLabel     = new JLabel( "Record ID" );
        toolTypeLabel  = new JLabel( "Type of Tool" );
        brandNameLabel = new JLabel( "Brand Name" );
        toolDescLabel  = new JLabel( "Tool Description" );
        partNumLabel   = new JLabel( "Part Number" );
        quantityLabel  = new JLabel( "Quantity" );
        priceLabel     = new JLabel( "Price" );

        // create the buttons 
        save = new JButton( "Save Changes" );
        cancel = new JButton( "Cancel" );

        // attach the ActionListener 
        recID.addActionListener( this );
        save.addActionListener( this );
        cancel.addActionListener( this );

        // Add the labels and text fields to the
        //  GridLayout manager context 
        add( recIDLabel );
        add( recID );
        add( toolTypeLabel );
        add( toolType );
        add( brandNameLabel );
        add( brandName );
        add( toolDescLabel );
        add( toolDesc );
        add( partNumLabel );
        add( partNum );
        add( quantityLabel );
        add( quantity );
        add( priceLabel );
        add( price );
        add( save );
        add( cancel );

        data = new Record();
    }

    /** 
    * checkDigit is used to ensure  that the data
    * entered is a digit
    */
    public boolean  checkDigit(String strVal) {

        int strLength = 0;
        boolean notDig = true;

        strLength = strVal.length();

        for (int index = 0; index < strLength; index++) {
        if (!Character.isDigit(strVal.charAt(index)) ) {
            notDig = false;
            break;
        }
        }

        return notDig;
    }

    /** 
    *  actionPerformed is the event handler that reesponds
    *  to the GUI events generated by the UpDate dialog.
    */
    public void actionPerformed( ActionEvent e )   {
        if ( e.getSource() == recID )  {
        if ( checkDigit( recID.getText() ) ) {
            theRecID = Integer.parseInt( recID.getText() );
        }
        else if ( theRecID < 0 || theRecID > 250 ) {
            JOptionPane.showMessageDialog(null,
                "A recID entered was:  less than 0 or greater than 250, which is invalid.\n" +
                "Please enter a number greater than 0 and less than 251.", "RecID Entered",
                JOptionPane.INFORMATION_MESSAGE) ;
            return;
        }

        theRecID = Integer.parseInt( recID.getText() );

        System.out.println( "UpdateRec(): 2a - The record id being sought is " + theRecID) ;

        for ( int index = 0;  index < pData.length ; index++ ) {
            if ( pData[ index  ] [ 0 ]  !=  null    )  {
                if ( Integer.parseInt( pData[ index  ] [ 0 ] ) == theRecID  ) {
                    theRecID = Integer.parseInt( pData[ index  ] [ 0 ] ) ;
                    found = true ;
                    System.out.println( "UpdateRec(): 2b - The record id was found." ) ;
                    break ;
                }
            }
        }

        try {

            file = new RandomAccessFile( hwstore.aFile , "rw" );
            file.seek( ( theRecID  ) * data.getSize() );
            data.ReadRec( file );

            recID.setText( "" + theRecID );
            toolType.setText( data.getToolType().trim() );
            brandName.setText( data.getBrandName().trim() ) ;
            toolDesc.setText( data.getToolDesc().trim() ) ;
            partNum.setText( data.getPartNumber().trim() ) ;
            quantity.setText( Integer.toString( data.getQuantity() ) );
            price.setText(  data.getCost().trim() );
            System.out.println( "UpdateRec(): 2c - The record found was " +
                data.getRecID() + " " +
                data.getBrandName() + " " +
                data.getToolDesc() + " " +
                data.getQuantity() + " " +
                data.getCost() + " in file " + hwstore.aFile) ;
        }
        catch ( IOException ex ) {
            recID.setText( "UpdateRec(): 2d -  Error reading file" );
        }

        if ( data.getRecID() >= 0 ) {
            
        }
        else
            recID.setText(  "This record " +
                theRecID + " does not exist" );
        }
        else if ( e.getSource() == save ) {
        try {
            data.setRecID( Integer.parseInt( recID.getText() ) );
            data.setToolType( toolType.getText().trim() );
            data.setBrandName( brandName.getText().trim() );
            data.setToolDesc( toolDesc.getText().trim() );
            data.setPartNumber( partNum.getText().trim() ) ;
            data.setQuantity( Integer.parseInt( quantity.getText().trim() ) );
            data.setCost(  price.getText().trim()  );

            file.seek( 0 ) ;
            file.seek(  theRecID   * data.getSize() );
            data.write( file );

            System.out.println( "UpdateRec(): 3 - The record found was " +
                data.getRecID() + " " +
                data.getBrandName() + " " +
                data.getToolDesc() + " " +
                data.getQuantity() + " " +
                data.getCost() + " in file " + hwstore.aFile) ;

            Redisplay(  file, pData ) ;
        }
        catch ( IOException ex ) {
            recID.setText( "Error writing file" );
            return;
        }

        toCont = JOptionPane.showConfirmDialog(null,
                "Do you want to add another record? \nChoose one",
                "Choose one",
                JOptionPane.YES_NO_OPTION);

        if ( toCont == JOptionPane.YES_OPTION  )  {
            recID.setText( "" );
            toolType.setText( ""  );
            quantity.setText( ""  );
            brandName.setText( ""  );
            toolDesc.setText( ""  );
            partNum.setText( ""  );
            price.setText( ""  );
        }
        else {
            upClear();
        }
        }
        else if ( e.getSource() == cancel ) {
        setVisible( false );
        upClear();
        }
    }

    private void upClear()   {
        recID.setText( "" );
        brandName.setText( "" );
        quantity.setText( "" );
        price.setText( "" );
        setVisible( false );
    }
}