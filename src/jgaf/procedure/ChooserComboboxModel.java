/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import jgaf.editor.Editor;

/**
 *
 * @author hanis
 */
public class ChooserComboboxModel extends AbstractListModel implements ComboBoxModel {

    Editor editor = null;
    Object selectedItem = null;
    private List<Editor> editors;


    ChooserComboboxModel(List<Editor> editors) {
        this.editors = editors;
    }

    public int getSize() {
        return editors.size();
    }

    public Object getElementAt(int index) {
        return editors.get(index);
    }

    public Object getSelectedItem() {
        return editor;
    }

    public void setSelectedItem(Object newValue) {
        editor = (Editor) newValue;
    }

}


