var clicks=0;
var sets=0;
//Not part of base converter, but this is for another part of the website
function clicking() { 
	clicks+=1;
	document.getElementById("counter").innerHTML = "Number of clicks: "+clicks;
	document.getElementById("sets").innerHTML = "Number of sets (7 clicks): "+sets;
    if(document.getElementById("demo").innerHTML == "This is a demonstration.") {
    	document.body.style.backgroundColor="#FF0000";
    	document.getElementById("demo").innerHTML = "Hello World!";
    }
    else if(document.getElementById("demo").innerHTML == "Hello World!") {
    	document.body.style.backgroundColor="#FF6600";
    	document.getElementById("demo").innerHTML = "Foo bar";
    }
    else if(document.getElementById("demo").innerHTML == "Foo bar") {
    	document.body.style.backgroundColor="#FFFF00";
    	document.getElementById("demo").innerHTML = "Computer science";
    }
    else if(document.getElementById("demo").innerHTML == "Computer science") {
    	document.body.style.backgroundColor="#00FF00";
    	document.getElementById("demo").innerHTML = "More words";
    }
    else if(document.getElementById("demo").innerHTML == "More words") {
    	document.body.style.backgroundColor="#0000FF";
    	document.getElementById("demo").innerHTML = "Even more words";
    }
    else if(document.getElementById("demo").innerHTML == "Even more words") {
    	document.body.style.backgroundColor="#4B0082";
    	document.getElementById("demo").innerHTML = "Even more words again";
    	sets+=1;
    }
    else {
    	document.body.style.backgroundColor="#7F00FF";
    	document.getElementById("a").innerHTML = "Keep clicking!";
    	document.getElementById("demo").innerHTML = "This is a demonstration.";
    }
}
//Revert the website and counter to the start
function reset() {
	clicks=0;
	sets=0;
	document.getElementById("demo").innerHTML = "This is a demonstration.";
	document.body.style.backgroundColor="#FFFFFF";
	document.getElementById("counter").innerHTML = "Number of clicks: "+clicks;
	document.getElementById("sets").innerHTML = "Number of sets (7 clicks): "+sets;
	document.getElementById("a").innerHTML = "Click me!";
}
//The method to convert base after getting ipnut from the user
function Convert() {
	var number = document.getElementById("number").value.toUpperCase();
	var bse = document.getElementById("base").value;
	var base = parseInt(bse);
	var frm = document.getElementById("from").value;
	if (frm !== "") {
		var from = parseInt(frm);
	} else {
		var from = 10;
	}
	var num = parseInt(number, from);
	if((isNaN(num) || notValidNumber(10, bse) || notValidNumber(10, frm))) {
		document.getElementById("error").innerHTML = "Sorry, that is invalid input. Please make sure that you input bases between 2 and 36 and no letters or special characters";
		document.getElementById("decimal").innerHTML = "";
		document.getElementById("original").innerHTML = "";
		document.getElementById("converted").innerHTML = "";
	}
	else if((base > 36) || (base < 2) || (from > 36) || (from < 2)) {
		document.getElementById("error").innerHTML = "Sorry, that is invalid input. Please make sure that you input a base that is between 2 and 36";
		document.getElementById("decimal").innerHTML = "";
		document.getElementById("original").innerHTML = "";
		document.getElementById("converted").innerHTML = "";
	}
	else if (notValidNumber(from, number)) {
		document.getElementById("error").innerHTML = "Sorry, that is invalid input. The number that was input is not a legal number for the 'convert from' base";
		document.getElementById("decimal").innerHTML = "";
		document.getElementById("original").innerHTML = "";
		document.getElementById("converted").innerHTML = "";
	}
	else {
		document.getElementById("original").innerHTML = "Here is your original number (base " + from + ") : " + number;
		if (from != 10) {
			document.getElementById("decimal").innerHTML = "Here is your number in base 10: " + num;
		}
		else {
			document.getElementById("decimal").innerHTML = "";
		}
		if (base == 10) {
			document.getElementById("converted").innerHTML = "";	
		} 
		else {
			document.getElementById("converted").innerHTML = "Here is your converted number (base " + base + ") : " + num.toString(base).toUpperCase();
		}
		document.getElementById("error").innerHTML = "";
	}
}
//Checks to see if the nnumber is valid for the base
function notValidNumber(base, number) {
	if(base < 3) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$98765432]/i).test(number));
	}
	else if(base < 4) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$9876543]/i).test(number));
	}
	else if(base < 5) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$987654]/i).test(number));
	}
	else if(base < 6) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$98765]/i).test(number));
	}
	else if(base < 7) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$9876]/i).test(number));
	}
	else if(base < 8) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$987]/i).test(number));
	}
	else if(base < 9) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$98]/i).test(number));
	}
	else if(base < 10) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$9]/i).test(number));
	}
	else if(base < 11) {
		return ((/[abcdefghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 12) {
		return ((/[bcdefghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 13) {
		return ((/[cdefghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 14) {
		return ((/[defghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 15) {
		return ((/[efghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 16) {
		return ((/[fghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 17) {
		return ((/[ghijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 18) {
		return ((/[hijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 19) {
		return ((/[ijklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 20) {
		return ((/[jklmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 21) {
		return ((/[klmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 22) {
		return ((/[lmnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 23) {
		return ((/[mnopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 24) {
		return ((/[nopqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 25) {
		return ((/[opqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 26) {
		return ((/[pqrstuvwxyz*$]/i).test(number));
	}
	else if(base < 27) {
		return ((/[qrstuvwxyz*$]/i).test(number));
	}
	else if(base < 28) {
		return ((/[rstuvwxyz*$]/i).test(number));
	}
	else if(base < 29) {
		return ((/[stuvwxyz*$]/i).test(number));
	}
	else if(base < 30) {
		return ((/[tuvwxyz*$]/i).test(number));
	}
	else if(base < 31) {
		return ((/[uvwxyz*$]/i).test(number));
	}
	else if(base < 32) {
		return ((/[vwxyz*$]/i).test(number));
	}
	else if(base < 33) {
		return ((/[wxyz*$]/i).test(number));
	}
	else if(base < 34) {
		return ((/[xyz*$]/i).test(number));
	}
	else if(base < 35) {
		return ((/[yz*$]/i).test(number));
	}
	else if(base < 36) {
		return ((/[z*$]/i).test(number));
	}
	else if(base < 37) {
		return ((/[*$]/i).test(number));
	}
	return false;
}