grammar lexerRules;

AFTER : '#' ;
OR : '|' ;
AND : '&' ;
LPAREN : '(' ;
RPAREN : ')' ;
NOT : '!' ;

INT : [1-9][0-9]* ;

WORD : [a-zA-Z0-9\u4e00-\u9fa5]+ ; //英文、数字、中文

WS : [ \t\n\r]+ -> skip;

