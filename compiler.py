def get_stack_size(prog):
    return 10


def gen_class(name, contents):
    return '\n'.join([
        '.class {0}'.format(name),
        '.super java/lang/Object',
        '.method public <init>()V',
        'aload_0',
        'invokenonvirtual java/lang/Object/<init>()V',
        'return',
        '.end method',
        contents,
    ])


def gen_main(body):
    return '\n'.join([
        '.method public static main([Ljava/lang/String;)V',
        '.limit stack {0}'.format(get_stack_size(body)),
        body,
        'return',
        '.end method',
    ])


def binary_op(instruction, operands):
    if len(operands) != 2:
        raise RuntimeError('Wrong number of operands for binary op')
    return '\n'.join([
        translate(operands[0]),
        translate(operands[1]),
        instruction,
    ])
        
def translate(prog):
    builtins = {
        '+': lambda args: binary_op('iadd', args),
        '-': lambda args: binary_op('isub', args),
        '*': lambda args: binary_op('imul', args),
        '/': lambda args: binary_op('idiv', args),
        'println': lambda args: '\n'.join([
            "getstatic java/lang/System/out Ljava/io/PrintStream;",
            translate(args[0]),
            "invokevirtual java/io/PrintStream/println(I)V",
        ]),
    }
    
    if isinstance(prog, list):
        sym = prog[0]
        args = prog[1:]

        if sym in builtins:
            return builtins[sym](args)
        else:
            raise RuntimeError('Unrecognized builtin')
    else:
        return 'ldc {0}'.format(prog)


def main():
    print gen_class('Main', gen_main(translate(['println', ['+', 2, 3]])))


if __name__ == '__main__':
    main()
