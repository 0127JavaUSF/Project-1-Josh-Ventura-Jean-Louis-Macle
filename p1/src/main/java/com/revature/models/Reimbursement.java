package com.revature.models;

import java.sql.Timestamp;

public class Reimbursement {
	private int userId;
	
	private int reimbursementId;	
	private Double reimbursementAmount;
	//https://docs.oracle.com/javase/7/docs/api/java/sql/Timestamp.html
	private String reimbursementDescription;
	private int reimbursementAuthor;
	private String reimbursementAuthorName;	
	private String reimbursementSubmitted;	
	private int reimbursementResolver;
	private String reimbursementResolverName;	
	private String  reimbursementResolved;	
	private String reimbursementStatus;
	private String reimbursementType;
	
	public Reimbursement(int userId, int reimbursementId, Double reimbursementAmount, Timestamp reimbursementSubmitted, String reimbursementDescription, int reimbursementAuthor,  String reimbursementStatus, String reimbursementType) {
		super();
		this.setUserId(userId);
		this.setReimbursementId(reimbursementId);
		this.setReimbursementAmount(reimbursementAmount);
		this.setReimbursementDescription(reimbursementDescription); 
		this.setReimbursementAuthor(reimbursementAuthor);		
		this.setReimbursementStatus(reimbursementStatus); 
		this.setReimbursementType(reimbursementType);
	}
	
	public Reimbursement(int reimbursementId, Double reimbursementAmount, Timestamp reimbursementSubmitted, String reimbursementDescription, String reimbursementType, int reimbursementAuthor, String reimbursementAuthorName, String reimbursementResolved, String resolverName,  String reimbursementStatus) {
		super();		
		this.setReimbursementId(reimbursementId);
		this.setReimbursementAmount(reimbursementAmount);
		this.setReimbursementDescription(reimbursementDescription); 
		this.setReimbursementAuthor(reimbursementAuthor);		
		this.setReimbursementStatus(reimbursementStatus); 
		this.setReimbursementType(reimbursementType);
	}
	
	/*Josh: the constructor used to create the list of Reimbursement objects when querying the database to display on the user interface.*/
	public Reimbursement(int reimbursementId, Double reimbursementAmount, String reimbursementDescription, String reimbursementType, int reimbursementAuthor, String reimbursementAuthorName, String reimbursementSubmitted, int reimbursementResolver,  String reimbursementResolverName, String reimbursementResolved, String reimbursementStatus) {
		super();		
		this.setUserId(reimbursementAuthor);
		this.setReimbursementId(reimbursementId);
		this.setReimbursementAmount(reimbursementAmount);
		this.setReimbursementDescription(reimbursementDescription); 
		this.setReimbursementType(reimbursementType);
		this.setReimbursementAuthor(reimbursementAuthor);		
		this.setReimbursementAuthorName(reimbursementAuthorName);
		this.setReimbursementSubmitted(reimbursementSubmitted);
		this.setReimbursementResolver(reimbursementResolver);
		this.setReimbursementResolverName(reimbursementResolverName);
		this.setReimbursementResolved(reimbursementResolved);
		this.setReimbursementStatus(reimbursementStatus); 
		
	}
	
	public String toString() {
		return "Reimbursement[Amount"+this.reimbursementAmount+", TS:Submitted: "+", Description: "+this.reimbursementDescription+", Author: "+this.reimbursementAuthor+", Resolver: "+this.reimbursementResolver+", Status: "+this.reimbursementStatus+", Type: "+this.reimbursementType+" ]";
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}
		
	public Double getReimbursementAmount() {
		return reimbursementAmount;
	}
	public void setReimbursementAmount(Double reimbursement_amount) {
		this.reimbursementAmount = reimbursement_amount;
	}
	
	public String getReimbursementDescription() {
		return reimbursementDescription;
	}
	public void setReimbursementDescription(String reimbursementDescription) {
		this.reimbursementDescription = reimbursementDescription;
	}
	public int getReimbursementAuthor() {
		return reimbursementAuthor;
	}
	public void setReimbursementAuthor(int reimbursementAuthor) {
		this.reimbursementAuthor = reimbursementAuthor;
	}
	
	public String getReimbursementAuthorName() {
		return reimbursementAuthorName;
	}

	public void setReimbursementAuthorName(String reimbursementAuthorName) {
		this.reimbursementAuthorName = reimbursementAuthorName;
	}
	
	public String getReimbursementSubmitted() {
		return reimbursementSubmitted;
	}

	public void setReimbursementSubmitted(String reimbursementSubmitted) {
		this.reimbursementSubmitted = reimbursementSubmitted;
	}
	
	public int getReimbursementResolver() {
		return reimbursementResolver;
	}
	public void setReimbursementResolver(int reimbursementResolver) {
		this.reimbursementResolver = reimbursementResolver;
	}
	
	public String getReimbursementResolverName() {
		return reimbursementResolverName;
	}

	public void setReimbursementResolverName(String reimbursementResolverName) {
		this.reimbursementResolverName = reimbursementResolverName;
	}
	
	public String getReimbursementResolved() {
		return reimbursementResolved;
	}

	public void setReimbursementResolved(String reimbursementResolved) {
		this.reimbursementResolved = reimbursementResolved;
	}
	public String getReimbursementStatus() {
		return reimbursementStatus;
	}
	public void setReimbursementStatus(String reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}
	public String getReimbursementType() {
		return reimbursementType;
	}
	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}
	
	
	

}
