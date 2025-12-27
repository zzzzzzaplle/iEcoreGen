package edu.ustb.sei.mde.eecg.tools

import edu.ustb.sei.mde.mwe2.enhancement.FileTools
import java.util.function.Consumer
import org.eclipse.emf.ecore.EClass
import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.MethodDeclaration

class CodeTools extends FileTools {
	static public val instance = new CodeTools
	
	def ClassLens createClassLens(EClass c, String code) {
		val clsBlk = new ClassLens(c, code)
		return clsBlk
	}
}

class MethodVisitor extends ASTVisitor {
	val Consumer<MethodDeclaration> nodeConsumer
	
	new(Consumer<MethodDeclaration> nodeConsumer) {
		this.nodeConsumer = nodeConsumer
	}
	
	override visit(MethodDeclaration node) {
		nodeConsumer.accept(node)
		return false
	}
	
}

class FieldVisitor extends ASTVisitor {
	val Consumer<FieldDeclaration> nodeConsumer
	
	new(Consumer<FieldDeclaration> nodeConsumer) {
		this.nodeConsumer = nodeConsumer
	}
	
	override visit(FieldDeclaration node) {
		nodeConsumer.accept(node)
		return false
	}
}
