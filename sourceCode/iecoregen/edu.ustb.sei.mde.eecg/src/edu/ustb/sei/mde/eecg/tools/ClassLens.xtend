package edu.ustb.sei.mde.eecg.tools

import org.eclipse.emf.ecore.EClass
import org.eclipse.jdt.core.dom.CompilationUnit
import java.util.List
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.dom.AST
import java.util.Map
import org.eclipse.jdt.core.dom.TypeDeclaration
import java.util.Set
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite
import org.eclipse.jface.text.Document
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.jdt.core.dom.ImportDeclaration
import org.eclipse.jdt.core.dom.ASTMatcher
import java.util.function.BiConsumer
import org.eclipse.jdt.core.dom.BodyDeclaration
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration
import org.eclipse.jdt.core.dom.FieldDeclaration
import org.eclipse.jdt.core.dom.VariableDeclarationFragment
import org.eclipse.jdt.core.dom.MethodDeclaration
import java.util.regex.Pattern
import static edu.ustb.sei.mde.eecg.tools.CharTool.*;


class ClassLens {
	public EClass eClass
	public val String source
	
	val CompilationUnit unit;
	
	var List<MethodLens> methods;
	
	new(EClass eClass, String source) {
		this.eClass = eClass
		this.source = source
		this.unit = parseAST
	}
	
	def AST getUnitAST() {
		unit.AST
	}
	
	protected def CompilationUnit parseAST() {
		val parser = ASTParser.newParser(AST.JLSLatest)
		
		val options = JavaCore.getOptions() as Map<String,String>
		 		
		JavaCore.setComplianceOptions(JavaCore.VERSION_10, options)
		 		
		parser.setCompilerOptions(options);
		
		parser.source = this.source.toCharArray
		 		
		val unit = parser.createAST(null) as CompilationUnit
		
		return unit
	}
	
	def TypeDeclaration getType() {
		unit.types.get(0) as TypeDeclaration
	}
	
	def String minimizeOperations(Set<String> operationNames) {
		val doc = new Document(source)
		
		val unitCopy = parseAST
		
		val rewrite = ASTRewrite.create(unitCopy.AST)
		
		val mMinimizer = new MethodVisitor[
			if(!operationNames.contains(it.name.identifier)) {
				rewrite.remove(it.javadoc, null)
				val emptyBody = rewrite.createStringPlaceholder("{ ... /* omitted */ }", ASTNode.BLOCK)
				rewrite.replace(it.body, emptyBody, null)
			}
		]
		
		unitCopy.accept(mMinimizer)
		
		val fMinimizer = new FieldVisitor[
			rewrite.remove(it.javadoc, null)
		]
		
		unitCopy.accept(mMinimizer)
		unitCopy.accept(fMinimizer)
		
		val edits = rewrite.rewriteAST(doc, null)
		
		edits.apply(doc)
		
		return doc.get
	}
	
	def List<MethodLens> getMethods() {
		if(this.methods === null) {
			this.methods = newArrayList
			
			val majorType = this.type
			
			val methodLensCons = new MethodVisitor[
				if(it.parent === majorType) {					
					val mLens = new MethodLens(it, this)
					this.methods += mLens
				}
			]
			
			this.unit.accept(methodLensCons)
			
			this.computeMethodLines
		}
		
		return this.methods
	}
	
	protected def void computeMethodLines() {
		val linePositionMap = computeLineMap()
		
		var currentLine = 1
		
		methods.sortBy[it.method.startPosition]
		
		for(ml : methods) {
			currentLine = ml.startLine = linePositionMap.getLineByPosition(ml.method.startPosition, currentLine)
			currentLine = ml.endLine = linePositionMap.getLineByPosition(ml.method.startPosition + ml.method.length, currentLine)
		}
	}
	
	private def Map<Integer,Integer> computeLineMap() {
		val linePositionMap = <Integer,Integer>newHashMap
		
		if(source.isNullOrEmpty) {
			linePositionMap.put(1, 0)
		} else {
			var currentLine = 1
			var currentPos = 0
			val length = source.length
			
			linePositionMap.put(1, 0)
			
			while(currentPos < length) {
				val currentChar = source.charAt(currentPos)
				
				if(currentChar === linebreakR) {
					currentLine ++
					currentPos ++
					
					if(currentPos < length) {
						linePositionMap.put(currentLine, currentPos)
					}
					
					if(currentPos < length && source.charAt(currentPos) === linebreakN) {
						currentPos ++
						
						if(currentPos < length) {
							linePositionMap.put(currentLine, currentPos) // remap
						}
					}
				} else if(currentChar === linebreakN) {
					currentLine ++
					currentPos ++
					
					if(currentPos < length) {
						linePositionMap.put(currentLine, currentPos)
					}
					
					if(currentPos < length && source.charAt(currentPos) === linebreakR) {
						currentPos ++
						
						if(currentPos < length) {
							linePositionMap.put(currentLine, currentPos) // remap
						}
					}
				} else {
					currentPos ++
				}
			}
			
			linePositionMap.put(linePositionMap.size + 1, length) // pseudo line
		}
		
		linePositionMap
	}
	
	
	private def static int getLineByPosition(Map<Integer, Integer> lineMap, int pos, int currentLine) {
		if(currentLine < 1) {
			println("ERROR in currentLine")
		}
		
		for(var i = currentLine; i < lineMap.size; i ++) {
			val lineStart = lineMap.get(i)
			val nextLineStart = lineMap.getOrDefault(i+1, Integer.MAX_VALUE)
			
			if(lineStart <= pos && pos < nextLineStart) return i
		}
		
		return -1
	}
	
	def MethodLens getMethodByName(String name) {
		return getMethods.findFirst[it.name == name]
	}
	
	def MethodLens getAccessorMethodByFeatureName(EStructuralFeature f, boolean getter) {
		val upf = f.name.toFirstUpper
		
		val methName = 
			if(getter) {
				if(f.EType.instanceTypeName=='boolean' || f.EType.instanceTypeName=='java.lang.Boolean') {
					'is'+upf
				} else 'get'+upf
			} else 'set'+upf

		return getMethodByName(methName)
	}
	
	def protected String getNodeString(ASTNode node) {
		return this.source.substring(node.startPosition, node.startPosition + node.length)
	}
	
	def String merge(ClassLens newVersion, Set<String> methods) {
		return merge(this, newVersion, methods)
	}
	
	static def String merge(ClassLens oldVersion, ClassLens newVersion, Set<String> methods) {
		val rewrite = ASTRewrite.create(oldVersion.unit.AST)
		
		rewrite.mergeImports(oldVersion, newVersion)
		rewrite.mergeMethods(oldVersion, newVersion, methods)[r, m|
			val oldDoc = m.javadoc
			val newDoc = oldDoc.replace('@generated', '@generated NOT')
			val replacement = r.createStringPlaceholder(newDoc, ASTNode.JAVADOC)
			
			r.replace(m.method.javadoc, replacement, null)
		]
		
		rewrite.appendAdditionalBody(oldVersion, newVersion)
		
		val doc = new Document(oldVersion.source)
		val edits = rewrite.rewriteAST(doc, null)
		edits.apply(doc)
		
		return doc.get
	}
	
	static def void mergeImports(ASTRewrite rewrite, ClassLens oldVersion, ClassLens newVersion) {
		val impRewrite = rewrite.getListRewrite(oldVersion.unit, CompilationUnit.IMPORTS_PROPERTY)
		val oldImp = oldVersion.unit.imports.map[it as ImportDeclaration]
		val newImp = newVersion.unit.imports.map[it as ImportDeclaration]
		
		val matcher = new ASTMatcher
		
		newImp.filter[ni|!oldImp.exists[oi| ni.subtreeMatch(matcher, oi)]].forEach[
			impRewrite.insertLast(it, null)
		]
	}
	
	static def void mergeMethods(ASTRewrite rewrite, ClassLens oldVersion, ClassLens newVersion, Set<String> methodNames, BiConsumer<ASTRewrite, MethodLens> additionalEdit) {
		for(String methodName : methodNames) {
			val oldMethod = oldVersion.getMethodByName(methodName)
			val newMethod = newVersion.getMethodByName(methodName)
			
			if(oldMethod === null || newMethod === null) {
				println('Missing '+methodName)
			} else {
				val replacement = rewrite.createStringPlaceholder(newMethod.body, ASTNode.BLOCK)
				rewrite.replace(oldMethod.method.body, replacement, null)
				
				if(additionalEdit !== null)
					additionalEdit.accept(rewrite, oldMethod)
			}
		}
		
//		val bodyRewriter = rewrite.getListRewrite(oldVersion.type, TypeDeclaration.BODY_DECLARATIONS_PROPERTY)
//		
//		for(newMethod : newVersion.getMethods) {
//			if(oldVersion.getMethodByName(newMethod.name) === null) {
//				val copy = rewrite.createStringPlaceholder(newMethod.methodString, ASTNode.METHOD_DECLARATION)
//				bodyRewriter.insertLast(copy, null)
//			}
//		}
	}
	
	static def void appendAdditionalBody(ASTRewrite rewrite, ClassLens oldVersion, ClassLens newVersion) {
		rewrite.appendAdditionalTopLevelType(oldVersion, newVersion)
		rewrite.appendAdditionalTypeBody(oldVersion, newVersion)
	}
	
	static protected def void appendAdditionalTopLevelType(ASTRewrite rewrite, ClassLens oldVersion, ClassLens newVersion) {
		val oldTypes = oldVersion.unit.types.map[it as TypeDeclaration].toMap([it.name.identifier])
		val newTypes = newVersion.unit.types.map[it as TypeDeclaration].toMap([it.name.identifier])
		
		val typeLstRewrite = rewrite.getListRewrite(oldVersion.unit, CompilationUnit.TYPES_PROPERTY)
		
		for(entry : newTypes.entrySet) {
			if(oldTypes.containsKey(entry.key)===false) {
				val copy = rewrite.createStringPlaceholder(newVersion.getNodeString(entry.value).appendJDocAnnotation("@LLM"), ASTNode.TYPE_DECLARATION)
				typeLstRewrite.insertLast(copy, null)
			}
		}
	}
	
	static protected def void appendAdditionalTypeBody(ASTRewrite rewrite, ClassLens oldVersion, ClassLens newVersion) {
		val oldType0 = oldVersion.type
		val newType0 = newVersion.type
		
		val oldBodyMap = oldType0.bodyDeclarations.map[it as BodyDeclaration].map[it.name->it].filter[it.key !== null].toMap([it.key], [it.value])
		val newBodyMap = newType0.bodyDeclarations.map[it as BodyDeclaration].map[it.name->it].filter[it.key !== null].toMap([it.key], [it.value])
		
		
		val bodyLstRewrite = rewrite.getListRewrite(oldType0, TypeDeclaration.BODY_DECLARATIONS_PROPERTY)
		
		for(entry : newBodyMap.entrySet) {
			if(oldBodyMap.containsKey(entry.key)===false) {
				val copy = rewrite.createStringPlaceholder(newVersion.getNodeString(entry.value).appendJDocAnnotation("@LLM"), entry.value.nodeType)
				bodyLstRewrite.insertLast(copy, null)
			}
		}
	}
	
	static protected def String getName(BodyDeclaration node) {
		switch(node) {
			AbstractTypeDeclaration: node.name.identifier
			AnnotationTypeMemberDeclaration: node.name.identifier
			FieldDeclaration: node.fragments.map[it as VariableDeclarationFragment].findFirst[true].name.identifier
			MethodDeclaration: node.name.identifier
			default: null
		}
	}
	
	static protected def String appendJDocAnnotation(String code, String ann) {
		val pattern = Pattern.compile('''/\*\*(.*)\*/''', Pattern.DOTALL)
		val matcher = pattern.matcher(code)
		if(matcher.find) {
			val doc = matcher.group
			if(doc.contains(ann)===false) {
				val newdoc = doc.replace("*/", "* "+ann+" */")
				code.replace(doc, newdoc)
			} else code
		} else {
			'''/** «ann» */ «code»'''
		}
	}
	
}