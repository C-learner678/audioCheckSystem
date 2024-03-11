// Generated from C:/files/Java/audioCheck/src/main/antlr4/ruleTool/parserRules.g4 by ANTLR 4.13.1
package com.jlu.audiocheck.ruleToolGenerated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link parserRulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface parserRulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link parserRulesParser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(parserRulesParser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(parserRulesParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(parserRulesParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code wordExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWordExpression(parserRulesParser.WordExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(parserRulesParser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpression(parserRulesParser.ParenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code defaultAfterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultAfterExpression(parserRulesParser.DefaultAfterExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code afterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAfterExpression(parserRulesParser.AfterExpressionContext ctx);
}