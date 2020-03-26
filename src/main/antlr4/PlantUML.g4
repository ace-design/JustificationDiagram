grammar PlantUML;

/*
 * Parser Rules
 */

diagram:
    START
    (title=TITLE)?
    instruction*
    END
    EOF;

instruction:
    declaration |
    relation;

declaration:
    element |
    conclusion;

element:
    TYPE ALIAS '=' label=STRING;

conclusion:
    'conclusion' ALIAS '=' label=STRING ('-' restriction=STRING)?;

relation:
    ALIAS LINK ALIAS;


/*
 * Lexer Rules
 */

WHITESPACE: (' ' | '\t')+ -> skip;
NEWLINE:  ('\r'? '\n' | '\r')+ -> skip;

START: '@startuml';
END: '@enduml';

TITLE: 'title ' ('A'..'Z' | 'a'..'z' | '1'..'9' | ' ')+ '\n';

TYPE: ('subconclusion' | 'strategy' | 'rationale' | 'domain' | 'support');
ALIAS: ('A'..'Z' | 'a'..'z' | '1'..'9')+;
LINK: ('-->');

STRING : '"' STRING_CHAR*? '"';
STRING_CHAR : ~('\r' | '\n');
