package online.decentworld.charge.service;

public abstract class TransferServiceTemplate {

	protected abstract TransferStatus createTransfer(int amount,String descipe,String openid,String transferNum,String ip)throws Exception;
	protected abstract TransferStatus check(int amount);

	protected TransferStatus transfer(int amount,String descipe,String openid,String transferNum,String ip) throws Exception{
		TransferStatus status=check(amount);
		 if(status!=TransferStatus.SUCCESS){
			 return status;
		 }else{
			 return createTransfer(amount,descipe,openid,transferNum,ip);
		 }
	}

}
