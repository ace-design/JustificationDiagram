grammar PlantUML;

/*
 * Parser Rules
 */

diagram:
    START NEWLINE+
    (title=TITLE NEWLINE?)?
    (ITEM WHITESPACE LINK WHITESPACE ITEM NEWLINE?)
    END NEWLINE?
    EOF;



/*
 * Lexer Rules
 */

WHITESPACE: (' ' | '\t')+;
NEWLINE:  ('\r'? '\n' | '\r')+;

START: '@startuml';
END: '@enduml';

TITLE: 'title ' ('A'..'Z' | 'a'..'z' | '1'..'9' | ' ')+ '\n';
AS: 'as';

ITEM: ('A'..'Z' | 'a'..'z' | '1'..'9')+;
LINK: ('--');
ID: ('A'..'Z' | 'a'..'z' | '1'..'9')+;



