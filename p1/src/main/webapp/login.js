const employeeNavBar = document.getElementById('employeeNavBar');
const supervisorNavBar = document.getElementById('supervisorNavBar');
const supervisorTable = document.getElementById('supervisorTable');
const addReinbursementBlock = document.getElementById('addReinbursments');
const employeeTable = document.getElementById('employeeViewTickets');

let user = null;  


// hides login menu when login button is pressed.
// need to update this to be if correct user info and password are entered



document.addEventListener("DOMContentLoaded", function() {

})
	//ignore red squiggles, eclipse doesn't like JS.
	async function postRequest(postParams, url, callback) {
        try {
            //encode post params
            let formBody = [];
            for (let property in postParams) {
              const encodedKey = encodeURIComponent(property);
              const encodedValue = encodeURIComponent(postParams[property]);
              formBody.push(encodedKey + "=" + encodedValue);
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
                    employeeTableBody.appendChild(th)  
                    
                    
                    
                    let td = document.createElement("td");
                    td.innerText = ticket.reimbursementType
                    employeeTableBody.appendChild(td) 

                    td = document.createElement("td");
                    td.innerText = ticket.reimbursementDescription
                    employeeTableBody.appendChild(td) 

                    td = document.createElement("td");
                    td.innerText = ticket.reimbursementAmount
                    employeeTableBody.appendChild(td) 

                    td = document.createElement("td");
                    td.innerText = ticket.reimbursementAuthor
                    employeeTableBody.appendChild(td) 

                    td = document.createElement("td");
                    td.innerText = ticket.reimbursementStatus
                    employeeTableBody.appendChild(td) 
                    

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
}

function showSupervisorTable(){
    if(supervisorTable.style.display === 'none'){
        supervisorTable.style.display = 'block';
    } else {
        hideSupervisorTable();
    }
    
}
function hideSupervisorTable(){
    supervisorTable.style.display = 'none';
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
	  //clears input fields after submitting 
      document.getElementById('exampleFormControlInput1').value = '';
      document.getElementById('exampleFormControlTextarea1').value='';
	  user = json;
  })
}

function hideAddReinbursment(){
    addReinbursementBlock.style.display = 'none';
}

//selects individual rows and highlights them
selectRow()

function selectRow(){
    const table = document.getElementById('table');
    const cells = table.getElementsByTagName('td');

    for(let i = 0; i < cells.length; i++) {
        let cell = cells[i];

        cell.onclick = function() {

            let rowId = this.parentNode.rowIndex;

            let rowsNotSelected = table.getElementsByTagName('tr');
            for (let row = 0; row < rowsNotSelected.length; row++){
                rowsNotSelected[row].style.backgroundColor = "";
                rowsNotSelected[row].classList.remove('selected');
            }
            let rowSelected = table.getElementsByTagName('tr')[rowId];
            rowSelected.style.backgroundColor = "lightgrey";
            rowSelected.className += " selected";
        }
    }
}

