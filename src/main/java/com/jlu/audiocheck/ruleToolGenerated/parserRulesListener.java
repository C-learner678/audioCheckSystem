// Generated from C:/files/Java/audioCheck/src/main/antlr4/ruleTool/parserRules.g4 by ANTLR 4.13.1
package com.jlu.audiocheck.ruleToolGenerated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link parserRulesParser}.
 */
public interface parserRulesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link parserRulesParser#pattern}.
	 * @param ctx the parse tree
	 */
	void enterPattern(parserRulesParser.PatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link parserRulesParser#pattern}.
	 * @param ctx the parse tree
	 */
	void exitPattern(parserRulesParser.PatternContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(parserRulesParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(parserRulesParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(parserRulesParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(parserRulesParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code wordExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterWordExpression(parserRulesParser.WordExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code wordExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitWordExpression(parserRulesParser.WordExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(parserRulesParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(parserRulesParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(parserRulesParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(parserRulesParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code defaultAfterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDefaultAfterExpression(parserRulesParser.DefaultAfterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code defaultAfterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDefaultAfterExpression(parserRulesParser.DefaultAfterExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code afterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAfterExpression(parserRulesParser.AfterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code afterExpression}
	 * labeled alternative in {@link parserRulesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAfterExpression(parserRulesParser.AfterExpressionContext ctx);
}