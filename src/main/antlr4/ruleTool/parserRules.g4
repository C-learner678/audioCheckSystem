grammar parserRules;
import lexerRules;

pattern: expr ;

expr : leftExpr = expr OR rightExpr = expr      # orExpression
     | leftExpr = expr AND rightExpr = expr     # andExpression
     | LPAREN expr RPAREN                       # parenExpression
     | NOT expr                                 # notExpression
     | WORD AFTER WORD AFTER INT                # afterExpression
     | WORD AFTER WORD                          # defaultAfterExpression
     | WORD                                     # wordExpression
     ;


