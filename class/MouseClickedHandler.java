import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.RandomAccessFile;
import java.io.Serializable;

static class MouseClickedHandler extends MouseAdapter {

    //--- properties 

    JTable table;
    String pData[][], columnNames[];
    RandomAccessFile f;
    private Object hardwareStore;

    //---methods

    MouseClickedHandler(RandomAccessFile fPassed, JTable tablePassed,
                        String p_Data[][]) {
        table = tablePassed;
        pData = p_Data;
        f = fPassed;

    }


    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) {
            int ii = table.getSelectedRow();
            JOptionPane.showMessageDialog(null,
                    "Enter the record ID to be updated and press enter.",
                    "Update Record", JOptionPane.INFORMATION_MESSAGE);
            Update.UpdateRec update = new Update.UpdateRec((HardwareStore) hardwareStore, f, pData, ii);
            if (ii < 250) {
                update.setVisible(true);
                table.repaint();
            }
        }
    }
}
