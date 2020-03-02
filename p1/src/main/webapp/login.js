const employeeNavBar = document.getElementById('employeeNavBar');
const supervisorNavBar = document.getElementById('supervisorNavBar');
const supervisorTable = document.getElementById('supervisorTable');
const addReinbursementBlock = document.getElementById('addReinbursments');
const employeeTable = document.getElementById('employeeViewTickets');

const STATUSAPPROVE = 'Approved';
const STATUSDENY = 'Denied';

let managerTickets = null;
let selected = [];
let user = null;  

document.addEventListener("DOMContentLoaded", function() {

})
	// ignore red squiggles, eclipse doesn't like JS.

// fetches for supervisor table
async function postRequest(postParams, url, callback, postParamKey = null, postParamArrayValues = null) {
    try {
        // encode post params
    let formBody = [];
    for (let property in postParams) {
      const encodedKey = encodeURIComponent(property);
      const encodedValue = encodeURIComponent(postParams[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    
    if(postParamKey) {
    	
    	for(let param of postParamArrayValues) {
            const encodedKey = encodeURIComponent(postParamKey);
            const encodedValue = encodeURIComponent(param);
            formBody.push(encodedKey + "=" + encodedValue);        		
    	}        	
    }
    
    formBody = formBody.join("&");
    const config = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
    }
    const response = await fetch(url, config);
    
    const json = await response.json();
    callback(json, response.status, '');

}catch(error){
	// add login for error

    }
}

async function getRequest(url, callback) {
    try {
        const config = {
            method: 'GET',
}
const response = await fetch(url, config);

const json = await response.json();
callback(json, response.status, '');
}catch(error){
	// add login for error
}
}


function hideLogin(){
    const usernameInput = document.getElementById('loginUsername').value;
const passwordInput = document.getElementById('loginPassword').value;

if(usernameInput == '' || passwordInput == ''){
alert('Both username and password must be filled out');
document.getElementById('loginUsername').value = '';
document.getElementById('loginPassword').value = ''; 
    return false;
    
} 

const postparam = {
		username: usernameInput,
		password: passwordInput
};
postRequest(postparam, 'http://localhost:8080/p1/servletLogin/', (json, responsestatus)=> {

user = json;
document.getElementById('loginUsername').value = '';
document.getElementById('loginPassword').value = '';
if(user == "EMPLOYEE"){
    const login = document.getElementById('login');
    login.style.display = 'none';
    
    showEmployeeNavBar();
} else if(user == "MANAGER"){
    const login = document.getElementById('login');

    login.style.display = 'none';
            showSupervisorNavBar();
    	}
    })
}

// shows login when logout button is clicked
function showLogin(){
    login.style.display = 'flex'
    hideEmployeeNavBar();
    hideSupervisorNavBar();
    hideSupervisorTable();
    hideAddReinbursment();
    hideEmployeeTable();
}

function showEmployeeNavBar(){
    employeeNavBar.style.display = 'block';
    
}
function hideEmployeeNavBar(){
    employeeNavBar.style.display = 'none';
}

function showSupervisorNavBar(){
    supervisorNavBar.style.display = 'block';
}
function hideSupervisorNavBar(){
    supervisorNavBar.style.display = 'none';
}

function showEmployeeTable(){
    if(employeeTable.style.display === 'none'){
employeeTable.style.display = 'block';
hideAddReinbursment();      


getRequest('http://localhost:8080/p1/servletUserData/', (json, responsestatus)=> {
	
	const ticketArray = json
	
    const employeeTableBody = document.getElementById('employeeTableBody');

        let i = 1;
    	for(let j = 0 ; j < 3 ; j++){
    		
    		for(let ticket of ticketArray[j]){
        	
    			let tr = document.createElement("tr");
    			employeeTableBody.appendChild(tr)  
        
    			let th = document.createElement("th");
    			th.setAttribute("scope", "row")
    			th.innerText = i;
    			tr.appendChild(th)  
            
    			let td = document.createElement("td");
    			td.innerText = ticket.reimbursementType
    			tr.appendChild(td)  

    			td = document.createElement("td");
    			td.innerText = ticket.reimbursementDescription
    			tr.appendChild(td) 

    			td = document.createElement("td");
    			td.innerText = ticket.reimbursementAmount
    			tr.appendChild(td) 

    			td = document.createElement("td");
    			td.innerText = ticket.reimbursementSubmitted
    			tr.appendChild(td) 

    			td = document.createElement("td");
            			td.innerText = ticket.reimbursementStatus
            			tr.appendChild(td) 
                    

            			i++;
            		} 
            	}
        })
        
    } else {
        hideEmployeeTable();
        
    }
}
function hideEmployeeTable(){
    employeeTable.style.display = 'none';
document.getElementById('employeeTableBody').innerHTML = '';
    
}

function showSupervisorTable(){
    if(supervisorTable.style.display === 'none'){
supervisorTable.style.display = 'block';

getRequest('http://localhost:8080/p1/servletAllReimb/', (json, responsestatus)=> {
    	
    	const ticketArray = json;
    	managerTickets = ticketArray;
    	  
    	fillManagerTable(ticketArray);
    });

        
    } else {
        hideSupervisorTable();
    }
    
}

function fillManagerTable(ticketArray, status = 0) {
    const supervisorTableBody = document.getElementById('supervisorTableBody');

supervisorTableBody.innerHTML = "";

let i = 1;
for (let property in ticketArray) { // user ids

    let n = 0;
    for (let subArray of ticketArray[property]) {

        if(status == 0) {}
        else {
            if(n == 0 && status == 1) {}
            else if(n == 1 && status == 2) {}
            else if(n == 2 && status == 3) {}
            else {n++; continue;}
        }

        for (let ticket of subArray) {

            let tr = document.createElement('tr');
            supervisorTableBody.appendChild(tr)

            tr.dataset.id = ticket.reimbursementId;
            tr.classList.add('clickable-row');
            tr.addEventListener('click', function () {

                this.classList.add('tableData');
                const id = this.dataset.id;
                selected.push({ id: id, tr: this });
            });

            let th = document.createElement('th');
            th.innerText = i;
            tr.appendChild(th)


            let td = document.createElement('td');
            td.innerText = ticket.reimbursementAuthorName
            tr.appendChild(td)

            td = document.createElement('td');
            td.innerText = ticket.reimbursementType
            tr.appendChild(td)

            td = document.createElement('td');
            td.innerText = ticket.reimbursementDescription
            tr.appendChild(td)

            td = document.createElement('td');
            td.innerText = ticket.reimbursementAmount
            tr.appendChild(td)

            td = document.createElement('td');
            td.innerText = ticket.reimbursementSubmitted
            tr.appendChild(td)

            td = document.createElement('td');
            td.id = 'resolved' + ticket.reimbursementId;
            td.innerText = ticket.reimbursementResolved
            tr.appendChild(td)

            td = document.createElement('td');
            td.id = 'status' + ticket.reimbursementId;
                td.innerText = ticket.reimbursementStatus
                tr.appendChild(td)


                i++;
            }

            n++;
        }
    }
}

function hideSupervisorTable(){
    supervisorTable.style.display = 'none';
document.getElementById('supervisorTableBody').innerHTML = '';
}

function showAddReinbursment(){
    if(addReinbursementBlock.style.display === 'none'){
addReinbursementBlock.style.display = 'block';
        hideEmployeeTable();
    } else {
        hideAddReinbursment();
    }
}

function addReimbursement(){
    const descriptionInput = document.getElementById('exampleFormControlTextarea1').value;
const amountInput = document.getElementById('exampleFormControlInput1').value;
const typeInput = document.getElementById('exampleFormControlSelect1').value;

if(descriptionInput == '' || amountInput == ''){
alert('All sections must be filled out.');

document.getElementById('exampleFormControlTextarea1').value = '';
document.getElementById('exampleFormControlInput1').value='';
        
        return false;
    }
    
    
    
  const postparam = {
		  reimbursementDescription: descriptionInput,
		  reimbursementAmount: amountInput,
		  reimbursementType: typeInput
  };
  postRequest(postparam, 'http://localhost:8080/p1/servletNewReimb/', (json, responsestatus)=> {
  // clears input fields after submitting
  document.getElementById('exampleFormControlInput1').value = '';
  document.getElementById('exampleFormControlTextarea1').value='';
	  user = json;
  })
}

function hideAddReinbursment(){
    addReinbursementBlock.style.display = 'none';
}

function onApproveorDeny(status){
	let ids = [];
	for(let s of selected){
		ids.push(s.id)
	}
	const postparam = {
			status: status,
	};
	postRequest(postparam, 'http://localhost:8080/p1/servletChangeStatus/', (json, responsestatus)=> {
	const updatedTickets = json;
	
	for(let s of selected) {
		
		// remove highlight
		s.tr.classList.remove('tableData');
		
		let foundTicket = null;
		const id = s.id;
		for(let ticket of updatedTickets) {
			if(ticket.reimbursementId == id) {
				foundTicket = ticket;
				break;
			}
		}
		
    for (let property in managerTickets) { // user ids
        for (let subArray of managerTickets[property]) {
            for (let ticket of subArray) {
				if(ticket.reimbursementId == id) {
					ticket.reimbursementResolved = foundTicket.reimbursementResolved;
					ticket.reimbursementStatus = foundTicket.reimbursementStatus == 2 ? "Approved" : "Denied";
					break;
				}
            }
        }
    }
		
		if(foundTicket) {
			document.getElementById("resolved" + id).innerText = foundTicket.reimbursementResolved;
			document.getElementById("status" + id).innerText = foundTicket.reimbursementStatus == 2 ? "Approved" : "Denied";
		}
	}
	
	selected = [];

    }, "reimbIds", ids)
	}
function onApprove(){
	onApproveorDeny(STATUSAPPROVE)
}
function onDeny(){
	onApproveorDeny(STATUSDENY)
}

function onFilter() {
	
	const status = document.getElementById("filter").value;
	
	fillManagerTable(managerTickets, status);
}