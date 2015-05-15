/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.register;

import java.util.ArrayList;
import java.util.List;
import jgaf.editor.EditorDescriptor;
import jgaf.editor.NoSuchEditorException;

/**
 *
 * @author hanis
 */
public class EditorRegister {



    private List<EditorDescriptor> descriptorList;

    public EditorRegister() {
        descriptorList = new ArrayList<>();
    }

    public EditorDescriptor getDescriptorById(String id) throws NoSuchEditorException {
        for (EditorDescriptor editorDescriptor : getDescriptorList()) {
            if(editorDescriptor.getId().equals(id)) {
                return editorDescriptor;
            }
        }
        throw new NoSuchEditorException();
    }


    public String getEditorClassPath(String id) throws NoSuchEditorException {
        return getDescriptorById(id).getClassPath();
    }

    public List<EditorDescriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<EditorDescriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

}
