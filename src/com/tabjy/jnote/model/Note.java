package com.tabjy.jnote.model;

import com.tabjy.jnote.util.Time;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Note extends HBox {
		
		//private Label icon = new Label();
		//private Label name = new Label();
		//private Label dt = new Label();
		
		private final IntegerProperty noteId;
		private final StringProperty title;
		private final StringProperty content;
		private final SimpleLongProperty time;
		private final StringProperty uid;
		private final BooleanProperty synced;
		//private final StringProperty status_time;
		//private final StringProperty status_sync;
		private final StringProperty status;
		//private final StringProperty status;
		
		
		public Note(int id, String title, String content, Long time, String uid, boolean synced){
			this.noteId = new SimpleIntegerProperty(id);
			this.title = new SimpleStringProperty(title);
			this.content = new SimpleStringProperty(content);
			this.time = new SimpleLongProperty(time);
			this.uid = new SimpleStringProperty(uid);
			this.synced = new SimpleBooleanProperty(synced);
			//this.status_time = new SimpleStringProperty(Time.getTimeFromStamp(time));
			this.status = new SimpleStringProperty("");
			if (synced){
				this.status.set(Time.getTimeFromStamp(time)+" - " +"Synced");
				
			} else if (!synced){
				this.status.set(Time.getTimeFromStamp(time)+" - " +"Modified");
			}
			
			setGrid();
		}
		
		public int getNoteId() {
			return noteId.get();
		}

		public void setNoteId(int noteId) {
			this.noteId.set(noteId);
		}
		
		public String getTitle(){
			return title.get();
		}
		
		public void setTitle(String title){
			this.title.set(title);
		}
		
		public String getContent(){
			return content.get();
		}
		
		public void setContent(String content){
			this.content.set(content);
		}
		
		public Long getTime(){
			return time.get();
		}
		
		public void setTime(long time){
			this.time.set(time);
		}
		
		public String getUid(){
			return uid.get();
		}
		
		public void setUid(String uid){
			this.uid.set(uid);
		}
		
		public boolean getSynced(){
			return this.synced.get();
		}
		
		public void setSynced(boolean synced, Long timeStamp){
			if (synced){
				this.status.set(Time.getTimeFromStamp(timeStamp)+" - " +"Synced");
			} else if (!synced){
				this.status.set(Time.getTimeFromStamp(this.time.get())+" - " +"Modified");
			}
			this.synced.set(synced);
			
		}
		
		public String getStatus(){
			if (this.synced.get()){
				return "Synced";
			} else if (!this.synced.get()) {
				return "Modified";
			} else {
				return null;
			}
			
			
		}
    
	    public Note(){
	  	  	super();
	  	  	
	  	  	//default value
	  	  	this.noteId = new SimpleIntegerProperty(0);
			this.title = new SimpleStringProperty("Untitled Note");
			this.content = new SimpleStringProperty("");
			this.time = new SimpleLongProperty(Time.getCurrentTimeStamp());
			this.uid = new SimpleStringProperty(null);
			this.synced = new SimpleBooleanProperty(false);
			this.status = new SimpleStringProperty();
	  	  	
			setGrid();
	        
	    }

		private void setGrid() {
			Label title_lab = new Label();
			Label status_lab = new Label();
			//Label sync_lab = new Label();
			Label placeholder_lab = new Label("");
			GridPane grid = new GridPane();
			
			grid.setHgap(0);
	        grid.setVgap(4);
	        grid.setPadding(new Insets(0, 0, 0, 0));
	        placeholder_lab.setPrefWidth(50);
	        title_lab.setPrefWidth(250);
	        
	        grid.add(placeholder_lab, 0, 0, 1, 2);                    
	        grid.add(title_lab, 2, 0);        
	        grid.add(status_lab, 2, 1);
	             
	        GridPane.setHalignment(status_lab, HPos.LEFT);
	        GridPane.setHalignment(title_lab, HPos.LEFT);
	         
	        title_lab.textProperty().bind(this.title);
	        status_lab.textProperty().bind(this.status);

	        title_lab.getStyleClass().add("noteList-Title");
	        status_lab.getStyleClass().add("noteList-Time");
	        	        
	        grid.setPrefWidth(250);
	        this.getChildren().addAll(grid);
	        this.getStyleClass().add("noteList-Cell");
			
		}

}