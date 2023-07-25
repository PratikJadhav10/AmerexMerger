function displayblock() {
	if (document.getElementById('tradeRefOption').checked) {
		document.getElementById('bridgeRequestId').disabled = false;
		document.getElementById('fromDateInput').disabled = false;
		document.getElementById('toDateInput').disabled = false;
		document.getElementById('select-start-period').style.display = "block";
		document.getElementById('select-end-period').style.display = "block";
		document.getElementById('bridgeRequestId').placeholder = "Enter TradeRef";
	}
	else {
		document.getElementById('bridgeRequestId').disabled = false;
		document.getElementById('select-start-period').style.display = "none";
		document.getElementById('select-end-period').style.display = "none";
		document.getElementById('bridgeRequestId').placeholder = "Enter GatewayId";
	}
}
/*function enableDateField() {
	document.getElementById('bridgeRequestId').disabled = false;
	//document.getElementById('flatdateInput').disabled = false;
	document.getElementById('fromDateInput').disabled = false;
	document.getElementById('toDateInput').disabled = false;
	var currentDate = new Date().toISOString().slice(0, 16);
	//document.getElementById('dateInput').min = currentDate;
	//document.getElementById('dateInput').max = currentDate;
	//document.getElementById('dateInput').value = currentDate;
	document.getElementById('bridgeRequestId').placeholder = "Enter TradeRef value";
}

function disableDateField() {
	//document.getElementById('flatdateInput').disabled = true;
	document.getElementById('fromDateInput').disabled = true;
	document.getElementById('toDateInput').disabled = true;
	document.getElementById('bridgeRequestId').disabled = false;
	document.getElementById('bridgeRequestId').placeholder = "Enter GatewayId";
}
*/

/*function initializeCodeMirror() {
	//debugger;
	var textarea = document.getElementById("designationTextArea");
	var codeMirrorInstance = CodeMirror.fromTextArea(textarea, {
		lineNumbers: true, // Show line numbers
		//matchBrackets: true,
		mode: "xml", // Set the mode to XML or the appropriate language you need
		theme: "default",
		//lineSeparator: "\n" // Set the theme to "default" or any other theme you prefer
	});

	return codeMirrorInstance;
}
*/
function retrieveXML() {
	$(document).ready(function() {
		$("#displayBtn").click(function() {
			var bridgeRequestId = $("#bridgeRequestId").val();
			$.get("/data/new/" + bridgeRequestId, function(data) {
				$("#designationTextArea").val(data);
			});
		});
	});
	// Retrieve XML logic goes here
}

//WORKING
function retrieveXMLFromReqFileName() {
	/*$(document).ready(function () {
		$("#displayBtn").click(function () {
			var reqFileName = $("#bridgeRequestId").val();
			$.get("/data/new/reqFileName/" + reqFileName, function (data) {
				$("#designationTextArea").val(data);
			});
		});
	});*/
	console.log('retrieveXMLFromReqFileName()');
	//debugger;
	var reqFileName = $("#bridgeRequestId").val();
	$.get("/data/new/reqFileName/" + reqFileName, function(data) {
		var parsedData = parseNewlinesAndTabs(data);
		console.log(data);
		//	initializeCodeMirror();
		$("#designationTextArea").val(parsedData);
	});
}

function validateInputForGatewayId() {
	var reqFileName = $("#bridgeRequestId").val();
	var numberRegex = /^\d+$/;
	if (!numberRegex.test(reqFileName) && gatewayIdOption.checked) {
		document.getElementById('error-message').textContent = "Enter valid input with only numbers";
		//alert("Enter a valid input with only numbers");
		//return;
	}
	else {
		document.getElementById('error-message').textContent = "";
	}
}

//WORKING
function retrieveDataFromTradeRefAndDate() {
	/*$(document).ready(function () {
		$("#displayBtn").click(function () {
			var tradeRef = $("#bridgeRequestId").val();
			console.log("id:" + tradeRef);
			var date = new Date($("#flatdateInput").val());
			console.log("date:" + date);

			// Format the date in the desired format expected by the backend
			var formattedDate = date.toISOString();
			console.log("formatted date:" + formattedDate);

			$.get("/data/get/tradeRefDate/" + tradeRef, {date: formattedDate}, function (data) {
				$("#designationTextArea").val(data);
			}).fail(function () {
				$("#designationTextArea").val("Error retrieving data.");
			});
		});
	});*/
	var tradeRef = $("#bridgeRequestId").val();
	console.log("id:" + tradeRef);
	var date = new Date($("#flatdateInput").val());
	console.log("date:" + date);

	// Format the date in the desired format expected by the backend
	var formattedDate = date.toISOString();
	console.log("formatted date:" + formattedDate);

	$.get("/data/get/tradeRefDate/" + tradeRef, { date: formattedDate }, function(data) {
		$("#designationTextArea").val(data);
	}).fail(function() {
		$("#designationTextArea").val("Error retrieving data.");
	});
}
//WORKING
function handleretrieve() {
	var tradeRefOption = document.getElementById("tradeRefOption");
	var gatewayIdOption = document.getElementById("gatewayIdOption");
	var bridgeRequestId = document.getElementById("bridgeRequestId").value.trim();

	if (tradeRefOption.checked) {
		getDatawithTradeRefAndDateRange2(bridgeRequestId);
	} else if (gatewayIdOption.checked) {
		retrieveXMLFromReqFileName(bridgeRequestId);
	}
}

function handleupdatedData() {
	var tradeRefOption = document.getElementById("tradeRefOption");
	var gatewayIdOption = document.getElementById("gatewayIdOption");
	//var bridgeRequestId = document.getElementById("bridgeRequestId").value.trim();

	if (tradeRefOption.checked) {
		updateXmlDataFromTradeRefAndDate();
	} else if (gatewayIdOption.checked) {
		updateXmlDataFromReqFileName();
	}
}

//WORKING
function updateXmlDataFromReqFileName() {
	//debugger;
	// Retrieve the updated data from the textarea
	var reqFileName = $("#bridgeRequestId").val();
	var updatedData = $("#designationTextArea").val();
	//var updatedData = document.getElementById("designationTextArea").value;
	//var bridgeRequestId = document.getElementById("bridgeRequestId").value;

	// Fetch the CSRF token
	fetch("/api/amerex/csrf-token")
		.then(function(response) {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error("Error retrieving CSRF token: " + response.status);
			}
		})
		.then(function(csrfToken) {
			// Prepare the data to be sent in the request body
			var requestBody = {
				reqFileName: reqFileName,
				updatedData: updatedData
			};

			// Send the AJAX request with the CSRF token
			fetch(`/data/new/update/reqFileName/${requestBody.reqFileName}`, {
				method: "PUT",
				headers: {
					"Content-Type": "application/json",
					"X-CSRF-Token": csrfToken
				},
				body: requestBody.updatedData
			})
				.then(function(response) {
					// Handle the response
					if (response.ok) {
						return response.text();
					} else {
						throw new Error("Error updating data: " + response.status);
					}
				})
				.then(function(responseText) {
					console.log("Successful");
					// Display a success message or perform any other actions
					console.log(responseText);
				})
				.catch(function(error) {
					// Handle any errors that occurred during the request
					console.log("Failed");
					console.error(error);
				});
		})
		.catch(function(error) {
			// Handle any errors that occurred during the CSRF token retrieval
			console.error(error);
		});
}

function getDatawithTradeRefAndDateRange2() {
	var tradeRef = document.getElementById("bridgeRequestId").value;
	console.log("TradeRef:" + tradeRef);
	var fromDate = new Date($("#fromDateInput").val());
	var toDate = new Date($("#toDateInput").val());
	console.log("fromDate:" + fromDate);
	console.log("toDate:" + toDate);

	// Format the date in the desired format expected by the backend
	var formattedfromDate = fromDate.toISOString();
	var formattedtoDate = toDate.toISOString();
	console.log("formattedfromDate:" + formattedfromDate);
	console.log("formattedtoDate:" + formattedtoDate);

	$.get("/data/new/findByDateAndMessageLoadData/" + tradeRef, { createDate: formattedfromDate, modifiedDate: formattedtoDate })
		.done(function(data) {
			displayDataforTradeRef(data);
		})
		.fail(function() {
			$("#designationTextArea").val("Error retrieving data.");
		});
}

// Function to replace \n and \t with their representations
function parseNewlinesAndTabs(input) {
	// Replace \n with a newline character (line break)
	var parsedInput = input.replace(/\\n/g, "\n");

	// Replace \t with a tab character
	parsedInput = parsedInput.replace(/\\t/g, "\t");

	return parsedInput;
}

var selectedReqFileName = null;
function displayDataforTradeRef(data) {
	var container = document.getElementById("dataContainer");
	container.innerHTML = ""; // Clear previous data

	if (data.length > 0) {
		var table = document.createElement("table");
		table.classList.add("table", "table-striped");

		// Create table header
		var headerRow = document.createElement("tr");
		for (var prop in data[0]) {
			var headerCell = document.createElement("th");
			headerCell.textContent = prop;
			headerRow.appendChild(headerCell);
		}
		table.appendChild(headerRow);

		// Create table rows
		for (var i = 0; i < data.length; i++) {
			var rowData = data[i];
			var row = document.createElement("tr");
			row.setAttribute("data-reqFileName", rowData.reqFileName); // Set the reqFileName as a data attribute
			row.setAttribute("data-messageLoad", rowData.messageLoad); // Set the messageLoad as a data attribute


			for (var prop in rowData) {
				var cell = document.createElement("td");
				var cellContent = rowData[prop];

				if (prop === "messageLoad") {
					cell.classList.add("message-load-cell");
					cell.setAttribute("title", cellContent);
					cell.textContent = cellContent;

					cell.addEventListener("dblclick", function() {
						var cellValue = this.textContent;
						var textarea = document.getElementById("designationTextArea");
						textarea.value = parseNewlinesAndTabs(cellValue); // Set the textarea content to the cell value
						//	textarea.dataset.line = countLines(cellValue); // Update line numbers
						var reqFileName = this.parentNode.getAttribute("data-reqFileName");
						var messageLoad = this.parentNode.getAttribute("data-messageLoad");
						console.log("reqFileName:", reqFileName);
						console.log("messageLoad:", messageLoad);
						selectedReqFileName = reqFileName;
					});
				} else {
					cell.textContent = cellContent;
				}
				row.appendChild(cell);
			}
			table.appendChild(row);
		}
		container.appendChild(table);
	} else {
		container.textContent = "No data found.";
	}
}

function updateXmlDataFromTradeRefAndDate() {
	console.log("Function executing properly");
	//debugger;
	// Check if a reqFileName is selected
	if (selectedReqFileName === null) {
		console.error("No reqFileName selected.");
		return;
	}
	console.log("SelectedReqFileNamee:" + selectedReqFileName);
	// Retrieve the updated data from the textarea
	var updatedData = $("#designationTextArea").val();

	// Fetch the CSRF token
	fetch("/api/amerex/csrf-token")
		.then(function(response) {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error("Error retrieving CSRF token: " + response.status);
			}
		})
		.then(function(csrfToken) {
			// Prepare the data to be sent in the request body
			var requestBody = {
				updatedData: updatedData
			};	

			// Send the AJAX request with the CSRF token
			fetch(`/data/new/update/reqFileName/${selectedReqFileName}`, {
				method: "PUT",
				headers: {
					"Content-Type": "application/json",
					"X-CSRF-Token": csrfToken
				},
				body: requestBody.updatedData
			})
				.then(function(response) {
					// Handle the response
					if (response.ok) {
						return response.text();
					} else {
						throw new Error("Error updating data: " + response.status);
					}
				})
				.then(function(responseText) {
					console.log("Successful");
					// Display a success message or perform any other actions
					console.log(responseText);
				})
				.catch(function(error) {
					// Handle any errors that occurred during the request
					console.log("Failed");
					console.error(error);
				});
		})
		.catch(function(error) {
			// Handle any errors that occurred during the CSRF token retrieval
			console.error(error);
		});

}
//Working not in use
/*function displayDataforTradeRef(data) {
	var container = document.getElementById("dataContainer");
	container.innerHTML = ""; // Clear previous data

	if (data.length > 0) {
		var table = document.createElement("table");
		table.classList.add("table", "table-striped");
		//table.border = "1";

		// Create table header
		var headerRow = document.createElement("tr");
		for (var prop in data[0]) {
			var headerCell = document.createElement("th");
			headerCell.textContent = prop;
			headerRow.appendChild(headerCell);
			var headerCell = document.createElement("th");
			headerCell.classList.add("table-header");
			headerCell.textContent = prop;
			headerRow.appendChild(headerCell);
		}
		table.appendChild(headerRow);

		// Create table rows
		for (var i = 0; i < data.length; i++) {
			var rowData = data[i];
			var row = document.createElement("tr");

			for (var prop in rowData) {
				var cell = document.createElement("td");
				var cellContent = rowData[prop];

				if (prop === "messageLoad") {
					cell.classList.add("message-load-cell");
					cell.setAttribute("title", cellContent);
					cell.textContent = cellContent.length > 50 ? cellContent.substring(0, 50) + "..." : cellContent;
					cell.addEventListener("dblclick", function() {
						this.style.whiteSpace = "normal";
						this.style.overflow = "auto";
					});
				} else {
					cell.textContent = cellContent;
				}
				cell.classList.add("table-cell");
				cell.textContent = rowData[prop];
				row.appendChild(cell);
			}

			table.appendChild(row);
		}

		container.appendChild(table);
	} else {
		container.textContent = "No data found.";
	}
}*/

//WORKING(NOT IS USE)
function postDataForBridgeRequesatID() {
	// Retrieve the updated data from the textarea
	var bridgeRequestId = $("#bridgeRequestId").val();
	var updatedData = $("#designationTextArea").val();
	//var updatedData = document.getElementById("designationTextArea").value;
	//var bridgeRequestId = document.getElementById("bridgeRequestId").value;

	// Fetch the CSRF token
	fetch("/api/amerex/csrf-token")
		.then(function(response) {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error("Error retrieving CSRF token: " + response.status);
			}
		})
		.then(function(csrfToken) {
			// Prepare the data to be sent in the request body
			var requestBody = {
				bridgeRequestId: parseInt(bridgeRequestId), // Convert to a number
				updatedData: updatedData
			};

			// Send the AJAX request with the CSRF token
			fetch(`/data/new/update/messageload?bridgeRequestId=${requestBody.bridgeRequestId}`, {
				method: "PUT",
				headers: {
					"Content-Type": "application/json",
					"X-CSRF-Token": csrfToken
				},
				body: JSON.stringify(requestBody.updatedData)
			})
				.then(function(response) {
					// Handle the response
					if (response.ok) {
						return response.text();
					} else {
						throw new Error("Error updating data: " + response.status);
					}
				})
				.then(function(responseText) {
					console.log("Successful");
					// Display a success message or perform any other actions
					console.log(responseText);
				})
				.catch(function(error) {
					// Handle any errors that occurred during the request
					console.log("Failed");
					console.error(error);
				});
		})
		.catch(function(error) {
			// Handle any errors that occurred during the CSRF token retrieval
			console.error(error);
		});
}



/*function getDatawithTradeRefAndDateRange() {
	var keyword = document.getElementById("bridgeRequestId").value;
	var fromDate = document.getElementById("fromDateInput").value;
	var toDate = document.getElementById("toDateInput").value;

	// Send AJAX request to the backend
	var xhr = new XMLHttpRequest();
	xhr.open("GET", "/data/new/findByDateAndMessageLoadData/" + encodeURIComponent(keyword) +
		"?startDate=" + encodeURIComponent(fromDate) +
		"&endDate=" + encodeURIComponent(toDate));
	xhr.onreadystatechange = function () {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				var data = JSON.parse(xhr.responseText);
				displayDataforTradeRef(data);
			} else {
				console.error("Error retrieving data: " + xhr.status);
			}
		}
	};
	xhr.send();
}*/

function retrieveData() {
	$(document).ready(function() {
		$("#displayBtn").click(function() {
			var id = $("#bridgeRequestId").val();
			console.log("id:" + id);
			var date = new Date($("#flatdateInput").val());
			console.log("date:" + date);

			// Format the date in the desired format expected by the backend
			var formattedDate = date.toISOString();
			console.log("formatted date:" + formattedDate);

			$.get("/data/get/id/" + id, { date: formattedDate }, function(data) {
				$("#designationTextArea").val(data);
			}).fail(function() {
				$("#designationTextArea").val("Error retrieving data.");
			});
		});
	});
}

function retrieveFromTradeRef() {
	$(document).ready(function() {
		$("#displayBtn").click(function() {
			var tradeRef = $("#bridgeRequestId").val();
			console.log(tradeRef);
			var dateTimeString = $("#flatdateInput").val();
			console.log(dateTimeString);

			var dateTime = new Date(dateTimeString);
			console.log(dateTime);

			// Format the date and time in the desired format expected by the backend
			var formattedDateTime = dateTime.toISOString();
			console.log(formattedDateTime);
			console.log(encodeURIComponent(formattedDateTime));

			$.get("/data/get/tradeRef/" + tradeRef + "/" + encodeURIComponent(formattedDateTime), function(data) {
				$("#designationTextArea").val(data);
			}).fail(function() {
				$("#designationTextArea").val("Error retrieving data.");
			});
		});
	});
}



/*flatpickr(".datetimepicker", {
	enableTime: true, // Enable time selection
	dateFormat: "Y-m-d H:i", // Format of the selected date and time
});
*/


/*$(document).ready(function() {
	$('#datetimepicker').datetimepicker({
		format: 'YYYY-MM-DD HH:mm:ss'
	});
	// Event listener for form submission
	document.getElementById('fetchDataForm').addEventListener('submit', function(event) {
		event.preventDefault();

		// Get the values from the form
		const tradeRef = document.getElementById('tradeRefInput').value;
		const date = document.getElementById('dateInput').value;

		// Call the fetchMessageLoad function
		fetchMessageLoad(tradeRef, date);
	});
})


function updateXML() {
	// Retrieve the updated data from the textarea
	var bridgeRequestId = $("#bridgeRequestId").val();
	var updatedData = $("#designationTextArea").val();
	console.log("hello there 1");

	// Prepare the data to be sent in the request body
	var requestBody = {
		bridgeRequestId: bridgeRequestId, // Replace with the actual record count
		updatedData: updatedData
	};

	// Get the CSRF token from the server
	$.get("/api/amerex/csrf-token", function(csrfToken) {
		// Include the CSRF token in the request headers
		$.ajax({
			url: "/api/amerex/updateData",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(requestBody),
			headers: {
				"X-CSRF-TOKEN": csrfToken // Include the CSRF token in the request headers
			},
			success: function(response) {
				// Handle the response
				console.log(response);
				console.log("hello there");// Perform any additional actions after successful update
			},
			error: function(error) {
				// Handle any errors that occurred during the request
				console.error(error);
			}
		});
	});
	console.log("hello there 2");
}



function updateData() {
	// Retrieve the updated data from the textarea
	var updatedData = document.getElementById("textarea").value;

	// Prepare the data to be sent in the request body
	var requestBody = {
		recordCount: recordCount, // Replace with the actual record count
		updatedData: updatedData
	};

	// Send the AJAX request
	fetch("/api/amerex/updateData", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(requestBody)
	})
		.then(function(response) {
			// Handle the response
			if (response.ok) {
				return response.text();
			} else {
				throw new Error("Error updating data: " + response.status);
			}
		})
		.then(function(responseText) {
			// Display a success message or perform any other actions
			console.log(responseText);
		})
		.catch(function(error) {
			// Handle any errors that occurred during the request
			console.error(error);
		});
}


/*
flatpickr(".datetimepicker", {
	enableTime: true, // Enable time selection
	dateFormat: "Y-m-d H:i", // Format of the selected date and time
});*/

//Below is JS function of code mirror to display formatted XML data
/* Get the textarea element
var textarea = document.getElementById("designationTextArea");
var editor = CodeMirror.fromTextArea(textarea, {
	lineNumbers: true,
	readOnly: true, // To make the text read-only
	mode: 'xml'

});

//Below functionality is to handle the two different functions based on radio buttons selected.


//retrieveBtn.addEventListener("click", handleretrieve);
//  var retrieveBtn = document.getElementById("displayBtn");


// Get the current date and time
/*var currentDate = new Date();
var currentYear = currentDate.getFullYear();
var currentMonth = currentDate.getMonth() + 1; // Months are zero-based
var currentDay = currentDate.getDate();
var currentHour = currentDate.getHours();
var currentMinute = currentDate.getMinutes();
if (currentHour >= 12) {
	// If current time is 12:00 or later, set "From Date" to today at 12:00
	var fromDate = new Date(currentYear, currentMonth - 1, currentDay, 12, 0);
} else {
	// If current time is before 12:00, set "From Date" to yesterday at 12:00
	var fromDate = new Date(currentYear, currentMonth - 1, currentDay - 1, 12, 0);
}
document.getElementById('fromDateInput').value = fromDate.toISOString().slice(0, 16);
var toDate = new Date(currentYear, currentMonth - 1, currentDay + 30, 12, 0); // Set "To Date" to 30 days ahead at 12:00
document.getElementById('toDateInput').value = toDate.toISOString().slice(0, 16);

// Set the maximum date and time for the fromDateInput
var maxFromDate = new Date(currentYear, currentMonth - 1, currentDay, 15, 30).toISOString().split('.')[0];
document.getElementById('fromDateInput').max = maxFromDate;*/

		// Add event listener to all cells with the 'message-load-cell' class
