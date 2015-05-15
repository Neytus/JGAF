package jgaf.lrextension;


 

 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public abstract class ProgressWorker<T>  extends  SwingWorker<T,Integer> {
     private Component parent;
    private ProgressMonitor progressMonitor = new ProgressMonitor(parent,
                                  "Running a Long Task",
                                  "", 0, 100);
    public ProgressWorker(Component parent) {
        super();
        this.parent = parent;
        progressMonitor.setMillisToDecideToPopup(0);
        progressMonitor.setMillisToPopup(0);
        //progressMonitor.getAccessibleContext().getAccessibleComponent().
    }
 
    
    public void setProg(int i){
    publish(i);
    }
   
    @Override
    public void process(java.util.List<Integer> chunk){
    Integer counterChunk = chunk.get(chunk.size()-1);
    progressMonitor.setProgress(counterChunk);
    
    }
   

    public void startWithProgress(){
    int delay = 100; //milliseconds
    ActionListener taskPerformer = new ActionListener() {
      private boolean started=false;
     
      public void actionPerformed(ActionEvent evt) {
         if (!started){
         started=true;
             
        progressMonitor.setProgress(0);
        
        
        execute();
         }else{
             
        
             if (isDone()){
             ((Timer) evt.getSource()).stop();
             }
             if (progressMonitor.isCanceled()) {
                    cancel(true);                    
                     ((Timer) evt.getSource()).stop();
                }
         }
      }
  };
 Timer T = new Timer(delay, taskPerformer);
 T.setInitialDelay(0);
 T.start();
    }
}
    
    
 
   