package com.tabjy.jnote.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

import com.sun.javafx.tk.Toolkit.Task;
import com.tabjy.jnote.model.Callback;
import com.tabjy.jnote.model.JsonData;
import com.tabjy.jnote.model.Note;
import com.tabjy.jnote.MainApp;
import com.tabjy.jnote.SettingWindow;
import com.tabjy.jnote.util.Configuration;
import com.tabjy.jnote.util.Constants;
import com.tabjy.jnote.util.HTMLBuilder;
import com.tabjy.jnote.util.HTTPRequest;
import com.tabjy.jnote.util.LaunchBrowser;
import com.tabjy.jnote.util.NetworkState;
import com.tabjy.jnote.util.PropertiesConfig;
import com.tabjy.jnote.util.Time;


public class OverviewController {

	@FXML
	private TextField noteTitle;
	@FXML
	private Label noteTime;
	@FXML
	private Label noteStatus;
	@FXML
	private Label connectionStatus;
	@FXML
	private Label statusInfo;
	
	@FXML
	private Button setting;
	@FXML
	private Button about;
	@FXML
	private Button feedback;
	
	@FXML
	private Button syncAll;
	@FXML
	private Button deleteAll;
	
	@FXML
	private Pane noteListContainer;
	
	@FXML
	private Button delete;
	@FXML
	private Button sync;
	@FXML
	private Label modifiedLabel;
	
	@FXML
	private SplitPane splitPane;
	@FXML
	private TextArea markdown;
	@FXML
	private WebView webView;
	
	private ListView<Note> noteList;
	private MainApp mainApp;
	private ObservableList<Note> noteData = FXCollections.observableArrayList();
	private String myUid = null;
	
	public OverviewController() {
    }
	
	//called by the main application to give a reference back to itself.
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	//Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	@FXML
    private void initialize() {
		markdown.setWrapText(true);
		
		//initialize note list
		noteList = new ListView<Note>();
		noteList.setItems(noteData);
		noteList.setPrefWidth(275);
		     	
		noteListContainer.getChildren().add(noteList);
		
		//add classes for CSS
		noteList.prefHeightProperty().bind(noteListContainer.heightProperty());
		noteList.getStyleClass().add("noteList");
		noteList.getStyleClass().add("noGlow_list");
		noteList.getStyleClass().add("customScrollBar");
		
		noteList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {
		    @Override
		    public void changed(ObservableValue<? extends Note> observable, Note oldValue, Note newValue) {
		        loadNoteContent(newValue);
		        //System.out.println("Selected item: "+newValue.getTitle());
		    }
		});
		delete.setDisable(true);
		sync.setDisable(true);
	}
	
	public void loadUid(){
		//TODO get uid and store it.
		if (!Configuration.hasUid()){ //判断是否存在配置文件
			showWizard();
		} else{
			myUid = Configuration.getUid(); //从配置文件读取UID
		}
	}
	
	public void showWizard(){
		//如果不存在
		if (mainApp.showWizard()){ //判断是否新用户
			showNewUser();
		} else{
			showExistingUser();
		}
	}

	public void showNewUser(){
		//如果是新用户
		String [] data =  mainApp.showNewUser();
		//System.out.println("UID: "+data[0]);
		//System.out.println("Email: "+data[1]);
		//System.out.println("Go back:" + data[2]);
		String uid,email;
		if (data[2] == "1"){
			showWizard();
		} else if (data[2] == "0") {
			uid = data[0];
			email = data[1];
			showValidNewUser(uid,email);
		}
	}
	
	public void showValidNewUser(String uid, String email){
		if(email.indexOf("@") > 0) {
			System.out.println("email is not null");
		} else {
			email = null;
		}
		String[] data = mainApp.showValidNewUser(uid, email);
		if (data[1] == "1"){
			showNewUser();
		} else if (data[1] == "0") {
			myUid = data[0];	
		}
	}
	
	public void showExistingUser() {
		String[] data = mainApp.showExistingUser();
		if (data[1] == "1"){
			showWizard();
		} else if (data[1] == "0") {
			myUid = data[0];	
		}
		
	}
	
	public void downloadNote(){
		PropertiesConfig.writeData("uid", myUid);
		String uid = myUid;
		//construct HTTP POST request content
		String post = "uid="+uid+"&count=100";
		HTTPRequest.sendPost(Constants.getAPI("list"), post, new Callback(){
			@Override
			public void onConnectionFailed(){
				System.err.println("Cannot connect to remote server! Please check your internet connection.");
				updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
				"Click OK to retry.");
				downloadNote();
				
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
				for (int i=0; i<jsonData.getNoteData().size();i++){
					noteData.add(jsonData.getNoteData().get(i));
				}
				updateStatusInfo(jsonData.getNoteData().size() +" notes downloaed at "+ Time.getCurrentTime());
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				System.err.println("Connected to remote server, but an error was returned.");
				System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
				System.err.println("Raw data recived is printed below:");
				System.err.println(jsonData.getRaw());
				
				updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
			}

		});
		
	}
	
	private void loadNoteContent(Note note) {
		try{
			delete.setDisable(false);
			sync.setDisable(false);
			modifiedLabel.setText("Last Modified:");
			noteTitle.setText(note.getTitle());
			markdown.setText(note.getContent());
			noteStatus.setText(note.getStatus());
			noteTime.setText(Time.getTimeFromStamp(note.getTime()));
			renderMarkdown();
		} catch (NullPointerException e){
			//It is normal to have this exception
			delete.setDisable(true);
			sync.setDisable(true);
			modifiedLabel.setText("");
			noteTitle.setText("");
			markdown.setText("");
			noteStatus.setText("");
			noteTime.setText("");
			
		}
	}
	
	@FXML
	private void newNote(){
		String uid = myUid;
		String post = "uid="+uid+"&title=UntitledNote&time="+Time.getCurrentTimeStamp();
		HTTPRequest.sendPost(Constants.getAPI("insert"), post, new Callback(){
			
			@Override
			public void onConnectionFailed(){
				System.err.println("Cannot connect to remote server! Please check your internet connection.");
				updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
				"Click OK to retry.");
				downloadNote();
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
				noteData.add(jsonData.getNoteData().get(0));
				updateStatusInfo("New note created at "+ Time.getCurrentTime());
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				System.err.println("Connected to remote server, but an error code is returned.");
				System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
				System.err.println("Raw data recived is printed below:");
				System.err.println(jsonData.getRaw());
				
				updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
			}
		});
	}
	
	@FXML
	private void deleteNote(){
		Note selectedNote = noteList.getSelectionModel().getSelectedItem();
		if (selectedNote != null) {
			if (mainApp.showConfirm("Are you sure to delete note: "+selectedNote.getTitle(),"This operation can NOT be undone!")){
				int selectedIndex = noteList.getSelectionModel().getSelectedIndex();
				String post = "uid="+myUid+"&id="+selectedNote.getNoteId();
				HTTPRequest.sendPost(Constants.getAPI("remove"), post, new Callback(){
					
					@Override
					public void onConnectionFailed(){
						System.err.println("Cannot connect to remote server! Please check your internet connection.");
						updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
						mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
						"Click OK to retry.");
						deleteNote();
					}
					
					@Override
					public void onSuccess(JsonData jsonData){
						System.out.println(jsonData.getRaw());
						System.out.println(noteData.get(selectedIndex).getTitle()+" will be deleted");
						System.out.println("Note index: " + selectedIndex);
						String title = noteData.get(selectedIndex).getTitle();
						noteData.remove(selectedIndex);
						updateStatusInfo(title+" has been deleted at "+ Time.getCurrentTime());
					}
					
					@Override
					public void onErrorReturned(JsonData jsonData){
						System.err.println("Connected to remote server, but an error code is returned.");
						System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
						System.err.println("Raw data recived is printed below:");
						System.err.println(jsonData.getRaw());
						
						updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
						mainApp.showAlterWindow("Synchronization Failed!",
								"Connected to remote server, but an error was returned.\n"+
								"Error Code: "+jsonData.getErrCode() +"\n"+
								"Error Messege: " + jsonData.getErrMsg() + "\n" +
								"Raw data recived is printed below:",jsonData.getRaw());
					}
				});
			}
		}
	}
	
	@FXML
	private void deleteAll(){
		if (mainApp.showConfirm("Are you sure to delete all your notes?","This operation can NOT be undone!")){
			while(!noteData.isEmpty()){
				sendDelete(noteData.get(0).getNoteId());
				String title = noteData.get(0).getTitle();
				noteData.remove(0);
				updateStatusInfo(title+" has been deleted at "+ Time.getCurrentTime());
				System.out.println(title+" has been deleted at "+ Time.getCurrentTime());
			}
		}
	}
	
	@FXML
	private void syncAll(){
		for (int i=0;i<noteData.size();i++){
			if (!noteData.get(i).getSynced()){
				sendSync(noteData.get(i));
			}
		}
	}
	
	private void sendSync(Note note){
		String title = note.getTitle();
		String content = note.getContent();
		Long time = note.getTime();
		String uid = note.getUid();
		int id = note.getNoteId();
		
		//construct HTTP POST request content
		String post = "title="+title+"&content="+content+"&time="+time+"&uid="+uid+"&id="+id;
		HTTPRequest.sendPost(Constants.getAPI("update"), post, new Callback(){
			@Override
			public void onConnectionFailed(){
				System.err.println("Cannot connect to remote server! Please check your internet connection.");
				
				updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.");
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
				note.setSynced(true,Time.getCurrentTimeStamp());
				updateStatusInfo(note.getTitle()+" has been updated at "+ Time.getCurrentTime());
				System.out.println("Sync successed! " +note.getTitle()+" has been updated.");
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				System.err.println("Connected to remote server, but an error code is returned.");
				System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
				System.err.println("Raw data recived is printed below:");
				System.err.println(jsonData.getRaw());
				
				updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
			}
		});
		noteStatus.setText(note.getStatus());
	}
	
	private void sendDelete(int noteId){
		String post = "uid="+myUid+"&id="+noteId;
		HTTPRequest.sendPost(Constants.getAPI("remove"), post, new Callback(){
			
			@Override
			public void onConnectionFailed(){
				System.err.println("Cannot connect to remote server! Please check your internet connection.");
				updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.\n"+
				"Click OK to retry.");
				sendDelete(noteId);
			}
			
			@Override
			public void onSuccess(JsonData jsonData){
			}
			
			@Override
			public void onErrorReturned(JsonData jsonData){
				System.err.println("Connected to remote server, but an error code is returned.");
				System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
				System.err.println("Raw data recived is printed below:");
				System.err.println(jsonData.getRaw());
				
				updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
				mainApp.showAlterWindow("Synchronization Failed!",
						"Connected to remote server, but an error was returned.\n"+
						"Error Code: "+jsonData.getErrCode() +"\n"+
						"Error Messege: " + jsonData.getErrMsg() + "\n" +
						"Raw data recived is printed below:",jsonData.getRaw());
			}
		});
	}
	
	
	@FXML
	private void saveChange(){
		renderMarkdown();
		//Saving changes
		Note selectedNote = noteList.getSelectionModel().getSelectedItem();
		if (selectedNote != null) {
			if (!selectedNote.getTitle().equals(noteTitle.getText()) || !selectedNote.getContent().equals(markdown.getText())){
				System.out.println("Saving changes: "+selectedNote.getTitle());
				selectedNote.setTitle(noteTitle.getText());
				selectedNote.setContent(markdown.getText());
				selectedNote.setSynced(false,Time.getCurrentTimeStamp());
				selectedNote.setTime(Time.getCurrentTimeStamp());
				noteStatus.setText(selectedNote.getStatus());
				noteTime.setText(Time.getTimeFromStamp(selectedNote.getTime()));
			}
		}
	}
	
	@FXML
	private void renderMarkdown() {
		String markdownString = markdown.getText().toString();
		String htmlString = "Oops~ Something worng with the Markdown processor.";
		try {
			htmlString = HTMLBuilder.renderMarkdown(markdownString);
		} catch (IOException e) {
			updateStatusInfo("Markdown rendering failed at "+ Time.getCurrentTime());
			mainApp.showAlterWindow("Markdown Rendering Failed!","An unexcepted error occcurs while trying to render markdown syntax. \n"+
					"Stack trace is printed below:",e.getStackTrace().toString());
			
			e.printStackTrace();
		}
		webView.getEngine().loadContent(htmlString);		
	}
	
	@FXML
	private void syncNote(){
		Note selectedNote = noteList.getSelectionModel().getSelectedItem();	
		if (selectedNote != null) {
			String title = selectedNote.getTitle();
			String content = selectedNote.getContent();
			Long time = selectedNote.getTime();
			String uid = selectedNote.getUid();
			int id = selectedNote.getNoteId();
			
			//construct HTTP POST request content
			String post = "title="+title+"&content="+content+"&time="+time+"&uid="+uid+"&id="+id;
			HTTPRequest.sendPost(Constants.getAPI("update"), post, new Callback(){
				
				@Override
				public void onConnectionFailed(){
					System.err.println("Cannot connect to remote server! Please check your internet connection.");
					
					updateStatusInfo("Failed to connect server at "+ Time.getCurrentTime());
					mainApp.showAlterWindow("Synchronization Failed!","Cannot connect to remote server! Please check your Internet connection.");
				}
				
				@Override
				public void onSuccess(JsonData jsonData){
					selectedNote.setSynced(true,Time.getCurrentTimeStamp());
					updateStatusInfo(selectedNote.getTitle()+" has been updated at "+ Time.getCurrentTime());
					System.out.println("Sync successed! " +selectedNote.getTitle()+" has been updated.");
				}
				
				@Override
				public void onErrorReturned(JsonData jsonData){
					System.err.println("Connected to remote server, but an error code is returned.");
					System.err.println("err_code: " + jsonData.getErrCode() +", err_msg: " + jsonData.getErrMsg());
					System.err.println("Raw data recived is printed below:");
					System.err.println(jsonData.getRaw());
					
					updateStatusInfo("An error was returned from server at "+ Time.getCurrentTime());
					mainApp.showAlterWindow("Synchronization Failed!",
							"Connected to remote server, but an error was returned.\n"+
							"Error Code: "+jsonData.getErrCode() +"\n"+
							"Error Messege: " + jsonData.getErrMsg() + "\n" +
							"Raw data recived is printed below:",jsonData.getRaw());
				}
			});
			noteStatus.setText(selectedNote.getStatus());
		}
	}
	
	@FXML
	private void handleStrong(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"**strong text**");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleItalic(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"*emphasized text*");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleQuote(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"\n> Blockquote");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleCode(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"\n    enter code here \n");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleImage(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"![enter image description here](Image_Link_Here)");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleLink(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"[enter link description here](Link_goes_here)");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleHeading(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"\nHeading \n=======\n");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void handleMore(){
		if (noteList.getSelectionModel().getSelectedItem() != null) {
			markdown.setText(markdown.getText()+"\n----------\n");
			saveChange();
			renderMarkdown();
		}
	}
	
	@FXML
	private void showSettingWindow(){
		SettingWindow.main(null);
	}
	
	@FXML
	private void showAbout(){
		mainApp.showAbout();
	}
	
	@FXML
	private void showFeedback(){
		LaunchBrowser.openURL("http://jnote.tabjy.com/feedback");
	}
	
	
	public void updateStatusInfo(String info) {
		statusInfo.setText(info);
	}
	
	public void updateConnectionStatus(boolean isOnline){
		if (isOnline){
			connectionStatus.setText("Server Connection: Online");
		} else {
			connectionStatus.setText("Server Connection: Offline");
		}
		
	}
}