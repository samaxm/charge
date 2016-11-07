package online.decentworld.charge.service;

public enum TransferStatus {
	SUCCESS(0),FAILED(1),PROCESSING(2),NOTENOUGH(3),STORED(4);
	private int statusCode;
	private TransferStatus(int statusCode){
		this.statusCode=statusCode;
	}
	public int getStatusCode(){
		return this.statusCode;
	}
}
