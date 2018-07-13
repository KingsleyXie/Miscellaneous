/* 1. Naive Sample For Reference Types */
var obj1 = new Object();
var obj2 = obj1;
obj1.name = 'name';
console.log(obj2.name);



/* 2. Object Literals */
var book = {
    name: "Book Name",
    year: 2018
};

// is the same as:
var book = {
    "name": "Book Name",
    "year": 2018
}

// and the same as:
var book = new Object();
book.name = "Book Name";
book.year = 2018;



/* 3. Function Literals */
function fun(v) {
    return v;
}

// is the same as:
var fun = new Function('v', 'return v;');



/* 4. Regular Expression Literals */
var nbr = /\d+/g;

// is the same as:
var nbr = new RegExp('\\d+', 'g');



/* 5. Property Access */
var arr = [];
arr.push(1);

// object's properties can be accessed via both dot notaion and bracket notation
// so it is the same as:
arr['push'](1);

// and the property value can actually be dynamically decided
// so it is also the same as:
var mtd = 'push';
arr[mtd](1);



/* 6. Identifying Reference Types */
var arr = [];
var obj = {};
function fun(v) { return v; }

console.log(arr instanceof Array);    // true
console.log(arr instanceof Object);   // true
console.log(obj instanceof Object);   // true
console.log(obj instanceof Array);    // false
console.log(fun instanceof Function); // true
console.log(fun instanceof Object);   // true

// actually the best way to identify arrays, especially for web developers:
console.log(Array.isArray(arr));      // true



/* 7. Primitive Wrapper Types */
var name = "Name";
var ch = name.charAt(0);
console.log(ch);

// what the JavaScript engine does:
var name = "Name";
var temp = new String(name);
var ch = temp.charAt(0);
temp = null; // autoboxing, so it is destoryed after being temporary used
console.log(ch);



/* 8. Primitive Wrapper Types (Futher) */
var name = "Name";
name.attr = "none";
console.log(name.attr); // undefined

// what the JavaScript engine does
var name = "Name";
var temp = new String(name);
temp.attr = "none";
temp = null; // temporary object destroyed
var temp = new String(name); // `temp` is now a new(different) object
console.log(temp.attr); // undefined
temp = null;



/* 9. Temporary Object Is Created Only When A Value Is [Read] */
var name = "Name";
var count = 10;
var found = false;
console.log(name instanceof String);   // false
console.log(count instanceof Number);  // false
console.log(found instanceof Boolean); // false



/* 10. An Object Is Always Considered True Inside A Conditional Statement */
var found = new Boolean(false);
if (found) alert(1); // alert will be executed
