package edu.ustb.sei.mde.eecg.tools

import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.core.dom.ASTNode

class MethodLens {
	public val MethodDeclaration method
	public val String name
	public val String signature
	val ClassLens parent
	
	public var int startLine = 0
	public var int endLine = 0
	
	new(MethodDeclaration m, ClassLens cls) {
		this.method = m
		this.name = method.name.identifier
		this.parent = cls
		
		val signatureStart = if(m.javadoc === null) m.startPosition else m.javadoc.startPosition + m.javadoc.length
		val signatureEnd = if(m.body === null) m.startPosition + m.length else m.body.startPosition
		
		this.signature = cls.source.substring(signatureStart, signatureEnd).trim
	}
	
	protected def String getNodeString(ASTNode node) {
		return parent.source.substring(node.startPosition, node.startPosition + node.length)
	}
	
	def String getMethodString() {
		getNodeString(method)
	}
	
	def String getBody() {
		if(method.body === null) return null
		else {
			return method.body.nodeString
		}
	}
	
	def String getJavadoc() {
		if(method.javadoc === null) return null
		else {
			return method.javadoc.nodeString
		}
	}
}
