package swingwtx.swing.filechooser;

import java.io.File;

import swingwtx.swing.Icon;

public abstract class FileSystemView {
	 public File 	createFileObject(File dir, String filename) {
		 return null;
	 }
	 
	 public File 	createFileObject(String path) {
		 return null;
	 }
	 
	 protected  File 	createFileSystemRoot(File f) {
		 return null;
	 }

	 abstract  File 	createNewFolder(File containingDir);
	 
	 public File 	getChild(File parent, String fileName){
		 return null;
	 }
	 
	 public File 	getDefaultDirectory(){
		 return null;
	 }
	 
	 File[] 	getFiles(File dir, boolean useFileHiding){
		 return null;
	 }
	 
	 static FileSystemView 	getFileSystemView(){
		 return null;
	 }
      
	 public File 	getHomeDirectory(){
		 return null;
	 }
      
	 public File 	getParentDirectory(File dir){
		 return null;
	 }

	 public File[] 	getRoots() {
		 return null;
	 }
	 
	 public String 	getSystemDisplayName(File f){
		 return null;
	 }

	 public Icon 	getSystemIcon(File f) {
		 return null;
	 }

	 public String 	getSystemTypeDescription(File f) {
		 return null;
	 }
	 
	 public boolean 	isComputerNode(File dir) {
		 return false;
	 }

	 public boolean 	isDrive(File dir){
		 return false;
	 }
	 
	 public boolean 	isFileSystem(File f) {
		 return false;		 
	 }
	 
	 public boolean 	isFileSystemRoot(File dir){
		 return false;
	 }

	 public boolean 	isFloppyDrive(File dir){
		 return false;
	 }
	 
	 public boolean 	isHiddenFile(File f){
		 return false;
	 }

	 public boolean 	isParent(File folder, File file){
		 return false;
	 }

	 public boolean 	isRoot(File f){
		 return false;
	 }
	 
	 public Boolean 	isTraversable(File f){
		 return null;
	 }
}
