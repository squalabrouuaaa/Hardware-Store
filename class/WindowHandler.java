public class WindowHandler extends WindowAdapter {

    //--- properties 
    
      HardwareStore hardwareStore;

    //--- methods 

      public WindowHandler( HardwareStore newHardwareStore ) { hardwareStore = newHardwareStore ; }

      public void windowClosing( WindowEvent event ) { hardwareStore.cleanup(); }
   }