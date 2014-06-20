package kr.ac.sogang.cs.ssep.minhoryang;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import kr.ac.sogang.cs.ssep.minhoryang.ListenerList.FireHandler;

@SuppressLint("DefaultLocale")
public class FileDialog {
    private static final String PARENT_DIR = "../";
    private String[] fileList;
    private File currentPath;
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public interface DirectorySelectedListener {
        void directorySelected(File directory);
    }
    private ListenerList<FileSelectedListener> fileListenerList = new ListenerList<FileDialog.FileSelectedListener>();
    private ListenerList<DirectorySelectedListener> dirListenerList = new ListenerList<FileDialog.DirectorySelectedListener>();
    private final Activity activity;
    private boolean selectDirectoryOption;
    private ArrayList<String> fileEndsWith = new ArrayList<String>();    

    /**
     * @param activity 
     * @param initialPath
     */
    public FileDialog(Activity activity, File path) {
        this.activity = activity;
        this.currentPath = path;
        if (!path.exists()) path = Environment.getExternalStorageDirectory();
    }

    /**
     * @return file dialog
     */
    public Dialog createFileDialog() {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(currentPath.getPath());
        if (selectDirectoryOption) {
            builder.setPositiveButton("Select directory", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    fireDirectorySelectedEvent(currentPath);
                }
            });
        }

        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String fileChosen = fileList[which];
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    loadFileList(chosenFile);
                    dialog.cancel();
                    dialog.dismiss();
                    showDialog();
                } else fireFileSelectedEvent(chosenFile);
            }
        });

        dialog = builder.show();
        return dialog;
    }


    public void addFileListener(FileSelectedListener listener) {
        fileListenerList.add(listener);
    }

    public void removeFileListener(FileSelectedListener listener) {
        fileListenerList.remove(listener);
    }

    public void setSelectDirectoryOption(boolean selectDirectoryOption) {
        this.selectDirectoryOption = selectDirectoryOption;
    }

    public void addDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.add(listener);
    }

    public void removeDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.remove(listener);
    }

    /**
     * Show file dialog
     */
    public void showDialog() {
    	loadFileList(this.currentPath);
        createFileDialog().show();
    }

    private void fireFileSelectedEvent(final File file) {
        fileListenerList.fireEvent(new FireHandler<FileDialog.FileSelectedListener>() {
            public void fireEvent(FileSelectedListener listener) {
                listener.fileSelected(file);
            }
        });
    }

    private void fireDirectorySelectedEvent(final File directory) {
        dirListenerList.fireEvent(new FireHandler<FileDialog.DirectorySelectedListener>() {
            public void fireEvent(DirectorySelectedListener listener) {
                listener.directorySelected(directory);
            }
        });
    }

    private void loadFileList(File path) {
        this.currentPath = path;
        List<String> r = new ArrayList<String>();
        if (path.exists()) {
            if (path.getParentFile() != null) r.add(PARENT_DIR);
            FilenameFilter filter = new FilenameFilter(){
				@Override
		        public boolean accept(File dir, String filename) {
		            File sel = new File(dir, filename);
		            if (!sel.canRead()) return false;
		            if (selectDirectoryOption) return sel.isDirectory();
		            else {
		                boolean endsWith = false;
		            	if (fileEndsWith.size() > 0){
		            		for(String my : fileEndsWith){
		            			if (filename.toLowerCase().endsWith(my)){
		            				endsWith = true;
		            				break;
		            			}
		            		}
		            	}else{
		            		endsWith = true;
		            	}
		                return endsWith || sel.isDirectory();
		            }
		        }};
            String[] fileList1 = path.list(filter);
            for (String file : fileList1) {
           		r.add(file);
            }
        }
        fileList = (String[]) r.toArray(new String[]{});
    }
    
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) return currentPath.getParentFile();
        else return new File(currentPath, fileChosen);
    }

    public void addFileEndsWith(String in) {
    	this.fileEndsWith.add(in.toLowerCase());
    }
 }

class ListenerList<L> {
	private List<L> listenerList = new ArrayList<L>();
	
	public interface FireHandler<L> {
	    void fireEvent(L listener);
	}
	
	public void add(L listener) {
	    listenerList.add(listener);
	}
	
	public void fireEvent(FireHandler<L> fireHandler) {
	    List<L> copy = new ArrayList<L>(listenerList);
	    for (L l : copy) {
	        fireHandler.fireEvent(l);
	    }
	}
	
	public void remove(L listener) {
	    listenerList.remove(listener);
	}
	
	public List<L> getListenerList() {
	    return listenerList;
	}
}