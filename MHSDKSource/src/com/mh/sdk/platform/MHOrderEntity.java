package com.mh.sdk.platform;

public class MHOrderEntity {
	private String subject;
	private String merchanturl;
	private String username;
	private String gameid;
	private String srvid;
	private String mark;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMerchanturl() {
		return merchanturl;
	}

	public void setMerchanturl(String merchanturl) {
		this.merchanturl = merchanturl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getSrvid() {
		return srvid;
	}

	public void setSrvid(String srvid) {
		this.srvid = srvid;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "subject=" + subject + "&merchanturl=" + merchanturl
				+ "&username=" + username + "&gameid=" + gameid + "&srvid="
				+ srvid + "&mark=" + mark;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
