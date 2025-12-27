package edu.ustb.sei.mde.eecg.llm.enhancement

class ErrorInfo {
	public var String filePath
	public var int lineNumber
    public var String errorType
    public var String message
    public var String codeLine
    public var String caretPosition
    
	override toString() {
		'''
		File: «filePath»
		Line: «lineNumber»
		Type: «errorType»
		Message: «message»
		Code line: «codeLine»
		Caret Pos: «caretPosition»
		'''
	}
				
}