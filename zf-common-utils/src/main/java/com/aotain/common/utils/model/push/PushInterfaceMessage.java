package com.aotain.common.utils.model.push;

import com.aotain.common.utils.push.PushSecurityTool.PushEncryptResult;

/**
 * 推送接口信息
 *
 * @author liuz@aotian.com
 * @date 2017年8月4日 下午6:08:15
 */
public class PushInterfaceMessage {
	private String randVal;
	private String pwdHash;
	private String push;
	private String pushHash;
	private int encryptAlgorithm;
	private int hashAlgorithm;
	private int compressionFormat;
	private String pushVersion;

	private int pushType; // 1-邮件，2-短信，3-微信，0-全部
	private long pushSequence; // 本次推送唯一序列

	public PushInterfaceMessage(PushEncryptResult ers, Integer pushType) {
		randVal = ers.getRandVal();
		pwdHash = ers.getPwdHash();
		push = ers.getPush();
		pushHash = ers.getPushHash();
		encryptAlgorithm = ers.getEncryptAlgorithm();
		hashAlgorithm = ers.getHashAlgorithm();
		compressionFormat = ers.getCompressionFormat();
		pushVersion = ers.getPushVersion();
		this.pushType = pushType;
		pushSequence = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "PushInterfaceMessage [randVal=" + randVal + ", pwdHash=" + pwdHash + ", push=" + push + ", pushHash=" + pushHash + ", encryptAlgorithm=" + encryptAlgorithm + ", hashAlgorithm="
				+ hashAlgorithm + ", commpresssionFormat=" + compressionFormat + ", pushVersion=" + pushVersion + ", pushType=" + pushType + ", pushSequence=" + pushSequence + "]";
	}

	public String getRandVal() {
		return randVal;
	}

	public void setRandVal(String randVal) {
		this.randVal = randVal;
	}

	public String getPwdHash() {
		return pwdHash;
	}

	public void setPwdHash(String pwdHash) {
		this.pwdHash = pwdHash;
	}

	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}

	public String getPushHash() {
		return pushHash;
	}

	public void setPushHash(String pushHash) {
		this.pushHash = pushHash;
	}

	public int getEncryptAlgorithm() {
		return encryptAlgorithm;
	}

	public void setEncryptAlgorithm(int encryptAlgorithm) {
		this.encryptAlgorithm = encryptAlgorithm;
	}

	public int getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(int hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public int getCompressionFormat() {
		return compressionFormat;
	}

	public void setCompressionFormat(int compressionFormat) {
		this.compressionFormat = compressionFormat;
	}

	public String getPushVersion() {
		return pushVersion;
	}

	public void setPushVersion(String pushVersion) {
		this.pushVersion = pushVersion;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public long getPushSequence() {
		return pushSequence;
	}

	public void setPushSequence(long pushSequence) {
		this.pushSequence = pushSequence;
	}

}
