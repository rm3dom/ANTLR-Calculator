/*
 * Math Rules
 */

grammar Math;

@header {
package org.example.gka.math;
}

math:  scalar | linear | EOF;

linear: (scalar | term) sumop (scalar | term | linear) ;

scalar: term scaleop (term | scalar);

term : number | '(' math ')';


sumop
   : PLUS
   | MINUS
   ;

scaleop
   : MULTIPLY
   | DIVIDE
   ;

number: NUMBER;

PLUS                            : '+' ;
MINUS                           : '-' ;
MULTIPLY                        : '*' ;
DIVIDE                          : '/' ;


NUMBER                     : [0-9\\.]+ ;

WS                         : [ \r\n\t]+ -> skip ;