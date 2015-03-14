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

   // private static EditorRegister INSTANCE = null;


    private List<EditorDescriptor> descriptorList;

    //private Map<String, String> editorsClassMap;
    //private Map<String, String> editorsNameMap;

    public EditorRegister() {
        descriptorList = new ArrayList<EditorDescriptor>();
//        editorsClassMap = new HashMap<String, String>();
//       // editorsClassMap.put("FA", "jgaf.automaton.fa.FSAutomatonEditor");
//         editorsClassMap.put("FA", "jgaf.automaton.fa.FSAutomatonEditor");
//        editorsClassMap.put("G", "jgaf.grammar.GrammarEditor");
//        editorsClassMap.put("RE", "jgaf.regex.RegularExpressionEditor");
//        editorsNameMap = new HashMap<String, String>();
//        editorsNameMap.put("FA", "Final State Automaton");
//        editorsNameMap.put("G", "Grammar");
//        editorsNameMap.put("RE", "Regular Expression");
    }


//    public static EditorRegister getInstance() {
//        if(INSTANCE == null) {
//            INSTANCE = new EditorRegister();
//        }
//        return INSTANCE;
//    }



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
//
//    public String getEditorName(String name) {
//        return editorsNameMap.get(name);
//    }
//
//    public Set<String> getAllEditorIDs() {
//        return editorsClassMap.keySet();
//    }
}
