package swingwt.awt.image;

public abstract class DataBuffer {
	 int 	getDataType() {
		 return 0;
	 }

	 static int 	getDataTypeSize(int type) {
		 return 0;
	 }

	 int 	getElem(int i) {
		 return 0;
	 }

	 abstract  int 	getElem(int bank, int i);

	 double 	getElemDouble(int i){
		 return 0;
	 }
	 
	 double 	getElemDouble(int bank, int i){
		 return 0;
	 }
	 
	 float 	getElemFloat(int i){
		 return 0;
	 }
	 
	 float 	getElemFloat(int bank, int i) {
		 return 0;
	 }

	 int 	getNumBanks(){
		 return 0;
	 }
	 
	 int 	getOffset(){
		 return 0;
	 }
	 
	 int[] 	getOffsets(){
		 return null;
	 }
	 
	 int 	getSize() {
		 return 0;
	 }
	 
	 void 	setElem(int i, int val) {		 
	 }

	 abstract  void 	setElem(int bank, int i, int val);
	 void 	setElemDouble(int i, double val){
		 
	 }

	 void 	setElemDouble(int bank, int i, double val){
		 
	 }
	 
	 void 	setElemFloat(int i, float val){
		 
	 }

	 void 	setElemFloat(int bank, int i, float val){
		 
	 }
}
