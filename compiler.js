(function() {

var _ = require('./underscore-min.js');
var util = require('util');

function calcStackSize(prog) {
	return 10;
}

function binaryOp(instruction, operands) {
	if (operands.length != 2) {
		throw new Error(
			'Wrong number of operands to binary operator: ' + operands.length);
	}

	return [
		compile(operands[0]),
		compile(operands[1]),
		instruction,
	].join('\n');
}

builtins = {
	"+": function(args) { return binaryOp("iadd", args); },
	"-": function(args) { return binaryOp("iadd", args); },
	"*": function(args) { return binaryOp("iadd", args); },
	"/": function(args) { return binaryOp("iadd", args); },
	"println": function(args) {
		if (args.length != 1) {
			throw new Error('Wrong number of arguments to println');
		}
		
		return [
			"getstatic java/lang/System/out Ljava/io/PrintStream;",
			compile(args[0]),
			"invokevirtual java/io/PrintStream/println(I)V",
		].join('\n');
	},
};	

function genPrimitive(prim) {
	if (typeof prim === 'number') {
		return 'ldc ' + prim;
	} else {
		throw new Error('Unhandled primitive type');
	}
}

function compile(prog) {
	if (util.isArray(prog)) {
		throw new Error('Invalid list in parse tree');
	}

	if (typeof prog === 'object') {
		if (prog.func in builtins) {
			return builtins[prog.func](prog.args);
		} else {
			throw new Error('Unrecognized symbol: ' + prog.func);
		}
	} else {
		return genPrimitive(prog);
	}
}

function genClass(name, contents) {
	return [
		'.class ' + name,
		'.super java/lang/Object',
		'.method public <init>()V',
		'aload_0',
		'invokenonvirtual java/lang/Object/<init>()V',
		'return',
		'.end method',
		contents,
	].join('\n');
}

function genMain(body) {
	return [
		'.method public static main([Ljava/lang/String;)V',
		'.limit stack ' + calcStackSize(body),
		body,
		'return',
		'.end method',
	].join('\n');
}

console.log(genClass("Main", genMain(compile(
	{func: "println",
	 args: [{func: "+",
			 args: [2, 3]}]}))));
	
})()
