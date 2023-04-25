function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter account number"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("account number length: 3-20"))
  } else {
    callback()
  }
}


function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter name"))
  } else if (value.length > 12) {
    callback(new Error("name length: 1-12"))
  } else {
    callback()
  }
}

