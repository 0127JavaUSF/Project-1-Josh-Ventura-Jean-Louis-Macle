package com.revature.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReimbursementPending extends Reimbursement {
	public ReimbursementPending (int userId, int reimbursementId ,Double reimbursementAmount, String reimbursementDescription, int reimbursementAuthor, String reimbursementType) {
		super(userId, reimbursementId, reimbursementAmount, Timestamp.valueOf(LocalDateTime.now()), reimbursementDescription, reimbursementAuthor, "Pending", reimbursementType);
	}
	
		
	public String toString() {
		return "Reimbursement[Amount: "+this.getReimbursementAmount()+", Description: "+this.getReimbursementDescription()+", Author: "+this.getReimbursementAuthor()+", Status: "+this.getReimbursementStatus()+", Type: "+this.getReimbursementType()+" ]";
	}

}
