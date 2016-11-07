package online.decentworld.charge.service;

public enum TransferAccountType {
	//user为用户本身身家账户
	ALIPAY(0),WXPAY(1),BANK(2);
	private int typeValue;
	private TransferAccountType(int type){
		this.typeValue=type;
	}
	public int getTypeValue(){
		return this.typeValue;
	}
	public static TransferAccountType getAccountType(int type){
		for(TransferAccountType account: TransferAccountType.values()){
			if(account.getTypeValue()==type){
				return account;
			}
		}
		return null;
	}
}
