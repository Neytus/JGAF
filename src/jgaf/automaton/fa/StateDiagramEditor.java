/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.automaton.fa;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jgaf.Constants.MathConstants;
import jgaf.automaton.Transition;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.fa.undo.*;
import jgaf.automaton.layouts.GEM;
import jgaf.automaton.visual.TransitionVisualProperties;
import jgaf.gui.StateDiagramRepresenter;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class StateDiagramEditor implements FSAutomatonSubEditor {


    private static final Cursor CURSOR_TEXT = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    private static final Cursor CURSOR_POINTER = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor CURSOR_CROSS = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    private static final Cursor CURSOR_HAND = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private static final Cursor CURSOR_MOVE = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);

    private final int VIRTUALTRANSITIONSITE = 20;

    private StatePopupMenu statePopupMenu;
    private LabelPopupMenu labelPopupMenu;
    private MultipleStatesPopupMenu multipleStatesPopupMenu;
    private TransitionPopupMenu transitionPopupMenu;
    private CanvasPopupMenu canvasPopupMenu;
    private InitialArrowPopupMenu initialArrowPopupMenu;

    private boolean leftMouseDown;
    private UndoRedoHandler undoHandler;
    private static double ZOOM_DEFAULT = 1;
    private static double ZOOM_MIN = 0.2;
    private static double ZOOM_MAX = 4;
    private static double ZOOM_JUMP = 0.2;
    public boolean draggingChecker = false;
    private boolean selectionDragging = false;
    private int selectionStartX = 0;
    private int selectionStartY = 0;
    private int selectionEndX = 0;
    private int selectionEndY = 0;
    private double zoomfactor = 1;
    private int offsetX = 0;
    private int offsetY = 0;
    private int oldoffsetX = 0;
    private int oldoffsetY = 0;
    private int panStartX = 0;
    private int panStartY = 0;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private double oldTansitionCurveFactor = 0;
    private double oldInitialArrowOrientation = 0;
    private List<CanvasLabel> labels;
    private Set<State> currentStatesSelected = new HashSet<State>();
    private Transition currentTransSelected = null;
    private CanvasLabel currentLabelSelected = null;
    private InitialArrow initArrow;


    private State transitionAddFrom = null;
    private State transitionAddTo = null;
    private Transition dummyTransition = new Transition(null, null, MathConstants.EPSILON);
    private EditorSelectionStates selectionState;
    private EditorState editorState;
    private EditorTransitionStates transitionState;
    private AutomatonPainter automatonPainter;
    


    private int StateNameIndex = 1;
    private String StateName = "p";

    private GEM gem;

    private boolean labelsVisibility = true;


    private StateDiagramRepresenter representer;

    private boolean editable = true;

    

    private FSAutomatonEditor editor;


    public StateDiagramEditor(Automaton automaton, CanvasLabel label) {
       this.editor = new FSAutomatonEditor();

        editor.setAutomaton(automaton);
        representer = new StateDiagramRepresenter(this);
        this.labels = new ArrayList<CanvasLabel>();
        labels.add(label);
        this.initArrow = new InitialArrow();
        getDummyTransition().getVisualProperties().setVisible(false);
        automatonPainter = new AutomatonPainter(); //AutomatonPainter();
        automatonPainter.setEditor(this);
        this.editorState = EditorState.hand;
        
        editor.setChanged(false);
    }

    public StateDiagramEditor(FSAutomatonEditor editor) {
        this.editor = editor;
        representer = new StateDiagramRepresenter(this);
        automatonPainter = new AutomatonPainter();
        automatonPainter.setEditor(this);
        undoHandler = new UndoRedoHandler();
        labels = new ArrayList<CanvasLabel>();
        setPopupMenus();
        initEditor();
        
        editor.setChanged(false);
    }


    public JFrame getMainFrame() {
        return editor.getMainFrame();
    }


    public void repaint() {
        representer.repaint();
    }

    public StateDiagramRepresenter getRepresenter() {
        return representer;
    }

    public boolean isEditable() {
        return editor.isEditable();
    }
   
    public void setEditable(boolean editable) {
        this.editable = editable;
    }


    private void setPopupMenus() {
        statePopupMenu = new StatePopupMenu();
        labelPopupMenu = new LabelPopupMenu();
        multipleStatesPopupMenu = new MultipleStatesPopupMenu();
        transitionPopupMenu = new TransitionPopupMenu();
        canvasPopupMenu = new CanvasPopupMenu();
        initialArrowPopupMenu = new InitialArrowPopupMenu();
    }


    public void paint(Graphics2D g2d) {
        if(automatonPainter != null) {
            automatonPainter.updateGraphics(g2d);
        }
    }


    public void handleRightMouseClick(MouseEvent e) {
        editor.setChanged(true);
        if (editorState == EditorState.hand) {
            removeAllSelectedFlags();
            currentTransSelected = null;
            currentLabelSelected = null;

            State state = getStateAtMouse(e.getX(), e.getY());
            if (state != null) {
                if (currentStatesSelected.contains(state)) {
                    markSelectedStates();
                    repaint();
                    if (isSingleStatesSelected()) {
                        statePopupMenu.show(this, getRepresenter(), e.getPoint());
                    } else {
                        multipleStatesPopupMenu.show(this, getRepresenter(), e.getPoint(), state);
                    }
                } else {
                    currentStatesSelected.clear();
                    currentStatesSelected.add(state);
                    markSelectedStates();
                    repaint();
                    statePopupMenu.show(this, getRepresenter(), e.getPoint());
                }
                return;
            }
            currentStatesSelected.clear();


            Transition transition = getTransitionAtMouse(e.getX(), e.getY());
            if (transition != null) {
                currentTransSelected = transition;
                markSelectedTransitions();
                repaint();
                transitionPopupMenu.show(this, getRepresenter(), e.getPoint());
                return;
            }

            CanvasLabel label = getLabelAtMouse(e.getX(), e.getY());
            if (label != null && areLabelsVisible()) {
                currentLabelSelected = label;
                markSelectedLabel();
                repaint();
                labelPopupMenu.show(this, getRepresenter(), e.getPoint());
                return;
            }
            if(isInitialArrowAtMouse(e)) {
                repaint();
                initialArrowPopupMenu.show(this, getRepresenter(), e.getPoint());
                return;
            }

            repaint();
            canvasPopupMenu.show(this, getRepresenter(), e.getPoint());
        }
    }


    public Automaton getAutomaton() {
        return editor.getAutomaton();
    }

//    public void setAutomaton(Automaton automaton) {
//        this.automaton = automaton;
//    }

    public AutomatonPainter getAutomatonPainter() {
        return automatonPainter;
    }

    public void setAutomatonPainter(AutomatonPainter automatonPainter) {
        this.automatonPainter = automatonPainter;
    }

    public Transition getDummyTransition() {
        return dummyTransition;
    }

    public void setDummyTransition(Transition dummyTransition) {
        this.dummyTransition = dummyTransition;
    }

    public boolean isAnythingSelected() {
        return !currentStatesSelected.isEmpty() || currentTransSelected != null;
    }

    public State getSingleStateSelected() {
        if (currentStatesSelected.isEmpty()) {
            return null;
        }
        return currentStatesSelected.iterator().next();
    }

    public boolean isMultipleStatesSelected() {
        if (currentStatesSelected.isEmpty()) {
            return false;
        }
        return currentStatesSelected.size() > 1;
    }

    public boolean isSingleStatesSelected() {
        if (currentStatesSelected.isEmpty()) {
            return false;
        }
        return currentStatesSelected.size() == 1;
    }

    public Set<State> getCurrentStatesSelected() {
        return currentStatesSelected;
    }

    public CanvasLabel getCurrentLabelSelected() {
        return currentLabelSelected;
    }

    public Transition getCurrentSelectedTransition() {
        return this.currentTransSelected;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isSelectionDragging() {
        return selectionDragging;
    }



    private void initEditor() {
        setEditable(true);
        this.initArrow = new InitialArrow();
        this.selectionState = EditorSelectionStates.selectNothing;
        this.editorState = EditorState.hand;
        this.transitionState = EditorTransitionStates.selectFromState;
        this.offsetX = 0;
        this.offsetY = 0;
        removeAllSelections();
        this.panStartX = 0;
        this.panStartY = 0;
        setToolEnvirionmentOptions();
    }

    public void setToolEnvirionmentOptions() {
        dummyTransition.getVisualProperties().setVisible(false);
        if (editorState == EditorState.hand) {
            setCursor(CURSOR_POINTER);
        }
        if (editorState == EditorState.addState) {
            setCursor(CURSOR_CROSS);
            
        }
        if (editorState == EditorState.addTransition) {
            dummyTransition.getVisualProperties().setVisible(false);
            setCursor(CURSOR_HAND);
        }
        if (editorState == EditorState.addLabel) {
            setCursor(CURSOR_TEXT);


        }
    }

    private void setCursor(Cursor cursor) {
        if(getRepresenter() != null) {
            getRepresenter().setCursor(cursor);
        }
    }

    public void removeAllSelections() {
        this.currentStatesSelected.clear();
        this.currentTransSelected = null;
        this.currentLabelSelected = null;
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().setSelected(false);
            state.getVisualProperties().setMouseOver(false);
        }
        for (Transition transition : getAutomaton().getTransitions()) {
            transition.getVisualProperties().setSelected(false);
            transition.getVisualProperties().setMouseOver(false);
        }
        for (CanvasLabel label : labels) {
            label.setSelected(false);
            label.setMouseOver(false);
        }
    }

    /**
     * handle the mouse PRESSED event
     * @param evt
     */
    public void handleMouseLeftPressed(java.awt.event.MouseEvent evt) {
        if (editorState == EditorState.hand) {
            setCursor(CURSOR_MOVE);
            setLeftMouseDown(true);
            boolean anyHit = handleObjectSelection(evt);
            if (!evt.isControlDown() && !anyHit) {
                selectionDragging = true;
                selectionStartX = (int) ((evt.getX() - offsetX) / zoomfactor);
                selectionStartY = (int) ((evt.getY() - offsetY) / zoomfactor);
                selectionEndX = selectionStartX;
                selectionEndY = selectionStartY;
            } else {
                selectionDragging = false;
            }
        }
        panStartX = evt.getX();
        panStartY = evt.getY();
        oldoffsetX = offsetX;
        oldoffsetY = offsetY;
        if(currentTransSelected != null) {            
            oldTansitionCurveFactor = currentTransSelected.getVisualProperties().getCurveFactor();
        }
        if(initArrow.isSelected()) {
            oldInitialArrowOrientation = initArrow.getOrientation();
        }
        updateGraphicsAll();
    }

    public void handleMouseLeftReleased(java.awt.event.MouseEvent evt) {
        if (editorState == EditorState.hand) {
            if (isLeftMouseDown()) {
                setCursor(CURSOR_POINTER);
                setLeftMouseDown(false);
                handleHandMouseLeftReleased(evt);
            }
        } else if (editorState == EditorState.addState) {
            handleAddState(evt);
        } else if (editorState == EditorState.addTransition) {
            handleAddTransitionStep(evt);
        } else if (editorState == EditorState.addLabel) {
            handleAddLabel(evt);
        }
        updateGraphicsAll();
    }

    /**
     * handle Hand Tool Mouse realease
     * @param evt
     */
    private void handleHandMouseLeftReleased(java.awt.event.MouseEvent evt) {
        if (!currentStatesSelected.isEmpty()) { // end states dragging
            handleStatesEndDrag(evt);
        } else if (currentTransSelected != null) { //end transition dragging
            handleTransitionEndDrag(evt);
        } else if (currentLabelSelected != null) { //end label dragging
            handleLabelEndDrag(evt);
        } else if (initArrow.isSelected()) {
            handleInitArrowEndDrag(evt);
//        } else if (!isSelectionDragging()) {
//            System.out.println("padam pdam pum");
//            State clickedState = getStateAtMouse(evt.getX(), evt.getY());
//            if (clickedState != null) { // mouse clicked on the state
//                if (!evt.isControlDown()) {
//                    currentStatesSelected.clear();
//                    currentStatesSelected.add(clickedState);
//                }
//            } else { //mouse clicked on the canvas
//                currentStatesSelected.clear();
//            }
//            markSelectedStates();
        } else if (isSelectionDragging()) { //end drawing selection rectangle
            currentStatesSelected = Tools.getStatesInRectangle(getAutomaton(), getSelectionRectangle());
            markSelectedStates();
            selectionDragging = false;
        } else if (oldoffsetX != offsetX || oldoffsetY != offsetY) { //end shifting offset
            undoHandler.addStep(new MoveGraphicsStep(this, offsetX, offsetY));
            applyOffsetToGraphics();
            //System.out.println("!!");            
        }       
    }

    public void handleMouseDragged(java.awt.event.MouseEvent evt) {
        if (editorState == EditorState.hand && isLeftMouseDown()) {
            handleHandMouseDragged(evt);
            updateGraphicsAll();
        }
    }

    /**
     * handle the mouse MOVED event
     * @param evt
     */
    public void handleMouseMoved(java.awt.event.MouseEvent evt) {        
        if (editorState == EditorState.hand) {
            handleHandMouseMoved(evt);
        }
        if (editorState == EditorState.addState) {
//            handleAddStateMouseMoved(evt);
        }
        if (editorState == EditorState.addTransition) {
            handleAddTransitionMouseMoved(evt);
        }
        updateGraphicsAll();
    }

    public void handleAddTransitionStep(java.awt.event.MouseEvent evt) {
        if (this.transitionState == EditorTransitionStates.selectFromState) {
            State stateHit = getStateAtMouse(evt.getX(), evt.getY());
            if (stateHit != null) {
                removeAllSelections();
                stateHit.getVisualProperties().setSelected(true);
                transitionAddFrom = stateHit;
                this.transitionState = EditorTransitionStates.selectToState;
            }
        } else {
            State stateHit = getStateAtMouse(evt.getX(), evt.getY());
            if (stateHit != null) {
                transitionAddTo = stateHit;
                this.transitionState = EditorTransitionStates.selectFromState;
                if (transitionAddFrom != null) {
                    addNewTransition(transitionAddFrom, transitionAddTo);
                    dummyTransition.getVisualProperties().setVisible(false);
                }
            } else {
                transitionAddTo = null;
                transitionAddFrom = null;
                this.transitionState = EditorTransitionStates.selectFromState;
                dummyTransition.getVisualProperties().setVisible(false);
                removeAllSelections();
            }

        }
    }

    public void handleKeyPressed(java.awt.event.KeyEvent evt) { 
        int key = evt.getKeyCode();
        if (editorState == EditorState.hand) {
            if (key == KeyEvent.VK_A && evt.isControlDown()) {
                selectAll();
            }

            if (key == KeyEvent.VK_F) {
                if (!currentStatesSelected.isEmpty()) {
                    changeAcceptingStates(currentStatesSelected, true);
                }
            }

            if (key == KeyEvent.VK_G) {
                if (!currentStatesSelected.isEmpty()) {
                    changeAcceptingStates(currentStatesSelected, false);
                }
            }

            if (key == KeyEvent.VK_DELETE) {
                handleDeleteObject();
            }

            if (key == KeyEvent.VK_RIGHT) {
                if (currentLabelSelected != null) {
                    handleLabelRotation(currentLabelSelected, 18);
                }
            }

            if (key == KeyEvent.VK_LEFT) {
                if (currentLabelSelected != null) {
                    handleLabelRotation(currentLabelSelected, -18);
                }
            }

            if (key == KeyEvent.VK_CONTEXT_MENU) {
                handleContexMenuPressed();
            }



//            if (key == KeyEvent.VK_U) {
//                writeUndoStack();
//            }
//
//            if (key == KeyEvent.VK_R) {
//                writeRedoStack();
//            }

//        if (evt.getKeyCode() == KeyEvent.VK_X) {
//            gem = new GEM(automaton);
//            gem.arrangeAutomaton();
//            centerAndScaleGraphics(true);
//            updateGraphicsAll();
//        }
//
//
//        if (evt.getKeyCode() == KeyEvent.VK_N) {
//            gem = new GEM(automaton);
//        }
//
//        if (evt.getKeyCode() == KeyEvent.VK_M) {
//            gem.setLayout(automaton);
//            updateGraphicsAll();
//        }
//
//        if (evt.getKeyCode() == KeyEvent.VK_B) {
//            gem.arrange();
//            updateGraphicsAll();
//        }

        }


        /////////////////////////test//////////////////
        if (key == KeyEvent.VK_X) {
            System.out.println("eps: " + getAutomaton().containsEpsilonSteps());
            System.out.println("nfa: " + getAutomaton().containsNonDeterminism());
        }

    }



    private void handleContexMenuPressed() {
        if (isSingleStatesSelected()) {
            statePopupMenu.show(this, getRepresenter(), getSingleStateSelected().getVisualProperties().getPoint());
        } else if (isMultipleStatesSelected()) {
            multipleStatesPopupMenu.show(this, getRepresenter(),
                    getSingleStateSelected().getVisualProperties().getPoint(),
                    getSingleStateSelected());
        } else if (currentTransSelected != null) {
            int x = currentTransSelected.getVisualProperties().getClickPositionX();
            int y = currentTransSelected.getVisualProperties().getClickPositionY();
            transitionPopupMenu.show(this, getRepresenter(), new Point(x,y));
        } else if (currentLabelSelected != null && areLabelsVisible()) {
            labelPopupMenu.show(this, getRepresenter(), currentLabelSelected.getPoint());
        }
    }

    public void handleSettingLabelRotationFactor(CanvasLabel label, int rotation) {
        int oldRotation = label.getRotationFactor();
        if (oldRotation != rotation) {
            label.setRotationFactor(rotation);
            undoHandler.addStep(new ChangeLabelRotationStep(label, oldRotation, rotation));
            updateGraphicsAll();
        }        
    }


    public void handleLabelRotation(CanvasLabel label, int angle) {
        handleSettingLabelRotationFactor(label, label.getRotationFactor() + angle);
    }


    public void handleDeleteObject() {
        System.out.println("states: " +currentStatesSelected + ", tran. :" + currentTransSelected);
        if (!currentStatesSelected.isEmpty()) {
            removeStates(currentStatesSelected);
        } else if (currentTransSelected != null) {
            removeTransition(currentTransSelected);
        } else if (currentLabelSelected != null) {
            handleRemoveLabel(currentLabelSelected);
        }
    }


    private String getStringFromInputBox(String defaultString) {
//        String string = (String) JOptionPane.showInputDialog(frame,
//                    "Input a new label for the transition.", "Add Transition Label",
//                    JOptionPane.PLAIN_MESSAGE, null, null, "");
//
        String string = (String) JOptionPane.showInputDialog(getRepresenter(),
                    "Input a new label for the transition.", "Add Transition Label",
                    JOptionPane.PLAIN_MESSAGE, null, null, defaultString);
        return string;

    }




    private void addNewTransition(State from, State to) {
        editor.setChanged(true);
        boolean isNew = !getAutomaton().containsTransition(from, to);
        if(isNew) {
            String labelsString = getStringFromInputBox("");
            SortedSet newLabels = Tools.getSortedSetFromString(labelsString);
            Transition transition = null;
            if(newLabels.isEmpty()) {
                transition = getAutomaton().addTransition(from, to, MathConstants.EPSILON);
            } else {
                transition = getAutomaton().addTransition(from, to, newLabels);
            }
            if(from.equals(to)) {
                transition.getVisualProperties().setCurveFactor(3*Math.PI/2);
            }
            MultiStep multiStep = new MultiStep();
            if(getAutomaton().isBidirectionalTransition(transition)) {
                Transition oppositeDisrection = getAutomaton().getReverseDirectionTransition(transition);
                if(oppositeDisrection.getVisualProperties().getCurveFactor() == 0) {
                    oppositeDisrection.getVisualProperties().setCurveFactor(1);
                    transition.getVisualProperties().setCurveFactor(1);
                    multiStep.addStep(new ChangeTransitionCurveFactorStep(oppositeDisrection, 0, 1));
                }
            }
            multiStep.addStep(new AddTransitionStep(getAutomaton(), transition));
            undoHandler.addStep(multiStep);
            removeAllSelections();
            updateGraphicsAll();
        }
    }



    private void handleHandMouseDragged(java.awt.event.MouseEvent evt) {
        if (!currentStatesSelected.isEmpty()) {
            handleStateMovement(evt);
            return;
        }
        if (currentTransSelected != null) {
            handleTransitionMovement(evt);
            return;
        }
        if (currentLabelSelected != null) {
            handleLabelMovement(evt);
            return;
        }
        if(initArrow.isSelected()) {
            handleInitArrowMovement(evt);
            return;
        }
        if (isSelectionDragging()) {
            selectionEndX = (int) ((evt.getX() - offsetX) / zoomfactor);
            selectionEndY = (int) ((evt.getY() - offsetY) / zoomfactor);
            markStatesInSelectionRectangle();
            return;
        } else {
            handleCanvasMovement(evt);
        }

    }

    public void markStatesInSelectionRectangle() {
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().setMouseOver(false);
        }
        for (State state : Tools.getStatesInRectangle(getAutomaton(), getSelectionRectangle())) {
            state.getVisualProperties().setMouseOver(true);
        }
    }


    private void removeAllSelectedFlags() {
        for (CanvasLabel label : getLabels()) {
            label.setSelected(false);
        }
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().setSelected(false);
        }
        for (Transition transition : getAutomaton().getTransitions()) {
            transition.getVisualProperties().setSelected(false);
        }
        getInitArrow().setSelected(false);
    }

    private void removeAllMouseOverFlags() {
        for (CanvasLabel label : getLabels()) {
            label.setMouseOver(false);
        }
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().setMouseOver(false);
        }
        for (Transition transition : getAutomaton().getTransitions()) {
            transition.getVisualProperties().setMouseOver(false);
        }
        getInitArrow().setMouseOver(false);
    }

    public void markSelectedStates() {
        for (State state : currentStatesSelected) {
            state.getVisualProperties().setSelected(true);
        }
    }

    public void markSelectedLabel() {
        if (currentLabelSelected != null) {
            currentLabelSelected.setSelected(true);
        }
    }

    public void markSelectedTransitions() {
        if (currentTransSelected != null) {
            currentTransSelected.getVisualProperties().setSelected(true);
        }
    }

    private void handleTransitionMovement(java.awt.event.MouseEvent evt) {
        editor.setChanged(true);
        if (currentTransSelected != null) {
            if (currentTransSelected.isReflexive()) {
                int x = (int) (currentTransSelected.getFromState().getVisualProperties().getXPos() * zoomfactor);
                int y = (int) (currentTransSelected.getFromState().getVisualProperties().getYPos() * zoomfactor);
                double dx = evt.getX() - x;
                double dy = evt.getY() - y;

                double angle = Math.atan(dy/dx);
//                angle = Math.atan(dy/dx);
//                if (angle < 0) {
//                    angle += 2*Math.PI;
//                }
                if (dx < 0) {
                    angle += Math.PI;
                }
                

                currentTransSelected.getVisualProperties().setCurveFactor(angle);
        //        System.out.println(angle + ", " + dx);
            } else {
                int s1x = currentTransSelected.getFromState().getVisualProperties().getXPos();
                int s1y = currentTransSelected.getFromState().getVisualProperties().getYPos();
                int s2x = currentTransSelected.getToState().getVisualProperties().getXPos();
                int s2y = currentTransSelected.getToState().getVisualProperties().getYPos();
                double dx = s2x - s1x;
                double dy = s2y - s1y;


                double l = 0;

                double vlength = getVectorLength(dx, dy);

                if (vlength > 0) {
                    double centerx = zoomfactor * (s2x + s1x) / 2;
                    double centery = zoomfactor * (s2y + s1y) / 2;

                    double normx = zoomfactor * dx / vlength;
                    double normy = zoomfactor * dy / vlength;

                    double turnedx = normy;
                    double turnedy = -normx;

                    double ddx = evt.getX() - offsetX - centerx;
                    double ddy = evt.getY() - offsetY - centery;


                    l = (6 * (ddx * turnedx + ddy * turnedy) / vlength) / (zoomfactor * zoomfactor);
                }
                currentTransSelected.getVisualProperties().setCurveFactor(l);
            }
        }

    }

    /**
     * helping function to get the euclidian length of a vector
     * @param dx
     * @param dy
     * @return
     */
    private double getVectorLength(double dx, double dy) {
        if (dx == 0 && dy == 0) {
            return 0;
        } else {
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    /**
     * handle the mouse drag of a state
     * @param evt
     */
//    private void handleAddStateMouseMoved(java.awt.event.MouseEvent evt) {
//        dummyState.getVisualProperties().setVisible(true);
//        dummyState.getVisualProperties().setXPos(evt.getX() - (int) getOffsetX());
//        dummyState.getVisualProperties().setYPos(evt.getY() - (int) getOffsetY());
//    }

    /**
     * handle the ADDING state procedure
     * @param evt
     */
    private void handleAddState(java.awt.event.MouseEvent evt) {
      //  dummyState.getVisualProperties().setVisible(false);
        editor.setChanged(true);
        removeAllSelections();
        State newState = new State(generateNewStateName());
        newState.getVisualProperties().setSelected(true);
        newState.getVisualProperties().setXPos((int) ((evt.getX() - getOffsetX()) / zoomfactor));
        newState.getVisualProperties().setYPos((int) ((evt.getY() - getOffsetY()) / zoomfactor));


//        newState.getVisualProperties().setXPos((int) (this.dummyState.getVisualProperties().getXPos() / zoomfactor));
//        newState.getVisualProperties().setYPos((int) (this.dummyState.getVisualProperties().getYPos() / zoomfactor));
        if (getAutomaton().addState(newState)) {
            undoHandler.addStep(new AddStateStep(getAutomaton(), newState));
        }
        updateGraphicsAll();
    }


    private String generateNewStateName() {
        while(getAutomaton().getStateByName(StateName + StateNameIndex) != null) {
            StateNameIndex++;
        }
        return StateName + StateNameIndex;
    }

    private void handleAddLabel(java.awt.event.MouseEvent evt) {
        removeAllSelections();
        String caption = JOptionPane.showInputDialog(getRepresenter(), "Input a label text." , "New label", JOptionPane.DEFAULT_OPTION);
        if(caption != null && !caption.equals("")) {
            CanvasLabel label = new CanvasLabel(caption);
            label.setXPos((int) (evt.getX() / zoomfactor));
            label.setYPos((int) (evt.getY() / zoomfactor));
            undoHandler.addStep(new addLabelStep(this, label));
            addLabel(label);
            repaint();
        }
    }


    public void showLabels(boolean visible) {
        labelsVisibility = visible;
        if(!visible) {
            currentLabelSelected = null;
        }
        repaint();
    }

    public boolean areLabelsVisible() {
        return labelsVisibility;
    }



    private boolean handleObjectSelection(java.awt.event.MouseEvent evt) {
        removeAllSelectedFlags();


        State stateHit = getStateAtMouse(evt.getX(), evt.getY());
        if (stateHit != null) {
            if (!currentStatesSelected.contains(stateHit)) {
                if (!evt.isControlDown()) {
                    currentStatesSelected.clear();
                }
                currentStatesSelected.add(stateHit);
            } else if (evt.isControlDown()) {
                currentStatesSelected.remove(stateHit);
            }
            for (State state : currentStatesSelected) {
                dragOffsetX = (int) (evt.getX() - offsetX - state.getVisualProperties().getXPos() * zoomfactor);
                dragOffsetY = (int) (evt.getY() - offsetY - state.getVisualProperties().getYPos() * zoomfactor);
                state.getVisualProperties().setDragOffsetX(dragOffsetX);
                state.getVisualProperties().setDragOffsetY(dragOffsetY);
            }
            markSelectedStates();
            return true;
        }
        currentStatesSelected.clear();


        Transition transHit = getTransitionAtMouse(evt.getX(), evt.getY());
        if (transHit != null) {
            currentTransSelected = transHit;
            markSelectedTransitions();
            return true;
        }
        currentTransSelected = null;


        CanvasLabel labelHit = getLabelAtMouse(evt.getX(), evt.getY());
        if (labelHit != null) {
            currentLabelSelected = labelHit;
            dragOffsetX = (int) (evt.getX() - offsetX - labelHit.getXPos() * zoomfactor);
            dragOffsetY = (int) (evt.getY() - offsetY - labelHit.getYPos() * zoomfactor);
            labelHit.setDragOffsetX(dragOffsetX);
            labelHit.setDragOffsetY(dragOffsetY);
            markSelectedLabel();
            return true;
        }
        currentLabelSelected = null;

        if(isInitialArrowAtMouse(evt)) {
            initArrow.setSelected(true);
            return true;
        }
        return false;
    }


    private CanvasLabel getLabelAtMouse(int mouseX, int mouseY) {
        if(!areLabelsVisible()) {
            return null;
        }
        for (CanvasLabel label : getLabels()) {
            if(label.getOutline().contains(mouseX, mouseY)) {
                return label;
            }
        }
        return null;
    }


    private State getStateAtMouse(int mouseX, int mouseY) {
        double actualX = (mouseX - offsetX) / zoomfactor;
        double actualY = (mouseY - offsetY) / zoomfactor;

        for (State state : getAutomaton().getStates()) {
            double dx = actualX - state.getVisualProperties().getXPos();
            double dy = actualY - state.getVisualProperties().getYPos();
            int radiusSquared = automatonPainter.getStateRadius() * automatonPainter.getStateRadius();
            if ((dx * dx + dy * dy) < radiusSquared) {
                return state;
            }
        }
        return null;
    }

    private Transition getTransitionAtMouse(int mouseX, int mouseY) {
        double actualX = (mouseX - offsetX) / zoomfactor;
        double actualY = (mouseY - offsetY) / zoomfactor;

        for (Transition transition : getAutomaton().getTransitions()) {
            double dx = actualX - transition.getVisualProperties().getClickPositionX();
            double dy = actualY - transition.getVisualProperties().getClickPositionY();
            if ((dx * dx + dy * dy) < VIRTUALTRANSITIONSITE * VIRTUALTRANSITIONSITE / 4) {
                return transition;
            }
        }
        return null;
    }

    private void handleHandMouseMoved(java.awt.event.MouseEvent evt) {                
        removeAllMouseOverFlags();
        State state = getStateAtMouse(evt.getX(), evt.getY());
       // System.out.println(state);
        if(state != null) {
            state.getVisualProperties().setMouseOver(true);
            return;
        }
        Transition transition = getTransitionAtMouse(evt.getX(), evt.getY());
        if (transition != null) {
            transition.getVisualProperties().setMouseOver(true);
            return;
        }
        CanvasLabel label = getLabelAtMouse(evt.getX(), evt.getY());
        if(label != null) {
            label.setMouseOver(true);
            return;
        }
        initArrow.setMouseOver(isInitialArrowAtMouse(evt));

    }

    private boolean isInitialArrowAtMouse(java.awt.event.MouseEvent evt) {
        if (!getAutomaton().hasInitialState()) {
            return false;
        }
        return initArrow.containsPoint(evt.getPoint());
    }



    private void handleAddTransitionMouseMoved(java.awt.event.MouseEvent evt) {
        removeAllMouseOverFlags();
        State stateHit = getStateAtMouse(evt.getX(), evt.getY());
        if (stateHit != null) {
            editor.setChanged(true);
            if (transitionState == EditorTransitionStates.selectToState && transitionAddFrom != null) {
                dummyTransition.setFromState(transitionAddFrom);
                dummyTransition.setToState(stateHit);
                dummyTransition.getVisualProperties().setVisible(true);
                if(dummyTransition.isReflexive()) {
                    dummyTransition.getVisualProperties().setCurveFactor(3*Math.PI/2);
                } else if(getAutomaton().isBidirectionalTransition(dummyTransition)) {
                    dummyTransition.getVisualProperties().setCurveFactor(1);
                } else {
                    dummyTransition.getVisualProperties().setCurveFactor(0);
                }
            }
            stateHit.getVisualProperties().setMouseOver(true);
        } else {
            dummyTransition.getVisualProperties().setVisible(false);
        }        
    }

    /**
     * handle the state movement procedure
     * @param evt
     */
    private void handleStateMovement(java.awt.event.MouseEvent evt) {
        if (!currentStatesSelected.isEmpty()) {
            editor.setChanged(true);
            for (State state : currentStatesSelected) {
                double actualX = (evt.getX() - state.getVisualProperties().getDragOffsetX() - offsetX);
                double actualY = (evt.getY() - state.getVisualProperties().getDragOffsetY() - offsetY);
                int newX = (int) (actualX / zoomfactor);
                int newY = (int) (actualY / zoomfactor);
                if(newX <= getAutomatonPainter().getStateRadius() || newY <= getAutomatonPainter().getStateRadius()) {
                    return;
                }
            }
            for (State state : currentStatesSelected) {
                double actualX = (evt.getX() - state.getVisualProperties().getDragOffsetX() - offsetX);
                double actualY = (evt.getY() - state.getVisualProperties().getDragOffsetY() - offsetY);
                int newX = (int) (actualX / zoomfactor);
                int newY = (int) (actualY / zoomfactor);
                if(newX > getAutomatonPainter().getStateRadius() && newY > getAutomatonPainter().getStateRadius()) {
                    state.getVisualProperties().setXPos(newX);
                    state.getVisualProperties().setYPos(newY);
                }
            }
        }
    }

    private void handleCanvasMovement(java.awt.event.MouseEvent evt) {
        int newOffsetX = oldoffsetX + evt.getX() - panStartX;
        int newOffsetY = oldoffsetY + evt.getY() - panStartY;

        for (CanvasLabel label : labels) {
            double actualX = (label.getXPos()) * zoomfactor;
            double actualY = (label.getYPos() - 15) * zoomfactor;
            //System.out.println("aY:" + actualY + ", offY:" + newOffsetY);
            if (actualX + newOffsetX < 0 || newOffsetY + actualY < 0) {
                return;
            }
        }

        for (State state : getAutomaton().getStates()) {
            double actualX = (state.getVisualProperties().getXPos() - getAutomatonPainter().getStateRadius()) * zoomfactor;
            double actualY = (state.getVisualProperties().getYPos() - getAutomatonPainter().getStateRadius()) * zoomfactor;
            //System.out.println("aY:" + actualY + ", offY:" + newOffsetY);
            if (actualX + newOffsetX < 0 || newOffsetY + actualY < 0) {
                return;
            }
        }
        setOffsetX(newOffsetX);
        setOffsetY(newOffsetY);
    }

    /**
     * handle the state movement procedure
     * @param evt
     */
    private void handleLabelMovement(java.awt.event.MouseEvent evt) {
        if (currentLabelSelected != null) {
            editor.setChanged(true);
            double actualX = (evt.getX() - currentLabelSelected.getDragOffsetX() - offsetX);
            double actualY = (evt.getY() - currentLabelSelected.getDragOffsetY() - offsetY);
            int newX = (int) (actualX / zoomfactor);
            int newY = (int) (actualY / zoomfactor);
            if(newX > 0 && newY > 15 * zoomfactor) {
                currentLabelSelected.setXPos(newX);
                currentLabelSelected.setYPos(newY);
            }
        }
    }


    private void handleInitArrowMovement(java.awt.event.MouseEvent evt) {
        if (initArrow.isSelected()) {
            editor.setChanged(true);
            int x = (int) (initArrow.getStateCenter().getX() * zoomfactor);
            int y = (int) (initArrow.getStateCenter().getY() * zoomfactor);
            double dx = evt.getX() - x;
            double dy = evt.getY() - y;
            double angle = Math.atan(dy / dx);
            if (dx >= 0) {
                angle += Math.PI;
            }
            initArrow.setOrientation(angle);
        }
    }


    private void handleTransitionEndDrag(java.awt.event.MouseEvent evt) {
        if (currentTransSelected != null) {
            editor.setChanged(true);
            double curveFactor = currentTransSelected.getVisualProperties().getCurveFactor();
            if (Math.abs(curveFactor - oldTansitionCurveFactor) > 0.0001) {
                undoHandler.addStep(new ChangeTransitionCurveFactorStep(currentTransSelected, oldTansitionCurveFactor, curveFactor));
            }
        }
    }

    private void handleStatesEndDrag(java.awt.event.MouseEvent evt) {
       // handleStateMovement(evt);
        int offX = evt.getX() - panStartX;
        int offY = evt.getY() - panStartY;
        if (offX != 0 || offY != 0) {
            editor.setChanged(true);
            Set<State> states = new HashSet<State>(currentStatesSelected);
            undoHandler.addStep(new MoveStatesStep(states, (int) (offX/zoomfactor), (int) (offY/zoomfactor)));
        }
    }

    private void handleLabelEndDrag(java.awt.event.MouseEvent evt) {
        //handleLabelMovement(evt);
        int offX = evt.getX() - panStartX;
        int offY = evt.getY() - panStartY;
        if (offX != 0 || offY != 0) {
            editor.setChanged(true);
            undoHandler.addStep(new MoveLabelStep(currentLabelSelected, (int) (offX/zoomfactor), (int) (offY/zoomfactor)));
        }
    }

    private void handleInitArrowEndDrag(java.awt.event.MouseEvent evt) {
        if (initArrow.isSelected()) {
            editor.setChanged(true);
            double newInitialArrowOrientation = initArrow.getOrientation();
            if (Math.abs(oldInitialArrowOrientation - newInitialArrowOrientation) > 0.00001) {
                undoHandler.addStep(new ChangeInitialArrowOrientationStep(initArrow, oldInitialArrowOrientation, newInitialArrowOrientation));
                repaint();
            }
        }
    }

    

    
    private void updateGraphicsAll() {
        repaint();
//        if(automatonTablePainter == null) {
//            automatonPainter.requestRepaintAll();
//        } else {
//            automatonTablePainter.requestRepaintAll();
//        }
    }


    public double getZoomfactor() {
        return zoomfactor;
    }

    public void setZoomfactor(double zoomfactor) {
        this.zoomfactor = zoomfactor;
    }

    public EditorSelectionStates getSelectionState() {
        return selectionState;
    }

    public void setSelectionState(EditorSelectionStates selectionState) {
        this.selectionState = selectionState;
    }

    public EditorState getToolState() {
        return editorState;
    }

    public void setToolState(EditorState toolState) {
        this.editorState = toolState;
        setToolEnvirionmentOptions();
        removeAllSelections();
    }

    public EditorTransitionStates getTransitionState() {
        return transitionState;
    }

    public void setTransitionState(EditorTransitionStates transitionState) {
        this.transitionState = transitionState;
    }

    /**
     * @return the selectionStartX
     */
    public int getSelectionStartX() {
        return selectionStartX;
    }

    /**
     * @return the selectionStartY
     */
    public int getSelectionStartY() {
        return selectionStartY;
    }

    /**
     * @return the selectionEndX
     */
    public int getSelectionEndX() {
        return selectionEndX;
    }

    /**
     * @return the selectionEndY
     */
    public int getSelectionEndY() {
        return selectionEndY;
    }

    public Rectangle getSelectionRectangle() {
        int x = Math.min(selectionStartX, selectionEndX);
        int y = Math.min(selectionStartY, selectionEndY);
        int w = Math.abs(selectionStartX - selectionEndX);
        int h = Math.abs(selectionStartY - selectionEndY);
        return new Rectangle(x, y, w, h);
    }

    public Rectangle getSelectionRectangle2() {
        int x = (int) ((Math.min(selectionStartX, selectionEndX) + offsetX) / zoomfactor);
        int y = (int) ((Math.min(selectionStartY, selectionEndY) + offsetY) / zoomfactor);
        int w = (int) (Math.abs(selectionStartX - selectionEndX) / zoomfactor);
        int h = (int) (Math.abs(selectionStartY - selectionEndY) / zoomfactor);
        return new Rectangle(x, y, w, h);
    }

    public void applyOffsetToGraphics() {
        moveGraphics(offsetX, offsetY);
        clearOffset();
    }


    public void moveGraphics(int x, int y) {
        int moveX = (int) (x / zoomfactor);
        int moveY = (int) (y / zoomfactor);
        for (CanvasLabel label : labels) {
            label.move(moveX, moveY);
        }
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().move(moveX, moveY);
        }
    }


    public void centerZoom(double oldZoom) {
//        //  System.out.println(automatonPainter.getPaintPanel().getSize().getWidth());
    //    double zoomChange = zoomfactor - oldZoom;
    //    double zoomRatio = zoomfactor / oldZoom;
//        System.out.println("ration: " + zoomRatio);
  //  if (zoomChange > 0) {
//                      offsetX = offsetX * zoomRatio;
//              offsetY = offsetY * zoomRatio;
//
      //      offsetX -= (automatonPainter.getPaintPanel().getSize().getWidth() / 2) * zoomChange;
      //      offsetY -= (automatonPainter.getPaintPanel().getSize().getHeight() / 2) * zoomChange ;
        //    applyOffsetToGraphics();
////
//     } else {
//              offsetX -= (automatonPainter.getPaintPanel().getSize().getWidth() / 2) * zoomChange;
//              offsetY -= (automatonPainter.getPaintPanel().getSize().getHeight() / 2) * zoomChange;
//              offsetX = offsetX * zoomRatio;
//              offsetY = offsetY * zoomRatio;

////            offsetX *= zoomRatio;
////            offsetY *= zoomRatio;
//
 //        }
     //   System.out.println(offsetX + ", " + offsetY);
    }


    public void setZoomInPercentage(double zoom) {
        zoom = zoom/100.0;
        if(zoom != zoomfactor) {
            double oldZoom = zoomfactor;
            zoomfactor = zoom;
         //   centerZoom(oldZoom);
            undoHandler.addStep(new ZoomStep(this, oldZoom, zoomfactor));
            updateGraphicsAll();
        }
    }

    public void zoomIn() {
        if (zoomfactor < ZOOM_MAX) {
            double oldZoom = zoomfactor;
            zoomfactor += ZOOM_JUMP;
            if (zoomfactor > ZOOM_MAX) {
                zoomfactor = ZOOM_MAX;
            }
           // centerZoom(oldZoom);
            undoHandler.addStep(new ZoomStep(this, oldZoom, zoomfactor));
            updateGraphicsAll();
        }
    }

    public void zoomOut() {
        if (zoomfactor > ZOOM_MIN) {
            // offsetX += 90;
            // offsetY += 30;
            double oldZoom = zoomfactor;
            zoomfactor -= ZOOM_JUMP;
            if (zoomfactor < ZOOM_MIN) {
                zoomfactor = ZOOM_MIN;
            }
          //  centerZoom(oldZoom);
            undoHandler.addStep(new ZoomStep(this, oldZoom, zoomfactor));
            updateGraphicsAll();
        }
    }

    public void clearZoom() {
        if (zoomfactor != ZOOM_DEFAULT) {
            undoHandler.addStep(new ZoomStep(this, zoomfactor, ZOOM_DEFAULT));
            double oldZoom = zoomfactor;
            zoomfactor = ZOOM_DEFAULT;
         //   centerZoom(oldZoom);
            updateGraphicsAll();
        }
    }
    

    public void clearOffset() {
        offsetX = 0;
        offsetY = 0;
    }

    public void undo() {
        editor.setChanged(true);
        undoHandler.undo();
        removeAllSelections();
        updateGraphicsAll();
    }

    public void redo() {
        editor.setChanged(true);
        undoHandler.redo();
        removeAllSelections();
        updateGraphicsAll();
    }

    public void applyGEMLayout() {
        editor.setChanged(true);
        gem = new GEM(getAutomaton());
        gem.arrangeAutomaton();
        centerAndScaleGraphics(true);
        updateGraphicsAll();
    }


    public void modifyLabel(CanvasLabel label, CanvasLabel newLabel) {
        if(!label.isEqualTo(newLabel)) {
            editor.setChanged(true);
            CanvasLabel oldLable = (CanvasLabel) label.clone();
            label.modify(newLabel);
            undoHandler.addStep(new ModifyLabelStep(label, oldLable, newLabel));
        }
        repaint();
    }

    

    public void modifyTransition(Transition transition, Transition newTransition) {
        if(!transition.getLabels().equals(newTransition.getLabels()) 
                || !transition.getVisualProperties().equals(newTransition.getVisualProperties())) {
            editor.setChanged(true);
            Transition oldTransition = new Transition(transition.getFromState(), transition.getToState());
            oldTransition.setVisualProperties((TransitionVisualProperties) transition.getVisualProperties().clone());
            oldTransition.setLabels(transition.getLabels());
            
            transition.getVisualProperties().modify(newTransition.getVisualProperties());
            transition.setLabels(newTransition.getLabels());
            
            undoHandler.addStep(new ModifyTransitionStep(transition, oldTransition, newTransition));
        }
        repaint();
    }

    public void modifyState(State state, State newState, boolean initial) {
        if(!state.equalsUpToInitial(newState) || state.isInitial() != initial) {
            editor.setChanged(true);
            State oldState = (State) state.clone();
            State lastInitialState = getAutomaton().getInitialState();
            getAutomaton().modifyState(state, newState, initial);
            undoHandler.addStep(new ModifyStateStep(getAutomaton(), state, oldState, newState, lastInitialState));
        }
        repaint();
    }


    public void modifyInitialArrow(InitialArrow newInitialArrow) {
        if(!initArrow.hasSameBasicProperties(newInitialArrow)) {
            editor.setChanged(true);
            InitialArrow oldInitialArrow = (InitialArrow) initArrow.clone();
            initArrow.modify(newInitialArrow);
            undoHandler.addStep(new ModifyInitialArrowStep(initArrow, oldInitialArrow, newInitialArrow));
        }
        repaint();
    }






    public void changeAcceptingState(State state, boolean accepting) {
        Set<State> set = new HashSet<State>();
        set.add(state);
        changeAcceptingStates(set, accepting);
    }


    public void changeAcceptingStates(Set<State> states, boolean accepting) {
        editor.setChanged(true);
        List<State> changedStates = new ArrayList<State>();
        for (State state : states) {
            if (state.isAccepting() != accepting) {
                changedStates.add(state);
            }
        }
        if (!changedStates.isEmpty()) {
            if (accepting) {
                getAutomaton().addAcceptingStates(changedStates);
            } else {
                getAutomaton().removeAcceptingStates(changedStates);
            }
            undoHandler.addStep(new ChangeAcceptingStatesStep(getAutomaton(), changedStates, accepting));
            updateGraphicsAll();
        }
    }


    public void swapAcceptingStates(Set<State> states) {
        if (getAutomaton().swapAcceptingStates(states)) {
            editor.setChanged(true);
            undoHandler.addStep(new SwapAcceptingStatesStep(getAutomaton(), new ArrayList<State>(states)));
            updateGraphicsAll();
        }
    }



    public void lineUpStatesVertically(Set<State> states, State privilegedState) {
        boolean anyChange = false;
        Map<State, Integer> statesShiftMap = new HashMap<State, Integer>();
        if(states.size() > 1) {
            for (State state : states) {
                int shift = privilegedState.getVisualProperties().getXPos() - state.getVisualProperties().getXPos();
                if(shift != 0) {
                    anyChange = true;
                    statesShiftMap.put(state, shift);
                    state.getVisualProperties().move(shift, 0);
                }
            }
            if(anyChange) {
                editor.setChanged(true);
                undoHandler.addStep(new LineUpStatesStep(statesShiftMap, LineUpStatesStep.VERTICALLY));
                repaint();
            }
        }
    }


    public void lineUpStatesHorizontally(Set<State> states, State privilegedState) {
        boolean anyChange = false;
        Map<State, Integer> statesShiftMap = new HashMap<State, Integer>();
        if(states.size() > 1) {
            for (State state : states) {
                int shift = privilegedState.getVisualProperties().getYPos() - state.getVisualProperties().getYPos();
                if(shift != 0) {
                    anyChange = true;
                    statesShiftMap.put(state, shift);
                    state.getVisualProperties().move(0, shift);
                }
            }
            if(anyChange) {
                editor.setChanged(true);
                undoHandler.addStep(new LineUpStatesStep(statesShiftMap, LineUpStatesStep.HORIZONTALLY));
                repaint();
            }
        }
    }






    public void changeInitialState(State state, boolean initial) {
        if(initial != state.isInitial()) {
            editor.setChanged(true);
            if(initial) {
                getAutomaton().setInitialState(state);
            } else {
                getAutomaton().removeInitialState();
            }
            undoHandler.addStep(new ChangeInitialStateStep(getAutomaton(), state, initial));
            updateGraphicsAll();
        }
    }


    public void changeTransitionLabels(Transition transition, SortedSet<String> labels) {
        SortedSet<String> oldLabels = transition.getLabels();
        if(transition.setLabels(labels)) {
            editor.setChanged(true);
            undoHandler.addStep(new ChangeTransitionLabelsStep(transition, oldLabels, labels));
            updateGraphicsAll();
        }
    }

    public void addTransitionLabel(Transition transition, String label) {
        if (transition.addLabel(label)) {
            editor.setChanged(true);
            undoHandler.addStep(new AddTransitionLabelStep(transition, label));
            updateGraphicsAll();
        }
    }


    public void addEpsilonTransition(Transition transition) {
        editor.setChanged(true);
        addTransitionLabel(transition, MathConstants.EPSILON);
    }



    public void removeTransition(Transition transition) {
        if (getAutomaton().removeTransition(transition)) {
            editor.setChanged(true);
            undoHandler.addStep(new RemoveTransitionStep(getAutomaton(), transition));
        }
        currentTransSelected = null;
        repaint();
    }

    public void changeTransitionCurveFactor(Transition transition, double factor) {
        double oldFactor = transition.getVisualProperties().getCurveFactor();
        if(oldFactor != factor) {
            editor.setChanged(true);
            transition.getVisualProperties().setCurveFactor(factor);
            undoHandler.addStep(new ChangeTransitionCurveFactorStep(transition, oldFactor, factor));
            updateGraphicsAll();
        }
    }


    public void removeStates(Set<State> states) {
        editor.setChanged(true);
        undoHandler.addStep(new RemoveStatesStep(states, getAutomaton()));
        getAutomaton().removeStates(states);
        currentStatesSelected.clear();
        updateGraphicsAll();
    }

    public void removeState(State state) {
        Set<State> states = new HashSet<State>();
        states.add(state);
        removeStates(states);
    }



    public void renameState(State state, String newName) {
        String oldName = state.getName();
        if(getAutomaton().renameState(state, newName)) {
            editor.setChanged(true);
            undoHandler.addStep(new RenameStateStep(getAutomaton(), state, oldName, newName));
        } else {
            if(!oldName.equals(newName)) {
                JOptionPane.showMessageDialog(getRepresenter(),
                    "The automaton already contains state named \"" + newName + "\".", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
        repaint();
    }





    public void changeInitialArrowOrientation(double angle) {
        double oldAngle = initArrow.getOrientation();
        if (Math.abs(oldAngle - angle) > 0.00001) {
            editor.setChanged(true);
            undoHandler.addStep(new ChangeInitialArrowOrientationStep(initArrow, oldAngle, angle));
            initArrow.setOrientation(angle);
            repaint();
        }
    }



    public void selectAll() {
        removeAllSelections();
        currentStatesSelected.clear();
        currentStatesSelected.addAll(getAutomaton().getStates());
        markSelectedStates();
        repaint();
    }


    public void handleRemoveLabel(CanvasLabel label) {
        undoHandler.addStep(new RemoveLabelStep(this, label));
        removeLabel(label);
        currentLabelSelected = null;
        repaint();
    }

    public void removeLabel(CanvasLabel label) {
        editor.setChanged(true);
        labels.remove(label);
    }


    public void renameLabel(CanvasLabel label, String newCaption) {
        String oldCaption = label.getCaption();
        if(!newCaption.equals(oldCaption)) {
            editor.setChanged(true);
            undoHandler.addStep(new ChangeLabelCaptionStep(label, oldCaption, newCaption));
            label.setCaption(newCaption);            
        }
        repaint();
    }

    public void center() {
        centerAndScaleGraphics(true);
    }

    public void centerAndScaleGraphics(boolean scaleIfNeeded) {
        centerAndScaleGraphics(scaleIfNeeded,
                                editor.getViewportSize().getWidth(), editor.getViewportSize().getHeight());
    }

    public void centerAndScaleGraphics(boolean scaleIfNeeded, double width, double height) {
        if (!getAutomaton().getStates().isEmpty() || !labels.isEmpty()) {

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (State state : getAutomaton().getStates()) {
                int x = state.getVisualProperties().getXPos();
                int y = state.getVisualProperties().getYPos();
                if (x < minX) {
                    minX = x;
                    if(state.isInitial()) {
                        minX-= 30;
                    }
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }

                if(state.isInitial()) {
                    double angle = initArrow.getOrientation();
                    int length = initArrow.getLength();

                    int toX = (int) (Math.cos(angle) * length);
                    int toY = (int) (Math.sin(angle) * length);

                    if(toX < 0) {
                        maxX-=toX;
                    } else {
                        minX-=toX;
                    }
                    if(toY < 0) {
                        maxY-=toY;
                    } else {
                        minY-=toY;                                            
                    }
                }
            }

            for (Transition transition : getAutomaton().getTransitions()) {
                int x = transition.getVisualProperties().getClickPositionX();
                int y = transition.getVisualProperties().getClickPositionY();
            //    System.out.println("tr: " + x + ", " + y);
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }

            if (areLabelsVisible()) {
                for (CanvasLabel label : labels) {
                    Rectangle bounds = label.getOutline().getBounds();
                    int x1 = (int) (bounds.getX() / zoomfactor);
                    int y1 = (int) (bounds.getY() / zoomfactor);
                    int x2 = (int) ((bounds.getX() + bounds.getWidth()) / zoomfactor);
                    int y2 = (int) ((bounds.getY() + bounds.getHeight()) / zoomfactor);
                    //   System.out.println("la: " + x1 + ", " + y1 + ";" + x1 + ", " + y1);
                    if (x1 < minX) {
                        minX = x1;
                    }
                    if (x2 > maxX) {
                        maxX = x2;
                    }
                    if (y1 < minY) {
                        minY = y1;
                    }
                    if (y2 > maxY) {
                        maxY = y2;
                    }
                }
            }


//            double width = editor.getViewportSize().getWidth();
  //          double height = editor.getViewportSize().getHeight();
            //System.out.println("pw:" + width);
            //System.out.println("ph:" + height);
            //System.out.println("w:" + getRepresenter().getSize().getWidth());
            //System.out.println("h:" + getRepresenter().getSize().getHeight());


            double graphicsWidth = maxX - minX;
            double graphicsHeight = maxY - minY;
            double widthRatio = width / (graphicsWidth + 100);
            double heightRatio = height / (graphicsHeight + 100);

            double newZoom = 1;//zoomfactor;

            if (scaleIfNeeded && (widthRatio < 1 || heightRatio < 1)) { // scale if needed
                newZoom = widthRatio < heightRatio ? widthRatio : heightRatio;
                if (newZoom > ZOOM_MAX) {
                    newZoom = ZOOM_MAX;
                } else if (newZoom < ZOOM_MIN) {
                    newZoom = ZOOM_MIN;
                }
            }

            int offX = (int) (((width - graphicsWidth * newZoom) / 2) / newZoom);
            int offY = (int) (((height - graphicsHeight * newZoom) / 2) / newZoom);

            int moveX = offX - minX;
            int moveY = offY - minY;

            MultiStep multiStep = new MultiStep();

            if (moveX != 0 || moveY != 0) {
                for (State state : getAutomaton().getStates()) {
                    state.getVisualProperties().move(moveX, moveY);
                }
                for (CanvasLabel label : labels) {
                    label.move(moveX, moveY);
                }
                multiStep.addStep(new MoveGraphicsStep(this, moveX, moveY));
            }
            if(zoomfactor != newZoom) {
                multiStep.addStep(new ZoomStep(this, zoomfactor, newZoom));
                zoomfactor = newZoom;
            }

            if(!multiStep.isEmpty()) {
                undoHandler.addStep(multiStep);
            }
            
            updateGraphicsAll();
        }
    }

    public void layoutTest(Dimension viewportSize) {
        LayoutTools.moveGraphicsToTheOrigin(getAutomaton(), viewportSize);
        updateGraphicsAll();
    }

    public List<CanvasLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<CanvasLabel> labels) {
        this.labels.clear();
        this.labels.addAll(labels);
    }

    public void addLabel(CanvasLabel label) {
        editor.setChanged(true);
        labels.add(label);
    }

    public boolean isLeftMouseDown() {
        return leftMouseDown;
    }

    public void setLeftMouseDown(boolean leftMouseDown) {
        this.leftMouseDown = leftMouseDown;
    }


    public void writeUndoStack() {
        System.out.println(undoHandler.writeUndoStack());
    }

    public void writeRedoStack() {
        System.out.println(undoHandler.writeRedoStack());
    }

//    public boolean isInitArrowSelected() {
//        return initArrowSelected;
//    }
//
//    public void setInitArrowSelected(boolean initArrowSelected) {
//        this.initArrowSelected = initArrowSelected;
//    }
//
//    public boolean isInitArrowMouseOver() {
//        return initArrowMouseOver;
//    }
//
//    public void setInitArrowMouseOver(boolean initArrowMouseOver) {
//        this.initArrowMouseOver = initArrowMouseOver;
//    }
//
//    public Shape getInitArrowBounds() {
//        return initArrowBounds;
//    }
//
//    public void setInitArrowBounds(Shape initArrowBounds) {
//        this.initArrowBounds = initArrowBounds;
//    }

    public InitialArrow getInitArrow() {
        return initArrow;
    }



//    @Override
//    public void exportFileSVG() {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
//        fc.setDialogType(JFileChooser.SAVE_DIALOG);
//        fc.setDialogTitle("SVG Export");
//        int returnVal = fc.showOpenDialog(getMainFrame());
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            try {
//                SVGExporter.exportAutomatonGraphics(getAutomatonPainter(), fc.getSelectedFile());
//                PropertiesHandler.getInstance().setFileLastPath(fc.getSelectedFile().getAbsolutePath());
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(StateDiagramEditor.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SVGGraphics2DIOException ex) {
//                Logger.getLogger(StateDiagramEditor.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(StateDiagramEditor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }



    /**
     * enum for select states
     */
    enum EditorSelectionStates {

        selectNothing, selecetState, selectTransition, selectAll
    }

    /**
     * enum for transition states
     */
    enum EditorTransitionStates {

        selectFromState, selectToState
    }













//
//
//    public Rectangle getRectangle() {
//        if (automaton.getStates().isEmpty() & labels.isEmpty()) {
//            return new Rectangle();
//        }
//
//        int minX = Integer.MAX_VALUE;
//        int maxX = Integer.MIN_VALUE;
//        int minY = Integer.MAX_VALUE;
//        int maxY = Integer.MIN_VALUE;
//
//        for (State state : automaton.getStates()) {
//            int x = state.getVisualProperties().getXPos();
//            int y = state.getVisualProperties().getYPos();
//        //    System.out.println("st: " + x + ", " + y);
//            if (x < minX) {
//                minX = x;
//                if (state.isInitial()) {
//                    minX -= 30;
//                }
//            }
//            if (x > maxX) {
//                maxX = x;
//            }
//            if (y < minY) {
//                minY = y;
//            }
//            if (y > maxY) {
//                maxY = y;
//            }
//        }
//
//        for (Transition transition : automaton.getTransitions()) {
//            int x = transition.getVisualProperties().getClickPositionX();
//            int y = transition.getVisualProperties().getClickPositionY();
//        //    System.out.println("tr: " + x + ", " + y);
//            if (x < minX) {
//                minX = x;
//            }
//            if (x > maxX) {
//                maxX = x;
//            }
//            if (y < minY) {
//                minY = y;
//            }
//            if (y > maxY) {
//                maxY = y;
//            }
//        }
//
//        for (CanvasLabel label : labels) {
//            Rectangle bounds = label.getOutline().getBounds();
//            int x1 = (int) (bounds.getX() / zoomfactor);
//            int y1 = (int) (bounds.getY() / zoomfactor);
//            int x2 = (int) ((bounds.getX() + bounds.getWidth()) / zoomfactor);
//            int y2 = (int) ((bounds.getY() + bounds.getHeight()) / zoomfactor);
//        //    System.out.println("la: " + x1 + ", " + y1 + ";" + x1 + ", " + y1);
//            if (x1 < minX) {
//                minX = x1;
//            }
//            if (x2 > maxX) {
//                maxX = x2;
//            }
//            if (y1 < minY) {
//                minY = y1;
//            }
//            if (y2 > maxY) {
//                maxY = y2;
//            }
//        }
//        return new Rectangle(minX - 50, minY - 50, maxX -Math.min(0, minX -50) + 100, maxY -Math.min(0, minY -50) + 100);
//    }


    

    public Rectangle getBounds() {
        if (!getAutomaton().getStates().isEmpty() || !labels.isEmpty()) {

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            int radius = automatonPainter.getStateRadius();
            for (State state : getAutomaton().getStates()) {
                int x = state.getVisualProperties().getXPos();
                int y = state.getVisualProperties().getYPos();
                if (x - radius < minX) {
                    minX = x - radius;
                }
                if (x + radius > maxX) {
                    maxX = x + radius;
                }
                if (y - radius < minY) {
                    minY = y - radius;
                }
                if (y + radius > maxY) {
                    maxY = y + radius;
                }

//                if (x < minX) {
//                    minX = x;
//                }
//                if (x > maxX) {
//                    maxX = x;
//                }
//                if (y < minY) {
//                    minY = y;
//                }
//                if (y > maxY) {
//                    maxY = y;
//                }


                if(state.isInitial()) {
                    double angle = initArrow.getOrientation();
                    int length = initArrow.getLength();

                    int toX = (int) (Math.cos(angle) * length);
                    int toY = (int) (Math.sin(angle) * length);

                    if(toX < 0) {
                        maxX-=toX;
                    } else {
                        minX-=toX;
                    }
                    if(toY < 0) {
                        maxY-=toY;
                    } else {
                        minY-=toY;
                    }
                }
            }

            for (Transition transition : getAutomaton().getTransitions()) {
                int x = transition.getVisualProperties().getClickPositionX();
                int y = transition.getVisualProperties().getClickPositionY();
            //    System.out.println("tr: " + x + ", " + y);
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }

            if (areLabelsVisible()) {
                for (CanvasLabel label : labels) {
                    Rectangle bounds = label.getOutline().getBounds();
                    int x1 = (int) (bounds.getX() / zoomfactor);
                    int y1 = (int) (bounds.getY() / zoomfactor);
                    int x2 = (int) ((bounds.getX() + bounds.getWidth()) / zoomfactor);
                    int y2 = (int) ((bounds.getY() + bounds.getHeight()) / zoomfactor);
                    //   System.out.println("la: " + x1 + ", " + y1 + ";" + x1 + ", " + y1);
                    if (x1 < minX) {
                        minX = x1;
                    }
                    if (x2 > maxX) {
                        maxX = x2;
                    }
                    if (y1 < minY) {
                        minY = y1;
                    }
                    if (y2 > maxY) {
                        maxY = y2;
                    }
                }
            }
            return new Rectangle((int) (minX * zoomfactor), (int) (minY * zoomfactor),
                    (int) (maxX * zoomfactor), (int) (maxY * zoomfactor));
        } else {
            return new Rectangle();
        }
    }


    public Dimension getPreferredSize() {
        Rectangle r = getBounds();
        return new Dimension(r.width, r.height);
    }



    public BufferedImage getImage() {
        Rectangle r = getBounds();
        BufferedImage bi = new BufferedImage(r.width - r.x + 100, r.height - r.y + 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D gimg = (Graphics2D) bi.getGraphics();
        getAutomatonPainter().updateGraphicsForSVG(gimg);
        return bi;
    }




//    public boolean exportPNG(File file) {
//        Rectangle r = getBounds();
//        BufferedImage bi = new BufferedImage(r.width - r.x + 100, r.height - r.y + 100, BufferedImage.TYPE_INT_RGB);
//        Graphics2D gimg = (Graphics2D) bi.getGraphics();
//        getAutomatonPainter().updateGraphicsForSVG(gimg);
//        try {
//            ImageIO.write(bi, "png", file);
//     //       JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
//            return true;
//        } catch (IOException ex) {
//     //       JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
//        }
//        return false;
//    }



//
//    @Override
//    public boolean save(File file) {
//        return exportXML(file);
//    }
//
//
//    @Override
//    public boolean open(File file) {
//        return importXML(file);
//    }
//
//
//    @Override
//    public boolean exportXML(File file) {
//        try {
//            XMLExporter.saveAutomaton(getAutomaton(), getLabels(), file);
//            return true;
//        } catch (IOException ex) {
//
//        }
//        return false;
//    }
//
//    @Override
//    public boolean importXML(File file) {
//        try {
//            setAutomaton(XMLImporter.getAutomaton(file));
//            setLabels(XMLImporter.getLabels(file));
//            return true;
//        } catch (DocumentException ex) {
//            //Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }



}

