package edu.ustb.sei.mde.mwe2.enhancement

import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.FileOutputStream

class FileTools {
	static public val instance = new FileTools
	
	def String loadFile(File file) {
		val reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))
		var String str
		val buffer = new StringBuffer
		
		while((str=reader.readLine) !== null) {
			buffer.append(str)
			buffer.append('\n')
		}
		
		reader.close
		
		return buffer.toString
	}
	
	def void saveFile(File file, String content) {
		val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))
		writer.append(content)
		writer.flush
		writer.close
	}
}