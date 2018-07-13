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
function fuc(v) {
    return v;
}

// is the same as:
var fuc = new Function('v', 'return v;');



/* 4. Regular Expression Literals */
var nbr = /\d+/g;

// is the same as:
var nbr = new RegExp('\\d+', 'g');
